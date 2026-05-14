import { Router } from 'express';
import { env } from '../lib/env.js';
import { createClient, buildFilter, SytelineError } from '../lib/syteline.js';

const router = Router();
const syteline = createClient(env);

const wrap = (handler) => async (req, res) => {
  try {
    const body = await handler(req, res);
    if (body !== undefined) res.json(body);
  } catch (err) {
    const status = err instanceof SytelineError ? err.status : 500;
    res.status(status).json({ error: err.message || 'Internal error' });
  }
};

// ── Collections ────────────────────────────────────────────────────────────

router.get('/collections', wrap(async (req) => {
  const { q, accessAs } = req.query;
  const parts = [];
  if (q) parts.push({ field: 'CollectionName', op: 'LIKE', value: q });
  if (accessAs === 'BaseSyteLine') parts.push({ field: 'AccessAs', op: '=', value: 'BaseSyteLine' });
  else if (accessAs === 'Custom') parts.push({ field: 'AccessAs', op: '=', value: '__EMPTY__' });
  const filter = buildFilter(parts);
  const result = await syteline.load('IdoCollections', {
    properties: 'CollectionName,AccessAs,ReplaceFlag',
    filter: filter || undefined,
    orderby: 'CollectionName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

// ── IDO detail ─────────────────────────────────────────────────────────────

router.get('/ido/:name/overview', wrap(async (req) => {
  const { name } = req.params;
  const [result, extendersResult] = await Promise.all([
    syteline.load('IdoCollections', {
      properties: 'CollectionName,AccessAs,CollectionDesc,Extends,ReplaceFlag,RevisionNo,RevisionDate,RecordDate,UpdatedBy,DevelopmentFlag,ExtClassName,DerHasProdVersion',
      filter: buildFilter([{ field: 'CollectionName', op: '=', value: name }]),
      recordcap: 1,
    }),
    syteline.load('IdoCollections', {
      properties: 'CollectionName,AccessAs,ReplaceFlag',
      filter: buildFilter([{ field: 'Extends', op: '=', value: name }]),
      orderby: 'CollectionName',
      recordcap: 20,
    }),
  ]);
  const item = (result.Items || [])[0] || null;
  const extenders = extendersResult.Items || [];
  return { item, extenders };
}));

router.get('/ido/:name/tables', wrap(async (req) => {
  const result = await syteline.load('IdoTables', {
    properties: 'TableName,TableAlias,TableType,JoinType,JoinText',
    filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/ido/:name/properties', wrap(async (req) => {
  const result = await syteline.load('IdoProperties', {
    properties: [
      'PropertyName', 'DataType', 'DataLength', 'PropertyClass', 'PropertyType',
      'PropertyValue', 'PropertyValueArguments', 'ColumnName', 'ColumnTableAlias',
      'ColumnTableName', 'SubCollectionName', 'IsReadOnly', 'IsRequired',
      'KeySequence', 'PseudoKeyFlag', 'Sequence', 'DomainIDOName', 'DomainProperty',
      'DomainListProperties', 'PropertyDesc', 'DefaultValue', 'Validators',
    ].join(','),
    filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
    orderby: 'PropertyName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/ido/:name/methods', wrap(async (req) => {
  const result = await syteline.load('IdoMethods', {
    properties: 'MethodName,MethodType',
    filter: buildFilter([{ field: 'CollectionName', op: '=', value: req.params.name }]),
    orderby: 'MethodName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/ido/:name/methods/:method/parameters', wrap(async (req) => {
  const { name, method } = req.params;
  const result = await syteline.load('IdoMethodParameters', {
    properties: 'Sequence,ParameterName,DataType,SpDataType,SpDataLength,SpDataScale,InputFlag,OutputFlag',
    filter: buildFilter([
      { field: 'CollectionName', op: '=', value: name },
      { field: 'MethodName',     op: '=', value: method },
    ]),
    orderby: 'Sequence',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/ido/:name/methods/:method/resultsets', wrap(async (req) => {
  const { name, method } = req.params;
  const result = await syteline.load('IdoMethodResultSets', {
    properties: 'Sequence,PropertyName',
    filter: buildFilter([
      { field: 'CollectionName', op: '=', value: name },
      { field: 'MethodName',     op: '=', value: method },
    ]),
    orderby: 'Sequence',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

// ── Cross-IDO search ────────────────────────────────────────────────────────

router.get('/search/properties', wrap(async (req) => {
  const { q, op = 'LIKE' } = req.query;
  if (!q) return { items: [] };
  const result = await syteline.load('IdoProperties', {
    properties: 'CollectionName,PropertyName,DataType,ColumnName,PropertyValue',
    filter: buildFilter([{ field: 'PropertyName', op: op === '=' ? '=' : 'LIKE', value: q }]),
    orderby: 'CollectionName,PropertyName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/search/expressions', wrap(async (req) => {
  const { q, op = 'LIKE' } = req.query;
  if (!q) return { items: [] };
  const result = await syteline.load('IdoProperties', {
    properties: 'CollectionName,PropertyName,DataType,ColumnName,PropertyValue',
    filter: buildFilter([{ field: 'PropertyValue', op: op === '=' ? '=' : 'LIKE', value: q }]),
    orderby: 'CollectionName,PropertyName',
    recordcap: 500,
  });
  return { items: result.Items || [] };
}));

router.get('/search/methods', wrap(async (req) => {
  const { q, op = 'LIKE' } = req.query;
  if (!q) return { items: [] };
  const result = await syteline.load('IdoMethods', {
    properties: 'CollectionName,MethodName,MethodType',
    filter: buildFilter([{ field: 'MethodName', op: op === '=' ? '=' : 'LIKE', value: q }]),
    orderby: 'CollectionName,MethodName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

router.get('/search/tables', wrap(async (req) => {
  const { q, op = 'LIKE' } = req.query;
  if (!q) return { items: [] };
  const result = await syteline.load('IdoTables', {
    properties: 'CollectionName,TableName,TableType,JoinType',
    filter: buildFilter([{ field: 'TableName', op: op === '=' ? '=' : 'LIKE', value: q }]),
    orderby: 'CollectionName,TableName',
    recordcap: 0,
  });
  return { items: result.Items || [] };
}));

export default router;
