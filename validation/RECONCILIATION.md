# Reconciliation Report — Hermes Crosscheck PR

**Date:** 2026-05-08
**Branch:** `validation-harness` (off `SalinaVortexCorporation/main` + Ivan's `hermes-crosscheck` PR #1)
**Pilot:** `http://s10dev.salinavortex.com` / config `Pilot_Vortx`
**Worker pool:** `claude1`–`claude5` (Automation-licensed super-users)

---

## TL;DR

| Result | Count |
|---|---|
| **Skills validated** | 22 (Tier A + B) |
| **PASS** | 11 |
| **PARTIAL** | 1 (a teaching example, expected) |
| **FAIL** | 0 |
| **No claims to verify** | 10 |
| **Skills out of scope** (Tier C) | 32 |
| **In-house docs corrected** | 3 files (Ivan's `b68f309` introduced 1 factual error and 1 misleading claim) |

The skill set is largely sound. Reconciliation work was small in volume but high in impact — the doc tweaks were where real corrections were needed, not the skills.

---

## Findings on Ivan's in-house doc tweaks (commit `b68f309`)

| Claim Ivan made | Pilot reality | Action taken |
|---|---|---|
| `MethodType` values: `2`=SP, `3`=ExtClass, `0`=System | `0`=SP, `2`=ExtClass, `1`=Custom Load Method (verified on 16 UserNames methods including `EncryptPassword`/`GetEncryptedUserPasswordSp`/`UserLov`) | **Fixed** — corrected the values in `02_IDO_OVERVIEW.md:86` and `05_DISCOVERY_GUIDE.md:30`. The OG canonical statement at `05:130` already had it right. |
| `PropertyName` is the field name (not `PropName`) | Confirmed: `PropertyName` works, `PropName` returns `"Property PropName not found"` | **Kept** — Ivan's gotcha note in `05` is correct |
| Derived properties have `ColumnName = null` | Confirmed on `UserNames.DerReplyToEmailTypeEmail` | **Kept** |
| `_mst` suffix convention | Already documented in OG `06_GOTCHAS.md` "## SQL Table Name Suffixes" — Ivan duplicated it | **Merged** — kept Ivan's better example/curl into the OG section, removed his duplicate "## Database Table Name Conventions" section |
| `SLJobmatls` (lowercase 'm') is correct, not `SLJobMatls` | Both casings exist as **distinct, separate IDOs** on the pilot (`SLJobMatlAlls`, `SLJobmatlCompliances`, `SLJobmatlJobs`, etc.) | **Softened** — kept the case-sensitivity warning but removed the misleading "lowercase is correct" claim |

## Findings per skill

See `MANIFEST.md` (auto-generated) for the per-skill table. Key non-obvious cases:

- **`syteline-rest-api-playbook`** (PARTIAL) — `CMP_SLItemPrices` listed as a teaching example of an extend-and-replace IDO. The skill text correctly says "may or may not exist." Not a defect; pilot doesn't have this particular extension. **Keep as-is.**
- **`syteline-extension-classes`** (was FAIL on `Products`, now no-claims) — `request.IDOName = "Products"` in C# code is an Infor sample placeholder, not a real Syteline IDO. Filtered as a known placeholder in the extractor. **No change needed in the skill** since the surrounding text is conceptual.
- **`syteline-rest-api-v2`** PASS only verified one IDO/method (`UserNames.GetUserAttributes`). The skill is mostly conceptual / Swagger-style spec content. **Adequate validation given the skill's nature.**

## Tier C handling (32 skills)

The non-REST skills (forms, scripts, AES, app-builder, dataviews, etc.) cannot be smoke-tested against the IDO REST API by definition. They remain valuable as **reference material** for agents working on form-side scripting, AES design, or other Mongoose framework topics. Recommendation:
- **Keep them in `skills/`.**
- **Do NOT surface them as MCP tools** in the upcoming Syteline MCP — exposing reference docs as tools adds noise.
- **Future work:** if/when we build form-extension automation or AES-driven workflows, those skills become validation targets for a different harness.

## Cross-cutting decision: Kade's PR vs Ivan's PR

Kade's PR #2 (already merged into `DirectorVector/main`) added a `skills/` folder to Brice's personal fork but is not on canonical. Ivan's PR adds a much larger `skills/` folder targeting canonical. The two skill sets:
- Don't share folder names — Kade's were focused, Ivan's is comprehensive.
- Will conflict on the README "Hermes skills section" if both eventually land on canonical.

**Recommendation:** Land Ivan's reconciled PR first (this branch), then re-mint Kade's PR against canonical, manually merging any overlap into the larger Hermes catalog. Keep Brice's `DirectorVector` fork in sync via merge/rebase rather than treating it as a parallel source of truth.

## What lands in this PR

The `validation-harness` branch contains:

```
validation/
├── README.md            ← how to run
├── pilot_client.py      ← read-only HTTP client
├── extract_claims.py    ← skill text → IDO/method/table claims
├── validate_skill.py    ← per-skill validator
├── validate_all.py      ← orchestrator (5-wide claude pool)
├── skill-inventory.md   ← Phase 1 snapshot of the PR
├── MANIFEST.md          ← auto-generated per-skill results
├── RECONCILIATION.md    ← this file
├── .env.example
└── .gitignore
docs/
├── 02_IDO_OVERVIEW.md   ← MethodType values fixed (Ivan's b68f309 was wrong)
├── 05_DISCOVERY_GUIDE.md ← MethodType values fixed
└── 06_GOTCHAS.md        ← _mst section deduped, casing claim softened
```

Everything Ivan added under `skills/` is preserved. Only his in-house doc tweaks were touched, and only to reflect what the pilot actually returns.
