---
name: "syteline-dotnet-client"
description: ".NET client class library - OpenSession, CloseSession, GetConfigurations, GetPropertyInfo, LoadCollection, UpdateCollection, Invoke, bookmark IDs"
version: "1.0"
tags:
  - syteline
  - dotnet
  - api
  - mongoose
---

# syteline-dotnet-client

Source: Infor Mongoose IDO Integration with External Applications Guide, Release 2026.x


Parameters
Name

Description

strSessionToken

This is the session token obtained through a call to the CreateSessionToken
API.

updateJsonObject

This parameter specifies a JSON object that contains records to be updated.

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
None
Example 1 – Insert action using SaveJson
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
// property values to insert

Infor Mongoose IDO Integration with External Applications Guide | 153

About the SOAP web service
PropertyStatusPair username = new PropertyStatusPair() { Property = "jdoe1",
Updated = true };
PropertyStatusPair userDesc = new PropertyStatusPair() { Property = "John Doe", Updated = true
};
// row to insert
SimpleIDOItem idoItem = new SimpleIDOItem
{
EditStatus = SimpleIDOItem.Modified.Inserted,
Properties = new List<PropertyStatusPair>() { username, userDesc }
};
SimpleIDOItemList insertRequest = new SimpleIDOItemList
{
IDOName = "UserNames",
PropertyList = new[] { "Username", "UserDesc" },
Items = new List<SimpleIDOItem>()
};
insertRequest.Items.Add( idoItem );
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
// provide the insertRequest as the payload
string payload = JsonConvert.SerializeObject( insertRequest );
string result = soapClient.SaveJson( sessionToken, payload, string.Empty,
string.Empty, string.Empty );

Example 2 – Update action using SaveJson
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
// property values to update
PropertyStatusPair userDesc = new PropertyStatusPair() { Property = "John Doe Sr.", Updated =
true };
// row to update
SimpleIDOItem idoItem = new SimpleIDOItem
{
EditStatus = SimpleIDOItem.Modified.Modified,
ID = "PBT=[UserNames] UserNames.DT=[2019-07-29 12:54:00.937] UserNames.ID=[6565e11a-80d8-43deb91e-951df7867816]",
Properties = new List<PropertyStatusPair>() { userDesc }
};
SimpleIDOItemList updateRequest = new SimpleIDOItemList
{
IDOName = "UserNames",
PropertyList = new[] { "UserDesc" },
Items = new List<SimpleIDOItem> { idoItem }
};
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
// provide the updateRequest as the payload
string payload = JsonConvert.SerializeObject( updateRequest );
string result = soapClient.SaveJson( sessionToken, payload, string.Empty,
string.Empty, string.Empty );

Example 3 – Delete action using SaveJson
string sessionToken = "b/XdI6IQzCviZOGJ0E+002…5vl903teP0jSDwkFs";
PropertyStatusPair userDesc = new PropertyStatusPair() { Property = "John Doe Sr.", Updated =
true };
// row to delete
SimpleIDOItem idoItem = new SimpleIDOItem

Infor Mongoose IDO Integration with External Applications Guide | 154

About the SOAP web service
{

EditStatus = SimpleIDOItem.Modified.Deleted,
ID = "PBT=[UserNames] UserNames.DT=[2019-07-29 12:55:22.173] UserNames.ID=[41ef60ef-1ec8-47409a52-ea361fbe83de]",
Properties = new List<PropertyStatusPair>()
};
SimpleIDOItemList deleteRequest = new SimpleIDOItemList
{
IDOName = "UserNames",
PropertyList = new[] { "UserDesc" },
Items = new List<SimpleIDOItem>()
};
deleteRequest.Items.Add( idoItem );
IDOWebService.DOWebServiceSoapClient soapClient = new
IDOWebService.DOWebServiceSoapClient();
// provide the deleteRequest as the payload
string json = JsonConvert.SerializeObject( deleteRequest );
string result = soapClient.SaveJson( sessionToken, json, string.Empty,
string.Empty, string.Empty );

Helper classes for the examples
You can use these classes to help construct the request as demonstrated in the code snippets above:
public class SimpleIDOItemList
{
public string IDOName { get; set; }
public string[] PropertyList { get; set; }
public List<SimpleIDOItem> Items;
}
public class SimpleIDOItem
{
public enum Modified
{
Unmodified,
Modified,
Deleted,
Inserted
}

}

public string ID { get; set; }
public List<PropertyStatusPair> Properties;
public Modified EditStatus { get; set; }

public struct PropertyStatusPair
{
public string Property { get; set; }
public bool Updated { get; set; }
}

Infor Mongoose IDO Integration with External Applications Guide | 155

About the Mongoose .NET client class library

Chapter 5: About the Mongoose .NET client class library

The .NET client class library is ideal to use for external application communications to access and perform
IDO-related operations, such as loading collections, updating collections, or running methods. This library
is another layer on top of the IDO request interface and uses the IDO request XML schema.

Using .NET commands
To open sessions or access configuration information from an external application, perform this procedure:
1

Create a .NET application based on .NET 4.7.2, using the language of your choice.
If you are using Visual Studio, you must use version 2019 or later.

2

Add these references in your project definition to these Mongoose framework’s class libraries:
•
IDOProtocol.dll
•
IDORequestClient.dll
•
MGShared.dll
•
WSEnums.dll
•
WSFormServerProtocol.dll

Supported .NET commands
This list shows the .NET commands supported by Mongoose:
•
•
•
•
•
•
•

OpenSession on page 157
CloseSession on page 157
GetConfigurations on page 158
GetPropertyInfo on page 158
LoadCollection on page 159
UpdateCollection on page 161
Invoke on page 162

Infor Mongoose IDO Integration with External Applications Guide | 156

About the Mongoose .NET client class library

OpenSession
The OpenSession method validates the user’s identity and creates a new session in the application. This is
essentially the same as the user logging into the application where the caller can communicate with the IDO.
Example
This example code opens a client session that accesses the IDO runtime using credentials such as the user
ID, user password, and the configuration name. It also uses the URL in requestServiceURL, where the URL
follows this pattern:
http://servername/IDORequestService/RequestService.aspx

where servername is the DNS or IP address of the server on which the Mongoose application/configuration
resides.
bool logonSucceeded = false;
Client client = new Client( requestServiceURL, IDOProtocol.Http );
OpenSessionResponseData response = default( OpenSessionResponseData );
using ( client )
{
// Substitute a valid username, password, and configuration name:
response = client.OpenSession( username, password, config );
logonSucceeded = response.LogonSucceeded;
}

Note: For the second argument for Client, you can also use IDOProtocol.Ipc. This indicates that the client
class should connect to the IDO Runtime Service on the local machine. In this case, requestServiceURL is
ignored.
This is equivalent to using the default constructor:
Client client = new Client();

CloseSession
The CloseSession method is used to close an existing Mongoose session. This is essentially the same as logging
out from the application.
Example
This example code terminates an existing session.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
using ( client )
{
client.CloseSession();
}

Infor Mongoose IDO Integration with External Applications Guide | 157

About the Mongoose .NET client class library

GetConfigurations
The GetConfigurations method returns a ConfigurationInfo class which includes a subset of configuration
properties and all the referenced application properties.
The Client class accesses configuration information through the ConfigServer.aspx page in conjunction with
the IDO Runtime Service and IDORuntimeHost.exe developer tool. The Web Server application, IDO Runtime
Service, and IDORuntimeHost.exe are all installed as part of a basic server installation.
Example 1 – Get a list of configurations
This example code retrieves an array of configuration information classes from the Default configuration
group through the ConfigServer.aspx page in IDORequestService, where the URL is like this:
http://servername/IDORequestService/ConfigServer.aspx

where servername is the DNS or IP address of the server where the Mongoose configurations are installed
and accessed.
ConfigurationInfoList configList = Client.GetConfigurations();
// Config info can be enumerated as follows
foreach ( ConfigurationInfo config in configList )
{
Console.WriteLine( config.Name );
}

Note: The IDORuntimeHost.exe must be running for this method to work.
Example 2 – Get the configurations for a specific configuration group
This example code retrieves an array of configuration information from a specified configuration server and
configuration group name, using the IDORuntimeHost.exe.
ConfigurationInfoList configList = Client.GetConfigurations( configServerURL, configGroup );
// Config info can be enumerated as follows
foreach ( ConfigurationInfo config in configList )
{
Console.WriteLine( config.Name );
}

Note: The IDORuntimeHost.exe must be running for this method to work.

GetPropertyInfo
The GetPropertyInfo method retrieves a list of properties and their attributes from a collection, or the columns
in a database table.
For example, if you ask for the properties of UserNames IDO, the response document returns information like
the name of the base table used by the collection and for each property, its data type, label string, class, and
so on.
Infor Mongoose IDO Integration with External Applications Guide | 158

About the Mongoose .NET client class library
Example
This example code retrieves an array of properties and their attributes from a specified IDO.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
GetPropertyInfoResponseData response = default( GetPropertyInfoResponseData );
using ( client )
{
response = client.GetPropertyInfo( idoName );

}

// Property info can be enumerated as follows
foreach ( PropertyInfo prop in response.Properties )
{
// Do something...
Console.WriteLine( prop.Name );
}

LoadCollection
The LoadCollection method uses the LoadCollection method of an IDO to query either an IDO collection or a
database table and return the results to the user.
Example 1 – Basic load collection
This example code retrieves the user ID, name, and description from the Users table.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
LoadCollectionResponseData response = default( LoadCollectionResponseData );
using ( client )
{
LoadCollectionRequestData request = new LoadCollectionRequestData()
{
IDOName = "UserNames",
PropertyList = new PropertyList( "UserId, Username, UserDesc" ),
RecordCap = -1
};
response = client.LoadCollection( request );

}

// Property info can be enumerated as follows
foreach ( IDOItem item in response.Items )
{
// Do something...
foreach ( IDOPropertyValue property in item.PropertyValues )
{
Console.WriteLine( property.Value );
}
}

Example 2 – Custom load collection
This example code retrieves the note content and description from the Object Notes table using the custom
load method GetNotesSp.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
LoadCollectionResponseData response = default( LoadCollectionResponseData );

Infor Mongoose IDO Integration with External Applications Guide | 159

About the Mongoose .NET client class library
using ( client )
{
InvokeParameterList clmParameters = new InvokeParameterList
{
"UserNames",
"4d6cb1eb-e4fc-4e12-aae8-95ff1086ee8c"
};
CustomLoadMethod clm = new CustomLoadMethod
{
Name = "GetNotesSp",
Parameters = clmParameters
};
LoadCollectionRequestData request = new LoadCollectionRequestData()
{
IDOName = "ObjectNotes",
PropertyList = new PropertyList( "SpcnNoteContent, SpcnNoteDesc" ),
RecordCap = -1,
CustomLoadMethod = clm
};
response = client.LoadCollection( request );

}

// Property info can be enumerated as follows
foreach ( IDOItem item in response.Items )
{
// Do something...
foreach ( IDOPropertyValue property in item.PropertyValues )
{
Console.WriteLine( property.Value );
}
}

Example 3 – Nested load collection
This example code is for a nested or hierarchical request. This request queries the Users table and the User
Emails tables in a single request, to get the usernames and associated email addresses for each user.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
LoadCollectionResponseData response = default( LoadCollectionResponseData );
using ( client )
{
LoadCollectionRequestData emailsRequest = new LoadCollectionRequestData
{
IDOName = "UserEmails",
PropertyList = new PropertyList( "EmailAddress, EmailType" ),
RecordCap = -1
};
// Set the relationship data of the child and parent IDOs
emailsRequest.SetLinkBy( "UserId", "UserId" );
LoadCollectionRequestData usersRequest = new LoadCollectionRequestData
{
IDOName = "UserNames",
PropertyList = new PropertyList( "UserId, Username, UserDesc" ),
RecordCap = -1
};
// Nest the user emails LoadCollection request inside the user LoadCollection request
usersRequest.AddNestedRequest( emailsRequest );
response = client.LoadCollection( usersRequest );
// Property info can be enumerated as follows
foreach ( IDOItem item in response.Items )
{
// Do something...
foreach ( IDOPropertyValue property in item.PropertyValues )

Infor Mongoose IDO Integration with External Applications Guide | 160

About the Mongoose .NET client class library
{

}

}

}

Console.WriteLine( property.Value );

UpdateCollection
The UpdateCollection method modifies a collection (inserting, updating, or deleting records) using the
UpdateCollection method of an IDO.
Example 1 – Basic Insert, Update, and Delete operations using UpdateCollection
This example code shows how you can perform different actions for each row in the collection within a single
request. This request creates a new user, updates the description of user wsmith, and deletes the record of
user cdelune.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
UpdateCollectionResponseData response = default( UpdateCollectionResponseData );
using ( client )
{
// item to insert
IDOUpdateItem inserItem = new IDOUpdateItem( UpdateAction.Insert );
inserItem.Properties.Add( new IDOUpdateProperty( "Username", "jdoe", true ) );
inserItem.Properties.Add( new IDOUpdateProperty( "UserDesc", "John Doe", true ) );
// item to update
IDOUpdateItem updateItem = new IDOUpdateItem( UpdateAction.Update, "PBT=[UserNames] User
Names.DT=[2019-08-15 17:30:09.870] UserNames.ID=[b3aedc23-722f-4058-b12c-f14bdfd955b4]" );
inserItem.Properties.Add( new IDOUpdateProperty( "UserDesc", "Will Smith Sr.", true ) );
// item to delete
IDOUpdateItem deleteItem = new IDOUpdateItem( UpdateAction.Delete, "PBT=[UserNames] User
Names.DT=[2019-08-15 17:30:52.253] UserNames.ID=[018ae411-42c3-48cb-a50b-b943dcb70dbe]" );
IDOUpdateItems updateItems = new IDOUpdateItems() { inserItem, updateItem, deleteItem };
UpdateCollectionRequestData request = new UpdateCollectionRequestData()
{
IDOName = "UserNames",
Items = updateItems,
RefreshAfterUpdate = true
};
response = client.UpdateCollection( request );

}

// Updated IDO items can be enumerated as follows
foreach ( IDOUpdateItem item in response.Items )
{
// Do something...
foreach ( IDOUpdateProperty property in item.Properties )
{
Console.WriteLine( property.Value );
}
}

Infor Mongoose IDO Integration with External Applications Guide | 161

About the Mongoose .NET client class library
Example 2 – Nested update collection operations
This is an example code for a nested or hierarchical request. This request inserts a user-defined type and its
value in a single request.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
UpdateCollectionResponseData response = default( UpdateCollectionResponseData );
using ( client )
{
// Create an UpdateCollection request for the child IDO
IDOUpdateItem userDefTypeVal = new IDOUpdateItem( UpdateAction.Insert );
userDefTypeVal.Properties.Add( new IDOUpdateProperty( "TypeName", "Month", true ) );
userDefTypeVal.Properties.Add( new IDOUpdateProperty( "Value", "January", true ) );
IDOUpdateItems userDefTypeValItems = new IDOUpdateItems() { userDefTypeVal };
UpdateCollectionRequestData userDefTypeValRequest = new UpdateCollectionRequestData
{
IDOName = "UserDefinedTypeValues",
RefreshAfterUpdate = true,
Items = userDefTypeValItems
};
// Set the relationship data of the child and parent IDOs
userDefTypeValRequest.SetLinkBy( "Name", "TypeName" );
// Create an UpdateCollection request for the parent IDO
IDOUpdateItem userDefType = new IDOUpdateItem( UpdateAction.Insert );
userDefType.Properties.Add( new IDOUpdateProperty( "Description", "An amount of time used
with calendars", true ) );
userDefType.Properties.Add( new IDOUpdateProperty( "Name", "Month", true ) );
// Nest the user emails LoadCollection request inside the user LoadCollection request
userDefType.AddNestedUpdate( userDefTypeValRequest );
IDOUpdateItems userDefTypeItems = new IDOUpdateItems() { userDefType };
UpdateCollectionRequestData userDefTypeRequest = new UpdateCollectionRequestData
{
IDOName = "UserDefinedTypes",
RefreshAfterUpdate = true,
Items = userDefTypeItems
};
response = client.UpdateCollection( userDefTypeRequest );

}

// Updated IDO items can be enumerated as follows
foreach ( IDOUpdateItem item in response.Items )
{
// Do something...
foreach ( IDOUpdateProperty property in item.Properties )
{
Console.WriteLine( property.Value );
}
}

Invoke
The Invoke method executes an IDO method. This method can be code in a custom assembly, or it can be a
stored procedure.

Infor Mongoose IDO Integration with External Applications Guide | 162

About the Mongoose .NET client class library
Example
This example code determines the user attributes of user jdoe using the IDO method GetUserAttributes.
Client client = new Client( requestServiceURL, IDOProtocol.Http );
InvokeResponseData response = default( InvokeResponseData );
using ( client )
{
InvokeParameterList parameters = new InvokeParameterList
{
"jdoe",
{ string.Empty, true },
{ string.Empty, true },
{ string.Empty, true },
{ string.Empty, true }
};
InvokeRequestData request = new InvokeRequestData
{
IDOName = "UserNames",
MethodName = "GetUserAttributes",
Parameters = parameters
};
response = client.Invoke( request );

}

// Method parameters can be enumerated as follows
foreach ( InvokeParameter parameter in response.Parameters )
{
// Do something...
Console.WriteLine( parameter.Value );
}

Infor Mongoose IDO Integration with External Applications Guide | 163

Example: Bookmark IDs in LoadCollection responses

Appendix A: Example: Bookmark IDs in LoadCollection
responses

This topic shows an example of a bookmark ID used in a LoadCollection response to an API request.
This response includes the data that is requested from the UserNames IDO, such as UserID, UserName and
UserDesc, and an initial bookmark ID.
{
"Bookmark": "<B><P><p>UserId</p></P><D><f>false</f></D><F><v>1</v></F><L><v>2</v></L></B>",
"Items": [
[
{
"Name": "UserId",
"Value": "1"
},
{
"Name": "Username",
"Value": "sa"
},
{
"Name": "UserDesc",
"Value": "WinStudio Admin user"
},
{
"Name": "_ItemId",
"Value": "PBT=[UserNames] UserNames.DT=[2019-06-13 14:02:44.003] UserNames.ID=[407c7a60c627-436f-88b0-2bd544a07208]"
}
],
[
{
"Name": "UserId",
"Value": "2"
},
{
"Name": "Username",
"Value": "FTAutomation"
},
{
"Name": "UserDesc",
"Value": null
},
{
"Name": "_ItemId",
"Value": "PBT=[UserNames] UserNames.DT=[2019-06-13 14:02:44.070] UserNames.ID=[cfb7d8230f3c-4c27-b61e-618ad8dc2b5f]"
}
]
],
"MoreRowsExist": true,
"Message": "Success",
"MessageCode": 0

Infor Mongoose IDO Integration with External Applications Guide | 164

Example: Bookmark IDs in LoadCollection responses

}

Since rowcap=2 is specified, only two records are available initially. To get the next two records, specify the
bookmark ID and the load type value Next in the next request:
http://server/IDORequestService/MGRESTService.svc/json/UserNames/adv?props=UserId,Username,UserDe
sc&rowcap=2&loadtype=Next&bookmark=<B><P><p>UserId</p></P><D><f>false</f></D><F><v>1</v></F><L><v>
2</v></L></B>/

This request now returns the next two rows, a new bookmark ID value that you can use to navigate on your
next request, and a MoreRowsExist flag.
The MoreRowsExist flag indicates that there are more rows that can be retrieved with a NEXT request. For
custom load methods, the CLM must be written to support bookmarking.
{

"Bookmark": "<B><P><p>UserId</p></P><D><f>false</f></D><F><v>3</v></F><L><v>4</v></L></B>",
"Items": [
[
{
"Name": "UserId",
"Value": "3"
},
{
"Name": "Username",
"Value": "mpalanca"
},
{
"Name": "UserDesc",
"Value": null
},
{
"Name": "_ItemId",
"Value": "PBT=[UserNames] UserNames.DT=[2019-06-13 14:03:59.083] User
Names.ID=[c869bbbb-e5c0-4812-8e2a-b4447a1eceb9]"
}
],
[
{
"Name": "UserId",
"Value": "4"
},
{
"Name": "Username",
"Value": "MarielElleynIna.Palanca@infor.com"
},
{
"Name": "UserDesc",
"Value": "MarielElleynIna Palanca"
},
{
"Name": "_ItemId",
"Value": "PBT=[UserNames] UserNames.DT=[2019-07-02 10:57:01.233] User
Names.ID=[b6bc0447-3621-4e21-9244-be4963eb3c5a]"
}
]
],
"MoreRowsExist": false,
"Message": "Success",
"MessageCode": 0
}

Once MoreRowsExist returns false, the last set of available records for this IDO is retrieved.

Infor Mongoose IDO Integration with External Applications Guide | 165


