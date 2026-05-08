# IDO Overview — What an Intelligent Data Object Is

## The Big Picture

Syteline (Infor CloudSuite Industrial) is built on the **Mongoose framework**. Every interaction — whether from the UI, an API call, or an integration — passes through the **IDO layer**. IDOs are the middleware between clients and the database.

```
┌─────────────┐     ┌─────────────┐     ┌──────────────┐
│  Syteline   │     │   IDO       │     │  SQL Server  │
│  UI / REST  │────▶│   Runtime   │────▶│  Database    │
│  API / SOAP │     │   Service   │     │              │
└─────────────┘     └─────────────┘     └──────────────┘
```

An IDO is **not** just a table wrapper. It's a metadata-defined object that can:
- Map to one or more database tables (with joins)
- Expose computed/derived properties via SQL expressions
- Define methods (stored procedures or .NET extension class code)
- Nest subcollections to represent one-to-many relationships
- Enforce business logic via Application Event System (AES) hooks

**The system builds itself.** IDO definitions are stored as metadata in the Objects database and are themselves accessible via IDOs (`IdoCollections`, `IdoProperties`, `IdoMethods`, `IdoTables`). You can query the API to discover everything about any IDO.

## Anatomy of an IDO

Every IDO has these components:

### 1. IDO Tables

An IDO maps to one or more database tables. Discoverable via the `IdoTables` IDO.

- **Primary Base Table** — The main table (inner join). Every IDO has exactly one.
- **Secondary Tables** — Additional tables joined to the primary (inner or left outer join).

Example — the `UserNames` IDO:

| Table Name | Table Alias | Table Type | Join Type | Join Text |
|---|---|---|---|---|
| UserNames | UserNames | Primary Base | Inner | |
| UserEmail | UserEmail | Secondary | Left Outer | `UserEmail.Username = UserNames.Username AND UserEmail.EmailType = UserNames.PrimaryEmailType` |

### 2. IDO Properties

Properties are the columns/fields exposed by the IDO. Discoverable via the `IdoProperties` IDO. There are three types:

**Bound to Column** — Directly maps to a column in one of the IDO's tables.
```
Property: Username
Column:   UserNames.Username  (from the UserNames table alias)
Type:     String(128)
```

**Derived** — A computed value defined by a SQL subquery expression, which can reference other IDO properties.
```
Property: DerReplyToEmailTypeEmail
Expression: (SELECT TOP 1 EmailAddress FROM UserEmail 
             WHERE UserNames.Username = UserEmail.Username 
             AND Usernames.ReplyToEmailType = UserEmail.EmailType)
Type: String(60)
```

**Subcollection** — Represents a one-to-many relationship to another IDO (a child collection linked by key properties).
```
Property: UserGroupMaps
Subcollection: MGCore.UserGroupMaps
Link: UserId=UserId
```

### 3. IDO Methods

Methods are executable actions defined on an IDO. Discoverable via the `IdoMethods` IDO. There are two main types:

**Stored Procedure — Standard Method** — Calls a database stored procedure directly.
```
Method: GetUserAttributes
Type:   Stored Procedure - Standard Method
SP:     GetUserAttributesSp
```

**Extension Class — Standard Method** — Calls .NET code in a custom assembly DLL.
```
Method: GetUserInformation
Type:   Extension Class - Standard Method
```

> **MethodType values** (from `IdoMethods.MethodType`, verified against the pilot): `0` = Stored Procedure, `2` = Extension Class (.NET), `1` = Custom Load Method. Use this to programmatically detect which methods have been converted from SP to extension class as part of Infor's database code-out initiative.

There are also **Custom Load Methods** (stored procedures that provide custom data loading for LoadCollection).

Example methods on the `UserNames` IDO:

| Method Name | Method Type | Stored Procedure |
|---|---|---|
| GetUserAttributes | Stored Procedure - Standard Method | GetUserAttributesSp |
| GetUserInformation | Extension Class - Standard Method | |
| UserLov | Stored Procedure - Custom Load Method | UserLov |
| UserValid | Stored Procedure - Standard Method | UserValidSp |

## Why This Matters for API Work

### Infor's "Database Code-Out" Initiative

Infor is actively migrating business logic out of SQL Server stored procedures and into .NET extension class methods (C# DLLs) as part of a long-term platform modernization effort. This is sometimes called the **"database code-out"** initiative. The goal is to make Syteline cloud-hostable by removing hard dependencies on direct database access.

**The practical impact for your organization:** SQL scripts that called stored procedures directly with `EXEC SpName` will break — silently at first, then with an error — as each SP is converted. The `StoredProcedure` column on `IdoMethods` will also become empty for converted methods, since there is no longer an underlying SP to reference.

When a converted SP is called directly:

> *"You may not call this '{SpName}' as it has been converted to custom assembly application method."*

The fix is always the same: **call it through the IDO layer via the REST API** instead. The IDO method wraps the same logic — whether the implementation is a stored procedure or extension class code, the method name and parameter signature stay stable.

**This is the core reason to migrate from SQL scripts to IDO REST API calls.** The IDO layer is the stable interface that Infor commits to maintaining across versions. The implementation behind it can change without breaking integrations built against the IDO API.

## Self-Describing System

These IDOs let you discover everything about the system via the same API:

| IDO | What it tells you |
|---|---|
| `IdoCollections` | All available IDO names, `AccessAs` (core vs custom) |
| `IdoProperties` | Properties (columns/fields) on any IDO, their types, bound table, expressions |
| `IdoMethods` | Methods available on any IDO, their type (SP or extension class) |
| `IdoMethodParameters` | Method parameter names, sequence, data types, input/output direction |
| `IdoTables` | Database tables that back any IDO, join types and conditions |
| `SqlColumns` | Columns in database tables, primary key flags |
| `SqlTableKeys` | Constraints and keys on database tables |

See [05_DISCOVERY_GUIDE.md](05_DISCOVERY_GUIDE.md) for curl commands to query all of these.
