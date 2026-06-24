# Bigin CRM Job Agent

A Claude Code MCP server that connects to your [Bigin CRM](https://www.bigin.com/) to categorize storm-damage jobs by urgency and schedule them.

## Setup

### 1. Get Zoho API credentials

1. Go to [Zoho API Console](https://api-console.zoho.com/)
2. Click **Add Client** → **Self Client**
3. Generate a code with these scopes:
   ```
   ZohoBigin.modules.ALL,ZohoBigin.settings.ALL
   ```
4. Use the generated code to get a refresh token:
   ```bash
   curl -X POST "https://accounts.zoho.com/oauth/v2/token" \
     -d "grant_type=authorization_code" \
     -d "client_id=YOUR_CLIENT_ID" \
     -d "client_secret=YOUR_CLIENT_SECRET" \
     -d "code=YOUR_GENERATED_CODE"
   ```
5. Save the `refresh_token` from the response.

### 2. Configure environment

```bash
cp .env.template .env
# Fill in ZOHO_CLIENT_ID, ZOHO_CLIENT_SECRET, ZOHO_REFRESH_TOKEN
```

### 3. Install and build

```bash
npm install
npm run build
```

### 4. Add to Claude Code

Add this to your Claude Code MCP settings (`.claude/settings.json` or via `claude mcp add`):

```json
{
  "mcpServers": {
    "bigin-job-agent": {
      "command": "node",
      "args": ["path/to/bigin-job-agent/dist/index.js"],
      "env": {
        "ZOHO_CLIENT_ID": "your-client-id",
        "ZOHO_CLIENT_SECRET": "your-client-secret",
        "ZOHO_REFRESH_TOKEN": "your-refresh-token"
      }
    }
  }
}
```

Or via the CLI:
```bash
claude mcp add bigin-job-agent node path/to/bigin-job-agent/dist/index.js \
  -e ZOHO_CLIENT_ID=xxx \
  -e ZOHO_CLIENT_SECRET=xxx \
  -e ZOHO_REFRESH_TOKEN=xxx
```

## Usage

Once connected, ask Claude Code things like:

- "List my jobs from Bigin"
- "Categorize all my jobs by storm damage priority"
- "Show me a schedule for the storm damage work"
- "Schedule the emergency jobs first"
- "What fields are available on my deals?"

## Priority tiers

| Priority | What it means | Auto-scheduled |
|----------|---------------|----------------|
| P1 EMERGENCY | Tree on house/building | Today |
| P2 URGENT | Tree on car, shed, blocking driveway | +1 day |
| P3 HIGH | Broken/leaning/threatening tree | +3 days |
| P4 NORMAL | Regular maintenance | +7 days |

The categorizer scans deal names and descriptions for keywords. You can always override with `bigin_set_priority`.
