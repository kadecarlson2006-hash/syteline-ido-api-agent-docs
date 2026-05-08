---
name: "syteline-rest-api-v2"
description: "Mongoose REST API Version 2 - generic endpoints (token, load, update, method, property) and dynamic IDO-specific endpoints with Swagger"
version: "1.0"
tags:
  - syteline
  - rest
  - api
  - v2
  - mongoose
---

# syteline-rest-api-v2

Source: Infor Mongoose IDO Integration with External Applications Guide, Release 2026.x


Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

X-Infor-MongooseConfig

This is the name of a configuration that is available on the application server. This is required only when using the Mongoose
API through the ION API.

X-IdoName

This is the name of the IDO collection.

X-RowPointer

This is the value of the IDO row pointer.

X-DocName

This is the name of the document object.

X-DocExt

This is the file extension used for the document object.

X-DocDesc

This is the description of the document object.

Request data
The request object is an unencoded binary stream of a file.

Infor Mongoose IDO Integration with External Applications Guide | 103

uploaddocobj

Using the REST API
Response data in XML format
<MGRestIOResponse>
<Success>true</Success>
<Message>
Match count: 6
Updated item count: 1
</Message>
<MessageCode>700</MessageCode>
</MGRestIOResponse>

Response data in JSON format
{

}

"Message": "Match count: 6 Updated item count: 1",
"MessageCode": 700,
"Success": true

Example
This example code uploads a file and attaches it as a document object of the referenced IDO and IDO row
pointer.
string xml = string.Empty;
using ( var client = new HttpClient() )
{
// optionally, you can use json as the response type
string requestUrl = $"http://server/IDORequestService/MGRESTService.svc/io/xml/uploaddocobj";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
client.DefaultRequestHeaders.Add( "X-IdoName", "UserNames" );
client.DefaultRequestHeaders.Add( "X-RowPointer", "2807a627-577b-462e-9494-aee568152c54" );
client.DefaultRequestHeaders.Add( "X-DocName", "WeeklyReport" );
client.DefaultRequestHeaders.Add( "X-DocExt", "docx" );
client.DefaultRequestHeaders.Add( "X-DocDesc", "Weekly Report" );
// select an image or a file and include it as the request payload
OpenFileDialog dialog = new OpenFileDialog();
byte[] file = new byte[] { };
if ( dialog.ShowDialog() == DialogResult.OK )
{
file = File.ReadAllBytes( dialog.FileName );
}
// pass the file as the request data and send the post request
HttpResponseMessage response = client.PostAsync( requestUrl, new ByteArrayContent( file )
).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
xml = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 104

Using the REST API

Version 2
This section presents the updated version of the Mongoose REST API.
Base URL
All endpoints are accessible using HTTP(s) and are located at this URL:
http://<serverName>/IDORequestService/ido

Generic API documentation
Generic swagger API documentation is available for the Mongoose REST API. This document fully describes
each API operation and includes both required and optional parameters, Request Data examples, and so on.
You can access this document using this endpoint:
http://<serverName>/IDORequestService/ido/api-docs

Dynamic API documentation
Dynamic swagger collection documentation is also available for the REST API. This set contains references
to individual IDO swagger documents which are generated using the REST API Wizard. You can access this
set using this endpoint:
http://serverName/IDORequestService/ido/dynamic/api-docs-collection

Swagger API documentation for a specific IDO can also be accessed. The endpoint for this document returns
an IDO swagger document that contains custom APIs specific for the given IDO. This document is generated
using the REST API Wizard. You can access the document using this endpoint:
http://serverName/IDORequestService/ido/dynamic/api-docs-collection/IDOName

Content type
Each endpoint requires the request and response data to either be empty or JSON-formatted. No other
content types are supported.

Version 2 Generic API endpoints
This list shows the Mongoose REST version 2 generic API endpoints:
•
•
•
•
•
•
•
•

DownloadDocumentObject
DownloadFileStream
FireAESEvent
GetConfigurations
GetDocumentObjects
GetPropertyInformation
GetSecurityToken
InvokeIDOMethod

Infor Mongoose IDO Integration with External Applications Guide | 105

Using the REST API
•
•
•
•

LoadCollection
UpdateCollection
UploadDocumentObject
UploadFileStream

All API calls, except GetConfigurations and GetSecurityToken APIs, must include an Authorization header that
contains either a Mongoose security token or a valid OAuth 2.0 bearer token, depending on how the API is
being called.
We recommend that you use the presented prototypes as examples when you create your API calls. All these
prototype examples use C# code.

DownloadDocumentObject
The DownloadDocumentObject API downloads a streamed file from an IDO document object.
Note: This topic is for REST version 2. See DownloadDocumentObject on page 58 for the REST version 1.
GET

/docobj/{ido}

http://localhost/IDORequestService/ido/docobj/UserNames?rowPointer=8c66c936-d348-4c13-868469bc6fa10473&name=WeeklyReport&refseq=4

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

rowPointer

Query

Yes

The value of an IDO row pointer

name

Query

No

The name of the document object
Note: This parameter is optional if the refseq parameter
is used.

refseq

Query

No

The reference sequence number of the document object
Note: This parameter is optional if the name parameter
is used.

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

Infor Mongoose IDO Integration with External Applications Guide | 106

Using the REST API

Name

Description

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
None
Response data
Unencoded binary stream of the file, with content type of application/octet-stream.
Example
This example code downloads a document object from the referenced IDO and IDO rowpointer.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string rowpointer = "4d6cb1eb-e4fc-4e12-aae8-95ff1086ee8c";
string name = "WeeklyReport";
string requestUrl = $"http://server/IDORequestService/ido/docobj/{ido}?rowPointer={rowpoint
er}&name={name}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

DownloadFileStream
The DownloadFileStream API downloads a streamed file from an IDO collection. The IDO collection must have
a property for storing binary data.
Note: This topic is for REST version 2. See DownloadFileStream on page 59 for the REST version 1.
GET

/file/{ido}

http://localhost/IDORequestService/ido/file/UserNames?property=UserImage&rowpointer=4d6cblebe4fc-4e12-aae8-95ff1086ee8c

Infor Mongoose IDO Integration with External Applications Guide | 107

Using the REST API
Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

property

Query

Yes

The name of an IDO property for storing binary data

rp

Query

Yes

The value of an IDO row pointer

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
None
Response data
Unencoded binary stream of the file, with content-type of application/octet-stream.
Example
This example code downloads a user image from the users table.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string property = "UserImage";
string rowpointer = "4d6cb1eb-e4fc-4e12-aae8-95ff1086ee8c";
string requestUrl = $"http://server/IDORequestService/ido/file/{ido}?property={property}&row
Pointer={rowpointer}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
// send the get request
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;
using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();

Infor Mongoose IDO Integration with External Applications Guide | 108

Using the REST API

}

}

json = result.Result;

FireAESEvent
The FireAESEvent fires an Application Event System (AES) event.
Note: This topic is for REST version 2. See FireAESEvent on page 61 for the REST version 1.
POST

/aes/{eventname}

http://localhost/IDORequestService/ido/aes/DivideNumbers

Parameters
Name

In

Required?

Description

eventname

Path

Yes

The name of the AES event

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
[

{

{

},

]

},

"Name": "Num1",
"Return": false,
"Value": 120

"Name": "Num2",
"Return": false,
"Value": 5
{
}

"Name": "Quotient",
"Return": true

Infor Mongoose IDO Integration with External Applications Guide | 109

Using the REST API
Response data
{

}

"Message": null,
"Success": true,
"EventName": "DivideNumbers",
"Parameters": [
{
"Name": "Quotient",
"Value": "1",
"Return": true
},
{
"Name": "Infobar",
"Value": "success",
"Return": true
}
]

Example
This example code executes an AES event that divides two numbers and returns the quotient as an output
parameter.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string eventname = "DivideNumbers";
string requestUrl = $"http://server/IDORequestService/ido/aes/{eventname}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
AESEventParameter num1 = new AESEventParameter
{
Name = "Num1",
Return = false,
Value = "4"
};
AESEventParameter num2 = new AESEventParameter
{
Name = "Num2",
Return = false,
Value = "4"
};
AESEventParameter[] parameters = new[] { num1, num2 };
// pass the array of parameters as the request data
string contentStr = JsonConvert.SerializeObject( parameters );
// send the post request
HttpResponseMessage response = client.PostAsync( requestUrl.ToString(), new StringContent(
contentStr, Encoding.UTF8, "application/json" ) ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 110

Using the REST API
This class was used with the foregoing code snippet:
public class AESEventParameter
{
public string Name { get; set; }
public string Value { get; set; }
public bool Return { get; set; }
}

GetConfigurations
The GetConfigurations API returns a list of the application configuration names available on the server.
Note: This topic is for REST version 2. See GetConfigurations on page 67 for the REST version 1.
GET

/configurations

http://localhost/IDORequestService/ido/configurations?configgroup=DEV

Parameters
Name

In

Required?

Description

configgroup

Query

No

The name of a configuration group
This parameter is used to get the configurations from a
specific configuration group.

Headers
None
Request data
None
Response data
{

}

"Message": null,
"Success": true,
"Configurations": [
"CSI_DALS",
"CSI_EMEA",
"CSI_LA"
]

Infor Mongoose IDO Integration with External Applications Guide | 111

Using the REST API
Example
This example code retrieves an array of configuration names from a specified configuration group using the
ConfigServer.aspx page in IDORequestService.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string configGroup = "DEV";
string requestUrl = $"http://server/IDORequestService/ido/configurations?configgroup={config
Group}";
// send the get request
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
// get the response containing the configuration list
json = result.Result;
}

GetDocumentObjects
The GetDocumentObjects API returns a list of document objects.
GET

/docobj/list

http://localhost/IDORequestService/ido/docobj/list?ido=UserNames

Parameters
Name

In

Required?

Description

ido

Query

No

The name of the IDO collection that contains the document
objects

rowPointer

Query

No

The value of the IDO row pointer

docName

Query

No

The name of the document object to retrieve

docExt

Query

No

The file extension used for the document object to be retrieved

refSeq

Query

No

The reference sequence of the document object to retrieve

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

Infor Mongoose IDO Integration with External Applications Guide | 112

Using the REST API

Name

Description

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
None
Response data
{

"Documents": [
{
"TableName": "Demo_Products",
"TableRowPointer": "d629ebd6-974c-4969-be2e-145dd8d84da8",
"DocumentName": "ultimate_choco_chip_cookies",
"Description": null,
"DocumentObject": "/9j/4AAQSkZJRgABAQAASABIAAD…Z",
"DocumentExtension": "jpg",
"MediaType": "image/jpeg",
"RefSequence": "1",
"_ItemId": "PBT=[DocumentObjectAndRefView] doc.ID=[0c4b201a-5ecd-4bf9-a242-86bf4c177727]
doc.DT=[2018-10-12 09:52:12.153]"
},
{
"TableName": "DemoSubmissionComments",
"TableRowPointer": "67d4b6c8-3b67-4269-8f4f-7d7a7ee81f71",
"DocumentName": "ayala_cinema_tickets_1508950029_6fccded3",
"Description": null,
"DocumentObject": "/9j/4AAQSkZJRgABAQAASABIAAD…Z",
"DocumentExtension": "jpg",
"MediaType": "image/jpeg",
"RefSequence": "1",
"_ItemId": "PBT=[DocumentObjectAndRefView] doc.ID=[d84729d2-8023-4624-8f02-5598def50073]
doc.DT=[2018-10-12 10:07:34.433]"
}
],
"Success": true,
"Message": null
}

Example
This example code retrieves a list of document objects attached to the UserNames IDO.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/docobj/list?ido={ido}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
// send the get request
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

Infor Mongoose IDO Integration with External Applications Guide | 113

Using the REST API

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

GetPropertyInformation
The GetPropertyInformation API returns a list of properties and their attributes from an IDO collection.
GET

/info/{ido}

http://localhost/IDORequestService/ido/info/UserNames

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

Headers
Name

Description

Authorization

If the API is called directly, then the Mongoose security token
is obtained through a call to the GetSecurityToken API.
If the API is called through the ION API, then a valid OAuth2.0
bearer token is provided by the ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
None
Response data
{

"Message": null,
"Success": false,
"Keys": [
"UserId"
],
"Properties": [
{
"BooleanFalseValue": "",
"BooleanTrueValue": "",
"CaseFormat": "",
"ClrTypeName": "Decimal",
"ColumnDataType": "TokenType",

Infor Mongoose IDO Integration with External Applications Guide | 114

Using the REST API
"DataType": "Decimal",
"DateFormat": "",
"DecimalPos": 0,
"DefaultIMECharset": "",
"DefaultValue": "",
"DomainIDOName": "",
"DomainListProperties": "",
"DomainProperty": "",
"InputMask": "",
"IsItemWarnings": false,
"JustifyFormat": "R",
"LabelStringID": "sUserID",
"Length": 11,
"Name": "UserId",
"PropertyClass": "Token",
"RORecord": false,
"ReadOnly": true,
"Required": true

},
{ ... }

}

],
"SubCollections": [
{
"IDOName": "MGCore.UserGroupMaps",
"LinkBy": [
{
"Child": "UserId",
"Parent": "UserId"
}
],
"Name": "UserGroupMaps"
},
{ ... }
]

Example
This example code retrieves an array of properties and their attributes from a specified IDO.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/info/{ido}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
// send the get request
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 115

Using the REST API

GetSecurityToken
The GetSecurityToken API returns a Mongoose security token for a specific user, which can be used to
authenticate requests when calling the API directly. You can also pass credentials in request headers, which
is recommended for usernames and passwords with special characters.
Note: This topic is for REST version 2. See GetSecurityToken on page 68 for the REST version 1.
Endpoint
•

Path-based
GET

/token/{config}/{username}/{password}

http://localhost/IDORequestService/ido/token/CSI_DALS/sa/Passwe1rd

•

Header-based
GET

/token/{config}\

http://localhost/IDORequestService/ido/token/CSI_DALS

Parameters
Name

In

Required?

Description

config

Path

Yes

The name of a configuration available on the application
server

username

Path/Header

Yes

The username for the Mongoose user

password

Path/Header

Required for
Header-based
request

The password for the Mongoose user

Headers
None
Request data
None
Response data
{

}

"Message": null,
"Success": true,
"Token": "b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…=="

Infor Mongoose IDO Integration with External Applications Guide | 116

Using the REST API
Example
This example code retrieves a Mongoose security token that can be used in succeeding requests.
string json = string.Empty;
using ( var client = new HttpClient() )
{
string config = "MG_DEV";
string username = "sa";
string password = string.Empty;
string requestUrl = $"http://server/IDORequestService/ido/token/{config}/{username}/{password}";
// send the get request
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
// get the response containing the token
json = result.Result;
}

InvokeIDOMethod
The InvokeIDOMethod API returns a list of properties and their attributes from an IDO collection.
POST

/invoke/{ido}

http://localhost/IDORequestService/invoke/UserNames?method=GetUserAttributes

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

method

Query

Yes

The name of the IDO method

Headers
Name

Description

Authorization

If the API is called directly, then the Mongoose security token
is obtained through a call to the GetSecurityToken API.
If the API is called through the ION API, then a valid OAuth2.0
bearer token is provided by the ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Infor Mongoose IDO Integration with External Applications Guide | 117

Using the REST API
Request data
[

]

"sa",
null,
null,
null,
null

Response data
{

}

"Message": null,
"Success": true,
"Parameters": [
"sa",
"4",
"1",
"CoreFormsAdmin",
""
],
"ReturnValue": "0"

Example
This example code determines the user attributes of user sa using the IDO method GetUserAttributes.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string method = "GetUserAttributes";
string requestUrl = $"http://server/IDORequestService/ido/invoke/{ido}?method={method}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
string[] parameters = new[] { "sa", "", "", "", "" };
// pass the array of parameters as the request data
string contentStr = JsonConvert.SerializeObject( parameters );
// send the post request
HttpResponseMessage response = client.PostAsync( requestUrl.ToString(), new StringContent(
contentStr, Encoding.UTF8, "application/json" ) ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

LoadCollection
The LoadCollection API is a basic load collection API that returns a set of records from an IDO collection.
Note: This topic is for REST version 2. See LoadCollection on page 74 for the REST version 1.

Infor Mongoose IDO Integration with External Applications Guide | 118

Using the REST API

GET

/load/{ido}

http://localhost/IDORequestService/ido/load/UserNames?properties=UserId,Username,UserDesc

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

properties

Query

No

A comma-delimited list of properties for which to return
values
You can also use an asterisk (*) to include all properties
except subcollection properties.
If this parameter is excluded or left blank, the server retrieves data with all the parameters.

filter

Query

No

This parameter restricts the result set. Specific approved
SQL functions are allowed in filter expressions. Currently,
the supported functions are GETDATE() and DATEADD(). All
other SQL functions, including aggregate functions are
blocked.
Unsupported functions, GROUP BY, HAVING, sub-queries, or
any non-white-listed expression will result in an IllegalF
ilterException.

orderby

Query

No

A comma-delimited list of properties that specifies how
the result set is to be sorted
Use the DESC keyword after a property name to sort that
property in descending order.

recordcap

Query

No

Determines how many records are to be retrieved in one
request
This list shows the valid values:
•
•

-1 returns 200 records.
0 returns all records.

Any other number specifies the number of records to be
retrieved.
distinct

Query

No

Specifies that the set of data to be returned must represent
only distinct combinations of properties

clm

Query

No

The name of a custom load method
This parameter works in conjunction with the clmparam
parameter.

clmparam

Query

No

A comma-delimited list of custom load method parameters

Infor Mongoose IDO Integration with External Applications Guide | 119

Using the REST API

Name

In

Required?

Description

loadtype

Query

No

This parameter is used for load collection paging.
This parameter is used in conjunction with the bookmark
parameter.
This list shows the valid values:
•
•
•
•

bookmark

Query

No

FIRST
NEXT
PREV
LAST

This parameter is used for the bookmark ID. Bookmark
IDs serve as a reference when you go to the next or previous records in a collection.
The bookmark value uses this format:
<B><P><p>UserId</p></P><D><f>false</f></D><F><v>1</v
></F><L><v>2</v></L></B>

where:
•
•
•
•
•
•
•

<B></B> is the bookmark envelope tag.
<P></P> is the property name of the primary key.
<D></D> is a flag that, if True, specifies that records are

to be read in descending order.
<f></f> is the value of the <D></D> element. This value
can be True or False.
<F></F> is the first row in the collection.
<L></L> is the last row in the collection.
<v><v> is the value of the row for the given property
name.

See Example: Bookmark IDs in LoadCollection responses
on page 164.
pqc

Query

No

Specifies a method to execute once for each row in the
result set after the query is completed
This parameter is the equivalent of the Design Mode PQ
option in Load/Save Overrides property and uses the
same syntax.

readonly

Query

No

When set to True, this parameter specifies that retrieved
records must not include the _ItemID property, which is
substantial for update and delete operations.

Infor Mongoose IDO Integration with External Applications Guide | 120

Using the REST API
Headers
Name

Description

Authorization

If the API is called directly, then the Mongoose security token
is obtained through a call to the GetSecurityToken API.
If the API is called through the ION API, then a valid OAuth2.0
bearer token is provided by the ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server.
This header is required only when using the Mongoose API
through the ION API.

Request data
None
Response data
{

"Items": [
{
"UserId": "1",
"Username": "sa",
"UserDesc": "System Admin"
},
{
"UserId": "2",
"Username": "jdoe",
"UserDesc": "John Doe"
}
],
"Bookmark": "<B><P><p>CollectionName</p><p>Development
Flag</p></P><D><f>false</f><f>false</f></D><F><v>ABDataServices</v><v>0</v></F><L><v>ABProjec
tRoles</v><v>0</v></L></B>",
"MoreRowsExist": true,
"Success": true,
"Message": null
}

Example
This example code retrieves records that contain the user ID, username, and user description from the Users
table.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/load/{ido}?properties=UserId,User
name,UserDesc";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

Infor Mongoose IDO Integration with External Applications Guide | 121

Using the REST API

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

}

UpdateCollection
The UpdateCollection API is used to insert, update, or delete one or more records from an IDO colleciton.
POST

/update/{ido}

http://localhost/IDORequestService/ido/update/UserNames?efresh=true

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

refresh

Query

No

Instructs the system to refresh the collection after the update is complete
Valid values are True or False.

Headers
Name

Description

Authorization

If the API is called directly, then the Mongoose security token
is obtained through a call to the GetSecurityToken API.
If the API is called through the ION API, then a valid OAuth2.0
bearer token is provided by the ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data for an Insert action
{

"Changes": [
{
"Action": 1,
"ItemId": "PBT=[UserNames]",
"Properties": [
{
"Name": "Username",
"Value": "jdelacruz",
"Modified": true,
"IsNull": false
},

Infor Mongoose IDO Integration with External Applications Guide | 122

Using the REST API
{

}

]

"Name": "UserDesc",
"Value": "Juan Dela Cruz",
"Modified": true,
"IsNull": false

}

}
],
"UpdateLocking": 1

Request data for an Update action
{

"Changes": [
{
"Action": 2,
"ItemId": "PBT=[UserNames] UserNames.DT=[2018-10-02 15:39:02.060] UserNames.ID=[4c9a96d0ba3c-4de4-8657-6d262f9dcd3f]",
"Properties": [
{
"Name": "UserDesc",
"Value": "John Doe Sr.",
"Modified": true,
"IsNull": false
}
],
"UpdateLocking": 1
}
]
}

Note: If "ItemId" is empty, then the key properties are used to update the row, and optimistic locking is not
used. All key properties must be included in the request when "ItemId" is empty.
Request data for a Delete action
{

"Changes": [
{

"Action": 4,
"ItemId": "PBT=[UserNames] UserNames.DT=[2018-12-14 13:52:27.737] u.ID=[265df7d2-802c4582-a774-a81b675a91a1]"
}
]
}

Note: If "ItemId" is empty, then the key properties are used to update the row, and optimistic locking is not
used. All key properties must be included in the request when "ItemId" is empty.
Response data for an Insert action
{

"Message": null,
"Success": true,
"RefreshItems": [
{
"Action": 1,
"ItemId": "PBT=[UserNames] UserNames.DT=[2019-05-03 13:24:07.080] UserNames.ID=[dd896b24-

Infor Mongoose IDO Integration with External Applications Guide | 123

Using the REST API
e1dd-47df-ad4f-f0378243c202]",
"ItemNo": 0,
"Properties": [
{
"IsNestedCollection": false,
"IsNull": false,
"Modified": false,
"Name": "Username",
"OriginalValue": null,
"Value": "jdelacruz"
},
{
"IsNestedCollection": false,
"IsNull": false,
"Modified": false,
"Name": "UserDesc",
"OriginalValue": null,
"Value": "Juan Dela Cruz"
}
],
"UpdateLocking": 0
}
]
}

Response data for an Update action
{

"Message": null,
"Success": true,
"RefreshItems": [
{
"Action": 2,
"ItemId": "PBT=[UserNames] UserNames.DT=[2019-05-03 13:24:07.080] UserNames.ID=[dd896b24e1dd-47df-ad4f-f0378243c202]",
"ItemNo": 0,
"Properties": [
{
"IsNestedCollection": false,
"IsNull": false,
"Modified": false,
"Name": "UserDesc",
"OriginalValue": null,
"Value": "John Doe Sr."
}
],
"UpdateLocking": 0
}
]
}

Response data for a Delete action
{

}

"Message": null,
"Success": true,
"RefreshItems": null

Example 1 - Insert using UpdateCollection
This example code inserts jdelacruz as a new user.
string json = string.Empty;
using ( var client = new HttpClient() )

Infor Mongoose IDO Integration with External Applications Guide | 124

Using the REST API
{

string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/update/{ido}?refresh=true";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
UpdateProperty username = new UpdateProperty
{
Name = "Username",
Value = "jdelacruz",
IsNull = false,
Modified = true
};
UpdateProperty userDesc = new UpdateProperty
{
Name = "UserDesc",
Value = "Juan Dela Cruz",
IsNull = false,
Modified = true
};
IDOUpdateItem idoItem = new IDOUpdateItem
{
Action = UpdateAction.Insert,
Properties = new[] { username, userDesc },
ItemId = "PBT=[UserNames]"
};

};

UpdateCollectionRequest request = new UpdateCollectionRequest { Changes = new[] { idoItem }
// pass the UpdateCollectionRequest as the request data
string contentStr = JsonConvert.SerializeObject( request );

// send the post request
HttpResponseMessage response = client.PostAsync( requestUrl.ToString(), new StringContent(
contentStr, Encoding.UTF8, "application/json" ) ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Example 2 - Update using UpdateCollection
This example code updates the description of user jdoe.
string json = string.Empty;
using ( var client = new HttpClient() )
{
string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/update/{ido}?refresh=true";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
UpdateProperty userDesc = new UpdateProperty
{
Name = "UserDesc",
Value = "John Doe Sr.",
IsNull = false,
Modified = true

Infor Mongoose IDO Integration with External Applications Guide | 125

Using the REST API
};
IDOUpdateItem idoItem = new IDOUpdateItem
{
Action = UpdateAction.Update,
Properties = new[] { userDesc },
ItemId = "PBT=[UserNames] UserNames.DT=[2019-05-03 13:24:07.080] UserNames.ID=[f33ef70819bb-4ab9-aade-88a6e8853742]"
};
};

UpdateCollectionRequest request = new UpdateCollectionRequest { Changes = new[] { idoItem }
// pass the UpdateCollectionRequest as the request data
string contentStr = JsonConvert.SerializeObject( request );

// send the post request
HttpResponseMessage response = client.PostAsync( requestUrl.ToString(), new StringContent(
contentStr, Encoding.UTF8, "application/json" ) ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Example 3 - Delete using UpdateCollection
This example code deletes the user with the given _ItemID property value.
string json = string.Empty;
using ( var client = new HttpClient() )
{
string ido = "UserNames";
string requestUrl = $"http://server/IDORequestService/ido/update/{ido}?refresh=true";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
IDOUpdateItem idoItem = new IDOUpdateItem
{
Action = UpdateAction.Delete,
ItemId = "PBT=[UserNames] UserNames.DT=[2019-05-03 13:24:07.080] UserNames.ID=[dd896b24e1dd-47df-ad4f-f0378243c202]"
};
};

UpdateCollectionRequest request = new UpdateCollectionRequest { Changes = new[] { idoItem }
// pass the UpdateCollectionRequest as the request data
string contentStr = JsonConvert.SerializeObject( request );

// send the post request
HttpResponseMessage response = client.PostAsync( requestUrl.ToString(), new StringContent(
contentStr, Encoding.UTF8, "application/json" ) ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 126

Using the REST API
You can use these classes to construct the Request Data as demonstrated in the previous code snippets:
public class UpdateCollectionRequest
{
public IDOUpdateItem[] Changes { get; set; }
public bool RefreshAfterSave { get; set; }
public string CustomInsert { get; set; }
public string CustomUpdate { get; set; }
public string CustomDelete { get; set; }
}
public class IDOUpdateItem
{
public UpdateAction Action { get; set; }
public string ItemId { get; set; }
public int ItemNo { get; set; }
public UpdateProperty[] Properties { get; set; }
public UpdateLocking UpdateLocking { get; set; }
}
public class UpdateProperty
{
public string Name { get; set; }
public string Value { get; set; }
public string OriginalValue { get; set; }
public bool Modified { get; set; }
public bool IsNull { get; set; }
}
public enum UpdateAction
{
Insert = 1,
Update = 2,
Delete = 4
}
public enum UpdateLocking
{
Row = 0,
Property = 1
}

UploadDocumentObject
The UploadDocumentObject API uploads a streamed file to an IDO document object.
Note: This topic is for REST version 2. See Upload Document Object on page 103 for the REST version 1.
POST

/docobj/{ido}

http://localhost/IDORequestService/ido/docobj/UserNames?rowPointer=8c66c936-d348-4c13-868469bc6fa10473&name=WeeklyReport&desc=Weekly%20Report&ext=xls

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the referenced IDO collection

itemid

Query

Yes

The value of the _ItemID property for the document object

Infor Mongoose IDO Integration with External Applications Guide | 127

Using the REST API

Name

In

Required?

Description

rowPointer

Query

No

The value of the referenced IDO row pointer
Note: Use this parameter to upload a new document object and link the object to the referenced IDO and row
pointer.

name

Query

No

The name of the document object

desc

Query

No

The description of the document object

ext

Query

No

The file extension for the document objects

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

Request data
Unencoded binary stream of the file.
Response data
{

"Message": null,
"Success": true,
"ItemId": "PBT=[DocumentObjectAndRefView] doc.ID=[7018e6d1-7397-466a-be43-17c52d51c3d6]
doc.DT=[2018-11-22 09:30:16.643]"
}

Example
This example code uploads a file and attaches it as a document object of the referenced IDO and IDO row
pointer.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string rowpointer = "4d6cb1eb-e4fc-4e12-aae8-95ff1086ee8c";
string requestUrl = $"http://server/IDORequestService/ido/docobj/{ido}?rowpointer={rowpoint
er}&name=WeeklyReport&desc=Weekly%20report%20for%20this%20week&ext=docx";

Infor Mongoose IDO Integration with External Applications Guide | 128

Using the REST API

// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
// select an image or a file and include it as the request payload
OpenFileDialog dialog = new OpenFileDialog();
byte[] file = new byte[] { };
if ( dialog.ShowDialog() == DialogResult.OK )
{
file = File.ReadAllBytes( dialog.FileName );
}
// pass the file as the request data and send the post request
HttpResponseMessage response = client.PostAsync( requestUrl, new ByteArrayContent( file )
).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

UploadFileStream
The UploadFileStream API uploads a streamed file to an IDO collection. The IDO collection must have a
property for storing binary data.
Note: This topic is for REST version 2. Seer Upload File Stream on page 101 for the REST version 1.
POST

/file/{ido}

http://localhost/IDORequestService/ido/file/UserNames?property=UserImage&itemId=PBT=[UserNames]
UserNames.DT=[2018-09-0511:01:00.167] UserNames.ID=[7d38e4fb-e1a8-4af7-936b-50accee5d35b]

Parameters
Name

In

Required?

Description

ido

Path

Yes

The name of the IDO collection

property

Query

Yes

The name of the IDO property to be used for storing binary
data

itemid

Query

Yes

The value of the _itemID property

Headers
Name

Description

Authorization

If the API is called directly, then a Mongoose security token is
obtained through a call to the GetSecurityToken API.
If the API is called through ION API, then a valid OAuth2.0
bearer token is provided by ION API.

Infor Mongoose IDO Integration with External Applications Guide | 129

Using the REST API

Name

Description

X-Infor-MongooseConfig

The name of a configuration that is available on the application
server
This header is required only when using the Mongoose API
through the ION API.

X-IdoName

The name of the IDO collection

X-Property

The name of the IDO property

X-RowPointer

The value of the IDO row pointer

Request data
Unencoded binary stream of the file.
Response data
{
}

"Message": null,
"Success": true

Example
This example code uploads a user image to the Users table.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string ido = "UserNames";
string property = "UserImage";
string itemid = "PBT=[UserNames] UserNames.DT=[2019-05-29 14:12:02.000] UserNames.ID=[4d6cb1ebe4fc-4e12-aae8-95ff1086ee8c]";
string requestUrl = $"http://server/IDORequestService/ido/file/{ido}?property={proper
ty}&itemid={itemid}";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQj…==" );
// select an image or a file and include it as the request payload
OpenFileDialog dialog = new OpenFileDialog();
byte[] file = new byte[] { };
if ( dialog.ShowDialog() == DialogResult.OK )
{
file = File.ReadAllBytes( dialog.FileName );
}
// pass the file as the request data and send the post request
HttpResponseMessage response = client.PostAsync( requestUrl, new ByteArrayContent( file )
).Result;
using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;

Infor Mongoose IDO Integration with External Applications Guide | 130

Using the REST API

}

}

Version 2 dynamic API endpoints
This list shows the Mongoose REST version 2 dynamic API endpoints:
•
•
•
•

Get Swagger Collection Document
Get IDO Swagger Document
Execute IDO Swagger Document Get Operation
Execute IDO Swagger Document Post Operation

If you consume these APIs directly from IDORequestService, you must include the X-Infor-MongooseConfig
header. All API calls, except Get Swagger Collection Document and Get IDO Swagger Document, must include
an Authorization header that contains a Mongoose security token.
If you consume these APIs from the ION API interface, all API calls, except Get Swagger Collection Document
and Get IDO Swagger Document, must include the X-Infor-Mongoose Config header and the Authorization
header that contains a valid OAuth2.0 bearer token.
If you consume these APIs in ION API through third-party applications, for example, Postman, you must include
the X-Infor-MongooseConfig header and the Authorization header that contains a valid OAuth 2.0 bearer
token.
We recommend that you use the presented prototypes as examples when you create your API calls. All these
prototype examples use C# code.

Get Swagger Collection Document
The Get Swagger Collection Document API returns a swagger collection that contains references to individual
IDO swagger documents.
GET

/dynamic/api-docs-collection

http://localhost/IDORequestService/ido/dynamic/api-docs-collection?search=UserNames

Parameters
Name

In

Required

Description

search

Query

No

Searches a string against the entity, category, swagger endpoint, or description
within the swagger collection

pageNumber

Query

No

Specifies the page number
If this parameter is excluded or left blank,
the server defaults the value to 1.

Infor Mongoose IDO Integration with External Applications Guide | 131

Using the REST API

Name

In

Required

Description

pageSize

Query

No

Specifies the number of items to show
per page.
If this parameter is excluded or left blank,
the server defaults the value to 10.

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
Response data
{

"info": {
"description": "Infor Mongoose",
"title": "Infor Mongoose Swagger Collection",
"version": "1.0"
},
"entityName": "Business Class",
"entityType": "Business Class",
"paging": {
"total": 30,
"pageNumber": 1,
"pageSize": 30
},
"swaggerCollection": {
"swagger": [
{
"category": "dynamic",
"entity": "AccessAs",
"desc": "Access As IDO",
"swaggerEndpoint": "../api-docs-collection/AccessAs"
},
{
"category": "dynamic",
"entity": "ActiveBGTask",
"desc": "ActiveBGTask",
"swaggerEndpoint": "../api-docs-collection/ActiveBGTask"
},
{
"category": "dynamic",
"entity": "UserNames",
"desc": "UserNames",
"swaggerEndpoint": "../api-docs-collection/UserNames"
}
]

Infor Mongoose IDO Integration with External Applications Guide | 132

Using the REST API
}

}

Example
This example code retrieves the swagger collection that contains a list of IDO swagger document endpoints.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string requestUrl = $"http://localhost/IDORequestService/ido/dynamic/api-docs-collection";
// provide the configuration name in the X-Infor-MongooseConfig header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"X-Infor-MongooseConfig",
"Mongoose" );
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Get IDO Swagger Document
The Get IDO Swagger Document API returns an IDO swagger document that contains strongly typed APIs.
These strongly typed APIs are generated using the REST API Wizard for the specified IDO collection.
GET

/dynamic/api-docs-collection/{ido}

http://localhost/IDORequestService/ido/dynamic/api-docs-collection/UserNames

Parameters
Name

In

Required

Description

ido

Path

Yes

The name of the IDO collection that is
associated with the strongly typed API

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

Infor Mongoose IDO Integration with External Applications Guide | 133

Using the REST API
Request data
None
Response data
{

"swagger": "2.0",
"info": {
"description": "UserNames",
"version": "v2",
"title": "UserNames",
"contact": {
"name": "Infor",
"url": "https://www.inforxtreme.com"
}
},
"basePath": "/YEL1_DEV/MONGOOSE_DEV/IDORequestService/ido/dynamic",
"tags": [
{
"name": "default",
"description": ""
}
],
"schemes": [
"https"
],
"paths": {
"/GetMyUserAttributes": {
"post": {
"tags": [
"default"
],
"summary": "GetMyUserAttributes",
"description": "Get My User Attributes",
"operationId": "GetMyUserAttributes",
"consumes": [
"application/json"
],
"produces": [
"application/json"
],
"parameters": [
{
"name": "X-Infor-MongooseConfig",
"in": "header",
"required": false,
"description": "Mongoose configuration to log into; required when using the
service through ION API, not needed otherwise.",
"type": "string"
}
],
"responses": {
"200": {
"description": "See Success and Message in the response for result status.",
"schema": {
"$ref": "#/definitions/InvokeMethodResponse"
}
},
"401": {
"$ref": "#/responses/Unauthorized"
}
}
}
},
"/GetUserNames": {
"get": {
"tags": [
"default"
],

Infor Mongoose IDO Integration with External Applications Guide | 134

Using the REST API
"summary": "GetUserNamesOnly",
"description": "Get Usernames Only",
"operationId": "GetUserNamesOnly",
"produces": [
"application/json"
],
"parameters": [
{
"name": "X-Infor-MongooseConfig",
"in": "header",
"required": false,
"description": "Mongoose configuration to log into; required when using the
service through ION API, not needed otherwise.",
"type": "string"
}
],
"responses": {
"200": {
"description": "See Success and Message in the response for result status.",
"schema": {
"$ref": "#/definitions/LoadCollectionResponse"
}
},
"401": {
"$ref": "#/responses/Unauthorized"
}
}
}
}
},
"definitions": { ... },
"responses": { ... }
}

Example
This example code retrieves the IDO swagger document that contains strongly typed APIs. These strongly
typed APIs are generated for the AccessAs IDO.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string requestUrl = $"http://localhost/IDORequestService/ido/dynamic/api-docs-collection/Ac
cessAs";
// provide configuration name in the X-Infor-MongooseConfig header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"X-Infor-MongooseConfig",
"Mongoose" );
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Execute IDO Swagger Document Get Operation
The Execute IDO Swagger Document Get Operation API executes a strongly typed API for the specified IDO
collection. This API is only applicable for strongly typed APIs that are LoadCollection or GetPropertyInformation.

Infor Mongoose IDO Integration with External Applications Guide | 135

Using the REST API

GET

/dynamic/api-docs-collection/{ido}/{operationID}

http://localhost/IDORequestService/ido/dynamic/api-docs-collection/UserNames/GetUserList

Parameters
Name

In

Required

Description

ido

Path

Yes

The name of the IDO collection that is
associated with the strongly typed API

operationID

Path

Yes

The name of the strongly typed API to be
executed

properties

Query

No

A comma-delimited list of properties for
which to return values
You can use an asterisk (*) to include all
properties, except subcollection properties.
If this parameter is excluded or left blank,
the server retrieves data with all the parameters.
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

filter

Query

No

Restricts the result set. Any valid SQL WHE
RE clause syntax is allowed
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

orderby

Query

No

A comma-delimited list of properties that
specifies how the result set is to be sorted
Use the DESC keyword after a property
name to sort that property in descending
order.
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

recordcap

Query

No

Determines how many records are to be
retrieved in one request
This list shows the valid values:
•
•
•

-1 returns 200 records.
0 returns all records.

Any other number specifies the
number of records to be retrieved.

Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

Infor Mongoose IDO Integration with External Applications Guide | 136

Using the REST API

Name

In

Required

Description

distinct

Query

No

Specifies that the set of data to be returned must represent only distinct
combinations of properties
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

clm

Query

No

The name of a custom load method
This parameter works in conjunction with
the clmparam parameter.
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

clmparam

Query

No

A comma-delimited list of custom load
method parameters
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

loadtype

Query

No

This parameter is used for load collection
paging.
This parameter is used in conjunction
with the bookmark parameter.
This list shows the valid values:
•
•
•
•

FIRST
NEXT
PREV
LAST

Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

Infor Mongoose IDO Integration with External Applications Guide | 137

Using the REST API

Name

In

Required

Description

bookmark

Query

No

This parameter is used for the bookmark
ID.
Bookmark IDs serve as a reference when
you get to the next or previous records in
a collection.
The bookmark value uses this format:
<B><P><p>UserId</p></P><D><f>false</f
></D><F><v>1</v

>

</F><L><v>2</v></L></B>

where:
•

<B></B> is the bookmark envelope

tag.
•
•

•

•
•
•

<P></P> is the property name of the

primary key.
<D></D> is a flag that, if True, specifies
that records are to be read in descending order.
<f></f> is the value of the <D></D>
element. This value can be True or
False.
<F></F> is the first row in the collection.
<L></L> is the last row in the collection.
<v></v> is the value of the row for the
given property name.

Note: Use this parameter only if the
strongly typed API is a LoadCollection API.
pqc

Query

No

Specifies a method to execute once for
each row in the result set, after the query
is completed
This parameter is equivalent to the Design
Mode PQ option in the Load/Save Overrides property and uses the same syntax.
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

readonly

Query

No

When set to True, this parameter specifies
that retrieved records must not include
the _ItemID property, which is substantial
for update and delete operations.
Note: Use this parameter only if the
strongly typed API is a LoadCollection API.

Infor Mongoose IDO Integration with External Applications Guide | 138

Using the REST API
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
Response data when the strongly typed API is a GetPropertyInformation API
{

"Message": null,
"Success": false,
"Keys": [
"ID"
],
"Properties": [
{
"BooleanFalseValue": "",
"BooleanTrueValue": "",
"CaseFormat": "",
"ClrTypeName": "Guid",
"ColumnDataType": "uniqueidentifier",
"DataType": "GUID",
"DateFormat": "",
"DecimalPos": 0,
"DefaultIMECharset": "",
"DefaultValue": "NEWGUID()",
"DomainIDOName": "",
"DomainListProperties": "",
"DomainProperty": "",
"InputMask": "",
"IsItemWarnings": false,
"JustifyFormat": "",
"LabelStringID": "sID",
"Length": 0,
"Name": "ID",
"PropertyClass": "",
"RORecord": false,
"ReadOnly": false,
"Required": true
},
{ ... }
],
"SubCollections": [
{
"IDOName": "DemoSubmissionComments",
"LinkBy": [
{
"Child": "SubmissionID",
"Parent": "ID"
}
],
"Name": "SubComments"
},
{

Infor Mongoose IDO Integration with External Applications Guide | 139

Using the REST API

}

]

}

"IDOName": "DemoSubmissionLikes",
"LinkBy": [
{
"Child": "SubmissionID",
"Parent": "ID"
}
],
"Name": "SubLikes"

Response data when the strongly typed API is a LoadCollection API
{

"Items": [
{
"UserId": "1",
"Username": "sa",
"_ItemId": "PBT=[UserNames] UserNames.DT=[2022-05-19 11:13:29.047] User
Names.ID=[37d1b76a-4bf7-4523-9176-ae8ed460d903]"
},
{
"UserId": "2",
"Username": "jdelacruz",
"_ItemId": "PBT=[UserNames] UserNames.DT=[2021-04-05 15:44:58.937] User
Names.ID=[f5f084a7-0049-4b69-9e74-a38095125eb3]"
},
{
"UserId": "3",
"Username": "jdoe",
"_ItemId": "PBT=[UserNames] UserNames.DT=[2021-04-05 15:52:34.833] User
Names.ID=[7850b9c8-2421-4956-b4fb-983ef76d3e58]"
}
],
"Bookmark": "<B><P><p>UserId</p></P><D><f>false</f></D><F><v>1</v></F><L><v>50004</v></L></B>",

}

"Success": true,
"Message": null

Example
This example code executes a strongly typed API that does load collection to retrieve the AccessAs IDO.
string json = string.Empty;
using ( HttpClient client = new HttpClient() )
{
string requestUrl = $"http://localhost/IDORequestService/ido/dynamic/api-docs-collection/Ac
cessAs/GetAccessAs";
// provide token in the Authorization header
client.DefaultRequestHeaders.TryAddWithoutValidation(
"Authorization",
"b/XdI6IQzCviZOGJ0E+002DoKUFOPmVDkwpQDbQjm3w/=…" );
HttpResponseMessage response = client.GetAsync( requestUrl ).Result;

}

using ( HttpContent content = response.Content )
{
Task<string> result = content.ReadAsStringAsync();
json = result.Result;
}

Infor Mongoose IDO Integration with External Applications Guide | 140

Using the REST API

Execute IDO Swagger Document Post Operation
The Execute IDO Swagger Document Post Operation API executes a strongly typed API for the specified IDO
collection. This API is only applicable for strongly typed APIs that are UpdateCollection or InvokeIDOMethod.
GET

/dynamic/api-docs-collection/{ido}/{operationID}

http://localhost/IDORequestService/ido/dynamic/api-docs-collection/UserNames/CreateUser

Parameters
Name

In

Required

Description

ido

Path

Yes

