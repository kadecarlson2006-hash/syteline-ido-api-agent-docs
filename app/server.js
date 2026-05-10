import express from 'express';
import { fileURLToPath } from 'node:url';
import { dirname, resolve } from 'node:path';
import { env } from './lib/env.js';
import { createClient, buildFilter, SytelineError } from './lib/syteline.js';

const __dirname = dirname(fileURLToPath(import.meta.url));
const app = express();
const syteline = createClient(env);

app.use(express.static(resolve(__dirname, 'public')));

const wrap = (handler) => async (req, res) => {
  try {
    const body = await handler(req, res);
    if (body !== undefined) res.json(body);
  } catch (err) {
    const status = err instanceof SytelineError ? err.status : 500;
    res.status(status).json({ error: err.message || 'Internal error' });
  }
};

app.get('/api/env', (_req, res) => {
  res.json({ site: env.site, baseUrl: env.baseUrl });
});

app.get(
  '/api/collections',
  wrap(async (req) => {
    const { q, accessAs } = req.query;
    const parts = [];
    if (q) parts.push({ field: 'CollectionName', op: 'LIKE', value: q });
    if (accessAs === 'BaseSyteLine') parts.push({ field: 'AccessAs', op: '=', value: 'BaseSyteLine' });
    else if (accessAs === 'Custom') parts.push({ field: 'AccessAs', op: '=', value: '__EMPTY__' });
    const filter = buildFilter(parts);
    const result = await syteline.load('IdoCollections', {
      properties: 'CollectionName,AccessAs',
      filter: filter || undefined,
      orderby: 'CollectionName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/ido/:name/overview',
  wrap(async (req) => {
    const { name } = req.params;
    const result = await syteline.load('IdoCollections', {
      properties: 'CollectionName,AccessAs',
      filter: buildFilter([{ field: 'CollectionName', op: '=', value: name }]),
      recordcap: 1,
    });
    const item = (result.Items || [])[0] || null;
    let extended = null;
    if (item) {
      const probe = await syteline.load('IdoCollections', {
        properties: 'CollectionName,AccessAs',
        filter: buildFilter([{ field: 'CollectionName', op: '=', value: `CMP_${name}` }]),
        recordcap: 1,
      });
      extended = (probe.Items || [])[0] || null;
    }
    return { item, extended };
  }),
);

app.get(
  '/api/ido/:name/tables',
  wrap(async (req) => {
    const result = await syteline.load('IdoTables', {
      properties: 'TableName,TableAlias,TableType,JoinType,JoinText',
      filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/ido/:name/properties',
  wrap(async (req) => {
    const result = await syteline.load('IdoProperties', {
      properties: 'PropertyName,DataType,PropertyClass,IsReadOnly,ColumnName,ColumnTableAlias',
      filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
      orderby: 'PropertyName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/ido/:name/methods',
  wrap(async (req) => {
    const result = await syteline.load('IdoMethods', {
      properties: 'MethodName,MethodType',
      filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
      orderby: 'MethodName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/ido/:name/methods/:method/parameters',
  wrap(async (req) => {
    const { name, method } = req.params;
    const result = await syteline.load('IdoMethodParameters', {
      properties:
        'Sequence,ParameterName,DataType,SpDataType,SpDataLength,SpDataScale,InputFlag,OutputFlag',
      filter: buildFilter([
        { field: 'CollectionName', op: '=', value: name },
        { field: 'MethodName', op: '=', value: method },
      ]),
      orderby: 'Sequence',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/search/properties',
  wrap(async (req) => {
    const { q } = req.query;
    if (!q) return { items: [] };
    const result = await syteline.load('IdoProperties', {
      properties: 'CollectionName,PropertyName,DataType,ColumnName',
      filter: buildFilter([{ field: 'PropertyName', op: 'LIKE', value: q }]),
      orderby: 'CollectionName,PropertyName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/search/methods',
  wrap(async (req) => {
    const { q } = req.query;
    if (!q) return { items: [] };
    const result = await syteline.load('IdoMethods', {
      properties: 'CollectionName,MethodName,MethodType',
      filter: buildFilter([{ field: 'MethodName', op: 'LIKE', value: q }]),
      orderby: 'CollectionName,MethodName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.get(
  '/api/search/tables',
  wrap(async (req) => {
    const { q } = req.query;
    if (!q) return { items: [] };
    const result = await syteline.load('IdoTables', {
      properties: 'CollectionName,TableName,TableType,JoinType',
      filter: buildFilter([{ field: 'TableName', op: 'LIKE', value: q }]),
      orderby: 'CollectionName,TableName',
      recordcap: 0,
    });
    return { items: result.Items || [] };
  }),
);

app.listen(env.port, () => {
  console.log(`IDO Explorer running on http://localhost:${env.port}`);
  console.log(`Connected to Syteline at ${env.baseUrl} (site: ${env.site})`);
});
