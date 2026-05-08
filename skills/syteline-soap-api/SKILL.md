---
name: "syteline-soap-api"
description: "SOAP web service - CallMethod, CreateSessionToken, GetConfigurationNames, LoadDataSet, LoadJson, SaveDataSet, SaveJson"
version: "1.0"
tags:
  - syteline
  - soap
  - api
  - mongoose
---

# syteline-soap-api

Source: Infor Mongoose IDO Integration with External Applications Guide, Release 2026.x


The name of the IDO collection that is
associated with the strongly typed API

operationID

Path

Yes

The name of the strongly typed API to be
executed

refresh

Query

No

Instructs the system to refresh the collection after the update is complete
Valid values are True or False.
Note: Use this parameter only if the
strongly typed API is an UpdateCollection
API.

parameter1 to parameter50

Query

No

Passes the parameters required by the
method being invoke
Note: Use these parameters only if the
strongly typed API is an InvokeIDOMethod
API.

Headers
Name

Description

Authorization

If the API is called directly, then the Mongoose security token
is obtained through a call to the GetSecurityToken API.
If the API is called through the ION API, then a valid OAuth2.0
bearer token is provided by ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server

Request data
None

Infor Mongoose IDO Integration with External Applications Guide | 141

Using the REST API
Response data when the strongly typed API is an UpdateCollectio API
{

"RefreshItems": [
{
"Action": 1,
"ItemId": "PBT=[UserNames] UserNames.DT=[2022-05-23 11:42:40.977] User
Names.ID=[64e98785-32ed-44f1-82eb-16d3719299e9]",
"ItemNo": 0,
"Properties": [
{
"Name": "Username",
"Value": "jdoe",
"OriginalValue": null,
"Modified": false,
"IsNull": false,
"IsNestedCollection": false
},
{
"Name": "InWorkflow",
"Value": "0",
"OriginalValue": null,
"Modified": false,
"IsNull": false,
"IsNestedCollection": false
}
],
"UpdateLocking": 0
}
],
"Success": true,
"Message": null
}

Response data when the strongly typed API is an InvokeIDOMethod API
{

}

"ReturnValue": "0",
"Parameters": [
"mpalanca",
"4",
"1",
"",
""
],
"Success": true,
"Message": null

Example
This example code executes a strongly typed API that invokes the GetUserAttributes method from the
UserNames IDO.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string requestUrl = $"http://localhost/IDORequestService/ido/dynamic/api-docs-collection/User
Names/GetUserAttributes?parameter1=sa&parameter2=&parameter3=&parameter4=&parameter5=";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQjm3w/=…" );
HttpResponseMessage response = client.PostAsync( requestUrl, null ).Result;

Infor Mongoose IDO Integration with External Applications Guide | 142

Using the REST API

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 143

About the SOAP web service

Chapter 4: About the SOAP web service

The SOAP web service is an alternative to the REST web service.

Using a SOAP web service
The SOAP web service is maintained primarily for backward compatibility, as it predates the REST API.
You can use the SOAP web service to access IDOs over the internet and perform IDO-related operations, such
as loading and updating IDO collections, or running IDO methods. The SOAP web service accesses Mongoose
through the IDO request interface, using the IDO request XML schema.

Creating SOAP API calls
SOAP web service calls are created using Visual Studio.
Note: The procedure for creating a SOAP web service call assumes that you have added a reference to your
project for the IDO .NET web service, as follows.
1

In Visual Studio, perform one of these actions:
•
In the Solution Explorer, right-click on the References node.
•
From the Project menu, select Add Service Reference.

2

Specify the URL for your .NET web service. Use this syntax:
http://server/IDORequestService/IDOWebService.asmx

where server is the name of DNS or IP address of the server that hosts your web service.
3
4

Set the Namespace to IDOWebServiceReference.
Click OK.

You can redirect calls at runtime, for instance, to a production server rather than a test server, by passing two
parameters to the constructor. For example:
IDOWebServiceReference.DOWebServiceSoapClient idoWS = new
IDOWebServiceReference.DOWebServiceSoapClient( "IDOWebServiceSoap", "http://other_server_name/IDORe
questService/IDOWebService.asmx" );

Infor Mongoose IDO Integration with External Applications Guide | 144

About the SOAP web service

Note: You can add a query parameter to the .NET web service URL to specify the configuration group, which
applies to the GetConfigurationNames web service API. For example: http://server /IDORequestService/
IDOWebService.asmx?configgroup=group-name

where group-name corresponds to the name of a configuration group created with the Configuration Manager
utility.
When you create SOAP web service API calls, we recommend that you use the prototypes as examples. All
the prototype examples are given in C#.

About SOAP web service methods
This section lists and describes the SOAP web service methods that are available for Mongoose applications
that integrate with external applications.

CallMethod
The CallMethod method executes an IDO method that can either be code in a custom assembly or in a stored
procedure.
Syntax
public object CallMethod( string strSessionToken,
string strIDOName,
string strMethodName,
ref string strMethodParameters )

Parameters
Name

Description

strSessionToken

This is the session token obtained through a call to the CreateSessionToken
API.

strIDOName

This is the name of the IDO that publishes the method.

strMethodName

This is the name of the IDO to call.

strMethodParameters

This parameter is an XML-formatted string that contains the parameters to pass
to the method. The argument for this parameters looks like this:
<Parameters>
<Parameter ByRef="Y|N">value</Parameter>
...etc...
</Parameters>

Infor Mongoose IDO Integration with External Applications Guide | 145

About the SOAP web service
Output
Returns the value that was returned to it by the method.
Example
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
string ido = "UserNames";
string idoMethod = "GetUserAttributes";
List<Parameter> parameters = new List<Parameter>
{
new Parameter() { Text = "sa", ByRef = "Y" },
new Parameter() { Text = string.Empty, ByRef = "Y" },
new Parameter() { Text = string.Empty, ByRef = "Y" },
new Parameter() { Text = string.Empty, ByRef = "Y" },
new Parameter() { Text = string.Empty, ByRef = "Y" }
};
XmlDocument xdoc = new XmlDocument();
XPathNavigator nav = xdoc.CreateNavigator();
using ( XmlWriter writer = nav.AppendChild() )
{
XmlSerializer serializer = new XmlSerializer( typeof( List<Parameter> ), new XmlRootAttribute(
"Parameters" ) );
serializer.Serialize( writer, parameters );
}
IDOWebService.DOWebServiceSoapClient soapClient = new IDOWebService.DOWebServiceSoapClient();
// provide the ido parameters as payload
string idoParameters = xdoc.OuterXml;
object result = soapClient.CallMethod( sessionToken, ido, idoMethod, ref idoParameters );

Helper classes for the example
You can use these classes to help construct the request as demonstrated in the code snippets above:
[XmlRoot( ElementName = "Parameter" )]
public class Parameter
{
[XmlAttribute( AttributeName = "ByRef" )]
public string ByRef { get; set; }
[XmlText]
public string Text { get; set; }
}
[XmlRoot( ElementName = "Parameters" )]
public class Parameters
{
[XmlElement( ElementName = "Parameter" )]
public Parameter Parameter { get; set; }
}

CreateSessionToken
The CreateSessionToken method validates the username, password, and configuration, and then returns a
session token that allows the user to open a session when any of the other web service methods (LoadDataSet,
SaveDataSet, and so on) are called.

Infor Mongoose IDO Integration with External Applications Guide | 146

About the SOAP web service
Syntax
public string CreateSessionToken( string strUserId,
string strPswd,
string strConfig )

Parameters
Name

Description

strUserId

The Mongoose user's username

strPswd

The Mongoose user's password

strConfig

The name of the application's configuration

Output
Returns a session token string, required by other web service methods.
This is an example of a session token:
b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQjm3w/qkdxDUzmqvSYEZDCmJGWpA23OTlhFpxRHF
z3WOsvay8V58XdIp/UIsr5TpCdMwtW3QXF2ahwQYp2O6GzKlJcx50PzAY5KGW7CHLvMml26H13iSRJ
vZB6an1hQXrBH191JCq6PYlPtGvQUiUMGCjVqzqgXHqaK58T6NkJfMbJv52jB1DyRTvtoshS5RGj1Q
VVPCtuuvkG3O659gM2Q+lFivNleD+erIHoiQFvS1MelxKd5L5fH4PQrZ0RTnIln+AfwmOWxXQTadcq
0NsVR8588DWj/QaqIr/usIVqp4tWMg==

Example
IDOWebService.DOWebServiceSoapClient idoSoapClient = new
IDOWebService.DOWebServiceSoapClient();
string token = idoSoapClient?.CreateSessionToken( "jdoe", "sJKam67s", "CSI_DALS" );

GetConfigurationNames
Use the GetConfigurationNames method to retrieve a list of the Mongoose configurations that are available
to access from an external application.
Syntax
public string[] GetConfigurationNames()

Parameters
None
Output
Returns a list of valid configuration names from the Default configuration group.

Infor Mongoose IDO Integration with External Applications Guide | 147

About the SOAP web service
Example
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
string[] configs = soapClient.GetConfigurationNames();

LoadDataSet
The LoadDataSet method uses the LoadCollection method of an IDO to query either an IDO collection or a
database table and return the results to the user.
Syntax
public DataSet LoadDataSet( string strSessionToken,
string strIDOName,
string strPropertyList,
string strFilter,
string strOrderBy,
string strPostQueryMethod,
int iRecordCap )

Parameters
Name

Description

strSessionToken

This is the session token obtained through a call to the CreateSessionToken
API.

strIDOName

This is the name of the IDO collection.

strPropertyList

This is a comma-delimited list of the IDO properties.

strFilter

This parameter allows you to filter the results of the operation. You can use ny
valid SQL WHERE clause syntax.

strOrderBy

This is a comma-delimited list of properties that govern the order in which the
result set should be sorted. To sort a given property in descending order, use
the DESC keyword after the property name.

strPostQueryMethod

This parameter specifies a method to execute once for each row in the result
set after the query is completed. This is the equivalent of the PQ option in
Load/Save Overrides and uses the same syntax.

iRecordCap

This parameter determines how many records are to be retrieved in one request.
Values are:
•
•
•

-1 – 200 records are retrieved.
0 – All records are retrieved.

Any other positive integer – The specified number of records are retrieved.

Infor Mongoose IDO Integration with External Applications Guide | 148

About the SOAP web service
Output
Returns a System.Data.DataSet that contains the results of the query. The returned data can be filtered by
any IDO-level filters. For information about IDO filters, see the online help.
This is an example output DataSet:
<DataSet xmlns="http://frontstep.com/IDOWebService">
<xs:schema xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-mi
crosoft-com:xml-msdata" id="UserNames">
<xs:element name="UserNames" msdata:IsDataSet="true" msdata:UseCurrentLocale="true">
<xs:complexType>
<xs:choice minOccurs="0" maxOccurs="unbounded">
<xs:element name="IDO">
<xs:complexType>
<xs:sequence>
<xs:element name="UserId" type="xs:decimal" minOccurs="0"/>
<xs:element name="Username" type="xs:string" minOccurs="0"/>
<xs:element name="UserDesc" type="xs:string" minOccurs="0"/>
</xs:sequence>
</xs:complexType>
</xs:element>
</xs:choice>
</xs:complexType>
</xs:element>
</xs:schema>
<diffgr:diffgram xmlns:msdata="urn:schemas-microsoft-com:xml-msdata" xmlns:diffgr="urn:schemasmicrosoft-com:xml-diffgram-v1">
<UserNames xmlns="">
<IDO diffgr:id="IDO1" msdata:rowOrder="0">
<UserId>1</UserId>
<Username>sa</Username>
<UserDesc>WinStudio Admin user</UserDesc>
</IDO>
<IDO diffgr:id="IDO2" msdata:rowOrder="1">
<UserId>2</UserId>
<Username>jdelacruz</Username>
<UserDesc>Juan Dela Cruz</UserDesc>
</IDO>
</UserNames>
</diffgr:diffgram>
</DataSet>

Example
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
string ido = "UserNames";
string properties = "UserId, Username, UserDesc, _ItemID";
string filter = string.Empty;
string orderBy = "UserId";
string postQueryMethod = string.Empty;
int recordCap = -1;
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
DataSet users = soapClient.LoadDataSet( sessionToken, ido, properties, filter, orderBy, post
QueryMethod, recordCap );

LoadJson
The LoadJson method uses the LoadCollection method of an IDO to query either an IDO collection or a
database table and return the results to the user in JSON format.

Infor Mongoose IDO Integration with External Applications Guide | 149

About the SOAP web service
Syntax
public string LoadJson( string strSessionToken,
string strIDOName,
string strPropertyList,
string strFilter,
string strOrderBy,
string strPostQueryMethod,
int iRecordCap )

Parameters
Name

Description

strSessionToken

This is the session token obtained through a call to the CreateSessionToken
API.

strIDOName

This is the name of the IDO collection.

strPropertyList

This is a comma-delimited list of the IDO properties.

strFilter

This parameter allows you to filter the results of the operation. You can use ny
valid SQL WHERE clause syntax.

strOrderBy

This is a comma-delimited list of properties that govern the order in which the
result set should be sorted. To sort a given property in descending order, use
the DESC keyword after the property name.

strPostQueryMethod

This parameter specifies a method to execute once for each row in the result
set after the query is completed. This is the equivalent of the PQ option in
Load/Save Overrides and uses the same syntax.

iRecordCap

This parameter determines how many records are to be retrieved in one request.
Values are:
•
•
•

-1 – 200 records are retrieved.
0 – All records are retrieved.

Any other positive integer – The specified number of records are retrieved.

Output
Returns a JSON string containing the results of the query. The returned data can be filtered by any IDO-level
filters. For information about IDO filters, see the online help.
This is an example of JSON output:
{

"IDOName": "UserNames",
"Items": [
{
"EditStatus": 0,
"ID": "PBT=[UserNames] UserNames.DT=[2019-05-03 13:24:07.080] UserNames.ID=[2bebf824-6b0f4725-9150-da7303cc86ec]",
"Properties": [
{
"Property": "11",
"Updated": false
},
{

Infor Mongoose IDO Integration with External Applications Guide | 150

About the SOAP web service

},
{

},
{

]

}

"Property": "sa",
"Updated": false
"Property": "System Admin",
"Updated": false

"EditStatus": 0,
"ID": "PBT=[UserNames] UserNames.DT=[2019-07-25 11:40:06.753] UserNames.ID=[c3878782-08434be7-8c0f-dad8dc00a6d7]",
"Properties": [
{
"Property": "32",
"Updated": false
},
{
"Property": "jdelacruz",
"Updated": false
},
{
"Property": "Juan Dela Cruz",
"Updated": false
}
]
}
],
"PropertyList": [
"UserId",
"Username",
"UserDesc"
]
}

Example
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
string ido = "UserNames";
string properties = "UserId, Username, UserDesc";
string filter = string.Empty;
string orderBy = "UserId";
string postQueryMethod = string.Empty;
int recordCap = -1;
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
string json = soapClient.LoadJson( sessionToken, ido, properties, filter, orderBy, postQueryMethod,
recordCap );

SaveDataSet
The SaveDataSet method modifies a collection (inserting, updating, or deleting records) using the
UpdateCollection method of an IDO.
Syntax
public DataSet SaveDataSet( string strSessionToken,
DataSet updateDataSet,
bool refreshAfterSave,
string strCustomInsert,

Infor Mongoose IDO Integration with External Applications Guide | 151

About the SOAP web service
string strCustomUpdate,
string strCustomDelete )

Parameters
Name

Description

strSessionToken

This is the session token obtained through a call to the CreateSessionToken
API.

updateDataSet

This parameter specifies the data set that contains records to be updated.

refreshAfterSave

When set to TRUE, this parameter specifies that the data set records to be updated are to be refreshed and returned to the caller.

strCustomInsert

This parameter is a comma-delimited list of methods and/or instructions which
override the default save behavior.

strCustomUpdate

This parameter is a comma-delimited list of methods and/or instructions which
override the default save behavior.

strCustomDelete

This parameter is a comma-delimited list of methods and/or instructions which
override the default save behavior.

Output
Returns a data set containing rows refreshed after the save. If the refreshAfterSave parameter is set to FALSE,
this method returns a value of 'null'.
Example 1 – Insert action using SaveDataSet
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
DataSet insertDS = new DataSet( "UserNames" );
DataTable idoTable = insertDS.Tables.Add( "IDO" );
idoTable.Columns.Add( "Username", typeof( string ) );
idoTable.Columns.Add( "UserDesc", typeof( string ) );
idoTable.Rows.Add( new object[] { "wsmiith", "Will Smith" } );
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
DataSet updatedDS = soapClient.SaveDataSet( sessionToken, insertDS, true,
string.Empty, string.Empty, string.Empty );

Example 2 – Update action using SaveDataSet
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
// GetDataSet makes a call to the LoadDataSet web service method to get the collection
DataSet ds = GetDataSet();
DataTable idoTable = ds.Tables["IDO"];
// Row to be updated
DataRow row = idoTable.Rows[2];
// Update the record’s property value
row["UserDesc"] = "John Doe Sr.";

Infor Mongoose IDO Integration with External Applications Guide | 152

About the SOAP web service
Example 3 – Delete action using SaveDataSet
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
// GetDataSet makes a call to the LoadDataSet web service method to get the collection
DataSet ds = GetDataSet();
DataTable idoTable = ds.Tables["IDO"];
// Record to be deleted
var row = idoTable.Rows[3];
row.Delete();
DataSet updatedDS = IDOSoapClient.SaveDataSet( sessionToken, ds, true, string.Empty, string.Empty,
string.Empty );

SaveJson
The SaveJson method modifies a collection (inserting, updating, or deleting records) using the UpdateCollection
method of an IDO, using JSON.
Syntax
public string SaveJson( string strSessionToken,
string updateJsonObject,
string strCustomInsert,
string strCustomUpdate,
string strCustomDelete )

