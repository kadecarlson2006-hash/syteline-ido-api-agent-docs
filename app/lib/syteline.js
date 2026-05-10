const escapeSqlString = (value) => String(value).replace(/'/g, "''");

export function buildFilter(parts) {
  const clauses = [];
  for (const part of parts) {
    if (!part) continue;
    const { field, op = '=', value } = part;
    if (value === undefined || value === null || value === '') continue;
    if (op === 'LIKE') {
      clauses.push(`${field} LIKE N'%${escapeSqlString(value)}%'`);
    } else if (op === 'IS NULL' || op === 'IS NOT NULL') {
      clauses.push(`${field} ${op}`);
    } else if (typeof value === 'number') {
      clauses.push(`${field} ${op} ${value}`);
    } else if (value === '__EMPTY__') {
      clauses.push(`(${field} IS NULL OR ${field} = N'')`);
    } else {
      clauses.push(`${field} ${op} N'${escapeSqlString(value)}'`);
    }
  }
  return clauses.join(' AND ');
}

export function buildLoadUrl(baseUrl, ido, { properties, filter, orderby, recordcap } = {}) {
  const params = new URLSearchParams();
  if (properties) params.set('properties', Array.isArray(properties) ? properties.join(',') : properties);
  if (filter) params.set('filter', filter);
  if (orderby) params.set('orderby', orderby);
  if (recordcap !== undefined && recordcap !== null) params.set('recordcap', String(recordcap));
  const qs = params.toString();
  return `${baseUrl}/load/${encodeURIComponent(ido)}${qs ? `?${qs}` : ''}`;
}

export function createClient({ baseUrl, site, username, password }) {
  async function getToken() {
    const res = await fetch(`${baseUrl}/token/${encodeURIComponent(site)}`, {
      method: 'GET',
      headers: { username, password },
    });
    if (!res.ok) {
      throw new SytelineError(`Token request failed (HTTP ${res.status})`, res.status);
    }
    const body = await res.json();
    if (!body.Success || !body.Token) {
      throw new SytelineError(body.Message || 'Token request returned Success=false', 502);
    }
    return body.Token;
  }

  async function load(ido, options = {}) {
    const token = await getToken();
    const url = buildLoadUrl(baseUrl, ido, options);
    const res = await fetch(url, {
      method: 'GET',
      headers: { Authorization: token },
    });
    const text = await res.text();
    let body;
    try {
      body = JSON.parse(text);
    } catch {
      throw new SytelineError(
        `Syteline returned non-JSON response (HTTP ${res.status}): ${text.slice(0, 200)}`,
        502,
      );
    }
    if (body && body.Success === false) {
      throw new SytelineError(body.Message || 'Syteline returned Success=false', 502);
    }
    return body;
  }

  return { getToken, load };
}

export class SytelineError extends Error {
  constructor(message, status = 502) {
    super(message);
    this.name = 'SytelineError';
    this.status = status;
  }
}
