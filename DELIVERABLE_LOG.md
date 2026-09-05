# Deliverable Log

---

## 2026-03-11 — Environment Validation, Doc Fixes, and IDO Introspection

**Request:** Validate that the docs are sufficient for an agent to connect and introspect IDOs. Discovered and fixed several gaps through live testing.

---

### How to Connect on Windows (No jq)

`jq` is **not installed** on this Windows/MSYS environment. The bash token example using `jq -r '.Token'` silently fails, returning an empty token and causing all subsequent calls to return `"Invalid token"`.

**Working token pattern for this environment:**

```bash
RESPONSE=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD")
TOKEN=$(pwsh -NoProfile -Command "('$RESPONSE' | ConvertFrom-Json).Token")
```

`pwsh` (PowerShell 7) is available at `/c/Program Files/PowerShell/7/pwsh`. Use this pattern for every token fetch.

**Environment variables are set as Windows system env vars** — no `.env` file needed. All of `SYTELINE_BASE_URL`, `DEFAULT_SITE`, `SYTELINE_AGENT_USERNAME`, `SYTELINE_AGENT_PASSWORD` are accessible directly from bash.

---

### Token Session Behavior

- Tokens **do not expire on a timer**
- Tokens are **destroyed after any `Invoke` call** (success or failure)
- `Load` and `Update` calls do **not** destroy the token
- **Safest pattern: fetch a fresh token immediately before every API call** — don't cache

---

### Confirmed IdoProperties Field Names

When querying `IdoProperties`, the correct property names are:

| Use this | Not this |
|---|---|
| `PropertyName` | `PropName` |
| `ColumnName` | `BoundColumn` |
| `CollectionName` | — |
| `DataType` | — |
| `IsReadOnly` | — |

Writable properties have `IsReadOnly = null` or `"0"`. Read-only = `"1"`. Derived properties (computed, no DB column) have `ColumnName = null`.

---

### StoredProcedure Property Does Not Exist on IdoMethods

Querying `IdoMethods` with `StoredProcedure` in the properties list returns:
> `"Property StoredProcedure not found on IdoMethods IDO."`

Use `MethodName,MethodType` only. Search by `MethodName LIKE N'%SpName%'` to find a method by SP name.

**MethodType values observed:**
- `2` — stored procedure (most common, includes all standard `...Sp` methods)
- `3` — .NET extension class method (e.g., `CLM_*`, converted SPs)
- `0` — system/internal methods (`DefineVariableSp`, `UndefineVariableSp`)

---

### SLItems IDO — Key Facts

**Primary key:** `Item` (`item` column)

**Most useful writable properties:** `Item`, `Description`, `Stat`, `UM`, `ProdType`, `MatlType`, `ProductCode`, `CostMethod`, `CostType`, `UnitCost`, `LeadTime`, `LotSize`, `OrderMin/Max/Mult`, `LotTracked`, `SerialTracked`, `Buyer`, `MfgWhse`, `SupplyWhse`, `PlanFlag`, `MrpPart`, `MpsFlag`

**Key subcollections (derived properties with no ColumnName):**

| Subcollection | Contents |
|---|---|
| `SLItemwhses` | Live inventory — qty on hand, allocated, WIP per warehouse |
| `SLSupDems` | Supply & demand detail (POs, jobs, CO lines) |
| `SLCoitems` | Customer order lines for this item |
| `SLPoItems` | Purchase order lines |
| `SLJobmatls` | Job BOM material requirements |
| `SLJobRoutes` | Routing operations on open jobs |
| `SLMatltrans` | Material transaction history |
| `SLPlanningDetails` | MRP/planning detail |
| `SLAttributes` | Item attribute values |
| `SLRelatedItems` | Alternate/related items |
| `SLExpiringLots` | Lot expiration tracking |

**Total:** 477 properties (263 writable, 61 read-only, 153 derived), 75 methods.

---

### SLJobRoutes IDO — Key Facts

**Primary key:** `Job` + `Suffix` + `OperNum` — each record is one operation step on a manufacturing job.

**Key writable properties:** `JshStartDate`, `JshEndDate`, `JshRunLbrHrs`, `JshRunMchHrs`, `JshSetupHrs`, `JshSchedHrs`, `JshSchedDrv`, `Complete`, `Efficiency`, `BflushType`

**Key subcollection:** `JrtResourceGroups` — resource groups assigned to the operation

**Notable methods:** `CompleteJobOperationSP`, `CalcJobrouteRunDurSp`, `JobOperationsSp`

---

### /ido-inspect Skill

A `/ido-inspect` slash command was added to this repo at `.claude/skills/ido-inspect/SKILL.md`.

Usage: `/ido-inspect SLItems`

It walks through IdoCollections → IdoProperties → IdoMethods and presents a categorized summary. If you then ask about a specific method's parameters, it queries `IdoMethodParameters`.

---

## 2026-03-11 — SLJobmatls IDO Introspection

**Request:** Inspect the `SLJobmatls` IDO — properties, methods, and structure.

**Note:** The IDO name is `SLJobmatls` (lowercase 'm') — not `SLJobMatls`. Always verify casing via IdoCollections first.

**Curl commands executed:**

```bash
# Step 1 — verify IDO exists and get casing
RESPONSE=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD")
TOKEN=$(pwsh -NoProfile -Command "('$RESPONSE' | ConvertFrom-Json).Token")

curl -s "$SYTELINE_BASE_URL/load/IdoCollections?properties=CollectionName,AccessAs&filter=CollectionName%3D'SLJobMatls'&recordcap=1" \
  -H "Authorization: $TOKEN"

# Step 2 — get all properties
RESPONSE=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD")
TOKEN=$(pwsh -NoProfile -Command "('$RESPONSE' | ConvertFrom-Json).Token")

curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,DataType,IsReadOnly,ColumnName&filter=CollectionName%3D'SLJobmatls'&recordcap=0" \
  -H "Authorization: $TOKEN"

# Step 3 — get all methods
RESPONSE=$(curl -s "$SYTELINE_BASE_URL/token/$DEFAULT_SITE" \
  -H "username: $SYTELINE_AGENT_USERNAME" \
  -H "password: $SYTELINE_AGENT_PASSWORD")
TOKEN=$(pwsh -NoProfile -Command "('$RESPONSE' | ConvertFrom-Json).Token")

curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=MethodName,MethodType&filter=CollectionName%3D'SLJobmatls'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

**Results:**

**IDO:** `SLJobmatls` | `AccessAs: BaseSyteLine` | 301 properties (83 writable, 79 read-only, 139 derived) | 69 methods

**Primary key:** `Job` + `Suffix` + `Sequence` — each record is one component/material line on a job.

**Key writable properties:**

| Property | Column | Notes |
|---|---|---|
| `Job` | `job` | |
| `Suffix` | `suffix` | |
| `Sequence` | `sequence` | |
| `Item` | `item` | Component item number |
| `OperNum` | `oper_num` | Which routing operation this material feeds |
| `MatlQty` | `matl_qty` | Required quantity |
| `QtyReleased` | `qty_released` | |
| `QtyIssued` | `qty_issued` | |
| `UM` | `u_m` | Unit of measure |
| `ScrapFact` | `scrap_fact` | |
| `Backflush` | `backflush` | |
| `BflushLoc` | `bflush_loc` | |
| `MatlType` | `matl_type` | |
| `OptCode` | `opt_code` | Optional/required flag |
| `AltGroup` / `AltGroupRank` | `alt_group` / `alt_group_rank` | Alternate material groups |
| `PlannedAlternate` | `planned_alternate` | |
| `PickDate` | `pick_date` | |
| `ACost`, `ALbrCost`, `AMatlCost`, `AFovhdCost`, `AVovhdCost`, `AOutCost` | `a_*` columns | Actual cost buckets |

**Key read-only properties:** `ItmDescription`, `MatlDescription`, `ItmLotTracked`, `ItmSerialTracked`, `ItmUM`, `ItmProductCode`, `ItmPhantomFlag`, `JobStat`, `JobPlant`, `JshEndDate`

**Subcollections:**

| Subcollection | Contents |
|---|---|
| `SLJobRoutes` | Back-link to the routing operation this material feeds |
| `SLPoItems` | PO lines sourcing this material |
| `SLPreassignedLots` | Pre-assigned lot numbers |
| `SLSerials` | Pre-assigned serials |
| `SLItemLocAlls` | Item location data |
| `SLJobRefs` | Job reference cross-links |
| `SLTrnitems` | Transfer/transaction items |
| `SLPreqitems` | Purchase requisition items |

**Notable methods:**
- `ProcessJobMatlTransSp` (type 2) — issue/return material transactions against a job
- `CurrentMaterialsUpdInsSp` / `CurrentMaterialsDeleteSp` (type 2) — insert/delete material lines
- `JobMaterialsPreDeleteSp` / `JobMaterialsPostDeleteSp` (type 2) — delete lifecycle hooks
- `GetJobMatlsSp` (type 3) — retrieve material list
- `DeleteBOMComponentsSp` (type 3) — bulk BOM component deletion
- `JoblowBG` / `JoblowSp` (type 2/3) — job cost rollup (BG = background task, Sp = inline)
- `EjoblowBG` / `EjoblowSp` (type 2/3) — estimated job cost rollup
- `BomChgBG` / `BomChgSp` (type 2/3) — BOM change

**Pattern noted:** BG/Sp method pairs appear consistently across job IDOs. `*BG` submits as a background task via `BGTaskDefinitions`; `*Sp` runs inline via `/invoke`.

---

## 2026-03-10 — Delete Zero-Price Records After Item Price Change

**Request:** After running `ItemPriceChangeSp`, duplicate item price records are created with `UnitPrice1 = 0`. Load the offending records and delete them.

**Discovery path:**

1. **Found property names** — Queried `IdoProperties` filtered to `SLItemprices` with column name hints. Confirmed: `EffectDate` (`effect_date`), `ItmProductCode` (`product_code`), `UnitPrice1` (`unit_price1`), `RowPointer` (standard Syteline PK).

2. **Loaded matching records** — Used `/load/SLItemprices` with a filter on all three fields. Requested `RowPointer` and `_ItemId` in the properties — `_ItemId` is required for the delete step. Verified with `recordcap=2` before running the full set.

3. **Deleted records** — Used `/update/SLItemprices` with the `Changes` format (Action `4`). Passed the `_ItemId` from the load response directly as `ItemId` in the delete body. **One record per request** — multiple `Changes` entries in a single request only processes the first.

**Deliverables:**

Load (verify first):
```bash
curl -s "https://YOUR-HOST/IDORequestService/ido/load/SLItemprices?properties=RowPointer,Item,ItmProductCode,EffectDate,UnitPrice1&filter=ItmProductCode%20%3D%20N'EMP'%20AND%20EffectDate%20%3D%20'2026-03-15'%20AND%20UnitPrice1%20%3D%200&recordcap=50" \
  -H "Authorization: YOUR_TOKEN"
```

Delete (one request per record, using `_ItemId` from load response as `ItemId`):
```bash
curl -s -X POST "https://YOUR-HOST/IDORequestService/ido/update/SLItemprices" \
  -H "Authorization: YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"Changes":[{"Action":4,"ItemId":"PBT=[itemprice] itp.DT=[...] itp.ID=[...]"}]}'
```

**Result:** `{"Message":null,"Success":true,"RefreshItems":null}` per record deleted successfully.

**Notes:**
- Always include `RowPointer` and `_ItemId` in the LoadCollection `properties` list — you need both to verify the record and to construct the delete.
- The `_ItemId` value from LoadCollection maps directly to `ItemId` in UpdateCollection (just drop the underscore prefix).
- One `Changes` entry per request — looping is required for bulk deletes. n8n handles this natively by iterating over input items.

A running log of tasks agents have completed in this repo. Each entry records:
- **Request** — what the user needed
- **Discovery path** — how the agent found the solution
- **Deliverable** — the final API call(s)

Agents should append a new entry at the top of this file after completing any non-trivial task.

---

## 2026-03-10 — Item Price Change via API

**Request:** Replace a SQL script that called `ItemPriceChangeSp` directly with an equivalent IDO REST API call. The SQL script looped over product codes and raised item prices by a given percent, creating new price records with a new effective date.

**Discovery path:**

1. **Identified the IDO** — The form is `ChangeItemPrice`; the backing IDO is `SLItemprices` (known from user context; confirmed by the fact that the SP name `ItemPriceChangeSp` matches the IDO method name once you strip the `Sp` suffix).

2. **Found the method** — Queried `IdoMethods` filtered to `CollectionName = 'SLItemprices'`. Returned 7 methods; `ItemPriceChangeSp` was immediately visible. Used `MethodName,MethodType` only — `StoredProcedure` property does not exist in this environment (converted to extension class).

   ```bash
   curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=CollectionName,MethodName,MethodType&filter=CollectionName%20%3D%20N'SLItemprices'&recordcap=0" \
     -H "Authorization: $TOKEN"
   ```

3. **Got method parameters** — Queried `IdoMethodParameters` filtered to the same IDO + method, ordered by `Sequence`. Used `InputFlag`/`OutputFlag` instead of `Direction` (which does not exist in this environment).

   ```bash
   curl -s "$SYTELINE_BASE_URL/load/IdoMethodParameters?properties=ParameterName,Sequence,DataType,InputFlag,OutputFlag,SpDataType&filter=CollectionName%20%3D%20N'SLItemprices'%20AND%20MethodName%20%3D%20N'ItemPriceChangeSp'&orderby=Sequence&recordcap=0" \
     -H "Authorization: $TOKEN"
   ```

   Returned 34 parameters. Key parameters mapped from the existing SQL script:
   - `UpdateCreate` (seq 21, BYTE) — `0` = create new records
   - `ItmPrc1` (seq 22, BYTE) — `1` = Item Price 1 only
   - `PriceType` (seq 28, VARCHAR) — `"I"` = Item
   - `AmtType` (seq 30, VARCHAR) — `"P"` = Percent
   - `PriAmt` (seq 31, NUMERIC) — the percent value
   - `NewEffectDate` (seq 20, TIMESTAMP) — new effective date
   - `Infobar` (seq 32, VARCHAR, IN/OUT) — pass `null`; returns result message

4. **Validated against existing SQL** — User provided their original SQL script. Confirmed `UpdateCreate=0` (not 1) for create, and `StartingEffectDateOffset`/`EndingEffectDateOffset` should be `null` (not `0`).

**Deliverable:**

```bash
curl -s -X POST "https://YOUR-HOST/IDORequestService/ido/invoke/SLItemprices?method=ItemPriceChangeSp" \
  -H "Authorization: YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '[null,"EMP","EMP",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"2026-03-11T00:00:00",0,1,0,0,0,0,0,"I",null,"P",3,null,null,null]'
```

**Result:** `Success: true`, `Infobar` (Parameters[31]) = `"723 Item(s) were processed."`

**Notes:**
- For n8n/Postman import, use standard bash curl on a single line with a literal token value — PowerShell syntax (`curl.exe`, backtick continuations) is not importable.
- In the full SQL script, the call was looped over 50 product codes. To replicate the loop via API, run this call once per product code, substituting `FromProductCode` and `ToProductCode` (positions 1 and 2, 0-indexed) each iteration.
