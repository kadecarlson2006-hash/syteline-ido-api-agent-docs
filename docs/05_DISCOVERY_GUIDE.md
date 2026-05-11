# Discovery Guide — Teaching an Agent to Fish

The Syteline IDO system is **self-describing**. Every IDO, its properties, methods, tables, and relationships can be discovered through the same REST API. An agent can explore the system without prior knowledge of any specific IDO.

**Base URL:** `$SYTELINE_BASE_URL`  
All examples assume `$TOKEN` is set (see [01_AUTHENTICATION.md](01_AUTHENTICATION.md)).

---

## General Discovery Rules

1. **Always set `recordcap=10` (or similar small number) during discovery.** Broad queries without a cap can return thousands of rows and overwhelm the response. Only use `recordcap=0` (all rows) when you have a narrow filter and need the complete set.

2. **Omit `properties` (or use `properties=*`) to get all properties during discovery.** When exploring an unfamiliar IDO, seeing all columns helps you confirm you found the right one. Once you know what you need, narrow to specific properties for efficiency.

3. **You can always fall back to the core meta-IDOs.** When you're stuck, these are the IDOs that describe the system itself:

| Meta-IDO | What it tells you | Key queryable properties |
|---|---|---|
| `IdoCollections` | All IDO names, `AccessAs` (core vs custom) | `CollectionName`, `AccessAs` |
| `IdoProperties` | Properties on any IDO — types, bound columns, expressions | `PropertyName`, `DataType`, `ColumnName`, `ColumnTableAlias`, `PropertyClass`, `IsReadOnly` |
| `IdoMethods` | Methods on any IDO — SP or extension class | `CollectionName`, `MethodName`, `MethodType` |
| `IdoMethodParameters` | Method parameter names, types, sequence, direction | `Sequence`, `ParameterName`, `DataType`, `SpDataType`, `InputFlag`, `OutputFlag` |
| `IdoTables` | Database tables backing any IDO, join relationships | `CollectionName`, `TableName`, `TableAlias`, `TableType`, `JoinType`, `JoinText` |
| `SqlColumns` | Raw database columns, types, primary key flags | `tableName`, `derShadowColumnName`, `dataType`, `isPrimaryKey`, `primaryKeyPosition` |
| `SqlTableKeys` | Constraints on database tables | |

> **Field name gotchas on IdoProperties:** The correct property names are `PropertyName` (not `PropName`), `ColumnName` (not `BoundColumn`). Writable properties have `IsReadOnly = null` or `"0"`; read-only = `"1"`. Derived properties have `ColumnName = null`.

> **MethodType values on IdoMethods** (verified against the pilot): `0` = Stored Procedure (most common), `2` = .NET Extension Class method, `1` = Custom Load Method. Note: the `StoredProcedure` property does **not** exist on `IdoMethods` — querying it returns `"Property StoredProcedure not found"`. Filter by `MethodName` instead.

4. **It's okay to try things.** The agent user is scoped to read-only introspection. An exploratory LoadCollection or Invoke that fails will return a clear error message, not break anything. However, **ask the user before executing methods that modify data** (Insert, Update, Delete, or Invoke on methods that change state) — those should use the automation user if configured.

---

## Step 1: Find the IDO

Finding the right IDO is the first and hardest challenge. There are several strategies depending on what you know.

### Strategy A: Search by name guess

If you know (or can guess) part of the IDO name:

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoCollections?properties=CollectionName,AccessAs&filter=CollectionName%20LIKE%20N'%25ItemPrice%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

`AccessAs` helps distinguish core vs custom IDOs:
- `AccessAs = 'BaseSyteLine'` → Core Syteline IDO (delivered by Infor)
- `AccessAs = ''` (empty) → Custom IDO (added by the implementation team, often prefixed with a company identifier like `CMP_`)

### Strategy B: Search by stored procedure name

If you're converting a SQL script that calls a stored procedure, search by method name (the IDO method name usually matches or closely resembles the SP name):

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=CollectionName,MethodName,MethodType&filter=MethodName%20LIKE%20N'%25ItemPriceChangeSp%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

> **Note:** The `StoredProcedure` property does not exist on `IdoMethods` in all environments — you'll get `"Property StoredProcedure not found"`. Filter by `MethodName` instead; the method name is sufficient.

This directly tells you which IDO hosts the method and whether it's a stored procedure or extension class.

### Strategy C: Search by database table name

If you know the SQL table (e.g., from a `DELETE FROM itemprice` statement), find which IDO uses it:

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoTables?properties=CollectionName,TableName,TableType,JoinType&filter=TableName%20%3D%20N'itemprice'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

`TableType` values:
- `3` = Primary Base Table (the IDO's main table)
- `0` = Secondary Table (joined to the primary)

**You usually want the IDO where your table is the Primary Base Table (TableType=3).**

If multiple IDOs share the same primary table, check `AccessAs` on `IdoCollections` to find the core one (or use the `CMP_`-prefixed extended version if it exists — the extended IDO inherits the core IDO's methods and adds custom properties).

### Strategy D: Poke around with a LoadCollection

Once you've identified a candidate IDO, load a few rows with all properties to confirm it's the right one:

```bash
curl -s "$SYTELINE_BASE_URL/load/UserNames?recordcap=3" \
  -H "Authorization: $TOKEN"
```

Omitting `properties` returns all properties. Seeing the actual data helps you verify you're looking at the right IDO — column names, data shapes, and values tell you a lot.

> **Note:** Some core IDOs (e.g., `SLItemprices`) may be replaced by a custom extended version (e.g., `CMP_SLItemPrices`) via extend and replace. If you get `"User requires [Read] privilege for CMP_{IDO}"`, the system is redirecting through the extended IDO. Request permissions on the IDO named in the error.

---

## Step 2: Get Properties on the IDO

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,DataType,PropertyClass,ColumnTableAlias,ColumnName&filter=CollectionName%20%3D%20N'SLItemprices'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

`PropertyClass` values:
- Empty/`0` = Bound to a database column (has a `ColumnName` and `ColumnTableAlias`)
- `1` = Derived (computed via SQL expression; `ColumnName` is `null`)
- `3` = Subcollection (link to child IDO; represents a one-to-many relationship)

This is especially useful to **map SQL column names to IDO property names**. For example, the SQL column `unit_price1` on the `itemprice` table maps to the IDO property `UnitPrice1` on `SLItemprices`.

### Find which property maps to a SQL column

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,ColumnName,ColumnTableAlias,DataType&filter=CollectionName%20%3D%20N'SLItemprices'%20AND%20ColumnName%20%3D%20N'unit_price1'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

---

## Step 3: Get Methods on the IDO

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=MethodName,MethodType&filter=CollectionName%20%3D%20N'SLItemprices'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

This tells you:
- What methods are available
- Whether each is a stored procedure (`MethodType=0`) or extension class (`MethodType=2`)
- The stored procedure name (if applicable)

> **Note:** The `StoredProcedure` property is documented in some Syteline versions but may not exist on `IdoMethods` in your environment — you'll get `"Property StoredProcedure not found"`. Omit it and use just `MethodName,MethodType`; the method name is sufficient to proceed to Step 4.

When a stored procedure has been converted to an extension class (MethodType=2), it can **only** be called through the IDO API, not via direct SQL `EXEC`.

---

## Step 4: Get Method Parameters

Use the `IdoMethodParameters` IDO to discover the exact parameter signature of any method:

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethodParameters?properties=Sequence,ParameterName,DataType,SpDataType,SpDataLength,SpDataScale,InputFlag,OutputFlag&filter=CollectionName%20%3D%20N'SLItemprices'%20AND%20MethodName%20%3D%20N'ItemPriceChangeSp'&orderby=Sequence&recordcap=0" \
  -H "Authorization: $TOKEN"
```

This returns every parameter with:

| Property | What it tells you |
|---|---|
| `Sequence` | Positional index (1-based) — determines the order in the JSON array for `/invoke` |
| `ParameterName` | Name of the parameter |
| `DataType` | IDO-level data type |
| `SpDataType` | SQL Server data type (e.g., `nvarchar`, `decimal`) |
| `SpDataLength` / `SpDataScale` | Length and scale for the SQL type |
| `InputFlag` | `1` = input parameter |
| `OutputFlag` | `1` = output parameter |

Parameters with both `InputFlag=1` and `OutputFlag=1` are **IN/OUT** — pass a value in, and the method writes back to the same position.

> **Note:** A `Direction` property is referenced in some Syteline docs but does not exist on `IdoMethodParameters` — you'll get `"Property Direction not found"`. Use `InputFlag` and `OutputFlag` instead; they convey the same information.

**This is the definitive way to build an `/invoke` call.** The parameter count and order from `IdoMethodParameters` must match your JSON array exactly.

### Fallback: Discover from task history

For background tasks, you can also see what parameters were used in historical runs:

```bash
curl -s "$SYTELINE_BASE_URL/load/BGTaskHistories?properties=TaskName,TaskParms1,TaskParms2,CompletionStatus&filter=TaskName%20LIKE%20N'%25ItemPrice%25'&orderby=CompletionDate%20DESC&recordcap=5" \
  -H "Authorization: $TOKEN"
```

---

## Step 5: Get the Backing Tables

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoTables?properties=TableName,TableAlias,TableType,JoinType,JoinText&filter=CollectionName%20%3D%20N'SLItemprices'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

---

## Step 6: Find Primary Keys

Primary keys matter for Update and Delete operations. Use `SqlColumns` to find them:

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=tableName,table_name_key,derShadowColumnName,dataType,isPrimaryKey,primaryKeyPosition&filter=tableName%20%3D%20N'itemprice_mst'%20AND%20isPrimaryKey%20%3D%201&recordcap=0" \
  -H "Authorization: $TOKEN"
```

> **Note:** SQL table names in `SqlColumns` use the `_mst` suffix (e.g., `itemprice_mst` not `itemprice`). The IDO layer strips this suffix. Use `derShadowColumnName` to get the actual column name.

Example result for `itemprice_mst`:

| Position | Column | Type |
|---|---|---|
| 1 | `item` | ItemType |
| 2 | `curr_code` | CurrCodeType |
| 3 | `effect_date` | Date4Type |
| 4 | `site_ref` | SiteType |

This compound key tells you: a price record is uniquely identified by item + currency + effective date + site. A query like `DELETE FROM itemprice WHERE unit_price1 = 0` without considering `effect_date` could be dangerously broad.

---

## Discovery Workflow Summary

```
1. What IDO do I need?
   → Search IdoCollections by name
   → Search IdoMethods by MethodName (LIKE %SpName%)
   → Search IdoTables by database table name
   → Poke around with LoadCollection (recordcap=3)

2. Is this the right IDO?
   → Load a few rows with all properties to verify
   → Check AccessAs on IdoCollections (core vs custom)

3. What properties does it have?
   → Query IdoProperties for the IDO
   → Map SQL columns ↔ IDO properties via ColumnName

4. What methods can I call?
   → Query IdoMethods for the IDO

5. What parameters does the method expect?
   → Query IdoMethodParameters (definitive)
   → Check BGTaskHistories for historical examples

6. What are the primary keys?
   → Query SqlColumns with isPrimaryKey=1

7. Call it
   → GET  /load/{IDO}                    to query
   → POST /invoke/{IDO}?method={Method}  to execute
   → POST /update/{IDO}                  to insert/update/delete
```

---

## Exploring Database Tables Directly

When you need the raw database schema (not the IDO abstraction):

### Find a table

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=tableName&filter=tableName%20LIKE%20N'%25itempric%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

> **Note:** Syteline tables use a `_mst` suffix in the database (e.g., `itemprice_mst`). The IDO layer references them without the suffix.

### List columns on a table

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=tableName,table_name_key,derShadowColumnName,dataType,isPrimaryKey,primaryKeyPosition,isNullable&filter=tableName%20%3D%20N'itemprice_mst'&recordcap=0" \
  -H "Authorization: $TOKEN"
```

Key properties on `SqlColumns`:

| Property | What it tells you |
|---|---|
| `tableName` | Table name (with `_mst` suffix) |
| `table_name_key` | Composite key (usually equals tableName) |
| `derShadowColumnName` | The actual column name |
| `dataType` | Custom Syteline type (e.g., `ItemType`, `CurrCodeType`) |
| `isPrimaryKey` | `1` = part of primary key |
| `primaryKeyPosition` | Position in the primary key (1-based) |
| `isNullable` | Whether the column allows NULL |

---

## Practical Example: Convert a SQL SP to an API Call

When you encounter an error like:
> *"You may not call this 'ItemPriceChangeSp' as it has been converted to custom assembly application method."*

### 1. Find the IDO that hosts the method

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethods?properties=CollectionName,MethodName,MethodType&filter=MethodName%20LIKE%20N'%25ItemPriceChangeSp%25'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

### 2. Get method parameters

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoMethodParameters?properties=Sequence,ParameterName,DataType,SpDataType,InputFlag,OutputFlag&filter=CollectionName%20%3D%20N'SLItemprices'%20AND%20MethodName%20%3D%20N'ItemPriceChangeSp'&orderby=Sequence&recordcap=0" \
  -H "Authorization: $TOKEN"
```

### 3. Invoke via the API

```bash
curl -s -X POST \
  "$SYTELINE_BASE_URL/invoke/SLItemprices?method=ItemPriceChangeSp" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["param1", "param2", null, ...]'
```

This pattern works for **any** stored procedure that has been converted to an extension class method.

---

## Practical Example: Convert a SQL DELETE to an API Call

Given a SQL script like `DELETE FROM itemprice WHERE unit_price1 = 0`, here's how to convert it to an API call:

### 1. Find the IDO for the table

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoTables?properties=CollectionName,TableName,TableType&filter=TableName%20%3D%20N'itemprice'%20AND%20TableType%20%3D%203&recordcap=10" \
  -H "Authorization: $TOKEN"
```

Filter `TableType=3` to find IDOs where `itemprice` is the **primary base table**.

### 2. Pick the right IDO

If multiple IDOs share the primary table, check which is core vs. custom:

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoCollections?properties=CollectionName,AccessAs&filter=CollectionName%20%3D%20N'SLItemprices'&recordcap=1" \
  -H "Authorization: $TOKEN"
```

For deletes, it usually doesn't matter whether you use the core IDO or the `CMP_`-extended version — the delete hits the same base table.

### 3. Map SQL column names to IDO property names

```bash
curl -s "$SYTELINE_BASE_URL/load/IdoProperties?properties=PropertyName,ColumnName,ColumnTableAlias,DataType&filter=CollectionName%20%3D%20N'SLItemprices'%20AND%20ColumnName%20%3D%20N'unit_price1'&recordcap=10" \
  -H "Authorization: $TOKEN"
```

Result: SQL column `unit_price1` → IDO property `UnitPrice1`.

### 4. Find the primary keys

```bash
curl -s "$SYTELINE_BASE_URL/load/SqlColumns?properties=derShadowColumnName,dataType,primaryKeyPosition&filter=tableName%20%3D%20N'itemprice_mst'%20AND%20isPrimaryKey%20%3D%201&recordcap=0" \
  -H "Authorization: $TOKEN"
```

The `itemprice` table has a **4-column composite key**: `item`, `curr_code`, `effect_date`, `site_ref`. This means `DELETE WHERE unit_price1 = 0` without considering `effect_date` is likely too broad — stop and confirm scope with the user.

### 5. Load matching records first

```bash
curl -s "$SYTELINE_BASE_URL/load/SLItemprices?properties=Item,CurrCode,EffectDate,UnitPrice1&filter=UnitPrice1%20%3D%200&recordcap=10" \
  -H "Authorization: $TOKEN"
```

### 6. Delete via Update endpoint

```bash
curl -s -X POST \
  "$SYTELINE_BASE_URL/update/SLItemprices" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"Items":[{"Action":"Delete","Properties":{"Item":"WIDGET-A","CurrCode":"USD","EffectDate":"2026-01-01","SiteRef":"{SITE_FROM_USER_SCOPE}"}}]}'
```

Use the key values from the LoadCollection to build each Delete item.
