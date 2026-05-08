---
name: syteline-customization
description: "Customizing deployed Mongoose applications - editing permissions, scope, version control, themes"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

Open the form and then going into Design Mode.
Go into Design Mode and then selecting Form menu > Definition > Design.

About the Design Mode user interface
When you activate Design Mode, Windows client displays a set of controls for creating and editing
objects. You can dock the controls, minimize them, or use them as floating windows.
Design Mode controls
•
•
•
•

Setting form, component, and object properties on page 24
Object Viewer on page 23
Design Mode Toolbar on page 21
Toolbox on page 22

Infor Mongoose Application and Form Development | 20

Fundamentals
•

Tips about using Design Mode controls on page 23

About the Design Mode toolbar
The Design Mode toolbar provides tools that help you:
•
•
•
•
•
•
•
•

Align form components with respect to other components and to the design grid
Resize components uniformly
Adjust space between components uniformly
Arrange components vertically or horizontally
Bring components to the front or send them to the back
Set tab order
Undo and redo changes
Launch the wizards that facilitate the creation of SQL tables, IDOs, and forms

Most options on the toolbar require you to select more than one component. To select multiple
components, hold down CTRL, and then click each component.
Alignment options align selected components with whichever component has solid black squares for
handles. After selecting all components to be aligned, click the one you want the others to align with.
The handles of this component should be solid black, and all others should be black-outlined handles.
The procedure for selecting components to be resized or spaced uniformly is similar.
Icon tooltip

Description

New Form Wizard

Launches the New Form Wizard, which allows you to create forms, either
new or as extensions of existing forms.

New Data Maintenance
Wizard

Launches the New Data Maintenance Wizard, which allows you to create
SQL tables, IDOs, and forms in one integrated process. You can also
create subcollections at the same time.

XML to IDO Wizard

Launches the XML to IDO Wizard, which allows you to create SQL tables
and IDOs from data contained in XML documents.

Copy Form Definition

Opens the Copy Form Definition dialog box, which allows you to copy
an existing form definition into a new form.

Delete Form Definition

Launches the Select Form dialog box, which allows you to select a form
definition for deletion.

Align Left

Aligns all selected components so their left edges line up.

Align Center

Aligns all selected components so their horizontal centers line up.

Align Right

Aligns all selected components so their right edges line up.

Align Top

Aligns all selected components so their top edges line up.

Align Middle

Aligns all selected components so their vertical middles line up.

Align Bottom

Aligns all selected components so their bottom edges line up.

Infor Mongoose Application and Form Development | 21

Fundamentals

Icon tooltip

Description

Make Same Width

Resizes all selected components so they are the same width.

Make Same Height

Resizes all selected components so they are the same height.

Make Same Size

Resizes all selected components so they are the same size.

Space Horizontally

Moves selected components so they are evenly spaced within the horizontal
boundaries. This option uses the left edge of the leftmost component and
the right edge of the rightmost component as the horizontal boundaries. It
moves only the inner components, leaving the outer two components where
they are.
This option works only when three or more components are selected.

Space Vertically

Moves selected components so they are evenly spaced within the vertical
boundaries. This option uses the top edge of the topmost component and
the bottom edge of the bottommost component as the vertical boundaries.
It moves only the inner components, leaving the outer two components
where they are.
This option works only when three or more components are selected.

Arrange in a Column

Arranges all selected components, stacking them vertically.

Arrange in a Row

Arranges all selected components in a horizontal row.

Bring to Front

Brings the selected components to the front, effectively placing them on
top of other, overlapping components. This ensures that the selected
components can be seen.

Send to Back

Sends the selected components to the back, effectively placing them under
other, overlapping components. This means that all or part of the selected
components may be hidden.

Tab Order

Allows you to set the tab order for the components on the form. This option
resets all components, starting with number 0.

Tab Order At

Allows you to set the tab order for components starting from a specific
component. This option resets only the components starting with the first
one you select. The order of all components to that point does not change.

Undo

Undoes the previous design action.
To undo multiple actions, click this button repeatedly.

Redo

Repeats a design action that has been undone.
To redo multiple actions, click this button repeatedly.

About the Design Mode Toolbox
Use the Toolbox to select a component type on page 177 to add to a form.

Infor Mongoose Application and Form Development | 22

Fundamentals
In Design Mode, the Toolbox is docked by default on the left side of the WinStudio main workspace
area.
If the Toolbox happens to be closed for some reason, you can open the Toolbox using the View menu,
Toolbox option.

Using the Object Viewer
The Object Viewer allows you to select an object in a graphical tree and open its properties for editing.
The tree represents forms that are open in Design Mode and all global objects in an application.
By default, Object Viewer is docked to the left side of the Windows client window. When you select an
object, its properties are displayed in the Form, Component, or Object property sheet or in the script
editor. Property sheets are docked to the right side of the window by default.
You can open objects for editing by clicking the object's name in the tree. This activates the Form,
Component, or Object property sheet for the selected object and, if applicable, selects the object in
the workspace pane. Use these property sheets to edit the attributes of the object.
The Forms node is the top-level node representing forms open in Design Mode. Expand this node to
access individual forms. Under a form name are collections, components, event handlers, and variables
related to the form. Expand the nodes to access objects in a form. You can similarly access global
objects.
You can also use the Find and Filter options to locate objects by name.

Tips about using Design Mode controls
You can adjust the view, size, and position of the various control windows used in Design Mode.
The behavior described here applies to the Form, Component, and Object property sheets as well as
to the Toolbox and Object Viewer.
Overlapping control windows
When in view, the Toolbox and Object Viewer share the same space on the left side of the main
workspace area. Only one of them can be viewed at a time.
Likewise, all three property sheets, Form, Component, and Object share the same space on the right
side.
The method of selecting the control window to view depends on whether the control window that they
share is pinned or unpinned. For details, see the section on pinning/unpinning controls.

Infor Mongoose Application and Form Development | 23

Fundamentals
Docking/Undocking control windows
A docked control window is one that is fixed in a particular location within the main workspace. An
undocked, or "floating" control window is one that is unattached and can be moved freely within the
main workspace.
To dock or undock a control window, double-click in the title bar for that control window.
When control windows are docked, the Object Viewer and Toolbox are located on the left side of the
WinStudio main workspace area. The Form, Component, and Object property sheets are located on
the right side.
When control windows are undocked ("floating"), you can:
•
•
•

Move the control windows anywhere inside the WinStudio main workspace. This includes placing
control windows behind other windows.
Change their size.
Minimize them. When minimized, control windows display a shortened title bar at the bottom of
the workspace, much like regular minimized windows.

Pinning/Unpinning controls
When a control is pinned, that control is always in display mode within a fixed space in the main
workspace. When a control is unpinned, the control window slides in and out of view, depending on
where the focus is. Control windows can only be pinned or unpinned when docked (see previous
section).
To pin or unpin control windows, click the push-pin icon.

Setting form, component, and object properties
In Design Mode, Form, C omponent, and Object property sheets display by default. The sheets can
be minimized or changed to floating windows.
To set properties:
1

In Design Mode, select the form, component, or object for which you want to set a property.
• To select a form, perform one of these actions:
• Click in the form background outside any component.
• In the Object Viewer on page 23, double-click Forms, and then double-click the form name.
• On the Edit menu, select Form Properties.
• To select a component, perform one of these actions:
• Click the component in the form.
• In the Object Viewer, double-click Forms, and then double-click Components. In the
components list, click the component name.
• In the Component property sheet, select the component name in the combo box below
the title bar of the property sheet.
• To select a global object, perform one of these actions:

Infor Mongoose Application and Form Development | 24

Fundamentals
•
•
2

In the Object Viewer, double-click a global object type (such as Component Classes), and
then click the object name.
On the Edit menu, click a global object type. Select the name of a global object, and then
click Edit.

Click the property for which you want to set the value, and perform one of these actions:
• In the property box, type a value.
• If the property box contains an down pointer, click the pointer and then select a value in the
list.
• If the property box contains an ellipsis (...) button, click it to display a detailed dialog box or
property sheet.

Setting preferences for working in Design Mode
You can set preferences for the display of form and component properties in property sheets, display
of hidden components, snap-to-grid settings, and other features.
To set Design Mode preferences:
1
2
3

From the View menu, select Settings.
Click Design Time.
Set your preferences.

For information about preferences, see the User Preferences help.

About the IDO metadata cache
During user sessions, Mongoose uses cache files containing the metadata for forms and IDOs in XML
files. This metadata can persist between sessions to allow users to use the cached metadata and open
forms more quickly.
However, during IDO and application development activities, developers usually want to be able to use
the most current metadata for forms and IDOs. For this reason, typically, developers must frequently
refresh the metadata cache by dumping the older metadata and replacing it with current metadata.
By default, when the metadata for global form objects is unloaded (using the Unload All Global Form
Objects item from the Form menu > Definition submenu), the IDO metadata does not get unloaded,
unless you have set an option to unload it any time the global form objects metadata is unloaded.
For form and application development purposes, we recommend that you clear the IDO metadata
cache frequently, to ensure that you are using the current, edited metadata. Windows client can be set
to clear the cache each time you use the Unload All Global Form Objects command.
See Setting the option to clear the IDO metadata cache on page 26.

Infor Mongoose Application and Form Development | 25

Fundamentals

Setting the option to clear the IDO metadata cache
For form and application development purposes, we recommend that you clear the IDO metadata
cache frequently, to ensure that you are using the current, edited metadata. Windows client can be set
to clear the cache each time you use the Unload All Global Form Objects command.
To set the option to clear IDO metadata when you unload global form objects:
1
2
3

From the View menu, select User Preferences.
Select the Run Time tab.
Select Unload IDO metadata along with global objects.

Now, whenever you unload global form objects, the IDO metadata cache is also cleared.
Note: With the IDO Runtime Development Server, you can select Discard IDO Metadata Cache on
the Configuration menu to clear IDO metadata without clearing global form objects.
Note also that this option does not affect the option to clear the IDO metadata cache for servers in a
server farm. For that option, see Refreshing the IDO metadata cache for a server farm on page 26.

Refreshing the IDO metadata cache for a local server
For form and application development purposes, we recommend that you clear the IDO metadata
cache frequently, to ensure that you are using the current, edited metadata.
To clear the IDO metadata cache for a local server/development configuration:
1

Verify that you have the option set to clear the IDO metadata cache whenever you unload all global
form objects.
See Setting the option to clear the IDO metadata cache on page 26.

2
3

From the Form menu, select Definition.
From the Definition submenu, select Unload All Global Form Objects.

The system unloads all IDO metadata previously in use for the current configuration and replaces it
with current metadata.
Note: The keyboard shortcut for this operation is Ctrl+U.
With the IDO Runtime Development Server, you can select Discard IDO Metadata Cache on the
Configuration menu to clear IDO metadata without clearing global form objects.
Note also that this option does not affect the option to clear the IDO metadata cache for servers in a
server farm. For that option, see Refreshing the IDO metadata cache for a server farm on page 26.

Refreshing the IDO metadata cache for a server farm
When developing applications in a multi-tenant (cloud) environment, it is as important to periodically
refresh the IDO metadata cache as it is in an on-premises environment. The procedure to do this,
however, is different.
To refresh the IDO metadata cache for all servers in a server farm:

Infor Mongoose Application and Form Development | 26

Fundamentals
1

Verify that you have the server farm set up with the Land Lord/TMS (Tenant Management Service)
and a site configuration.
The configuration must be set up using the Configuration Manager, Configurations tab.

2
3

Open the Refresh Farm Metadata form.
Click Refresh Farm IDO Metadata.
Optionally, select the Refresh shared Extension Class Assemblies option to unload and restart
the app domain and reload the shared custom assemblies.
Note: The Refresh shared Extension Class Assemblies is an expensive operation.

The system contacts each server in the farm and proceeds to unload and replace the IDO metadata
for the configuration.

Designing for accessibility
Web accessibility is a practice of breaking down barriers that prevent people with disabilities from
