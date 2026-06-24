import { Priority, PriorityResult, BiginDeal } from "./types.js";

const PRIORITY_RULES: {
  priority: Priority;
  label: string;
  keywords: RegExp[];
  description: string;
}[] = [
  {
    priority: "P1_EMERGENCY",
    label: "EMERGENCY - Tree on structure",
    keywords: [
      /tree\s*(on|hit|fell\s*on|through|into|damaged?)\s*(the\s*)?(house|home|roof|building|residence|apartment|living)/i,
      /on\s*(the\s*)?(house|home|roof|building|residence)/i,
      /roof\s*(damage|penetrat|hole|caved|collapse)/i,
      /structur(e|al)\s*(damage|emergency)/i,
      /house\s*(damage|emergency|tree)/i,
    ],
    description: "Tree on a house or occupied structure — life safety risk",
  },
  {
    priority: "P2_URGENT",
    label: "URGENT - Tree on vehicle/shed/blocking access",
    keywords: [
      /tree\s*(on|hit|fell\s*on|through|into|damaged?)\s*(the\s*)?(car|truck|vehicle|auto|van|suv)/i,
      /on\s*(the\s*)?(car|truck|vehicle|shed|garage|barn|outbuilding|carport)/i,
      /tree\s*(on|hit|fell\s*on|through|into)\s*(the\s*)?(shed|garage|barn|outbuilding|carport|fence)/i,
      /block(ing|ed|s)?\s*(the\s*)?(driveway|road|street|access|path|sidewalk)/i,
      /driveway\s*(block|clear|remov)/i,
      /can'?t\s*(get\s*(out|in)|leave|access|drive)/i,
      /road\s*(block|clear|hazard)/i,
    ],
    description:
      "Tree on a vehicle, shed, or blocking driveway/road access",
  },
  {
    priority: "P3_HIGH",
    label: "HIGH - Broken/threatening tree",
    keywords: [
      /broken\s*tree/i,
      /tree\s*(broke|broken|crack|split|lean|hang|dangl|threaten|unstable|danger)/i,
      /hang(ing|er)\s*(limb|branch|tree)/i,
      /lean(ing)?\s*tree/i,
      /split\s*(tree|trunk|fork)/i,
      /widow\s*maker/i,
      /about\s*to\s*fall/i,
      /could\s*fall/i,
      /risk\s*of\s*fall/i,
      /threatening/i,
      /hazard(ous)?\s*tree/i,
    ],
    description: "Tree is broken, leaning, or threatening to fall",
  },
  {
    priority: "P4_NORMAL",
    label: "NORMAL - Scheduled work",
    keywords: [],
    description: "Regular scheduled maintenance, trimming, or planned removal",
  },
];

export function categorizeJob(deal: BiginDeal): PriorityResult {
  const text = [
    deal.Deal_Name,
    deal.Description,
    deal.Stage,
    ...(typeof deal["Notes"] === "string" ? [deal["Notes"]] : []),
  ]
    .filter(Boolean)
    .join(" ");

  for (const rule of PRIORITY_RULES) {
    if (rule.keywords.length === 0) continue;
    for (const re of rule.keywords) {
      if (re.test(text)) {
        return {
          priority: rule.priority,
          label: rule.label,
          reason: `Matched: "${text.match(re)?.[0]}" in deal "${deal.Deal_Name}"`,
        };
      }
    }
  }

  return {
    priority: "P4_NORMAL",
    label: PRIORITY_RULES[3].label,
    reason: `No storm-damage keywords found in "${deal.Deal_Name}" — defaulting to normal priority`,
  };
}

export function categorizeBatch(
  deals: BiginDeal[]
): { deal: BiginDeal; result: PriorityResult }[] {
  return deals
    .map((deal) => ({ deal, result: categorizeJob(deal) }))
    .sort((a, b) => a.result.priority.localeCompare(b.result.priority));
}

export function getPriorityDescription(priority: Priority): string {
  const rule = PRIORITY_RULES.find((r) => r.priority === priority);
  return rule?.description ?? "Unknown priority";
}

export const PRIORITY_LABELS: Record<Priority, string> = {
  P1_EMERGENCY: "EMERGENCY - Tree on structure",
  P2_URGENT: "URGENT - Tree on vehicle/shed/blocking access",
  P3_HIGH: "HIGH - Broken/threatening tree",
  P4_NORMAL: "NORMAL - Scheduled work",
};
