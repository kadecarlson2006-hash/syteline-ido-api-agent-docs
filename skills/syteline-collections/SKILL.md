---
name: syteline-collections
description: "IDO collections and filters - primary/secondary/subcollections, collection attributes, binding, pagination, query form filters"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

sort columns alphabetically, click the Options button on the NDMW toolbar, and use the New Data
Maintenance Wizard Options dialog box.
12 Define the table columns/IDO properties/form components that the wizard is to create. Add a row
to the grid for each column/property/form component.
The grid includes these attributes for each column/property/form:
Grid column/Attribute

Specification/Comments

Name

Required. Specify a name for the new column/property/form component.
Note: You cannot use spaces or special characters in the name.

Property Class

Optional. Specify the property class on which you want to base the
column/property attributes.
If you select a property class, several other column fields are populated
automatically.
If the property class you want does not yet exist, you can click the Add
option and use the Add Property Class dialog box to create it.

Infor Mongoose Application and Form Development | 111

Working with Forms

Grid column/Attribute

Specification/Comments

Data Type

Required. Specify the Mongoose data type for the IDO property.
Assuming you specify this setting before the SQL Data Type, the wizard
automatically selects an appropriate data type for the SQL Data Type
setting, which you can then override if you want.

SQL Data Type

Required. Specify the SQL data type for each record in this column of
the table.
You can specify an existing SQL base data type or a User-Defined
Data Type (UDT). To create and specify a UDT, click the Add option
and use the Add User Defined Type dialog box.
Note: The New Data Maintenance Wizard generates data using nvarchar
data types.

Property Length

Optional, depending on the data type.
Certain data types allow a length to be set. This specification determines
the maximum length that the component's value can have. In the case
of string types, it sets the maximum number of characters that can be
used. In the case of number types, it sets the maximum total number
of digits the number can have to the left or right of any decimal point.

Decimal Precision

Optional. Certain data types allow you to specify how many decimal
places should display to the right of the decimal point.
To specify that scale, specify a number in this field.

Primary Key

To make the column/property a primary key, select this check box.

Required

If the column/property value is required, that is, it cannot be null, select
this check box. If the property is to allow null, clear this check box.

Label String ID

Optional. Specify a translatable string to use for the form component's
caption.
If you specify a string that does not already exist, WinStudio asks
whether you want to create one. If you say no, WinStudio treats this
value as a literal string.
To create a new translatable string, you can click the Add option and
then use the String Properties dialog box to create it.

Default Value

Optional. If the form component is to display a default value, specify
that default value here. You can use keywords, such as the AUTONUMBER().
Note: This setting refers to the IDO default value and not a SQL default
value.

13 To add more columns/properties/components, use the Add Row button on the NDMW toolbar.
14 Optionally, use the move buttons on the right to rearrange the order of your
columns/properties/components.

Infor Mongoose Application and Form Development | 112

Working with Forms
15 Optionally, to include a subcollection as part of the form, click the Add Collection button on the
NDMW toolbar.
This action creates a new "page" on which you can define the subcollection. Most of the fields and
options on this page are the same as for the primary collection. An additional optional field allows
you to assign a Subcollection Name that is different from the basic collection Name.
For additional steps that are required if you specify a subcollection, see Adding a Subcollection in
the New Data Maintenance Wizard on page 114.
16 When you are finished defining the columns/properties/form components for the primary collection
and any subcollections, click Next.
The wizard displays the Finish page.
17 Optionally, to save the form definition as the new form is created, select the Save Form Definition
Template option.
18 Optionally, to have the generated scripts saved to a text file (with a .sql extension, that SQL can
use), click Save Script .
19 Optionally, to save the generated scripts to the Windows system clipboard, click Copy to Clipboard
.
20 To finalize the process, click Finish.
The wizard then processes the specifications and creates the SQL table, IDO properties, and (optionally)
form.

About the New Data Maintenance Wizard toolbar
The first page of the New Data Maintenance Wizard (NDMW) includes a toolbar with buttons to allow
these actions:
Note: To view the button names, hover the pointer over their icons in the toolbar.
Button name

Description / Comments

Open Template

To locate and select the XML file that contains a template you want to use
as the basis for a new table/IDO/form, click this button.
When you select and open the XML template file, any NDMW settings
contained within that template populate the appropriate NDMW fields.

Save Template

To save the current table/IDO/form settings as a template for future use,
click this button, and then use the Window Save File dialog box to save
the settings to an XML file.

Get Database Schema

To load and use the schema of an existing database table as the basis for
a new table/IDO/form, click this button. Then use the Get Database Table
Schema dialog box to select the database table from which you want to
use the schema.
Note: This option clears the Name/Property grid in the NDMW before
populating it with the new data.

Infor Mongoose Application and Form Development | 113

Working with Forms

Button name

Description / Comments

Add Collection

To create a subcollection for your table/IDO/form, click this button.
When this button is clicked, the NDMW adds two pages to the wizard process:
•

•

The first new page allows you to specify the IDO collection to be used
for the subcollection, as well as other settings. Other than the Subcollection Name field, the options and settings on this page are identical
to that used for the primary collection.
The second new page provides options to create the linkage and key
relationships between the primary collection and the subcollection.

See Adding a subcollection in the New Data Maintenance Wizard on page
114.
Add Row

To add a row to the grid for the columns/properties/components, click this
button.

Delete Row

To delete the currently selected row from the columns/properties/components grid, click this button.

Delete All Rows

To delete all rows from the columns/properties/components grid and start
over, click this button.

Options

To add an IDO description, a form description, a form caption, and/or a
specification to have the columns sorted alphabetically, click this button.
See New Data Maintenance Wizard Options dialog box on page 942.

Adding a subcollection using the New Data Maintenance Wizard
When you add a subcollection as part of the New Data Maintenance Wizard process, the wizard
automatically adds the "pages" required for the subcollection. Most of the fields and options on this
page are the same as for the primary collection. An additional optional field allows you to assign a
Subcollection Name that is different from the basic collection Name.
In addition, when you click Next to advance the wizard process, another subcollection-related page
displays. You must use this page to provide the primary key and foreign key linkage information that
the wizard needs to successfully create the form with the subcollection.
1

In the Primary Collection Property field, specify the property from the primary collection to use
as the link property.
The wizard automatically displays the data type and primary key status of the selected property.

2
3

In the Sub Collection field, specify which subcollection is to be used to establish the link.
In the Subcollection Property field, specify the property from the subcollection to use as the link
property.
The wizard automatically display the data type and primary key status of the selected property.

4

Click Add Link.
The wizard displays the Subcollection Linkage, properly formatted.

5

Click Next.

Infor Mongoose Application and Form Development | 114

Working with Forms
At this point, the New Data Maintenance Wizard launches the New Form Wizard prepopulated with
the data it needs for the type of form you selected earlier in this procedure.
After the form creation process has completed, the New Data Maintenance Wizard returns to the Finish
page.

About the Report Builder wizard
The Report Builder wizard is used to create a runnable report-type form and associated criteria filter
form in Runtime Mode. This allows authorized users to generate these reports without the need to use
the Web Designer (in the web client) or Design Mode (in the Windows client).
The Report Builder wizard is a Runtime Builder form. This means that, to use it, you must be authorized
to use Runtime Builder forms.
When run, the Report Builder wizard first prompts you for basic information about the form
names/captions, layout, and source of data. Next, the wizard prompts you for the properties to be used
in the report output. On the third page, the wizard prompts you to define the order in which those
properties are displayed, the captions to be used as labels for those properties, and how the criteria
form should select the data for the report. Finally, the wizard provides the opportunity to optionally
select one or more properties by which to group the data in the report output.
When you click Finish to create the report-type form and the criteria form, the wizard creates the forms
and displays the criteria form. You can now use that form to provide filter criteria and process the report.
If you want, you can also preview the report output before actually processing it, by clicking the Preview
icon at the far right side of the criteria form title bar. To actually print the report, then, click the Print
icon next to the Preview icon.
When you actually process and print the report, you typically do not see the actual report-type form
until the report is actually processed and printed. That is because the report-type form is basically just
a template used to filter the data and generate the actual report.
However, if you want, you can use the Web Designer/Design Mode to further modify or refine your
report-type form (template), as well as the criteria form.

Creating a report form using the Report Builder wizard
Use the Report Builder wizard to create a runnable report-type form, along with a criteria form you
can use to generate the report.
1
2

On the toolbar, click the Runtime Builder icon.
In the Runtime Builder Selection form, select these options:
Category
Select FormBuilder.
Runtime Builder
Select Report.

3
4

Click OK.
On the first page of the Report Builder wizard (Form), make these settings:

Infor Mongoose Application and Form Development | 115

Working with Forms

Enter a name for this form
(Required) Enter the name for your report form, or select the name of an existing report form from
the drop-down list.
This form name is used only in the Web Designer or Design Mode and does not display on the
form itself. You must use standard form naming conventions.
Enter a caption for this report
(Required) Provide the caption that is to display in the title bar of the form.
When you exit the previous field, the wizard automatically populates this field with a proposed
caption based on the form name. You can modify this proposed caption to suit your needs.
Select an IDO to use with this form
(Required) Select the IDO that contains the data to use for your report.
Enter a name for the criteria form
(Required) Enter the name for the criteria form that will be used to filter data and generate the
report.
When you exit the form name field, the wizard automatically populates this field with a proposed
name for the criteria form, based on the form name. You can modify this proposed name to suit
your needs.
This form name is used only in the Web Designer or Design Mode and does not display on the
criteria form itself. You must use standard form naming conventions.
Enter a caption for the criteria form
(Required) Provide the caption that is to display in the title bar of the criteria form.
When you exit the form name field, the wizard automatically populates this field with a proposed
caption for the criteria form, based on the form name. You can modify this proposed caption to
suit your needs.
Set Orientation
(Required) Specify whether the report is to use a Portrait orientation or a Landscape orientation.
Paper Size
(Optional) Select the size of the paper that is to be used to print the report.
If you do not select a paper size, the wizard uses the default paper size from the Report Options
form.
Select Regions
(Optional) Set the regions to include in the generated report. Clear all others.
5
6

Click Next.
On the next page of the wizard (Properties), select the properties to include in the primary collection.
The wizard initially displays the internally used properties of the IDO in the Properties list (on the
left) and other properties in the Primary Collection list (on the right). Depending on your need,
you might want to clear the entire Primary Collection list and then then select only the properties
you actually want to use. Alternatively, simply deselect the properties you do not want to use from
the Primary Collection list.

Infor Mongoose Application and Form Development | 116

Working with Forms
To filter for specific properties, you can use the search field above the list of Properties. The wizard
searches for any values you enter whenever you tab out of the field or click the magnifying glass
icon.
Note: Do not worry on this page about the order of the properties in the Primary Collection list.
That will be done on the next page.
7
8

Click Next.
On the next page of the wizard (Organization), use the up and down arrow icons to set the order
in which properties are to be presented on the form.
Optionally, you can also set these attributes for each property:
Caption
In cases where a translatable string is already assigned to a property, the wizard populates this
field with that value. If no value is pre-populated, enter the caption as you want it to appear in the
criteria form.
Criteria Type
The wizard offers two ways to filter criteria data: using single values or using ranges of values.
• Use the Range option to display Starting and Ending fields that are used to define the starting
and ending values of the data to return and present in the report.
Use these options to return data that are a subset of all values. The Starting field acts as a
>= operator, and the Ending field acts as a <= operator. These operators work with both
numeric and alphabetic values.
•

Use the Single Value option to display a single edit field for that property, in which you can
enter a value to search for and present in the report.
Note: You cannot use wild cards when filtering for data from a Single Value field. The field
must contain the exact value of a record for that property.

Summary Type
The wizard provides a number of options to provide and display aggregate values for data that is
returned for a property. Aggregate values are displayed both for groups (when defined) and for
all records.
The wizard can display these types of aggregate values:
•
•
•
•
•

Summation: Adds the values of all records retrieved for that property. This option works only
with numeric data types.
Minimum: Shows the minimum value returned for that property. If the value is a string or text
value, standard alphanumeric valuation is used.
Maximum: Shows the maximum value returned for that property. If the value is a string or
text value, standard alphanumeric valuation is used.
Average: Displays the mean average of the values retrieved for that property. This option
works only for numeric data types.
Count: Shows a count of the records where the value of the property is non-null/non-blank.
If a property is selected that has a value in every record, this is the same as the record count.
But, if the property column contains nulls/blanks, this can be less than the record count.

9 Click Next.
10 Optionally, select one or more properties from the Select Group Properties list to group on the
report.

Infor Mongoose Application and Form Development | 117

Working with Forms
When a group is selected, the report output is sorted into subsets of data in which records share
a common property value. For example, if you are creating a sales report, and you want to have
the data gouped together according to which salesperson made each sale, you might select a
property named SalesPersonID.
If you select multiple properties on this page, the data is grouped according the order the properties
are arranged on the Organization (previous) page of the wizard. For example, if you selected
CustomerName as a second property group, each salesperson's data would be further grouped
by customers.
11 Click Finish.
The wizard creates the runnable report-type form, the criteria form for the report, and the background
task for processing and printing the report according to system and report printer settings. When these
are done being created, the wizard displays a confirmation message and launches the criteria form.
To run the report, enter the desired filter criteria and then click either the Preview icon or the Print icon.

About Responsive Form Builder forms
The Responsive Form Builder is a set of wizards that you can use to create fully "responsive" forms
of various types, based on predefined templates. Basic options include full-size and widget forms. You
can access Responsive Form Builder wizards only by means of the Runtime Builder.
Note: To use the Responsive Form Builder, you must be authorized to create forms using the Runtime
Builder feature.
"Responsive" forms are forms that can adapt their displays automatically for different types of devices,
by adjusting their layouts and font sizes according to each device's screen size and orientation.
Whether you are creating full-size forms or smaller widget forms, the forms created by this set of wizards
are all created as "FormOnly" types of forms in Mongoose. That is, they typically do not include standard
toolbars or menu bars.
Each of the various types of forms you can create using the Responsive Form Builder is based on
a different template. The wizards themselves help guide you through the selection and creation process.
Note: In most cases, when the wizard finishes, you will want or need to refine the form design layout
and component displays, regardless of what form wizard you use to create your form.
To access this set of wizards, you must click the Runtime Builder icon on the toolbar, and then select
Category = Form Builder and Runtime Builder = Responsive Form.
"FullForm" forms
"FullForm" forms are designed to be full-sized regular forms.
For detailed information about the "FullForm" options, see "FullForm" options on page 1302.
Widget forms
Widget forms are designed to be smaller and to function within an Infor Ming.le™ environment. Widgets
are typically simple, designed with a single, focused purpose in mind.
For detailed information about the Widget form options, see Widget options on page 1325.

Infor Mongoose Application and Form Development | 118

Working with Forms
Non-Data forms
"Non-Data" forms are essentially created as blank forms, to which you can add whatever fields and
other components you might want. These forms are created without a binding to any particular IDO or
other set of data. They are created with only a title bar/form name at the top and the capability for an
optional menu.

Creating a "Widget" with Responsive Form Builder
The Responsive Form BuilderResponsive Form Builder provides a number of templates and layouts
from which to choose, the basic procedure to create a "widget" form with is a set of wizards that you
can use to create simple forms in Runtime Mode, without having to use the Web Designer (in the web
client) or go into Design Mode (in the Windows client). Although Responsive Form Builder is the
same for all Widget options.
Widget forms are designed to be smaller and to function within an Infor Ming.le is a set of wizards that
you™ environment. Widgets are typically simple, designed with a single, focused purpose in mind.
1
2
3
4
5

To open the Runtime Builder Selection form, click the Runtime Builder icon on the toolbar.
From the Category drop-down list, select FormBuilder.
From the Runtime Builder drop-down list, select ResponsiveForm.
Click OK.
On the Form Properties page of the Responsive Form Builder wizard, provide this information:
Enter A Name For This Form
Supply the name of the form to create.
Until you change it, this is the name of the form as it displays in the title bar of the widget form.
Select an IDO to use with this form
From the drop-down list, select the IDO to use as the source of data for the form.

6
7
8

From the Select A Form Type list, select Widget.
Click Next.
On the Layout page, select the template/layout to use for your form.
For detailed information about the templates/layouts in this set, see Widget options on page 1325.

9

Click Next.
The wizard opens the Field Selection page.
The number of properties that can be presented on the form depends on which template/layout
you select, but the procedure to select the properties you want to include is the same:
a

Select a property from the list of Available properties on the left.
Use the Search field to help locate the desired properties.

b
c

Click the right arrow.
Repeat for additional properties.

To place the properties in the order you want, use the up and down arrows.
Note: The order you determine here is the order in which the properties are displayed on the
finished form.

Infor Mongoose Application and Form Development | 119

Working with Forms
10 Click Next.
The wizard displays the Review page.
11 Click Finish.
The wizard generates the new widget and launches it. You can refine the form, if you wish, in the Web
Designer or in Design Mode.

Creating a "FullForm" with Responsive Form Builder
The Responsive Form Builder is a set of wizards that you can use to create simple forms in Runtime
Mode, without having to use the Web Designer (in the web client) or go into Design Mode (in the
Windows client). Although Responsive Form Builder provides a number of templates and layouts
from which to choose, the basic procedure to create a full-sized form with Responsive Form Builder
is the same for all "FullForm" options.
The "FullForm" option creates a form with standard Mongoose-based application size standards.
1
2
3
4
5

To open the Runtime Builder Selection form, click the Runtime Builder icon on the toolbar.
From the Category drop-down list, select FormBuilder.
From the Runtime Builder drop-down list, select ResponsiveForm.
Click OK.
On the Form Properties page of the Responsive Form Builder wizard, provide these values:
Enter A Name For This Form
Supply the name of the form to create.
Select an IDO to use with this form
From the drop-down list, select the IDO to use as the source of data for the form.

6
7
8

From the Select A Form Type list, select FullForm.
Click Next.
On the Layout page, select the template/layout to use for your form.
For detailed information about the templates/layouts in this set, see "FullForm" options on page
1302.

9

Click Next.
The wizard opens the Field Selection page.
The number of properties that can be presented on the form depends on which template/layout
you select, but the procedure to select the properties you want to include is the same:
a

Select a property from the list of Available properties on the left.
You can use the Search field to help locate the desired properties.

b
c

Click the right arrow.
Repeat for additional properties.

To place the properties in the order you want, use the up and down arrows.
Note: The order you determine here is the order in which the properties are displayed on the
finished form.
10 Click Next.

Infor Mongoose Application and Form Development | 120

Working with Forms
The wizard displays the Review page.
Note that, in some cases, you have the option here to position the various fields as you want to
them to display on the finished form.
11 Click Finish.
The wizard generates the new form and launches it. You can refine the form, if you wish, in the Web
Designer or in Design Mode.

Creating a "Non-Data" from with Responsive Form Builder
The Responsive Form Builder is a set of wizards that you can use to create simple forms in Runtime
Mode, without having to use the Web Designer (in the web client) or go into Design Mode (in the
Windows client). "Non-Data" forms are one such type of form.
Although Responsive Form Builder provides a number of templates and layouts from which to choose,
a "Non-Data" form creates only the framework for a form. Beyond that, the form is totally blank, and
you must essentially design and build the form from "scratch".
1 To open the Runtime Builder Selection form, click the Runtime Builder icon on the toolbar.
2 From the Category drop-down list, select FormBuilder.
3 From the Runtime Builder drop-down list, select ResponsiveForm.
4 Click OK.
5 On the Form Properties page of the Responsive Form Builder wizard, provide this information:
Enter A Name For This Form
Supply the name of the form to create.
Until you change it, this is the name of the form as it displays in the title bar of the widget form.
Select an IDO to use with this form
Leave this field blank.
"Non-Data" forms are not bound to any data source, so you cannot use an IDO in the wizard.
6
7

From the Select A Form Type list, select Non-Data.
Click Next.
At this time, there is only on "Non-Data" form layout available. It is automatically selected.

8

Click Next.
On the Field Selection page of the wizard, since there is no associated IDO, no properties are
available.

9 Click Next.
10 On the last page of the wizard, review the summary and then click Finish.

IDO from ION API Wizard
Use the IDO from ION API Wizard to create a Mongoose IDO, using an ION API as the source for the
table and properties of the IDO.

Infor Mongoose Application and Form Development | 121

Working with Forms
Before you can use this wizard, you must first make sure that the default ION API server is set up for
it, and you must designate the wizard as a Runtime Builder form. You must also be authorized to use
Runtime Builder forms.

About the IDO from ION API Wizard
The IDO from ION API Wizard is used to generate IDOs and their properties from an ION API endpoint.
This wizard also generates a custom load method and a standard method to invoke Get, Post, Put,
and Delete actions from the API.
Essentially, the IDO from ION API Wizard works by connecting with an ION API that has swagger
documentation and using it to generate an IDO collection in Mongoose. The wizard uses the swagger
documentation to create the necessary input and output parameters for the IDO. The wizard also parses
the response data from the ION API to generate and populate the rows and columns for the IDO table,
using JSONPath expressions.
After generating the IDO, the wizard checks it in to the application database automatically.

Creating an IDO from an ION API
You can create a Mongoose IDO that uses an ION API to access a source of data. Use the IDO from
ION API Wizard to connect with an ION API, using its swagger documentation to define parameters
and generate an IDO, complete with table and properties.
1 Open the IDO from ION API Wizard form:
Note:
• Before using this wizard, you must have an ION API server set up for it, using the ION API
Servers form.
• You must be authorized to use Runtime Builder forms to access and use this form.
a Launch the Runtime Builder Selection dialog box.
b From the Category drop-down list, select External Data Source (ExtDatasource).
c From the Runtime Builder drop-down list, select IDO from ION API Wizard
(IDOFromIONAPIWizard).
d Click OK.
2

In the IDO from ION API Wizard form, make these settings:
Server ID
From the drop-down list, select the server you want to use to connect with the ION API.
Suite Context
From the drop-down list, select the ION API suite that contains the API you want.
Proxy Path
If the API contains multiple endpoints, specify the proxy path to be used to locate the swagger
documentation.
Note: You can obtain the proxy path from the ION API endpoint itself, using the ION API app. For
instructions on how to do that, consult the ION API app documentation.

Infor Mongoose Application and Form Development | 122

Working with Forms

Select Method to Generate IDO
From the drop-down list, select the method to create when generating the IDO. When applicable,
this is the method that handles and processes the posting of data from the ION API.
3

Click Next.
The wizard proceeds to the Get Input Parameters page, which displays the input parameters for
the specified endpoint.

4

At a minimum, provide values for any required parameters.
Note: Information about parameters, whether they are required or not, are derived from the swagger
documentation provided for the API in the ION API app. You should be aware that, depending on
the endpoint you are using, some parameters required by the API might not indicate as required
by the endpoint; but they are still required for use by the IDO that is generated.
Values for other parameters are optional, depending on what data you want and how you want to
use them. Any values you provide are used to filter the data that is retrieved by the IDO. It is worth
noting that you can change the filter value during the implementation (runtime) of the IDO, to filter
the data from the API.

5

Click Next.
The wizard proceeds to the Get Output Parameters page, which displays the list of properties to
be created as IDO properties that you selected.

6

Select the parameters you want to use as output parameters.
Typically, this is only the parameters required by the endpoint.

7
8

Click Next.
On the Add Post Method and Parameters page, at a minimum, provide values for any parameters
required for Post (Add) operations.
Note:
• If a parameter is of the Type "body", then you must obtain the JSON-formatted body template
from the API endpoint, modify it, and paste it into the Value field.
See Getting body parameter formats for ION API-based IDOs on page 1237.
•

If a selected parameter is required, the Value column cells all turn yellow (not just the cells for
the required parameters), indicating that a value is required for the selected parameters. For
parameters that are not required, the cells all turn white (including the cells for the required
parameters). For this reason, you should make sure you know ahead of time which parameters
are required by the API and which are not.

Values for other parameters are optional, depending on your needs.
9 Click Next.
10 On the Add Put Method and Parameters page, at a minimum, provide values for any parameters
required for Put (Update) operations.
Note: See the notes for Step 8.
11 Click Next.
12 On the Add Delete Method and Parameters page, at a minimum, provide values for any parameters
required for Delete operations.
Note: See the notes for Step 8.

Infor Mongoose Application and Form Development | 123

Working with Forms
13 Click Next.
14 On the Generate IDO (final) page of the wizard, supply this information:
IDO Name
Provide the name to assign to the newly generated IDO in Mongoose
Project Name
From the drop-down list, select the IDO project to which the generated IDO is to belong.
Description
Optionally, provide a description for internal use that can be used to identify the purpose and/or
intendeed use of the new IDO.
15 Click Finish.
The wizard proceeds to generate the IDO, creating the appropriate properties and tables, and then
to check in the newly generated IDO. If successful, the system displays a message to let you know
that the IDO was generated successfully.
16 (Optional, but recommended) Use the IDOs form and related IDO forms to verify that the IDO exists
and has the correct properties and methods.
The IDO can now be used much like any other IDO collection in your application.
Note: When using this IDO collection in a form, although it is no longer required, you can use the
GetIONAPIData custom load method with it.
To do this, in Design Mode or the Web Designer, select the Collections property sheet, and then select
GetIONAPIData as the Custom Load Method attribute value.
Depending on your implementation, you might also be able to use load/save override methods.

REST API Wizard
Use the REST API Wizard to create strongly typed APIs and, at the same time, generate Swagger
documentation. These APIs can then be made available to the Mongoose REST API Version 2 set,
using the Infor ION API app.
Before this wizard can be used, it must be designated as a Runtime Builder form. When setting this
up on the Form Wizard Categories form, select these options:
•
•
•

Category - REST
Wizard - APIGenerator
Form = WSFormWizardRestApi

See Designating a form wizard as a Runtime Builder form on page 100.

Using the REST API Wizard
Note: Before you can use the REST API Wizard, you must first be authorized to use Runtime Builder
forms. Also, if the REST API Wizard is not already designated as a Runtime Builder form, you must
designate it as such before you can use it to create or modify the IDO with Swagger documentation.
1 Open the REST API Wizard form.
Infor Mongoose Application and Form Development | 124

Working with Forms
2

On the first page of the wizard, provide these specifications:
IDO Name
From the drop-down list, select the IDO for which the REST API and the Swagger document are
to be created.
Document Description
Optionally, provide a description that can be used to help understand the purpose of the Swagger
document.
Action
From the drop-down list, select the type of action to be performed by the API.
Operation ID
Provide a name that can be used as a unique identifier for the API. This ID is used to access the
API from the IDO Swagger document and/or in the ION API.
This ID typically includes part of the IDO name and the operation being performed by the API (for
example: GetLanguageIDPropertyInfo).
Operation Description
Optionally, provide a description for the API itself. This is typically a summary of what the API
does or indicates its purpose.

3

Click Next.
What happens next depends on the type of action you are using for your API. For details of what
happens for each type of action, see one of these topics:
• Creating an API for a Get Property Information operation on page 125
• Creating an API for an Invoke Method operation on page 126
• Creating an API for a Load Collection operation on page 126
• Creating an API for an Update Collection operation on page 127
When you click Next for the last time, the wizard displays a summary of both the name of the IDO
Swagger document and the base URL and filename of the REST API to be created. The generated
path is comprised of the Swagger Collection documentation URL, the IDO Swagger document
name, and the Operation ID.

4

Click Finish.

The REST API Wizard saves the information to create the REST API endpoint and the associated
Swagger documentation. You can now use the Object External APIs form to view information about
all the API endpoints created in your system and to see the data for each API. You can also use the
Object External API Endpoints form to further define your API endpoint.

Creating an API for a Get Property Information operation
When creating an API for a Get Property Information operation, no additional information is needed.
After you click Next on the first page of the REST API Wizard, the wizard takes you directly to the
Finish page.
Return to the last step of Using the REST API Wizard on page 124.

Infor Mongoose Application and Form Development | 125

Working with Forms

Creating an API for an Invoke Method operation
After you click Next on the first page of the REST API Wizard for an Invoke Method operation, the
wizard displays a second page.
1

On this second page, provide this information:
IDO Method Name
From the drop-down list, select the IDO method you want the API to invoke.
When you select the method, the wizard automatically populates the Parameters grid with any
parameters expected by the IDO method.
Parameters
Provide the values for any parameters on which you want the IDO method to operate.
Note: Parameters with blank values are not ignored but get passed "as is".

2

Click Next.
Tthe wizard displays a summary of both the name of the IDO Swagger document and the base
URL and filename of the REST API to be created.

3

Return to the last step of Using the REST API Wizard on page 124.

Creating an API for a Load Collection operation
After you click Next on the first page of the REST API Wizard for a Load Collection operation, the
wizard displays a second page. Make these settings:
1

From the list of Available Properties, select the properties to include in the operation.
Optionally, to include all properties in the operation, select the Include All Properties option.

2
3

Optionally, use the up and down buttons to reorder the Properties To Include list to your liking.
Click Next.
The next page offers options to specify filter criteria to limit the Load Collection operation.

4

Optionally, provide one or more of these filter criteria for the operation:
Filter
Specify the filter criteria.
This can be any valid SQL WHERE clause.
Order By
Specify one or more properties by which the collection is to be sorted.
This parameter is a comma-delimited list of properties that specifies the order in which the result
set should be sorted. To have a property sorted in descending order, use the DESC keyword after
the property name.
Record Cap
To limit the number of returns, specify the number of records to return.
This parameter specifies how many records are to be retrieved in each request. These are the
valid values:
• -1: This specifies that the system default record cap (200) is to be used.
• 0: This value specifies that all records are to be retrieved, regardless of any record cap settings.
• Any other positive integer specifies the number of records that are to be retrieved.

Infor Mongoose Application and Form Development | 126

Working with Forms
Note: This setting is subject to any system-wide record cap overrides that might be in effect.
Bookmark
Specify a bookmark ID.
Bookmark IDs serve as a reference when you want to get to the next or previous records in a
collection.
Load Type
This parameter is used for load collection paging. It is used in conjunction with the bookmark
parameter.
To use, specify one of these types:
•
•
•
•

FIRST
NEXT
PREV
LAST

Post Query Command
Specify one or more commands for the system to execute (server-side) for every record returned
by a Load Collection action.
District
When set to True, this parameter specifies that a set of data that represents only distinct
combinations of requested properties is to be returned.
Read-Only
When set to True, this parameter specifies that the return results are to be marked as "Read
Only".
5

Optionally, to include a custom load method in the query, click Include Custom Load Method
Parameters.
This action launches a modal page on which you can select the custom load method and provide
the parameter values, if any, for that method.
When yoou are finished with this page, to return to the previous page, click Done.

6

Click Next.
Tthe wizard displays a summary of both the name of the IDO Swagger document and the base
URL and filename of the REST API to be created.

7

Return to the last step of Using the REST API Wizard on page 124.

Creating an API for an Update Collection operation
After you click Next on the first page of the REST API Wizard for an Update Collection operation, the
wizard displays a second page.
1

To define an update action for an item in the IDO, click Add.
The wizard adds a record to the grid.

2

To instruct the system to refresh the collection after the operation has completed, select the Refresh
After Update option.

Infor Mongoose Application and Form Development | 127

Working with Forms
3

From the Action drop-down list, select the operation you want the API to perform.
These are the available options:
• Insert - Adds a specified record to the collection.
• Update - Makes a specified change to a designated record in the collection.
• Delete - Removes the designated record from the collection.
If you select the Insert option, the wizard automatically creates the Item ID, based on the IDO. If
you select the Update option or the Delete option, you must supply the Item ID.

4

If you selected Update or Delete, provide the Item ID, if required.
This is the typical format for the Item ID: PBT=tableName tableAlias.DT=timestamp
aa.ID=rowPointer
where:
•
•
•
•

tableName is the name of the IDO table.
tableAlias is the table alias, if any.
timestamp is
rowPointer is the pointer for the row for the specified item.

5

In the Update Locking field, specify the update locking mechanism for the item.
These are the supported options:
• Row (default)
• Property

6

To select properties to include and, optionally, to define for each item, click Modify Properties.
Clicking this link launches the Update Item Properties page in which you can select and define
the properties you want.
a To add a property to include, click Add.
b From the Property drop-down list, select the property on which to perform the action.
c In the grid, specify these values for each property you want to update:
Property
From the drop-down list, select the property for which you want to perform the operation.
Value
Provide the value of the property.
Modified
When selected, this option indicates that the property has been modified.
Null
When selected, this option indicates that the property has a null (empty) value.
d
e

7

Optionally, rearrange the list of properties using the up and down arrows to the right of the
grid.
When you are finished selecting, defining, and ordering properties, to return to the Update
Items page, click Done.

Click Next.
Tthe wizard displays a summary of both the name of the IDO Swagger document and the base
URL and filename of the REST API to be created.

Infor Mongoose Application and Form Development | 128

Working with Forms
8

Return to the last step of Using the REST API Wizard on page 124.

XML to IDO Wizard
Use the XML to IDO Wizard to create SQL tables, Intelligent Data Objects (IDOs), and forms, based
on XML documents. The XML documents are typically, though not necessarily, Business Object
Documents (BODs).
Before you can use this wizard, you must designate the wizard as a Runtime Builder form. You must
also be authorized to use Runtime Builder forms.

About the XML to IDO Wizard
Use the XML to IDO Wizard to create SQL tables, Intelligent Data Objects (IDOs), and forms, based
on XML documents. The XML documents are typically, though not necessarily, Business Object
Documents (BODs). The primary intended use of this wizard is to integrate BODs with the Mongoose
framework, but any XML files can be used as templates.
The XML to IDO Wizard provides functionality to select the elements or attributes of an XML document,
and create tables and IDOs based on the schema of these sections. The schema information is gathered
from XML XSD references, if they are available. Using this wizard, you can indicate relationships
between the tables to be created; and so, any child tables include the appropriate primary and foreign
key references.
You can also use the wizard to create forms to be associated with these tables and IDOs.
Optionally, for integration, replication document metadata can be created, according to the selected
collections and properties in the XML file.
When an XML document is selected for conversion, the wizard automatically analyzes the XML code
and parses it. If any XSD schema information is included in the XML file, and the schema locations
are accurate and accessible, then the schema information (data types) can be gathered and used.
Once the XML file has been parsed and displayed, you can then interact with the code. You can:
•

Search for key words or phrases in the XML code by specifying a Search term and clicking the
Search button.
Any matches that are found are highlighted with red text and a gray background.

•
•

Expand and collapse all nodes by clicking the Expand or collapse all nodes button. Expand and
collapse individual nodes in the code by double-clicking the icons at the beginning of each line.
Undock or dock the XML parser window by clicking the Toggle docking button. This can make
viewing and working with the XML code easier.

Once you are done selecting the data you want from the XML code, the wizard proceeds to process
your selections, create the replication document metadata, and create the tables, IDOs, and optional
forms.
You can also save your project as a template. All the XPATH/IDO property mappings and links are
saved in an XML file.

Infor Mongoose Application and Form Development | 129

Working with Forms
In the XML to IDO Wizard, you can define two types of data collections to be used in creating the tables
and IDOs: scalar collections and table collections.
Scalar collections
A scalar collection allows you to select only single values from the XML file. Ultimately, whatever is
selected must end up in a SQL table and an IDO, so with scalar values, you must first add one or more
scalar collections to contain any scalar variables.
Once you create a scalar collection, you can add elements to the collection, but each element can
contain only a single selected value. For this reason, you can only select elements from the XML code
that have values. This also means that, if you have multiple sibling elements in the XML code, only the
element that you select is defined.
Later in the process, when the tables and IDOs are created, the scalar collection becomes a table, and
each element in the collection becomes a column within the table, a property within the IDO, and
potentially, a component on the form.
If you require a particular element among siblings, you can add the xpath syntax to indicate the
sequence; for example //Addresses/AddressLine[2], which would select the second sibling
element. You can also indicate which sibling by referencing an attribute name and value, if any exist.
You can add more than one scalar collection if you want. Each collection is subsequently mapped to
a particular IDO. So, if you need to insert various values from the XML file into different IDOs, you must
create a collection corresponding to each of these IDOs.
Table collections
A table collection allows you to select multiple values for conversion from the XML file. You can specify
selected elements in the XML as either table elements or property elements. Table elements are later
converted to tables and then IDOs. Property elements are converted to table columns, IDO properties,
and potentially, bound to form components.
Unlike the scalar collection, a table collection returns multiple elements that match. For example, if you
select a SalesOrderLine element, and that element has ten sibling elements (that is, ten sibling
SalesOrderLine elements), then the selected xpath returns all of them. Consequently, a table collection
returns a table of data of varying length.
When working with table collections, you also have the ability to create primary and subcollections.
Only one primary collection can be assigned, but you can add as many subcollections as you need.
Keep in mind also that each collection maps to one IDO; so, if you need to distribute data across
multiple IDOs, you must create a collection corresponding to each IDO.
Launching the XML to IDO Wizard
The method used to launch the XML to IDO Wizard depends on the environment in which you are
working:
•
•

In the Web Designer: Click the New Form Definition icon on the toolbar. Then select the New Data
category and the XML to IDO (XmlToIdo) wizard.
In Design Mode of the Windows client: Click the XML to IDO Wizard icon on the toolbar.

Infor Mongoose Application and Form Development | 130

Working with Forms
•

If the wizard has been designated as a Runtime Builder form on your system, and you are authorized
to use Runtime Builder forms: Launch the Runtime Builder Selection form, and then select the
New Data category and the XML to IDO (XmlToIdo) wizard.

Creating tables and IDOs from XML documents
Use the XML to IDO Wizard to create SQL tables and Intelligent Data Objects (IDOs) based on data
found in XML documents.
1
2

In Design Mode, click the XML to IDO Wizard button on the Design Mode toolbar.
In the XML to IDO Wizard, click the Open XML File button next to the XML File field.
Alternatively, if you want to work on an existing project template XML file. click Open Template.

3

In the Windows Open dialog box, navigate to and open the XML file on which you want to base
your new tables/IDOs/forms.
The wizard automatically parses the XML code in the document. As it does so, it attempts to find
and capture any schema information that is referenced by the XML file. If no schema information
is found, then all elements are defaulted to a string data type with a length of 30.

4

Create one or more collections to be mapped to SQL tables and IDOs.
This is done by selecting from the XML code what nodes and elements to use in constructing your
new tables/IDOs/forms. On this first page of the wizard, you can create either scalar collections or
table collections, or both. For more information about these collection types, see About the XML
to IDO Wizard on page 129.
•
•

To create a scalar collection, see Creating a scalar collection on page 133.
To create a table collection, see Creating a table collection on page 132.

5

Optionally, if you have multiple collections, designate one collection to be considered the primary
collection.
To do this, select the check box to the right of the collection row for the collection that is to be
considered the primary collection. If you designate one collection as the primary collection, all other
collections are then created as subcollections.

6
7

Verify that you have at least one primary key identified for each collection.
Click Next.
If you have multiple collections with one collection designated as the primary collection, the next
page of the wizard is used to establish the linkage between the tables/IDOs. If this is the case,
proceed to the next step.
If there is no collection is designated as the primary collection, skip the next step.

8

Specify the Primary Collection Property and the Subcollection, and then click Add Link.
Repeat as required for additional subcollections.

9 If you want to save as a template, click Save Template.
10 Click Next.
The next page of the wizard displays only if there are multiple table collections. This page provides
options to define the settings for the primary collection, if there is one collection designated as the
primary collection. Otherwise, this page provides those options for the first collection defined in
the list on the previous page.
Infor Mongoose Application and Form Development | 131

Working with Forms
At this point, even though the name of the wizard does not change, the options and following pages
are virtually the same as for the New Data Maintenance Wizard on page 941 (NDMW).
11 To continue the process, use the procedure provided in the topic, Using the New Data Maintenance
Wizard on page 110, starting with Step 3.
The exception to the regular NDMW process is that, after you have specified all options related to
the collections and their properties, the wizard presents you with a page on which you can specify
whether to create the metadata for an inbound replication documen or an AES event handler from
your specifications. If you select to create an inbound replication document, you can also create
the metadata for an outbount replication document. If you choose to create an AES event handler
instead, you cannot create an outbound replication document.
To have the wizard create the inbound or outbound replication document metadata or AES event
handler:
a

Select Create Replication Document Inbound Metadata or Create AES Event Handler.

b

Note: The BOD noun-verb combination cannot exist already in the Replication Document
Incoming Cross-References form. It it does, the wizard displays a warning, and you cannot
create the replication document inbound metadata until you delete the combination on the
Replication Document Incoming Cross-References form.
The Document Name is pre-populated from previous screens. You can modify this field if
necessary..
Optionally, select Create Replication Document Outbound Metadata. If you have selected
Create AES Event Handler this option is disabled.
Select a site from the Target Site list.

c
d

The BOD Noun and BOD Verb should already be specified on this page.
After the wizard is finished processing, make sure you check all opened form to verify that what you
expected to be created is in fact what the wizard did create.

Creating a table collection
To create a table collection in the XML to IDO Wizard, you must have the wizard open to the first page
and an XML document already selected and parsed.
1
2
3

In the Collection Type group box, select the Table option.
In the Table Selection Type group box, select the Table Element option.
In the XML parser window (where the XML code is displayed), locate and double-click the XML
node that you want to use as the basis for creating the table and IDO.
This must be an element that contains the children elements you need as properties.
The wizard displays the specified XML node in the edit window, indicating visually that it is a
collection element. Notice that the field to the right of the name field displays the xpath for the
specified node.

4
5
6

Optionally, change the name for the collection in the name field.
Optionally, to indicate that this is to be the primary collection, select the check box at the end of
the line.
In the Table Selection Type group box, select the Property Element option.

Infor Mongoose Application and Form Development | 132

Working with Forms
7

In the XML parser window, locate and double-click each XML node that you want to use as the
basis for creating the table columns and IDO properties.
You can select multiple XML nodes for this.

8

Designate at least one property element as a primary key, by clicking the key icon to the right of
that element's line.

When you are finished creating the table collection specifications, either:
•
•

Create any additional collections you might want.
To continue with the wizard, click Next.

Creating a scalar collection
To create a scalar collection in the XML to IDO Wizard, you must have the wizard open to the first page
and an XML document already selected and parsed.
1
2

In the Collection Type group box, select the Scalars option.
In the wizard toolbar, click the Add Scalar Collection button.
The wizard adds a Scalar line in the edit field and assigns it a default name (for example,
ScalarCollection1).

3

In the XML parser window (where the XML code is displayed), locate and double-click the XML
node that you want to use as the basis for creating the scalar property.
This must be an element that contains a value. In other words, it cannot be a parent node or
element.
The wizard displays the specified XML element in the edit window. Notice that the field to the right
of the name field displays the xpath for the specified element. You can edit this xpath by
double-clicking in the field.
The added property contains any schema information, if it is available. If none is available, the
default data type chosen is a String value (nvarchar with 30 characters length) in the SQL database.

4

Optionally, to specify a scalar property element as a key field, click the key icon to the right of that
property element.
This defines the property as a primary key in SQL and is considered a required property in the
IDO.

When you are finished creating the scalar collection specifications, either:
•
•

Create and additional collections you might want.
To continue with the wizard, click Next.

Infor Mongoose Application and Form Development | 133

Working with Forms

Master and Derived Forms
About devices and derived forms
Windows client allows you to define different devices from which a Windows client form can be accessed.
The properties of each device are defined on the Edit Device Types form. Device 0, also referred to
as the default device, represents the application's standard desktop PC interface, and can be neither
edited nor deleted. Each additional device type represents an alternate interface (for example, a mobile
phone).
Versions of forms that are accessible on a device are called derived forms, and are based on a single
master form. Although the master form is typically the version that is accessed within your applications's
standard PC interface, it is possible for a master form to be a device version.

Defining device types
To define which device types you can design derived forms for:
1
2
3
4
5
6
7

Open the Edit Device Types dialog box. Initially, only a default entry exists.
To begin defining a device, click Add.
Specify the name to be associated with the device.
Optionally, specify the device's screen dimensions, measured in pixels.
In the User Agent field, specify which browsers are compatible with the device, separated by the
pipe symbol ( | ).
Repeat steps 2 through 5 until all devices are defined.
Click OK.

Designing derived forms
Derived forms are copies of master forms, and are meant to be accessed on alternate devices (for
example, mobile phones and tablet PCs). Because of the differences in devices, components on a
derived form must often be rearranged or removed.
1
2
3
4

On the Edit Device Types dialog box, verify that the intended device has been defined.
With the master form open and in Design Mode, select Form > Definition > Form Device Type.
In the Editing Versions for Form dialog box, select a checked row to edit the derived form for
that device, or select an unchecked row to create a derived form for that device.
To close this dialog box and open the derived form, click OK.

Note: This dialog box can be accessed in Runtime Mode. However, in this case, only existing derived
forms can be selected. To create a new derived form, you must be in Design Mode.

Infor Mongoose Application and Form Development | 134

Working with Forms

Managing master and derived forms
In general, the master version of a form is the version that is accessed within the standard PC interface
and is designated as the default device on the Edit Device Types form. Derived forms are alternate
versions of the master form that can be accessed on other devices or simply in other formats.
Sharing components
The master form is unique in that its components can be shared with its derived versions. Derived
forms are copies of master forms that are typically meant to be accessed on alternate devices (for
example, mobile phones and tablet PCs). Because of the display size differences in devices, components
on a derived form must often be rearranged, or removed. Removing the shared relationship between
master and derived forms creates a copy of the component, which allows you to customize that
component on the derived form. This is done using the Unsharing Component dialog box.
Deleting and Copying Master Forms
When you attempt to delete a form, if it has any derived forms associated with it, you are prompted
whether to delete the master and all derived forms, or only a derived form.
When you attempt to copy a form, an option displays that allows you to copy all derived forms as well.
If you do not select this check box, a drop-down list displays, allowing you to specify which derived
form to copy from, and which to copy to. The form you copy to becomes the new master form.
Previewing Derived Forms
You can preview any derived forms using a browser preview option. This helps when developing a
derived form to specific sizes and so on.

Sharing components between master and derived forms
1
2

3

With the master or derived form open and in Design Mode, select the component you want to
share.
In the Shared property field, perform one of these actions:
• To open the Unsharing Component dialog box and automatically deselect all derived devices,
select False.
• To open the Unsharing Component dialog box and automatically select all derived devices,
select True.
• To open the Unsharing Component dialog box and selectively choose which components
are shared and not shared, click the ellipsis (...) button.
Click OK.

Note: If the component is currently shared, you can select the devices where the component should
remain. This component is then no longer shared, and distinct versions exist for each form selected.
If the component is currently not shared, selecting Shared replaces the same component on all other
versions of this form with the current device's component.

Infor Mongoose Application and Form Development | 135

Working with Forms

Specifying a device type to use for the master form
If you are designing a form that will not normally be accessed by means of a standard PC interface,
then you should consider choosing a different device version to be used for the master form.
1
2
3
4
5

In the Edit Device Types dialog box, ensure that the intended device type has been defined.
Go into Design Mode.
Select Form > Definition > New.
In the Specify Form Name and Type wizard, enter or select the form as you normally would, but
in the Device field, select the device that is to be used for the master version.
Continue through the Specify Form Name and Type wizard.

Extended forms
About creating new forms by extension
To keep the basic functionality and design of a form but customize it for your own needs, an effective
option can be to create a new form based on the existing version. Windows client provides a means
to do that, using the New Form Wizard.
When you select the option to Extend an existing form, the New Form Wizard allows you to create
a copy of the original form, which you can then modify to your own needs.
The big advantage to creating a form with this option is that if/when changes are made to the original
(parent) form, they automatically cascade to your extended form. This allows you to have a customized
version of the form while at the same time keeping up with the latest changes to the original form on
a selective basis.
Note: This option creates a totally new form and leaves the original form unchanged.
The new form starts as an exact duplicate of the original form, but with all the components locked. The
Form properties sheet indicates that it is an extension of an existing form with the Base Form Name
property.
By default, all components, event handlers, variables, and scripts are inherited from the base form.
These objects are all read-only by default, but you can unlock components and modify them as required.
To do this for a selected component, set the From Base Form attribute in the Component property
sheet to False, and then make your changes as with any other component.
You can also add new components and functionality to the form, according to your needs.
Any time that you want to see which objects on the extended form are inherited from the original (base)
form and which are unique to your extended form or modified in your extended form, you can view this
information in the Extended Form Details dialog box.
When a derived form is initially created, it shares the same collections with the base form. If you make
any changes to a collection in the derived form, all collections from the base form are unshared. If you

Infor Mongoose Application and Form Development | 136

Working with Forms
make changes on the collections of the base form, you must make these changes on the derived form
as well.

Creating a form by extension
Note: This procedure applies to forms being created in Design Mode in Windows client. For the
procedure to create a form by extension in the Web Designer, see Creating a form by extension on
page 34 (in the Web Designer).
To create a new form by extending an existing form in Windows client:
1
2
3

In Design Mode, launch the New Form Wizard.
In the first page of the wizard, select the Extend an existing form option, and then click Next.
In the next page, specify:
• The name to assign to the new form
• The form on which the new form is to be based (Base Form)
• Optionally, a description of the new form
Windows client automatically provides a base description that you can change.
•

4
5

Optionally, set the new form to load when a request to open the base form is received (Replace
Base Form)

Click Next.
Click Finish.
Note: Unlike other form creation options, this type of form does not allow you to save a template.

Windows client then creates a copy of the base form, gives it the new name, and opens it in Design
Mode.

About pop-up forms
You can design your form as a floating pop-up window. In a pop-up form, form open, explorer, and
Multiple Document Interface (MDI) options are disabled. All windows and tabs behind a pop-up window
are disabled. All toolbar and menu options are available in tabbed and MDI format.
Any form launched from a modal form is also launched as a modal form, regardless of the event used
to launch the form. Forms opened as modal forms are not added to the MRU list.
To set up a form as a pop-up form, select Popup Windows in the Specify Form Run Options dialog
box.
To design a pop-up form, design a form as you normally do, with the dimensions that you want the
modal form to be, in both MDI and tabbed form layout.
To display a toolbar in FormOnly mode, set a form-level variable DisplayMobileToolbar. This variable
can have the values none, top, bottom, or both. If the variable is set to any value other than none,
the toolbar is displayed at the top of the form.

Infor Mongoose Application and Form Development | 137

Working with Forms
Standard form buttons are shown only if standard operations on the form are enabled (specifically
Save, Filter, New, and Delete).

Portal-type shell forms
About creating a portal-type form
You can employ a web user control to create a "portal"-type shell form that can display multiple subforms.
This functionality can be used to create "dashboard"-type interfaces.
The size, and optionally, the location of any subforms are controlled by means of a specially designated
form variable. These subforms are initially laid out within a grid in the the portal form user control, but
you can drag them to other locations as desired.
The subforms hosted by this type of form can be set up to exchange information and data between
them.
The process to create a portal-type form consists of three interdependent procedures. You must:
•

Create the shell form that is used to display the subforms.
See Creating a portal-type shell form on page 138.

•

Create the persistent form variable that is used define the contents and layout of the shell form.
See Creating the form variable for a portal-type form on page 141.

•

Set up the communications that are to take place between the subforms.
See Setting up communications between subforms on page 141.

Creating a portal-type shell form
To create a shell form that you can use as a portal to host subforms:
1

In Design Mode, use the New Form Wizard to create a form of the type Build From Scratch.
Note: You can use other form types if you want, but we recommend using the Build From
Scratch option, because of the unique restrictions on this type of form. For more information, see
About creating a portal-type shell form on page 138.

2
3

When the New Form Wizard has created the form, in the Toolbox, select the User Control
component and draw the outline for the user control on the form.
In the Edit User Control References dialog box, verify that:
• TheUse Web Control option is selected.
• The Assembly Name field is blank.
• The Class Override field is blank.
• The Web User Control Asset option is selected.

Infor Mongoose Application and Form Development | 138

Working with Forms
4

From the Control URL drop-down list, select mongoose/controls/portal.html.

5

Click OK.

The appearance of the User Control component does not change, but you now have a portal-type shell
in which you can display subforms.
To populate the contents of the User Control component, you must create a persistent form variable.

About the JSON code for a portal-type form
The value of the persistent variable PortalSetup that is used to control the contents and layout of a
portal-type shell form must be in JSON code. Here is a representative example of this type of code:
"colWidth":"170",
"rowHeight":"130",
"gutter":"10",
"onLayoutChanged":"OnLayout",
"forms":[
{
"form":"FormA",
"width":"260px",
"height":"120px"
},
{
"form":"FormB"
},
{
"form":"FormC",
"width":"220px",
"height":"420px",
"top":"50px",
"left":"1px"
}
]
}

This table lists and describes the variable properties you can use to create the JSON code:
Variable property

Description / Comments

colWidth

This property sets the width of the layout columns, in pixels.
Subforms are aligned horizontally to this in the grid.
If not specified, the default value of this property is 170 pixels.

rowHeight

This property sets the height of the layout rows, in pixels.
Subforms are aligned vertically to this in the grid.
If not specified, the default value of this property is 130 pixels.

Infor Mongoose Application and Form Development | 139

Working with Forms

Variable property

Description / Comments

gutter

This property sets the minimum space allowed between subforms, in pixels.
This property applies to both vertical and horizontal spacing.
If not specified, the default value of this property is 10 pixels.

onLayoutChanged

This property is the name of a form script method that takes a single parameter (string): OnLayout
This property is invoked whenever forms are moved around in the user
control and the order is changed.

id
name

These properties are used only when the onLayoutChanged method is invoked.
This is because the form script is passed a parameter that contains a JSON
string indicating the ID and name of each form in order, using this format:
[{"id":"bob","name":"PanelTest1"},
{"id":"sue","name":"PanelTest2"},
{"id":"joe","name":"PanelTest1"}]

backColor

This property sets the background color for the user control component.
Use RGB color settings in hexadecimal format; for example, #FFFFFF.

forms

This section of the code specifies what forms are to be displayed and in
what order.
Note that all forms to be displayed are listed inside a set of square brackets
following this key word. Inidividual form designations are enclosed with
curly braces.

form

This property designates a form to be displayed.
You must use the actual form name, and not the form caption.

width
height

These properties designate the size of the form as it is to be displayed inside the user control.
If not specified, the size is taken from the form definition.

top
left

These properties specify the initial placement of the subform within the
user control.
These settings are normally unnecessary. When not used, forms
flowwithin the user control container in the specified order. When these
settings are used, the normal order and other layout anomalies can occur.

Notes
As with most programming languages, pay special attention to the use of curly braces and other
punctuation marks in the JSON code.
It is not necessary to declare the coding language in this case, because the user control URL is expecting
it.

Infor Mongoose Application and Form Development | 140

Working with Forms

Setting up communications between subforms
You can set up a portal-type form so as to allow the subforms displayed to communicate with one
another and exchange information/data. Do this using the Send Web Container Message form event
response type. This process involves two forms, one as the "sending form" and the other as the
"receiving form". Each form must be separately set up.

Creating the form variable for a portal-type form
The form variable used for the contents of a portal-type shell form must be a persistent form variable
named PortalSetup. This variable controls the contents (subforms that display) and layout of the
user control.
To create the form variable for a portal-type form:
1
2
3

In Design Mode, from the Edit menu, select Variable.
In the Variables dialog box, click New.
In the Variable Properties dialog box:
• For the Name, specify PortalSetup.
• Verify that the Persistent field is set to True.
• Verify that the Global field is set to False.

4

In the Value field, enter the JSON code for the portal layout and subforms.
For more information about the JSON parameters, see About the JSON code for a portal-type form
on page 139.

5
6

Click OK, then click Done.
Save your work and test it in both a web client and a smart client.

Editing Forms
Adding an IDO collection to a form
To add a collection to a form:
1
2
3
4
5
6

With a form open in Design Mode, select the Form property sheet.
Click the Collections tab.
In the hierarchical tree at the top of the property sheet, click the Collections node.
In the toolbar above the tree, click the New icon.
In the property grid, specify an IDO in the Collection property.
In the property grid, specify a name in the Collection Name property.

Infor Mongoose Application and Form Development | 141

Working with Forms
7
8

Set other collection properties as needed.
Save.

Adding a user-defined data type
Note: This dialog box is available only from the New Data Maintenance Wizard.
To add a user-defined data type (UDT):
1
2
3
4

5

In the New Data Maintenance Wizard, specify the name for your UDT in the SQL Data Type field
for a property attribute.
When Windows client prompts you to create the UDT, respond Yes.
Windows client opens the Add User-Defined Type dialog box with the Name field already filled
in.
In the Add User-Defined Type dialog box, specify these values:
Field

Description/Comments

Schema

Currently, only dbo is supported.

System Data Type

Use the drop-down list to specify what data type the new UDT is to be.

Length

Optional. On data types that require or permit a length specification,
specify the length.

Decimal Precision

Optional. Some data types allow you to specify how many decimal
places should be displayed. Specify that precision level here.

Scale

Optional. Some data types allow you to specify the maximum total
number of digits the property value can have. Specify that number here.

Click OK.

Copying forms or templates
You can create a new form or a new form template by copying an existing one.
When you copy a form or form template, Windows client makes an exact copy, which you must then
rename and modify as required to create your new form.
In addition to making exact copies of forms and their templates, Windows client also provides an option
to create a copy of a form that inherits from the original form and still allows you to extend and customize
it according to your needs.

Infor Mongoose Application and Form Development | 142

Working with Forms

Copying a form
To make an exact copy of a form that you can use as the basis of a new one:
1
2
3
4

In Design Mode, from the Form menu, select Definition > Copy.
In the Source Form field, select the form that you want to make a copy of.
In the Target Form field, specify the name for the new form.
Optionally, to copy the source form's template as well as its definition, select Copy Template Also.
Note: The next two steps are applicable only if you have multiple device type versions of a form.

5

Optionally, specify whether you want to copy all device type versions of the form, as well as the
master form:

•
•
•
•

To create copies of all device type versions, select Copy All Device Types.
To create only a specific device type version, clear the Copy All Device Types option.
If you cleared the Copy All Device Types option:
In the Source Device field, specify which device type version is to be used for the copy of the
form.
In the Target Master field, specify which device type version is to be used as the master version
of the form.
Click OK.

•
•

Deleting forms
The phrase "deleting a form" can refer to different outcomes in a Mongoose-based application:
•

You can delete the reference to a form from an Explorer folder.
In this case, you remove the form from the folder, but you do not remove the form from other folders
or from your system.

•

You can delete the form itself from the application altogether.
In this case, the form ceases to exist at all in the application.

Note: If you are customizing an application and have Basic, Full User, or Site Developer editing
permissions, be aware that the version of a form you display (and delete) is determined by your user
ID and by the hierarchy of user-group-site versions that exist on your system. Your ability to customize
a form is determined by your editing scope.

Regenerating a form
Some editing changes are reflected immediately. Before you can see other editing changes, you must
regenerate the form. Regenerating a form saves your changes, closes the form, and reopens it in
Design Mode.
To regenerate a form, in Design Mode, perform one of these actions:

Infor Mongoose Application and Form Development | 143

Working with Forms
•
•

On the toolbar, click the Regenerate Form icon.
On the Edit menu, click Regenerate Form.

Modifying/Customizing reports
Modifying or customizing reports, in many cases, is much the same as modifying or customizing any
other form. It depends, however, on what type of report you want to modify/customize and whether
you want to modify the report object or the report criteria form.
Modifying the report object
As discussed in other, related topics, the type of report object you might need to modify depends on
what type of report you are working with:
•

The report object for a report based on a Report (template) type form is the report-template form
itself. Making modifications to this type of report is as straightforward as modifying any other
Mongoose form. All you need to do is open the form in Design Mode, make the changes you want,
and save the form.
When modifying this type of report object, we recommend that you make a copy of the original
report-template form and modify that.

•
•

The report object for a DataView report is a predefined DataView. Making modifications to this type
of report is the same as modifying any other predefined DataView.
The report object for an SSRS report is the report definition file.T This is by far the most challenging
type of report to modify, as the report definition file involves so many parts and pieces. This might
involve having to modify the stored procedure, an IDO or a method used by the IDO, or the
background task used by the report.

Customizing a report criteria form
Generally, a report criteria form is the easiest part of a report to customize. This is because it typically
involves just adding new criteria by which to filter the report output or to removew existing criteria.

Using Form Templates
Creating, editing, and using form templates
You can create a template from a form and use it to build other forms of a similar type. Form templates
are stored in the templates database and are unaffected by user, group, or site customizations.
Each form template must have a unique name.

Infor Mongoose Application and Form Development | 144

Working with Forms
Creating a form template
Form templates are normally created during the form creation process when using either the New
Form Wizard or the New Data Maintenance Wizard. The last page of each of these tools offers the
option to create a form template. Simply select that opition before you click Finish.
You can also create a form template from an existing form, by opening the existing form in Design
Mode. Then from the Edit menu, you would select Create Form Template From This Form.
Using a form template
Once the form template has been created, you can use it to create similar forms. You must be in Design
Mode, and then start the New Form Wizard. When the New Form Wizard displays the list of Form
Templates, select the existing template on which to base the new form.
Editing a Form Template
There are two ways to edit a form template.
•
•

Create a new template and save it as the one to be changed.
Make changes to the IDO properties that are included on a form template.

Copying a form template
To make an exact copy of a form template that you can use as the basis for a new one:
1
2
3
4

In Design Mode, from the Form menu, select Definition > Copy Template.
In the Source Template field, select the form template that you want to make a copy of.
In the New Template field, specify the name for the new form.
Click OK.

Adjusting Size, Position, and Display Elements
Including or removing scroll bars
In forms with more than one pane (such as a multiview form), you can include or remove horizontal or
vertical scroll bars in either pane.

Infor Mongoose Application and Form Development | 145

Working with Forms

About splitter bars
Forms with more than one pane (such as multiview forms), have a splitter bar between the panes
(either vertical or horizontal).
Changing the splitter bar position in Runtime Mode
All users, regardless of their editing permissions, can move splitter bars by dragging them to the desired
positions. If the process default Allow saving form runtime changes is not set to zero (0), then these
changes are saved at the user level and persist for that user.
If the user has selected the Prompt to Save Form Splitter Changes in User Preferences, then the
user is prompted whether to save those changes when the form is closed. If the user has not selected
the Prompt to Save Form Splitter Changes in User Preferences, then the changes are saved
automatically without the user being prompted.
Setting the default splitter bar position
Form developers can set the default splitter bar position for a splitter bar only in Design Mode. This is
done on the Form property sheet, using the Layout property options. In addition to setting the position
of the splitter bar, form developers can specify:
•
•
•

Whether there is to be a splitter bar or not
Whether the splitter bar is to be veritical or horizontal
Whether each pane created by splitter bars displays scroll bars or not

Setting or changing the form size
To set the default height and width of a form, you must have the form open in Design Mode. The actual
displayed size depends on screen resolution and on the base font (see Height on page 1106 and Width
on page 1149).
You can adjust the size of a form by performing either of these actions:
•
•

Click and drag the form window borders to the new size.
Select the Form property sheet and then set the Height and Width properties.

About character units
In Windows client Design Mode, nearly all size and location measurements are calculated in terms of
what we call Mongoose "character units." This can be confusing to developers used to calculating sizes
and locations of objects in terms of pixels, ems, or other, more absolute, units of measure.
Mongoose character units are calculated in terms relative to the size of the base font designated for
the Mongoose-based application, similar to percent measurement designations. These facts - that
character units are relative measurements, and that their reference point is the base font size - are the

Infor Mongoose Application and Form Development | 146

Working with Forms
two most helpful things to keep in mind when trying to size or position components or other aspects of
Mongoose forms.
How are character units calculated?
To calculate the size of the character unit, we:
•

•

Measure the combined width of eight different alpha characters (ABCMabcm), using the default
font and font size that is applied to the form. We calculate the average width of these eight characters
as one character unit.
Multiply the height of the characters by 1.2. This allows for characters that extend below the baseline.

About pixel units
Pixel unit is a standard unit widely used across most platforms specially on web. It is more absolute,
it remains the same, and does not scale, regardless of browser window and size of the screen.

About setting form and component background colors
The primary settings for both forms and form components come from the system default settings and
whatever theme is applied. Background "colors" can be either:
•
•

A single color, with or without transparency
A pair of colors used for a linear gradient, each color with its own RGB and transparency settings

Windows client provides a couple options to override the default background colors for forms and form
components. To set a background color definition for a form or component, you can:
•

Use the Edit Color form to create and/or apply a background color definition object.
The advantage of this approach is that you can reuse color definition objects in multiple forms
and/or components. This can contribute greatly to creating a consistent look-and-feel across the
forms and components of your applications.

•

Use the property sheet for the form or component to create a color definition for just that form or
component.

Setting the background color with the Edit Color form
To set the background color for a form or component, using the Edit Color form, use the procedure
found in the topic "Applying a Background Color definition object to a form or component on page 1208."
Setting the background color with the Form/Component property sheet
To set the background color using the Form/Component property sheet, use one of these procedures:
•
•

Setting a solid color without transparency on page 1256
Setting a single color with transparency on page 1256

Infor Mongoose Application and Form Development | 147

Working with Forms
•

Setting a linear gradient with or without transparency on page 1255

Resetting the background color to the defaults
To reset the background settings to the original default values (that is, the system/theme background
color), click the ellipsis (...) button to the right of the Color attribute in the Form/Component property
sheet.

Creating sprite images for toolbar buttons
When creating a toolbar component, one option for the display of the toolbar icon is to use a "sprite
image" which allows the icon to change its appearance - say, its color - whenever the mouse pointer
is over the icon. This is a good visual cue to let users know that they are in the active area for the
button.
Specifications for the sprite image
The sprite image must meet these specifications:
•
•
•

•

The dimensions of the image must be exactly 25 pixels wide by 50 pixels high.
The image must be formatted as .png with a transparent background.
The image must consist of two glyphs, one for the default state and one for the hover state:
• The dimensions for each glyph cannot exceed 16 pixels by 16 pixels.
• The glyph for the default state must begin 2 pixels from the left side and 1 pixel from the top.
• The glyph for the hover state must begin 2 pixels from the left side and 26 pixels from the top.
The file for the image must contain "mgsprite" in the name; for example: mytbbutton_mgsprite.png
This naming convention lets WinStudio know that it is a sprite image for the button and should be
handled accordingly.

•

The image file can be:
• Stored in the Mongoose file system. We recommend placing it in the same directory as the
winstudio.exe file.
• Imported into the application database using the Edit (menu) > Images dialog box.

After creating the sprite image, you must then implement it for the toolbar button.

Infor Mongoose Application and Form Development | 148

Working with Forms

Collections
About forms with collections
A form's definition can include zero, one, or multiple IDO collections. Most forms include at least one
IDO collection.
The first IDO specified for a form returns the primary collection. Additional IDOs return secondary
collections or subcollections. Both primary collections and secondary collections are considered
"top-level" collections. Both top-level collections can also use subcollections.
About the current collection
At runtime, a collection becomes the "current collection" when the user clicks on a component that is
bound to it, or when an event handler, code, or other internal logic makes it the current collection. When
a collection becomes the current collection, then the toolbar and events refer to that collection.
About the primary collection
The primary collection is the first collection defined (if any). It is distinct from other types of collections
in these ways:
•

•

A form can be defined with an Initial Command that executes when the form is launched. This
initial command for a form applies only to the primary collection. For example, if the initial command
for a form is Refresh, then, when the form opens, a query is run to return and display the contents
of the primary collection.
Note: To accomplish the same action for a secondary collection, you must use some form of
logic—for instance, event handlers, or form scripts.
Unbound components are validated upon navigation of the primary collection.
Components bound to collections are, as a matter of course, validated when the collection to which
they are bound is navigated or saved. For unbound or variable-bound components, though, the
primary collection provides that functionality.
Similarly, when the user puts focus on an unbound component, the primary collection becomes
the current collection.

•
•

The linked parent/child form mechanism is restricted to primary collections in certain respects.
The scripting APIs have a shortcut to this collection: ThisForm.PrimaryCollection
For secondary collections and subcollections, the APIs are slightly more complex.

About secondary collections
Optionally, forms can include one or more secondary collections. As top-level collections, they function
much like primary collections, with the exception of the differences noted in the previous section.
Secondary collections are useful when you need to create a form that works with multiple collections,
and when some of those do not have parent-child relationships characteristic of subcollections. Since

Infor Mongoose Application and Form Development | 149

Working with Forms
it is easier to implement forms with subcollections than forms with secondary collections, you should
use secondary collections only in those cases where subcollections will not work.
About subcollections
Subcollections contain "child data"; that is, data that is related to "parent data" in a top-level collection,
and linked by specifying one ore more parent property=child property clauses.
For each parent collection row that the user has "visited" since the last time the parent collection was
refreshed, Mongoose caches the the subcollection rows ("child data") automatically. This data is sent
to the application server, then, whenever the parent collection is saved.
Note: By contrast, even though it is possible to implement a parent/child relationship between top-level
collections (for example, primary > secondary) by filtering one by the values of another, users will need
to proactively save changes to the "child" data when navigating the parent collection.
A word about parent/child collections
Mongoose supports two cases of parent/child collections:
•
•

Subcollections within a single form
Linked parent/child forms

In both cases, a LinkBy expression is used to construct a filter for querying the child collection, based
on the current values of properties in the parent collection.
Also in both cases, when the parent collection is refreshed, navigated, or saved, the default behavior
is to requery the child collection.
Collection-level commit and the IDO Request Interface
One of the more unique features of the Mongoose UI model, which provides significant end-user
productivity but also significant internal complexity, is the collection-level-commit model. This means
that rows in a collection can have a status of modified, deleted, or new, and the user can continue to
navigate rows, updating, inserting, deleting, and sorting, before finally saving the collection.
The IDO Request Interface UpdateCollection verb accepts an arbitrary number of rows, marked for
insert, update, or delete, potentially nested with child collection rows, and then, by default, processes
all this together in a single transaction. This means that end-users can treat such data much like a
spreadsheet, sorting and mass-updating values, without being forced to commit each row before moving
to another (row-level commit).

About forms with subcollections
A subcollection is a 'child' collection whose records (rows) are associated with and dependent on the
items in the 'parent' (primary) collection. Subcollections are the primary mechanisms for defining
hierarchical, parent-child data relationships on forms. Subcollections implement one-to-many
relationships between collections.

Infor Mongoose Application and Form Development | 150

Working with Forms
To implement a form with a subcollection, bind a grid component to a subcollection property. This
property is defined in the parent IDO as a child IDO that is filtered from the parent IDO. You can bind
individual properties of the subcollection to grid columns or other form components.
When using subcollections, keep these limitations in mind:
•
•

You can only have one level of subcollections (that is, parent-child collections). In other words, a
subcollection cannot in turn have its own subcollection.
Subcollections must be bound to Grid components.
This is in contrast to top-level collections that can be bound at the form level, and not necessarily
to any one (type of) component.

Examples of forms that use subcollections include the Users form, the Background Task History
form, and the Event Handlers form.

About collection attributes/properties
All collections used on a form have their own attributes that can, for the most part, be defined or altered
using the Collections tab for the form in the Web Designer (web client) or in Design Mode (Windows
client).
These tables list and describe the attributes/properties that can be used to define collections on forms
and determine their capabilities and behaviors:
Table 1: Definition attributes/properties
The IDO name specifies the data source for the collection. This specification allows the framework to
implement refresh (query), insert, update, and delete operations without requiring any further information
from the developer, because the IDO request interface generically provides these operations for all
IDOs.
Optionally, a collection definition can override any of these operations using custom load method
specifications.
Unless specified otherwise, all attributes/properties apply to both primary and secondary collections.
Attribute name

Description/Comments

Collection

This property specifies the source of a collection of data for a form. The
value of the property must be the name of an IDO.

Base Table and Alias

This read-only field displays the name of the primary table and its alias for
the IDO named in the Collection field.

Collection Name

This property is a string name or literal value that identifies the collection
in error messages and menu options. The name should be appropriate for
the end user.

Infor Mongoose Application and Form Development | 151

Working with Forms

Attribute name

Description/Comments

Custom Load Method

Use this property when you want to specify an IDO method as an alternative
for loading or manipulating data in the collection. This property should be
specified only if the standard default method of loading the collection is
inadequate. This specification is usually required only in very advanced
applications.

Method Parameters

Use this property to specify any parameters that must be passed to a
custom load method. Parameters may include literal values, properties,
variables, or component values.

Read Mode

Use this property to specify the isolation level for transactions when loading
a collection. This setting overrides the default value for the Collection
Read Mode as set on the Process Defaults form. The override applies
only to the collection for which it is specified.
This property permits or prevents "dirty reads."
•

•

A setting of UNCOMMITTED implements dirty reads, in which no
shared locks are issued and no exclusive locks are honored. It is possible to read uncommitted data; values can be changed and rows can
appear or disappear in the collection before the end of the transaction.
The setting corresponds to the Transact-SQL statement SET
TRANSACTION ISOLATION LEVEL READ UNCOMMITTED.
A setting of COMMITTED specifies that shared locks are held while
data is being read to avoid dirty reads, but the data can be changed
before the end of the transaction. The setting corresponds to the
Transact-SQL statement SET TRANSACTION ISOLATION LEVEL
READ COMMITTED.

Other options for collections
Collection specifications can also be modified in other ways, including the way standard operations
are handled, and how data in those collections are handled.
These tables list and describe the Options attributes/properties:
Table 2: Standard Operations attributes/properties
Each of these operations can be set in the Web Designer or Design Mode; altered at runtime by means
of API calls from form scripting; and/or overridden by security rules.
Attribute name

Description/Comments

Navigate

Use this property to specify whether the user can use shortcut keys or the
Actions menu to navigate to the first, next, previous, and last rows of the
collection.
• True: Navigation is enabled.
• False: Navigation is disabled.

Refresh

Use this property to specify whether the user can requery the data in the
collection.

Infor Mongoose Application and Form Development | 152

Working with Forms

Attribute name

Description/Comments

Filter

Use this property to specify whether the user can set filter criteria for data
being queried in the collection.

New/Copy

Use this property to specify whether the user can create new rows or copy
rows in the collection.

Save

Use this property to specify whether the user can save changes made to
data in the collection.

Delete

Use this property to specify whether the user can delete data from the
collection.

Notes

Use this property to specify whether the user can attach notes to rows in
the collection.

Graph

Use this property to specify whether the user can generate an ad hoc graph
of the data in the collection.

Spreadsheet Disabled

Use this property to specify whether the user can export the contents of
the collection to a spreadsheet, such as Microsoft Excel (or any other application capable of reading CSV-formatted spreadsheets).
This property cannot be used for subcollections.

Table 3: Advanced Options attributes/properties
Attribute name

Description/Comments

Record Cap Override/Page Size

Use this property to specify a maximum record cap for list sources of type
IDO Collection and IDO Method, collections/subcollections, and tree definitions.
Note: This setting is subject to any system-wide caps that might be in effect.
If no other value is specified, this field defaults to the system default setting
(200).

Enable Paging

Use this property to enable pagination of records displayed for a primary
collection or a subcollection.
When this property is set to True, you must specify the number of records
to be displayed per page in the Record Cap Override/Page Size property
field.

Operate As Primary

Use this property to designate a collection other than the primary collection
to be associated with unbound or variable-bound components that are
normally associated with the form's primary collection. This would typically
be set to True only for secondary collections or subcollections.
For instance, variable-bound components are re-initialized when the user
inserts a new row into the primary collection. This property allows you to
designate another collection, perhaps a secondary collection, to be associated with these components.

Infor Mongoose Application and Form Development | 153

Working with Forms

Attribute name

Description/Comments

Reload After Save

Use this property to determine whether the collection is to be refreshed
after each save operation.

Reload After Child
Save

Use this property to specify whether the parent collection is to be refreshed
after a child subcollection is saved.

Defer Refresh Until
Visible

Use this property to specify whether a collection or subcollection is to be
populated before it becomes visible to the user.
Deferral is especially useful in cases where subcollections or secondary
collections are displayed on notebook tabs. If this property is set to True,
the collection is not queried until the user selects the tab that contains it.

Save One Row Per
Transaction

Use this property to specify whether to save each modified row in a separate
transaction.
For more detailed information, see Save One Row Per Transaction property on page 1134.
The property applies only to primary and secondary collections.

Load/Save Overrides

Use this property to specify custom procedures for update, insert, delete,
and post-query processing.
To specify which type of operation is to be handled by custom processing
in the IDO instead of by a standard operation on the collection, click the
ellipsis (•••) button, which opens the Edit Middleware Load/Update
Overrides dialog box.

Update Locking

Use this property to select which column-locking option is to be used during
an IDO item update.
For more detailed information, see Update Locking property on page 1145.

Table 4: Collection Selection attributes/properties
Attribute name

Description/Comments

Permanent Filter Expression

Use this property to specify a permanent filter to be applied to the collection
for a form. Ad hoc filter specifications set by a filter form or Filter-In-Place
are added to this filter when querying the collection.
A Permanent Filter Expression value is a SQL WHERE clause without
"WHERE" (for example, username <>
'ser
vice').
Note: Use the Edit Permanent Filter dialog box to create or edit permanent
filter expressions. This dialog box opens when you click the ellipsis (•••)
button next to the field.
Expressions can reference the values of components, variables, or properties on other collections, by means of substitution keywords, etc. These
use Boolean AND filtering with any end-user filtering or security rules for
row-level filtering.

Infor Mongoose Application and Form Development | 154

Working with Forms

Attribute name

Description/Comments

Optional Filters

Use the Optional Filters property to specify optional filters to be applied
when querying IDO collections.
These filters are applied to the IDO query only when there is a value supplied here. Optional filters are excluded from the query when this field value
is blank.
Optional filters are stored in the data source as in this example:
FILTEROPTS(FILTEROPT(…),FILTEROPT(…))
FILTEROPT(PROP(…)OPER(…)EOD())
Note: Use the Edit Optional Filters dialog box to create or edit optional
filters. This dialog box opens when you click the ellipsis (•••) button next
to the field.

Distinct

Use this property to remove duplicate records from returned data.
As with other properties, this attribute effects the SQL code that the IDO
Runtime service generates for IDO LoadCollection actions.
Caution: If you remove duplicates, the data cannot be reliably updated.
This option should only be used with read-only data.

Order By

Use this property to specify IDO properties by which a collection is sorted.
An Order By setting is a comma-delimited list of IDO property names. The
default order is ascending. You can specify the order of any of the properties
by adding either desc (descending) or asc (ascending) after the property
name.
Expressions can reference the values of components, variables, or properties on other collections, by means of substitution keywords, etc. These
use Boolean AND filtering with any end-user filtering or security rules for
