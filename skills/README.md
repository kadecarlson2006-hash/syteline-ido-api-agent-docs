# Syteline Hermes Skills

This directory contains **51 Hermes Agent skills** covering the full breadth of official Infor Mongoose/Syteline documentation. Each skill is a directory containing a `SKILL.md` file (with YAML frontmatter) and optionally a `reference/content.txt` for larger content.

## Installation

To install these skills into your Hermes agent:

```bash
# Copy all skills to your Hermes skills directory
cp -r skills/syteline-* ~/.hermes/skills/syteline/
```

Or install individual skills:

```bash
cp -r skills/syteline-rest-api-v2 ~/.hermes/skills/syteline/
```

## Skill Categories

### IDO Integration Guide (5 skills)
| Skill | Description |
|---|---|
| `syteline-ido-request-interface` | IDO request interface overview and XML schema |
| `syteline-rest-api-v1` | REST API Version 1 endpoints (reference) |
| `syteline-rest-api-v2` | REST API Version 2 - generic and dynamic endpoints |
| `syteline-soap-api` | SOAP web service integration |
| `syteline-dotnet-client` | .NET client class library |

### IDO Development Guide (4 skills)
| Skill | Description |
|---|---|
| `syteline-ido-api` | .NET IDO API and class library |
| `syteline-custom-ido` | Custom IDO development |
| `syteline-extension-classes` | .NET IDO extension classes |
| `syteline-ido-samples` | IDO code samples |

### Core Extensibility Guide (10 skills)
| Skill | Description |
|---|---|
| `syteline-ido-definitions` | IDO definitions, projects, tables, properties, methods |
| `syteline-form-control` | Form customization and control |
| `syteline-aes-core` | Application Event System core concepts |
| `syteline-aes-design` | AES design patterns |
| `syteline-aes-samples` | AES code samples |
| `syteline-app-messages` | Application messages |
| `syteline-sql-schema` | SQL schema and database structure |
| `syteline-critical-numbers` | Critical number sequences |
| `syteline-dataviews` | Data view definitions |
| `syteline-form-extensions` | Form extensions (reference) |

### Form Development Guide (18 skills)
| Skill | Description |
|---|---|
| `syteline-overview` | Mongoose framework overview |
| `syteline-design-mode` | Form design mode |
| `syteline-customization` | Form customization |
| `syteline-forms` | Form types and structure (reference) |
| `syteline-collections` | Collection definitions (reference) |
| `syteline-events` | Event handling |
| `syteline-components` | UI components (reference) |
| `syteline-validation` | Data validation |
| `syteline-global-objects` | Global objects and utilities |
| `syteline-scripts` | Scripting (reference) |
| `syteline-variables` | Variables and scope |
| `syteline-testing` | Testing and debugging (reference) |
| `syteline-reference-actions` | Action reference (reference) |
| `syteline-reference-keywords` | Keyword reference (reference) |
| `syteline-standard-events` | Standard events reference (reference) |
| `syteline-api` | Client API reference (reference) |
| `syteline-aes` | Application Event System (reference) |
| `syteline-glossary` | Terminology glossary |

### App Builder / App Hub Guide (3 skills)
| Skill | Description |
|---|---|
| `syteline-app-builder` | App Builder overview |
| `syteline-app-builder-design` | App Builder design patterns |
| `syteline-app-hub-components` | App Hub components |

### Basics User Guide (4 skills)
| Skill | Description |
|---|---|
| `syteline-basics-navigation` | UI navigation |
| `syteline-basics-forms` | Form usage basics |
| `syteline-basics-tasks` | Common tasks |
| `syteline-basics-ui-reference` | UI reference |

### Administration Guide (7 skills)
| Skill | Description |
|---|---|
| `syteline-admin-architecture` | System architecture |
| `syteline-admin-licensing` | Licensing |
| `syteline-admin-processes` | Background processes |
| `syteline-admin-config` | Configuration management |
| `syteline-admin-reports` | Reports and utilities |
| `syteline-admin-replication` | Data replication (reference) |
| `syteline-admin-transport` | Data transport |

## Source Documentation

These skills were created from the following official Infor documentation (Release 2026.x unless noted):

1. **Mongoose Form Development Solution Help** — Form development, customization, scripting, events
2. **Mongoose IDO Development Guide** — IDO .NET API, extension classes, custom IDOs
3. **Mongoose Core Extensibility Guide** — IDO definitions, AES, SQL schema, form extensions
4. **Mongoose IDO Integration with External Applications Guide** — REST API, SOAP, .NET client
5. **Mongoose App Builder and App Hub User Guide** — App Builder, App Hub components
6. **Mongoose Basics User Guide** — Navigation, forms, common tasks
7. **CloudSuite Industrial Administration Guide** (CSI 10.x) — Architecture, licensing, replication

## Cross-Check Status

All skills have been cross-checked against the [docs/](../docs/) folder in this repo. The docs focus on REST API V2 practical usage; the skills cover the full official documentation breadth including SOAP, .NET, form development, AES, and administration.
