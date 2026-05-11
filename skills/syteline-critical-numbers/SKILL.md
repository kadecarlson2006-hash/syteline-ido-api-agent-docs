---
name: "syteline-critical-numbers"
description: "Critical numbers and drilldowns - defining, parameters, goals, alerts, snapshots, IDO method/stored procedure drilldowns, sub-drilldowns, filters"
version: "1.0"
tags:
  - syteline
  - critical-numbers
  - drilldowns
  - mongoose
---

# syteline-critical-numbers

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

Note: Only SQL tables that are set to Unlocked in the Metadata Access Controls form display in
this list. You can select SQL tables that are set to Read Only but you cannot modify them.
d
e
f
g
h
i
j

3

In the Class Name field, select a user class to associate with the table.
To enable you to draw the user-defined fields on forms, select Active.
Click Rule Assistant.
Select a field, an operator and a value for the rule.
If you want to find all records that match one of the search criteria instead of finding all records that
match the search criteria in the previous clause, select OR instead of AND with previous clause.
Click Add.
The rule displays in the (Criteria) field.
Click OK.

Save your changes.

Creating a user class
1
2
3

Open the UET Classes form and execute Filter-In-Place.
Initiate a new record.
Provide this information:
Class Name
Provide the name the system is to use in identifying the user class. This name is an internal name and
does not display anywhere other than on the UET-related forms.
Label
Optionally, provide a label for the system is to use in the UI.
Description
Optionally, provide a description to explain the intended use or purpose of the user class.

4

Save your work.

The user class is now available for use within forms.

Infor Mongoose Core Extensibility Guide | 176

User-extended tables (UETs)

Creating user-defined fields
If you have the correct editing permissions, you can add your own user-defined fields to most forms in your
Mongoose-based application. These user-defined fields can be assigned for use at the default site leve, the
group level, or the individual user level.
1 Open the UET User Fields form and execute Filter-In-Place.
2 Initiate a new record.
3 Provide this information:
User Field Name
Specify a unique name, prefixed with Uf.
The "Uf" prefix is not case sensitive, so you can specify something like Uf_Category , uf_Category, or
UF_Category as a user field name. Other than the underscore character, you cannot use special characters,
only alphanumeric characters.
User Data Type
Optionally, select a user data type.
The user data type is a named combination of the data type, precision, and decimals of the user field. If
you select an existing user data type, the next three fields become read-only, according to the user data
type specifications.
Data Type
If you left the User Data Type field blank, select a standard SQL Server data type for the new field.
Precision
If you selected a Data Type of decimal, nchar, nvarchar, or varbinary, specify the number of
alphanumeric characters that can be entered in the field.
Decimals
If you selected Decimal in the Data Type field, specify the maximum number of decimal places to be
available in the new field.
Initial Value
Optionally, specify an initial value to display in the user field. This can be a default or prompt value.
Description
Optionally, enter a description of the user field, typically to provide the reason or purpose for the new
field.
4

Save your work.

About user-defined data types
The term "user-defined data type" refers to a named group of data values that can be used as a list source
for a component. For example, a user-defined data type named "Size" could invoke a list of values consisting
of "Small," "Medium," and "Large."

Infor Mongoose Core Extensibility Guide | 177

User-extended tables (UETs)
You can create user-defined data types and add values to them using the User Defined Types form. Value
and Description pairs display in a two-column list for each type that you define. The list of all values for all
user-defined data types are included in the User Defined Type Values form.
After a user-defined data type is specified as the source for a component that supports lists (a combo box,
for example), users can add values to the type directly through the right-click menu Add function. In the
example provided, a user might add the value "X-Large."
User-defined data types are often useful with user-extended tables (UETs). They can also be used with
user-defined fields. Users can add list-type components to forms without modifying the schema or IDOs or
writing special form code.

Defining an index for a user class
For any user class you create, you can also create an index.
1 Open the UET Class/Index Relationships form and execute Filter-In-Place.
2 Initiate a new record.
3 Provide this information:
Class Name
Select the name of the class to index.
Index Name
Specify a unique name for the index.
Description
Optionally, provide a description for the index.
Field Name
Select the user-defined field to include in the index.
Ascending
To have the field values in the index sort in ascending order, select this option.
4

Save your work.

Drawing UET fields on forms
Once you have created a user-extended table (UET), you can add appropriate field component forms:
1
2
3
4

Open the form to which you want to add a UET field in the Web Designer.
In the Toolbox, click the component type that corresponds to the user-defined field you want to create.
Add the component to the form.
In the Component Properties sheet, set the properties and attributes for the user-defined field.

Infor Mongoose Core Extensibility Guide | 178

User-extended tables (UETs)
When binding a UET user-defined field, the Binding property is specified as object.<tableAlias><UDFname>,
where:
•
tableAlias is the three-character abbreviation for the name of the table.
•
UDFname is the name of the user-defined field.
5

Save your work.

Impacting the schema
After you make changes to form information using user-extended table (UET) form, you must ensure that
those changes are saved in the schema.
1 Open the UET Impact Schema form and specify these options: select Commit Form Changes.
Commit Form Changes
Verify that this option is selected.
This option ensures that any changes you have made to form information using the UET forms is saved.
Note: If you select this option but not the Impact Schema option, when you click Process, the changes
you have made are saved, but the schema is not affected.
Rollback Form Changes
To undo any changes you have made to UET form and fields since the last Commit action, select this
option.
Impact Schema
To change the database schema to contain the columns and indexes corresponding to the user-defined
fields you have created in any UET-based forms, select this option.
Corresponding views over multi-site tables are also updated.
Note: If you select this option but not the Commit Form Changes option (and you have not previously
committed your changes), if you click Process, nothing happens.
2

Click Process.

If your system is multi-site and the table where you add the UET, or the corresponding _all table, is replicating
data, there are additional steps you must take.

Infor Mongoose Core Extensibility Guide | 179

Critical numbers

Chapter 8: Critical numbers

About critical numbers
Critical numbers are key performance indicators (KPIs) that you can use to get a quick view of important
statistics or track progress. Use critical numbers to answer these kinds of questions:
•
•
•

How am I doing?
What should I be doing?
Where are things in jeopardy?

Critical numbers are based on stored procedure calculations or on IDO calculations. With the appropriate
permissions, you can add critical numbers to forms. This application is designed to support both simple and
complex critical numbers to meet a wide range of users and needs.
For example, within the IDO critical number definitions, you can quickly generate numbers based on this kind
of information:
•
•
•

The sum, count, average, minimum or maximum of a value
The Group By and Date Range properties
Comparisons with other properties on the form

You can see the details behind the critical number using an enhanced drilldown and do further analysis on
issues you discover.
Where critical numbers can be used
Critical numbers can be added to any form. To add a critical number as a component on a form, you must
have the authorizations and editing permissions to access the form and, in Design Mode, to add the component.
Critical numbers can also be used in widgets on a user's Start form.
Critical numbers can be surfaced as Infor OS Portal® widgets, or used as widgets in the workspaces or
homepages. To configure this, use the User Critical Number Selection form.
Critical number definitions
Before they can be used to display data on a form, critical numbers must be defined. Define critical numbers
on the Critical Numbers Setup form. On this form, the basic process is this:
•
•
•

Specify a source for the data that is to be used.
Define how the critical number's actual value is to be calculated.
Determine whether to use Goal and Alert settings, and if so, how.

Infor Mongoose Core Extensibility Guide | 180

Critical numbers
See About critical number goals and alerts on page 181.
•

Optionally, you can also include this information:
•
Specify what critical number drilldowns are to be used when the user seeks more information about
how the critical number was calculated and what it means.
See About critical number drilldowns on page 193.
•

Determine what groups and users have access to the critical number data, using critical number
categories and individual user or group assignments.
The ability to view a critical number is controlled by the permissions set for the specific number.
User or group permissions can be set for a category of critical numbers using the Critical Number
Category form; or user permissions can be set for a specific number on the Critical Number Setup
form.

•

Define critical number parameters and how they are to be used in critical number calculations and
displays.
See Setting up a critical number drilldown on page 193.

•

Determine and set up filters to be used in retrieving critical number data.
See Setting up a critical number/drilldown IDO filter on page 201.

•

Specify whether snapshots of critical number values are to be captured whenever the critical number
is queried.
See About critical number snapshots on page 191.

For the complete procedure to define a critical number, see Defining a critical number on page 182.
Goal and Alert settings
Critical numbers are color-coded to show their Goal and Alert status. These colors are used by default:
•
•
•
•

Red: The value of the number is in the alert or critical range.
Yellow: The value of the number is in the warning range.
Green: The value of the number is within the normal or ideal range.
Gray: The number represents an "informational" value only.

You can use themes to change from the default colors.

About critical number goals and alerts
Goals and alerts are used in critical numbers to determine how the critical numbers are to be displayed and
interpreted. Goal and alert settings indicate thresholds that can be used to indicate when goals are being
met, when goals are in jeopardy, and when goals are not being met.
•
•
•

Goals: Anything in the "Goal" range is considered good and by default is displayed using a green color.
Alerts: Anything in the "Alert" range is considered bad and by default is displayed using a red color.
Warnings: When both goal and alert settings have been defined, a "warning" range is generated
automatically between them. This range by default is displayed using a yellow color.

Infor Mongoose Core Extensibility Guide | 181

Critical numbers
If you choose to use a Goal setting without a corresponding Alert setting, anything not in the acceptable
(goal) range is considered a Warning.
If you choose to use an Alert setting without a corresponding Goal setting, anything not in the
unacceptable (Alert) range is considered a Warning.
•

Informational displays: When neither a Goal nor an Alert setting has been defined, the critical number
display is considered to be informational only. Informational numbers are values that you want to monitor
but not set thresholds for. By default, informational displays use a gray color.

You can change the default colors for each of these types of critical number values, by using themes.
When using email generation with critical numbers, you can choose which of these four states (Goal, Alert,
Warning, Informational) is to generate emails to appropriate individuals. To make these settings, use the
Email Generation form.
Typically, the Goal and Alert operators are set opposite of each other.
For example, with a Goal < 1000 and a corresponding Alert < 10000, this leaves the Warning from 1000 to
10,000.
Therefore, if the value of the critical number was for example:
if the value is 500 the gauge would show green as the Goal was met.
If the value is over 1000 the gauge would show yellow for a warning.
If the value is over 10,000 the gauge would show red as the alert level was met.
Note: The setup form permits Goals and Alerts operators to point in the same direction. This may create
undesired results in the color displays on the gauge.

Defining a critical number
Critical numbers help users track how they are doing. Critical numbers can be based on a stored procedure
calculation or on an IDO calculation. To define a critical number, use the Critical Numbers Setup form.
1
2

Open the Critical Numbers Setup form and execute Filter-In-Place.
Initiate a new critical number record.
To create a critical number based on an existing one, you can copy and modify the existing critical number.

3

Provide this information:
Critical Number
Required. This must be a unique integer. If you do not specify an integer here, the system automatically
assigns the next available integer.
Active
When selected (the default), this option activates the critical number and makes it functional. When
cleared, this option deactivates the critical number.
Snapshot
To keep a snapshot history of the critical number, select this option.
Infor Mongoose Core Extensibility Guide | 182

Critical numbers
Description
Specify a description of the critical number. This can include information about what it represents, or
how it is intended to be used. The value in this field is used as the caption on any gauges, or other chart
elements that might be used to display the critical number's value.
Short Desc
Specify a shorter description that the system can use in the Subject line of emails related to it.
Result Divisor
If the critical number is expected to return and display large values, and you want to divide those values
before displaying them, specify the number by which to divide the value before it is displayed.
For example, if you expect the numbers to be retrieve to have values in the millions, such as 120,000,000,
you can set this field to one million (1,000,000), and the resulting display shows that value as 120. In a
case like this, you would want to set the Description field to (in millions) to clarify what value the
number (120) actually represents.
4

On theGeneral tab, specify the optional source, calculations, alert values to use, and goals to apply.
For more information, see Making Critical Number General Tab Settings on page 553.

5

Optionally, use the Drilldowns tab to assign drilldowns to be used for this critical number.
Drilldowns are defined on theDrilldowns Setup form.

6

Optionally, use the Categories tab to assign the critical number to one or more critical number categories.
For more information, see Critical Number Categories on page 299.

7
8
9

Optionally, use the Users tab to assign (or deny) permissions to individual users.
Optionally, use the Groups tab to assign (or deny) permissions to groups of users.
Optionally, use the Static Parameters tab to define name-value pairs that are used to allow end users
to change critical number values without modifying the source code.
Note: Static parameter options are most effective when the source of data for the critical number is a
stored procedure. When the data source is an IDO, the same end is accomplished much more easily using
filters.

10 Optionally, use the Input Parameters tab to define and list filters that are used to determine what
information the critical number is based on.
The Sequence number determines the order in which the IDO properties are evaluated during a critical
number query. This data provides readable labels for the stored procedure parameters throughout the
system.
11 Optionally, use the Snapshots tab to view a snapshot history of this critical number.
From this tab, you can launch the Critical Number Snapshots form to view details of the snapshots for
the selected critical number.
You can also export the snapshot data to a CSV (comma-separated values) file that you can open in a
spreadsheet application.
12 Save your changes.

Infor Mongoose Core Extensibility Guide | 183

Critical numbers

Setting up critical number parameters
To set up parameters and values that are generic to all critical numbers and global settings, use the fields on
the Critical Number Parameters form.
1

On the General tab, provide this information:
Alert Symbol
Specify a character to indicate a critical number alert condition on emails that are automatically
generated.
The default character is a capital X.
Warning Symbol
Specify a character to indicate a critical number warning on emails that are automatically generated.
The default character is a minus symbol (-).
Goal Symbol
Specify a character to indicate a critical number goal condition on emails that are automatically generated.
The default character is a plus sign (+).
Load Batch Size
Specify the maximum number of records to be pulled at one time from the database when the system
retrieves data for critical number drilldowns and DataViews.
This setting does not prevent you from retrieving more records, it simply controls the batch size. So, if
you want 12,500 records, and this option is set to 5,000, it requires three rounds of queries, instead of
one, to retrieve the entire number of records (5,000 + 5,000 + 2,500).

2

On the Global tab, specify user definable settings to be used for multiple critical numbers.
Note: If the numbers are specific to a critical number, we recommend that you assign those to the
individual critical number with which they are associated.

Setting up multiple results for one critical number
For processes that run through a large amount of data, you can create multiple results for one critical number
calculation.
Multiple results for an IDO-based critical number
These are the actions required to set up multiple results for an IDO-based critical number:
•

Build an IDO-based critical number.
See Creating Critical Numbers on page 182.

•

To generate multiple results, select values for the Group By or Date Property fields.

Infor Mongoose Core Extensibility Guide | 184

Critical numbers
Multiple results for a stored procedure-based critical number
Generating multiple results with stored procedure-based critical numbers is more complex. The preconfigured
AR Age critical number is an example of one record that can be set up to create hundreds of other numbers,
when programmed correctly.
These are the actions required to set up multiple results for a stored procedure-based critical number:
•

Build the stored procedure-based critical number.
See Creating Critical Numbers on page 182.
Note: Consider using static parameters to set up the Goal Value and Alert Value, and other settings for
each record you want to create. For example, the AR Age number uses Alert-1, Alert-2, Alert-3€¦.Alert-7,
Goal-1, Goal-2, Goal-3...Goal-7, Bucket-1, Bucket-2...Bucket-7. This ensures that the values are not
hard-coded and the end-user can change them without modifying the stored procedure.

•

Write your routine as you do others, but instead of assigning only @Actual, you must create all the
#tt_cr_nums records that you want to include. Call the standard procedure WBLoadCrAddSp to accomplish
this:
CREATE PROCEDURE dbo.WBLoadCrAddSp (
, @Id
nvarchar(500)
, @Amount
AmountType
, @Description NVARCHAR(500)
, @GoalVal
AmountType = NULL
, @AlertVal
AmountType = NULL
, @MessageTxt Infobar = NULL
, @GoalOper
WBOperatorType = NULL
, @AlertOper
WBOperatorType = NULL
) AS

@KPINum

WBKPINumType, @Category

WBCategoryType

Notes
Because the results have the same critical number, set the Id parameter to distinguish the numbers. The Id
is passed to the drilldown so you can view the appropriate information.
GoalVal and AlertVal are optional. The system uses the values from the Critical Number Setup form if you
do not override them.

Example: IDO method critical numbers
To set up a program, create a simple critical number in the Critical Numbers form.
This is an example of IDO method-based critical number:
[IDOMethod]
public int UserNamesCount( int KpiNum, DateTime AsOfDate, ref decimal Amount,
string Parm1, string Parm2, string Parm3, string Parm4, string Parm5, string Parm6,
string Parm7, string Parm8, string Parm9,
string Parm10, string Parm11, string Parm12, string Parm13, string Parm14, string Parm15,
string Parm16, string Parm17, string Parm18, string Parm19,

Infor Mongoose Core Extensibility Guide | 185

Critical numbers

string Parm20, string Parm21, string Parm22, string Parm23, string Parm24, string Parm25,
string Parm26, string Parm27, string Parm28, string Parm29,
string Parm30, string Parm31, string Parm32, string Parm33, string Parm34, string Parm35,
string Parm36, string Parm37, string Parm38, string Parm39,
string Parm40, string Parm41, string Parm42, string Parm43, string Parm44, string Parm45,
string Parm46, string Parm47, string Parm48, string Parm49,
string Parm50 )
{
int severity = 0;
object result;

Text );

using ( ApplicationDB appDB = IDORuntime.Context.CreateApplicationDB() )
{
using ( var cmd = appDB.CreateCommand() )
{
cmd.CommandType = CommandType.Text;
cmd.Connection = appDB.Connection;
cmd.CommandText = string.Concat( " SELECT COUNT(*) ",
" FROM usernames un "
);
cmd.CommandText = IDORuntime.AppDataProvider.MassageQueryForProvider( cmd.Command
try
{
result = cmd.ExecuteScalar();
if ( result == null )
Amount = 0;
else
Amount = int.Parse( result.ToString() );

}
}

}

}
catch
{
severity = 16;
Amount = 0;
}

return severity;

Example: Stored procedure critical number
1

Set up your critical number in the Critical Numbers form.
•
Choose a program name for your custom stored procedure.
We recommend that you create a naming convention for your custom procedures so that they do
not conflict with current or future procedures. For example, you can use a prefix that includes your
company name.
•

•

Choose the parameters you want to be able to set without changing the values in the code. Careful
planning of parameter definitions can make the same program usable for multiple critical numbers
you want to retrieve. For example, you can set a specific buyer's ID or make the same program run
against three different warehouses.
Keep in mind that you can override any and all settings on this form in your code.

Infor Mongoose Core Extensibility Guide | 186

Critical numbers
2

Open your preferred code editor and create your custom stored procedure. The parameters of every
critical number are the same. For example:
CREATE PROCEDURE [dbo].[SSSWBCanCoBookSp] (
@KPINum
WBKPINumType
, @AsOfDate DateType
, @Amount
AmountType OUTPUT
, @Parm1
WBSourceNameType = NULL
, @Parm2
WBSourceNameType = NULL
, @Parm3
WBSourceNameType = NULL
, @Parm4
WBSourceNameType = NULL
, @Parm5
WBSourceNameType = NULL
, @Parm6
WBSourceNameType = NULL
, @Parm7
WBSourceNameType = NULL
, @Parm8
WBSourceNameType = NULL
, @Parm9
WBSourceNameType = NULL
, @Parm10
WBSourceNameType = NULL
, @Parm11
WBSourceNameType = NULL
, @Parm12
WBSourceNameType = NULL
, @Parm13
WBSourceNameType = NULL
, @Parm14
WBSourceNameType = NULL
, @Parm15
WBSourceNameType = NULL
, @Parm16
WBSourceNameType = NULL
, @Parm17
WBSourceNameType = NULL
, @Parm18
WBSourceNameType = NULL
, @Parm19
WBSourceNameType = NULL
, @Parm20
WBSourceNameType = NULL
, @Parm21
WBSourceNameType = NULL
, @Parm22
WBSourceNameType = NULL
, @Parm23
WBSourceNameType = NULL
, @Parm24
WBSourceNameType = NULL
, @Parm25
WBSourceNameType = NULL
, @Parm26
WBSourceNameType = NULL
, @Parm27
WBSourceNameType = NULL
, @Parm28
WBSourceNameType = NULL
, @Parm29
WBSourceNameType = NULL
, @Parm30
WBSourceNameType = NULL
, @Parm31
WBSourceNameType = NULL
, @Parm32
WBSourceNameType = NULL
, @Parm33
WBSourceNameType = NULL
, @Parm34
WBSourceNameType = NULL
, @Parm35
WBSourceNameType = NULL
, @Parm36
WBSourceNameType = NULL
, @Parm37
WBSourceNameType = NULL
, @Parm38
WBSourceNameType = NULL
, @Parm39
WBSourceNameType = NULL
, @Parm40
WBSourceNameType = NULL
, @Parm41
WBSourceNameType = NULL
, @Parm42
WBSourceNameType = NULL
, @Parm43
WBSourceNameType = NULL
, @Parm44
WBSourceNameType = NULL
, @Parm45
WBSourceNameType = NULL
, @Parm46
WBSourceNameType = NULL
, @Parm47
WBSourceNameType = NULL
, @Parm48
WBSourceNameType = NULL
, @Parm49
WBSourceNameType = NULL
, @Parm50
WBSourceNameType = NULL
) AS

where SSSWBCanCoBookSp is the name of your procedure.
3
4

Write the logic to calculate your value and assign it to @Amount. The amount is returned to be displayed
to the user.
To retrieve any parameters that you may have set up, you can call a standard function. It is
dbo.WBGetParm. Pass in the critical number you are dealing with (@KPINum), and the parameter you
want to retrieve. The parameter is looked for first in that specific critical number, and then in the general

Infor Mongoose Core Extensibility Guide | 187

Critical numbers
listing on the Critical Number Parameters form. To retrieve a parameter called "Acct" and set it into a
variable in your stored procedure, you must follow use this example:
An example of past due order lines:
CREATE PROCEDURE SSSWBCanCoitemPastDueSp (
@KPINum
WBKPINumType
, @AsOfDate
DateType
, @Amount
AmountType OUTPUT
, @Parm1
WBSourceNameType = NULL
, @Parm2
WBSourceNameType = NULL
, @Parm3
WBSourceNameType = NULL
, @Parm4
WBSourceNameType = NULL
, @Parm5
WBSourceNameType = NULL
, @Parm6
WBSourceNameType = NULL
, @Parm7
WBSourceNameType = NULL
, @Parm8
WBSourceNameType = NULL
, @Parm9
WBSourceNameType = NULL
, @Parm10
WBSourceNameType = NULL
, @Parm11
WBSourceNameType = NULL
, @Parm12
WBSourceNameType = NULL
, @Parm13
WBSourceNameType = NULL
, @Parm14
WBSourceNameType = NULL
, @Parm15
WBSourceNameType = NULL
, @Parm16
WBSourceNameType = NULL
, @Parm17
WBSourceNameType = NULL
, @Parm18
WBSourceNameType = NULL
, @Parm19
WBSourceNameType = NULL
, @Parm20
WBSourceNameType = NULL
, @Parm21
WBSourceNameType = NULL
, @Parm22
WBSourceNameType = NULL
, @Parm23
WBSourceNameType = NULL
, @Parm24
WBSourceNameType = NULL
, @Parm25
WBSourceNameType = NULL
, @Parm26
WBSourceNameType = NULL
, @Parm27
WBSourceNameType = NULL
, @Parm28
WBSourceNameType = NULL
, @Parm29
WBSourceNameType = NULL
, @Parm30
WBSourceNameType = NULL
, @Parm31
WBSourceNameType = NULL
, @Parm32
WBSourceNameType = NULL
, @Parm33
WBSourceNameType = NULL
, @Parm34
WBSourceNameType = NULL
, @Parm35
WBSourceNameType = NULL
, @Parm36
WBSourceNameType = NULL
, @Parm37
WBSourceNameType = NULL
, @Parm38
WBSourceNameType = NULL
, @Parm39
WBSourceNameType = NULL
, @Parm40
WBSourceNameType = NULL
, @Parm41
WBSourceNameType = NULL
, @Parm42
WBSourceNameType = NULL
, @Parm43
WBSourceNameType = NULL
, @Parm44
WBSourceNameType = NULL
, @Parm45
WBSourceNameType = NULL
, @Parm46
WBSourceNameType = NULL
, @Parm47
WBSourceNameType = NULL
, @Parm48
WBSourceNameType = NULL
, @Parm49
WBSourceNameType = NULL
, @Parm50
WBSourceNameType = NULL
) AS
DECLARE @CoStatList LongListType
, @CoitemStatList
LongListType
, @CredHold
ListYesNoType
, @LateDays
GenericIntType
, @QtyDue
QtyUnitType
, @OrdTotal
AmountType
, @ParmsSite
SiteType
, @CustNum
CustNumType
, @CoNum
CoNumType

Infor Mongoose Core Extensibility Guide | 188

Critical numbers

, @LineFilter
CoLineType
, @ItemFilter
ItemType
, @ProdCodeFilter
ProductCodeType
, @WhseFilter
WhseType
, @StatFilter
CoitemStatusType
SELECT @ParmsSite = site
FROM parms
SET @CoStatList
= ISNULL(dbo.WBGetParm(@CrNum, 'COStatusList'), 'POS')
SET @CoitemStatList = ISNULL(dbo.WBGetParm(@CrNum, 'COITEMStatusList'), 'PO')
SET @CredHold
= ISNULL(dbo.WBGetParm(@CrNum, 'CredHold'), 0)
SET @LateDays
= ISNULL(dbo.WBGetParm(@CrNum, 'LateDaysTolerance'), 0)
SET @CustNum
= dbo.ExpandKyByType('CustNumType', @Parm1)
SET @CoNum
= dbo.ExpandKyByType('CoNumType', @Parm2)
SET @LineFilter = NULLIF(@Parm3, '')
SET @ItemFilter = NULLIF(@Parm4, '')
SET @ProdCodeFilter = NULLIF(@Parm5, '')
SET @WhseFilter = NULLIF(@Parm6, '')
SET @StatFilter = NULLIF(@Parm7, '')
SELECT @Amount = COUNT(*)
FROM coitem
LEFT OUTER JOIN item itm
ON itm.item = coitem.item
WHERE (@CoNum IS NULL OR co_num = @CoNum)
AND charindex(coitem.stat, @CoitemStatList) > 0
AND qty_ordered > qty_shipped
AND ISNULL(due_date, '1900-01-01') &GT= dateadd(dd, @LateDays, @AsOfDate)
AND ship_site = @ParmsSite
AND EXISTS (SELECT 1 FROM co
WHERE (@CustNum IS NULL OR co.cust_num = @CustNum)
AND co.co_num = coitem.co_num
AND charindex(co.stat, @CoStatList) > 0
AND co.credit_hold = @CredHold
)
AND (@LineFilter IS NULL OR coitem.co_line = @LineFilter)
AND (@ItemFilter IS NULL OR coitem.item = @ItemFilter)
AND (@ProdCodeFilter IS NULL OR itm.product_code = @ProdCodeFilter)
AND EXISTS (SELECT 1 FROM co
WHERE (@WhseFilter IS NULL OR co.whse = @WhseFilter)
AND (@StatFilter IS NULL OR co.stat = @StatFilter)
AND co.co_num = coitem.co_num)
RETURN 0

Changing critical number display settings
To change the display settings of a critical number gauge, use the Critical Number Display Settings form.
Note: Keep in mind:
•
•

You can reach this form only by selecting the Display Settings option from the right-click menu of a
critical number gauge display.
Settings made on this form do not become part of the form definition. If you want to persist any changes
beyond the current use/instance of the form, you must go into Design Mode and save them as part of the
form definition.

1
2

Right-click on any critical number gauge, and select Display Settings.
Use these fields to change any settings:

Infor Mongoose Core Extensibility Guide | 189

Critical numbers

Field

Description / Comments

Critical Number

This field displays the ID number for the critical number value displayed in this
gauge.
You can, if you wish, select a different critical number for which to display data.

Date Range

Use this field to specify a date range for which to retrieve data:
•
All Dates
•
YTD (Year-to-date)
•
PTD (Period-to-date)
Note: By default, a period is defined as one month.
•

Group

WTD (Week-to-date)

This field indicates what groups, if any, the critical number is assigned to.
Note: If the critical number is configured to Group By some value, then the specific group can be specified in this field. So, for example, if you have the critical
number set to display results by state, you might have one gauge set to display
sales in Indiana and another gauge set to display sales in Ohio. You would then
use this field to specify which gauge displays the results for which state.

As Of Date

Use this field to specify a starting date for the data to be retrieved and displayed.

Drilldown

Use this field to specify the drilldown to be used with this critical number display.

Gauge Type

Use this field to specify what type of gauge display is to be presented to the user.

Goal Value

Use this field to change the upper or lower limit of the Goal range (depending on
how the critical number is set up).
Note: You cannot use operators in conjunction with the numbers in this field,
only numbers or expressions that resolve to number values. For example, something like >=500 would not be a valid value here. On the other hand, P(GoalValue)
could be a valid value, as long as the value of the GoalValue property is a number.

Alert Value

Use this field to change the upper or lower limit of the Alert range (depending on
how the critical number is set up).
Note: You cannot use operators in conjunction with the numbers in this field,
only numbers or expressions that resolve to number values. For example, something like >=500 would not be a valid value here. On the other hand, P(GoalValue)
could be a valid value, as long as the value of the GoalValue property is a number.

Infor Mongoose Core Extensibility Guide | 190

Critical numbers

3

Field

Description / Comments

Caption Format

Specify the format to use for the gauge caption:
•
Long: The caption for the gauge displays the contents of the Description
field on the Critical Numbers Setup form.
•
Short: The caption for the gauge displays the contents of the Short Desc
field on the Critical Numbers Setup form.
•
Default: The type of caption that the gauge displays depends on the type of
gauge being displayed. This option uses the gauge type to determine whether
to use the Description or the Short Desc value. Some gauge types like
Thermometer and Vertical LED are naturally narrow, so they use the Short
Desc value by default. Other gauges, like the Horizontal Linear Gauge, are
naturally wider, so they use the Description value by default.
•
None: No caption displays.

Sub-Caption Format

Specify which format to use for the gauge sub-caption:
•
All: This option displays the Goal setting, if used; the Alert setting, if used;
and the Actual value of the critical number. Each value is labeled separately.
•
Actual: This option displays only the actual value of the critical number, with
no label.
•
None: This option causes no sub-caption to be displayed at all.

Description

This read-only field displays the description for the critical number (usually like
a title), as specified on the General tab of the Critical Numbers Setup form. This
is also the same value used for a Long caption for the gauge display.

Calculation Definition

This read-only field displays the calculation definition as specified on the General
tab of the Critical Numbers Setup form.

To save your changes and see the resulting display on the critical number gauge display, click OK.

About critical number snapshots
Critical number snapshots are used to provide a history of critical numbers and show whether the numbers
are improving. You can set snapshots to run in the background queue or run them any time using the Snapshot
Generation utility.
To export the list of snapshots to create charts and graphs, use the Critical Numbers Setup form.
This information is displayed on the Critical Number Snapshots form:
•
•
•
•
•

The critical number category, ID, and description
The date that the "snapshot" was taken
The actual (calculated) value of the critical number
Whether the Alert option is selected and if so, a description of the alert range
Whether the Goal option is selected and if so, a description of the goal value

Infor Mongoose Core Extensibility Guide | 191

Critical numbers
•

Whether a symbol is used to show how the actual value of the critical number compares to the goal and
alert values set up for that number

Generating a critical number snapshot
1
2
3

Open the Snapshot Generation form.
To determine when the calculation of the critical number is to begin, select an As Of Date value.
Optionally, to add the snapshot to an existing list of critical numbers, select Append to Current Day.
If you do not select this check box, the new snapshot replaces any existing snapshots.

4

ClickProcess.

The application generates a "snapshot" value for the critical number. You can view it on the Critical Number
Snapshots form.

Infor Mongoose Core Extensibility Guide | 192

Critical number drilldowns

Chapter 9: Critical number drilldowns

About critical number drilldowns
Because a critical number shows only one result value, it is often necessary to access the detailed records
that contain the results. You can configure critical number drilldowns to return a set of data. For example,
you can set up a drilldown for the inventory value critical number to view the items in the inventory, along
with the item warehouse, location, and value.
Like critical numbers, drilldowns can be configured to use IDOs or stored procedures.
Typically, the IDO and filter used on the Critical Numbers Setup form are the same primary IDO and filter
used when defining the drilldown. However, additional tables and IDOs might need to be referenced in the
drilldown to provide user-readable output.
A critical number drilldown is displayed in a DataView. As with other DataViews, you can manipulate the data
and save layouts, whether you activate the drilldown from a critical number control or from the Critical
Numbers form.

Setting up a critical number drilldown
The procedure to set up a critical number drilldown depends on the source for the critical number:
•
•
•

For IDO-based drilldowns, see Setting Up an IDO Critical Number Drilldown on page 193.
For IDO method-based drilldowns, see Setting Up an IDO Method Critical Number Drilldown on page 194.
For stored procedure-based drilldowns, see Setting Up a Stored Procedure Critical Number Drilldown
on page 196.

Setting up an IDO critical number drilldown
You can set up a drilldown for an IDO-based critical number either by creating one new or by copying an
existing one and then modifying it.
1
2

Open the Drilldowns Setup form and execute Filter-In-Place.
Initiate a new record.
To create a drilldown based on an existing one, you can copy and modify the existing drilldown.

Infor Mongoose Core Extensibility Guide | 193

Critical number drilldowns
3

In the Drilldown field, specify an unused integer to identify the drilldown.
If you do not specify an integer here, the system automatically assigns the next available integer.

4

Optionally, in the Description field, provide a descriptive name by which the drilldown and its use can
be easily identified.
On the General tab, make the basic settings for the drilldown.
For more information, see Making General Tab Settings for a Drilldown on page 558.

5
6

Optionally, set up the IDO source for the drilldown.
For more information, see Setting Up the IDO Source for a Drilldown on page 564.

7

Optionally, use the Output Columns tab to rearrange the order in which the output is to be displayed
and the captions to be displayed for those columns. The data that is displayed on this tab is derived from
the IDO source set up in the previous step.
For more information, see Setting Up IDO-Based Critical Number Drilldowns - Output Columns Tab on
page 560.

8

Optionally, use the Categories tab to specify one or more categories to which the drilldown belongs.
When you select a category, the Category Description field is populated automatically.
For more information, see Critical Number Categories on page 299.

9

Optionally, use the Sub Drilldowns tab to specify one or more sub-drilldowns to use in conjunction with
the selected drilldown.
For more information, see Setting Up an IDO-Based Sub Drilldown on page 201.

10 Optionally, use the Static Parameters tab to specify one or more name-value pairs to be used as static
parameters.
Note: Static parameter options are most effective when the source of data for the critical number is a
stored procedure. When the data source is an IDO, the same end is accomplished much more easily using
filters.
11 Optionally, use the Input Parameters tab to rearrange the order in which input parameters are queried
for data and provide descriptions of each.
The data that is displayed on this tab is derived from the IDO source set up in Step 6.
12 Associate the drilldown with a critical number:
a Open the Critical Numbers Setup form and navigate to the critical number.
b On the Drilldowns tab, select the new drilldown and specify a description.
c Save your changes.

Setting up an IDO method critical number drilldown
You can set up a drilldown for an IDO method-based critical number either by creating a new one or by copying
an existing one and then modifying it.
1
2

Open the Drilldowns Setup form and execute Filter-In-Place.
Initiate a new record.
To create a drilldown based on an existing one, you can copy and modify the existing drilldown.

Infor Mongoose Core Extensibility Guide | 194

Critical number drilldowns
3

Provide this information:
Drilldown
Specify an unused integer to identify the drilldown.
If you do not specify an integer, the system automatically assigns the next available integer.
Description
Provide a descriptive name by which the drilldown and its use can be easily identified.

4

On the General tab, set up the basic specifications for the drilldown.
See Setting Up Critical Number Drilldowns - General Tab on page 558.

5

Optionally, set up the IDO source for the drilldown.
See Setting Up the IDO Source for a Drilldown on page 564.

6

Optionally, on the Output Columns tab, rearrange the output columns order and specify captions of
each.
The data that is displayed on this tab is derived from the IDO source set up.
See Setting Up IDO-based Critical Number Drilldowns - Output Columns Tab on page 560.

7

Optionally, on the Categories tab, specify one or more categories where the drilldown belongs. When
you select a category, the Category Description field is populated automatically.
See Critical Number Categories on page 299.

8

Optionally, on the Sub Drilldowns tab, specify one or more sub drilldowns to use with the selected
drilldown.
See Setting Up an IDO-based Sub Drilldown on page 201.

9

Optionally, on the Static Parameters tab, specify one or more name-value pairs
Note: Static parameter options are most effective when the source of data for the critical number is a
stored procedure. When the data source is an IDO, the same end is accomplished much more easily using
filters.

10 Optionally, on the Input Parameters tab, rearrange the input parameters order and provide descriptions
of each.
The data that is displayed on this tab is derived from the IDO source set up.
11 Associate the drilldown with a critical number:
a Open the Critical Numbers Setup.
b Select the critical number.
c On the Drilldowns tab, select the new drilldown and specify a description.
d Save the record.
An example of a custom load method for the drilldown:
[IDOMethod( MethodFlags.CustomLoad, "" )]
public DataTable UserNamesDetail( DateTime AsOfDate, int DrillNum, int KpiNum, string Id,
string Parm1, string Parm2, string Parm3, string Parm4, string Parm5, string Parm6,
string Parm7, string Parm8, string Parm9,
string Parm10, string Parm11, string Parm12, string Parm13, string Parm14, string Parm15,
string Parm16, string Parm17, string Parm18, string Parm19,
string Parm20, string Parm21, string Parm22, string Parm23, string Parm24, string Parm25,

Infor Mongoose Core Extensibility Guide | 195

Critical number drilldowns

string Parm26, string Parm27, string Parm28, string Parm29,
string Parm30, string Parm31, string Parm32, string Parm33, string Parm34, string Parm35,
string Parm36, string Parm37, string Parm38, string Parm39,
string Parm40, string Parm41, string Parm42, string Parm43, string Parm44, string Parm45,
string Parm46, string Parm47, string Parm48, string Parm49,
string Parm50 )
{
var dtResults = new DataTable();
using ( ApplicationDB appDB = IDORuntime.Context.CreateApplicationDB() )
{
using ( var cmd = appDB.CreateCommand() )
{
cmd.CommandType = CommandType.Text;
cmd.Connection = appDB.Connection;
cmd.CommandText = string.Concat( " SELECT un.Username, un.UserDesc, un.EditLevel,
un.SuperUserFlag, un.Status ",
" FROM usernames un "
);
cmd.CommandText = IDORuntime.AppDataProvider.MassageQueryForProvider( cmd.Command
Text );

}
}

}

using ( DbDataAdapter adapter = IDORuntime.AppDataProvider.CreateAdapter() )
{
adapter.SelectCommand = (DbCommand)cmd;
adapter.Fill( dtResults );
}

return dtResults;

Setting up a stored procedure critical number drilldown
You can set up a drilldown for a critical number based on a stored procedure either by creating one new or
by copying an existing one and then modifying it.
1
2

Open the Drilldowns Setup form and execute Filter-In-Place.
Initiate a new record.
To create a drilldown based on an existing one, you can copy and modify the existing drilldown.

3

In the Drilldown field, specify an unused integer to identify the drilldown.
If you do not specify an integer here, the system automatically assigns the next available integer.

4

In the Description field, provide a descriptive name by which the drilldown and its use can be easily
identified.
On the General tab, make the basic specifications for the drilldown.
For more information, see Making General Tab Settings for a Drilldown on page 558.

5
6

Optionally, use the Output Columns tab to rearrange the order in which the output is to be displayed
and the captions to be displayed for those columns.
The data that is displayed on this tab is derived from the stored procedure identified in Step 4.
For more information, see Setting Up Stored Procedure-based Drilldowns - Output Columns Tab on page
560.

Infor Mongoose Core Extensibility Guide | 196

Critical number drilldowns
7

Optionally, use the Categories tab to specify one or more categories to which the drilldown belongs.
When you select a category, the Category Description field is populated automatically.
For more information, see Critical Number Categories on page 299.

8

Optionally, use the Sub Drilldowns tab to specify one or more sub-drilldowns to use in conjunction with
the selected drilldown.
For more information, see Setting up a Sub-Drilldown Based on a Stored Procedure on page 202.

9

Optionally, use the Static Parameters tab to specify one or more name-value pairs
Note: If the data needs to be refined by a hard-coded list of values, we recommend that you assign the
name and value on this tab. You can then reference the static parameters when you set up the critical
number/drilldown filter using the CRPARM() syntax.
For example, a critical number based on an Account Balance stored procedure might have a static
parameter called Acct. This tells the stored procedure which account number to use. The number that
ships with the product uses 10000 (cash), but users can change this value if they want to monitor a
different account or if their cash account uses a different account number.

10 Optionally, use the Input Parameters tab to provide values that can be used in processing sub-drilldowns,
especially.
11 Associate the drilldown with a critical number:
a Launch the Critical Numbers Setup form and navigate to the critical number.
b On the Drilldowns tab, select the new drilldown and specify a description.
c Save the record.
12 Create the stored procedure in your preferred code editor.
The parameters for a drilldown are slightly different from a critical number, as shown in this example:
CREATE PROCEDURE WBCanCoitemPastDueDetailsSp (
@AsOfDate
DateType
, @DrillNum
WBDrillNumType
, @KPINum
WBKPINumType
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

Infor Mongoose Core Extensibility Guide | 197

Critical number drilldowns
, @Parm27
, @Parm28
, @Parm29
, @Parm30
, @Parm31
, @Parm32
, @Parm33
, @Parm34
, @Parm35
, @Parm36
, @Parm37
, @Parm38
, @Parm39
, @Parm40
, @Parm41
, @Parm42
, @Parm43
, @Parm44
, @Parm45
, @Parm46
, @Parm47
, @Parm48
, @Parm49
, @Parm50
) AS

WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType
WBSourceNameType

Records are returned to the user through the WBTmpDrilldowns temporary table. The columns that you
set in this temporary table correspond to the columns you set up on the Output Columns tab on the
Drilldowns Setup form. If you specified a Detail form on the Drilldowns Setup form and you want to
see details on the specific record, set the RowPointer, as shown in this example:
INSERT INTO WBTmpDrilldowns(
RefRowPointer
, DATE01
, CHAR01
, INTE01
, CHAR02
, CHAR03
, SessionID
)
SELECT
coitem.RowPointer
, coitem.due_date
, co.co_num
, coitem.co_line
, co.cust_num
, custaddr.name
, @SessionID
FROM coitem
INNER JOIN co
ON co.co_num = coitem.co_num
LEFT OUTER JOIN custaddr
ON custaddr.cust_num = co.cust_num
AND custaddr.cust_seq = co.cust_seq
LEFT OUTER JOIN item itm
ON itm.item = coitem.item
WHERE co.cust_num = ISNULL(NULLIF(@CustNum,''), co.cust_num)
AND co.co_num = ISNULL(NULLIF(@CoNum,''), co.co_num)
AND charindex(coitem.stat, @CoitemStatList) > 0
AND qty_ordered > qty_shipped
AND ISNULL(due_date, '1900-01-01') = dateadd(dd,="" @latedays,="" @asofdate)="" and=""
ship_site="@ParmsSite" charindex(co.stat,="" @costatlist)=""> 0
AND co.credit_hold = @CredHold
AND (@LineFilter IS NULL OR coitem.co_line = @LineFilter)
AND (@ItemFilter IS NULL OR coitem.item = @ItemFilter)
AND (@ProdCodeFilter IS NULL OR itm.product_code = @ProdCodeFilter)
AND (@WhseFilter IS NULL OR co.whse = @WhseFilter)
AND (@StatFilter IS NULL OR co.stat = @StatFilter)

Infor Mongoose Core Extensibility Guide | 198

Critical number drilldowns
13 You can set these additional values in the WBTmpDrilldowns table to affect what is displayed in the
drilldown:
•
RowPointer provides a link to the specific record when launching a detail form.
•
GoalValue overrides the Goal Value from the Drilldowns Setup form.
•
AlertValue overrides the Alert Value from the Drilldowns Setup form.
This example shows Customer Order Past Due:
CREATE PROCEDURE WBCanCoitemPastDueDetailsSp (
@AsOfDate
DateType
, @DrillNum
WBDrillNumType
, @KPINum
WBKPINumType
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
DECLARE @Severity INT
, @CoNum
CoNumType

Infor Mongoose Core Extensibility Guide | 199

Critical number drilldowns

, @CustNum CustNumType
, @ParmsSite SiteType
, @CoStatList LongListType
, @CoitemStatList
LongListType
, @CredHold
ListYesNoType
, @LateDays INT
, @LineFilter
CoLineType
, @ItemFilter
ItemType
, @ProdCodeFilter
ProductCodeType
, @WhseFilter
WhseType
, @StatFilter
CoitemStatusType
, @SessionID
RowPointerType
SET @Severity = 0
SET @LateDays = 0
SET @SessionId = dbo.SessionIdSp()
SET @CustNum = dbo.ExpandKyByType('CustNumType', @Parm1) SET @CoNum = dbo.ExpandKyByType('CoNum
Type', @Parm2) SET @LineFilter = NULLIF(@Parm3, '') SET @ItemFilter = NULLIF(@Parm4, '') SET
@ProdCodeFilter = NULLIF(@Parm5, '') SET @WhseFilter = NULLIF(@Parm6, '') SET @StatFilter = NUL
LIF(@Parm7, '')
IF @CoNum IS NULL AND @LineFilter IS NOT NULL
SET @LineFilter = NULL
SELECT @ParmsSite = site
FROM parms
SET @CoStatList
= ISNULL(dbo.WBGetDrillParm(@DrillNum, @KPINum, 'COStatusList'), 'POS')
SET @CoitemStatList = ISNULL(dbo.WBGetDrillParm(@DrillNum, @KPINum, 'COITEMStatusList'), 'PO')
SET @CredHold
= ISNULL(dbo.WBGetDrillParm(@DrillNum, @KPINum, 'CredHold'), 0)
SET @LateDays
= ISNULL(dbo.WBGetDrillParm(@DrillNum, @KPINum, 'LateDaysTolerance'), 0)
INSERT INTO WBTmpDrilldowns(
RefRowPointer
, DATE01
, CHAR01
, INTE01
, CHAR02
, CHAR03
, SessionID
)
SELECT
coitem.RowPointer
, coitem.due_date
, co.co_num
, coitem.co_line
, co.cust_num
, custaddr.name
, @SessionID
FROM coitem
INNER JOIN co
ON co.co_num = coitem.co_num
LEFT OUTER JOIN custaddr
ON custaddr.cust_num = co.cust_num
AND custaddr.cust_seq = co.cust_seq
LEFT OUTER JOIN item itm
ON itm.item = coitem.item
WHERE co.cust_num = ISNULL(NULLIF(@CustNum,''), co.cust_num)
AND co.co_num = ISNULL(NULLIF(@CoNum,''), co.co_num)
AND charindex(coitem.stat, @CoitemStatList) > 0
AND qty_ordered > qty_shipped
AND ISNULL(due_date, '1900-01-01') = dateadd(dd,="" @latedays,="" @asofdate)="" and=""
ship_site="@ParmsSite" charindex(co.stat,="" @costatlist)="" 0
AND co.credit_hold = @CredHold
AND (@LineFilter IS NULL OR coitem.co_line = @LineFilter)
AND (@ItemFilter IS NULL OR coitem.item = @ItemFilter)
AND (@ProdCodeFilter IS NULL OR itm.product_code = @ProdCodeFilter)
AND (@WhseFilter IS NULL OR co.whse = @WhseFilter)
AND (@StatFilter IS NULL OR co.stat = @StatFilter)
RETURN @Severity

Infor Mongoose Core Extensibility Guide | 200

Critical number drilldowns

Setting up a critical number/drilldown IDO filter
1

To open the Critical Number / Drilldown IDO Filter Setup form, click Filter on the Critical Number IDO
Source Setup form or the Drilldowns IDO Setup form.
Use filters to filter the query performed against the database to narrow the selected records.
Note: You cannot open this form directly.

2
3
4

5
6
7
8
9

In the Property Name field, select the IDO property for which you want to set up the filter. This field is
not available if the comparison type is CN Parameter Has Value.
Specify the operator to use for the filter. If you select IS NULL or IS NOT NULL, the comparison field and
value need not be specified. This field is not available if the comparison type is CN Parameter Has Value.
Select the comparison type:
•
Literal: The property value is compared to the literal value you specify in the last field on this row.
•
Property Name: The property value is compared to another property in the same IDO. The other
property is specified in the last field on this row.
•
Critical Number Parameter: The property value is compared to the value of a static parameter
defined on the Critical Numbers form. The name of that parameter is then specified in the last field
on this row.
•
CN Parameter Has Value: The property value is compared to a critical number parameter value.
That value is specified in the last field on this row. If this option is selected, then the Property Name
and operator fields are not available.
In the last field on this row, specify the comparison value to be used for the corresponding comparison
type.
Optionally, if you are using multiple filter clauses, to instruct the system to treat the clauses as Boolean
OR comparisons rather than Boolean AND comparisons, select OR Instead of AND with Previous Clause.
To add the filter clause to the list of clauses in the display panel, click Add. The clause is added to the
list, formatted with the proper syntax.
Optionally, use Steps 2 through 7 to specify additional filter clauses.
Optionally, to remove all filter clauses and start over, click Remove.
Note: You cannot selectively remove individual filter clauses: If you choose to remove one, you remove
them all.

10 Click OK.
For complex comparison logic, you can edit the text in the editor field to add parentheses around OR and
AND statements, to ensure that the filter is evaluated properly.

Setting up an IDO-based sub-drilldown
Second-level drilldowns can be very useful in cases where a critical number is calculated from subtotaled
data, and the details of the subtotal need to be accessible. You can use Output Column property names of
the parent drilldown as input parameters for the sub-drilldown.

Infor Mongoose Core Extensibility Guide | 201

Critical number drilldowns
1
2
3
4

Create the drilldown.
On the Drilldowns Setup form, navigate to the top-level drilldown.
On the Sub Drilldowns tab, specify the new drilldown and a description for it.
Save the record.

Setting up a sub-drilldown based on a stored procedure
Second-level drilldowns can be very useful in cases where a critical number is calculated from subtotaled
data, and the details of the subtotal need to be accessible. You can use Output Column property names of
the parent drilldown as input parameters for the sub-drilldown.
The preconfigured Inventory Value critical number (in some applications) is a good example of second-level
drilldowns for stored procedure-based critical numbers. This critical number shows your whole inventory
value, and drills down to a subtotal by inventory. It drills down one step further to item totals or location
totals by warehouse.
1

Create the drilldown.
See Setting Up a Stored Procedure-based Critical Number Drilldown on page 196.

2

Optionally, use the @Parms parameters to accept filters into your drilldown.
For example, the Inventory Value Detail Drilldown (SSSWBCanInvValDtlSp) accepts Whse in @Parm1,
Item in @Parm2, and Location in @Parm3. It is coded to use these values as filters if provided, or ignore
them if they are not provided.

3

Specify Source Parms in sequence for how you want to accept your parameters in your stored procedure.
When one drilldown calls another, the sub-drilldown automatically pulls these values by column header
name from the calling drilldown. For example, the Item Inventory Value Detail drilldown has a column
heading named "Item". When it calls the Inventory Value Detail sub-drilldown, the Item value is passed
to the Inventory Value Detail program in @Parm2.

4
5
6
7

Launch the Drilldowns Setup form.
Navigate to the top level drilldown.
On the Sub Drilldowns tab, specify the new drilldown and a description for it.
Save the record.

Example:
First Level Drilldown Program:
SSSWBCanInvValItemDtlSp
CREATE PROCEDURE SSSWBCanInvValItemDtlSp (
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

Infor Mongoose Core Extensibility Guide | 202

Critical number drilldowns

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
, @TmpAmount AmountType
, @StartItem ItemType
, @EndItem
ItemType
DECLARE @ttItemloc TABLE (
RowPointer uniqueidentifier
, item
nvarchar(30)
, amount
decimal(20,8)
, processed tinyint
)
SET @StartItem = ISNULL(@Parm1, dbo.LowString('ItemType'))
SET @EndItem
= ISNULL(@Parm1, dbo.HighString('ItemType'))
INSERT INTO @ttItemloc
SELECT RowPointer, item, 0, 0
FROM itemloc
WHERE item BETWEEN @StartItem AND @EndItem
WHILE EXISTS (SELECT TOP 1 1 FROM @ttItemloc WHERE processed = 0)
BEGIN
SELECT TOP 1 @RowPointer = RowPointer
FROM @ttItemloc
WHERE processed = 0
SET @TmpAmount = 0
EXEC SSSWBCanInvValSubItemlocSp @RowPointer, @TmpAmount OUTPUT

Infor Mongoose Core Extensibility Guide | 203


