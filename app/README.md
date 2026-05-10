# IDO Explorer

A local web dashboard for browsing the Syteline IDO catalog. Powered by the same
meta-IDOs documented in `docs/05_DISCOVERY_GUIDE.md` (`IdoCollections`,
`IdoTables`, `IdoProperties`, `IdoMethods`, `IdoMethodParameters`).

The app is a thin Express proxy plus a single static page. The proxy holds the
Syteline credentials and fetches a fresh token before every API call (matching
the policy in `DELIVERABLE_LOG.md`). The browser never sees credentials and
never talks to Syteline directly, so CORS and the token-on-Invoke quirks don't
apply.

## What it does

- **Left pane**: list of every IDO (`IdoCollections`), filterable by name and
  by core vs. custom (`AccessAs`).
- **Right pane (per selected IDO)**:
  - Overview — name, `AccessAs`, whether a `CMP_`-prefixed extended variant
    exists.
  - **Tables** (`IdoTables`) with per-column text filters and a Primary /
    Secondary badge from `TableType`.
  - **Properties** (`IdoProperties`) with per-column filters and quick-toggle
    chips for Writable / Read-only / Derived / Subcollection.
  - **Methods** (`IdoMethods`) with per-column filters. Click a method row to
    open a drawer that fetches `IdoMethodParameters` and shows the ordered
    signature with IN / OUT / IN-OUT badges (derived from `InputFlag` and
    `OutputFlag`).
- **Top bar — cross-IDO search**: pick `Property`, `Method`, or `Table`, type a
  string, and hit Search. The proxy issues a single LIKE query against the
  matching meta-IDO and returns every IDO that has a match. Example use case:
  type `Item` under "Property name" → see every IDO that exposes an `Item`
  property. Clicking a result row jumps the left pane to that IDO.

## Running locally

```bash
# from the repo root
cp .env.template app/.env       # then fill in the four SYTELINE_* values
cd app
npm install
npm start                       # http://localhost:3000
```

Required env vars (read from `app/.env`, falling back to the repo root `.env`):

| Variable                    | Used for                                        |
| --------------------------- | ----------------------------------------------- |
| `SYTELINE_BASE_URL`         | Base URL ending in `/IDORequestService/ido`     |
| `DEFAULT_SITE`              | Site config name passed to `/token/{config}`    |
| `SYTELINE_AGENT_USERNAME`   | Agent user (introspection-only meta-IDO access) |
| `SYTELINE_AGENT_PASSWORD`   | Agent user password                             |
| `PORT` (optional)           | Defaults to `3000`                              |

`npm run dev` runs with `node --watch` for restart-on-save.
`npm test` runs the unit test for the filter builder.

## Project layout

```
app/
├── server.js              # Express app + proxy routes
├── lib/
│   ├── env.js             # .env loading + required-var validation
│   └── syteline.js        # token fetch + load() + buildFilter()
├── public/
│   ├── index.html         # single-page UI
│   ├── app.js             # vanilla-JS frontend
│   └── styles.css
└── test/
    └── filter.test.js     # buildFilter() unit test
```

## API routes (proxy)

All routes return JSON. Errors from Syteline come back as HTTP 502 with
`{ "error": "..." }`.

| Route                                                       | Purpose                                |
| ----------------------------------------------------------- | -------------------------------------- |
| `GET /api/env`                                              | Site + base URL (for the header pill)  |
| `GET /api/collections?q&accessAs`                           | List IDOs                              |
| `GET /api/ido/:name/overview`                               | Single IDO + extended-version probe    |
| `GET /api/ido/:name/tables`                                 | `IdoTables` for the IDO                |
| `GET /api/ido/:name/properties`                             | `IdoProperties` for the IDO            |
| `GET /api/ido/:name/methods`                                | `IdoMethods` for the IDO               |
| `GET /api/ido/:name/methods/:method/parameters`             | `IdoMethodParameters` for the method   |
| `GET /api/search/properties?q`                              | Cross-IDO property name LIKE           |
| `GET /api/search/methods?q`                                 | Cross-IDO method name LIKE             |
| `GET /api/search/tables?q`                                  | Cross-IDO table name LIKE              |

## Scope

Read-only introspection. There is no `/invoke` or `/update` path — this app is
intentionally limited to the same access level as the agent user.
