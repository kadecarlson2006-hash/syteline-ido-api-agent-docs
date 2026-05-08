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
| `'` | `%27` | String delimiter (usually OK unencoded) |

### Filters: What's Allowed and Not Allowed

Filters support: property names, comparison operators (`=`, `<>`, `>`, `<`, `>=`, `<=`, `LIKE`), Boolean operators (`AND`, `OR`), `IN`, `NOT`, `IS NULL`, `IS NOT NULL`, and string literals.

**Not allowed** in filters: subqueries, function calls, or anything that could be used for SQL injection. Filters are validated server-side.

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

If `ItemId` is omitted, the IDO falls back to key-property matching with no optimistic locking — all key properties must then be included in `Properties`.

### Insert

Properties are an array of `{Name, Value, Modified, IsNull}` objects.

```json
{
  "Changes": [
    {
      "Action": 1,
      "ItemId": "PBT=[UserNames]",
      "Properties": [
        { "Name": "Username",  "Value": "jdelacruz",     "Modified": true, "IsNull": false },
        { "Name": "UserDesc",  "Value": "Juan Dela Cruz", "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }
  ]
}
```

### Update

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

**Send one record per request.** Although the schema accepts multiple `Changes` entries, in practice only the first item processes successfully — subsequent items in the same request fail silently or with an error. Loop over records individually (n8n does this by default; in scripts use one request per row).

### Response

```json
{
  "Message": null,
  "Success": true,
  "RefreshItems": null
}
```

- `Success: true` + `RefreshItems: null` — delete succeeded
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

---

## Additional V2 API Capabilities

Beyond the three core operations, the REST API V2 also supports:

| Operation | HTTP Method | Endpoint | Purpose |
|---|---|---|---|
| Fire AES Event | POST | `/ido/aesevent/{IDO}` | Trigger Application Event System events |
| Get Property Info | GET | `/ido/propertyinfo/{IDO}` | Retrieve property metadata (types, read-only, etc.) |
| Download File | GET | `/ido/filestream/{IDO}` | Download a file stream from an IDO |
| Upload File | POST | `/ido/filestream/{IDO}` | Upload a file stream to an IDO |
| Download Doc Object | GET | `/ido/docobj/{IDO}` | Download a document object attachment |
| Upload Doc Object | POST | `/ido/docobj/{IDO}` | Upload a document object attachment |
| Get Configurations | GET | `/ido/configurations` | List available site configurations |

These are less commonly needed for typical integration work but are available when required. See the Infor Mongoose IDO Integration with External Applications Guide for full details on request/response formats.
