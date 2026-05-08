# Form Extension and Replacement in Syteline

Quick-reference for extending (and optionally replacing) existing Syteline/Infor Mongoose forms. Covers both web client (Web Designer) and Windows client (Design Mode).

## Core Concepts

| Concept | Description |
|---------|-------------|
| **Extension** | Create a new form that is a copy of an existing base form. Both forms exist independently. The original still loads normally. |
| **Replacement ("Replace Base Form")** | The extended form loads **in place of** the original whenever any code, menu, or user requests the base form. This is how you override/replace standard Syteline forms. |
| **Form Type Inheritance** | The extended form is always the same type as the base form (MultiView, Query, Grid Only, Detail Only, Report, Form Only, Tile, Build from Scratch, Flex). |

## When to Use Which

- **Extension only**: You want a variant form alongside the original (e.g., a specialized entry form for power users).
- **Extension + Replace**: Your customization should affect ALL users who open the original form (e.g., adding a required field, changing validation logic, hiding/showing components).
- **Never modify vendor forms directly** — always extend. This preserves your changes during upgrades/Form Sync.

---

## Method 1: Web Designer (Web Client / Cloud)

### Procedure

1. In the web client, launch the **New Form Wizard**.
2. In the Web Designer menu bar, click the **New Form Definition icon** (document with plus sign).
3. On the **Form Wizard Selection page**, select **Extend Form**.
4. Click **OK**.
5. On the next page, specify:
   - **Name**: The name for your new (extended) form
   - **Base Form**: The existing form you want to extend (select from dropdown)
   - **Description**: Optional (auto-generated from base form by default)
6. **To REPLACE the base form** (override for all users): Check the option  
   *"Set the new form to load when a request to open the base form is received"*
7. Click **Next**, then **Finish**.

### Result
- Web Designer creates a copy of the base form with your new name
- Opens in Design Mode for further customization
- If "Replace Base Form" was checked, your form loads instead of the original

### Note
Unlike other form creation options, **extension does not allow saving a template**.

---

## Method 2: Design Mode (Windows Client / On-Premises)

### Procedure

1. Go into **Design Mode** in Windows client.
2. Launch the **New Form Wizard** via **Form menu > Definition > New**.
3. On the first page of the wizard, select **"Extend existing form"** (NOT "Create new form").
4. Click **Next**.
5. On the next page, specify:
   - **Name**: Your new form name (must be unique)
   - **Base Form**: Select the form to extend from the drop-down list
6. Continue through wizard pages (properties, layout, etc.) — these will be pre-populated from the base form.
7. Click **Finish**.

### Result
- Windows client creates the form and displays a rough-draft version in Design Mode
- New form inherits all components, collections, event handlers, and scripts from the base form
- To make it replace the base form, set the replacement property after creation (see below)

---

## What You Can Do After Extending

Once the extended form is open in Design Mode/Web Designer:

| Action | How |
|--------|-----|
| **Add components** | Drag from Toolbox onto the form (fields, buttons, grids, tabs, etc.) |
| **Modify components** | Change properties: size, position, visibility, colors, component classes |
| **Add/modify events** | Add new event handlers, extend existing handler behavior, add validation |
| **Add scripts** | Create Script objects for custom logic, call external methods/APIs |
| **Modify collections** | Add secondary collections, modify bindings, add subcollections |

---

## Setting "Replace Base Form" (Windows Client)

In the Windows client wizard, the replace option may not be presented inline during creation. To set it afterward:

1. Open the extended form in Design Mode
2. In the **Form Properties** sheet, look for the **Base Form** or **Replace Base Form** property
3. Configure the replacement mapping so requests for the original form resolve to your extended form

Alternatively, use the **Copy Form Definition** approach from the Design Mode toolbar if you need more control over the relationship.

---

## Best Practices

1. **Always extend, never edit vendor forms directly** — protects customizations during upgrades.
2. **Use naming conventions** for extended forms (e.g., company prefix like `Ext_` or `Cust_`) so they're easily identifiable.
3. **Set permissions** on extended forms via User Permissions tab to control access.
4. **Test thoroughly** — extended forms inherit ALL base form behavior; changes to the base can affect your extension.
5. **Refresh IDO metadata cache frequently** during development:  
   `Form > Definition > Unload All Global Form Objects` (**Ctrl+U**)  
   Enable: `View > User Preferences > Run Time tab > Unload IDO metadata along with global objects`.
6. **Use Form Sync during upgrades** — preserves customizations while updating vendor forms; review public folders after sync.
7. **Required permissions**: You need Site Developer or Vendor editing level to create extended forms.

---

## Related Skills

- `syteline-customization` — Editing permissions, scope, version control, Design Mode UI
- `syteline-form-extensions` — Form Extension Class concepts (separate from form definition extension)
- `syteline-events` — Event handlers you'll want to add/modify on extended forms
- `syteline-components` — Working with form components (adding/modifying on extended forms)
