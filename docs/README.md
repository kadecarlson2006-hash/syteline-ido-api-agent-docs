# Syteline IDO REST API — Agent Reference

**Purpose:** Teach AI coding agents how to interact with the Infor Syteline ERP system via the IDO (Intelligent Data Object) REST API. Attach these docs to your agent's context and tell it what you need to accomplish.

**Environment:** Syteline 10 (Infor CloudSuite Industrial / Mongoose framework)  
**Base URL:** `$SYTELINE_BASE_URL` (points to `/IDORequestService/ido` — REST API V2)  
**Default site:** `DEFAULT_SITE` (defaults to `Demo_DALS`)  
**Site discovery:** use `SLSites` only when default does not apply or the user requests other sites

## Docs

| File | What it covers |
|---|---|
| [01_AUTHENTICATION.md](01_AUTHENTICATION.md) | Token acquisition, multi-site configs, credential handling |
| [02_IDO_OVERVIEW.md](02_IDO_OVERVIEW.md) | What an IDO is, how the metadata system works, how to think about IDOs |
| [03_CORE_OPERATIONS.md](03_CORE_OPERATIONS.md) | Load (query), Invoke (execute), Update (insert/update/delete) — the three verbs |
| [04_BGTASK_SUBMISSION.md](04_BGTASK_SUBMISSION.md) | Submitting background tasks, the 20-parameter pattern, polling for completion |
| [05_DISCOVERY_GUIDE.md](05_DISCOVERY_GUIDE.md) | How to find any IDO, its properties, methods, and parameter formats — "teach an agent to fish" |
| [06_GOTCHAS.md](06_GOTCHAS.md) | Data type pitfalls, common errors, troubleshooting |

## Deliverable Log

See [`DELIVERABLE_LOG.md`](../DELIVERABLE_LOG.md) in the repo root for a running log of completed agent tasks — what was requested, how it was solved, and the final API calls. Check it before starting work; append to it when you're done.

## Quick Start for Agents

1. Read [01_AUTHENTICATION.md](01_AUTHENTICATION.md) to get a token
2. Read [02_IDO_OVERVIEW.md](02_IDO_OVERVIEW.md) to understand what IDOs are
3. Read [03_CORE_OPERATIONS.md](03_CORE_OPERATIONS.md) for the API verbs you'll use
4. Use [05_DISCOVERY_GUIDE.md](05_DISCOVERY_GUIDE.md) to explore IDOs you haven't worked with before
5. Check [06_GOTCHAS.md](06_GOTCHAS.md) when something doesn't work

## Credential Setup

Two user accounts are supported (sourced from `.env`):

```env
SYTELINE_BASE_URL=https://YOUR-SYTELINE-HOST/IDORequestService/ido
DEFAULT_SITE=Demo_DALS

# Agent user — introspection only (discovers IDO structure)
SYTELINE_AGENT_USERNAME=<agent-user>
SYTELINE_AGENT_PASSWORD=<from .env>

# Automation user — optional (tests/executes API calls)
SYTELINE_AUTOMATION_USERNAME=<automation-user>
SYTELINE_AUTOMATION_PASSWORD=<from .env>
```

- **Agent user:** Has meta-IDO permissions only. Used for all discovery and introspection. Should **not** have access to real business data.
- **Automation user:** Optional. Used to test and execute the API calls the agent builds. Grant task-specific IDO permissions to this user as needed.

**Never hardcode credentials in scripts or documentation.** Use `$SYTELINE_AGENT_USERNAME` / `$SYTELINE_AGENT_PASSWORD` (bash) or `$env:SYTELINE_AGENT_USERNAME` / `$env:SYTELINE_AGENT_PASSWORD` (PowerShell). When testing with the automation user, use `$SYTELINE_AUTOMATION_USERNAME` / `$SYTELINE_AUTOMATION_PASSWORD`.

## What These Docs Don't Cover

- Specific IDO method parameter schemas (e.g., a 55-parameter report SP or the 477 individual properties on the SLItems ido) — those belong in project-specific docs or an `ido-references/` folder
- N8N workflow design
- Specific evening utility catalog and scheduling
