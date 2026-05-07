# SKILL: Multi-Step Workflow Combinations

Use this skill when a task requires chaining multiple IDO operations together. Single API calls rarely accomplish anything meaningful in SyteLine — real workflows combine inserts, loads, updates, and invocations in sequence.

---

## Why Chaining Is Necessary

SyteLine's IDO layer enforces data integrity and triggers business logic through the same mechanisms as its forms. This means:

- An inserted header record must exist before you can insert child line records
- Some fields are computed by the server and only available after loading the saved record
- Certain methods (CTP, pricing, confirmation) must be invoked separately after the record exists
- Updates often require the `_ItemId` token, which is only available after a load

---

## Pattern 1: Insert Header → Insert Lines

The most common multi-step pattern in SyteLine. The header (e.g., CO, PO, Work Order) must be committed before lines can reference it.

**Step 1: Insert the header**

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/SLCos" \
  -H "Authorization: $TOKEN1" \
  -H "Content-Type: application/json" \
  -d '{
    "IDOName": "SLCos",
    "RefreshAfterSave": true,
    "Changes": [{
      "Action": 1, "ItemId": "",
      "Properties": [
        { "Name": "CoNum",     "Value": "V000123",              "Modified": true, "IsNull": false },
        { "Name": "CustNum",   "Value": "    888",              "Modified": true, "IsNull": false },
        { "Name": "OrderDate", "Value": "20260401 00:00:00.000","Modified": true, "IsNull": false },
        { "Name": "Stat",      "Value": "Q",                   "Modified": true, "IsNull": false },
        { "Name": "Type",      "Value": "E",                   "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }]
  }'
```

**Step 2: Insert each line** (one request per line — see [syteline-batch-operations.md](syteline-batch-operations.md))

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/VC_SLCoitems" \
  -H "Authorization: $TOKEN2" \
  -H "Content-Type: application/json" \
  -d '{
    "IDOName": "VC_SLCoitems",
    "RefreshAfterSave": true,
    "Changes": [{
      "Action": 1, "ItemId": "",
      "Properties": [
        { "Name": "CoNum",        "Value": "V000123",              "Modified": true, "IsNull": false },
        { "Name": "CoLine",       "Value": "1",                   "Modified": true, "IsNull": false },
        { "Name": "Item",         "Value": "WIDGET-A",            "Modified": true, "IsNull": false },
        { "Name": "QtyOrdered",   "Value": "10",                  "Modified": true, "IsNull": false },
        { "Name": "QtyOrderedConv","Value": "10",                 "Modified": true, "IsNull": false },
        { "Name": "Price",        "Value": "99.99",               "Modified": true, "IsNull": false },
        { "Name": "PriceConv",    "Value": "99.99",               "Modified": true, "IsNull": false },
        { "Name": "DueDate",      "Value": "20260601 00:00:00.000","Modified": true, "IsNull": false },
        { "Name": "Stat",         "Value": "O",                   "Modified": true, "IsNull": false },
        { "Name": "Uf_VcStatus",  "Value": "SCHEDULING",          "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }]
  }'
```

**n8n wiring:** Header insert → Lines Code node (map input rows to payloads) → HTTP Request (one per line)

---

## Pattern 2: Insert → Load → Update (Post-Insert Field Set)

Some fields cannot be written during the initial insert — they require the record to exist first. The pattern: insert → load the new record → update the specific field.

**Use case:** Setting `Uf_CoLineSeqNo` on CO lines (must be done as a separate update after insert).

**Step 1: Insert lines** (as above)

**Step 2: Load the inserted lines to get `_ItemId`**

```bash
curl -s "$SYTELINE_BASE_URL/load/SLCoitems?properties=CoNum,CoLine,_ItemId&filter=CoNum%20%3D%20N'V000123'&recordcap=0" \
  -H "Authorization: $TOKEN3"
```

**Step 3: Update the field that couldn't be set on insert**

```javascript
// n8n Code node
const items = $input.first().json.Items;
const changes = items.map(item => ({
  Action: 2,
  ItemId: item._ItemId,
  Properties: [
    { Name: "Uf_CoLineSeqNo", Value: String(Number(item.CoLine) * 10), Modified: true, IsNull: false }
  ]
}));
return [{ json: { IDOName: "VC_SLCoitems", RefreshAfterSave: false, Changes: changes } }];
```

---

## Pattern 3: Insert + Invoke Confirmation

After inserting or updating records, some workflows require calling an IDO method to confirm, price, or trigger downstream processes (e.g., CTP scheduling, order confirmation, job release).

**Important:** Tokens are consumed by `/invoke`. Fetch a fresh token before every step.

**Step 1: Insert the record** (using $TOKEN1)

**Step 2: Invoke the confirmation method** (using fresh $TOKEN2)

```bash
# Example: CTP (Capable to Promise) scheduling per CO line
curl -s -X POST "$SYTELINE_BASE_URL/invoke/SLCtps?method=ApsCtpUpdateDueDateSp" \
  -H "Authorization: $TOKEN2" \
  -H "Content-Type: application/json" \
  -d '["V000123", "1", "0", null]'
  # Parameters: CoNum, CoLine, CoRelease, Infobar (output — pass null)
```

Check `ReturnValue: "0"` for success. The `Infobar` output parameter (last position) will contain the result message or error description.

**n8n wiring for per-line invoke:**

```javascript
// Code node: one invoke payload per line
const items = $input.first().json.Items;
return items.map(item => ({
  json: {
    coNum: item.CoNum,
    coLine: item.CoLine,
    // Positional array for the invoke body
    params: [item.CoNum, item.CoLine, "0", null]
  }
}));
```

HTTP Request node: POST to `/invoke/SLCtps?method=ApsCtpUpdateDueDateSp`, body = `{{ $json.params }}`

---

## Pattern 4: SQL → IDO Filter → Bulk Update

When you need to update IDO records based on conditions that span multiple tables (header + line fields), query the condition in SQL, extract key values, then drive the IDO load and update.

**Full workflow:**

```
SQL query (get co_nums meeting condition)
  → Code: build CoNum IN (...) filter
    → IDO GET (load matching lines with _ItemId)
      → IF (Items.length > 0)
        → Code: build update payload
          → IDO POST update (one per line)
            → Code: build summary email
              → Send email
```

**Code node: build filter from SQL results**

```javascript
const coNums = $input.all().map(i => `'${i.json.co_num}'`).join(',');
const filter = `CoNum IN (${coNums}) AND Stat = 'O' AND (Uf_VcStatus <> 'APPROVALS NEEDED' OR Uf_VcStatus IS NULL)`;
return [{ json: { filter } }];
```

**Why SQL instead of IDO filter?** The IDO filter cannot express joins. If the condition you need (e.g., "CO header has approval flag set") lives on a different table than the records you're updating (CO lines), SQL is the right tool for the condition, and IDO is the right tool for the update.

---

## Pattern 5: Load → Conditional Branch → Different Updates

When different records in the same load result need different updates (e.g., some get status A, others get status B based on a date field), branch in a Code node before building payloads:

```javascript
const items = $input.first().json.Items;

const needsApprovals = items.filter(i => i.DueDate === "20991231 00:00:00.000");
const needsEngineering = items.filter(i => i.DueDate === "20990101 00:00:00.000");

const approvalsChanges = needsApprovals.map(item => ({
  Action: 2, ItemId: item._ItemId,
  Properties: [{ Name: "Uf_VcStatus", Value: "APPROVALS NEEDED", Modified: true, IsNull: false }]
}));

const engineeringChanges = needsEngineering.map(item => ({
  Action: 2, ItemId: item._ItemId,
  Properties: [{ Name: "Uf_VcStatus", Value: "ENGINEERING", Modified: true, IsNull: false }]
}));

// Return both sets — route to different HTTP Request nodes or combine
return [
  { json: { label: "approvals", IDOName: "VC_SLCoitems", Changes: approvalsChanges } },
  { json: { label: "engineering", IDOName: "VC_SLCoitems", Changes: engineeringChanges } }
];
```

---

## Token Management in Multi-Step Workflows

In n8n, fetch the token once in a dedicated node and reference it by node name in downstream steps:

```javascript
// In any downstream node referencing the token:
const token = $('Get Prod Token').first().json.Token;
```

**Do not** reference `$json.Token` — if any node is inserted between the token node and the consumer, `$json` will refer to the wrong node's output.

**Exception:** If your workflow calls `/invoke` at any point, that token is consumed and gone. Any step after the invoke must fetch a fresh token, even if the previous token was just fetched.

---

## Checklist for Multi-Step Workflows

Before building a multi-step chain, answer:

- [ ] Does the header record need to exist before lines can be inserted? → Insert header first
- [ ] Are there fields that can't be set on insert? → Plan a load + follow-up update step
- [ ] Does the workflow call `/invoke`? → Plan for a fresh token after every invoke
- [ ] Are records identified by a cross-table condition? → Use SQL for the condition, IDO for the mutation
- [ ] Could the load return 0 records? → Add an IF guard before the update step
- [ ] Do different records need different values? → Branch in a Code node before building payloads
