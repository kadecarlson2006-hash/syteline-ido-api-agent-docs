#!/usr/bin/env node

import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";
import { BiginClient } from "./bigin-client.js";
import { categorizeJob, categorizeBatch, PRIORITY_LABELS } from "./categorizer.js";
import { buildSchedule, scheduleJob, scheduleBatch, ScheduleEntry } from "./scheduler.js";
import { BiginConfig, Priority } from "./types.js";

function loadConfig(): BiginConfig {
  const required = (name: string): string => {
    const val = process.env[name];
    if (!val) throw new Error(`Missing required env var: ${name}`);
    return val;
  };

  return {
    clientId: required("ZOHO_CLIENT_ID"),
    clientSecret: required("ZOHO_CLIENT_SECRET"),
    refreshToken: required("ZOHO_REFRESH_TOKEN"),
    domain: process.env["ZOHO_DOMAIN"] ?? "zoho.com",
    pipelineName: process.env["BIGIN_PIPELINE_NAME"] ?? "Pipeline",
  };
}

const server = new McpServer({
  name: "bigin-job-agent",
  version: "1.0.0",
});

let client: BiginClient;

try {
  client = new BiginClient(loadConfig());
} catch (e) {
  console.error(
    `Config error: ${e instanceof Error ? e.message : e}\nCopy .env.template to .env and fill in your Zoho credentials.`
  );
  process.exit(1);
}

// ── Tool: List all jobs (deals) ──

server.tool(
  "bigin_list_jobs",
  "List all jobs (deals) from Bigin CRM. Returns deal name, stage, contact, and description.",
  { page: z.number().optional().describe("Page number (default 1)") },
  async ({ page }) => {
    const deals = await client.listDeals(page ?? 1);
    const summary = deals.map((d) => ({
      id: d.id,
      name: d.Deal_Name,
      stage: d.Stage,
      contact: d.Contact_Name?.name ?? "—",
      amount: d.Amount,
      description: d.Description?.substring(0, 200) ?? "—",
    }));
    return {
      content: [
        {
          type: "text" as const,
          text: JSON.stringify(summary, null, 2),
        },
      ],
    };
  }
);

// ── Tool: Get job details ──

server.tool(
  "bigin_get_job",
  "Get full details for a specific job (deal) by its Bigin record ID.",
  { deal_id: z.string().describe("Bigin deal record ID") },
  async ({ deal_id }) => {
    const deal = await client.getDeal(deal_id);
    return {
      content: [{ type: "text" as const, text: JSON.stringify(deal, null, 2) }],
    };
  }
);

// ── Tool: Categorize all jobs by storm priority ──

server.tool(
  "bigin_categorize_jobs",
  `Categorize all jobs by storm-damage priority.
Priority tiers:
  P1 EMERGENCY — tree on house/building (life safety)
  P2 URGENT — tree on car/shed, blocking driveway/road
  P3 HIGH — broken, leaning, or threatening tree
  P4 NORMAL — regular scheduled maintenance`,
  { page: z.number().optional().describe("Page of deals to categorize") },
  async ({ page }) => {
    const deals = await client.listDeals(page ?? 1);
    const results = categorizeBatch(deals);

    const output = results.map(({ deal, result }) => ({
      id: deal.id,
      name: deal.Deal_Name,
      priority: result.priority,
      label: result.label,
      reason: result.reason,
    }));

    const counts: Record<string, number> = {};
    for (const r of results) {
      counts[r.result.priority] = (counts[r.result.priority] ?? 0) + 1;
    }

    return {
      content: [
        {
          type: "text" as const,
          text: JSON.stringify({ summary: counts, jobs: output }, null, 2),
        },
      ],
    };
  }
);

// ── Tool: Manually set priority on a job ──

server.tool(
  "bigin_set_priority",
  "Override the auto-categorized priority on a specific job. Updates a custom field on the deal in Bigin.",
  {
    deal_id: z.string().describe("Bigin deal record ID"),
    priority: z
      .enum(["P1_EMERGENCY", "P2_URGENT", "P3_HIGH", "P4_NORMAL"])
      .describe("Priority tier to set"),
  },
  async ({ deal_id, priority }) => {
    await client.updateDeal(deal_id, {
      Storm_Priority: priority,
      Description_Prefix: PRIORITY_LABELS[priority as Priority],
    });
    return {
      content: [
        {
          type: "text" as const,
          text: `Updated deal ${deal_id} to priority ${priority} (${PRIORITY_LABELS[priority as Priority]})`,
        },
      ],
    };
  }
);

// ── Tool: Preview schedule ──

server.tool(
  "bigin_preview_schedule",
  "Preview a suggested schedule for all jobs based on storm priority. Does NOT create any tasks — just shows what would be scheduled.",
  { page: z.number().optional() },
  async ({ page }) => {
    const deals = await client.listDeals(page ?? 1);
    const categorized = categorizeBatch(deals);
    const schedule = buildSchedule(categorized);

    const output = schedule.map((s) => ({
      dealId: s.dealId,
      dealName: s.dealName,
      priority: s.priority,
      suggestedDate: s.suggestedDate,
      biginPriority: s.taskPriority,
    }));

    return {
      content: [
        {
          type: "text" as const,
          text: JSON.stringify(output, null, 2),
        },
      ],
    };
  }
);

// ── Tool: Schedule a single job ──

server.tool(
  "bigin_schedule_job",
  "Create a task in Bigin for a specific job. The task will appear in Bigin's Activities/Calendar.",
  {
    deal_id: z.string().describe("Bigin deal record ID"),
    deal_name: z.string().describe("Job/deal name for the task subject"),
    priority: z
      .enum(["P1_EMERGENCY", "P2_URGENT", "P3_HIGH", "P4_NORMAL"])
      .describe("Priority tier"),
    due_date: z
      .string()
      .optional()
      .describe("Due date (YYYY-MM-DD). If omitted, auto-assigned by priority."),
    description: z.string().optional().describe("Additional notes for the task"),
  },
  async ({ deal_id, deal_name, priority, due_date, description }) => {
    const entry: ScheduleEntry = {
      dealId: deal_id,
      dealName: deal_name,
      priority: priority as Priority,
      suggestedDate:
        due_date ??
        buildSchedule([
          {
            deal: { id: deal_id, Deal_Name: deal_name } as any,
            result: {
              priority: priority as Priority,
              label: "",
              reason: "",
            },
          },
        ])[0].suggestedDate,
      taskPriority:
        priority === "P1_EMERGENCY"
          ? "Highest"
          : priority === "P2_URGENT"
            ? "High"
            : priority === "P3_HIGH"
              ? "Normal"
              : "Low",
    };

    const { taskId } = await scheduleJob(client, entry, description);

    return {
      content: [
        {
          type: "text" as const,
          text: `Created task ${taskId} for "${deal_name}" due ${entry.suggestedDate} (priority: ${priority})`,
        },
      ],
    };
  }
);

// ── Tool: Schedule all categorized jobs at once ──

server.tool(
  "bigin_schedule_all",
  "Create tasks in Bigin for ALL jobs based on auto-categorized priority. Tasks appear in Bigin's calendar. This will create one task per deal.",
  { page: z.number().optional() },
  async ({ page }) => {
    const deals = await client.listDeals(page ?? 1);
    const categorized = categorizeBatch(deals);
    const schedule = buildSchedule(categorized);
    const results = await scheduleBatch(client, schedule);

    return {
      content: [
        {
          type: "text" as const,
          text: JSON.stringify(
            {
              message: `Created ${results.length} tasks`,
              tasks: results,
            },
            null,
            2
          ),
        },
      ],
    };
  }
);

// ── Tool: View existing schedule ──

server.tool(
  "bigin_list_schedule",
  "List existing tasks and events from Bigin Activities (the Bigin calendar).",
  {
    type: z
      .enum(["tasks", "events", "both"])
      .optional()
      .describe("Which activities to show (default: both)"),
  },
  async ({ type }) => {
    const show = type ?? "both";
    const results: Record<string, unknown[]> = {};

    if (show === "tasks" || show === "both") {
      results.tasks = await client.listTasks();
    }
    if (show === "events" || show === "both") {
      results.events = await client.listEvents();
    }

    return {
      content: [
        { type: "text" as const, text: JSON.stringify(results, null, 2) },
      ],
    };
  }
);

// ── Tool: List pipelines ──

server.tool(
  "bigin_list_pipelines",
  "List all pipelines configured in Bigin. Useful for finding pipeline names and stages.",
  {},
  async () => {
    const pipelines = await client.listPipelines();
    return {
      content: [
        { type: "text" as const, text: JSON.stringify(pipelines, null, 2) },
      ],
    };
  }
);

// ── Tool: Inspect deal fields ──

server.tool(
  "bigin_list_fields",
  "List all available fields on a Bigin module (default: Deals). Useful for understanding what data is available.",
  {
    module: z
      .string()
      .optional()
      .describe("Module name (default: Deals). Also try: Tasks, Events, Contacts"),
  },
  async ({ module }) => {
    const fields = await client.getFields(module ?? "Deals");
    const summary = (fields as { api_name: string; data_type: string; display_label: string }[]).map(
      (f) => ({
        api_name: f.api_name,
        type: f.data_type,
        label: f.display_label,
      })
    );
    return {
      content: [
        { type: "text" as const, text: JSON.stringify(summary, null, 2) },
      ],
    };
  }
);

// ── Start ──

async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
}

main().catch((err) => {
  console.error("Fatal:", err);
  process.exit(1);
});
