---
name: "syteline-basics-forms"
description: "Forms, fields, records, and collections - MultiView forms, query forms, grids, Detail Only, Flex forms, Filter-in-Place, wildcards, caps, sorting, exporting, workspaces, customizing forms"
version: "1.0"
tags:
  - syteline
  - basics
  - forms
  - collections
  - filters
  - mongoose
---

# syteline-basics-forms

Source: Infor Mongoose Basics User Guide, Release 2026.x

Alternatively, select Save as PDF in the Print dialog box to save a PDF copy of the Help topic.

Printing help guides to PDF
1
2

Open a topic and click the Print icon.
Select Print PDF.
Note: The Print PDF option creates a PDF of the entire help guide in which the topic is located.

3

Optionally, click the Download icon to download the file or click the Print icon to print the file.

Infor Mongoose Basics User Guide | 19

About Forms, Fields, Records, and Collections

Chapter 4: About Forms, Fields, Records, and Collections

The Web client is a database-oriented user interface. To get or submit information, you generally access one
or more tables in databases. The application uses fields and other components on forms to present collections
of data records.
To use this application effectively, you must understand these basic concepts:
•
•
•
•

Forms
Fields
Records
Collections

Working with Forms
A form is a window through which you interact with information in a database. Looking up information,
entering new information, and generating reports are typical interactions. Each form is devoted to a business
task or to several related tasks. Nearly everything you do in this application involves the use of one or more
forms.
Types of Forms
The system uses these basic types of forms:
•
•
•
•
•
•
•
•

MultiView forms
Query forms
Grid Only forms
Detail Only forms
Report forms (both system report forms and Report type forms)
Tile forms
Build from Scratch forms
Utilities and activities

Finding Forms
Use these methods to locate and open the forms you need:
•

Explorer

Infor Mongoose Basics User Guide | 20

About Forms, Fields, Records, and Collections
See Finding and Opening Forms Using the Explorer on page 30.
•

Select Form dialog box
See Finding and Opening Forms Using the Select Form Dialog Box on page 31.

Navigating Through Forms
You have several options for navigating through forms.
See Changing Focus in Collections on page 48.
Workspaces and Forms
You might find that there are a limited number of forms that you use regularly. In many cases, these forms
are interrelated. You can set up and use personal workspaces that automatically open and arrange these
forms for you.
See Using Workspaces on page 31.

Form Types
MultiView forms
A MultiView form is bound to an IDO collection, the records of which are displayed in a two-pane window
separated by a splitter bar. The splitter bar can be either vertical or horizontal, but it is most commonly
vertical.

Infor Mongoose Basics User Guide | 21

About Forms, Fields, Records, and Collections
One pane - commonly the left pane - displays the collection's records in a grid. The other pane displays details
for a single IDO record in the collection (the item currently selected in the grid).

MultiView forms must contain a primary collection and can also contain one or more subcollections or
secondary collections. Typically, the Grid View displays only the primary collection. When subcollections or
secondary collections are included, the Detail View usually displays the primary collection in the upper part,
and the lower portion displays the subcollection or secondary collection, often in a grid.

Working in a MultiView form
MultiView forms typically support filters, by allowing you either to activate Filter-in-Place or to launch an
associated query form. Query forms can return their results to the form from which they are called.
When working in a MultiView form, you can:
•
•
•
•
•

Navigate the collection by selecting items in the Grid View.
Edit data in either view.
Move the splitter bar to see more or less of either view.
Completely hide either view.
Save the state of the views and the relative position of the splitter bar when the form is closed.

The number of records that can be displayed at one time is controlled both by system-level and user-level
settings.
Creating MultiView forms
There are a number of different ways you can create MultiView forms. The created MultiView form functions
the same regardless of the method used to create it.
These are the options for creating a MultiView form:
•

In a web client operating environment, you can use the Web Designer to create the form.

Infor Mongoose Basics User Guide | 22

About Forms, Fields, Records, and Collections
•

If the web client Form Wizard - MultiView form has been designated as a Runtime Builder form and you
have authorization to create Runtime Builder forms, you can use that option. In this case, the form wizard
operates the same as the Web Designer option.

Query Forms
Most multiview forms have query forms associated with them. These query forms usually have the same name
as the associated multiview form, appended with Query. You can use these query forms to help locate particular
records that you are concerned with. You can then return the results of your query to the associated multiview
form automatically.
Query forms typically consist of these components:
•

•
•

The criteria selection box sets the criteria used to limit your search. This box contains two tabs:
•
The Primary Criteria tab is used for the most common search criteria. There are typically from two
to four fields on this tab.
•
The Additional Criteria tab is used mostly to refine and narrow the search.
Action buttons allow you to perform various actions in your search.
The results grid displays the results of your query in tabular format. You can use the scroll bars to locate
and select the record you are looking for.

Primary Criteria in Query Forms
On the Primary Criteria tab of a query form, you can specify query criteria for the principal fields on the
associated multiview form. Usually these fields represent only the two or three most commonly searched
criteria for the form. For example, in a form used to maintain information about customers, primary fields
might include customer identification number and customer name.
The same fields are also listed on the Additional Criteria tab. This means that query criteria for the principal
fields can be specified on either tab.
Primary criteria consist of an operator for the selected field on which you want to filter data and a value for
the field. By default, the comparison operator for all primary criteria is like, and the values are empty. With
these settings, all records are considered a match. The result set is the same as the result set from executing
Filter-in-Place when no values are entered in fields on the form.
Primary criteria are automatically joined by a Boolean AND. That is, records are retrieved from the database
only if they meet all the specified criteria.
Example:
Suppose that you own a company that deals primarily with bicycle retailers. You want to retrieve all records
that contain bi in the Name field, so that you will get a collection displaying all records that contain either
"bike" or "bicycle." Specify this expression in the Primary Criteria tab:
Name LIKE *bi*

Where Name is a field listed on the Primary Criteria tab, LIKE is the operator you selected, and *bi* is the
value you specified.

Infor Mongoose Basics User Guide | 23

About Forms, Fields, Records, and Collections

The value *bi* restricts the results to those records containing "bi" in the Name field. The wildcard character
(*) matches all other possible combinations of characters that come either before or after the search value
"bi". The query might return records containing "Bicycle Center" and "Terry's Bike Shop" in the Name field.
Remember that the value you specifiy here is not case-sensitive.

Additional Criteria in Query Forms
On the Additional Criteria tab of a query form, you can specify search criteria for any of the fields on the
associated multiview form.
Note: This tab includes the fields that are listed on the Primary Criteria tab. This means that you are not
obligated to specify primary criteria before doing a query. See Primary Criteria in Query Forms on page 23.
One reason for using the Additional Criteria tab for the principal fields, rather than using the Primary Criteria
tab is if you want to do a Boolean OR search, rather than a Boolean AND search on those fields. The Primary
Criteria tab only allows AND searches.
Query Clauses
Query criteria are made up of query clauses. A query clause consists of a Boolean search delimiter, a field
name, an operator, and a value. For example, to retrieve records for vendors located in New York, you would
create a clause similar to the following:
AND State = 'NY'

where
•
•
•

State is a field name on the multiview form in which vendors are defined in your system.
= is an operator specifying that returned results must match the value exactly.
NY is the value that you want the system to search for in the State field.

Note: The letter N might be shown between the operator and the value in the clause. This N indicates that
the system is using Unicode (double-byte) character codes.
Multiple clauses, if you use them, work sequentially. That is, the system searches using the first defined clause,
then applies the second clause to the returns, and then the third clause, and so forth. This means that the
order in which you define your clauses must be planned carefully, as order can make a difference.
AND and OR (Boolean) Searches
You can create multiple clauses and join them by Boolean AND or OR filters. Clauses joined by AND retrieve
only those records that meet the criteria specified in all clauses. Clauses joined by OR retrieve records that
meet any one of the criteria.
The system adds the Boolean filter automatically when you define the query clause. If you select the check
box labeled OR instead of AND with previous clause, the clause is defined as an OR clause. If that check box
is cleared, the system defines the clause as an AND clause.

Infor Mongoose Basics User Guide | 24

About Forms, Fields, Records, and Collections

Operators on Query Forms
Query forms support the comparison operators that are listed in this table:
Operator

Description

Like

Returns records containing a field value that matches the value you type using one
or more wildcard characters. See Using Wildcards on page 38.
If you do not use a wildcard character, Like acts the same as = (equals).
This is the default operator for the Primary Criteria tab fields.

= (Equals)

Returns records containing a field value that matches exactly the value you type.
You cannot use wildcard characters with this operator.

> (Greater than)

Returns records containing a field value greater than the value you enter. With letters,
> refers to alphabetical order from A to Z. This entry is not case-sensitive.
You can use wildcard characters with this operator.

< (Less than)

Returns records containing a field value less than the value you enter. With letters,
< refers to alphabetical order from A to Z. This entry is not case-sensitive.
You can use wildcard characters with this operator.

<> or != (Not equal)

Returns records containing field values that do not match exactly the value you
specify.
You cannot use wildcard characters with this operator.

Not like

Returns records containing a field value that does not match the value you type using
one or more % wildcard characters.
If you do not use a wildcard character, Not like acts the same as <> or != (Not equal).
Not like is not included in the list of operators, but you can type Not like in the

operator field.

Buttons on Query Forms
Query forms have these action buttons:

Infor Mongoose Basics User Guide | 25

About Forms, Fields, Records, and Collections

Button

Description

OK

Click to return any search results that match the current criteria to the parent multiview
form and close the query form.
If you opened the query form to perform a form-level query, the result set is a collection
of records, which can be returned to the parent form. If you opened the query form to
find a value for a single field in a form, the result is a field value in the currently selected
record in the results grid. This value can be returned to the corresponding field in the
parent form.
Be aware of this information:
•

•

•

Cancel

When you click this button, if you have selected any results to return to the parent
form, the system prompts you for confirmation. If you want to return the results
to the parent form, click Yes. If you do this, the previous collection on the multiview
form is overwritten with the collection being returned from the query form.
In a form-level query, records returned to the parent form match the current criteria. These records may be different from records displayed in the Results pane.
The difference can occur when you specify criteria, click Refresh to display the
search results, and then change the criteria without clicking Refresh to display
the new results.
This is typically the last button you click on the query form, because it returns you
to the parent multiview form. To get the results of your query, you must first click
Refresh.

Closes the query form without returning any search results to the parent multiview
form.
Whether you opened the query form from a form or a field, no information is returned
to the parent form or field.

Clear

Clears any search criteria from the query form without closing it.
This can be useful if you make a mistake and want to start over without having to close
the query form.

Refresh

Searches the database using the current criteria and displays the selected records in
the results grid.

Add (Additional
Criteria tab only)

Adds the criteria currently in the specification fields to the list of query clauses.

Remove (Additional Criteria tab
only)

Removes the selected query clause from the list of clauses.

Criteria in the specification fields do not become part of the filter until you click this
button.

Grid Only Forms and Grids
The most common form type or part of a form used to display records and collections of records is a grid,
which displays a collection of records using a tabular (row-column) format. A grid can constitute the sole

Infor Mongoose Basics User Guide | 26

About Forms, Fields, Records, and Collections
content of a form, in which case it is called a Grid Only form. Grids can also be used in other forms, most
commonly MultiView forms.
Grid Only Forms
A Grid Only form is a form in which all data is displayed and modified using a grid, similar to a spreadsheet
or table display.
A Grid Only form displays IDO collection data using a single grid. Each row in a grid represents one record in
the collection.
Grid Only forms are typically used to define and display related groups of values, settings, parameters, or
codes. The collections they display are usually small with a limited amount of data.
Some fields (columns) contain display-only data, usually indicated by shaded cells. Other columns can be
used to enter and modify related data. Usually, you can enter the values you want directly in editable fields,
but some fields require you to select values from a drop-down list.
Grids in Other Forms
Forms that are not purely grid forms can also make use of grids to display and manipulate data.
For grids in multiview forms:
•
•

A multiview form uses a grid in one view of the form, usually the view on the left of the splitter bar.
The grid displayed in a mutiview form works similar to a grid form.

For grids in query forms:
•
•

Query forms make use of grids to display the records returned from a query using that form. These grids
display in the bottom portion of the query form and are labeled Results.
For more information about Results grids in query forms, see About Query Forms on page 23.

For grids in report, activity and utility forms:
•
•

Some report, activity, and utility forms retrieve records and allow you to preview and select what records
will be processed, using a grid.
For specific information about forms that use grids, see the online help for those forms.

Note: Many of these forms include a Select option (check box) for each record that is returned for preview.
When you select a record using this option, the system displays a modified indicator in the row label for that
record. This indicates that the record has been selected but not yet processed.
On such forms, when you select Commit and click Process, the system processes all (and only) selected
records, that is, all records with the Select option selected and the modified indicator in the row label. If all
selected records process successfully, then typically the system refreshes the form. Normally, when the form
is refreshed, all selected records, having already been processed, no longer display.
In a case where the system is set to process records individually, there might occur an error midway through
processing of records. In this case, typically, the records that were processed before the error occurred are
already committed in the database. The collection is not refreshed, so they still appear in the grid; yet the
modified indicator is now gone. If you then attempt again to process the records, these records are not
processed again.

Infor Mongoose Basics User Guide | 27

About Forms, Fields, Records, and Collections

In a case where the system is set to process all records as a batch, and there occurs an error, the system does
not complete the processing of any records. In this case, the system returns all records to the status they had
before processing was attempted.

Detail Only forms
A Detail Only form displays the details for a single record of an IDO collection. This form is basically the same
as the Detail View on a MultiView form. See MultiView forms on page 21
The limitation and potential drawback of creating this type of form is exactly that you can only view a single
record from the collection at a time. Because there is no accompanying Grid View, the only way to navigate
through the records in the collection is either with the navigation buttons on the main toolbar or by setting
components on the form itself to provide the navigation options.
Other than the absence of the Grid View, this type of form is similar to the MultiView form and has virtually
all the same capabilities, including the potential to host container components and subcollections.
Creating Detail Only forms
There are a number of different ways you can create Detail Only forms. The created Detail Only form functions
the same regardless of the method used to create it.
These are the options for creating a Detail Only form:
•
•

In a web client operating environment, you can use the Web Designer to create the form.
If the web client Form Wizard - Detail Only form has been designated as a Runtime Builder form and
you have authorization to create Runtime Builder forms, you can use that option. In this case, the form
wizard operates the same as the Web Designer option.

System Report Forms
System report forms are forms created by Infor. These forms are used primarily to generate reports for a wide
range of uses. In some cases, report forms are also used to generate forms or other documents required to
complete some business task.
Many system report forms allow you to generate a preview before actually printing or sending the generated
report file.

Mobile Forms
Mobile forms use a suffix of .mobi and are intended to be used only on mobile devices. They can use special
toolbars and other design features. For more information, see the related topics.

Infor Mongoose Basics User Guide | 28

About Forms, Fields, Records, and Collections

Utilities and Activities Forms
Utilities and activities are specialized forms that are usually associated with other, more basic forms. They
typically process multiple records in one operation, performing tasks such as purging records, updating
values, posting transactions, or changing the status of records.
For instance, suppose you need to change the status of a range of job operations to Complete. You could use
a Complete Job Operations utility to change them all at one pass, rather than having to change each job
operation separately.
Utilities and activities forms typically present pairs of fields and other options that allow you to define ranges
of and criteria for records to be processed.
Previewing and Selecting Records
Many (though not all) utilities and activities forms include a grid section, in which you can preview records
before processing. Often, these forms include a Select check box for each record that is returned for preview.
When you select a record using this option, the system displays an indicator in the row label for that record
that indicates that the record has been selected but not yet processed.
On such forms, when you select Commit and click Process, the system processes all (and only) selected
records, that is, all records with the Select option selected and the modified indicator in the row label. If all
selected records process successfully, then typically the system refreshes the form. Normally, when the form
is refreshed, all selected records, having already been processed, no longer display.
In a case where the system is set to process records individually, there might occur an error midway through
processing of records. In this case, typically, the records that were processed before the error occurred are
already committed in the database. The collection is not refreshed, so they still appear in the grid; yet the
modified indicator is now gone. If you then attempt again to process the records, these records are not
processed again.
In a case where the system is set to process all records as a batch, and there occurs an error, the system does
not complete the processing of any records. In this case, the system returns all records to the status they had
before processing was attempted.

Flex forms
The Flex type form is designed to use FlexLayout components to render all other components, allowing you
to create forms that are dynamically responsive to the viewing environment. When initially created, the Flex
form includes only a single FlexLayout component divided into three vertical regions. Optionally, you can
add FlexLayout and other components and regions needed to create the form.
Creating Flex forms
There are a number of different ways you can create Flex forms. The created Flex form functions the same
regardless of the method used to create it.
These are the options for creating a Flex form:
•

In a web client operating environment, you can use the Web Designer to create the form.

Infor Mongoose Basics User Guide | 29

About Forms, Fields, Records, and Collections
•

If the web client Form Wizard - Flex Basic form has been designated as a Runtime Builder form and you
have authorization to create Runtime Builder forms, you can use that option. In this case, the form wizard
operates the same as the Web Designer option.

Finding and Opening Forms
There are two ways to find and open forms:
•
•

To locate and open a form based on its function, if you do not know the name of the form, you can use
the Explorer.
If you know the name or caption used for the form, you might find it easier and quicker to use the Select
Form dialog box.

You can open more than one form at a time. Each form opens in a separate window that can be resized,
minimized, or maximized. You can also use the Window menu to position opened forms in a cascade or tile
arrangement.
If there are forms that you use regularly, you can arrange these and save them as workspaces, so that it is
easier to find, open, and arrange these forms. You can also put them in your My Folders area, where you can
find them easily and even set them to load or open automatically when you log in.

Finding and opening forms using the Explorer
You can use the Explorer to find and open forms. The Explorer is most useful when you do not know the name
of the form, but you know how the form is used, or who uses the form.
In the Explorer, you can look up forms by user role or application module, or you can open the All Forms
folder and type the first few letters of the form name.
To find and open a form using the Explorer:
1

In the Explorer window, expand the Master Explorer or another folder to display the list of forms that
contains the form you want.
To see the complete list of forms, expand the All Forms folder.

2
3

Locate the form you want to open.
To quickly locate a form name in a long list of names:
a Click any form in the list.
b Type the first few letters of the form you want to find. Explorer selects the first form name that starts
with the letters you typed.
c Use the down arrow or scroll wheel to refine your search.

4

Double-click the name for the form you want.

Infor Mongoose Basics User Guide | 30

About Forms, Fields, Records, and Collections

Finding and opening forms using the Select Form dialog box
If you know the name or caption of a form you need, one of the quickest and easiest ways to find and open
it is by use of the dialog box. But even if you do not know the exact name, this is a powerful option, because
of the filtering capabilities in the Select Form dialog box.
1

Select Form > Open.
A list of all the forms in the application is displayed. By default, forms are listed by caption, which is the
title that is shown when the form is open. You can select whether to display the list according to the form
Name or the Caption.

2

To restrict the list by filtering:
•
In the Filter field, specify the portion of the caption or name that you know.
•
Optionally, to match your capitalization, select the Case Sensitive option.
•
Optionally, use the Filter by field, to return searches from the Caption column, Name column, or
both.
The dialog box automatically updates the list display as you enter characters.

3
4

In the list, select the form you want.
Click OK.

Opening Utilities and Activities
Most forms in the application have shortcut buttons to related utility and activity forms. These buttons on
the tool bar open folders in the Explorer window where utility and activity forms are grouped by function
under the main folders.
To open a utility form that is related to the current form:
1
2

In the current form, click the Go To Utilities tool bar button. The Explorer opens, with the appropriate
utilty folder selected.
In the Explorer folder, double-click to open a utility form.

Using Workspaces
After you have worked with the system for awhile, you will probably find that there is a certain set of forms
that you work with frequently (if not daily), and that you like to use in a particular arrangement in your work
area.
The system provides a way to save and open such a configuration of forms easily, so you do not have to redo
it every time you use the system. In the system, this configuration of forms is called a workspace.
Once you have set up and saved a workspace, you can either open it manually through the Form menu, open
it through a shortcut in the Explorer, or set the system to open it automatically when you log in. You can also
later modify the arrangement of the forms, and even add or delete forms from the workspace. And, should
you decide you no longer need it, you can delete a workspace.

Infor Mongoose Basics User Guide | 31

About Forms, Fields, Records, and Collections

Setting Up and Saving Workspaces
A workspace is a configuration of forms that open together. If you use a group of forms often, you can set up
a workspace that lets you open all the forms with one command, with the size and position of the forms
preset.
Note: Arranging the order of the forms in a workspace only applies when using a Windowed layout. The order
of the forms and how they are opened is not honored in a Tabbed Layout.
To set up and save a workspace:
1

Open each form that you want to include in the group and then adjust the size and position of each form
to the configuration you want. Here are some tips:
•
To remove the Explorer window from the group, minimize it.
•
To move a form, drag the title bar.
•
To change the form's size, drag the borders.
•
To arrange the forms as tiled or cascaded windows, use the Window menu.

2
3
4
5
6
7

Select Form > Workspaces.
In the Workspaces dialog box, click New.
In the Name field, specify the name of the workspace.
Click OK.
Click Set from Current Forms.
Click Done.

Opening Workspaces
After a workspace is saved, you can open it by any of these methods:
•
•
•

Open the workspace manually through the Form menu.
Open a workspace shortcut in the Explorer.
Set the workspace to open automatically when you log in.

Note: Before you can open a workspace, you must have first saved it.

Creating a Workspace Shortcut
To create a workspace shortcut:
1
2
3
4

Set up and save the workspace.
In the Explorer, right-click on a folder and select New Workspace Shortcut. This option is enabled when
you have read and write permissions for the folder.
Select a workspace from the list of workspaces you have created and click OK.
To change the name or description of the workspace shortcut, right-click on the new shortcut and select
Properties.

To use the shortcut, double-click on it. The forms included in the workspace open.
Infor Mongoose Basics User Guide | 32

About Forms, Fields, Records, and Collections

Changing the Forms in Workspaces
After you set up and save a workspace, you can add forms to it, delete forms from it, and adjust the size and
position of the forms.
To change the forms in a workspace:
1
2

Open an existing saved workspace as described in Opening Workspaces on page 32.
Adjust the forms in the workspace as needed:
•
To delete a form from the workspace, close the form.
•
To add a form to the workspace, open the form.
•
Adjust the size and position of each form.

3
4
5
6

Select Form > Workspaces.
Select the name of the workspace you are changing.
Click Set from Current Forms.
Click Close.

Deleting Workspaces
To delete a workspace:
1
2
3
4

Select Form > Workspaces.
Click the name of the workspace you want to delete.
Click Delete.
Click Close.

Customizing Forms
As a user, you can customize many aspects of the way a given form looks, and save those changes so that,
every time you open the form, it includes your changes. You can make these changes in run-time, regardless
of your editing permissions
•
•
•
•
•

The size of the form
The position of the splitter in multiview forms
The sequence of how columns in a grid are displayed
The width of columns in a grid
Whether columns in a grid are displayed

Note:
•
•

Your system administrator must have the Allow saving form runtime changes process default set to 1.
You must select both of these Runtime Behavior options in User Preferences:
•
Prompt to Save Form Splitter Changes
•
Prompt to Save Grid Changes

Infor Mongoose Basics User Guide | 33

About Forms, Fields, Records, and Collections

If you do not select these options, you cannot save run-time form changes.
Any changes beyond these require that you have editing permissions that allow you to go into Design Mode
and make changes there.

About Trees on Forms
Some forms include an area that displays information in a tree structure, for easier comprehension. The tree
contains nodes that open other forms where you can find additional information about the selected record.
For example, you might have an item in inventory at several warehouses. The tree could have a node for each
warehouse where the item exists. Under each warehouse node, you could have another set of nodes for each
location in that warehouse where the item exists.
You can right-click on any of these nodes to open a form to see additional information specific to that record
and that node.

Hiding Forms
Note: This setting is not applicable in Design Mode. You can view and open hidden forms in the Design Mode
list.
1

Open the Hidden In Forms List form.
Perform any of these actions:
•
In Explorer > Master Explorer > Modules > System > Utilities, select the form.
•
In Form > Open > Select From, search for the form.

2
3
4

Click the New icon.
In the Form Name field, select the form to hide.
Click the Save icon.

Showing hidden forms
1
2
3
4

Select View > User Preferences.
On the Behavior tab, select the Show Hidden Forms in All Forms List check box.
Click OK.
View or open the hidden form.
Perform any of these actions:
•
In Explorer > Master Explorer > All Forms, select the form.
•
In Form > Open > Select From, search for the form.

Infor Mongoose Basics User Guide | 34

About Forms, Fields, Records, and Collections

Exporting hidden forms data
To export the Hidden In Forms List data, perform any of these actions:
•
•

Select Actions > To Excel.
Click the Export to Excel icon.

Importing hidden forms data
1
2
3
4
5
6
7

Open the exported Excel file.
In the Excel > Form Name column, copy the form names.
Open the Hidden In Forms List form.
Select Edit > Paste Rows Append.
In the Paste Rows Append dialog box, paste the form names from the Excel file.
Click OK.
Click Save.

Working with Collections
A collection is a set of related records. The records that the system retrieves are almost always related by the
form you are using. Often they are further limited to records that meet certain search criteria you can set by
using filters.
Retrieving Collections
First, you must locate the records you need to work with. There are several ways to find the appropriate
collection of records.
See Retrieving Collections on page 36.
Processing Collections
After you have the collection of records that you need, you can process them in a variety of ways. You can:
•

Sort them in various ways.
See Sorting collections on page 49.

•

Preview collections before you print them.
See Previewing a Record or Collection Before Printing on page 63.

•

Print the data in the collection.
See Printing Records or Collections of Records on page 63.

•

Export the collection data to an external file.
See Exporting Collections to External Files on page 50.
Infor Mongoose Basics User Guide | 35

About Forms, Fields, Records, and Collections
•

Copy collection data to or from a spreadsheet.
See Copying Data To/From a Spreadsheet on page 51.

•

Create and format graphs based on the collection.
See Creating and Formatting Graphs from a Collection on page 56.

Retrieving Collections
Use one of these methods to retrieve the records you need on a form:
•
•

To return records that match specified criteria in one or more fields, use Filter-in-Place.
To specify search criteria and return selected records to a parent form, use query forms.

When you perform these searches, you can usually use filters and wildcard characters. You can also find
records where a certain field is blank (null).
You can limit or expand the number of records that a particular search can return.

Finding records with Filter-in-Place
When you activate Filter-in-Place, any collection currently associated with the form is cleared and fields on
the form become blank. You can then specify filter criteria in one or more fields and retrieve a collection of
records that meet those criteria.
If a previous search has already been done, any fields that were used as search criteria can still have the search
criteria in them. To clear the form of all search criteria, select Actions menu > Filter > Clear in Place.
To find records with Filter-in-Place:
1
2
3

Open a form that is used to retrieve records.
Activate Filter-in-Place mode: Select Actions > Filter > Begin in Place.
Specify a value in one or more fields to specify criteria for the records you want to retrieve.
See About filter options.

4

Perform Filter-in-Place. Select Actions > Filter > Execute in Place.
The system displays all available records that match the filter criteria you designated. The number of
records retrieved is limited to the current cap on data records.
See About Caps on page 41.

5

Optionally, to view the next set of records (beyond the record cap), click Get more rows in the current
collection.

If no records meet the criteria, the system automatically places the form in "new" mode. You can create a
new record as you would after selecting Actions > New, or you can repeat this procedure with different filter
criteria. If you repeat the procedure, your previous filter criteria are initially specified in the fields. You can
adjust them, or you can remove all of them. To remove all of them, select Actions > Filter > Clear in Place.

Infor Mongoose Basics User Guide | 36

About Forms, Fields, Records, and Collections

Finding Records Using a Query Form
Most multiview forms have a query form associated with them. You can use these query forms to select a
collection of records and return the collection to the multiview form.
Caution: Records retrieved by a query form replace the current collection of records in the multiview form.
Be sure to save any changes you want to retain in the multiview form before you retrieve records with a query
form.
To perform a typical query using a query form and return the results to the associated multiview form:
1

With a multiview form open, perform one of these actions:
•
Press CTRL+Q.
•
Select Actions > Filter > By Query.
•
Right-click on the form somewhere other than on a field, and then select Filter > By Query.
Note: On a form that is not associated with a query form, the By Query menu option is not available.

2

Optionally, use one or both fields on the Primary Criteria tab to set the primary search criteria.
For more information about setting primary search criteria, see Primary Criteria in Query Forms on page
23.

3

Optionally, use the fields and buttons on the Additional Criteria tab to refine the search criteria further.
For more information about setting addition search criteria, see Additional Criteria in Query Forms on
page 24.

4
5
6
7

Click Refresh. The system returns all records that meet your search criteria.
Optionally, repeat Steps 2 - 4 as desired to get the return results you need.
To return the results of your query to the parent multiview form, click OK.
When asked if you want to return the result to the mutliview form, click Yes.

Using the Null Keyword
You can use the null keyword to search for records that contain blank fields with null values. In form fields,
a null value appears as an empty field in which no value has been specified.
You can use the null keyword in these areas of the application:
•
•
•

Filter-in-Place mode
Query form searches
Saved filters

The null keyword cannot be used for searches of field values in a collection (Edit > Find Value in Collection).
To do a null keyword search:
•
•

In Filter-in-Place mode, specify null in any field in which you can type characters.
In query forms, you can use the null keyword in any of the primary criteria or additional criteria fields
that use the = (equal) or <> (not equal) operator.

Infor Mongoose Basics User Guide | 37

About Forms, Fields, Records, and Collections

Note:
•
•
•

To find records where a particular field is not blank (null), specify <>null
The null keyword is not case-sensitive.
To perform a search in which a field might contain, in whole or in part, the word "null," enclose it in
asterisks (*null*).

Using Wildcards
When you search for information, you can use the wildcard character or implied wildcards to find items that
contain your specified sequence of characters and that may contain additional, unspecified characters.
By default, the wildcard character is an asterisk (*), but you can change it. You would especially want to do
this if you want to use the asterisk as an actual data character in fields.
You can use wildcard characters when you:
•
•
•

Search for values for fields.
Use filters and Filter-in-Place.
Use query forms.

Note: Not all fields support the use of the wildcard character as might be expected. This is especially true of
fields that automatically expand a field to a required number of characters. This functionality is commonly
seen in order and ID number fields. In these cases, you might have to place the wildcard both before and after
your search characters.
The wildcard behaves slightly differently, depending on the context in which you are using it.
The wildcard character in text searches
In a text search, the wildcard represents zero or more possible missing alphanumeric characters.
For example, using the default wildcard (*), a search using A* as the search string returns all items that begin
with the letter A.
Note: Text wildcard searches are not case-sensitive, so this search would return all items beginning with the
letter A, for example, Anthony and automobile.
Searching for *qui* matches all items that contain the letters qui. So, items that would be returned could
include Quigley and requisite.
The wildcard character in date fields
In a date search, the wildcard character matches the month, day of the month, or year.
Here are some examples:
In a system using the short date format M/d/yyyy (month/day/year):
•
•

The search term 12/*/2015 returns records for all dates in December, 2015.
The search term */*/2015 returns records for all dates in the year 2015.

Infor Mongoose Basics User Guide | 38

About Forms, Fields, Records, and Collections
•

The search term 12/31/* returns records for December 31 in all years.

Note: The wildcard character stands for the entire specification for a month, a day, or a year; you cannot use
the wildcard in a combination such as 200* to return all years from 2000 to the current year, or in a day
specification such as 2* to return all days of a month from 20 to 29.
Implied wildcards
In some fields, when you specify values on which you want to search, you can type a partial value. For example,
to find any value that contains the string "test", specify test and clear the Case Sensitive check box. This
locates values such as Test case, Amy's testing results, or Sales Contest. This is known as using an implied
wildcard.
The sort order of the collection determines the order in which you find matching records.
Note: The Find Value and Replace Value features do not support use of the wildcard character, the "null"
keyword, or comparison operators. These characters in such searches are treated as literal values. See Using
the Null Keyword on page 37 and Operators on Query Forms on page 25.
Changing the wildcard character
If you want to enter the asterisk as actual data in a field, you must first change the wildcard character.
See Changing the wildcard character.

Understanding Filters
Before you can work with a record or a collection, you must retrieve it from the database and display it in a
form. Specifications that determine which records are retrieved are referred to as filter criteria.
The system provides several different ways for you to use filter criteria to help locate only those records and
collections you actually need.
•
•
•
•

•
•

When you open a form and records display automatically, you see the results of a filter that has been
permanently attached to the form so that it selects certain records whenever the form is opened.
When you use Filter-in-Place to search for records, you create a temporary filter by entering search criteria
in selected fields. You cannot save the filter criteria used in Filter-in-Place mode.
When you use a query form to search for records, you create a filter by entering criteria in the query form
to identify the records you want to retrieve.
If this is a search you perform often using the same search criteria, you can save the criteria entered on
a query form and create your own saved filter. You can later use this saved filter without having to re-enter
all the search criteria each time.
From a query form, you can also copy or delete filters that you have saved. Copying a filter allows you to
modify and resave it without having to start from scratch.
From the Row Authorizations form, you can set filters on IDOs so that users can only view certain data,
based on the user ID under which they log in. For example, you can create a filter so that salesperson
users can only view their opportunities and not those of other salespeople. Or you can create a filter so

Infor Mongoose Basics User Guide | 39

About Forms, Fields, Records, and Collections
that your customers who log into this application through a portal can see only information about their
orders.
To find out which filter is currently used in a form, display the form and select Help > About This Form.

Creating and Saving Filters
If you frequently use the same search criteria to retrieve a collection of records, you can create and save a
filter that specifies the criteria. You can then use the filter to retrieve the collection without having to re-specify
the criteria each time. Query forms are embedded within the main form, but may also be opened standalone.
Note: You can save a search filter only from a query form. To create and save a filter:
1
2
3
4
5
6
7

Open the form that you frequently use for a query.
If the form is in Filter-in-Place mode, select Actions menu> Filter > Cancel in Place to cancel the mode
or Execute in Place to execute the filter.
To view the standalone query form, select Actions > Filter > By Query. To view the embedded query
form, select the New Filter arrow beneath the Filter field.
In the query form, enter your primary criteria and additional criteria.
See Primary Criteria in Query Forms on page 23 and Additional Criteria in Query Forms on page 24
Select Save.
Specify a name for the filter.
Click OK.

You can also create a filter by copying an existing filter, editing it, and then saving it with a new name. See
Copying and Editing Filters on page 40.

Using a Saved Filter
Once saved, you can easily use a search filter to perform a filter search any time you need to.
To use a saved filter:
1
2
3
4

Open the form for which you saved the search filter.
In the Filter field, select the filter you want to apply.
Click OK.
In the query form, click Refresh.

Copying and Editing Filters
You can edit a saved filter. You can also create a new filter by opening an existing one, editing it, and saving
it with a new name.
To edit a filter:

Infor Mongoose Basics User Guide | 40

About Forms, Fields, Records, and Collections
1
2
3
4
5
6
7

Open a query form.
Select Actions > Filter > Open.
Select a filter and click OK.
Edit the search criteria as required.
Select Actions > Filter > Save.
Optionally, to create a new filter that includes this changed criteria, specify a new Name for the filter.
Otherwise, the changes are saved to the existing filter.
Click OK.

Deleting Filters
At this time, you cannot delete a saved filter.

About caps
The maximum number of records allowed in a collection is restricted to a numerical cap. The maximum
number of items in drop-down lists is similarly capped. The system default cap on both records and list items
is 200.
Caps are designed to maintain the performance of your system and of your data source. Retrieval of a large
number of records or list items can put unnecessary demands on the data source and slow system performance.
The default caps are suitable for most data-entry tasks and information-search tasks. Still, you should be
aware of options for dealing with the size of collections and lists.
LoadCollection record cap overrides
By default, the system record cap displays 200 records (or list items) on a form. There are several ways to
override this limit:
•

Farm-wide record caps: For customers operating in a cloud-tenant farm, the farm's system administrator
can set record caps that apply to the entire server farm. These settings override other record cap settings,
depending on what type of session is being used or whether the records are being retrieved for reports.
Farm-wide settings can be made for these situations:
•
FarmUIMaxRecordCap: Used for UI sessions; for example, form and global script LoadCollection
calls, including the .NET Client class
•
FarmAPIMaxRecordCap: Used for IDO and service sessions; for example, REST API calls
•
FarmReportMaxRecordCap: See Report-related record caps on page 42.
Note: There is a possible exception to these settings: If tenants have their own system-wide record caps
set (using the User Preference Max Record Cap process default or the Report max record cap process
default) before a farm-wide cap has been set, the system honors that tenant-level setting, even if it
exceeds the farm-level setting. That tenant-level setting remains in effect until such time as the tenant
might decide to change the setting. At that point, the farm-wide setting takes precedence, and the
tenant-level setting cannot be set to exceed the farm-wide setting, even using these process defaults.

Infor Mongoose Basics User Guide | 41

About Forms, Fields, Records, and Collections
These settings can only be made by authorized farm administrators, using the Configuration Manager,
optionally by way of a Command Line Interface (CLI).
•

•

User Preferences Max Record Cap: Local and tenant system administrators can use this process default
to override the system default for their own configurations. This setting overrides most other overrides
and settings for record caps. The only exceptions to this rule are the farm-wide record cap settings.
User-level overrides: Individual users can set their own record cap overrides, in the User Preferences
dialog box. These settings generally override other record cap settings, but they are subject to the any
farm-wide record cap settings and the User Preferences Max Record Cap, if they have been set. If neither
of those settings is in effect, then user-level overrides cannot exceed the system default cap (200).
Note: By default, these User Preferences settings carry over from one session to the next. However, if
system administrators do not want those settings to persist, they can set the Save record cap override
process default. In that case, the User Preference settings last only as long as the user's current session.

•

•

•

IDO collection-level overrides: Developers can specify cap overrides at the IDO collection level for forms.
These overrides affect list sources, any included IDO collections (primary, secondary, and subcollection),
and tree components. This cap overrides the system default and user-level caps, but cannot exceed
farm-wide record caps or the User Preferences Max Record Cap process default, if they have been set.
Script overrides: Developers can use application and tree component scripting APIs to specify individual
record cap overrides. This cap overrides the system default and user-level caps, but cannot exceed
farm-wide record caps or the User Preferences Max Record Cap process default, if they have been set.
Application Event System overrides: Developers can use the Record Cap parameter in an application
event handler to override other record caps, including any system-wide record cap overrides.
Note: These overrides do not supersede farm-wide record caps that might be set. They are subject to
farm-wide record caps, the same as any other system record caps.

Report-related record caps
Record caps related to report outputs can be set at these levels:
•

•

At the farm-level, the FarmReportMaxRecordCap setting: This setting is used for report processing and
generation; for example, report-type form outputs or DataView report outputs. This setting overrides all
other record caps related to reports.
At the system or tenant level, the Report max record cap process default: This setting is a system-wide
setting and can be overridden only by the FarmReportMaxRecordCap setting.

The effect of caps when processing records
When the system processes records (for example, during posting) it ignores any cap you have set and processes
all records that meet the criteria. However, if the process returns a result set, a record cap override could still
affect how many returned records display on the form.
The effect of caps when exporting collections
When you export a collection of records to an external file, you usually want no limit on the number of records.
You need not reset the cap on collections in this case. Instead, select the Unlimited query option when you
export a collection.

Infor Mongoose Basics User Guide | 42

About Forms, Fields, Records, and Collections

More about record cap interactions
To some extent, the way that record cap settings interact with other record caps depends on what type of
session the record caps are designed to work with.
There are three basic types of sessions:
•
•
•

UI sessions, which include form and global script LoadCollection calls, including the .NET Client class
(see Interactions for UI sessions)
IDO and service sessions, which include, for example, REST API calls (see Interactions in IDO and service
sessions)
Report processing and generation, which includes, for example, report-type form outputs or DataView
report outputs (see Interactions in report processing and generation)

Interactions in UI sessions
This table illustrates how the various record cap settings work when used within a UI session:
Note: In all cases, the system default is 200. A setting of '0' is the same as no setting.
If the FarmUIMaxRecordCap is
set to:

And the User Preferences Max Record Cap
process default is:

And the User Data
Record Cap (under User
Preferences) is:

Then the operative
record cap is:

20,000

10,000

0

10,000
(Process default setting)

20,000

50,000

0

("Grandfathered" value)
20,000

50,000

(Process default setting)
0

(After the FarmUIMaxRecordCap has
been set)
20,000

10,000

50,000
20,000
(Farm-level setting)

1,000

1,000
(User-level setting)

20,000

10,000

30,000

10,000
(Process default setting)

20,000

0

30,000

20,000
(Farm-level setting)

20,000

0

0

20,000
(Farm-level setting)

0

0

0

200
(System default)

Infor Mongoose Basics User Guide | 43

About Forms, Fields, Records, and Collections
Interactions in IDO and service sessions
This table illustrates how the various record cap settings work when used within a API/service session:
If the FarmAPIMaxRecord Cap is
set to:

And the request from an
API/service call is for:

Then the operative record cap
is:

20,000

30,000

20,000
(Farm-level setting)

20,000

10,000

10,000
(Request from the API/service)

0

30,000

30,000
(Request from the API/service)

Interactions in report processing and generation
This table illustrates how the various record cap settings work when used within a report processing session:
If the FarmReportMaxRecord
Cap is set to:

And the Report Max Record Cap
process default is set to:

Then the operative record cap
is:

50,000

30,000

30,000
(Process default setting)

50,000
50,000

50,000

70,000

70,000

("Grandfathered" value)

(Process default setting)

70,000

50,000

(After the FarmReportMaxRecordCap has been set)

(Farm-level setting)

0

50,000
(Farm-level setting)

0

70,000

70,000
(Process default setting)

Working with user record caps on collections
When you retrieve records for a collection, a user-level record cap can limit the number of records to be
returned. You should consider this cap when you retrieve a collection for which the size might exceed the
cap.
Note: The techniques and procedures in this topic do not help you to know if there is a system-wide list cap
in use and, if it is, the value to which it is set. You should know, however, that any system-wide settings can
override your user-level record cap setting.

Infor Mongoose Basics User Guide | 44

About Forms, Fields, Records, and Collections
Finding the current user record cap
If you know you need to retrieve a large number of records for a collection, you can find out whether your
current user cap will allow you to display that number of records. For more information, see Viewing the
current user record cap on collections on page 45.
Identifying when you have reached the cap
Knowing whether you have reached the cap on records is fairly straightforward: The number of records in a
collection shows in the status bar in the lower-left corner of the application window.
To determine whether a collection contains the maximum number of records allowed, compare the number
of records retrieved (the second number) with the current user cap on data records. See Viewing the current
user record cap on collections on page 45.
The Go to the last object... button on the tool bar navigates to the last record displayed, not necessarily the
last actual record in the database. You can use this to help determine when a collection contains the maximum
number of records allowed but not all potential records that match your filter criteria.
The Get more rows in the current collection button on the tool bar also provides a visual cue as to whether
there are more records available. If the double arrows are blue, then there are more records available.
Retrieving additional records
If you have a collection that does not display all the records you need, try replacing the current collection
with a collection filtered using more specific criteria. You can use any of these options:
•
•
•

Do a more focused Filter-in-Place query. See Finding records with Filter-in-Place on page 36.
Use the Get more rows in the current collection button on the tool bar to retrieve and display the next
set of records.
Use the associated query form to perform a more refined query. See Finding Records Using a Query Form
on page 37.

In most cases, you should be able to get the collection you are looking for by one of these methods. Only if
these methods do not return the collection you need in full should you increase the cap on records. See
Setting the user preference for record caps on page 46.

Viewing the current user record cap on collections
If the number of records displayed in a collection is not what it needs to be, then you can view the current
user-level cap in the User Preferences, to find out what it is set to.
Caps are designed to maintain the performance of your local system and of the database server. Because
data retrieved from the server resides on your local system in a collection, retrieval of a large number of
records or list items can put unnecessary demands on the database server and slow system performance.
Note: For customers operating in a multi-tenant cloud farm, the farm's system administrator can set a record
cap that applies to the entire farm. System administrators for on-premises installations can also set
system-wide record caps. For more information, see About caps on page 41.
1

Select View > User Preferences.

Infor Mongoose Basics User Guide | 45

About Forms, Fields, Records, and Collections
2
3

Click the Behavior tab.
Determine the value set in the Data Record Cap section.
The selected option shows the current user-level cap on data records that are retrieved from collections.

Setting the user preference for record caps
Before you reset the cap for collections, consider using the Get more rows in the current collection button
on the main tool bar. With this button, you can view more records without having to change the cap.
Note: System administrators can override the Retrieve all or Use specified max setting with a farm-wide
or system-wide maximum record cap. For example, if you set the record cap to display up to 500 items, but
the system administrator has set a system-wide maximum of 250, the maximum number of items you can
display in one retrieval is only 250.
The user-level cap that you set applies to all collections in all forms.
1
2
3

Select View > User Preferences.
Click the Behavior tab.
In the Data Record Cap section, select any of these options:
•
Use Default: To use the system default cap setting, which is set to 200, select this option. This is the
default option.
•
Retrieve All: To retrieve all records and override the system default setting, select this option.
Caution: Consider carefully before selecting this option. Unlimited retrieval of a collection can
degrade system performance.
•

Use Specified Max: To set your own maximum cap, select this option. If you select this option, then
you must also specify a value in the field next to it.
Note:
•
•

Even with this setting, you cannot retrieve a number of records greater than a farm-wide record
cap or a User Preferences Max Record Cap setting.
Transferring a large number of records to your system can degrade system performance.

If you select the Use Default option, then -1 displays in this field. If you select the Retrieve All option,
then 0 displays in this field.
4

Click OK.

To refresh the collection in the current form, select Actions > Refresh.

Working with user caps on list items
Certain drop-down lists, particularly business-data list boxes, have caps on the number of list items they can
display.

Infor Mongoose Basics User Guide | 46

About Forms, Fields, Records, and Collections
Finding the current user cap on list items
If you know you need to display a large number of items in a drop-down list, you can find out whether the
current user cap will allow you to display that number of items. For more information and the procedure, see
Viewing the current user cap on list items on page 47.
Note: These techniques do not help you to know if there is a system-wide list cap in use and, if it is, the value
to which it is set.
Identifying when you have reached the cap
Knowing whether you have reached the cap on list items is more difficult than with collections. These tips
might help:
•
•

A list has probably reached the cap if it ends arbitrarily, for example, in the middle of the alphabet in an
alphabetically sorted list.
A list has probably reached the cap if it does not contain an item that you believe it should contain.

Retrieving additional list items
If you have a drop-down list that does not display all the records you need to use, try these options first:
•
•

Replace the current list with a filtered list whose items match your search criteria. For more information
and the procedure, see Filtering a Drop-down List on page 59.
Right-click on the field and select Find. For more information, see Finding a Specific Value in a Drop-down
List on page 59.

In most cases, you should be able to get the item you are looking for by using one of these methods. Only if
these methods do not return the list you need in full should you increase the cap on list items. See Setting
the user preference for list caps on page 48.

Viewing the current user cap on list items
If the number of items displayed in a field list is not what it needs to be, then you can view the current user-level
cap in the User Preferences, to find out what it is set to.
Caps are designed to maintain the performance of your local system and of the database server. Because
data retrieved from the server resides on your local system in a collection, retrieval of a large number of
records or list items can put unnecessary demands on the database server and slow system performance.
Note: For customers operating in a cloud-tenant farm, the farm's system administrator can set a record cap
that applies to the entire farm. System administrators for on-premises installations can also set system-wide
record caps. For more information, see About caps on page 41.
1
2
3

Select View > User Preferences.
Click the Behavior tab. I
Determine the value set in the List Record Cap section.
The selected option shows the current user-level cap on list items that are retrieved for drop-down lists.

Infor Mongoose Basics User Guide | 47

About Forms, Fields, Records, and Collections

Setting the user preference for list caps
Before you reset the cap for list items, consider using one or more of the techniques described in the help
topic Working with user caps on list items on page 46.
Note: System administrators can override the Retrieve all or Use specified max setting with a system-wide
maximum record cap. For example, if you set the record cap to display up to 500 items, but the system
administrator has set a system-wide maximum of 250, the maximum number of items you can display in one
retrieval is only 250.
Any change you make to the cap lasts only until you exit the application. The next time you open the application,
the default cap is applied.
1
2
3

Select View > User Preferences.
Click the Behavior tab.
In the List Record Cap section, select any of these options:
•
Use Default: To use the system default cap setting, which is set to 200, select this option. This is the
default option.
•
Retrieve All: To display all items in a list and override the system default setting, select this option.
Caution: Consider carefully before selecting this option. Unlimited retrieval of a collection can
degrade system performance.
•

Use Specified Max: To set your own maximum cap, select this option. If you select this option, then
you must also specify a value in the field next to it.
Note:
•
•

Even with this setting, you cannot retrieve a number of list items greater than any farm-wide
record cap or a User Preferences Max Record Cap setting.
Transferring a large number of list items to your system can degrade system performance.

If you select the Use Default option, then -1 displays in this field. If you select the Retrieve All option,
then 0 displays in this field.
4

Click OK.
To refresh a list, specify a wildcard character in the list box and then click the drop-down arrow. The
default wildcard character is the asterisk (*) character.

Changing Focus in Collections
If a form has more than one collection of records, you can select the collection you want to work with by
clicking a part of the form that displays the collection. For example, you can click a field or a box surrounding
several fields. Doing so changes the focus of the form to the selected collection.
Another way is to locate the collection you want in a grid and click any cell for the record you want.
While working with a collection, keep this information in mind:
•

Actions menu commands affect only the selected collection.

Infor Mongoose Basics User Guide | 48

About Forms, Fields, Records, and Collections
For example, the Next command navigates to the next record in the selected collection.
•
•

The selected collection is displayed in relief, with highlights and shadows.
Status bar messages change to reflect selected fields within the collection.

Sorting Collections
When you retrieve a collection of records, you often sort them according to the contents of one field. You can
sort based on the contents of a column in a grid, or on the value of information displayed in a field
To sort a collection based on the contents of a grid column, double-click the column heading for the column.
To toggle the sort between ascending order and descending order, repeat this action.
To sort a collection based on a field value:
1
2
3
4

In an open a form, display the collection you want to sort.
Click in the field on which you want to sort.
Select Edit > Sort Collection.
By default, records are sorted on the field in the primary collection that currently has focus. If you are
not sorting the primary collection, select a collection and field:
•
From the Collection list, select the name of the collection you want to sort. If you do not know the
name of the collection, to see the name of the current collection, return to the form and clickHelp
> About This Form.
•
In the By Property box, click the name of the field on which you want to sort.

5

By default, records are sorted in ascending order, regardless of capitalization. To sort by descending
order, select Descending.
Optionally, select Case-sensitive to take into account capitalization when sorting.
Click OK.

6
7

Using Collections Outside the Application
There are several ways you can use the data from a collection of records in other applications:
•

Export the data to an external file.
For more information, see Exporting Collections to External Files on page 50.

•

Copy the data from a collection to an external spreadsheet program.
For more information, see Copying To/From a Spreadsheet on page 51.

•

Paste data from an external spreadsheet into the collection.

•

Display the data in a graph format.
For more information, see Creating and Formatting Graphs from a Collection on page 56.

Infor Mongoose Basics User Guide | 49

About Forms, Fields, Records, and Collections

Exporting Collections to External Files
You can export the data in a collection to an external file, which in turn can be imported into another
application. Field values in the external file can be separated by either commas or tabs. Each record in the
collection becomes a row in the exported file. Each field or property becomes a column.
If you want to export the files to a spreadsheet, you can use the To Excel option instead of this process.
Note:
•

•

•

By default, columns in the file are ordered left-to-right according to an internal property list. The order
does not necessarily follow the order of columns in any grid shown on the form. However, if you use the
To Excel menu option, and the currently selected field is either in a grid or has a related grid on the form,
you can choose whether the spreadsheet should match the sequence of columns in the related grid or
the order of the internal property list.
In the spreadsheet, columns are created for the data or property for each visible component on the form.
If more than one visible component is bound to the same data, for example, a grid column and a combo
box, there is just one column in the export. Columns that are hidden on the form are still included in the
export if other visible components are bound to the same property.
Rows with multi-line fields that contain carriage returns and line feeds do not export to a file in a regular
way. To export these rows, you must copy the collection to a spreadsheet. See Copying Data to/from a
Spreadsheet on page 51.

To export a collection to an external file:
1
2
3

Open the form and display the collection that you want to export.
Select Form > Export to File.
In the Export to File dialog box, specify this information:
Source Collection
Select the name of the collection you want to export.
If you do not know the name, return to the form and click a section of the form that displays the collection.
To see the name of the current collection, click Help > About This Form.
Cap Option or Data Record Cap
Select one of these options:
•
From data currently in the collection: Select this option to export only the records currently
displayed in the collection.
•
Unlimited query (might be limited by the site administrator): Select this option to retrieve and
export all records that match the current filter criteria.
Output File Type
Select one of these options:
•
Comma-separated: Select this option to generate output with quotation marks around each string
item and then separated with commas, for example:
"doejo",4,"",1,"john.doe@acmemfg.com","",

•

Tab-separated: Select this option to generate output with tabs between fields, for example:
doejo

4

1

john.doe@acmemfg.com

Infor Mongoose Basics User Guide | 50

About Forms, Fields, Records, and Collections

Note: Ensure that the external application in which you want to view the collection supports the format
you select here.
Output File Name
Specify the file name of the external file to be created.
If you do not specify a file name, the name of the selected collection is used.
Browse
Click this button to locate the folder where the file is to be saved, or specify the destination folder.
If you do not specify a path, a dialog box will prompt you to specify the destination folder.
Note: This button and field are not available in the web client.
4

Click OK.

In the web client, the system generates the file, then starts a download to the user’s browser.

Copying Data to/from a Spreadsheet
There are times when it is useful to be able to use the data in a collection in a spreadsheet outside the system,
or to bring data from a spreadsheet into a collection in the system. The system allows you to do both.
Copying data from a form to a spreadsheet
When copying data from a form, keep these facts in mind:
•
•
•
•

Carriage returns and line feeds in a multi-line cell are removed when you copy a row to the clipboard.
So, if you have multi-line cells, you must copy those cells separately or reconstruct them in the spreadsheet.
If you plan to copy updated spreadsheet rows back into this application, see the next section.
Numeric values represented by check boxes in grids are 0 (zero) when cleared and 1 (one) when selected.
These values are transferred to and displayed in spreadsheets as 0 and 1, respectively.
This procedure can copy only the records that have actually been retrieved. If you want to copy all the
records from a collection, and the cap prevents some records from being displayed, you must change
the cap so that all records are retrieved.
See Retrieving Collections on page 36and About caps on page 41.

•

•

Row labels are pasted in the spreadsheet as the first column. The labels are for reference only and should
not be copied and pasted back into a grid. In the spreadsheet, you can delete the row-label column,
unless you want to keep it for reference purposes.
By default, the header row is not included as part of this procedure. If you want to copy the header row
as well as the records, perform one of these actions:
•
Select View > User Preferences and then select Include Grid Column Headers Copying to Clipboard.
•
Use the To Excel menu option.

Pasting data from a spreadsheet into a form grid
When pasting data from a spreadsheet into a form grid, keep these facts in mind:

Infor Mongoose Basics User Guide | 51

About Forms, Fields, Records, and Collections
•

The spreadsheet and the grid must follow the same data scheme. Some grids contain hidden columns.
Columns in the spreadsheet must exactly match the visible columns in the grid. The first column in the
spreadsheet must correspond to the first column in the grid; the second column in the spreadsheet must
correspond to the second column in the grid; and so forth.
Normally, if you originally copied from a grid into the spreadsheet, the data schemes are identical.
However, in some cases, this is not true because the order of the grid may not match the order of the
collection that was exported; in those cases, you must reorder the columns in the spreadsheet to match
the grid order.

•

•

•

•

Be careful, too, when ordering columns in the grid view. Because the paste order matches that of the
spreadsheet, if you paste a value into a column that affects the value of a later column, then when the
later column value is pasted, it might overwrite the desired (calculated) value with the value from the
spreadsheet.
Be careful when you select the data in the spreadsheet to copy. Do not select complete rows, because
the null cells at the end of the rows are also selected and copied. This causes errors when you try to paste
the rows into the grid. Select and copy only the cells that contain data.
Numeric values represented by check boxes in grids are 0 (zero) when cleared and 1 (one) when selected.
These values are transferred to and displayed in spreadsheets as 0 and 1, respectively. So, if you plan to
paste content from a spreadsheet into a grid and a check box value is included, you must enter the correct
values as 0s and 1s in your spreadsheet.
You can insert rows from a spreadsheet as new rows in a grid, or you can overwrite existing rows in the
grid with rows from the spreadsheet. The overwrite operation skips columns that are read-only, leaving
the current values unmodified.
This means you might have to take other steps to get the appropriate values into those read-only fields.

•

•

•

•

The form might validate each cell when you paste rows into a grid. Invalid data in cells in the spreadsheet
or empty cells that correspond to required fields in the grid might generate validation error messages.
Be aware that, if you edit a cell in response to a validation message during the paste operation, the paste
operation ends with the current record. Therefore, we recommend that you respond No to all prompts
for validation during the paste operation, and then go back after the operation is complete, to edit these
fields.
The maximum number of rows you can paste into a form at one time depends on the memory resources
of your computer. Pasted rows are held in memory until you save them. You can avoid out-of-memory
conditions and related errors by dividing a large number of records into smaller batches and then pasting
and saving each batch separately.
To ignore the values in some cells of the spreadsheet and replace them with the default value in the
application, type [null] in those cells of the spreadsheet before copying the rows into the grid. Do not
leave the cells blank. Be sure to include the brackets and use lowercase.
We do not recommend that you paste data back into form grids from spreadsheets that were created
using the Actions > To Excel menu option. The To Excel option is intended for exporting data to other
applications, and the output might include hidden columns. Use another copy option instead, if you plan
to paste updated data back into the application.

Copy/Paste options
To copy from a grid to a spreadsheet, see these topics:
•
•
•

Copying Entire Collections to a Spreadsheet on page 53
Copying a Single Record to a Spreadsheet on page 54
Copying a subset of records to a spreadsheet
Infor Mongoose Basics User Guide | 52

About Forms, Fields, Records, and Collections
•
•
•
•

Copying a Single Column to a Spreadsheet on page 54
Copying a subset of columns to a spreadsheet
Copying Single Cells to a Spreadsheet
Copying Blocks of Cells to a Spreadsheet on page 54

To copy from a spreadsheet into a form grid, see these topics:
•
•
•

Pasting Entire Collections into a Grid from a Spreadsheet on page 55
Pasting Selected Records (Rows) from a Spreadsheet on page 55
Pasting the Contents of a Single Cell from a Spreadsheet on page 55

Saving a collection to Microsoft Excel
To save a collection to a file and then automatically open the file in Microsoft Excel, use the Actions > To
Excel menu option or the To Excel toolbar button. When you do, your application opens the Export Collection
to Excel dialog box. Use that dialog box to provide a path and filename for the spreadsheet file to be created.
You can also specify whether or not the sequence of columns in the spreadsheet is to match the sequence
from the original source.
When you click OK, the records are placed in the specified file in the local My Documents folder - for example,
My Documents\itemsExport4.csv. If Excel is installed on your local system, it is then launched and the new file
is opened as a spreadsheet. If you have a different application set up to open comma-separated value (.csv)
files, then that application opens the file instead.
Only data displayed in the current collection is populated into the spreadsheet, and the usual export-to-file
rules apply. See Exporting Collections to External Files on page 50.
Note: Some collections might not have the To Excel option enabled.

Copying Entire Collections to a Spreadsheet
You can copy an entire collection of records from the system to a spreadsheet using the steps below, or you
can use the To Excel menu option.
These steps assume that you have the form open and the collection you want to copy showing.
To copy an entire collection to a spreadsheet:
1
2
3

Click the blank box in the upper-left corner of the grid. This action selects and highlights the entire
collection.
Press Ctrl+C or select Edit > Copy. The contents of the collection are copied to the system clipboard.
In your spreadsheet application, select the cell you want to be the first (most upper-left) cell of the
collection.
Typically, this is the first cell in the worksheet.

4

Press Ctrl+V or use whatever command your spreadsheet program uses to paste in the material.

Infor Mongoose Basics User Guide | 53

About Forms, Fields, Records, and Collections

Copying a Single Record to a Spreadsheet
These steps assumes that the form is open and showing the collection you want to copy from.
To copy a single record to a spreadsheet:
1
2
3
4

Click in the left-most column for the record you want to copy. This action selects and highlights the entire
record.
Press Ctrl+C, or select Edit > Copy. This action copies the record to the system clipboard.
In your spreadsheet application, select the cell you want to be the first (left-most) cell of the pasted
record.
Press Ctrl+V or use the command your spreadsheet program uses to paste in the material.

Copying a Single Column to a Spreadsheet
These steps assume that you have the form open and showing the collection you want to copy from.
To copy a single column to a spreadsheet:
1
2
3
4

Click in the header row for the column you want to copy. This action selects and highlights the entire
column.
Press Ctrl+C, or select Edit > Copy. This action copies the contents of the column to the system clipboard.
In your spreadsheet application, select the cell you want to be the top (upper-most) cell of the pasted
column.
Press Ctrl+V or use the command your spreadsheet program uses to paste in the material.

Copying Blocks of Cells to a Spreadsheet
These steps assume that you have the form open and showing the collection you want to copy from.
To copy a block of cells from a collection to a spreadsheet:
1
2

Click in the first (upper-left) cell you want to copy.
Shift-click in last (lower-right) cell you want to copy. The block of cells is selected and highlighted.
You cannot select non-adjacent cells. That is, you can use Ctrl-click to select multiple non-adjacent cells,
but when you paste them into the spreadsheet only the first cell (or first contiguous block of cells) actually
gets pasted into the spreadsheet.
Alternatively, you can select the cells to copy by pressing shift-up, shift-down, shift-right, and shift-left.

3
4
5

Press Ctrl+Insert. This action copies the selected block of cells to the system clipboard.
In your spreadsheet program, select the cell you want to be the first (most upper-left) cell for the pasted
columns.
PressCtrl+V or use whatever command your spreadsheet program uses to paste in the material.

Infor Mongoose Basics User Guide | 54

About Forms, Fields, Records, and Collections

Pasting Entire Collections into a Grid from a Spreadsheet
The procedure to copy an entire collection of records from a spreadsheet and paste them into a grid is virtually
the same as the procedure described for a subset of records .
The only real difference is that you select the entire collection instead of just a subset of rows.

Pasting Selected Records (Rows) from a Spreadsheet
You can paste the contents of selected records that you have worked on in a spreadsheet into a form grid.
This might be useful, for instance, when you have copied a set of records from the system into a spreadsheet
for editing and now you want to return the contents of the records, with the changes, to the system.
To paste selected rows into a grid:
1

Open the form into which you want to paste the records.
To paste the records into a blank grid, you can do a Filter-in-Place query that you know will retrieve no
records. This puts the form in "new" mode.
See Finding Records with Filter-in-Place on page 36.

2

In the spreadsheet, select the rows you want and press Ctrl+C to copy them to the system clipboard.
Do not include the header row, if you have one. If you include the header, the paste operation probably
will not complete successfully.

3

In the grid, click inside the field at the location where you want to copy the data.
In many cases, especially if you have a blank form in "new" mode, this is the first cell in the grid, though
you can paste them in anywhere. Some tips:
•
•

4

Do not create a new row; the paste action will automatically create the row.
Do not click the row label. Instead, click inside any field on the row you want to replace or below
which you want to append the new rows. If you are replacing multiple rows, the paste action will
replace the row in which you clicked and each subsequent row, replacing the contents of those rows
except for the read-only fields.

From the Edit menu, select one of these options:
•
To insert new records at (below) the selected field, select Paste Rows Append.
•
To overwrite the content of existing records, beginning with the selected field, select Paste Rows
Overwrite. This option only overwrites active fields. Fields that are read-only are left unchanged.
Note: Do not use Ctrl+V to paste the rows. Doing so causes the system to write the entire contents of
the source row to a single cell. Use one of the options listed in this topic.

Pasting the Contents of a Single Cell from a Spreadsheet
You can paste the contents of selected records that you have worked on in a spreadsheet into a grid.
This might be useful, for instance, when you have copied a set of records from the system into a spreadsheet
for editing, made minor changes in a few records, and now want to copy those changes back to the grid
without having to copy and paste the entire collection.

Infor Mongoose Basics User Guide | 55

About Forms, Fields, Records, and Collections

Note: This procedure does not work for blocks of cells. If you attempt to copy and paste blocks of cells, the
system attempts to write the contents of all source cells to a single cell in the destination grid.
To paste the contents of a single cell from a spreadsheet into a grid cell:
1
2

Open the form where you want to paste the content.
In the spreadsheet, select the cell you want and then press Ctrl+C to copy the content to the system
clipboard.
To avoid problems when copying content from Microsoft Excel spreadsheets, select (but do not copy)
the cell, and then copy only the cell contents in the formula bar field. If you select the entire cell, certain
hidden formatting content gets copied that can cause problems in the grid.

3
4

In the grid, click inside the field where you want to copy the data.
Press Ctrl+V, or select Edit > Paste.

This action overwrites any content that was previously in the field.

Creating and Formatting Graphs from a Collection
Note: This topic applies to the graphing tool, which is supported for backward compatibility. We recommend,
if you want to add charts or gauges to a form, that you use the newer FusionCharts tool instead.
There are at least two ways you can redisplay data from a collection of records using a graph format:
•

If you have copied the data to a spreadsheet, you can use the graphing capabilities of the spreadsheet
program.
See the documentation for your spreadsheet application.

•

You can redisplay the data in graph format within the system, using the built-in graphing capabilities.

To create a graph using the system graphing capabilities, you identify what information is to be used to
construct the graph and what type of graph is to be drawn, such as a bar graph or a line graph.
See Creating a Graph (Pre-Fusion Charts) on page 56.
After the graph is displayed, you can adjust it, including changing the information included, changing the
type of graph, and adjusting the labels.
See Editing or Reformatting a Graph (Pre-Fusion Charts) on page 57.
When you're satisfied with the online display, you can handle the results by saving, copying, or printing the
graph.
See Handling the Graph Results (Pre-Fusion Charts) on page 57.

Creating a Graph (Pre-Fusion Charts)
Note: This topic applies to the graphing tool, which is supported for backward compatibility. We recommend,
if you want to add charts or gauges to a form, that you use the newer FusionCharts tool instead.
1

Open a form and display the collection of records you want to graph.

Infor Mongoose Basics User Guide | 56

About Forms, Fields, Records, and Collections
If you do not want to see the graphs for all records in the collection, you can either filter the collection
to display only the records you do want to see, or (especially if you want to view the graph for one particular
record) select the record you want to start with in the grid view.
2
3

Select Actions > Graph.
In the first page of the Graph Collection dialog box, select the information you want to include in the
graph. In the list, click one or more properties (field names) that identify the information you want to
graph.
The system uses a different color in the graph for each property you include.

4
5

Click Next.
In the second page, select the information to appear in the horizontal axis labels.
If you do not want any labels for the horizontal axis, do not select anything from this page.

6

Click Next. The system displays a preliminary view of the graph. By default, the graph is a 2D column
chart with 8 columns.

Editing or Reformatting a Graph (Pre-Fusion Charts)
Note: This topic applies to the graphing tool, which is supported for backward compatibility. We recommend,
if you want to add charts or gauges to a form, that you use the newer FusionCharts tool instead.
To edit or reformat the graph:
•
•
•

To change the type of graph, select a different option in the Type field and click Refresh.
To change the number of items that display at one time, type a number in the Scroll By box and then
click Refresh.
To resize the graph so you can see detailed labels and scaling, verify that the Lock Objects check box is
cleared.
Then, when you click an editable area of the graph, a frame with handles displays. To resize the area, use
these handles. To move the editable area, click-and-drag inside the frame.

•

To navigate through the records displayed in graph views, click << Previous and/or Next >> below the
graph.

Handling the Graph Results (Pre-Fusion Charts)
Note: This topic applies to the graphing tool, which is supported for backward compatibility. We recommend,
if you want to add charts or gauges to a form, that you use the newer FusionCharts tool instead.
When you move your pointer over the graph area, these buttons display in the upper right corner of the graph:
•
•

•

Print Chart: Click this button to open the standard Print dialog box, where you can select a printer and
set printer options. When you click OK, the system prints the graph using the selected printer and settings.
Copy Chart to Clipboard: Click this button to copy the graph to the system clipboard. From there, you
can paste it into a document or file such as a Word document, a PowerPoint presentation, or an Excel
spreadsheet. You can also paste it into a graphics editing program for further touchup.
Copy Chart to File: Click this button to save the graph file in any of these formats:

Infor Mongoose Basics User Guide | 57

About Forms, Fields, Records, and Collections
•
•
•
•
•
•

Bitmap (*.bmp)
JPEG (*.jpg)
GIF (*.gif)
TIFF (*.tif)
PNG (*.png)
Enhanced Metafile (*.emf)

Working with Records
A record is a group of related pieces of data or information. On most forms, each piece of information displays
in a separate field on the form. Taken together, these pieces of information constitute one record. On forms
that display collections of records in grids, each row represents a single record.
In most of your day-to-day operations, you will be dealing with records. You can learn more about locating,
modifying and managing records in these topics:
•
•
•
•

Locating Specific Information in Records on page 58
About Row Labels on page 61
Printing (and Previewing) Records and Collections of Records on page 63
About Adding, Changing, and Deleting Records on page 64

Locating Specific Information in Records
When you work with a collection of records, you often need to find a particular record or piece of information
within that record. There are several ways to do this.
•

With field list boxes that contain business data on page 71, you can search for valid values and retrieve
a selected value into the field.
For more information, see Finding a Specific Value in a Drop-Down List on page 59.

•

On most field lists, you can filter for designated values, to limit the number of items you must look through
to find the ones you want.
For more information, see Filtering a Drop-down List on page 59.

•

On most field lists, you can also search the items for specific values.
For more information, see Finding a Value in a Collection of Records on page 60.

For other suggestions on how to locate the records you are looking for, see Tips for Finding Records on page
60.

Infor Mongoose Basics User Guide | 58

About Forms, Fields, Records, and Collections

Finding a specific value in a drop-down List
With drop-down list boxes of the business-data type, you can search for valid values and retrieve a selected
value into the field.
To find and retrieve a specific value from a drop-down list:
1
2

Click in a drop-down list box.
To select the Find feature, place the focus on the field for which you want to find a value and then select
Edit > Find Value for Current Field.
Note: The Find feature is not available for all fields.

3

The system opens one of these forms:
•
The query form associated with the form from which you initiated the Find. If this form opens, perform
these steps:
•
Specify any primary criteria for your search.
•
Specify any additional criteria.
•
Click Refresh.
•
Select a record in the Results pane that contains the field value you want.
•
Click OK.
•
At the prompt, click Yes. The system returns to the original form and inserts the value you
selected in the field.
•
The form in which values for the field are defined. If this form opens, perform these steps:
•
Select the record containing the value you want.
•
Select Form > Close and Save Changes.
•
At the prompt, click Yes. The system returns to the original form and inserts the value you
selected in the field.

Filtering a drop-down list
When searching within a field list for a particular value, you can limit the contents of the list to make it easier
to find the desired value.
To filter a field list:
1
2
3
4

Click in a field list box.
If there is a value in the box, delete it.
Specify the characters you want to match. You can use one or more instances of the wildcard character
to broaden or narrow your search. For more information, see Using Wildcard Characters on page 38.
Click the arrow in the field.

The system displays a list consisting of items that match your criteria. The number of items displayed is limited
to the current cap on lists. See About caps on page 41.
Note:
•

You can filter only on drop-down lists for business-data list boxes. See About lists in fields on page 71.

Infor Mongoose Basics User Guide | 59

About Forms, Fields, Records, and Collections

•
•
•

Comparison operators are not supported in filtering drop-down lists. See Operators on Query Forms on
page 25.
Validators do not run in Filter-In-Place mode.
In two-column lists, which show an identification number in the left column and a name or description
in the right column, filter criteria apply only to the left column. Also, the left column may contain leading
spaces and thus require a preceding wildcard when you enter filter criteria.

For example, to find ID numbers starting with 3 in a two-column list, you might need to specify *3*.
To filter on data in the right column, use the Find feature, if it is available, to open a query form. See Finding
a specific value in a drop-down List on page 59.

Finding a Value in a Collection of Records
You can search the records in a collection for a specific value in a field. For example, suppose you wanted to
locate the records for all customers located in the state of Indiana. Using the Customers form, you would
search on the State/Prov field for the abbreviation IN. The Find Value in Collection feature locates the first
record for the state of Indiana. You can then repeat the Find action until you locate the particular customer
you want.
Note: This search applies only to a collection that has been retrieved and does not query the database. Thus,
it is limited by the cap on records retrieved.
To find a specific field value in a collection of records:
1
2
3
4

In an open form, display the collection you want to search.
Click in the field or grid column you want to search. Note that, in a grid, the search only looks at records
below the current cursor position.
Select Edit > Find Value in Collection.
In the Find dialog box, Find field, specify the value you want to find, such as a word or number.
The Find Value feature uses an "implied wildcard" search.
By default, the Find Value feature searches the field in the primary collection that currently has focus. If
you do not want to search the primary collection, you can select a different collection and field:
•

•
5
6
7

From the In Collection list, select the name of the collection you want to search. If you do not know
the name, return to the form and select Help > About This Form to see the name of the current
collection.
From the In Property list, select the name of the field you want to search.

To find records that have the exact capitalization you typed, select Case Sensitive.
To find the first record that contains the value for which you are searching, click OK.
To find the next record that contain the value, select Edit > Repeat Find.

Tips for Finding Records
If you cannot find the record you want in a collection, consider these possibilities:

Infor Mongoose Basics User Guide | 60

About Forms, Fields, Records, and Collections
•

Are you looking in the correct collection?
Try changing the focus of the form, to make sure you are focused on the correct collection. You can tell
which collection currently has the focus by noting where the cursor is and/or which fields are highlighted.

•

Are the correct records retrieved?
To identify the current filter being used to retrieve records, select Help > About This Form. If necessary,
use Filter-in-Place or a query form to retrieve the collection you need. For more information, see one of
these topics:

•

•
Finding Records with Filter-in-Place on page 36
•
Finding Records Using a Query Form on page 37
Have you overlooked the record?
Try sorting the collection. Select Edit > Sort Collection.
For more information, see Sorting Collections on page 49.
You can also search for a specific value in a field in a collection. Select Edit > Find Value in Collection.
For more information, see Finding a Value in a Collection of Records on page 60.

•

Do you retrieve the same collection of records every time you use a form?
You may want to save your search criteria as a filter so that you can reuse it instead of having to re-enter
it every time.
For more information, see Creating and Saving Filters on page 40.

About Row Labels
Row labels are displayed in the left column of a grid form. These labels show the numerical order of records
in a collection and provide indicators about the current state of each record, whether it is saved, new, modified,
or marked for deletion. Row labels also show whether unsaved records contain validation errors that must
be corrected before the records can be saved.
Use this table to interpret the row label indicators that are shown in the first column of grids:
Indicator

Meaning of the Indicator
Indicates the current (selected) record in the collection. In a multiview form, this is the record
that displays in the detail view.
Indicates that the record is new and contains no validation errors. It has not yet been saved.
Indicates that the record has been modified and contains no validation errors. It has not yet
been saved.
A modification is considered to occur whenever there is a change to a value in any field.
Indicates a record marked for deletion.
The record is removed from the database when you save the record or the collection containing it.

Infor Mongoose Basics User Guide | 61

About Forms, Fields, Records, and Collections

Indicator

Meaning of the Indicator
Indicates at least one validation error for a new record, modified record, or record marked
for deletion.
The indicator is added to the row label when you navigate away from the record, receive a
validation error message concerning the record, and do not correct the error. When you
subsequently try to save the record, or the entire collection containing the record, the system
repeats the validation error message, and you cannot save it until the error has been corrected.
Indicates that the record is currently suspended by the Application Event System (AES) and
is awaiting a workflow or inbox response.
This mean that an IdoOnItemUpdate is configured for the record. Processing is currently suspended and waiting for a workflow or inbox response (for example, Yes or No) for that row.

Replacing a Value in a Record
You can search a collection for records that contain certain field values and replace them with new values.
To replace a field value in a collection:
1
2
3
4

Open the form and display the collection you want.
Place the cursor in the field for which you want to replace values. Note that, in a grid, the replacement
only looks at records below the current cursor position.
Select Edit > Replace Value in Collection.
In the Replace field, specify the value you want to find and change.
The Replace Value feature uses what is known as an "implied wildcard" search.
See Using wildcards on page 38.

5
6

In the With field, specify the new value you want to use.
Optionally, from the In Collection list, select the name of the collection you want to search.
If you placed the cursor in the desired field before starting, the correct collection should already be
selected.
If you do not know the name of the collection, return to the form and click the section displaying the
collection. Then select Help > About This form to see the name of the current collection.

7

Optionally, from the In Property list, select the name of the field in which you want to make replacements.
If you placed the cursor in the desired field before starting, the correct property should already be selected.

8
9

To find and replace a value only if it has the exact capitalization you specified, select Case Sensitive.
To replace the value in this field regardless of the current value, select the All Values check box. The
Replace field is deactivated, indicating that any value in the field will be replaced.
10 Click OK.
11 When prompted, click one of these buttons:
•
To replace only the currently selected and displayed field value, click Yes.
•
To skip the currently selected and displayed field value and move to the next one, click No.
•
To cancel the operation and make no replacements, click Cancel.
Infor Mongoose Basics User Guide | 62

About Forms, Fields, Records, and Collections
•

To replace all values for this field in the collection, click Yes to all.

Printing Records or Collections of Records
It is sometimes useful to print a collection of records. You can also preview the collection before sending it
to the printer.

Previewing a Record or Collection Before Printing
Before printing a record or collection, you might want to preview what will be printed.
1
2
3
4

Open a form and display the collection you want.
Optionally, to print a single record, select that record.
Select Form > Print Preview.
Select one of these options:
•
To preview the selected single record, select Current Field Data.
•
To preview the entire collection, select Collection Properties and select the collection you want to
print.
If you do not know the collection name, return to the form and click the section displaying the collection.
Then click Help > About This Form to see the name of the current collection.

5
6
7

Specify whether the output should be Tabular with each field or property shown in a separate column,
or Columnar, with each field in the first column and the value in the second column.
Optionally, to sort the fields and properties listed in the Select Fields/Properties box alphabetically,
select the Sort Alphabetically check box.
From the Select Fields/Properties group box, select the items you want to include in the preview:
•
To exclude an item, clear its check box.
•
To change the order, moving an item up or down in the list, use the Up and Down buttons.

8

Optionally,for collections only, to show a light gray background behind alternating records, select Shade
Alternate Detail Lines.
9 Optionally, to change the display font, click Font. Specify the font family, size, and style you want to use,
and then click OK.
10 Optionally, to change the print setup options for the preview, click Print Setup, adjust these print settings,
and click OK:
•
Paper size and source
•
Page orientation (portrait or landscape)
•
Page margins
11 Click OK. The preview window is displayed.

Infor Mongoose Basics User Guide | 63

About Forms, Fields, Records, and Collections

Printing a Record or Collection
You can print selected records or collections of records. You can also preview the output before printing it.
See Previewing a Record or Collection Before Printing on page 63.
1
2
3
4

Open a form and display the collection you want.
Optionally, to print a single record, select that record.
Select Form > Print.
Select one of these options:
•
To print the selected single record, select Current Field Data.
•
To print the entire collection, select Collection Properties and select the collection you want to
print.
If you do not know the collection name, return to the form and click the section displaying the collection.
Then click Help > About This Form to see the name of the current collection.

5
6
7

Specify whether the output should be Tabular with each field or property shown in a separate column,
or Columnar, with each field in the first column and the value in the second column.
Optionally, to sort the fields and properties listed in the Select Fields/Properties box alphabetically,
select the Sort Alphabetically check box.
From the Select Fields/Properties group box, select the items you want to print:
•
To exclude an item, clear its check box.
•
To change the order, moving an item up or down in the list, use the Up and Down buttons.

8

Optionally,for collections only, to show a light gray background behind alternating records, select Shade
Alternate Detail Lines.
9 Optionally, to change the display font, click Font. Specify the font family, size, and style you want to use,
and then click OK.
10 Optionally, to change the print setup options, click Print Setup, adjust these print settings, and click OK:
•
Paper size and source
•
Page orientation (portrait or landscape)
•
Page margins
11 Click OK.
12 Optionally, In the Windows Print dialog box, you can select an alternate printer or set additional print
options, before sending the output to the printer.
13 Click OK. The information is printed.

About Adding, Changing, and Deleting Records
After you add a new record or change an existing record, you have the option of saving it immediately or
saving it later. If you do not save it immediately, the record is marked as new or modified in its row label.
Similarly, when you delete a record, it is labeled as deleted but the deletion is not committed to the database
until you save the record.

Infor Mongoose Basics User Guide | 64

About Forms, Fields, Records, and Collections
Before you save your changes, you can undo them, singly or in a batch. You can then make further changes,
remove a new record, or restore a record you have marked for deletion.
When you save the changes, they are transferred to the database for permanent storage and the status labels
are removed.
Within a collection of records, you can perform these tasks:
•
•
•
•
•
•
•
•
•

Adding Records on page 65
Copying records on page 65
Checking for Errors on Forms on page 66
Saving Records on page 67
Deleting Records on page 67
Undoing Deletion of Records on page 68
Undoing Changes to a Record on page 68
Undoing All Changes to Records on page 68
Copying Data to/from a Spreadsheet on page 51

Adding Records
With most forms that display records in grids, you can add (new) records to the collection. When you do so,
the new record is added after whichever record is currently selected.
To add a record:
1

Locate and select the record in a collection after which you want to add the record.
If you want to add the record after the current last record in the collection,select the last row in the grid,
which has the arrow in the row label.

2
3
4

Select Actions > New.
Complete the fields as needed.
Save the record.

Copying records
Copying a record, rather than creating a new one, and then modifying it, can often save time in repetitive
data-entry tasks.
To copy a record:
1
2

Select the record to copy.
Perform one of these actions:
•
Select Actions > Duplicate.
•
Right-click the row and select Duplicate.
A copy of the selected record is inserted.

3

Edit the record as needed.

Infor Mongoose Basics User Guide | 65

About Forms, Fields, Records, and Collections
4

Save the record.

Note:
•
•
•

You must change the primary key value; for example, if you copy an item record you must change the
item ID to be unique.
If the record contains a field with a number that is automatically assigned and cannot be manually
changed, the system assigns the next available number to the copied record when you save it.
If the record contains a field with a number that is automatically assigned but which can be changed
manually, the number is duplicated in the copied record. You can update the value manually before you
save the record. To allow the system to assign the next available number automatically, make the field
blank before you save the record.

Checking for Errors on Forms
Most forms validate new and modified records and report validation errors. Messages inform you about any
field that contains an invalid value or about any field that requires a value but does not contain one.
Validation occurs when you perform these actions:
•
•
•

Navigate away from a new or modified record.
Attempt to save new or modified records.
Select Actions > Validate.

Some fields are set to validate immediately and not wait until you perform one of these actions. In these
cases, validation occurs as soon as you attempt to move the focus to another field in the same record.
Validation Error Messages
When you perform an action, other than an action to save all records, that triggers validation, an error message
is displayed for the first field in the current record that contains an error. If there are multiple errors, the
system then cycles through each field with an error.
If you attempt to save a form that has multiple records with errors, the system reports which records in the
current collection contain errors. You cannot save the form until all errors have been corrected.
Other Warning and Informational Messages
Some forms are designed to report warnings and other information in a dialog box. The dialog box opens
when you attempt to save records. Use the dialog box to troubleshoot and correct the problems:
•
•
•

To move from the dialog box to the associated record in the form, double-click the message.
If a record is associated with several messages, you can expand the display to see them all. Select the
message and click the Expand Selection button.
You can close the dialog box and redisplay it later.

To close it, click Done.
To reopen it, select View > Warnings. The messages remain available for viewing until you refresh the collection
or perform another save operation.

Infor Mongoose Basics User Guide | 66

About Forms, Fields, Records, and Collections

Correcting Form Errors
To correct records in the collection:
1
2
3
4
5
6
7

Note the rows in the grid that are marked with an error icon.
Navigate to a record indicated by an error.
Select Actions > Validate.
Note the name of the field reported in the error message.
Move the insertion point to the field named in the error message.
Specify a valid value.
Repeat steps 3 through 6 until all fields in the current record are valid.

Repeat the entire procedure until you have corrected all invalid records.

Saving Records
You can save a single record, or you can save all records at one time.
You can selectively save new or modified records in a collection. Similarly, you can selectively commit single
deletions to the database.
Alternatively, if you have made multiple changes in a form, for instance, if you have added new records in a
form, modified them, or marked them for deletion, you can save all changes to the records in a single step.
To save a single record, select the record and select Actions > Save current.
To save all records, select Actions > Save.

Deleting Records
You can delete a single record or a group of adjacent records from a collection.
1

Select the record you want to delete.
Alternatively, to select a group of records for deletion, click the row label for the first record in the group
you want to delete. Hold down SHIFT and click the row label of the last record you want to delete.

2
3
4

From the Actions menu, select Delete.
When a message tells you that the record will be permanently deleted after a save action, click OK.
To delete the records permanently from the database, save the collection.

Note: The record is not actually deleted from the database until you save your changes. If you make a mistake
or change your mind about deleting records, you can unmark those records. For more information, see these
topics:
•
•

Undoing Deletion of Records on page 68
Undoing Changes to All Records on page 68

Infor Mongoose Basics User Guide | 67

About Forms, Fields, Records, and Collections

Undoing Deletion of Records
A record is not permanently deleted from the database until you save it. Before you save the record, which
deletes it permanently, you can undo the deletion.
Note: After you save a record or a collection that has records marked for deletion, you cannot undo the
deletions.
To undo deletion of a record:
1

Select the record marked for deletion that you want to unmark.
Records marked for deletion have a deletion indicator in the row label. See About Row Labels on page
61.

2

Select Actions > Delete.

Notice that the deletion marker is no longer displayed in the row label.

Undoing Changes to a Record
If you have made changes to a record but not saved the record or the form, you can undo your changes and
restore its original values. In a record marked for deletion, this procedure removes the deletion mark as well
as undoing any changes to field values.
Caution: Note that, after you save a record or form that has records that have been changed, you cannot
undo the changes.
To undo changes to a record:
1

Select the record for which you want to undo the changes.
Records that have been changed include a change indicator in the row label. See About Row Labels on
page 61.

2

Select Actions > Refresh Current.

Notice that the system restores any changes made to their original values, and the change indicator no longer
appears in the row label.

Undoing All Changes to Records
If you have made changes to multiple records or marked multiple records for deletion, but you have not saved
the records or the form, you can undo your changes and restore records to their original values. In records
marked for deletion, this procedure removes the deletion marks as well as undoing any changes to field
values.
Caution: Note that, after you save a record or form that had records that had been changed or marked for
deletion, you cannot undo the changes.
To undo all changes to records:

Infor Mongoose Basics User Guide | 68

About Forms, Fields, Records, and Collections
1
2

Select Actions > Refresh.
When asked if you want to save changes, click No.

Notice that the system restores any changes made to their original values, and change indicators no longer
display in the row labels. See About Row Labels on page 61.

Working with Fields
The system displays each piece of information in a record in a different field (in a detail view) or column (in
a grid view). Taken together, all the related fields on a form typically constitute one record. Fields represent
the most basic bit of information in a record.
In this section, you can find the following information about fields:
•
•
•
•
•
•

Types of fields on page 71
The significance of background colors in fields on page 69
Lists in fields on page 71
Date fields on page 73
Changing or deleting specific field values on page 73
Use of graphics or other binary objects in fields on page 73

About editable field types and colors
The foreground (font) and background colors of certain components can be used to indicate this information
about the component:
•
•
•

Whether values are required or optional
Whether values can be updated or are read-only
Whether values are automatically generated when the record is saved

The default colors for these fields are preset; however, your system administrator can change them by using
themes.
These are the four basic types of fields that can be controlled in this way:

Infor Mongoose Basics User Guide | 69

About Forms, Fields, Records, and Collections

Type of field

Description/Comments

Required

Indicates that you must enter a value for a record before you can save the record.
Note: A required field that is part of an optional subcollection is required only if you
create a record in the subcollection.
For example, when you add a new user on the Users form, you can optionally enter
group information for the user, but it is not required. However, if you do create a
group record, the Group Name and Primary Group fields on that record must be
filled in.
Optionally, the system can be set to use, in place of or in addition to a set of colors,
a red asterisk in front of the field name. System administrators can set this using a
theme.
To change the colors for required fields, use the Required Foreground and Required
Background color options, and the Required Field Indicator color option to change
the required field indicator color in the Theme Editor.

Optional

Indicates that the field does not require a value for the form to be saved.
To change the colors for optional fields, use the Foreground and Background color
options in the Theme Editor.

Read-only

Indicates that the value in the field cannot be changed by the end user.
To change the colors for read-only fields, change the Read-Only Foreground and
Read-Only Background color settings in the Theme Editor.

To Be Determined
(TBD)

Indicates that the value will be generated by the system at the time the record is
saved, if no other value has been specified.
In this type of field, you have the option to enter your own value. If you do not enter
a value, the system generates a value for the field automatically when you save the
record.
Optionally, the application can be set to use, in place of or in addition to a set of
colors, a green asterisk in front of the field. System administrators can set this using
a theme.
To change the colors for TBD fields, change the TBD Foreground and TBD Background color settings in the Theme Editor.

These types of components can be controlled using these settings:
•
•
•
•
•
•
•
•

Check boxes
Combo boxes
Date pickers
Drop-down lists
Edit fields
Grids and grid columns
Option (radio) buttons
Vertical grids and vertical grid elements

Infor Mongoose Basics User Guide | 70

About Forms, Fields, Records, and Collections

Types of Fields
WinStudio uses a variety of field types to present options and information. It is at the field level that changes
to records take place, so it is a good idea to understand the different types of fields as described in this table:
Type

Description

Input fields

Input fields allow you to enter information to be saved with the record. The type of input
(whether alphabetic only, numeric only, alphanumeric, or other) depends on how the
field is defined.
For more information about a particular input field, see the online help for that field.
The background color of the field indicates how you are intended to use it. For more information, see About Field Types and Background Colors on page 69

Lists

Lsts are intended primarily to allow you to select an item from a list that is displayed when
you click the arrow. However, in many lists, you can type in particular values you want or
values to use as the basis for a Find operation.
List fields come in two types: Business-data lists and option lists. For more information
about these list types and how to use them, see About Lists in Fields on page 71.

Check boxes

Check boxes typically represent an on/off option. When selected, a check mark appears
inside the box (as in the example here), and the option is considered "on." When it is
cleared (that is, the check box is empty), the option is considered "off."
Check boxes are often presented in groups where several related options can all be either
selected or not.

Option buttons

Option buttons (sometimes known as "radio buttons") are typically used for a group of
mutually exclusive options. In these cases, only one option can be selected. In most cases,
all options are not selected until you actually select one.
In grid columns, the cell displays the selected option, if any.

Using the Question Mark Character
In system-generated fields, which have a green background or contain a green asterisk depending on the
theme, you can use the question mark (?) character to fill in and increment after a prefix. For example, if you
specify JB?, in a Job Number field on a Job Orders form that uses this functionality and then save, the entry
is expanded to JB00000001. If you add another record with JB?, it is incremented to JB00000002, and so on.

About lists in fields
There are two types of lists:
•
•

Business data lists display information added to the database by system users using data-input forms.
Option lists display a fixed set of options, for example the three statement cycle options Weekly, Monthly,
and Quarterly. Option lists are built in to the application, rather than being created and stored in the

Infor Mongoose Basics User Guide | 71

About Forms, Fields, Records, and Collections
database. You cannot usually change the options in these lists, and they usually have fewer than ten
items.
The rest of the information in this section applies only to business data lists.
Filtering lists
You can filter to reduce the number of items returned and displayed in a business data list.
Right-click context menus
Right-clicking a business data list field brings up a context menu that usually presents the Add, Details, Find,
and Help commands. These commands, along with other features for using business-data lists, are basic aids
to finding and entering information in the system.
Refreshing lists
While you are viewing a form containing a business data list, you or another user might edit the records in
the original form from which the list is derived. Any new or removed records are not reflected in the list, unless
you refresh it.
To refresh the list, perform one of these actions:
•
•

With the cursor in the list field, press F2.
Type one wildcard character (usually *) in the list field, and then click the drop-down arrow.

Adding a New Field Value
If a value does not exist in a field list, and you need it, in many cases you can add the new value into the field
using these steps:
1
2
3

Open a form and display the record where you want to add the field value.
Click in the field.
Select Edit > Add Value for Current Field.
This option is available only for business-data list fields When the option is available, the form that is
used to maintain the values (records) for this field is displayed. A new record is added automatically.

4
5

Complete the fields for the new record.
Select Form > Close and Save Changes.
The original form is displayed again, with the new value inserted into the field.

Finding Details about a Value in a Drop-down List
When selecting an option in a business-data list, you might want to know more about the option before you
select it. You can use the Details menu option to drill down to the form where the option is defined.
To find details on a particular list item, with the cursor in the field:

Infor Mongoose Basics User Guide | 72

About Forms, Fields, Records, and Collections
1
2
3

Click in the field list for which you want to find details.
Select Edit > Details for Current Field. Alternatively, you can right-click and select Details. Not all field
lists include a Detail option.
Review the information about the option in the form that opens.

Entering Dates
To select a date from a pop-up calendar:
1

In the date field, click the arrow.
The system displays a pop-up calendar with the current date selected.

2

Use the calendar to select the date you want:
•
Click a date in the calendar to enter it in the field.
•
To change the month, click the forward or back arrows at the top of the calendar.

If the system converts the date to the appropriate format when you exit the field, the input is accepted.
You can also specify a date manually in a date field, using any valid date format.

Changing or Deleting Field Values
To add or delete a value in a field that contains a business-data list:
1
2
3

Open a form and display the record for which you want to change a field value.
Click in the field.
Right-click and select Details.
The system displays the form used to maintain the values (records) for this field and automatically selects
the value you selected.

4
5

Make your changes or delete the record.
Save your changes and close the form.
The system returns to the original form. The field you selected reflects the changes.

Note: You might have to refresh the collection to see the changes. Select Actions > Refresh.

Graphics and Other Binary Objects in Fields
If you can use graphics or other types of binary objects in a field, commands on the Edit menu are enabled
so you can work with the object. Use the menu options as shown in this table:

Infor Mongoose Basics User Guide | 73

About Forms, Fields, Records, and Collections

To perform this action

Use this Edit menu option

Results

View the graphics file or
object

View Binary Data for Current
Field

The application associated with the file type
is run and displays the object. For example,
if the file is a bitmap (.bmp) image, and you
have your system set to open bitmap objects
with Microsoft Paint by default, the system
opens Paint and displays the bitmap image.

Insert a graphics file or
other binary object

Import Binary Data for Current Field

A dialog box is displayed where you select
the path for the file. The dialog box lists only
the types of files appropriate for the field.

Delete a graphics file or
other binary object from
a field

Delete Binary Data for Current Field

The object is deleted.

Export the graphics file or
other object to an external file so you can use it
elsewhere

Export Binary Data for Current Field

A dialog box is displayed where you select
the path and file name for the object. The
dialog box lists only the types of files appropriate for the object you are exporting.

Sending form-specific emails
To quickly send an email that includes specific information from the form you are currently viewing:
1
2

3
4
5

6

Select an object on the form and click the envelope icon on the toolbar, or select Actions > Email for
Current. The Send email for the current object form is displayed, linked to the current form.
Specify a template, if appropriate, which fills in the email fields (To/From, Subject, Body, etc.) with
values from the template. Substitution of variables is performed before the content is displayed. Any
attached documents specified in the template are attached to the email. To create a new template, click
Manage Templates.
If your text contains HTML formatting, select Body is in HTML Format.
Optionally, specify notes about the email in the Notes field. These notes are not included in the mailed
message, but you can see the note with the message later if you click View Sent Emails.
In the Attached Documents grid, there might be a list of documents that are included with the template
that you selected. You can perform these actions in the grid:
•
Click View to view a selected document.
•
Click Exclude to exclude the documents that you do not want to send as attachments with this email.
•
Click Attach Documents to open the Attached Documents form, where you can specify a new
document to attach to this email.
Click Send Email. The information is validated, saved in the database, and then sent using the application

