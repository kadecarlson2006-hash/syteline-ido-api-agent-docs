---
name: "syteline-app-messages"
description: "Application message construction, message numbers, invoking/concatenating messages, constraint exception messages"
version: "1.0"
tags:
  - syteline
  - messages
  - mongoose
---

# syteline-app-messages

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

CATEGORY("FORMAT(sOrder Approval)")
...

After the event action execution, the full components of the message in the database are:
•
•
•
•
•
•

EventMessage.Question
FORMAT(sPoItemApprovalQuestion, ~LIT~(100.0), ~LIT~(Metric Tons), STRINGS(sItem), ~LIT~(5" screw/x03
chrome\x04hex head\x05), STRINGS(sWhse), ~LIT~(MAIN))
EventMessage.Subject
FORMAT(sPoItemApproval)
EventMessage.Category
FORMAT(sOrderApproval)

An English-speaking user who refreshes the Inbox sees this message:
Component

Text

Question

Do you approve of purchasing 100.0 Metric Tons of [Item: 5" screw, chrome
(hex head)] for delivery to [Whse: MAIN]

Subject

Purchase Approval

Category

Order Approval

A Spanish-speaking user who refreshes the Inbox sees this message:
Component

Text

Question

Usted aprueba de comprar 100.0 Metric Tons del Prod "5" screw, chrome
(hex head)" para la entrega al Alm "MAIN"

Subject

Aprobación de Comprar el Artículo

Category

Aprobación del Documento

Sample Scenario 10: Opening a Session in a Remote Environment
In this scenario, you want to call in to another site or another Mongoose-based application environment and
return a specific discrete piece of information from the remote application database. In this scenario, you
want to return the On Hand quantity of an item in another site.
For the details of and procedure for this scenario, see the Infor Mongoose IDO Integration with External
Applications guide.

Infor Mongoose Core Extensibility Guide | 151

Application Event System

Sample Scenario 11: Cross-site Event Firing - Adding a Message
to another Site's Inbox
This scenario illustrates the general requirements that are used to set up cross-site event firing. The example
adds a message to another site’s Inbox form.
•

A GenericNotify event handler is available in every target site. You can fire this event to perform the
remote work.
This event has one handler with one action that performs a Notify by using event parameters for To,
Subject, Category, and Body.

•

A stored procedure called dbo.FireGenericNotifySp is available in every target site. You can use this stored
procedure to fire the application event. This stored procedure accepts the T-SQL parameters (@To,
@Subject, @Category, and @Body) and fires the GenericNotify event that passes in the information that
the application event requires.
Note: You can do the same thing by using a hand-coded IDO Method that calls
Mongoose.EventSystem.EventHandlers.FireApplicationEvent() in case the event requires IDO-level actions.
However, for this scenario, the stored procedure is easier to perform.

•

To add a message to another site's inbox, your event handler includes a Dispatch IDO Request action
that provides these parameters:
Parameter

Description

URL( )

URL of an IDO Request Service that serves the target site
The sample value is http://MyUtilityServer/IDORequestService/Request
Service.aspx.

CONFIGNAME( )

Name of a configuration whose application database contains the target
site.
Note: Mongoose requires a configuration that is named after each site,
so you can use the site name here.
The sample value is E(SiteId).

IDOREQUEST( )

Invoke “SP!” (or the name of an IDO) plus the name of the stored procedure
(in this case FireGenericNotifySp) that passes in the event parameters as
method parameters.
The sample value (shown as XML to allow pasting into the form) is:
SUBSTITUTE('<RequestHeader Type="Invoke"> <InitiatorType />
<InitiatorName /> <SourceName /> <TargetName /> <RequestData>
<Name>SP!</Name> <Method>FireGenericNotifySp</Method>
<Parameters> <Parameter>{0}</Parameter> <Parameter>{1}</
Parameter> <Parameter>{2}</Parameter> <Parameter>{3}</
Parameter> <Parameter ByRef="Y" /> </Parameters> </RequestData>
</RequestHeader>', E(ToParm), E(SubjectParm), E(CategoryParm),
E(BodyParm))

Infor Mongoose Core Extensibility Guide | 152

Application Event System
•

•

•

If you have multiple intranets that do not serve all sites and you want the application event to fire across
intranets, you must set up an event global constant for each intranet (or site) URL and select one of those
at runtime.
The sample action shown here assumes that the originating user has the same password on the source
and target sites, which allows the user to log in for the Invoke. If this may not be the case, you can instead
set up a generic remote user, with permissions only to perform this one Invoke, and specify the
USERNAME() and PASSWORD() parameters on the Dispatch IDO Request action. Alternatively, you can
adjust the USELOCALPASSWORD() parameter.
To perform a different type of remote work, you can set up your own application events with handlers
and actions to address the work that you require. Then you can create stored procedures to fire those
application events. Finally, call those stored procedures using Dispatch IDO Request actions in the
appropriate existing handlers.

Infor Mongoose Core Extensibility Guide | 153

Application messages

Chapter 5: Application messages

About application message construction
Use application messages to display these types of information:
•
•
•
•

Information or questions that require responses from the user
Warnings about potential problems or conditions
Error conditions within the application environment or operations
System responses to user actions

Note: The types of application messages described here are generated through application database code
(stored procedures, triggers, etc.) and/or in the IDO layer (IDO extension class assemblies, etc.). Messages
that are generated at the client level are not applicable there and do not reference or use the messages that
are contained in those application tables.
Application messages are stored in the system and are invoked in response to user or system operations.
Messages can be constructed with both literal strings and substituted variable values, which are inserted into
the message when it is invoked.
Messages are identified and invoked with a unique message number that is assigned to the message when it
is constructed.

Constructing application messages
A message can be constructed so that values of various parameters can be picked up and used dynamically
when the message is invoked. This is done with the use of substitution parameters that are evaluated when
the message is invoked and run-time values substituted for the parameters.
These substitution parameters are indicated in a message by the use of an ampersand (&), followed by a
number or letter; for example &5 and &C.
These are possible sources of values for substitution parameters:
•
•
•
•

Row captions
Column or property captions
Column or property values (literal or variable)
Column or property value-captions (that is, the translatable caption that appears in the drop-down list
of one or more combo box components on a form)

Infor Mongoose Core Extensibility Guide | 154

Application messages
When you construct a message with substitution parameters, make sure the code that invokes the message
contains the correct number of values for the substitutions.
To invoke the message at run-time, insert an expression into your code at the point where you want to call
the message. This expression must use the MESSAGE on page 513 parameter function.
For example, the message code E=NoExistForIs5 displays this message (in English): "There exists no &1 where
&2 is &3 for &4 that has [&5: &6] and [&7: &8] and [&9: &A] and [&B: &C] and [&D: &E]."
This message requires these parameters:
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
•
•
•
•

&1 = The row caption
&2 = The column or property caption
&3 = The column or property value
&4 = The row caption
&5 = The column or property caption
&6 = The column or property value
&7 = The column or property caption
&8 = The column or property value
&9 = The column or property caption
&A = The column or property value
&B = The column or property caption
&C = The column or property value
&D = The column or property caption
&E = The column or property value

An example of a call for this message might look like this:
MESSAGE("E=NoExistforIs5", "@serial", "@serial.ser_num", V(SerNum)
, "@item"
, "@serial.whse", V(Whse)
, "@serial.item", V(Item)
, "@serial.loc", V(Loc)
, "@serial.lot", V(Lot)
, "@rsvd_inv.import_doc_id", V(ImportDocId)
)

At run-time execution time, this example might evaluate to this string:
There exists no Serial Number where S/N is S/N1234
for Item that has [Whse: MAIN] and [Item: BK-27000-0007] and
[Location: STOCK] and [Lot: LOT00012345] and [Import Doc Id: DocId000123456].

Creating application message numbers
Typically, the unique message number consists of an alphanumeric prefix that identifies the application or
its owner, followed by an autonumbered suffix that is created and applied when the message is constructed.
You are not required to follow this model, but it provides the best way to identify and locate messages.

Infor Mongoose Core Extensibility Guide | 155

Application messages
For example, suppose your organization is called WonderWare, and you have an application called IssueTrack.
You could create and designate a message number prefix like "WW-IssTr-" to identify all messages used in
conjunction with this application. If you added an autonumbering suffix to this, you could then create messages
for your application without worrying about maintaining unique message numbers. Using this process also
allows you to locate all messages designed for use with your application.
To create this type of message numbering, perform these steps:
1
2

Open the System Parameters form.
In the Message Num Prefix field specify the desired prefix.
This causes the system to use the prefix automatically when application messages are generated.

3
4

Open the Maintain Application Messages.

