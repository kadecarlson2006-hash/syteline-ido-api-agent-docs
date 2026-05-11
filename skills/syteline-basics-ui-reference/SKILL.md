---
name: "syteline-basics-ui-reference"
description: "UI reference - themes (applying in Windows/web client), application clients, diagnostics, keyboard shortcuts, menus (Form/Actions/Edit/View/Help), toolbars"
version: "1.0"
tags:
  - syteline
  - basics
  - ui
  - shortcuts
  - menus
  - toolbars
  - mongoose
---

# syteline-basics-ui-reference

Source: Infor Mongoose Basics User Guide, Release 2026.x

the server:
•

For a shared file server path, use this format:
\\server\share\

Infor Mongoose Basics User Guide | 109

Working with Document (File) Attachments
You can also use this format to specify a single file; for example:
\\server\share\filename.ext
•

For an FTP/SFTP URI, use this format: (S)FTP_Server/folder/
This field can include record-specific variables and property values. For example, a UNC path of
\\MGserver1\MyShare\CustomerDocuments\P(CustNum)\* returns all of the files found in a folder that
matches the currently selected customer number on the parent form.
Note: Any part of the specified path or filename can consist of variable or property value
replacements, as in this example.

Use Server Path As Root Path
When this option is selected, the system uses the server root path along with the File Specification.
Otherwise, you must specify the full path in the File Specification field.
For example, suppose the Server Root Path is \\Server1\RootPath, and the File Specification is
\folderxxx\. If this option is selected, then the system uses the path \\Server1\RootPath\folderxxx\. If
this option is cleared, then the system attempts to use \folderxxx\ as the path.
Auto Populate
When this option is selected, files are populated automatically on the Attached Documents form, and
users cannot select this document type from the drop-down list on the Attached Documents form.
Population Depths
From the drop-down list, specify the number of levels the system can use when storing and retrieving
documents.
If you select 0 or 1, all files of the specified folder are included. If you select 2, all files of the specified
folder and its first-level subfolders are included. If you select 3, all files of the specified folder and two
levels of its subfolders are included.
3

If you specified a Storage Method of Link, you can optionally specify the forms and/or properties on
forms to which the linked document types are to be attached:
•
To make all files of this document type available only for certain forms, regardless of which properties
appear there, specify each Form Name in the grid, using a separate row for each form. Then specify
a single asterisk (*) in the Property Name field for each form. You must use the actual form name,
and not its caption.
•
To make all files of this document type available for all forms that contain certain properties, specify
each Property Name in the grid, using a separate row for each property. Then, for each property,
specify a single asterisk (*) in the Form Name field. You must use the actual property name, and not
its caption or label.
•
To make all files of this document type available only on the specified form and then only if it contains
the specified property, specify both the Form Name and the Property Name, using a separate row
for each combination.
•
To make all files of this document type available for all forms and all properties, specify single asterisks
in both the Form Name and Property Name fields.
Note: This combination overrides all other Form Name-Property Name combinations.

4

Save your changes.

Infor Mongoose Basics User Guide | 110

Working with Document (File) Attachments

Using the Application Event System for document (file)
attachments
The Application Event System (AES) provides two basic means to work with document attachments. You can
perform these tasks:
•
•

Include documents attached to records when initiating those records for processing
Attach, link, or detach documents to records during the course of processing

Including documents attached to records
If you plan to use application events and event handlers with collections and records that can already have
document attachments, and you want to include those attached documents in whatever action is taking
place through the event handling, you can use the Attach event action parameter in conjunction with a Notify,
Prompt, or Send Email event action. This option allows you to include all documents, only internal documents,
all document other than internal documents, or specified individual documents from the record being
processed. You can also exclude all document attachments from an event handler action.
With the AES, you can update or detach documents that are already attached to records and included in such
actions. For more information about how to use the fields, see the help for the Event Action Attach form.
Processing Document Attachments
You can use the AES to attach, link, update, or detach documents during the processing of an application
event handler. For these types of actions, use the Attach event action type as part of the handler sequence.
Attaching documents from a file server is supported in the AES.

Attaching Documents (Files) to Forms, Properties, or
Records
Documents can be "attached" to forms, to particular properties on forms, or to specific records in a collection
on a form.
Note: The term "document" in this context can be any kind of file, not just a text document. The file could
be a spreadsheet, a graphics file, or any type of file that you need to attach to an object in the application.
When attaching documents, you have two options. You can:
•
•

Attach new documents
Attach existing documents, that is, documents that have been attached already to other forms, properties,
or records.

Infor Mongoose Basics User Guide | 111

Themes

Chapter 14: Themes

About themes
At their most basic level, themes affect the look and feel of the application. You can create your own themes
or use one of the two system themes that we provide.
Note: There are some differences in the way themes are handled in the web client. The web client recognizes
theme settings related to the explorer, foreground colors, background colors, tabbed layout, and required
field indicators. The web client does not recognize settings pertaining to the group boxes or notebook tabs.
In the Infor OS Portal, Mongoose adapts to the Infor OS Portal theme that is mapped using theTheme Color
Mapping form.
You can change the look of most components. The changes that are available for each component are different.
To determine what can be changed, see the Global Objects - Themes in the web client. You can also determine
the explorer style, the group box style, the notebook style, the toolbar grouping, and whether there is a
required field indicator.
System themes
The application ships with these themes:
•
•
•
•
•

Infor: Notable aspects include five menu choices in the menu bar and required fields that are identified
with a red asterisk. This theme is the default theme.
Classic: This theme provides the look and feel of legacy versions of the application. Required fields have
a yellow background.
Light: This theme is the re-branded Infor theme that provides a lighter look and feel for the user interface.
Dark: This theme provides a darker look and feel for the user interface. This theme is a combination of
the colors black for the background and white for the buttons, menus, texts, and labels.
High Contrast: This theme uses a darker shade of black and a brighter shade of white to make the user
interface easier to use in low-light environment and for users with photo sensitivity or contrast issues.

You can edit these themes, but you cannot delete them.

Infor Mongoose Basics User Guide | 112

Themes

Applying a theme
Applying a theme in the Windows client
Use themes to change the look and feel of your application environment.
1 Select View > User Preferences.
2 Click the Runtime Layout tab.
3 Specify this information:
Theme
Select a theme from the list.
Aside from the themes that are provided with the application, user-created themes, if available, also
displays on the list.
Tabbed Form Layout
This option is selected by default. When selected, forms open in a series of docked tabbed items. The
name of open form displays on each tab.
When cleared, forms open as undocked floating items, also known as Multiple Document Interface (MDI)
layout.
4

Optionally, to open the Theme editor and edit the selected theme, click the Edit button.
Note: This button is disabled for Vendor Developer users. To edit themes while logged in as a Vendor
Developer, use the Theme editor in Design Mode.

5

Click OK.

Applying a theme in the web client
Use themes to change the look and feel of your application environment.
1 Select View > User Preferences.
2 Click the Layout tab.
3 Specify this information:
Theme
Select a theme from the list.
Aside from the themes that are provided with the application, user-created themes, if available, also
displays on the list.
Update Existing View
This option is selected by default. When selected, the theme is applied immediately. When cleared, you
must refresh the browser to apply theme changes.

Infor Mongoose Basics User Guide | 113

Themes
Tabbed Form Layout
This option is selected by default. When selected, forms are shown in tabbed form layout.
When cleared, forms open as undocked floating items, also known as Multiple Document Interface (MDI)
layout.
Display Status Bar
To display the status bar as a footer in the web browser, select this option.
4

Click OK.

Infor Mongoose Basics User Guide | 114

About Application Clients

Chapter 15: About Application Clients

Forms are accessed by means of a client. Users can access forms in the Mongoose-based application using
any of these clients:
•
•
•

A Windows client includes Design Mode, runs in a Windows environment using Internet Explorer, and is
installed with click-once. This client can be used with Infor OS Portal®.
The web client is an HTML5 client that includes Design Mode, can run in all common browsers on all
popular devices and can be used with Infor OS Portal.
A lightweight web client is a specialized version of the web client that is optimized to run on certain
mobile devices.

Web clients must be accessed by means of specially formatted URLs.
These topics covers the capabilities and limitations of the web client clients:
•
•

Diagnostics Support in Web Clients on page 116
Limitations of the Lightweight Client on page 116

Web Clients and User Controls
Earlier versions of user controls - that is, user controls designed primarily to work within the Windows client
(smart client) environment - generally do not work in web clients. You can, however, update your user controls
to take advantage of the web user control functionality now available.
Alternatively, you can make a User Control component work in both the smart client and the web clients by
retaining the Windows client assembly and also specifying a web assembly and URL. The web assembly
contains a non-Windows client class derived from our WSUserControlBase class, or by implementing the
interface. This implements the server-side communications between the Windows client Runtime Service
and your code.
You can write a web page which communicates with our web browser infrastructure using a javascript API
that includes the ability to post requests back to the Windows client Runtime Service in the browser.
The web assembly gets deployed on the web-rendering web server. The web content can then be deployed
wherever you want.

Infor Mongoose Basics User Guide | 115

About Application Clients

Diagnostics Support in Web Clients
If you need to use diagnostics capabilities on the web server, use this procedure in the web client:
1 On the web server for the web client, edit the user preferences XML file for the user to be granted
diagnostics privileges.
These files are located in the C:\ProgramData\Mongoose folder. ProgramData is a hidden folder, so you
will need to unhide it to see the folder and its contents. Each user preference file uses this naming format:
appNamePrefsuserName
where:
•
•
2
3
4

appName is the name of your Mongoose-based application.
userName is the user ID of the user being given diagnostics privileges.

Find the section in the XML file for "Diagnostics".
Enable the settings you want by changing the value of the elements from 1 to 0.
Run the Log Monitor:
•
For a Windows Server 2008 machine, select Start > All Programs > Infor > Tools > Log Monitor.
•
For a Windows Server 2012 machine, find Log Monitor under Apps.

Limitations of the Lightweight Client
The lightweight version of the web client is a specialized version designed specifically to work on mobile
devices. Because of this, it has been optimized to load and render Mongoose forms more quickly. And, because
it is a version of the web client, it has all the same limitations as the standard version web client.
In addition, some component types have been modified to make them more "mobile friendly." Be aware that
the lightweight web client:
•
•
•
•

•
•
•
•
•

Cannot display Fusion charts, gauges, or calendars.
Removes some of the "SoHo" styling; option buttons and check boxes, for example, use browser
component styling.
Uses combo boxes that, by default, do not display the qwerty keyboard when clicking/tapping in the
drop-down field. Combo boxes are sized to the device viewport and the form size to make scrolling easy.
Displays message boxes that are larger, adjust to the viewport width, and remove extra borders. If the
message box contains Yes, No, Cancel, or OK responses, users can respond by pressing y , n , c , or o ,
respectively, on the qwerty keyboard. (Optional when using JS edit)
Properly sizes forms to the size of the device on which they are displayed.
In general, display text and buttons larger than in other client displays.
Treats the Enter key in a fashion similar to the Tab key on other client displays, cycling through non-grid
and non-read-only components. (Optional when using JS edit)
Treats the up arrow key in a fashion similar to Shift-Tab on other client displays, cycling through non-grid
and non-read-only components in reverse order. (Optional when using JS edit)
Selects all text when clicking/tapping or tabbing into a field.

Infor Mongoose Basics User Guide | 116

About Application Clients
•

Can trigger events on appropriate key presses. When this is the case, Mongoose captures not only the
key that was pressed/tapped, but also the field in which the key press/tap took place. (Optional when
using JS edit)

Infor Mongoose Basics User Guide | 117

User Interface Reference

Chapter 16: User Interface Reference

In the Reference section of the help, you can find quick references for many of the common features and
functions of the user interface:
•
•
•

Keyboard shortcuts are arranged by functions and by key combinations
Menus and menu options describe equivalent keyboard shortcuts and toolbar options
Toolbars and toolbar options describe equivalent keyboard shortcuts and menu options

Note: Administrator settings, including themes, can affect the display of all menus and toolbars.

Keyboard Shortcuts
A variety of keyboard shortcuts are available:
•

Form shortcuts are designed to help you at the form level, with actions such as opening and saving forms.
See Form Shortcuts.

•

Editing shortcuts are designed to help with editing actions and functions.
See Editing Shortcuts.

•

Record/Collection shortcuts are designed to help you work with data in records.
See Record/Collection Shortcuts.

•

Navigation shortcuts are designed to help you navigate on and between forms.
See Navigation Shortcuts.

Menus
Most of the tasks you need to accomplish can be done by using one or more of the menus. The placement of
these menus depends on what theme you are using (see About Themes on page 112).
These topics provide information about each of the standard menus, in table format, to help you more quickly
locate and use the option you need:

Infor Mongoose Basics User Guide | 118

User Interface Reference
•
•
•
•
•
•

The Form menu includes options for working with forms, including opening, closing, creating, editing,
and saving forms.
The Actions menu provides options that deal mostly with records and collections of records.
The Edit menu provides options for editing content in forms as well as for editing the forms themselves.
The View menu includes a number of options for showing or hiding various elements of the user interface.
The Window menu includes options for working with windows when you have multiple forms open.
The Help menu provides a number of options for accessing the online help, as well as for getting
information about the system software and any form you might have open.

Form menus
The Form menus include options for working with forms, including opening, closing, editing, and saving
forms. Some options are available only if you have a form open.
Form menus are used on these interfaces:
•
•

The web client
The Web Designer

Form menu for the web client
This table lists and describes the Form menu options for the web client.
Note: Many of these options are not available in Design Mode of the Windows client.
Menu option

Action

Web client keyboard shortcut

Open

Opens the Select Form dialog box, which you can use to locate
and select the form you want to open.

Ctrl + O

See these topics:
•
•
Export to File

Finding and Opening Forms Using the Select Form Dialog
Box on page 31
Finding and Opening Forms Using the Explorer on page 30

In the web client, opens Export to File dialog box.

n/a

Use this option to export data from a collection to an external
file that can be used in another program, such as a spreadsheet.
See Exporting Collections to External Files on page 50.
Close and Save
Changes

Saves any changes made and then closes the form.

n/a

If any records were marked for deletion, permanently deletes
those records.

Infor Mongoose Basics User Guide | 119

User Interface Reference

Menu option

Action

Web client keyboard shortcut

Close and Cancel
Changes

Closes the form without saving changes.

n/a

Any records that have been changed revert to their original
values.
Any records marked for deletion are not deleted and are no
longer marked for deletion.

Definition

See Form > Definition submenu for both clients on page 120.

n/a

Sign Out

Opens the Sign In dialog box with the current user ID and configuration filled in.

n/a

You can sign in to a different configuration from the current
configuration. You can also sign in with a different user ID.
Recent forms list

Displays a list of most recently opened forms, arranged in reverse chronological order.

n/a

This list is not labeled as such.

Form > Definition submenu for the web client
In the web client, the Definition option has its own submenu. Like the main Form menu, most of these options
are identical.
Most of these options are for the Web Designer and can be used only if you have appropriate editing
permissions.
There are no keyboard shortcuts for the web client version of this menu.
Menu option

Action

Web client keyboard shortcut

Open

Opens the Select Form dialog box, which you can use to locate
and select the form you want to open.

Ctrl+O

For more information, see Finding and Opening Forms Using
the Select Form Dialog Box on page 31.
For an alternate way of opening forms, see Finding and Opening
Forms Using the Explorer on page 30.
Page Setup

Opens the Page Setup dialog box, which allows you to set
printer options before printing.

n/a

This option is not available in the web client.
Print

Opens the Print Options dialog box, from which you can print
a record or a collection of records.

n/a

For more information, see Printing Records or Collections of
Records on page 63.
This option is not available in the web client.

Infor Mongoose Basics User Guide | 120

User Interface Reference

Menu option

Action

Web client keyboard shortcut

Print Preview

Opens both the Print Options dialog box and the Print Preview
dialog box. Use these dialog boxes to generate a preview before
actually printing.

n/a

For more information, see Previewing a Record or Collection
Before Printing on page 63.
This option is not available in the web client.
Export to File

Opens the Export Collection to File dialog box.

n/a

Use this option to export data from a collection to an external
file that can be used in another program, such as a spreadsheet.
For more information, see Exporting Collections to External
Files on page 50.
This option is not available in the web client.
Close and Save
Changes

Saves any changes made and then closes the form. If any
records were marked for deletion, permanently deletes those
records.

n/a

Close and Cancel
Changes

Closes the form without saving changes. Any records that have
been changed revert to their original values. Any records
marked for deletion are not deleted and are no longer marked
for deletion.

n/a

Definition

See the Form > Definition submenu in the next table.

n/a

Workspaces

Opens the Workspaces dialog box, which you can use to define,
save, change, and delete workspaces.

n/a

See Using Workspaces on page 31.
This option is not available in the web client.
New Sign In

Opens the Sign In dialog box with the current user ID and configuration filled in.

n/a

You can sign in to a different configuration from the current
configuration. You can also sign in with a different user ID.
In the web client, this option displays as "Sign Out".
Exit WinStudio

Exits the system, closing the software and all forms.

n/a

This option is not available in the web client.
Recent forms list

Displays a list of most recently opened forms, arranged in reverse chronological order.

n/a

This list is not labeled as such.

Infor Mongoose Basics User Guide | 121

User Interface Reference

Actions menu
The Actions menu provides options that deal mostly with records and collections of records. It includes
entries for locating records, navigating through collections, and other features related to records.
This menu is available only if you have at least one form open. The options available depend, at least to some
extent on the form that is open or the type of form that is open.
One of the options is Filter, which has its own submenu, again, depending on what kind of form is open.
Menu option

Action

Keyboard shortcut

Refresh

Redisplays the selected collection using the current filtering
criteria.

F5

This option reverses any changes made to records in the collection and restores fields to their original values. Any records
marked for deletion are unmarked and revert to their original
values.
To avoid refreshing the wrong collection or subcollection when
multiple collections are displayed, make sure the cursor focus
is on the collection or subcollection you want to refresh before
selecting this option.
Refresh Current

Redisplays the selected record and restores the field to its
original value.

Ctrl+F5

To avoid refreshing the wrong record, make sure the cursor
focus is on the record you want to refresh before selecting this
option.
Filter

Filters a collection of records using the submenu options.
See the Filter submenu table for a description of the options.

See the Filter submenu table

Next

Moves the focus to the next record in the current collection.

F8

Previous

Moves the focus to the previous record in the current collection.

F7

First

Moves the focus to the first record in the current collection.

n/a

Last

Moves the focus to the last record in the current collection.

n/a

Get More Rows

Retrieves the next set, or "bunch," of records in the collection.

n/a

The size of the bunch is determined by the data record cap
settings.
New

Creates a new, blank record below the currently selected record.

Ctrl+I

See Adding Records on page 65.
Copy

Makes a copy of the current record and inserts it immediately
below that record. Marks it as a new record.

n/a

Infor Mongoose Basics User Guide | 122

User Interface Reference

Menu option

Action

Keyboard shortcut

Save

Saves any changes to the current collection without closing
the form. Any records marked for deletion are permanently
deleted and removed from the collection.

Ctrl+S

Save Current

Saves only changes made to the currently selected record. If
the record is marked for deletion, deletes the record permanently and removes it from the collection.

n/a

Delete

Marks the selected record for deletion.

Ctrl+D

Note that the record is not actually deleted and removed from
the database until you save the form or save the current record.
Validate

Checks all fields in the currently selected record to make sure
they contain valid values.

n/a

If invalid values are found, the system presents a message that
tells you which field contains an error and displays an error indicator in the row label.
Once all values are found to be valid, the error indicator no
longer displays in the row label.
Notes for All

Launches the Class Notes form, from which you can view, attach, or detach notes that pertain to the entire collection of
records.

n/a

See Working with Notes on page 101.
Documents for
Current

Launches the Attached Documents form, which allows you to
view, attach, and remove file attachments for the current
record.

n/a

See Working with File Attachments on page 108.
Email for Current

Launches the Send Email for the current object form, which
you can use to compose and send an email regarding the selected form.

n/a

Notes for Current

Launches the Object Notes form, which allows you to view,
create, attach, and detach notes for the current record.

n/a

If a selected record has one or more notes attached to it, this
menu item displays a check mark.
See Working with Notes on page 101.
Graph

Opens the first page of the Graph Collection wizard, which you
can use to generate a graph based on data from the current
collection.

n/a

See Creating and Formatting Graphs from a Collection on page
56.

Infor Mongoose Basics User Guide | 123

User Interface Reference

Menu option

Action

Keyboard shortcut

To Excel

Opens a Microsoft Excel worksheet (or other application used
by default to open .csv files) containing data exported from the
current collection.

n/a

See Saving a Collection to Microsoft Excel on page 53.
To Form DataView

Sends the current data collection to a DataView, displayed on
the DataView Form Results form.

n/a

See the help about DataViews.
View Event Status

Opens the Event Status form for collections that have the InWorkflow property set to "1".

n/a

The InWorkflow property gets set when an event handler executes against an update or deletion of an object with the Suspend option enabled.
Get Options

(Available only with some report, activity, and utility forms)
Opens the Get Options dialog box, which allows you to apply
a set of stored criteria for record retrieval to the form.

n/a

For example, suppose you have a particular report that you run
on a daily basis, using the same criteria for record retrieval.
Rather than have to set up the same criteria every time you
want to run the report, you can set it up once, and then, before
running it, store the options, as described in the Store Options
menu option.
Then, use this option to apply the same set of search criteria
to the form the next time you want to run the report.
Store Options

(Available only with some report, activity, and utility forms)
Opens the Store Options dialog box, which allows you to save
a set of search or retrieval criteria.

n/a

For example, suppose you have a particular report that you run
on a daily basis, using the same criteria for record retrieval.
Rather than have to set up the same criteria every time you
want to run the report, you can set it up once, and then, before
running it, select this option.
Then, to apply it, use the Get Options option.
Background

(Available only with some report, activity, and utility forms)
Opens the Background Queue dialog box, which you can use
to schedule the report, activity, or utility to run automatically.

n/a

For example, suppose you have a particular report that you run
on a daily basis, using the same criteria for record retrieval.
Rather than have to run the report manually every day, you can
use this option to schedule the report to run automatically at
the time you designate.
For more information, see the help about background queues.

Infor Mongoose Basics User Guide | 124

User Interface Reference

Menu option

Action

Keyboard shortcut

Where Used

(Available only with certain grid forms) Opens a linked Where
Used form that shows where a particular value from the grid
form is used elsewhere in the system.

n/a

View Amounts

(Available only on some utilities) Generates a report preview
that shows the total value of the selected invoices.

n/a

View Workflows

Opens the My Workflows form, which you can use to create
and edit saved workflows.

n/a

See Creating Workflows with the Wizard on page 79.
New Workflow

Open the Workflow Wizard, which you can use to create simple
notification workflows.

n/a

See Creating Workflows with the Wizard on page 79.
Copy Groups From
User

Launches the Copy Groups From User form, in which you can
apply the group authorizations of a selected user to another
newly created or modified user account. If multiple groups are
listed, you can select only the groups that you want to add for
the other user.

n/a

This form can only be accessed from the Users form.

Edit menus
The Edit menu provides options for editing content in forms and for editing the forms themselves. The Edit
menu varies in options according to where you are using it. Possibilities include these:
•
•

The web client
The Web Designer

Note: Many form editing options are accessible to you only if you have the appropriate editing permissions.
Some editing options in the Web Designer are available only when a form is open.

Edit menu in the web client
The Edit menu in the web client is different from the Edit menus available in the Web Designer or the Windows
("Smart") client.
These are the options available on the Edit menu in the web client:
Note: There are no keyboard shortcuts for this menu.
Menu item

Action

Designer

Launches the Web Designer, which is available only in the web client.

Infor Mongoose Basics User Guide | 125

User Interface Reference

Menu item

Action

Paste Rows Append

Pastes records (rows) from the system clipboard, inserting them just below the
currently selected record in a grid.

Paste Rows Overwrite

Pastes records (rows) from the system clipboard into the collection, overwriting
existing records starting with the currently selected record.
For example, if you are pasting in three rows worth data, and your currently
selected row is the fourth row, this option overwrites the records that currently
exist in rows 4, 5, and 6.

Find Value in Collection

Launches the Find dialog box, which allows you to search through a collection
for a specified value in a particular field.

Repeat Find

Repeats the search as performed in Find Value in Collection.

Replace Value in Collection

Launches the Replace dialog box, which allows you to search through a collection for a specified value in a particular field and then replace the current value
with a new value that you provide.

Sort Collection

Opens the Sort dialog box, which allows you to sort a collection according to
criteria you specify in this dialog box.

Import Binary Data for
Current Field

When active, allows you to import a graphics file or other binary data object
into the field.
Active only for fields which can contain graphics or other binary data objects.

Export Binary Data
from Current Field

When active, allows you to export a graphics file or other binary data object
from the field.
Active only for fields which can contain graphics or other binary data objects.

Delete Binary Data
from Current Field

When active, allows you to delete a graphics file or other binary data object
from the field.
Active only for fields which can contain graphics or other binary data objects.

Edit menu in the Web Designer
The Edit menu in the Web Designer is different from the Edit menus available in the web client or the Windows
("Smart") client.
These are the options available on the Edit menu in the Web Designer:
Note:
•
•

With a form open, this menu displays standard Undo, Redo, Cut, Copy, and Paste options. These options
are not included in the table below.
There are no keyboard shortcuts for this menu.

Infor Mongoose Basics User Guide | 126

User Interface Reference

Menu item

Description

Form Script

This option launches the Script Editor for the open form. Use the Script Editor
to create and edit form scripts.
Note: This option is available only if a form is open in the Web Designer. It is
used for developing form scripts only, and not global scripts. For global scripts,
use the Script option on this menu.

Background Color

This option launches the Edit Color dialog box, which you can use to create,
select, and edit color definitions for use as backgrounds in forms, components,
and themes.

Component Class

This option launches the Global Objects form for Component Classes. Use this
form to create, select, and edit component classes as global objects.

Image

This option launches the Images dialog box, which you can use to add, import,
export, and delete image files.

Property Class Extension

This option launches the Global Objects form for Property Class Extensions.
Use this form to create, select, edit, and delete property class extensions as
global objects.

Script

This option launches the Global Objects form for Scripts. Use this form to
create, select, edit, and delete scripts as global objects.
Note: This option is for developing global scripts only, and not form scripts.
For form scripts, use the Form Script option on this menu.

Shortcut Menu

This option launches the Global Objects form for Shortcut Menus. Use this
form to create, select, edit, and delete shortcut (right-click) menus as global
objects.

String

This option launches the Global Objects form for Strings. Use this form to
create, select, edit, and delete translatable strings as global objects.

Theme

This option launches the Global Objects form for Themes. Use this form to
create, select, edit, and delete themes as global objects.

Validator

This option launches the Global Objects form for Validators. Use this form to
create, select, edit, and delete validators as global objects.

Variable

This option launches the Global Objects form for Variables. Use this form to
create, select, edit, and delete variables as global objects.

Web User Control Assets

This option launches the Global Objects form for Web Assets. Use this form to
create, select, edit, and delete web user controls as global objects.

Revert Global Object
Definition

This option allows you to revert global object definitions that have been modified
to their original definitions. To use this option, the original changes must have
been made and can only be reverted using a non-Vendor login.

Infor Mongoose Basics User Guide | 127

User Interface Reference

Edit menu in Design Mode
The Edit menu for the Windows ("Smart") client in Design Mode is different from the Edit menu in Runtime
Mode.
This table lists and describes the options available on the Edit menu in Design Mode:
Menu item

Action

Keyboard shortcut

Undo

Undoes the last editing change you made.

Ctrl+Z

This option works only for editing operations, such as cut, copy,
or paste, performed in Design Mode.
Cut

Cuts a selected component or text from a field.

Ctrl+X

As is common in most Windows programs, this option removes
the selection, but keeps a copy of it on the system clipboard.
This allows you to paste the deleted selection somewhere else.
Copy

Copies a component or the contents of a selected field.

Ctrl+C

As is common in most Windows programs, this option copies
the selection to the system clipboard. This allows you to paste
the selection somewhere else.
Paste

Pastes a component or text into a field.

Ctrl+V

As is common in most Windows programs, this option pastes
the contents of the system clipboard into the desired location.
If you select this option after cutting or copying an entire component, a dialog box opens that offers various alternatives for
what to paste and where.
Paste Rows Append

Not active in Design Mode.

n/a

Paste Rows Overwrite

Not active in Design Mode.

n/a

Find Value in Collection

Not active in Design Mode.

n/a

Repeat Find

Not active in Design Mode.

n/a

Replace Value in
Collection

Not active in Design Mode.

n/a

Sort Collection

Not active in Design Mode.

n/a

Find Value for
Current Field

Not active in Design Mode.

n/a

Add Value for Current Field

Not active in Design Mode.

n/a

Infor Mongoose Basics User Guide | 128

User Interface Reference

Menu item

Action

Keyboard shortcut

Details for Current Field

Not active in Design Mode.

n/a

Import Binary Data for Current
Field

Not active in Design Mode.

n/a

Export Binary Data from Current
Field

Not active in Design Mode.

n/a

Delete Binary Data from Current
Field

Not active in Design Mode.

n/a

View Binary Data
for Current Field

Not active in Design Mode.

n/a

Design Mode

Exits Design Mode.

Ctrl+E

Regenerate Form

Saves the form, including any changes made, closes the form,
and then automatically reopens the form so that the changes
are reflected and displayed.

Ctrl+G

Regenerate Form
from Cache

Places the current form definition in the local runtime cache,
then regenerates the form in Design Mode.

n/a

Component

See the Edit > Component submenu section.

n/a

Background Color

Launches the Edit Color dialog box, which allows you to define
background colors to be used in forms and components.

n/a

Component Class

Launches the Component Class dialog box, which allows you
to select, view, add, modify, and delete component classes.

n/a

Event Handlers

Launches the Event Handlers dialog box, which allows you to
select, view, add, modify, and delete event handlers.

n/a

Image

Launches the Images dialog box, which allows you to select,
view, add, import, export, and delete graphics images. These
images become part of your application.

n/a

Property Class Extension

Launches the Property Class Extension dialog box, which allows you to select, view, add, modify, and delete property class
extensions.

n/a

Script

Launches the Scripts dialog box, which allows you to select,
view, add, modify, and delete scripts.

n/a

Shortcut Menu

Launches the Shortcut Menus dialog box, which allows you to
select, view, add, modify, and delete shortcut menus.

n/a

String

Launches the Strings dialog box, which allows you to select,
view, add, modify, and delete character strings.

n/a

Infor Mongoose Basics User Guide | 129

User Interface Reference

Menu item

Action

Keyboard shortcut

Theme

Launches the Themes dialog box, which allows you to copy,
edit, or delete a theme. You can also create a new theme. You
can only delete user-created themes. The Theme Editor is displayed for some functions.

n/a

Validator

Launches the Validators dialog box, which allows you to select,
view, add, modify, and delete validators.

n/a

Variable

Launches the Variables dialog box, which allows you to select,
view, add, modify, and delete variables.

n/a

Filter

See the Edit > Filter submenu section.

n/a

Script Editor

Launches the Edit Form/Global Script dialog box,which allows
you to edit the code associated with the current form script or
global script.

n/a

If you have Visual Studio installed on your computer, and you
have the appropriate Visual Studio option selected in User
Preferences, this option launches the Visual Studio application.
Otherwise, if the WinStudio option is selected in User Preferences, the native script editor window launches.
Web User Control
Assets

Launches the Edit Web User Control Assets dialog box, which
allows you to select, create, edit, import, or delete web user
controls and web user control elements.

n/a

Edit > Component submenu
These options are available and active only in Design Mode.
There are no keyboard shortcuts for this submenu.
Menu Item

Action

Delete Selected

Deletes the selected component. If the component contains any objects or
other components within it, these are also deleted.

List

Launches the Form Component List dialog box, which allows you to view, edit,
and delete information about all the components associated with the active
form.

Set Current Form Page

Makes this form page the current one for this form.

Tab Order

Toggles on or off the view in which you can set the order in which fields should
receive focus as the user navigates through the forms using the TAB key.

Tab Order at

Resets the tab order beginning with the selected component. Any components
that already have a tab order ahead of the selected component are not changes.

Infor Mongoose Basics User Guide | 130

User Interface Reference

Menu Item

Action

Adjust Grid

Adjusts all grids in the current form so that the right-most grid column fits
within the specified grid dimensions. This adjustment eliminates extra space
and any unnecessary horizontal scroll bar.

Edit > Filter submenu
These options are available and active only in Design Mode.
There are no keyboard shortcuts for this submenu.
Menu Item

Action

Edit

Launches the Select Filter dialog box, which allows you to select a filter to edit.

Edit Current

Launches the Edit Filter dialog box, which allows you to edit the saved filter
for the form you are working with.

View menus
The View menu includes a number of options for showing or hiding various elements of the user interface.
The options on the View menu depend on where you are using it. Some options are available only if you have
a form open.
View menus are used on these interfaces:
•
•

The web client
The Web Designer

View menu in the web client
The View menu includes a number of options for showing or hiding various elements of the user interface.
Some options are available only if you have a form open.
This table lists and describes the options available on the View menu in the web client:
Menu option

Action

Keyboard shortcut

Explorer

Shows or hides the Explorer panel, which can be used to access
forms

n/a

See Using the Explorer on page 9.
User Preferences

Launches the User Preferences window

n/a

This is where you can set your own user preferences.
See Setting User Preferences in a Web Client on page 15.

Infor Mongoose Basics User Guide | 131

User Interface Reference

Menu option

Action

Keyboard shortcut

Inbox

Opens the Inbox form

n/a

Generates the StdAppLaunchInbox standard form event
Note: Developer users can generate or intercept this event.
Report Output
Files

Opens the Report Output Files form

n/a

Tasks

Opens the Create User Task form

n/a

Generates the StdAppLaunchTask standard form event
Note: Developer users can generate or intercept this event.
System Notes

Opens the System/User Notes form, which is used to create,
read, and maintain reusable notes

n/a

See Working with Notes on page 101.
Activate Next Collection

Moves the focus from one collection to the next

Home Cursor

In a grid, moves the cursor to the first active column in the
record

n/a

Note: This option is available only for forms that display multiple collections.
Ctrl + Home

In a detail view or other type of form, moves the cursor and the
focus to the first active field in the form
Hide/Show 1st
Splitter Pane

Toggles the grid view on or off

Ctrl + Shift + 1

Hide/Show 2nd
Splitter Pane

Toggles the detail view on or off

Activate Next
Pane

Moves the focus from wherever the pointer currently is to the
other view

Note: This option works only with multiview forms, query
forms, or other forms in which there are both grid views and
detail views.
Ctrl + Shift + 2

Note: This option works only with multiview forms, query
forms, or other forms in which there are both grid views and
detail views.
n/a

Note: This option works only with multiview forms, query
forms, or other forms in which there are both grid views and
detail views.
Diagnostic Log

Opens the Diagnostics window, which is used for debugging
and other analytic tasks.

n/a

Diagnostic Trace

Opens the Trace Viewer , which is used for debugging and
other analytic tasks.

n/a

Infor Mongoose Basics User Guide | 132

User Interface Reference

View menu in the Web Designer
The View menu includes a number of options for showing or hiding various elements of the user interface.
Some options are available only if you have a form open.
This table lists and describes the options available on the View menu in the Web Designer. There are no
keyboard shortcuts for these options.
Menu option

Action

User Preferences

Launches the User Preferences dialog box.
This is where you can set your own user preferences.
See Setting User Preferences in a Web Client on page 15.

Hide/Show 1st Splitter
Pane

This option is not functional in the Web Designer.

Hide/Show 2nd Splitter
Pane

This option is not functional in the Web Designer.

Diagnostics

Opens and closes the Diagnostics window, which is used for debugging and
other analytic tasks.

Help menu
The Help menu provides a number of options for accessing the online help, as well as for getting information
about the system software and any form you might have open.
Note: All help topics display in the default web browser for your system.
Menu option

Description/Comments

Contents and Search

Opens the online help in its own browser window, displaying the Contents in
the left pane and the initial help topic in the right pane
This can be especially useful when you want to:
•
•

Browse help topics for general learning purposes
Use the Search functions to find information about a specific topic or feature

Current Form

Opens the online help to the topic for the currently active form

Current Field

Opens the online help to the topic for the currently selected field or other
component

About [Application]

Opens an informational window that presents information for the application
you are using:
•
Copyright notice
•
Database configurations
•
Your user login ID and editing permissions
•
Optionally, a custom message created by a system administrator

Infor Mongoose Basics User Guide | 133

User Interface Reference

Menu option

Description/Comments

About This Form

Opens the About This Form information window, which presents this information for the currently active form:
•
The name and original caption of the form
•
The version of the form
•
The name of the parent form, if any
•
Which collection is currently selected
•
The name of any permanent or user-defined filters in use

Toolbars
The system interface has two tool bars available:
•
•

The main toolbar includes options (and buttons) for performing many of the most common actions.
The alignment tool bar is available only in Design mode and includes options for placing components
on forms.

Main toolbar
The main toolbar includes tools to help you accomplish the most common tasks.
This toolbar displays just underneath the menu bar. You can choose whether to display the toolbar.
This table lists and describes the main toolbar options:
Icon tooltip

Description/Comments

Open

Opens the Select Form dialog box, which you can use to locate and select
the form you want to open.
For more information, see Finding and opening forms using the Select
Form dialog box on page 31.
For an alternate way of opening forms, see Finding and opening forms
using the Explorer on page 30.

Save

Saves the form and any changes you have made to the form, but does not
close the form.

New

Adds a new record after the currently selected entry.

Delete

Marks the selected record for deletion.
The record is not actually deleted and removed from the database until
you save the form or save the current record.

Infor Mongoose Basics User Guide | 134

User Interface Reference

Icon tooltip

Description/Comments

Refresh

Re-queries and displays the selected collection using the current filtering
criteria.
This option reverses any changes made to records in the collection and
restores fields to their original values. Any records marked for deletion
are unmarked and revert to their original values.
To avoid refreshing the wrong collection or subcollection when multiple
collections are displayed, make sure the cursor focus is on the collection
or subcollection you want to refresh before selecting this option.

Filter In Place

When on, opens the current form with Filter-in-Place, which allows you
to filter for specific forms. This option clears them, so you can define a
new search.
For more information, see Finding records with Filter-in-Place on page
36.
After you use Filter-in-Place to identify the records you want to locate,
use this option to start the search. The system displays the records that
match the specified criteria as the current collection.
For more information, see Finding records with Filter-in-Place on page
36.

Get more rows

Retrieves the next set of records in the collection.
The size of the set is determined by the data record cap settings. For example, if the record cap is set to 200, then a query returns only the first
200 records in the collection. Clicking this icon retrieves the next 200
records, and so on.

System Notes

In the web client, opens the System/User Notes dialog box. In the Windows
client, opens the Object Notes form, which allows you to view, create,
attach, and detach notes for the current record.
For more information, see About notes on page 101.

Documents

Opens the Attached Documents form, which allows you to view, attach,
and remove file attachments for the current record.
For more information, see Working with document (file) attachments on
page 108.

Send email for the current object

Opens the Send Email for the current object form, which allows you to
send an email that contains information about the current record.
For more information, see Sending form-specific emails on page 74.

Export to Excel

Opens a Microsoft Excel worksheet (or other application set to open .csv
files) containing selected data exported from the current collection.
For more information, see Saving a collection to Microsoft Excel on page
53.

Revert runtime changes...

Closes the current form, reverts any run-time changes made to that form,
and reopens the form.

Infor Mongoose Basics User Guide | 135

User Interface Reference

Icon tooltip

Description/Comments

Runtime Builder...

Opens the Runtime Builder Selection form, which you can use to launch
designated "Runtime Builder" forms.

Open designer in a new window

Opens the Web Designer in a new browser window (web client). If a form
is open, opens the form in the designer in both cases.
The purpose/goal is the same in both cases: You must be in the Web Designer/Design Mode to customize and develop forms.

Help Current Form

Opens the online help to the topic for the currently active form.

Auxilliary toolbars (in designers)
Various auxilliary toolbar options are available only in the Web Designer. These toolbars include options to
perform these tasks:
•
•
•
•
•
•
•

Create, copy, and delete forms
Align components on forms
Size components with respect to other components
Space components
Arrange columns and rows
Set tab order of components
And more...

The combined toolbar
You can change how the main toolbar and menu bar are displayed. You can display them as a single-level,
combined toolbar and form options bar.
The combined toolbar extends and contracts according to your needs. The combined toolbar has a basic
section that is always visible and two extendable sections with >> buttons that you can use to move between
sections for each theme. The Form menu displays and is available via a toolbar button. The combined toolbar
displays the current site configuration; hover over it to see a tooltip that includes the user name and site
configuration. The About dialog can also be opened by clicking the site configuration.
When multiple form tabs are open, the form tabs will shrink to an equal width until they reach a minimum of
250 pixels. Once the minimum size is reached, Mongoose hides any additional tabs and makes them accessible
via a drop-down menu located at the end of the form tab bar.
For the procedure to implement this option, see Implementing a combined toolbar on page 137.

Infor Mongoose Basics User Guide | 136

User Interface Reference

Implementing a combined toolbar
In the web client, you can consolidate most application functions and options into a single-level combined
toolbar.
Note: This option is available only in the web client.
1
2
3
4

From the View menu, select User Preferences.
In the User Preferences dialog box, select the Layout tab.
From the Toolbar Type drop-down list, select Combined.
Click OK.

To see the change, you must sign out of the web client and then sign back in. The combined toolbar now
displays as described in the topic "The combined toolbar on page 136".
Note:
•
•
•

The Default toolbar type, is based on the Combined Toolbar User Preference process default setting
if that has been set. Otherwise, it is the same as the Standard option.
If you select the Standard toolbar type option, options are displayed as a typical two-tiered menu bar
and toolbar, with a separate Form menu.
When multiple form tabs are open, the form tabs will shrink to an equal width until they reach a minimum
of 250 pixels. Once the minimum size is reached, Mongoose hides any additional tabs and makes them
accessible via a drop-down menu located at the end of the form tab bar.

Infor Mongoose Basics User Guide | 137


