import { BiginConfig, BiginDeal, BiginActivity, TokenResponse } from "./types.js";

export class BiginClient {
  private config: BiginConfig;
  private accessToken: string | null = null;
  private tokenExpiresAt = 0;

  constructor(config: BiginConfig) {
    this.config = config;
  }

  private get accountsUrl(): string {
    return `https://accounts.${this.config.domain}`;
  }

  private get apiUrl(): string {
    return `https://www.zohoapis.com/bigin/v2`;
  }

  private async refreshAccessToken(): Promise<string> {
    const params = new URLSearchParams({
      grant_type: "refresh_token",
      client_id: this.config.clientId,
      client_secret: this.config.clientSecret,
      refresh_token: this.config.refreshToken,
    });

    const resp = await fetch(`${this.accountsUrl}/oauth/v2/token`, {
      method: "POST",
      body: params,
    });

    if (!resp.ok) {
      const text = await resp.text();
      throw new Error(`Token refresh failed (${resp.status}): ${text}`);
    }

    const data = (await resp.json()) as TokenResponse;
    if (data.error) {
      throw new Error(`OAuth error: ${data.error}`);
    }

    this.accessToken = data.access_token;
    this.tokenExpiresAt = Date.now() + (data.expires_in - 60) * 1000;
    return this.accessToken;
  }

  private async getToken(): Promise<string> {
    if (this.accessToken && Date.now() < this.tokenExpiresAt) {
      return this.accessToken;
    }
    return this.refreshAccessToken();
  }

  private async request<T>(
    method: string,
    path: string,
    body?: unknown
  ): Promise<T> {
    const token = await this.getToken();
    const resp = await fetch(`${this.apiUrl}${path}`, {
      method,
      headers: {
        Authorization: `Zoho-oauthtoken ${token}`,
        "Content-Type": "application/json",
      },
      body: body ? JSON.stringify(body) : undefined,
    });

    if (!resp.ok) {
      const text = await resp.text();
      throw new Error(`Bigin API ${method} ${path} failed (${resp.status}): ${text}`);
    }

    return resp.json() as Promise<T>;
  }

  async listDeals(page = 1, perPage = 50): Promise<BiginDeal[]> {
    const data = await this.request<{ data: BiginDeal[] }>(
      "GET",
      `/Deals?page=${page}&per_page=${perPage}`
    );
    return data.data ?? [];
  }

  async getDeal(id: string): Promise<BiginDeal> {
    const data = await this.request<{ data: BiginDeal[] }>("GET", `/Deals/${id}`);
    return data.data[0];
  }

  async updateDeal(
    id: string,
    fields: Record<string, unknown>
  ): Promise<void> {
    await this.request("PUT", "/Deals", {
      data: [{ id, ...fields }],
    });
  }

  async searchDeals(criteria: string): Promise<BiginDeal[]> {
    const data = await this.request<{ data: BiginDeal[] }>(
      "GET",
      `/Deals/search?criteria=${encodeURIComponent(criteria)}`
    );
    return data.data ?? [];
  }

  async createTask(task: {
    Subject: string;
    Due_Date: string;
    Priority: string;
    Status: string;
    Description?: string;
    What_Id?: string;
    se_module?: string;
  }): Promise<{ id: string }> {
    const body: Record<string, unknown> = { ...task };
    if (task.What_Id) {
      body["What_Id"] = { id: task.What_Id };
      body["$se_module"] = task.se_module ?? "Deals";
      delete body["se_module"];
    }

    const data = await this.request<{ data: { details: { id: string } }[] }>(
      "POST",
      "/Tasks",
      { data: [body] }
    );
    return { id: data.data[0].details.id };
  }

  async listTasks(page = 1, perPage = 50): Promise<BiginActivity[]> {
    const data = await this.request<{ data: BiginActivity[] }>(
      "GET",
      `/Tasks?page=${page}&per_page=${perPage}`
    );
    return data.data ?? [];
  }

  async createEvent(event: {
    Event_Title: string;
    Start_DateTime: string;
    End_DateTime: string;
    Description?: string;
    What_Id?: string;
    se_module?: string;
  }): Promise<{ id: string }> {
    const body: Record<string, unknown> = { ...event };
    if (event.What_Id) {
      body["What_Id"] = { id: event.What_Id };
      body["$se_module"] = event.se_module ?? "Deals";
      delete body["se_module"];
    }

    const data = await this.request<{ data: { details: { id: string } }[] }>(
      "POST",
      "/Events",
      { data: [body] }
    );
    return { id: data.data[0].details.id };
  }

  async listEvents(page = 1, perPage = 50): Promise<BiginActivity[]> {
    const data = await this.request<{ data: BiginActivity[] }>(
      "GET",
      `/Events?page=${page}&per_page=${perPage}`
    );
    return data.data ?? [];
  }

  async listPipelines(): Promise<unknown[]> {
    const data = await this.request<{ pipelines: unknown[] }>(
      "GET",
      "/settings/deal/pipelines"
    );
    return data.pipelines ?? [];
  }

  async getFields(module = "Deals"): Promise<unknown[]> {
    const data = await this.request<{ fields: unknown[] }>(
      "GET",
      `/settings/fields?module=${module}`
    );
    return data.fields ?? [];
  }
}
