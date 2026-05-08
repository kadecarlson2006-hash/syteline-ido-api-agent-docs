---
name: "syteline-rest-api-playbook"
description: "Battle-tested practical guide for Syteline IDO REST API V2 - authentication patterns, token lifecycle, curl templates, BGTask submission, IDO discovery workflow, data type gotchas, and operational best practices"
version: "1.0"
tags:
  - syteline
  - rest
  - api
  - v2
  - ido
  - curl
  - playbook
  - operations
  - mongoose
---

# syteline-rest-api-playbook

Practical operational playbook for the Syteline IDO REST API V2, synthesized from real-world testing and production usage. This skill covers the "how" that the official documentation leaves out.

> **This skill complements the official docs skills** (`syteline-rest-api-v1`, `syteline-rest-api-v2`, `syteline-ido-request-interface`). Those cover the API specification; this covers proven patterns, gotchas, and templates.

---

## API Basics

**Base URL:** `$SYTELINE_BASE_URL` → points to `/IDORequestService/ido` (REST API V2)

**Three core operations:**

| Operation | HTTP Method | Endpoint | Purpose |
|---|---|---|---|
| Load | GET | `/load/{IDO}` | Query data |
| Invoke | POST | `/invoke/{IDO}?method={Method}` | Execute a method |
| Update | POST | `/update/{IDO}` | Insert / Update / Delete |

**Common mistake:** Using POST for `/load` or GET for `/invoke` → `405 Method Not Allowed`.

---

## Authentication

### Two-User Security Model

Use two separate Syteline users with different permission scopes:

- **Agent user** (`$SYTELINE_AGENT_USERNAME`) — introspection only. Has meta-IDO permissions to discover IDO structures, methods, and parameters. Should NOT have access to real business data.
- **Automation user** (`$SYTELINE_AUTOMATION_USERNAME`) — optional. Used to test and execute the API calls the agent builds. Grant task-specific IDO permissions as needed.

Never grant business-data IDO permissions to the agent user. Never grant meta-IDO permissions to the automation user unless needed.

### Getting a Token

Credentials go in HTTP headers — NOT in the URL path.

```bash
# Bash (with jq)
TOKEN=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD" | jq -r '.Token')

# Bash (Windows/MSYS without jq — use PowerShell to parse)
RESPONSE=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD")
TOKEN=$(pwsh -NoProfile -Command "('$RESPONSE' | ConvertFrom-Json).Token")
```

```powershell
# PowerShell — MUST use curl.exe, not Invoke-RestMethod
$TOKEN = (curl.exe -s "$env:SYTELINE_BASE_URL/token/$env:DEFAULT_SITE" `
  -H "username: $env:SYTELINE_AGENT_USERNAME" `
  -H "password: $env:SYTELINE_AGENT_PASSWORD" | ConvertFrom-Json).Token
```

### Token Lifecycle — Critical Behavior

- Tokens do **NOT** expire on a timer
- Tokens are **DESTROYED after any `/invoke` call** (success or failure)
- `/load` and `/update` do NOT destroy the token
- **Always fetch a fresh token immediately before every API call** — do not cache or reuse

### Using the Token

Pass as raw `Authorization` header — NO `Bearer` prefix:

```bash
# Correct
curl -s "$SYTELINE_BASE_URL/load/SomeIDO?properties=Col1" -H "Authorization: $TOKEN"

# WRONG — do not prefix with Bearer
curl -s "..." -H "Authorization: Bearer $TOKEN"
```

### PowerShell: Always Use curl.exe

PowerShell's `Invoke-RestMethod` rejects the Syteline token format — it throws `"The format of value '...' is invalid"` because it tries to parse the `Authorization` header.

**Always use `curl.exe`** (the native Windows binary, not the PowerShell alias) for all Syteline API calls.

### Permissions Model

API sessions check permissions against **IDOs**, not forms. The user must have:
1. IDO-level authorization (LoadCollection, UpdateCollection, Invoke)
2. At least one license module that includes the IDO

If you get `"You are not licensed to use the {FormName} form"`, the user needs IDO-level permissions or a license module covering the target IDO.

### Health Check

```
GET {SYTELINE_BASE_URL without /ido}/Ping.aspx
```

`200` = all running, `503` = IDO Runtime down, `404` = IIS/web service down.

---

## LoadCollection (Query Data)

```bash
curl -s "$SYTELINE_BASE_URL/load/{IDO}?properties={cols}&filter={where}&orderby={sort}&recordcap={limit}" \
  -H "Authorization: $TOKEN"
```

| Parameter | Required | Notes |
|---|---|---|
| `properties` | No | Comma-delimited. Omit to get all properties. |
| `filter` | No | SQL WHERE syntax, URL-encoded. |
| `orderby` | No | Comma-delimited, append `DESC` per column. |
| `recordcap` | No | `-1`=200 rows (default), `0`=all, `N`=N rows. |

**During discovery, always set `recordcap=10` or smaller** to avoid overwhelming responses.

### Filter Syntax

Filters use SQL WHERE syntax, URL-encoded. String literals MUST use `N'...'` (Unicode prefix).

```
# Exact match
filter=TaskName%20%3D%20N'ChangeCOStatusUtility'

# LIKE (starts with)
filter=TaskName%20LIKE%20N'Change%25'

# AND
filter=TaskName%20%3D%20N'MyTask'%20AND%20RequestingUser%20%3D%20N'jdoe'

# Date comparison
filter=CompletionDate%20%3E%20'2026-02-01'
```

**URL encoding reference:**

| Char | Encoded | Purpose |
|---|---|---|
| space | `%20` | Separates tokens |
| `=` | `%3D` | Comparison operator |
| `%` | `%25` | SQL LIKE wildcard |
| `>` | `%3E` | Greater than |
| `<` | `%3C` | Less than |

### Response Shape

```json
{
  "Items": [ { "Prop1": "val1" } ],
  "Bookmark": "...",
  "MoreRowsExist": true,
  "Success": true,
  "Message": null
}
```

Every row includes `_ItemId` — save this for update/delete operations.

---

## Invoke (Execute a Method)

```bash
curl -s -X POST "$SYTELINE_BASE_URL/invoke/{IDO}?method={METHOD}" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["param1", "param2", null]'
```

**The body is a FLAT JSON array** — not wrapped in an object.

```
WRONG:  { "parameters": ["TaskName", ...] }
RIGHT:  ["TaskName", ...]
```

### Response

```json
{
  "Parameters": ["echoed1", "echoed2", "output_value"],
  "ReturnValue": "0",
  "Success": true,
  "Message": null
}
```

- `ReturnValue: "0"` = success
- `ReturnValue: "16"` = validation error (check Infobar parameter)
- `Success: false` + `Message` = API-level error

### The Infobar Pattern

Most Syteline SP methods have an `Infobar` parameter (usually last or second-to-last). It is IN/OUT — pass `null`, check the response position for result message or error text.

**Always include Infobar** in your parameter array even if unused — omitting it shifts all positional indices.

### CRITICAL: Invoke Destroys the Token

After ANY `/invoke` call (success or failure), the token is invalid. Fetch a new token before your next API call.

---

## UpdateCollection (Insert / Update / Delete)

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/{IDO}" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"Changes": [...]}'
```

### Action Codes

| Code | Operation |
|---|---|
| `1` | Insert |
| `2` | Update |
| `4` | Delete |

### The _ItemId to ItemId Mapping

LoadCollection returns `_ItemId` on every row. Use this value as `ItemId` in UpdateCollection (drop the underscore). It encodes table, timestamp, and row GUID for optimistic locking.

### CRITICAL: One Record Per Request

Even though `Changes` accepts an array, **only the first item processes successfully**. Subsequent items fail silently or with an error. Loop over records individually.

### Insert Template

```json
{
  "Changes": [{
    "Action": 1,
    "ItemId": "PBT=[TableName]",
    "Properties": [
      { "Name": "Col1", "Value": "val1", "Modified": true, "IsNull": false },
      { "Name": "Col2", "Value": "val2", "Modified": true, "IsNull": false }
    ],
    "UpdateLocking": 1
  }]
}
```

### Update Template

```json
{
  "Changes": [{
    "Action": 2,
    "ItemId": "PBT=[TableName] TableName.DT=[timestamp] TableName.ID=[guid]",
    "Properties": [
      { "Name": "Col1", "Value": "newval", "Modified": true, "IsNull": false }
    ],
    "UpdateLocking": 1
  }]
}
```

### Delete Template

```json
{
  "Changes": [{
    "Action": 4,
    "ItemId": "PBT=[TableName] TableName.DT=[timestamp] TableName.ID=[guid]"
  }]
}
```

**Always LoadCollection first** to verify scope and get `_ItemId` values before deleting.

### Common Mistakes

```
WRONG:  { "Items": [{ "Action": "Delete", "Properties": { "RowPointer": "..." } }] }
RIGHT:  { "Changes": [{ "Action": 4, "ItemId": "PBT=[...]..." }] }
```

---

## Background Task Submission

### BGTaskSubmit — 20 Positional Parameters

```bash
curl -s -X POST "$SYTELINE_BASE_URL/invoke/BGTaskDefinitions?method=BGTaskSubmit" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["TASK_NAME", "TASK_PARMS1_VALUE", null, null, null, null, null, "AUTOMATION_USER", 0, null, null, null, null, null, null, null, null, null, null, null]'
```

| Index | Name | Direction | Notes |
|---|---|---|---|
| 0 | TaskName | INPUT | Case-sensitive task name |
| 1 | TaskParms1 | INPUT | Task-specific parameter string |
| 2 | TaskParms2 | INPUT | Usually null |
| 3 | Infobar | OUTPUT | Error text on failure |
| 4 | TaskID | OUTPUT | Assigned task number — save for monitoring |
| 5-6 | Status/StringTable | INPUT | Usually null |
| 7 | RequestingUser | INPUT | The automation username |
| 8 | PrintPreview | INPUT | `0` (NOT `false` — BYTE type) |
| 9-10 | Output fields | OUTPUT | null |
| 11-19 | Scheduling | INPUT | null for immediate execution |

**All 20 positions must be present.** Short arrays cause parsing errors.

### TaskParms1 Discovery

Query BGTaskHistories to see how the task was run before:

```bash
curl -s "$SYTELINE_BASE_URL/load/BGTaskHistories?properties=TaskName,TaskParms1,TaskParms2,CompletionStatus,RequestingUser&filter=TaskName%20LIKE%20N'%25YOUR_TASK%25'&orderby=CompletionDate%20DESC&recordcap=5" \
  -H "Authorization: $TOKEN"
```

Three common TaskParms1 formats:
- **Comma-delimited:** `,,,,,,,,,,,,,,T,T,C,MESSAGE,,,,,,`
- **SETVARVALUES:** `SETVARVALUES(NewId=guid,PostThroughVar=date,Parm_Site=SITE)`
- **~LIT~ tokens:** `~LIT~(V000222721),E,,0,1,1,1,0,1,0,BG~TASKID~,1`

If no history exists, run the task once manually in the UI, then query again.

### Task Monitoring

```bash
# Step 1: Is it still running?
curl -s "$SYTELINE_BASE_URL/load/ActiveBGTasks?properties=TaskNumber,TaskName,TaskStatusCode&filter=TaskNumber%20%3D%20TASK_ID" \
  -H "Authorization: $TOKEN"
# Empty Items[] = completed (moved to history)

# Step 2: Check completion
curl -s "$SYTELINE_BASE_URL/load/BGTaskHistories?properties=TaskNumber,CompletionStatus,TaskErrorMsg,CompletionDate&filter=TaskNumber%20%3D%20TASK_ID" \
  -H "Authorization: $TOKEN"
# CompletionStatus "0" = success, "-1" = failed

# Step 3: Get error details (on failure)
curl -s "$SYTELINE_BASE_URL/load/ProcessErrorLogs?properties=*&filter=TaskNumber%20%3D%20TASK_ID" \
  -H "Authorization: $TOKEN"
```

---

## IDO Discovery Workflow

The system is self-describing — use meta-IDOs to discover everything.

### Step 1: Find the IDO

**By name guess:**
```bash
curl -s "$SYTELINE_BASE_URL/load/IdoCollections?properties=CollectionName,AccessAs&filter=CollectionName%20LIKE%20N'%25ItemPrice%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

**By stored procedure / method name:**
```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=CollectionName,MethodName,MethodType&filter=MethodName%20LIKE%20N'%25SpName%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

**By database table:**
```bash
curl -s "$SYTELINE_BASE_URL/load/IdoTables?properties=CollectionName,TableName,TableType&filter=TableName%20%3D%20N'tablename'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

`TableType`: `3` = Primary Base Table (you usually want this), `0` = Secondary.

`AccessAs`: `'BaseSyteLine'` = core IDO, empty = custom IDO.

### Step 2: Get Properties

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,DataType,PropertyClass,ColumnName,ColumnTableAlias,IsReadOnly&filter=CollectionName%20%3D%20N'YourIDO'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

**Field name gotchas:**
- Use `PropertyName` (NOT `PropName`)
- Use `ColumnName` (NOT `BoundColumn`)
- `IsReadOnly`: `null` or `"0"` = writable, `"1"` = read-only
- Derived properties have `ColumnName = null`

**PropertyClass values:**
- Empty/`0` = Bound to database column
- `1` = Derived (computed SQL expression)
- `3` = Subcollection (child IDO link)

### Step 3: Get Methods

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=MethodName,MethodType&filter=CollectionName%20%3D%20N'YourIDO'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

**MethodType values:**
- `2` = Stored Procedure
- `3` = .NET Extension Class (converted from SP — database code-out)
- `0` = System/internal

> The `StoredProcedure` property does NOT exist on `IdoMethods`. Do not include it in queries.

### Step 4: Get Method Parameters

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethodParameters?properties=Sequence,ParameterName,DataType,SpDataType,SpDataLength,InputFlag,OutputFlag&filter=CollectionName%20%3D%20N'YourIDO'%20AND%20MethodName%20%3D%20N'YourMethod'&orderby=Sequence&recordcap=0" \
  -H "Authorization: $TOKEN"
```

- `Sequence` is 1-based; JSON array index = `Sequence - 1`
- `InputFlag=1` + `OutputFlag=1` = IN/OUT parameter
- Pass `null` for output-only parameters

> The `Direction` property does NOT exist on `IdoMethodParameters`. Use `InputFlag`/`OutputFlag`.

### Step 5: Get Primary Keys

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=derShadowColumnName,dataType,isPrimaryKey,primaryKeyPosition&filter=tableName%20%3D%20N'tablename_mst'%20AND%20isPrimaryKey%20%3D%201&recordcap=0" \
  -H "Authorization: $TOKEN"
```

> SQL table names in `SqlColumns` use the `_mst` suffix (e.g., `itemprice_mst` not `itemprice`). The IDO layer strips this suffix.

---

## Data Type Gotchas

### BYTE fields reject booleans

```
WRONG:  [..., false, ...]    -> "failed to parse normalized string [false], Type = Byte"
RIGHT:  [..., 0, ...]
```

Affects `PrintPreview`, `SchedFreqType`, and other BYTE-typed parameters.

### IDO name casing matters

IDO names can be case-sensitive. Always verify via `IdoCollections` first:
- `SLJobmatls` (correct — lowercase 'm')
- `SLJobMatls` (wrong)

### Extended IDO redirects

Some custom IDOs (e.g., `CMP_SLItemPrices`) extend and replace core IDOs (e.g., `SLItemprices`). If you get a permission error referencing a `CMP_` IDO you did not request, the system redirected. Request permissions on the IDO named in the error.

### curl Import Rules (n8n / Postman)

These tools only accept standard bash curl format:
- Use `curl`, not `curl.exe`
- No backtick or backslash line continuations — one line
- Substitute literal token values (env vars will not resolve on import)

---

## Database Code-Out Migration

Infor is converting stored procedures to .NET extension class methods. When a converted SP is called directly via SQL:

> "You may not call this 'SpName' as it has been converted to custom assembly application method."

**Fix:** Call it through the IDO REST API instead. The method name and parameter signature remain stable — only the implementation changes.

**Detection:** Query `IdoMethods` — if `MethodType = 3`, it has been converted to an extension class.

**Migration pattern:**
1. Find the IDO: `IdoMethods` filtered by `MethodName LIKE N'%SpName%'`
2. Get parameters: `IdoMethodParameters` for that method
3. Call via `/invoke/{IDO}?method={Method}` instead of `EXEC SpName`
