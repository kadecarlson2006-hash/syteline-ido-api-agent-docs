import sql from 'mssql';
import { env } from './env.js';

let pool = null;

function buildConfig() {
  const base = {
    server: env.dbServer,
    database: env.dbName,
    options: {
      encrypt: false,              // internal network — no TLS required
      trustServerCertificate: true,
    },
    pool: {
      max: 5,
      min: 0,
      idleTimeoutMillis: 30_000,
    },
  };

  if (env.dbDomain) {
    // Windows domain auth (NTLM) — current dev mode.
    // To switch to SQL login for containers: remove DB_DOMAIN from env,
    // set DB_USER / DB_PASSWORD to a SQL login, and this branch won't run.
    return {
      ...base,
      authentication: {
        type: 'ntlm',
        options: {
          domain:   env.dbDomain,
          userName: env.dbUser,
          password: env.dbPassword,
        },
      },
    };
  }

  // SQL login — used when DB_DOMAIN is absent (containers, CI, etc.)
  return {
    ...base,
    authentication: {
      type: 'default',
      options: {
        userName: env.dbUser,
        password: env.dbPassword,
      },
    },
  };
}

export async function getPool() {
  if (!env.dbServer) throw new Error('DB not configured — set DB_SERVER in .env');
  if (!pool) {
    pool = new sql.ConnectionPool(buildConfig());
    await pool.connect();
  }
  return pool;
}

export { sql };
