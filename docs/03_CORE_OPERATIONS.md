# Core Operations

The IDO REST API exposes three primary operations. All require a valid token in the `Authorization` header (see [01_AUTHENTICATION.md](01_AUTHENTICATION.md)).

**Base URL:** `$SYTELINE_BASE_URL`

---

## 1. Load Collection (Query Data)

Retrieve rows from an IDO. This is a **GET** request.

```
GET /load/{IDO}?properties={columns}&filter={where}&orderby={sort}&recordcap={limit}
Authorization: {token}
```

| Parameter | Required | Description |
|---|---|---|
| `properties` | No | Comma-delimited property names. Omit to get all properties. |
| `filter` | No | SQL WHERE clause (URL-encoded). See filter syntax below. |
| `orderby` | No | Comma-delimited sort. Append `DESC` per column as needed. |
| `recordcap` | No | `-1` = 200 rows (default), `0` = all rows, `N` = N rows |

> **Tip:** During discovery, omit `properties` to see all available columns, and set `recordcap=10` or smaller to avoid overwhelming the response.

### Response

```json
{
  "Items": [
    { "Property1": "value1", "Property2": "value2" },
    { "Property1": "value3", "Property2": "value4" }
  ],
  "Bookmark": "...",
  "MoreRowsExist": true,
  "Success": true,
  "Message": null
}
```

- `Items` — array of objects, one per row
- `MoreRowsExist` — `true` if the record cap was hit and more data exists
- `Bookmark` — pagination cursor (pass back for next page)

### Example: Query items from a table

```bash
curl -s -X GET \
  "$SYTELINE_BASE_URL/load/SLItems?properties=Item,Description,UnitCost&filter=Item%20LIKE%20N'ABC%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

### Filter Syntax

Filters use SQL WHERE syntax, URL-encoded. String literals must be wrapped in `N'...'` (Unicode prefix).

```
# Exact match
filter=TaskName%20%3D%20N'ChangeCOStatusUtility'

# Starts with
filter=TaskName%20LIKE%20N'Change%25'

# Contains
filter=TaskName%20LIKE%20N'%25Journal%25'

# AND
filter=TaskName%20%3D%20N'OrderVerificationReport'%20AND%20RequestingUser%20%3D%20N'npetrie'

# IN list (useful for driving from SQL query results)
filter=CoNum%20IN%20(N'V000123',N'V000124',N'V000125')

# Date comparison
filter=CompletionDate%20%3E%20'2026-02-01'

# IS NULL / IS NOT NULL
filter=Uf_VcStatus%20IS%20NULL
filter=Uf_VcStatus%20IS%20NOT%20NULL

# Combining IS NULL with OR (common when a field may have never been set)
filter=Uf_VcStatus%20%3C%3E%20N'APPROVED'%20OR%20Uf_VcStatus%20IS%20NULL
```

**URL encoding reference:**

| Char | Encoded | Purpose |
|---|---|---|
| space | `%20` | Separates tokens |
| `=` | `%3D` | Comparison operator |
| `<>` | `%3C%3E` | Not equal |
| `%` | `%25` | SQL LIKE wildcard |
| `>` | `%3E` | Greater than |
| `<` | `%3C` | Less than |
| `'` | `%27` | String delimiter (usually OK unencoded) |

### Filters: What's Allowed and Not Allowed

Filters support: property names, comparison operators (`=`, `<>`, `>`, `<`, `>=`, `<=`, `LIKE`), Boolean operators (`AND`, `OR`), `IN`, `NOT`, `IS NULL`, `IS NOT NULL`, and string literals.

**Not allowed** in filters: subqueries, function calls, or anything that could be used for SQL injection. Filters are validated server-side.

### Always include `_ItemId` when you plan to update

If you're loading records to update or delete them afterward, always include `_ItemId` in your `properties` list. It maps directly to `ItemId` in the update request (drop the underscore). You cannot get it after the fact without loading again.

```bash
curl -s "$SYTELINE_BASE_URL/load/SLCoitems?properties=CoNum,CoLine,Item,_ItemId&filter=CoNum%20%3D%20N'V000123'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

---

## 2. Invoke (Execute a Method)

Execute a method defined on an IDO. This is a **POST** request.

```
POST /invoke/{IDO}?method={METHOD}
Authorization: {token}
Content-Type: application/json
Body: [ param1, param2, param3, ... ]
```

The body is a **flat JSON array** of positional parameters. Not wrapped in an object — just the raw array.

### Response

```json
{
  "Message": null,
  "Success": true,
  "Parameters": ["echoed_param1", "echoed_param2", "output_value", ...],
  "ReturnValue": "0"
}
```

- `ReturnValue: "0"` — success
- `ReturnValue: "16"` — validation error (check output parameters for error message)
- `Success: false` + `Message` — API-level error (permissions, parsing, etc.)

### OUTPUT Parameters

Methods can have input, output, or input/output parameters. Output values are returned in the same positional index in `Parameters[]`. Pass `null` for output-only parameters in the request.

### The Infobar pattern

Most Syteline stored procedure methods have an `Infobar` parameter (type `VARCHAR`, `InputFlag=1`, `OutputFlag=1`) — typically the last or second-to-last parameter. It returns a human-readable summary of what the method did, e.g. `"723 Item(s) were processed."`

- Pass `null` for it in the request body.
- On success check `Parameters[n]` at the `Infobar` sequence index (0-based: `Sequence - 1`) for the result message.
- On validation failure (`ReturnValue: "16"`), `Infobar` will contain the error description.

Always include `Infobar` in your parameter array even if you don't intend to use it — omitting it will shift all subsequent positional indices and cause a parsing error.

### Token is consumed by Invoke

Every `/invoke` call destroys the session token. Fetch a fresh token before each call — even if you just fetched one for a `/load`. Do not reuse tokens across any sequence of calls.

### Example: Call a method

```bash
curl -s -X POST \
  "$SYTELINE_BASE_URL/invoke/UserNames?method=GetUserAttributes" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["jdoe",null,null,null,null]'
```

Output parameters come back in their positional indices:
```json
{
  "Parameters": ["jdoe", "2", "1", "", ""],
  "ReturnValue": "0"
}
```

---

## 3. Update Collection (Insert / Update / Delete)

Modify data in an IDO. This is a **POST** request.

```
POST /update/{IDO}
Authorization: {token}
Content-Type: application/json
```

Optional query parameter: `?refresh=true` — instructs the server to return refreshed property values in `RefreshItems` after the update.

### Action Codes

| Code | Operation |
|------|-----------|
| `1`  | Insert    |
| `2`  | Update    |
| `4`  | Delete    |

### The `ItemId` Token

For Update and Delete, the `ItemId` field is the row identity token returned as `_ItemId` in every LoadCollection response. It encodes the table, timestamp, and row GUID — the IDO runtime uses it for optimistic locking and row location. **Always pass it for updates and deletes.**

For Insert, pass an empty string: `"ItemId": ""`.

If `ItemId` is omitted on an update, the IDO falls back to key-property matching with no optimistic locking — all key properties must then be included in `Properties`.

### Property Object Shape

Every entry in the `Properties` array follows this structure:

```json
{ "Name": "PropertyName", "Value": "thevalue", "Modified": true, "IsNull": false }
```

- `Modified: true` — required; tells the runtime this field was intentionally set
- `IsNull: true` — use this (with `Value: null`) to explicitly set a field to NULL
- **All values are strings**, even numbers and dates — `"Value": "42"` not `"Value": 42`

### Date Format

Dates must use SyteLine's internal format, **not** ISO 8601:

```
CORRECT:  "20260331 00:00:00.000"
WRONG:    "2026-03-31"
WRONG:    "2026-03-31T00:00:00Z"
```

To convert from an ISO date in JavaScript: `isoDate.replace(/-/g, "").split("T")[0] + " 00:00:00.000"`

### Insert

Properties are an array of `{Name, Value, Modified, IsNull}` objects.

```json
{
  "Changes": [
    {
      "Action": 1,
      "ItemId": "",
      "Properties": [
        { "Name": "Username",  "Value": "jdelacruz",     "Modified": true, "IsNull": false },
        { "Name": "UserDesc",  "Value": "Juan Dela Cruz", "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }
  ]
}
```

#### Real-World Insert Patterns

**Use custom/extended IDOs for custom fields.** If your environment has extended IDOs (e.g., `VC_SLCoitems` extending `SLCoitems`), you must use the extended IDO to read or write custom `Uf_` fields. The base IDO (`SLCoitems`) does not expose them.

```json
{ "Changes": [{ "Action": 1, "ItemId": "", "Properties": [
  { "Name": "CoNum",       "Value": "V000123",              "Modified": true, "IsNull": false },
  { "Name": "CoLine",      "Value": "1",                    "Modified": true, "IsNull": false },
  { "Name": "Uf_VcStatus", "Value": "SCHEDULING",           "Modified": true, "IsNull": false }
]}]}
```

**Some fields cannot be set on insert — use a follow-up update.** Certain IDO properties are computed by post-save triggers and will be ignored or error if sent during the initial insert. Discover which ones by inserting without them, then loading the saved record and updating only that field.

Example: `Uf_CoLineSeqNo` on `VC_SLCoitems` cannot be set during insert. Pattern:
1. Insert the line (without `Uf_CoLineSeqNo`)
2. Load the saved line to get its `_ItemId`
3. Send a separate Action=2 update with only `Uf_CoLineSeqNo`

**Fixed-length character fields need padding.** Some key fields in SyteLine are stored as fixed-length `CHAR` types and require exact padding to match. Customer numbers, for example, are often 10 characters and must be padded with leading spaces:

```json
{ "Name": "CustNum", "Value": "    888", "Modified": true, "IsNull": false }
```

If inserts silently fail to link related records (e.g., wrong customer), check whether the field type is `CHAR(N)` and pad accordingly.

**`RefreshAfterSave: true` triggers post-save IDO logic.** When you need the IDO to run pricing, defaults, or validation after saving, include this at the top level of the request body. The response will include a `RefreshItems` array with the recalculated property values. For inserts where you're supplying all values yourself, `false` is fine and avoids the overhead.

```json
{
  "IDOName": "SLCos",
  "RefreshAfterSave": true,
  "Changes": [{ ... }]
}
```

**Environment differences (dev vs. prod).** Some IDO methods depend on CLR assemblies or licensed modules that may not be available in all environments. If an insert succeeds in prod but fails in dev with an assembly error, you may need environment-specific workarounds (e.g., setting `Reprice: "0"` to skip the repricing assembly call, or hardcoding a `TaxCode` that bypasses tax calculation).

### Update

The load-then-modify pattern is the safest and most reliable approach:

1. Load the record with `_ItemId` in `properties`
2. Use the `_ItemId` value as `ItemId` in the update body (drop the underscore prefix)
3. Only include fields you want to change with `Modified: true` — unchanged fields can be omitted

```json
{
  "Changes": [
    {
      "Action": 2,
      "ItemId": "PBT=[UserNames] UserNames.DT=[2018-10-02 15:39:02.060] UserNames.ID=[4c9a96d0-ba3c-4de4-8657-6d262f9dcd3f]",
      "Properties": [
        { "Name": "UserDesc", "Value": "John Doe Sr.", "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }
  ]
}
```

#### Real-World Update Patterns

**Update-after-load in n8n.** A common two-node pattern: first node GETs the IDO with `_ItemId`, second node POSTs the update. Build the `Changes` array in a Code node by mapping over `$input.first().json.Items`:

```javascript
const items = $input.first().json.Items;
const changes = items.map(item => ({
  Action: 2,
  ItemId: item._ItemId,
  Properties: [
    { Name: "Uf_VcStatus", Value: "APPROVED", Modified: true, IsNull: false }
  ]
}));
return [{ json: { IDOName: "VC_SLCoitems", RefreshAfterSave: false, Changes: changes } }];
```

**Bulk status updates driven by SQL.** When you need to update records based on a condition that spans multiple tables (e.g., a header field controlling a line-level update), query the condition in SQL, extract the key values, then drive an IDO `IN` filter:

```javascript
// Code node: build filter from SQL results
const coNums = $input.all().map(i => `'${i.json.co_num}'`).join(',');
return [{ json: { filter: `CoNum IN (${coNums}) AND Stat = 'O' AND (Uf_VcStatus <> 'APPROVED' OR Uf_VcStatus IS NULL)` } }];
```

Use the filter as `{{ $json.filter }}` in the IDO GET node's query parameter, then pipe the results into the update payload builder.

**Guard against empty result sets.** Before posting an update, check that the load returned rows. In n8n, add an IF node with condition `{{ $json.Items.length > 0 }}` before the update node to prevent posting an empty `Changes` array when no records matched.

**SyteLine UI shows stale data after API updates.** After a successful update (`Success: true`), the Syteline form still shows the old value until the record is closed and reopened. This is normal — the update did apply. Don't re-run the update assuming it failed.

### Delete

Delete only requires `Action` and `ItemId` — no `Properties` array needed.

```json
{
  "Changes": [
    {
      "Action": 4,
      "ItemId": "PBT=[UserNames] UserNames.DT=[2018-12-14 13:52:27.737] UserNames.ID=[265df7d2-802c-4582-a774-a81b675a91a1]"
    }
  ]
}
```

**Always LoadCollection first** with a small `recordcap` to verify the scope of your filter before deleting. Confirm the records look right, then use the `_ItemId` values from that response as `ItemId` in the delete `Changes` array.

### One Record Per Request

**Send one record per request.** Although the schema accepts multiple `Changes` entries, in practice only the first item processes successfully — subsequent items in the same request fail silently or with an error. Loop over records individually.

In n8n this happens automatically since the HTTP Request node processes one input item at a time. In scripts, loop explicitly:

```javascript
for (const change of changes) {
  const res = await fetch(`${BASE_URL}/update/${IDO}`, {
    method: "POST",
    headers: { Authorization: token, "Content-Type": "application/json" },
    body: JSON.stringify({ Changes: [change] })
  });
  const result = await res.json();
  if (!result.Success) console.error(`Failed on ItemId ${change.ItemId}:`, result.Message);
}
```

### Error Handling

The response `Success` field and `Message` are your primary signals:

| Scenario | `Success` | `Message` | What to do |
|---|---|---|---|
| Record saved | `true` | `null` | Done |
| Validation failed | `false` | Error text | Check `Message` — usually a missing required field or constraint violation |
| Permission denied | `false` | `"User requires [Write] privilege..."` | Grant the automation user write access to the IDO |
| Record locked | `false` | `"Record locked by user..."` | Another user/process has the record open; retry later |
| Wrong IDO | `false` | `"IDO not found"` | Verify IDO name spelling and case |

When `Success: false`, log the full response body — `Message` often includes the field name and the constraint that failed, which is enough to fix the payload without further discovery.

### Response

```json
{
  "Message": null,
  "Success": true,
  "RefreshItems": null
}
```

- `Success: true` + `RefreshItems: null` — operation succeeded (no refresh requested)
- `Success: true` + `RefreshItems: [...]` — operation succeeded; array contains post-save property values
- `Success: false` + `Message` — error; check `Message` for details

### Important Notes

- **Always LoadCollection first** with a small `recordcap` to verify the scope of your filter before deleting. Confirm the records look right, then use the `_ItemId` values from that response as `ItemId` in the delete `Changes` array.
- The `_ItemId` field from LoadCollection maps directly to `ItemId` in UpdateCollection — just drop the underscore.
- Include all non-nullable properties that don't have defaults when inserting.
- The IDO runtime handles SQL generation — you never write raw INSERT/UPDATE/DELETE statements.

---

## HTTP Method Summary

| Operation | HTTP Method | Endpoint Pattern |
|---|---|---|
| Query data | `GET` | `/load/{IDO}?properties=...&filter=...` |
| Execute method | `POST` | `/invoke/{IDO}?method={Method}` |
| Insert/Update/Delete | `POST` | `/update/{IDO}` |

**Common mistake:** Using POST for `/load` or GET for `/invoke` returns `405 Method Not Allowed`.
