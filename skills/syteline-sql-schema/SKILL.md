---
name: "syteline-sql-schema"
description: "Editing SQL schema - creating/updating tables, columns, primary keys, constraints, user-defined data types, restricted tables, SQL reserved words, UETs"
version: "1.0"
tags:
  - syteline
  - sql
  - schema
  - uet
  - mongoose
---

# syteline-sql-schema

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

Use this form to construct the message.
The Message Num column on this form is defined as a TBD (To Be Determined) field, which means that,
when you create a message and save it, the system automatically uses the prefix and assigns the next
available number as the suffix. When you take action to create a new application message, this field is
automatically populated with the prefix defined in Step 2. Numbers are automatically added to the prefix
when you save the message.
Note: Predefined core messages assigned to and used by Mongoose have the prefix "MG_". Other
Mongoose-based applications might have and use other prefixes.
For more information about constructing application messages, see Constructing Application Messages
on page 154.

5

Save the message.

Mongoose creates the application message with the prefix as defined on the System Parameters form and
increments the Message Num value by 1.
Continuing our example, when you create your first application message, the system automatically assigns
the message number "WW-IssTr-1" to it. The next message is created as "WW-IssTr-2", and so on.

Invoking and concatenating multiple application messages
You can construct calls to display multiple messages simultaneously. To do this, use multiple MESSAGE
expressions separated by pipe ( | ) symbols. The pipe symbol concatenates the messages.
For example, consider these MESSAGE expressions:
MESSAGE("E=NoExistforIs5", "@serial", "@serial.ser_num", V(SerNum)
, "@item"
, "@serial.whse", V(Whse)
, "@serial.item", V(Item)
, "@serial.loc", V(Loc)
, "@serial.lot", V(Lot)
, "@rsvd_inv.import_doc_id", V(ImportDocId)
)
| MESSAGE("E=AppLockFail3")

Infor Mongoose Core Extensibility Guide | 156

Application messages

At run time, this concatenation might evaluate to something like this message:
There exists no Serial Number where S/N is S/N1234 for Item
that has [Whse: MAIN] and [Item: BK-27000-0007] and [Location: STOCK] and
[Lot: LOT00012345] and [Import Doc Id: DocId000123456].

Creating application message objects
Use the Maintain Application Message Objects to create and modify application message objects that can be
used in displaying application messages of various types.
For example, suppose that an existing message number MG_100001 has the text "The &1 entered is not valid."
You could reuse this message number and text to construct a custom constraint error message.
1
2
3

Open the Maintain Application Message Objects form and execute Filter-In-Place.
Initiate a new message object.
In the Object Name field, specify a name for a SQL constraint.
Use the rules as specified in the Object Name on page 381 field description.

4

Perform one of these actions:
•
If you know the message text you want to display: From the Message Text drop-down list, select an
existing message.
If you perform this action, the Message Num field is automatically populated with the associated
message number.
For example, if you were to select the entry for "&1 must not be between &2 and &3.", then the
Message Num field is populated with the value "MG_89".
•

If you know the message number for the message you want to display: From the Message Num field,
select the message number.
If you perform this action, the Message Text field is automatically populated with the associated
message text.
For example, if you were to select the entry for "MG_89", then the Message Text field is populated
with the text "&1 must not be between &2 and &3.".

5

In the Message Desc field, specify a description for how and when the message is intended to be used.
For example: "Call to a remote method in a DLL"

6

From the Message Type drop-down list, select the type of message it is.
For information about the available message types, see Message Type on page 374.
Note: For any given Object Name, you can have only one Message Type.

7

From the Object Type drop-down list, select the type of object that the message object is intended to
be used with.
For information about the available object types, see Object Type on page 382.

8

In the Message Severity field, specify the level of severity represented by the message object.
For information about levels of message severityy, see Message Severity on page 373.

Infor Mongoose Core Extensibility Guide | 157

Application messages
9

Save the record.
If the Message Type is 5 Standard Message Or Text, you are finished.
If the Message Type is 17 Constraint Message or 18 Delete Constraint, the Build Messages grid is
enabled, where you can specify additional information, according to these guidelines:
•
•
•

If the Message Text in the main grid has multiple substitution expressions (&1, &2, and so on), add
a row with a Sequence Number corresponding to each of the substitution expressions.
To reference the substitution text to use, either select from the Message Num drop-down list a
message number; or, optionally, in the Object Name field, select another, existing message object.
A message number (Message Num) is always required.
To indicate that the value in the Object Name field is to be used for the substitution text, select the
MG_1 Reserved Message Placeholder option, and then select the desired Object Name.
If the Message Number is any value other than MG_1, then the application uses that value to look
up the message text on the Maintain Application Messages form; and it ignores any value in the
Object Name field.

•

The Message Text field displays the text to be displayed in place of the substitution expression.
If the Message Num is anything other than MG_1, then the text for the selected message number
displays. If the Message Num is MG_1 and an Object Name is selected, then the text for the selected
message object displays.

10 Save the record again.
11 Optionally, if your company uses source code control, click Generate Message Script File. Then, in the
Generate Application Messages Script form, specify the appropriate file path and filter information,
and click Generate SQL Script File.

About constraint exception messages
When a SQL constraint exception is thrown from the application database, the IDO Request layer catches the
exception and can build a translatable message from the SQL constraint name and type, if a message for that
constraint name exists in the ObjectMainMessages table. Different constraints can use the same basic message,
which varies only by the different object names that are referenced in the message text.
For example, many constraint exceptions could be reported to a user with this basic message:
The &1 entered already exists.

In this example, the &1 substitution expression could refer to any one of hundreds of different objects.
However, the text to be substituted (or the object name that references the text) cannot be passed when the
constraint exception occurs. The IDO Request layer can only pick up the constraint name and type from the
caught exception. The ObjectBuildMessages table contains child records that reference either a message
number defined on the Maintain Application Messages form or another object name, as defined on the
Maintain Application Message Objects form.
Thus, the same base message from the ApplicationMessage table can be used by many different constraints,
each of which defines a different set of references for the substitution text placeholders in the message.

Infor Mongoose Core Extensibility Guide | 158

Editing schema (SQL tables and elements)

Chapter 6: Editing schema (SQL tables and elements)

About maintaining tables and other SQL schema elements
You can maintain tables and other SQL schema elements from within your Mongoose-based application.
Among other things, you can do these things:
•
•
•
•
•

Create SQL tables
Add, delete, or modify columns on tables
Define one or more primary keys or other constraints
Update existing SQL tables
Edit SQL user-defined data types

Note: We do not currently support the building of IDOs over tables in schemas other than dbo at this time.
System administrators can use the Metadata Access Controls form to restrict access to application metadata
and prevent users from altering Core SQL tables. You cannot modify SQL tables that are set to Locked in the
Metadata Access Controls form. You can view SQL tables that are set to Read Only but you cannot modify
them. You can view and modify only SQL tables that are set to Unlocked on that form.

Creating SQL tables
To create SQL tables, use the Sql Tables form.
1 In the Sql Tables form, execute Filter-In-Place and initiate a new table.
2 Verify that the Schema is dbo.
Note: We do not currently support the building of IDOs over tables in schemas other than dbo at this
time.
3
4

Specify the Table Name.
Optionally, to include a SiteRef column, which allows for different data for different sites, select the
Multi-Site option.
Note: If you select this option, these additional requirements apply:
•
•

All primary and unique keys for the table, and any non-unique indexes must contain the SiteRef
column. See Specifying Primary Keys and Other Constraints for a Table on page 161.
In the Application Schema Tables Metadata form, select this table and specify a name in the View
Name field.

Infor Mongoose Core Extensibility Guide | 159

Editing schema (SQL tables and elements)

Use the View Management form to generate a view over the table. The standard convention is to
name multi-site tables so that they end in _mst, and then use the table name without the _mst for
the view name.
•

Build an IDO over this view to prevent one site’s data from being seen in other sites.

5
6
7

Save the record.
Click Columns.
In the Sql Columns form, add columns for the new table and define metadata for the columns.
This metadata can include specifications such as the data type, length, and default value (as applicable
to the data type).

8
9

Save the columns and close the Sql Columns form.
Back in the Sql Tables form, to open the Sql Tables Constraint form and define one or more primary
keys or other constraints for the table, click New Constraint.
See Specifying Primary Keys and Other Constraints for a Table on page 161.

10 To save the constraint and return it to the Sql Tables form, click Finish.
After you create tables or columns, you can create IDOs, IDO extension classes, or events that use the tables
and columns. You can also filter for a table in the Sql Tables form, and modify the columns and other attributes.
Note: This application requires certain columns on tables that it uses. To add those required columns to
your table, import the table into your database. You can then filter for it in the Sql Tables form, and click
Update Current Table, which action adds those required columns.

About maintaining columns on SQL tables
To add, delete, or modify columns on tables, use the Sql Columns form. However, understand that you cannot
make changes to certain restricted tables.
Caution: You can add columns, but do not delete or modify columns on existing base application tables.
Doing so can cause system instability.
Note: System administrators can use the Metadata Access Controls form to restrict access to core SQL
tables. You cannot modify SQL tables that are set to Locked in the Metadata Access Controls form. You can
view SQL tables that are set to Read Only but you cannot modify them. You can view and modify SQL tables
that are set to Unlocked.
To add a new column to a table that already contains columns, the new column must meet at least one of
these conditions:
•
•
•

Allows nulls
Has a default definition specified
Is an identity or timestamp data type

If none of these conditions is true, then the column can be added only to an empty table.
You can also change the definition of an existing column, for example, the data type.

Infor Mongoose Core Extensibility Guide | 160

Editing schema (SQL tables and elements)

Specifying primary keys and other constraints for a table
You can define one or more primary keys or other constraints for a SQL table.
1

On the Sql Tables form, click one of these buttons:
•
To define a new constraint, click New Constraint. Then specify a Constraint Type and its related
information:
Primary Key
Specify whether the constraint should be implemented with the Clustered Index attribute.
Index
Specify whether the constraint is Unique (that is, only one unique combination of the columns
contained in the constraint is permitted in the table); and whether the constraint should be
implemented with the Clustered Index attribute.
Foreign Key
From the Referenced Table drop-down list, select the table to which the current table refers.
•

To change an existing constraint, select the constraint from the grid and click Modify Constraint.
The data type and constraint name display. You can modify any active fields/options.

2
3

Click Next.
In the Columns panel, select the column or columns you want to be constraints on the table.
To add them to the Keys pane, click Add.
To change the order of the columns on the constraint, use the Move Up or Move Down buttons.

4
5

To delete an existing constraint, remove all columns in the Keys panel, by selecting them and then clicking
Remove.
To save your changes and return to the Sql Tables form, click Finish.

Updating existing SQL tables
To update a table that you imported into the application database, use the Sql Tables form. These are tables
that were not created using the Sql Tables form.
1
2

Open the Sql Tables form and execute Filter-In-Place.
Select the table you want to update.
Note: System administrators can use the Metadata Access Controls form to restrict access to core SQL
tables. SQL tables that are set to Locked in the Metadata Access Controls form are not available in the
list. You can select SQL tables that are set to Read Only but you cannot modify them. You can view and
modify SQL tables that are set to Unlocked.

3

Verify that the Schema is dbo.
At this time, the only schema that is supported is the dbo schema.

4

Click Update Current Table.

Infor Mongoose Core Extensibility Guide | 161

Editing schema (SQL tables and elements)

Note: If the selected table is set to Read Only in the Metadata Access Controls form, then this button
is disabled.
The application performs these actions on the table:
•
Add the standard Mongoose-based application columns such as Create Date and Updated By.
•
Create the Delete, Insert and UpdatePenultimate triggers.
•
Add application schema table metadata.

Editing SQL user-defined data types
You can use the Sql Tables form to create or edit user-defined data types (UDDTs).
1
2
3

In the Sql Tables form, select the table for which you want to create a new data type, click SQL Data
Types.
In the SQL Data Types form, execute Filter-In-Place and initiate a new record.
Provide this information:
Schema
Verify that this is set to dbo.
Note: We do not currently support the building of IDOs over tables in schemas other than dbo at this
time.
Data Type
Select the base data type to use, or provide the name of your new UDDT.
System Data Type
Select the type of system data on which your UDDT is to be based.
Depending on the type you choose here, provide appropriate values for the Length, Precision, and
Scale attributes.

4

Save your work and close the SQL Data Types form.

Optionally, to change a data type, SQL Server requires that the data type be dropped and recreated. To drop
a data type, it must not be in use by a table, stored procedure, or function. Perform these steps:
1 Filter for the data type and verify that the Where Type Used grid is empty.
2 Select Actions > Delete and save the record to drop the data type.
3 Select Actions > New, specify the information again, and save the data type record to recreate it.

Restricted tables
Do not add new custom columns to the tables in this list, and do not extend them with UETs. Customizations
to these tables are not preserved during upgrades to the application. Asterisks denote that the table is
associated with a form.

Infor Mongoose Core Extensibility Guide | 162

Editing schema (SQL tables and elements)

Note: Aside from these tables, system administrators can use the Metadata Access Controls form to restrict
access to core SQL tables. You cannot modify SQL tables that are set to Locked in the Metadata Access
Controls form. You can view or select tables that are set to Read Only but you cannot modify them. You can
select and modify SQL tables that are set to Unlocked. These restrictions apply when you modify SQL tables
in the Sql Tables form, the Sql Columns form, and the UET Table/Class Relationships form.
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

ABOPTS_mst
ALTCHG_mst
ALTCHGDTL_mst
ALTERN_mst
** ALTPLAN_mst (Planning Parameters form)
** ALTSCHED_mst (Shop Floor Control Parameters form)
ALTSUM_mst
APPCFG_mst
APSMSGS_mst
APSOPTIONS000_mst
APSSITE_mst
ATTRIB000_mst
BATCH000_mst
BATPROD000_mst
BATPRODORD000_mst
BATRL000_mst
BATSUM000_mst
BATTIME000_mst
BATWAIT000_mst
BOM000_mst
** CAL000_mst (Holidays form)
CONSPLAN000_mst
DOWN000_mst
DOWNPLAN000_mst
EFFECT000_mst
ERDBGW_mst
EXRCPT000_mst
FDBVER_mst
FIELDS_mst
GNTHLCAT_mst
GNTHLCRIT_mst
GNTPREFS_mst
GNTSELCAT_mst
GNTSELMBR_mst
INVPLAN000_mst
JOB000_mst
JOBLNKS000_mst
JOBPLAN000_mst
JOBSTEP000_mst

Infor Mongoose Core Extensibility Guide | 163

Editing schema (SQL tables and elements)
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
•
•
•
•
•

JS10VR000_mst
JS11VR000_mst
JS12VR000_mst
JS13VR000_mst
JS14VR000_mst
JS15VR000_mst
JS16VR000_mst
JS17VR000_mst
JS18VR000_mst
JS19VR000_mst
JS2VR000_mst
JS3VR000_mst
JS4VR000_mst
JS6VR000_mst
JS7VR000_mst
JS8VR000_mst
JS9VR000_mst
JSATTR000_mst
LOADPERF000_mst
LOADSUM000_mst
LOOKUP000_mst
LSTATUS000_mst
MATADDQ000_mst
MATDELOUT000_mst
MATL000_mst
MATLALT000_mst
MATLATTR000_mst
MATLDELV000_mst
MATLGRP000_mst
MATLPBOMS000_mst
MATLPLAN000_mst
MATLPPS000_mst
MATLRULE000_mst
MATLWHSE000_mst
MATREMQ000_mst
MATSCHD000_mst
MATSUM000_mst
MSLPLAN000_mst
OPRULE000_mst
ORDATTR000_mst
ORDER000_mst
ORDGRP000_mst
ORDIND000_mst
ORDPERF000_mst

Infor Mongoose Core Extensibility Guide | 164

Editing schema (SQL tables and elements)
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
•
•
•
•

ORDPLAN000_mst
ORDSUM000_mst
OSMATL000_mst
PART000_mst
PARTSUM000_mst
PBOM000_mst
PBOMMATLS000_mst
PDBSIZES000_mst
PLANINT000_mst
POEXCEPT000_mst
POLSCHD000_mst
POOL000_mst
POOLQ000_mst
POOLSUM000_mst
PROBDEF_mst
PROCPLN000_mst
RELRECS_mst
REPPAR_mst
RESATTR000_mst
RESLOAD000_mst
RESMNT000_mst
RESPAIR000_mst
RESPLAN000_mst
RESQ000_mst
** RESRC000_mst (Resources form)
RESSCHD000_mst
RESSEND000_mst
RESSUM000_mst
RGATTR000_mst
RGLOAD000_mst
** RGRP000_mst (Resource Groups form)
** RGRPMBR000_mst (Resource Groups form)
RGRPSUM000_mst
SCHEDOP000_mst
** SHIFT000_mst (Scheduling Shifts form)
** SHIFTEXDI000_mst (Resources form, Shift Exceptions tab)
TBLLIST000_mst
TODEMAND000_mst
TOODP000_mst
TOSUPPLY000_mst
TRACELOG000_mst
WAIT000_mst
WHSE000_mst

Infor Mongoose Core Extensibility Guide | 165

Editing schema (SQL tables and elements)

SQL reserved words
This application reserves these words, so that they cannot be used as IDO property names or SQL table column
names:
add
all
alter
and
any
as
asc
authorization
avg
backup
begin
between
break
browse
bulk
by
cascade
case
check
checkpoint
close
clustered
coalesce
column
commit
committed
compute
confirm
constraint

Infor Mongoose Core Extensibility Guide | 166

Editing schema (SQL tables and elements)
contains
containstable
continue
controlrow
convert
count
create
cross
current
current_date
current_time
current_timestamp
current_user
cursor
database
dbcc
deallocate
declare
default
delete
deny
desc
disk
distinct
distributed
double
drop
dummy
dump
else
end
errlvl

Infor Mongoose Core Extensibility Guide | 167

Editing schema (SQL tables and elements)
errorexit
escape
except
exec
execute
exists
exit
fetch
file
fillfactor
floppy
for
foreign
freetext
freetexttable
from
full
goto
grant
group
having
holdlock
identity
identity_insert
identitycol
if
in
index
inner
insert
intersect
into

Infor Mongoose Core Extensibility Guide | 168

Editing schema (SQL tables and elements)
is
isolation
join
key
kill
left
level
like
lineno
load
max
min
mirrorexit
national
nocheck
nonclustered
not
null
nullif
of
off
offsets
on
once
only
open
opendatasource
openquery
openrowset
option
or
order

Infor Mongoose Core Extensibility Guide | 169

Editing schema (SQL tables and elements)
outer
over
percent
percision
perm
permanent
pipe
plan
prepare
primary
print
privileges
proc
procedure
processexit
public
raiserror
read
readtext
reconfigure
references
repeatable
replication
restore
restrict
return
revoke
right
rollback
rowcount
rowguidcol
rule

Infor Mongoose Core Extensibility Guide | 170

Editing schema (SQL tables and elements)
save
schema
select
serializable
session_user
set
setuser
shutdown
some
statistics
sum
system_user
table
tape
temp
temporary
textsize
then
to
top
tran
transaction
trigger
truncate
tsequal
uncommitted
union
unique
update
updatetext
use
user

Infor Mongoose Core Extensibility Guide | 171

Editing schema (SQL tables and elements)
values
varying
view
waitfor
when
where
while
with
work
writetext

Infor Mongoose Core Extensibility Guide | 172

User-extended tables (UETs)

Chapter 7: User-extended tables (UETs)

About user-extended tables (UETs)
The user-extended tables (UET) feature gives system administrators the ability to extend existing application
database tables and add custom user fields to forms in the application. Use this feature to keep track of
information that is not currently in the application database schema.
Note: If you are using replication, you must click the Regenerate Replication Triggers button on the
Replication Management form after UETs are changed, added, or deleted.
This application comes with SQL tables that contain predetermined fields. Using the UET capability, you can
add your own fields to these tables.
Note: System administrators can use the Metadata Access Controls form to restrict access to application
metadata and prevent users from altering core SQL tables. You cannot modify SQL tables that are set to
Locked in the Metadata Access Controls form. You can view SQL tables that are set to Read Only but you
cannot modify them. You can view and modify SQL tables that are set to Unlocked.
Once a table is extended, you can add your own fields to any form that uses the table.
If you bind a new component to a UET on a form that uses a custom load method stored procedure, an error
message displays when you refresh the form. You can close the error message and continue. Only the primary
table of the form's primary collection is extensible on that form.
When data is entered into pre-existing fields, and if the rule expressions you defined for those fields are true,
the events to arrange and display information in new custom user fields are triggered.
Reports
You can view information about UET user classes, user fields, user indexes, and user tables, on these reports:
•
•
•
•

Quick Dictionary Report
User Class Report
User Fields Report
User Index Report

Infor Mongoose Core Extensibility Guide | 173

User-extended tables (UETs)

Associating user-defined fields with a user class
You can associate user-defined fields with a user class.
1
2
3
4
5

Open the UET Class/Field Relationships form and execute Filter-In-Place.
Initiate a new record.
From the Class Name drop-down list, select the user class.
From the Field Name drop-down list, select a field name to associate with the class.
Save your work and close the form.

Extending application database tables
You can add custom user fields to forms by extending application database tables.
1

Create a user class.
The user class definition is the highest level at which you can extend an application database table.
See Creating a User Class on page 176.

2

Create the user fields.
User-defined fields are generic and can be a part of many classes. If the user changes any property of a
user-defined field, then all user classes inherit the change.
See Creating User Fields on page 177.

3

Associate the user-defined fields with the user class.
The user-extended table (UET) tools look for this association to place the user-defined fields in the form
that belongs to the user class.
See Associating User Fields with a User Class on page 174.

4

Define the index for the class.
Users who generate their own reports or browse through the classes can take advantage of using an
index. This gives users the ability to define their own sorting process in reports. You do not need to define
the index for a class, but if you do not, and you sort these fields in custom reports, then the system
performance can slow down.
See Defining an Index for a Class on page 178.

5

Create a relationship between an application database table and the user class.
The association between a table and a class provides the information that the UET needs to be able to
retrieve, arrange, and display the user-defined fields that belong to a user class. To link the table with
the class, define a rule that determines if the record accessed has a valid user class associated with it. If
valid data is entered in existing fields to make the rule expression true, then the new user-defined field
displays.
See Creating a Relationship Between a Database Table and a User Class on page 175.

6

Impact the schema.

Infor Mongoose Core Extensibility Guide | 174

User-extended tables (UETs)
Use the UET Impact Schema form to apply the changes you made in the previous steps to all affected
databases. This step also updates the corresponding views over multi-site tables.
See Impacting the Schema on page 179.
7

Draw the user-defined fields on forms.
Draw the user-defined fields on the forms that have extended tables associated with them. When the
user-defined fields are placed on the form, they act like any other existing field.
See Drawing UET Fields on Forms on page 178.

Copying a user-defined field
You can copy a user-defined fields from user-extended tables.
1
2

On the UET User Fields form, select the user-defined field you want to copy.
Click Copy Field.
The UET Copy Field form opens.

3
4
5

In the Table field, select a table for the new user-defined field.
In the Column field, select a column for the new user-defined field.
Click OK.
This returns you to the UET User Fields form.

6
7

Rename the new copy of the user-defined field.
Optionally, change the attributes for the new user-defined field.

Creating a relationship between a database table and a
user class
1

Determine the name of the primary table for the form:
a Open the form to which you want to add a user-extended table (UET) field.
b Launch the Web Designer.
c If the Form Properties sheet is not displaying, select the Form tab.
d Select the Collections tab on the property sheet.
Note: In the Web Designer, this tab is located on the Details panel.
e
f

In the Collections tree, select the primary collection.
Take note of the primary table name, as displayed in the Base Table and Alias property field.

Note: System administrators can use the Metadata Access Controls form to restrict access to application
metadata and prevent users from altering core SQL tables. You cannot modify SQL tables that are set to

Infor Mongoose Core Extensibility Guide | 175

User-extended tables (UETs)

Locked in the Metadata Access Controls form. You can view or select tables that are set to Read Only
but you cannot modify them. You can select and modify SQL tables that are set to Unlocked.
2

Associate a user class with the application database table:
a Open the UET Table/Class Relationships form, and execute Filter-In-Place.
b Initiate a new record.
c In the Table Name field, select the table you noted in the previous step.

