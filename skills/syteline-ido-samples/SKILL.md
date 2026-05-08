---
name: syteline-ido-samples
description: "Mongoose IDO code samples - LoadCollection, UpdateCollection, and Invoke examples in C#"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, dotnet, extension-class, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, dotnet, extension-class, csi]
---

You should now be able to set breakpoints in the Visual Studio debugger and step through your code.

Logging diagnostic messages
IDO extension class methods can send messages to the common logging facility IDO Runtime Development
Server (IDORuntimeHost.exe) by calling one of the overloaded IDORuntime.LogUserMessage methods.
Example
This sample sends a message to the log each time the ValidateCreditCard method is called:
•

The first parameter is messageSource (String).
This is the source identifier that displays in the log viewer.

•

The second parameter is messageType (Mongose.IDO.UserDefinedMessageType).
This is the type identifier that displays in the log viewer.

•

The third parameter is message (String).
This is the text of the message to be logged.
[IDOMethod(MethodFlags.None, "infobar")]
public int ValidateCreditCard(
string cardNumber,
DateTime expires,
decimal amount,
string infobar)
{

Infor Mongoose IDO Development Guide | 49

IDO extension classes

int result = 0;
// Call a Web service to validate the CC info:
IDORuntime.LogUserMessage(
"MyExtensionClass",
UserDefinedMessageType.UserDefined0,
"ValidateCreditCard called by user " + IDORuntime.Context.UserName);
}

return result;

Infor Mongoose IDO Development Guide | 50

Code samples

Appendix A: Code samples

Note: These examples assume you are writing code in an IDO extension class that inherits from the Mongoose
.IDO.ExtensionClassBase class.

LoadCollection examples
Loading a collection and returning properties
This sample code demonstrates executing a LoadCollection for the SLTtJobtMatPosts IDO, bringing back the
TransNum and TransSeq properties.
LoadCollectionResponseData loadResponse;
loadResponse =
.Context.Commands.LoadCollection(
"SLTtJobtMatPosts",
"TransNum,TransSeq",
[filter],
.Empty, 0);

Loading a collection for an IDO extension class
This sample demonstrates executing a LoadCollection for the IDO that the extension class belongs to (the
current instance of the executing IDO).
LoadCollectionResponseData loadResponse;
loadResponse =
.LoadCollection(
"TransNum, EmpNum, EmpEmpType, EmpDept, DerJobPWcDept ",
[filter],
"Posted, LowLevel DESC, Job, Suffix, CloseJob, TransNum", 0);

Iterating through items and accessing property values
This sample demonstrates one way to iterate through items in a LoadCollectionResponseData instance
returned from a LoadCollection request, and how to access property values.
empNum = null;
(

{
{

index = 0; index <= responseData.Items.Count; index++)
(!responseData[index, "EmpNum"].IsNull)
empNum = responseData[index, "EmpNum"].Value;

Infor Mongoose IDO Development Guide | 51

Code samples

}

}

UpdateCollection examples
Inserting a new item
This sample demonstrates inserting a new item:
UpdateCollectionRequestData request =
UpdateCollectionRequestData();
UpdateCollectionResponseData response;
IDOUpdateItem customerItem =
IDOUpdateItem();
request.IDOName = "SLCustomers";
request.RefreshAfterUpdate =
;
