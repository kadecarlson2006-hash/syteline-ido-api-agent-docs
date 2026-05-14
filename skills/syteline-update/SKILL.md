# SKILL: Update Records in SyteLine via IDO API

Use this skill to modify existing records in SyteLine. The safe, reliable pattern is always: **Load → Capture `_ItemId` → Update**. Never guess an `ItemId` or try to construct one by hand.

---

## When to Use This Skill

- Changing field values on existing records
- Bulk status updates driven by business logic
- Correcting data after an insert
- Setting fields that cannot be written during the initial insert

---

## The Load → Update Pattern

### Step 1: Load the records you want to update

Always include `_ItemId` in `properties`. This is the row identity token you'll need for the update.

```bash
curl -s "$SYTELINE_BASE_URL/load/VC_SLCoitems?properties=CoNum,CoLine,Uf_VcStatus,_ItemId&filter=CoNum%20%3D%20N'V000123'%20AND%20Stat%20%3D%20N'O'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

Response:
```json
{
  "Items": [
    { "CoNum": "V000123", "CoLine": "1", "Uf_VcStatus": null, "_ItemId": "PBT=[coitem_mst] coitem_mst.DT=[2026-03-15 09:22:11.453] coitem_mst.ID=[a1b2c3...]" },
    { "CoNum": "V000123", "CoLine": "2", "Uf_VcStatus": null, "_ItemId": "PBT=[coitem_mst] coitem_mst.DT=[2026-03-15 09:22:12.001] coitem_mst.ID=[d4e5f6...]" }
  ],
  "MoreRowsExist": false,
  "Success": true
}
```

### Step 2: Build the update payload

Use `_ItemId` as the `ItemId` value (drop the underscore). Only include fields you want to change with `Modified: true` — all other fields can be omitted.

```json
{
  "Changes": [
    {
      "Action": 2,
      "ItemId": "PBT=[coitem_mst] coitem_mst.DT=[2026-03-15 09:22:11.453] coitem_mst.ID=[a1b2c3...]",
      "Properties": [
        { "Name": "Uf_VcStatus", "Value": "APPROVED", "Modified": true, "IsNull": false }
      ],
      "UpdateLocking": 1
    }
  ]
}
```

### Step 3: POST the update

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/VC_SLCoitems" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"Changes":[{"Action":2,"ItemId":"PBT=[coitem_mst]...","Properties":[{"Name":"Uf_VcStatus","Value":"APPROVED","Modified":true,"IsNull":false}],"UpdateLocking":1}]}'
```

---

## Key Rules

| Rule | Detail |
|---|---|
| `Action: 2` | Always for update |
| `ItemId` | Exact value from `_ItemId` in LoadCollection — never construct manually |
| Only changed fields | Omit fields you don't want to change; include only `Modified: true` fields |
| All values are strings | `"Value": "42"` not `"Value": 42` |
| One record per request | See [syteline-batch-operations.md](syteline-batch-operations.md) |
| `UpdateLocking: 1` | Include this; it enables optimistic locking via the `ItemId` timestamp |

---

## Handling `RefreshItems`

When you need the IDO to run post-save logic (repricing, computed fields, workflow triggers), add `?refresh=true` to the URL:

```bash
curl -s -X POST "$SYTELINE_BASE_URL/update/SLCos?refresh=true" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{ "Changes": [...] }'
```

The response will include a `RefreshItems` array with recalculated property values. Check `RefreshItems[0]` for the updated record.

For simple field-level updates (status flags, text fields) where you don't need the IDO to recalculate anything, skip `?refresh=true` — it adds latency.

---

## n8n Pattern: Bulk Update After Load

**Code node (build update payload):**

```javascript
const items = $input.first().json.Items;
const changes = items.map(item => ({
  Action: 2,
  ItemId: item._ItemId,
  Properties: [
    { Name: "Uf_VcStatus", Value: "APPROVALS NEEDED", Modified: true, IsNull: false }
  ]
}));
return [{ json: { IDOName: "VC_SLCoitems", RefreshAfterSave: false, Changes: changes } }];
```

**Upstream: guard against empty loads**

Add an IF node before the update with condition `{{ $json.Items.length > 0 }}` to skip the update when no records matched the filter.

---

## Updating a Field That Couldn't Be Set on Insert

Some properties are not writable during insert (computed by triggers, set by workflow, etc.). The pattern:

1. Insert the record normally (without the problematic field)
2. Load the record: `properties=KeyField,_ItemId&filter=KeyField = 'value'`
3. Send Action=2 with only the target field

```javascript
// After insert + load, in a Code node:
const item = $input.first().json.Items[0];
return [{ json: {
  Changes: [{
    Action: 2,
    ItemId: item._ItemId,
    Properties: [
      { Name: "Uf_CoLineSeqNo", Value: String(Number(item.CoLine) * 10), Modified: true, IsNull: false }
    ]
  }]
}}];
```

---

## Error Reference

| `Message` contains | Meaning | Fix |
|---|---|---|
| `"User requires [Write] privilege"` | Automation user lacks write access | Grant Write on this IDO to the automation user |
| `"Record locked by user..."` | Another session has the record open | Wait and retry; check for stuck sessions in SyteLine |
| `"Invalid ItemId"` | ItemId is stale (record was modified after load) | Reload the record and retry |
| `"Property X not found"` | Wrong IDO — property exists on a different one | Check if a custom/extended IDO exposes the field |
| `"IDOValueType.GetValue: failed to parse..."` | Wrong value type | Confirm value is a string; check BYTE fields use `"0"`/`"1"` |

---

## Gotchas

- **SyteLine UI shows stale data** after API updates — closing and reopening the form shows the real value. Don't re-run the update if `Success: true` was returned.
- **Custom `Uf_` fields need the extended IDO** — updating `Uf_VcStatus` requires `VC_SLCoitems`, not `SLCoitems`.
- **`_ItemId` is stable within a session** but changes if the record is modified by another process. Always load fresh if your workflow spans multiple sessions.
- **Tokens are consumed by `/invoke`** — if your update sequence includes an invoke step, fetch a fresh token before each request.
