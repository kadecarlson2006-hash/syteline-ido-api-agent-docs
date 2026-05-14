# Syteline IDO REST API — Agent Reference

Reference documentation for the Infor Syteline (CloudSuite Industrial) IDO REST API, designed to be attached to any AI coding agent's context.

## Deliverable Log

[`DELIVERABLE_LOG.md`](DELIVERABLE_LOG.md) is a running log of tasks agents have completed in this repo. Each entry records the request, the discovery path taken, and the final deliverable (curl command). **Read it first** — your task may already be solved. **Append an entry when you finish** so the next agent can benefit.

---

## Prerequisites

### 1. Agent User (`SYTELINE_AGENT_USERNAME`) — introspection only

A dedicated Syteline user whose **sole purpose** is system introspection. This user discovers IDO structures, methods, and parameters so the agent can build API calls. It should **not** have permissions to read or modify real business data.

- **License:** Automation
- **Super User:** unchecked
- **Groups:** none
- **Password:** set (used via HTTP headers — never in URL paths)

#### Required IDO Permissions (you can Paste Rows Append these into User Permissions!)

| IDO | Read | Execute | Edit | Insert | Delete | Update |
|---|---|---|---|---|---|---|
| `ActiveBGTasks` | Granted | Granted | Revoked | — | — | Revoked |
| `BGTaskDefinitions` | Granted | Granted | Revoked | — | — | Revoked |
| `BGTaskHistories` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoCollections` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoMethodParameters` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoMethodResultSets` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoMethods` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoProperties` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoPropertyClasses` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoRequestExclusions` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoSubColFilterProperties` | Granted | Granted | Revoked | — | — | Revoked |
| `IdoTables` | Granted | Granted | Revoked | — | — | Revoked |
| `ProcessErrorLogs` | Granted | Granted | Revoked | — | — | Revoked |
| `SchemaTypes` | Granted | Granted | Revoked | — | — | Revoked |
| `SLSites` | Granted | Granted | Revoked | — | — | Revoked |
| `SLSystemTypes` | Granted | Granted | Revoked | — | — | Revoked |
| `SqlColumns` | Granted | Granted | Revoked | — | — | Revoked |
| `SqlTableKeys` | Granted | Granted | Revoked | — | — | Revoked |
| `SqlTables` | Granted | Granted | Revoked | — | — | Revoked |

These are **meta-IDO** permissions only. Do **not** grant this user access to business-data IDOs (e.g., `SLItems`, `SLItemprices`, `COs`).

### 2. Automation User (`SYTELINE_AUTOMATION_USERNAME`) — optional, for testing

A separate Syteline user used to **test and execute** the API calls the agent builds. This user is optional — set it up only when you want the agent to run API calls against the live system.

- **License:** Automation
- **Super User:** unchecked
- **Groups:** none
- **Password:** set

Grant task-specific IDO permissions to this user as needed (e.g., `Read` on `SLUserNames` to query user data or `SLItemPrices` to query item pricing data). Grant the minimum necessary permissions for each integration. **Never grant these permissions to the agent user.**

---

## Quick Start

1. Copy `.env.template` to `.env` and fill in your credentials
2. Attach the `docs/` folder to your AI agent's context
3. Tell the agent what you need to do in Syteline

## Documentation

See [docs/README.md](docs/README.md) for the full table of contents.

| Doc | Purpose |
|---|---|
| [Authentication](docs/01_AUTHENTICATION.md) | Token acquisition via HTTP headers |
| [IDO Overview](docs/02_IDO_OVERVIEW.md) | What IDOs are and how the metadata system works |
| [Core Operations](docs/03_CORE_OPERATIONS.md) | Load, Invoke, Update — the three API verbs |
| [Background Tasks](docs/04_BGTASK_SUBMISSION.md) | Submitting and monitoring background tasks |
| [Discovery Guide](docs/05_DISCOVERY_GUIDE.md) | How to find any IDO, its properties, methods, and parameters |
| [Gotchas](docs/06_GOTCHAS.md) | Common pitfalls and troubleshooting |
