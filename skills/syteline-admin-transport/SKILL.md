---
name: "syteline-admin-transport"
description: "App Metadata Transport, transaction isolation levels, integration with other applications (ION, BODs, Data Catalog), improving system performance"
version: "1.0"
tags:
  - syteline
  - admin
  - transport
  - integration
  - performance
  - mongoose
---

# syteline-admin-transport

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 18: App Metadata Transport
About App Metadata Transport
Use the App Metadata Transport form/utility to export or import metadata for these objects:
•
IDOs
•
Property classes
•
Custom assemblies
•
The Application Event System (AES)
•
Replication documents, which include Business Object Documents (BODs), inbound cross references,
and outbound cross references
•
Critical numbers
•
DataViews
•
DataView actions
•
DataSearch sources from an XML file to a target database
•
Form extensions
•
Init data from specified tables (Command Line Interface only)
The typical use of this utility includes these basic steps:
1 
Export the metadata from a Source database to an XML file.
2 
Import the metadata from an XML file to a Target database.
The App Metadata Transport utility can be run using any of these methods:
•
Use the App Metadata Transport form in the web client.
Note:  This form is created for instances where the App Metadata Transport stand-alone utility cannot
be run due to security concerns.
•
Run the AppMetadataTransport.exe file from a command line.
•
Locate and double-click the AppMetadataTransport.exe file in the folder where the application is installed.
Note:  This option is available only in an on-premises installation where you have access to this utility
as a stand-alone executable.
Infor Mongoose Administration Guide | 306
 
App Metadata Transport

Before using App Metadata Transport
You can export metadata at any time, because App Metadata Transport simply reads the data and writes it
to an XML file.
However, to avoid potential collisions, you should import metadata only during periods of non-peak use.
We recommend that you back up the Target database before importing any metadata.
Caution:  To avoid collisions, do not run multiple instances of this utility.
Exporting application metadata
Before any application metadata can be imported, it must first be exported into an XML file.
Note:  You can also export metadata using a Command Line Interface (CLI). See Running App Metadata
Transport from a CLI on page 312.
1 
Open the App Metadata Transport utility (either the form version or the stand-alone version).
2 
On the Action page, select the Export metadata from a source database to an XML file check box, and
then click Next.
3 
(Stand-alone version only) On the Source page, select the Source Configuration to use as the source
for the metadata.
When you make your selection, the Source Connection panel displays additional information about the
source.
Note:  The form version skips this step. You can use only the configuration in which you are working as
the Source.
4 
Click Next.
5 
On the Target page, specify the name for the XML file that is to contain the exported metadata.
The action to do this depends on where you are using the utility:
•
In the web client, enter the file name into the Export File Name field.
Note:  Make sure you add the .XML extension at the end of the file name. The web client does not
add the extension automatically.
•
In the stand-alone version, you can use the Browse button to launch a Save As dialog box in which
you can navigate to the desired location for the file and provide the file name.
Note:  In these cases, the utility appends the .XML extension automatically.
6 
Click Next.
7 
For each of the next series of pages, select the metadata to export, and then click Next.
For most tabs, you can select none, all, or selected items. In some cases, you can also select only items
that are referenced by selected items, as indicated in this table:
Infor Mongoose Administration Guide | 307
 
App Metadata Transport

Affected data
Page
Multiple database tables, such as ido.Collections, ido.Tables,
ido.Methods, and others
Even though multiple tables are affected, the IDO is exported as a single
unit, for example, the ido.Collections record and all its children.
Note:  You must select the IDO Project to which the IDO belongs before
you can select specific IDOs for export.
IDOs
Only data in the ido.PropertyClasses tables
You can also select to have the metadata for property classes referenced
by selected IDOs exported.
Property Classes
Only data in the ido.CustomAssembly tables
Note:  In the stand-alone version, this page is labeled Custom Assem-
blies. It amounts to the same thing.
You can also select to have the metadata for IDO assemblies referenced
by selected IDOs exported.
IDO Assemblies
Application events metadata for a selected Access As value
Note:  You must select an Access As value before this and the following
options become available.
Events
Application event trigger metadata for the previously selected Access
As value
You can also select to have the metadata for event triggers that are
referenced by selected events exported.
Event Triggers
All the application event handler metadata for the previously selected
Access As value
You can also select to have the metadata for event handlers that are
referenced by selected events exported.
Event Handlers
All the application event handler global constant metadata for the
previously selected Access As value
You can also select to have the metadata for event global constants
that are referenced by selected event handlers exported.
Event Global Constants
All Business Object Document (BOD), Inbound Cross Reference, and
Outbound Cross Reference metadata for a selected Access As value
Note:  You must select an Access As value before these options and
following few become available.
You can also select to have the metadata for entire replication docu-
ments where any portion matches the Access As filter exported.
Replication Documents
Infor Mongoose Administration Guide | 308
 
App Metadata Transport

Affected data
Page
All metadata for replication document elements of the previously se-
lected Access As value
You can also select to have the metadata for replication document el-
ements referenced by selected replication documents exported.
Replicaton Document Elements
All metadata for replication document elements of the previously se-
lected Access As value
You can also select to have the metadata for replication document at-
tributes referenced by selected replication document elements export-
ed.
Replicaton Document Attributes
All metadata for inbound replication documents of the previously se-
lected Access As value
You can also select to have the metadata for inbound replication doc-
uments referenced by selected replication documents exported.
Replicaton Document Inbounds
All metadata for outbound replication documents of the previously
selected Access As value
You can also select to have the metadata for outbound replication
documents referenced by selected replication documents exported.
Replicaton Document Out-
bounds
Multiple database tables, including WBDataViews, WBDataViewLayouts,
WBDataViewActions, and others
This metadata can include multiple DataViews, DataView Layouts, and
associated DataView Setup information.
You can also select specific DataView Actions separately.
Predefined DataViews
All the Form Extensions data for a particular Access As value
Note:  You must select an Access As value before the selection options
become available.
Form Extensions
8 
On the Summary page, verify the actions to be performed.
9 
Click Finish.
The App Metadata Transport utility converts the metadata to an XML format and saves it with the designated
file name (and path, if applicable).
In the case of the web client version, the browser typically presents the saved file automatically, with the
option to open it. If it does not, you can locate the file in your Downloads folder and open it from there.
In the case of the stand-alone version, the file is saved to the location you designated when naming the file.
In all cases, you can use any good text/code editor to review the saved metadata.
Infor Mongoose Administration Guide | 309
 
App Metadata Transport

Importing application metadata
Before any application metadata can be imported, it must first be exported into an XML file. This must be a
file that was previously exported using any version of the App Metadata Transport utility.
Note:  You can also import metadata using a Command Line Interface (CLI). See Running App Metadata
Transport from a CLI on page 312.
1 
Open the App Metadata Transport utility (either the form version or the stand-alone version).
2 
On the Action page, select Import metadata from an XML file to a target database, and then click
Next.
3 
On the Source page, provide this information:
Import File Name
If you know the name of the file, you can type it into this field. Otherwise, perform one of these actions
(depending on which version of the utility you are using):
•
In the web client, you can click Upload, which launches the Import Binary Data From File dialog
box. Then, either drag-and-drop the XML file from its location on your local computer; or you can
click Select File and use the Windows Open dialog box to navigate to and select the XML file.
Note:  Your XML file must have the .XML file extension, or the utility cannot upload it.
Convert to local Access As
To convert the Access As identifier of the imported components to be the Access As value of the target
environment, select this check box.
Note:  If the IDO project of the IDO to be imported has an Access As value different from the environment
in which you are currently working, you cannot convert the Access As value of that IDO collection.
4 
Click Next.
5 
(Stand-alone version only) On the Target tab, select the Target Configuration.
Note:  This page is not available in the form version.
6 
Click Next.
7 
For each of the next series of pages, select the metadata to import, and then click Next.
Note:  For most tabs, you can select none, all, or selected items. Be aware that, if no metadata was
exported for any given metadata type in this table, then that page does not display for import operations.
Affected data
Page
Multiple database tables, such as ido.Collections, ido.Tables,
ido.Methods, and others
Even though multiple tables are affected, the IDO is imported as a single
unit, for example, the ido.Collections record and all its children.
IDOs
Only metadata in the ido.PropertyClasses tables
Property Classes
Only metadata in the ido.CustomAssembly tables
IDO Assemblies
Application event trigger metadata
Event Triggers
Infor Mongoose Administration Guide | 310
 
App Metadata Transport

Affected data
Page
Application event handler metadata
Event Handlers
Application event global constant metadata
Event Global Constants
Metadata for all replication document elements
Replication Document Elements
Metadata for all replication document attributes
Replication Document At-
tributes
Metadata for all replication documents used as inbound documents
Replication Document Inbounds
Metadata for all replication documents used as outbound documents
Replication Document Out-
bounds
Metadata for Predefined DataViews, DataView Layouts, and associated
DataView Setup information
Predefined DataViews
Metadata for custom form extensions
Form Extensions
8 
Click Next.
9 
On the Summary page, verify the actions to be performed.
10 Click Finish.
The App Metadata Transport utility imports the metadata from the uploaded XML file and converts it to the
appropriate metadata in the application database.
Testing App Metadata Transport results
To verify that the data was imported successfully, use one of these options:
•
Open the XML file and manually compare the file to the resulting Target database.
•
Run queries on the Target database:
1 
Before you import the metadata, create a backup of the target database.
2 
Import the metadata.
See Importing application metadata on page 310.
3 
Restore the backup of the target database under a different name.
4 
Run queries that compare the original (pre-import) database to the updated (post-import) database to
determine what was changed and verify that it behaves as expected.
Infor Mongoose Administration Guide | 311
 
App Metadata Transport

Running App Metadata Transport from a CLI
You can run the App Metadata Transport utility from a command line interface (CLI), by calling
AppMetadataTransport.exe.
General CLI command and swiches
The primary command in a CLI associated with the App Metadata Transport utility is the -action command.
This is followed by any of these switches:
Note:  When you execute any of these switches from the command line, the App Metadata Transport utility
opens, set up for the designated action.
Description
Switch
Imports a single metadata object from an XML configuration file.
import
Exports a single metadata object to an XML file.
export
Imports all metadata objects within a set of XML files residing in a designated
folder.
This option is available only from a command line.
massimport
Exports all metadata objects to a set of XML files. 
All IDOs, property classes, and IDO extension class assemblies from the
source configuration are exported to the designated folder, each in a sepa-
rate file.
This option is available only from a command line.
massexport
Imports all metadata objects within a set of XML files residing in a designated
folder, but does not launch the App Metadata Transport utility user interface.
The operation completes entirely within the Command Prompt window.
This option is available only from a command line.
massimportnoui
Exports all metadata objects to a set of XML files, but does not launch the
App Metadata Transport utility user interface. The operation completes
entirely within the Command Prompt window.
All IDOs, property classes, and IDO extension class assemblies from the
source configuration are exported to the designated folder, each in a sepa-
rate file.
This option is available only from a command line.
massexportnouiaccessas
Exports the init data of the specified table to an XML file. See About export/im-
porting init data from/to tables in the Infor Mongoose Administration Guide.
This option is available only from a CLI and can be used for only a single
table at a time.
This option requires a tableName parameter to be specified.
exportinitdata
Infor Mongoose Administration Guide | 312
 
App Metadata Transport

Description
Switch
Imports the init data of the specified table from an XML file to which it has
been exported. See See About export/importing init data from/to tables in
the Infor Mongoose Administration Guide.
This option is available only from a CLI and can be used for only a single
table at one time. This option requires a tableName parameter to be speci-
fied.
importinitdata
Imports the init data from all of the XML files in a specified directory. See
See About export/importing init data from/to tables in the Infor Mongoose
Administration Guide.
This option is available only from a CLI. This option requires a tableName
parameter to be specified.
massimportinitdata
Common parameters used with these switches
This table lists most of the common parameters used in association with this utility.
Description
Parameter
Specify the name of the application configuration to be used as the Source
-sourceConfig
Specify the name of the application configuration to be used as the Target
-targetConfig
Specify the Access As value of the metadata to be exported. The metadata
can include IDO collections, custom assemblies, property classes, replication
documents, and events.
This parameter is used only with the massexportnouiaccessas switch.
-accessAs
Specify the name of the XML file that contains the metadata to be imported
(sourceFileName.xml); or, when using the mass import command, the name
of the folder that contains the metadata to be imported.
-sourceXML
Specify the name of the XML file that is to contain the metadata being ex-
ported (targetFileName.xml); or, when using the mass export command,
the name of the folder that is to contain the metadata being exported.
-targetXML
Specify the name of the directory to which the XML file is to be written.
-targetDir
Specify the name of the table that has the init data that you want to export.
-tableName
Specifies that only INSERT actions are to be performed, and not UPDATE
actions.
-insertOnly
Use this parameter to set the timeout value, in seconds, for each individual
command that runs on the database.
-commandTimeout
Specify the path and filename to which the log file is to be written.
-logFileLocation
Specify the path to the directory where error information is to be written.
-errorsFolder
Use this parameter to launch a dialog box that lists all the available param-
eters for this utility, with brief descriptions.
-help
Infor Mongoose Administration Guide | 313
 
App Metadata Transport

Examples
To perform a mass import, use this command:
AppMetadataTransport -action massimport -targetConfig 
 configName -sourceXml "C:\sourceFolder" 
 
To perform a mass export, use this command:
AppMetadataTransport -action massexport -sourceConfig 
 configName -targetXml "C:\destinationFolder"
For examples of actions involving init data, see About export/importing init data from/to tables in the Infor
Mongoose Administration Guide.
Infor Mongoose Administration Guide | 314
 
App Metadata Transport

Chapter 19: Transaction isolation levels
About transaction isolation levels
The Collection Read Mode setting corresponds to the Transact-SQL statement SET TRANSACTION ISOLATION
LEVEL READ COMMITTED [or UNCOMMITTED]. It applies to queries that load primary collections, secondary
collections, and lists, and to "in-collection" validations. It also applies to background-task queries that
generate reports and to background-task stored procedures.
The base, system-wide transaction isolation level is set using the Collection Read Mode process default
setting on the Process Defaults form. You can override this base setting for individual reports and stored
procedures using the Background Task Definitions form. You can also override the setting at the form level
using the Read Mode field in the designer.
The UNCOMMITTED setting allows the reading of uncommitted data. This means that users do not have to
wait for other long-running transactions accessing the same dataset to complete before their queries can
complete. However, an uncommitted record can be rolled back between the time the query displays it and
the time the user attempts to save updates to it. Users cannot save a record if uncommitted data is rolled
back at some time after the query, because the optimistic locking fails. The user must refresh the record to
get valid data before making changes and saving the data.
With the COMMITTED setting, a query reads committed data and returns only data for which the query can
get a shared lock.
Setting transaction isolation levels
Setting the base transaction isolation level
To change the base transaction isolation level, perform these actions:
1 
In the Process Defaults form, select the Collection Read Mode process default.
2 
In the Default Value field, specify either COMMITTED or UNCOMMITTED.
If the field is blank, queries use COMMITTED as the default transaction isolation level.
Overriding the transaction isolation level for a report or stored procedure
To change the transaction isolation level for a report or stored procedure, perform these actions:
Infor Mongoose Administration Guide | 315
 
Transaction isolation levels

1 
In the Background Task Definitions form, select a background task of the type RPT or SP.
2 
In theIsolation Level field, select Read Committed or Read Uncommitted.
If the field is blank, queries use the transaction isolation level as set for the Collection Read Mode process
default on the Process Defaults form.
Overriding the transaction isolation level for an individual form
To change the transaction isolation level for a collection in a form, use the Read Mode field in the Web designer.
Infor Mongoose Administration Guide | 316
 
Transaction isolation levels

Chapter 20: About integration with other applications
Applications that are based on the Mongoose framework can integrate with other applications using a variety
of methods, including but not limited to these:
•
Point-to-point access using web services.
See the IDO Integration with External Applications section in the help.
•
Business Object Documents (BODs) that are passed through ION to the other application.
See the help topics about replication and BODs.
•
Point-to-point access through ION APIs.
See the IDO Integration with External Applications section in the help.
•
XML documents other than BODs. Your Mongoose-based application can use non-transactional replication
to send formatted files to a designated “site” for transfer to the other application.
See the help topics about replication and BODs.
•
Direct IDO communication with another application, for example, connection to Infor Document
Management (IDM) and the Infor Data Lake.
About external application parameters
To maintain the user credentials for external applications, such as Infor Document Management (IDM), use
the External App Parameters form. This form provides a central location for application connection
credentials.
These parameters are needed to create a user profile for external applications:
Description
Parameter
The name of the connecting application
Application Name
The name of the instance
Application Instance
URL address
URL
Username credentials for the base URL. This field changes to
Client Key whenever OAuth1 is selected as the authentication
method
User Name or Client Key
Infor Mongoose Administration Guide | 317
 
About integration with other applications

Description
Parameter
Password credentials for the username account. This field
changes to Client Secret whenever OAuth1 is selected as the
authentication method
Password or Client Secret
List of system-defined authentication methods
Authentication
List of system-defined signature methods. This field is shown
if OAuth1 is selected as the authentication method
Signature Method
List of ION API servers
ION API Server ID
Indicates it is an active record
Is Active
Only one active record should exist for each unique Site Reference and Application Name pair. For example,
for SiteRefA and AppName1, where:
•
Record 1: Application Instance is InstanceName1 and Is Active check box is selected
•
Record 2: Application Instance is InstanceName2 and Is Active check box is cleared
•
Record 3: Application Instance is InstanceName3 and Is Active check box is cleared
These records are valid since there is only one active record in the system.
Note:  For each Site Reference and Application Name pair, you can create one to many inactive records,
but only zero to one active records can exist in the system.
The Application Instance must be unique for a Site Reference and Application Name pair. For example, for
SiteRefA and AppName1, where:
•
Record 1: Application Instance is InstanceName1 and Is Active check box is selected
•
Record 2: Application Instance is InstanceName2 and Is Active check box is cleared
•
Record 3: Application Instance is InstanceName1 and Is Active check box is cleared
Record 3 is invalid since AppName1 has multiple instance name values of InstanceName1.
There can only be one active record for each unique Site Reference and Application Name pair. Other records
must be marked inactive. These records are valid:
•
Record 1: SiteRefA, AppName1, InstanceName1, Active
•
Record 2: SiteRefA, AppName1, InstanceName2, Inactive
•
Record 3: SiteRefA, AppName1, InstanceName3, Inactive
•
Record 4: SiteRefA, AppName2, InstanceName1, Active
•
Record 5: SiteRefB, AppName1, InstanceName1, Active
The Application Name and Application Instance is a unique pair within a Site Reference. Record 1 and
Record 5 are both valid because they are on different sites.
For IDM, you can define multiple connections to multiple IDM instances, if present. These records are valid:
•
Record 1: Application Name is IDM, Application Instance is IDM Live, andIs Active check box is selected
•
Record 2: Application Name is IDM, Application Instance is IDM Test, and Is Active check box is cleared
These records are valid because only one Application Name is active. There can be many inactive application
names.
Infor Mongoose Administration Guide | 318
 
About integration with other applications

Creating a user profile for external applications
1 
Open the External App Parameters form, and initiate a new record.
2 
Provide this information:
Application Name
Provide a name for the connecting application, for example, Infor Document Management (IDM).
Application Instance
Specify a name for the instance that is to be used for the connecting application.
Note:  You can create one to many instance names for each Application Name.
URL
Using the HTTPS format, specify the URL of the connecting application. For example, for IDM, you would
specify https://servername:port/ca.
User Name
Specify the user ID that is used for logging into the connecting application.
Password
Specify the password for the connecting application username account.
Client Key
Specify the client key to use for logging into the connecting application. This field is shown whenever
OAuth1 is selected as the authentication method.
Client Secret
Specify the client secret for the connecting application client key. This field is shown whenever OAuth1
is selected as the authentication method.
3 
Select the Authentication method.
Although other authentication methods are listed, only Basic and OAuth1 are currently supported.
Note:  For IDM, OAuth1 method accepts the consumer key as the username and the secret key as the
password. Basic authentication uses a domain account. OAuth1 is chosen as a backend service account
so that impersonation can be implemented, rather than have every IDM's CreatedBy and UpdatedBy
value applied to the same user.
4 
Optionally, Select the Signature Method
Note:  This field is shown whenever OAuth1 is selected as the authentication method.
5 
Optionally, Select the ION API Server ID.
Note:  Once the ION API Server ID is selected, the URL, Username, Password, and Authentication fields,
as well as the Verify button are disabled.
6 
If the record is to be active, select the Is Active check box.
7 
Optionally, to confirm that the login information and credentials are valid, click Verify.
If the login information and credentials are not valid, the application generates a failure message.
8 
Save.
Infor Mongoose Administration Guide | 319
 
About integration with other applications

About widget forms
Widgets are forms that are small and suitable for use in Infor OS Portal, or as context applications within Infor
OS Portal, or in other similar scenarios. To view a list of the forms that developers and customers have created
to be used as widgets, use the Widget Forms form.
See Designating a form as a “widget form” on page 320.
Designating a form as a “widget” form
1 
Open the Widget Forms form, and initiate a new record.
2 
Provide this information:
Form Name
Select the name of the form that you want to designate and use as a widget.
Description
Optionally, provide a description of what the widget is intended to do.
3 
Select the Use as Widget check box.
4 
Save.
Note:  The Access As field contains the name of the environment where the currently selected record was
created. If the current record has an Access As value that is different from the current environment’s Access
As, that record is read-only.
Infor ION API
About Infor ION API
ION API Gateway is an Infor product that aggregates APIs from various products into a single clearing house,
with a common security model, monitoring, throttling, and so on. ION API also has a UI component that
accompanies the gateway, where you can add and see APIs that are exposed by the gateway, set up authorized
clients that can access the APIs, look at the logs, test the APIs, among other tasks.
Mongoose includes functionality that allows administrators to set up a reference to the gateway by means
of an ION API server; and then use an IDO method to make calls to APIs through that server. In the cloud, when
a tenant is provisioned with an ION API app reference in their Mongoose-based application (done in CSP), the
ION API server reference to the gateway in the cloud is set up automatically. Other servers can be set up
manually; for example, in a cloud Mongoose instance referring to a gateway on-premises, or vice versa.
Infor Mongoose Administration Guide | 320
 
About integration with other applications

The ION API Servers form contains some basic info needed to access the gateway by means of the ION API
server. There are two modes of authenticating users to the gateway, referred to as “SSO” (Single Sign-On)
and “non-SSO”. Using non-SSO authentication requires additional setup on the ION API side. At the end of
that setup process an .ionapi file is produced. This .ionapi file must then be uploaded using the ION API
servers form.
Note:  If you add a brand new server, the ION API servers form auto-fills some values for the URLs that are
not correct. This is a known bug. It does not, however, interfere with existing values, assuming that they are
correct.
Use of the remaining ION API-related forms are not, strictly speaking, necessary to use the gateway through
the IDO method. Their primary purpose is to enable easier no-code testing of the APIs. “ION API server suites”
correspond roughly to the APIs exposed by the gateway. In each suite, then, there are individual methods
that can be called in the API. Those related forms describe the suites or methods you might want to access;
but be aware that they do not have to be in the forms to be usable. The forms simply enable the drop-down
functionality in the Test ION API Invoke form, so that you can choose the suite and method without typing
them in. Also, those forms have buttons that can query the gateway for available suites and methods in the
suite.
Adding an ION API server
To be able to access and use the resources of an ION API server, that server must first be "registered" in your
base application.
1 
Open the ION API Servers form and initiate a new record.
2 
Provide this information:
Server ID
This field is automatically populated, but you can change it. Typically, this ID is a non-negative integer,
with zero (0) being reserved for the default ION API server.
Note:  If you are using the server to enable the Data Lake IDO Wizard, you must designate the server
as the default ION API server. To do this, set this field to 0 (zero).
Description
Optionally, specify a helpful description for the server.
Server URL
Optionally, specify the URL of the ION API server.
This field is for documentation purposes only. The actual URL is derived from the .ionapi file.
STS URL
Specify the address of the WS-Trust STS (Security Token Service) endpoint. This is available from the
environment’s idp.properties file, under the property name idp.wstrust.sts.url.
Token URL
Specify the address of the OAuth token endpoint. This is available from the environment’s idp.properties
file, under the property name idp.oauth.token.url.
Infor Mongoose Administration Guide | 321
 
About integration with other applications

Import .ionapi file
Use this button to browse for an .ionapi file.
You can get the .ionapi file from the Infor ION API provisioning page.
Note:  The .ionapi exists check box is automatically selected once the .ionapi file is imported.
Import Private Key
Use this button to browse for a private key, in .json format.
You must import a .ionapi first to enable this button.
Note:  The Private Key Exist check box is automatically selected once the record is saved.
Import Suites
Optionally, use this button to search for and select an ION API server suite. When a suite is imported,
information about the suite displays in the Suites grid.
Suites
Optionally, use this button to launch the ION API Suites form, in which you can define a suite (or group)
of ION API servers.
When you create a suite using that form and save it, it is automatically added to this form and information
about it displays in the Suites grid when you refresh the form.
3 
Save.
Adding an ION API server suite
Use this procedure to create your own API server suite. To import an existing server suite, see Importing an
ION API server suite on page 325.
1 
Open the ION API Servers form.
2 
Select an entry where a suite is to be added.
3 
Click Suites.
4 
In the ION API Suites form, initiate a new record.
5 
Provide this information:
Name
Provide a name for the suite.
Description
Optionally, provide a helpful description for this suite.
Context
Specify the API context that is to be appended to the base URL when calling methods from this suite.
6 
Optionally, define or import one or more API methods to be used.
7 
Save.
Infor Mongoose Administration Guide | 322
 
About integration with other applications

About ION API Methods
The ION API Methods provides the core integration layer between the Mongoose runtime and the Infor ION
API. These methods are used for SSO-based calls, token-based calls, token retrieval, and endpoint metadata.
Method type
Description
Method name
Extension class standard method
This method processes the output
fields from an ION API response
and returns them in a structured
format for IDO consumption. This
method is used by Invoke meth-
ods to shape the returned data.
CollectOutputFields
Extension class standard method
This method retrieves a bearer
token using SSO and exchanges
the Ming.le session for an OAuth
token. This method includes retry
logic when the token retrieval
fails.
GetBearerToken
Extension class standard method
This method is similar to the GetB
earerToken method, and this
method is supports updated re-
sponse shapes or the newer
OAuth behavior.
GetBearerToken2
Extension class standard method
This method retrieves the base
ION API URL and the tenant ID re-
quired to build API calls.
GetIONAPIInfo
Extension class standard method
This method calls an ION API
endpoint using an SSO based au-
thentication.
InvokeIONAPIMethod
Extension class standard method
This is the updated version of the
InvokeIONAPIMethod with the ex-
panded parameters and improved
response handling.
InvokeIONAPIMethod2
Extension class standard method
This method calls an ION API
endpoint using a provided bearer
token instead of an SSO. This
method includes a recursive retry
when the HTTP response codes
are not successful.
InvokeIONAPIMethodWithToken
Infor Mongoose Administration Guide | 323
 
About integration with other applications

Method type
Description
Method name
Extension class standard method
This is the update version of the
InvokeIONAPIWithToken that sup-
ports binary responses and de-
compression logic. This method
includes an expanded output in-
dexes and a binaryResponse flag.
InvokeIONAPIWithToken2
Adding an ION API method
Use this procedure to create your own API method definition. To import an existing method definition, see
Importing an ION API method on page 326.
1 
Open the ION API Servers form.
2 
Select the server profile to which you want to add the method.
3 
Click Suites.
4 
Select the suite to which you want to add the method.
5 
Click Methods.
6 
In the ION API Methods form, initiate a new record.
7 
Provide this information:
HTTP Method
Select the HTTP method.
Method Name
Specify a name for this method.
Path
Specify the relative URL path for this method.
Description
Optionally, specify a short description for this method.
8 
Save.
The application adds the method profile to the suite profile. If it does not display automatically in the ION
API Suites form, refresh that form.
Testing an ION API method
Use this procedure to test an API method definition.
1 
Open the Test ION API Invoke form.
2 
Provide this information:
Infor Mongoose Administration Guide | 324
 
About integration with other applications

Server ID
Select the ION API server ID you want to test.
Suite
Select the ION API suite that has the method you want to test.
HTTP Method
Select the appropriate HTTP method.
Method
Select the method you want to test.
Parameters
Optionally, specify any parameters required by the selected method, in JSON format.
Content Type
Specify type if one of the parameters is of type "body" or "form."
Timeout
Specify the maximum length of time, in milliseconds, to wait for the result to be returned. The default
is 10000 ms.
Use SSO
If the invoke method needs to authenticate with the user's Infor OS Portal SSO session credentials, select
this option.
3 
Click Invoke.
The application starts the test and returns any results to the Response section when the test is finished.
Importing an ION API server suite
1 
Open the ION API Servers form.
2 
Select the server into which the suite is to be imported.
3 
Click Import Suites.
4 
In the ION API Search Criteria dialog box, specify the Search Criteria.
To authenticate the search with Infor OS Portal SSO credentials, select the Use SSO option.
5 
Click Search.
6 
If more than one suite is returned, select one from the list and then click OK.
7 
Save.
The application imports the selected API server suite and adds it to the selected API server. You might need
to refresh the ION API Servers form to see the new suite in the display.
Infor Mongoose Administration Guide | 325
 
About integration with other applications

Importing an ION API method
1 
Open the ION API Server form.
2 
Select the server into which you want to import the API method.
3 
Click Suites.
4 
In the ION API Suites form, select the suite into which you want to import the method.
5 
Click Import Methods.
6 
In the ION API Search Criteria dialog box, specify the Search Criteria.
Note:  To authenticate the search with Infor OS Portal SSO credentials, select theUse SSO option.
7 
Click Search.
8 
If more than one method is returned, select one from the list, and then click OK.
9 
Save.
The application imports the selected API method and adds it to the selected API server suite. You might need
to refresh the ION API Suites form to see the new method in the display.
Infor Mongoose Administration Guide | 326
 
About integration with other applications

Chapter 21: Improving system performance
This group of topics presents information on how to improve the performance of your system. In general,
techniques for improving performance are designed to reduce unnecessary processing, network traffic, and
blocking.
The techniques minimize these factors:
•
The number of unneeded records stored in tables
•
The number of records retrieved in queries
•
The number of locks on records in queries
•
The duration of locks on records
•
The size and duration of transactions
•
The fragmentation of tables and indexes
•
Timeouts
•
The number of user authorizations required by inbound BOD transactions
Note:  Many topics in this group of topics apply only to on-premises environments. These are the sections
that also apply to cloud environments:
•
Centralized Vendor Metadata Database (CVMD) on page 328
•
Customizations on page 338
•
User actions on page 338
•
Replication on page 339
•
Locking and blocking on page 340, except for monitor blocking
Hardware
Note:  This topic applies only to on-premises environments.
You must have appropriate hardware to meet the demands put on your system. For minimum requirements,
consult your Customer Service Representative.
•
Server usage: See our recommended server usage in the Infor Mongoose Installation Guide.
•
Transaction log drives versus data drives: Use separate physical drives for the data and log files. Because
transaction logs are written sequentially, they require fewer dedicated drives than do data files. The
number of physical drives, capacity, and performance are more important for the data drives than for
the transaction log drives.
Infor Mongoose Administration Guide | 327
 
Improving system performance

SQL Server settings
SQL requirements are listed at the beginning of each section in this guide where appropriate. This section
includes some settings that can be used to help improve system performance. For more information, refer
to the SQL documentation.
Note:  This topic applies to only on-premises environments.
•
Auto shrink: On the application database machine, always have Auto shrink disabled for all databases.
If it is disabled, your system will not show significant performance loss related to shrinking the database.
If it is enabled, SQL Server checks every 30 minutes to see if it needs to shrink the database; this can cause
a huge performance hit. When you need to shrink databases, you can use the DBCC SHRINKDATABASE or
DBCC SHRINKFILE commands, or you can use the SQL Server Agent to schedule regular file-shrinking during
non-peak hours, instead of enabling Auto shrink.
•
Auto update statistics: We recommend that you enable Auto update statistics for all databases. This
feature is enabled by default. With this feature enabled, SQL Server updates the statistics of an index
based on this criteria:
•
If the number of rows in a table is greater than 6, but 500 or less, statistics are updated when there
have been 500 modifications made.
•
If the number of rows in the table is greater than 500, updates are made when 500 plus 20% of the
number of rows in the table have been modified.
When a SQL Server database is under a very heavy load, this feature can update the statistics during busy
times, causing a performance issue. If you find that enabling the feature causes more problems than it
solves, you can turn it off, and then manually update the statistics when the database has a lighter load.
We recommend that you both enable Auto update statistics and update statistics manually.
See Updating statistics on page 333.
•
Tempdb: Set the original size of the tempdb database files to a reasonable size to prevent the files from
automatically expanding as more space is needed. If the tempdb database expands too frequently,
performance can be affected.
•
To avoid the tempdb database files from growing by too small a value, set the file-growth increment
percentage to a reasonable size (10% is usually a good choice). If the file growth is too small compared
to the amount of data being written to the tempdb database, then tempdb might need to constantly
expand, thereby affecting performance.
•
To ensure good performance, place the tempdb database on a fast I/O subsystem.
•
For better performance, stripe the tempdb database across multiple disks.
•
Use filegroups to place the tempdb database on disks different from those used by user databases.
•
Minimum server memory and Maximum server memory: Set these values based on the size and activity
of your instance of SQL Server.
•
MAXDOP: Set the maximum degree of parallelism option to 8 or less by using sp_configure.
Centralized Vendor Metadata Database (CVMD)
You can reduce the database footprint and improve system performance by creating and implementing a
Centralized Vendor Metadata Database (CVMD). A CVMD reduces the amount of data required for upgrades
Infor Mongoose Administration Guide | 328
 
Improving system performance

by putting all the basic vendor metadata that ships with a Mongoose-based application into a single database.
Customer-generated data and metadata continue to exist in a separate database.
When a system is converted to be a CVMD setup, all requests for vendor-level data and metadata are redirected
to the CVMD, and that data/metadata in the original application database is then ignored. When being set up
for a multi-tenant cloud (MTC) user, this results in a much smaller customer database, because what exists
there is only the customer-generated data and metadata.
Although Infor initially created CVMDs primarily to reduce storage space demands for multi-tenant cloud
(MTC) environments, CVMDs can also be created and implemented in an on-premises installation environment.
Although this does not necessarily result in great disk storage savings for the on-premises customer, it does
reduce the time required for future upgrades.
The use of CVMDs results in each customer's/tenant's databases requiring less space than with a traditional
installation. Because of this, the total amount of storage space required for the MTC farm is greatly reduced.
(How much it is reduced is, of course, dependent on the number of tenants on the farm.) This also reduces
the time required for MTC upgrades, because the vendor-level metadata is updated only once for everyone,
and not repeatedly, for each individual tenant.
Finally, the maintenance window time required to perform the FormSync portion of upgrades, is also reduced,
which provides time-saving benefits for both MTC and on-premises customers.
For on-premises installations, the process to implement a CVMD environment involves these basic steps:
•
Create a CVMD to hold the master vendor data and metadata.
•
Then associate the CVMD with the Mongoose-based application.
The preferred method of creating a CVMD is by means of a CLI (Command Line Interface). However, CVMDs
can also be created, much like any other database, using the Infor Mongoose Configuration Wizard and
selecting the Express Configuration option.
When created, CVMDs contain all the schema for applications, objects, templates, and forms in an All-in-One
database. Each CVMD contains all this vendor metadata, as well as all language packs.
Creating a CVMD
Centralized Vendor Metadata Databases (CVMDs) are typically created and implemented by system
administrators for Multi-Tenant Cloud (MTC) environments. However, CVMDs can also be implemented and
can help improve system performance in on-premises installation environments as well.
CVMDs can be created, much like any other database, using the Infor Mongoose Configuration Wizard. If
you decide to create it using the Infor Mongoose Configuration Wizard, you must select the CVMD option
of the wizard, and then use the Express Configuration option.
You can also create the CVMD using a CLI (Command Line Interface). This topic provides the procedure to
create the CVMD using the CLI.
1 
On the application server, launch a Command Prompt window and navigate to the folder where the
Mongoose-based application is installed.
In a typical installation, this is C:\Program Files\Infor\appName\, where appName is the name of your
Mongoose-based application.
Infor Mongoose Administration Guide | 329
 
Improving system performance

2 
At the CLI prompt, enter this command:
infordbcl.exe createdb -databasename:CVMDname -product:appName -cvmd
where:
•
CVMDname is the name to assign to the CVMD database you are creating.
While the database name can be anything, as allowed by configuration naming standards, we suggest
naming it appname_CVMD for easy identification.
Note:  This is the naming convention that the Express Configuration option uses to name the new
database.
•
appName is the name of your Mongoose-based application.
As given so far, this is the command to use if you are using Windows authentication and not SQL Server
authentication. If you are using SQL Server authentication, these additional switches are required:
-username:SQLusername -password:SQLpassword -servername:SQLserverInstanceName
where:
•
SQLusername is the user ID for an authorized SQL user (such as 'sa').
•
SQLpassword is the login password for the user ID provided.
•
SQLserverInstanceName is the name of the SQL server instance in which the CVMD is to be created.
The system creates the CVMD database. The -cvmd option instructs the system to create the database with
all the required constituents.
Note:  This procedure does not extract, or remove, the vendor-level metadata from the tenant databases.
That is accomplished using a separate procedure. This procedure simply creates the CVMD. Once the CVMD
is created, you must still implement it. This can be done manually, using the Configuration Manager. If you
use the Express Configuration option, implementation is taken care of automatically.
Implementing a CVMD
You can reduce the database footprint and improve system performance by creating and implementing a
Centralized Vendor Metadata Database (CVMD). The CVMD is usually created using a Command Line Interface.
The CVMD can implemented (as with all databases) using the Configuration Manager, or it can be implemented
automatically in the Infor Mongoose Configuration Wizard.
Note:  This topic and procedure apply only to those who are authorized to use the Configuration Manager,
usually in an on-premises environment.
If you use the Express Configuration option in the Infor Mongoose Configuration Wizard, implementation
is accomplished automatically, and so, you do not need this procedure.
1 
Launch the Configuration Manager and select the Applications tab.
2 
Select the application you want to use with the CVMD, and click Edit.
3 
In the Edit Application dialog box, select the Advanced tab.
4 
In the Central Vendor Forms Metadata section, select the Enable option.
5 
In the Database Connection dialog box that opens, provide these values:
Infor Mongoose Administration Guide | 330
 
Improving system performance

User
Supply the user ID for a user who is authorized to log in and use the CVMD.
This user ID must have, at the least, read, execute, and View Definition SQL rights for the CVMD database.
Password
Supply the password required for the user identified to log in to the CVMD.
Server
Supply the name of the SQL instance in which the CVMD resides.
Database Name
Supply the name of the CVMD that was assigned when it was created.
Misc. Connect Info
Optionally, specify any keyword/value pair that modifies the connection string that is used to connect
to the database.
A connection string to a database consists of a series of keyword/value pairs, separated by semicolons,
for example:
Server=ServerName;Database=DatabaseName;User Id=User;Password=xxx
The application creates connection strings based on configuration information. However, there can be
situations where you might want to add a particular keyword/value pair to achieve a desired result. An
example is MultiSubnetFailover=True, which is required for SQL Server AlwaysOn configurations.
To use Microsoft Windows credentials for SQL Server authentication, specify ;Integrated Security=SSPI
for each database type. The IDO runtime must be set to a Windows user ID with SQL permissions.
Note:  Only use this field to add a particular keyword/value to every connection made by Mongoose.
6 
Click OK.
7 
Stop and restart the Infor Mongoose IDO Runtime service.
The application now pulls all requested vendor-level data and metadata from the CVMD and all other data
and metadata from the customer database.
Once the CVMD has been implemented, you can create additional databases that use it. See Creating customer
database to use with a CVMD in Infor Mongoose Administration Guide.
Creating customer databases to use with a CVMD
When using a Centralized Vendor Metadata Database (CVMD) setup, new customer databases should be
created that do not contain or use vendor-level data or metadata. You can create such databases using either
the Infor Mongoose Configuration Wizard or a Command Line Interface (CLI) command.
When using the Infor Mongoose Configuration Wizard, this is the procedure:
1 
Launch the Infor Mongoose Configuration Wizard.
2 
Select the Advanced Configuration option.
Infor Mongoose Administration Guide | 331
 
Improving system performance

3 
When you come to the page where you create and configure the databases, select the No Metadata
option.
4 
Complete the rest of the configuration as desired.
When using the CLI, this is the command:
Infordbcl.exe created -databasename:databaseName -product:Mongoose -allinone -nometadata
where databaseName is the name to be assigned to the new customer database.
Removing vendor-level data from a database
When converting your environment to use a Centralized Vendor Metadata Database (CVMD), you must first
create and implement the CVMD. Once that is accomplished, the vendor-level data and metadata can be
removed from the customer database.
To remove the vendor-level data and metadata, use a Command Line Interface (CLI). Navigate to the folder
where your Mongoose-based resides and use this command:
Infordbcl.exe removemetadata -config:databaseName 
where databaseName is the name of the database from which you want to remove the vendor-level data and
metadata.
SQL Server maintenance
SQL Server statistics that are out of date and tables and indexes that are significantly fragmented adversely
affect system performance. You can monitor their condition and take steps to enhance their performance.
Note:  This group of topics applies only to on-premises environments.
Statistical information
SQL Server uses statistical information about the distribution of values in a column to determine the optimal
strategy for evaluating a query. Distribution statistics help the system estimate how efficient an index would
be in retrieving data associated with a key value or range specified in the query.
Note:  This topic applies only to on-premises environments.
As the data in a column changes, index and column statistics can become out-of-date, affecting query
performance. The statistics should be refreshed anytime significant numbers of changes to keys occur in the
index.
We recommend that you update statistics nightly or weekly for best performance.
Infor Mongoose Administration Guide | 332
 
Improving system performance

See Updating statistics on page 333.
You can use the dbcc show_statistics statement to generate a report on the distribution statistics for an
index. The statements in this section use this syntax:
dbcc show_statistics (table_name, index_name)
The results indicate the selectivity of an index (the lower the density returned, the higher the selectivity) and
provide the basis for determining whether an index is useful in optimizing queries.
For dbcc show_statistics and other DBCC (Database Console Commands) statements, see the SQL Server
help.
Updating statistics
This topic describes when to update statistics and provides an example.
Note:  This topic applies only to on-premises environments.
Use the Transact-SQL statement UPDATE STATISTICS if these events occur:
•
A process suddenly takes much longer than usual to run.
•
There is a significant change in the key values in an index.
•
A large amount of data in an indexed column has been added, changed, or removed, or the table has
been truncated using the TRUNCATE TABLE statement and then repopulated.
We recommend that you update statistics nightly or weekly.
This example updates the statistics for all indexes on the customer table:
UPDATE STATISTICS customer
To update statistics for all tables in the in the current database, you can run the SQL Server stored procedure
sp_updatestats, which uses UPDATE STATISTICS:
EXEC sp_updatestats
For more information, see SQL Server help for UPDATE STATISTICS and sp_updatestats.
Fragmentation information
Fragmentation occurs through data modifications (INSERT, UPDATE, and DELETE operations). For queries
that scan part or all of a table, this fragmentation can cause additional pages to be read, adversely affecting
performance.
Note:  This topic applies only to on-premises environments.
Infor Mongoose Administration Guide | 333
 
Improving system performance

You can use the Transact-SQL DBCC SHOWCONTIG statement to display fragmentation information for the data
and indexes of a specified table.
To determine whether a table is heavily fragmented, use this command in SQL Server Management Studio,
with the application database selected as the current database:
DBCC SHOWCONTIG (table_name)
In the result set, the value of Logical Scan Fragmentation gives an indication of the table's fragmentation
level. The value should be close to zero, although a value from 0% through 10% might be acceptable.
To show in a grid an abbreviated result set for every index on every table, use this command:
DBCC SHOWCONTIG WITH TABLERESULTS, FAST
To show the full result set for every index on every table, use this command:
DBCC SHOWCONTIG WITH TABLERESULTS, ALL_INDEXES
For more information, see SQL Server help for DBCC SHOWCONTIG.
Defragmenting indexes
We recommend that you rebuild your table indexes on a weekly basis if possible.
Note:  This topic applies only to on-premises environments.
The Transact-SQL DBCC INDEXDEFRAG statement defragments indexes of a specified table, improving
index-scanning performance.
DBCC INDEXDEFRAG (database_name, table_name, index_name)
The script below uses DBCC INDEXDEFRAG and DBCC SHOWCONTIG to defragment all indexes in a database
fragmented above a declared threshold of 30 percent. The script is from Microsoft’s Transact-SQL Reference,
copyright © 2004 Microsoft Corporation.
Note that you must specify a database before you run the script.
/*Perform a 'USE <database name>' to select the database in which to run the script.*/
-- Declare variables
SET NOCOUNT ON
DECLARE @tablename VARCHAR (128)
DECLARE @execstr  VARCHAR (255)
DECLARE @objectid INT
DECLARE @indexid  INT
DECLARE @frag   DECIMAL
DECLARE @maxfrag  DECIMAL
-- Decide on the maximum fragmentation to allow
SELECT @maxfrag = 30.0
-- Declare cursor
DECLARE tables CURSOR FOR
Infor Mongoose Administration Guide | 334
 
Improving system performance

  SELECT TABLE_NAME
  FROM INFORMATION_SCHEMA.TABLES
  WHERE TABLE_TYPE = 'BASE TABLE'
-- Create the table
CREATE TABLE #fraglist (
  ObjectName CHAR (255),
  ObjectId INT,
  IndexName CHAR (255),
  IndexId INT,
  Lvl INT,
  CountPages INT,
  CountRows INT,
  MinRecSize INT,
  MaxRecSize INT,
  AvgRecSize INT,
  ForRecCount INT,
  Extents INT,
  ExtentSwitches INT,
  AvgFreeBytes INT,
  AvgPageDensity INT,
  ScanDensity DECIMAL,
  BestCount INT,
  ActualCount INT,
  LogicalFrag DECIMAL,
  ExtentFrag DECIMAL)
-- Open the cursor
OPEN tables
-- Loop through all the tables in the database
FETCH NEXT
  FROM tables
  INTO @tablename
WHILE @@FETCH_STATUS = 0
BEGIN
-- Do the showcontig of all indexes of the table
  INSERT INTO #fraglist 
  EXEC ('DBCC SHOWCONTIG (''' + @tablename + ''') 
   WITH FAST, TABLERESULTS, ALL_INDEXES, NO_INFOMSGS')
  FETCH NEXT
   FROM tables
   INTO @tablename
END
-- Close and deallocate the cursor
CLOSE tables
DEALLOCATE tables
-- Declare cursor for list of indexes to be defragged
DECLARE indexes CURSOR FOR
  SELECT ObjectName, ObjectId, IndexId, LogicalFrag
  FROM #fraglist
  WHERE LogicalFrag >= @maxfrag
   AND INDEXPROPERTY (ObjectId, IndexName, 'IndexDepth') > 0
-- Open the cursor
OPEN indexes
-- loop through the indexes
FETCH NEXT
  FROM indexes
  INTO @tablename, @objectid, @indexid, @frag
WHILE @@FETCH_STATUS = 0
BEGIN
  PRINT 'Executing DBCC INDEXDEFRAG (0, ' + RTRIM(@tablename) + ',
   ' + RTRIM(@indexid) + ') - fragmentation currently '
    + RTRIM(CONVERT(varchar(15),@frag)) + '%'
  SELECT @execstr = 'DBCC INDEXDEFRAG (0, ' + RTRIM(@objectid) + ',
    ' + RTRIM(@indexid) + ')'
  EXEC (@execstr)
Infor Mongoose Administration Guide | 335
 
Improving system performance

  FETCH NEXT
   FROM indexes
   INTO @tablename, @objectid, @indexid, @frag
END
-- Close and deallocate the cursor
CLOSE indexes
DEALLOCATE indexes
-- Delete the temporary table
DROP TABLE #fraglist
GO
Unneeded data
Unneeded data in tables with a large number of records can increase query time and slow certain processes.
Mongoose provides utilities for reducing unneeded data. SQL Server system stored procedures aid in
understanding table size.
Note:  This topic applies to only on-premises environments.
Purging or compressing unneeded data
These forms allow you to purge or compress data to improve performance. Determining when to use these
forms is primarily a business decision you need to make. We have made recommendations for some listed
below. For information about how to use individual forms, refer to the online help.
It is important to formulate a data retention plan for each area. You should decide how long to retain data
and who will purge or compress records that are older than the planned retention period.
•
Purge BOD Information
•
Replication Document Inbox/Outbox Utilities
•
Delete Audit Logs
•
Audit Log Types: Look at the types you have on this form and verify that you need all the ones you have
created. Types 1 through 6 are standard default types, and you cannot delete them. All other types (10,000
and above) are custom types created by you. You can generally delete these types.
Examining table size
The SQL Server system stored procedure sp_spaceused reports information about a table that can be useful
in forming and implementing a data retention plan.
Note:  This topic applies only to on-premises environments.
The stored procedure shows this information:
•
Number of rows in a table
•
Space reserved for a table
•
Space used by data in a table
•
Space used by the index in a table
Infor Mongoose Administration Guide | 336
 
Improving system performance

•
Unused space in a table
In SQL Server Management Studio, with the Mongoose application database selected as the current database,
use this syntax to generate a report on a table:
EXEC sp_spaceused table_name
For example:
EXEC sp_spaceused ledger
To report on tables that are likely to need attention in a data retention plan, you can use this script:
-- Audit Logs
EXEC sp_spaceused AuditLog
To report on all tables in the database, you can use this script:
DECLARE @table_name sysname
DECLARE Tables_Cursor CURSOR FOR
SELECT name 
FROM sysobjects
WHERE type = 'U' ORDER BY 1
OPEN Tables_Cursor
FETCH NEXT FROM Tables_Cursor
INTO @table_name
WHILE @@FETCH_STATUS = 0
BEGIN
  EXEC sp_spaceused @table_name
  FETCH NEXT FROM Tables_Cursor
  INTO @table_name
END
CLOSE Tables_Cursor
DEALLOCATE Tables_Cursor
To select tables with similar names, modify the WHERE clause in the script. For example, to report only on
tables with the _all suffix, replace this clause:
WHERE type = 'U' ORDER BY 1
With this clause:
WHERE type = 'U' AND name LIKE '%[_]all' ORDER BY 1 
For more information about sp_spaceused, see the SQL Server help.
Infor Mongoose Administration Guide | 337
 
Improving system performance

Customizations
Customizations to Mongoose should be evaluated for performance along with standard product components.
You should ensure that indexes for new tables are designed correctly and maintained adequately. If a custom
feature performs slower than when it was first implemented, determine whether unneeded records are
causing the performance reduction.
Custom reports and processes should be evaluated to see if they are reading the least number of records.
User actions
The practices of en users can slow Mongoose performance. Actions such as querying an unlimited number
of records into a form, specifying overly broad query criteria in reports, and running unneeded reports increase
network traffic and can tax database resources.
The application offers a number of different options to limit the number of records or tasks that end users
can access. For more information, see topics related to record caps and report tasks.
Reducing the number of rows returned in queries
You can choose to retrieve all rows or any specified maximum number of rows in queries. This option overrides
the system default limit that is set on queries that return data records and items in drop-down lists. However,
unlimited queries can degrade system performance or exceed the resources of the application server and
the client machine.
Regardless of the option you select, record retrieval is subject to any system-wide record cap overrides that
might be in effect. See About caps.
Reducing the scope of reports
Report users should be sure to set criteria in a such way that the system returns only the information needed
for the purposes of the report. Users should limit the range of time frames and other criteria to prevent
needless processing.
Users should avoid running unnecessary reports.
Authorized administrators can limit the number of reports that can be generated at one time using appropriate
record/report caps. See About caps.
Infor Mongoose Administration Guide | 338
 
Improving system performance

Replication
When you use replication, you must set up an intranet with a master site and share certain _all tables. This
allows other sites to use views into shared _all tables on the master site, reducing replication traffic between
the sites.
Simplifying user security role checks for inbound BODs
The processing of inbound BODs (Business Object Documents) often requires that checks be performed for
all user security roles associated with the BOD. There can be dozens of security roles for each user, so these
security role checks can take a significant amount of time to complete.
We now have a process that can delegate all such processing to a single C# entry point. This can greatly reduce
the amount of time required to process inbound BODs.
1 
Open the Replication Documents form and retrieve all replication documents.
2 
Select the record for SecurityUserMaster.
This is the legacy version. In this procedure, we remove the inbound references to this document.
3 
Click Inbound Cross-Refs.
4 
Delete the SecurityUserMaster record.
5 
Save and close the linked form.
6 
Back in the Replication Documents form, select the record for SecurityUserMaster2.
7 
Click Inbound Cross-Refs.
8 
Create a new inbound cross reference with these specifications:
BOD Noun
Enter: SecurityUserMaster
BOD Verb
Enter: Sync
9 
Save and close the linked form.
To enable this feature, the old replication document needs to be disassociated and the new replication
document needs to be associated.
Reverting to legacy security role checks for inbound BODs
The processing of inbound BODs (Business Object Documents) often requires that checks be performed for
all user security roles associated with the BOD. There can be dozens of security roles for each user, so these
security role checks can take a significant amount of time to complete. There is a process in place to simplify
these user security role checks (see Simplifying user security role checks for inbound BODs on page 339.
In cases where this simplified procedure is in use, but where you want to revert to the legacy process, you
can do so, using this procedure:
Infor Mongoose Administration Guide | 339
 
Improving system performance

1 
Open the Replication Documents form and retrieve all replication documents.
2 
Select the record for SecurityUserMaster2.
3 
Click Inbound Cross-Refs.
4 
Delete the SecurityUserMaster record.
5 
Save and close the linked form.
6 
Back in the Replication Documents form, select the record for SecurityUserMaster.
7 
Click Inbound Cross-Refs.
8 
Create a new inbound cross reference with these specifications:
BOD Noun
Enter: SecurityUserMaster
BOD Verb
Enter: Sync
9 
Save and close the linked form.
Locking and blocking
This topic describes locking and blocking and their effect on system performance.
Locking prevents users from reading data being changed by other users, and prevents multiple users from
changing the same data at the same time. If locking is not used, data within the database can become logically
incorrect, and queries performed against that data can produce unexpected results. SQL Server enforces
locking automatically. Locking can occur at record, page, or table level.
Blocking occurs when one user holds a lock and a second user requires a conflicting lock type. This forces
the second user to wait, blocked by the first. Typically, the second user sees an hourglass while trying to
process or save records. Most blocking problems happen because a single process holds locks for an extended
period of time, causing a chain of blocked processes. A design goal is to minimize the amount of time a record
is locked to reduce the potential blocking of another user.
A deadlock arises when two processes have data locked, and each process cannot release its lock until the
other process has released its lock. SQL Server rolls back one of the transactions and then allows the other
transaction to continue.
Saving each modified row in a separate transaction
You can specify that the application save each modified row in a separate transaction. By default, without
this setting, all modified rows are sent to the middle tier to be processed within a single transaction.
Saving one row per transaction can alleviate blocking problems in some forms. Whether the setting enhances
performance depends on the complexity of a form's save operation and the speed of the network connection.
Forms that require highly complex saves may benefit from the setting, especially if connection speed is
Infor Mongoose Administration Guide | 340
 
Improving system performance

adequate. Forms that require simple save operations are less likely to benefit, especially if the additional
network traffic required in saving one row at a time slows the system.
The default behavior is that, if one row fails, the entire transaction is rolled back. When you save each row in
a separate transaction and a row fails, all previous rows remain committed.
Note:  If your form design requires that all modified rows be committed as a unit, with processing on the unit
before and/or after a save operation, saving rows in separate transactions might not be appropriate. If an
error occurs on a row, some rows might be committed and others not committed.
For instructions on setting this feature, see the topic on "Saving One Row Per Transaction".
Setting the collection read mode (transaction isolation level)
You can specify whether form queries read committed or uncommitted data by setting the Collection Read
Mode process default. This setting applies to queries that load primary collections, secondary collections,
and lists, and to in-collection validations. It also applies to background-task queries that generate reports
and to background-task stored procedures. The setting does not affect SQL SELECT operations or other
processing coded in stored procedure (method) calls. The default Collection Read Mode value, UNCOMMITTED,
corresponds to the Transact-SQL statement SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED.
With the UNCOMMITTED setting, which allows the reading of uncommitted data, users do not have to wait for
other long-running transactions that access the same dataset to complete before their queries can complete.
With the COMMITTED setting, a query reads committed data and returns only data for which the query can get
a shared lock.
This base, system-wide transaction isolation level (Collection Read Mode) is set on the Process Defaults
form. Note that if this setting is unsuitable for all forms and tasks, you can override it for selected forms and
tasks. You can set the isolation level for individual reports and stored procedures on the Background Task
Definitions form. You can also override the system setting at the form level in the Web designer. for information
about setting the read mode for a collection and about setting transaction isolation levels, see About
transaction isolation levels on page 315.
Windows tools
Memory - Use Perfmon to determine memory usage and to determine if expansion is needed. If additional
memory is used as AWE memory, verify in the SQL Server error log that the statement "Address Windowing
Extensions enabled" exists.
Note:  This topic applies only to on-premises environments.
Infor Mongoose Administration Guide | 341
 
Improving system performance

SQL Server stored procedures and commands
The items in this section can be used to return information related to performance.
Note:  This topic applies only to on-premises environments.
•
sp_who and sp_who2: The stored procedure sp_who shows what SPID is blocked. sp_who2 shows who is
blocking.
•
sp_helpindex (table_name): Gives index information for a table.
•
DBCC OPENTRAN: Determines whether an open transaction exists within the log.
•
DBCC INPUTBUFFER (SPID): Displays the last statement sent from a client to SQL Server.
•
DBCC Trace On: Enables specified trace flags.
Printing barcode reports in PDF format
After you install barcode fonts on the application server, restart the server so that the fonts appear properly
in PDF format on barcode reports.
Note:  This topic applies only to on-premises environments.
Troubleshooting timeout errors
Note:  This topic applies only to on-premises environments.
If you receive timeout errors when executing long-running processes, use this topic as a troubleshooting
guide. The items listed are listed in the order you should check them. It is a good idea to keep track of the
original settings so you can change them back if you need or want to.
1 
Edit the Query Timeout value in the Configuration Manager:
a 
On the application server, select Start > All Programs > Infor > Tools > Configuration Manager.
b 
Select the configuration and click Edit.
c 
For the application database, ensure Query Timeout is set to 0 to make the timeout unlimited.
2 
On the SQL Server (database server), set the Query Wait value:
a 
Open SQL Server Management Studio and log in.
b 
Right-click on your SQL Server and select Properties.
c 
Click Advanced.
d 
Set the value of Query Wait to 0 to make the timeout unlimited.
3 
Set the MSDTC Transaction Timeout value:
a 
On the application server, select Start > All Programs > Administrative Tools > Component Services.
b 
Expand until you find My Computer.
c 
Right-click on My Computer and select Properties.
Infor Mongoose Administration Guide | 342
 
Improving system performance

d 
Click the Options tab and set Transaction Timeout value to 0.
4 
Set the httpRuntime executionTimeout value in the web.config file on the application server where IIS is
running:
a 
In C:\Inetpub\wwwroot\IDORequestService, find the web.config file. This file is delivered with Mongoose.
b 
Open the file and look for this section:
<httpRuntime executionTimeout="number" maxRequestLength="16384"/>
c 
Change number to 7800, which is the maximum.
5 
Set the machineSettings maxTimeout value in the machine.config file on the application server:
a 
In C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727\CONFIG, find the machine.config file. This file is
not delivered with Mongoose.
b 
Open the file and find the </configuration> element. Just to the left of it, add this section of code,
which changes the timeout value to 2 hours:
<system.transactions>
<defaultSettings distributedTransactionManagerName="" timeout="02:00:00"/>
<machineSettings maxTimeout="02:00:00"/>
</system.transactions>
Infor Mongoose Administration Guide | 343
 
Improving system performance

