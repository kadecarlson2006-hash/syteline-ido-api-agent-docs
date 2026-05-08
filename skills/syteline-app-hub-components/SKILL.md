---
name: "syteline-app-hub-components"
description: "App Hub usage and component settings reference - publishing, activating, access control, Button/Chart/Checkbox/Datagrid/Datepicker/Dropdown/Flexbox/Hyperlink/Image/Label/List/Lookup/RadioButton/Tabs/TextArea/TextBox/Timepicker settings, visibility, tips and tricks"
version: "1.0"
tags:
  - syteline
  - app-hub
  - components
  - settings
  - mongoose
---

# syteline-app-hub-components

Source: Infor Mongoose App Builder/App Hub User Guide, Release 2026.x

This segment is identified by the ellipses (•••) icon.

2

In the Standard Boundaries dialog box, select one or more standard boundaries to add to your project.
Note: You can theoretically select as many standard boundaries as you like. But be aware that the more
boundaries you add, the more complicated it becomes to manage the various boundaries and their
associated layouts. Depending on how you anticipate that your app will be used, you can often manage
fine with just two or three boundary settings.

3

Click OK.

App Builder adds the new boundary markers to the horizontal ruler in your project. You can now use these
markers to help define and test the various boundary settings for your project.

Setting custom boundaries
In addition to the standard boundary definitions included with App Builder, you can make your own custom
boundary settings wherever you want them.
1
2

With your project open, use the slider to position the right edge of the canvas area where you want to
set the boundary.
Click the uppermost segment of the slider.
This segment is identified by the plus sign (+) icon.

App Builder adds the new boundary marker to the horizontal ruler in your project. You can now use this
marker to help define and test the various boundary settings for your project.
Note: Alternatively, you can simply double-click in the horizontal ruler at the point where you want to create
the boundary. If you use this option, when the boundary marker is created, App Builder automatically moves
the right edge of the canvas area to that point.

Using boundaries in a project
Once you have added boundary markers to your project, you can use them to further develop and test how
your app is to behave on various devices.

Infor Mongoose App Builder/App Hub User Guide | 75

Working with boundaries
When you click a boundary marker, the canvas snaps to the boundary. This makes it easy to quickly check
the layout for each boundary setting. A second click on the same marker results in the canvas expanding to
fill the workspace area.
After you select and click a boundary marker, you can arrange the contents of the canvas in various ways for
that boundary setting. For instance, when switching from a laptop boundary marker to a smart phone boundary
marker, you might want to change the orientation of the AppPanel from horizontal to vertical. You might
want to move any buttons or other components from a left alignment to a center alignment. However you
want, you can change the layout and other display elements of your app for each boundary marker.
You can also click and drag the slider back and forth to verify that the app layout changes correctly at each
boundary marker.

Removing a boundary
If you should decide that you no longer want to use a particular boundary setting in your project, you can
remove the setting by deleting the boundary marker.
1

Hover the cursor over the boundary marker you want to remove.
App Builder highlights the marker setting in bright white and displays the delete icon as a white circle
enclosing a black x.
Note: If the marker is selected (that is, if it is blue), click the marker to deselect it.

2
3

Click the delete icon.
When prompted to confirm the deletion, click Yes.

App Builder deletes the boundary marker and any layout or orientation settings associated with it.

Infor Mongoose App Builder/App Hub User Guide | 76

Using the App Hub

Chapter 11: Using the App Hub

End users must be licensed and authorized to access and use the apps in the App Hub. App Builder
administrators must be licensed and authorized to perform administrative operations on App Builder apps.
Authorized administrators can perform any of these tasks:
•
•
•
•
•
•
•

View information about apps in the App Hub.
Activate or deactivate apps.
Specify who has access to specific apps.
Add tags to apps.
Import and export apps.
Deactivate apps.
Delete apps.

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

Activating a published app
Once an App Builder project has been published to the App Hub, it must also be activated before it is available
for use. The App Hub is available as a separate widget, if being accessed in Infor OS Portal, or as a separate
form if being accessed in Mongoose.
1

In the App Hub, expand the Menu.

Infor Mongoose App Builder/App Hub User Guide | 77

Using the App Hub
2

Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4

In the App Administration panel, select your App Builder project.
Optionally, in the project information panel on the right side, modify the fields as needed, before activating
the app.
Updates can include the app name, its description, tags, and who has access to the app.
Note: Typically, access is granted only to those in the AppBuilder User Group; that is, those who have
an APPBUILDER-User license assigned. However, if you do not specify access to at least one group, access
is granted to all.

5
6
7

If you made changes in the project information panel, click Save.
On the project panel for your app, click Activate.
Return to the App Hub and verify that your app appears in the list of available apps.

Modifying access to an app
Once an App Builder app is published to the App Hub, you can restrict who has access to the activated app.
You can restrict access either at the time that the app is activated or later, after the app has been activated.
You can also remove restrictions using this procedure. You can make these modifications either to activated
or to deactivated apps.
1
2

In the App Hub, expand the Menu.
Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4
5

In the App Administration panel, select the App Builder app you want to modify access to.
In the app information panel on the right side of the App Administration page, scroll down to the App
Access pane.
To add user roles or groups to the list of restricted App Access, click + Add Role.
From the list of Select Security Roles, select the check boxes associated with the user roles/groups you
want to add, and then click Select.

6

To remove user roles or groups from the list of restricted App Access, select the role/group to remove.
Then click Remove.

7

Click Save.

Importing an app into the App Hub
App Builder projects can be exported and imported as apps from one App Hub to another.

Infor Mongoose App Builder/App Hub User Guide | 78

Using the App Hub
To import an app into the App Hub, that app must first have been exported from an App Hub.
Note: Although projects exported from App Builder and apps exported from the App Hub both use an *.iap
extension, you cannot import into the App Hub an App Builder project that has not first been published to
the App Hub. Once the project has been published to the App Hub and then has been exported from the App
Hub, you can import the app/project into either App Builder or an App Hub.
1
2

In the App Hub, expand the Menu.
Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4

On the App Administration toolbar, click + Import App.
In the Import App dialog box, perform one of these actions:
•
From a Windows Explorer window, locate and select, and then click and drag the *.iap file for the
app you want to import. Drop the file onto the dialog box where it says "Drop File Here".
•
Click Select File and use the Open dialog box to navigate to, select, and open the *.iap file for the
app you want to import.

5

In the Import App dialog box, click Upload.

The App Hub imports the file and adds it to the list of apps in the App Administration page.
If an earlier version of the app is already in the App Hub, you are prompted to either replace the version
already in the App Hub or cancel the operation.
If the *.iap was exported from App Builder and not an App Hub, the import operation fails.

Exporting an app from the App Hub
App Builder projects can be exported and imported as apps from one App Hub to another. When they from
the App Hub, apps maintain their status as either active or inactive apps and retain all access settings.
1
2

In the App Hub, expand the Menu.
Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4
5
6

Click to select the app you want to export.
Click Export (at the bottom of the app's tile).
In the Export Project dialog box, optionally provide an alternate name for the file to be exported.
Click Export.

The App Hub exports the app, in the form of an *.iap file to the Download folder on your system.

Infor Mongoose App Builder/App Hub User Guide | 79

Using the App Hub

Deactivating an app in the App Hub
If, for whatever reason, you no longer want an activated app in the App Hub to be available to users, you can
deactivate it. Deactivating an app does not delete an app from the App Hub, it simply makes it unavailable
for use.
1
2

In the App Hub, expand the Menu.
Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4
5

In the App Administration panel, select your App Builder project.
On the project panel for your app, click Deactivate.
Return to the App Hub and verify that your app no longer appears in the list of available apps.

Deleting an app from the App Hub
If you no longer need or want an app in the App Hub, you can delete it. Deleting an app from the App Hub
does not delete it from the App Builder environment in which it was created.
1
2

In the App Hub, expand the Menu.
Select the Administration option.
Note: You can select this option only if you have administrative privileges in the App Hub.

3
4

In the App Administration panel, select the app to delete.
On the project panel for the app, click Delete.
Note: You cannot delete an active app. To delete an app that is currently activated, you must first
deactivate it, and then you can delete it.

5

When prompted whether to delete the project, click Yes.

The system deletes the app from the App Administration page and from the App Hub.

Infor Mongoose App Builder/App Hub User Guide | 80

Basic component settings

Appendix A: Basic component settings

The settings for basic components fall into three general categories: Content settings, Interactions, and
Style settings. In each case, all basic components have some settings in common, while other settings are
limited to individual components or a small group of basic components.
Common Content settings
In addition to the setting to Show Error, each basic component in App Builder (except for the Flexbox) has
at least one additional setting that can be made with respect to the component's content.
These additional settings are available on the Content tab of the Properties panel when the component is
selected on the canvas.
Most of these settings allow you to use a static value, a value derived from a data service, a value from another
component, or a value from the application itself as the Source for the setting. As well, they provide for a
Display Value or Display Field.
Common Interaction settings
Interactions typically consist of trigger events and trigger actions. Both the trigger event and the trigger action
depend primarily on what components are involved and what the interaction is designed to make happen.
See About Interactions on page 21.
Common Style settings
All basic components include a Visibility style setting that determines whether the component displays in
the workspace. If you wish, you can make the Visibility setting conditional, by selecting the lock icon next to
the basic setting switch.
See Setting conditional component visibility on page 119.
In addition to the setting for Visibility, each basic component in App Builder (except for the Flexbox) has an
another setting for Margins. Most components also offer at least one additional Style setting.
These additional settings are available on the Style tab of the Properties panel when the component is
selected on the canvas.
Additional settings for individual basic components
The rest of this group of reference topics lists the basic components with the additional Content, Interactions,
and Style settings available for each.

Infor Mongoose App Builder/App Hub User Guide | 81

Basic component settings

General settings by component
This section lists and describes the general settings for each basic component.

Button
This topic describes the options and limitations available when using a Button component.
Content settings
Content
This setting determines what text displays on the face of the button.
This component has no Content settings other than Source and Display Value.
Interactions
Trigger Event
The Button component can trigger these events:
•
On Click
•
On Load - This event might display as:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component;
OR
•

On Load (No Data Service), where no data service has been assigned to the component.

Target Action
The Button component can only run a target action when it has a data service assigned to it.
Style settings
Style
This setting has these options:
•
Default appearance options:
•
Primary
This option uses the default settings for the button's appearance, typically, white text on a blue
background.
•

Secondary
This option displays the button as black text on a gray background.

•

Tertiary
This option displays the button as dark gray text on a white background.

•

Disabled
This option disables the button.
To disable the button conditionally, click the lock icon next to this check box.

Infor Mongoose App Builder/App Hub User Guide | 82

Basic component settings
See Setting conditional component disablement on page 119.

Chart
This topic describes the options and limitations available when using a Chart component.
Content settings
Data Sets
This setting specifies what content, or data, is displayed on the actual chart.
To add data sets to be used by the chart, click Add. To use this option, you must first have a data service
selected. Clicking this button launches the Add Data Set dialog box, in which you can specify the data to be
used by the Chart component.
Header
This setting specifies what is to be displayed as the header (title) for the Chart component.
Category axis title
This setting specifies what is to be displayed as the title for Category axis of the chart.
Note: This does not display on all chart types.
Value axis title
This setting specifies what is to be displayed as the title for Value axis of the chart.
Note: This does not display on all chart types.
Interactions
Trigger Event
The Chart component can trigger only these events:
•
On Selection Changed
This event is triggered when a different value is selected on the component.
•

On Load: This event might display as one of these values:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component

Target Action
The Chart component can only run a target action when it has a data service assigned to it.
Style settings
Style
This setting has a number of options used to control how the chart and its data display. These are the options:
•
Show Header

Infor Mongoose App Builder/App Hub User Guide | 83

Basic component settings
When selected, this option displays the header. When cleared, the header does not display.
•

Show Legend
This option determines whether the legend displays.

•

Show Animation
When turned on, the data display moves into position on the chart when the chart displays. When turned
off, the data displays in its final position without moving.

•

Chart Type
This option determines what type of chart is used: Bars, Columns, Pie, Donut, Line, etc.

Checkbox
This topic describes the options and limitations available when using a Checkbox component.
Content settings
Content
This setting determines what text displays as the text/label for the check box, as well as how it displays.
State
These settings (Source and Display Value) determine what the source of the check box's value is.
Interactions
Trigger Event
The Checkbox component can trigger these events:
•
On Load
This event might display as one of these values:
•

•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
On Checked
This event controls what happens when the user selects the checkbox.

•

On Not Checked
This event controls what happens when the user clears the check box.

Target Action
The target Action that can be assigned for a Checkbox depends on what Component is selected to be the
target for the action.
Style settings
This component has one additional Style setting: Disabled. When this option is selected, the check box is
disabled and read-only.

Infor Mongoose App Builder/App Hub User Guide | 84

Basic component settings
To disable the check box conditionally, click the lock icon next to this option.
See Setting conditional component disablement on page 119.

Datagrid
This topic describes the options and limitations available when using a Datagrid component.
Content settings
Columns
This setting makes these column specifications:
•
•

The number of columns
Addition or deletion of columns as needed

To make the settings for a particular column, click the name of that column in the list. This opens the Edit
Datagrid Column dialog box. Use that dialog box to make these settings:
•
•
•
•

The name for each column, as it appears in the Datagrid header row
The source of the content for each column's cells
Options to make each column editable, resizable, and/or sortable.
The type of formatting to apply to the content of cells in each column.
See Defining a Datagrid column on page 87.

•

Inclusion of a summary row, used to total a set of numeric values for the column.

Header
This setting specifies the text to display as the header (title) for the Datagrid component. Specify both a
Source and a Display Value.
Options
These settings specify various aspects of the Datagrid's behavior, including these:
•

Keep Selection
When selected, this option indicates that any record that is selected when the collection is refreshed,
remains selected after the collection is refreshed.
When this option is cleared, after the collection is refreshed, the focus returns to the first record in the
collection.

•

Selectable
When selected, this option specifies that records in the grid can be selected and modified by the user.
When this option is cleared, the user can view selected records (which are highlighted), but the contents
are read-only.

•

Multi Select
When selected, this option causes a column with check boxes for each record to display. These check
boxes allow users to select multiple records for further action.

Infor Mongoose App Builder/App Hub User Guide | 85

Basic component settings
Toolbar
These settings determine what information is to be displayed in the Datagrid toolbar. The toolbar information
displays at the top of the Datagrid component.
Header. For the toolbar to be visible, you must select the Show Header option on the the Style tab.
•
Enable Row Count
When selected, this option displays the number of records in the current collection, inside parentheses
( ) next to the Header text.
When this option is cleared, no record count displays.
•

Enable Filter Bar
When selected, this option displays a magnifying glass icon on the right side of the toolbar. This magnifying
glass icon allows you to filter the contents of the Datagrid, so that only records that contain the filter
criteria display. To use the filter, you must click the icon and type in the field the string you want to
search for. Then press Enter. The Datagrid component then displays only the records that contain that
string.
Note: You can filter only one string at a time. Boolean searches are not allowed.
When this option is cleared, the filter feature is not available.

•

Enable Export
When selected, this option displays an Export button on the Datagrid toolbar. In Preview or Launch
mode, you can then export the data in the Datagrid to an Excel or CSV-formatted file.

Paging
These settings determine how many records are to be displayed in the Datagrid at any one time.
•
Use Paging
When selected, only a specified number of records display in the Datagrid at any one time. Users can
access/view additional records using the paging controls at the bottom of the grid.
When this option is cleared, all records display, and the system ignores any Items per page setting.
•

Items per page
When the Use Paging option is selected, use this drop-down list to select the number of records to
display on each page.
If the Use Paging option is not selected, any value in this field is ignored.

Interactions
Trigger Event
The Datagrid component can trigger these events:
•
On Selection Changed
This event is triggered when the selection of a record in the Datagrid changes.
•

On Row Updated
This event is triggered when any of the data in a record (row) is updated.

•

On Cell Updated
This event is triggered when any of the data in an individual cell is updated.

Infor Mongoose App Builder/App Hub User Guide | 86

Basic component settings
•

On Button Clicked
This event is triggered when an associated button is clicked.

•

On Load: This event might display as one of these values:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
Datagrid component
•
On Load (No Data Service), where no data service has been assigned to the Datagrid component

Target Action
Depending on the target, the Datagrid component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Style
This setting has one option: Show Header
When this option is selected, the header (title) for the Datagrid component displays above the grid. If other
toolbar options (such as the row count and filter settings) are active, they also display.
When this option is cleared, no header row information displays.
List
This setting has one option: Display as List
When this option is selected, the grid does not display separator lines between the columns.
Row Height
This setting has three options: Small, Medium, and Large
These options determine the relative height of the rows.

Defining a Datagrid column
When a Datagrid component is first added to the workspace, column headers are automatically given generic
names (Column 1, Column 2, and so on). To make the column headers more useful, you likely want to rename
them.
In addition, for a Datagrid to be useful, you must tell App Builder where to find the contents for each column,
whether to allow users to select or alter the contents of cells, and what format to apply to the contents of
each cell in a column.
1
2
3
4
5

In the workspace, select the Datagrid component.
In the Properties panel, select the Content tab.
Click to expand the Columns option.
In the list of columns, click the name of the column you want to define.
Use the Edit Datagrid Column dialog box to make the settings for the column:
a In the Name field, type the name of the column as you want it to appear in the header row.
b Click to expand the Source Field section.
c Use the Value field to select the desired source for the column.
d Click to expand the Options section.
Infor Mongoose App Builder/App Hub User Guide | 87

Basic component settings
e

f
g

Set the Options as desired:
•
Editable - This option determines whether users can edit the contents of cells in this column.
To make the column cells read-only, clear this check box.
•
Resizable - This option determines whether users can change the size of the Datagrid. To prevent
users from resizing the Datagrid, clear this check box.
•
Sortable - This option determines whether users can sort the rows of columns by clicking inside
the header of one column. For example, to sort the rows alphabetically by the contents in Column
1, users can click inside the column header, and rows are sorted in alternating
ascending/descending order.
Click to expand the Formatter section.
From the list of possible formats (Type), select the one to use as the default format for that column's
contents.
Depending on the Type you select, you might be prompted for additional criteria.

h

i

Optionally, to create a summary row for a column with numeric values, expand the Summary Row
section, and make these settings:
•
Enable - When selected, this option causes a summary row to be created for the column.
Otherwise, no summary row is created or displayed.
•
Text - This option specifies the text to display before the summary total; for example: Total:
•
Text Placement - Select whether to display the text before the number or after the number. For
example, do you want it to display as "Total: 122" (Before) or "122 Total" (After)?
Optionally, to allow one property value to display in place of a specified property value, expand the
Substitute Value section, and make these settings:
•
Enable - When this option is selected, the other Substitute Value settings take effect. Ohterwise,
any values in the other settings are ignored.
•
Data Service - From the drop-down list, select the data service to serve as the source of the
column display. This data service must contain both the property for the Source and the property
for the Display Value.
Note: You can use only data services that do not use input parameters.
•
•

6

Source - From the drop-down list, select the property to use as the source for the data.
Display Value - From the drop-down list, select the property to use for the display of the specified
source data.

Click Save.

Adding a column to a Datagrid
By default, App Builder creates three columns in a newly placed Datagrid component. You can add more
columns as need requires.
To add a column to a Datagrid component, use this procedure:
1
2
3
4

In the workspace, select the Datagrid component.
In the Properties panel, select the Content tab.
Under the Columns option, click Show More.
Click the Add Column icon.

Infor Mongoose App Builder/App Hub User Guide | 88

Basic component settings
App Builder opens the Add Datagrid Column dialog box. The use of this dialog box is identical to the
Edit Datagrid Column dialog box.
See Defining a Datagrid column on page 87.
5

When you are done defining the column, click Save.

App Builder adds the new column as the last column in the grid.

Deleting a column from a Datagrid
By default, App Builder creates three columns in a newly placed Datagrid component. You can delete more
columns as need requires.
To add a column to a Datagrid component, use this procedure:
1
2
3
4

In the workspace, select the Datagrid component.
In the Properties panel, select the Content tab.
Under the Columns option, click Show More.
In the list of columns, select the column you want to delete.
App Builder opens the Edit Datagrid Column dialog box.

5

Click Delete.

App Builder deletes the column without further prompting.

Filtering Datagrids for specific records
In cases where a Datagrid component contains a great quantity of data, it can be helpful to users to be able
to filter the data so as to more easily locate specific records, without having to scroll excessively. App Builder
provides such a filtering option for Datagrid components.
Note: For end users to be able to use the filtering feature, these options must be selected when building the
app:
•
•

The Show Headers option, in the Style section of the Style tab
The Enable Filter Bar option, in the Toolbar section of the Contents tab

1
2
3

With the app in Preview mode or launched, click the magnifying glass icon.
When the filter field expands, type in the text string you want to search for.
Press Enter.

The Datagrid does a search and then displays only those records that contain the specified string.

Infor Mongoose App Builder/App Hub User Guide | 89

Basic component settings

Substituting a property value for display in a Datagrid component
In cases where you might want to request records using a certain property but display the information using
a different property value, you can substitute the display property for the source property in a Datagrid
component column.
1
2
3
4
5

In the App Builder designer, select the Datagrid component that is used to display the information you
want.
On the Content tab, select the Columns section, and then click the name of the column in which you
want the information to display.
In the Edit Datagrid Column dialog box, expand the Substitute Value section.
Select the Enable check box.
From the Data Service drop-down list, select the data service to serve as the source of the column display.
This data service must contain both the property for the Source and the property for the Display Value.
Note: You can use only data services that do not use input parameters.

6
7
8

From the Source drop-down list, select the property to use as the source for the data.
From the Display Value drop-down list, select the property to use for the display of the specified source
data.
Click Save.

The specified column displays the substitute property value instead of the source property value.

Exporting data from a Datagrid component
You can use the Datagrid component in an App Builder app to export the grid's data to an Excel or
CSV-formatted file. Before you can actually do that, you must set up the Datagrid component to enable this
capability.
1
2

In your App Builder project, select the Datagrid component from which to export the data.
If not already done, on the component Properties panel, make the settings necessary to export the data:
a Select the Content tab.
b Expand the Toolbar section.
c Select the Enable Export option.
d On the Style tab, expand the Style section.
e Select the Show Header option.

3
4
5
6
7

Go into Preview mode or launch your app.
Click the Export button in the Datagrid component toolbar.
In the Export to file dialog box, provide a Name for the file to be exported.
Select one of the Type options.
Click Export.

App Builder generates the file to be exported and places it in your local Downloads folder. You can now use
Excel or another application that handles CSV-formatted files to open the file that contains your data.

Infor Mongoose App Builder/App Hub User Guide | 90

Basic component settings

Datepicker
This topic describes the options and limitations available when using a Datepicker component.
Content settings
Label
This setting specifies what text is to display as the label, if a label is displayed.
Date
These settings specify what the source of the value is and how that value displays as the date in this field.
Interactions
Trigger Event
The Datepicker component can trigger these events:
•
On Changed: This event is triggered when the date value is changed on the Datepicker component.
•
On Load: This event might display as one of these values:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
Target Action
Depending on the target, the Datepicker component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Style
These settings include these options:
•
Read-Only
When this option is selected, the Datepicker component is disabled and read-only.
To disable the Datepicker component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.
•

Show Label
This setting determines whether a label is to display with the component.

Date Formats
This setting has these options:
•
To display
This option specifies what format to use in the Datepicker component display.
•

For bindings (outbound)
This option specifies what format to use when transmitting date data to another output destination.

•

For bindings (inbound)
This option specifies what format to use when receiving date data from an external source.

Infor Mongoose App Builder/App Hub User Guide | 91

Basic component settings

Note:
•
•
•
•

The ISO8601 designation is to indicate that the date format complies with the international (ISO) date
standard.
The M3 designation indicates the the date format is structured as required by the Infor M3 product (has
no hyphens or slashes).
The Raw timestamp designation for outbound bindings indicates the use of "Unix time".
The Automatic designation for inbound bindings indicates the default option, which is the date object
as taken from the browser.

Default Date
This setting has one option: Today's Date
When this option is selected, the current system date displays. When this option is cleared, you must define
the source from which the date to be displayed comes.

Dropdown
This topic describes the options and limitations available when using a Dropdown component.
Content settings
Label
Use this setting to specify exactly what text displays as the label (if a label is shown), and what the source of
that text is.
Note: The option to display or not display a label is on the Style tab.
Display Value
This setting specifies what data is to be used to populate the drop-down list values. In most cases, this will
probably be the {In Context Value} of the data service selected for this component.
Interactions
Trigger Event
The Dropdown component can trigger these events:
•
On Selection Changed
This event is triggered when the selection of a record in the list changes.
•

On Load
This event might display as one of these values:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the Dropdown component can perform a Get operation, a Run operation, or a Clear
operation on the target component.

Infor Mongoose App Builder/App Hub User Guide | 92

Basic component settings
Style settings
Style
These settings include these options:
•
Show Label
This setting determines whether a label is to display with the component.
•

Disabled
When this option is selected, the component is disabled and read-only.
To disable the component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.

Flexbox
This topic describes the options and limitations available when using a Flexbox component.
Content settings
Flexbox Layout
These settings control how the basic Flexbox space is set up.
The only content settings you can make for Flexbox components is to split the layout into rows or columns.
In actuality, adding rows or columns simply nests two new Flexboxes inside the original Flexbox. Deleting
rows or columns removes the selected Flexbox and allows the parent Flexbox, if any, to fill the entire space.
You can use these options in any Flexbox component.
These are the basic options:
•

Split Into Rows
When you select this option, the Content tab display changes to provide options to specify the number
of rows (that is, horizontally-oriented Flexbox components) the primary Flexbox is to be divided into.
To increase the number of rows, click the plus (+) button. To decrease the number of rows, click the
minus (–) button. If there are only two rows, and you click the minus button, the parent Flexbox reverts
to a single component.

•

Split Into Columns
When you select this option, the Content tab display changes to provide options to specify the number
of columns (that is, vertically oriented Flexbox components) the primary Flexbox is to be divided into.
To increase the number of columns, click the plus (+) button. To decrease the number of rows, click the
minus (–) button. If there are only two columns, and you click the minus button, the parent Flexbox
reverts to a single component.

Interactions
Trigger Event
The Flexbox component can trigger these events:
•
On Startup

Infor Mongoose App Builder/App Hub User Guide | 93

Basic component settings
This event is defined automatically and immediately when you assign a data service to the Flexbox
component. Typically, it runs the assigned data service.
•

On Load
This event might display as one of these values:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the Flexbox component can perform a Get operation or a Clear operation on the
target component.
Style settings
Note: The Flexbox component has no Margin setting.
Orientation
This setting has these options:
•
Vertical (default)
When this option is selected, contained components are arranged in a vertical stack.
•

Horizontal
When this option is selected, contained components are arranged in a horizontal line.

Width / Height
This setting has these basic options, with a related Value field:
•
Relative (default)
When this option is selected, use the Value field to specify a value for the width/height that is relative,
as a ratio, to other Flexboxes in that same basic space.
For example, suppose you have a layout with two Flexboxes next to each other horizontally. If you set
the relative width of the left Flexbox to 1, and you set the relative width of the right Flexbox to 3, then
the system adjusts the width of both Flexboxes in a 1:3 ratio. This means that the Flexbox on the right
is now three times wider than the Flexbox on the left.
The same principle holds for Flexboxes that are arranged vertically within the same horizontal space.
For example, if you were to add a row within a Flexbox, the system actually creates two Flexboxes within
the first one, and stacks them vertically. You can then specify relative heights for the two new Flexboxes,
using the same ratio principle.
•

Pixel
When this option is selected, use the Value field to specify the number of pixels used to make the width
or height of the Flexbox. This essentially makes the width/height an absolute setting. Components always
retain the same amount of space.
If you have two Flexboxes side by side, and you set the first one to be 400 pixels wide, it renders a Relative
setting for the other meaningless. The second Flexbox remains the same width (the rest of the available
horizontal space), regardless of any Relative setting. You can, however, also set the second Flexbox to
a fixed Pixel setting. In this case, you might or might not use all the available space and have blank areas.

Infor Mongoose App Builder/App Hub User Guide | 94

Basic component settings

Note: Regardless of whether you use a Relative setting or a Pixel setting, the Value field recognizes only
positive integers.
Overflow
This setting controls how the Flexbox is to handle any content that does not fit within the edges of the
container. This setting has these options:
•
Hidden
When this option is selected, any content that does not fit within the edges of the container simply does
not display. It is cut off by whatever edge it exceeds.
•

Wrap
When this option is selected, App Builder attempts to "wrap," or move, any content that does not fit
within the edges of the container. For instance, if you have a horizontal line of components, and the last
one does not fit within the right edge of the Flexbox, App Builder moves it down to the next line, so it
can still be displayed.

•

Scroll
When this option is selected, App Builder provides an appropriate scrollbar, so that you can scroll back
and forth (or up and down) to view all the content.

Background Color
Use this setting to specify a background color for the Flexbox. You have these options to specify a color:
•
Click the down arrow at the right of the field, and then select a color from the popup color picker.
•
Type a hexadecimal color value into the field, using the #RRGGBB format.
Alignment
Use this setting to align the content within the Flexbox. You can align content both horizontally (Left, Center,
or Right) and vertically (Top, Middle, or Bottom).
Border
Use these options to define a border for the Flexbox. When defined, any components in the Flexbox do not
overlap with the border, but remain inside the border.
When defining a border, you have these options:
•

You can create a border in which All Sides have the same width or in which each Individual side can be
different widths (including a width of zero [0]).
Width sizes are given in pixels.

•

You can set the border Color using the same options for color specification as for the Background Color.

Padding
This setting specifies the amount of space inside the Flexbox that is to remain clear of any objects or
components. Padding settings are given in pixels.
You can set the padding for All Sides of the Flexbox to be the same at one time, or you can set each Individual
side to different values.

Infor Mongoose App Builder/App Hub User Guide | 95

Basic component settings

Hyperlink
This topic describes the options and limitations available when using a Hyperlink component.
Content settings
Link Name
These settings specify what text is to display as the hyperlink text. This text is formatted as a typical hyperlink,
in a blue font and underlined.
Note: This text is not necessarily the same as the actual hyperlink (URI). It is simply the text that the user
sees and clicks to go to the target destination. The actual linkage is created using the Link Value fields.
Link Value
Use the Address option to specify the actual hyperlink (URI). This can be a typical website URL or a local
(relative) destination. When going to a local destination, use the \\domain\path\file format.
You can also use this component to "drill back" to another program and open a particular screen or window,
or to open a query within the IDM (Infor Document Management) application.
To use the hyperlink for drillback, select the Use as Drillback option. With this option selected, you must
use this syntax: ?LogicalId=yourLogicalID, where yourLogicalID is the logical ID for your particular product.
This is followed, then, by the syntax required to open the desired screen or window.
Interactions
Trigger Event
The Hyperlink component can trigger these events:
•
On Click
This event is triggered when the link is clicked.
•

On Load
This event might display as one of these values:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the Hyperlink component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Style
This setting has one option: Disabled. When this option is selected, the Hyperlink component is disabled.
To disable the hyperlink conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.

Infor Mongoose App Builder/App Hub User Guide | 96

Basic component settings
Caret
Carets, before or after a hyperlink component can indicate a directional value. This can be useful if you are
constructing a "breadcrumb trail," for example.
This setting has these options:
•

None
No caret displays.

•

Back
A left angle bracket (<) displays to the left of the hyperlink text.

•

Forward
A right angle bracket (>) displays to the right of the hyperlink text.

Image
This topic describes the options and limitations available when using an Image component.
Content settings
Source
This setting specifies what the source is for the image to be displayed. There are basically two options for
the Source of images:
•
URL-based images use references to image files on the Internet. These are easier to set up, but can be
less reliable if you do not control the ownership and location of the image file.
•
Infor Document Management (IDM)-based images use image files that are stored in the IDM. These are
more complicated to set up, but are typically more reliable in terms of maintenance.
Interactions
Trigger Event
The Image component can trigger the On Load event.
This event might display as:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the Image component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Position
This setting has these options:
•
Fill

Infor Mongoose App Builder/App Hub User Guide | 97

Basic component settings
When this option is selected, App Builder stretches or shrinks the image so that it fills the entire space
of the container.
This can result in unexpected results and distort the image. If you choose this option, it is a good idea
to make the container the same size as the image object, or at least has the same exact aspect ratio.
•

Fit
When this option is selected, App Builder enlarges or reduces the size of the image, while keeping the
original aspect ratio of the image object.
Depending on the aspect ratio of the container, this means that you might have blank areas on either
side of the image object or above and below it.

•

Center (default)
When this option is selected, App Builder places the image object in the center of the container at its
original size and aspect ratio.

Using a URL-based image
One way to incorporate images in your project is to use an Image component in conjunction with a URL to
an Internet-hosted image file.
To configure an Image component that uses a URL-based image file, use this procedure:
1

Obtain the complete URL for the Internet-hosted image file.
Note: Different web browsers use different methods for obtaining this URL. For assistance, refer to the
help for your web browser.

2
3
4
5
6
7

In App Builder, place an Image component in the desired Flexbox.
Select the Image component.
In the Properties panel, select the Content tab.
For the Source option, click Show More.
From the Source drop-down list, select {Static Value}.
In the Display Value field, paste the complete URL for the image file.
Note: You must use a complete URL; that is, a web address beginning with http:// or https://.

Assuming you are connected to the Internet, the Image component should immediately display the image.

Using an IDM-based image
One way to incorporate images in your project is to use an Image component in conjunction with an image
file hosted in the Infor Document Management (IDM) app.
This approach requires that the image file be created and imported into the Infor Document Management
before you perform this procedure. For information on how to do that, consult the IDM documentation.
To use an IDM-based image in your project, use this process:

Infor Mongoose App Builder/App Hub User Guide | 98

Basic component settings
1

Create the data service for the image component.
See Creating a data service for an Image component on page 99.

2

Set up the code to call the image file.
See Getting the code to call the image file on page 100.

3

Import and configure the data service for the image file.
See Finishing the Image component data service on page 100.

4

Create the interaction to access the image file.
See Creating the interaction for the Image component on page 101.

As soon as the last procedure is performed, the image displays in your project.

Creating a data service for an Image component
The first task in using an IDM-based image file is to create the data service for the Image component that is
to be used to display the image.
To create the data service for an IDM-based Image component, use this procedure:
1
2
3
4
5
6
7
8
9
10
11
12

Place an Image component in the Flexbox that is to display the image.
Select the Image component and then click Manage Data Services.
In the Project Data Services dialog box, click + Add Data Service.
From the drop-down list, select Create from Wizard.
In the Create from Wizard dialog box, from the Select Source drop-down list, select ION API.
Click Next.
From the Suite drop-down list, select Infor Document Management.
In Search field, type: search
Click the Search button.
From the list of results that displays, select Search for items (by string).
Click Next.
In the Create Data Service dialog box, verify that GET is the selected Http Method.
Leave everything else unchanged for now.

13
14
15
16
17
18
19

Click Next.
On the Output Parameters page, leave everything unchanged for now.
Click Next.
On the Name & Description page, provide a Name for the data service and, optionally, a Description.
Click Next.
On the Summary (last) page, review your settings and click Finish.
Verify that the data service has been created and then click Close.

You must now obtain the code that will retrieve the image file from the IDM.

Infor Mongoose App Builder/App Hub User Guide | 99

Basic component settings

Getting the code to call the image file
Once the image file has been created and placed in the Infor Document Management (IDM) app, and the data
service for the Image component has been created, the next stage is to get the code to call it.
To get the code to call the image file, use this procedure:
Note: This procedure assumes that you are using Infor OS Portal as your primary working environment.
1
2
3

From the Infor OS Portal menu, select Infor ION API.
From the Available APIs, select Infor Document Management.
In the Endpoints data grid, click the Documentation icon for the REST Api endpoint
(https://nlbavwabmgdemo1.infor.com:7443/infor/IDM/api).
This displays the documentation page for the IDM REST API.

4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19

In the list of available APIs, locate the group of APIs for Items.
In that group, locate GET > /items/search > Search for items (by string).
Click GET.
Click Try it out.
From the Infor OS Portal menu, select Document Management.
In the Search Document Management field, click the down arrow (˅).
From the Document Type drop-down list, select File.
Click Search.
In the Search Document Management field, click the down arrow (˅) and then click Show XQuery.
Select and copy the XQuery text (/MDS_File)
From the Infor OS Portal menu, select ION API.
In the $query ("Query parameter") field, paste the text you copied.
In the $offset ("Start index...") field, type: 0 (zero)
In the $limit ("The maximum number of...") field, type 1 (one).
From the Response content type drop-down list, select application/json;charset=utf-8.
From the Response body Download field, select and copy the entire body of code.

You are now ready to finish configuring the data service for your Image component.

Finishing the Image component data service
Once you have obtained the JSON code required to call the Infor Document Management (IDM)-based image
file, you must get that code into the data service for the Image component and finish configuring the data
service.
To get the code into the data service and finish configuring it, use this procedure:
Note: This procedure assumes that you are using Infor OS Portal as your primary working environment.
1
2
3

From the Infor OS Portal menu, select App Builder.
Select your Image component and then click Manage Data Services.
In the Project Data Services dialog box, select the data service you created for the Image component.

Infor Mongoose App Builder/App Hub User Guide | 100

Basic component settings
4
5
6
7
8
9

On the toolbar, click Edit.
In the Edit Data Service dialog box, select the Output Parameters page.
Click VIEW JSON.
In the JSON Preview dialog box, select the text ("text") inside the Json field.
Paste the Response body code you copied earlier (in Getting the code to call the image file on page 100).
Click Save.
If necessary, also click Close to exit the JSON Preview dialog box.

10 In the Edit Data Service dialog box, scroll down to the Root Element field.
11 In the Root Element field, type: $.items.item[0].resrs
12 Near the bottom of the Output Parameters section, click GENERATE PARAMETERS.
App Builder generates these parameters:
•
.res[?(@.name=='')].size
•
.res[?(@.name=='Preview')].mimetype
•
.res[?(@.name=='SmallPreview')].mimetype
•
.res[?(@.name=='Thumbnail')].mimetype
Each of these parameters can have an optional alias, three of which are assigned by default. You can
change these aliases as you like.
13
14
15
16

Change the final elements ("size" and "mimetype") for each of these parameters to: url
Click Next and then Next again.
On the Summary page, click Finish.
In the Project Data Services dialog box, click Close.

Creating the interaction for the Image component
Once the data service for the Image component has been created and configured, before you can see the
image, you must create an interaction to actually call and display the image.
1
2

In your App Builder project, select the Image component.
On the Properties panel, from the Data Service drop-down list, select the data service you created for
the Image component.
Note: Because you have not yet created the interaction, the Image component displays an error symbol.
As soon as the interaction is correctly created and configured, this error symbol disappears.

3
4
5

Select the Interactions tab.
Click Create Interaction.
For the Trigger Event section, make these settings:
•
Component = Application
•
Event = On Startup

6

For the Target Action section, make these settings:
•
The Component is set to your Image component (This Component).
•
The Action is set to run the data service you created for the Image component.

Infor Mongoose App Builder/App Hub User Guide | 101

Basic component settings
7

In the Data Key Mapping section, make these settings for the first Action Data Field (which is already
set to $query (Mandatory) and is read-only):
•
Source = {Static Value}
•
Display Value - Set this field using this sub-procedure:
a From the Infor OS Portal menu, select Document Management.
b In the Search Document Management field, click the Down arrow.
c From the Document Type drop-down list, select File.
d From the Property or Attribute drop-down list, select Filename.
The Operation field automatically selects = Equal.
e
f

In the Search field, type the name of your image file, with its extension.
Click Search.
The results list should now display only one file, the image file you want.

g
h
i
j
k

In the Search Document Management field, click the Down arrow again.
Click Show XQuery.
In the XQuery that displays, copy the entire string.
From the Infor OS Portal menu, select App Builder.
Paste the string that you copied into the Display Value field.

8

In the Data Key Mapping section, make these settings for the second Action Data Field (which is already
set to $offset (Mandatory) and is read-only):
•
Source = {Static Value}
•
Display Value = 0 (zero)

9

In the Data Key Mapping section, make these settings for the third Action Data Field (which is already
set to $limit (Mandatory) and is read-only):
•
Source = {Static Value}
•
Display Value = 1 (one)

App Builder displays the image and the error indicator no longer displays.

App Builder Image components in converted Mongoose forms
When an App builder is "copied to" or converted to be, a Mongoose form, Image components need special
handling to map correctly to the Mongoose form.
During the conversion process, image mapping follows these principles:
•
•

If the image file has been incorporated in App Builder using a base64/ URL link, the image is first copied
into the global image repository in Mongoose, and then placed appropriately in the form.
If the image file has been incorporated in App Builder using an Infor Document Management (IDM) hosted
file, then the image file is stored in an IDO and then mapped appropriately to the Mongoose form.

Infor Mongoose App Builder/App Hub User Guide | 102

Basic component settings

Label
This topic describes the options and limitations available when using a Label component.
The Label component in App Builder is comparable to the Static component in Mongoose. It is typically used
for text that is not intended to change: headers, titles, captions, and so on.
Content settings
Label
These settings specify the text that is to display as the label.
Tooltip
These settings specify what text is to display as a "tooltip"; that is, a bit of text that appears when a user
hovers over the label text.
Interactions
Trigger Event
The Label component can trigger the On Load event. This event might display as:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
Target Action
Depending on the target, the Label component can perform a Get operation or a Run operation on the target
component.
Style settings
Typography
Note: These settings do not allow you to select a particular font family or alternate sizes/weights/colors for
the font display (other than the specific sizes/weights/colors provided).
These settings have these options:
Font Style
These options basically control the size and, in some cases, the weight of the label text. Where a size is
specified, the size is provided in pixels (not points).
Font Characteristic
These options control aspects, other than size, of how the label displays, such as the font weight and any
decorative elements (such as underlining). In most cases, these options also determine the font color. The
exceptions to this rule are Default, Emphasis, and Strong.
Note: These settings do not control how the label actually behaves, only the appearance. For example,
formatting the label text as Link does not actually make the text a hyperlink.
Color
This setting allows you, within limits, to select the color for the lable text.

Infor Mongoose App Builder/App Hub User Guide | 103

Basic component settings

Note: If you have the Font Characteristic set to anything other than Default, Emphasis, or Strong, this
setting has no effect.

List
This topic describes the options and limitations available when using a List component.
Content settings
Header
These settings provide the text for only the header (or title) of the list. The actual contents of the list come
from whatever data service is used as the data source for the list.
Options
This setting provides only one option: Keep Selection
When selected, this option indicates that any record that is selected when the collection is refreshed, remains
selected after the collection is refreshed.
When this option is cleared, after the collection is refreshed, the focus returns to the first record in the
collection.
Interactions
Trigger Event
The List component can trigger these events:
•
On Selection Changed
This event is triggered when the selection of an item in the list changes.
•

On Load
This event might display as one of these values:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the List component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Template
This setting has one option, Cell Template, with these choices:
•
One Label
When this option is selected, only one value displays for each item in the list.
To specify what property value is to be used, use the Content > Label setting for the OneLabelList
subcomponent.

Infor Mongoose App Builder/App Hub User Guide | 104

Basic component settings

Note: This subcomponent is selected by clicking on the value for a single record of the list. That makes
the subcomponent available for modification the same as any basic component.
•

Two Labels Vertical
When this option is selected, two values display for each item in the list. The "primary" value displays
on top, in a slightly larger font. The "secondary" value displays underneath, in a slight smaller font.
To specify what values are to be used, use the Content > Label settings for the TopLabelList and
BottomLabelList subcomponents.
Note: These subcomponents are selected by clicking on the top or bottom value for a single record of
the list. That makes the subcomponents available for modification the same as any basic component.

•

Image & Labels
When this option is selected, one value displays, similar to the Two Labels Vertical choice, and an image
displays directly below it .
Note: The images must be accessed by means of a data service, similar to the use of an Image component.

•

One Hyperlink
When this option is selected, each list item consists of a Hyperlink component. The Hyperlink component
must be configured the same as any other Hyperlink component.

•

Label & Hyperlink
When this option is selected, each list item contains a Label component, followed by a Hyperlink
component. Both components must be configured according to their types.

•

Image, Label and Hyperlink
When this option is selected, each list item contains an Image component on the left, and both a Label
component and a Hyperlink component on the right. Each of these components must be configured
separately.
Note: The images must be accessed by means of a data service, similar to the use of an Image component.

Style
This section has one option: Show Header
When selected, this option displays the list header. When cleared, the header is not displayed.

Lookup
This topic describes the options and limitations available when using a Lookup component.
Content settings
Columns
When Add Column at the right side of the component is clicked, App Builder launches the Edit Lookup
Datagrid Column dialog box. This dialog box provides several choices, based on whatever data service the
Lookup component is associated with.

Infor Mongoose App Builder/App Hub User Guide | 105

Basic component settings
If there are more choices than will fit on the page, the user can access/view additional records using the
paging controls at the bottom of the grid. Using the Records per page drop-down list at the bottom of the
dialog box, you can specify how many records are to display on each page.
When the user selects a record in the list, that record's value is returned to the Lookup field as the dialog box
closes (assuming you have set the component to display a Dialog Output Value).
Label
These settings specify what text is to display as the label for the component, if a label is displayed.
Note: The option to display or not display the label is on the Style tab.
Text
These optional settings specify what text displays in the Lookup component field when the app launches.
You can use this value to prompt the user.
Dialog Output Value
This setting specifies what value is to display in the Lookup component field when the user selects an item
from the list in the dialog box.
Interactions
Trigger Event
The Lookup component can trigger only the On Click event. This event is triggered when the user clicks the
magnifying glass icon in the Lookup component.
Target Action
Depending on the target, the Lookup component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Style
These settings include these options:
•
Show Label
This setting determines whether a label is to display with the component.
•

Read-Only
When this option is selected, the component is disabled and read-only.
To disable the component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.

RadioButton
This topic describes the options and limitations available when using a RadioButton component.

Infor Mongoose App Builder/App Hub User Guide | 106

Basic component settings
Content settings
Radio Buttons
This group of settings specifies a mutually exclusive number of choices to be presented to the user. You can
define what text displays for each option, what value is represented by each option, and change the order
of the options in the list.
By default, the RadioButton component is created with three option buttons. You can delete unwanted
buttons or add more buttons (Add Radio Button).
When you click the ellipsis (•••) button for an option and then select Properties, App Builder launches the
Edit Radio dialog box. Use this dialog box to change the name displayed for the option and to provide a
value for what happens when this option is selected by the user.
To change the order of options, click and drag the vertical ellipsis for the option you want to move to the
desired place in the set of options.
Label
These settings specify what text is to display as the label for the component, if a label is displayed.
Note: The option to display or not display the label is on the Style tab.
Interactions
Trigger Event
The RadioButton component can trigger these events:
•
On Selection Changed
This event is triggered when the selection of an option in the list changes.
•

On Load
This event might display as:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the RadioButton component can perform a Get operation, a Run operation, or a
Clear operation on the target component.
Style settings
Style
These settings include these options:
•
Show Label
This setting determines whether a label is to display with the component.
•

Disabled
When this option is selected, the component is disabled and read-only.
To disable the component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.

Infor Mongoose App Builder/App Hub User Guide | 107

Basic component settings

Tabs
This topic describes the options and limitations available when using a Tabs component.
Note: This topic deals with the Tabs component itself. For information about the options and limitations of
individual tabs, see Tab subcomponent on page 109.
Content settings
Tabs
The Tabs component itself can be thought of primarily as a container for other components. As a component,
it has few Content settings of its own, relying on the individual tabs and subcomponents to adhere to their
own Content settings.
This group of settings specifies a set of tabs to be included within the Tabs component. For each tab, you
can define what text displays, what the source of data is, and you can change the order of the tabs within
the component display.
By default, the Tabs component is created with four tabs. You can delete unwanted tabs or add more tabs
(Add Tab).
When you click the ellipsis (•••) button for an tab and then select Properties, App Builder launches the Edit
Tab dialog box. Use this dialog box to specify the Name (label) that displays on the tab itself (and in the
Contents list of tabs). Optionally, if you have a data service associated with the Tabs component, you can
specify a source for the name from one of its options/properties.
To change the order of tabs, click and drag the vertical ellipsis for the option you want to move to the desired
place in the set of options.
Interactions
Trigger Event
The Tabs component can trigger only the On Load event. This event might display as:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
Target Action
Depending on the target, the Tabs component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
The Tabs component itself has only one Style setting. Each individual tab, however, has its own Style setting
options.
Note: The Tabs component does have the usual Margin and Visibility options. Individual tab panels, however,
do not, as a rule.
Orientation
This setting has these options:
•
Horizontal (default)

Infor Mongoose App Builder/App Hub User Guide | 108

Basic component settings
When this option is selected, the individual tabs display horizontally, along the top of the Tabs component.
•

Vertical
When this option is selected, the individual tabs display stacked vertically along the left side of the Tabs
component.

Tab Panel subcomponent
In App Builder, individual tabs exist as subcomponents of a Tabs component and are labeled as tab panels.
These subcomponents have their own options and limitations, as described in this topic.
Note: For information about Tabs components, which are the containers for these subcomponents, see Tabs
on page 108.
Content settings
In actuality, individual tabs (TabPanels) consist of layered Flexboxes contained within the Tabs component.
As a result, virtually all content within the individual tab depends on the components that are placed and
configured there.
To view the contents of an individual tab, you must first select the Tabs component itself, and then use the
Tabs group on the Contents tab to select the individual tab to be the active one (Open Tab). Then you can
click anywhere inside the tab panel area to view and modify the contents of that tab.
In terms of actual tab panel content, essentially, it consists of the addition of components and the ability to
subdivide the panel by adding columns and/or rows. Use the Split Into Rows and Split Into Columns options
to subdivide the tab panel area. The content, then, for each individual tab is governed individually by the
components that are placed within the tab panel area.
Interactions
The interactions possible for each individual tab depend entirely on what components reside within that tab
panel.
Style settings
Each individual tab has its own Style setting options. If a tab has more than one row or column (Flexbox),
each Flexbox is goverend by its own Style settings
Note: The parent Tabs component has the usual Margin and Visibility options. Individual tab panels, however,
do not, as a rule.
Orientation
This setting has these options:
•
Vertical (default)
When this option is selected, the contents of the tab (or Flexbox) display stacked vertically.
•

Horizontal
When this option is selected, the contents of the tab (or Flexbox) display horizontally, in a row.

Infor Mongoose App Builder/App Hub User Guide | 109

Basic component settings
Height/Width
This option is available only if the tab panel contains multiple rows or columns (Flexboxes). It is enabled
individually for each Flexbox. See Flexbox on page 93.
Overflow
This setting controls how the tab panel is to handle any content that does not fit within the edges of the
container. This setting has these options:
•
Hidden
When this option is selected, any content that does not fit within the edges of the container simply does
not display. It is cut off by whatever edge it exceeds.
•

Wrap
When this option is selected, App Builder attempts to "wrap," or move, any content that does not fit
within the edges of the container. For instance, if you have a horizontal line of components, and the last
one does not fit within the right edge of the panel, App Builder moves it down to the next line, so it can
still be displayed.

•

Scroll
When this option is selected, App Builder provides an appropriate scrollbar, so that you can scroll back
and forth (or up and down) to view all the content.

Background Color
Use this setting to specify a background color for the tab panel. From the drop-down list, select the color
you want.
Alignment
Use this setting to align the content within the tab panel. You can align content both horizontally (Left,
Center, or Right) and vertically (Top, Middle, or Bottom).
Padding
This setting specifies the amount of space inside the tab panel that is to remain clear of any objects or
components. The unit of measure for Padding settings is pixels.
You can set the padding for all sides of the panel to be the same at one time, or you can individually set each
side to different values.
Visibility
This option is available only if the tab panel contains multiple rows or columns (Flexboxes). It is enabled
individually for each Flexbox.

TextArea
This topic describes the options and limitations available when using a TextArea component.
Content settings
Text
This setting displays any text that is entered in the field. If you provide text as part of the component
specification, that text displays by default and overrides any Placeholder text that might exist.
Infor Mongoose App Builder/App Hub User Guide | 110

Basic component settings
Users can override any text you might have provided, unless you create the component as a read-only field.
Even if they do so, your default text remains as part of the component configuration, though it does not
display in runtime.
This component allows for multi-line input. Text automatically wraps when the length of the line reaches
the border of the text area. If the amount of text is too large to fit in the allotted space, it extends below the
bottom edge and a vertical scroll bar is employed automatically.
You can use this component pretty much the same way you would a TextBox component. The big difference
is in the fact that this is a multi-line component and can display much more text easily.
Label
These settings specify what text is to display as the label for the component, if a label is displayed.
Note: The option to display or not display the label is on the Style tab.
Placeholder
This setting allows you to provide text to be used as a placeholder, typically as a prompt for user input. The
text displays in a gray font. As soon as the user enters any text into the field, the placeholder text disappears.
Any text you use in the Text setting overrides and hides any Placeholder text.
Max Length
This setting specifies the maximum number of characters that can be entered into the field.
When this setting is defined, the phrase "Characters left: ###" displays in a smaller gray font below the text
area. This display counts down the number of characters that can still be entered, as the user types, and
displays the number (###) to the user.
Interactions
Trigger Event
The TextArea component can trigger only the On Load event. This event might display as:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
Target Action
Depending on the target, the TextArea component can perform a Get operation, a Run operation, or a Clear
operation on the target component.
Style settings
Style
This setting has these options:
•
Show Label
This setting determines whether a label is to display with the component.
•

Read-Only
When this option is selected, the contents of the text area are disabled and read-only.
To disable the component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.

Infor Mongoose App Builder/App Hub User Guide | 111

Basic component settings
•

Resizable
When this option is selected, the actual text area can be resized vertically (but not horizontally).

TextBox
This topic describes the options and limitations available when using a TextBox component.
Content settings
Text
This setting displays any text that is entered in the field. If you provide text as part of the component
specification, that text displays by default and overrides any Placeholder text that might exist.
Users can override any text you might have provided, unless you create the component as a read-only field.
Even if they do so, your default text remains as part of the component configuration, though it does not
display in runtime.
Note: Keep in mind that this field can only be one line high. Any text that extends beyond the end of the field
can only be read by moving the cursor beyond the edge of the field.
Depending on how you intend to use this component, you can:
•
•
•
•

Provide text for informational, read-only purposes.
Use the field for user input that can be further processed.
Employ another component to populate the field with text from a data service.
And so on...

Label
This setting determines what text is to display as the label for the text box, if a label is used.
Note: The setting to either display or hide the label is made on the Style tab.
Placeholder
This setting allows you to provide text to be used as a placeholder, typically as a prompt for user input. The
text displays in a gray font. As soon as the user enters any text into the field, the placeholder text disappears.
Any text you use in the Text setting overrides and hides any Placeholder text.
Interactions
Trigger Event
The TextBox component can trigger only the On Load event. This event might display as:
•
Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
•
On Load (No Data Service), where no data service has been assigned to the component
Target Action
Depending on the target, the TextBox component can perform a Get operation, a Run operation, or a Clear
operation on the target component.

Infor Mongoose App Builder/App Hub User Guide | 112

Basic component settings
Style settings
Style
This setting has these component-specific options:
•
Masking
This option allows you to define and use various masking options for the text to be displayed.
See Masking options on page 113.
•

Style
This option includes these sub-options:
•

Show Label
When selected, this option displays a label over the text area. When this check box is cleared, no
label displays.
Note: The text that is to display as the label is set on the Content tab, Text option.

•

Read-Only
When this option is selected, the contents of the TextBox are read-only and cannot be changed.
To enable and disable the TextBox component conditionally, click the lock icon next to this check
box.
See Setting conditional component disablement on page 119.

•

Width
This option controls the relative width of the TextBox component. These are the choices and their
approximate widths in pixels:
•
•
•
•

Tiny - 85 px.
Small - 160 px.
Medium - 225 px.
Large - 310 px.

When using boundaries, you can assign a different Width setting for each boundary.

Masking options
The App Builder TextBox component allows for various masking options to be applied to the text. This topic
lists and describes the other masking options that are available.
Types of masks
To define a mask for the text display, first select the Type of mask you want to apply. Most of the types, then,
allow you to refine the mask definition, using the Masking Options dialog box. This dialog box is accessed
by clicking the Mask Options icon to the right of the Type drop-down field. The options in the Masking Options
dialog box vary according to the Type you select, from these types:
Type

Description/Comments

None

(Default) This choice does not apply any masking to the text.

Infor Mongoose App Builder/App Hub User Guide | 113

Basic component settings

Type

Description/Comments

Number

This choice allows only the display of numeric values. The Masking Options
dialog box includes these options:
•
Allow Negative - When selected, this option allows the display of negative
numerals. When cleared, this option allows only non-negative numerals.
If allowed, the negative (minus) sign does not count against the Integer
Limit.
•
Allow Thousands Separator - When selected, this option displays a comma
separator between each set of three integer digits (thousands, millions,
billions, and so on). When cleared, this option does not display any separators.
•
Integer Limit - Use this field to specify the maximum possible number of
integer digits, that is, numerals that can display to the left of the decimal
point.
Note: If you use a prefix, the prefix characters count against this limit.
•

•

Decimal Limit - Use this field to specify the maximum possible number of
decimal places, that is, numerals that can display to the right of the decimal
point.
Prefix - Use this field to specify a prefix to be displayed at the beginning of
each value displayed. You can use any non-numeric characters.
Note: If you specify a prefix, the number of characters in the prefix counts
against the Integer Limit. This means, for example, that if you have an Integer Limit of 10 and you specify a 3-digit prefix, then the de facto limit on
integer digits is 7.

Password

This choice causes any text in the field to display instead as "bullet" characters
(•), thus hiding the actual password.

Infor Mongoose App Builder/App Hub User Guide | 114

Basic component settings

Type

Description/Comments

Date/Time

Infor Mongoose App Builder/App Hub User Guide | 115

Basic component settings

Type

Description/Comments
This choice allows you to set up and use patterns ("templates") for date/time
inputs. In the Masking Options dialog box, you can either select a predefined
Format from the drop-down list, or you can create a Custom Format in the
field provided.
When setting up a format, you can use these characters as placeholders for input:
MM
Displays the number of the month, in 2-digit format; for example, “08” (for August).
MMM
Displays the abbreviated name of the month; for example, “Aug”.
MMMM
Displays the full name of the month; for example, “August”.
dd
Displays the day/date padded with zeroes.
d
Displays the day/date unpadded.
yyyy
Displays the year using a 4-digit format. You should always use this format for
the year.
yy
Accepts input for the year using a 2-digit format, but if used, this is converted to
the 4-digit format.
hh
Displays the hour using a 12-hour format, with padding.
h
Displays the hour using a 12-hour format, without padding.
HH
Displays the hour using a 24-hour format, with padding.
H
Displays the hour using a 24-hour format, without padding.
a
Displays the AM/PM part of the time with the 12-hour format.
mm
Displays the minute digits.
ss
Displays the seconds digits.
SSS

Infor Mongoose App Builder/App Hub User Guide | 116

Basic component settings

Type

Description/Comments
Displays the milliseconds digits.
zz
Displays the time zone name in short form, for example, “EST”, using the language
of the current locale.
zzzz
Displays the time zone name in long form; for example, “Eastern Standard Time”,
using the language of the current locale.
You can use hyphens (-), forward slashes (/), and colons (:) as separators.

String

This choice allows you to create a masking pattern of your own design. You can
allow for predefined characters to be used, as well as places for user input.
The Masking Options dialog box provides a single field in which you create
your masking pattern. When setting up your mask, you have these format
characters to work with:
#
This format character allows the input of any numeric character, 0-9.
x (lower case)
This format character allows the input of any alphabetic character, either upper
case or lower case.
* (asterisk)
This format character allows the input of any alphanumeric character (all numerals and all upper case and lower case letters).
a
This format character is used to represent the first character of a time period (AM
or PM). It can be replaced by a, A, p, or P.
m
This format character is used to represent the second character of the time period
(AM or PM). This character can be replaced either by m or M.

Timepicker
This topic describes the options and limitations available when using a Timepicker component.
Content settings
Label
These settings specify what text is to display as the label for the component, if a label is displayed.
Note: The option to display or not display the label is on the Style tab.

Infor Mongoose App Builder/App Hub User Guide | 117

Basic component settings
Time
These settings specify what the source of the value is and how that value displays as the time in this field.
Interactions
Trigger Event
The Timepicker component can trigger these events:
•
On Changed: This event is triggered when the time value is changed on the Timepicker component.
•
On Load
This event might display as one of these values:
•
•

Data Service has run (On Load), where Data Service is the name of a data service assigned to the
component
On Load (No Data Service), where no data service has been assigned to the component

Target Action
Depending on the target, the Timepicker component can perform a Get operation, a Run operation, or a
Clear operation on the target component.
Style settings
Style
These settings include these options:
•
Read-Only
When this option is selected, the component is disabled and read-only.
To disable the component conditionally, click the lock icon next to this check box.
See Setting conditional component disablement on page 119.
•

Show Label
This setting determines whether a label is to display with the component.

Format
These settings include these options:
•
Time Format
This setting specifies whether the time value is to display using a 24-hour format or a 12-hour (AM/PM)
format.
•

Enable seconds
Use this option to specify whether seconds are to be included in the time display.

Specialized component settings
App Builder includes a number of specialized settings that are often common to several different basic
components. This section lists and describes those specialized settings.

Infor Mongoose App Builder/App Hub User Guide | 118

Basic component settings

Setting conditional component visibility
All App Builder basic components can be set to either display or not display. Normally, this is set on an
unconditional basis; that is, the component either displays all the time or not at all.
There might be times, however, when you want a component to display (or not display) dependent on some
condition, such as a button being clicked, or some action taken in another component.
1
2
3
4
5

In App Builder, select the component for which you want to set conditional visibility.
In the Properties panel, select the Style tab.
Expand the Visibility section.
Click the lock icon next to the Show Component switch.
In the Component Expression - Hide When dialog box, click + Add.
Note: You can also use the Component Expression - Hide When dialog box to edit or delete existing
conditional expressions.

6
7
8

In the New Condition dialog box, provide the settings for the condition that will cause the component
to be hidden.
Click Save.
Click Close.

App Builder adds the setting to the component.

Setting conditional component disablement
There might be times when you want a component to be disabled (or enabled) based on some condition,
such as a button being clicked, or some value being displayed in another component.
1
2
3

In App Builder, select the component you want to conditionally enable or disable.
In the Properties panel, select the Style tab.
On the Style tab, expand the Style options section and locate the Read Only option.
Not all basic components have this option. Generally, only components that have Style options include
the Read Only option, and then not all of them.

4
5

Click the lock icon to the right of the Read Only option.
In the Component Expression - Disable When dialog box, click + Add.
Note: You can also use the Component Expression - Disable When dialog box to edit or delete existing
conditional expressions.

6
7
8

In the New Condition dialog box, provide the settings for the condition that will cause the component
to be disabled.
Click Save.
Click Close.

Infor Mongoose App Builder/App Hub User Guide | 119

Miscellaneous tips and tricks

Appendix B: Miscellaneous tips and tricks

This appendix contains a number of topics that provide a variety of tips and tricks for using App Builder more
effectively and creatively.
•
•

Creating an apparent border for a Flexbox on page 120
Making a hidden component visible on page 121

Creating an apparent border for a Flexbox
Flexbox components in App Builder do not include a setting to create borders around the Flexbox. You can,
however, use nested Flexboxes and the Padding setting for Flexboxes to create apparent borders. This topic
provides the basic procedure for how to do this.
To create an apparent border for a Flexbox component, use this procedure:
1
2

Select the Flexbox in the workspace.
On the Content tab, click Add Column.
This divides the Flexbox into two nested Flexboxes.

3
4
5

Select one of the nested Flexbox components.
On the Style tab, locate the Width option and click Show More.
Select the Relative option and set the Value to 0 (zero).
This effectively hides that nested Flexbox and allows the other Flexbox to fill the entire space.
Note: Should you decide later to "unhide" the hidden Flexbox, you can do so.
See Making a hidden component visible on page 121.

6
7

Select the original Flexbox component and set the background color.
Select the nested Flexbox component and set the background color to a contrasting color.
Note: Do not set this color to None.

8

Adjust the thickness of the border by resetting the Padding value.

Infor Mongoose App Builder/App Hub User Guide | 120

Miscellaneous tips and tricks

Making a hidden component visible
App Builder components can be hidden, so that they still exist but are not visible to the user. When a component
has been hidden, you can "unhide" it, making it visible again. The procedure for doing this depends on how
the component was hidden.
Using the Visibility Style setting
If the component has been hidden by using the Visibility setting on the Style tab, make it visible again by
simply resetting the Visibility setting.
Note: When you reset the Visibility option, the hidden component becomes visible and displays in its original
position inside the Flexbox. This usually means that other components can be displaced, particularly
components that might have been added after the hidden component was added.
Using the Width setting of a Flexbox component
In some cases, the component might have been hidden by setting the Width or Height option for the
component to 0 (zero). This might, for example, have been done when creating apparent borders for a Flexbox.
See Creating an apparent border for a Flexbox on page 120.
To make a hidden component visible when its Width or Height has been set to 0, perform this procedure:
1
2
3
4

In the workspace, select the Flexbox in which the hidden (nested) Flexbox exists.
Select the Content tab and locate the hidden Flexbox from the list of components in that Flexbox.
Select the hidden (nested) Flexbox.
Select the Style tab and set the Width or Height Value to something other than 0.

This makes the component visible again.
Note: Because this method of hiding a nested Flexbox results in one nested Flexbox overlaying the hidden
one, doing this changes the layout of the parent Flexbox component.

Creating tooltips for App Builder projects
App Builder does not innately have the option to produce tooltip help for components and other elements
of App Builder projects. The Label component, however, does provide for the implementation of tooltips in
the App Builder project UI.
Tooltips are bits of text that display when the cursor hovers over a component or other element in the user
interface. To create a UI for your App Builder app that uses tooltips, you can replace built-in component labels
with actual Label components. Then, on the Content tab of the Properties panel, set the Label component's
Label value to the original component's label text and provide the Tooltip value.

Infor Mongoose App Builder/App Hub User Guide | 121


