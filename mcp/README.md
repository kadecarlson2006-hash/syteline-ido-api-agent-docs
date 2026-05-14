# Syteline MCP Server

A stdio Model Context Protocol server that exposes the Infor Syteline IDO REST API to Claude. Claude spawns it on demand via `docker run -i --rm`; there's no long-running daemon.

The server's tools mirror the discovery model documented in [`../docs/05_DISCOVERY_GUIDE.md`](../docs/05_DISCOVERY_GUIDE.md):

| Tool | What it does |
|---|---|
| `syteline_find_ido` | Search `IdoCollections` by name fragment |
| `syteline_describe_ido` | Properties + methods overview for an IDO |
| `syteline_method_signature` | `IdoMethodParameters` for a single method, ordered |
| `syteline_load` | LoadCollection — read records (uses the agent user) |
| `syteline_invoke` | Call an IDO method (uses the automation user) |
| `syteline_update` | UpdateCollection — insert / update / delete (uses the automation user) |

## Files

| File | Purpose |
|---|---|
| `syteline_mcp.py` | The MCP server (stdio) |
| `Dockerfile` | Image build |
| `requirements.txt` | Python deps (`mcp[cli]`, `httpx`, `pydantic`) |

## Setup

### 1. Fill in credentials

The server reads from environment variables; the repo's [`.env.template`](../.env.template) already lists the right names:

```
SYTELINE_BASE_URL=https://your-host/IDORequestService/ido
DEFAULT_SITE=YourSiteConfigName
SYTELINE_AGENT_USERNAME=
SYTELINE_AGENT_PASSWORD=
SYTELINE_AUTOMATION_USERNAME=     # required for invoke/update
SYTELINE_AUTOMATION_PASSWORD=
```

Copy `.env.template` to `.env` at the repo root and fill it in.

### 2. Build the image

```powershell
cd mcp
docker build -t syteline-mcp .
```

### 3. Register with Claude Code

```powershell
claude mcp add syteline --scope user -- docker run -i --rm --env-file "D:\Projects\syteline-ido-api-agent-docs\.env" syteline-mcp
```

For Claude Desktop, drop the equivalent block into `%APPDATA%\Claude\claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "syteline": {
      "command": "docker",
      "args": [
        "run", "-i", "--rm",
        "--env-file", "D:\\Projects\\syteline-ido-api-agent-docs\\.env",
        "syteline-mcp"
      ]
    }
  }
}
```

### 4. Restart Claude

`claude mcp list` should show `syteline ✓ Connected`.

## Notes

- **Two-user model:** read tools use `SYTELINE_AGENT_USERNAME`; write tools use `SYTELINE_AUTOMATION_USERNAME`. Keep introspection separate from data modification — same policy as the docs.
- **Internal DNS:** if the Syteline host only resolves on the corporate network, add `--add-host your-host:<ip>` to the docker run args, or use `--network=host` (on Windows Docker Desktop, use `host.docker.internal`).
- **Token lifecycle:** the server fetches a fresh token per call (matches the guidance in [`../docs/01_AUTHENTICATION.md`](../docs/01_AUTHENTICATION.md) — tokens are destroyed after `Invoke`).
