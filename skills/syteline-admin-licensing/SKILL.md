---
name: "syteline-admin-licensing"
description: "License management and file servers - license modules, usage tracking, concurrent sessions, file server setup, document types, physical paths"
version: "1.0"
tags:
  - syteline
  - admin
  - licensing
  - file-servers
  - mongoose
---

# syteline-admin-licensing

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 3:  License management
License modules
Infor Mongoose includes license modules that allow users to access specific forms, IDOs, and other resources
in the application. For example, there are license modules that control access to administrative forms,
DataView forms, user-created forms, App Builder and App Hub forms, and so on.
Prior to Mongoose Release 2019.04, users needed to be granted access to various forms, IDOs, and other
configuration resources by having user license modules assigned by means of the User Modules form, which
is accessed from the Users form. This licensing model can still be used.
Beginning with Mongoose Release 2019.04, however, there is a new "usage-tracking license" model. When
using this type of license, validation and checking of user license modules is bypassed. User access to forms
and IDOs is controlled by group authorizations instead.
License document
If your product installation is on-premises, you must have a license document to apply your licenses to the
application. Infor provides this document in the form of a *.txt file when you purchase an Infor product. The
file contains, in an encrypted format, all the information that is required to apply your licenses to each
application database. You must determine which license modules you require, and how many licenses you
require for each module, before the license document can be provided.
Note:  For multi-tenant cloud customers, licensing is handled by the Cloud Enablement Team.
Tokens
Each module that you purchase has a certain number of tokens available for users to consume, based on the
number of user licenses that you purchased for that module. Generally, each user who logs in to Mongoose
consumes one or more tokens. License tokens are consumed when a user logs into the system. The tokens
are released when the user logs out.
Here, too, when using a usage-tracking license, tokens are not counted or tracked. In essence, customers
have an unlimited number of tokens, and can have an unlimited number of users. Instead, of forms, IDOs,
and other resources is tracked as users go about their work in the application.
Session types
Mongoose allows named and concurrent licensing based on session type. Among other benefits, this feature
allows named users to be logged in multiple times using different types of sessions, but only consume license
modules for the appropriate type of session.
Infor Mongoose Administration Guide | 37
 
License management

When using a usage-tracking license, sessions are not limited or counted. Customers (and individual users)
can have an unlimited number of sessions.
These are the exceptions to this rule:
•
The 'sa' user login is allowed only one session at a time. Also, 'sa' user consumption of forms and IDOs
is not tracked.
•
The '$service' login is used by the system and is not tracked.
Cloud environment
In a cloud environment, be aware of these licensing differences from an on-premises environment:
•
The Infor Cloud team applies the licenses to your database. Unless you have a usage-tracking license
agreement, you must assign the licenses to specific users.
•
All licenses are named-user licenses.
•
Intranet licensing is not needed.
•
Some products described in the list of license modules are not available.
License administration in the application
License administration is performed using these Mongoose forms:
•
License Management: This form indicates the dates when the license begins and ends, which modules
are licensed, and how many licensed users you have. This form is where you apply the license to stay
current. You can see your multi-session users in the Multi-Session Users tab.
•
Licensed Modules: This form lists the modules for which your company is licensed in the current site.
This is helpful for users who cannot access the License Management form.
•
User Modules: Use this form to associate user names with license modules.
Note:  If you have a usage-tracking license, this form is not needed or checked.
•
License Modules Per User Connection: This form lists the license module names associated with each
current user connection. If intranet licensing is enabled, then this form also shows the site from which
the users are connected. In non-intranet licensed environments, only connections from the current site
are shown. Connected users consume user licenses (tokens) based on the license module to which the
users have been assigned, and the session type under which they are logging in.
See Licensing by session type on page 43.
•
License Module Concurrent User Counts: This form lists all the license module names that are currently
associated with a user connection, as well as the total number of users who are consuming a license on
each module. Use this form to monitor whether your purchased license count is appropriate for the
number of concurrent logins at peak times.
•
Session Management: Use this form to view current user sessions, and to delete user sessions when
necessary. This form also includes a column to indicate the session type.
•
Sites or Sites/Entities: If this form indicates that intranet licensing is being used, then licenses can only
be assigned at the master site for the intranet. Intranet licensing is only used in an on-premises
environment.
Infor Mongoose Administration Guide | 38
 
License management

•
Modules for Form: On this form, you can filter by form name to identify license modules that are required
to be able to run that form. You can also filter by license module to identify forms that are associated
with that module.
For more information about using any of these forms, see the online help for the form in question.
About the usage-tracking license
The "usage-tracking" only license module is fundamentally different from other Mongoose license modules.
Instead of tracking user and session counts, this module causes your application to track usage of forms,
IDOs, and other resources.
The license module that does this is named MGUsageTrackingOnly. When this module is included as part of
your license, the system:
•
Bypasses session limit checks. This effectively allows you an unlimited number of active sessions.
•
Bypasses checks on user module assignments. This effectively allows you an unlimited number of user
logins.
•
Preserves and recognizes security settings (that is, group and individual user authorizations).
•
Tracks usage of forms, IDOs, and other system resources for each user and session.
The collected data is stored in special tables that can be audited by means of SQL queries.
Among other things, this means that:
•
System administrators do not need to make User Modules assignments.
•
Under standard licensing, all modules assigned to the user are immediately consumed, regardless of
which forms the user opens. If you apply the usage-tracking license, the License Modules Per User
Connection form and the License Module Concurrent User Counts form only show the module after a
user opens at least one form that belongs to that module.
•
Under usage-tracking, even though the actual number of sessions and license module usage are not
explicitly enforced, the usage data logged under this license module is tracked and audited by Infor to
ensure customer compliance with purchased licensing limits.
About intranet licensing
Note:  This topic does not apply to customers with a usage-tracking license.
You can choose to set up an intranet with a master site that controls licensing for other sites on the intranet.
This consolidates license administration and validation at the master site. One license document applied on
the master site can service all of the sites enabled for intranet licensing on that intranet. All named user and
concurrent login license checks at each of the participating sites are redirected to the master site for validation.
You can choose which sites on the intranet will use intranet licensing, through a check box on the Sites form.
Infor Mongoose Administration Guide | 39
 
License management

You can use the Intranet Shared User Tables feature, which requires Intranet Licensing, to create and
administer all users for the intranet from the master site. If you select Intranet Licensing but you do not use
the Intranet Shared User Tables feature, users must be created at each participating site, and all users from
all participating sites must be created at the master site as well.
A named user is allowed one login at each participating site on the intranet. If a named user tries to log into
the same site that he is already logged into, the system requires the user to cancel the existing session first.
A user with a concurrent license can log in as many times on as many sites as needed, up to the license limit.
Each of those concurrent logins consumes a license token.
Intranet licensing requires reliable connectivity between all participating sites. If connectivity between an
intranet licensing site and its master site goes down, then all subsequent license checks will fail until
connectivity is restored, so users will not be able to log in at non-master sites.
After intranet licensing has been turned on for a site, you should not turn it off without good reason. If you
do, you must apply license documents to each site where you turn it off. In addition, custom objects at that
site may then have incomplete licensing, if the site is not either the master site or the site at which the objects
were originally created, because the appropriate licensing records for the object will not exist in the application
database at that site.
License information is not replicated and no tables need to be shared specifically for intranet licensing.
However, the sites on the intranet must replicate the Site Admin category, in order to recognize the master
site. Information about how many sites are logged on using a particular license is stored in the
SiteConnectionInformation table, which is kept up-to-date only at the master site.
The Session Management form reflects only the sessions for the local site. If the current site is enabled for
intranet licensing, the License Modules per User Connection form shows license module use across all
participating sites.
The License Management and User Modules forms are disabled at participating sites other than the master
site.
Associating users with modules
Note:  This topic is optional for customers with a usage-tracking license.
The system administrator associates each user with one or more license modules. When an administrator
associates a user with a module, the administrator is granting that user the license rights to use resources
contained in that module. For online users, the licensed resources are the forms they run. For web service
and other automated sessions, the licensed resources are IDOs.
License rights are separate from security rights. Security rights are controlled by assigning users to groups
or by allowing user authorizations for certain types of access, such as read, delete, execute, and so on, to
specific objects.
Depending on whether you selected named or concurrent licensing, Mongoose consumes the license tokens
in different ways.
Infor Mongoose Administration Guide | 40
 
License management

About named user and concurrent login licensing
A named user license links one user login to one license through the User Modules form. For example, if you
plan to have three users, such as bobw, jeanp, and miker, that run Mongoose, you must request three named
licenses.
Unless you have a usage-tracking license, named license users are only allowed one active login to a Mongoose
site, and that login consumes a license. If you try to log in a subsequent time as a named user who holds an
active session, Mongoose requires the active session to be released. However, there are two exceptions to
this:
•
If you have a usage-tracking license, you can have multiple users logged in, limited only by the number
of licenses you purchased on your agreement.
•
If you have a special multi-session user provided as part of your license, that user can have multiple logins
and sessions.
With named user licensing, when you associate a user with a license module, Mongoose verifies that there
are remaining tokens for the module not yet associated with a user. A license module token is consumed at
the time the user is assigned to that module. If a license module assignment is later removed from a user,
that license module token is returned to the pool of available tokens. When the user logs in and attempts to
access a form or IDO, Mongoose verifies that the user is associated with at least one license module containing
the resource.
In a multiple-sites-per-database environment, each named user can log in once per shared site, which counts
as one license token against each module to which that user is assigned.
Again, if you have a usage-tracking license, this is rule is bypassed.
With concurrent login licensing, licenses are assigned to users, but the license check does not occur until
users log in. If the number of users logged in to a site with a particular license module is equal to the license
number limit, and another user tries to log in, that user is not allowed to log in to the site. Using the same
example, if you have two concurrent Mongoose licenses, you can assign that license to three users (bobw,
jeanp, and miker). However, only two of them can use the Mongoose license token at a time.
Note:  As with named licenses, if you have a usage-tracking license, you can have an unlimited number of
multiple concurrent users.
The reverse is also true. For example, miker is assigned concurrent licenses for Mongoose and
ServiceManagement, so he can work in both functional areas while he is logged in.
Under concurrent licenses, a user can log into a certain site multiple times, as long as the total number of
license tokens currently consumed in Mongoose for any module that applies to that user is less than the
licensing limit for that module in that site. A module applies to a user if that module is assigned to that user
and has the same session type as the user's connection, or has an Undefined session type.
With concurrent login licensing, you can associate as many users as you want with a module in a site, regardless
of how many tokens you have purchased for the module. When a user logs in, Mongoose verifies that there
are remaining tokens for each of the modules or session types that apply to that user, and then consumes a
token for each of those modules for the duration of that user's session. License module tokens consumed
during a user’s session are returned to the available pool when the user logs off.
Again, this rule is bypassed if you have a usage-tracking license. In this case, no tokens are consumed or
counted.
Infor Mongoose Administration Guide | 41
 
License management

Licensing in a multi-site environment
Note:  This topic does not apply to customers with a usage-tracking license.
In a multi-site environment with multiple sites in one database, one license document applies to all sites,
and named versus concurrent limits are enforced across all sites.
In an intranet licensed environment or a multiple sites per database environment, if a named license user is
logged into one site, the user can log into additional sites without having to relinquish the existing session.
However, the user cannot have multiple login instances in the same site; the user must relinquish the current
session to start another one.
In a multi-site environment with one site per database, if intranet licensing is not used, each site has its own
license document and keeps track of its own tokens. Each site has no knowledge of any logins on any other
site. If a user is defined in multiple sites and is logged into more than one site, whether concurrent or named,
that user uses a license token for each of those logins in each site.
For one site, all the licenses must be specified as either named or concurrent. You cannot mix types.
About system users with special licensing
These users are special cases:
•
The 'sa' user: The 'sa' user does not require licensing. By default, this user has the ability to open any
form and run any process in the site. The 'sa' user should be reserved for system administration tasks
and should not be used to run multi-site processes.
Only one instance of the 'sa' user can be logged in at any time, even under a concurrent license or a
usage-tracking license. With a usage-tracking license, form and IDO consumption is not tracked for the
'sa' user.
Do not assign any license modules to this user.
•
Multi-session or concurrent user: This capability is designed for web service automation scenarios, where
an application that is accessing the IDO layer can have multiple threads that process simultaneously,
with each thread sharing the same configured multi-session user.
Under a named user license, each standard user is only allowed one session per site. The multi-session
user gives an administrator one user that can have multiple concurrent sessions. Under a concurrent
license, all users except 'sa' can have multiple concurrent sessions. Therefore the multi-session user
provides no real significance in a concurrent license environment.
When the multi-session user is assigned to a license module, the tokens consumed are the number of
concurrent sessions defined for that user. In other words, if the multi-session user is defined by the license
to allow seven concurrent sessions, assigning that user to a module consumes seven license tokens for
that module. Multi-session users and their concurrent session limits are defined in the license document
generated by the vendor. You cannot create or modify a user to be a multi-session user.
Multi-session users are shown in the Multi-Session Users tab on the License Management form.
•
Infor ION: ION uses a direct connection to the application when retrieving BODs, so no license token is
required.
Infor Mongoose Administration Guide | 42
 
License management

•
Infor Framework services: Framework services such as the Replicator use '$service' sessions to connect
to the application. The '$service' session does not consume a license.
Licensing by session type
Mongoose allows named and concurrent licensing based on session type. Among other benefits, this feature
allows named users to be logged in multiple times using different types of sessions, but only consume license
modules for the appropriate type of session.
One of these session types is associated with each license module:
•
Full Client: This session type is used for a fully trusted client where form licensing grants permission to
the form and all of the IDOs used by that form. This include web clients where the web rendering is not
in FormOnly mode.
For example, when users log in from a web client on a computer, it is a "full client" session, and they can
access both forms and IDOs.
•
Web Form Only: This session type is for web client rendering in FormOnly mode.
When a user logs in from a mobile device, it is generally a "web form-only" session, that is, through a URL
that includes the "formonly" URL parameter. The "web form-only" session type also applies to forms
displayed through our Microsoft Office Integration.
•
IDO: This session type is used for programmatic (automated) access to the application's IDOs and their
methods.
For example, when users log in through the Mongoose Microsoft add-in or through a Mongoose portal,
it is an "IDO" session, because they access Mongoose IDOs rather than forms.
•
IDO Custom User: This session type is used for Mongoose REST web service calls only. This session allows
multiple (concurrent) web service calls to consume one license token for a given user and user module.
An IDO Custom User session type matches any modules defined as IDO session type.
Note:  To leverage this session type, this header attribute must be specified as part of each REST web
service call:
X-infor-MongooseSessionType: CustomUser
Any REST web service calls that do not contain this header attribute are executed with a standard IDO
session type.
•
Undefined: No session type is defined. You should see this only if an orphaned connection record still
exists from an earlier version of the application.
•
Additionally, there is a $service session type that is not associated with any module. The $service session
type is used by framework utilities such as TaskMan. Under a service session, access to licensed form
and IDO resources is granted without consuming any license tokens.
Infor Mongoose Administration Guide | 43
 
License management

Verifying the license at user login
When license checks are made during user logins, only license modules whose session type specification
matches the user’s current session type are considered.
If the module is associated with a session type, then a license token is only consumed for that module if the
module's session type matches the user’s connection session type.
Verifying the license at runtime
After the user has successfully logged in, runtime license checks are made to determine if a certain object,
such as form or IDO, is available to that user, based on the user’s licensing and session type. Only modules
matching the user’s current session type are considered.
Otherwise, the session type is considered.
Licensing custom forms and IDOs
If customers add custom forms, copy or extend existing forms, create new IDOs, or extend existing IDOs, the
forms and IDOs are licensed using this information in the license document and the ModuleMembers table:
•
New customer-created IDOs that do not extend other IDOs are automatically assigned to the
MGUserCreatedIDOs license module.
•
New customer-created IDOs that extend another IDO are automatically assigned to the license modules
to which the base IDO belongs.
•
New customer-created forms that are not copied from another form are automatically assigned to the
MGUserCreatedForms license module.
•
Copied forms are automatically assigned to the license modules to which the source/original form
belonged.
•
A new form that extends an existing form is automatically assigned to the license modules to which the
existing form belonged.
Be aware that, if you have multiple application databases pointing to the same forms database, you must
copy the form licensing information to each application database, using FormSync.
Licensing for automated sessions
In automated sessions, multiple threads of the calling application can overlap. For example, a web application
can call the Mongoose IDOWebService to query data to present. When making a call to the IDO Web Service,
the web application supplies a specific user ID, password, and configuration. The web application can have
multiple threads executing simultaneously, so it requires multiple concurrent Mongoose sessions.
Infor Mongoose Administration Guide | 44
 
License management

Multi-session users are designed for use with automation sessions. They are defined in your license document
and are created when the license document is applied.
However, any user can make overlapping web service requests and still only consume one IDO license token,
if the IDO Custom User session type is used. This session type is optionally available to sessions that are
created during Mongoose REST, WebService, and IntegrationService calls.
Automation sessions can be established using the technologies and tools listed in this topic. Each of these
options exposes the same underlying IDO Request interface, as described in the document IDO Integration
with External Applications section of the help. The licensing implications of these options vary, as described
in this list:
•
SOAP WebService: These calls are synchronous, consuming a license token during the processing.
•
COM: These calls are synchronous, consuming a license token during the processing.
•
XML: Mongoose application and web servers provide both synchronous and asynchronous URLs. The
calling process must format an IDORequest XML document, and then decide whether to use the
synchronous route or asynchronous route, according to these guidelines:
•
For the synchronous route, an automation session is established for the user specified in the XML,
consuming a license that is assigned to that user. (Often this user is assigned a MGCoreAutomation
license, because MGCoreAutomation is the only license module that contains all the MongooseIDOs
plus customer-created ones.) The synchronous route allows the calling application to receive the
results of the request.
•
For the asynchronous route, although security is checked for the user specified in the XML, no licensing
consumption occurs. The processing is performed by the framework service ReplQListener. XMLs
processed this way require a valid user, but that user does not need to take licensing into account,
only security.
List of Mongoose license modules
This table lists all license modules and access capabilities that exist in the interface.
The Modules For Form form lists all forms included in each license module.
Access capabilities
License module
The AppBuilder and AppHub forms.
Users who have need access to the AppBuilder and AppHub forms, either
in Mongoose or in the cloud, must have this license assigned. They must
also have the MGUserCreatedForms assigned, so they can access forms
created in AppBuilder projects.
MGAppBuilder
Infor Mongoose Administration Guide | 45
 
License management

Access capabilities
License module
IDO access.
This license module allows direct access to IDOs by external applications.
Mongoose provides the IDORequest interface to a variety of possible
technologies, including SOAP web services, .NET class libraries, XML over
HTTP, and COM. Regardless of the technology used to access the Mongoose
application, if the external application needs to use core Mongoose IDOs,
the user account it employs must have this license module assigned.
Some integrations require this license module to log in to the application
and collect data. Infor allows you to request automation licenses equal to
the number of MGCoreTranslicenses your company buys.
If you plan to have custom applications connecting to the Mongoose appli-
cation through the IDO layer, you might need to buy additional MGCoreAu-
tomation licenses to have enough licenses for each application connection.
This license module is always assigned to the IDO session type.
MGCoreAutomation
All core forms in the framework.
The core forms constitute the bulk of the operational forms in Mongoose.
This license module is typically assigned to all system users. Access to
forms is further controlled by user and group permissions.
MGCoreTrans
DataViews, DataSearch, and Critical Numbers (KPI) forms.
This license module allows access to and use of those forms that make use
of DataViews, DataSearch, and Critical Numbers. It also allows developer
access to that functionality for the design and development of forms that
require it.
MGDataViews
IDO editing forms and SQL table or column editing forms.
This license module allows access to all forms commonly used in the design
and development of new forms in Mongoose. It is also required for devel-
opers who customize or modify forms at the Site or Vendor level.
This module also allows the use of application forms that start with the
prefix "IDO" or "SQL" that are used for development. This license module
is always assigned to the Full Client session type.
MGDeveloper
Mongoose Platform as a Service (PaaS) and user-defined web control de-
velopment.
This license module is not directly assigned to users. It is used to indicate
that the environment is an Infor cloud PaaS multi-tenant cloud environ-
ment. With this module, users can create or modify user-defined web
controls. This module is only available to PaaS tenants.
MGPAAS
Infor Mongoose Administration Guide | 46
 
License management

Access capabilities
License module
All users, all sessions.
This license module is not directly assignable to users. When this module
is part of the license, it allows for an unlimited number of users and an
unlimited number of sessions. The system bypasses both individual user
module assignments and session limit checks.
Security (group and user authorization) settings still apply.
Instead of user and session counts, this module tracks form and IDO usage
from all users. The usage data is collected and can then be audited.
MGUsageTrackingOnly
User-created forms.
This license module is not directly assignable to users. It is used for (at least
temporary) licensing of forms that Mongoose form or application developers
create. This is required, because without this module, any forms created
by Mongoose developers would not be available for use by others.
Mongoose automatically assigns new forms and new versions of forms to
this module. To make them available to non-developer users, you must
authorize them at the User or Group level.
Note:  Mongoose does not add to this module forms that are copied from
other forms or that extend other forms. Instead, Mongoose adds those
forms to whatever module the original or base form belongs to.
This license module is assigned to the Undefined session type, so it is
suitable for Web Form Only and Full Client sessions.
MGUserCreatedForms
User-created IDOs.
This license module is not directly assignable to users. It is used for (at least
temporary) licensing of IDOs that Mongoose form or application developers
create. This is required, because without this module, any IDOs created by
Mongoose developers would not be available for use by others.
Mongoose automatically assigns new IDOs and IDO extensions to this
module. To make them available to other external applications, you must
authorize them to the user account those applications use to create a
session.
This license module is always assigned to the IDO session type.
MGUserCreatedIDOs
Assigning users to license modules
When you create a user in Infor OS Portal® and migrate that user to a Mongoose-based application, that user
automatically gets assigned a default license module.
In other cases, license modules must be assigned to users manually.
Note:  If you have a usage-tracking license, you need not perform this procedure.
Infor Mongoose Administration Guide | 47
 
License management

1 
On the Users form, select the user.
2 
Click User Modules.
3 
On the User Modules form, select a license module to assign to the user.
To assign multiple license modules to the user, add rows in the grid.
4 
Save your changes.
Identifying the forms included in a license module
Use the Modules for Form form to display a list of the forms included in a license module.
1 
Open the Modules for Form form.
2 
In the Module Name column, select the license module for which you want to know what forms are
included.
3 
Press F4.
The form displays a list of all the forms associated with the selected license module.
Note:  Some license modules have no associated forms.
Identifying the license module for a form
Use the Modules for Form form to display a list of the license modules to which a form belongs.
1 
Open the Modules For Form form.
2 
In the Form Name column, select the form for which you want to know what license modules it belongs
to.
3 
Press F4.
The form displays a list of the license modules associated with the selected form.
Setting the default license module
When you create a user in Infor OS Portal® and migrate that user to a Mongoose-based application, that user
automatically gets assigned a default license module.
Additional license modules must be assigned to users manually.
Note:  In a usage-tracking environment, application users are not assigned to specific license modules, so
this task is not needed.
Infor Mongoose Administration Guide | 48
 
License management

Use this procedure to set the default process license module for any Mongoose-based application. Developers
can use an event handler to trigger the associated Application Event System event or trigger the event from
the database using a stored procedure. Both of these options use this process default.
1 
Open the Process Defaults form.
2 
Provide this information:
Admin License Module Name
Specify the license module for an administrative user. The default license module is MGCoreTrans.
3 
Click Save.
Registering a Mongoose-based application
If you are an Independent Software Vendor (ISV) building a Mongoose application for resale, you must register
your application with Infor, so that we can generate license keys for that application.
If you have purchased Mongoose to build applications for your own use, you are not required to register the
applications.
Submit the name of your application and the names of your license modules. For each module, specify its
session type and form and IDO names.
For example, if your application has a customer service module designed for users on normal computers,
and a field-service module with a set of forms designed for mobile devices in Form-Only mode, you can specify
these new license modules:
Contains these forms and IDOs
Session type
Module
CSCustomers, CSOrders, CSParameters
Full client
CustService
FSCustomers, FSWorkOrders, FSParameters
Web form only
FieldService
If your application has a requirement to restrict the user logins based on the combination of license module
and the type of site, you can use the SiteTypeModuleMap table to set up acceptable combinations of site type
and license module. A user can only access a site if the site’s “site type” can be used with the license module
to which the user is assigned.
You must also provide an addendum to this guide that describes your application’s license modules, so that
your end users can request the appropriate modules.
Requesting a license
Contact Infor customer service to request your license keys. You must specify your SQL Server name and
Application Database name. To find the SQL server name, open the SQL Server Management Studio and enter
Select @@Servername.
Infor Mongoose Administration Guide | 49
 
License management

To find the application database name, open SQL server Enterprise Manager. Expand the Databases folder
to see the names of the databases that include the text _App. Those are your Mongoose Application Databases.
You can then determine the name of the Application Database within that server that is to be licensed.
Applying a license
To apply a license, you must be logged in as a user in the System Administration group.
1 
Open the License Management form.
Note:  If you are using intranet licensing, this form is blocked for users on all sites except the master site.
2 
Select the Apply License tab.
3 
In the License Document field, paste your license document.
You can also click the Import License button to import the document from a text file on your drive.
Note:  The document is encrypted and every character counts, so if you copy and paste, be careful to
copy the entire document.
4 
Click Apply License.
Note:  Any previous multi-session users that you had are deleted and replaced with the ones defined in
the new license document.
Suppose you are applying a new license to replace an old one for the MGCoreTrans module. The old license
is for 50 users, and the new one is for 40. When you click the Apply License button, you get an error message.
If the new license is for fewer users than the old license, you must go to the Users Module form and delete
users associated with the MGCoreTrans module until you have the same amount or fewer than the new license
allows. If your new license is for the same number of users or more, then you can apply the license and you
are not required to do anything else.
Discarding the IDO Runtime Service cache
Note:  This topic is intended for use only in an on-premises environment.
If there are active client sessions when you applied the license, you must discard the IDO Runtime Service
cache for all configurations that reference the application database to which the new license is applied. If
there are no active sessions, you can skip this process.
1 
On the Application Server, open the Configuration Manager.
2 
Select the Utilities tab.
3 
Select the configurations that reference the Application Database to which the new license is being
applied.
Infor Mongoose Administration Guide | 50
 
License management

4 
If you are in a development environment and you use the local IDO Runtime Host utility on your computer,
select the IDO Runtime on Local Machine check box.
5 
Click Discard IDO Cache.
For more information, see the Configuration Manager help topic for the Utilities tab.
Applying a license using a command line
As an alternative to applying the license in the License Management form, you can use the ApplyMongoose
License.exe utility to apply a license document to the application database of a specified configuration using
a command line interface.
Note:  This topic and this utility are applicable only for on-premises environments.
Utility location
This utility file must reside in the executable folder of a Mongoose-based application's admin client or
Application Server, and the file must be run from that location. The IDO Runtime Service or equivalent
IDORuntimeHost must also be running on that machine. If not, you receive an error message when you try to
apply the license:
Error processing an IDO request (Protocol=Ipc): Communication error executing IDO request: Failed
 to connect to an IPC Port: The system cannot find the file specified.
Running the command
To run ApplyMongooseLicense.exe as a command line utility, use this syntax:
ApplyMongooseLicense –config:config_name {-license:license_document | -licensefile:filename}
To apply a new license, use only one of these options:
•
-license: Specifies an actual license document
•
-licensefile: Specifies a path/file containing a license document
To reapply the current license, specify the command without either of those options.
If any arguments are provided that do not match the required parameters, then a “Usage” message is displayed
to show the required format:
Usage: ApplyMongooseLicense <parameters> where <parameters> represents:
 -help: Displays this usage text. If specified, only parm required.
 -config: Config name (required)
 -logfile: Path/filename of logfile (optional)
One of the following to apply new license or neither if reapplying current
 -license: A license document
 -licensefile: Path/filename containing a license document
If the license is successfully applied, the command line utility returns an exit code of 0. Otherwise, it returns
-1. If the license application is successful, a success message is displayed on the console.
Infor Mongoose Administration Guide | 51
 
License management

Troubleshooting
These conditions can cause an error when you run the utility:
•
Insufficient or invalid parameters are specified for the command line.
•
The specified configuration does not exist.
In this case, this message is displayed when the utility tries to connect to the configuration:
[Machine_Name] NullReferenceException exception processing 'OpenServiceSession' request: Object
 reference not set to an instance of an object.
•
The license document is invalid or does not match the server and database name to which it is being
applied.
•
The IDO Runtime Service, or equivalent IDORuntimeHost.exe, is not currently running on this computer.
•
The ApplyMongooseLicense.exe utility is not being run from the installed Mongoose executable folder.
This utility is dependent on other assembly files that exist in this folder.
•
If neither -license or -licensefile is specified and no license document is currently installed, this message
displays:
Reapply License Failed: Reason = License is missing or invalid
Troubleshooting license and token problems
Troubleshooting: Replacing a license with fewer users
Symptoms
You received an error that your new license is for fewer users than your old license. For example, your old
transactional license was for 50 users, and your new license is for 40 users. In this case, when you click Apply
License in the License Management form, an error message displays.
This error does not occur in a usage-tracking license environment.
Possible solution
Open the User Modules form and delete user associations with the Transactional module until you have the
same or fewer users than the new license allows.
Infor Mongoose Administration Guide | 52
 
License management

Troubleshooting: Recovering locked tokens
Symptoms
If Mongoose terminates unexpectedly, it is possible that license tokens that were in use at the time are still
locked, thus preventing users from logging in.
Under usage-tracking licensing, any orphaned sessions (locked license tokens) do not prevent users from
logging in. However, orphaned sessions are shown in queries of usage-tracking data, so they could result in
inflated module usage counts and reporting of non-compliance if licensed limits are exceeded.
Possible solution
Use the Session Management form to free the locked tokens.
Infor Mongoose Administration Guide | 53
 
License management

Chapter 4: File servers
Setting up a file server with logical folders
When file servers and logical folders are set up in the application, you can use them to specify locations for
uploading or downloading files and documents. File servers and logical folder specifications can be used in
either of these cases:
•
Cloud applications, where the file servers are predefined as Amazon Web Service Simple Storage Services
(AWS S3) “buckets” by the Cloud team. Use of SFTP servers or web servers for logical folders is also
allowed in the Cloud.
•
On-premises applications, where a shared UNC path, FTP/SFTP, or AWS S3 services can be used for logical
folders.
Prerequisites:
•
You must be a system administrator for this application, with permission to access the appropriate forms.
•
Decide where your file servers should reside (cloud, FTP/SFTP server, Web Server, or shared on-premises
server), and know the file path and login information, if on-premises.
•
You cannot read or write to a shared file server in a partial trust environment.
•
Decide what logical folders you need to create. Some logical folders are predefined by the application.
Others might depend on your company’s needs, for example, HR documents.
•
Determine which logical folders should reside on which file server, if you have a choice of file servers.
•
Decide which users should have access to which logical folders.
To set up a file server with logical folders, use this procedure:
1 
For an on-premises application, define each file server on the File Servers form.
For a cloud application, the file servers might be predefined by the cloud team. You can define your own
file server in this environment by using the default AWS bucket with a root path, or with the Additional
Properties section (see below). You can also set up a local web server as a file server.
a 
Provide this information:
Active
If the file server is currently active and available for use, select this option.
Server Name
Provide a name that describes the server, for example FTP_EDI, fileserver1, or EDI_bucket.
Server Type
Select one of these options:
•
FTP: Use this option for either cloud or on-premises applications.
Infor Mongoose Administration Guide | 54
 
File servers

When you select this option, the FTP tab is automatically selected.
•
Shared Path: Use this option only for on-premises applications.
•
AWS S3: Use this option only for cloud applications.
When you select this option, the AWS S3 tab is automatically selected.
•
SFTP: Use this option only for either cloud or on-premises applications.
When you select this option, the SFTP tab is automatically selected, and the label of the Domain
field changes to Host Name.
•
Mongoose Web Service: Use this option only for cloud applications.
•
Infor Document Management: Use this option if you are using Infor Document Management
as your file server for either cloud or on-premises operations.
When you select this option, the Infor Document Management tab is automatically selected.
b 
If you select a server type of FTP, provide this information:
Domain
Provide the domain for user logins.
Shared/FTP Path
Specify the root path where files are stored on the server. Use this format: FTP_Server/folder/
User ID
Provide the user ID to access the FTP site.
Password
Provide the user password to access the FTP site.
Remote Server Name
Optionally, specify the DNS name or IP address of the server.
Enable SSL
Select this check box to enable SSL.
Use Passive
Select this check box to enable Passive FTP.
c 
If you select a server type of Shared Path, provide this information:
Domain
Provide the domain for user logins.
Shared/FTP Path
Specify the root path where files are stored on the server. Use this format: \\server\share\
User ID
Provide the user ID to access the shared server.
Password
Provide the user password to access the shared server.
Infor Mongoose Administration Guide | 55
 
File servers

Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this option.
d 
If you select a server type of AWS S3, provide this information:
Bucket Name/Set as Default Bucket
Provide an AWS bucket name or select Set as Default Bucket to set this field to Default.
Root Path
Specify a sub path to use after the bucket path. Doing this, you can create different file servers under
the same bucket name.
For example: Server1 has a root path of /Benefits/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/Benefits/
Server2 has a root path of /CustomerOrders/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/CustomerOrders/
User ID
Provide the user ID to access the AWS bucket.
Password
Provide the user password to access the AWS bucket.
Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this option.
Server Side Encryption Method
Select the encryption method. If the Set As Default Bucket is selected, this option is disabled.
•
None: If no encryption method is to be used, select this option.
•
AES256: To use Advanced Encryption Standard 256 encryption method, select this option.
•
AWSKMS: To use Key Management or KMS encryption method, select this option.
Server Side Encryption Key
Optionally, provide the key to be used for the AWS S3 file encryption. If the Set As Default Bucket
is selected, this option is disabled.
External Bucket Name
Provide the bucket name to be used. If the Set As Default Bucket is selected, this option is disabled.
AWS Access Key
Provide the AWS access key.
AWS Secret
Provide the AWS secret access key.
Infor Mongoose Administration Guide | 56
 
File servers

AWS Session
Provide the AWS session token.
Use Pre-Signed URL To Upload
Optionally, to use the pre-signed URL to upload, select this check box.
Use Pre-Signed URL To Download
Optionally, to use the pre-signed URL to download, select this check box.
e 
If you select a server type of SFTP, provide this information:
Host Name
Provide the host name for user logins (for example, sftp.cilrebo.com:22).
Root Path
Specify a sub path to use after the bucket path. Doing this, you can create different file servers under
the same bucket name.
For example: Server1 has a root path of /Benefits/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/Benefits/
Server2 has a root path of /CustomerOrders/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/CustomerOrders/
User ID
Provide the user ID to access the SFTP site.
Password
Provide the user password to access the SFTP site.
Remote Server Name
Optionally, provide the DNS name or IP address of the server.
Authentication
Select the authentication to use:
•
Basic: Requires only the user ID and password
•
Use Private/Public Key: Requires only the key value
•
Use Both Password and Key: Requires both the password and key value
SSH/OpenSSH Key File Path
This field contains the path to access the key file.
Encrypted SSH/OpenSSH Key
This field contains encrypted key value when you click the Save Key button.
Pass Phrase / Key Password
Provide a password to open or read the key file path or content. This value is encrypted when you
click Save.
Load Key
To load the key content from the file, click this button.
If you select Basic authentication, this button is disabled.
Infor Mongoose Administration Guide | 57
 
File servers

Clear Key
To clear the content or file path, click this button.
If you select Basic authentication, this button is disabled.
Save Key
To encrypt the content or the file path, click this button.
If you select Basic authentication, this button is disabled.
f 
If you select a server type of Mongoose Web Service, provide this information:
Domain
Specify the domain for user logins.
URL
Provide the URL for the Mongoose web service, for example: https://servicename:port/ca
User ID
Provide the user ID needed to access the web server.
Password
Provide the user password to access the web server.
Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this check box.
g 
If you select a server type of Infor Document Management, provide this information:
Domain
Provide the domain for user logins.
URL
Provide the URL for the IDM service, for example: https://servicename:port/ca
User ID
Provide the user ID to access the IDM server.
Password
Provide the user password to access the IDM server.
IDM Document ID
Provide the document ID of the document type that you created in IDM.
Authentication
Select the type of authentication to use:
•
Basic: Uses the domain account
•
OAuth1: Uses the consumer key as user name and secret key as password
Note:  Although other authentication methods are listed, only Basic and OAuth1 are currently
supported.
Infor Mongoose Administration Guide | 58
 
File servers

Map IDM Entity with Logical Folder
To use multiple document types for any single file server, select this option.
Delete All Before Overwrite
To remove all versions of the same document before you overwrite or update any existing document
in IDM that have the same key, select this option.
Use Tenant
To use the Tenant ID with an IDM connection, select this option.
Print Server
To set the file server as the print server for IDM Enterprise Print, select this option.
Validate Printer
To validate the file server for IDM Enterprise Print, click this button.
Use Authentication Service
To attach an authentication service, instead of loading OAuth keys or specifying a consumer key
and shared secret, select this option.
Note:  This field is enabled for OAuth1 authentication only.
Service Name
Select the service name, as specified in Configuration Manager.
Set Single IDM User Name/ID
Provide one IDM user name or ID to be shared across the application to access IDM.
Note: 
•
If you keep this field blank, the application uses the Workstation Domain/ID that is specified
in the Users form.
•
If you select Basic authentication, this field is disabled.
Use Single IDM User
Enable the use of a single IDM user name or ID to be shared across the application to access IDM.
•
For All Users: When selected, the Set Single IDM User Name/ID value is used for all users.
•
As Default: When selected, the Set Single IDM User Name/ID value is used for users who do
not have IDM accounts.
Configure IDM
To open a new form where you can create and maintain IDM document types, click this button.
Load OAuth Keys
To load the OAuth JSON key file that contains the consumer key and secret key, click this button.
If you select Basic authentication, this button is disabled.
Clear OAuth Keys
To clear the previously loaded OAuth JSON key file, click this button.
If you select Basic authentication, this button is disabled.
Infor Mongoose Administration Guide | 59
 
File servers

h 
Optionally, to open the JSON Key Value Pairs form where you can set key-value pairs, click Additional
Properties.
See Additional Properties for File Servers.
i 
To verify the server login information, click Validate.
j 
Click Activate.
The file server is now available for selection on other forms.
2 
On the File Server Logical Folders form, to create logical folders on a file server, provide this information:
Active
To immediately activate a new file server without restarting the IDO Runtime, select this option.
File servers are automatically activated when the first login occurs in the application. The file server is
available for use in other forms when Active is selected.
Logical Folder Name
Provide a name for the logical folder.
Server Name
Select the file server where you want the logical folder to reside.
Folder Template
Provide a substitutable folder path.
These substitutable keywords can be used for any template:
•
USERGROUP() is replaced by the authorization group name.
•
LOGICALNAME() is replaced by the logical folder name.
•
USERNAME() is replaced by the session user name.
If the template is form-specific, you can use P() and V() keyword values.
Folder Access Depth
Specify the level to which subfolders can be specified under this folder. This should be a value based on
the folder path. For example:
\depth1
\depth1\depth2
\depth1\depth2\depth3
Note:  For performance reasons, this value is set to 3. This cannot be overridden by the logical folder's
folder depth. The logical folder's folder depth is also used to check the access depth of the user. If you
want to increase the directory depth, you can do so by passing the recursive depth in the form script.
But note that this can lead to huge performance issues.
Attached Document Type
Provide the IDM document ID for any logical folder the Mongoose file server created for IDM.
See Example: Setting Up Templates for Logical Folders on page 61.
3 
Set up the user groups that can access certain logical folders:
a 
On the Groups form, set up a group and add users to it.
b 
Click Group Authorizations.
Infor Mongoose Administration Guide | 60
 
File servers

c 
In the Object Authorizations for Group form, for the selected group, select File Server as the Object
Type.
d 
In the Object Name field, select a logical folder whose access should be restricted to this group.
e 
Set these access privileges to Granted: Delete, Edit, and Read.
4 
For on-premises applications, set up physical folders on the server machine to match the logical folder
structure, and set up sharing on those folders as appropriate.
After the file servers and logical folders are defined, you can select them on parameters forms and on the
Documents and Attached Documents forms. Users can then access the files from the File Maintenance form
as shown in this diagram:
File Request:
Get File
Upload File
Delete File
List File
Validate File
Error message
Does user have
access to appropriate
logical folder?
File Servers and
Logical Folders
Shared Path (UNC)
FTP
Amazon S3
no
yes
You can also use the file server as back up in case the local file system gets shut down or pulled out from the
application site. You must set up this report server location in Configuration Manager and any report
generated is also copied to this file server. See the "New/Edit Application - Advanced tab" in the Configuration
Manager help.
Example: Setting up templates for logical folders
In this example, the system administrator defined this information on the File Server Logical Folders form:
Folder depth
Folder template
Server name
Logical folder name
3
/Customers/P(CUST-
NUM)/PO/
ServerS3
CustPO
3
/USERNAME()/Benefits/401/
ServerFTP
Benefits401K
Infor Mongoose Administration Guide | 61
 
File servers

3
/USERGROUP()/LOGICAL-
NAME()/
ServerUNC
BenefitsMisc
3
/USERGROUP()/inter-
nals/USERNAME()/LOGICAL-
NAME()/
ServerUNC
BenefitsMedPlan
The system administrator set up an authorization group called CompanyDocs that grants access to these
logical folders:
•
Benefits401K
•
BenefitsMedPlan
When the user jdean tries to access the logical folder BenefitsMedPlan, the application takes these actions:
1 
Checks whether jdean belongs to the group CompanyDocs. If yes, it continues.
2 
Checks whether the file path matches the folder template and depth. In this case, the template path is
substituted as /CompanyDocs/internals/jdean/BenefitsMedPlan/ and the folder depth is 3.
3 
The user can access any file in these folders:
•
CompanyDocs/internals/jdean/BenefitsMedPlan
•
CompanyDocs/internals/jdean/BenefitsMedPlan/level1
•
CompanyDocs/internals/jdean/BenefitsMedPlan/level1/level2
Managing file servers for linked documents
Using linked files on a file server or FTP/SFTP server can be useful if you want to set up your documents in a
hierarchical structure. For example, you could create an Items folder on the server, with subfolders for each
item number that contain documents relevant to that item. Then you can link the documents to the appropriate
items within the application.
If you have documents that can be stored as linked files outside of the application database, use the File
Servers form to set up and manage the servers or FTP sites that contain the files:
1 
In the File Servers form, provide this information:
Active
To have the file server automatically be activated when the application starts, select this option.
If you do not want the file server to be activated automatically when the application starts, clear this
check box. To manually activate a server without restarting the application, click the Activate button.
To manually deactivate a server without restarting the application, click the Deactivate button.
Server Name
Specify a unique name for the server.
Users can select this name in the Document Types form.
Server Type
Select one of these options:
•
FTP: Use this option only for on-premises applications.
Infor Mongoose Administration Guide | 62
 
File servers

When you select this option, the FTP tab is automatically selected.
•
Shared Path: Use this option only for on-premises applications.
•
AWS S3: Use this option only for cloud applications.
When you select this option, the AWS S3 tab is automatically selected.
•
SFTP: Use this option only for cloud applications.
When you select this option, the SFTP tab is automatically selected, and the Domain field label
changes to Host Name.
•
Mongoose Web Service: Use this option only for cloud applications.
•
Infor Document Management: Use this option if you are using Infor Document Management as
your file server for either cloud or on-premises operations.
When you select this option, the Infor Document Management tab is automatically selected.
2 
If you select a server type of FTP, provide this information:
Domain
Provide the domain for user logins.
Shared/FTP Path
Specify the root path where files are stored on the server. Use this format: FTP_Server/folder/
User ID
Provide the user ID to access the FTP site.
Password
Provide the user password to access the FTP site.
Remote Server Name
Optionally, specify the DNS name or IP address of the server.
Enable SSL
Select this check box to enable SSL.
Use Passive
Select this check box to enable Passive FTP.
3 
If you select a server type of Shared Path, provide this information:
Domain
Provide the domain for user logins.
Shared/FTP Path
Specify the root path where files are stored on the server. Use this format: \\server\share\
User ID
Provide the user ID to access the shared server.
Password
Provide the user password to access the shared server.
Infor Mongoose Administration Guide | 63
 
File servers

Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this option.
4 
If you select a server type of AWS S3, specify this information:
Bucket Name/Set as Default Bucket
Provide an AWS bucket name or select Set as Default Bucket to set this field to Default.
Root Path
Specify a sub path to use after the bucket path. Doing this, you can create different file servers under
the same bucket name.
For example: Server1 has a root path of /Benefits/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/Benefits/
Server2 has a root path of /CustomerOrders/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/CustomerOrders/
User ID
Provide the user ID to access the AWS bucket.
Password
Provide the user password to access the AWS bucket.
Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this option.
Server Side Encryption Method
Select the encryption method. If the Set As Default Bucket is selected, this option is disabled.
•
None: If no encryption method is to be used, select this option.
•
AES256: To use Advanced Encryption Standard 256 encryption method, select this option.
•
AWSKMS: To use Key Management or KMS encryption method, select this option.
Server Side Encryption Key
Optionally, provide the key to be used for the AWS S3 file encryption. If the Set As Default Bucket is
selected, this option is disabled.
External Bucket Name
Provide the bucket name to be used. If the Set As Default Bucket is selected, this option is disabled.
AWS Access Key
Provide the AWS access key.
AWS Secret
Provide the AWS secret access key.
Infor Mongoose Administration Guide | 64
 
File servers

AWS Session
Provide the AWS session token.
Use Pre-Signed URL To Upload
Optionally, to use the pre-signed URL to upload, select this check box.
Use Pre-Signed URL To Download
Optionally, to use the pre-signed URL to download, select this check box.
5 
If you select a server type of SFTP, specify this information:
Host Name
Provide the host name for user logins (for example, sftp.cilrebo.com:22).
Root Path
Specify a sub path to use after the bucket path. Doing this, you can create different file servers under
the same bucket name.
For example: Server1 has a root path of /Benefits/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/Benefits/
Server2 has a root path of /CustomerOrders/
The full path is infor-awsbucket-prd-csbi/PRIVATE/csbi/903/tenant1/site1/CustomerOrders/
User ID
Provide the user ID to access the SFTP site.
Password
Provide the user password to access the SFTP site.
Remote Server Name
Optionally, provide the DNS name or IP address of the server.
Authentication
Select the authentication to use:
•
Basic: Requires only the user ID and password
•
Use Private/Public Key: Requires only the key value
•
Use Both Password and Key: Requires both the password and key value
SSH/OpenSSH Key File Path
This field contains the path to access the key file.
Encrypted SSH/OpenSSH Key
This field contains encrypted key value when you click the Save Key button.
Pass Phrase / Key Password
Provide a password to open or read the key file path or content. This value is encrypted when you click
Save.
Load Key
To load the key content from the file, click this button.
If you select Basic authentication, this button is disabled.
Infor Mongoose Administration Guide | 65
 
File servers

Clear Key
To clear the content or file path, click this button.
If you select Basic authentication, this button is disabled.
Save Key
To encrypt the content or the file path, click this button.
If you select Basic authentication, this button is disabled.
6 
If you select a server type of Mongoose Web Service, specify this information:
Domain
Specify the domain for user logins.
URL
Provide the URL for the Mongoose web service, for example: https://servicename:port/ca
User ID
Provide the user ID needed to access the web server.
Password
Provide the user password to access the web server.
Remote Server Name
Provide the DNS name or IP address of the server.
Use IDO Request Service Always
To use the IDO request service at all times, select this check box.
7 
If you select a server type of Infor Document Management, specify this information:
Domain
Provide the domain for user logins.
URL
Provide the URL for the IDM service, for example: https://servicename:port/ca
User ID
Provide the user ID to access the IDM server.
Password
Provide the user password to access the IDM server.
IDM Document ID
Provide the document ID of the document type that you created in IDM.
Authentication
Select the type of authentication to use:
•
Basic: Uses the domain account
•
OAuth1: Uses the consumer key as user name and secret key as password
Note:  Although other authentication methods are listed, only Basic and OAuth1 are currently supported.
Infor Mongoose Administration Guide | 66
 
File servers

Map IDM Entity with Logical Folder
To use multiple document types for any single file server, select this option.
Delete All Before Overwrite
To remove all versions of the same document before you overwrite or update any existing document in
IDM that have the same key, select this option.
Use Tenant
To use the Tenant ID with an IDM connection, select this option.
Print Server
To set the file server as the print server for IDM Enterprise Print, select this option.
Validate Printer
To validate the file server for IDM Enterprise Print, click this button.
Use Authentication Service
To attach an authentication service, instead of loading OAuth keys or specifying a consumer key and
shared secret, select this option.
Note:  This field is enabled for OAuth1 authentication only.
Service Name
Select the service name, as specified in Configuration Manager.
Set Single IDM User Name/ID
Provide one IDM user name or ID to be shared across the application to access IDM.
Note: 
•
If you keep this field blank, the application uses the Workstation Domain/ID that is specified in the
Users form.
•
If you select Basic authentication, this field is disabled.
Use Single IDM User
Enable the use of a single IDM user name or ID to be shared across the application to access IDM.
•
For All Users: When selected, the Set Single IDM User Name/ID value is used for all users.
•
As Default: When selected, the Set Single IDM User Name/ID value is used for users who do not
have IDM accounts.
Configure IDM
To open a new form where you can create and maintain IDM document types, click this button.
Load OAuth Keys
To load the OAuth JSON key file that contains the consumer key and secret key, click this button.
If you select Basic authentication, this button is disabled.
Clear OAuth Keys
To clear the previously loaded OAuth JSON key file, click this button.
If you select Basic authentication, this button is disabled.
Infor Mongoose Administration Guide | 67
 
File servers

8 
Optionally, to add any properties that might be required for a different server type in JSON format,
perform these steps for each required key/value pair:
a 
Click Additional Properties.
b 
In the JSON Key Value Pairs form, specify the key name and value for each key/value pair required
by the server.
c 
Optionally, to have the key/value pair data encrypted, select the Encrypt option.
9 
Optionally, to validate that the file server is connected properly, click Validate.
10 To immediately activate the connection to the file server, click Activate.
11 Optionally, to reset the connections of all active file servers, click Reset All Servers.
Infor Mongoose Administration Guide | 68
 
File servers


