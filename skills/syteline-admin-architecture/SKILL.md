---
name: "syteline-admin-architecture"
description: "System architecture and user authorizations - Mongoose layers, IDO runtime, configurations, users/groups/permissions, editing levels, super users, object security"
version: "1.0"
tags:
  - syteline
  - admin
  - architecture
  - security
  - users
  - mongoose
---

# syteline-admin-architecture

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 1: System architecture
This section provides an overview of the Mongoose architecture.
Here is a diagram of the architecture:
About SQL Server and the framework
Microsoft SQL Server is responsible for:
•
Maintaining the relationships between data in the database
•
Ensuring that data is stored correctly and that the rules defining data relationships are not violated
•
Managing data and user security
Infor Mongoose Administration Guide | 13
 
System architecture

•
Optimizing server function performance
•
Recovering all data to a point of known consistency in case of system failures
SQL Server is primarily responsible for managing databases that interact with enterprise business systems.
Some of the components that make up these databases are:
•
Tables
•
Views
•
Stored procedures
•
Triggers
•
Constraints
•
Indexes
•
Keys
•
User defined data types
These databases are available in the SQL Server: The application database, the forms database, the objects
database, and the templates database. In current usage these databases are usually combined into a single
"All-in-One" database.
Application database
The application database holds all of the user and administrative data for your Mongoose-based application.
This database is made up of tables, stored procedures, triggers, user defined data types, and indexes.
Forms database
The forms database holds all of the metadata used to construct about each form.
For example, components such as text boxes, labels, tabs, radio groups, buttons, and all of the associated
properties, such as size, color, validation, and events, are kept in the forms database in a multitude of tables.
Objects database
The objects database stores the IDO (Intelligent Data Objects) metadata.
Templates database
The templates database stores information about form structures from the Form Wizard, This includes such
information as the form type, the associated IDO name, IDO properties, and so on. If you decide to recreate
a form, all the structural information previously entered is preserved so that you can use it as a base and
make incremental changes. This is typically only used in the initial stages of form development. Once you
start adjusting layout and adding components, we do not recommend that you recreate the form unless you
understand that any changes you have made are lost.
Infor Mongoose Administration Guide | 14
 
System architecture

Minimum requirements for SQL Server logins
Note:  This topic applies only to system administrators in on-premises installations. Administrators for
Multi-Tenant Cloud (MTC) customers do not have access to SQL Servers.
A SQL user login is required when setting up configurations that point to the various Mongoose system
databases. This SQL account requires certain permission levels to these databases. The SQL 'sa' user account
has all the required permissions and is often used to set up configurations.
However, the 'sa' login also has a much greater scope of permissions than is required and many organizations
prefer to make very minimal use of the 'sa' account. In such cases where organizations do not want to use
the 'sa' login credentials, it is possible to set up a special set of SQL user logins that have the minimum required
permissions to get the job done.
Note:  The 'sa' user login must be set in the Configuration Manager. For this reason, it must be created by
Infor Cloud Operations personnel. MTC customer administrators, however, can reset the 'sa' user password
on the Users form.
SQL Server logins come into play at two basic times: When you create the databases required for
Mongoose-based application configurations; and when creating and using the actual configurations. Each
of these situations requires somewhat different levels of permission on SQL Server.
These are the SQL Server login requirements:
•
Creation of a custom role, mg_executor
•
Connections to the application, forms, and templates databases require db_datareader, db_datawriter,
and mg_executor permissions
•
Any operation where views to the objects database are regenerated also require db_datareader,
db_datawriter, and mg_executor permissions
•
Operations in the application database, in which the schema is to be altered, require those same
permissions, plus db_ddladmin permissions
Changing the database name
If for any reason you have changed the name of your Application Database, you must also change a pointer
in the system to that database using the new name.
To change the database name, use this procedure:
1 
Open the Sites form.
2 
In the System Info > Database Name field, specify the new database name.
3 
Click Save.
Infor Mongoose Administration Guide | 15
 
System architecture

About the user client
The user client or the web client is where the user interacts with the application data and can customize forms
in browsers and can run on multiple platforms and devices. Users can get the URLs from the administrator.
These URLs are set up on the Application Server when your application is installed.
Infor Mongoose Administration Guide | 16
 
System architecture

Chapter 2: User authorizations
About user authorizations
An individual user's access to and ability to use various forms and IDOs are controlled by these things (in
order):
•
The license modules that the user is assigned to
•
The group or groups to which the user belongs
•
Individual user authorizations
License modules
Before a user can access any forms or other objects, that user must have the proper license modules assigned.
For example, if the user is a form/application developer, that user would need to have the MG Developer
license module assigned.
License modules are assigned to users on the User Modules form, which is accessed by clicking the User
Modules button on the Users form.
User groups
Once license modules (which provide basic access) have been assigned, the most common—and most
recommended—way of assigning permissions to a user is by means of one or more user groups. User groups
allow system administrators to assign permissions for a whole set of forms to everyone assigned to that group,
without having to assign the permissions individually to each user.
The system ships with several user authorization groups already defined. You can view a listing of these groups
(as well as any user groups you might have created) on the Groups form. For each group, this same form also
displays a list of all the users who are members of that group.
You can also add your own user groups, using the same Groups form.
Note:  We recommend that, when creating your own groups, you use a naming convention that clearly
identifies the group as one that you (or someone else in your organization) created. For example, if you worked
for the Acme Company, you might create a name like this: Acme Sales.
To see what forms and other objects (IDOs and file servers) are associated with and controlled by a particular
group, select that group in the Grid View and then click Group Authorizations. This launches the Object
Authorization for Group form. This form displays all the forms and other objects that belong to that group.
Use the Object Authorization for Group form also to modify the list of forms or other objects that a group
can access or to modify its access permissions for objects.
Infor Mongoose Administration Guide | 17
 
User authorizations

Individual user authorizations
A third way that you can set permissions for users is to set them for individual users. These are typically special
cases, used when the user needs some permission not granted by group membership—OR needs a permission
granted by group membership to be revoked. Individual user authorizations and permissions override group
settings.
To set authorizations and permissions for individual users, use the Object Authorization for User form,
which is accessible from both the Users form and the Groups form.
Other means to control access and grant permissions
The framework also provides a variety of other means you can use to control access to forms and other objects,
including these:
•
Designating what basic type of user an individual user is when you create the user ID profile (whether
Basic, Full User, Site Developer, or whatever).
•
Setting row authorizations, which are settings that are used to filter on IDOs, so that users or groups can
see a restricted set of data from that IDO.
•
Using composite groups and subgroups to control multiple sets of users from a few settings.
•
Copying user settings from one user to another user who needs the same (or similar) permissions.
•
Creating authorizations for external "users" from web services. These web services "users" need to be
granted authorization for any IDOs being called by the web service.
Assigning user IDs and passwords
Setting up users
Initially, only the supplied default system administrator user ID can create or delete other user IDs. User IDs
and other information are set up on the Users form.
Setting passwords
Any user who is added to the System Administration group can access the Users form and change the password
for any other user. Individual users can change their own passwords using the User Information form.
System administrators can use the Password Parameters form to specify password requirements, such as
use of mixed case, numbers and special characters, and minimum/maximum password length. This form
also allows you to set up rules for password expiration and locking out users after a certain number of password
retries.
See Setting Password Parameters on page 20.
Using token-based authentication
One option to increase system security is to require users to enter a second password, called a "passcode,"
from a token-based authentication service. When this type of security is used, the Sign In dialog box displays
Infor Mongoose Administration Guide | 18
 
User authorizations

a passcode field when the user attempts to log in. The user must obtain a passcode from a hardware or
software "token" and enter it in this field to successfully log in.
See Token Authentication in Mongoose Applications on page 21.
Locked-out users
If users enter an incorrect password more times than the allowed number of retries (as specified on the
Password Parameters form), they are locked out of the system for the number of minutes specified in Lockout
Duration (on the Password Parameters form). If a user must log in to the system before the lockout time is
up, a system administrator can manually override the lockout on the Users form by setting the User Login
Status for the user to Active, which then resets the Login Failures to 0.
Portal user information
Portal users can control some information about their user account from the User Portal Profile form. They
can change their passwords and user descriptions, specify primary email addresses, specify whether they
want to receive external prompts and notifications, and specify their preferred theme. They cannot change
their user IDs.
Before a user can update information on the User Profile Portal form, the system administrator must define
on the Users form at least the user ID, a user description, and an initial password. If more than one email
account is specified on the Users form, only the primary email account displays and can be changed on the
User Profile Portal form.
The system administrator must also ensure that the user is authorized to access the User Portal Profile form.
The administrator must then provide a version of this URL to portal users:
http(s)://webServer/WSWebClient/Default.aspx?page=formonly&form=UserProfilePortal&notitle=1
where webServer is the name of the web server that is specified in the Configuration Manager.
Creating "super users"
A "super user" can run all forms and perform all actions on all forms for which they hold a license.
Note:  In some cases, actions can be performed only by the ’sa’ user account.
If you create a super user, you do not need to set any other authorizations for this user. The super user status
overrides all other types of authorizations.
To create a super user, use this procedure:
1 
Open the Users form.
2 
Select the desired user ID.
3 
Select the Super User option.
4 
Click Save.
Infor Mongoose Administration Guide | 19
 
User authorizations

Changing passwords
Any user who is added to the System Administration group or designated as a "super user" can access the
Users form and change the password there for any other user. Users without such authorization can change
only their own passwords on the User Information form.
Setting password parameters
To specify parameters that apply to the password settings for users at all sites in an application database,
use this procedure:
1 
Open the Password Parameters form and specify this information:
Enforce Mixed Case
When this field is selected (the default), user passwords must include at least one uppercase character
and one lowercase character, for example: MyPassword
Enforce Number
When this field is selected (the default), user passwords must include at least one number, for example:
mypassword1
Enforce Special Character
When this field is selected (the default), user passwords must include at least one special character, for
example: my#password. Special characters include any character that is not either an alphabetic character
or a numeric digit.
Forbid Embedded User Name
When this field is selected, user passwords cannot include the username.
Number of Retries
Specify the number of times users can re-enter their password before being locked out of the system.
The default value is 3.
A value of 0 disables the lockout feature for all users.
Lockout Duration (Minutes)
Specify the number of minutes users are locked out of the system following the maximum number of
unsuccessful login attempts. The default lockout duration is 30 minutes.
Password Length Minimum
Specify the minimum number of characters required for user passwords. The default value is 8.
Password Length Maximum
Specify the maximum number of characters allowed for user passwords. The default value is 30, which
is the most allowed by this application.
Consecutive Identical Character Max
Specify the number of times a user can consecutively include the same character in the password.
Infor Mongoose Administration Guide | 20
 
User authorizations

Password Expiration Days
Specify the number of days that are to elapse between a new password reset and its expiration. This
calculation starts each time the user sets or resets the password.
For example, if you specify a value of 60 , and a user resets his password on June 1, the password expires
on July 30. The Expiration Date on the Users form is set accordingly.
If you reset this value, the change only affects users who modify their passwords after the change was
made. The default value is 0, meaning the password does not expire.
Password Warning Days
Specify the number of days before the password expiration date that the user starts to see a warning
that the password is soon to expire. The warning message displays each time the user logs in and includes
the number of days left until the password expires. For example, if you specify a value of 5, and the user
password is set to expire on July 30, the warning message starts to display on July 26.
The default value is 0, which means that no warning is given. The value in this field must be less than
the value in the Password Expiration Days field.
Password History Count
Specify the number of previous passwords that the system is to remember for each user. When a user
creates a new password, it cannot match any other previous passwords that the system remembers.
Password Minimum Days
Specify the number of days that must pass after the user resets the password before the user can reset
the password again. The intended use of this setting is to prevent users from quickly cycling through
the Password History Count so as to effectively keep the same password all the time.
The default value is 0, which indicates that there is no minimum number of days before the password
can be reset. System administrators can override this value for users if necessary.
See Password Set Date.
2 
Save your changes.
The changes are applied to user accounts the next time they log in.
Setting token authentication
One option to increase security and control access to your system is to use a token-based authentication
service in tandem with the normal application login credentials. With this type of system, users must enter
not only their user IDs and passwords, but also a second password, or passcode, obtained from a hardware
or software token.
To use a token-based authentication service in the application:
1 
Purchase access to the RSA SecurID token authentication service.
At this time, the RSA SecurID service is the only token authentication service supported in this application.
You are responsible for purchasing and setting up this service for your system.
2 
Configure your application to work with the token authentication service. This involves these basic
procedures:
Infor Mongoose Administration Guide | 21
 
User authorizations

•
Install and configure the Token Authenticator Service on the Application Server, using the application's
Configuration Wizard. This service is installed and run as an optional IIS service but is required for
token authentication of applications. This service can be used by many Mongoose-based applications
simultaneously.
See the Infor Mongoose Installation Guide.
•
Configure the application in the Configuration Manager.
•
Set the passcode mapping for each user who will be using the service.
•
Let each user know how to use the token to sign in.
About users and groups
You can set access levels and authorizations both for individual users and/or for groups of users.
Group authorizations
When you create a new user profile, perhaps the easiest way to assign authorizations and permissions to that
user is by assigning the user to one or more user groups. This is done using the Users form, when you create
the user profile.
Several default groups are already set up and delivered in the application; for example, the CoreFormsAdmin
and the APPBUILDER-Administrator group. To modify the forms and permissions for an existing group, select
the group on the Groups form and click Group Authorizations to open the Object Authorization for Group
form.
You can add or delete groups using the Groups form.
Caution:  Although the system allows you to modify or delete default groups that are provided with your
application, doing so can cause future conversion problems while upgrading, and possibly other problems.
We recommend you copy the the default group and give it a new group name, then modify the new group
authorizations. We strongly recommend that you NOT delete or modify the original default groups.
For a list of the default Mongoose groups, open the Groups form, select the group you want to know about,
and then click Group Authorizations. This launches the Object Authorization for Group form, which displays
the member forms (and/or other objects) for that group.
User authorizations by copying another user's goups
When you create a new user profile, you can copy the user's group memberships from an existing user. On
the Users form, select the user who needs to add groups, and click Copy Groups from User. On the Copy
Groups From User form, select the user from whom you want to copy the groups. If multiple groups are
listed, you can select the groups that you want to copy to this user. To return to the Users form, click OK.
Then save the changes to the new user's record there.
Infor Mongoose Administration Guide | 22
 
User authorizations

User authorizations
If a user is not assigned to any group, use the Object Authorizations for User form to determine what forms
and privileges are available to that user.
How authorizations work together
User groups allow you to determine the authorizations and permissions for multiple users by assigning them
all to a single group.
Group authorizations work together. If a user is included in a group where a privilege is granted on a certain
form, that granted privilege prevails over any "not granted" setting for the same form in other groups assigned
to this user. However, any user authorizations set for individuals always override group authorizations defined
for a form.
At the user authorizations level (Object Authorizations for User form), privileges are either granted or
revoked. There is only one set of privileges per form or per component per user. Therefore, if a privilege is
revoked at the user authorizations level, the same privilege, if granted, at the group authorizations level (on
the Object Authorizations for Group form) is not honored.
User authorizations cannot have multiple privileges for the same form or same component.
If privileges are left blank at the user authorization level, the user is assigned the permissions defined at the
group level.
User Authorization Report
In the User Authorization Report, user and group authorizations for forms and IDOs are grouped together
by user ID. Row authorizations are grouped together by user ID and group name, and are sorted by IDO and
group name. Options on the form let you choose the specific forms or IDOs you want to see in the report. You
can see and compare all authorizations for a single user in the same section of the report. This makes it easier
for you to determine whether a user has multiple permissions set differently for the same form, through
different groups to which the user is assigned.
Creating a user group
To create groups and to assign user IDs to them, use the Groups form. These groups can be organized in any
way that makes sense for your company or organization: by location, by organization, by job description, and
so on. Authorizations and permissions for the group apply to every individual or user ID in the group. Thus,
groups offer an easy and efficient way to assign permissions to users.
On this form, the External Entity and External Entity Application assigned to a user or a group also display.
To create a user group, use this procedure:
1 
Open the Groups form, and execute Filter-In-Place.
2 
Initiate a new record.
3 
Assign the group a Group Name.
4 
Optionally, provide a description of the group and how it is intended to be used.
Infor Mongoose Administration Guide | 23
 
User authorizations

5 
In the User ID column, assign at least one user to the new group.
6 
Optionally, on the Subgroup tab, Group Name field, select another group to be used as a subgroup.
This step is required only if the group is to be used as a composite group. See Creating a composite group
on page 25.
7 
Save the new group.
About groups, composite groups, and subgroups
Groups can be "nested" or combined as "composite groups", consisting of two or more "subgroups". Any
existing group can be used as a subgroup within a composite group.
Composite groups can be created to make the administration of authorizations and permissions of users
easier and more efficient.
When you create a composite group, any sub-groups inherit the permissions of the parent group.
This means, for example, that if a parent group in a composite group has only Execute and Read access of a
form object, and its child (subgroup) has both Update and Insert privileges, the end result would be
Execute-Read-Update-Insert access for all members of that subgroup.
Other examples
Example 1: Multiple sub-groups
This diagram represents a composite group in which:
•
Group A is the 'parent' group.
•
Group B is one sub-group of Group A.
•
Group C is a different sub-group of Group A.
Group A:
Execute
&
Read
privileges
Group B:
Write
privileges
Group C:
Edit
privileges
These are the results of this setup:
•
Users who are members only of Group A have Execute and Read privileges, but no other privileges.
•
Users who are members of Group B have Execute, Read, and Write privileges, but no other privileges.
Members of this group inherit Execute and Read privileges from Group A.
Infor Mongoose Administration Guide | 24
 
User authorizations

•
Users who are members of Group C have Execute, Read, and Edit privileges.
Members of this group inherit Execute and Read privileges from Group A.
•
Neither Group B nor Group C inherit anything from the other group.
Example 2: Multiple levels of sub-groups
This diagram represents a composite group in which:
•
Group A is the 'parent' group.
•
Group B is a sub-group of Group A.
•
Group C is a sub-group of Group B (which also makes it a sub-group of Group A).
Group A:
Execute
&
Read
privileges
Group B:
Write
privileges
Group C:
Edit
privileges
These are the results of this setup:
•
Users who are members only of Group A have Execute and Read privileges, but no other privileges.
•
Users who are members of Group B have Execute, Read, and Write privileges, but no other privileges.
Members of this group inherit Execute and Read privileges from Group A.
•
Users who are members of Group C have Execute, Read, Write, and Edit privileges.
Members of this group inherit Execute and Read privileges from Group A, as well as Write privileges from
Group B.
Creating a composite group
Groups can be "nested" or combined as "composite groups", consisting of two or more "subgroups". Any
existing group can be used as a subgroup within a composite group. These composite groups can be created
to make the administration of authorizations and permissions of users easier and more efficient.
Infor Mongoose Administration Guide | 25
 
User authorizations

To create a composite group, use this procedure:
1 
Open the Groups form.
2 
Perform one of these actions:
•
To use an existing group as the composite group, select the group from the Group Name column of
the grid view.
Note:  This is not generally a good practice, unless you know exactly the permissions granted to the
group or that group is newly created. Changing any permissions to a group changes all uses of that
group by all users. We recommend that, if you want to use a group as a composite group, you create
a new group or copy an existing group.
•
To create a new group to serve as the composite group, create it as you would any other group.
See Creating a user group on page 23.
Note:  When naming the composite group, we also recommend either that you use a naming convention
to identify it as a composite group; or that you provide a Group Description that identifies it as a composite
group.
3 
Verify that a group that you want to use as a subgroup to the composite group exists.
If that group does not exist, then you must create it before you use it as part of the composite group.
Note:  You can use multiple groups as subgroups. You can also use subgroups as composite groups within
parent composite groups. In other words, you can nest composite groups as subgroups inside other
composite groups.
4 
Save the new composite group.
Assigning a user to groups
When you assign a user to a group, that user gets access to all the forms, authorizations, and permissions
associated with that group.
To assign a user to a group, use this procedure:
1 
Open the Users form.
2 
Select the desired user ID.
3 
In the Groups > Group Name field, select the name of the group.
4 
Save your changes.
To assign the user to multiple groups, repeat this procedure for each additional group.
Assigning a user to a primary group
A primary group is used by the system to load the correct version of customized forms for the selected user.
Users can belong to more than one group, but only one group may be designated as the primary group.
Infor Mongoose Administration Guide | 26
 
User authorizations

Note:  In general, it is easier to first create the group, then assign user authorizations. This allows you to assign
each user a primary group that specifies authorizations without going through each user's authorizations
form by form.
For example, suppose user Bob is a member of the End User and Developer groups. End User group is marked
as Bob’s primary group. If Bob launches a form, the system looks for a group-level customized version of that
form for the End User group, not the Developer group. If such a customized version exists, and if there is no
user-level customized version for user Bob, then that is the form the system displays.
To assign a user to a primary group, use this procedure:
1 
Open the Users form.
2 
Select the desired user ID.
3 
On the Groups tab, specify this information:
Group Name
Select the name of the group.
Group Description
Optionally, specify a description, if one does not already exist.
Primary Group
Select this check box.
4 
Save your changes.
Editing authorizations for a user in a group
User authorizations override group authorizations. To edit the user authorizations for a user in a group, use
this procedure:
1 
Verify that the user is assigned to a group.
2 
Open the Users form.
3 
Select the user whose authorizations you want to edit.
4 
Click User Authorizations.
5 
On the Object Authorizations for User form, perform these steps:
a 
In the Object Type field, select whether the object is a Form, IDO, or a File Server.
b 
In the Object Name field, select the object that you want to edit for this user.
c 
Depending on the desired privileges, specify Granted or Revoked.
d 
Save your changes.
Copying user tables
Note:  This topic applies only in an on-premises environment.
Infor Mongoose Administration Guide | 27
 
User authorizations

If you upgrade your application from one version to another version, you can copy your user and group
authorizations to the new version with the Copy User Tables utility, so you do not have to set them up again.
•
This utility should only be used when you first set up a database for use, and not after users are defined,
and Foreign Key references are added.
•
The latest version of your application must be installed and licensed before you run this utility.
Note:  If your latest license is for fewer users than your old license, not all users will carry forward. When you
complete the procedure, open the Users form in the new version of your application to check the users, and
then make changes accordingly.
1 
In the Apps section of the Application Server, open the Copy User Tables utility.
2 
On the Source tab, provide this information:
SQL Server Name
Select the name of the server.
SQL Server Login ID
Specify the login ID.
SQL Server Logon Password
Specify the password associated with the login ID.
Use Windows Authentication
Select this check box to log in automatically to the server, using the username of the person currently
logged in to the Windows machine, instead of supplying a SQL Server login or password.
Source Database
Select the name of the Application Database that has the user or group authorizations that you want to
copy.
3 
On the Target tab, provide this information:
SQL Server Name
Select the name of the server.
SQL Server Login ID
Specify the login ID.
SQL Server Logon Password
Specify the password associated with the login ID.
Use Windows Authentication
Select this check box to log in automatically to the server, using the username of the person currently
logged in to the Windows machine, instead of supplying a SQL Server login or password.
Target Database
Select the name of the Application Database to which you want to copy the user or group authorizations.
4 
On the Login tab, provide this information:
User ID
Specify the user ID.
Infor Mongoose Administration Guide | 28
 
User authorizations

Password
Specify the password associated with the user ID.
Data Source Config
Select the target application database.
5 
Click Copy.
The status bar shows the progress. The process can take several minutes.
In the directory of the Application Server, a UserTables.txt file is created. This file lists the tables that were
copied by the Copy User Table utility.
Example: Hiding fields from certain users
You can control security at the component level. For example, you might allow outside salespeople or vendors
to view some of your application forms through a URL, but you do not want them to see certain fields such
as item costs. To do this for a group of users, use steps like these:
1 
On the Groups form, create a group called, for example, Hide Costs - Outside Users.
2 
Click Group Authorizations and add to this group the forms where you want to hide certain fields.
3 
For forms where you want the outside users to be able to view but not update the forms, set the Read
and Execute privileges to Granted and all of the others to Not Granted. For forms where you want to
allow them to make updates, set all privileges to Granted.
4 
Select one of the forms and click Component Privileges to set up privileges for specific fields
(components).
5 
In the Name field, select the component that you do not want the users to view, and set the Read and
Update privileges to Not Granted.  Leave the other columns blank.
6 
Repeat steps 4 and 5 for all forms in the group.
7 
On the Users form, select each of the outside users and assign them to this group.
Note:  You can also set up component-level authorizations for individual users. On the Users form, click User
Authorizations and follow similar steps.
Disabling inactive accounts
The system keeps track of the last date when any activity by each user in the application was recorded. This
information is stored for each user in the Last Active Date field on the Users form.
System administrators can set a value, in days, for the User inactivity threshold process default on the
Process Defaults form. If a value is set, then when a user logs in, the user's Last Active Date is compared to
the current date. If the difference is more than the number of days specified for the User inactivity threshold,
the login is refused, and the account is disabled.
Infor Mongoose Administration Guide | 29
 
User authorizations

For an account to be enabled again, the user's status on the Users form must be changed to Active.
Setting a lockout for session inactivity
System administrators can set the application to lock a user's computer if a session is idle for more than a
specified period of time. This is done by setting a process default to the number of minutes to allow a session
to remain idle before locking out the user.
For example, if the process default Browser inactivity lock minutes is set to 30, then any user who leaves
the computer running and idle for more than 30 minutes, with an application session open, is locked out of
the application. To unlock the application and return to the session, the user must provide the user password.
If the user (or other individual) is not successful in providing the password after a specified number of attempts,
then the application remains locked until a system administrator unlocks it.
About form-level security
Form-level security is checked when a user opens a form. The application queries the Account Authorizations
table to see what privileges are granted to the user or to the group to which the user belongs.
About row-level security
Row-level security is checked through the Row Authorizations form. This form allows you to create IDO-level
filters that are used to restrict data that users can view.
Using IDO filters to limit user access
To restrict the data that the user can see, you can place filters on the IDO. Any user client that accesses the
application can then view an appropriate subset of the data in the collection, based on the filters applied to
the IDO. IDO filters can be general, that is, applied to the IDO when anyone accesses it, or they can be
constrained by user IDs or groups.
For example, you can create a filter so that sales personnel can view only their own opportunities and not
those of other salespeople. Or you can create a filter so that your customers who log into the application
through a portal can see limited information about their orders.
To turn filters on and off as needed, for example, during testing, use the Active check box.
Infor Mongoose Administration Guide | 30
 
User authorizations

Caution:  By default, the Active check box is selected for all Infor-provided filters that are defined in the Row
Authorizations form. If you clear the Active check box for a filter, then any user who was previously restricted
by the filter is no longer restricted. For example, if you turn off the Infor-provided filters that are set on the
Interactions IDO, then any customer portal user can see ALL interactions in the system, not just the ones
related to that customer.
You can access (and edit) IDO filters using the Row Authorizations form, which can be launched using any
of these methods:
•
On the IDOs form, click Filters to open the form filtered on the selected IDO.
•
On the Users form, click either of the buttons labeled Row Authorizations.
The button near the User Modules button opens the Row Authorizations form filtered for the selected
user. The button near the bottom, next to the Group Authorizations button (Groups tab) opens the Row
Authorizations form filtered on the group select in the grid.
•
On the Groups form, click either of the buttons labeled Row Authorizations.
The button near the Group Authorizations button opens the Row Authorizations form filtered for the
selected group. The button near the bottom, next to the User Authorizations button, opens the Row
Authorizations form filtered on the user select in the grid.
•
Use the Mongoose Explorer.
Notes regarding IDO filters
•
When logged in as a Super User, you always see unfiltered data.
•
When you display an IDO collection on a form, you can tell whether IDO filters have been applied by
opening the About This Form dialog box. Any applied filters are listed in the dialog box.
•
The IDO filter is an additional clause that is added to the pseudo-SQL WHERE clause which is transformed
by the runtime into the SQL used to load the collection. The filters for an IDO are loaded with the rest of
the metadata, so they are cached with the metadata objects. When the collection is loaded, the SQL
command builder uses the metadata to add the IDO-level filter clauses to the WHERE clause, unless the
load bypasses the filters (is loaded by a super user).
•
The complete IDO filter specification is built by using AND clauses with any or all of these filters:
•
The general IDO filter, where both User and Group are blank
•
The user's IDO filter, where User is set to the current login
•
The IDO filters for the user's group, where Group is set to any of the current user's group memberships.
(The group filters use Boolean OR statements, because the user should be able to see the rows visible
to any of the groups that user belongs to.)
•
Placing many individual user filters on an IDO requires a lot of setup and maintenance time for the system
administrator. Where possible, include multiple users in a group or cross-reference table, and then apply
the filter to the group or the table join.
•
Be aware that, when you use filters on IDOs that have multiple levels of IDO inheritance, or when you
have users who are members of many groups that each have filters, the filter clauses can accumulate to
the point where performance might suffer.
•
Look at the pseudo-SQL query that is produced. If any of the columns in the query are not indexed, it can
affect processing time. Compare the pseudo-SQL query to the original query that existed before you set
up Row Authorizations, to determine the outcome of the additional filtering. You can view the pseudo-SQL
query using the Log Monitor utility or the IDO Runtime Development Server.
Infor Mongoose Administration Guide | 31
 
User authorizations

Example: IDO filters
This example applies to the Row Authorizations form.
Simple IDO filter example: Username Property = Current User
Suppose you want to allow all users to see their own user information on the Users form (description, email
address, group memberships, etc.) but not any other user's information. To do this, you could set the fields
like this:
Notes
Setting
Field
The filter applies only to the UserNames IDO.
UserNames
IDO
Leave blank, so the filter applies to all users.
 
User
Leave blank, so the filter applies to users in all groups.
 
Group
the User ID field on the Users form is bound to the User-
Names.Username property. (The Usernames.Userid property
is only used internally.)
Username
Property
 
=
[Operator]
 
Current User
This Value
When you click Add This Clause, the pseudo-SQL query that displays looks like this:
(Username = dbo.UserNameSp())
After you activate and save the filter, any user who logs in (and who has the proper authorizations for the
form) can open the Users form but will see only the record that matches their user ID.
Example: Multiple IDO filters
Note:  This example was created specifically for SyteLine users, but customers using other Mongoose-based
applications might benefit from the concepts manifested here.
Suppose each of your salespeople has an company login. They are allowed to see their own sales opportunities,
but you also want them to see any opportunities that do not currently have a salesperson assigned, so that
they can assign the opportunity to themselves.
Instead of creating a new mapping table, we can take advantage of the existing mapping in the SyteLine
slsman_mst table, which has both a username column and a slsman column.
The filter on SLOpportunities requires two clauses.
Infor Mongoose Administration Guide | 32
 
User authorizations

First Clause
Notes
Setting
Field
The filter applies only to the SLOpportunities IDO.
(You will be ORing another filter clause after this
one.)
SLOpportunities
IDO
Leave blank so the filter applies to all users. You will
use the table join to determine who the filter applies
to.
 
User
Leave blank so the filter applies to users in all
groups.
 
Group
The Salesperson field on the Opportunities form is
bound to the SLOpportunities.slsman property.
slsman
Property
 
=
[Operator]
Use this setting to specify a table join.
Current User Joined To
This Value
This is the existing slsma_mstn table that includes
the username (login ID) and slsman (salesperson)
mapping.
slsman_mst
This Table
This drop-down list will populate after you specify
the table.
username
Where This Column is
the Username
This is the value that is compared to the slsman
property on the SLOpportunities IDO.
slsman
And This Column has
the Value
When you click Add This Clause, the pseuo-SQL query that displays looks like this:
Slsman = ANY (SELECT [slsman] FROM [slsman_mst] WHERE [slsman_mst].[username] = dbo.UserNameSp()))
 
Second Clause
Notes
Setting
Field
The filter applies only to the SLOpportunities IDO.
(You will be "OR"ing this filter clause to the previous
one.)
SLOpportunities
IDO
Leave blank so the filter applies to all users.
 
User
Leave blank so the filter applies to users in all
groups.
 
Group
The Salesperson field on theOpportunities form is
bound to the SLOpportunities.slsman property.
slsman
Property
 
=
[Operator]
Infor Mongoose Administration Guide | 33
 
User authorizations

Notes
Setting
Field
Compare the property to the literal value in the ad-
joining field.
Literal
This Value
Leave this field blank, or specify null to indicate a
null value. The null will match any opportunities
where a salesperson is not defined.
 
[Literal field value]
Select this box to indicate an OR
Selected
OR Instead Of AND With
Previous Clause
When you click Add This Clause, the pseudo-SQL query that displays looks like this:
Slsman = ANY (SELECT [slsman] FROM [slsman_mst] WHERE [slsman_mst].[username] = dbo.UserNameSp()))
 OR (Slsman = null)
 
After you activate and save the filter, when a salesperson logs into the application and tries to access the
Opportunities IDO (either through a form or through a web-based call to the IDO), the filter looks up the login
ID in the slsman_mst table and limits the information they can view to records with that customer number.
However, the salesperson can also see any Opportunities records where no salesperson is assigned, because
of the OR and the second clause above.
Example: Table join IDO filter
Note:  This example was created specifically for SyteLine users, but customers using other Mongoose-based
applications might benefit from the concepts manifested here.
This example applies to the Row Authorizations form.
Suppose you have created some application logins for your customers, and you want to restrict the customers
so they can see only their orders and no other customers' orders.
First, you need to create a custom SQL table that connects the application's user logins to customer numbers.
Name the table CustLogin, and include two columns: custuser and custnum. Populate the table with your
customer user logins and their associated customer numbers.
Notes
Setting
Field
The filter applies only to the SLCos IDO. (You would also
create separate filters for the SLCoitems IDO and any other
IDOs needed to view the customer information you want
to include.)
SLCos
IDO
Leave blank so the filter applies to all users. You will use
the table join to determine who the filter applies to.
 
User
Leave blank so the filter applies to users in all groups.
 
Group
Infor Mongoose Administration Guide | 34
 
User authorizations

Notes
Setting
Field
The Customer field on the Customers form is bound to
the SLCos.custnum property.
custnum
Property
 
=
[Operator]
Use this setting to specify a table join.
Current User Joined
To
This Value
This is your custom table with the mappings between the
user ID and the customer number. The table must be in
the application database.
CustLogin
This Table
This drop-down list will populate after you specify the ta-
ble.
custuser
Where This Column
is the Username
This is the value that is compared to the SLCos.custnum
property.
custnum
And This Column has
the Value
When you click Add This Clause, the pseudo-SQL query that displays looks like this:
(CustNum = ANY (SELECT [cust_num] FROM [CustLogin] WHERE [CustLogin].[username] = dbo.UserName
Sp()))
After you activate and save the filter, when a customer user logs into the application and tries to access the
SLCos IDO (either through a form or through a web-based call to the IDO), the filter will look up their user ID
in the CustLogin table and, if it finds a matching custnum, it will limit the information they can view to records
with that customer number.
Example: Group IDO filter
Note:  This example was created specifically for SyteLine users, but customers using other Mongoose-based
applications might benefit from the concepts manifested here.
Suppose your customer BigCycle (customer number 89765) has 20 employees who are allowed to log into
your application through a web portal to check the status of their orders. Instead of setting up a separate IDO
filter for each login, you can set up a group called BigCycleLogins and set up all 20 users as members of that
group. Then you can create a filter for the group. To do this, you would set the fields like this:
Notes
Setting
Field
The filter applies only to the SLCos IDO. (In order for cus-
tomers to view details of the order you would also need
to set up a similar filter on the SLCoitems IDO.)
SLCOs
IDO
Leave blank, so the filter applies to all users.
 
User
The filter applies to all users in this group.
BigCycleLogins
Group
Infor Mongoose Administration Guide | 35
 
User authorizations

Notes
Setting
Field
The property whose value will be compared is
SLCos.CustNum.
CustNum
Property
 
=
[Operator]
 
Literal
This Value
This literal value is compared to the value of the CustNum
property.
89765
Literal edit field
When you click Add This Clause, the pseudo-SQL query that displays looks like this:
(CustNum = "  89765")
 
After you activate and save the filter, any user in the BigCycleLogins group who logs into this application and
requests data from the SLCos IDO will only see records where CustNum = 97765.
If you have 10 different customers who have several users each with application login access, you could create
a group for each customer, and then create 10 "group" IDO filters on the SLCOs IDO (one for each customer
group).
Infor Mongoose Administration Guide | 36
 
User authorizations


