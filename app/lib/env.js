import dotenv from 'dotenv';
import { existsSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, resolve } from 'node:path';

const __dirname = dirname(fileURLToPath(import.meta.url));
const appEnv = resolve(__dirname, '..', '.env');
const repoEnv = resolve(__dirname, '..', '..', '.env');

if (existsSync(appEnv)) dotenv.config({ path: appEnv });
else if (existsSync(repoEnv)) dotenv.config({ path: repoEnv });
else dotenv.config();

const required = [
  'SYTELINE_BASE_URL',
  'DEFAULT_SITE',
  'SYTELINE_AGENT_USERNAME',
  'SYTELINE_AGENT_PASSWORD',
];

const missing = required.filter((k) => !process.env[k]);
if (missing.length) {
  console.error(
    `Missing required env vars: ${missing.join(', ')}.\n` +
      `Create app/.env from .env.template and fill in your Syteline credentials.`,
  );
  process.exit(1);
}

export const env = {
  baseUrl: process.env.SYTELINE_BASE_URL.replace(/\/$/, ''),
  site: process.env.DEFAULT_SITE,
  username: process.env.SYTELINE_AGENT_USERNAME,
  password: process.env.SYTELINE_AGENT_PASSWORD,
  port: Number(process.env.PORT) || 3000,
};
