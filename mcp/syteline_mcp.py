#!/usr/bin/env python3
"""
syteline_mcp — MCP server for Infor Syteline (CloudSuite Industrial) IDO REST API.

Exposes the three IDO REST verbs (Load / Invoke / Update) plus convenience
discovery tools (find_ido, describe_ido, method_signature). Designed for the
two-user model from syteline-ido-api-agent-docs:

  - Agent user (introspection-only) is used by default
  - Automation user (per-IDO write permissions) is used only when the caller
    explicitly opts in via as_automation=True

Transport: stdio (runs as a subprocess on Brice's workstation)

Configuration (environment variables):
  SYTELINE_BASE_URL              e.g. http://s10dev.salinavortex.com/IDORequestService/ido
  DEFAULT_SITE                   e.g. Pilot_Vortx
  SYTELINE_AGENT_USERNAME        introspection-only Syteline user
  SYTELINE_AGENT_PASSWORD
  SYTELINE_AUTOMATION_USERNAME   optional, for /invoke writes and /update
  SYTELINE_AUTOMATION_PASSWORD

Usage:
  export SYTELINE_BASE_URL=http://s10dev.salinavortex.com/IDORequestService/ido
  export DEFAULT_SITE=Pilot_Vortx
  export SYTELINE_AGENT_USERNAME=claude1
  export SYTELINE_AGENT_PASSWORD=claude
  python syteline_mcp.py
"""

import json
import os
import sys
from enum import Enum
from typing import Any, Dict, List, Optional

import httpx
from mcp.server.fastmcp import FastMCP
from pydantic import BaseModel, ConfigDict, Field

# ---------------------------------------------------------------------------
# Server init
# ---------------------------------------------------------------------------

mcp = FastMCP("syteline_mcp")

# ---------------------------------------------------------------------------
# Config — read from environment at startup
# ---------------------------------------------------------------------------

SYTELINE_BASE_URL: str = os.environ.get("SYTELINE_BASE_URL", "").rstrip("/")
DEFAULT_SITE: str = os.environ.get("DEFAULT_SITE", "")

AGENT_USERNAME: str = os.environ.get("SYTELINE_AGENT_USERNAME", "")
AGENT_PASSWORD: str = os.environ.get("SYTELINE_AGENT_PASSWORD", "")
AUTOMATION_USERNAME: str = os.environ.get("SYTELINE_AUTOMATION_USERNAME", "")
AUTOMATION_PASSWORD: str = os.environ.get("SYTELINE_AUTOMATION_PASSWORD", "")

if not SYTELINE_BASE_URL:
    print("WARNING: SYTELINE_BASE_URL is not set. All API calls will fail.", file=sys.stderr)
if not AGENT_USERNAME or not AGENT_PASSWORD:
    print("WARNING: SYTELINE_AGENT_USERNAME / SYTELINE_AGENT_PASSWORD not set.", file=sys.stderr)


# ---------------------------------------------------------------------------
# Token cache — keyed by (username, site_config). Refreshed on session-deleted.
# ---------------------------------------------------------------------------

_token_cache: Dict[tuple, str] = {}


async def _acquire_token(client: httpx.AsyncClient, username: str, password: str, site: str) -> str:
    url = f"{SYTELINE_BASE_URL}/token/{site}"
    resp = await client.get(url, headers={"username": username, "password": password})
    resp.raise_for_status()
    data = resp.json()
    if not data.get("Success"):
        raise RuntimeError(f"token acquisition failed for {username}/{site}: {data.get('Message')}")
    return data["Token"]


async def _get_token(client: httpx.AsyncClient, username: str, password: str, site: str, *, force_refresh: bool = False) -> str:
    key = (username, site)
    if not force_refresh and key in _token_cache:
        return _token_cache[key]
    token = await _acquire_token(client, username, password, site)
    _token_cache[key] = token
    return token


def _resolve_user(as_automation: bool) -> tuple:
    if as_automation:
        if not AUTOMATION_USERNAME or not AUTOMATION_PASSWORD:
            raise RuntimeError(
                "Automation user not configured. Set SYTELINE_AUTOMATION_USERNAME "
                "and SYTELINE_AUTOMATION_PASSWORD to use as_automation=True."
            )
        return (AUTOMATION_USERNAME, AUTOMATION_PASSWORD)
    return (AGENT_USERNAME, AGENT_PASSWORD)


def _is_session_deleted(response_json: Dict[str, Any]) -> bool:
    msg = (response_json.get("Message") or "").lower()
    return "session" in msg and "deleted" in msg


async def _ido_request(
    method: str,
    path: str,
    *,
    as_automation: bool,
    site: Optional[str] = None,
    params: Optional[Dict[str, Any]] = None,
    json_body: Any = None,
) -> Dict[str, Any]:
    site_to_use = site or DEFAULT_SITE
    if not site_to_use:
        raise RuntimeError("No site configured. Set DEFAULT_SITE or pass site=...")

    username, password = _resolve_user(as_automation)
    url = f"{SYTELINE_BASE_URL}/{path.lstrip('/')}"

    async with httpx.AsyncClient(timeout=60.0) as client:
        for attempt in (0, 1):
            token = await _get_token(client, username, password, site_to_use, force_refresh=(attempt == 1))
            headers = {"Authorization": token}
            if json_body is not None:
                headers["Content-Type"] = "application/json"
            resp = await client.request(method, url, headers=headers, params=params, json=json_body)
            resp.raise_for_status()
            data = resp.json()
            if data.get("Success") is False and attempt == 0 and _is_session_deleted(data):
                continue
            return data
        return data


def _handle_error(e: Exception) -> str:
    if isinstance(e, httpx.HTTPStatusError):
        code = e.response.status_code
        try:
            detail = e.response.json().get("Message") or e.response.text[:300]
        except Exception:
            detail = e.response.text[:300]
        messages = {
            400: f"Bad request — check IDO/method names and parameter format. {detail}",
            401: "Unauthorized — token may be invalid. Check Syteline credentials.",
            403: f"Forbidden — user lacks permission for this IDO. {detail}",
            404: "Not found — endpoint or IDO does not exist on this Syteline server.",
            405: "Method not allowed. /load is GET only; /invoke and /update are POST only.",
            500: f"Syteline server error. {detail}",
        }
        return f"Error {code}: {messages.get(code, str(e))}"
    if isinstance(e, httpx.TimeoutException):
        return "Error: Syteline IDO Runtime did not respond. Check VPN/network."
    return f"Error: {type(e).__name__}: {e}"


# ---------------------------------------------------------------------------
# Shared
# ---------------------------------------------------------------------------


class ResponseFormat(str, Enum):
    MARKDOWN = "markdown"
    JSON = "json"


# ---------------------------------------------------------------------------
# Tool 1: syteline_load — read-only LoadCollection
# ---------------------------------------------------------------------------


class LoadInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    ido: str = Field(..., description="IDO collection name, e.g. 'UserNames' or 'IdoMethods'.", min_length=1, max_length=128)
    properties: Optional[str] = Field(default=None, description="Comma-delimited property list (e.g. 'Username,UserDesc'). Omit to return all properties.")
    filter: Optional[str] = Field(default=None, description="SQL WHERE clause (NOT URL-encoded — server encodes for you). Use N'...' for string literals. Example: \"CollectionName = N'UserNames'\".")
    orderby: Optional[str] = Field(default=None, description="Comma-delimited sort. Append DESC per column as needed.")
    recordcap: int = Field(default=10, ge=0, le=10000, description="Max rows. -1 = 200 (server default), 0 = all rows, N = N rows. Default 10 for safety.")
    site: Optional[str] = Field(default=None, description="Override the site config (default: DEFAULT_SITE).")
    as_automation: bool = Field(default=False, description="Use the automation user instead of the agent user. Required when reading business-data IDOs that the agent user cannot access.")
    response_format: ResponseFormat = Field(default=ResponseFormat.MARKDOWN)


@mcp.tool(
    name="syteline_load",
    annotations={
        "title": "Syteline LoadCollection (read)",
        "readOnlyHint": True,
        "destructiveHint": False,
        "idempotentHint": True,
        "openWorldHint": True,
    },
)
async def syteline_load(params: LoadInput) -> str:
    """
    Query rows from a Syteline IDO via the IDO REST API /load endpoint.

    This is read-only. Uses the agent user by default; pass as_automation=True
    only when the agent user lacks read permission on the target IDO (the
    agent user is intentionally scoped to meta-IDOs only).

    Args:
        params (LoadInput):
            - ido: IDO collection name (e.g. 'UserNames')
            - properties: optional comma-delimited property list
            - filter: optional SQL WHERE clause (use N'...' for strings)
            - orderby: optional sort
            - recordcap: row cap (default 10)
            - site: optional site override
            - as_automation: opt-in to automation user
            - response_format: 'markdown' or 'json'

    Returns:
        Items from the IDO with metadata (MoreRowsExist, Bookmark).
    """
    query: Dict[str, Any] = {"recordcap": params.recordcap}
    if params.properties:
        query["properties"] = params.properties
    if params.filter:
        query["filter"] = params.filter
    if params.orderby:
        query["orderby"] = params.orderby

    try:
        data = await _ido_request(
            "GET",
            f"load/{params.ido}",
            as_automation=params.as_automation,
            site=params.site,
            params=query,
        )
    except Exception as e:
        return _handle_error(e)

    if not data.get("Success"):
        return f"LoadCollection failed: {data.get('Message')}"

    if params.response_format == ResponseFormat.JSON:
        return json.dumps(data, indent=2)

    items = data.get("Items") or []
    more = data.get("MoreRowsExist")
    lines = [
        f"## /load/{params.ido}",
        f"_{len(items)} row(s) returned" + (" (MoreRowsExist=true)" if more else "") + "_",
        "",
    ]
    if not items:
        lines.append("No rows match.")
        return "\n".join(lines)
    if isinstance(items[0], dict):
        cols = [k for k in items[0].keys() if not k.startswith("_")]
        if cols:
            lines.append("| " + " | ".join(cols) + " |")
            lines.append("|" + "|".join(["---"] * len(cols)) + "|")
            for item in items:
                row = [str(item.get(c, "") if item.get(c) is not None else "—") for c in cols]
                lines.append("| " + " | ".join(row) + " |")
            return "\n".join(lines)
    lines.append("```json")
    lines.append(json.dumps(items, indent=2))
    lines.append("```")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Tool 2: syteline_invoke — execute a method
# ---------------------------------------------------------------------------


class InvokeInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    ido: str = Field(..., description="IDO collection that hosts the method.", min_length=1, max_length=128)
    method: str = Field(..., description="Method name (case-sensitive).", min_length=1, max_length=128)
    parameters: List[Any] = Field(..., description="Flat positional parameter array. Pass null for output-only params and Infobar. ALL positions must be present (no shorter array).")
    site: Optional[str] = Field(default=None, description="Override the site config.")
    as_automation: bool = Field(default=False, description="Use the automation user. Required for any method that reads or writes business data.")
    response_format: ResponseFormat = Field(default=ResponseFormat.MARKDOWN)


@mcp.tool(
    name="syteline_invoke",
    annotations={
        "title": "Syteline Invoke (execute IDO method)",
        "readOnlyHint": False,
        "destructiveHint": True,
        "idempotentHint": False,
        "openWorldHint": True,
    },
)
async def syteline_invoke(params: InvokeInput) -> str:
    """
    Execute a method on an IDO via /invoke. Use syteline_method_signature first
    to discover the exact parameter sequence and direction (input/output/inout).

    Many invokes mutate state (BGTaskSubmit, ItemPriceChangeSp, ChangeCOStatus)
    — the destructive hint is on by default. Confirm with the user before
    calling unfamiliar methods.

    Args:
        params (InvokeInput):
            - ido: hosting IDO
            - method: method name (case-sensitive)
            - parameters: flat positional array (length must match signature)
            - site: optional site override
            - as_automation: opt-in to automation user (required for writes)
            - response_format: 'markdown' or 'json'

    Returns:
        ReturnValue, Parameters[] (echoed inputs + output values), Infobar text.
    """
    try:
        data = await _ido_request(
            "POST",
            f"invoke/{params.ido}",
            as_automation=params.as_automation,
            site=params.site,
            params={"method": params.method},
            json_body=params.parameters,
        )
    except Exception as e:
        return _handle_error(e)

    if params.response_format == ResponseFormat.JSON:
        return json.dumps(data, indent=2)

    rv = data.get("ReturnValue")
    success = data.get("Success")
    msg = data.get("Message")
    out_params = data.get("Parameters") or []
    lines = [
        f"## /invoke/{params.ido}?method={params.method}",
        "",
        f"- **Success:** {success}",
        f"- **ReturnValue:** {rv}" + ("  (validation error — check Infobar)" if rv == "16" else ""),
    ]
    if msg:
        lines.append(f"- **Message:** {msg}")
    if out_params:
        lines.append("")
        lines.append("**Parameters returned (positional):**")
        for i, val in enumerate(out_params):
            lines.append(f"  {i}: {val!r}")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Tool 3: syteline_update — insert/update/delete
# ---------------------------------------------------------------------------


class UpdateInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    ido: str = Field(..., description="IDO collection name.", min_length=1, max_length=128)
    action: int = Field(..., description="1=Insert, 2=Update, 4=Delete.", ge=1, le=4)
    item_id: Optional[str] = Field(default=None, description="The _ItemId from a prior /load (drop the underscore). Required for Update/Delete; required as 'PBT=[<table>]' template for Insert.")
    properties: Optional[List[Dict[str, Any]]] = Field(default=None, description="For Insert/Update: array of {Name, Value, Modified, IsNull}. Omit for Delete.")
    refresh: bool = Field(default=False, description="If true, server returns refreshed property values in RefreshItems.")
    site: Optional[str] = Field(default=None, description="Override the site config.")
    response_format: ResponseFormat = Field(default=ResponseFormat.MARKDOWN)


@mcp.tool(
    name="syteline_update",
    annotations={
        "title": "Syteline UpdateCollection (insert / update / delete)",
        "readOnlyHint": False,
        "destructiveHint": True,
        "idempotentHint": False,
        "openWorldHint": True,
    },
)
async def syteline_update(params: UpdateInput) -> str:
    """
    Insert, update, or delete a single row in a Syteline IDO via /update.

    Always uses the automation user (writes require it; the agent user is
    deliberately scoped out). Sends one Changes entry per request — Syteline
    only processes the first item if you batch them, so loop in the caller.

    Confirm with the user before calling. Run syteline_load first to verify
    the rows you intend to delete and to capture _ItemId values.

    Args:
        params (UpdateInput):
            - ido: target IDO
            - action: 1=Insert, 2=Update, 4=Delete
            - item_id: from prior /load (_ItemId without underscore)
            - properties: [{Name, Value, Modified, IsNull}] for Insert/Update
            - refresh: include refreshed values in response
            - site: optional site override

    Returns:
        Success / Message / RefreshItems from the server.
    """
    if not AUTOMATION_USERNAME:
        return "Error: SYTELINE_AUTOMATION_USERNAME not set — /update requires the automation user."

    if params.action in (1, 2) and not params.properties:
        return "Error: Insert/Update requires 'properties' (array of {Name, Value, Modified, IsNull})."
    if params.action in (2, 4) and not params.item_id:
        return "Error: Update/Delete requires 'item_id' (the _ItemId from a prior /load with the underscore dropped)."

    change: Dict[str, Any] = {"Action": params.action}
    if params.item_id:
        change["ItemId"] = params.item_id
    if params.properties is not None:
        change["Properties"] = params.properties
        change["UpdateLocking"] = 1

    body = {"Changes": [change]}
    query = {"refresh": "true"} if params.refresh else None

    try:
        data = await _ido_request(
            "POST",
            f"update/{params.ido}",
            as_automation=True,
            site=params.site,
            params=query,
            json_body=body,
        )
    except Exception as e:
        return _handle_error(e)

    if params.response_format == ResponseFormat.JSON:
        return json.dumps(data, indent=2)

    success = data.get("Success")
    msg = data.get("Message")
    refresh = data.get("RefreshItems")
    action_name = {1: "Insert", 2: "Update", 4: "Delete"}.get(params.action, "?")
    lines = [
        f"## /update/{params.ido} — {action_name}",
        "",
        f"- **Success:** {success}",
    ]
    if msg:
        lines.append(f"- **Message:** {msg}")
    if refresh:
        lines.append("")
        lines.append("**RefreshItems:**")
        lines.append("```json")
        lines.append(json.dumps(refresh, indent=2))
        lines.append("```")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Tool 4: syteline_find_ido — search IdoCollections
# ---------------------------------------------------------------------------


class FindIdoInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    pattern: str = Field(..., description="Substring to LIKE-match against CollectionName (case-sensitive). E.g. 'ItemPrice'.", min_length=1, max_length=128)
    limit: int = Field(default=20, ge=1, le=200, description="Max IDOs to return.")
    site: Optional[str] = Field(default=None, description="Override the site config.")
    response_format: ResponseFormat = Field(default=ResponseFormat.MARKDOWN)


@mcp.tool(
    name="syteline_find_ido",
    annotations={
        "title": "Find Syteline IDO by name pattern",
        "readOnlyHint": True,
        "destructiveHint": False,
        "idempotentHint": True,
        "openWorldHint": True,
    },
)
async def syteline_find_ido(params: FindIdoInput) -> str:
    """
    Search IdoCollections for IDO names matching a pattern. Casing matters —
    Syteline has IDOs like SLJobMatls AND SLJobmatls as distinct entities,
    so verify exact casing here before using the name elsewhere.

    Args:
        params (FindIdoInput):
            - pattern: substring (LIKE %pattern% match)
            - limit: max results
            - site: optional site override
            - response_format: 'markdown' or 'json'

    Returns:
        IDO names with AccessAs (BaseSyteLine = core, empty = custom).
    """
    filt = f"CollectionName LIKE N'%{params.pattern}%'"
    try:
        data = await _ido_request(
            "GET",
            "load/IdoCollections",
            as_automation=False,
            site=params.site,
            params={"properties": "CollectionName,AccessAs", "filter": filt, "recordcap": params.limit},
        )
    except Exception as e:
        return _handle_error(e)

    items = data.get("Items") or []
    if params.response_format == ResponseFormat.JSON:
        return json.dumps(items, indent=2)

    if not items:
        return f"No IDOs match `%{params.pattern}%`."
    lines = [f"## IDOs matching `%{params.pattern}%`", "", "| CollectionName | AccessAs |", "|---|---|"]
    for item in items:
        access = item.get("AccessAs") or "_(custom)_"
        lines.append(f"| {item['CollectionName']} | {access} |")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Tool 5: syteline_describe_ido — properties + methods + tables
# ---------------------------------------------------------------------------


class DescribeIdoInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    ido: str = Field(..., description="Exact IDO name (case-sensitive).", min_length=1, max_length=128)
    site: Optional[str] = Field(default=None, description="Override the site config.")


@mcp.tool(
    name="syteline_describe_ido",
    annotations={
        "title": "Describe Syteline IDO (properties + methods + tables)",
        "readOnlyHint": True,
        "destructiveHint": False,
        "idempotentHint": True,
        "openWorldHint": True,
    },
)
async def syteline_describe_ido(params: DescribeIdoInput) -> str:
    """
    Convenience: fetch the properties, methods, and backing tables of an IDO
    in one call — the standard discovery flow from 05_DISCOVERY_GUIDE.md.

    Args:
        params (DescribeIdoInput):
            - ido: exact IDO name (case-sensitive — verify with syteline_find_ido)
            - site: optional site override

    Returns:
        Markdown summary with properties, methods, and tables.
    """
    filt = f"CollectionName = N'{params.ido}'"
    site = params.site
    try:
        props = await _ido_request("GET", "load/IdoProperties", as_automation=False, site=site,
            params={"properties": "PropertyName,DataType,PropertyClass,ColumnTableAlias,ColumnName", "filter": filt, "recordcap": 0})
        methods = await _ido_request("GET", "load/IdoMethods", as_automation=False, site=site,
            params={"properties": "MethodName,MethodType", "filter": filt, "recordcap": 0})
        tables = await _ido_request("GET", "load/IdoTables", as_automation=False, site=site,
            params={"properties": "TableName,TableAlias,TableType,JoinType", "filter": filt, "recordcap": 0})
    except Exception as e:
        return _handle_error(e)

    method_type_label = {"0": "SP", "1": "Custom Load", "2": "Extension Class"}
    table_type_label = {"3": "Primary Base", "0": "Secondary"}

    lines = [f"## IDO: `{params.ido}`", ""]

    p_items = props.get("Items") or []
    lines.append(f"### Properties ({len(p_items)})")
    if p_items:
        lines.append("")
        lines.append("| PropertyName | DataType | Class | Column |")
        lines.append("|---|---|---|---|")
        for p in p_items[:200]:
            cls = p.get("PropertyClass") or "0/Bound"
            col = p.get("ColumnName") or "—"
            alias = p.get("ColumnTableAlias")
            col_full = f"{alias}.{col}" if alias and col != "—" else col
            lines.append(f"| {p.get('PropertyName', '?')} | {p.get('DataType', '?')} | {cls} | {col_full} |")
        if len(p_items) > 200:
            lines.append(f"_…{len(p_items) - 200} more properties truncated_")
    lines.append("")

    m_items = methods.get("Items") or []
    lines.append(f"### Methods ({len(m_items)})")
    if m_items:
        lines.append("")
        lines.append("| MethodName | Type |")
        lines.append("|---|---|")
        for m in m_items:
            mt = m.get("MethodType")
            label = method_type_label.get(str(mt), str(mt))
            lines.append(f"| {m.get('MethodName', '?')} | {label} |")
    lines.append("")

    t_items = tables.get("Items") or []
    lines.append(f"### Backing tables ({len(t_items)})")
    if t_items:
        lines.append("")
        lines.append("| TableName | Alias | Role | Join |")
        lines.append("|---|---|---|---|")
        for t in t_items:
            role = table_type_label.get(str(t.get("TableType")), str(t.get("TableType")))
            lines.append(f"| {t.get('TableName', '?')} | {t.get('TableAlias', '?')} | {role} | {t.get('JoinType', '—')} |")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Tool 6: syteline_method_signature — IdoMethodParameters lookup
# ---------------------------------------------------------------------------


class MethodSignatureInput(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True, validate_assignment=True, extra="forbid")

    ido: str = Field(..., description="Exact IDO name.", min_length=1, max_length=128)
    method: str = Field(..., description="Exact method name (case-sensitive).", min_length=1, max_length=128)
    site: Optional[str] = Field(default=None, description="Override the site config.")
    response_format: ResponseFormat = Field(default=ResponseFormat.MARKDOWN)


@mcp.tool(
    name="syteline_method_signature",
    annotations={
        "title": "Get Syteline IDO method parameter signature",
        "readOnlyHint": True,
        "destructiveHint": False,
        "idempotentHint": True,
        "openWorldHint": True,
    },
)
async def syteline_method_signature(params: MethodSignatureInput) -> str:
    """
    Fetch the parameter signature for an IDO method, ordered by Sequence —
    the definitive way to build a syteline_invoke parameter array.

    InputFlag=1 means input; OutputFlag=1 means output; both=1 means IN/OUT
    (pass a value, server overwrites at the same index). Most SP methods end
    with an Infobar param (VARCHAR, IN/OUT) — pass null and read back the
    human-readable result.

    Args:
        params (MethodSignatureInput):
            - ido: hosting IDO
            - method: method name
            - site: optional site override
            - response_format: 'markdown' or 'json'

    Returns:
        Ordered parameter list with Sequence, ParameterName, types, IN/OUT flags.
    """
    filt = f"CollectionName = N'{params.ido}' AND MethodName = N'{params.method}'"
    try:
        data = await _ido_request(
            "GET",
            "load/IdoMethodParameters",
            as_automation=False,
            site=params.site,
            params={
                "properties": "Sequence,ParameterName,DataType,SpDataType,SpDataLength,SpDataScale,InputFlag,OutputFlag",
                "filter": filt,
                "orderby": "Sequence",
                "recordcap": 0,
            },
        )
    except Exception as e:
        return _handle_error(e)

    items = data.get("Items") or []
    if params.response_format == ResponseFormat.JSON:
        return json.dumps(items, indent=2)

    if not items:
        return f"No parameters found for {params.ido}.{params.method}. The method may not exist — try syteline_describe_ido."

    lines = [
        f"## Signature: `{params.ido}.{params.method}` ({len(items)} parameters)",
        "",
        "| Seq | ParameterName | DataType | SpDataType | Length | Direction |",
        "|---|---|---|---|---|---|",
    ]
    for p in items:
        in_flag = p.get("InputFlag") in ("1", 1, True)
        out_flag = p.get("OutputFlag") in ("1", 1, True)
        if in_flag and out_flag:
            direction = "IN/OUT"
        elif out_flag:
            direction = "OUT"
        else:
            direction = "IN"
        sp_type = p.get("SpDataType") or "—"
        sp_len = p.get("SpDataLength") or "—"
        lines.append(f"| {p.get('Sequence', '?')} | {p.get('ParameterName', '?')} | {p.get('DataType', '?')} | {sp_type} | {sp_len} | {direction} |")
    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Entrypoint
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    mcp.run()
