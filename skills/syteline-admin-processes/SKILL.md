---
name: "syteline-admin-processes"
description: "Process defaults and TaskMan - process default parameters, background task definitions, task scheduling, task history, TaskMan service configuration"
version: "1.0"
tags:
  - syteline
  - admin
  - process-defaults
  - taskman
  - background-tasks
  - mongoose
---

# syteline-admin-processes

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 5: Process defaults
About process defaults
Process defaults are settings that system administrators can make to control various aspects of how the
system responds to user commands and actions. In some cases, process defaults are used to supply default
data values, such as what server is used for objects metadata, for processes that require input data.
Process defaults are global settings; that is, they operate whenever and wherever in the system that the
condition for their operation exists. This means, among other things that a process default setting applies to
all sites in a database.
Many process defaults act as on/off switches, either enabling or disabling certain system features or behaviors.
These process defaults typically use the numbers 1 and 0 to enable or disable the process. One of the possible
values is always considered the default value, which means that it represents system operations without the
process default being set. Generally, if you want to use the default value, there is no need to set it on the
Process Defaults form. Use the Process Defaults form only to set the process default to a non-default
behavior.
The Process Default Names form can be used to view the complete list of process defaults available on the
system, including those that are not currently active. This form is also used to set validation value types for
process defaults; that is, to define what kinds of values can be used for each process default.
Note:  Without validation values, it is possible for administrators to inadvertently use invalid values when
activating process defaults on the Process Defaults form. Among other things, validation values are useful
in preventing login failures in cases where an invalid value has been set to activate a process default on the
system.
The Process Defaults form is used to actually activate process defaults and set their values (normally to a
valid non-default value). You can also use this form to determine what process defaults are currently active
on the system.
Setting a process default value
Process defaults are set or activated using the Process Defaults form.
1 
Open the Process Defaults form.
2 
Initiate a new record.
3 
Select the process default you want to set/activate from the Default Name field.
Infor Mongoose Administration Guide | 69
 
Process defaults

4 
In the Default Value field, provide a valid value for the process default.
To set or learn the values that are valid for each process default, use the Process Default Names form;
or see List of process defaults on page 71.
5 
Click Save.
Specifying validation value types for process defaults
To view a complete list of the process defaults available on the system, use the Process Default Names form.
You can also use the same form to register new process defaults that are available and to specify the types
of values that are considered valid for each process default.
Note:  Predefined process defaults—that is, process defaults that are included with your Mongoose-based
application—already have appropriate validation types specified for each process default. You need this
procedure only if you are creating your own custom process defaults.
1 
Open the Process Default Names form.
2 
Initiate a new record.
3 
In the ID field, enter the identification number (positive integer) of the process default for which you are
specifying a validation type.
Note:  Unless you are an Infor (Vendor) developer, use a value in this field that is equal to or greater than
10,000.
4 
In the Default Name field, enter the name of the process default for which you are specifying validation
value types.
Note:  This name is case-sensitive. For example, "Test" as a process default name is not the same as
"test".
5 
To allow validation of the value being used for this process default during user login, select the Enable
Validation option.
6 
If you selected the Enable Validation option in the previous step, specify from these options the type of
values that are considered valid:
AlphaNumeric
This option considers all alphabetic and numeric values to be potentially valid. Most special characters
are allowed. The special characters that are NOT allowed include these characters: ^  $  +  =  <  >  `  ~ 
As an example, the Report paper size process default uses this option for validation.
Numeric
This option allows only non-negative integers.
As an example, the Round Robin Batch Size process default uses this option for validation.
In List
This option considers only members of the list, as defined here, to be valid values. Any values used on
the Process Defaults form must match exactly one of the values from this list. Any other value used for
the process default generates an error on login.
Infor Mongoose Administration Guide | 70
 
Process defaults

The majority of on/off (or yes/no or true/false) toggle-type process defaults use this type of validation.
Regular Expression
This option allows the use of any regular expression. The possible limitation of this option is that the
expression itself is not validated; the Process Defaults form only validates the expression against the
expression input.
As an example, the FormSync notifications email address process default uses this regular expression
for validation: ^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$
7 
Save.
To activate the process default on your system, create an entry for it on the Process Defaults form.
List of process defaults
Process defaults are settings that system administrators can make to control various aspects of how the
system responds to user commands and actions.
This table lists the process defaults that are included with the system. Where a default value is given, it means
that the process default value has not been set on the Process Defaults form.
There are also application-specific process defaults.
Description/Valid Values
Process Default
This process default specifies the license module for an admin user. The default value
is MGCoreTrans.
Admin License
Module Name
This process default specifies which email address the Application Event System is
to use to send notifications.
If the value is set to 1 and both the intranet and site From Email fields have values,
the site From Email value overrides what is defined in the intranet level.
If the value is set to 1, but the site From Email value is blank, the From Email value
of the selected intranet is used.
If set to 0, the originator's email address is used.
AES From Email
use Intranet or
Site
Infor Mongoose Administration Guide | 71
 
Process defaults

Description/Valid Values
Process Default
If set to 1 (default), users can save minor changes, such as these, to a form made
during normal operations:
•
Changing the form size
•
Moving the splitter bar
•
Hiding either the grid view or the detail view
•
Changing the visibility of grid columns
•
Changing the sequence of columns in a grid display
If set to 0 (zero), this process default prevents such changes from being saved.
Note:  This is different from the ability of a user with editing permissions of Basic or
higher to save a user version of their form. This setting allows all users to save minor
run-time changes and does not require users to have Design Mode permissions.
Allow saving form
 runtime changes
If set to 1 (default), form developers can specify a scripting language other than the
default for individual scripts, effectively overriding the system setting for the default
scripting language.
If set to 0 (zero), form developers cannot specify alternate scripting languages for
individual scripts, but enforces the default scripting language setting (Default script
language).
Allow script lan-
guage selection
Deprecated.
If set to 1, this process default causes the Static labels on all required fields to display
an asterisk (*).
This process default has generally been superseded by the use of themes and by the
"Infor" theme in particular.
Asterisk as re-
quired indicator
This process default can be needed when running a report that contains browser-
based components as a background task. These browser-based components include
components such as charts or gauges. This is because the chart or gauge can be not
finished rendering before the report is processed.
This process default, then, allows you to set an extra amount of time, in milliseconds,
for the components to render before the report is submitted for processing.
This process default causes the Static labels on all required fields to display an asterisk
(*). If no other value is specified, a default value of 1000 milliseconds is used.
Browser Compo-
nent BG Render
Interval
A positive integer in the Default Value column specifies how many minutes to allow
a user session to remain idle before locking the user out. For example, if this process
default is set to 30, then, if a user were to leave the computer with the session still
running, after 30 minutes of inactivity, the computer is locked. To resume the session,
the user must enter the user password.
If this process default value is set to 0 (default), the system does not lock users out
of a session because of inactivity.
Browser inactivity
lock minutes
This process default determines the interval, in minutes, at which the system checks
the Inbox for new messages, and sends a notification.
This process default value must be a positive integer.
Check for New
Message Interval
Infor Mongoose Administration Guide | 72
 
Process defaults

Description/Valid Values
Process Default
When set to the default value (0), this process default prevents the Log Monitor Service
from collecting messages of type Diagnostics.
If this process default is set to 1, messages of type Diagnostics are included in data
collection and can be displayed in the Log Monitor stand-alone utility.
If the timer labeled Send Diagnostics Log Until Time expires, this process default
automatically resets to 0.
Client Diagnostic
send to LogMoni-
tor
This process default determines the interval, in minutes, at which clients are to send
ping messages to the IDO layer, to indicate that the client is still connected and
working.
This process default is closely related to the Minutes to close orphan sessionsprocess
default.
Client minutes to
ping session
This process default specifies the global transaction isolation level to use for queries.
transaction isolation level on page 315
This setting affects collection-level queries for form collections and collection-level
validations. This setting does not affect SQL SELECT operations or other processing
coded in a stored procedure (method) call.
Valid values for this process default are:
•
Committed: Use this value when all queries should see only data for which the
query can get a shared lock.
•
Uncommitted: Use this value when queries can read and display data that has not
yet been committed in the database.
With this setting, you do not need to wait for other long-running transactions
accessing the same dataset to complete before your queries can complete.
However, an uncommitted record might be rolled back between the time the
query displays it and the timaae you want to save updates to it. You cannot save
a record if the uncommitted data gets rolled back at some time after the query,
because optimistic locking fails. You must refresh the record to get valid data
before making changes and saving.
In background tasks, you can override this process default value for individual stored
procedures or reports by setting the Background Task Definitions form's Isolation
Level field.
Form developers can also override this process default for individual form-collection
specifications, in-collection list sources, and in-collection validations by setting the
Read Mode property on the Collection properties sheet in Design Mode.
Collection read
mode
Use this process default to set Combined as a Toolbar Mode in the User Preferences
form as the default behavior of the toolbar. These are the valid values:
•
0 (default): There are no changes to the standard toolbar mode.
•
1: The main toolbar and Form menu options are combined into a single-level
toolbar.
Combined Tool-
bar User Prefer-
ence
Infor Mongoose Administration Guide | 73
 
Process defaults

Description/Valid Values
Process Default
This process default is used to set the mgShowName value.
Note:  When a user signs in and creates a Mongoose web session, both the Mongoose
URL and the web.config file are examined for the mgShowName value. This value
then affects both the overall session information along with extra data being sent to
the browser client with every form retrieved.
•
If set to 0 or blank, there are no changes to the current behavior.
•
If set to 1 or justOuter, the data-mgcompname attribute is added to the outer or
top level element.
•
If set to 2 or andValue, the data-mgcompname attribute is added to the outer or top
level element and the data value element.
•
If set to 3 or andGridCell, the data-mgcompname attribute is added to the outer or
top level element, the grid cell element, and the data value element.
Component Name
as HTML Attribute
If set to 1, the system honors character (upper/lower) case inputs when setting these
behavior properties:
•
Visible When
•
Required When
•
Enabled When
•
Theme Class When
•
Conditional Action event handlers
If set to 0, the the system ignores character case when setting these behavior proper-
ties.
Conditional ac-
tions case sensi-
tive
This process default is used to display time in the user's local time zone.
If set to 0 (zero), time is displayed based on the database site time zone.
If set to 1 (default), time conversion is applied based on your local time zone.
You can set your Local Time Zone on the Users form's Login Information tab or on
the User Information form.
DateTime in
Client Time Zone
This process default specifies where the labels appears for components. Valid values
are:
•
Side
•
Top
Default Compo-
nent Label Loca-
tion
This process default specifies the default scripting language for custom form or
global scripts. Valid values are:
•
Visual Basic (default)
•
Visual C#
Default script lan-
guage
This process default is used to disable form aliasing. Form aliasing is the procedure
to redirect a named form to another named form. When a form is opened, the corre-
sponding form alias will open instead. This also extends to actions, including hyper-
links, right-click menus, and the open form dialog.
If set to 0 (default), Form Aliasing is enabled.
If set to 1, Form Aliasing is disabled.
Disable Form
Aliasing
Infor Mongoose Administration Guide | 74
 
Process defaults

Description/Valid Values
Process Default
This process default instructs the system to send a notification email to the designated
recipients whenever failures or mismatched records are encountered during the lo-
calization process.
To be functional, this process default requires a valid email address in the Default
Value column.
To send notifications to multiple email recipients, enter the addresses separated by
semi-colons.
Email Notifica-
tions for Datalake
This process default specifies whether messages are entered in the audit log. Valid
values are:
•
0 (off, default): Messages are not entered.
•
1 (on): Messages are entered.
If the audit log is on, messages are automatically sent to the log when users log on
and when they open a form. Messages for other events can be generated if developers
create event handlers of the Add Entry to Audit Log response type.
Enable audit log-
ging
Use this process default to enable the application to automatically retrieve more
rows as the user scrolls through a grid, without requiring the user to click the option
to “Get More Rows”. The additional records are retrieved and load automatically as
soon as the vertical scrollbar has reached its end. At that point the number of new
records retrieved are limited by the effective record cap.
This process default is disabled by default (Default Value = 0). To enable it, add an
entry (Enable Automatic Get More Rows) on the Process Defaults form, and set the
value to 1.
Enable Automatic
Get More Rows
This process default is used to enable the prioritization of the incoming BODs pro-
cessing.
•
0 (false, default): The prioritization of processing the inbound BOD is disabled.
•
1 (true): The prioritization of processing the inbound BOD is enabled.
Enable Inbound
BOD Prioritization
This process default is used to view message logs remotely. Valid values are:
•
0 (default): Remote logging is disabled and records are deleted.
•
1: Remote logging is enabled.
Message logs are collected, stored, and displayed in the Log Monitor form.
We recommend that you disable this process default when not in use.
Enable Remote
Logging
Infor Mongoose Administration Guide | 75
 
Process defaults

Description/Valid Values
Process Default
If set to 1, this process default enforces a prefix on names, based on the Access As
identifier, for any user-created:
•
IDOs and tables
•
Table columns in non-user-created tables
•
Properties on IDOs that extend non-user-created IDOs
This allows for automatic upgrades.
Also, this process default restricts the user from updating the records of a Table Name
that does not start with the current AccessAs value in the In the Application Schema
Tables Metadata form and Application Schema Columns Metadata form.
A secondary purpose is to prevent users from deleting SQL tables and columns that
they did not create.
If not set or if set to 0, users can give duplicate names to these IDOs and components,
which can cause problems when it is time to upgrade.
See About enforced prefixes.
Enforce prefix
from Access As
If this process default is set to 1, the API LoadCollection requests returns an error
when:
•
Bookmark is not enabled
•
API exceeds FarmAPIMaxRecordCap setting
Note:  This process default only takes effect if FarmAPIMaxRecordCap value has been
set.
Error if API Ex-
ceeds Max Cap
This process default is used to control the scope of validation for any filters used for
LoadCollection requests. This process default has three valid values:
•
High : Validates filters for all sessions
•
Medium (default) : Validates filters for only automation sessions
•
Low : Does not validate filters at all
See About filter validation.
Filter Validation
This process default is used to store the contact email information that is required
in Form Sync upgrades and patch command line options.
Note:  This information can be overridden by the currently available -m and -mail
command line options.
If the email address is not available in the upgrade control file or on the patch com-
mand line, then Form Sync looks for this process default value by using the target
configuration.
FormSync notifica-
tions email ad-
dress
Infor Mongoose Administration Guide | 76
 
Process defaults

Description/Valid Values
Process Default
An internal setting sets a cap of 9 on recursive event handler calls generated by an
Application Event System (AES) event. When this limit is reached, the system stops
processing the relevant AES handler.
Use this process default to override that internal setting and allow more (or fewer)
than 9 recursive event handler calls from AES events. Use positive integers to specify
how many recursions to allow. For example, a value of 20 allows 20 recursions before
halting processing. A value of 3 here would allow only three recursions.
Caution:  This process default should rarely be used, and then only for emergency
or special-use cases. It is useful mostly for very specific troubleshooting cases and
should be used only if and as directed by an Infor Customer Support representative.
A high value for this process default could result in system disruptions.
Framework Event
Max. Recursion
Depth
If set to 1, the Check Out buttons for IDO editing forms are always disabled.
If set to 0 (default), the Check Out buttons are enabled when appropriate.
IDO checkout dis-
abled
This process default determines how the logical ID (LID) values for a SecurityUserMas-
ter (SUM) security role should be treated when synchronizing user records with Infor
OS Portal:
•
When set to 0 (the default) or left as null, the LID values are accepted.
A security role is only added to the user if the logical ID matches the current
Mongoose instance logical ID.
•
When set to 1, the LID values are ignored.
All security roles that are found in the SUM BOD are applied to the user.
Ignore LogicalID
in SUM Security-
Role
This process default specifies whether the admin license module is automatically
assigned to all users, while processing the inbound SecurityUserMaster BOD.
•
0 or null: The admin license module is not automatically assigned to all users.
•
1 (default): The admin license module is automatically assigned to all users.
Inbound SUM As-
sign Admin Li-
cense
This process default specifies whether to synchronize the Application Event System
inbox data with ION. If this process default is set to 1, then the system creates notifi-
cations to send to ION, and monitors incoming notifications, routing them into the
AES inbox.
This is part of the process required to allow end users at an enterprise that has both
a Mongoose-based application and ION to see and respond to notifications and
prompts from either system, in either system's user interface.
ION pulse inter-
face
Infor Mongoose Administration Guide | 77
 
Process defaults

Description/Valid Values
Process Default
This process default determines how the groups assigned to a user should be treated
when synchronizing user records with Infor OS Portal:
•
When set to 0 or left as null (the default), any groups that are assigned to the
user in Mongoose, but that are not referenced by (or known by) Infor OS Portal
are automatically removed from the user record in Mongoose during synchro-
nization.
•
When set to 1, the default behavior is overridden and the groups assigned to the
user are left intact in Mongoose. In this case, if you want to remove a group
membership for a user in Mongoose you must remove it manually on the Users
form.
Note:  This process default has no effect on the ability to add roles to users in Infor
OS Portal, which end up as group memberships for that user in Mongoose.
Keep Groups in
SecurityUserMas-
ter
This process default determines how the license module access assigned to a user
should be treated when synchronizing user records with Infor OS Portal:
•
When set to 0 or left as null (the default), any license modules that are assigned
to the user in Mongoose, but that are not referenced by (or known by) Infor OS
Portal are automatically removed from the user record in Mongoose during
synchronization.
•
When set to 1, the default behavior is overridden and the license modules as-
signed to the user are left intact in Mongoose. In this case, if you want to remove
a license module assignment for a user in Mongoose you must remove it manu-
ally on the User Modules form.
Note:  This process default has no effect on the ability to add license modules to
users in Infor OS Portal, which end up as license modules for that user in Mongoose.
Keep Modules In
SecurityUserMas-
ter
If set to 1, the application event system saves a set of state records while an event is
running.
If set to 0 (default), these state records are deleted.
If you need to view the state history of successfully executed events, you can tem-
porarily add this process default and set it to 1. However, the tables containing this
state history information grow quickly and adversely affect performance.
Keep successful
event states
Deprecated.
This process default was created as a temporary workaround for functionality now
provided in ION.
If set to 1, this process default directs that the BOD itself be searched for logical ID
information, when dealing with a multi-site environment.
Logical IDs aliased
in BOD
Infor Mongoose Administration Guide | 78
 
Process defaults

Description/Valid Values
Process Default
This process default specifies the number of minutes after a "heartbeat" ping that a
session can remain open after it has become unresponsive.
An unresponsive, or orphan, session is different from a session where the user is
simply inactive. For example, these situations can cause an unresponsive session:
•
Network problems such as a loss of connection
•
Problems with the primary domain controller
•
A restart of the computer where the user was logged into the client, after the
computer froze for some reason
If a session has a process currently running through the IDO layer, the session is not
closed, even if it has stopped sending ping messages.
If no value is set for this on the Process Defaults form, then orphan sessions are not
automatically closed. The interval at which the client sends a "heartbeat" ping is
specified in the Client Minutes to ping session process default.
Note:  After setting or resetting this value, you must restart the IDO Runtime Service.
After the restart, any new orphan sessions will be removed using the information in
this process default. However, orphaned sessions that existed prior to the restart
must be manually deleted from the database, using the Session Management form.
Caution:  Restarting the IDO Runtime Service terminates all application sessions, so
ensure that all users are out of the system before restarting this service.
Minutes to close
orphan sessions
This process default specifies whether the system is to treat strings as Unicode or
non-Unicode strings. Valid values include:
•
Default (default): Treats all literal strings as Unicode strings.
•
Ansi:Treats all literal strings as non-Unicode strings.
Use this option when a non-Mongoose database table makes use of non-Unicode
strings.
•
Smart:Looks at each string in turn and attempts to determine whether it is Uni-
code or non-Unicode and processes it accordingly.
Mongoose-based applications build queries that are submitted to the database using
literal strings. The literal strings that are used are normally SQL Server Unicode
strings.
In the case of non-Mongoose database tables, however, it is possible that non-Unicode
columns have been defined in the database tables. In that case, the 'where' clause
that uses a Unicode literal string fails to properly use the indexes defined on the table,
because it first converts the value in the column to a Unicode value in order to com-
pare it to the Unicode literal. This results in an index scan instead of an index seek.
This means that, if non-Unicode data is defined in a table, a non-Unicode literal is
desired, so that you get index seek operations instead of index scan operations.
Non unicode liter-
al
Infor Mongoose Administration Guide | 79
 
Process defaults

Description/Valid Values
Process Default
This process default specifies overrides to the default padding inherent with a
NUMSORTCHAR datatype field when both alphabetic and numeric characters are
used in an <alphaPrefix><numericSuffix> format or only numeric characters. If default
padding is overridden, this default also specifies what character should be used for
the padding.
Valid values for this process default include:
•
PAD(x)
•
LEFTPAD(y)
where x/y is a single standard keyboard character.
Note: 
•
When this process default is set, only new NUMSORTCHAR values are affected.
Once a NUMSORTCHAR value has been padded and persisted, this process default
has no further effect on that value.
•
The settings you make for this process default are global; that is, they affect all
NUMSORTCHAR data type fields, wherever they might appear in the application.
See About NUMSORTCHAR field padding.
NUMSORTCHAR
numeric pad
This process default specifies the name of the IDO metadata (objects) database that
is linked to the application database. This is created by Configuration Manager.
Objects metadata
linkage
This optional process default defines the form prefix to make Mongoose-based appli-
cation forms unique across all applications in the Infor OS Portal portal. The default
value is mongoose_.
The prefix depends on how the form IDs are handled in Infor OS Portal. You can use
the application name as the prefix.
Optional Context
Message Form
Prefix
This process default is used by the SecurityUserMaster (SUM) outbound BOD and the
SecurityRoleMaster (SRM) outbound BOD as an override to the site logical-Id for the
<SecurityRole> element.
The default value is empty, which means to use the "Message Bus Logical ID" for this
site (from the Sites form).
For a site with a message bus ID of:
lid://infor.mongoose.mongoose/default
The customer can specify that the security roles will have this LID:
lid://infor.mongoose.mongoose
In this case, the full security role will be:
<SecurityRole>
<ID lid="lid://infor.mongoose.mongoose">my-role</ID>
<Description>my role description</Description>
</SecurityRole>
Note:  This process default is intended for use primarily by system administrators
setting up the application for use with Infor OS Portal.
Outbound SUM
Role LID Override
Infor Mongoose Administration Guide | 80
 
Process defaults

Description/Valid Values
Process Default
When set to 1, this process default causes the system to continue client caching of
IDO and form metadata to XML files on client computers. This is typically done to allow
better, faster startup performance.
When set to 0 (default), this process default disables client caching of IDO and form
metadata.
See Maintaining or Discarding the Local Metadata Cache on page 145.
Persist WinStudio
metadata cache
This process default is used to specify a user-defined callout method whose purpose
is to prune out form names from an Explorer folder that should not be shown to the
current user. The specification must be in the form: IDOName.MethodName.
Note:  The IDOName and MethodName are determined by the application developers.
The method must be implemented by the application development team. If specified,
this method is called by the framework before each explorer folder node is expanded.
If you want to disable this process default, just change the name in the Default Value
field. For example, you might add "Disabled" to the end of the value.
See About “pruning” the Master Explorer folder display on page 151.
Prune Explorer
Folder Callout
This process default controls what users, if any, can sign in to using a remote session
across a network. Valid values are:
•
Allow: This setting allows any user with valid credentials to sign in and use the
smart client from a network-connected computer.
•
Deny: (Default) This setting prevents all users from signing in using a smart client
from a network-connected computer. .
•
PerUser: This setting allows for selective permission to sign in remotely. This
setting requires explicit permission for each user, using the Allow Remote Win-
Studio Sessions option on the Login Information tab of the Users form.
Remote WinStu-
dio Sessions
This process default is a fallback for the Report Batch Size property if the property
value is -1.
If the value of this process default is not defined, a default internal value of 5,000 is
used.
This value is the fallback for the report forms that have a Report Batch Size property
value of -1.
Note:  The value of this process default is not set initially.
Report Batch Size
This process default specifies the maximum number of records that users can retrieve
at a time on report-type forms or DataView reports.
Valid values include all non-negative integers.
This process default creates a separate record cap for reports and allows reports to
query more records than queries from other types of forms.
In a multi-tenant farm environment, this process default cannot be used to override
the FarmReportMaxRecordCap (farm-wide setting for reports).
Report max
record cap
Infor Mongoose Administration Guide | 81
 
Process defaults

Description/Valid Values
Process Default
When this process default is set to 1, all report output is directed into this path:
\\TaskMan_Path\Output Files\ReportName_GUID.FileExtension
All report files reside in the Output Files folder and are distinguished by the session
ID appended to the report name.
When this option is set to 0 (default), the report output destination depends on report
output settings on other forms.
Report output ob-
fuscation
This process default specifies what size of paper should be used when printing reports.
Valid values include most common paper sizes.
This setting can be overridden for any particular report output on either the Back-
ground Task Definitions form or the Report Options form. Custom paper sizes can
be defined on the Report Paper Sizes form.
See List of report paper size options.
Report paper size
This process default allows you to limit the amount of time that a web service REST
token is valid after its creation. The time limit is set in terms of seconds. When the
time limit is reached, the server no longer accepts the token, and the web service
can no longer use that token.
Valid values for this process default are [blank], 0 (zero), and positive integers. Values
of [blank], 0, or any non-valid value all disable this feature, which means that the
token never expires. By default, this process default is not set and is blank.
REST Token Life-
time (sec)
To hide the Help option in context menus, use this process default. You can also use
this parameter to globally disable right-click actions, thus, disabling all context
menus. This settings applies only in Runtime Mode.
Valid values for this process default include:
•
Enabled: The right-click action is enabled and the Help option is displayed in all
context menus.
•
EnabledWithoutHelp: The right-click action is enabled and the Help option is
hidden in all context menus.
•
Disabled: The right-click action is disabled and context menus are not displayed.
Note:  If you do not configure this process default parameter, then the right-click
context menu and the Help option are enabled by default.
Alternatively, with a form open in the Web Designer, to hide the Help option in the
context menus of your chosen form components, you can specify the NOHELP()
keyword as a Format attribute.
See NOHELP keyword.
Right-Click Con-
text Menu
This process default specifies how many configurations/databases the TaskMan is
to poll at one go. Valid values are positive integers.
If this process default is not defined, the system defaults to 1.
See About "round robin" polling.
Round Robin
Batch Size
Infor Mongoose Administration Guide | 82
 
Process defaults

Description/Valid Values
Process Default
This process default specifies the amount of time, in milliseconds, that TaskMan is
to pause, after one round is complete, before beginning the next round of polling.
If this process default is not defined, the system defaults to 1000 (one second).
See About "round robin" polling.
Round Robin
Polling Interval
(ms)
This process default specifies whether user overrides of record caps persist (are
saved) from one session to the next. Valid values are:
•
0 (disabled, default): Overrides are discarded once the user signs out. The selected
option for both User Data Record Cap and User List Record Cap sections in
User Preferences are cleared and use the default record cap.
•
1 (enabled): Overrides are saved even after the user signs out. The selected option
for both User Data Record Cap and User List Record Cap sections in User
Preferences are retained.
Save record cap
override
This process default is set to 1 by default.
This process default needs to be added by the user manually and not added by default.
This process default determines whether context messages are sent. If set to 1, the
context message is sent when the form is active.
Send Context
Message on Form
Focus
This process default designates a user ID to be used when no "From" data is available
for a Send Email event action. This user ID must be the username of a valid system
user.
This is typically required in situations where Send Email actions can be initiated in
response to a BodOnReceive event.
Service AES Origi-
nator
This process default designates a value in the SiteRef column of a multi-site table
created by the Data Maintenance Wizard instead of the default value, which is
"SiteRef".
Another purpose of this process default, in the Application Schema Tables Metadata
form, is to populate the Site Column Name field, if empty, with its value.
Also, the SQL Tables form uses this process default.
Site Reference
Column Name
This process default is used to enable the support of publishing of Infor Business
Context message.
If the Send Context Message on Form Focus process default is set to 1, the Support
Product ID is included in the context message.
Support Process
ID
Infor Mongoose Administration Guide | 83
 
Process defaults

Description/Valid Values
Process Default
This process default specifies TaskMan debug options. These options are immediately
applied to the TaskMan service and continue in effect until you delete the setting
from the Process Defaults form. You can specify one or more of the following, sepa-
rated by commas, semicolons, or spaces:
•
debug: This option runs the current TaskMan thread in debug mode. (Messages
are explained in the Creating and Customizing Reports guide.)
•
debugrep: TaskMan creates a text file containing a log of messages generated
during the running of a report. To use this option, a txt extension for a document
type must be defined in the Document Types form. This setting should only be
used for report debugging purposes, and must be removed as soon as testing is
done.
•
eventlog: When debugging background tasks (with the debug option above),
TaskMan directs its debug logs to the toolset messaging systems, which can be
viewed through the IDO Runtime Host or Log Monitor utilities. If you want to direct
TaskMan debug logs to the Windows event systems so you can view and debug
them through the Windows Event Viewer, use this option.
•
taskmsg: If this is specified, TaskMan inserts some task-specific messages while
processing requested tasks. This allows you to review the status/process of tasks.
These messages can be viewed in the Task Messages area on the Background
Task History form.
TaskMan options
This process default determines whether the system uses SQL Server session variables
or falls back to the SessionContextNamesBase table for backward compatibility.
•
if set to 1, SQL Server session variables are used, and only a single row is support-
ed. This is the default value.
•
if set to 0, the table containing the data is used.
Note:  The SessionContextNames is now a view that looks at either SQL Server session
variables or looks at a backward-compatibility table named SessionContextNamesBa
se.
Use session vari-
ables
This process default specifies the number of days of inactivity to allow before a user
account is disabled.
Valid values are all non-negative integers. A setting of 0 (default) indicates that there
is no threshold; in other words, user accounts are never disabled because of inactiv-
ity.
See Disabling Inactive Accounts on page 29.
User inactivity
threshold
Infor Mongoose Administration Guide | 84
 
Process defaults

Description/Valid Values
Process Default
This process default specifies the maximum number of records that users can retrieve
in a query.
Valid values include all non-negative integers. A setting of 0 (default) does not specify
a maximum number and honors the default system setting.
See the help topic "About caps".
Note:  Unlimited queries can adversely affect performance and exceed the resources
of the utility server and the client machine.
This process default is automatically enabled in on-premises environments. This
setting is disabled or displayed as read-only for multi-tenant farm environments
where farm-wide record caps have been set.
User Preferences
Max Record Cap
Infor Mongoose Administration Guide | 85
 
Process defaults

Chapter 6: TaskMan and background tasks
About the Infor Framework TaskMan service
Infor Framework TaskMan service (which, it should be noted, is different from the Windows Task Manager),
is a Windows service that:
•
Polls the Application Database, as scheduled, for tasks to run
•
Launches applications
•
Executes SQL stored procedures
•
Processes and generates reports
•
Runs IDO methods
Polling the Application Database
On startup, TaskMan connects to the Application Database. It then polls the Application Database to identify
new tasks to run. TaskMan uses a polling interval that is defined on the Intranets form.
When a task that is waiting to be run is found, the task information is queried and the appropriate process is
performed. This task information includes the executable file, the procedures or methods to run, the type of
task parameters involved, and the user name.
Executing SQL stored procedures
For a SQL stored procedure, TaskMan launches a database process thread. The database process then connects
to the Application Database. It logs in as the user who submitted the task. The stored procedure is then
executed using the process connection.
Launching applications
TaskMan launches a system process to execute any valid Windows application such as EDI (EDIImporter.EXE)
and the Scheduler (AIM_BATS.EXE), which enables the application to carry out its processing. The application
is executed under the user account used by the TaskMan service.
Infor Mongoose Administration Guide | 86
 
TaskMan and background tasks

Setting up the Infor Framework TaskMan service during
installation
Note:  This topic applies only to on-premises installations. For Multi-Tenant Cloud (MTC) customers, this
activity is performed by the Cloud Enablement Team.
TaskMan is installed and configured as part of the installation. If you are not using an All-In-One database
configuration, we recommend that you install TaskMan on the Application Server. Much of the TaskMan setup
is done behind the scenes. For more information, see the Infor Mongoose Installation Guide.
The installation process performs these activities:
1 
Installs TaskMan on the Application Server, which places the RunReport.exe program, the TaskMan.exe
program, and other necessary files in the correct installation folder.
2 
Sets up these subfolders within the installationFolder\Report folder:
Description
Subfolder
This subfolder:
•
Contains errors generated during report processing
•
Creates subfolders that match the IDs of the users who submitted the reports;
for example, \Report\Errors\johsmi
Errors
This subfolder:
•
Contains finished report output files
•
Creates subfolders that match the IDs of the users who submitted the reports
•
Displays the file extensions such as .DOC or .HTM that indicate the format
of the report
OutputFiles
This subfolder:
•
Contains XML files with the report parameter information
•
Creates subfolders that match the IDs of the users who submitted the reports,
for example, Report\ParmFiles\johsmi
ParmFiles
This subfolder contains all report definitions (.rdl) files.
Reports
Note:  Error and output file names include the site name and task number, using the format
taskName_site_taskNumber. If you configure TaskMan to run with two or more databases that have the
same site specification, for example, test and production databases, and name clashes occur, a single
digit is appended to the file name to eliminate the clash; for example: DatabaseSchemaReport_OH_150844_2
3 
Creates TaskMan as a Windows service on the Application Server, set for automatic startup.
If you enter a service logon ID and password during the TaskMan configuration part of the installation,
the installation script uses that domain user ID and password to determine the Windows user account
that controls the service.
After installation is complete, you can also set the service logon ID and password from the Services dialog
box. For the procedure to do that, see your operating system help.
4 
Determines which Strings table in the to use when displaying report form labels. This is a
language-dependent setting available in the Sites form.
Infor Mongoose Administration Guide | 87
 
TaskMan and background tasks

You can also use the Service Configuration Manager utility to configure additional OLE databases for
monitoring by TaskMan. Find the Server Service Configuration under Apps.
Setting up the Infor Framework TaskMan service after
installation
Note:  This topic applies only to on-premises installations. For Multi-Tenant Cloud (MTC) customers, this
activity is performed by the Cloud Enablement Team.
Before users can print or preview reports, or run other tasks as background processes, you must perform
these setup tasks:
•
Configure a printer on the application server.
See Configuring printers on page 88.
•
Define TaskMan and report settings on the Intranets form.
See Defining TaskMan and reports settings on page 90.
•
Define email addresses, if required.
See Setting up the Application Server to send email notifications on page 93.
•
Define report options.
See Defining report options on page 93.
•
Define any excluded tasks.
See Defining excluded tasks on page 95.
Configuring printers
Note:  This topic applies only to on-premises installations. For Multi-Tenant Cloud (MTC) customers, this
activity is performed by the Cloud Enablement Team.
Note:  Before reports can be printed, verify that the printer is configured on the Application Server where
Infor Framework TaskMan Service is installed.
1 
Log in to the Application Server using the same system administrator account that you used to install
and run TaskMan.
2 
Configure the printers using the UNC name. Use this format: \\PrintServer\PrinterName
Defining printers
1 
Open the Printers form.
2 
Provide this information:
Infor Mongoose Administration Guide | 88
 
TaskMan and background tasks

Printer Name
For printers that are configured with the Infor Framework TaskMan Service, specify the path to the
printer, as a fully qualified domain name. Use this syntax: \\domainName\printerName
For printers that are registered in Infor Document Management (IDM), provide the name of the printer.
Description
Optionally, specify a printer description.
Type
Select Enterprise Printer.
Printer Server Name
Select the name of the IDM file server as set on the File Servers form.
Printer ID
This field displays the unique identifier of the printer.
For IDM Enterprise printing, this field is automatically populated with printer IDs of registered printers
of the file server selected in the Print Server Name field.
Internet Info Name
Select the service account name of the printer.
For IDM Enterprise printing, leave this field blank.
3 
Save.
Setting up an IDM Enterprise printer
Before a user can print reports in a Multi-Tenant Cloud (MTC) environment using an IDM Enterprise printer,
you must set up and validate the print server for IDM.
1 
On the File Servers form, set up the IDM file server and its logical folder.
See Setting up a file server with logical folders on page 54.
Note:  When setting this up, keep these thoughts in mind:
•
If the print server and report server are the same, the Logical Folder Name value of the report server
is used. During provisioning, set the report server as the print server.
Select the Print Server check box, and then click the Validate Printer button.
•
If the print server and report server are different, the static Logical Folder Name value, which is
MONGOOSE_ENTERPRISEPRINT, is used. During provisioning, you must configure this logical folder.
Click Configure IDM. See Creating an IDM document type.
2 
Save.
3 
On the Printers form, provide this information:
Note:  This form is accessible only to administrators or users that are part of CoreFormsAdmin permission
group.
Infor Mongoose Administration Guide | 89
 
TaskMan and background tasks

Printer Name
Specify the name of the printer.
Type
Select Enterprise Printer.
Printer Server Name
Select the name of the IDM file server as set on the File Servers form.
Printer ID
Select the printer ID available in the selected printer server.
4 
Click Save.
5 
On the Report Options form, define the report options.
See Defining report options on page 93.
6 
Save.
Defining TaskMan and reports settings
1 
Open the Intranets form and execute Filter-In-Place.
2 
Initiate a new record.
3 
In the General tab, provide this information:
TaskMan Path
Specify the path to a shared drive and folder that contains the Infor TaskMan.exe utility.
Report Preview URL
Specify the path to the folder or internet area where output files must be placed.
Polling Interval
Specify the interval in milliseconds at which TaskMan queries the configured databases to see if tasks
are waiting to be run.
Maximum Concurrent Tasks
Specify the maximum number of tasks that TaskMan can run simultaneously.
Max Report Tasks
Specify the maximum number of report tasks that TaskMan can process simultaneously.
Send Email Notification
To enable email notification for all users when reports are printed, select this option.
4 
In the Reporting tab, provide this information:
Output
Select a format to use as the default format for all reports. The default format is Acrobat Format.
Infor Mongoose Administration Guide | 90
 
TaskMan and background tasks

Preview
Select the format in which report previews are viewed. The default format is Acrobat Format.
5 
Save.
Note:  You can leave most of these fields blank and accept the default values, but you must enter a TaskMan
path for report previews. Some of these values can also be overridden on other forms.
About maximum concurrent tasks
To avoid overburdening TaskMan, you can limit the total number of tasks that can be run at the same time.
Accomplish this using these fields:
•
Maximum Concurrent Tasks and Maximum Concurrent Report Tasks on the Intranets form
•
Max Concurrent on the Background Task Definitions form
Note:  If a task is submitted, but the maximum number of tasks is currently running, the task goes into a
queue, and is processed as soon as a place becomes available.
The maximum allowable tasks within a configuration
To define the maximum number of all tasks, including report tasks, that can run at a single time on the current
configuration, use the Maximum Concurrent Tasks field. If a task is submitted, TaskMan processes it only if
the total number of tasks currently running is less than this number.
If TaskMan is monitoring multiple configurations, then each configuration's number of maximum tasks
combine to define the maximum number of tasks that this specific TaskMan service can handle. Suppose, for
example:
•
TaskMan is monitoring two configurations: Config1 and Config2.
•
The number of Maximum Concurrent Tasks for Config1 is set to 10.
•
The number of Maximum Concurrent Tasks for Config2 is set to 20.
•
Then the maximum number of concurrent tasks for this instance of TaskMan is: 10 + 20, or 30.
These conditions being the case, then it follows that these statements are true:
•
If 10 tasks are running on Config1, then Config1 can no longer run any tasks, because its limit has been
met, even though TaskMan can handle 20 more tasks.
•
If 20 tasks are running on Config2, then Config2 cannot launch any more tasks, but Config1 could still
launch up to 10.
The maximum number of report tasks
The Maximum Concurrent Report Tasks field is used to define the maximum number of report preview or
print report tasks that can run at a single time in the current configuration. If a report or report preview is
submitted, TaskMan processes it only if the total number of report previews or print jobs currently running
is less than this number.
Infor Mongoose Administration Guide | 91
 
TaskMan and background tasks

Note:  TaskMan tracks report preview and print report tasks separately. This means, for example, that if you
have this field set to 20, then you can have up to 20 report preview AND 20 print report tasks processing
simultaneously.
Because report tasks are a subset of all tasks, the Maximum Concurrent Report Tasks setting should be less
than or equal to the Maximum Concurrent Tasks setting . If you allow more reports than overall tasks,
TaskMan limits the reports to the maximum overall tasks. So, for example, suppose:
•
Maximum Concurrent Tasks is set to 10.
•
Maximum Concurrent Report Tasks is set to 20.
Then it follows that:
•
If you submit 20 reports, only 10 run, because that is the maximum allowable number of overall tasks.
•
If you submit 20 reports while 5 tasks are already running, only 5 reports are processed immediately.
TaskMan monitoring multiple configurations
If TaskMan is monitoring multiple configurations, then each configuration's number of currently running
report tasks is calculated by adding the currently running report tasks from each configuration that TaskMan
is monitoring.
Suppose, for example, these conditions:
•
TaskMan is monitoring two configurations: Config1 and Config2
•
The Maximum Concurrent Report Tasks for Config1 is set to 10.
•
The Maximum Concurrent Report Tasks for Config2 is set to 20.
•
The number of reports currently running on Config1 is 5.
•
The number of reports currently running on Config2 is 3.
Then it follows that these statements are true:
•
The number of report tasks that are currently available to run on Config1 is [10 - (5 + 3)], or 2.
•
The number of report tasks that are currently available to run on Config2 is [20 - (5 + 3)], or 12.
Using the Max Concurrent setting
To limit the number of times a single, specific task can be run at the same time, use the Max Concurrent field.
For example, suppose these are the settings:
•
Maximum Concurrent Tasks is set to 10.
•
Maximum Concurrent Report Tasks is set to 5.
•
Max Concurrent for a task, ABCAnalysisRpt, is set to 1.
Then it follows that these statements are true:
•
If no tasks are currently running, and you submit the ABCAnalysisRpt report task twice, only one will be
processed immediately.
•
If five report tasks are currently running, and you submit the ABCAnalysisRpt report task, it will not run
until one of the five already running has finished.
•
If eight general tasks, and two report tasks, are running, and you submit the ABCAnalysisRpt report task,
it will not run immediately, even though only two of the available five report tasks is running, because
the total number all tasks has been met.
Infor Mongoose Administration Guide | 92
 
TaskMan and background tasks

Setting up the Application Server to send email notifications
Before users can send email notifications, you must perform these setup tasks:
Configuring the Application Server
Use the Intranets form to configure the application. On the Reporting tab, provide the information required
in the SMTP section.
Enabling email attachments
Use the Intranets form to enable email attachments. On the General tab, specify the desired site and select
the Send Email Notification check box.
Activating email notifications
Use the Report Options form to activate email notifications. For each Task Name or report that is to send
email notifications, select Yes in the Email Notification field.
Depending on how you organized your report options, you must create new records for reports that will use
email notifications.
Entering user email addresses
Use the Users form to enter email addresses for users. For each user who is to receive email report notification,
ensure that a valid email address is entered.
Defining report options
To define options for specific users and specific reports, use this procedure:
1 
Open the Report Options form.
2 
Initiate a new record, or select the report to edit.
3 
Provide this information:
User
Specify the user ID to which these report options are to apply.
This field defaults to your user ID, but you can change it.
Task Name
Specify the name of the report to which these options are to apply.
Output Format
Select the format to use for report outputs.
If you select the Printer or Cloud Print output format, the Printer Name field is enabled.
Note:  This option overrides the format specified in the Intranets form.
Infor Mongoose Administration Guide | 93
 
TaskMan and background tasks

Printer Name
Specify the name of the printer to use when the designated user submits the specified report.
Note:  In an on-premises environment, this printer must be defined as a network printer and must be
configured on the server where TaskMan is installed. Also, the user ID set up as the owner of the TaskMan
service must have print privileges for each printer defined here.
For a Multi-Tenant Cloud (MTC) environment, the printer must be registered in Infor Document
Management (IDM).
Note:  In a MTC environment, application developers can add a task parameter on form events to specify
an IDM Enterprise printer, to print form reports that are submitted as background tasks. You can define
this parameter by editing a form report in the Web Designer. Use this feature only on form events that
are assigned with a Response > Type of Run Background Task.
See Adding a task parameter in application events for enterprise printing.
Alternatively, on the Event Actions form, you can use the TaskProfileEnterprisePrinter parameter to
specify an IDM Enterprise printer for task events. If no IDM Enterprise printer is defined, the report is
printed using the default settings.
See Creating application event actions.
Paper Size
Select the size of paper to use when printing the report output.
Report Theme
Optionally, select the theme to use when generating the report.
Output Directory
Specify an output directory for a specific report output profile.
Note:  This option overrides both the TaskMan Path field on the Intranets form and the Report Output
Folder field, as specified on the Sites form.
Email Notification
Designate whether an email notification is to be sent to this user whenever this report is processed and
generated (Yes) or not (No).
Attach Report
Designate whether a report is to be attached to the email notification (Yes) or not (No).
Auto View
To have printed reports display automatically in either a web browser or in the Report Viewer form,
select this option.
4 
Save.
Infor Mongoose Administration Guide | 94
 
TaskMan and background tasks

Defining excluded tasks
If there are tasks that should not run at the same time as a task that you have defined in your system, you
must signify them as excluded tasks. To define excluded tasks:
1 
Open the Background Task Definitions form and execute Filter-In-Place.
2 
Select the task to exclude.
3 
Click Excluded Tasks.
4 
On the Excluded Tasks form, select the task to exclude.
5 
Save and close the Excluded Tasks form.
About background tasks
Background tasks are operations or activities that can be set up to run in the background. Many reports,
utilities, and some activities can be scheduled to run daily, weekly, or monthly. After the process is scheduled,
it runs automatically in the background at a set time, using your selection criteria and option settings.
Most reports that are run as background tasks are placed on a list of active background tasks, to be run as
scheduled or demanded. You can also set up other tasks to run in the background. The Infor Framework
TaskMan service regularly polls the list of active background tasks and executes any task that is posted as
READY to be executed.
Forms used with background tasks
These are the basic forms and utilities associated with background tasks:
•
Background Task Definitions: Use this form to create records that define and identify background tasks
for TaskMan. TaskMan then monitors the application database in order to execute these defined
background tasks, either on demand or as scheduled.
•
Active Background Tasks: Use this form to schedule background tasks and to view the statuses of
background tasks that are waiting to execute or are in progress.
•
Background Task History: Use this form to display information about background tasks that have been
run, and whether they completed successfully. If not, this form also displays associated error messages.
•
Active Background Task Purge Utility: Use this form to purge (usually) long-running active background
tasks that are in READY or RUNNING status. This utility can be used for tasks that have become "hung" or
that have been created accidentally and are no longer needed.
•
Background Queue: Use this form to specify when and/or how often, the task from the 'parent' form is
to run.
Guidelines for creating background tasks
Background tasks must meet these requirements:
•
The task name should match the form name, to make it easier to identify.
•
Stored procedures must be written in SQL and reside in your SQL Application Database. Utility and activity
forms generally use the Executable Type: SP (stored procedure).
Infor Mongoose Administration Guide | 95
 
TaskMan and background tasks

•
Executable programs must reside in a directory available to the directory where TaskMan resides.
•
These types of reports are supported as background tasks:
•
DATAVIEW: Reports based on predefined DataViews and their layouts.
•
FORM: Reports created using the Report form type.
•
SSRS: (Deprecated) Reports created using Microsoft SQL Server Reporting Services (SSRS).
Note:  To enable the Report Type options, you must select RPT (Report) as the Executable Type.
•
IDO methods, as background tasks, specify a method name (in the format IDO.Method--for example,
MyProgID.SLSites.MyTestMethod)--and use the Executable Type IDOMTH. When processing tasks of this
type, TaskMan requires a configuration with the same name as the site name. On the machine where
TaskMan is running (typically the application server), you must create a configuration where the
configuration name matches the database site name, if one does not already exist.
Background task statuses
The status of an active or scheduled background task displays on the Active Background Tasks form. The
status might be any of these, depending on how the task is defined and scheduled:
•
READY: The task has been submitted and is ready to execute.
•
RUNNING: The task is currently executing.
•
WAITING: The task is scheduled and on hold until some requirement (usually the scheduled day/time)
is met.
•
PROCESS: The task is a scheduled task that has spawned a 'child' task that is either READY or RUNNING.
The status of a task that has already run or is currently running displays on the Background Task History
form. The status indicates one of these cases:
•
Task Succeeded
•
Task Failed - Error -1: The task failed to finish and generated an error. The explanation of the error is
provided in the Error Message field.
•
Task Cancelled - Error -2: The task was cancelled at the request of a user. When this is the case, the
explanation of the cancellation is provided in the Error Message field.
Scheduling background tasks
Background tasks are set up using the Background Task Definitions form. They can also be scheduled to
run on a once-only or recurring basis using the Active Background Tasks form or the Background Queue
form.
Note:  When scheduling background tasks, keep these facts in mind:
•
Forms can only be scheduled to run as background tasks if they have the Background option available
on the Actions menu.
•
To schedule (and run) background tasks, you must have the Infor Framework Event Service configured
and running.
Once scheduled, background tasks can only be deleted if they have a Status of WAITING on the Active
Background Tasks form. Tasks that are in a status of READY or RUNNING can be canceled without deleting them.
Infor Mongoose Administration Guide | 96
 
TaskMan and background tasks

About background task definitions
Background task definitions instruct the Infor Framework TaskMan service how to process background tasks
when they run.
Create background tasks only for your organization's custom forms and reports. Where needed, background
task definitions are already set up for standard forms and reports; and those background task definitions are
listed on the Background Task Definitions form.
Use the Background Task Definitions form to create a record that identifies the background task to TaskMan.
Every report and every utility or activity that can be run as a background process must be listed in this form.
Ensure that background tasks meet these requirements:
•
The task name must match the form name to make it easier to identify.
•
Some (older) reports are processed and generated using the Microsoft SQL Server Reporting Service
(SSRS). For these reports, the output file must be placed in the Reports folder on the same Application
Server where TaskMan is installed. Although the report uses a stored procedure or IDO, its Executable
Type must be set to RPT.
Creating a background task definition
To create a background task definition, use this procedure:
1 
Open the Background Task Definitions form and execute Filter-In-Place.
2 
Initiate a new task record.
3 
Provide this information:
Task Name
Specify a name for the task.
Task Description
Optionally, provide a helpful description of the task.
Executable Name
Specify the object to be executed as a background task:
•
For a stored procedure, specify the name of the stored procedure.
•
For a report, specify the name of the DataView to be used to generate the report.
•
For an IDO method, specify the name of the IDO method.
Executable Type
Select the type of object to be executed as a background task:
•
For a stored procedure, select SP.
•
For a report, select RPT.
•
For an IDO method, select IDOMTH.
Exclusive
To prevent more than one instance of the task from running simultaneously, select this option.
Infor Mongoose Administration Guide | 97
 
TaskMan and background tasks

Report Type
Select which type of report generator is to be used to generate the report:
Note:  This field is enabled only if you select RPT as the Executable Type.
•
DATAVIEW: Reports based on DataViews and their layouts.
When this report type is specified, the Executable Name field must contain the name of the
predefined DataView, followed by a dash and the name of the DataView layout to be used for the
report.
For example, you might have a predefined DataView named "Customer Orders" and a layout named
"Print" that you use to send the report output to a printer. In this case, you would specify the
Executable Name as Customer Orders-Print.
This report type supports output only to PDF or Excel formats.
•
FORM: Reports created using the Report form type.
When using this report type, use the form name (not caption) in the Executable Name field.
This report type supports output only to the PDF format.
•
SSRS: (Deprecated) Reports created using Microsoft SQL Server Reporting Services (SSRS).
The report output file must be placed in TaskMan's reports subdirectory on the server. Although
the report uses a stored procedure, its Executable Type must be set to RDL, since it requires special
handling through SSRS.
Report Options
To launch the Report Options form, in which you can make various settings for the output of a report,
click this button.
Note:  This button is enabled only if you select RPT as the Executable Type.
Paper Size
Select the size of paper to be used for the report output.
For descriptions of the various paper sizes available, see List of report paper size options.
Note:  This field is enabled only if you select RPT as the Executable Type.
Maximum Concurrent
Specify the maximum number of concurrent instances of this task you want TaskMan to allow.
The default value is 20.
Valid values are positive integers. A value of -1 means any number of concurrent instances of this task
are allowed to run, up to other limits imposed on all tasks.
Caution:  Do not attempt to use a value of zero (0), as it disables the task completely.
Isolation Level
Select the transaction isolation level to use when running stored procedure tasks (Executable Type is
set to SP) or report stored procedures (Executable Type is set to RPT):
•
COMMITTED: All queries run from this stored procedure see only data for which the query can get
a shared lock. No reads of uncommitted data are performed.
•
UNCOMMITTED: All queries run from this stored procedure can read uncommitted data. Use this
option carefully, since it can display data that has not yet been committed in the database.
Infor Mongoose Administration Guide | 98
 
TaskMan and background tasks

If no isolation level is set in this form, the stored procedure uses the global Collection Read Mode value
from the Process Defaults form. If that value does not exist, the stored procedure uses COMMITTED as
the transaction isolation level.
4 
Save.
Managing background tasks
Use these forms to schedule some processes to run at certain times of day and at regular intervals:
•
Background Task Definitions: Use this form to create definitions that identify each background task to
TaskMan. TaskMan uses these definitions to monitor the Application Database in order to execute
background tasks.
Every report, and every utility or activity that can be run as a background process, must be listed and
defined on this form.
•
Background Queue: Use this form to specify when and/or how often, the task from the parent form is
to run.
This form can be accessed through the Actions menu on many reports, activities, and utilities. Each time
the task runs, this form uses any search criteria you may have set up.
•
Active Background Tasks: Use this form to schedule background tasks and to view the background
tasks that are waiting to run or are in progress.
This form provides many options for scheduling background tasks. This is especially important when
scheduling background tasks that recur. Many of these scheduled background tasks are for reports that
are to be produced on a regular basis. You can, however, also schedule other tasks, such as utilities that
can run operations in the background.
•
Background Task History: Use this form to display information about background tasks that have been
run, and whether they have completed successfully.
Return codes generated by background tasks display here.
These types of tasks can be displayed:
•
Reports (RPT): Background tasks of the type RPT are for reports of any of the currently supported
report types.
Note:  If the Report Server has been set up in the Application settings of the Configuration Manager,
the server name, logical folder name, and file server path where the report output is written, display
on this form. This information can be used to retrieve the file even if the configuration changes at a
later time.
•
Stored procedures (SP): Developers supply the exit status for a stored procedure within the coding
of the procedure. Generally, the exit status of the stored procedure functions as its return code,
which is returned by TaskMan and displays on this form.
•
IDO methods (IDOMTH): IDO methods can be called by a background task, usually with parameters.
The method code then returns a status of success or failure and, optionally, a message that can be
displayed on this form. In this sense, the IDO method can be used in a way similar to the way stored
procedure background tasks are used.
Infor Mongoose Administration Guide | 99
 
TaskMan and background tasks

Scheduling background tasks (developer or administrator)
1 
Use the Background Task Definitions form to create the background task.
If you already have a background task created that you want to schedule, skip this step.
For the procedure to create a background task, see Creating a background task definition on page 97.
2 
Optionally, to schedule the task to run in the background, use the options on the Background Queue
form to do the scheduling.
You can schedule the task to run only once or on a recurring daily, weekly, or monthly basis.
Note:  For scheduled background tasks to run, you must have the Infor Framework Event Service
configured and running.
3 
Use the Background Task History form to check and see if the task is running or waiting.
When TaskMan starts a task, it updates the Started field in the Status Information group box. If the
Completed field is blank, the task is still running.
Note:  You can also use the Active Background Tasks form to see which tasks are currently running.
4 
Optionally, view details about the completed task.
After TaskMan has completed the task, even if it fails, you can view details about its execution on the
Background Task History form, which displays this information:
•
Task Information
•
Status Information
•
Other details about the task
•
Messages triggered by the task
Sample error message in the background task history
This topic shows a sample error message that might be shown in the Background Task History form:
-1 Error running report Error... [2003-08-06 11:05:57] 
MainModule.Main: This field name is not known. 
Command Line Parameters: -d SL9.00_OH_Dev -l sa -p **** -s 
Forms.dbo.Strings -f 1 -r OrderStatus -t 152995 -u matagl -e 
"D:\Taskman\Report\Errors\matagl\OrderStatusReport_OH_152995.txt" 
-site "OH" -db OH_App -n 1 -m ,,RB,POSCH,POFCH,1,B,0,OH,,,,,,,,,,,,,,,,,,,,,O,0
This table describes the syntax of this example and matches the parameters with the example message:
Example
Parameter
-1
General return code
Error                         running report E
rror...
Warning/error indicator
[2015-08-06 11:05:57]
Date and time
MainModule.Main:
Part of source code that failed
This                         field name is not
known.
Report error message
Infor Mongoose Administration Guide | 100
 
TaskMan and background tasks

Example
Parameter
Command                         Line Parameter
s: -d SL7.03_OH_Dev -l sa -p **** -s Forms.dbo
.Strings -f 1 -r                         Order
Status -t 152995 -u matagl -e
"D:\Taskman\Report\Errors\matagl\OrderStatusRe
port_OH_152995.txt" -site "OH"
-db OH_App -n 1 -m                         ,,R
B,POSCH,POFCH,1,B,0,OH,,,,,,,,,,,,,,,,,,,,,O,0
Parameters that were passed to TaskMan
Scheduling reports and utilities to run in the background
If a report or utility has a Background option on its Actions menu, you can schedule the report or utility to
run at a time you choose. The background task can be run once, or it can be set up as a recurring task that
runs at certain times daily, weekly, or monthly.
1 
From the report or utility form, select Actions > Background.
2 
On the Background Queue form, provide this information:
Recurrence
Use the various options to specify whether you want the task to run only once, or on a daily, weekly, or
monthly basis.
Frequency
Specify the time and date information.
Recurrence Range
Optionally, for recurring tasks, use these fields to specify the dates on which task processing is to start
and end.
3 
Click OK and then save.
Scheduling a process to run in the background
1 
Open the report or utility form that you want to schedule to run in the background, and set your selection
criteria and task options.
2 
From the Actions menu, click Background.
This launches the Background Queue form.
3 
In the Recurrence section, select one of these options:
•
Once: If you select this option, set the Occurs Once At date and time fields to the future time at
which the task is to run.
•
Daily: Use the Occurs Every field to determine how often the task is to run.
To have the task run every other day, for instance, set this field to 2.
If you choose this option, you must also set the appropriate options in the Frequency section.
•
Weekly: Use the Recurs Every...Week(s) On to determine how often the task is to run.
Infor Mongoose Administration Guide | 101
 
TaskMan and background tasks

To have the task run every other Saturday, for instance, set this field to 2 and select the Saturday
check box.
If you choose this option, you must also set the appropriate options in the Frequency section.
•
Monthly: To have the task run on a particular date of each month, select this option.
To have the task run on the 15th day of each month, for instance, you would set On Day to 15 and
Recurs Every...Month(s) to 1.
If you choose this option, you must also set the appropriate options in the Frequency section.
•
Monthly Relative: To have the task run on a particular day of each month, select this option.
To have the task run on the last Saturday of every third month, for instance, you would set Occurs
to Last and Saturday, and Recurs Every to 3.
If you choose this option, you must also set the appropriate options in the Frequency section.
4 
Use the Frequency settings that are appropriate to your choice in the Recurrence section.
5 
To determine the dates on which task processing are to begin and end, make the appropriate settings
in the Recurrence Range section.
6 
Click OK and save.
Checking which background tasks are running
When the Background Queue form is used to create a job, it creates an Active Background Task record with
a WAITING status, and includes the appropriate task name and parameters.
Note:  The task name, which is determined through the Background Task Definitions form, is usually the
same as the form name of the report or utility that is waiting to run.
1 
Open the Active Background Tasks form.
2 
Verify the task status:
•
READY: Tasks with this status generally process immediately. If necessary, these tasks can be deleted
from the queue from within your application using the Active Background Task Purge Utility form.
•
RUNNING: Tasks with this status are currently processing. These tasks cannot be deleted from the
queue from within your application.
•
WAITING: Scheduled tasks with this status remain in the queue until their scheduling requirements
are met, as set in the Background Queue form. These tasks can be deleted from the queue from
within your application.
You can reschedule the time and modify the recurrence of a WAITING task using the options provided
on the Recurrence and Recurrence Range sections of the Active Background Tasks form.
Note:  If the DateTime in Client Time Zone process default is enabled, the date and time value is
displayed based on your local time zone. If this process default is disabled, the date and time value
is displayed based on the site time zone.
•
PROCESS: Scheduled tasks with this status have passed beyond the elapsed time interval and are
ready to process, but have not been processed.
The PROCESS state is used with scheduled tasks. When the scheduled interval elapses, the scheduled
task moves to a PROCESS state and a new READY task is created to execute the task. The scheduled
Infor Mongoose Administration Guide | 102
 
TaskMan and background tasks

task moves back to a WAITING state after the task is processed. This keeps the scheduled interval
from elapsing again while the previous task is still processing.
After a task is completed, the task is deleted from the queue. You can view details about its processing in the
Background Task History form.
Although you cannot stop a background task with RUNNING status from within your application, if you need
to stop a long-running process, you can use the Windows Task Manager to do so.
Note:  The Windows Task Manager is different from the Infor Framework TaskMan Service.
About canceling background tasks
Background tasks that have a status of either READY or RUNNING can be canceled.
The specific procedure to cancel a background task depends on what form you are using to request the
cancellation:
•
Active Background Task: See Canceling a task on the Active Background Task form on page 103.
•
Background Task History: See Canceling a task on the Background Task History form on page 104.
•
Active Background Task Purge Utility: See Using the Active Background Tasks Purge Utility on page
104.
Canceling a task on the Active Background Task form
Background tasks that have a status of either RUNNING or READY can be canceled on the Active Background
Tasks form. Canceling a task does not delete it or remove it from the set of Active Background Tasks form
if it is a scheduled task. It simply stops or prevents the task from running and allows the next task to be
executed.
Canceling a RUNNING or READY task that was created by a 'parent' scheduled task does not also stop the 'parent'
scheduled task from executing on its schedule. To stop the 'parent' scheduled task you must navigate to the
'parent' scheduled task, verify that it has a status of WAITING, and then you can disable or delete the 'parent'
scheduled task.
1 
Open the Active Background Tasks form and select the task to cancel.
Note:  Only tasks that have a status of RUNNING or READY can be canceled.
2 
Click Request Cancellation.
This button is displayed only for tasks that are RUNNING or READY.
3 
When prompted, click OK to confirm.
The Status of the task changes immediately to CANCEL.
If the task is RUNNING when you request cancellation, it takes some time to stop and reverse the task processes.
To confirm the cancellation, wait a time and then refresh the form.
If the task is READY when you request cancellation, the form refreshes and the task disappears from the list
immediately.
Infor Mongoose Administration Guide | 103
 
TaskMan and background tasks

Canceling a task on the Background Task History form
On the Background Task History form, background tasks that are READY to execute or are executing (RUNNING)
display no Return Status, and the Completed field is blank. These tasks can be canceled upon request.
1 
In the Background Task History form, verify that the Completed field for the task is blank.
2 
Click Request Cancellation.
Note:  This button displays only if the task status is READY or RUNNING (as displayed on the Active
Background Tasks form).
3 
When prompted, click OK to confirm.
The Return Status changes to Task Cancelled - Error -2, and the application logs an Error Message.
If the task is RUNNING when you request cancellation, it takes some time to stop the task execution processes.
To confirm the cancellation, wait some time and then refresh the form and verify the Return Status and Error
Message.
Using the Active Background Tasks Purge Utility
If you have a large number of active background tasks that you want to clear or cancel in one operation, use
the Active Background Tasks Purge Utility. You can use this utility to cancel tasks that are in the RUNNING
state, the READY state, or both.
1 
Open the Active Background Tasks Purge Utility.
2 
In the Task Name field, select the background task you want to purge.
Note:  This drop-down list displays only background tasks that have a Status of RUNNING or READY on the
Active Background Tasks form.
3 
In the User Name field, select the name of the user who submitted the background tasks.
4 
Select either the Ready option, the Running option, or both.
These options control which active background tasks are canceled.
5 
Click Request Cancellation.
6 
When prompted, click OK to confirm.
The application displays an informational message that tells how many tasks were canceled.
You can also verify the results on the Active Background Tasks form.
Canceling background tasks from a CLI
Background tasks that are in a Status of RUNNING can be canceled using a Command Line Interface (CLI). This
is typically done during ZDT ("Zero Down-Time") deployments to ensure that no tasks are running before
database updates are performed.
When canceling background tasks using a CLI, you can cancel tasks for specific configuration or for specific
tenants in the Multi-Tenant Cloud (MTC).
Infor Mongoose Administration Guide | 104
 
TaskMan and background tasks

Canceling tasks for a specific configuration
When canceling RUNNING background tasks for a specific configuration, use this syntax:
ConfigManager.exe -U:CONFIG -mode:utensil -utensil:CancelBgTasks -name:configName 
where configName is the name of the configuration that is being updated.
Canceling tasks for a specific tenant
When canceling RUNNING background tasks for a specific MTC tenant, use this syntax:
ConfigManager.exe -U:CONFIG -mode:batchutensil -application_dbsource:dbServerName 
-application_dbname:tenantDbName -utensil:CancelBgTasks 
where:
•
dbServerName is the name of the server that hosts the tenant database.
•
tenantDbName is the name of the tenant database.
Example 1
This example updates the version of a configuration to MAINT and cancels all RUNNING background tasks for a
configuration named MyConfig:
ConfigManager.exe -U:CONFIG -mode:edit -name:MyConfig -version:MAINT -cancelbgtasks 
Example 2
This updates a the version of a configuration to MAINT and also cancels all RUNNING background tasks for a
tenant database named MyTenantDB hosted on a server named MyDBServer:
ConfigManager.exe -U:CONFIG -mode:batchedit -application_dbsource:MyDBServer 
-application_dbname:MyTenantDB -version:MAINT -cancelbgtasks 
Viewing the count of background task types for a configuration
After using a command to cancel RUNNING background tasks, it is a good idea to verify that those tasks have
actually canceled and stopped running before proceeding with any other action. This can be done by getting
a report of the count of each type of task currently on the target configuration or tenant. The BGTaskReport
utility generates this report. Before proceeding with another action, you would want to verify that the count
of RUNNING tasks is 0 (zero).
To view a count of each type of task for a specific configuration, use this syntax:
ConfigManager.exe -U:CONFIG -mode:report -name:BGTaskReport -config:configName 
where configName is the name of the configuration to check.
Infor Mongoose Administration Guide | 105
 
TaskMan and background tasks

This is an example of the output from this report:
Parent process: cmd, Current process: ConfigManager, Need Console: True
Attached Console for process id: 29908, name: cmd
CreateNoWindow = False
Running in console mode ......
Output Redirection ......False
Using StreamWriter
Utility : CONFIG
Generating BGTask Count Report:
ConfigName: Mongoose2209
WAITING    2
PROCESS    1
READY      1
RUNNING    0
CANCEL     0
End using StreamWriter
Enter Key Pressed from Output not redirected! 
You can also view the count of task types in JSON format, using this syntax:
ConfigManager.exe -U:CONFIG -mode:report -name:BGTaskReport -config:configName -json 
This is an example of the output in JSON format:
{"Results":[{"ConfigName":"configName","TaskStatusCode":"WAITING","Count":2},
{"ConfigName":"configName","TaskStatusCode":"PROCESS","Count":1},
{"ConfigName":"configName","TaskStatusCode":"READY","Count":1},
{"ConfigName":"configName","TaskStatusCode":"RUNNING","Count":0},
{"ConfigName":"configName","TaskStatusCode":"CANCEL","Count":0}]} 
About removing background tasks
The procedure to remove a background task from the Active Background Tasks form differs according to
the current status of the task:
•
WAITING tasks are background tasks that are scheduled to run at some future time.
Tasks can be scheduled by authorized users. When the Enabled option is selected, these tasks are then
placed in a WAITING status until their scheduled time to run.
WAITING tasks can be disabled or deleted. Clearing the Enabled check box, disables it but leaves it available
to be run again if enabled. Deleting it leaves the definition on the Background Task Definitions form,
but prevents it from running automatically.
When the Background Queue form is used to schedule a task, it creates a record on the Active Background
Tasks form with a status of WAITING, and the appropriate task name and parameters.
•
PROCESS tasks are background tasks that have been scheduled to run and the next scheduled start time
has elapsed. At the scheduled start time, one related 'child' task was spawned in the READY state.
These tasks cannot be deleted, as such. To delete one of these tasks, you must first cancel its 'child' task
to return it to a status of WAITING. You can then disable or delete the task.
•
RUNNING tasks are background tasks that are currently in the process of executing.
You can cancel a RUNNING task by clicking Request Cancellation.
Infor Mongoose Administration Guide | 106
 
TaskMan and background tasks

If you cancel a scheduled task that is RUNNING and want to prevent it from running again (in other words,
to prevent the 'parent' scheduled task from running again), you must first wait for the 'parent' task to be
returned to a WAITING status. You can then disable or delete the 'parent' task.
•
READY tasks are background tasks that are to be executed as soon as TaskMan has the capacity available
to carry out the execution.
As with RUNNING tasks, to cancel a READY task, click the Request Cancellation button.
If you cancel a scheduled READY task and want to prevent it from running (in other words, to prevent the
'parent' scheduled task from executing it), you must first verify that the 'parent' task is in a WAITING status.
You can then disable or delete the 'parent' task.
•
CANCEL indicates that a cancellation request has been made and that the system is carrying out the
cancellation process. These tasks cannot be deleted, because they disappear as soon as the cancellation
process is complete.
Note:  Cancellation requests can be made only for READY or RUNNING tasks. You might need to refresh the
form to verify that the cancellation has been completed.
Deleting WAITING tasks
Background tasks can be deleted only if they have a Status of WAITING on the Active Background Tasks form.
Even then, deleting a task record from that form does not delete the task definition from the Background
Task Definitions form.
1 
Open the Active Background Tasks form.
2 
In the Grid View, select the task to be deleted.
Note:  The task must have a Status of WAITING. Tasks that have any other Status indication on that form
cannot be deleted.
3 
Delete the task record as you would any other record.
4 
Save.
Note:  When deleting background tasks, keep these facts in mind:
•
To completely delete a background task, you must also delete the task definition from the Background
Task Definitions form.
•
You can use the Background Queue form to add a task several times. If you later decide to delete a task
on the Active Background Tasks form, be sure to delete the correct task.
Deleting background tasks that are processing
Before you can delete a scheduled task that is executing (that is, which has a Status of PROCESS on the Active
Background Tasks form), you must first cancel any RUNNING or READY 'child' tasks and return the 'parent'
(PROCESS) task to a Status of WAITING:
1 
Open the Active Background Tasks form and select the scheduled task that is processing.
Processing tasks have a Status of PROCESS. Typically, they also have a spawned 'child' task that is either
RUNNING or READY.
Infor Mongoose Administration Guide | 107
 
TaskMan and background tasks

2 
If there are any 'child' tasks that have a Status of RUNNING or READY, cancel the 'child' tasks.
For the procedure, see Canceling a task on the Active Background Task form on page 103.
Note:  If desired, to locate and cancel any 'child' tasks that might be RUNNING or READY, you can use the
Spawned Task Number and Spawned Status fields. To navigate to the 'child' task from the 'parent'
PROCESS task (or from the 'child' back to the 'parent' task), click the hyperlinks.
3 
After any 'child' tasks are canceled, verify that the Status of the 'parent' task has changed to WAITING.
4 
Delete the 'parent' task record as you would any other record.
5 
Save.
Note: 
Note:  When deleting background tasks, keep these facts in mind:
•
If you do not delete (or disable) the 'parent' task record, then, when the task is next scheduled to process,
the Status changes to PROCESS, and processing resumes.
•
To completely delete a background task, you must also delete the task definition from the Background
Task Definitions form.
Viewing background task history information
Use the Background Task History form to display information about background tasks, whether they have
successfully completed or not.
Note:  Infor Framework TaskMan is a Windows service which monitors the application database in order to
execute background tasks.
Return codes generated by background tasks can include this information:
•
Reports: Background tasks of the RPT type are reports. The RunReport.exe application returns exit codes
to TaskMan that display on this form. If the return code indicates an error, you can also consult the error
log for additional information.
•
Stored procedures: Developers supply the exit status for a stored procedure within the coding of the
procedure. Generally, the exit status of the stored procedure functions as its return code, which is returned
by TaskMan and displayed on this form.
•
IDO methods: Developers can optionally supply the exit status for an IDO method within the coding of
the method. Generally, this exit status can be returned by TaskMan and displayed on this form.
Modifying forms to submit tasks to the background queue
The application runs reports as background tasks that are placed on a "queue" to be run in order. You can
also set up other tasks to run in the background. The TaskMan service polls the list of Active Background
Tasks and executes any new task that is posted to the queue with a status of READY.
1 
To set up the form for which you want the background task to run:
a 
Add a MenuItem component called BackgroundQueue to the form.
b 
On the Behavior tab, add RunBackgroundQueue as the primary event.
Infor Mongoose Administration Guide | 108
 
TaskMan and background tasks

c 
Create a RunBackgroundQueue event of type Run Form As Modal Child.
The parameters for the event should be as shown in this example, where GenerateReport is the name
of the form's Run Background Task event: BackgroundQueue( SETVARVALUES(BGTaskName=V(BG
TaskName), RunTaskEvent=GenerateReport) )
d 
Add parameters to the end of the parameter string for the Run Background Task event that submits
the task:
TASKSTATUS(V(BGTaskStatus))TASKNUMBER(BGTaskNumber)
Use these keywords and parameters:
•
TASKSTATUS is an optional input parameter to BGTaskSubmit. If the TASKSTATUS keyword is
omitted, or if its value is anything other than WAITING, the task is inserted into the ActiveBGTasks
table with a status of READY and is run by TaskMan. If TASKSTATUS is set to WAITING, the task
is entered in the ActiveBGTasks table with a status of WAITING. TaskMan ignores any records
in this table with a status other than READY or RUNNING.
•
TASKNUMBER is an optional keyword used to specify the name of a variable that holds the
TaskNumber generated when a record is inserted into the ActiveBGTasks table.
•
You can include substitution keywords that are replaced by appropriate values after the task is
submitted to TaskMan.
2 
Create a background task.
For the procedure, see Creating a background task definition on page 97.
3 
Configure the form event you created in Step 1 to run the background task you just created.
4 
Save your work and test the form event to make sure it works as expected.
You can use the Active Background Tasks form to see which tasks are currently running.
After TaskMan has completed the task, even if it fails, you can view details about its execution on the
Background Task History form. Details include:
•
Task description
•
Return status
•
User who submitted the task
•
Messages triggered by the task
Reverting to the default version of a form
If you customized a form and you want to revert to the original, default version, consider these questions:
•
Did you verify that the form has been customized?
To see whether a form has been customized, select Help > About This Form. If the form version is not
Vendor Default, then the form has been customized.
•
Who are you?
If you are not a Vendor Developer or a Site Developer, you can revert only those changes that you personally
have made to the form.
•
What do you want to revert to?
Infor Mongoose Administration Guide | 109
 
TaskMan and background tasks

If you have Site Developer, Full User, or Basic User editing permissions, you can cancel customizations
to the form and any associated global form objects and restore another version according to this hierarchy:
•
A user version reverts to a group version, if one exists.
•
A group version reverts to a site version, if one exists.
•
A site version reverts to the default vendor version.
•
Do you want to save a copy of this version?
If you have Site Developer editing permissions, you can copy the customized form to a new form name
before you revert the form definition. Then, after reverting the existing form, you can copy any
customizations that you want to keep, from the copy to the new standard form.
To make a copy of a form, select Form > Definition > Copy and then specify the Source and Target form
names. For example, you could specify CustomerOrderLines for the Source form and
Site_CustomerOrderLines for the Target form.
You have two options for reverting a form:
•
Reverting Form Changes Made in Runtime Mode on page 110
•
Reverting Form Changes to Vendor Default Version on page 110
Reverting form changes made in runtime mode
1 
Open the form in Runtime Mode.
2 
Click the Revert runtime changes button on the main tool bar.
3 
When prompted for confirmation, click OK.
The form is closed, is reverted to the vendor default version, and is reopened.
4 
Verify that the run-time changes have been reverted.
Reverting form changes to vendor default version
Note:  This procedure applies only to customizations and modifications made at any level in the designer.
1 
Open the form in the designer.
2 
Select Edit > Revert Form Definition.
3 
When prompted for confirmation, click OK.
4 
Close the form and select Form > Definition > Unload All Global Form Objects.
5 
Reopen the form and verify that it has reverted to the vendor default form definition.
Running stored procedures and IDO methods
In addition to running reports, you can use TaskMan to run stored procedures or IDO methods.
Infor Mongoose Administration Guide | 110
 
TaskMan and background tasks

Setting up stored procedures
TaskMan can execute stored procedures directly, without going through the IDO layer. To do this,TaskMan
bundles the stored procedure in a transaction.
This example shows the steps to set up a stored procedure that runs from a form using TaskMan:
1 
Open the Background Task Definition form and execute Filte-In-Place.
2 
Initiate a new record, if needed.
3 
Specify this information:
Task Name
Enter AddProcessErrorLogSp.
This is typically the name of the stored procedure.
Task Description
Optionally, specify a helpful task description.
Executable Name
Provide the name of the stored procedure.
Executable Type
Select SP.
Maximum Concurrent
Specify the maximum number of concurrent instances of this task you want TaskMan to allow.
The default value is 20.
Valid values are positive integers. A value of -1 means any number of concurrent instances of this task
are allowed to run, up to other limits imposed on all tasks.
Caution:  Do not attempt to use a value of zero (0), as it disables the task completely.
Isolation Level
Select the transaction isolation level to use when running stored procedure tasks (Executable Type is
set to SP) or report stored procedures (Executable Type is set to RPT):
•
COMMITTED: All queries run from this stored procedure see only data for which the query can get
a shared lock. No reads of uncommitted data are performed.
•
UNCOMMITTED: All queries run from this stored procedure can read uncommitted data. Use this
option carefully, since it can display data that has not yet been committed in the database.
If no isolation level is set in this form, the stored procedure uses the global Collection Read Mode value
from the Process Defaults form. If that value does not exist, the stored procedure uses COMMITTED as
the transaction isolation level.
4 
Save, and close the Background Task Definition form.
5 
Open your form in the Web designer.
6 
Add a Button component.
7 
In the Component > Properties sheet, provide this information:
Name
Enter: TestSP
Infor Mongoose Administration Guide | 111
 
TaskMan and background tasks

Caption
Enter: sTest
8 
Create an event for the TestSP button:
a 
In the Component > Events sheet, next to the Primary field, click the ellipses (•••) button.
b 
In the Event Handlers dialog box, click New.
c 
In the Event Handler Properties dialog box, provide this information:
Event
Enter: RunSP
Description
Optionally, provide a helpful event description.
Type
Select Run Background Task.
d 
Next to the Parameters field, click the ellipses (•••) button.
e 
In the Event Handler Parameters dialog box, provide this information:
Error Message
Enter: mBackendMessage
Success Message
Enter: sSubmitted
f 
Click Type Specific Parameters.
g 
In the Edit Background Task Name and Parms dialog box, provide this information:
Task Name
Enter: AddProcessErrorLogSp
Task Parms
Enter: BG~TASKID~,FV(TestMessage)
Note:  The FV keyword tells the application to enclose the value of TestMessage in single quotes. The
BG~TASKID~ substitution keyword is replaced at runtime with the task number.
h 
Click OK repeatedly until you are back in the Events tab.
i 
Verify that RunSP is the primary event for the TestSP button.
9 
Save the form.
10 Add an Edit component with a variable called TestMessage as the data source.
11 Save the form.
12 Exit the designer.
13 Specify a message in the TestMessage Edit component and then submit the task.
The system displays a message box that says: Submitted.
14 Open the Background Task History form.
When the task completes, the test message shows up in the event log.
Infor Mongoose Administration Guide | 112
 
TaskMan and background tasks

Setting up IDO methods
This example shows the steps to set up an IDO method that runs using TaskMan from a form:
1 
Set up a task definition on the Background Task Definition form:
Task Name
Provide the name of the IDO method.
Executable Name
Provide the name of the IDO method to be run. Use this format: IDO.method.
Executable Type
Select IDOMTH.
2 
In the form from which you want to run the IDO method, set up a component that has a form event whose
task parameters match the IDO method’s parameters. You can pass bare values, for example,
MyParameter1,MyParameter2.
Note:  The application does not allow you to use ~LIT~ syntax as part of a value. If white spaces are
significant, use the keywords such as P(...), V(...), C(...), and FPC(...), FV(...), or FC(...).
3 
Save the event and the form.
Using Store Options and Get Options
Use the Store Options form and the Get Options form to store background task settings and reuse them.
Store Options
For any report or utility in your application, you can use the Store Options form to save the information you
entered for later use. After you specify the desired information on the report or utility form, select Actions >
Store Options to save your entries.
Note:  System administrators can store options for any user. Non-administrators can only store options for
the current user.
Get Options
Use the Get Options form to recall any stored options. After you store options for a form, from that report or
utility for which you want those options, select Actions > Get Options.
Options Defaults
Use the Options Defaults form to view the stored options for a form.
Infor Mongoose Administration Guide | 113
 
TaskMan and background tasks

Using TaskMan substitution keywords
TaskMan supports substitution keywords.
Before you execute a task, TaskMan replaces substitution keywords with their appropriate values when
creating a string made up of the task executable name, as defined on the Background Task Definitions form,
and the task parameters that is passed from the form.
Generally, you can specify these keywords when you define task parameters for an event on a form. Some of
these keywords are used to get values from TaskMan settings:
Description
Keyword
Replaced by the task number from the ActiveBGTasks and BGTaskHis-
tory tables
BG~TASKID~
Replaced by the task name from the BGTaskDefinitions table
BG~TASKNAME~
Replaced by the configuration name
BG~CONFIG~
Replaced by the user name requesting the task
BG~REQUSER~
Login associated with the user ID used to connect to a database
BG~SQLLOGIN~
Name of the DSN used by TaskMan to connect to a database
BG~DSN~
User ID used by TaskMan to connect to a database
BG~UID~
Server name used by TaskMan to connect to a database
BG~SERVER~
Database name used by TaskMan to connect to a database
BG~DB~
Directory where TaskMan.exe and RunReport.exe are installed
BG~TMHOMEDIR~
When a task completes, TaskMan copies the contents of this file to
BGTaskHistory, and then deletes the file
BG~ERRFILE~
Path to the output directory under the TaskMan home director
Allows EXEs to run through TaskMan to produce output
BG~OUTDIR~
The SQL Server isolation level used by TaskMan for reports and stored
procedures
The return value is UNCOMMITTED or COMMITTED. Values are set in the
Isolation Level field on theBackground Task Definitions form and
the Collection Read Mode field on the Process Defaults form.
BG~ISOLATIONLEVEL~
The report output file created in the directory \TaskMan_Directory\Re-
port\OutputFiles\user on the TaskMan machine
BG~OUTPUTFILE~
TaskMan also supports additional keywords used as command line switches. TaskMan deletes these keywords
from the task executable and parameter string:
Infor Mongoose Administration Guide | 114
 
TaskMan and background tasks

Description
Keyword
The application wraps many literal values in ~LIT~(...). This keyword,
which can be specified in an event handler on a form, tells TaskMan
to leave these values. For example, you can create an event on a form
that runs an EXE through TaskMan. Use this keyword to allow any ~LIT~
keywords in the parameters to be passed through to the EXE.
BG~LEAVELITS~
TaskMan normally puts the contents of the BG~ERRFILE~ error file in
BGTaskHistory and then deletes the file. This keyword tells TaskMan
to leave the error files.
BG~LEAVETEMPS~
Running TaskMan in debug mode
If you have problems with a background task, you can run TaskMan in debug mode. Doing so generates
additional messages for the Microsoft Event Viewer.
Enabling debug mode through your application
When attempting to debug your application, you can use a process default to avoid having to stop and restart
the Infor Framework Taskman service.
To enable debug mode through your application, use this procedure:
1 
Open the Process Defaults form.
2 
Provide this information:
Default Name
Locate and select TaskMan options.
If the TaskMan options process default is not already set, initiate a new record and select it from the
drop-down list.
Default Value
Enter: debug
3 
Save and close the form.
TaskMan is now in debug mode. When you are finished with your debugging, remember to remove this entry
from the Process Defaults form.
Infor Mongoose Administration Guide | 115
 
TaskMan and background tasks

Enabling debug mode through the TaskMan service
Note:  This topic applies solely to system administrators for your work environment and primarily to
on-premises work environments.
To enable debug mode through the Infor Framework Taskman service, use this procedure:
1 
On the server where TaskMan resides, open Windows Services.
2 
In the list of services, select Infor Framework TaskMan.
3 
If the TaskMan service is running, stop the service.
Caution:  We strongly recommend that you make sure that no TaskMan process is running before you
stop the service. When you stop TaskMan, all running tasks are terminated.
4 
Right-click on the Infor Framework TaskMan service and select Properties.
5 
In the Start Parameters field of the Properties dialog box, specify debug.
6 
To restart TaskMan, click Start.
TaskMan is now in debug mode. When you finish debugging your application, make sure that you reverse this
procedure; that is, that you stop the TaskMan service, remove the debug parameter, and then restart the
service.
There is also an optional nowait parameter for TaskMan. If you manually start TaskMan, this keyword allows
the service to start faster.
TaskMan debug mode messages
This table lists and describes the Infor Framework Taskman service debug mode messages:
Description
Message
This message is generated when a stored procedure
is called.
<SPname> After Call 
<TaskManSource>:<SourceLineNr> 
This message indicates that TaskMan is trying to
clear database connections
Active Task Set not open: <DatabaseInfo>. 
<TaskManSource>:<SourceLineNr>
This message indicates that an error occurred while
retrieving information about the home directory
from which TaskMan was executing.
Cannot find last slash. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Close process connection completed for Task <n>.
 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Closing database: 
<dsn>.<TaskManSource>:<SourceLineNr> 
This is an information message only.
Closing process connection for Task <n>. 
<TaskManSource>:<SourceLineNr>
This informational message indicates that TaskMan
is decrementing the Running Tasks list when the
task completes.
Decrement <taskname> 
<TaskNr>.<TaskManSource>:<SourceLineNr>
Infor Mongoose Administration Guide | 116
 
TaskMan and background tasks

Description
Message
This informational message indicates that the task
has been deleted from the active tasks table.
DELETE ActiveBGTasks where TaskNumber = <n>. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that TaskMan
is cleaning up report (RPT) process handles.
Delete TaskInfo handle. 
<TaskManSource>:<SourceLineNr> 
TaskMan retrieves its module name in order to get
its home directory. This message indicates that there
was an error in retrieving the module name, so
TaskMan cannot determine its home directory.
Error retrieving TaskMan Module Name. 
<TaskManSource>:<SourceLineNr>
This message indicates that TaskMan is polling the
table for active background tasks in the each config-
ured application database.
Increment <taskname> <tasknumber> 
Total requests <n> Queue size <size>. 
<TaskManSource>:<SourceLineNr> 
This message indicates that TaskMan is using the
default values because it cannot find a matching
Intranets record.
No Intranet records found, using defaults. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Opening Intranet record set. 
<TaskManSource>:<SourceLineNr> 
This information message indicates that TaskMan
has paused to make sure that SQL Server has started
before trying to access the databases.
Pause to ensure SQL Server is completely up.
<TaskManSource>:<SourceLineNr>
This message indicates that TaskMan successfully
queried the Intranet table and retrieved the speci-
fied information.
Poll=<n> 
Connect=<n> 
Process=<n> 
MaxNo=<n> 
NumRec=<n> 
site = <site> 
Intranet = <intranetname> 
String Table = <stringtable> 
URL = <URLpath> 
Format = <outputformat> 
ReportPath = <path> 
Email Notif = <emailnotification>. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan failed
while trying to run a report task.
Rpt task failed. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
ServiceMain starting. 
<TaskManSource>:<SourceLineNr>
This error message indicates that the attempted SQL
cancelation of the process and task was not success-
ful.
SQLCancel failed. Deleting TaskInfo handle. 
<TaskManSource>:<SourceLineNr>
This error message indicates that TaskMan canceled
a stored procedure background task.
SQLCancel of Task <tasknumber> completed. 
Return Code = <n>. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that a Windows error
occurred.
Task <tasknumber> <taskname>. 
An error occurred while waiting for the process
 to finish. 
Error return = <code> message = <message>. 
<TaskManSource>:<SourceLineNr> 
Infor Mongoose Administration Guide | 117
 
TaskMan and background tasks

Description
Message
This error message indicates that TaskMan tried and
failed to add a task message to the Background Task
History table.
Task <tasknumber> <taskname>. 
Call to AddProcessErrorLogSp failed for user 
<userID>. 
Return code = <code>. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan cannot
retrieve information from the Report Options table.
Task <tasknumber> <taskname>. 
Call to CloseSessionSp failed for task 
<taskname> user <userID>. 
Return code = <code>, Error message = <message>.
 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that TaskMan's
attempt to call the GetTaskOptionsSp stored proce-
dure failed.
Task <tasknumber> <taskname>. 
Call to GetTaskOptionsSp failed for task 
<taskname> user <userID>. 
Return code = <code>, Error message = <message>.
 
<TaskManSource>:<SourceLineNr>
This informational message indicates that TaskMan's
attempt to call the InitSessionContextSp stored
procedure failed.
Task <tasknumber> <taskname>. 
Call to InitSessionContextSp failed for task 
<taskname> user <userId>. 
Return code = <code>, Error message = <message>.
 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Task <tasknumber> <taskname>. 
Calling AddProcessErrorLogSp. <commandline>. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Task <tasknumber> <taskname>. 
Calling sp.Call 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan cannot
start a stored procedure background task.
Task <tasknumber> <taskname>. 
Can transact. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan cannot
start a transaction for a stored procedure task. The
stored procedure is not executed.
Task <tasknumber> <taskname>. 
Cannot transact. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
CloseSessionSp called with Input Parameter 
<sessionID>. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
Could not close session <sessionID>. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
Creating directory <directory>. Result = <code>.
 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan could
not process a request because the database was not
open.
Task <tasknumber> <taskname>. 
Database not open. Cannot enter Process Error:
 <message>. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that the task
has been deleted from the active tasks table.
Task <tasknumber> <taskname>. 
DELETE ActiveBGTasks where TaskNumber = <n>. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that an error occurred
when TaskMan started the specified command.
Task <tasknumber> <taskname>. 
Error <code> <message> when starting Command -
 
<commandline>. 
<TaskManSource>:<SourceLineNr> 
Infor Mongoose Administration Guide | 118
 
TaskMan and background tasks

Description
Message
This error message indicates that TaskMan cannot
copy the output file to the OutputFile folder.
To correct this problem, make sure that permissions
are set up properly and the folder exists.
Task <tasknumber> <taskname>. 
Error moving file <outputfile> to <new output-
file>. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that the
background task has finished running.
Task <tasknumber> <taskname>. 
Exiting RunTask: Removing Task from List. 
<TaskManSource>:<SourceLineNr>
This informational message indicates that TaskMan
is retrieving specific information about the runtime
user requesting this report background task.
Note:  This information is retrieve from the Report
Options form and the Intranets form.
Task <tasknumber> <taskname>. 
GetTaskOptionsSp called with Input Parameters 
<taskname>, 
<userID>, 
<stringID> 
returned Output Parameters format = <output 
format>, 
printer = <printer name>, 
email = <email notification>, 
attach = <attach report>, 
email address = <email address>, 
Return code = <code>, 
Error message = <message>, 
String Table = <string table>.
<TaskManSource>:<SourceLineNr>
This is an information message only.
Task <tasknumber> <taskname>. 
InitSessionContextSp called with Input Parameter
 <taskname>, 
returned Output Parameter <sessionID>, 
Return code = <code>. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that TaskMan
was unable to call the InitSessionContextSp stored
procedure.
Task <tasknumber> <taskname>. 
InitSessionContextSp failed. Could not call SP.
 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
No attachment sent: either the report was sent
 to the printer, 
the Task Type was not RPT, or the report didn't
 complete successfully. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that TaskMan
encountered a problem attempting to open the
UserNames database table.
Task <tasknumber> <taskname>. 
Opening user names failed. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
Password decrypted. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Task <tasknumber> <taskname>. 
Profile Values -- 
String Table: <stringtable>, 
String ID: <stringID>, 
Email: <email address>, 
Number of copies: <n>, 
Printer: <printer name>. 
<TaskManSource>:<SourceLineNr> 
This information message is generated immediately
after a background task has ended.
Task <tasknumber> <taskname>. 
Return = <code> WaitForSingleObject return = 
<code>. 
<TaskManSource>:<SourceLineNr> 
Infor Mongoose Administration Guide | 119
 
TaskMan and background tasks

Description
Message
This is an information message only.
Task <tasknumber> <taskname>. 
Running: <stored procedure>.
<TaskManSource>:<SourceLineNr>
This informational message is for the command line
that TaskMan executes to run a report.
For debugging purposes, you can paste this com-
mand line into a batch file and execute it:
RunReport.exe                   <CommandLine>
Task <tasknumber> <taskname>. 
Running Report: <RunReport.exe command line>.
<TaskManSource>:<SourceLineNr>
This informational message indicates that TaskMan
has opened a connection for a background task.
That task is used to update the Task History table
and to delete entries from the Active Task table in
the application database.
Task <tasknumber> <taskname>. 
Setting Connection Timeout <n> and opening a 
connection for this task. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
Task <tasknumber> <taskname>. 
Setting Process Timeout <n>. 
<TaskManSource>:<SourceLineNr> 
This error message indicates that TaskMan cannot
get the SQL login information needed to process the
task.
Task <tasknumber> <taskname>. 
Stored Procedure call GetSQLServerLoginSp failed
 with return code <code> 
and error message <message>. 
<TaskManSource>:<SourceLineNr>
This informational message indicates that a stored
procedure background task has completed success-
fully and can be committed.
Task <tasknumber> <taskname>. 
Successful termination of Task. Performing com-
mit. 
<TaskManSource>:<SourceLineNr> 
This informational messages indicates that TaskMan
could not access an RTF file using the specified path.
To correct this problem, verify that the file and path
exist and that permissions allow TaskMan access to
it.
Task <tasknumber> <taskname>. 
Unable to access <documentFilePath>. 
This informational message indicates that, as
TaskMan selects a background task to run, it is to
change its status to RUNNING.
Task <tasknumber> <taskname>. 
UPDATE ActiveBGTasks SET TaskStatusCode = 'RUN-
NING' where TaskNumber = <n>.
<TaskManSource>:<SourceLineNr> 
This informational message indicates that, when
TaskMan completes a background task, it is to up-
date the BGTaskHistory table with the appropriate
information.
Task <tasknumber> <taskname>. 
UPDATE BGTaskHistory SET CompletionDate = 
<date>, 
CompletionStatus = <status>, 
TaskErrorMsg = '<message>' where TaskNumber = 
<n>. 
<TaskManSource>:<SourceLineNr> 
This informational message indicates that TaskMan
is to update the process ID (pid) for a background
task in the BGTaskHistory table.
Note:  You can use the process ID to trace the status
of a background task in the Windows Task Manager.
This ID also displays in the Background Task Histo-
ry form.
Task <tasknumber> <taskname>. 
UPDATE BGTaskHistory SET ProcessId = <pid> 
WHERE TaskNumber = <n>. 
<TaskManSource>:<SourceLineNr> 
Infor Mongoose Administration Guide | 120
 
TaskMan and background tasks

Description
Message
This informational message indicates the user ID of
the individual requesting the current background
task.
Task <tasknumber> <taskname>. 
UserNames.Username = '<userID>'.
<TaskManSource>:<SourceLineNr> 
This informational message indicates the user ID
and group name of the individual requesting the
current background task.
Task <tasknumber> <taskname>. 
Usernames.Username = <userID> 
Groupname = <groupname>.
<TaskManSource>:<SourceLineNr> 
This error message indicates that the number of
running tasks in the system does not match the task
counter value.
Taskman Error: TaskCounter is not keeping accu-
rate count of Running Tasks. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
Taskman home directory: <homedirectory>. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
TaskMan Stopping: Clearing database connections.
 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
The maximum number of concurrently running tasks
 is <n>. 
<TaskManSource>:<SourceLineNr>
This is an information message only.
<dsn> UID <userID> PWD **** 
String Table=<stringtable> 
Poll=<n> 
Process Timeout=<n> 
Connection Timeout=<n> 
Max Num Tasks=<n>. 
<TaskManSource>:<SourceLineNr> 
This is an information message only.
UPDATE BGTaskHistory 
SET CompletionDate = <date>, 
CompletionStatus = <status>, 
TaskErrorMsg = '<message>' where TaskNumber = 
<n>. 
<TaskManSource>:<SourceLineNr> 
Checking TaskMan event messages
In addition to the debug messages, TaskMan generates event messages during normal processing. You can
view these messages in the Microsoft Event Viewer.
To access the Event Viewer, perform these steps on the server where TaskMan resides:
1 
Select Settings > Control Panel from the Windows Start menu.
2 
Open Administrative Tools and then Event Viewer.
3 
Select Application Log.
Infor Mongoose Administration Guide | 121
 
TaskMan and background tasks

Troubleshooting problems with TaskMan
This group of topics describes several possible problems with TaskMan and how to resolve them.
These topics are included in this group:
•
TaskMan does not start on page 122
•
Changes to Intranets form settings are ignored on page 122
•
Background task runs but has no history record on page 123
•
Labels not replaced with string table values on page 123
•
The transport failed to connect to the server on page 124
•
Application event messages from TaskMan on page 124
TaskMan does not start
Symptoms
The Infor Framework Taskman service does not start, and you see this message in the Application Event log:
No database definitions defined. TaskMan must be configured before starting the service.
Possible solutions
•
Run the Service Configuration Manager utility and verify that the correct Application Databases are
configured for TaskMan.
•
Taskman might be running under a user ID that does not have privileges to access the registry. Try
restarting the service to run it as a local system account. If it starts, then the problem is with the user ID.
•
In Windows, restart the Infor Framework Taskman as an active service.
Changes to Intranets form settings are ignored
Symptoms
Changes are made on the Intranets form, but the changes are not reflected in tasks that subsequently run
and that should be using the changed values.
Possible solutions
When the Infor Framework TaskMan service starts, it caches information from the Intranets table. If you then
change a setting on the Intranets form, TaskMan does not 'see' this change. Instead, it continues to use the
cached setting for these fields:
•
Polling Interval
•
Connection Query Timeout
Infor Mongoose Administration Guide | 122
 
TaskMan and background tasks

•
Process Timeout
•
Maximum Concurrent Tasks
For this reason, after changing one of these settings on the Intranets form, you must stop and restart the
Infor Framework TaskMan service so the change can take effect.
Background task runs but has no history record
Symptoms
A background task is submitted from a form. It appears to have been submitted without any problems, but
no record is created in the Background Task History form.
Explanation/Possible solutions
The BGTaskHistory record is created by a trigger on the ActiveBGTasks table. ActiveBGTasks is the queue of
tasks submitted to TaskMan. So, if there is no history record, the task never made it to the queue, despite any
messages that might have displayed on the form.
This is probably a bug in the sequence of form events the form used to submit the task.
Labels not replaced with string table values
Symptoms
Component labels are not being replaced with string table values or are not being translated properly.
Explanation/Possible solutions
Use the Site Name from the System Parameters form to select the correct record on the Sites form. Make
sure that, for this site, there is a value in the Sites form’s Forms Database Name field. In some versions, this
field is labeled as the Strings Table Specification field. If the forms database is on a different server than the
application database, the field’s value should also indicate the linked server name, in this format:
serverName.formsDatabase
Infor Framework Taskman then determines the proper strings table name by searching the specified forms
database for the strings table associated with the current application session.
If labels are not translated, stop and restart TaskMan. The Strings Table Specification field or the Forms
Database Name field might have been modified after TaskMan was last started. TaskMan checks this value
only once, when it first starts up.
Infor Mongoose Administration Guide | 123
 
TaskMan and background tasks

The transport failed to connect to the server
Symptoms
When you attempt to send an email with the SMTP protocol, or if you receive a similar error when testing the
SMTP protocol using Telnet, TaskMan returns this error:
The transport failed to connect to the server.
Possible solutions
The firewall or antivirus software on the mail server might be blocking the email.
Application event messages from TaskMan
TaskMan runs as a Windows service called Infor Framework Taskman. This service generates event messages
that you can view in the Microsoft Event Viewer. If you have problems with a background task, you can run
TaskMan in debug mode, which generates additional messages for the Microsoft Event Viewer.
The messages listed here are generated normally and do not require Infor Framework Taskman to run in
debug mode. If a database exception occurs, TaskMan tries to retrieve and log the error message.
Explanation/Description
Message
This error message indicates that a function call
failed and provides the failure location.
<functionName> failed with return code <code>
in <TaskManSource> at <SourceLineNr>.
This error message indicates that a stored procedure
background task was rolled back due to one of these
circumstances:
•
The stored procedure generated a return code
less than 0 or greater than 5.
•
Executing the stored procedure generated an
exception.
Abnormal termination of Task <n> (returncode =
<code>). Performing rollback.
This message is informational only.
An unknown exception occurred. <TaskManSource>:
<SourceLineNr>
This error message indicates that a TaskMan stored
procedure process was canceled because of a shut-
down.
Canceling Process <taskname>, Task <n>
This error message indicates that a TaskMan report
task was canceled because of a shutdown.
Note:  Some reports might be left hanging even if
TaskMan is shut down.
Canceling Report <taskname>, Task <n>
This message is informational only.
DSN <dsn> Database <db>. Login successful
Infor Mongoose Administration Guide | 124
 
TaskMan and background tasks

Explanation/Description
Message
This message is informational only.
Either the DSN or the SQL login was not set.
<ReturnMessage>
This error message indicates that a critical handler
for a background task failed.
RegisterServiceCtrlHandler failed with return
code <n> in <TaskManSource> at <SourceLineNr>.
This informational message indicates that, if
TaskMan is shut down while tasks are running, those
tasks will be canceled.
RSTaskMan shutdown: <n> tasks are running and
will be canceled.
This message is informational only.
RSTaskMan starting: Version <n>
This message is informational only.
RSTaskMan Terminating
This error message indicates that TaskMan cannot
determine its home directory and will shut down.
RSTaskMan Terminating - TaskMan Home Directory
not properly detected
This message is informational only.
SetServiceStatus failed in <TaskManSource> at
<SourceLineNr>.
This error message indicates that TaskMan was not
able to start the Service Dispatcher.
StartServiceCtrlDispatcher failed with return
code <n> in <TaskManSource> at <SourceLineNr>.
This error message indicates that the value for the
Executable Type setting (from the Background
Task Definitions form) was not valid.
TaskTypeCode not recognized
Using SQL profiler to trace TaskMan instances
Note:  This topic applies primarily to on-premises environments. Multi-tenant cloud customer adminstrators
cannot perform this procedure.
To use the SQL Profiler to see which instance of TaskMan is monitoring an application database, follow these
steps:
1 
Start the SQL Server Profiler.
2 
In the SQL Server Profiler, select File > New Trace.
3 
Connect to the application database server you want to trace.
4 
On the Events Selection tab, remove everything from the Events column except TSQL -- SQL:Batch
completed.
5 
On the Data Columns tab, add Server Name to the Selected Data Column.
6 
To start the trace, click Run.
Infor Mongoose Administration Guide | 125
 
TaskMan and background tasks

Using stored procedures for performance benefit
Note:  This topic applies primarily to on-premises environments. Multi-tenant cloud customer adminstrators
cannot perform this procedure.
To maximize efficiency and improve the performance of your system, these stored procedures should be run
regularly:
•
sp_updatestats: To update statistics for all the tables in a database, run this SQL-supplied stored procedure
regularly. You can get more information about this stored procedure from the SQL online help.
•
SLServerRestartSp: This stored procedure runs and performs general cleanup whenever the database
server is restarted, as no one is logged in at that time.
•
PurgeNextKeysSp: Run this stored procedure to clean up the NextKeys table. NextKey records are inserted
but are never updated to get concurrency. This stored procedure cleans out the extra rows.
Note:  Do not run this utility while anyone is using the system. The utility locks users out, so you should
verify that everyone is logged out of the system before running this utility.
Infor Mongoose Administration Guide | 126
 
TaskMan and background tasks


