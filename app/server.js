import express from 'express';
import { fileURLToPath } from 'node:url';
import { dirname, resolve } from 'node:path';
import { env } from './lib/env.js';
import idoRouter from './routes/ido.js';
import dbRouter  from './routes/db.js';

const __dirname = dirname(fileURLToPath(import.meta.url));
const app = express();

app.use(express.static(resolve(__dirname, 'public')));

app.get('/api/env', (_req, res) => {
  res.json({
    site: env.site,
    baseUrl: env.baseUrl,
    dbConfigured: !!env.dbServer,
  });
});

app.use('/api', idoRouter);
app.use('/api/db', dbRouter);

// SPA fallback — must come after all /api/* routes
app.get('*', (_req, res) => res.sendFile(resolve(__dirname, 'public', 'index.html')));

app.listen(env.port, () => {
  console.log(`IDO Explorer  →  http://localhost:${env.port}`);
  console.log(`Syteline      →  ${env.baseUrl}  (site: ${env.site})`);
  console.log(`DB            →  ${env.dbServer ? `${env.dbServer} / ${env.dbName}` : 'not configured'}`);
});
