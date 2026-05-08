---
name: "syteline-dataviews"
description: "DataViews and DataSearch - setup, layouts, filters, expression columns/summaries, right-click menus, Load Just in Time, data sources, source sets"
version: "1.0"
tags:
  - syteline
  - dataviews
  - datasearch
  - mongoose
---

# syteline-dataviews

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

Critical number drilldowns

UPDATE @ttItemloc
SET amount = @TmpAmount
, processed = 1
WHERE RowPointer = @RowPointer

END
INSERT INTO #tt_drill_results(
CHAR01, DECI01, amount
)
SELECT item, SUM(amount), SUM(amount)
FROM @ttItemloc
GROUP BY item
UPDATE #tt_drill_results
SET RowPointer = item.RowPointer
FROM #tt_drill_results tt, item
WHERE item.item = tt.CHAR01
RETURN 0

Second Level Drilldown Program:
SSSWBCanInvValItemDtlSp
CREATE PROCEDURE SSSWBCanInvValDtlSp (
@AsOfDate
DateType
, @DrillNum
WBDrillNumType
, @CrNum
WBCrNumType
, @Id
nvarchar(500)
, @Parm1
WBSourceNameType
, @Parm2
WBSourceNameType
, @Parm3
WBSourceNameType
, @Parm4
WBSourceNameType
, @Parm5
WBSourceNameType
, @Parm6
WBSourceNameType
, @Parm7
WBSourceNameType
, @Parm8
WBSourceNameType
, @Parm9
WBSourceNameType
, @Parm10
WBSourceNameType
, @Parm11
WBSourceNameType
, @Parm12
WBSourceNameType
, @Parm13
WBSourceNameType
, @Parm14
WBSourceNameType
, @Parm15
WBSourceNameType
, @Parm16
WBSourceNameType
, @Parm17
WBSourceNameType
, @Parm18
WBSourceNameType
, @Parm19
WBSourceNameType
, @Parm20
WBSourceNameType
, @Parm21
WBSourceNameType
, @Parm22
WBSourceNameType
, @Parm23
WBSourceNameType
, @Parm24
WBSourceNameType
, @Parm25
WBSourceNameType
, @Parm26
WBSourceNameType
, @Parm27
WBSourceNameType
, @Parm28
WBSourceNameType
, @Parm29
WBSourceNameType
, @Parm30
WBSourceNameType
, @Parm31
WBSourceNameType
, @Parm32
WBSourceNameType
, @Parm33
WBSourceNameType
, @Parm34
WBSourceNameType
, @Parm35
WBSourceNameType
, @Parm36
WBSourceNameType
, @Parm37
WBSourceNameType
, @Parm38
WBSourceNameType
, @Parm39
WBSourceNameType
, @Parm40
WBSourceNameType
, @Parm41
WBSourceNameType
, @Parm42
WBSourceNameType
, @Parm43
WBSourceNameType

Infor Mongoose Core Extensibility Guide | 204

Critical number drilldowns

, @Parm44
WBSourceNameType
, @Parm45
WBSourceNameType
, @Parm46
WBSourceNameType
, @Parm47
WBSourceNameType
, @Parm48
WBSourceNameType
, @Parm49
WBSourceNameType
, @Parm50
WBSourceNameType
) AS
DECLARE
@RowPointer RowPointer
, @Whse
WhseType
, @TmpAmount AmountType
, @StartWhse WhseType
, @EndWhse
WhseType
, @StartItem ItemType
, @EndItem
ItemType
, @StartLoc
LocType
, @EndLoc
LocType
DECLARE @ttItemloc TABLE (
RowPointer uniqueidentifier
, whse
nvarchar(4)
, item
nvarchar(30)
, loc
nvarchar(15)
, amount
decimal(20,8)
, processed tinyint
)
SET @StartWhse = ISNULL(@Parm1, dbo.LowString('WhseType'))
SET @EndWhse
= ISNULL(@Parm1, dbo.HighString('WhseType'))
SET @StartItem = ISNULL(@Parm2, dbo.LowString('ItemType'))
SET @EndItem
= ISNULL(@Parm2, dbo.HighString('ItemType'))
SET @StartLoc = ISNULL(@Parm3, dbo.LowString('LocType'))
SET @EndLoc
= ISNULL(@Parm3, dbo.HighString('LocType'))
INSERT INTO @ttItemloc
SELECT RowPointer, whse, item, loc, 0, 0
FROM itemloc
WHERE whse BETWEEN @StartWhse AND @EndWhse
AND item BETWEEN @StartItem AND @EndItem
AND loc BETWEEN @StartLoc AND @EndLoc
WHILE EXISTS (SELECT TOP 1 1 FROM @ttItemloc WHERE processed = 0)
BEGIN
SELECT TOP 1 @RowPointer = RowPointer
FROM @TTItemloc
WHERE processed = 0
SET @TmpAmount = 0
EXEC SSSWBCanInvValSubItemlocSp @RowPointer, @TmpAmount OUTPUT
UPDATE @ttItemloc
SET amount = @TmpAmount
, processed = 1
WHERE RowPointer = @RowPointer
END
INSERT INTO #tt_drill_results(
CHAR01, CHAR02, CHAR03, DECI01, amount, RowPointer
)
SELECT whse, item, loc, amount, amount, RowPointer
FROM @ttItemloc
RETURN 0

Infor Mongoose Core Extensibility Guide | 205

DataViews

Chapter 10: DataViews

About DataViews
DataViews are advanced data grids that you can create to query a custom set of data quickly and easily. The
basic DataView presentation is similar to an Excel spreadsheet, displaying data in columns.
Among other things, you can perform these actions:
•
•
•
•

Use DataViews to create special groupings of data and summaries, optionally with post-query filters.
Merge data from two levels of data and present them as one record.
Use the data returned from various sources to create your own custom columns that include calculations
and calculated summaries.
Navigate to the related data maintenance form by clicking Details on the right-click context menu.

DataView sources and types
The data for a DataView can come from a variety of sources, each resulting in one of four types of DataView
(or DataView-like) displays:
•

•

Form DataViews can be created from any form that displays data.
•
These DataViews are launched by clicking the DataView button in the toolbar.
•
These DataViews use the data from the form as the source.
•
It does not matter whether the data was filtered before displaying on the form or loaded using a
custom load method. But keep in mind that the user can see in the DataView only the data that they
can access on the form.
•
You can manipulate the layout for a DataView to view the form data in ways not possible on the form.
•
Layouts created from these DataViews can be saved and applied to the data from the same form
again in the future.
•
There is a toolbar icon that you can use to send the data in the current collection to DataView. If this
icon is hidden in your toolbar, you can use the Theme editor to display it.
Predefined DataViews are constructed using the DataViews setup process and are available only to users
who have access.
•
These DataViews are typically launched from the DataViews form.
•
They require more setup than form DataViews, using the DataViews Setup form.
•
Predefined DataViews provide greater control over which IDOs are accessed and which properties
are shown than do Form DataViews.
•
Permissions made on the DataViews Setup form control which DataViews a given user can see.
•
Predefined DataViews can be embedded in forms. This is done by implementing a DataView
component from the Toolbox in the designer.

Infor Mongoose Core Extensibility Guide | 206

DataViews
•
•

•

Predefined DataViews can be used to produce custom reports. These reports can be treated as system
reports, with Vendor-level protections and associated report criteria forms.
Critical Number Drilldowns are specialized DataViews that provide details about what makes up a critical
number.
•
Drilldowns are launched by double-clicking a critical numbers form or gauge.
•
The drilldown controls specifically which properties are displayed.
•
These drilldowns inherit their permissions from critical numbers authorizations.
•
They have the same layout capabilities as other DataViews.
•
Drilldowns are configured using the Drilldowns Setup form.
•
Both IDO collection and stored procedure sources are supported.
•
When used on critical numbers forms, row coloring is supported for goals and alerts.
DataSearches are specialized searches in which you can search across predefined data sources for specific
values.
•
DataSearches are launched either by clicking the DataSearch toolbar icon or by opening the
DataSearch form.
•
The search results are displayed in a DataView-like display.
•
Compared to the typical DataView display, however, the DataSearch display, while similar, is much
more limited. Basically, you can only expand the data records, and you can reorder the columns.
•
Sources of data to be used for DataSearches are defined using the DataSearch Source Setup form.
This form provides control over which properties are searched and which users or groups can see
what data sources.

DataView results
When data is returned from a DataView query, the results are typically displayed in a basic grid on the
appropriate DataView results form. There, depending on what kind of DataView query was performed, you
can reorganize the data, decide what data displays, and take other actions with the results, such as printing
a quick, on-the-fly report, or export the data to a spreadsheet or PDF file.
See About DataView Results on page 569.
DataView layouts
To organize and present the data that is included in a DataView, you can create layouts. Layouts can eliminate
the redundancy of grouping and sorting the results every time you want to view the data.
These layouts are created by employing a number of options. You can perform these actions:
•
•
•
•
•
•

Rearrange, reorder, and resize the columns.
Define data groupings.
Create custom columns, which can include "on the fly" calculations.
Display or hide selected columns.
Merge different levels of data.
Define summaries.

Once you have the DataView layout organized and the data presented the way you want it, you can save these
layouts for future use and reference. You can even use these layouts to create your own custom reports.

Infor Mongoose Core Extensibility Guide | 207

DataViews
As with form customization, DataViews support user-level, group-level, and site-level layouts. Access to these
layouts can also be controlled using filters and user permission settings.
See About DataView Layouts on page 214.

Setting up a new DataView
To set up (create) a new predefined DataView, use the DataViews Setup form.
1
2
3
4

Open the DataViews Setup form and execute Filter-In-Place.
Initiate a new record.
In the DataView field, specify a name that best describes the data to be presented.
Optionally, to prevent modifications to the DataView by users other than those with Vendor Developer
permissions, select the System Record option.
This option allows you to protect the structure of DataViews that you deliver to customers/users. It
prevents others from deleting or modifying any content you provide, while also allowing customers to
add their own content.

5

Optionally, to designate a report caption/title, in the Caption Override field, specify the caption.
This can be a translatable string or a literal value.
When specified, this string replaces the default system-generated caption on DataView report outputs.
Note: You can also specify a layout-specific caption override, by setting the Caption Override field on
the Layouts tab. If both that and this field have caption overrides, the layout-specific override takes
precedence.

6

Optionally, use the Criteria Form drop-down list to select a form to use to collect report criteria.
This option is used primarily when the report requires a significant number of option specifications for
data to be collected. When that is a low number, you typically prompt for each option individually; but
when there is a significant number, it is easier and faster for the user to specify the data to be collected
using a report criteria form.

7

Optionally, use the Maximum Header Height field to change the height of the column headers. Enter
an integer that corresponds to the number of rows to be made available for the column headers. The
default value is 1 and the maximum value is 9.
When specified, the column names are word wrapped to occupy the space in the header; if the column
name is longer than the allotted space, an ellipse is shown to indicate that there is hidden information.

8

On the General tab, specify the required and optional information for each IDO that is to be included in
the DataView queries.
See Specifying DataView setup information - General tab on page 216.

9

Optionally, after specifying each IDO, to make additional IDO specifications for the DataView, click IDO
Setup and follow the procedure in the topic Setting additional IDO specifications for a DataView on page
219.
10 Optionally, use the options on the Input Parameters tab to add input values or ranges.
See Specifying DataView setup information - Input Parameters tab on page 220.

Infor Mongoose Core Extensibility Guide | 208

DataViews
11 Optionally, use the options on the User Permissions tab to control who has access to this DataView.
See Specifying DataViews setup information - User Permissions tab on page 222.
12 Optionally, view information about or make copies of layouts on the Layouts tab.
See Specifying DataView setup information - Layouts tab on page 222.
13 Optionally, use the Report Options tab fields to select the report orientation, the maximum cap exceeded
notification type, and what regions are to be displayed on the report.
See Specifying DataView setup information - Report Options tab on page 224.
14 Save your work.
Note: If the intended use for this DataView is to be used for report output, you should create an associated
report criteria form.

Displaying a predefined DataView
Use the DataViews form to launch the DataView Results form and view predefined DataViews and layouts.
Predefined DataViews are DataViews that have been set up for specific uses in the system. You can use the
results on these DataViews to drive ad hoc reporting.
1
2
3

4

Open the DataViews form and execute Filter-In-Place.
Select a DataView from the list.
If multiple layouts exist for the DataView, select a layout.
To help determine which layout to view, examine the column values for each layout:
Column

Description

Layout

This field provides a description to help distinguish one version of the DataView
layout from another.

Scope Type

This field specifies the level at which the DataView layout is available: Vendor,
Site, Group, or User.

Scope Name

This field specifies the group or user for which this layout is designed. Otherwise,
this field displays [NULL].

Default

When more than one layout exists for the same scope type and name, this field
is selected for the layout that has precedence.

To open the DataView Results form for the selected DataView layout, click Launch.

Infor Mongoose Core Extensibility Guide | 209

DataViews

Creating custom columns for DataViews
Use custom columns to merge and display data from various sources and to create columns on which you
want to perform operations and calculations on data in ways not possible on the original forms.
Use the Custom Columnsdialog box to create custom columns for a DataView:
1
2
3

While DataView results are displayed, select Display > Custom Columns.
Verify that the Collection field shows the IDO collection from which the data is being retrieved.
In the Name field, specify the name to assign to the custom column.
Note: This name is the label that will be used for the custom column header. Take care when specifying
this name, because you cannot change it later. If you decide to change the name/label later, you must
delete the existing column and create it again with the new name.

4
5
6
7

In the Data Type field, specify the type of data to use for the custom column display.
Click Add.
Use the Advanced Editor to define the formula or expression that is to specify what is displayed in the
column.
After you finish in the Advanced Editor, click OK.

The DataView grid immediately shows the new column with the results of the formula or expression.

Specifying summaries for DataViews
Use specified summaries to display aggregate information about the data represented in a DataView. This
aggregate information can include either standard summaries, such as Average, Count, Maximum, Minimum,
and Sum; or custom summaries that you define.
1
2
3

With DataView results being shown, select Display > Show Summaries.
Select a column on which you want to base a summary and click the "sigma" (Σ) button.
In the Select Summaries dialog box, specify one or more standard summaries, one or more custom
summaries, or both.
To specify a standard summary, depending on the data type of the column you used to launch the Select
Summaries dialog box, you can select any combination, all, or none of these options:
•
Average: Calculates the mean average of the values retrieved for that column. This option is enabled
only for numeric data types.
•
Count: Shows the total number of all the records retrieved.
•
Maximum: Shows the maximum value represented in that column. If the value is a string or text
value, standard alphanumeric valuation is used.
•
Minimum: Shows the minimum value represented in that column. If the value is a string or text value,
standard alphanumeric valuation is used.
•
Sum: Adds the values of all records retrieved for that column. This option is enabled only for numeric
data types.
To specify a new custom summary in the Select Summaries dialog box:

Infor Mongoose Core Extensibility Guide | 210

DataViews
a

In the Name field, specify the name to assign to the custom summary.
Note: This is the label that will be used for the custom summary caption. Take care when specifying
this name, because you cannot change it later. If you decide to change the name/caption later, you
must delete the existing summary specification and re-create it with the new name.

b
c

Click Add.
Use the Advanced Editor to define the formula or expression that is to specify what is displayed in
the column.
After you finish in the Advanced Editor, click OK.

The summary bar at the bottom of the DataView grid immediately displays the specified summaries.

About displaying DataView results
To display the DataView control, click the gear icon on any column header. You can display DataView results
in these ways:
•

To display predefined DataViews, from the DataViews form, select an existing DataView and then click
Launch.
Predefined DataViews are constructed using the DataViews setup process and are accessible only to
selected users. The results are displayed in the DataView Results form.

•

To display form DataViews, in any form that displays data, click the DataView icon in the toolbar.
These DataViews use the data from the form as its source. The results are displayed in the DataView
Form Results form.

•

To create an event to launch the DataView Results form directly from a form you choose, open your
chosen form and create the event for that form in the Web Designer.

The DataView Results form and DataView Form Results form show data based on the properties that have
been set on the DataViews Setup form and the DataView IDO Setup form. Properties differ by DataView and
layout.
You can use the DataView control to perform several actions on the data displayed in the results grid. Among
other things, you can perform these actions:
•
•
•
•
•

Save changes to the current layout or save it as a new layout with a different name.
Customize how data is displayed in the grid.
Print the data as a report.
Export the data into a file.
Process the data displayed in the results grid.

Infor Mongoose Core Extensibility Guide | 211

DataViews

DataView control in the web client
You can use the DataView control to perform several actions on the data displayed in the DataView results
grid.
Note: You can resize the DataView control by clicking and dragging to size in most form types. If used in an
IDS type form, its size depends on the height property of the container that specifically holds the DataView
control. The DataView control fills whatever size its container is.
Top-level menu options
This table shows the top-level menu options in the DataView control in the web client:
Option

Description

Hide Summaries

To hide the Summary icons in the column headers, select this option.

Show Summaries

To display the Summary icons in the column headers, select this option.
Note: This option displays if you select Hide Summaries menu option.

Hide

To hide the selected column, select this option.

Advanced Filter

To launch the Advanced Filter dialog box, select this option.
You can use combinations of AND/OR operators across multiple filter conditions
in a single layout, to filter data from multiple columns.
Consecutive OR conditions are grouped together. An AND operator breaks each
grouping or each condition.
You can also rearrange the sequence and groupings of the filter conditions. To
rearrange the sequence and groupings of the filter conditions, use the up and
down arrows for each filter condition.
A summary of the complete set of filter conditions displays in the dialog box,
including the groupings and the order in which the filter conditions are implemented.

Expression Columns

To launch the Edit DataView Expressions form, in which you can create or edit
expressions, select this option.

Expression Summaries

To launch the Edit DataView Expression Summaries dialog box, which you
can use to maintain expression summaries, select this option.

Move To options
This table shows the Move To menu options in the DataView control in the web client:
Option

Description

Move Columns

To launch the Move Columns dialog box, in which you can rearrange the order
of the columns, select this option.
To move a column, click the Move up or Move down button.

Infor Mongoose Core Extensibility Guide | 212

DataViews

Option

Description

Move first

To move the column to the first position in the grid, select this option.

Move left

To move the column one position to the left, select this option.

Move right

To move the column one position to the right, select this option.

Move last

To move the column to the last position in the grid, select this option.

Manage Layout options
This table shows the Manage Layout menu options in the DataView control in the web client:
Option

Description

Save

To save changes to the current layout, select this option.

Save As

To save the layout as a new layout with a different name, select this option.

Delete

To delete the current layout, select this option.

Select Layout option
This table shows the Select Layout menu option in the DataView control in the web client:
Option

Description

ReportOutput

To select other existing layouts for the current DataView, select this option.

Data options
This table shows the Data menu options in the DataView control in the web client:
Option

Description

Refresh

To requery the data with the same search criteria as the current display, select
this option.

Get More Rows

When the number of available records exceeds the record cap of a query, select
this option to requery and return more records.

Prompt for Inputs

For DataViews that have input parameters with no supplied values, select this
option to perform a new query using different input parameter values.

Send To options
Use these menu options to export the DataView into an output file. When you use these options, the current
state of the DataView is exported. Features such as column order and visibility,filters, and summaries are
preserved and supported in the output file.
This table shows the Send To menu options in the DataView control in the web client:

Infor Mongoose Core Extensibility Guide | 213

DataViews

Option

Description

Excel

To export the DataView as a spreadsheet file, select this option. The output file
opens in Excel, or other compatible spreadsheet application.
Note: When you export a DataView in which summary labels are set to Excel
format, you are prompted whether to include the summary labels in the contents
of the output file. To include the summary labels in the contents of the output
file, select Yes. To exclude the summary labels in the contents of the output file,
select No.

PDF

To export the DataView as a PDF file, select this option.

Display options
This table shows the Display menu options in the DataView control in the web client:
Options

Description

Detail

To launch a dialog box with the details of the current DataView, select this option.

Expand Groups

To display all rows of data in the grid, select this option.

Collapse Groups

To display only the top-level rows of data in the grid, select this option.

About DataView layouts
Once a basic DataView has been created, users who have access to them can rearrange and further organize
the DataView display. You can perform these additional actions:
•
•
•
•
•

Columns can be moved or hidden.
Summaries of data can be created.
Data can be grouped and merged.
Retrieved data can be further filtered.
Custom columns can be created and treated like any other columns. These custom columns can include
calculations that modify and manipulate the data so as to present additional values not available from
just the original data source.

Use these actions to rearrange, reorganize, and manipulate the data that was retrieved.
After the information is organized and displayed as desired, the resulting presentation is considered a layout.
Generally, a layout is only available as long as the DataView is still open and not modified further. However,
any particular layout can be saved and reused.
Furthermore, any given DataView can have virtually any number of layouts created from it, simply by using
the techniques and practices already mentioned. This means that a single DataView can have multiple layouts
and many uses.

Infor Mongoose Core Extensibility Guide | 214

DataViews

Note: DataSearch layouts are the exception to this rule. You can save DataSearch layouts, but you can only
have one layout per DataSearch Source.
Layouts created and saved by Vendor Developers have a scope of "Vendor" associated with them. Users who
have Site Developer editing permissions can save layouts at the Site, Group, and/or User scope level. Individual
users with DataViews access can normally only save layouts at their own user level.
Layouts can be copied and used as the basis for yet other layouts. For example, the copy of a layout might
have additional custom columns added to created calculated values. This version of the layout might then
have a scope of User assigned and be assigned to a single manager.

Copying a DataView layout
You can copy a DataView layout either from the DataView Layouts form or from the Layouts tab of the
DataViews Setup form.
1
2
3
4
5
6
7

From the list of saved layouts, select the one you want to copy.
Click Copy Layout.
Optionally, use the Layout field to rename the copied layout.
Optionally, change the Scope Type to the desired level.
If you changed the Scope Type to either Group or User, use the appropriate drop-down list to specify
the desired group or user.
Optionally, if there are multiple layouts with the same name and scope type, select the Default check
box for whichever layout should be considered as the default layout.
Save your work.

Setting up predefined DataViews
Using the DataViews Setup form, you can create and maintain predefined DataViews, select the IDOs to use
when results are displayed to the user, designate user and group permissions, and select data layout options.
The definition for a predefined DataView consists of up to these basic sets of options:
•

The general settings are the fundamental settings that define the DataView. These settings are made on
the General tab:
•
The DataView name
•
The IDOs that the DataView is set to query
•
How many records the query is limited to
•
The definitions of any filters to be used in the query
•
Other related information
For more information, see Setting up a new DataView on page 208.

Infor Mongoose Core Extensibility Guide | 215

DataViews
•

•
•
•

Input parameters are considered optional and are set using the Input Parameters tab. Input parameters
are used as filtering mechanisms to limit the amount and type of data retrieved. These parameters can
be especially useful in setting up DataViews as sources for formal reports.
You can control who has access to what DataViews by setting user and group permissions on the User
Permissions tab.
You can view information about and make copies of layouts, using the Layouts tab.
Finally, you can determine the way the report will print, using the options on the Report Options tab.
Note: A predefined DataView is first processed as a temporary report, the same as a Report-type form.
Because of this, you can use these options to specify what options are to appear on the final report output.

Specifying DataView setup information - General tab
When you set up a predefined DataView, specify this information on the General tab of the DataViews Setup
form:
Note: You can specify multiple data sources to be queried in the DataView. Specify individual information
for each data source.
Option

Required or Optional?

Description/Comments

IDO Setup

Optional

Use this button to launch the DataView IDO Setup
form and specify exactly what data you want to use
from a specified IDO.
You can use this button/form for each IDO that you
specify for a DataView, to define and limit exactly
what data from each IDO is to be queried.
See Setting additional IDO specifications for a
DataView on page 219.

Source Type

Required

Specify whether the data source is to be an IDO
Collection or a custom load IDO Method.

IDO

Required

Specify what IDO is to be the source for the data to
be returned.

IDO Alias

Required

Verify that the IDO alias is what you want.
This value is generated automatically. You can
modify the recommended alias.

Source Name

Required when enabled

Specify the name of the custom load IDO method
being used as the data source.
This field is enabled only if IDO Method is selected
as the Source Type.

Infor Mongoose Core Extensibility Guide | 216

DataViews

Option

Required or Optional?

Description/Comments

Parent IDO

See Description/Comments.

Use this field to specify an IDO collection that is to
be considered the "parent" of the subcollection
specified in this row.
This field is enabled only if IDO Collection is selected
as the Source Type. It is required only if the specified
IDO collection is a subcollection.

Record Cap

Optional

Use this field to specify the maximum number of
records to return when the DataView query is performed. Select from these options:
•
Use System Setting: This option specifies that
any system-wide record cap override that is in
effect for reports is to be used.
•
Use Specified Max: This option allows you to
set your own maximum for the number of
records to be returned. When selected, this option makes visible and enables the Record Cap
Value field.
Note: This setting is subject to and cannot exceed any record cap overrides for report outputs
that might be in effect.
•

Record Cap Value

See Description/Comments.

Retrieve All: This option instructs the system
to retrieve all available records, regardless of
any record cap settings elsewhere on the system.

This column is visible and enabled only when the
Record Cap setting is Use Specified Max. By default,
this field contains the system setting for the record
cap. You can change it to whatever value you choose.
Note: This setting is subject to and cannot exceed
any record cap overrides for report outputs that
might be in effect.

Filter

Optional

Specify a filter for the system to use when performing the query. This option is enabled only when
Source Type is IDO Collection.
To filter data when Source Type is IDO Method, you
must specify the filter parameters within the method
itself.

Order By

Optional

Optionally, specify which property is to be used to
sort the retrieved results. This option is enabled
only when Source Type is IDO Collection.

Infor Mongoose Core Extensibility Guide | 217

DataViews

Option

Required or Optional?

Description/Comments

Link Type

Optional

Specify whether the parent-child link is to be a multilevel or single-level link.
Note: This option is enabled only if a Parent IDO is
specified.
•

•

Link By

Optional

The Multi Level option (default) nests and indents the child IDO information under the parent information.
The Single Level option links and presents the
parent and child IDO information at the same
level.

Specify how the subcollection is linked to the parent
IDO collection.
Note: This option is enabled only if a Parent IDO is
specified.
Use the syntax described in the topic, IDO Link By
Editor on page 261.

System Record

Optional

To prevent modifications to the setup for the selected IDO by users other than those with Vendor Developer permissions, select this option.
This option is slightly different from the System
Record option for the DataView itself, in that it protects only the selected IDO setup.
The exceptions to this rule are the Record Cap settings and the Show Notes settings.

Show External Notes

Optional

To display any external notes that might be attached
to the DataView, select this option.
When this option is selected, external notes are displayed as child layers in the DataView, underneath
the IDO.

Show Internal Notes

Optional

To display any internal notes that might be attached
to the DataView, select this option.
When this option is selected, internal notes are displayed as child layers in the DataView, underneath
the IDO.

Infor Mongoose Core Extensibility Guide | 218

DataViews

Option

Required or Optional?

Description/Comments

Report Batch Mode

Optional

Specify the report batching mode of the DataView
reports.
•
The None option retrieves rows in a single query
that does not use batch size. The Report Batch
Mode option defaults to None for IDO methodbased DataViews.
•
The Cumulative option retrieves rows one
batch at a time, based on the specified batch
size. The Report Batch Mode option defaults
to Cumulative for IDO collection-based
DataViews.
Note: The System Record is selected unless the
user is a Vendor Developer.

Report Batch Size

Optional

Specify the batch size of the DataView reports.
Note: This option does not have a default value.
If this option is left empty, the default hierarchy is
the first of the following that has a value:
•
•
•

Load Batch Size from Critical Number Parameters
Report Batch Size from Process Defaults
Hard-coded value of 5,000

Setting additional IDO specifications for a DataView
When you create a predefined DataView, you can set up the associated IDO so that only the desired data is
retrieved when the DataView is displayed. This ensures that you minimize the retrieval time and get only the
data that you really want.
Note: This information applies only to predefined DataViews. The data for other types of DataView displays
is controlled by the source from which the display was launched.
Also, the information in this topic is closely related to the information in the topic Specifying DataView setup
information - General tab on page 216. In general, when information is presented in that topic, it is not repeated
here. Only additional information and the expanded capabilities of the DataView IDO Setup form are presented
here.
To configure additional IDO specifications for a DataView, follow these guidelines on the DataView IDO Setup
form:
•
•

IDO section: Depending on the Source Type, you can change or modify several of the settings in this
section. You can also set or modify these settings on the General tab of the DataViews Setup form.
Link By section: As with the Link By option on the DataViews Setup form, these options are enabled
only when a Parent IDO is specified on that form.

Infor Mongoose Core Extensibility Guide | 219

DataViews
This form, however, provides additional specification options. You can specify which properties are to
be used to link and display data. Do this by specifying a Parent Property and a Child Property and then
clicking Add.
•

Order By section: As with the Order By option on the DataViews Setup form, these options are enabled
only when the Source Type is IDO Collection, as specified on that form.
This form, however, provides additional specification options. You can specify properties by which to
sort the data that is displayed. Do this by specifying one or more properties by which to sort the data and
whether the data is to be displayed in Ascending or Descending order; and then clicking Add.
Note: If a record cap is in use, the Order By setting determines which records are selected for retrieval
first.

•

Properties section: You can specify exactly which properties are to be included in the data query by
selecting them in this section. Properties that are not "Selected" are not included in the return results
for the data query.
In addition to selecting which properties to include in the data query, you can also view and/or specify
these settings for each property:
Setting

Description

Description

Read-only. This field displays any description provided for the property, as
specified on the IDO Properties form.

Default Caption

Read-only. This field displays any default caption currently defined for the
property, as specified on the IDO Properties form.
You can override the default caption, using the Caption Override field.

Caption Override

Use this field to override the default caption for a property. Whatever you
provide as a caption override is what displays as the column header for that
property in the DataView Results form.

System Record

When selected, this option prevents anyone other than a Vendor Developer
from making modifications to or deleting a property specification for the
DataView.

Specifying DataView setup information - Input Parameters tab
When you set up a predefined DataView, you can specify this information on the Input Parameters tab of
the DataViews Setup form.
Note: The options on this tab can be especially helpful when setting up a DataView to be used to create
reports. You can use input parameters to specify exactly what data can be used for query ranges or other
return results, using a report criteria form associated with the DataView.

Infor Mongoose Core Extensibility Guide | 220

DataViews

Column

Description

Sequence

These integers indicate the order in which the specified input parameters are
queried.
This number is assigned at the time of the parameter's creation. Once this parameter has been saved, you cannot change it.

Property Name

Use the drop-down list to specify the IDO property to be used for the input parameter.
When you select a property in this field, the Description field is automatically
populated with the default value for the property, if one exists. You can modify
the value in the Description field, if desired.
Note: If the Source Type on the General tab is set to IDO Method, this field is
disabled.

Operator

This field specifies the operation to use in setting a range for the data to be retrieved. When two such operators are used in tandem, you can set an upper
range limit and a lower range limit.
For example, suppose you want to retrieve the values of all customers whose
names begin with the letter 'C'. You could create one parameter using the operator Greater Than Or Equal To with a Description value of C. You could then
create a second parameter using the operator Less Than, with a Description
value of D. By applying both of these parameters to the Property Name of
CustName, you effectively set a range for customers whose names begin with the
letter 'C'.
See DataView input parameter operators on page 478.
Note: If the Source Type on the General tab is set to IDO Method, this field is
disabled.
If you select Event Parameter here, the name of the parameter is required in
the Name column.

Description

Specify a string that describes this parameter.
This string is used when prompting for this parameter, so that the user has an
idea of what the parameter is. In a sense, you can consider this a prompt string
or label string.
This string can be a literal string (for example, Starting Customer) or it can be
the name of a translatable string (for example, sStartingCustomer). When
translatable strings are used, they are translated at run time.
If the DataView is self-prompting, then this string is used as the label for the input
parameter on the DataView Inputs form.
If the DataView component is embedded in a form, then this string is used to
label the input parameter field within the DataView component binding.

Infor Mongoose Core Extensibility Guide | 221

DataViews

Column

Description

End of Day

This option is enabled only when the specified property is a date/time property.
When selected, this option causes the time on a Date/Time property value to
be set to the "end of day," which is defined in the system as 11:59:59.99 PM.
When cleared, the system uses the system date/time to perform the query. The
use of this option allows you to set very precise date/time ranges.
Note: If the Source Type on the General tab is set to IDO Method, this field is
disabled.

Name

This option is enabled and required only if you select Event Parameter as the
Operator. This field specifies the name of a parameter defined for the event in
the Application Event System (AES).

Specifying DataViews setup information - User Permissions tab
Use the User Permissions tab on the DataViews Setup form to specify who can access the DataView that is
being created or modified. You can specify permissions at the user level, the group level, or a combination
of the two.
Column

Description

User Name

Specify users who are to have access to the DataView. The list is populated from
the list of users in the system.

[User] Description

(Read-only) This field displays the description associated with the specified
user, as specified on the Users form.

Group Name

Specify user groups that are to have access to the DataView. The list is populated
from the list of user groups in the system.

[Group] Description

(Read-only) This field displays the description associated with the specified
user group, as specified on the Groups form.

Specifying DataView setup information - Layouts tab
When you set up a predefined DataView, you can specify this information on the Layouts tab of the DataViews
Setup form.
Note: You can also use this tab to create copies of layouts. These duplicate layouts can each have a different
scope and can be designated as the default version of the layout if desired.

Infor Mongoose Core Extensibility Guide | 222

DataViews

Field/Option

Description

Copy Layout

To create a copy of a layout that you can use as the basis of a new layout, click
this button.
When you copy a layout, the copy initially has all the same field values, except
for the Scope Type, which defaults to Site.

Layout

Use this field to name and identify a new layout.
For layouts that have already been saved, this field is read-only and shows the
name of the layout as it was saved.

Scope Type

Use this field to specify the level of scope to which the layout is available. The
default scope for a copied layout is Site.
If you specify a scope of either Group or User, you must also specify a Group
Name or User Name, respectively.
If you have Vendor Developer editing permissions, and you want to define the
scope type as Vendor Default, enter 0 (zero) in this field.

Scope Name

If you specify either Group or User in the Scope Type field, you must specify
the name of the applicable user group or the individual user in this field.
Note: If Group is specified as the Scope Type, this column's header displays
as Group Name. If User is specified as the Scope Type, this column's header
displays as User Name.

Default

When there are multiple layouts with the same Scope Type and Scope Name,
this option identifies the default layout.
The Default layout option is set using the DataView Layouts form. On this form,
the option is read-only.

Caption Override

Optionally, to designate a caption/title to use for the selected layout, specify
the caption.
This can be a translatable string or a literal value.
When specified, this string replaces the default system-generated caption on
DataView report outputs.
Note: You can specify a DataView-level caption override, by setting the Caption
Override field at the top of the form. If both fields have caption overrides, the
layout-specific override here takes precedence.

Report Orientation

Use this field to set the orientation (Landscape or Portrait) for the selected
layout.
Note: You can specify a DataView-level orientation, by setting the Report Orientation field at the top of the form. If both fields have orientations specified,
the layout-specific setting here takes precedence.

Infor Mongoose Core Extensibility Guide | 223

DataViews

Specifying DataView setup information - Report Options tab
When you set up a predefined DataView, you can specify this information on the Report Options tab of the
DataViews Setup form.
Fields/Options

Description

Report Orientation

Select either the Portrait or Landscape orientation.

Max Cap Exceeded Notification Type

A notification is displayed when the results of a report is limited due to the maximum record cap that
is set at either the process default or at the farm
level.
Note: This option is only visible when you enable
the FarmReportMaxCapExceededNotification setting
in the Configuration Manager.
Select the type of notification:
•
•
•

None: No notification.
Warning: The default value. A warning message
is displayed on the report footer region.
Error: The generation of the report fails and the
error information is displayed on the Background Task History form.
Note: For sub-reports, the error is returned to
the parent report and is displayed inline in place
of the sub-report content.

•
•
•
•
•
•
•

Display Report Header
Display Page Header and Footer
Repeat Headers on New Page
Repeat Headers on Collection Change
Insert Page Break Between Groups
Reset Page Number Between Groups
Can Grow

Because the predefined report output is first determined by a temporary Report-type form layout,
these settings are all the same as those used for the
Report-type form.

Copying a DataView to create a new one
You can copy an existing DataView that is close to what you want, and then modify it and save it as a new
DataView.
1
2
3

With the DataViews Setup form open, select the DataView that you want to copy, and click Copy DataView.
In the DataViews Copy form, verify that the Current DataView is the one that you want to copy.
In the New DataView field, specify a name for the new DataView.

Infor Mongoose Core Extensibility Guide | 224

DataViews
4
5

Click Copy DataView.
When the confirmation message displays, click OK.
The DataViews Copy form closes, and the new DataView copy is added to the list of DataViews in the
DataViews Setup form.

6

Select your new DataView and modify it as desired.
Use the procedure in Setting up a new DataView on page 208, starting with the step to specify the required
and optional information for each IDO.

Setting up a DataView filter
You can use filters in a predefined DataView to retrieve exactly the data you want for the DataView.
1 Open the DataViews Setup form and execute Filter-In-Place.
2 Click IDO Setup.
3 In the DataView IDO Setup form, click Filter.
Note: You cannot open the DataView IDO Setup form directly.
4
5
6

In the Property Name field, specify the IDO property for which you want to set up the filter.
Specify the operator to use for the filter. If you select IS NULL or IS NOT NULL, the comparison field and
value need not be specified.
Select the comparison type:
•
Literal: The property value is compared to a hard-coded value.
•
DataView Property: The property value is compared to another property value from the DataView.
This can be useful when you want to use a comparison of two properties to control what records are
returned. For example, if you wanted to see which orders do not have the full number of items
shipped, you could set up the property comparison QtyShipped < QtyOrdered. Or suppose you wanted
to know which orders were not shipped on time, you could set up the property comparison ShipDate
> DueDate.
•

Form Property: Use this option in cases where the DataView is embedded on a form and you want
to filter the DataView based on properties from the form's IDO collections.
For example, you could use this option to filter the DataView's customer number property by the
form's customer number property. When you then add the filter clause, the resulting syntax might
look something like this: CustNum = FP(CustNum)

7

In the last field on this row, specify the comparison value to be used for the corresponding comparison
type.
8 Optionally, use the previous steps to specify additional filter clauses.
9 Optionally, if using multiple filter clauses, to instruct the system to treat the clauses as Boolean OR
comparisons rather than Boolean AND comparisons, select the check box labeled OR Instead of AND
with Previous Clause.
10 To add the filter clause to the list of clauses in the display panel, click Add.
The clause is added to the list, formatted with the proper syntax.

Infor Mongoose Core Extensibility Guide | 225

DataViews
11 Optionally, to remove all filter clauses and start over, click Remove.
Note: You cannot selectively remove individual filter clauses: If you choose to remove one, you remove
them all.
12 Click OK.
For complex comparison logic, it might be necessary to add parentheses around filter clauses, to make sure
they are evaluated properly. To accomplish this task, you can manually edit the data in the display list.

Setting up the right-click menu for DataViews
You can use the DataView Actions Setup form to set up right-click menu actions for components. You can
specify the forms to open, executables to run, and so on. The options you specify are displayed alphabetically
in the dynamic menu list. You can specify a menu action at a class level so that the action is displayed each
time a property of that class is displayed. For example, actions associated with the UserClass class can be
available on any component that display a User Class field. You can also associate a menu action with a
specific IDO or property to limit the action's availability to a more specific set of components.
Note: The options you specify in the dynamic menu list are displayed alphabetically within Action Type and
are displayed below the static menu options.
Each time a user right-clicks on a component, the system dynamically builds the menu options based on the
menu actions that are defined for the class, IDO, and property of the component value. The user can perform
any of these types of actions that are defined:
•
•
•
•

Launch a specific form that is filtered to show values from the selected component value.
Run an executable program and pass it parameters that are values from the selected component value.
Launch another component value.
Perform a global search, which are available on all columns, that launches the DataSearch form that is
filtered with values from the selected component value.

Setting the caption for the menu option
In the Caption field, specify the text that displays for this action in the right-click menu. You can specify a
translatable string name here if you want the option to be translatable.
This field can be used to suppress multiple occurrences of the same command if the same action is available
at different scope levels. Only one occurrence of a caption with the same name is displayed. If there are
duplicate actions, the more specific level takes precedence: User, then Group, then Site, then Vendor.
Displaying the action on the Action menu
If the action should be displayed on the Action menu, select Active. Clear this field to temporarily disable an
action.

Infor Mongoose Core Extensibility Guide | 226

DataViews
Specifying "Applies To" information
To set up actions that are shared by multiple forms and components, use the Applies To section of the
DataView Actions Setup form. This creates a structure that identifies the cases where an action is displayed.
Specify which property class, IDO, or property should have access to the action you are defining. If any fields
in this section are left blank, the action applies to everything in that group. For example, if you specify a Class
Name of CustNum, the menu action is enabled for every component that is associated with a customer number.
However, if you specify the IDO as WBFSCustomers, then only those components that are built using the
WBFSCustomers IDO display the menu action.
In this section, you can specify this information:
Scope
Specify the level of scope to which this action applies: Vendor, Site, Group, or User. If the scope is Group
or User, you must also specify the group name or user name.
Class Name
Specify the name of an IDO property class to which this action applies.
Specifying "Action" information
Select the Action Type to be performed by this menu option:
•

Run DataView: Provide this additional information:
DataView
Select the predefined DataView that is to be run when the option is clicked.
Filter Property
Optionally, select the property that is to be filtered on when retrieving data for the DataView. See "About
filters."
Layout
Optionally, select the layout to be applied when the DataView is launched.
Additional Filter(s)
Optionally, provide additional filters to use on the data being retrieved. You can use the Additional
Filter(s) button to launch the DataView Actions Filter Setup form, in which you can define and add
the filters, correctly formatted.

•

Run Form: Provide this additional information:
Form
Select the form to launch when the right-click menu option is clicked.
Filter Property
Optionally, select the property that is to be filtered on when retrieving data for the DataView. See "About
Filters."
Initial Command
Select the action that is to be taken when the form launches:
•
Refresh: This option opens the form and immediately returns all records in the collection (up to
the record cap).

Infor Mongoose Core Extensibility Guide | 227

DataViews
•
•

Add: This option opens the form in such a way that you are set to create a new record.
Filter In Place: This option opens the form in Filter-in-Place mode, so that you can set criteria by
which to filter the records for display.

Additional Filter(s)
Optionally, provide additional filters to use on the data being retrieved. You can use the Additional
Filter(s) button to launch the DataView Actions Filter Setup form, in which you can define and add
the filters, correctly formatted.
Set Variables
Specify any variables to be set on the target form when it launches.
Separate the variables in a list of multiple variables with commas. The list of variables must contain the
values to which to set them.
For example, for a DataView action that opens the User Class Report using the selected component,
ClassNameStarting, set the value in this field to
ClassNameStarting=FP(UserClass),ClassNameEnding=FP(UserClass), where FP is a substitution keyword.
This example sets both the user class starting and ending range of the report to the value of the UserClass
in the form where you selected the action. The substitution keyword CURPROP() could be used instead
of UserClass if there is a possibility that the property might have a different name, for example,
DefUserClass. See "Substitution keywords on page 229."
•

Run Executable: Provide this additional information:
Executable Name
Provide the path and filename of the executable program that you want to run when the action is selected.
You can use the Browse button to locate and select the executalbe program.
Note: The executable program runs on the client side. If users who select this action cannot access the
executable file on their local computers using the path you specify here, an error message displays.
Command Line Parameters
Enter any command line parameters you want to use to further customize the way in which the executable
program is to open. See About application startup parameters on page 568.

About filters
If the Action Type is Run DataView or Run Form, you can use the Filter Property and Additional Filter fields
to specify how you want to filter the data returns.
The Filter Property is the property on the target form or DataView on which the value of the current property
is filtered. For example, if you define an action with the caption Item Details, where the Action Type is Run
Form and the Form Name is Items, then if a user right-clicks on the item number CP-10000 in a component
and selects Item Details, the Filter Property is the property in the Items form that is filtered by CP-10000.
You can apply additional filters using the Additional Filter field and button.
See Setting Up a DataView Actions Filter on page 229.

Infor Mongoose Core Extensibility Guide | 228

DataViews
Substitution keywords
Substitutions are supported in the Additional Filter, Command Line Parameters, and Set Variables fields.
Supported substitution keywords are P(...), FP(...), and CURPROP(). These keywords work the same way as
they work in the designer (Web Designer or Design Mode), except that if the component is a DataView, P and
FP refer to the properties in the current row of the DataView instead of the form collection, and CURPROP()
refers to the name of the property on which the user right-clicked. Substitutions can be used in these cases:
•
•
•

To filter by additional values in the collection
To set variables on target forms from values in the form
To pass a value from the form to an executable by means of a command line parameter

Setting up a DataView Actions filter
You can build a filter to use on a DataView action.
1
2
3

Open the DataView Actions Setup form and select the DataView you want to filter for.
Click Additional Filter(s).
In the DataView Actions Filter Setup form, select the property of the IDO to be evaluated.
For an Action Type of Run DataView, this is the primary collection of the predefined DataView. For a
Action Type of Run Form, this is the primary collection on the target form.
If this form is launched from the DataView Actions Setup form with a Run Form action type selected,
the Property Name drop-down list shows all bound and derived properties in the IDO, not just those
that are bound to the form.

4

Select the operation.
If you select IS NULL or IS NOT NULL, do not specify anything in the Comparison field and value.

5

Select the comparison type:
•
Literal: The property is compared to a specified value.
•
Target DataView Property: Use this IDO property from the target DataView (the DataView that will
be opened by the action) to filter the DataView that is opened.
For example, if the action opens a Customer Order Lines DataView, this filter might show only
unfilled order lines, where QtyOrdered > QtyShipped.
•

Source DataView Property: Use this IDO property from the source DataView (where the user
right-clicked) to filter the form or DataView that is opened by the action.
For example, if the user right-clicks on a customer number and selects an Action menu option that
opens the Customer Orders form, the additional filter created here might include the Ship To
(CustSeq) property as part of the filter, like this: CustSeq=FP(CustSeq).

6
7
8

If you selected Literal, provide a comparison value.
To translate the information from the fields into filter syntax and show the filter expression in the editor
box, clickAdd.
Click OK to save changes.

Infor Mongoose Core Extensibility Guide | 229

DataViews

Note:
•
•
•

To clear the contents of the editor box, click Remove.
For complex comparison logic, it might be necessary to manually edit the text in the editor box to add
parentheses around or and and statements, to ensure that the filter is evaluated properly.
To better understand how filters can be used, look at the Vendor-level DataViews that are provided as
part of the application.

About Load Just in Time options
The Chart, Gauge, and DataView components can take some time to load due to the volume of data that these
controls require, affecting the initial load and refresh performance of a form. If you need to reduce the loading
time of a form, you can defer the initial loading and drawing of these controls until such time that they are
visible in the form.
Use the Load Just in Time option in the Specify Chart Settings, Specify Gauge Settings, and Specify
DataView Settings forms to defer the loading of Chart, Gauge, and DataView controls, respectively.

About expression columns
Using the Edit DataView Expressions form, you can create new columns within DataViews, based on
expressions.
These expression columns can be created in runtime, when you interact with the DataView through the Edit
DataView Expression form. This form functions similarly to the Edit Expression Binding dialog box with
these changes:
•
•
•

DATAVIEWPROP and DATAVIEWEXP functions are supported.
V, P, and C functions are not supported.
Aggregate functions are applied to DataView IDOs.

Note: The syntax or keywords are similar to component expressions with adjustments made for
DataView-specific functionality.
The expressions are stored and retrieved as part of the DataView layouts. When loading a DataView layout
that contains expressions, expression columns are created and the values are calculated based on the
expression.
Note: A DataView report run by Taskman creates a temporary Mongoose report-type form and then sends
that form to the form report processor to create the report. During the creation of the temporary report form,
DataView expressions are translated into component expressions for inclusion in the report.
This is an example of how to format an expression for use with expression columns in DataViews:
DATAVIEWPROP( OnHand ) - DATAVIEWPROP( QtyRsvd )

Infor Mongoose Core Extensibility Guide | 230

DataViews

This expression uses the quantity of an item that is immediately available and subtracts the quantity of that
item that is reserved to calculate the quantity of the item that remains.

Creating expression columns
To maintain the columns for the DataView layout and the Edit DataView Expression form to build a single
expression, use the Edit DataView Expressions form.
1
2
3
4

While DataView results are displayed, click the feature chooser button represented by a gear icon.
Click Expression Columns.
On the Edit DataView Expressions form, click Add.
Provide this information:
IDO Alias
Select the IDO alias for the DataView that is being used.
Name
Provide a name for the expression column.
Caption
Optionally, provide a caption for the expression column.
Note: You can specify a translatable string or a literal string.
Data Type
Select the data type that is used to format the expression column.
Expression
Specify the expression.
Note: You can also click the Edit button to build the expression. This button opens the Edit DataView
Expression form.

5

Optionally, on the Edit DataView Expression form, perform these steps:
a In the Function field, select what type of expression function to perform on the values returned for
the expression column.
b Specify the operand values.
Note: Click Edit to recursively open the expression editor to build or edit the operand value. The
function information from the previous form is contained in the Containing Expression field, and
the operand value placement is indicated by two apostrophes.
c

6

Click Validate.

Click OK.

To delete an expression column, select the row that you want to remove and click Delete.

Infor Mongoose Core Extensibility Guide | 231

DataViews

About expression summaries
Using the Edit DataView Expression Summaries form, you can create expression summaries within DataViews,
based on expressions.
These expression summaries can be created in runtime, when you interact with the DataView through the
Edit DataView Expression Summaries form.
The expression summaries are stored in DataView Layouts and applied when the layout is applied.
Note: The DataView reports generated as background reports through TaskMan calculate and show expression
summaries for groups and overall summaries.

Creating expression summaries
Use the Edit DataView Expressions Summaries form to maintain the expression summaries for the DataView
layout and the Edit DataView Expression form to build a single expression.
1
2
3
4

While DataView results are displayed, click the Feature chooser button represented by a gear icon.
Click Expression Columns.
On the Edit DataView Expression Summaries form, click Add.
Provide this information:
Column
Select the column to be used for the expression summary.
Name
Provide a name for the expression summary.
Caption
Optionally, provide a caption for the expression summary.
Expression
Provide the expression.
Note: You can also click the Edit button to build the expression. This button opens the Edit DataView
Expression form.

5

Optionally, on the Edit DataView Expression form, perform these steps:
a In the Function field, select what type of expression function to perform on the values returned for
the expression column.
b Provide the operand values.
Note: Click Edit to recursively open the expression editor to build or edit the operand value. The
function information from the previous form is contained in the Containing Expression field, and
the operand value placement is indicated by two apostrophes.
c

Click Validate.

Infor Mongoose Core Extensibility Guide | 232

DataViews
6

Click OK.

Infor Mongoose Core Extensibility Guide | 233

DataSearch

Chapter 11: DataSearch

