# Bigin CRM Job Agent

MCP server that connects Claude Code to your Bigin CRM for storm-damage job triage.

## What this does

Pulls jobs (deals) from Bigin CRM, categorizes them by storm-damage urgency, and schedules them as tasks in Bigin's calendar.

### Priority tiers

| Priority | Meaning | Default due |
|----------|---------|-------------|
| P1_EMERGENCY | Tree on house/building | Today |
| P2_URGENT | Tree on car/shed, blocking driveway | Tomorrow |
| P3_HIGH | Broken/leaning/threatening tree | 3 days |
| P4_NORMAL | Regular scheduled work | 7 days |

## Available tools

- `bigin_list_jobs` — List all deals from Bigin
- `bigin_get_job` — Get details on a specific deal
- `bigin_categorize_jobs` — Auto-categorize all jobs by storm priority
- `bigin_set_priority` — Manually override a job's priority
- `bigin_preview_schedule` — Preview suggested schedule (no changes made)
- `bigin_schedule_job` — Create a Bigin task for one job
- `bigin_schedule_all` — Create Bigin tasks for all jobs at once
- `bigin_list_schedule` — View existing tasks/events from Bigin
- `bigin_list_pipelines` — Show Bigin pipeline configuration
- `bigin_list_fields` — Inspect available fields on any module

## Typical workflow

1. Run `bigin_list_jobs` to see what's in the pipeline
2. Run `bigin_categorize_jobs` to auto-triage by storm damage
3. Review the categorization — override with `bigin_set_priority` if needed
4. Run `bigin_preview_schedule` to check proposed dates
5. Run `bigin_schedule_all` to push tasks into Bigin's calendar
