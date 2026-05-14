# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This repository contains **reference documentation** for the Infor Syteline (CloudSuite Industrial) IDO REST API. The documentation is designed to be attached to AI coding agents' context to teach them how to interact with the Syteline API.

### Key Concepts

- **IDO (Intelligent Data Object):** The Mongoose framework middleware layer between clients and the database
- **Three API verbs:** Load (GET), Invoke (POST), Update (POST)
- **Self-describing system:** Use meta-IDOs to discover any IDO's structure via the API itself
- **Background tasks:** Long-running operations submitted via `BGTaskDefinitions.BGTaskSubmit`

## Documentation Structure

The repository contains comprehensive documentation in the `docs/` folder:

| File | Purpose |
|------|--------|
| [01_AUTHENTICATION.md](docs/01_AUTHENTICATION.md) | Token acquisition, multi-site configs, credential handling |
| [02_IDO_OVERVIEW.md](docs/02_IDO_OVERVIEW.md) | What an IDO is, how the metadata system works, how to think about IDOs |
| [03_CORE_OPERATIONS.md](docs/03_CORE_OPERATIONS.md) | Load (query), Invoke (execute), Update (insert/update/delete) — the three verbs |
| [04_BGTASK_SUBMISSION.md](docs/04_BGTASK_SUBMISSION.md) | Submitting background tasks, the 20-parameter pattern, polling for completion |
| [05_DISCOVERY_GUIDE.md](docs/05_DISCOVERY_GUIDE.md) | How to find any IDO, its properties, methods, and parameter formats — "teach an agent to fish" |
| [06_GOTCHAS.md](docs/06_GOTCHAS.md) | Data type pitfalls, common errors, troubleshooting |

## Common Development Tasks

### 1. Understanding the API

- **Read the documentation:** Start with `docs/01_AUTHENTICATION.md` to understand token acquisition, then `docs/02_IDO_OVERVIEW.md` for IDO concepts, and `docs/03_CORE_OPERATIONS.md` for API operations.
- **Use the Discovery Guide:** When working with an unfamiliar IDO, use `docs/05_DISCOVERY_GUIDE.md` to learn how to explore IDOs, properties, methods, and parameters via the API itself.

### 2. Finding Information

- **Search for IDOs:** Use `IdoCollections` meta-IDO to find available IDOs
- **Explore IDO structure:** Use `IdoProperties`, `IdoMethods`, `IdoMethodParameters` to understand any IDO
- **Check DELIVERABLE_LOG:** Before starting work, check `DELIVERABLE_LOG.md` — a previous agent may have already built the workflow. Only append a new entry when you've completed a non-trivial end-user IDO workflow; do not log repo maintenance, doc edits, or validation runs.

### 3. Testing API Calls

- **Use curl:** All examples use curl format (universal, easily convertible to any client)
- **Two user accounts:**
  - `SYTELINE_AGENT_USERNAME`: Introspection only (meta-IDO read/execute access)
  - `SYTELINE_AUTOMATION_USERNAME`: Optional, for testing/executing API calls
- **Never hardcode credentials:** Use environment variables (`$SYTELINE_AGENT_USERNAME`, `$SYTELINE_AGENT_PASSWORD`)

## Architecture Overview

### IDO System Architecture

```
┌─────────────┐     ┌─────────────┐     ┌──────────────┐
│  Syteline   │     │   IDO       │     │  SQL Server  │
│  UI / REST  │────▶│   Runtime   │────▶│  Database    │
│  API / SOAP │     │   Service   │     │              │
└─────────────┘     └─────────────┘     └──────────────┘
```

IDOs are metadata-defined objects that:
- Map to one or more database tables (with joins)
- Expose computed/derived properties via SQL expressions
- Define methods (stored procedures or .NET extension class code)
- Nest subcollections to represent one-to-many relationships
- Enforce business logic via Application Event System (AES) hooks

### Self-Describing System

The system is self-describing through these meta-IDOs:

| Meta-IDO | What it tells you |
|----------|-------------------|
| `IdoCollections` | All IDO names, `AccessAs` (core vs custom) |
| `IdoProperties` | Properties on any IDO — types, bound columns, expressions |
| `IdoMethods` | Methods on any IDO — SP or extension class |
| `IdoMethodParameters` | Method parameter names, types, sequence, direction |
| `IdoTables` | Database tables backing any IDO, join relationships |
| `SqlColumns` | Raw database columns, types, primary key flags |
| `SqlTableKeys` | Constraints on database tables |

## Important Files

- **`.env.template`**: Template for credential and token configuration
- **`DELIVERABLE_LOG.md`**: Running log of completed agent tasks — check this first before starting work!
- **`docs/`**: Main documentation set (see table above)

## Development Guidelines

1. **Always check DELIVERABLE_LOG.md first** — a prior agent may have already solved the task
2. **Use the agent user for discovery** — it has meta-IDO permissions only and cannot access real business data
3. **Ask before modifying data** — confirm with the user before executing Invoke calls on methods that modify state, or Update calls
4. **Set small recordcap values** during discovery to avoid overwhelming responses
5. **Verify API behavior** by running actual requests against the test environment

## Tips for Working with This Repository

- The documentation is **comprehensive and self-contained** — all information needed to work with the Syteline IDO REST API is in the `docs/` folder
- **Focus on the discovery process** — the system is designed to be self-describing, so agents should learn how to explore IDOs
- **Use curl examples** — they are universal and can be easily converted to any programming language
- **Two-user model** is critical for safety — keep introspection separate from data modification

## Environment Setup

1. Copy `.env.template` to `.env`
2. Fill in your Syteline credentials
3. Attach the `docs/` folder to your AI agent's context
4. Tell the agent what you need to accomplish in Syteline
