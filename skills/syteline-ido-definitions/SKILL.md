---
name: "syteline-ido-definitions"
description: "IDO definitions, projects, tables, properties, methods, extending/replacing IDOs, Access As identifiers"
version: "1.0"
tags:
  - syteline
  - ido
  - mongoose
  - extensibility
---

# syteline-ido-definitions

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

For the latest documentation, go to Documentation Central at docs.infor.com. We recommend that you check
this website periodically for updated documentation.

Infor Mongoose Core Extensibility Guide | 8

About the Access As identifier

Chapter 1: About the Access As identifier

One key element of the Mongoose security and authorization structure is the "Access As" identifier. This
identifier indicates ownership and modification rights for most Intelligent Data Objects (IDOs) and other
metadata. It is used similarly to control objects used by the Application Event System (AES).
Among other things, the Access As identifier indicates who (that is, what organization) created and owns an
IDO, an AES event, or other related event system object. It also prevents unauthorized developers from
modifying or deleting system metadata or objects that they do not own.
Generally, the Access As identifier falls into one of three groups:
•
•
•

Core: Indicates that the object is one that Infor created and owns.

OtherName: Typically, indicates that the object was created by and belongs to one of Infor’s business
partners or other authorized vendors.
[Blank]: Indicates that the object was created by and belongs to an end customer.

Within any Mongoose-based application, several forms include an Access As field. On the Access As form,
this field indicates the current Access As identifier value. This is the value assigned to any new system objects
you create. On all other forms, this field indicates who has ownership of the pertinent object metadata; in
other words, who created and owns it.
You can only modify or delete metadata for system objects that have the same value as on the Access As
form—in other words, system objects and metadata that your organization has created and owns.
With a few exceptions (noted where applicable), you can attach your own event triggers and event handlers
to event system objects owned by other organizations (that is, with a different Access As identifier than yours);
but you cannot directly modify or delete those objects.
Note: Importing metadata, IDOs, and related elements is also controlled by the Access As identifier. The
Convert to local Access As option is available in all metadata import utilities so that you can have the flexibility
to either retain the Access As value from the imported file or use the target environment’s Access As value.

Infor Mongoose Core Extensibility Guide | 9

IDOs

Chapter 2: IDOs

About IDOs
An Intelligent Data Object (IDO) is a business object that encapsulates units of information and logic that are
called from the client layer to interact with data in the database. The basic job of the IDO is to transport
collections of data back and forth between the client and the database, with any validation or rules needed.
IDO elements
An IDO consists of these elements:
•

A set of one or more SQL tables
Each table contains the data for a specified part of the application and must include columns (representing
properties) that the application requires to work properly.

•

A set of properties
A property may represent persistent data stored in the application database, derived data, or temporary
data used to communicate information to the middle tier. A property may also represent a whole
subcollection of data.

•

A set of standard methods
All IDOs implement these methods:
•
•
•
•

LoadCollection: Retrieves a collection of rows from the database
UpdateCollection: Takes a set of rows marked for insert, update, or delete, and executes the
appropriate SQL code on the database
GetPropertyInfo: Returns detailed information about the properties supported by the IDO
Invoke: Allows the application to call and execute a custom method

Using "configurations", Application Databases are linked with an Objects Database and a Forms Database.
Note: These three types of data might be contained within a single "All-in-One" database.
Defining an IDO
The IDO forms serve as the development environment for the definitions of IDOs. IDO definitions are stored
as metadata in the Objects Database. You can create and edit this metadata using the IDO forms.
The IDOs form is commonly used as the starting point to define an IDO. IDO definitions include these elements:
•
•

Attributes for the IDO itself
References to tables in the Application Database

Infor Mongoose Core Extensibility Guide | 10

IDOs
•
•

Property definitions for table columns
Metadata about methods defined for the IDO

You can access and develop all of these elements from the IDOs form. You can also access the row-level
security that can be defined for IDOs.
The metadata that you edit in the IDOs form is stored in the Objects Database (or its equivalent in the All-in-One
database).
Existing IDOs must be checked out before they can be edited. You can only check out IDOs that have the same
Access As setting as your development environment.
You can also perform these tasks using the IDOs form:
•

Add, edit, or delete an IDO.
Note: You can use deletion rules to determine what is to happen when one or more records meet specified
criteria for other IDOs that reference properties in the one you want to delete. Define these deletion rules
on the Rules tab.

•
•
•
•
•
•
•

View the elements and attributes of an IDO.
Edit or remove an IDO table.
Add a base or secondary table to an IDO.
Add a property to an IDO.
Edit an IDO property.
Add a method to an IDO.
Edit an IDO method.

How IDOs are used
Forms use IDOs in multiple ways. Forms that interact with the data in the Application Database define
collections based on IDOs. Many types of validators and list sources are built over IDOs.
The Application Event System (AES) also leverages IDOs. Many of the framework AES events are generated
as the application operates on IDOs. Many of the actions that are provided in the AES operate on IDOs. This
allows you to quickly define business processes, automation rules, or general server-side logic in your
application.
Note: You can view the AES events that are associated with an IDO on the Workflow Summary tab.

About extending and replacing IDOs
This application allows for the creation of new IDOs (Intelligent Data Objects) that inherit all the properties,
methods, tables, and extension classes from an existing IDO. The relationship between the newly created
IDO and the original (base) IDO is called an "extended" relationship: The new IDO inherits these elements
from the base IDO and adds to them.
You can optionally mark an extended IDO to be used as a replacement for the base IDO. When an extended
IDO replaces its base IDO, the application reroutes all IDO requests that are targeted for the base IDO to the

Infor Mongoose Core Extensibility Guide | 11

IDOs
extended IDO instead. The requests affected by this rule include GetPropertyInfo, LoadCollection,
UpdateCollection, and Invoke requests.
Use the IDO Wizard to extend or extend-and-replace an IDO. This wizard, which is accessible from the IDOs
form, includes the Extends option and the Extend and Replace option. See Adding an IDO on page 23.
An extended IDO can only make additive changes to the base IDO. That is, new properties, methods, tables,
or extension classes can be added, but none of the base properties, methods, tables, or extension classes
can be modified or deleted. New properties can be bound to columns from tables in the extended IDO or in
the base IDO. Likewise, new "derived properties" can reference properties in the extended IDO, as well as
properties in the base IDO.
An extended IDO can itself be extended by one or more other IDOs. There is no hard limit to the number of
levels in an inheritance chain. While any number of extended IDOs can share the same base IDO, no more
than one sibling IDO can be used as a replacement for the base IDO.
When the IDO Runtime Service processes IDO requests, it generates events that might be handled by IDO
extension classes. If an extended IDO has one or more base IDOs that have extension classes associated with
them, the events fire in all extension classes, but the order in which they fire is indeterminate.
The base IDO and the Extend and Replace option can only be set when creating a new IDO. The attributes
are read-only for existing IDOs.

Working with IDO projects
Adding an IDO project
An IDO project is a group of one or more associated IDOs. Every IDO that is created must be created within
an IDO project. Should you need an IDO project that does not meet the need for your new IDOs, you can create
a new IDO project. This must be done before you can add your new IDOs.
1
2
3

Open the IDO Projects form and execute Filter-In-Place.
Initiate an action to create a new project.
In the Project Name field, provide a name for your IDO project.
The name must follow these rules:
•
It must be unique in the objects database.
•
It must consist of alphanumeric characters only.
•
It can contain no spaces.
•
It must begin with a letter (not a number).
•
It can be no longer than 30 characters.

4
5

Optionally, provide a Description of the intended purpose for or use of the IDO project.
Save the project.

The system creates the IDO project. Once it is created, you cannot modify it further, except for the Description.

Infor Mongoose Core Extensibility Guide | 12

IDOs
From this same form (IDO Projects) you can create IDOs for your project by clicking New IDO and using the
IDO Wizard.

Deleting an IDO project
You can delete IDO projects from the current Objects Database, but only if there are no IDOs associated with
the IDO project.
1

Open theIDO Projects form and select the IDO project to delete.
Note: You cannot delete the IDO project if it is associated with any IDOs.

2

To verify that no IDOs are associated with the project, click IDOs.
This launches the IDOs form. If there are any IDOs associated with the project, the IDO names display in
the Grid View. If no IDO Name displays, then you can close the IDOs form and proceed to delete the IDO
project. If any IDO names display in the Grid View, you must first delete the IDOs from the project before
you can delete the IDO project itself.

3

Click the trash can icon in the toolbar.
When the advisory message displays, click OK.

4

Save your changes.

Working with IDOs
The basics
About viewing IDO definitions
To view the elements and attributes of an IDO, use the IDOs form and execute Filter-In-Place. Then, in the
Grid View, select the IDO you want to view.
The "wizard" buttons
Across the top of the form are four buttons. Each of these four buttons launches a "wizard" form that you can
use to create these resources for an IDO you have checked out.
For more information about these buttons, see the context-sensitive help associated with each button.

Infor Mongoose Core Extensibility Guide | 13

IDOs
General IDO attributes
In the section just below these buttons, the IDOs form displays the basic, or general, attributes of the selected
IDO: its name, associated IDO project, and so on.
In the lower right-hand area of this section, there are also two buttons (Check In/Check Out and Undo Check
Out) that allow you to check the IDO out. check it in, or unlock it if you are using source control on your system.
These source control actions can be performed from this form, without having to use Form Control.
For detailed information about each of the attributes in this section, see the context-sensitive help associated
with each attribute.
Form access buttons
Below the general attributes area is a row of buttons that you can use to launch forms for the various elements
of an IDO—tables, properties, methods, and so on. These forms provide more detailed information than is
generally available on the IDOs form. They also usually provide options to create or modify IDO elements.
For detailed information about these buttons, see the context-sensitive help associated with each button.
Attributes grid
At the bottom of the form is a notebook component with several tabs. Use these tabs to view summaries of
the elements and other information associated with the selected IDO:
This tab:

Displays a list and condensed view of this information:

Tables

Lists the tables associated with the IDO

Properties

LIsts the properties defined in the IDO

Methods

Lists the methods associated with the IDO

Filters

Lists any filters applied to the IDO, whether active or not

Rules

Lists any rules that define relationships between IDOs and the policies to enforce
when records are deleted

Workflow Summary

Lists any application system events associated with the IDO and the number of
event handlers for each event

About source control
To allow multiple developers in the system the ability to customize and maintain forms without having to
worry about overwriting one another's changes, use a source control system.
This application supports multiple source control systems:
•
•
•

Microsoft Team Foundation Server
Apache Subversion
Git

Infor Mongoose Core Extensibility Guide | 14

IDOs
If you plan to use any of these source control systems to manage changes to forms and other system
components, you must take these steps:
•
•

Obtain, install, and configure the source control software.
Use the Mongoose Configuration Manager to configure the source control software to work with your
system. For more information, see one or both of these documentation sources:
•
The Infor Mongoose Installation Guide: Information about setting up the required databases
•
The Configuration Manager online help: Information about setting up the source control software
to work with Mongoose
Note: The Configuration Manager help provides information only about how to set Mongoose up
to work with your source control software. For information about how to set up your source control
software, see the documentation for that software.

•

On the Users form, Source Control tab, configure the user profile for each system user who is authorized
to check forms and other components in and out.

Note: Not all source control systems require this.

Comparing IDOs during check-in
Note: This topic is applicable only if you are using source control, have a file comparison tool configured,
and are using the Windows ("smart") client. It does not work in the web client or a cloud environment.
Before you check in an IDO that you have modified, you can compare it with the current version. You can use
a file comparison tool to compare the new and the current version of an IDO. You must first set up a comparison
tool in User Preferences.
To accomplish this, use the IDOs form or the Mass Check In form. When you click Compare on either of these
forms, two XML files are created. The compare tool that you specified in User Preferences then opens these
two XML files to compare the contents.

About non-Mongoose data used in Mongoose-based applications
You can incorporate data from a non-Mongoose source; for example, a legacy application that is being
converted to a Mongoose base. However, certain schema elements required by Mongoose often do not exist
in the non-Mongoose data source, as described in this topic.
To incorporate these data sources into your Mongoose-based application, you must create views that provide
the schema elements required by Mongoose.
For the procedure, see Including Data from an External Database into a Mongoose Application on page 18.
Note: Currently, only SQL Server, Oracle, DB2, Postgres, and Progress data sources can be linked to Mongoose
applications.

Infor Mongoose Core Extensibility Guide | 15

IDOs
Unicode support
Mongoose application databases are designed to support Unicode, but other data sources might not support
Unicode. To avoid improper scanning of indexes in the non-Mongoose data sources, you might need to set
the Non unicode literal process default in the Mongoose-based application.
Optimistic locking
Mongoose databases use the RecordDate property to provide optimistic locking. In many cases, however,
the non-Mongoose data source does not have a RecordDate property. So, one of the major problems with
using non-Mongoose data sources is the coordination of optimistic locking. Both the IDO Linked Databases
form and the IDO Linked Tables form provide options to specify a column to be used for optimistic locking
in the non-Mongoose data source.
This specification can include these options:
•
•
•

Allowing Mongoose to identify whether a RecordDate column exists in the non-Mongoose data source.
Designating a default column on the IDO Linked Databases form. This is the name of a column that is
normally used for optimistic locking by the non-Mongoose tables, if such a column exists.
Designating a column for optimistic locking on the IDO Linked Tables form.

If none of these options exist, the literal string "NODATE" is designated as the RecordDate value. Because this
literal string is applied to any access of the non-Mongoose data source, no optimistic locking occurs.
About the Mongoose view
The created Mongoose view over the non-Mongoose data source includes the columns from the data source
plus these additional columns, required by Mongoose for processing:
•

•

RecordDate is used for optimistic locking in Mongoose. The system maps whatever column you have
assigned in the non-Mongoose table to use for optimistic locking to this column, as described previously.
During run-time, Mongoose checks to see if this value has been modified elsewhere since the data was
first queried, before your modifications.
RowPointer is required to be a value that is unique for the entire table. For "outrigger" data sources,
this value is derived by concatenating the primary key values from each row in the external table.

After this view is created, you can create IDOs and forms. You can also perform read and write operations on
the linked database table like any other Mongoose database table. One exception is that you cannot use the
non-Mongoose database and tables for any event action where the workflow must be suspended.

Including data from a different SQL database into your application
To incorporate data from a non-Mongoose linked SQL Server database into a Mongoose-based application,
you must specify information about the other database, using the IDO Linked Database form and the IDO
Linked Tables form.
Note: To link to a non-Unicode database, use the Process Defaults form to set the Non Unicode Literal
process default. This helps ensure that the database indexes are scanned and accessed properly when
performing queries.

Infor Mongoose Core Extensibility Guide | 16

IDOs
1

Open the IDO Linked Database form and specify these values:
Link Database
Specify the name that is to identify the database in the application. This is an internal designation only
and need not be the same as the actual database name.
Database Name
Specify the name of the SQL Server database to which you want to link. If this database resides in the
same location as the Mongoose databases, you can provide just the name of the database. If this database
resides in a location other than the Mongoose databases, you must also provide the location of the
database. Use this format: databaseServer.databaseName
Note: The database administrator must first create a linked server relationship using the SQL Server
configuration tools.
Optimistic Lock Column Name
Optionally, specify the name of a column that might be available for optimistic locking in the
non-Mongoose database. If no specific column is designated on the IDO Linked Tables form, this value
is used as the default optimistic lock setting.

2
3

Save your changes.
Open the IDO Linked Tables form to verify and, if necessary, adjust the column settings for the
non-Mongoose table:
•
Verify that the columns listed in the Column Name column match those of the non-Mongoose table.
•
Optionally, rename the View Column Names as you want them to display in your Mongoose
application.
•
Verify that the primary keys for the non-Mongoose table are correct. Modify the choices for keys as
required.
•
Optionally, select the column to use for optimistic locking in the non-Mongoose database.
When the conditions listed here exist, Mongoose designates the NODATE literal string for the
RecordDate value, and no optimistic locking is performed:
•
•
•

4

No RecordDate column exists in the non-Mongoose table.
The column specified in the Optimistic Lock Column Name field of the IDO Linked Databases
form does not exist in the non-Mongoose table.
No column is designated for optimistic locking on the IDO Linked Tables form.

To create the Mongoose view, click Create View.
Mongoose creates a view that includes the columns from the linked table, along with columns and values
for these properties:
•
RecordDate: Used for optimistic locking
•
RowPointer: Required to be a value that is unique for the entire table
•
AddMongooseFields: With a literal value of 1, which is used internally

You can now use the linked database and tables in the same way that you use any database created within
Mongoose. You can create IDOs and forms, and perform read-write operations on them like any other Mongoose
database. However, you cannot use the non-Mongoose SQL database and tables for any event action where
the workflow must be suspended.

Infor Mongoose Core Extensibility Guide | 17

IDOs

Including data from an external database into a Mongoose-based
application
Mongoose-based applications can communicate with external databases hosted on various types of servers,
through the IDO layer. To set up communication, use these steps:
Note: To link to a non-Unicode database, use the Process Defaults form to set the Non Unicode Literal
process default. This ensures that the database indexes are scanned and accessed properly when performing
queries.
1

In the Outrigger Profiles form, create a profile for the database that has these specifications:
Note: This step is not necessary for a SQL Server database hosted on the same server as the Mongoose
database, or for a SQL Server database hosted on a linked server.
Profile Name
(Required) Provide a name for the outrigger (external) database.
Database Type
(Required) Select the type of outrigger database to connect to.
Server
(Required for most, but not all database types) Specify the name of the server on which the outrigger
database exists.
Data Source
•
If you selected DB2 for the Database Type, specify the schema name.
•
If you selected Oracle for the Database Type, with the Direct option set to False, specify the Oracle
service name as defined in the tnsnames.ora file.
•
If you selected Oracle for the Database Type, with the Direct option set to True, specify the Oracle
server's IP address or DNS name. For additional options, see the notes at the end of this topic.
•
If you selected Postgres, Progress, Redshift, or SQL Server for the Database Type, specify the
database name.
Query Timeout
(Optional) Specify the amount of time, in seconds, that your application is to wait for responses to a
query from the outrigger database. If you leave this field blank, the query does not time out.
User/Password
(Required) Provide the login credentials to be used to access the outrigger database.

2
3

Optionally, to test the connection to the outrigger database, click Test Connection.
In the IDO Linked Databases form, create a new linked database record with these values:
Link Database
Specify the name that is to identify the database in Mongoose. This is an internal designation only and
need not be the same as the actual database name.
Profile Name
Specify the profile created in Step 1.

Infor Mongoose Core Extensibility Guide | 18

IDOs
For a SQL Server database hosted on the same server as the Mongoose database, or for a SQL Server
database hosted on a linked server, leave this field blank.
Database Type
This field defaults to the database type selected in Step 1, or, if the Profile Name is blank, SQL Server.
Database Name
For a SQL Server database hosted on the same server as the Mongoose database, specify the name of
the database to which you want to link.
For a SQL Server database hosted on a linked server, you must also provide the name of the server using
this format: databaseServer.databaseName.
Optimistic Lock Column Name
Optionally, specify the name of a column for optimistic locking that is present in some or all tables in
the non-Mongoose database. If no specific column is designated on the IDO Linked Tables form, this
value is used as the default optimistic lock setting.
4

In the Tables grid, specify or select an existing table name in the external database.
Optionally, rename the View Name to avoid conflict with any tables or views in your Mongoose application
or with other external tables being linked.

5
6

To enable the buttons on the form, save the table record.
Select the table and click Columns.
This opens the IDO Linked Tables form, with an empty Columns grid.

7

To connect to the database and create the default column information for the external table, click
Repopulate.
The results can be adjusted by following these steps:
a Verify that the columns listed in the Column Name column match those of the external table.
b Optionally, rename the View Column Names as you want them to be shown in the IDO Wizard form.
c Verify that the primary keys for the external table are correct, as specified in the Keys column. Add
or modify the choices for keys as necessary.
Note: Keys are not initialized for Redshift databases, so you must specify them manually.
d

8

Optionally, select the column to use for optimistic locking in the external table.
When the conditions stated here exist, Mongoose designates the NODATE literal string for the
RecordDate value, and thus no optimistic locking is performed:
•
No RecordDate column exists in the external table.
•
The column specified in the Optimistic Lock Column Name field of the IDO Linked Databases
form does not exist in the external table.
•
No column is designated for optimistic locking on the IDO Linked Tables form.

To create a new view in the Mongoose database that defines the necessary columns and data types, click
Create View.
The created view includes the columns from the external table, along with these columns and values:
•
RecordDate: Used for optimistic locking
•
RowPointer: Required to be a value that is unique for the entire table

Infor Mongoose Core Extensibility Guide | 19

IDOs
9

Use the IDO Wizard form to create an IDO whose Primary Base Table is the View Name that is created.
If applicable, the profile name is automatically populated.
10 Use the Advanced IDO Attributes form to ensure that the proper primary keys are selected in the correct
order.
11 Save your work.
Notes about the process:
•
•

The profile that is stored with an IDO definition is used to make an Application Database object within
the IDO layer for read and write operations on the table, as well as for method calls to that database.
As an alternative to building an IDO through the wizard, which allows basic read-write functionality to
the external database, you can use IDORuntime.Context.CreateOutriggerApplicationDB in a custom
program to provide direct access to the outrigger database.
For a code sample that uses this assembly, see Example: Custom Code to Communicate with an Outrigger
Database on page 21.

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
•
•

•

For DB2 databases, a third-party driver package must be installed and configured. Contact Support for
details.
For Oracle and Progress databases, a license to a third-party driver package must be purchased. Contact
Support for details.
There are two modes for connecting to Oracle databases:
•
If the application server is the Oracle server or Oracle Client software is installed on the Application
Server, in the Options grid, specify an option of Direct with a value of False; and in the Data Source
field, specify the Oracle service name (SID).
•
Otherwise, in the Options grid, add an option of Direct with a value of True; and in the Data Source
field, specify the Oracle server's IP address or DNS name. Finally, add an additional option of SID
with a value of the Oracle service name on the Oracle server. You can also add an option of Port with
the port number to connect to. The default value is 1521. In this mode, you can perform write
operations on a linked table in an Oracle database only through a configuration where the Use
Distributed Transactions (DTC) option is cleared.
You cannot use asynchronous event handlers when communicating with external databases.
You cannot use the external database and tables for any event action where the workflow must be
suspended.
You cannot use User-Defined Fields (UDFs) when communicating with external databases.
All external tables referenced in an IDO must exist in the same database.
RowPointer and RecordDate properties are used by Mongoose but do not exist in external tables; so those
IDO properties are derived, instead of pointing to base table columns.
A called Oracle IDO method must be a function with a return type of Integer.
Due to driver limitations, these data-types are not supported for linked tables in a Progress database:
LVARBINARY, BLOB, LVARCHAR, CLOB, BINARY, VARBINARY, RECID, TINYINT, BIGINT.
For Postgres databases, the BIT data-type is not supported, because IDO Properties have no equivalent
data-type.
Due to security requirements, you can perform read and write operations on a linked table in a DB2,
Oracle, Postgres, or Progress database only through a configuration, where the IDO Extension Classes
Run In Partial Trust option is cleared.
Due to driver limitations, you can perform write operations on a linked table in a DB2 or Progress database
only through a configuration, where the Use Distributed Transactions (DTC) option is cleared.

Infor Mongoose Core Extensibility Guide | 20

IDOs

Example: Custom code to communicate with an outrigger database
This code sample uses a custom method, IDORuntime.Context.CreateOutriggerApplicationDB to provide
direct access to an outrigger database. The database must first be linked to your Mongoose application as
described in Including data from an external database into a Mongoose-based application on page 18.
public int IdoLinkOtherDbColPopulateSp( string linkedDatabase, string tableName, string
optimisticColumnName, byte databaseType, string profileName, string infobar )
{
int result = 0;
if ( databaseType == 1 ) // SQL Server
{
if ( !DoSqlServerColumnsSp( linkedDatabase, tableName, infobar ) )
return 16;
else
return 0;
}

) )

if (!DeletePreviousColumns( linkedDatabase, tableName, infobar ) )
return 16;
using ( ApplicationDB db = IDORuntime.Context.CreateOutriggerApplicationDB( profileName
{

IDbCommand cmd = db.Connection.CreateCommand();
IDbDataParameter parm;
cmd.CommandType = CommandType.Text;
cmd.CommandText = @"

SELECT
cols.COLUMN_NAME
, cols.COLUMN_NAME As ViewColumnName
, CASE WHEN DATA_TYPE IN ('VARCHAR', 'NVARCHAR', 'VARCHAR2', 'NVARCHAR2') THEN 1 ELSE 0 END AS
IsCharacterColumn
, CASE WHEN xx.TABLE_NAME IS NULL THEN 0 ELSE 1 END AS IsKeyColumn
, cols.DATA_TYPE
, CASE WHEN cols.DATA_PRECISION IS NULL THEN cols.DATA_LENGTH ELSE DATA_PRECISION END
, cols.DATA_SCALE
FROM user_tab_cols cols
LEFT OUTER JOIN (
select ucc.table_name, ucc.column_name
from user_constraints uc
inner join user_cons_columns ucc on
ucc.table_name = uc.table_name
and ucc.constraint_name = uc.constraint_name
and uc.constraint_type = 'P'
) xx ON
xx.TABLE_NAME = cols.TABLE_NAME
AND xx.COLUMN_NAME = cols.COLUMN_NAME
WHERE cols.TABLE_NAME = :ptable
";
parm = cmd.CreateParameter();
parm.ParameterName = "ptable";
parm.Value = tableName.ToUpper();
cmd.Parameters.Add( parm );
cmd.CommandType = CommandType.Text;
cmd.Connection = db.Connection;
IDataReader colReader = cmd.ExecuteReader();
while (colReader.Read() )
{
string colName = colReader.GetString(0);
string viewColName = colReader.GetString(1);
byte isCharacter = colReader.GetByte(2);
byte isKey = colReader.GetByte(3);
byte isOptimisticLock = 0;
string propertyDataType = colReader.GetString(4);
int propertyLength = colReader.GetInt32(5);
int? propertyScale = colReader.GetInt32(6);
if ( colReader.IsDBNull(6) )
propertyScale = null;

Infor Mongoose Core Extensibility Guide | 21

IDOs
if ( optimisticColumnName == colName )
isOptimisticLock = 1;
if (!InsertOneColumn( linkedDatabase, tableName, colName, viewColName, isCharacter,
isKey, isOptimisticLock, propertyDataType, propertyLength, propertyScale, infobar ) )
{
break;
}
}
}
return result;
}

Checking in and out
Checking in IDOs
When you finish working with an IDO, check it into the current Objects Database. If you use a source control
system, checking an IDO into the Objects Database also checks it into the source control system. (New IDOs
do not exist in source control until you check them in.)
1
2
3

Open the IDOs form and select the desired IDO.
Click Check In.
If prompted, enter a comment for the IDO.
Check-in comments are stored in the source control system.

4

Click OK.

Note: You can check in multiple IDOs at one time, using the Mass Check In form.

Checking in multiple objects
Note: To ensure that you are viewing the latest records, right-click and select Refresh on any tab.
You can check in multiple IDOs and related objects at one time.
1

Open the Mass Check In form.
When you first open the form, it is pre-populated with any IDOs or related objects that are currently not
checked in and are associated with your user name.

2

On each tab, specify the files to check in by selecting the Inc option for each row, or by clicking Select
All.
Optionally, to attach and check-in assembly images or symbols with an IDO extension class assembly:
a On the IDO Assemblies tab, select the row of the IDO extension class assembly.
b Click Assembly Image or Symbols.
c Specify the image or symbol file to attach.
d Click OK.

3

Note: After importing an assembly image or symbol, the collection is saved automatically.

Infor Mongoose Core Extensibility Guide | 22

IDOs
4
5

Click Check In.
If your source control application requires it, specify any additional required information, such as
comments or tracking numbers.

Checking out IDOs
If an IDO has been checked in to the current Objects Database, you must check it out before you can making
any changes to it.
1
2

Open the IDOs form and select the desired IDO.
Click Check Out.

The application locks the IDO as being checked out to you. Others cannot make changes to it until you check
it back in.

Adding, editing, and deleting IDOs
Adding an IDO
An IDO (Intelligent Data Object) provides a set of properties and methods. It also responds to requests from
the IDO request interface (LoadCollection, UpdateCollection, and Invoke).
You can add IDOs to your IDO project using this procedure:
1
2

Open either the IDO Projects form or IDOs form, and click New IDO.
In the first page of the IDO Wizard, provide this information:
Project Name
(Required) Select the IDO project to which the IDO is to belong
Primary Base Table
(Required unless extending) Select the table or view that is to be used to store the values for the properties
of the new IDO. To be selectable, the table must already exist in the IDO project.
Note: Use this option only if you are creating a completely new IDO. To create an IDO that is an extension
of an existing IDO, use the Extends option.
IDO Name
(Required) This is the name to assign to the new IDO. This name must be unique in the Objects Database
(or its equivalent in the All-in-One database).
This field is automatically populated when you select the Primary Base Table, but you can change it.
Description
(Optional) Provide a brief description of the intended use for the new IDO.
Label String ID
(Optional) Select a translatable string to use for the label on the IDO.

Infor Mongoose Core Extensibility Guide | 23

IDOs
If there is a string associated with the Primary Base Table you selected, this field is automatically
populated with a recommended string, but you can change it.
Access As
This read-only field displays the Access As identifier that is to be associated with the new IDO. This field
should display the Access As identifier for your development environment.
Table Alias
(Required) Provide a unique, valid SQL identifier for this IDO table. This alias is usually an abbreviated
version of the table name.
This identifier is used to reference the table in join specifications and in generated SQL.
Extends
(Optional) To create an IDO that is an extension of an existing IDO, select the IDO you want to extend.
If you select this option, the Primary Base Table field and the Table Alias field are both disabled.
Extend And Replace
(Optional) To have the extended IDO replace the base IDO at runtime, select this option.
3

Click Next.
Note: If you specify a value for the Extends option, page 2 of the wizard is not used and the Next button
is disabled. In this case, click Finish.

4

In the second page of the wizard, create bound properties for this IDO that are based on columns in the
primary base table in the Application Database.
By default, all of the columns in the primary base table are included as properties in the IDO.
•
•
•

5

To remove a column and omit it as a property, clear the Inc check box for that column.
To change a property name, specify the new name in the Property Name field for that property.
To make changes to all property names at one time, click Massage Property Names, which opens
the Massage Options form.

Click Finish.

The IDO is created and, if you are using source control, it is automatically checked out to you. Before it is
accessible for general use, you must check it in.

Setting advanced IDO attributes
The advanced attributes of an IDO include its primary key properties, and other options.
Primary keys reflect the primary key properties of the IDO's base table in the application or external database.
The system automatically detects primary key properties, except for IDOs that are based on SQL views. The
order of the IDO's primary key properties should match the order used in the application's underlying database
table. This order determines the default sort order for the IDO, which might be overridden in the application
or elsewhere.
1
2
3

On the IDOs form, select the IDO to edit.
If necessary, check out the IDO.
Click Advanced Attributes.

Infor Mongoose Core Extensibility Guide | 24

IDOs
In the Advanced IDO Attributes form, the IDO Properties list shows the properties of the IDO that can
be designated as primary keys. The Primary Keys list indicates the designated primary keys detected
by the system and their sort order.
4

Optionally, perform any of these actions:
•
Add primary keys: Select a property and click Add.
•
Set the sort order for the primary keys: Use the Up and Down buttons.
•
Remove primary keys: Select a primary key and click Remove.

5

Optionally, select (or clear) the Quote Table Aliases option.
This option controls whether appropriate quoting is applied to table aliases during SQL generation for
the tables in this IDO.
To protect table aliases that represent SQL keywords or reserved words in the query language of the
server hosting the table’s database, select this option.
If you selected all table aliases to avoid duplicating SQL keywords, you can clear this option.

6
7

To save your changes and close the Advanced IDO Attributes dialog box, click OK.
In the IDOs form, save the IDO record.

Remember to check the IDO back in. Your changes do not become operative until you check them in.

Editing an IDO
After you make changes in the Application Database—for example, modification of tables or stored
procedures—you must update any IDO that is associated with the changed tables or stored procedures.
1
2
3

In the IDOs form, select the IDO to edit.
If necessary, check out the IDO.
You can make many required changes directly on the IDOs form.
Note: You might also want or need to open the IDO Tables form, the IDO Properties form, or the IDO
Methods form, and make any necessary adjustments there.
For example, you must update derived, subcollection, and unbound properties manually.

4

Save your changes.

Remember to check the IDO back in. Your changes do not become operative until you check them in.

Canceling or undoing changes to IDOs
After you check out an IDO and change it, you can cancel your changes before you check it back into the
Objects Database.
This is the procedure:
1
2

With the IDOs form open, select the IDO that has been checked out.
Click Undo Check Out.

You might have to refresh or close the form to see your changes.

Infor Mongoose Core Extensibility Guide | 25

IDOs

Deleting an IDO
Deleting an IDO removes all the methods and properties associated with it in the Objects Database.
You can delete an IDO only if you have first checked it out. To check out the IDO, you can use the IDOs form
or Form Control.
Note: You cannot delete an IDO that has an Access As identifier that is different from yours.
1
2

On the IDOs form, select the IDO to delete.
Click the trash can icon in the toolbar.
When prompted, click OK.

3

Save your changes.

IDO deletion rules
You can define rules that govern what happens when you (or another individual) attempt to delete an IDO.
These rules identify this information:
•
•
•

The IDO properties in this IDO that are referenced by another IDO
The name of the IDO that is referencing the IDO properties in this IDO
The properties on the referencing IDO that correspond to the referenced properties in this IDO

For each rule, you can specify one of these actions to take when one or more records match the rule definition
for deletion:
•

Restrict: Aborts the deletion and displays a message indicating that the delete operation is not allowed
For example: "Cannot delete record. At least one sales order exists for this customer."
Note: For Restrict actions, you must specify translatable application message strings to be substituted
in the delete operation.

•
•

Cascade: Allows the deletion but first deletes any referencing records (subcollections)
Remove: Allows the deletion but sets the referencing properties to NULL

Optionally, provide this information:
•
•

Specify the translatable message (Message Object Name) to display to the end user if the IDO deletion
is prevented.
Supply a filter (Filter) to further restrict the rows to which the delete policy applies.

Exporting and importing IDOs
Exporting IDOs and related elements
You can export IDOs, and/or their property classes and IDO assemblies, for use in a different Objects Database.
1

Open the IDO Export Wizard form and click Browse.

Infor Mongoose Core Extensibility Guide | 26

IDOs
2

In the Export File Name dialog box, specify a directory path and a filename for the export file to be
created.
The file type must be XML.
Note: In a partial trust situation or in the web client, there is no Browse button. In such cases, you have
these options to specify the path and filename in the Export File Name field:
•

Enter the full path and filename to a directory on your local computer; for example: C:\Temp\MyIDO.
xml

•

3
4

Specify just a filename. In this case, the system either prompts the user for a save location, or saves
the file to the user's default download location.

In the IDOs To Export section, select the desired IDO project from the Project Name drop-down list.
Select one of these options:
•
To export all the IDOs in the project, select All IDOs In Selected Project.
•
To export only some IDOs, select Selected and then select the IDOs you want from the list.
Note: To select multiple IDOs, Ctrl-click the desired IDOs.
Note: In cases where you want to export only property classes and/or IDO assemblies, select None.

5

In the Property Classes To Export section, select one of the these options:
•
To export all property classes in the current Objects database, select All.
•
To export only property classes used by the IDOs selected in the IDOs To Export section, select
Referenced by selected IDOs.
•
To export only some property classes, select Selected. Then select the property classes you want
from the list.

6

In the IDO Assemblies To Export section, select one of these options:
•
To export all IDO extension class assemblies in the current Objects, select All.
•
To export only IDO extension class assemblies used by the IDOs selected in the IDOs to Export
section, select Referenced by Selected IDOs.
•
To export only some IDO extension class assemblies, select Selected. Then select the assemblies
you want from the list.

7

When you have selected all the objects to export, click OK.

The IDO Export Wizard creates the XML file and saves it to the designated location. When finished, the wizard
closes automatically.

Importing IDOs and related elements
You can import IDOs, their property classes and IDO assemblies, from a different Objects Database.
1
2
3

Open the IDO Import Wizard form.
Click Browse.
In the Import File Name dialog box, locate and select the XML file to be imported.
The file must have been created with the IDO Export Wizard, and the file type must be XML.

Infor Mongoose Core Extensibility Guide | 27

IDOs

Note: In a partial trust situation or in the web client, there is no Import File Name field or Browse button.
Instead, you must click Import XML. Use the Import Binary Data From File dialog box to locate and
upload the XML file you want from your local computer.
4

Click Open.
The IDO Import Wizard displays all the IDOs, property classes, and IDO assemblies to be imported into
your Objects Database. Initially, all IDOs and objects are selected.

5

In the IDOs To Import section, select one of these options:
•
None: Import none of the IDOs.
•
All: Import all the IDOs contained in the XML file.
•
Selected: Import selected IDOs.
To select multiple IDOs, CTRL + click each IDO to include.

6

In the Property Classes To Import section, select one of these options:
•
None: Import none of the property classes.
•
All: Import all the property classes contained in the XML file.
•
Selected: Import selected property classed.
To select multiple property classes, CTRL + click each property class to include.

7

In the IDO Assemblies To Import section, select one of these options:
•
None: Import none of the IDO extension class assemblies.
•
All: Import all IDO extension class assemblies contained in the XML file.
•
Selected: Import selected IDO extension class assemblies.
To select multiple assemblies, CTRL + click each assembly to include.

8

Optionally, to convert the Access As identifier of the imported components to be the Access As value of
the target environment, select the Convert to local Access As check box.
Note: If the IDO project of the IDO to be imported has an Access As value different from the environment
in which you are currently working, you cannot convert the Access As value of that IDO collection.

9

Click OK.

The IDO Import Wizard imports the selected objects and saves them in your Objects Database. When finished,
the wizard notifies you.

Working with IDO tables
About tables used by IDOs
Although there are many aspects of IDOs (Intelligent Data Objects) that do not correspond directly to tables
in a SQL Server relational database, SQL Server tables provide the foundation on which any IDO is built:
•

All tables used by IDOs must have certain required columns and triggers.

Infor Mongoose Core Extensibility Guide | 28

IDOs
•
•

•

Each IDO must have at least one base table.
For most IDOs, primary key properties used by the base table in the application database are detected
automatically. However, primary key properties for IDOs based on a SQL View must be specified manually.
To identify the primary key properties and their sort order, set the advanced attributes for the IDO.
To include read-only information from associated tables in an IDO, use secondary tables.

About IDO base tables
Every IDO (Intelligent Data Object) must have at least one base table. IDO base tables are used to store all
updateable and persistent data associated with the IDO.
When you create an IDO, you must provide the name of the primary base table for the IDO. The primary base
table is the central table to which all other tables in the IDO are related in some way.

About IDO secondary tables
There are situations where you might want to include read-only information from associated tables in your
IDO. Suppose, for instance, that you store a "Unit of Measure" code in your base table. The description
associated with this code is stored in a separate Unit of Measure code table. You can publish the Unit of
Measure description on your IDO by adding a secondary table to the IDO.
Columns from secondary tables can be published in the IDO as read-only properties. Secondary tables do
not need to have the same primary key columns as the base tables, but you must specify the join criteria
needed to include columns from secondary tables in your IDO.

Adding base or secondary tables to IDOs
When you create an IDO, you must specify a primary base table.
If there are columns in other tables that have a relationship with the primary base table, and if you want to
expose these columns as properties in the IDO, you must also add the related table to the IDO as either a base
table or a secondary table.
You can add tables to IDOs only if you have first checked out the IDO for which you want to create the table.
Use the IDOs form to check out the IDO. Once the IDO has been checked out, you can click the New Table
button on either the IDOs form or the IDO Tables form to launch the IDO Table Wizard.
1

On the IDO Table Wizard form, provide this information:
Table Name
Select the table you want to add.
Table Alias
Provide a unique, valid SQL identifier to be used for this table. This alias is used to reference the table
in join specifications and in generated SQL. This is commonly a shortened version of the table name.

Infor Mongoose Core Extensibility Guide | 29

IDOs
Table Type
Select the type of table that represents the intended use of the new table:
•
Base: For additional tables that have a one-to-one relationship with the primary base table, share
the same key values as the primary base table, and are updated through the collection, select this
option.
•
Secondary: For tables that are added to an IDO and are not base tables, select this option. Columns
on secondary tables that are exposed as properties on the IDO are read-only.
Join Type
Select one of these options:
•
Inner: Select this option only if the columns referenced in the join are not nullable and the join is
on a foreign key.
•
Left Outer: Select this option in all other situations.
2
3

If you are adding a secondary table, specify the Join Conditions, using the fields provided.
Click OK.

The table and join conditions are added to the IDO.

Editing or removing IDO tables
If tables in the Application Database change, you must update the table definitions for any IDOs associated
with those tables.
Note: You can edit or delete most tables from an IDO, but you cannot edit or delete the primary base table.
1
2
3
4

Open the IDOs form and select the IDO you want.
If necessary, check out the IDO.
Click Tables.
Use the IDO Tables form to accomplish these actions:
•
To edit a table (including the join conditions), select the table and click Edit Table. Use the Edit
Table form to make and save your changes.
•
To delete a table, select the table and then select Actions > Delete.

The changes are finalized when you save the record.

Specifying join conditions for secondary tables
When you work with secondary tables, you must specify join conditions. You can accomplish this by selecting
parameters from options in the IDO Table Wizard form, in the IDO Tables form, or in the Edit Table form.
For more complex join conditions, you can specify the join conditions manually.
Specifying join conditions by selecting parameters
To specify join conditions by selecting parameters, use this procedure:

Infor Mongoose Core Extensibility Guide | 30

IDOs
1
2
3
4
5
6

In the first Join Conditions field, select the column in the secondary table that is being used to join to
the base table.
In the second field, select the primary or other base table being joined to.
In the third field, select the column in the base table being used for the join.
To add the join conditions statement in the Join Text edit field, click Add.
Optionally, to add other join conditions as needed, repeat the steps so far.
Click OK.

Specifying join conditions manually
You can specify join conditions manually, if you wish, in either the IDO Table Wizard form, the IDO Tables
form, or the Edit Table form.
To do this, specify each join condition as a SQL SELECT statement in the Join Conditions edit field. You can
specify multiple join conditions using AND statements.

Tip: Using multiple base tables
Most IDOs should have only a single base table. Sometimes, however, you might find it useful to implement
an IDO with multiple base tables. Normally, multiple base tables are required only when you are working with
an existing database schema that was designed for other purposes.
Non-primary base tables function as extensions to the primary base table. They must have the same key
columns as the primary base table. Non-primary base tables can store either of these types of data:
•
•

Required data, where a corresponding record must exist for each record in the primary base table
Optional data, where a corresponding record might or might not exist for each record in the primary base
table

If a non-primary base table stores optional data, then it must be joined using a left outer join.
It is the responsibility of the Insert trigger on the primary base table to insert records into each required base
table.
It is the responsibility of the Delete trigger on the primary base table to delete records in all related base
tables, whether they are optional or required.

Working with IDO properties
About IDO properties
IDO properties can be of four different types.

Infor Mongoose Core Extensibility Guide | 31

IDOs
Bound properties
Bound properties are persistent properties whose values are stored in an Application Database table. This is
the most common type of property.
Derived properties
Derived properties are properties whose values are derived from SQL expressions. You can use derived
properties to calculate values, to execute subqueries, or to call SQL functions.
Unbound properties
Unbound properties are properties whose values are not stored in a database table and consequently are
not persistent. Use this type of property to pass temporary values from a form to an IDO. This data can be
used by a custom insert, update, or delete method.
Subcollection properties
A subcollection property is a property that specifies a child IDO that is filtered from a parent IDO. A subcollection
is the child IDO whose returned collection is associated with, and dependent on, the objects returned in the
primary collection belonging to the parent IDO. Subcollections are the principal mechanisms for defining
hierarchical or parent-child data relationships. Use subcollections to implement one-to-many relationships
between IDOs.
The typical implementation of order lines in a business application is a good example of a subcollection. Each
order returns a collection of order lines. The system would define the order lines as a subcollection of the
Orders IDO.
Note that order lines are dependent on orders; that is, order lines cannot exist independently of their parent
orders. However, this is not a requirement of subcollections in general. For instance, you might define a
collection of customers as a subcollection of the collection of account managers. Each account manager has
a set of customers that he or she deals with. Customers can exist totally independently of their account
managers. It should be possible, for instance, to move a set of customers to a new account manager and
delete their old account manager from the system.
Subcollections can also be used to implement recursive data structures. A good example of this is a typical
implementation of a product structure or bill of materials. A product structure record typically includes a
reference to a set of child entities that are themselves product structure records. In this case, you could define
a Product-Structures IDO with a subcollection of product structures.
To define a subcollection, you must first define the collection class that characterizes the child IDO. Then you
must establish the relationship between the new child IDO and the parent IDO by creating a subcollection
property on the parent. In the case of orders and order lines, you would define an Orders collection and an
OrderLines collection. Then you would define the relationship between the two by defining a subcollection
property on the Orders IDO.

Infor Mongoose Core Extensibility Guide | 32

IDOs

Adding a property to an IDO
You can add properties to IDOs only if you have first checked out the IDO for which you want to create the
property. Use the IDOs form to check out the IDO. Once the IDO has been checked out, you can click the New
Property button on either the IDOs form or the IDO Properties form to launch the IDO Property Wizard.
1
2

In the IDOs form or the IDO Properties, select the IDO you want to create the property for, and click New
Property.
On the first page of the wizard, select the type of binding to use for the new property.
For information about these binding options, see About IDO properties on page 31.

3
4

Click Next.
Set the attributes for the type of property you selected:
•
For Bound properties, see Attributes for Bound properties on page 263.
•
For Derived properties, see Attributes for Derived properties on page 265.
•
For Unbound properties, see Attributes for Unbound properties on page 266.
•
For Subcollection properties, see Attributes for Subcollection properties on page 268.

5

When you are finished setting the property attributes, click Finish.

The IDO Property Wizard creates the property with the attributes you have specified and adds it to the list
of properties for the IDO.
You can further define the applicable attributes of the new property using the IDO Properties form. [Reminder:
Context-sensitive help is available for all fields/options on that form.]
Remember to check the IDO back in. Your changes do not become operative until you check them in.

Editing an IDO property
You can edit the IDO property for any table in the IDO. For example, you should edit bound properties after
the table column bound to the property is modified in the application database.
Note: You can edit IDO properties only if you have first checked out the IDO that has the properties.
1
2
3
4

In the IDOs form, select the IDO that has the property you want to edit.
If necessary, check the IDO out.
Click Properties.
In the IDO Properties form, select the property you want to edit and make your changes.
For details about the various fields and options, see the context-sensitive help.

5

Save your changes and close the IDO Properties form.

When you return to the IDOs form, your changes should now be reflected in the Properties tab grid. You
might need to refresh the form to see your changes.
Remember to check the IDO back in. Your changes do not become operative until you check them in.

Infor Mongoose Core Extensibility Guide | 33

IDOs

Adding or editing validators for an IDO property or property class
You can add, edit, or change the order of validators for an IDO property or property class.
1

Access the Edit Validators form, by clicking the ellipses (•••) button to the right of the Validators field in
either the IDO Properties form or the Property Classes form.
Note: Any validators you define here are executed by the IDO Runtime Service during a collection Save
operation. These validators are also inherited by any component on a form that is bound to this property
or to a property derived from that class.

2

On the first (main) page of the form, to add a new validator, click Add. To edit an existing validator, click
Edit.
If you click Add here, you go to the Select Validator Type page. The rest of this procedure assumes that
you selected the Add option.
If you click Edit, the page you go to is the correct one for that validator. For the options available on that
page, see the topic for that page.

3

On the Select Validator Type page, select the type of validator to add, and then click Next.
The correct page for the type you select displays.
For the specific options available with each option, see these topics:
•
•
•

In Inline List: See Edit Validators, Set Properties From Inline List page on page 246.
In IDO Collection: See Edit Validators, In Collection Validator page on page 245.
IDO Method: See Edit Validators, Select Validator Type page on page 246.

4

When you are finished making your specifications, click OK.
This returns you to the first page of the Edit Validators form.

5

Optionally, if you have multiple validators, you can rearrange the order in which the validators execute,
using the Move Up and Move Down buttons.
To save your work, close this form, and return any validator definitions to the parent form, click OK.

6

Creating an inline list
Note: This topic applies to the creation of inline lists to use in conjunction with IDO properties and property
classes.
An inline list is a set of "hard-wired" values for a property that can be applied to a component. Such a list is
typically used when the number of values to appear in a combo box or other display is limited and the values
will be constant and unchanging.
1
2

3

On the IDO Properties form or the Property Classes form, click the ellipses button (•••) to the right of
the Inline List field.
In the Edit Inline List form, determine the size of your inline list by adding rows and/or columns.
You can add any number of rows or columns, but for inline lists, it is usually a good idea to keep the
number small. You should decide before creating the list exactly how many rows and how many columns
your list needs to have.
Populate the rows and columns in the grid with values.

Infor Mongoose Core Extensibility Guide | 34

IDOs
The values in the grid cells can be any combination of literal values and translatable strings.
4
5

In the Column For Value field, specify by column number which column is to provide the values to any
components that use this IDO property or property class.
In the Display Columns field, specify by column number which columns are to have their values displayed
when the list is used.
If you want to display multiple columns, separate the column numbers with commas.

Be aware that, although you can use translatable strings in these lists, the IDO Runtime Service cannot access
the string IDs. You can, however, resolve the string IDs at the user interface level.
For example, suppose you want to create an inline list with this information:
•
•
•

A set of four severity levels: Low, Medium, High, and Severe
A translatable string value for each level: sLow, sMedium, sHigh, sSevere
A numerical value for each severity level, so that the list items can be presented in the correct order,
regardless of alphabetization or translation concerns

In this case, you would require two columns, one for the translatable strings and the other for the numeric
values. You would require four rows, one for each severity level.
Then, in the Column For Value field, you would want to specify the numeric column as the value for the
component using this property to use in creating the list in the correct order.
And finally, in the Display Columns field, you would want to specify the column containing the strings as the
values to display when the list is actually presented in the UI.
When you click OK in the Edit Inline List form, then, this metadata would be created and displayed in the
Inline List field:
ENTRIES(sLow\0,sMedium\1,sHigh\2,sSevere\3) DISPLAY(1) VALUE(2)

Working with IDO methods
About IDO methods
IDOs (Intelligent Data Objects) can use two kinds of methods: standard methods and custom methods.
Standard methods
All IDOs implement the methods LoadCollection, UpdateCollection, GetPropertyInfo, and Invoke.
•
•
•
•

LoadCollection retrieves a collection of rows from the database.
UpdateCollection takes a set of rows marked for insert, update, or delete, and executes the appropriate
SQL code on the database.
GetPropertyInfo returns detailed information about the properties supported by the IDO.
Invoke executes a custom method.

Infor Mongoose Core Extensibility Guide | 35

IDOs
Custom Methods
Custom methods are defined by the developer. You can define custom methods that are implemented in
Transact-SQL. Transact-SQL is the preferred programming language because it is easier to use for most of
these tasks and because the IDO forms provide useful facilities for defining methods based on these procedures.
There are two kinds of methods based on Transact-SQL stored procedures:
•
•

Methods based on stored procedures without a result set
Methods based on stored procedures with a result set

A stored procedure that does not return a result set can have both input parameters and output parameters,
but it does not select data to be returned to the caller.
You can use a method that returns a result set to the caller (a list of values) to populate collections. A typical
use is to populate drop-down list boxes in forms. You can also bind forms to these returned sets.

About IDO extension classes
An IDO extension class is a .NET class that allows developers to extend the functionality of an existing IDO by
adding methods and event handlers. IDO extension classes are compiled into a .NET class library assembly
and stored in the IDO metadata Objects Database. The IDO runtime loads these assemblies on demand and
calls methods and event handlers in the extension classes in response to IDO requests.
An extension class is short-lived: It is created at the start of a request and disposed of immediately when the
response is completed. Therefore, no state should be stored in an extension class.
Any public class in an IDO extension class assembly can be identified as the extension class for an IDO in the
IDO metadata Objects Database. IDO extension class assemblies are .NET framework class libraries that are
created using any of the .NET languages.
Note: For the list of currently supported .NET class libraries, see the Infor Mongoose Installation Guide.

Transactions and IDO methods
IDO methods can be run within the context of a transaction. To reduce transactional overhead and blocking,
you can set a method to run without a transaction.
Methods that do not update data in the database can likely be run without a transaction. A method that runs
a long time in a transaction, potentially blocking other transactions, may perform better if it is run without
a transaction. You can control the size of individual transactions by starting them and committing or rolling
them back within a stored procedure.
To set a method to run within a transaction, select the Transactional check box on the IDO Methods form.
At runtime, the method starts a transaction before the stored procedure is called.
To set a method to run without a transaction, clear the Transactional check box. The method does not start
a transaction before the stored procedure is called.

Infor Mongoose Core Extensibility Guide | 36

IDOs

Adding a method to an IDO
An IDO method is either based on a stored procedure in the application database or is an extension class
method. When customizing your application, you might need to add your own methods to an IDO.
You can add methods to IDOs only if you have first checked out the IDO for which you want to create the
method. Use the IDOs form to check out the IDO. Once the IDO has been checked out, you can click the New
Method button on either the IDOs form or the IDO Methods form to launch the IDO Method Wizard.
1

In the IDO Method Wizard form, make these selections:
Method Type
Select the type of procedure being called by this method. You can specify a method that either does or
does not return a result set.
Stored Procedure
Select the stored procedure to add. This is the stored procedure on which the method is based.
Note: If you selected a Stored Procedure option as the Method Type, this field is required. If you
selected an Extension Class option, this field is disabled.
Method Name
Enter the method to add. The method name must be unique to the IDO.
Note: If you selected a Stored Procedure option as the Method Type, this field is automatically populated
with the name of the stored procedure as well. You can change it if you want. If you selected an Extension
Class option, you must provide the name of the method. In this case, the name you provide here must
match exactly the name of the method as provided in the extension class code.
Transactional
If the method should be run as a transaction, select this option. Otherwise, leave it cleared.

2
3

Click OK.
Refresh the IDOs form.

The new method is added to the IDO. After you refresh, the method displays in the Methods tab.
Remember to check the IDO back in. Your changes do not become operative until you check them in.

Editing an IDO method
After you modify a stored procedure in the application database, any method that is associated with the
stored procedure must be updated to reflect the changes.
To accomplish this, use this procedure:
1
2
3
4
5

Open the IDOs form and select the IDO you want to edit.
If necessary, click Check Out.
Click Methods.
In the IDO Methods form, select the method you want to edit.
Use the avaialble fields to make your changes.

