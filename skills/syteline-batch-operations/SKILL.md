# SKILL: Batch Operations — Processing Multiple Records

Use this skill when you need to insert, update, or delete more than one record. The IDO API processes **one record per request** — this skill explains why and shows the correct loop patterns for different environments.

---

## The One-Record-Per-Request Rule

Even though the `Changes` array accepts multiple entries, **only the first entry is processed per request**. Subsequent entries in the same array either fail silently or return an error. This is not a bug you can work around — it is the expected behavior.

```json
// WRONG: Only the first change will be applied
{
  "Changes": [
    { "Action": 2, "ItemId": "PBT=[...]...", "Properties": [...] },
    { "Action": 2, "ItemId": "PBT=[...]...", "Properties": [...] }
  ]
}

// CORRECT: One Changes entry per HTTP request
{ "Changes": [{ "Action": 2, "ItemId": "PBT=[...]...", "Properties": [...] }] }
{ "Changes": [{ "Action": 2, "ItemId": "PBT=[...]...", "Properties": [...] }] }
```

---

## Pattern A: n8n (Automatic Per-Item Processing)

n8n's HTTP Request node processes one input item per execution by default. Structure your Code node to return one item per record, and the HTTP Request node will loop automatically.

**Code node — map input rows to individual update payloads:**

```javascript
const items = $input.first().json.Items;

// Return one item per record — HTTP Request node sends each separately
return items.map(item => ({
  json: {
    IDOName: "VC_SLCoitems",
    RefreshAfterSave: false,
    Changes: [{
      Action: 2,
      ItemId: item._ItemId,
      Properties: [
        { Name: "Uf_VcStatus", Value: "APPROVED", Modified: true, IsNull: false }
      ]
    }]
  }
}));
```

**HTTP Request node settings:**
- Method: POST
- URL: `{{ $SYTELINE_BASE_URL }}/update/VC_SLCoitems`
- Body: JSON → `{{ $json }}`
- The node runs once per item automatically

> **Tip:** If your Code node is in "Run Once for All Items" mode (for building insert payloads from multiple upstream items), switch to returning an array of items instead of wrapping everything into one payload.

---

## Pattern B: Script Loop (Node.js / JavaScript)

When running outside n8n, loop over records and make one request per record. Collect errors so the loop doesn't stop on first failure.

```javascript
async function batchUpdate(baseUrl, ido, changes, getToken) {
  const results = { success: [], failed: [] };

  for (const change of changes) {
    const token = await getToken(); // fetch fresh token per request
    const res = await fetch(`${baseUrl}/update/${ido}`, {
      method: "POST",
      headers: {
        "Authorization": token,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ Changes: [change] })
    });

    const result = await res.json();
    if (result.Success) {
      results.success.push(change.ItemId);
    } else {
      results.failed.push({ ItemId: change.ItemId, error: result.Message });
      console.error(`Failed: ${change.ItemId} — ${result.Message}`);
    }
  }

  console.log(`Done: ${results.success.length} succeeded, ${results.failed.length} failed`);
  return results;
}
```

---

## Pattern C: Large Dataset — Batch with Record Cap

For large datasets (hundreds or thousands of records), use `recordcap` to process in batches and track progress with a count query.

**Workflow:**
1. Run a count query (SQL or IDO load with `recordcap=1` + check `MoreRowsExist`) to know total scope
2. Load a batch: `recordcap=500` with a filter that excludes already-processed records
3. Process the batch (one request per record)
4. Repeat until count query returns 0

```bash
# Step 1: Count remaining
curl -s "$SYTELINE_BASE_URL/load/VC_SLCoitems?properties=CoNum&filter=Stat%20%3D%20N'O'%20AND%20(Uf_VcStatus%20<>%20N'APPROVED'%20OR%20Uf_VcStatus%20IS%20NULL)&recordcap=1" \
  -H "Authorization: $TOKEN"
# If MoreRowsExist=true, there are more than 1 record remaining

# Step 2: Load a batch
curl -s "$SYTELINE_BASE_URL/load/VC_SLCoitems?properties=CoNum,CoLine,_ItemId&filter=Stat%20%3D%20N'O'%20AND%20(Uf_VcStatus%20<>%20N'APPROVED'%20OR%20Uf_VcStatus%20IS%20NULL)&recordcap=500" \
  -H "Authorization: $TOKEN"
# Process each item, then repeat
```

**Why `recordcap=500`?** It's a safe batch size that keeps responses fast. Avoid `recordcap=0` (all rows) for large datasets — responses can be very large and time out.

---

## Pattern D: Driving IDO Batch Updates from SQL

When the records you need to update are best identified by a SQL query (especially when you need to join tables the IDO can't express as a filter), use SQL to get the keys, then drive the IDO via a `CoNum IN (...)` filter.

```javascript
// n8n Code node: build IDO filter from SQL results
const coNums = $input.all().map(i => `'${i.json.co_num}'`).join(',');
const filter = `CoNum IN (${coNums}) AND Stat = 'O' AND (Uf_VcStatus <> 'APPROVALS NEEDED' OR Uf_VcStatus IS NULL)`;
return [{ json: { filter } }];
```

Use `{{ $json.filter }}` as the `filter` query parameter in the IDO GET node. This pattern avoids re-implementing join logic in the IDO layer.

> **Limitation:** Very large `IN` lists (thousands of values) can hit URL length limits. If this happens, chunk the list into batches of 100–200 values and run the GET + update cycle for each chunk.

---

## Per-Record Error Handling

Never abort a batch on the first error. Log failures and continue — a constraint violation on one record shouldn't stop the remaining 499 from updating.

In n8n, enable **"Continue on Fail"** on the HTTP Request node, then add a downstream Code node to inspect which items failed:

```javascript
// Downstream of HTTP Request with "Continue on Fail" enabled
const results = $input.all();
const failures = results.filter(r => r.error || r.json?.Success === false);
const successes = results.filter(r => !r.error && r.json?.Success === true);

console.log(`${successes.length} succeeded, ${failures.length} failed`);
failures.forEach(f => console.error("Failed:", f.json?.Message || f.error?.message));

return [{ json: { successCount: successes.length, failureCount: failures.length } }];
```

---

## Progress Tracking

For long-running batches, use a SQL count query before and after to verify progress:

```sql
-- Count remaining records before and after to verify progress
SELECT COUNT(*) AS remaining
FROM VC_SLCoitems  -- or underlying table: coitem_mst
WHERE Stat = 'O'
AND (Uf_VcStatus <> 'APPROVED' OR Uf_VcStatus IS NULL)
```

Or via IDO (no SQL access needed):
```bash
curl -s "$SYTELINE_BASE_URL/load/VC_SLCoitems?properties=CoNum&filter=Stat%20%3D%20N'O'%20AND%20(Uf_VcStatus%20%3C%3E%20N'APPROVED'%20OR%20Uf_VcStatus%20IS%20NULL)&recordcap=0" \
  -H "Authorization: $TOKEN"
# Check Items.length in response
```

---

## Gotchas

- **Token reuse:** Tokens are session-based, not timer-based. Reuse the same token across batch requests until the session is destroyed or the server rejects it. If a long-running script starts getting unauthorized responses, obtain a new token and continue; in some environments, infrastructure or session timeouts may interrupt long batches, but that is environment-specific rather than normal token expiry.
- **n8n "Run Once for All Items" vs. per-item:** Code nodes in "Run Once for All Items" mode collect all input items before running. If you need per-item processing downstream, return an array of items (not a single item with an array inside).
- **Empty batch guard:** Always check `Items.length > 0` (or `MoreRowsExist`) before entering the loop — posting an empty `Changes` array is harmless but wastes a round trip.
- **Rate limiting:** The IDO API doesn't advertise rate limits, but hammering hundreds of requests per second can stress the server. Add a short delay (50–200ms) between requests if the server starts returning timeouts.
