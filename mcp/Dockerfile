# syteline-mcp — Dockerized MCP server for Infor Syteline IDO REST API
#
# Build (from this folder):
#   docker build -t syteline-mcp .
#
# Run (standalone test):
#   docker run -i --rm \
#     -e SYTELINE_BASE_URL=http://s10dev.salinavortex.com/IDORequestService/ido \
#     -e DEFAULT_SITE=Pilot_Vortx \
#     -e SYTELINE_AGENT_USERNAME=claude1 \
#     -e SYTELINE_AGENT_PASSWORD=claude \
#     syteline-mcp
#
# Claude Code / Desktop config snippet (claude_desktop_config.json):
#   {
#     "mcpServers": {
#       "syteline": {
#         "command": "docker",
#         "args": [
#           "run", "-i", "--rm",
#           "--env-file", "C:\\Users\\you\\path\\to\\.env",
#           "syteline-mcp"
#         ]
#       }
#     }
#   }
#
# Network note:
#   s10dev.salinavortex.com is internal DNS only. If Docker can't resolve it,
#   add --add-host s10dev.salinavortex.com:<internal-ip> to the run command,
#   or switch to --network=host (Windows Docker Desktop: use host.docker.internal).

FROM python:3.12-slim

ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY syteline_mcp.py .

# Runtime config — override via -e flags or --env-file
ENV SYTELINE_BASE_URL=""
ENV DEFAULT_SITE=""
ENV SYTELINE_AGENT_USERNAME=""
ENV SYTELINE_AGENT_PASSWORD=""
ENV SYTELINE_AUTOMATION_USERNAME=""
ENV SYTELINE_AUTOMATION_PASSWORD=""

ENTRYPOINT ["python", "syteline_mcp.py"]
