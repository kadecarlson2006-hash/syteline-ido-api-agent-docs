# SKILL: Insert a Record into Any SyteLine IDO

Use this skill to create new records in SyteLine via the IDO REST API. Covers the full discovery-first pattern: find required properties, validate the payload shape, then insert.

---

## When to Use This Skill

- Creating new records (orders, order lines, customers, items, etc.)
- Automating data entry that would otherwise be done through a SyteLine form
- Seeding or migrating data into SyteLine

---

## Step 0: Confirm You're Using the Right IDO

Before building the insert, confirm the IDO:

```bash
# Search by name if you know what you're looking for
curl -s "$SYTELINE_BASE_URL/load/IdoCollections?properties=CollectionName,AccessAs&filter=CollectionName%20LIKE%20N'%25CoItem%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

If your organization uses custom IDOs (prefixed with company initials like `VC_`, `CMP_`, etc.), prefer the extended IDO if it exists — it inherits all base properties and adds custom `Uf_` fields. Example: use `VC_SLCoitems` instead of `SLCoitems` if you need access to custom fields.

---

## Step 1: Discover Required Properties

Query `IdoProperties` to see which fields exist, their types, and which are likely required:

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,DataType,PropertyClass,ColumnName&filter=CollectionName%20%3D%20N'SLCos'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

`PropertyClass` values:
- Empty / `0` — bound to a database column (these are the ones you set)
- `1` — derived/computed (read-only; do not include in inserts)
- `3` — subcollection link (not a settable property)

To find which columns are non-nullable (and therefore required for insert):

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=derShadowColumnName,dataType,isNullable,isPrimaryKey&filter=tableName%20%3D%20N'co_mst'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

> **Tip:** Load an existing record with all properties (`recordcap=1`, no `properties` parameter) to see a complete example of real field values. This is often faster than reading the schema.

---

## Step 2: Build the Insert Payload

The insert body follows this shape:

```json
{
  "IDOName": "SLCos",
  "RefreshAfterSave": false,
  "Changes": [
    {
      "Action": 1,
      "ItemId": "",
      "Properties": [
        { "Name": "CoNum",     "Value": "V000123",              "Modified": true, "IsNull": false },
        { "Name": "CustNum",   "Value": "    888",              "Modified": true, "IsNull": false },
        { "Name": "OrderDate", "Value": "20260401 00:00:00.000","Modified": true, "IsNull": false },
        { "Name": "Stat",      "Value": "Q",                   "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }
  ]
}
```

**Key rules:**

| Rule | Detail |
|---|---|
| `Action: 1` | Always for insert |
| `ItemId: ""` | Empty string — do not omit this field |
| All values are strings | `"Value": "42"` not `"Value": 42` |
| Date format | `"YYYYMMDD 00:00:00.000"` — not ISO 8601 |
| `Modified: true` | Required on every property you want to save |
| `IsNull: true` | Use this (with `Value: null`) to explicitly null a field |
| `RefreshAfterSave` | Set `true` if the IDO should run post-save logic (pricing, defaults) |

**Fixed-length character fields** (like `CustNum` stored as `CHAR(10)`) need exact padding — leading spaces to fill the field width. Check `SqlColumns.dataType` if a key lookup fails unexpectedly.

**Date conversion from ISO:** `isoDate.replace(/-/g, "").split("T")[0] + " 00:00:00.000"`

---

## Step 3: Execute the Insert

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/SLCos" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "IDOName": "SLCos",
    "RefreshAfterSave": false,
    "Changes": [{
      "Action": 1,
      "ItemId": "",
      "Properties": [
        { "Name": "CoNum", "Value": "V000123", "Modified": true, "IsNull": false },
        { "Name": "CustNum", "Value": "    888", "Modified": true, "IsNull": false },
        { "Name": "Stat", "Value": "Q", "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }]
  }'
```

---

## Step 4: Check the Response

```json
{ "Success": true, "Message": null, "RefreshItems": null }
```

| Result | What it means |
|---|---|
| `Success: true` | Record created |
| `Success: false` + `Message` | Insert failed — read `Message` for the field/constraint that failed |
| `Success: false` + permission error | Grant the automation user `Write` privilege on this IDO |

---

## Common Patterns

### n8n Code node — insert multiple records as separate items

Set the Code node to **"Run Once for All Items"** and return one item per record so the downstream HTTP Request node processes them individually:

```javascript
const lines = $input.all().map(item => item.json);
return lines.map(line => ({
  json: {
    IDOName: "VC_SLCoitems",
    RefreshAfterSave: true,
    Changes: [{
      Action: 1,
      ItemId: "",
      Properties: [
        { Name: "CoNum",      Value: line.co_num,              Modified: true, IsNull: false },
        { Name: "CoLine",     Value: String(line.line_number), Modified: true, IsNull: false },
        { Name: "Item",       Value: line.item,                Modified: true, IsNull: false },
        { Name: "QtyOrdered", Value: String(line.qty),         Modified: true, IsNull: false },
        { Name: "DueDate",    Value: line.due_date.replace(/-/g,"").split("T")[0] + " 00:00:00.000", Modified: true, IsNull: false }
      ],
      UpdateLocking: 1
    }]
  }
}));
```

### Fields that cannot be set on insert

Some IDO properties are controlled by post-save triggers and are silently ignored (or cause errors) when included in an insert. Common examples: sequence numbers computed from line numbers, fields set by approval workflows.

**Pattern:** Insert without the problematic field → load the new record to get `_ItemId` → send a follow-up Action=2 update with only that field.

---

## Gotchas

- **BYTE fields:** Use `"0"` or `"1"`, not `false`/`true`
- **One record per request:** Do not put multiple `Changes` entries in one call — only the first is processed
- **Token not reusable:** If your workflow also calls `/invoke`, fetch a fresh token before every request
- **Custom fields need the custom IDO:** `Uf_` prefix fields only exist on the extended IDO (e.g., `VC_SLCoitems`), not the base one (`SLCoitems`)
- **Dev vs. prod differences:** Some methods depend on CLR assemblies that may only be trusted in production. Test with minimal field sets first; add optional computed fields once the base insert works
