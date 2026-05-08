---
name: syteline-ido-api
description: "Infor Mongoose IDO .NET API and class library - IDORequestClient, IDOProtocol, IIDOCommands interface, LoadCollection, UpdateCollection, Invoke, GetPropertyInfo, null values, transactions, filter strings, type conversions, logging, Wire Tap"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, dotnet, extension-class, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, dotnet, extension-class, csi]
---

Infor Mongoose IDO Development Guide | 6

IDO .NET API and class library

Chapter 1: IDO .NET API and class library

Developers can use the IDO .NET class library to programmatically access the Mongoose business objects,
which are called IDOs.
Interactions with an IDO are based on requests and responses. The caller constructs an IDO request and sends
it to the IDO runtime service to be executed. The IDO runtime service constructs and returns a response to
the caller containing the results of the action requested.
All client access to the application database is through the IDOs. The primary responsibilities of the IDO
runtime service are to query data sets (LoadCollection), save data (UpdateCollection), and call methods
(Invoke).

Note: The name "WinStudio" refers to the Mongoose framework.
Each IDO request has a request type, such as OpenSession, LoadCollection, UpdateCollection, or Invoke.
Each IDO request also contains an optional request payload that is dependent on the request type. For
example, the payload for an OpenSession request contains logon information for a user, such as user ID,
password, and configuration.
The Application Program Interface (API) is used by stand-alone client applications, form and global scripts,
user controls, and IDO extension classes. The same programming interface is available on the client using
the Mongoose.IDO.Client class and from an IDO extension class using the Me.Context.Commands property.
This chapter focuses on use of the class library. The extension classes are described in IDO extension classes
on page 24.

IDORequestClient .NET class library
The IDORequestClient .NET class library contains the Client class. This class is in the Mongoose.IDO.Client
namespace.

Infor Mongoose IDO Development Guide | 7

IDO .NET API and class library
The Client class implements the IIDOCommands interface, such as LoadCollection, UpdateCollection, and
Invoke, and additional commands, such as OpenSession, GetConfigurations, CloseSession, that are used only
for external application communications.

IDOProtocol .NET class library
The IDOProtocol .NET class library contains classes that correspond to each level in the hierarchy of an IDO
request or response. These classes are used to construct IDO requests and responses. All IDOProtocol classes
are in the Mongoose.IDO.Protocol namespace.
The top level of the IDOProtocol class hierarchy is implemented by the IDORequestEnvelope class, and the
IDO request or RequestHeader in XML, is implemented by the IDORequest class. The request payload
implementation varies depending on the request type.
•
•

For an OpenSession request, the payload is implemented by the OpenSessionRequestData class.
For a LoadCollection request, the payload is implemented by the LoadCollectionRequestData class.

There are other similar classes for each request type that carries a payload.
The IDO responses are implemented in classes that mirror the requests, with the IDOResponseEnvelope at
the top of the hierarchy.
Executing an IDO runtime Invoke call using IDOProtocol classes
As an example, these steps are required to execute an IDO runtime Invoke call using IDOProtocol classes:
1
2
3
4
5
6
7
8
9
10
11

Create a new IDORequestEnvelope instance.
Set the IDORequestEnvelope.SessionID property.
Create a new instance of IDORequest.
Set the IDORequest.Type property to Invoke.
Create a new instance of InvokeRequestData.
Set the InvokeRequestData.IDOName property.
Set the InvokeRequestData.MethodName property.
Add the parameters to the InvokeRequestData.Parameters collection.
Add the IDORequest to the IDORequestEnvelope.Requests collection.
Set the payload for the IDORequest using the IDORequest.SetPayload method.
Send the completed IDORequestEnvelope to the IDO runtime and receive the IDOResponseEnvelope in
return.

Using the IIDOCommands interface
Whether you write the code on the client side or in an IDO extension class, the framework also provides
wrapper methods through the IIDOCommands interface. These wrappers typically reduce IDO interactions
to a single line of code.
When you use the IIDOCommands interface, you can only specify one IDO request per command and receive
one response per command. But when you use the IDOProtocols classes instead, you can build one

Infor Mongoose IDO Development Guide | 8

IDO .NET API and class library
IDORequestEnvelope that includes multiple requests, and then receive one response that includes information
for all the requests.
Using the ApplicationDB class
The ApplicationDB class, part of the Mongoose.IDO.DataAccess namespace, is available only to extension
classes. This class provides direct access to the application database and access to common framework
features, such as application messages and session variables. See ApplicationDB class on page 38.
Note: The ApplicationDB class replaces the AppDB class. Existing code that uses the AppDB class continues
to work, but only works on Microsoft SQL Server databases. The ApplicationDB class works with any
framework-supported database.

IIDOCommands interface
This basic IDO runtime API is defined by the IIDOCommands interface in both C# and VB formats.
The IIDOCommands interface provides a simple alternative to constructing IDO requests when interacting
with IDOs. The process of creating an IDORequestEnvelope, IDORequest, and payload class instance for each
interaction is replaced by a single method call, which constructs the request for you, using the parameter
list.

IIDOCommands interface (C#)
public interface IIDOCommands
{
GetPropertyInfoResponseData GetPropertyInfo( string idoName );
LoadCollectionResponseData LoadCollection(
LoadCollectionRequestData requestData );
UpdateCollectionResponseData UpdateCollection(
UpdateCollectionRequestData requestData );
InvokeResponseData Invoke(
InvokeRequestData requestData );
string[] GetIDONames();
LoadCollectionResponseData LoadCollection(
string idoName,
string propertyList,
string filter,
string orderBy,
int recordCap );
LoadCollectionResponseData LoadCollection(
string idoName,
PropertyList propertyList,
string filter,
string orderBy,
int recordCap );
InvokeResponseData Invoke(
string idoName,
string methodName,
params object[] parameters );
}

Infor Mongoose IDO Development Guide | 9

IDO .NET API and class library

IIDOCommands interface (Visual Basic)
Public Interface IIDOCommands
Function GetIDONames() As String()
Function GetPropertyInfo(ByVal idoName As String) As _
GetPropertyInfoResponseData
Function GetPropertyInfo(ByVal idoName As String, _
ByVal includeClassNotesFlag As Boolean) As _
GetPropertyInfoResponseData
Function Invoke(ByVal requestData As InvokeRequestData) As _
InvokeResponseData
Function Invoke(ByVal idoName As String, ByVal methodName As String, _
ByVal ParamArray parameters() As Object) As InvokeResponseData
Function LoadCollection(ByVal requestData As _
LoadCollectionRequestData) As LoadCollectionResponseData
Function LoadCollection( _
ByVal idoName As String, _
ByVal propertyList As PropertyList, _
ByVal filter As String, _
ByVal orderBy As String, _
ByVal recordCap As Integer) As LoadCollectionResponseData
Function LoadCollection( _
ByVal idoName As String, _
ByVal propertyList As String, _
ByVal filter As String, _
ByVal orderBy As String, _
ByVal recordCap As Integer) As LoadCollectionResponseData
Function UpdateCollection(ByVal requestData As _
UpdateCollectionRequestData) As UpdateCollectionResponseData
End Interface

APIs and related IDO protocol classes
This section describes each API and associated protocol classes, and provides examples of each.
The examples in this section use the syntax of extension class methods, for example:
•
•

VB.NET: ...Me.Context.Commands.LoadCollection(request)
C#: ...this.Context.Commands.LoadCollection(request)

The syntax for using the IIDOCommands interface is different in other contexts, such as a form script or a
stand-alone client application.
Note: The code examples are given for C# unless otherwise indicated.

GetPropertyInfo
Use GetPropertyInfo to retrieve a list of properties and their attributes for an IDO. GetPropertyInfo requests
are constructed using the LoadCollectionRequestData IDO protocol class.
Properties
These properties are available on the LoadCollectionRequestData class:

Infor Mongoose IDO Development Guide | 10

IDO .NET API and class library

Property

Data Type

Description

IDOName

System.String

Identifies the IDO used to execute the request

IncludeClassNotesFlag

System.Boolean

If set to True, the ClassNotesExist property in the
response is set to indicate if any class notes exist
for this IDO
This property is only available if you use the GetResponse client class method.

GetPropertyInfo example
GetPropertyInfoResponseData

response;

response = this.Context.Commands.GetPropertyInfo( "SLItems" );
foreach ( PropertyInfo idoProp in response.Properties )
{
String propertyName = idoProp.Name;
// etc.
}

LoadCollection
Use LoadCollection to perform queries. LoadCollection requests are constructed using the
LoadCollectionRequestData IDO protocol class.
Properties
These properties are available on the LoadCollectionRequestData class:
Property

Data Type

Description

IDOName

System.String

Identifies the IDO used to execute the request.

ReadMode

Mongoose.IDO.Protocol.
ReadMode

Specifies the collection read mode, which controls the isolation level used when executing
queries
Options are:
•
•
•

ReadCommitted
ReadUncommitted
Default

This property does not apply to custom load
methods.
See the Infor Mongoose Online Help for the list
of process defaults.
PropertyList

Mongoose.IDO.Protocol.
PropertyList

Specifies a subset of properties published by
the IDO to be included in the response

Infor Mongoose IDO Development Guide | 11

IDO .NET API and class library

Property

Data Type

Description

Filter

System.String

Specifies a filter or WHERE clause to be used
for the query
Default: Empty

RecordCap

System.Int32

Specifies the maximum number of items to return in the response
If set to 0, all items are returned. If set to -1, the
default cap of 200 items is used.
Default: -1

OrderBy

System.String

Specifies a list of property names used to override the default sort order
Default: Empty
Only bound properties can be included in OrderBy.

Distinct

System.Boolean

If set to True, the request returns a set of items
containing only the unique combinations of
properties named in the PropertyList property
Default: False

CustomLoadMethod

Mongoose.IDO.Protocol.
CustomLoadMethod

Allows the caller to specify a method to perform
the query in place of the standard query built
by the IDO runtime

PostQueryCommand

System.String

Allows the caller to specify a method to be executed for each item returned by the query
The method must be a member of the IDO
specified by the IDOName property.
Default: Empty

LinkBy

Mongoose.IDO.Protocol.
PropertyPair array

Applies to inner nested LoadCollection requests
only and specifies the relationship between a
parent and child IDO in terms of property pairs
Use the SetLinkBy method to set this property.
Default: Empty

NestedRequests

System.Collections. ICollection

Specifies a collection of additional LoadCollectionRequestData instances used to load child
collections.

LoadCap

System.Int32

Specifies the maximum number of nested
LoadCollection requests to process, for example, if this property is set to 1, and 200 items are
returned by the parent LoadCollection request,
only the first parent item in the response contains nested child items.

Infor Mongoose IDO Development Guide | 12

IDO .NET API and class library
LoadCollection example 1
To execute a LoadCollection request, first construct an instance of the LoadCollectionRequestData class, and
pass it to the LoadCollection method on the IIDOCommands interface.
LoadCollectionRequestData request = new LoadCollectionRequestData();
LoadCollectionResponseData response;
request.IDOName = "SLItems";
request.PropertyList.SetProperties( "Item, Description, QtyOnHand" );
request.Filter = "Item LIKE N'AB%'";
request.OrderBy = "QtyOnHand";
request.RecordCap = 0;
response = this.Context.Commands.LoadCollection( request );

LoadCollection example 2
In many cases, it is more convenient to call the overloaded version of the LoadCollection method that accepts
parameters for the required and most common LoadCollectionRequestData properties.
LoadCollectionResponseData response = LoadCollectionResponseData;
// This is equivalent to example 1.
response = this.Context.Commands.LoadCollection(
"SLItems",
"Item, Description, QtyOnHand",
"Item LIKE N'AB%'",
"QtyOnHand",
0);

LoadCollection example 3: accessing item properties
The LoadCollection methods return an instance of the LoadCollectionResponseData class, which is populated
with the same values that were sent in the request plus the results from the query. These results are accessed
through the Items property, which is a collection of IDOItem instances. Properties can be accessed through
the IDOItem instances or directly using the indexer on the LoadCollectionResponseData class.
int x = response.PropertyList.IndexOf("QtyOnHand");
int qty = 0;
foreach (IDOItem item in response.Items)
{
qty += item.PropertyValues[x].GetValue<int>();
}
qty = 0;
for (int row = 0; row < response.Items.Count; row++)
{
qty += response[row, "QtyOnHand"].GetValue<int>();
}

UpdateCollection
Use UpdateCollection to perform updates on data, which includes insertion, modification, and deletion of
data. UpdateCollection requests are constructed using the UpdateCollectionRequestData IDO protocol class.

Infor Mongoose IDO Development Guide | 13

IDO .NET API and class library
Properties
These properties are available on the UpdateCollectionRequestData class:
Property

Data Type

Description

IDOName

System.String

Identifies the IDO used to execute the request

CollectionID

System.String

Specifies an identifier associated with this UpdateCollection request

RefreshAfterUpdate

System.Boolean

Specifies a boolean value used to indicate if the
caller wants the response to contain the updated
items refreshed after they were saved

CustomInsert

System.String

Specifies the properties used for custom actions
that are used to save items

Mongoose.IDO.Protocol.
PropertyPair array

Applies to inner nested UpdateCollection requests
only and specifies the relationship between a parent
and child IDO in terms of property pairs

CustomUpdate
CustomDelete
LinkBy

Use the SetLinkBy method to set this property.
Default: Empty
Items

Mongoose.IDO.Protocol.
IDOUpdateItems

Specifies an instance of the IDOUpdateItems class
that contains zero or more IDOUpdateItem instances
The IDOUpdateItem class contains the update information for a single item

TxnScope

Mongoose.IDO.Protocol.TxnScope

If set to Item, each individual item in the UpdateCollection request is saved in a separate transaction
For a hierarchical (nested) UpdateCollection request,
the value of the TxnScope attribute at the root level
determines the behavior for the entire UpdateCollection request. This attribute has no effect when it
is included in an inner UpdateCollection within hierarchical requests.

UpdateCollection example
To execute an UpdateCollection request, first construct an instance of the UpdateCollectionRequestData
class, and pass it to the UpdateCollection method on the IIDOCommands interface.
UpdateCollectionRequestData request = new
UpdateCollectionRequestData();
UpdateCollectionResponseData response;
IDOUpdateItem newCust = new IDOUpdateItem();
request.IDOName = "SLCustomers";
request.RefreshAfterUpdate = true;
newCust.Action = UpdateAction.Insert;
newCust.ItemNumber = 1;

Infor Mongoose IDO Development Guide | 14

IDO .NET API and class library
// used for error reporting
newCust.Properties.Add( "CustNum", "C000100" );
newCust.Properties.Add( "Name", "Boxmart" );
newCust.Properties.Add( "CurrCode", "USD" );
newCust.Properties.Add( "BankCode", "BK1" );
request.Items.Add( newCust );
response = this.Context.Commands.UpdateCollection( request );

LoadCollection/UpdateCollection example: saving changes to existing records
This example loads data from an IDO, updates the data and saves the collection.
public void DoUpdate()
{
LoadCollectionResponseData loadresponse;
string sFilter = "CoNum = 'C0000567'";
loadresponse = this.Context.Commands.LoadCollection("SLCos", "Charfld1, CustNum", sFilter,
"", -1);
if ( loadresponse.Items.Count > 0 )
{
UpdateCollectionRequestData updateRequest;
IDOUpdateItem updateItem;
// Create a new UpdateCollection request:
updateRequest = new UpdateCollectionRequestData("SLCos");
// Create a new update item for the row we loaded:
updateItem = new IDOUpdateItem(UpdateAction.Update,
loadresponse.Items[0].ItemID);
// Add the CustNum property from LoadResposne, not modified:
updateItem.Properties.Add("CustNum",loadresponse[0,"CustNum"].Value,
false);
// Add the Charfld1 property using a new value, modified:
updateItem.Properties.Add(“Charfld1”, “Owzat?”, true);
// Add the update item to update the request:
updateRequest.Items.Add(updateItem);
// Save the changes:
this.Context.Commands.UpdateCollection(updateRequest);
}
}

Invoke
Use Invoke to call IDO methods, not including the custom load methods. Invoke requests are constructed
using the InvokeRequestData IDO protocol class.
Properties
These properties are available on the InvokeRequestData class:
Property

Data Type

Description

IDOName

System.String

Identifies the IDO used to execute the request

MethodName

System.String

Identifies the IDO method to be executed

Parameters

Mongoose.IDO.Protocol.InvokeParameterList

Specifies a collection that contains instances of the
InvokeParameter class corresponding to the IDO
method parameters

Infor Mongoose IDO Development Guide | 15

IDO .NET API and class library
Invoke example 1
To execute an Invoke request, first construct an instance of the InvokeRequestData class, and pass it to the
Invoke method on the IIDOCommands interface.
InvokeRequestData request = new InvokeRequestData();
InvokeResponseData response;
request.IDOName = "UserNames";
request.MethodName = "UserValidSp";
request.Parameters.Add( "ajones" );
// user name, input
request.Parameters.Add( IDONull.Value );
// user ID, output
request.Parameters.Add( IDONull.Value );
// description, output
request.Parameters.Add( IDONull.Value );
// infobar, output
response = this.Context.Commands.Invoke( request );
if ( response.IsReturnValueStdError() )
{
// get infobar output parameter
string errorMsg = null;
errorMsg = response.Parameters[3].Value;
}
else
{
int userID = 0;
string desc = null;

}

userID = response.Parameters[1].GetValue<int>();
if ( !response.Parameters[2].IsNull )
{
desc = response.Parameters[2].Value;
}

Invoke example 2
In many cases it is more convenient to call the overloaded version of the Invoke method that accepts
parameters for the IDOName, MethodName, and method parameters InvokeRequestData properties.
InvokeResponseData response;
response = this.Context.Commands.Invoke(
"UserNames",
"UserValid",
"ajones",
IDONull.Value,
IDONull.Value,
IDONull.Value );
if ( response.IsReturnValueStdError() )
{
// get infobar output parameter
string errorMsg = "";
errorMsg = response.Parameters[3].Value;
}
else
{
int userID = 0;
string desc;
userID = response.Parameters[1].GetValue<int>();
if ( !response.Parameters[2].IsNull )
{

Infor Mongoose IDO Development Guide | 16

IDO .NET API and class library

}

}

desc = response.Parameters[2].Value;

Assigning and checking null values in IDO requests
You can use several IDO properties and methods to assign or check null values in IDO requests.

Assigning null values
When assigning a null value to a property or parameter in the IDO protocol classes, the best practice is to use
the IDONull.Value static property.
InvokeResponseData myMethodResponse;
UpdateCollectionRequestData updateRequest;
// pass NULL for the 3rd parameter
myMethodResponse = this.Invoke("MyMethod", "test", 100, IDONull.Value);
// set the Note property to NULL
updateRequest.Items[0].Properties.Add("Note", IDONull.Value);

Checking null values
When checking for null values, the best practice is to use the IDONull.IsNull static method. For parameter or
property values in an IDO protocol class, you can also use the IsNull property to check for null values.
// Check for null value in a LoadCollectionResponseData:
{

}

if (!loadResponse(0, "UserId").IsNull)
{
userId = loadResponse(0, "UserId").GetValue<long>(-1);
}

// Check for a null parameter value:
public int MyFunc(Nullable<int> param)
{
// Test for a null value:
if (IDONull.IsNull(param))
{
// Handle the null parameter.
}
}

Infor Mongoose IDO Development Guide | 17

IDO .NET API and class library

Translation/localization considerations
All data values in IDO requests and responses are stored as string values in an "internal" format that is
independent of any regional settings for the application or local machine where the code is executing.

Internal format for numbers
Numeric values are always stored internally using a period for the decimal separator (for non-integer values)
and without any digit-grouping characters.

Internal format for dates
Date values are always stored internally using the format YYYYMMDD HH:MM:SS.mmm for date and time,
YYMMDD for date only, and HH:MM:SS.mmm for time only (milliseconds are optional).

Type conversions
The IDO protocol classes store all property and parameter values internally as strings in a non-localized
internal format. However, the framework also provides methods that allow you to transparently convert
between supported native .NET CLR data types and the internal formatted string values. You should always
use strongly typed variables when getting values from, or putting values into, any of the IDO protocol classes
in order to take advantage of this feature and avoid problems related to systems running with different
regional settings. This is especially important when working with date and numeric values.
The built-in VB functions for data type conversions (CStr, CDate, CInt, etc.) are sensitive to the regional settings
for the application or local machine. Do not use these functions for converting to and from internal IDO strings.

Setting property and parameter values
Use the SetValue or Add methods to set parameter and property values into the IDO protocol classes.
Add/SetValue with InvokeRequestData
This example shows how to do this with the InvokeRequestData IDO protocol class:
InvokeRequestData invokeRequest = new InvokeRequestData();
int version = 600;
DateTime recordDate = DateTime.Now;
invokeRequest.IDOName = "MyIDO";
invokeRequest.MethodName = "MyMethod";
invokeRequest.Parameters.Add("Mongoose");

Infor Mongoose IDO Development Guide | 18

IDO .NET API and class library
invokeRequest.Parameters.Add(version);
invokeRequest.Parameters.Add(Guid.NewGuid());
invokeRequest.Parameters.Add(recordDate);
invokeRequest.Parameters.Add(IDONull.Value); // Set later.
// Set the fifth parameter after it has been added:
invokeRequest.Parameters[4].SetValue(100);

Both the Add and SetValue methods accept a value of any supported .NET data type and automatically handle
the conversion to internal format; so, you do not need to think about conversion issues.
Add/SetValue with UpdateCollectionRequestData
The code to set property values into the UpdateCollectionRequestData IDO protocol class is very similar to
the previous example.
UpdateCollectionRequestData updateRequest = new UpdateCollectionRequestData();
IDOUpdateItem updateItem = new IDOUpdateItem();
updateItem.Action = UpdateAction.Insert;
updateItem.Properties.Add("UserId", IDONull.Value);
// Set later.
updateItem.Properties.Add("UserName", "MGUser");
updateItem.Properties.Add("RecordDate", DateTime.Now);
updateItem.Properties.Add("RowPointer", Guid.NewGuid());
updateItem.Properties["UserId"].SetValue(100L);
updateRequest.IDOName = "MyIDO";
updateRequest.Items.Add(updateItem);

Getting property and parameter values
Use one of the overloaded GetValue methods to get parameter and property values from the IDO protocol
classes. These examples show how to do this using the LoadCollectionResponseData IDO protocol class.
GetValue with LoadCollectionResponseData
In this example, the desired native .NET data type is specified as an additional parameter in the call to GetValue
(using the .NET Framework's generics feature).
LoadCollectionResponseData loadResponse;
long userId = 0;
string userName = null;
DateTime recordDate;
Guid rowPointer;
loadResponse = this.Context.Commands.LoadCollection("UserNames", "UserId, UserName, RecordDate,
RowPointer", "", "", 0);
userId = loadResponse[0, "UserId"].GetValue<long>();
userName = loadResponse[0, "UserName"].GetValue<string>();
recordDate = loadResponse[0, "RecordDate"].GetValue<DateTime>();
rowPointer = loadResponse[0, "RowPointer"].GetValue<Guid>();

Handling nullable values
Be aware that, if any of the values are null, this version of the GetValue method throws an exception. When
working with nullable values, you have three options to avoid throwing an exception:

Infor Mongoose IDO Development Guide | 19

IDO .NET API and class library
•
•
•

Check the IsNull property before calling GetValue.
Call the overloaded GetValue method with a default value to be returned in case of null.
Use .NET nullable value types.

Those three techniques are illustrated in this example:
LoadCollectionResponseData loadResponse;
Nullable<long> userId;
loadResponse = this.Context.Commands.LoadCollection("UserNames", "UserId, UserName, RecordDate,
RowPointer", "", "", 0);
// Check for null before calling GetValue:
if (!loadResponse[0, "UserId"].IsNull) {
userId = loadResponse[0, "UserId"].GetValue<long>();
} else {
userId = -1;
}
// Call GetValue, return -1 if null:
userId = loadResponse[0, "UserId"].GetValue<long>(-1);
// Call GetValue, check for null return value:
userId = loadResponse[0, "UserId"].GetNullableValue<long>();
if (!userId.HasValue)
userId = -1;

Creating filter strings
If you need to dynamically build a SQL filter (WHERE clause) to be used in a LoadCollection request, the
framework provides the SqlLiteral class to help build filter strings that contain embedded literals.
SqlLiteral.Format method
The static SqlLiteral.Format method accepts any supported .NET CLR typed value and converts it into a literal
that can be used when constructing a filter as shown in this example:
string filter = null;
filter = string.Format(
"SessionId = {0} AND trans_num = {1}",
SQL Literal.Format(SessionID),
SQL Literal.Format(TransNum));

This example produces a filter with the appropriate syntax for the literal values, similar to this:
SessionId = N' 7A4930D6-AE9E-4F32-9687-17ABDBF4E818' and trans_num = 1234

You must always pass in a strongly typed value in order to get the correct result. For example, do not pass a
string containing a date value.
This table shows some examples of input types, values, and the SqlLiteral.Format result for each.
.NET CLR type

Input value

SqlLiteral.Format result

System.DateTime

12/31/2006 23:59:59.999

N'20061231 23:59:59.999'

Infor Mongoose IDO Development Guide | 20

IDO .NET API and class library

.NET CLR type

Input value

SqlLiteral.Format result

System.Int32

123

123

System.String

Joe's Bar

N'Joe''s Bar’

System.Decimal

150.32

150.32

System.Guid

7A4930D6-AE9E-4F32-968717ABDBF4E818

N' 7A4930D6-AE9E-4F32-9687-17ABDBF4E818'

Supported .NET CLR types
These .NET data types are supported by the IDO protocol classes using the GetValue, Add, and SetValue
methods.
System.String

System.Char

System.DateTime

System.Int32

System.UInt32

System.Int16

System.UInt16

System.Int64

System.UInt64

System.SByte

System.Byte

System.Single

System.Double

System.Guid

System.Decimal

System.Boolean

System.Byte[ ]

Transactions
The IDO runtime service automatically executes all UpdateCollection requests in a transaction. It also executes
IDO methods in a transaction if they are marked as “transactional” methods. All other IDO requests are
executed without a transaction. Be aware that, if a method is not marked “transactional” and is called from
a method that is marked “transactional”, then it still executes in the caller's transaction.
For transactional IDO methods, if the method returns an integer value less than 5, the transaction is committed.
The exception is if the method was executed within an existing outer transaction. Then the transaction
commits only if and when the outer transaction commits. If the IDO method returns an integer value greater
than or equal to 5 the transaction is rolled back immediately. The Mongoose standard is to return 16 to indicate
a method failed. The transaction also rolls back if the method throws an exception.

Infor Mongoose IDO Development Guide | 21

IDO .NET API and class library
If you need more control over transaction scope, which is often necessary for long-running processes, you
can achieve this while still allowing the IDO runtime service to manage transactions.
For example, consider a method that posts a large batch of records. You need a method that reads the batch,
loops through each record, and attempts to post it. If the post is successful, the action commits, otherwise
it rolls back. The best way to accomplish this is to create two methods: a top level entry point method that
queries the records to be posted; and another method to do the posting. The top-level method should not
be marked as transactional, while the posting is transactional. The entry point method queries the records
to be posted and loops through each one, calling the posting method to post each record. This way, if a record
fails to post, it does not roll back all posted records, only the failed record.

Logging diagnostic messages
All applications and services in the Mongoose framework use a common message logging facility. This enables
the framework to provide a single, consolidated view of all activity logged on the local machine. For developers,
this log is available in the IDO Runtime Development Server (IDORuntimeHost.exe).
Message Logging API
public static void LogUserMessage(
string messageSource,
UserDefinedMessageType messageType,
string message)
{
}
public static void LogUserMessage(
string messageSource,
UserDefinedMessageType messageType,
string format, params object[] args)
{
}

Parameters
•
•
•
•
•

messageSource: A short, user-defined identifier of the source of a message (for example, class or project
name)
messageType: One of the UserDefinedMessageType enumeration values; used for searching and filtering
log messages
message: A string containing the log message text
format: A log message format string (see “String.Format()” in the .NET Framework documentation for
more information about formatting strings)
args: A variable-length list of format-string substitution parameters

Examples
IDORuntime.LogUserMessage(
"SLCustomers",
UserDefinedMessageType.UserDefined0,
