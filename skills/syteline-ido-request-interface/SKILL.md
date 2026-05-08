---
name: "syteline-ido-request-interface"
description: "IDO request interface overview and XML schema - OpenSession, LoadCollection, UpdateCollection, Invoke, CloseSession, wire taps, permissions, licensing"
version: "1.0"
tags:
  - syteline
  - ido
  - xml
  - api
  - mongoose
---

# syteline-ido-request-interface

Source: Infor Mongoose IDO Integration with External Applications Guide, Release 2026.x


Infor Mongoose IDO Integration with External Applications Guide | 6

About the IDO request interface

Chapter 1: About the IDO request interface

For Mongoose applications, the Intelligent Data Object (IDO) layer resides on the application server, in between
the clients and the database. An IDO consists of references to tables in the Application database, definitions
of properties, and definitions of specific methods.
IDO definitions are stored as metadata in an Objects database and are accessed through IDO editing forms.
Through configurations, application databases are linked with an Objects database and a Forms database.
Optionally, the objects and forms databases are embedded in the application database.
Developers can create logic associated with IDOs using the Application Event System (AES) and can incorporate
.NET IDO extension class code as well; but the Mongoose framework implements the IDO request interface
for all IDOs. This interface consists of these session and metadata operations:
•
•
•

Load Collection generates and executes SQL to retrieve a collection of rows from the database.
Update Collection takes a set of rows marked for insert, update, or delete, and generates and executes
the appropriate SQL code against the database.
Invoke allows you to execute a specific method defined for the IDO.

Each of the operations in the IDO request interface consists of a request and a response. The caller builds an
IDO request and sends the request to the IDO runtime engine to be executed. The IDO runtime engine builds
and returns a response to the caller that contains the results of the requested action.
The Mongoose user interface runs entirely by means of the IDO request interface, by using the IDO request
XML schema with the use of HTTP(s).
Integrating to IDOs involves understanding this IDO request interface and leveraging this interface using the
variety of technologies, such as REST, SOAP, .NET class library, XML or HTTP. The REST version of the IDO
request interface is also provided in Infor's ION API repository.
To know how to integrate with IDOs, you must first understand the IDO request XML schema, as this schema
is the native Mongoose interface. The other technologies all map to or from this interface.

About the IDO Runtime Service
The application server for Mongoose is the IDO runtime, which is usually run as a service. Though for
development, IDO runtime can also be run as an executable file that has its own user interface, named the
IDO Runtime Development Server or IDORuntimeHost.exe.

Infor Mongoose IDO Integration with External Applications Guide | 7

About the IDO request interface
This service has a front-end in Internet Information Services (IIS) and is the code that implements the IDO
request interface.
Integrating with IDOs involves establishing communications to the IDO runtime through a URL and one of
the technology options.

Pinging the IDO Runtime Service
There have been cases where the IDO Runtime Service has been unavailable while the IDO Request Web
Service has been available. To detect whether this situation is occurring on your system, you can perform a
quick check of the IDO Runtime Service through the IDO Request Web Service.
This mechanism can be used to check a server instance that is running Internet Information Services (IIS),
the IDO Request Web Service, and the IDO Runtime Service:
1
2

Open a web browser.
In the browser's address field, specify this URL:
https://IDOServerName/IDORequestService/Ping.aspx

where IDOServerName is the domain name or the IP address of the web server that is hosting the IDO
Request Web Service.
If IIS is running and the IDO Request Web Service is available, and the IDO Runtime Service is running, then
the system returns a 200 - Success message.
If IIS is running and the IDO Request Web Service is available, but the IDO Runtime Service is not running, the
system returns a 503 - Service Unavailable error message.
If either IIS is not running or the IDO Request Web Service is unavailable, the system returns a 404 - File Not
Found error message. In this case, it does not matter whether the IDO Runtime Service is running or not.

Accessing the IDO request interface
To access the IDO request interface, Mongoose provides these technology options:
•
•
•
•

IDO request XML schema, using HTTP(s)
REST API, including ION API
SOAP web service
Mongoose .NET client class library

Infor Mongoose IDO Integration with External Applications Guide | 8

About the IDO request interface

Permissions and licensing for the IDO request interface
Sessions established with the Mongoose user interface involve a different model for permissions and licensing
than sessions established programmatically using the IDO request interface.
For UI sessions, the user account must be granted authorizations or permissions, for each form the user
attempts to run. Authorizations are granted at a group or user level in the Groups or Users form. Authorizations
can also include policy as to which operations are enabled, and even field-level permissions. Similarly, the
users must be granted at least one license module that contains the form that must be run, unless the
Usage-Tracking-Only mode of licensing is in effect.
By contrast, for sessions created through an API, the user account permissions and licensing are checked
against the IDOs being accessed, and not the forms. So, the user account for the session must be granted
with permissions to the IDOs for which LoadCollection, UpdateCollection, and Invoke requests are made.
Those permissions might further restrict which operations that user account can use for various IDOs, as well
as property-level restrictions. Finally, the user account must be granted with at least one license module
which includes the IDO being accessed, unless the Usage-Tracking-Only mode of licensing is in effect.
Note: When the REST option is used within the ION API, the user account is drawn from the browser, and
single-sign-on occurs. The resulting user account, however, still requires IDO-level permissions and licensing.
To know more about configuring permissions and licensing, see the online help for the Users and Groups
forms.

Infor Mongoose IDO Integration with External Applications Guide | 9

About the IDO request XML schema

Chapter 2: About the IDO request XML schema

The IDO request XML schema is the native Mongoose format for the IDO request interface. The Mongoose user
interface uses this transport format for all aspects of the execution of the UI. The other options provided for
the IDO request interface, such as REST, SOAP, and .NET class library, are mappings to and from this protocol.
Understanding the IDO request XML schema is helpful even if you are leveraging other options, because the
schema shows all the actual capabilities. You can wiretap a session in your application and see this format
of the traffic between the client and application server. You can also use this format directly, by sending and
receiving these XMLs to the Mongoose web server from custom client code.

About the IDO request XML headers
The IDO request XMLs include requests and responses for the key operations, as well as session logon and
logout provided by the IDO request interface.
This list shows the available IDO request XML headers:
•
•
•
•
•

OpenSession
LoadCollection
UpdateCollection
Invoke
CloseSession

OpenSession
An OpenSession request/response document validates the user’s identity, creates a new session in the
application, and returns a session ID. This is essentially the same as the user logging into the application
where the caller can communicate with the IDO.
You must submit an OpenSession request before you can submit other requests.

Infor Mongoose IDO Integration with External Applications Guide | 10

About the IDO request XML schema
Request data
An OpenSession request document has this payload structure that contains data, such as the user’s login
information (user ID, password, configuration name). These data are required to complete the request.
<RequestData>
<UserID>userid</UserID>
<LanguageID>culture</LanguageID>
<ConfigName>config</ConfigName>
<MachineName>machinename</MachineName>
<DomainUserName>domainusername</DomainUserName>
<ApplicationName>appname</ApplicationName>
<AllowCloseExistingSessions>true|false</AllowCloseExistingSessions>
<Password Encrypted="N">password</Password>
<Workstation>workstation</Workstation>
</RequestData>

Response data
An OpenSession response document has this payload structure and includes requested data and return
values.
<ResponseData>
<UserID>userid</UserID>
<LanguageID>culture</LanguageID>
<ProductVersion>version</ProductVersion>
<DeadlockRetry>0</DeadlockRetry>
<License Status="status">
<Message>message</Message>
</License>
<RegionalSettings MessageLanguageID="langID" LocaleID="localeID" DecimalSeparator="dec-sep"
DigitGroupSeparator="dig-sep" DigitsInGroup="dig">regional-settings</RegionalSettings>
<AdditionalFailureInformation>failureinfo</AdditionalFailureInformation>
<ServerDate>serverdatetime</ServerDate>
<LoginResult>loginstatus</LoginResult>
<PrimaryGroupName>group</PrimaryGroupName>
<DaysUntilPasswordExpires>days</DaysUntilPasswordExpires>
<EditLevel>editlevel</EditLevel>
<SuperUser>su-indicator</SuperUser>
<StartupMethods>startup-method-spec</StartupMethods>
<AuditingEnabled>true|false</AuditingEnabled>
</ResponseData>

Element descriptions
Element

Description

AdditionalFailureInformation

If the login fails, this element can contain additional information about
why.

AllowCloseExistingSessions

Optional.
If this is True, existing sessions can be closed if the concurrent login
limit prevents user from logging in.
Default = False

ApplicationName

Optional.
This is the name of the application being used for the session.
This element is used primarily for diagnostics.

AuditingEnabled

This element is for internal use only.

Infor Mongoose IDO Integration with External Applications Guide | 11

About the IDO request XML schema

Element

Description

ConfigNamne

This is the name of the configuration where the session runs.
You can get a list of configurations by using this in the request.

DaysUntilPasswordExpires

This is the number of days until the specified user’s current password
expires.

DeadlockRetry

This is no longer used.

DomainUserName

Optional.
This is the client’s NT user name.
This element is used primarily for diagnostics.

EditLevel

This is the level of editing permission assigned to the user, as specified
on the Users form:
•
0 = None
•
1 = Basic
•
2 = Full User
•
3 = Site Developer
•
4 = Vendor Developer
For information about the particulars of these editing/permission
levels, see the online help topic, "Editing permissions".

LanguageID

Optional.
This is the culture value being used for the session. This corresponds
to a value in the LanguageIDs table.
Default = en-US

License

If the login is successful, this indicates the status of the application
database’s license (VALID or INVALID). If INVALID, the message contains
additional info, such as when the license expired.

Infor Mongoose IDO Integration with External Applications Guide | 12

About the IDO request XML schema

Element

Description

LoginResult

This is the result of the specified user’s login attempt. These are the
possible values:
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

Success
InvalidCredentials
AccountDisabled
AccountLocked
PasswordExpired
PasswordWillExpire
SessionLimit
ConcurrentSessionLimit
InvalidConfiguration
UnknownFailure
Note: This response should never present.

•

LicenseInconsistency
Note: This response indicates that someone has tampered with
the licensing.

MachineName

Optional.
This is the name of the computer being used for the session.
This element is used primarily for diagnostics.

Password

This is the user’s password.
If Encrypted = Y, the password is encrypted. However, Encrypted is
usually set to N.

PrimaryGroupName

This is the name of the primary group defined for the user as specified
on the Users form.

ProductVersion

This is the version of the application.

RegionalSettings

This is the regional settings and locale settings used for this session.
This information comes from the LanguageIDs table.

ServerDate

This is the date on the Application server.
The CURDATE() and CURTIME() keywords must yield values that match
the server for the application, not the client machine.
This value is used to compute the difference between the client machine’s date and time and the Application server’s date and time. This
is done so that each time CURDATE() and CURTIME() are evaluated,
Mongoose can produce the correct server-based result.

StartupMethods

This element is for internal use only.

Infor Mongoose IDO Integration with External Applications Guide | 13

About the IDO request XML schema

Element

Description

SuperUser

This element indicates whether the specified user is a "super user".
•
1 = Yes
•
0 = No
For more information about super users, see the online help topic,
"Super User".

UserID

This is the user's ID, which must be defined for the specified configuration.

Workstation

Optional.
This element is for internal use only.

Examples for OpenSession
This example request opens a session using the configuration MG_DEV for a user whose ID is jdelacruz.
<IDORequest ProtocolVersion="6.03" SessionID="">
<RequestHeader Type="OpenSession">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<UserID>jdelacruz</UserID>
<LanguageID />
<PrefsLanguageID />
<ConfigName>MG_DEV</ConfigName>
<AllowCloseExistingSessions>true</AllowCloseExistingSessions>
<Password Encrypted="N">Passwe1rd</Password>
<Workstation />
<Passcode />
<TrustedClient>false</TrustedClient>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains the session ID which can be used for submitting other requests.
<IDOResponse ProtocolVersion="6.03" SessionID="04269518-2d4b-4b13-938b-65ed2f561c53">
<ResponseHeader Type="OpenSession">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<UserID>sa</UserID>
<LanguageID />
<PrefsLanguageID />
<ProductVersion>10.08.00</ProductVersion>
<PendoGuidesAPIKey />
<FormsVersion />
<License Status="VALID">
<Message />
<BusinessLogicalID>default</BusinessLogicalID>
</License>
<RegionalSettings MessageLanguageID="1033" LocaleID="1033" DecimalSeparator="." Digit

Infor Mongoose IDO Integration with External Applications Guide | 14

About the IDO request XML schema
GroupSeparator="," DigitsInGroup="3">
<StringTableName>Strings</StringTableName>
<LanguageSubDir>en-US</LanguageSubDir>
<HelpSubDir>en-US</HelpSubDir>
<LanguageCode>ENU</LanguageCode>
</RegionalSettings>
<LastChangeDateIDOMetadata>0x00000000000278d0</LastChangeDateIDOMetadata>
<LastChangeDateFormsMetadata />
<AdditionalFailureInformation />
<ClientRetryMinutes>0</ClientRetryMinutes>
<ServerDate>20190813 10:12:17.203</ServerDate>
<LoginResult>Success</LoginResult>
<PrimaryGroupName />
<DaysUntilPasswordExpires>2147483647</DaysUntilPasswordExpires>
<UserDesc />
<TokenAuthenticationMessage />
<HostEnvironment>InforPAAS</HostEnvironment>
<EditLevel>4</EditLevel>
<SuperUser>1</SuperUser>
<UserLanguageID />
<StartupMethods>AccessAs.StartupMethodSp(RVAR V(Parm_Site),RVAR V(Parm_DisplayReportHead
ers),RVAR V(AccessAs),RVAR V(Parm_DefaultStartingToEnding))</StartupMethods>
<AuditingEnabled>false</AuditingEnabled>
</ResponseData>
</ResponseHeader>
</IDOResponse>

LoadCollection
A LoadCollection request/response document uses the LoadCollection method of an IDO to query either an
IDO collection or a database table and return the results to the user.
Request data
A LoadCollection request document has this payload structure and contains data such as the collection name,
property list, and so on, which are required to complete the request.
<RequestData>
<LoadCollection Name="collection" LoadCap="loadcap">
<LoadType>loadtype</LoadType>
<ReadMode>readmode</ReadMode>
<Cursor />
<PropertyList>
<propertyname>property01</propertyname>
<propertyname>property02</propertyname>
<propertyname>property...</propertyname>
</PropertyList>
<RecordCap>recordcap</RecordCap>
<Filter>filter</Filter>
<OrderBy>orderby</OrderBy>
<PostQueryCmd>command</PostQueryCmd>
</LoadCollection>
</RequestData>

Resonse data
A LoadCollection response document has this payload structure and includes requested data and return
values.
<ResponseData>
<LoadCollection Name="collection" LoadCap="loadcap">

Infor Mongoose IDO Integration with External Applications Guide | 15

About the IDO request XML schema
<PropertyList>
<propertyname>property01</propertyname>
<propertyname>property02</propertyname>
<propertyname>property...</propertyname>
</PropertyList>
<LoadType>loadtype</LoadType>
<RecordCap>recordcap</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="itemid01">
<P>propertyvalue</P>
<P>propertyvalue</P>
<P>propertyvalue</P>
</Item>
<Item ID="itemid02">
<P>propertyvalue</P>
<P>propertyvalue</P>
<P>propertyvalue</P>
</Item>
<Item ID="itemid...">
<P>propertyvalue</P>
<P>propertyvalue</P>
<P>propertyvalue</P>
</Item>
</Items>
<FilterIDO />
<MoreItems>moreitems</MoreItems>
</LoadCollection>
</ResponseData>

Element descriptions
Element

Description

Cursor

The response data can include a cursor value that is a "bookmark" in
the collection, to tell where the retrieval stopped. If you want to make
multiple requests using NEXT or PREV to retrieve data, include a single
<cursor
/> element in your FIRST request.
The response document provides a cursor value. Include the cursor
value of the response document (as <cursor>value</cursor>) in the
NEXT request document, so the system can tell where to start the next
retrieval request.
Continue updating the cursor value as you provide additional NEXT or
PREV requests.
A request of LAST should use an empty <cursor
/> element.

Distinct

Retrieves a set of data representing only the distinct combinations of
properties requested.
For example:
<LoadCollection Name="MGCore.Users">
<LoadType>FIRST</LoadType>
<PropertyList>
<UserID />
</PropertyList>
<Distinct />
</LoadCollection>

Infor Mongoose IDO Integration with External Applications Guide | 16

About the IDO request XML schema

Element

Description

Filter

This element restricts the result set and corresponds to the FILTER or
FILTERPERM keyword in Design Mode. You can use simple expressions,
such as property names, comparison operators, Boolean operators,
and so on.
Some examples:
<Filter>CoNum like 'ABC%'</Filter>
<Filter>QtyOnHand < 20 AND Whse = 'OH'</Filter>

Not allowed are things like subqueries, function calls, or anything else
that could be used for an injection attack.
For cloud environments, filters are always validated in the cloud. For
on-premises installations, you can disable validation using the Filter
Validation process default.
In the response document, this element contains any IDO-level filter
specifications that were used.
Item

The ID attribute contains a value used within the system to identify
the item.

LinkBy

This element is similar to the LINKBY keyword. The information within
this element tells how the child collection is linked to the parent collection.
For example:
<LinkBy>
<PropertyPair Child="Item" Parent="Item" />
<!-- Optional: More sets of PropertyPair tags -->
</LinkBy>

LoadCollection

This element has these possible attributes:
•
Name - This is the name of the collection to be loaded (for example, GroupNames)
•
LoadCap - This attribute applies only to subcollections. This is
the maximum number of subcollections to include in the returns.
These are the valid values for this attribute:
•
•
•

-1 - Includes all subcollections
1 - (Default) Only queries the subcollection records for the

first parent item.
Any other number - Specifies the maximum number of subcollections to retrieve.

Infor Mongoose IDO Integration with External Applications Guide | 17

About the IDO request XML schema

Element

Description

LoadType

This element specifies one of these types:
•
FIRST
•
NEXT
•
PREV
•
LAST
The first time you submit a LoadCollection request, it must be called
FIRST or LAST. For subsequent requests, you can use NEXT or PREV.

OrderBy

This element provides a comma-delimited list of properties that
specify how the response document result set should be sorted. The
DESC keyword can appear after a property name to sort that property
descending.
For example:
<LoadCollection Name="MGCore.Users">
<LoadType>FIRST</LoadType>
<PropertyList>
<UserID />
<Username />
<Location />
</PropertyList>
<OrderBy>UserID, Username</OrderBy>
</LoadCollection>

P (Property Value)

For each item, this list contains values corresponding the properties
listed.

PropertyList

In request data, you only need to list the properties you want returned.
Each property is used as an element. The response data mimics the
request list.
For example:
<PropertyList>
<Acct />
<AcctUnit1 />
<AcctUnit2 />
<AcctUnit3 />
<AcctUnit4 />
. . .
</PropertyList>

Infor Mongoose IDO Integration with External Applications Guide | 18

About the IDO request XML schema

Element

Description

ReadMode

This element specifies the collection "read mode", which controls the
isolation level used when executing queries. See the online help for
"Process Defaults".
These are the valid attributes for this element:
•
•
•

ReadCommitted
ReadUncommitted
Default

If this element is empty or omitted, Default is assumed. If this element
in the request is anything except Default, the read mode value appears
in the response.
RecordCap

This element specifies how many records are to be retrieved in one
request.
These are the valid values:
•

•
•
PostQueryCmd

-1 - (Default) 200 records are to be retrieved by system default.

For other possible system record caps, see the online help topic
"About caps".
0 - No cap, all records are to be retrieved.
Any other number - The specified number of records are to be retrieved.

This element specifies a method to execute once for each row in the
result set, after the query is completed. This is equivalent to the PQ
option in Load/Save overrides and uses the same syntax.
For example:
<PostQueryCmd>
MyPostQuerySp( Property1, Property2, REF Property3 )
</PostQueryCmd>

Example 1 - Basic Load Collection
This example request queries the user ID, username, and user description from the users table.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<LoadCollection Name="UserNames" LoadCap="0">
<PropertyList>
<UserDesc />
<UserId />
<Username />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>

Infor Mongoose IDO Integration with External Applications Guide | 19

About the IDO request XML schema
<Filter />
<OrderBy />
<PostQueryCmd />
</LoadCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains user information such as user ID, username, and user description from the
users table.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<LoadCollection Name="UserNames" LoadCap="0">
<PropertyList>
<UserDesc />
<UserId />
<Username />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-13 14:14:23.487] UserNames.ID=[919f93d72427-489a-b78b-839f37a757db]">
<P>Will Smith</P>
<P>38</P>
<P>wsmiith</P>
</Item>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-13 14:14:23.453] UserNames.ID=[122cb5afc5fb-4109-a95b-a331cb4e258c]">
<P>Juan Dela Cruz</P>
<P>41</P>
<P>jdelacruz</P>
</Item>
</Items>
<FilterIDO />
<MoreItems>false</MoreItems>
</LoadCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Example 2 - Custom Load Collection
One variation of the LoadCollection request is to specify an IDO method for loading data into the collection.
It includes these elements:
<CustomLoadMethod Name="methodname">
<Parameters>
<Parameter>parametervalue</Parameter>
<Parameter>parametervalue</Parameter>
</Parameters>
</CustomLoadMethod>

Infor Mongoose IDO Integration with External Applications Guide | 20

About the IDO request XML schema
This example request queries the note content and description from the object notes table using the custom
load method GetNotesSp.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<LoadCollection Name="ObjectNotes" LoadCap="0">
<PropertyList>
<SpcnNoteContent />
<SpcnNoteDesc />
</PropertyList>
<LoadType>FIRST</LoadType>
<ReadMode>ReadCommitted</ReadMode>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<CustomLoadMethod Name="GetNotesSp">
<Parameters>
<Parameter>UserNames</Parameter>
<Parameter>4d6cb1eb-e4fc-4e12-aae8-95ff1086ee8c</Parameter>
</Parameters>
</CustomLoadMethod>
</LoadCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains the note contents and description from the object notes table.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<LoadCollection Name="ObjectNotes" LoadCap="0">
<PropertyList>
<SpcnNoteContent />
<SpcnNoteDesc />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="">
<P>Send in your weekly report every Friday before leaving the office</P>
<P>Weekly Report</P>
</Item>
</Items>
<MoreItems>false</MoreItems>
</LoadCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Infor Mongoose IDO Integration with External Applications Guide | 21

About the IDO request XML schema
Example 3 - Nested Load Collection
LoadCollection requests can be hierarchichal. For example, this sample request queries the users and user
emails table in a single request, by nesting the UserEmails IDO LoadCollection request inside the UserNames
IDO LoadCollection request.
<IDORequest ProtocolVersion="6.03" SessionID="71e1c28d-4e00-495e-bff0-571b26179649">
<RequestHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<LoadCollection Name="UserNames" LoadCap="0">
<PropertyList>
<UserDesc />
<UserId />
<Username />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<LoadCollection Name="UserEmails" LoadCap="0">
<LinkBy>
<PropertyPair Parent="UserId" Child="UserId" />
</LinkBy>
<PropertyList>
<EmailAddress />
<EmailType />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
</LoadCollection>
</LoadCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains a set of records retrieved from the users and user emails table.
<IDOResponse ProtocolVersion="6.03" SessionID="71e1c28d-4e00-495e-bff0-571b26179649">
<ResponseHeader Type="LoadCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<LoadCollection Name="UserNames" LoadCap="0">
<PropertyList>
<UserDesc />
<UserId />
<Username />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-13 14:14:23.487] UserNames.ID=[919f93d7-

Infor Mongoose IDO Integration with External Applications Guide | 22

About the IDO request XML schema
2427-489a-b78b-839f37a757db]">
<P>Will Smith</P>
<P>38</P>
<P>wsmiith</P>
<LoadCollection Name="UserEmails" LoadCap="0">
<PropertyList>
<EmailAddress />
<EmailType />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="PBT=[UserEmail] ue.ID=[2732c8a5-1400-4417-88a4-28f6e8dcb9fd] ue.DT=[201908-13 14:12:57.393]">
<P>will.smith@infor.com</P>
<P>P</P>
</Item>
<Item ID="PBT=[UserEmail] ue.ID=[9916ae94-07ef-443b-8ae9-a9ce4e24b4b9] ue.DT=[201908-13 14:14:23.513]">
<P>wsmith@business.com</P>
<P>S</P>
</Item>
</Items>
<FilterIDO />
<MoreItems>false</MoreItems>
</LoadCollection>
</Item>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-13 14:14:23.453] UserNames.ID=[122cb5afc5fb-4109-a95b-a331cb4e258c]">
<P />
<P>41</P>
<P>jdelacruz</P>
<LoadCollection Name="UserEmails" LoadCap="0">
<PropertyList>
<EmailAddress />
<EmailType />
</PropertyList>
<LoadType>FIRST</LoadType>
<RecordCap>-1</RecordCap>
<Filter />
<OrderBy />
<PostQueryCmd />
<Items>
<Item ID="PBT=[UserEmail] ue.ID=[28685ab7-c635-4e86-b465-f21e6497a263] ue.DT=[201908-13 14:13:13.803]">
<P>juan.delacruz@infor.com</P>
<P>P</P>
</Item>
</Items>
<FilterIDO />
<MoreItems>false</MoreItems>
</LoadCollection>
</Item>
</Items>
<FilterIDO />
<MoreItems>false</MoreItems>
</LoadCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

UpdateCollection
An UpdateCollection request/response document modifies a collection (inserting, updating, or deleting
records) using the UpdateCollection method of an IDO.

Infor Mongoose IDO Integration with External Applications Guide | 23

About the IDO request XML schema
When performing an update or deletion request, identify the records you want to update or delete. There are
two ways to do this:
•

•

By item ID – To update or delete by item ID, you must perform a LoadCollection first to retrieve the item
ID, and then include it in the <Item> node’s ID attribute when performing the UpdateCollection update
or delete operation.
By the key data – In this case, specify the attribute UseKeys=”Y” on the <Item> node and set the appropriate
properties to the key data when performing the update or delete operation.

For update requests, you can also specify the locking option to be used during an IDO item update by using
the UpdateLocking attribute on the <Item> node. There are two options for this:
•
•

Row – (Default) This option extracts the row’s item ID to get the Row Pointer and Record Date, and use
them to construct the WHERE clause.
Property – This option includes the row’s key values and original property values to construct the WHERE
clause. This attribute ensures that any intermediate change on the row is overwritten with the latest
values. This property also enables simultaneous users to edit on the same row on different columns.

For insert requests, this information is not needed.
Request data
An UpdateCollection request document has this payload structure and contains data such as the collection
name and the rows that are subjected to change, which are required to complete the request:
<RequestData>
<UpdateCollection Name="collection" RefreshAfterUpdate="Y">
<Items>
<Item ID="itemid" ItemNo="0" Action="actiontype">
<Property Name="propertyname">propertyvalue</Property>
<Property Name="propertyname">propertyvalue</Property>
</Item>
<Item ID="itemid" ItemNo="0" Action="actiontype">
<Property Name="propertyname">propertyvalue</Property>
<Property Name="propertyname">propertyvalue</Property>
</Item>
</Items>
</UpdateCollection>
</RequestData>

Response data
An UpdateCollection response document has this payload structure and includes requested data and return
values.
<ResponseData>
<UpdateCollection Name="collection" RefreshAfterUpdate="Y">
<CollectionID />
<Items>
<Item ID="itemid" ItemNo="0" Action="actiontype">
<Property Name="propertyname">propertyvalue</Property>
<Property Name="propertyname">propertyvalue</Property>
</Item>
<Item ID="itemid" ItemNo="0" Action="actiontype">
<Property Name="propertyname">propertyvalue</Property>
<Property Name="propertyname">propertyvalue</Property>
</Item>
</Items>
</UpdateCollection>
</ResponseData>

Infor Mongoose IDO Integration with External Applications Guide | 24

About the IDO request XML schema
Element descriptions
Element

Description

CollectionID

Optional. This element is used when reporting errors that occur while
processing items in an UpdateCollection request. If this element is
omitted in the request, the IDO name is used instead for any error response.
An UpdateCollection request can contain hierarchical (nested) requests.
Having a collection ID allows the system response to include both the
error message and which item caused the error. (See example 3.)

Item

This element can contain these attributes:
•
Action – This attribute can be Insert, Update, or Delete. For Insert
operations, if the InitiatorType is set to Replication, the insert is
treated as an update if the record already exists.
•
ItemNo – This number is user-defined and optional. It must be a
valid 32-bit signed integer. If RefreshAfterUpdate is Y and this
number is specified, the response includes the record number
value, so that you can match updated records with the originals
and see more easily what has changed.
•
ID – This value is used within the system to identify the item. When
requesting an update or deletion of existing records, you must
include either this, the item ID attribute, or the UseKeys attribute.
Omitting the ID attribute defaults to the same behavior as
UseKeys="Y".
•
UseKeys:
•
Y (the default value) indicates that the update or delete request is to use key data, rather than item IDs.
•
N indicates that the request is to include item IDs.
•
UpdateLocking:
•
Row (the default value) extracts the row’s indentifying property (ItemID) to get the RowPointer and RecordDate, and use
them to construct the WHERE clause.
•
Property includes the row’s key values and original values
to construct the WHERE clause. RecordDate and RowPointer
are omitted from the WHERE clause.

Infor Mongoose IDO Integration with External Applications Guide | 25

About the IDO request XML schema

Element

Description

Property

Include in your request any properties that you plan to update, as well
as all non-nullable properties that do not have a default value. You
can omit properties that have default values that do not change because of the update.
This element can contain these attributes:
•
•
•

UpdateCollection

Name – This is the name of the property.
Modified – This value specifies whether the property was updated.
Only values flagged as "Modified=Y" are updated in the database.
OriginalValue – This attribute contains the original value of the
property. Use this attribute when the <Item> node’s UpdateLocki
ng=”Property”.

This element can include these attributes:
•
Name – This attibute contains the name of the IDO collection.
•
RefreshAfterUpdate:
•
Y indicates that the response document should show information for inserted or updated items that was updated after the
update was done. Deleted items are not updated.
•
N indicates that the response document information was not
updated after the modification.
•
TxnScope – Optional. This attibute can be set to Collection (the
default value) or Item.
When this is set to Item, each individual item in the UpdateCollection request is saved in a separate transaction. For a hierarchical
(nested) UpdateCollection request, the value of the TxnScope attribute at the root level determines the behavior for the entire
UpdateCollection request. If an exception occurs while processing
an UpdateCollection request for an item using the TxnScope attribute, records that were saved before the exception are still
committed, but no additional records are saved.
If the TxnScope attribute is omitted or is set to Collection, the
request is processed in a single transaction.
This attribute has no effect when it is included in an inner UpdateCollection within hierarchical requests.

LinkBy

This element is valid only in CONTAINS relationships, much like the
LINKBY keyword.
The information within this pair of tags tells how the CONTAINS relationship is linked to the parent collection.
For example:
<LinkBy>
<PropertyPair Child="Item" Parent="Item" />
<!-- Optional: more sets of PropertyPair tags -->
</LinkBy>

Infor Mongoose IDO Integration with External Applications Guide | 26

About the IDO request XML schema
Example 1 - Using UpdateCollection for Insert, Update, and Delete operations
This example request shows how you can perform different actions for each row in the collection within a
single request. This request 1) creates a new user, 2) updates the description of user wsmith, and 3) deletes
the record of user cdelune.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-13 14:14:23.453] UserNames.ID=[122cb5afc5fb-4109-a95b-a331cb4e258c]" ItemNo="0" Action="Insert">
<Property Name="UserDesc">John Doe</Property>
<Property Name="Username">jdoe</Property>
</Item>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 17:30:09.870] UserNames.ID=[b3aedc23722f-4058-b12c-f14bdfd955b4]" ItemNo="0" Action="Update">
<Property Name="UserDesc">Will Smith Sr.</Property>
</Item>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 17:30:52.253] UserNames.ID=[018ae41142c3-48cb-a50b-b943dcb70dbe]" ItemNo="2" Action="Delete" />
</Items>
</UpdateCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response includes the updated description of user wsmith, and the details of the new user jdoe.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<CollectionID />
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 17:32:43.233] UserNames.ID=[b3aedc23722f-4058-b12c-f14bdfd955b4]" ItemNo="0" Action="Update">
<Property Name="UserDesc">Will Smith Sr.</Property>
</Item>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 17:32:43.283] UserNames.ID=[1a69ed89a34e-4634-95fd-dad989ef3a39]" ItemNo="0" Action="Insert">
<Property Name="UserDesc">John Doe</Property>
<Property Name="Username">jdoe</Property>
<Property Name="InWorkflow">0</Property>
</Item>
</Items>
</UpdateCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Infor Mongoose IDO Integration with External Applications Guide | 27

About the IDO request XML schema
Example 2 - Nested UpdateCollection
UpdateCollection requests can be hierarchichal. For example, this request document inserts a user-defined
type and its value in a single request, by nesting UserDefinedTypeValues IDO UpdateCollection request inside
the UserDefinedTypes IDO UpdateCollection request.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<UpdateCollection Name="UserDefinedTypes" RefreshAfterUpdate="Y">
<Items>
<Item ID="" ItemNo="0" Action="Insert">
<Property Name="Description">An amount of time used with calendars</Property>
<Property Name="Name">Month</Property>
<UpdateCollection Name="UserDefinedTypeValues" RefreshAfterUpdate="Y">
<LinkBy>
<PropertyPair Parent="Name" Child="TypeName" />
</LinkBy>
<Items>
<Item ID="" ItemNo="0" Action="Insert">
<Property Name="TypeName">Month</Property>
<Property Name="Value">January</Property>
</Item>
</Items>
</UpdateCollection>
</Item>
</Items>
</UpdateCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains the complete information regarding the new user-defined type and its value.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<UpdateCollection Name="UserDefinedTypes" RefreshAfterUpdate="Y">
<CollectionID />
<Items>
<Item ID="PBT=[UserDefinedTypes] UserDefinedTypes.ID=[f22b0d6b-fdba-4b14-b0084fd418b0cd75] UserDefinedTypes.DT=[2019-08-16 09:54:34.450]" ItemNo="0" Action="Insert">
<Property Name="Description">An amount of time used with calendards</Property>
<Property Name="Name">Month</Property>
<Property Name="InWorkflow">0</Property>
<UpdateCollection Name="UserDefinedTypeValues" RefreshAfterUpdate="Y">
<LinkBy>
<PropertyPair Parent="Name" Child="TypeName" />
</LinkBy>
<CollectionID />
<Items>
<Item ID="PBT=[UserDefinedTypeValues] UserDefinedTypeValues.ID=[54a99f45-7a9e4c3b-a974-7fe496db8c8b] UserDefinedTypeValues.DT=[2019-08-16 09:54:34.463]" ItemNo="0" Action="In
sert">
<Property Name="TypeName">Month</Property>
<Property Name="Value">January</Property>
<Property Name="InWorkflow">0</Property>

Infor Mongoose IDO Integration with External Applications Guide | 28

About the IDO request XML schema
</Item>
</Items>
</UpdateCollection>
</Item>
</Items>
</UpdateCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Example 3 - UpdateCollection using item IDs
This example request updates an existing record using the ID attribute. To find a specific ID, perform a
LoadCollection request on the data before performing the UpdateCollection.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 17:32:43.233] UserNames.ID=[b3aedc23722f-4058-b12c-f14bdfd955b4]" ItemNo="0" Action="Update">
<Property Name="UserDesc">Will Smith</Property>
</Item>
</Items>
</UpdateCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response includes the updated description of user wsmith.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<CollectionID />
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 18:38:30.520] UserNames.ID=[b3aedc23722f-4058-b12c-f14bdfd955b4]" ItemNo="0" Action="Update">
<Property Name="UserDesc">Will Smith</Property>
</Item>
</Items>
</UpdateCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Infor Mongoose IDO Integration with External Applications Guide | 29

About the IDO request XML schema
Example 4 - UpdateCollection using keys
This example request updates an existing record using the UseKeys attribute which indicates that the update
or delete request will use key data.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<Items>
<Item ItemNo="0" Action="Update" UseKeys="Y">
<Property Name="UserDesc">John Mark Doe</Property>
<Property Name="UserId" Modified="N">45</Property>
</Item>
</Items>
</UpdateCollection>
</RequestData>
</RequestHeader>
</IDORequest>

This example response includes the updated description of the user with user ID 45.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="UpdateCollection">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<UpdateCollection Name="UserNames" RefreshAfterUpdate="Y">
<CollectionID />
<Items>
<Item ID="PBT=[UserNames] UserNames.DT=[2019-08-15 18:46:16.520] UserNames.ID=[1a69ed89a34e-4634-95fd-dad989ef3a39]" ItemNo="0" Action="Update">
<Property Name="UserDesc">John Mark Doe</Property>
<Property Name="UserId" Modified="N">45</Property>
</Item>
</Items>
</UpdateCollection>
</ResponseData>
</ResponseHeader>
</IDOResponse>

Invoke
An Invoke request/response document executes an IDO method. This method can be code in a custom
assembly, or it can be a stored procedure.

Infor Mongoose IDO Integration with External Applications Guide | 30

About the IDO request XML schema
Request data
An Invoke request document has this payload structure and contains data such as the collection name,
method name, and the method parameter values, which are required to complete the request.
<RequestData>
<Name>collection</Name>
<Method>methodname</Method>
<Parameters>
<Parameter>parametervalue</Parameter>
<Parameter>parametervalue</Parameter>
<Parameter ByRef="Y" />
</Parameters>
</RequestData>

Response data
An Invoke response document has this payload structure and includes requested data and return values.
<ResponseData>
<Name>collection</Name>
<Method>methodname</Method>
<Parameters>
<Parameter>parametervalue</Parameter>
<Parameter>parametervalue</Parameter>
<Parameter ByRef="Y">parametervalue</Parameter>
</Parameters>
<ReturnValue>returnvalue</ReturnValue>
</ResponseData>

Element descriptions
Element

Description

Method

This is the name of the IDO that contains the method.

Name

This is the name of the method being invoked.

Parameter

This is the name of a parameter for the method.
This element can contain the ByRef attribute. This attribute is optional.
When used, parameters that are marked as ByRef in the IDO metadata
are returned in the response, regardless of the ByRef value in the request. If set to Y, the parameter is input/output. If set to N, or if this attribute is omitted, the parameter is input-only.
Note: Parameters should be listed in the order that the method is expecting them. If you use the obsolete Seq attribute, it is ignored.

ReturnValue

Used only in the response document, this element contains the return
value from the method.

Examples for Invoke
This example request determines the user attributes of user jdoe using the IDO method GetUserAttributes.
<IDORequest ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<RequestHeader Type="Invoke">

Infor Mongoose IDO Integration with External Applications Guide | 31

About the IDO request XML schema
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData>
<Name>UserNames</Name>
<Method>GetUserAttributes</Method>
<Parameters>
<Parameter>jdoe</Parameter>
<Parameter ByRef="Y" />
<Parameter ByRef="Y" />
<Parameter ByRef="Y" />
<Parameter ByRef="Y" />
</Parameters>
</RequestData>
</RequestHeader>
</IDORequest>

This example response contains the user attributes of the user jdoe.
<IDOResponse ProtocolVersion="6.03" SessionID="bde8caa4-1343-4808-9bca-ed844ffe7129">
<ResponseHeader Type="Invoke">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData>
<Name>UserNames</Name>
<Method>GetUserAttributes</Method>
<Parameters>
<Parameter>jdoe</Parameter>
<Parameter ByRef="Y">2</Parameter>
<Parameter ByRef="Y">1</Parameter>
<Parameter ByRef="Y" />
<Parameter ByRef="Y" />
</Parameters>
<ReturnValue>0</ReturnValue>
</ResponseData>
</ResponseHeader>
</IDOResponse>

CloseSession
A CloseSession request/response document closes an existing session.
Request data
None
Response data
None

Infor Mongoose IDO Integration with External Applications Guide | 32

About the IDO request XML schema
Example for CloseSession
This example request closes a session with the given session ID.
<IDORequest ProtocolVersion="6.03" SessionID="71e1c28d-4e00-495e-bff0-571b26179649">
<RequestHeader Type="CloseSession">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<RequestData />
</RequestHeader>
</IDORequest>

This is an example response of the CloseSession request.
<IDOResponse ProtocolVersion="6.03" SessionID="71e1c28d-4e00-495e-bff0-571b26179649">
<ResponseHeader Type="CloseSession">
<InitiatorType />
<InitiatorName />
<SourceName />
<SourceConfig />
<TargetName />
<TargetConfig />
<ResponseData />
</ResponseHeader>
</IDOResponse>

Using wire taps
To know the structure of the IDO request XML schema, you must monitor the IDO communication exchange
using a wire tap. You can use a wire tap to monitor the IDO requests and responses that are sent and received
in a user session.
In the Listening Touser window of a wire tap, you can select and view each IDO XML document associated
with the session.

Infor Mongoose IDO Integration with External Applications Guide | 33

