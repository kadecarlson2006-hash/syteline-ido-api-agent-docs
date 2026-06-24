export interface BiginConfig {
  clientId: string;
  clientSecret: string;
  refreshToken: string;
  domain: string;
  pipelineName: string;
}

export type Priority = "P1_EMERGENCY" | "P2_URGENT" | "P3_HIGH" | "P4_NORMAL";

export interface PriorityResult {
  priority: Priority;
  label: string;
  reason: string;
}

export interface BiginDeal {
  id: string;
  Deal_Name: string;
  Stage: string;
  Amount: number | null;
  Contact_Name: { name: string; id: string } | null;
  Description: string | null;
  Pipeline: string;
  [key: string]: unknown;
}

export interface BiginActivity {
  id: string;
  Subject: string;
  Due_Date: string;
  Status: string;
  Priority: string;
  Description: string | null;
  What_Id: { name: string; id: string } | null;
  [key: string]: unknown;
}

export interface TokenResponse {
  access_token: string;
  expires_in: number;
  token_type: string;
  error?: string;
}
