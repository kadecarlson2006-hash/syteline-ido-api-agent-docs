"""SL10 pilot HTTP client — read-only meta-IDO queries.

This client deliberately only supports /load operations on the IDO REST API.
The validation harness must never mutate pilot state, so /invoke and /update
are intentionally not exposed here.
"""

from __future__ import annotations

import json
import os
import urllib.parse
import urllib.request
from dataclasses import dataclass
from typing import Any


class PilotError(RuntimeError):
    pass


@dataclass
class PilotConfig:
    base_url: str
    site_config: str
    username: str
    password: str

    @classmethod
    def from_env(cls) -> "PilotConfig":
        return cls(
            base_url=os.environ["SYTELINE_BASE_URL"].rstrip("/"),
            site_config=os.environ.get("DEFAULT_SITE", "Pilot_Vortx"),
            username=os.environ["SYTELINE_AGENT_USERNAME"],
            password=os.environ["SYTELINE_AGENT_PASSWORD"],
        )


class PilotClient:
    def __init__(self, config: PilotConfig, timeout: float = 30.0):
        self._config = config
        self._timeout = timeout
        self._token: str | None = None

    def _acquire_token(self) -> str:
        url = f"{self._config.base_url}/token/{self._config.site_config}"
        req = urllib.request.Request(
            url,
            method="GET",
            headers={
                "username": self._config.username,
                "password": self._config.password,
            },
        )
        with urllib.request.urlopen(req, timeout=self._timeout) as resp:
            data = json.loads(resp.read())
        if not data.get("Success"):
            raise PilotError(f"token acquisition failed: {data.get('Message')}")
        return data["Token"]

    def _ensure_token(self) -> str:
        if self._token is None:
            self._token = self._acquire_token()
        return self._token

    def load(
        self,
        ido: str,
        *,
        properties: str | None = None,
        filter: str | None = None,
        recordcap: int = 10,
    ) -> dict[str, Any]:
        """LoadCollection on a meta-IDO (read-only)."""
        params: dict[str, str] = {"recordcap": str(recordcap)}
        if properties:
            params["properties"] = properties
        if filter:
            params["filter"] = filter
        qs = urllib.parse.urlencode(params, quote_via=urllib.parse.quote)
        url = f"{self._config.base_url}/load/{ido}?{qs}"
        req = urllib.request.Request(
            url,
            method="GET",
            headers={"Authorization": self._ensure_token()},
        )
        with urllib.request.urlopen(req, timeout=self._timeout) as resp:
            data = json.loads(resp.read())
        if not data.get("Success"):
            raise PilotError(f"LoadCollection on {ido} failed: {data.get('Message')}")
        return data

    def ido_exists(self, name: str) -> bool:
        data = self.load(
            "IdoCollections",
            properties="CollectionName",
            filter=f"CollectionName = N'{name}'",
            recordcap=1,
        )
        return bool(data.get("Items"))

    def method_exists(self, ido: str, method: str) -> dict[str, Any] | None:
        data = self.load(
            "IdoMethods",
            properties="CollectionName,MethodName,MethodType",
            filter=f"CollectionName = N'{ido}' AND MethodName = N'{method}'",
            recordcap=1,
        )
        items = data.get("Items") or []
        return items[0] if items else None

    def method_param_count(self, ido: str, method: str) -> int | None:
        data = self.load(
            "IdoMethodParameters",
            properties="ParameterName",
            filter=f"CollectionName = N'{ido}' AND MethodName = N'{method}'",
            recordcap=0,
        )
        return len(data.get("Items") or [])

    def table_exists(self, table: str) -> bool:
        candidates = [table] if table.endswith("_mst") else [table, f"{table}_mst"]
        for cand in candidates:
            data = self.load(
                "SqlColumns",
                properties="tableName",
                filter=f"tableName = N'{cand}'",
                recordcap=1,
            )
            if data.get("Items"):
                return True
        return False
