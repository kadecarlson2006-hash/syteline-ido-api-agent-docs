import { BiginClient } from "./bigin-client.js";
import { Priority, BiginDeal, PriorityResult } from "./types.js";

const PRIORITY_TASK_PRIORITY: Record<Priority, string> = {
  P1_EMERGENCY: "Highest",
  P2_URGENT: "High",
  P3_HIGH: "Normal",
  P4_NORMAL: "Low",
};

function addDays(date: Date, days: number): Date {
  const d = new Date(date);
  d.setDate(d.getDate() + days);
  return d;
}

function formatDate(date: Date): string {
  return date.toISOString().split("T")[0];
}

function suggestDueDate(priority: Priority, fromDate: Date = new Date()): string {
  switch (priority) {
    case "P1_EMERGENCY":
      return formatDate(fromDate);
    case "P2_URGENT":
      return formatDate(addDays(fromDate, 1));
    case "P3_HIGH":
      return formatDate(addDays(fromDate, 3));
    case "P4_NORMAL":
      return formatDate(addDays(fromDate, 7));
  }
}

export interface ScheduleEntry {
  dealId: string;
  dealName: string;
  priority: Priority;
  suggestedDate: string;
  taskPriority: string;
}

export function buildSchedule(
  categorized: { deal: BiginDeal; result: PriorityResult }[],
  fromDate: Date = new Date()
): ScheduleEntry[] {
  return categorized.map(({ deal, result }) => ({
    dealId: deal.id,
    dealName: deal.Deal_Name,
    priority: result.priority,
    suggestedDate: suggestDueDate(result.priority, fromDate),
    taskPriority: PRIORITY_TASK_PRIORITY[result.priority],
  }));
}

export async function scheduleJob(
  client: BiginClient,
  entry: ScheduleEntry,
  description?: string
): Promise<{ taskId: string }> {
  const { id } = await client.createTask({
    Subject: `[${entry.priority}] ${entry.dealName}`,
    Due_Date: entry.suggestedDate,
    Priority: entry.taskPriority,
    Status: "Not Started",
    Description:
      description ??
      `Auto-scheduled by storm triage. Priority: ${entry.priority}`,
    What_Id: entry.dealId,
    se_module: "Deals",
  });
  return { taskId: id };
}

export async function scheduleBatch(
  client: BiginClient,
  entries: ScheduleEntry[]
): Promise<{ dealId: string; dealName: string; taskId: string }[]> {
  const results: { dealId: string; dealName: string; taskId: string }[] = [];
  for (const entry of entries) {
    const { taskId } = await scheduleJob(client, entry);
    results.push({
      dealId: entry.dealId,
      dealName: entry.dealName,
      taskId,
    });
  }
  return results;
}
