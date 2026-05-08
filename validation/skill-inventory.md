# Skill Inventory — Hermes Crosscheck PR

**Source:** `SalinaVortexCorporation/syteline-ido-api-agent-docs` PR #1 — branch `ivanvelocastaneda:hermes-crosscheck`
**Snapshot date:** 2026-05-08
**Total skills:** 54 folders under `skills/`
**Total insertions:** ~143,645 lines across 74 files
**Author:** Ivan Velo (SL10 migration team)
**Project codename:** "Hermes" (per `metadata.hermes.tags` in skill frontmatter)

---

## Commits in PR

| SHA | Subject | Scope |
|---|---|---|
| `b68f309` | docs: cross-check improvements from official Infor documentation | 6 in-house docs, +77/-13 |
| `57200c3` | skills: add 51 Hermes Agent skills from official Infor documentation | bulk skill add |
| `9906334` | readme: add Hermes skills section with link to skills catalog | README |
| `0ca3bde` | skills: add syteline-rest-api-playbook from repo docs cross-check | one in-house-aligned skill |

---

## Skill format

Standard Claude Code layout:

```
skills/<name>/
  SKILL.md            ← frontmatter + body
  reference/          ← optional, large content offloaded
    content.txt
  references/         ← optional, narrower refs (e.g. form-extension-and-replacement.md)
```

Frontmatter shape (typical):

```yaml
---
name: "syteline-foo"
description: "..."
version: "1.0"
tags: [erp, syteline, infor, mongoose, ...]
category: syteline           # optional
metadata:                    # optional
  hermes:
    tags: [...]              # Hermes project label
---
```

## Two content patterns

| Pattern | Example | Approx. count |
|---|---|---|
| **Inline** — full content in SKILL.md | `syteline-rest-api-v2` (2,785 lines) | ~38 |
| **Reference-deferred** — thin SKILL.md + `reference/content.txt` | `syteline-form-extensions` (16 + 16,223 lines) | ~16 |

## Voice classification

- **In-house aligned** (practitioner voice, matches `docs/01–06`): only `syteline-rest-api-playbook`. Opens with *"Battle-tested practical guide ... covers the 'how' that the official documentation leaves out."*
- **Infor-source verbatim**: every other skill — opens with *"Infor Mongoose IDO Development Guide | 6"* or *"Source: Infor Mongoose ... Guide, Release 2026.x"* and is largely a doc dump.

---

## Validation classification

Three tiers based on whether the skill makes claims testable via the IDO REST API:

| Tier | Count | Strategy |
|---|---|---|
| **A. REST-API-validatable** | 6 | Full harness — meta-IDO existence checks, signature verification, sampled curl probes against pilot |
| **B. Partially validatable** | 16 | Harness — verify any concrete IDO/table/method/property names mentioned, even when surrounding skill is conceptual |
| **C. Non-REST, out-of-scope** | 32 | Skip harness validation. Document as reference material — kept in repo as background context for future agents but not surfaced as MCP tools. |

### Tier A: REST-API-validatable (6)

| Skill | SKILL lines | Ref lines | Notes |
|---|---|---|---|
| `syteline-rest-api-playbook` | 489 | 0 | **In-house aligned.** BGTask submission, IDO discovery patterns, real curl templates. Highest expected PASS rate. |
| `syteline-rest-api-v1` | 17 | 5,340 | V1 REST API spec. Verify endpoint shape against pilot (V1 may or may not be enabled). |
| `syteline-rest-api-v2` | 2,785 | 0 | V2 REST API spec — what our `$SYTELINE_BASE_URL` targets. Verify endpoint shape end-to-end. |
| `syteline-ido-request-interface` | 1,591 | 0 | OpenSession/LoadCollection/UpdateCollection/Invoke/CloseSession schema. Verify against actual responses. |
| `syteline-ido-samples` | 117 | 0 | Concrete code samples. Map any IDO references to pilot. |
| `syteline-soap-api` | 709 | 0 | SOAP — out of REST scope, but extract shared IDO names for cross-reference. |

### Tier B: Partially validatable (16)

| Skill | SKILL lines | Ref lines | Concrete claims to verify |
|---|---|---|---|
| `syteline-ido-api` | 959 | 0 | .NET API patterns; verify any IDO names mentioned exist on pilot |
| `syteline-ido-definitions` | 1,638 | 0 | Verify IDO meta (`AccessAs` etc.) against `IdoCollections` |
| `syteline-collections` | 2,468 | 0 | Verify subcollection patterns (`IdoProperties.PropertyClass=3`) |
| `syteline-custom-ido` | 46 | 0 | Verify CMP_-prefix convention against `IdoCollections` |
| `syteline-extension-classes` | 1,484 | 0 | Verify extension-class methods exist (`IdoMethods.MethodType=3`) |
| `syteline-sql-schema` | 1,057 | 0 | Verify table/column claims against `SqlColumns` / `SqlTables` |
| `syteline-critical-numbers` | 1,969 | 0 | Verify drill-down IDOs exist |
| `syteline-dataviews` | 1,838 | 0 | Verify DataView meta-IDOs |
| `syteline-admin-architecture` | 1,040 | 0 | Verify any specific IDO references |
| `syteline-admin-config` | 1,121 | 0 | Verify TimeZones/languages IDOs |
| `syteline-admin-licensing` | 1,384 | 0 | Verify license-module IDOs |
| `syteline-admin-processes` | 2,972 | 0 | **High-value** — TaskMan + `BGTaskDefinitions` claims |
| `syteline-admin-replication` | 18 | 6,253 | Verify replication IDOs |
| `syteline-admin-reports` | 647 | 0 | Verify report IDOs |
| `syteline-admin-transport` | 1,654 | 0 | App Metadata Transport / ION integration |
| `syteline-customization` | 405 | 0 | Mostly conceptual; verify any IDO refs |

### Tier C: Non-REST, out-of-scope for harness (32)

```
syteline-aes                  syteline-aes-core           syteline-aes-design
syteline-aes-samples          syteline-api                syteline-app-builder
syteline-app-builder-design   syteline-app-hub-components syteline-app-messages
syteline-basics-forms         syteline-basics-navigation  syteline-basics-tasks
syteline-basics-ui-reference  syteline-components         syteline-design-mode
syteline-dotnet-client        syteline-events             syteline-form-control
syteline-form-extensions      syteline-forms              syteline-global-objects
syteline-glossary             syteline-overview           syteline-reference-actions
syteline-reference-keywords   syteline-scripts            syteline-standard-events
syteline-testing              syteline-validation         syteline-variables
```

These skills cover form-side scripting, AES, app-builder UI, design mode, .NET client APIs, glossary/reference. They are valuable as agent reference material for future work outside the IDO REST API surface but cannot be smoke-tested via meta-IDOs.

---

## In-house doc changes assessment (commit `b68f309`)

Ivan's tweaks are **surgical additions**, not rewrites — total +77/-13 across 6 files:

| File | Δ | What he added | Verdict |
|---|---|---|---|
| `01_AUTHENTICATION.md` | +12 | REST API V2 version note, OAuth/ION API alt auth | **Keep** — factual; OAuth path is optional and useful to document |
| `02_IDO_OVERVIEW.md` | +2 | MethodType values (0=System, 2=SP, 3=ExtClass) | **Keep** — verifiable fact |
| `03_CORE_OPERATIONS.md` | +18 | Additional V2 endpoints (AES, file ops, property info, configs) | **Keep, verify each endpoint exists on pilot** |
| `05_DISCOVERY_GUIDE.md` | +28/-11 | Queryable property list, field-name gotchas (`PropertyName` not `PropName`, `ColumnName` not `BoundColumn`), `MethodType`, `PropertyClass` detail | **Keep, verify the field-name gotchas against actual meta-IDO responses on pilot** |
| `06_GOTCHAS.md` | +28 | `_mst` table suffix convention, IDO name casing sensitivity | **Possibly redundant** — `_mst` is already in our existing `06_GOTCHAS`. Dedupe in Phase 3. |
| `README.md` | +2/-1 | V2 base URL clarification | **Keep** |

Net assessment: **doc changes are net-positive.** The original concern ("he used Infor docs instead of ours") applies almost entirely to the *skills*, not the doc tweaks. Reconcile in Phase 3 by verifying each new fact against the pilot and deduping the `_mst` mention.

---

## Cross-cutting issue: Kade's PR is on DirectorVector but not canonical

Kade's PR #2 (already merged into `DirectorVector/main`) added a `skills/` directory to Brice's personal fork. **It's not on `SalinaVortexCorporation/main` yet.** Ivan's PR is targeted at canonical, so the two skill bodies don't overlap on this base — but if both eventually merge upstream, conflicts are likely.

**Decisions to defer until Phase 3:**
1. Merge Kade's PR into canonical first (keeps history clean)?
2. Or rebase Ivan's PR on top after Kade lands?
3. Or treat them as simultaneous and resolve at merge?

Note for now; resolve when the validation results are in hand.
