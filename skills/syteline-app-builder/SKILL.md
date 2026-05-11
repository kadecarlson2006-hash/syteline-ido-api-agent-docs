---
name: "syteline-app-builder"
description: "App Builder and App Hub overview - UI, projects dashboard, workspace, toolbar, canvas, repository, responsive design, creating/editing/exporting/importing/publishing projects"
version: "1.0"
tags:
  - syteline
  - app-builder
  - app-hub
  - mongoose
---

# syteline-app-builder

Source: Infor Mongoose App Builder/App Hub User Guide, Release 2026.x

this website periodically for updated documentation.

Infor Mongoose App Builder/App Hub User Guide | 7

About App Builder and the App Hub

Chapter 1: About App Builder and the App Hub

App Builder
App Builder is a set of application design tools that are incorporated in and can be run from within Infor
Mongoose. As part of the Infor Technology Suite, App Builder is designed primarily to run within Infor OS
Portal™ and in the cloud as part of a multi-tenant offering.
You can use App Builder to reuse existing business logic across Infor products and to build composite
applications that use that business logic. You can also use App Builder to create custom-built and highly
specialized apps to boost your productivity.
App Builder is designed to build basic apps using an intuitive WYSIWYG user interface. App Builder offers
click-and-drop of user interface components, a data service layer to connect with APIs, and an interaction
wizard to set up how the components of your app interact with each other.
App Builder also includes responsive-design mechanisms that enable you to easily design apps for optimal
operation and display, regardless of what device the end user is using.
Apps can be built, exported, and published from and into different environments. This means you can develop
and test in your test environment, and then later deploy the app in your production environment. Optionally,
you can begin the creation of an app with App Builder and then convert the app to be a Mongoose form for
further development. You can also receive and incorporate apps from other Infor or third-party sources.
Apps built in App Builder are client-side applications that use API Gateway or ION API, Mongoose Intelligent
Data Object (IDO) back end services, or both. Because many Infor products are API Gateway or ION API-certified,
you can use them when building apps with App Builder. You can manually attach your own APIs behind the
API Gateway, and that way, you can use any REST-based API as part of your apps. In addition, you can use
Mongoose IDOs in your App Builder apps.
App Hub
The App Hub is a repository where published and activated App Builder apps are stored and made available
to users. App Hub is the main user interface for the end user of your apps. The App Hub offers a list of all apps
the user has access to.
Authorized administrators can administer their company's apps within the App Hub. Administrators can
control which apps are available, in what versions, and who can access them.
Mongoose as host for App Builder and App Hub
When used within Infor Mongoose, App Builder and App Hub are both hosted within Mongoose forms that
have the same names. In fact, with the incorporation of App Builder and App Hub into Mongoose, these apps

Infor Mongoose App Builder/App Hub User Guide | 8

About App Builder and the App Hub
can now operate as FormOnly-mode Mongoose forms. If you are using Infor OS Portal to access App Builder
and App Hub, you must be a licensed Mongoose user operating in Infor OS Portal. And if you attempt to access
App Builder or the App Hub from a URL, you are prompted to sign in to Mongoose before the form opens.
Note: Although App Builder or App Hub can run within a Mongoose environment, they are designed primarily
to work within an Infor OS Portal environment.
Complementary design and storage of App Builder apps
App Builder and Mongoose can be considered complementary design tools. What you do in App Builder can
be converted to work within Mongoose, as Mongoose forms.
For example, if you create an app in App Builder. When you do, App Builder can convert and store the app as
a Mongoose form.
Note: This does not necessarily imply that all content in one can transfer to the other. There are limitations,
in that the metadata from an App Builder project must have an analogous concept or component in Mongoose,
or Mongoose does not recognize and use it.
Interactions created in App Builder projects are generally created and stored in their corresponding Mongoose
forms as form event handlers.
When an App Builder project is copied to and converted to be a Mongoose form, data services created from
API Gateway or ION APIs in App Builder projects are created in Mongoose as IDOs. Collections created with
or for those IDOs are then added to the Mongoose form, and Mongoose components are bound to those
collections.
Of course, data services that were created from Mongoose IDOs, retain and reuse those IDOs when the project
is converted to be a Mongoose form.

About the App Builder UI
The App Builder (designer) UI is designed to be user-friendly and WYSIWYG. It consists of a dashboard and a
workspace.
The Projects dashboard is where you view and select the project you want to work on, or you create a new
project.
The workspace consists of three primary areas—the Toolbar, the Components/Properties panel, and the
canvas. This is where you actually design and create your App Builder projects.

The Projects dashboard
The App Builder Projects dashboard is the starting point for all new and existing projects.
The Projects dashboard offers these options for viewing and accessing your App Builder projects:

Infor Mongoose App Builder/App Hub User Guide | 9

About App Builder and the App Hub
•

•

The Tile View (the default) displays information and options for each project using tiles, one for each
project. This view displays which project (if any) is currently open, as well as creation and change data
for the project. To view the project description and action options, move your cursor over the tile. To
open the project click the tile.
The Grid View displays project information in a data grid format. You can use this option view similar
information as on the tiles. You can also perform any of the action options in this view.

To select the view you want, use the icons in the top left corner of the dashboard.
View option

Icon

Tile View

Grid View

From the Projects dashboard, you can perform these operations:
•
•
•
•
•
•
•
•
•

See a display of the projects currently under development (at the bottom of the window).
Use the Search option to filter for a particular project (magnifying glass icon).
Create new projects, using templates and/or specifying types (using tags).
Import projects.
Access the App Builder Repository to view and select Data Services and/or Composite Components
for use in your project. You can also create data services in the Repository.
Filter the list of projects to see a list of all projects or just those you have created (All Projects or Created
by me drop-down list).
Sort by the date the project was created or changed, in ascending or descending order.
Filter the projects displayed by type (using tags).
Delete, duplicate, export, launch, or publish a selected project.

The App Builder workspace
The App Builder workspace is the area in which you actually develop your App Builder apps. It consists of
three primary sections: the toolbar, the Components/Properties panel, and the canvas.
This illustration shows the primary sections of the workspace:

Infor Mongoose App Builder/App Hub User Guide | 10

About App Builder and the App Hub

The toolbar
The toolbar provides access to a variety of common functions, including these:
•
•
•

Selection of the view in App Builder
Common editing functions
Miscellaneous other project settings, option, and activities

The canvas
The canvas is where you actually build your app, placing and configuring components.
The Components/Properties panel
The Components/Properties panel provides for these options:
•
•
•

Selection of components to be used in the app
Setting of component properties and attributes
Access to composite components, including the option to create them

The App Builder toolbar
The toolbar provides access to a variety of common functions, including view selection, editing functions,
and other project settings, options, and activities.

Infor Mongoose App Builder/App Hub User Guide | 11

About App Builder and the App Hub
View selection icons
The icons on the left side of the toolbar provide options to select the view.
Icon

Tooltip

Description

Return to Dashboard

When clicked, returns you to the Projects dashboard.

Components

When clicked, displays the list of components you can use
to create your apps. You can display both the Basic components and any Composite components you might have
created.

Component Properties

When clicked, displays the various property settings you can
make for a selected component on the canvas.
Note: This option is selected automatically whenever you
select a component on the canvas.

Project and miscellaneous other options icons/settings
The icons on the right side of the toolbar provide project-level options and miscellaneous other high-level
options.
Icon

Tooltip

Description

Preview

When clicked, displays the project in Preview mode, keeping
it in the canvas area. This allows you to test the functionality
without leaving the workspace.

Launch

When clicked, launches the project in a new window. This
allows you to test your app in the same kind of environment
that the end user will experience it.

Save

When clicked, saves the project.
This icon also hosts a validity indicator. When App Builder
detects that there are no errors in the app, it displays a
checkmark in a green circle. If App Builder detects that there
is an error condition, the green circle changes to a red circle
with a minus sign. If this happens, you cannot save the
project until the error condition is corrected.

More...

When clicked, displays a menu of project-level and miscellaneous other options available from the workspace.
See Additional App Builder project options on page 13.

Infor Mongoose App Builder/App Hub User Guide | 12

About App Builder and the App Hub
Editing option icons
The icons in the center of the toolbar provide access to common editing operations and are only active/enabled
when appropriate.
Icon

Tooltip

Description

Undo (Ctrl+Z)

Undoes the previous action.
App Builder supports multiple levels of Undo.

Redo (Ctrl+Y)

Redoes a previous action that was undone.
App Builder supports multiple levels of Redo.

Cut (Ctrl+X)

Cuts (removes) a component selected on the canvas and
saves a copy to the system clipboard.

Copy (Ctrl+C)

Saves a copy of a component selected on the canvas to the
system clipboard.

Paste (Ctrl+V)

Pastes the contents of the system clipboard at a selected
location on the canvas.
When this option is selected, a "ghost" image of the component (or other contents of the clipboard) displays at the location where the copy is to be pasted. When you have the
copy where you want it, you must click to complete the paste
action.
Note: If the paste action does not result in the component
being pasted at exactly the location you want, you can use
the component properties to move it.

Delete (Del)

Deletes a selected component from the canvas.

Additional App Builder project options
The App Builder workspace toolbar offers additional project-level options. These options are accessed by
clicking the More icon.
Project Settings
When you select the Project Settings option, App Builder launches the Project Settings dialog box. In this
dialog box, you can make these project settings:

Infor Mongoose App Builder/App Hub User Guide | 13

About App Builder and the App Hub

Option/Setting

Description

Project Name

(Required) The project name is used only in App Builder. It does not
necessarily have to be the name you want to use for the app itself. This
is the name that displays with the project on the Projects dashboard.

App Header Title

(Required) The app header title is the name that displays in the title
bar of the app when it is running.

Description

(Optional) The contents of this field display only on the Projects
dashboard. Use it to provide you (and others) helpful information
about the app and its use.

Tags

(Optional) When you start to type in this field, if there are any existing
tags that have the character strings you typed, then a drop-down list
of possible tags displays. You can select the tag you want, then, from
the list.
If no existing tags contain those characters, then the drop-down list
displays "No Results". You can use this to create a new tag by finishing
the tag text as you want it to appear and then pressing Enter.
If the field is displaying a value, and you want to add another tag, click
the plus (+) button.

Version

(Required) By default, if you are working on a new project, App Builder
sets the value of this field to 1.0.
If you want to use a different version number, you can change this
value to whatever you want.

Default theme

(Optional) App Builder provides five built-in themes. You can select
which one you want to use as the default theme for your app by selecting it from the drop-down list.
Note: The two "Transparent" themes mirror their "Light" and "Dark"
counterparts, with the exception that you can set the background
color of flexboxes to something other than white or black, respectively.

Use Title Bar

When selected, this option displays a title bar at the top of your app.
The title bar displays the name of your app (as defined in the App
Header Title field) and sometimes other options.
When cleared, no title bar displays.

Enable Theme Switch

When selected, this option allows users to select their own themes,
using an ellipsis (…) button that displays on the title bar.
Note: This button displays only if the Use Title Bar option is also selected.
When cleared, App Builder uses only the Default Theme.

Infor Mongoose App Builder/App Hub User Guide | 14

About App Builder and the App Hub
Create App Thumbnail
When clicked, this option creates a thumbnail image of the project for use on the Projects dashboard. This
can make it easier to locate and select projects.
Repository
When clicked, this option accesses the App Builder Repository.
See About the App Builder Repository on page 24.
Keyboard Shortcuts
When selected, this option opens a display of the currently defined keyboard shortcuts you can use in App
Builder.
Refresh
When clicked, this option refreshes the display of the project you are working on, so you can see the current
setup.
Language
When selected, this option displays a drop-down list from which you can select languages to use for
components that have appropriate translation options defined.
Note: The selected language affects only the components for which you have specified translation options
for that language. For example, if you have a component for which you want the label to display in Swedish,
you must first have the Swedish Translation created for the label before the Language option has any effect.
Translations
When clicked, this option launches the Translations dialog box, in which you can define component strings
and global strings for any languages you are using. Many U.S. English global strings are already defined within
App Builder (and are the same as the standard Mongoose strings), but you can add your own as well.

The Components/Properties panel
The Components/Properties panel is where virtually all the work to design and lay out the project is performed.
In this panel, you select, place, and configure components. You also set properties that govern how the
components display and behave.
Depending on what you are doing, this panel performs one of these basic functions:
•

When in Components mode, this panel displays the basic and composite components available for
selection and use. The Basic components display in one notebook tab, on tiles, which you can click to
select the component you want. Any composite components you might have created display on the
Composite tab and can be selected from there.

Infor Mongoose App Builder/App Hub User Guide | 15

About App Builder and the App Hub
•

When in Properties mode, this panel makes available the properties for a component that has been
selected. This panel displays automatically whenever you select a component on the canvas. The properties
that are available depend on what kind of component is selected. Use these properties to modify the
functionality and appearance of the selected component.

About components
Components are the "building blocks" you use to create your projects. There are two fundamental types of
components: Basic and Composite.
Basic components
Basic components are ready-to-use components that are included in App Builder. Some basic components
are used to display data. Some basic components allow you to select options within them. Other basic
components allow you to enter, modify, and delete data that can then be acted upon.
For each basic component, you can define user actions, triggers, styles, content, and—depending on the type
of component—specific settings that you can control as the designer of the app.
Each basic component is considered either a "small" component or a "large" component. "Small" components
are small enough in size that you can typically fit multiple small components into a single Flexbox. "Large"
components are designed to occupy an entire Flexbox. In some cases, you can also place "small" components
within certain "large" components.
Composite components
Composite components are more complex components that you create for your own use. These components
typically include two or more basic components that you add to a single Flexbox component and then save
as a composite component.
Once you have created and saved a composite component, it displays on the Composite component tab.
You can then use it as you would any other component.

Basic components
Basic components are the components that are included in App Builder. These comprise the fundamental
"building blocks" that you use to create any App Builder project.
This table lists and describes the basic components that come packaged with App Builder. Each component
is designated as either "Small" or "Large".
See About components on page 16.
Component

Description/Comments

Button

Button components typically initiate an process such as running a procedure
or launching/closing a form. But they can also serve a variety of other functions,
such as displaying data or text from a different component.

[Small]

These are best used for performing discrete operations on a given set of data,
or triggering a separate workflow.

Infor Mongoose App Builder/App Hub User Guide | 16

About App Builder and the App Hub

Component

Description/Comments

Chart

Chart components display data from a source, using a visual format. You can
select from a variety of chart types, set different color schemes, and determine
if and where a legend displays.

[Large]
Checkbox
[Small]

Checkbox components are typically used to select (or deselect) various options.
They can also be used to provide multiple options for a user to select.
These are best used when all possible options should be clearly visible to a user.

Datagrid
[Large]

Datagrid components are used to display and manipulate data, often from a
database or other data service, using a tabular format.
These are best used for comparison across many objects and attributes.
These components are comparable to Grid and Vertical Grid components in
Mongoose.

Datepicker
[Small]

Datepicker components allow you to select and display dates. These components can also derive their values from a data service.
You can select the format to be used for the date display from a variety of format
options.
This component is similar to the DateCombo component in Mongoose.

Dropdown
[Small]

Dropdown components are used primarily to present a list of options for the
user to select from. It displays one or more selectable values in a menu that is
collapsed by default. A user can select an actionable value from the expanded
list.
These are best used when users do not require a view of all possible values at
all times.
This component is similar to the DropList component in Mongoose.

Hyperlink
[Small]
Image
[Large]
Label
[Small]

At this time, the Hyperlink component can be used only as a type of button,
responsive to an On Click Event interaction.
The Image component is used to display a graphics image. Graphics image files
can be obtained from a static URL address, IDM (Infor Document Management),
or an IDM data service.
Label components can be used to display static text, data from a source, or
other text that you do not want to be editable.
This component is similar to the Static component in Mongoose, except that
you cannot use it to display graphics images, only text.

Infor Mongoose App Builder/App Hub User Guide | 17

About App Builder and the App Hub

Component

Description/Comments

List

The List component displays a list of items that can be obtained from or derived
from a data service or a manually created list. It displays a set of related data
objects and their attributes.

[Large]

These are best used for limited attribute data that might or might not include
clear differentiators like status.
This list can be read-only, or you can allow users to select single items from the
list, by including an interaction on the list that monitors mouse clicks and responds.
This component is similar to the List component in Mongoose.
Lookup
[Small]
RadioButton
[Small]

The Lookup component allows the user to open a dialog box that contains a
list from which the user can make selections.
The RadioButton component typically presents the user with a set of options
from which only one can be selected. You can add, delete, rename, and change
the order of options.
This component is comparable to the RadioButton component in Mongoose.

Tabs
[Large]

The Tabs panel component allows you to create a multi-tabbed notebook that
you can use to display multiple sets of components and data within a single
component. In effect, it is a container for other components, both large and
small.
You can add, delete, rename, and change order of tabs in the component.
This component is comparable to the Notebook component in Mongoose.

TextBox
[Small]

The TextBox component is used to display text on a single line. This text can be
read-only, or you can use it as an alphanumeric text-input field. You can add
"placeholder" text to prompt users on what is expected.
These are best used for open-ended inputs that complete a key-value pair.
This component is comparable to the Edit component in Mongoose.

TextArea
[Small]

The TextArea component is used to display text in a multi-line format. Text that
runs beyond the edge wraps. You can make this component resizable and/or
read-only. Like the TextBox component, you can also use it as a text-input field.
You can add "placeholder" text to prompt users on what is expected. Finally,
you can set a maximum number of characters that can be used within the text
area.
These are best used for open-ended inputs might require more than a single to
display effectively.
This component is comparable to the MultiLineEdit component in Mongoose.

Composite components
Composite components are components that you create by assembling some number of components within
a Flexbox and then defining the collection as a composite component. These composite components enable

Infor Mongoose App Builder/App Hub User Guide | 18

About App Builder and the App Hub
you to save a set of components that you want to be able to reuse as a unit; for example, a feedback form, a
set of input fields, or even a major part of your app.
Composite components are saved to your your list of composite components in the workspace. They display
on the Composite tab of the Components panel.
You can also export composite components to files and import composite components from files. They can
then be included in the App Builder Repository for reuse.

Placing components
A basic aspect of App Builder project design is the placement and configuration of components. When
performing these operations, it is helpful to have an understanding of some key principles.
Basic placement of components is pretty straightforward: You simply click to select the component you want
from the Components panel. After you select the component, and as you move your mouse pointer over a
Flexbox a "shadow" image of the component appears inside the Flexbox to indicate where App Builder will
place the component. When you click inside the Flexbox where you want to use that component, App Builder
then automatically places the component at that default location.
It is often the case, however, that the default location is not exactly where you want to place the component.
There are a few ways to modify things so as to place the component precisely where you do want it.
Note: These options typically apply only to "small" components. They do not affect (or are not available for)
"large" components, because those components normally occupy the entire Flexbox into which they are
placed.
Generally, you can use any of these options:
•
•
•

•
•
•

You can change the order in which components display within a Flexbox by clicking and dragging a
component to a different position.
Virtually all components allow you to set margins around the component.
Some components allow you to add columns or use other properties to control their display. For example,
suppose you have two components in a vertically oriented Flexbox, and you want to display two
components side-by-side. Normally, App Builder stacks them one on top of the other. To display them
side-by-side, you can add a column to the Flexbox and then place one of the components inside the
second column.
You can use the Padding property, Alignment property, and other properties of the containing Flexbox
to help determine where and how the component displays.
Other components, particularly tabs and tab panels, offer similar—and other—properties that you can
use to control spacing and placement.
You can subdivide Flexboxes into rows and columns. This option offers a great deal of flexibility, as you
can then set the relative width and height of each row and column to attain the layout you want.
Note: "Rows" and "columns" are actually Flexboxes within other Flexboxes, which makes their use
extremely adaptable and flexible.

Infor Mongoose App Builder/App Hub User Guide | 19

About App Builder and the App Hub

Configuring components
After placing a component on the canvas, there are numerous ways that you can configure them to look and
function the way you want.
There are four basic ways to configure a component. You can:
•
•
•
•

Bind it to a data service.
Determine what kind of Content it handles.
Specify how it is to interaction with other components (Interactions).
Define how it is to display (Style).

All of these options are set on the Properties panel. Data services are created and managed using the Manage
Data Services link at the top of the panel. The other settings are all handled on their own respective tabs on
the Properties panel.

About data services
App Builder uses data services to retrieve data from other sources, connect with other Infor products using
API Gateway or ION APIs, and use Mongoose resources.
For example, a data service can be used to load data from an external source into your Infor App Builder
project, or to save data from your project to an API Gateway or ION API destination.
Data services can connect App Builder to Infor APIs, using API Gateway or ION API technology, as persistent
references. This enables the use of App Builder that involves multiple other applications.
Data services can be created directly within your App Builder project. They can also be exported to files, which
can then be imported into the App Builder Repository for use in other projects. This enables you to share
them and move them between different projects, environments, and installations.
For a data service to work with other applications in App Builder, you must have the appropriate system or
ERP in your IOS installation. For example, if you have Infor M3, Infor Document Management (IDM), Infor OS
Portal, or Infor Distribution SX.e installed and configured in IOS, then you can use their respective APIs in App
Builder.
In addition, because App Builder is fully integrated with and incorporated into Infor Mongoose, you can use
data services as a means of accessing and using Mongoose Intelligent Data Objects (IDOs) in your App Builder
projects.

About Content settings
In App Builder projects, the Content settings essentially determine what content is displayed on or handled
by a given component.
All basic components have the Show Error option. When this option is selected for the component, if for any
reason the content cannot display properly, App Builder displays a red error icon on the Save icon to indicate
that an error exists. To display the error message that lets you know what the error is, hover over the Save
icon. This option is always selected by default.
Any other content settings depend on the component. The Datagrid component, for example, has content
settings for Columns, Style (how the content is to be displayed, in this case), and Paging; while the List
component has a single content setting for a Header.

Infor Mongoose App Builder/App Hub User Guide | 20

About App Builder and the App Hub
Some common Content values
With a very few exceptions, nearly all Content settings include options to specify the Source of the content
for the setting and, in many cases, options to further specify a particular Display Value or Display Field value
from the Source, in cases where multiple value options exist in the Source.
Typically, for the Source, you can select any of the other components in the project, a Flexbox, or the
application itself. You can also set any or all of them to a {Static Value} or an {In Context Value}. The Display
Value, then is determined by what is selected as the Source.
A {Static Value} setting allows you to specify exactly what is to display in the Display Value field for that
component option. This display value always remains the same until or unless you change it.
An {In Context Value} setting takes its Display Value from a data service that is specified for the component
or its parent container. If there is no other data service designated for a child object, it inherits from the parent
container when this option is used. If there are multiple properties or attributes available for the Display
Value, you can select from a drop-down list.

About Interactions
Much of the functionality of an App Builder app occurs through "interactions" between the application, data
services, and components. Interactions might or might not require data services. Each interaction can exist
between only two components and perform only one action. Each component, though, can have multiple
interactions.
For example, if you create a data service based on a Mongoose IDO, you can instruct the data service to load
certain property values into a component such as a List or a Datagrid whenever the app is launched. This
creates an interaction in App Builder designed to load the appropriate values from that IDO and display them
in the component as soon as the app opens.
Other interactions can be created such that, when a user action takes place in one component, it triggers
something to happen in another component. For instance, you might have a drop-down list that, when you
select an item from the list, populates text fields with other property values from that data service.
When the Interactions tab is selected and a component is selected, the Properties panel displays a list of
all interactions associated with the selected component at the top of the panel. This can be very helpful in
determining what, exactly, is going on with each component in your app. To see the particulars about an
interaction definition, click that interaction in the list.
The + Additional Data Mapping button at the bottom of the panel is enabled whenever you are using a data
service that requires one or more input parameters. In this case, click the button to open a dialog box to
provide the input parameters.
The structure of interactions
Interactions typically consist of two parts: the Trigger Event and the Target Action.
The trigger event is something that happens within the app, usually by some action taken by a user. It can
also be something that happens elsewhere, for example, when the app first loads.
The target action is what happens when the trigger event occurs. For example, a user clicking a button (the
trigger event) can cause a data grid to load and display the data.

Infor Mongoose App Builder/App Hub User Guide | 21

About App Builder and the App Hub
Trigger events
Interaction trigger events include, but are not necessarily limited to these:
•
•
•
•
•
•
•
•
•

On Startup - This event is triggered automatically when the app launches.
On Load - This event is triggered when data is loaded from a data source without the use of a Data Service.
On Click - This event is triggered by the user clicking the mouse on the component.
On Changed - This event is triggered by the user changing the value of an item or object.
On Checked - This event is triggered by the user selecting a check box.
On Not Checked - This event is triggered by the user clearing a check box.
On Item Updated - This event is triggered when the user updates an item.
On Selection Changed - This event is triggered when the user selects an item or object in the component.
<Data Service> has run (On Load) - This event is triggered as soon as the data service has been run.

Target actions
The target actions available always depend on what component is associated with the trigger event and on
what component is the target of the action. Interaction target actions include, but are not necessarily limited
to these:
Note: When an action that is described in this list as either <Data Service> or (No Data Service) has a data
service assigned to it, the "data service" text is replaced by the name of the assigned data service; for example,
Run Usernames.
•
•
•
•

Clear - This action clears the contents of the target component.
Run <Data Service> - This action runs the data service associated with the target component and gets
all appropriate values.
Get (No Data Service) - This action returns the data for a single record.
List (No Data Service) - This action returns the data for multiple records.

About Style settings
In App Builder projects, the Style settings essentially determine how components and the content on them
are displayed.
All basic components have Margin and Visibility options, except for the Flexbox, which has no Margin option.
The Margin options allow you to specify how much space can exist between the sides of the component and
the container in which it resides. You can set the margins for all sides to be the same or you can set each side
individually. This helps provide for precise positioning within the container.
The unit of measure for all margins is pixels.
The Visibility option determines whether the component is actually visible to the user or hidden. This option
can be helpful when you want a component to do something like perform a calculation, but you do not want
or need for the user to be able to see it.
Most components have additional style settings that you can make, depending on the specific component.
The Label component, for example, has additional style settings for Typography and (font) Color; while the
Datepicker component has a single additional style setting for Date Formats.

Infor Mongoose App Builder/App Hub User Guide | 22

About App Builder and the App Hub

The App Builder canvas
The App Builder canvas is that part of the workspace in which you actually construct your project/app. It can
be divided into multiple Flexboxes and have multiple sets of boundaries.
You construct App Builder projects/apps by selecting components and then placing them within the Flexboxes
that have been defined for the project.
To make it possible for your app to adapt to different devices (for example, laptops and mobile phones), you
make boundary settings that the app then responds to automatically to display the correct layout for each
device.

About Flexboxes
Flexboxes are defined areas within the App Builder canvas, in which you place and configure the components
that make up your app. Initially, the number and placement of the Flexboxes in your project depends on the
project template you choose as a starting point. You can, however, modify, add, or delete the Flexboxes in
your project.
You can make a number of settings that control how Flexboxes display and behave. Among other things, you
can make these settings:
•
•

Set the orientation to be horizontal or vertical.
Make the width or height relative to adjacent Flexboxes (using ratios) or more absolute, based on pixel
dimensions.
Only the width or height of a particular Flexbox is altered, depending on the orientation of the Flexbox,
whether it is horizontal (width) or vertical (height).

•
•
•
•
•

Determine what App Builder does with any content that overflows the defined viewing area.
Set a background color that is different from the default.
Specify how content within the Flexbox is aligned, horizontally and vertically.
Determine the amount of padding between the content and the sides of the Flexbox.
Subdivide the area inside the Flexbox into rows and columns.

About App Builder boundaries
Boundaries are used in App Builder projects to specify those widths at which the display changes to
accommodate various-sized displays. The primary purpose of boundaries is to allow App Builder apps to
automatically adjust to different devices.
The basis of a boundary is the width of the app display. You can think of a boundary setting as the maximum
width at which a particular display layout is used. Once the width of the display increases beyond that point,
the next boundary layout, if any, is used.
There are two ways to establish boundaries in your project: You can use any of a set of predefined standard
boundaries in App Builder; or you can set your own custom boundaries. The predefined standard boundaries
include settings for these popular devices:
•
•

Standard laptop and computer monitors
Popular mobile phone devices

Infor Mongoose App Builder/App Hub User Guide | 23

About App Builder and the App Hub
•

Common Infor OS Portal widgets

Boundaries are set in the horizontal (width) ruler above the canvas area. Each boundary setting is represented
by a boundary marker:

You can test how boundary definitions and the components within display using the slider and the boundary
markers in the ruler.
Understand that the more boundaries you add, the more complicated it becomes for you to control all the
associated settings. This does not, however, affect the performance of the app.

About the App Builder Repository
In this repository, you can view and access the data services available to you. You can also view information
about any composite components you might have created.
Use the App Builder Repository to perform these activities:
•
•
•
•
•
•
•

Create, locate, edit, and delete data services.
Create data services from APIs or from Mongoose IDOs.
Import and export data services.
Configure data services manually.
View information about composite components.
Export composite component data to a file.
Import composite component data from a file.

About responsive design in App Builder
"Responsive design" can be a key aspect of apps built in App Builder. Your apps can be designed and built to
adapt automatically to different types of devices on which it might be used. App Builder has several tools and
features that can enable you to make your app responsive to multiple devices and screen sizes. Most of these
features operate automatically, based on the width of the screen on the device being used to access your
App Builder app.
By "responsive design", we refer to the practice of creating your app in such a way that it can adjust
automatically to the different sizes and different orientations of the devices on which it is to be used. So, for
example, on a regular laptop screen, the display might be 1440 pixels wide and use a "landscape" orientation.
On a mobile device, such as a smart phone, the display might be 360 pixels wide and use a "portrait" orientation.

Infor Mongoose App Builder/App Hub User Guide | 24

About App Builder and the App Hub
App Builder makes it easy to provide for a variety of layouts from the same basic app, each one designed to
work on a different device size and orientation. This is done by setting one or more "boundaries" for the app,
with each boundary triggering a different size and/or layout.
Built-in responsiveness of components
Each basic component delivered with App Builder is designed to be responsive in itself. This means that it
can automatically adapt to the size of your device's screen. This is the default responsive behavior. However,
as each component can have its own responsive behavior, we recommend that you test and evaluate the
result on any target device types or simulators.
To use an alternative to the built-in responsive behaviors in App Builder, you must modify the "boundaries"
and component settings, where you can manipulate the orientation, visibility, style, size, or other settings of
each component. This enables you to change the look and feel of your app on different devices, without
having to create different versions of your app.
Simulating different screen sizes
To simulate the effect of various screen display sizes, App Builder uses a mechanism called a "boundary."
Boundaries essentially define the various widths at which the app display changes. For example, you might
define one set of boundaries for mobile phones, another set for tablets, another set of boundaries for a laptop
display, and yet another for a standard computer monitor display. Each of these boundary settings can cause
the display for your app to change automatically in response to the device on which it is operating—without
you having to design different versions of your app.
The controlling factor for the change from one boundary display to another is the width of the screen on the
target device. A boundary set at 320 pixels, for example, might be appropriate for a mobile phone, while a
boundary set at 1440 pixels would be more appropriate for a standard monitor.

About the App Hub UI
The App Hub is the repository where App Builder apps are published, activated, and made available to end
users. In Infor OS Portal, App Hub is available as an app or widget.
When you first open the App Hub, it appears as a plain screen with the Infor logo in the center and the label
Infor App Hub. Use the Menu in the App Hub toolbar to access its functions.
Expand the Menu by clicking the icon and label. The expanded menu displays a list of all the App Builder apps
that are available for your use. To launch an app, click the app name in the list.
Administrator operations
At the bottom of the Menu panel, is a button labeled Administration. This button allows you to access the
administrative operations of the App Hub.
Note: Only authorized App Hub administrators can access these operations.
Authorized administrators can perform any of these tasks:

Infor Mongoose App Builder/App Hub User Guide | 25

About App Builder and the App Hub
•
•
•
•
•
•
•

View information about all apps in the App Hub.
Activate or deactivate apps.
Specify who has access to specific apps.
Add tags to apps.
Import and export apps.
Deactivate apps.
Delete apps.

Infor Mongoose App Builder/App Hub User Guide | 26

Using App Builder (the Designer)

Chapter 2: Using App Builder (the Designer)

When talking about App Builder, essentially we are talking about the App Builder "Designer", in which you
design and create apps.
In the Designer, you conduct these basic operations:
•
•
•
•
•
•
•

Creating the app project
Defining and using the basic layout (Flexboxes)
Creating and managing components
Working with composite components
Managing data services
Performing additional project-level activities
Setting boundaries for responsive design

Infor Mongoose App Builder/App Hub User Guide | 27

Basic project-level activities

Chapter 3: Basic project-level activities

The basic project-level activities you can perform in App Builder all start at the Projects dashboard.
From the dashboard, you can perform these activities:
•
•
•
•
•
•
•
•
•
•

Create a project
View project information
Search for a project
Edit a project
Launch a project
Export a project
Import a project
Duplicate a project
Publish a project
Delete a project

Creating an App Builder project
Creating an App Builder project typically involves naming the project, selecting a basic layout, and optionally,
assigning tags to the project to make it easier to locate.
To create an App Builder project, use this procedure:
1

Launch App Builder.
This opens to the Projects dashboard.

2
3
4

In the banner at the top of the dashboard, click + New Project.
On the New Project page, provide a Project Name.
Optionally, provide a Description.
This description is used only internally, typically to help identify the app and its use. It displays only on
the project panel in the Projects dashboard.

5

Optionally, assign any desired Tags.
See About tags on page 29.

6
7

From the Page Templates section, select the basic template you want for your app.
In the banner at the top of the page, click Create.

Infor Mongoose App Builder/App Hub User Guide | 28

Basic project-level activities
App Builder automatically opens the project in the workspace.

About page templates
App Builder page templates are predefined layouts that can provide a basic start in creating a project.
When you first start to create an App Builder project, Page Templates is one of the options provided on the
New Project page of the Designer. These templates are designed to provide a good variety of basic layouts
for you to use in creating your app.
Each page template consists of a number of Flexbox components arranged in common layout patterns. Pick
the patterns that most closely matches the basic layout you want to use for your app. You can later rearrange
to the basic layout, add or remove Flexboxes, and change the dimensions of the Flexboxes according to your
app's needs.

About tags
Tags are markers that you can add to projects primarily to help later in searching for particular apps in App
Builder. They are particularly useful when you have a great number of apps in your App Hub.
We recommend that, when you create a project, you also add some tags. Tags can be created based on the
type of app it is, the type of device it is designed to run on, or any other criteria by which you might classify
your app.

Creating and assigning tags
When creating an App Builder project, it is generally a good idea to assign one or more tags to the project.
These tags are used later to help search for and identify the project.
Note: To view a list of all the tags currently defined in App Builder, use the drop-down field on the dashboard
that displays the default value All. This drop-down list displays all tags currently defined.
To create and assign a tag for use with an App Builder project:
1

After beginning the process of creating a project, on the New Project page, click inside the Tags field.
Note: If you are creating or assigning a tag to an existing project, you can assign tags using the Project
Settings option from the ellipses (•••) menu in the workspace. This opens the Project Settings dialog
box, which contains a Tags field that is identical in function to the one on the New Project page.

2

In the Tags field, type the text for the tag.
If you already have any tags defined in your system, and if the text you are typing matches any of them,
they display automatically below the Tags field as you type. If no tags match the text you type, that
displays says: No Results.

3

Perform one of these actions:

Infor Mongoose App Builder/App Hub User Guide | 29

Basic project-level activities
•
•

If App Builder identified any tags that you can reuse, select the tags and then press Enter.
If App Builder did not identify any existing tags (that is, it displays No Results), click the plus (+)
button. This adds the tag to the list of existing tags and to your project.

Whichever action you perform, App Builder adds the tags to the list of tags assigned to the project and
displays them above the Tags field.

Using tags
Tags are used in App Builder projects primarily to help search for and locate projects. This is most helpful if
you have a good number of projects in your App Builder environment.
To use tags to locate projects:
1 In the Projects dashboard, click the down arrow next to the unlabeled drop-down list.
The drop-down list displays all the tags currently defined.
2
3

From the list, select the tag to use for filtering.
Click the plus (+) button next to the drop-down list.

App Builder automatically locates and displays the panels for all projects that have that tag assigned. At the
same time, it hides all other project panels.
To apply multiple tag filters, repeat this procedure and add additional tags. If/When you do this, App Builder
uses Boolean OR logic and displays the panels for any projects that have either tag assigned.
To remove a tag from filtering, click the X button in the tag.

Viewing project information
Each project in your App Builder environment has its own display panel on the Projects dashboard. Each of
these panels displays a variety of information about the project.
The information and options that display depend on whether your cursor is hovering over the panel.
•

When your cursor is NOT hovering over the panel, the panel displays this information:
•
Name of the project
•
A thumbnail of the project UI, if you have created one
If you have not created a thumbnail, then a generic default image displays.
•
•
•
•

The user ID of the individual who created the project
The date and time at which the project was first created
The user ID of the last individual who made changes to the project
The date and time at which the project was last changed

If the project is the one currently open on your system, this is also indicated.
•

When your cursor is hovering over the panel, the panel displays this information:
•
Name of the project

Infor Mongoose App Builder/App Hub User Guide | 30

Basic project-level activities
•
•

The description of the project (from Project Settings), if one has been created.
At the bottom, a series of icons that provide options to delete, duplicate, export, launch, or publish
the project.

Searching for and locating projects
If your work environment has a great number of App Builder projects, it can be challenging to find the one
you want to work on. The Projects dashboard offers a number of alternatives to help you search for and
locate the project you want.
These are the alternatives you can use to locate projects:
•
•

•

All Projects drop-down list - By default, this drop-down list is set to display all the projects in your
environment. To display only the projects that you created, select Created by me.
Search - If you know at least part of the project's name, you can search on the name by clicking the
magnifying glass icon in the banner. Then start typing the project name. As you type, App Builder displays
all projects whose names contain that set of characters.
By order - The Projects dashboard provides these options for sorting the project panels:
•
The first option displays By Name as the drop-down list default. This option displays the project
panels in alphanumeric order of the project names.
The Changed Date and Created Date options display the panels in the order in which the projects
were last changed and the order in which theh projects were created, respectively.
•

•

The second option allows you to sort the project panels in ascending or descending order (for example,
in alphanumeric order or reverse alphanumeric order, when sorting by name).
By tags - See Using tags on page 30.

Editing an App Builder project
When working on App Builder projects, it is likely that, at some point, edits to an existing project will be
needed.
To edit an existing App Builder project, use this procedure:
1
2

On the Projects dashboard, locate the project you want to edit.
Click the project panel for that project.
App Builder automatically opens the project in the workspace.

After making the required edits, remember to save the project before exiting.

Infor Mongoose App Builder/App Hub User Guide | 31

Basic project-level activities

Launching an App Builder project
In terms of what happens, "launching" an App Builder project is quite different from "opening" an App Builder
project. When you "open' a project, the project is opened in the App Builder Designer workspace, and you
can edit it. When you "launch" a project, you open it as it is intended to be used and can test its functionality.
To launch an App Builder project, use this procedure:
1
2

On the Projects dashboard, locate the panel for the project you want to launch.
With the cursor hovering over the panel, locate and click the Launch icon.

The App Builder app launches.
Note: You can also launch the project from within the project workspace, using the Launch icon in the toolbar.

Exporting an App Builder project
There might be times when you must continue with an App Builder project in an environment other than
where it was begun. In such cases, you can export the project as an *.iap file, and then import it into the new
environment.
To export an App Builder project, use this procedure:
1
2

On the Projects dashboard, locate the project you want to export.
With the cursor hovering over the project panel, locate and click the Export icon.
This opens the Export Project dialog box, with the project name and the current date automatically
populating the Name field. You can rename the file as desired.

3

Click Export.

App Builder saves the project information to a proprietary *.iap-formatted file. This file can be found in the
local Downloads folder. You can then transport and import the project into a different App Builder environment.

Importing an App Builder project
App Builder projects can be imported as projects exported from other App Builder environment or as apps
from an App Hub. To be imported into App Builder, the original project or app must have been exported as
an *.iap file.
Note: Projects exported from App Builder and apps exported from the App Hub both use an *.iap extension.
This means that you can import into App Builder a project/app that has been exported from either environment.
Apps that have been exported from an App Hub do not lose their status as published apps when they are
imported into App Builder and modified.
1
2

In the App Builder Projects dashboard, click to expand the More (•••) menu.
From the context menu, select Import Project.
Infor Mongoose App Builder/App Hub User Guide | 32

Basic project-level activities
3

In the Import Project dialog box, perform one of these actions:
•
From a Windows Explorer window, locate and select, and then click and drag the *.iap file for the
app you want to import. Drop the file onto the dialog box where it says "Drop File Here".
•
Click Select File and use the Open dialog box to navigate to, select, and open the *.iap file for the
app you want to import.

4

In the Import Project dialog box, click Upload.

App Builder imports the file and adds it to the list of projects on the dashboard.
If an earlier version of the project is already in App Builder, you are prompted either to import and replace
the version already in App Builder, to import the file as a new project, or to cancel the operation.

Duplicating an App Builder project
There might be times when you want to use one App Builder project as the basis of another project. If this is
the case, you can duplicate the original project and then use the duplicate for your new project.
To duplicate an App Builder project, use this procedure:
1
2

On the Projects dashboard, locate the project you want to duplicate.
With the cursor hovering over the project panel, locate and click the Duplicate icon.
This opens the Duplicate Project dialog box, with the project name and an appending numeral
automatically populating the Name field. You can rename the file as desired.

3

Click Duplicate.

App Builder creates an exact duplicate of the original project with the new name.

Publishing an App Builder project (app)
Once you are finished designing and building your app (project) in App Builder, it must be published to the
App Hub and activated before users can access and use it.
This is a two-step process. The first step is to publish the app.
1
2
3

On the Projects dashboard, locate the project to publish.
With the cursor hovering over the project panel, locate and click the Publish icon.
When prompted, click Yes.

The app is now in the App Hub and must be activated before it is available for use.

Infor Mongoose App Builder/App Hub User Guide | 33

Basic project-level activities

Deleting an App Builder project
If, for whatever reason, you want to delete a project from the App Builder Designer, you can.
To delete an App Builder project, use this procedure:
1
2
3

On the Projects dashboard, locate the project you want to delete.
With the cursor hovering over the project panel, locate and click the Delete icon.
When prompted, click Yes.

App Builder deletes the project.

Infor Mongoose App Builder/App Hub User Guide | 34

Additional project-level activities

Chapter 4: Additional project-level activities

In addition to the basic project-level activities described in the previous section, App Builder provides for a
variety of less basic project-level activities and options that you might or might not want to use.
These additional project-level activities and options are available from the "More" menu, accessed when you
click the ••• icon on the far right side of the App Builder Toolbar. These are the options on this menu:
•
•
•
•
•
•
•
•
•

Project Settings
Create App Thumbnail
Copy To Form
Repository
Keyboard shortcuts
Refresh
Language
Translations
Manage URL Variables

For information about these options, see the Related Topics.

Making or editing project settings
Typically, most project settings are made at the time you create the project. Some of these are created
automatically, just in the process of creating the project. However, you might decide later you want to change
one or more of the basic project settings. Use the Project Settings option on the "More" menu to make those
changes.
To make or edit basic project settings, use this procedure:
1

With your project open, select Project Settings from the "More" (•••) menu.
App Builder opens the Project Settings dialog box.

2

In the Project Settings dialog box, change any or all of these options:
Project Name
This is the name given to the project when it was created. It is the name that displays with the project
in the Projects dashboard. You can change it however you want. End users of the app do not see this
name.

Infor Mongoose App Builder/App Hub User Guide | 35

Additional project-level activities
App Header Title
This is the text that displays in the title bar of the project.
Description
This is the optional description provided when the project was first created (if any). Use this field to
provide a brief description of what the app is designed to do, or to otherwise identify it.
This description appears only on the project panel in the Projects dashboard. It is not displayed to end
users.
Tags
This field displays any tags currently attached to the project, if any. You can use it to create and add
tags.
Version
By default, this field is set to 1.0. You can change it to whatever you want.
Note: This value does not update automatically. To reflect a new version of your app, you must change
it manually. We recommend doing this as soon as possible after publishing and activating your app in
the App Hub.
This version number displays in the App Administration page of the App Hub.
3

When you have the settings the way you want them, click Save.

Creating an app thumbnail image
You can create a thumbnail image of your App Builder project for use on the Projects dashboard. This image
is used only on the project's display panel on the dashboard, as a means to help identify it.
To create an app thumbnail image for use on the dashboard, use this procedure:
1
2

With your project open, select Create App Thumbnail from the "More" (•••) menu.
Go to the Projects dashboard and verify that the project panel for your app now displays a thumbnail
image of the project.

Converting a project to a Mongoose form
You can convert your App Builder project to be a Mongoose form. After converting the project to be a Mongoose
form, however, you cannot reverse the process from Mongoose form back to App Builder project.
1
2

With your project open, select Copy To Form from the "More" (•••) menu.
When the Copy To Mongoose Form dialog box opens, provide a New Form Name.
Note: This name becomes both the Name and the Caption for the new form.

Infor Mongoose App Builder/App Hub User Guide | 36

Additional project-level activities
3

Click Copy.

App Builder creates a Mongoose form that is, as closely as possible, the Mongoose equivalent of the original
App Builder project. You can now open and use or modify the new form in Mongoose, using the same
configuration used by App Builder.

Using the App Builder Repository
The App Builder Repository is a central storage area used to create, store, and access data services, composite
components, and locally created IDOs (Intelligent Data Objects) for use by App Builder projects. As such, it is
available to all App Builder projects as a resource center.
Access the App Builder Repository by clicking the "More" menu icon (•••) at the far right side of the Toolbar.
The open Repository has three tabs, one for data services, one for composite components, and one for IDOs.

Working with data services in the Repository
One of the primary uses of the App Builder Repository is to provide storage for and access to data services
used in App Builder projects.
Use the Data Services tab to perform these activities:
•
•
•
•
•
•
•

View information about data services already in the Repository.
Add (store) data services to the Repository, either by creating them using the wizard, importing them
from files, or configuring them manually.
Locate a data service for use in your project.
Edit a data service already in the Repository.
Delete data services from the Repository.
Export data services to a file.
Test data services.

Viewing information about data services
One function of the App Builder Repository is to provide basic information about the data services stored
there.
To view this basic information about a data service, open the App Builder Repository and select the Data
Services tab.
This table lists and describes the information you can view:

Infor Mongoose App Builder/App Hub User Guide | 37

Additional project-level activities

Column

Description/Notes

Check box

The first column contains only check boxes. Use these check boxes to
select data services for further action.
To select (or deselect) all of the data services on the page, select the
check box in the header row.

Name

This column lists the data services by name.
To sort the list alphabetically by Name, click the up or down arrow
next to the column label.

Author

This column displays the App Builder user name for the individual who
created the data service and placed it in the Repository.
To sort the list alphabetically by Author, click the up or down arrow
next to the column label.

Created

This column displays the date that each data service was created.
To sort the list chronologically by creation date, click the up or down
arrow next to the column label.

Description

This column displays the description of the data service, if any, that
was provided by the author.

Suite context

This column displays the product line, or "suite", used to create the
data service. This is determined when the data service is first created
and cannot be changed.

Action

To test the data service (usually before deciding whether you want to
use it), click Test Data Service. This action launches the Test Data
Service dialog box, which typically prompts you for input parameters
required to test the data service and then displays the appropriate
output parameters.

Note: To view more detailed information about a data service, click Edit to launch the Edit Data Service
dialog box.

Locating a data service
If the App Builder Repository has a great number of data services in it, you can use the Search function to
more easily locate the one you want.
To locate a particular data service in the Repository, use this procedure:
1

With the App Builder Repository open, locate the Search field on the Data Services tab.
(It is the field with the magnifying glass icon.)

2

Type in the string of characters on which to search.
Although the App Builder Respository search does not allow for the use of wild cards, you can search on
a partial string, such as a part of a word.

Infor Mongoose App Builder/App Hub User Guide | 38

Additional project-level activities
3

Press ENTER.

App Builder returns a list of all data services that contain the string you typed.

Creating or adding a data service in the Repository
In App Builder, there are two starting points from which you can create or add a data service for a project:
You can add it first into the App Builder Repository and then import it from there into your project, or you can
add it directly into your project.
If you add the data service first into the App Builder Repository, it is initially saved only to the Repository. If
you then want to use it in your project, you can import it to your project from the Repository.
If you add the data service directly to your project, it is saved only as part of your project. If you later decide
to put it into the App Builder Repository, you must first export it from your project as a file and then import
that file into the Repository. In the case of adding a data service by file import, however, you might be able
to simply import the same data service file from the same source you imported it into your project.

Creating a data service in the Repository
To create a data service in the App Builder Repository using the Wizard, follow this procedure:
Note: The procedure to create a data service in a project using the Wizard is similar, but not exactly the same.
See Creating a data service within a project on page 64.
1
2
3

With the App Builder Repository open, select the Data Services tab.
Click + Add Data Service and then, from the drop-down list, select Create from Wizard.
In the Create from Wizard dialog box, select the source for the data service:
•
ION API - This option allows you to select from among the many API Gateway or ION APIs available
to you, based on your licensed product suites.
•
Local IDO - This option allows you to select a Mongoose IDO.

4

Click Next.
Your next steps at this point depend on which option you selected in the previous step. If you selected:
•
ION API - See Configuring an ION API data service on page 65.
•
Local IDO - See Configuring a Local IDO data service on page 66.

Adding a data service by file import
To import a data service to the App Builder Repository, follow this procedure:
Note: The procedure to import a data service directly into a project is similar, but not totally the same. See
Adding a data service into a project by file import on page 68.
1
2
3

With the App Builder Repository open, select the Data Services tab.
Click + Add Data Service.
From the drop-down list, select Import from File.

Infor Mongoose App Builder/App Hub User Guide | 39

Additional project-level activities
4
5
6

In the Import Data Services dialog box, click the folder icon.
Use the Open dialog box to navigate to, select, and "open" the data service (*.iads) file.
In the Import Data Services dialog box, click Import.

App Builder adds the the data service to the list of available data services.

Editing a data service
Should you find the need to modify a data service once it has been created, you can access and edit that data
service in the App Builder Repository.
To edit a data service in the App Builder Repository, use this procedure:
1
2
3

On the Data Services tab, select the data service to edit.
Click Edit.
In the Edit Data Service dialog box, make whatever changes are needed.

Removing a data service from the Repository
If a data service is obsolete or is no longer needed, you can remove it permanently from the App Builder
Repository.
Note: Deleting a data service from the Repository does not affect the data service if it is in use in a project.
To delete a data service from the App Builder Repository, use this procedure:
1
2
3

On the Data Services tab, select the data service to delete.
Click Delete.
When prompted, confirm the deletion.

Exporting a data service to a file
You can export a data service in the form of a specially formatted file. You can then copy this file to another
system or import it into another App Builder project.
Note: You can also export a data service from your App Builder project to a file. The procedure is similar but
not exactly the same. See Exporting a data service (from a project) on page 70.
To export a data service in the App Builder Repository to a file, use this procedure:
1
2
3

On the Data Services tab, select the data service to export.
Click the ••• icon on the tab toolbar and select Export to File.
In the Export Data Services dialog box, provide a name for the file.
If you do not provide a name, App Builder uses the current date as the filename.

4

Click Export.

Infor Mongoose App Builder/App Hub User Guide | 40

Additional project-level activities
App Builder saves the data service file with an *.iads extension to your Downloads folder.

Testing a data service
To verify that the data service does what you expect, you can test the operation of a data service in the App
Builder Repository. When tested, the data service returns JSON code that you can then copy and paste into
your project.
To test a data service in the App Builder Repository, use this procedure:
1
2
3

On the Data Services tab, select the data service to test.
In the same row as the data service, click Test Data Service.
In the Test Data Service dialog box, if prompted, provide at least the mandatory input parameters, as
well as any other parameters you want to supply.
If there are any required or optional input parameters needed by the data service, App Builder prompts
you for those parameters. Any input parameter marked as Mandatory must be supplied, or the data
service does not run.
Some data services do not require any input parameters and can just be run.

4

Click Run Data Service.

In the Response Body field, App Builder displays the JSON code that is returned as a result of running the
data service. You can then copy the Response Body data to the system clipboard for use elsewhere.

Working with composite components in the Repository
One of the primary uses of the App Builder Repository is to provide storage for and access to composite
components used in App Builder projects.
Use the Composite Components tab to perform these activities:
•
•
•
•
•

View information about the composite components already in the Repository.
Locate a composite component for use in your project.
Delete composite components from the Repository.
Import a composite component from a file.
Export a composite component to a file.

Viewing information about composite components
One function of the App Builder Repository is to provide basic information about the composite components
stored there.
To view this basic information about a composite component, open the App Builder Repository and select
the Composite Components tab.
This table lists and describes the information you can view:

Infor Mongoose App Builder/App Hub User Guide | 41

Additional project-level activities

Column

Description/Notes

Check box

The first column contains only check boxes. Use these check boxes to
select composite components for further action.
To select (or deselect) all of the composite components on the page,
select the check box in the header row.

Name

This column lists the composite components by name.
To sort the list alphabetically by Name, click the up or down arrow
next to the column label.

Author

This column displays the App Builder user name for the individual who
created the composite component and placed it in the Repository.
To sort the list alphabetically by Author, click the up or down arrow
next to the column label.

Created

This column displays the date that each composite component was
created.
To sort the list chronologically by creation date, click the up or down
arrow next to the column label.

Data Services

This column displays the number of data services, if any, that are included as part of each composite component.

Interactions

This column displays the number of interactions, if any, that are included as part of each composite component.

Locating a composite component
If the App Builder Repository has a great number of composite components in it, you can use the Search
function to more easily locate the one you want.
To locate a particular composite component in the Repository, use this procedure:
1

With the App Builder Repository open, locate the Search field on the Composite Components tab.
(It is the field with the magnifying glass icon.)

2

Type in the string of characters on which to search.
Although the App Builder Respository search does not allow for the use of wild cards, you can search on
a partial string, such as a part of a word.

3

Press ENTER.

App Builder returns a list of all composite components that contain the string you typed.

Removing a composite component from the Repository
If a composite component is obsolete or is no longer needed, you can remove it permanently from the App
Builder Repository.

Infor Mongoose App Builder/App Hub User Guide | 42

Additional project-level activities

Note: Deleting a composite component from the Repository does not affect the composite component if it
is in use in a project.
To delete a composite component from the App Builder Repository, use this procedure:
1
2
3

On the Composite Components tab, select the composite component to delete.
Click Delete.
When prompted, confirm the deletion.

Importing a composite component into the Repository
To make composite components readily available to App Builder projects, you can import it into the Repository.
From there, you can import it into any project.
To import a composite component into the Repository, use this procedure:
1

With App Builder open, select Repository from the More (•••) menu.
You can access this toolbar menu either from the Projects dashboard or from within an open App Builder
application.

2
3
4
5
6

In the App Builder Repository, select the Composite Components tab.
Click Import from File.
In the Import Composite Components dialog box, click the folder icon.
In the Open dialog box, navigate to and "open" the *.iac file that has the composite component to import.
In the Import Composite Components dialog box, click Import.

App Builder imports the composite component and adds it to the list of composite components available in
the Repository.

Exporting a composite component
You can export a composite component in the form of a specially formatted file. You can then copy this file
to another system or import it into another App Builder project.
To export a composite component in the App Builder Repository to a file, use this procedure:
1
2
3

On the Composite Components tab, select the composite component to export.
Click the ••• icon on the tab toolbar and select Export to File.
In the Export Composite Components dialog box, provide a name for the file.
If you do not provide a name, App Builder uses the current date as the filename.

4

Click Export.

App Builder saves the data service file with an *.iac extension to your Downloads folder.

Infor Mongoose App Builder/App Hub User Guide | 43

Additional project-level activities

Working with local IDOs
One of the primary uses of the App Builder Repository is to provide storage for and access to IDOs (Intelligent
Data Objects) used in App Builder projects.
Use the IDOs tab to perform these activities:
•
•
•
•
•

View a list of all the local IDOs autogenerated or created by users.
Search for particular IDOs for viewing and processing.
Preview the IDO properties in tabular format.
Create a local IDO, using the Table & IDO Wizard.
Delete unused IDOs and their tables from the Repository.

Viewing information about local IDOs
One function of the App Builder Repository is to provide basic information about the local IDOs (Intelligent
Data Objects) stored there.
To view this basic information about an IDO, open the App Builder Repository and select the IDOs tab.
This table lists and describes the information you can view:
Column

Description/Notes

Check box

The first column contains only check boxes. Use these check boxes to
select IDOs for further action.
To select (or deselect) all of the IDOs in the list, select the check box
in the header row.

Name

This column lists the IDOs by name.
To sort the list alphabetically by Name, click the up or down arrow
next to the column label.

Created

This column displays the date that each IDO was created.
To sort the list chronologically by creation date, click the up or down
arrow next to the column label.

Description

This column displays the description of the IDO, if any, that was provided by its creator.

Project

This column displays the App Builder project for which the IDO was
created. This is determined when the IDO is first created and cannot
be changed later.

Action

To view detailed information about an IDO (usually before deciding
whether you want to use it), click the Show Table button for that IDO.
This action launches the Preview IDO Properties dialog box, which
lists all the properties and their attributes for the selected IDO.

Infor Mongoose App Builder/App Hub User Guide | 44

Additional project-level activities

Locating an IDO
If the App Builder Repository has a great number of local IDOs (Intelligent Data Objects) in it, you can use the
Search function to more easily locate the one you want.
To locate a particular IDO in the Repository, use this procedure:
1

With the App Builder Repository open, locate the Search field on the IDOs tab.
(It is the field with the magnifying glass icon.)

2

Type in the string of characters on which to search.
Use a string that you know exists in either the Name, the Description, or the Project column. Although
the App Builder Respository search does not allow for the use of wild cards, you can search on a partial
string, such as a part of a word.

3

Press ENTER.

App Builder returns a list of all IDOs that contain the string you typed.

Creating an IDO in App Builder
There are two basic ways in which IDOs can be found in the App Builder Repository: IDOs are available in the
Repository from any stock Mongoose IDO you might employ as part of a data service; and you can create your
own App Builder-based IDOs, using the Table & IDO Wizard.
This topic is for the creation of your own IDOs and their associated tables, using the Table & IDO Wizard.
1
2
3

Access the App Builder Repository and select the IDOs tab.
Click Create from Wizard.
In the Table & IDO Wizard, provide a Name for your IDO.
We recommend using a name that will help you and other users be able to identify its purpose.

4

In the Table Alias field, provide an alias for the IDO table.
This alias is a unique, valid SQL identifier for this table. It is used to reference the table in join specifications
and in generated SQL.

5

From the Project drop-down list, select the IDO project with which the IDO and table are to be associated
or included.
Note: This value refers to "IDO Projects" as defined and used in Mongoose, and not to individual App
Builder projects.

6
7
8

Optionally, provide a description that you and others can use to identify the IDO and its use.
Click Next.
On the next page of the wizard, define the properties to be included in the IDO and table:
a To add a property, start by clicking the plus (+) icon.
b On the left side of the Create Properties page, provide values for these property attributes:
Name
(Required) Provide a name for the property.

Infor Mongoose App Builder/App Hub User Guide | 45

Additional project-level activities
The property name is case-sensitive and cannot contain spaces or most special characters. The
rules for special characters are the same as for SQL tables.
Data Type
(Required) From the drop-down list, select the type for the data to be used by this property.
Property Length
Specify the maximum number of characters to be allowed in this field.
Note: This field is disabled for some data types.
Options
Select the Primary Key, Required, and Read Only options as appropriate for this property.
Repeat these substeps for each additional property you want to define for use.
9 Click Next.
10 Use the Summary page of the wizard to review your settings and definitions of the IDO and table
11 When you are satisfied that it is all correct, click Finish.
App Builder generates the IDO and table and adds it to the App Builder Repository.
Note: When the IDO and table are generated, App Builder also creates additional properties that are required
for use by the IDO in Mongoose.

Deleting a local IDO
In the event that you have an IDO that you no longer use or want, you can delete it from the App Builder
Repository. At the same time, you delete the table created for its use.
Caution: Be aware that any IDOs and their tables that you delete from the App Builder Respository are no
longer available to any App Builder project using it. This will break any App Builder app that uses it. So, before
you delete a local IDO and its table, you should verify that none of your App Builder projects are using it.
1
2
3
4
5

Access the App Builder Repository and select the IDOs tab.
Locate the IDO and table you want to delete. and use the check box to select it.
Optionally, click Show Table and verify in the Preview IDO Properties dialog box that it really is the IDO
and table you want to delete.
In the toolbar, click Delete.
When prompted, click Yes.

Viewing keyboard shortcuts
For those who prefer to use keyboard shortcuts, App Builder provides a dialog box that lists the keyboard
shortcuts available for use in App Builder.

Infor Mongoose App Builder/App Hub User Guide | 46

Additional project-level activities

Note: At this time, you cannot create or assign your own keyboard shortcuts in App Builder.
To view the list of keyboard shortcuts available in App Builder, use this procedure:
1

With your project open, select Keyboard Shortcuts from the "More" (•••) menu.
App Builder displays the Keyboard Shortcuts dialog box.

2

To return to your project, click OK.

Refreshing the workspace
At times, you might have concerns about the data being presented or used in your App Builder project. For
this reason, you might want to refresh the workspace display so as to see the actual current state of the
project.
To refresh the workspace display: With your project open, select Refresh from the "More" (•••) menu. App
Builder clears the project workspace and reloads it with the current saved data.

Specifying a project language
Before you can see the effects of any translated strings in your App Builder project, you must specify the
availability of the language for the project.
To specify the availability of a language for an App Builder project, use this procedure:
1
2
3

With the App Builder project open, click the "More" menu icon (•••).
From the drop-down menu, select Language.
From the list of languages, specify the one you want to use in your project.
Note: The language you select from this list is applied only to those components or global strings for
which you provide translations. Components or global strings that do not have translations specified for
that language use the default language, U.S. English.

Creating and using translations
Before you can see the effects of any translated strings in your App Builder project, you must both: specify
the availability of the language for the project; and provide the translations for any components or global
strings you want to be translated in the UI.
To create a translated string for an App Builder component or component element, use this procedure:
1

With the App Builder project open, click the "More" menu icon (•••).
Infor Mongoose App Builder/App Hub User Guide | 47

Additional project-level activities
2

From the drop-down menu, select Translations.
App Builder launches the Translations dialog box.

3

In the Component column, select a Component and Property for which you want to provide a translated
string and click the plus (+) symbol.
App Builder expands the entry for that component and property, to reveal spaces for the field values of
both U.S. English and your other language. If an English string exists, that also displays.

4

Use the field provided for your language to supply the translated string.
If no English string yet exists, you can also supply that in the space provided.

Managing URL variables
URL variables can be used to accept inbound variable values from other applications into your App Builder
app. These variable values are passed by means of specially formatted URLs from the source application or
web service.
To be able to use a URL variable, first create the variable in your App Builder project. Once it has been created,
you can use it as needed on components. You can also test the variable to verify that it behaves as expected.
For the URL variable to actually perform as designed, you need another app that can pass variable values to
your App Builder app as part of the URL. For the URL to work correctly for these inbound variables, use this
syntax:
http(s)://serverNameAndPath:2443/WSWebClient/PlainForm.aspx?page=FormOnly&notitle=1&Form=AppBuilder
(SETVARVALUES(URLvariableName=URLvariableValue))

where:
•
•
•

serverNameAndPath is the root URL (server and domain).
URLvariableName is the name of the URL variable as it appears in your app.
URLvariableValue is the value to be used by the URL variable.

Note that, for this to work, you must include the Mongoose SETVARVARLUES keyword as a parameter in the
URL.

Creating a URL variable
Before you can use a URL variable in your App Builder project, you must create the variable.
To create a URL variable, use this procedure:
1
2
3

From the More (•••) menu, select Manage URL Variables.
In the Manage URL Variables dialog box, provide a name for the New URL Variable.
Optionally, provide a Description.
This description is for your own identification purposes only. End users do not see it.

Infor Mongoose App Builder/App Hub User Guide | 48

Additional project-level activities
4

Click the plus (+) icon.
App Builder adds the new variable to the list of Existing URL Variables.

5

Click Save.

You can now use this URL variable as the Source for a component value.

Using a URL variable
After creating a URL variable, you can use that variable in your App Builder project.
To use a URL variable, use this procedure:
1
2
3
4
5
6

In your App Builder project, add or select the component for which you want to use the URL variable.
On the Properties panel, select the Content tab.
Select the component property for which you want to use the variable.
From the Source drop-down list, select {URL Value}.
From the Data Field drop-down list, select the URL variable to use.
Optionally, enter a test value into the URL Value field and verify that it behaves as expected in the
component.
For a more comprehensive test of the URL variable, see Testing a URL variable on page 49.

Testing a URL variable
Before using a newly created URL variable in your App Builder project, it is a good idea to test it to verify that
it uses an inbound variable value as expected.
To test a URL variable, use this procedure:
1

Set up a component in your project to use the URL variable.
See Using a URL variable on page 49.

2
3

Save your project.
Launch the project (using the launch icon on the toolbar).
Your app opens in a new browser tab/window.

4

Copy the entire URL from the browser Address field.
The URL should be structured something like this: https://serverNameAndPath:2443/WSWebClient/Plain
Form.aspx?page=FormOnly&notitle=1&Form=AppBuilder(SETVARVALUES(arbitraryValue))

where:
•
•
5
6

serverNameAndPath is the root URL.
arbitraryValue is a string that App Builder inserts when the project is launched.

Paste the copied URL into your favorite text editor.
Replace the arbitraryValue part of the URL with a string that uses this syntax:

Infor Mongoose App Builder/App Hub User Guide | 49

Additional project-level activities
URLname=URLtestValue
where:
•
•

URLname is the name of the URL variable you want to test.
URLtestValue is the value you want that variable to use in your project.

7
8

Copy the altered URL to the clipboard.
In your browser, paste the altered URL into the Address field and click Enter.
The browser launches App Builder using the inbound variable value (URLtestValue) you set. App Builder
opens to the Projects dashboard.

9

Open your project.

When your project opens, the component using the URL variable displays/uses the value you provided in the

