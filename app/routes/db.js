import { Router } from 'express';
import { getPool, sql } from '../lib/db.js';

const router = Router();

const wrap = (handler) => async (req, res) => {
  try {
    const body = await handler(req, res);
    if (body !== undefined) res.json(body);
  } catch (err) {
    const status = err.message?.includes('not configured') ? 503 : 500;
    res.status(status).json({ error: err.message || 'DB error' });
  }
};

// ── Column ─────────────────────────────────────────────────────────────────
// GET /api/db/column/:table/:column
// Returns the SQL Server column definition for a single column.
// Used by the property drawer to validate IDO ColumnName against the real schema.

router.get('/column/:table/:column', wrap(async (req) => {
  const pool = await getPool();
  const result = await pool.request()
    .input('table',  sql.NVarChar, req.params.table)
    .input('column', sql.NVarChar, req.params.column)
    .query(`
      SELECT
        c.COLUMN_NAME,
        c.DATA_TYPE,
        c.CHARACTER_MAXIMUM_LENGTH,
        c.NUMERIC_PRECISION,
        c.NUMERIC_SCALE,
        c.IS_NULLABLE,
        c.COLUMN_DEFAULT
      FROM INFORMATION_SCHEMA.COLUMNS c
      WHERE c.TABLE_NAME  = @table
        AND c.COLUMN_NAME = @column
    `);
  return { item: result.recordset[0] || null };
}));

// ── Table columns ──────────────────────────────────────────────────────────
// GET /api/db/table/:table/columns
// All columns for a table — used to validate full IDO→DB table mapping
// and to spot columns that exist in the DB but have no IDO property bound to them.

router.get('/table/:table/columns', wrap(async (req) => {
  const pool = await getPool();
  const result = await pool.request()
    .input('table', sql.NVarChar, req.params.table)
    .query(`
      SELECT
        c.COLUMN_NAME,
        c.ORDINAL_POSITION,
        c.DATA_TYPE,
        c.CHARACTER_MAXIMUM_LENGTH,
        c.NUMERIC_PRECISION,
        c.NUMERIC_SCALE,
        c.IS_NULLABLE,
        c.COLUMN_DEFAULT
      FROM INFORMATION_SCHEMA.COLUMNS c
      WHERE c.TABLE_NAME = @table
      ORDER BY c.ORDINAL_POSITION
    `);
  return { items: result.recordset };
}));

// ── Table indexes ──────────────────────────────────────────────────────────
// GET /api/db/table/:table/indexes
// Index definitions — cross-referenced against non-sargable IDO properties
// to confirm whether a filter will hit an index or do a full scan.

router.get('/table/:table/indexes', wrap(async (req) => {
  const pool = await getPool();
  const result = await pool.request()
    .input('table', sql.NVarChar, req.params.table)
    .query(`
      SELECT
        i.name                                                            AS IndexName,
        i.type_desc                                                       AS IndexType,
        i.is_unique                                                       AS IsUnique,
        i.is_primary_key                                                  AS IsPrimaryKey,
        STRING_AGG(c.name, ', ') WITHIN GROUP (ORDER BY ic.key_ordinal)  AS KeyColumns
      FROM sys.indexes        i
      JOIN sys.index_columns  ic ON i.object_id  = ic.object_id AND i.index_id = ic.index_id
      JOIN sys.columns        c  ON ic.object_id = c.object_id  AND ic.column_id = c.column_id
      JOIN sys.tables         t  ON i.object_id  = t.object_id
      WHERE t.name               = @table
        AND ic.is_included_column = 0
      GROUP BY i.name, i.type_desc, i.is_unique, i.is_primary_key
      ORDER BY i.is_primary_key DESC, i.name
    `);
  return { items: result.recordset };
}));

// ── Table / View definition ────────────────────────────────────────────────
// GET /api/db/table/:table/definition
// Returns object type (VIEW / USER_TABLE), the CREATE VIEW text if applicable,
// and the full column list. Used by the IDO Tables drawer.

router.get('/table/:table/definition', wrap(async (req) => {
  const pool = await getPool();
  const { table } = req.params;

  const [defResult, colResult] = await Promise.all([
    pool.request()
      .input('table', sql.NVarChar, table)
      .query(`
        SELECT o.type_desc AS ObjectType, m.definition AS Definition
        FROM sys.objects o
        LEFT JOIN sys.sql_modules m ON o.object_id = m.object_id
        WHERE o.name = @table
          AND o.type_desc IN ('VIEW', 'USER_TABLE')
      `),
    pool.request()
      .input('table', sql.NVarChar, table)
      .query(`
        SELECT
          c.COLUMN_NAME, c.ORDINAL_POSITION, c.DATA_TYPE,
          c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION, c.NUMERIC_SCALE,
          c.IS_NULLABLE, c.COLUMN_DEFAULT
        FROM INFORMATION_SCHEMA.COLUMNS c
        WHERE c.TABLE_NAME = @table
        ORDER BY c.ORDINAL_POSITION
      `),
  ]);

  const def = defResult.recordset[0] || null;
  return {
    objectType:  def?.ObjectType || null,
    definition:  def?.Definition || null,
    columns:     colResult.recordset,
  };
}));

// ── Stubs for future layers ────────────────────────────────────────────────
// GET /api/db/forms             → browse SLForms / Forms table
// GET /api/db/forms/:form       → single form detail
// GET /api/db/forms/:form/components → FormComponents for a form
// (implement once DB layer is validated via column/index routes above)

export default router;
