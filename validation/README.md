# Validation Harness

Read-only validator for `skills/*/SKILL.md` entries against an SL10 pilot.

## What it does

1. Walks each skill folder.
2. Extracts concrete claims about IDOs, methods, and tables (REST URL patterns and filter expressions in skill text).
3. Verifies each claim against the pilot using **read-only** queries against meta-IDOs (`IdoCollections`, `IdoMethods`, `IdoMethodParameters`, `SqlColumns`).
4. Writes per-skill JSON reports and an aggregated `MANIFEST.md`.

The harness **never** calls `/invoke` or `/update`. Method existence is verified by signature lookup, not execution.

## Setup

```bash
cp .env.example .env
# edit if your pilot URL or password differ
set -a; . ./.env; set +a   # bash
# or, in PowerShell: gc .env | %{ if ($_ -match '^([^=]+)=(.*)$') { Set-Item -Path "Env:$($Matches[1])" -Value $Matches[2] } }
```

## Run

```bash
# All Tier A + B skills (~22), 5-wide pool of claude1..claude5
python validate_all.py --skills-dir ../skills

# A single skill
python validate_skill.py ../skills/syteline-rest-api-playbook

# Just Tier A
python validate_all.py --skills-dir ../skills --tier A
```

Per-skill JSON lands in `results/<skill>.json`; aggregated `MANIFEST.md` lands next to this README.

## Files

| File | Purpose |
|---|---|
| `pilot_client.py` | Read-only HTTP client (token + meta-IDO queries) |
| `extract_claims.py` | Parse SKILL.md → concrete IDO/method/table claims |
| `validate_skill.py` | Per-skill validator (CLI) |
| `validate_all.py` | Orchestrator — fans out across claude1..claude5 |
| `skill-inventory.md` | Static inventory of the current skills PR (regenerate per snapshot) |

## What "PASS / PARTIAL / FAIL / no-claims" mean

- **PASS** — every concrete claim extracted from the skill verified against the pilot.
- **PARTIAL** — some claims verified, some did not. Likely fixable by tweaking IDO/method names in the skill, or by granting the agent user permission for a specific meta-IDO.
- **FAIL** — none of the claims verified. Suspect the skill targets a different version of Syteline, or refers to objects that don't exist on this pilot.
- **no-claims** — no concrete claims could be extracted (skill is purely conceptual). Not a failure; the skill is just outside the harness's verification scope.
