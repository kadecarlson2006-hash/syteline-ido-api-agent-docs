---
name: "syteline-aes-samples"
description: "11 AES sample scenarios - notifications, approvals, voting, email prompts, cross-site events, translating captions"
version: "1.0"
tags:
  - syteline
  - aes
  - events
  - samples
  - mongoose
---

# syteline-aes-samples

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x

For example, if you set the Minimum to 3 for a Preferred Choice of Approve,
and three recipients respond with Approve, the preferred choice wins. If less
than that number of votes are cast for that choice after all recipients have responded, the vote reverts to Plurality. In that case, the preferred choice wins.
Note: When you set the Minimum to 1, this rule behaves as a Preferred Choice.

Minimum Percentage
Preferred Choice

If a specified percentage of votes for a specified choice is cast, that choice wins.
If you use this rule, you must also include a Minimum (MINIMUM) parameter to
specify the minimum percentage, and a Preferred Choice (PREFCHOICE) parameter to specify which is the preferred choice.
For example, if you set the Minimum to 25% for a Preferred Choice of Approve,
and two of eight of recipients respond with Approve, the preferred choice wins.
If less than that percentage of votes are cast for that choice after all recipients
have responded, the vote reverts to Plurality. In that case, the preferred choice
wins.

About AES voting status
In the Application Event System (AES), multiple voters can be included in a Prompt action. You can use the
Voting tab on the Event Status form and Event Handler Status form to monitor the voting status of a prompt.
As an administrator, you can use voting status to determine why a suspended row might not be released yet.
The Voting tab provides this information:
•
•
•

Which message recipients have yet to vote
The number of votes still needed to reach a quorum
The length of time the workflow has been waiting since voting began, and since the last vote

Infor Mongoose Core Extensibility Guide | 102

Application Event System
•

Current vote tally statistics for each choice, including these statistics:
•
Front-runner: Indicates whether this choice is in the lead to win
•
Tied: Indicates whether this choice is tied with another choice
•
Margin of Victory/Lead: Numerically indicates how far ahead this choice is in relation to second
place, or how far behind a choice is in relation to first place
•
Votes to Win: Indicates the number of additional votes required to win
•
Status: Indicates whether a choice is a contender, the winner, or a loser

Dealing with indeterminate voting results
These example situations create indeterminate voting results and set action attributes that are exposed as
event functions that can be evaluated by subsequent event actions:
•

Any disagreement among multiple recipients that is registered as soon as a disagreement is detected.
This can include a vote similar to the example offered in the Plurality description.
The associated event function is the VOTINGDISPARITY( ) event function, which is a Boolean function
that indicates only that there was a disagreement.

•

A tie in the case of a Plurality or Majority vote that is registered at the point when all responses have
been received or when the timeout period has expired.
The associated event function is the VOTINGTIE( ) event function, which is a Boolean function that indicates
only that there was a tie.

You can use the returns from these functions, along with the functions RECIPIENTS( ), RESPONDERS( ),
RECIPIENTLIST( ), RESPONDERLIST( ), and NONRESPONDERLIST() to take further actions, such as these:
•
•
•
•

Reprompting all the recipients and try to get a consensus
Reprompting only a select group of the respondents and urging them to adopt a different choice
Reprompting only recipients who have not yet responded
Taking some other predetermined action

About quorums
On a Prompt action in the Application Event System (AES), a quorum is automatically calculated based on
the number of recipients, the voting rule, and voting parameters such as Minimum. If there is a number of
votes by whose tally a voting result can be determined unambiguously, that number is the quorum. Otherwise,
the quorum is the total number of recipients, that is, everyone has a chance to vote unless a timeout expires.
As soon as the quorum is reached, voting is closed, any remaining unvoted messages are expired, and the
application event continues to the next event action.
However, if you specify a Quorum value, that overrides the automatic calculation. For example, if a message
requiring a response is sent to 10 people, but you want a quorum to be reached when only four have voted,
then specify 4 as the Quorum value.

Infor Mongoose Core Extensibility Guide | 103

Application Event System
By default, if Quorum is not specified or is specified with a positive value, Wait for Quorum is true; that is,
the application event waits until the quorum is reached before it continues with the next event action. If
Quorum is specified with a non-positive value, the Wait for Quorum default value is FALSE. If these settings
conflict, for example Quorum is 3 and Wait for Quorum is FALSE, the system displays an error message.
If Wait for Quorum is FALSE, the application event does not wait for a quorum to be reached. As soon as the
messages are sent, execution continues with the next event action. If the system is not waiting for a quorum,
the event designer must determine when and under what circumstances enough votes have been received
and exactly what further actions the system is to take. This can be done using VOTINGRESULT(), RESPONDERS(),
RECIPIENTS(), and so on, in combination with the Wait or Sleep actions.

Sample scenarios
You can use the Application Event System to automate various tasks in response to various situations. In each
of the scenarios in this set of topics, the situation is described and then a proposed solution that involves
application events, handlers, and triggers. These solutions are presented in a step-by-step format, as examples
that you can learn from and possibly modify for your own use.
Note:
•

•

To a certain extent, each scenario builds on the concepts and practices of previous scenarios, so the most
effective way to use them is to work through them sequentially. However, each scenario is also
self-contained and can be used independently of the others.
To see a graphical representation for each flow as you work on it, you can use the Diagram button on
the Event Handlers form. This button opens the Event Handler Diagram form, which you can use to
view the flow of the event handler as well as access theEvent Actions form to edit individual actions.

These scenarios are available:
Sending notifications
•

Sample scenario 1: Notification of a new record - Adding a user on page 105
A simple notification is sent to a credit manager when a new customer is added to the database.

•

Sample Scenario 2: Notification of Changes to an Existing Record - Changing the Credit Limit on page 110
The credit manager is notified by email that a customer’s credit limit has been changed and the new
credit limit is specified.

•

Sample Scenario 3: Notification that includes an Old Value on page 115
A group of inventory stockers are automatically notified whenever an item’s lot size changes. In the
message that is sent, both the previous lot size and the new lot size are included.

Requesting approvals
•

Sample Scenario 4: Approval for a New Record on page 121
A purchasing manager is prompted for approval whenever a new purchase order is requested.

Infor Mongoose Core Extensibility Guide | 104

Application Event System
•

Sample Scenario 5: Requesting Approval by External Email for Changes to an Existing Record on page
127
A credit manager is prompted through an external email for approval of a change to a customer’s credit
limit.

•

Sample Scenario 6: Requesting Multiple and Complex Approvals on page 133
A purchasing manager is prompted for approval on a purchase order (PO) both of a change in status to
Ordered and for the amount of the PO. If the PO is for an amount greater than $100,000, a supervisor is
also prompted for approval. If the PO is for an amount greater than $1,000,000, two senior level executives
must also approve the PO.

Modifying records
•

Sample Scenario 7: Adding Information to a Record on page 145
A credit manager is prompted to provide a credit limit for a new customer by means of a response to a
message.

Voting
•

Sample Scenario 8: Voting for Various Choices on page 147
Several managers are prompted to approve an engineering change by means of a response to a message.

Localizing message contents
•

Sample Scenario 9: Translating Captions in a Purchase Request on page 150
A message that contains localizable strings is created.

More advanced scenarios
•

Sample Scenario 10: Opening a Session in a Remote Environment on page 151
A remote site or Mongoose-based application environment is accessed to retrieve data. The details of
and procedure for this scenario are in the IDO Integration with External Applications section of the help.

•

Sample Scenario 11: Cross-site Event Firing - Adding a Message to another Site's Inbox on page 152
A message is sent to another site’s Inbox form, using a GenericNotify event.

Sample scenario 1: Notification of a new record - Adding a user
Suppose you have a system administrator who wants to be notified whenever a new user is added to the
system, regardless of who adds the user. You could simply require each employee who adds users to the
system to manually send a notice whenever a user is added. But that places an additional burden on the
employees and is prone to possible oversight.
You can use the Application Event System to automatically create a notice whenever a new user is added. In
this example, you do not need to create an application event, because Infor provides an application event,

Infor Mongoose Core Extensibility Guide | 105

Application Event System
named IdoPostItemInsert, that you can use. All you need to do is create an event handler for that application
event and assign an action that generates and sends the message to the system administrator.
Actually, we could use the IdoOnItemInsert framework event instead of the IdoPostItemInsert event. This is
significant in refining the message. The advantage of using the IdoPostItemInsert event is that, if you allow
the Users form to auto-assign the user ID number, instead of specifying the user ID number yourself, the
system waits until the ID number has been assigned before filling in the CustNum data in the message. If you
use the IdoOnItemInsert event, the system does not wait, which means that, if you auto-assign the customer
ID number, the resulting message has TBD in place of the actual customer number.
Note: This scenario was created explicitly to work in the SyteLine (CSI) application. It will not work in Mongoose
as a stand-alone application, because it refers to IDOs and forms that are not part of the Mongoose framework.
1

Create the event handler:
a Open the Event Handlers form.
b Press F3.
c Press Ctrl + N.
d Create the handler with these settings:
Event Name
Select IdoPostItemInsert.
Applies to Initiators
Leave this field blank.
Applies to Objects
Specify SLCustomers.
Keep With
Leave this field blank.
Chronology
Leave this field blank.
Initial State
Leave this field blank.
Initial Action
Leave this field blank.
Active
Select this check box.
Can Override
Select this check box.
Ignore Failure
Clear this check box.
Suspend
Clear this check box.

Infor Mongoose Core Extensibility Guide | 106

Application Event System
Obsolete
Clear this check box.
Synchronous
Clear this check box.
Note: Because this notification does not require any response from the credit manager, it can run
asynchronously.
Transactional
Clear this check box.
e
2

Save.

Define the action for the event handler you just created:
a On the Event Handlers form, select the handler you created in Step 1.
b Click Event Actions.
c On the Event Actions form, specify this information:
Action Sequence
Specify 10.
Note: Technically, you can specify any integer you want in this field and the system treats them in
sequential order. We recommend using multiples of ten, initially at least, in case you later need to
add more action steps between existing steps, so you do not need to renumber all existing steps.
Action Type
Select Notify.
d
e
f

Click Edit Parameters.
On the Event Action Notify form, click the To button.
On the Event Action Parameter Recipients form, from the list of recipients, select the user ID of the
credit manager or whoever is serving in that role.
Note: You can select more than one recipient. Also, to deselect a recipient, click the user ID again.

g
h

Click Update and then OK.
On the Event Action Notify form, specify this information:
Subject
Specify New Customer!.
Save in Sent Items
Select this check box.
Note: This parameter tells the system to save a copy of the notification in the Sent Items folder of
the person who added the new customer.
Body
Specify We have a new customer!.
Category
Specify Change Notification.

Infor Mongoose Core Extensibility Guide | 107

Application Event System
i
Click OK.
On the Event Actions form, in the editable field on the Parameters tab, you should see this:
CATEGORY("Change Notification")
TO("userID")
SUBJECT("New Customer!")
BODY("We have a new customer!")
SAVEMESSAGE(TRUE)

where userID is the sign-in user ID for the credit manager.
Note: Note where double quotation marks and parentheses are inserted. Because it can be confusing
to know where and when to use these punctuation marks, we recommend that you use the event action
parameter forms as described in these scenarios. They insert the correct punctuation marks automatically
where and when needed and can help you avoid many time-consuming errors in syntax.
3
4
5

Optionally, click Check Syntax to verify that there are no syntax errors.
Save the action and close the Event Actions form.
Discard the cached metadata.

Test the application event by using the Customers form to create a new customer. Then, sign in as the user
ID that specified in the TO parameter, open the Inbox form, and verify that the message was received. The
message must appear there, along with the properties associated with the new customer, on the Variables
tab. Note that, because you did not specify any variable access rules, all properties (variable values) are
display-only.
In creating this kind of event handler, keep these points in mind:
•
•
•

If you do not require a response from the recipient, create the handler as an asynchronous handler, to
avoid system slow-downs.
To be able to use a recipient in other handlers and be able to change that recipient when necessary in
only one place, use an event global constant for the recipient.
To use active data in a message, use the SUBSTITUTE and PROPERTY (or P) function constructs.

Refining the message
You can refine the message to make it even more informative and useful to the recipient. Not only do you
want the recipient to get a message, but you want that message to include the customer number and name
for the new customer, so the recipient can look up the customer profile more easily.
To include the customer number and name in the message:
1
2
3
4

On the Event Handlers form, select the handler that you created and then click Event Actions.
On the Event Actions form, click Edit Parameters.
On the Event Action Notify form, click the Body button.
On the Event Action Expression Editor form, specify this information:
Select a function
Select SUBSTITUTE.

Infor Mongoose Core Extensibility Guide | 108

Application Event System

Note: The SUBSTITUTE function allows you to specify the basic text of a message with replacement
markers that are embedded in the message. At run time, the system substitutes specified values for
these replacement markers. This effectively allows you to create messages with dynamic content.
Argument 1
Specify We have added a customer, {0}, customer ID {1}, to our family of customers.
Note: The numbers enclosed in curly braces ( {0} and {1} ) are the replacement markers for which values
is substituted at run time.
Replacement markers must be enclosed in curly braces { }. They must begin with zero (0) and increment
sequentially. If you do not begin with zero or you skip integers, they do not work.
5

Create the expression that is used to supply the value for replacement marker {0}:
a Place the cursor in row 1 of the Arguments grid and then click Build Expression.
b On the Event Action Expression Editor form, from the Select a function drop-down list, select
PROPERTY.
The PROPERTY function picks up the value of the CustNum or Customer Number field.
c
d

6
7
8
9
10

In the Argument 1 field, specify Name.
Click OK.

Repeat Step 5 for row 2 by using CustNum for the PROPERTY argument (propertyname) in substep c.
On the Event Action Notify form, click OK.
Optionally, on the Event Actions form, click Check Syntax.
Save the action and close the Event Actions form.
Discard the cached metadata.

Test by creating a new customer record and verifying that the intended recipient receives a notification
message that includes the correct new customer name and number.

Refining the Recipient
When defining the recipients for this message, it can be a good idea to use a global constant value, rather
than a hard-coded user ID. This allows you to use the same global constant value in other places in your
application. Then, if the name of the credit manager changes, for instance, it is possible to change the recipients
by simply changing the global constant value. It also allows you to add multiple recipients, for instance, if
you have co-credit managers or you have a trainee you want to also receive the messages.
To redefine the recipients as a global constant:
1

Create the global constant:
a Open the Event Global Constants form.
b Press F3.
c Specify this information:
Name
Specify CreditMgr or the name to assign to the constant.

Infor Mongoose Core Extensibility Guide | 109

Application Event System
Value
Specify the logon user ID for the credit manager.
Note: To add multiple recipients, specify the user IDs separated by semi-colons only and no spaces.
d
2

Save the global constant and close the form.

Incorporate the global constant in the event handler action:
a On the Event Handlers form, select the handler that you created and then click Event Actions.
b On the Event Actions form, click Edit Parameters.
c On the Event Action Notify form, click the To button.
d On the Event Action Parameter Recipients form, click the Recipients button.
e On the Event Action Expression Editor form, specify this information:
Select a function
Select GC.
Note: The GC function calls a specified global constant and uses its value at run time. In this case,
you want the global constant you created in Step 1.
Argument 1
Select the global constant that you created in Step 1 (CreditMgr).
Notice that the global constant name is not enclosed in double quotation marks. Generally, only
literal strings and property names must be enclosed in double quotation marks.
f
g
h
i
j

Click OK.
On the Event Action Parameter Recipients form, click the OK.
On the Event Action Notify form, click the OK.
To verify that there are no syntax errors, click Check Syntax.
Optionally, on the Event Actions form, click the Substituted Parameters tab.
Notice that the TO parameter indicates the actual recipient or the value of the global constant.

k

Save the action and close the Event Actions form.

Test by creating a new customer record and verifying that all designated recipients receive the notification
message. You can also check the Saved Messages form for the user ID from which you were signed in when
the message was sent.

Sample Scenario 2: Notification of Changes to an Existing Record
- Changing the Credit Limit
You want to create an application event and handler that notifies the credit manager whenever a customer’s
credit limit is changed. Because the credit manager prefers email and is not always signed in to the system,
you want to send the notification as an email.
Note: For this scenario to work properly, you must enable and configure SMTP on the Intranets form of the
application (utility) server. You must set up your SMTP server to relay the emails that are sent. For information

Infor Mongoose Core Extensibility Guide | 110

Application Event System

on how to do this, consult your Windows operating system documentation. Finally, any recipients must also
have email addresses saved as part of their user profiles.
You can use an existing framework event, IdoOnItemUpdate, and create our own handler. Because you are
simply sending out a notification and the system is not waiting for a response from the credit manager, you
can make an asynchronous event handler.
This event handler requires two actions:
•
•

Checks whether the Credit Limit field has been changed
Sends the email notification

To accomplish this scenario:
1

Create the event handler:
a Open the Event Handlers form.
b Press F3.
c Press Ctrl + N.
d Create the handler with these settings:
Event Name
Select IdoOnItemUpdate.
Applies to Initiators
Leave this field blank.
Applies to Objects
Specify SLCustomers.
Keep With
Leave this field blank.
Chronology
Leave this field blank.
Initial State
Leave this field blank.
Initial Action
Leave this field blank.
Active
Select this check box.
Can Override
Select this check box.
Ignore Failure
Clear this check box.
Suspend
Clear this check box.

Infor Mongoose Core Extensibility Guide | 111

Application Event System
Obsolete
Clear this check box.
Synchronous
Clear this check box.
Note: Because this notification does not require any response from the credit manager, it can run
asynchronously.
Transactional
Clear this check box.
e
2

Click Save.

Create the first action, which checks the condition of the Credit Limit field when the customer record is
saved:
a On the Event Handlers form, select the handler you created in Step 1.
b Click Event Actions.
c On the Event Actions form, specify this information:
Action Sequence
Specify 10.
Action Type
Select Finish.
Note: This action type tells the system to finish executing the handler when a particular condition
has been met and exit.
d
e
f
g

Click Edit Parameters.
On the Event Action Finish form, click the Condition button.
On the Event Action Parameter Condition form, click the Expression 1 button.
On the Event Action Expression Editor form, specify this information:
Select a function
Specify PROPERTYMODIFIED. The PROPERTYMODIFIED function checks to see whether the named
property has been modified since the last save. If the property has been modified, the expression
returns a value of TRUE.
Argument 1
Specify CreditLimit, which is the name of the property that you want to check.

h

Click OK.
Notice in the Event Action Parameter Condition form that the expression has been returned and
that double quotation marks have been automatically inserted around the name of the property.
Notice also that the Operator andExpression 2 fields have been disabled. This is because the
PROPERTYMODIFIED function is a Boolean expression; thus, no comparison is needed to return a
Boolean value.

i

On the Event Action Parameter Condition form, select the NOT check box.
If this check box is cleared, the expression returns a value of TRUE whenever the CreditLimit property
has been modified and the handler is finished. But you want the system to continue to the next action
Infor Mongoose Core Extensibility Guide | 112

Application Event System
when the CreditLimit property has been modified; you want the system to finish at this point only if
the CreditLimit property has not been modified.
j

Click OK.
Notice that the system returns the expression to the Event Action Finish form correctly formatted.

k

On the Event Action Finish form, click OK.
Notice that the system returns the entire parameter to the Event Actions form with the syntax
correctly formatted.

l
To verify that there are no syntax errors, click Check Syntax.
m Save the action.
3

Create the second action, which sends the email notification:
a Press Ctrl + N.
b Specify this information:
Action Sequence
Specify 20.
Action Type
Select Send Email.
c
d
e

Click Edit Parameters.
On the Event Action Send Email form, click the To button.
On the Event Action Parameter Recipients form, select the user ID for the credit manager.
If the credit manager has an email address set up as part of the user profile, the email address displays
to the right of the user ID. If the credit manager’s user ID does not display an email address, you must
add the email address to the credit manager’s user profile on the Users form.
Notice that you can use a global constant for the credit manager’s email address. However, because
you are sending email, you cannot reuse the existing CreditMgr global constant, but must create a
new global constant for the credit manager’s email address. The reason for not using an event global
constant in this case was simply to give you some experience with the Event Action Parameter
Recipients form’s other capabilities, but in most scenarios, a global constant is used.

f

Click Update.
Notice that the system places the email address for the credit manager in the Recipients field.

g
h

Click OK.
On the Event Action Send Email form, specify this information:
Subject
Specify Credit limit change!.
Category
Specify Financial.

i
j

Click the Body button.
On the Event Action Expression Editor form, specify this information:
Select a function
Specify SUBSTITUTE.

Infor Mongoose Core Extensibility Guide | 113

Application Event System
Argument 1
Specify The credit limit has been changed to ${0} for customer {1}, customer number {2}.
k
l

Place the cursor in the first row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information:
Select a function
Specify FILTERPROPERTY.
Argument 1
Specify CreditLimit.

m Click OK.
n Place the cursor in the second row of the Arguments grid, and click Build Expression.
o On the Event Action Expression Editor form, specify this information:
Select a function
Specify FILTERPROPERTY.
Argument 1
Specify Name.
p
q
r

Click OK.
Place the cursor in the third row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information:
Select a function
Specify FILTERPROPERTY.
Argument 1
Specify CustNum.

s

Click OK.
Notice that the system returns the entire SUBSTITUTE expression to the Event Action Send Email
form, correctly formatted.
Notice also that there is no option to save the message to the user’s Sent Items folder. This is because
this notification is being sent as an email. That being the case, you cannot use the SAVEMESSAGE
parameter to have the system save a copy of the notification in the Sent Items folder of the person
who added the new customer.

t
u
4
5

On the Event Action Send Email form, click OK.
Save the action and close the Event Actions form.

To verify that there are no syntax errors, click Check Syntax.
Discard the cached metadata.

Test this event handler by changing a customer’s credit limit and saving the record. The system generates
an email message that gets sent to the credit manager.
In creating this kind of event handler, keep these points in mind:
•

To create an event handler that sends an email, you must have the SMTP set up on the Intranets form.
Also, the email service on that computer must be set up to enable the relaying of email automatically.

Infor Mongoose Core Extensibility Guide | 114

Application Event System
•
•

To have the handler perform an action only when certain conditions are met, use the Finish action type
and the CONDITION(NOT PROPERTYMODIFIED) parameter and function.
To eliminate the single quotes that appear around replacement values in the generated messages, use
the PROPERTY function in place of the FILTERPROPERTY function we used in this scenario.

Sample Scenario 3: Notification that includes an Old Value
You want to notify a group of inventory stock clerks automatically whenever an item’s lot size changes. In
the message that is sent, you want to include both the previous lot size and the new lot size. You also want
to let them know who initiated the change.
You can use an existing framework event, IdoOnItemUpdate, and create a handler for this application event.
Because this handler needs to retrieve the before property values, you must make this synchronous, so that
during handler execution you can retrieve from the database the original row that is being updated, before
it is updated by the IDO request.
This event handler requires three actions:
•
•
•

Checks whether the Lot Size field has been changed and, if not, finish
Retrieves the row that is being updated and both the original and new values for the Lot Size field
Sends the notification to the inventory stock clerks

To accomplish this scenario:
1

Create the event handler:
a Open the Event Handlers form.
b Press F3.
c Press Ctrl + N.
d Create the handler with these settings:
Event Name
Select IdoOnItemUpdate.
Applies to Initiators
Leave this field blank.
Applies to Objects
Specify SLItems.
Keep With
Leave this field blank.
Chronology
Leave this field blank.
Initial State
Leave this field blank.
Initial Action
Leave this field blank.

Infor Mongoose Core Extensibility Guide | 115

Application Event System
Active
Select this check box.
Can Override
Select this check box.
Ignore Failure
Clear this check box.
Suspend
Clear this check box.
Obsolete
Clear this check box.
Synchronous
Select this check box.
Note: Because this notification requires both the original value and the new value, this handler
must be run synchronously.
Transactional
Clear this check box.
e
2

Click Save.

Create an event global constant for the group of stock clerks.
Putting the user IDs for the entire group into a global constant allows you to change the list, which can
be used in other places as well, in a single place easily.
a
b
c
d

Open the Event Global Constants form.
Press F3.
Press Ctrl + N.
Specify this information:
Name
Specify StockClerks or the name for the global constant.
Value
Specify the user IDs for the stock clerks.
Note: To add multiple recipients, specify the user IDs separated by semi-colons only and no spaces.

e
3

Save the global constant and close the form.

Create the first action, which checks the condition of the Lot Size field when the item record is saved:
a On the Event Handlers form, select the handler that you created.
b Click Event Actions.
c On the Event Actions form, specify this information:
Action Sequence
Specify 10.

Infor Mongoose Core Extensibility Guide | 116

Application Event System
Action Type
Select Finish.
d
e
f
g

Click Edit Parameters.
On the Event Action Finish form, click the Condition button.
On the Event Action Parameter Conditions form, click the Expression 1 button.
On the Event Action Expression Editor form, specify this information, then click OK.
Select a function
Select PROPERTYMODIFIED.
Argument 1
Specify LotSize.

4

h
i
j

On the Event Action Parameter Condition form, select the NOT check box.
Click OK.
On the Event Action Finish form, click OK.
This parameter tells the system to check the LotSize property. If this property is not modified, finish
handler execution and exit. If this property is modified, continue with the second action.

k

Save the action.

Create the second action, which retrieves both the original and new values for the Lot Size field.
As part of this step, the system retrieves the original value of the Lot Size field and stores the value to an
event variable named OldLotSize.
•
Use the IDO( ) function to identify the same IDO that fired the application event.
•
Name the same property in the PROPERTIES( ) and SET( ) functions, and both must be the same as
the field or component property value the user is changing.
•
Make sure the handler is synchronous.
To create the action:
a
b

Press Ctrl + N.
Specify this information:
Action Sequence
Specify 20.
Action Type
Select Load IDO Row.

c
d

Click Edit Parameters.
On the Event Action Load IDO Row form, specify this information:
IDO
Specify SLItems.
Note: You can also select the IDO you want from the drop-down list, but you have to filter on the
field or increase the record cap for drop-down lists to see this one.
Also, the procedure to determine what IDO collection you need is similar to the procedure to
determine what property name you need.

Infor Mongoose Core Extensibility Guide | 117

Application Event System
Properties
Select LotSize.
e
f

Click the Filter button.
On the Event Action Expression Editor form, specify this information:
Select a function
Select SUBSTITUTE.
Argument 1
Specify Item = {0}.

g
h

Place the cursor in the first row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information, then click OK.
Select a function
Select FILTERPROPERTY.
Argument 1
Specify Item.

i
j

On the Event Action Load IDO Row form, click the Output button.
On the Event Action Output Parameters form, specify this information, then click OK.
Output Type
Specify Return Variable.
Output Object Name
Specify OldLotSize.
Value
Specify LotSize.

k

On the Event Action Load IDO Row form, click OK.
Your resulting syntax statement must appear similar to this in the Parameters field of the Event
Actions form:
IDO("SLItems")
PROPERTIES("LotSize")
FILTER(SUBSTITUTE("Item = {0}", FP("Item")))
SET(RV(OldLotSize) = "LotSize")

l
To verify that there are no syntax errors, click Check Syntax.
m Save the action.
5

Create the third action, which sends the notification:.
a Press Ctrl + N.
b Specify this information:
Action Sequence
Specify 30.

Infor Mongoose Core Extensibility Guide | 118

Application Event System
Action Type
Select Notify.
c
d
e
f

Click Edit Parameters.
On the Event Action Notify form, click the To button.
On the Event Action Parameter Recipients form, click the Recipients button.
On the Event Action Expression Editor form, specify this information, then click OK.
Select a function
Select GC.
Note: The GC function allows you to designate an event global constant to use for the recipients.
In this case, designate the global constant you created earlier, StockClerks.
Argument 1
Select StockClerks.

g
h
i
j

On the Event Action Parameter Recipients form, click OK.
On the Event Action Notify form, click the Cc button.
On the Event Action Parameter Recipients form, click the Recipients button.
On the Event Action Expression Editor form, from the Select a function drop-down list, select
ORIGINATOR.
Notice that the ORIGINATOR function takes no arguments.

k
l

Click OK twice.
On the Event Action Notify form, specify this information:
Subject
Specify Lot size change.
Category
Specify Change Notification.

m Click the Body button.
n On the Event Action Expression Editor form, specify this information:
Select a function
Select SUBSTITUTE.
Argument 1
Specify The lot size has been changed for item: {1} The previous lot size was {2}, the new
lot size is {3}. Please take note and adjust your activities accordingly. This change was
made by {0}.

o
p

Place the cursor in the first row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, from the Select a function drop-down list, select
ORIGINATOR then click OK.
Notice that you can place this first property (ORIGINATOR) last in the message, and, as long as you
have the appropriate index number assigned ( {0} ), it is correctly displayed in the message.

q
r

Place the cursor in the second row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information, then click OK.

Infor Mongoose Core Extensibility Guide | 119

Application Event System
Select a function
Select FP.
Argument 1
Select Item.
Because you want the item number to be enclosed in single quote marks, you use the FP (alternate
for the FILTERPROPERTY) function. However, if you do not want single quote marks around the lot
size amounts, you can let those values evaluate as their native datatypes.
s
t

Place the cursor in the third row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information, then click OK.
Select a function
Specify V.
Argument 1
Specify OldLotSize.

u
v

Place the cursor in the fourth row of the Arguments grid, and click Build Expression.
On the Event Action Expression Editor form, specify this information, then click OK.
Select a function
Specify P or PROPERTY.
Argument 1
Select LotSize.

w

Click OK.
The resulting syntax statement looks similar to this:
BODY(SUBSTITUTE("The lot size has been changed for item: {1} The previous lot size was
{2}, the new lot size is {3}. Please take note and adjust your activities accordingly.
This change was made by {0}.",
ORIGINATOR(),
FP("Item"),
V(OldLotSize),
P("LotSize"))

Note: You can add line returns to make your syntax statement look similar to this example. The
system ignores white space and line returns when processing the statements.
x
6
7
8

On the Event Action Notify form, click OK.

To verify that there are no syntax errors, click Check Syntax.
Save the action and close the Event Actions form.
Discard the cached metadata.

Test this event handler by using the Items form and locating an item that is lot-tracked. On the General tab,
you can change the value in the Lot Size field. In the stockers’ Inbox forms, verify that the message is sent
and contains the correct values.
In creating this kind of event handler, keep these points in mind:
•

To retrieve the existing value of a field for display in a message, you must make the handler synchronous.

Infor Mongoose Core Extensibility Guide | 120

Application Event System
•
•
•

To display both the original value of a field and the new one, use an event variable to temporarily store
the original value.
Substituted values in a statement can be presented in any order as long as their index numbers match
their positions in the list.
To prevent single quotes from being placed around a substituted value, use the PROPERTY function
instead of the FILTERPROPERTY function.

Try to add an item description to the body of the message. This may require you to determine the name of
the item description property.

Sample Scenario 4: Approval for a New Record
You want to send a message to the purchasing manager whenever a purchase order (PO) is added. This
message prompts the purchasing manager for approval of the PO. The purchasing manager can indicate
approval (or disapproval) by clicking a button in the message itself. If approved, the system adds the PO; if
not, the system does not add the PO.
You can use the system framework built-in event, IdoOnItemInsert and create a handler. The handler itself
requires two actions:
•
•

Sends the prompt
Tells the system what to do if approval is denied

To set the system up to handle this:
1

Create a global constant for the purchasing manager.
a Open the Event Global Constants form.
b Press F3.
c Press Ctrl + N.
d Specify this information:
Name
Specify PurchasingMgr.
Value
Specify the purchasing manager’s user ID.
Note: To add multiple recipients, specify the user IDs separated by semi-colons only and no spaces.
e

2

Save the global constant and close the form.

Create the event handler:
a Open the Event Handlers form.
b Press F3.
c Press Ctrl + N.
d Create the handler with these settings:
Event Name
Select IdoOnItemInsert.

Infor Mongoose Core Extensibility Guide | 121

Application Event System
Applies to Initiators
Leave this field blank.
Applies to Objects
Specify SLPos.
Keep With
Leave this field blank.
Chronology
Leave this field blank.
Initial State
Leave this field blank.
Initial Action
Leave this field blank.
Active
Select this check box.
Can Override
Select this check box.
Ignore Failure
Clear this check box.
Suspend
Clear this check box.
Note: Because this notification requires a response from the purchasing manager, it must run
synchronously and be suspended.
However, you cannot select this (and make it "stick") until at least one adjourning action exists. So,
you must leave this cleared for now and come back to it after your actions have been defined.
Obsolete
Clear this check box.
Synchronous
Select this check box.
Note: Because this notification requires a response from the purchasing manager, it must run
synchronously and be suspended. When you later select the Suspend option, this option is
automatically selected.
Transactional
Clear this check box.
e
3

Click Save.

Create the first action, which sends the message:
a On the Event Actions form, specify this information:

Infor Mongoose Core Extensibility Guide | 122

Application Event System
Action Sequence
Specify 10.
Action Type
Select Prompt.
This action type sends a notification to the designated recipient and prompts the recipient for a
response.
b
c

Click Edit Parameters.
Starting with the Event Action Prompt form, use the associated forms to create these parameters:
Field or Button

Action

Result or Comments

To

Click this button.

The Event Action Parameter Recipients
form is displayed.

Recipients

Click this button.

The Event Action Expression Editor form
is displayed.

Select a function

Select GC.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
Event Action Parameter Recipients form.

OK

Click this button.

Subject

Select New purchase order needs
your approval.

Category

Specify Order Approval.

Body

Click this button.

The Event Action Expression Editor form
is displayed.

Select a function

Select SUBSTITUTE.

The Argument 1 field and drop-down list,
Arguments grid, and buttons are displayed.

Argument 1

Specify A new purchase order

Specify PurchasingMgr.
Click OK.

The system returns the expression to the
Event Action Prompt form.

has been requested for vendor
{0}, {1}. Please review the
details on the Variables tab
and register your approval on
the Response tab.

Arguments row
1

1
2

Place the cursor in this field.
Click Build Expression.

Select a function

Select P or PROPERTY.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
parent Event Action Expression Editor
form.

Select VendNum.
Click OK.

The Event Action Expression Editor form
is displayed.

Infor Mongoose Core Extensibility Guide | 123

Application Event System

Field or Button

Action

Result or Comments

Arguments row
2

1
2

The Event Action Expression Editor form
is displayed.

Select a function

Select P or PROPERTY.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
parent Event Action Expression Editor
form.

OK

Click this button.

The system returns the entire BODY parameter content to the Event Action Prompt
form.

Question

Specify Do you approve this new

Note that when the handler runs, the
QUESTION parameter is presented on the
Response tab of the recipient’s Inbox.

Place the cursor in this field.
Click Build Expression.

Select VendorName.
Click OK.

PO?

Choices

Click this button.

The Event Action Prompt Choices form is
displayed.
Note that the CHOICES parameter creates,
displays, and enables the voting buttons
that will be required for the purchasing
manager to signal approval or rejection.
When the handler runs, these CHOICES
buttons appear directly beneath the
QUESTION in the recipient’s Inbox.

d
e

Return Value
row 1

Specify 1.

Note that you can specify any value that
you want.

Button Caption
row 1

Specify sYes.

Note that you can use translatable strings
from the Strings table. These strings appear
in the drop-down list for this field.

Return Value
row 2

Specify 0.

Button Caption
row 2

Specify sNo.

OK

Click this button.

The system returns the Choices values to
the Event Action Prompt form.

OK

Click this button.

The system returns all defined parameters
to the Event Actions form, correctly formatted.

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Infor Mongoose Core Extensibility Guide | 124

Application Event System
If you have done everything correctly, your syntax for this action step must look similar to this:
TO(GC(PurchasingMgr))
SUBJECT("New purchase order needs your approval")
CATEGORY("Order Approval")
BODY(SUBSTITUTE("A new purchase order has been requested for vendor {0}, {1}. Please review
the details on the Variables tab and register your approval on the Response tab.",
P("VendNum"), P("VendorName") )
SAVEMESSAGE(FALSE)
QUESTION("Do you approve this new PO?")
CHOICES("1,sYes,0,sNo")

4

Create the second action, which tells the system how to respond if approval is not granted:
a On the Event Actions form, specify this information:
Action Sequence
Specify 20.
Action Type
Select Fail.
This action type ends handler execution with an error status. This effectively aborts the process and
prevents the PO from being added to the database.
b
c

Click Edit Parameters.
Starting with the Event Action Fail form, use the associated forms to create these parameters:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Select a function

Select VOTINGRESULT.

The system displays the Action drop-down list
field.
This function and action evaluate the results of
whatever action is selected from the list. The action refers by number to the action type.
In this case, you only have one other action, and
that is the correct action, the Prompt action.

Action

Select 10 Prompt.

OK

Click this button.

Operator

Select = (equal sign).

Expression 2

Select 0 (zero).

This value tells the system to fail the handler with
an error if the recipient responds with a "No" (0).

OK

Click this button.

The system returns the expression to the Event
Action Fail form.

The system returns the expression to the Expression 1 field on the Event Action Parameter
Condition form.

Infor Mongoose Core Extensibility Guide | 125

Application Event System

Field or Button

Action

Result or Comments

Result

Specify The PO request

This message is displayed on the Event Status
form if the purchasing manager responds with a
"No."

was rejected by the purchasing manager.

OK

d
e
5
6
7
8

Click this button.

The system returns both parameters to the Event
Actions form, correctly formatted.

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Return to the Event Handlers form and select the Suspend check box.
Save the hanlder.
Discard the cached metadata.
Test this event handler:
a On the Purchase Orders form, create a new purchase order and click Save.
After you save the PO, when the Purchase Orders form refreshes the display, the new record should
disappear from the display. The record remains hidden until approved.
If you do not assign a PO number, the generated message is displayed with a PO number of TBD.
b
c

Open the Inbox form for the individual designated as the Purchasing Manager and verify that the
message is received and that the Response tab displays the question and choice buttons.
Optionally, with the Purchase Orders form selected, from the Actions menu, select View Event.
This opens the Event Status form. Navigate to the last row and verify that the status for this
application event is Running.

d
e

On the Inbox form, click the Yes button.
Refresh the collection on the Purchase Orders form and verify that the new PO now displays in the
list.

You can also do a second test by clicking the No button to reject the request. In this case, when you
refresh the Purchase Orders form, the new PO record is never added to the database and does not appear
in the list of POs.
When creating this kind of event handler, keep these points in mind:
•

When creating a message that requires a response from the recipient (usually a Prompt action type), you
must mark the handler so that it suspends when executed. This means that it is also automatically marked
as a synchronous handler.

•

Because these event handlers must be suspended, pending the purchasing or credit manager’s response,
the Framework Event Service must be enabled for the configuration in which you are logged on.

Note: When you try to change the Subject line so it displays the user ID of the person who created the new
PO and the PO number, you must use the SUBSTITUTE function.

Infor Mongoose Core Extensibility Guide | 126

Application Event System

Sample Scenario 5: Requesting Approval by External Email for
Changes to an Existing Record
You want the credit manager’s approval for the credit limit change, and you are sending the request to the
manager’s external email address. If the credit manager approves the change, the system writes and saves
the change. If the credit manager does not approve the change, the system rolls back the record to the
previously approved credit limit.
You can use existing framework events and IDOs to accomplish this. However, because you are sending out
a prompt and requiring a response from the credit manager, you must make a synchronous and suspending
event handler.
This event handler requires these actions:
•
•
•

Checks whether the Credit Limit field has been changed. If not, the event action finishes with a status
of Success.
Sends the prompt message and external email.
If the credit manager does not approve the change, the event action fails the application event and rolls
back the record.

To accomplish this scenario:
1

Set up the recipient in the Users form to allow external email and to have the appropriate default language
code.
•
Select Send Email Prompts
•
Ensure that the Email Address is correct
•
Specify the Default Language to use for formatting text strings

2

Create an event handler with these settings:
a Open the Event Handlers form.
b Press F3.
c Press Ctrl + N.
d Create the handler with these settings:
Event Name
Select IdoOnItemUpdate.
Applies to Objects
Specify SLCustomers.
e

Click Save.

Note: If the handler that you created for Sample Scenario 2: Notification of Changes to an Existing Record
- Changing the Credit Limit is active, the clear the Active check box or select the Obsolete check box, so
that this event handler and the existing one do not create duplicate messages.
3

Create the first action, which checks the condition of the Credit Limit field when the customer record is
saved:
a On the Event Handlers form, click Event Actions for the handler that you created.
b Click Event Actions.
c On the Event Actions form, specify this information:

Infor Mongoose Core Extensibility Guide | 127

Application Event System
Action Sequence
Specify 10.
Action Type
Select Finish.
d
e

Click Edit Parameters.
Starting with the Event Action Finish form, use the associated forms to create these parameters:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Select a function

Select PROPERTYMODIFIED.

The system displays the Argument 1 button and
field.

Argument 1

1
2

Specify CreditLimit.
Click OK.

The system returns the expression to the Event
Action Parameter Condition form and disables
the Operator and Expression 2 options.

NOT

1
2

Select this check box.
Click OK.

This check box tells the action to finish with a
status of Success if the Credit Limit field has not
been changed.
The system returns the expression to the Event
Action Finish form.

OK

f
g
4

Click this button.

The system returns the entire parameter to the
Event Actions form, correctly formatted.

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Create the second action, to send the prompt message.
This action sends the prompt to the credit manager, through both the Inbox form and external email,
and suspends the handler until the credit manager responds to the request.
a On the Event Actions form, specify this information:
Action Sequence
Specify 20.
Action Type
Select Prompt.
b
c

Click Edit Parameters.
Starting with the Event Action Prompt form, use the associated forms to create these parameters:

Infor Mongoose Core Extensibility Guide | 128

Application Event System

Field or Button

Action

Result or Comments

To

Click this button.

The Event Action Parameter Recipients
form is displayed.

Recipients

Click this button.

The Event Action Expression Editor form
is displayed.

Select a function

Select GC.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
Event Action Parameter Recipients form.

OK

Click this button.

The system returns the expression to the
Event Action Prompt form.

Subject

Click this button.

The Event Action Expression Editor form
is displayed.

Select a function

Select SUBSTITUTE.

The system displays the buttons and fields
associated with the SUBSTITUTE function.

Argument 1

Specify Credit limit change request for customer ID: {0}.

Notice that the SUBSTITUTE function is
used to present the customer’s ID number
in the Subject line, so that messages can
be saved and tracked more easily.

Arguments row
1

1
2

The Event Action Expression Editor form
is displayed.

Select a function

Select P or PROPERTY.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
Event Action Parameter Recipients form.

OK

Click this button.

Category

Specify Financial.

Body

Click this button.

The Event Action Expression Editor form
is displayed.

Select a function

Select SUBSTITUTE.

The system displays the buttons and fields
associated with the SUBSTITUTE function.

Argument 1

Specify You have a request for

This sets up the basic message with three
replacement markers.

Specify CreditMgr.
Click OK.

Place the cursor in this field.
Click Build Expression.

Specify CustNum.
Click OK.

a credit limit change to ${0}

The system returns the expression to the
Event Action Prompt form.

for {1}, Customer ID {2}.
Please respond to the question
and indicate your approval on
the Response tab.

Infor Mongoose Core Extensibility Guide | 129

Application Event System

Field or Button

Action

Result or Comments

Arguments row
1

1
2

The Event Action Expression Editor form
is displayed.

Select a function

Select P.

The system displays the Argument 1 button and field.

Argument 1

1
2

Select CreditLimit.
Click OK.

The system returns the expression to the
first row of Arguments grid on the parent
Event Action Expression Editor form.

Arguments row
2

1
2

Place the cursor in this field.
Click Build Expression.

The Event Action Expression Editor form
is displayed.

Select a function

Select P.

The system displays the Argument 1 button and field.

Argument 1

1
2

Select Name.
Click OK.

The system returns the expression to the
first row of Arguments grid on the parent
Event Action Expression Editor form.

Arguments row
2

1
2

Place the cursor in this field.
Click Build Expression.

The Event Action Expression Editor form
is displayed.

Select a function

Select P.

The system displays the Argument 1 button and field.

Argument 1

1
2

Select Name.
Click OK.

The system returns the expression to the
first row of Arguments grid on the parent
Event Action Expression Editor form.

Arguments row
3

1
2

Place the cursor in this field.
Click Build Expression.

The Event Action Expression Editor form
is displayed.

Select a function

Select P.

The system displays the Argument 1 button and field.

Argument 1

1
2

The system returns the expression to the
first row of Arguments grid on the parent
Event Action Expression Editor form.

OK

Click this button.

The system returns the entire SUBSTITUTE
expression to the Event Action Prompt
form.

Question

Specify Do you approve this

Note that you have an 80-character limit in
the Question field.

Place the cursor in this field.
Click Build Expression.

Select CustNum.
Click OK.

credit limit change?

Choices

Click this button.

The Event Action Prompt Choices form is
displayed.

Return Value
row 1

Specify 1.

Note: Theoretically, you can use any value
as long as you remember what it is and use
the same value later in the Fail action step.

Infor Mongoose Core Extensibility Guide | 130

Application Event System

Field or Button

Action

Result or Comments

Button Caption
row 1

Specify sYes.

Notice that this is a translatable string from
the Strings table. Also notice that you can
select a string from the Strings table from
the drop- down list.

Return Value
row 2

Specify 0.

Button Caption
row 2

Specify sNo.

OK

Click this button.

The system returns the Choices values to
the Event Action Prompt form.

Save in Sent
Items

Select this check box.

This sends a copy of the message to
whomever initiated the credit limit change.

OK

Click this button.

The system returns the entire set of Prompt
action parameters to the Event Actions
form.

Note: You can incorporate other field values from the Event Action Prompt form before saving and
closing.
d
e
5

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Create the third action, which tells the system how to respond if approval is not granted.
This parameters tells the system to consider the action as having failed if the credit manager rejects the
credit limit change. In other words, if the credit manager votes "No" [0] on the second action (Action
Sequence = 20), then this action fails.
a On the Event Actions form, specify this information:
Action Sequence
Specify 30.
Action Type
Select Fail.
This action type ends handler execution with an error status. This effectively aborts the process and
prevents the credit limit from being changed for the customer.
b
c

Click Edit Parameters.
Starting with the Event Action Fail form, use the associated forms to create these parameters:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Infor Mongoose Core Extensibility Guide | 131

Application Event System

Field or Button

Action

Result or Comments

Select a function

Select VOTINGRESULT.

The system displays the Action drop-down list
field.

Action

Select 20 Prompt.

Notice that the system displays only the 20 in the
field. The action type name in the drop- down list
is there to help you select the correct action step.

OK

Click this button.

The system returns the expression to the Event
Action Parameter Condition form.

Operator

Select = (equal sign).

Expression 2

Select 0 (zero).

Note: This is a reference to the value you designated for a disapproval (sNo). If you used some
value other than 0, that is what you must specify
here.

OK

Click this button.

The system returns the expression to the Event
Action Fail form.

Result

Specify The credit manag-

This is the text that appears in the Result field of
the Event Status form if the credit manager disapproves the change. Since that is the only time
and place this message is displayed, you can use
a literal value. To make it clearer, you can use a
SUBSTITUTE function with the customer name
and ID number.

er has disapproved the
credit limit change.

Note: You can also set another event action to notify the original sender by message that the change
has been approved or disapproved.
d
e
6
7
8
9

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Return to the Event Handlers form and select the Suspend check box.
Save the hanlder.
Discard the cached metadata.
Test this event handler:
a On the Customers form and change the credit limit for a customer and click Save.
Notice that the entire record for this customer is now temporarily disabled, because the update has
been suspended pending approval. Therefore, no further changes can be made to this customer
record until this application event is resolved.
Also notice that all fields, including the Credit Limit field, display their original values. Anyone who
views this suspended record sees the original values, until this suspended event finishes successfully,
at which time the new values are saved in the database and displayed on the Customers form.
b

Optionally, open the Saved Messages form for your current logon ID and verify that a copy of the
message has been saved there.

Infor Mongoose Core Extensibility Guide | 132

Application Event System
c
d
e

Open the Inbox form for the individual designated as the credit manager and verify that the message
is received and that the Response tab displays the question and choice buttons.
Open the credit manager’s external email system and verify that the email is received, and the
question and choice links display.
Optionally, with the Customers form open and customer record that is changed selected, from the
Actions menu, select View Event Status.
This opens the Event Status form. Verify that the status for this event is Running. You can also open
the Event Status form manually.

f
g
h

In the credit manager’s email, click the link labeled Yes. Verify that the ASP processes the message
and returns a success response.
In the credit manager’s Inbox form, verify that the message is automatically marked as Expired, the
Choices buttons are now disabled, and the Selected Choice is Yes.
Refresh the collection on the Customers form and verify that the new credit limit was saved.
Notice too that the entire customer record is once again enabled for editing.

You can also do a second test by clicking the link labeled No to reject the request. In this case, when you
refresh the Customers form, notice that the Credit Limit field has retained its original amount.
In creating this kind of event handler, keep these points in mind:
•

•

You can use the SUBSTITUTE function in other places other than the body of a message. You can use this
function in the Subject line and other places. You can also use this function for purposes other than
replacing text in messages.
When checking on a voting result, the number referred to in the syntax is the action sequence number
for the action that contains the choice.

Extra challenges are:
•

Changing the body of the message to include both the original credit limit and the proposed new limit
Note: You must save the old credit limit in an event variable.

•

Creating another event action to notify the original sender by message that the change has been approved
or disapproved

Sample Scenario 6: Requesting Multiple and Complex Approvals
These are the conditions in this scenario:
•
•
•

When a purchase order (PO) status is changed to Ordered, the PO requires the approval of the purchasing
manager.
If the PO is for more than $100,000, the PO requires the approval of the purchasing manager’s supervisor.
If the PO is for more than $1,000,000, the PO requires the further approval of two senior executives.

If the PO is disapproved at any level, the PO is rolled back to the previous values, and any changes made are
lost. During the approval process, the PO remains suspended until approval or disapproval is determined. If
one or more approvers fail to respond to the request, the PO is locked and cannot be changed until all required
approvers respond.
This flow diagram illustrates what must happen with this handler:

Infor Mongoose Core Extensibility Guide | 133

Application Event System

For this scenario:
•
•

Use the same global constant for the purchasing manager (PurchasingMgr) that you used in Sample
Scenario 4: Approval for a New Record.
Use the IdoOnItemUpdate framework event for the SLPos IDO.
This will cause the event to be generated whenever a PO record is updated in the Purchase Orders form
or by other processes that perform the update through an IDO request.

•

Pass property values and an identifying property (ItemId) to the application event as input parameters
so the system can store them with the application event as event parameters.

Infor Mongoose Core Extensibility Guide | 134

Application Event System
To accomplish this scenario:
1

Create and save an event handler with these settings:
Event Name
Select IdoOnItemUpdate.
Applies to Objects
Specify SLPos.

2

On the Event Global Constants form, create the required event global constants:
Name

Value

Comments

PurchasingMgr

userID1

The value is the logon user ID for the purchasing manager required for initial approval.
The value can be the same as the one you created for
Sample Scenario 4: Approval for a New Record.
If you have multiple recipients, separate them with semicolons and no spaces.

PurchasingSuper

userID2

The value is the logon user ID for the purchasing supervisor required to approve POs over $100,000.

PurchasingSenior

userID3;userID4

The values are the logon user IDs for the two senior executives required to approve POs over $1,000,000.

SuperCost

100000

This global constant represents the minimum amount
that must be approved by a supervisor. You are using a
global constant so that this value can be changed globally at some future time.
The value is a literal amount. Do not add commas.

SeniorCost

1000000

This global constant represents the minimum amount
that must be approved by a supervisor. You are using a
global constant so that this value can be changed globally at some future time.
The value is a literal amount. Do not add commas.

POApprovalPrompt

See below.

Because you want to use the same basic prompt for all
levels of approvals, you must place this value into a
global constant. This is accomplished with the use of a
few variables.

Value for POApprovalPrompt:
SUBJECT(" Purchase Order Update Approval Needed")
CATEGORY("Order Approval")
TO(GC(TV(Approver)))
BODY(SUBSTITUTE("A purchase order, {0}, has been updated to Ordered status for vendor,
{1}, number: {2}. Please review the details on the Variables tab and then indicate your ap
proval on the Response tab.",
FP("PoNum"),
P("VendorName"),
FP("VendNum")))

Infor Mongoose Core Extensibility Guide | 135

Application Event System
QUESTION("Do you approve this PO change?")
CHOICES("1, sYes, 0, sNo")TV(CountMethod)
FILTERFORM("PurchaseOrders")
FILTER( SUBSTITUTE("PoNum={0}", FP("PoNum")))

3

Add the first event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 1.
Action Type
Select Finish.
b

Starting with the Edit Parameters button, use the event action parameter forms to complete the
event action:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Select a function

Select PROPERTYMODIFIED.

The system displays the Argument 1 button and
field.

Argument 1

1
2

The system returns the expression to the Event
Action Parameter Condition form.

NOT

Select this check box.

Condition

Specify OR P("Stat") <>
"O".

Specify Stat.
Click OK.

Note: The reason this is required is that the Event
Action Parameter Condition form can only be
used to construct simple condition statements.
For complex conditions, you can start with that
form, but you must then manually edit the condition statement.
The "O" in this case is the capital letter, not a zero.
The final result of this condition is:
•
•

OK

c
d

Click this button.

If the Stat property is not modified, or if the
value of the Stat field is not O, then finish.
If the Stat property is modified and the value
of the field is O, then continue to the next action.

The system returns the expression to the Event
Action Finish form.

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Infor Mongoose Core Extensibility Guide | 136

Application Event System
4

Add the second event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 2.
Action Type
Select Set Values.
b

Starting with the Edit Parameters button, use the event action parameter forms to complete the
event action:
Field or Button

Action

Result or Comments

Variables

Click this button.

The Event Action Set Name/Value Pairs form is
displayed.

Variable
Namerow 1

Specify Approver.

Valuerow 1

Specify PurchasingMgr.

Variable
Namerow 2

Specify CountMethod.

Valuerow 2

Specify VOTINGRULE(Plurality).

Although this value is similar to a function, the
system in this case does not treat VOTINGRULE()
as other functions.

OK

Click this button.

The system returns to the Event Action Set Values form.

OK

Click this button.

The system returns the parameters to the Event
Actionsform.

This action step sets the values of these variables:
•
•

c
d
5

The variable named Approver is set to the value of the global constant, PurchasingMgr, which
is the user ID for the purchasing manager.
The variable named CountMethod is set to count the votes using the Plurality rule, which simply
says that the choice with the greatest number of votes wins. Since you have only one individual
set to vote at this point, the purchasing manager’s vote alone determines what happens next.

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Add the third event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 3.
Action Type
Select Prompt.

Infor Mongoose Core Extensibility Guide | 137

Application Event System
Parameters
Specify TGC(POApprovalPrompt).
Note: Because TGC( ) is a pre-parser function, you cannot use the event action parameter forms to
create or set this statement. You must specify this statement directly in the Parameters field of the
Event Actions form. This also means that you cannot use the Check Syntax button to check the
syntax.
This statement performs a text evaluation of the POApprovalPrompt global constant to obtain the
parameters for a prompt action. Part of this text evaluation includes an evaluation and insertion of
the values for the two variables you set in the previous step.
After evaluating the POApprovalPrompt global constant, this action also sends out the prompt
message to the purchasing manager and suspends the handler pending the manager’s response.
Because you did not specify any Variable Access rules, the message allows the purchasing manager
to modify any variable values before approval.
b
6

Save the action.

Add the fourth event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 4.
Action Type
Select Branch.
b

Starting with the Edit Parameters button, use the event action parameter forms to complete the
event action:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Select a function

Select VOTINGRESULT.

The system displays the Action drop-down list.

Action

Select 3 Prompt.

The Action field displays 3.

OK

Click this button.

The system returns to the Event Action Parameter Condition form.

Operator

Select <>.

Expression 2

Specify 1.

OK

Click this button.

The system returns to the Event Action Branch
form.

Infor Mongoose Core Extensibility Guide | 138

Application Event System

Field or Button

Action

Result or Comments

Destination

Specify 14.

Even though the action Sequence 14 is not yet
created, you can specify the number here. This
action eventually jumps if the purchasing manager rejects the request.

OK

Click this button.

The system returns the actions to the Event Actions form, correctly formatted.

This action step evaluates the purchasing manager’s response (from action Sequence 3) and directs
the handler to the next action depending on that response. If the manager approves the request,
the handler continues to the next action. If the manager rejects the request, the system goes to the
destination, action Sequence 14, and continues from there.
c
d
7

To verify that there are no syntax errors, click Check Syntax.
Save the action.

Add the next event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 5.
Action Type
Select Finish.
b

Starting with the Edit Parameters button, use the event action parameter forms to complete the
event action:
Field or Button

Action

Result or Comments

Condition

Click this button.

The Event Action Parameter Condition form is
displayed.

Expression 1

Click this button.

The Event Action Expression Editor form is displayed.

Select a function

Select P.

The system displays the Argument 1 button and
field.

Argument 1

1
2

This property name is derived from the Purchase
Orders form.

Specify POCost.
Click OK.

The system returns the expression to the Event
Action Parameter Condition form.
Operator

Select <.

Expression 2

Specify 1.

The Event Action Expression Editor form is displayed.

Select a function

Select GC.

The system displays the Argument 1 button and
field.

Infor Mongoose Core Extensibility Guide | 139

Application Event System

Field or Button

Action

Result or Comments

Argument 1

1
2

The system returns the statement to the Event
Action Parameter Condition form.

OK

Click this button.

Result

Specify Approved by Purchasing Manager.

OK

Click this button.

Specify SuperCost.
Click OK.

The system returns the condition parameter to
the Event Action Finish form.

The system returns to the Event Actions form
with the parameters correctly formatted.

This action step determines whether the cost of the PO is less than $100,000, the value of the SuperCost
global constant. If it is, then the handler commits the PO record to the database, writes the result to
the event state (viewable on the Event States form), and finishes with a status of Success. If the PO
cost is $100,000 or greater, then the handler continues to the next action.
8

Add the next event action:
Action Sequence
Specify 6.
Action Type
Select Set Values.
This action step is similar to action Sequence 2, with these differences:
•
•

9

The variable named Approver is set to the name of the global constant, PurchasingSuper, which
contains the user ID for the purchasing manager’s supervisor.
The variable named CountMethod is set to count the votes using the Majority rule, which says that
any choice that gets more than 50% of the vote wins. If you have three supervisors voting, for instance,
whichever choice gets the first two votes determines the outcome. Since you have only one individual
set to vote at this point, the purchasing supervisor’s vote alone determines what happens next.

Add the next event action:
Action Sequence
Specify 7.
Action Type
Select Prompt.
Parameters
Specify TGC(POApprovalPrompt).
As in action Sequence 3, this action step performs a text evaluation of the POApprovalPrompt global
constant to obtain the parameters for a prompt action. This time, the prompt uses the new variable
values for the purchasing supervisor that are set in Step 8.
After evaluating the POApprovalPrompt global constant, this action sends out the prompt message to
the purchasing supervisor and suspends the handler again, pending the supervisor’s response. Again,
because you did not specify any Variable Access rules, the message allows the purchasing supervisor to
modify any variable values before approval.

Infor Mongoose Core Extensibility Guide | 140

Application Event System
10 Add the next event action:
Action Sequence
Specify 8.
Action Type
Select Branch.
This action is similar to action Sequence 4, with the sole difference being that for the VOTINGRESULT( )
expression, you look at action Sequence 7 instead of Sequence 3.
This action, then, evaluates the purchasing supervisor’s response (from Action Sequence 7). As soon as
any choice has a majority (more than 50% of the votes), the system directs the handler to the next action
depending on that response.
In this case, if the supervisor approves the request, the handler continues to the next action. If the
supervisor rejects the request, the system goes to the destination, action Sequence 14, and continues
from there.
11 Add the next event action:
Action Sequence
Specify 9.
Action Type
Select Finish.
This action is similar to action Sequence 5, with these differences:
•
•

The global constant to use for the condition expression is SeniorCost, instead of SuperCost.
The Result statement should read: Approved by both the purchasing manager and the purchasing
supervisor.

This action step determines whether the cost of the PO is less than $1,000,000, the value of the SeniorCost
global constant. If it is, then the handler commits the PO record to the database, writes the result to the
event state (viewable on the Event States form), and finishes with a status of Success. If the PO cost is
$1,000,000 or greater, then the handler continues to the next action.
12 Add the next event action:
Action Sequence
Specify 10.
Action Type
Select Set Values.
SETVARVALUES(Approver="PurchasingSenior",
CountMethod="VOTINGRULE(MinimumPercentage) MINIMUM(100)")

This action step is similar to action Sequences 2 and 6, with these differences:
•
•

The variable Approver is set to the name of the global constant, PurchasingSenior, which contains
the user IDs for the senior-level executives who must approve requests over $1,000,000.
The variable named CountMethod is set to count the votes using the MinimumPercentage rule, which
says that the first choice to reach a minimum percentage determines the next action. In this case,
the minimum percentage is 100%, so all recipients must approve for the PO to reach final approval.

Infor Mongoose Core Extensibility Guide | 141

Application Event System
If any recipient rejects the request, the entire request is rejected, no matter who has approved it to
that point.
Note: The MinimumPercentage voting rule requires that you specify a minimum percentage for
passage. This means that, in addition to the VOTINGRULE( ) keyword, you must also specify the
MINIMUM( ) keyword as part of the variable definition. The resulting declaration for the Value column
of the CountMethod variable is: VOTINGRULE(MinimumPercentage) MINIMUM(100).
13 Add the next event action:
Action Sequence
Specify 11.
Action Type
Select Prompt.
Parameters
Specify TGC(POApprovalPrompt).
As in action Sequences 3 and 7, this action step performs a text evaluation of the POApprovalPrompt
global constant to obtain the parameters for a prompt action. This time, the prompt uses the new variable
values for the senior-level executives that are set in the previous step.
After evaluating the POApprovalPrompt global constant, this action sends out the prompt message to
the senior executives. Again, because you did not specify any Variable Access rules, the message allows
the executives to modify any variable values before approval.
14 Add the next event action:
Action Sequence
Specify 12.
Action Type
Select Branch.
This action is similar to action Sequences 4 and 8, with the difference that for the VOTINGRESULT( )
expression, you look at action Sequence 11.
This action step evaluates the senior executives’ responses (from action Sequence 11). If both executives
vote to approve the request, then the handler moves on to the next action. If either or both of them vote
to reject the request, then the handler goes to the destination, action Sequence 14, and continues from
there.
15 Add the next event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 13.
Action Type
Select Finish.
b
c

Click Edit Parameters.
In the Event Action Finish > Result field, specify Purchase order change approved by senior
purchasing executives.

Infor Mongoose Core Extensibility Guide | 142

Application Event System
This action sequence commits the PO record to the database, writes the result to the event state
(viewable on the Event States form), and finishes with a status of Success.
d

Save all actions.

16 Add the next event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 14.
Action Type
Select Notify.
For any result which ends up in a disapproval of the request change, this action step sends a
notification message to the individual who made the original change to the PO status, letting that
individual know that the change request has been disapproved at some level.
b

Use the event action parameter forms to create this notification message event action:
TO(ORIGINATOR())
SUBJECT(SUBSTITUTE("Purchase order {0} change request disapproved",
FP("PoNum"))
CATEGORY("Notification")
BODY(SUBSTITUTE("Your purchase order change request for PO {0} for {1}, vendor number:
{2} has been disapproved. If you have questions, please see the required approvers.",
FP("PoNum"),
P("VendorName") ,
FP("VendNum")))
SAVEMESSAGE(FALSE)

c
d

To verify that there are no syntax errors, click Check Syntax.
Save the action.

17 Add the final event action:
a On the Event Actions form, specify this information:
Action Sequence
Specify 15.
Action Type
Select Fail.
b

In the Event Action Fail > Result field, specify PO change not approved.
This action step writes the result of the rejection to the event state (viewable on the Event States
form), and exits with a status of Failure.

18 Now that all event actions (including the requisite adjourning actions) have been created and saved, go
back to the Event Handlers form and select the Suspend check box.
19 Save the handler.
20 Optionally, click the Diagram button to view the diagrammatic view of the event handler flow in the
Event Handler Diagram form.
21 Discard the cached metadata.
22 Test the event handler.

Infor Mongoose Core Extensibility Guide | 143

Application Event System
In this scenario, keep these points in mind:
•
•

•

Whenever possible, use the event action parameter forms thorough the Edit Parameters button. This
is your best insurance against syntax errors.
Not all actions can be created using the event action parameter forms. This is particularly true of the
pre-parser functions. In these cases, you cannot check the syntax for these actions using the Check
Syntax button.
You can create whole actions using global constants. Although, you cannot subsequently check these
for syntax errors by using the Check Syntax button, so proceed with caution.

Extra challenges are:
•
•

Adding the user ID to the Result statements for both approvals and disapprovals.
Having the system generate a message to the user who initiated the request notifying the user that the
request has been approved.

Testing the event handler
For Sample Scenario 6: Requesting Multiple and Complex Approvals, you must perform these tests:
•

Test 1: Create a purchase order for less than $100,000 and let the purchasing manager disapprove the
order. See steps below.
Expected result: The PO status is not changed to Ordered.

•

Test 2: This test is the same as Test 1, except that this time let the purchasing manager approve the
change.
Expected result: The PO status is changed to Ordered and the PO is again enabled for change.

•

Test 3: Create a PO with a total cost of between $100,000 and $1,000,000. Let the purchasing supervisor
disapprove the order.
Expected result: The change is rolled back.

•

Test 4: This test is the same as Test 3, except that this time let both the purchasing manager and purchasing
supervisor approve the order.
Expected result: The change is written to the database and the record is again enabled for other changes..

To perform Test 1:
1
2
3

On the Purchase Orders form, create a new PO and save the PO.
On the Purchase Order Lines form, create a line for an amount of less than $100,000.
On the Purchase Orders form, change the status for the line that you created to Ordered and save the
PO.
Notice that the PO record is disabled because the record is now in a suspended state. The status appears
to revert to Planned, because the record is not yet approved. Thus, the record has not yet actually been
changed in the database.

4
5
6
7

Log in using the purchasing manager’s user ID.
Open the Inbox form.
Read the new prompt message that the system generated.
On the Response tab, select the No option.

Infor Mongoose Core Extensibility Guide | 144

Application Event System
8
9

Open or refresh the Purchase Orders form.
Verify that the new PO line status is Planned and that the PO is again enabled for changes.

For complete thoroughness, other tests are devised and conducted before making this event handler live on
an active system. For example, you can change the status to something other than Ordered and make sure
that the PO change does not suspend. You can also test for the senior executive approvals and disapprovals.

Sample Scenario 7: Adding Information to a Record
In this scenario, you want to notify a credit manager that a new customer is added and ask the credit manager
to determine what is the credit limit for that customer. So, you must send a notification that prompts the
credit manager for a response and then uses the data from that response to add data to the pending new
customer record and commit the changes to the database.
For this scenario:
•
•

Use the same global constant (CreditMgr) the you used for previous scenarios.
Use the IdoOnItemInsert framework event.

To accomplish this scenario:
1

Create and save an event handler with these settings:
Event Name
Select IdoOnItemInsert.
Applies to Objects
Specify SLCustomers.

2

Create one action, which sends a prompt to the credit manager requesting a response:
a On the Event Actions form, specify this information:
Action Sequence
Specify 10.
Action Type
Select Prompt.
b

Use the Event Action Prompt form and associated event action parameter forms to create these
parameters:
TO
Specify GC(CreditMgr).
SUBJECT
Specify SUBSTITUTE("Need credit limit for customer ID {0}, {1}", FP("CustNum"), P("Name")).
CATEGORY
Specify "Financial".

Infor Mongoose Core Extensibility Guide | 145

Application Event System
BODY
Specify SUBSTITUTE("Please use the Variables tab to provide a credit limit for customer ID
{0}, {1}. Then use the Post button on the Response tab to register the new credit limit.",
FP("CustNum"), P("Name") ).

SAVEMESSAGE
Specify FALSE.
QUESTION
Specify To post the credit limit, click the button below.
CHOICES
Specify 1,sPost.
FILTERFORM
Specify Customers.
FILTER
Specify SUBSTITUTE("CustNum={0}", FP("CustNum")).
c

On the Variable Access tab, specify this information:
Name
Specify CreditLimit.
Access
Select Mandatory.
This forces a response from the credit manager.

d
e
3
4
5
6

Save the action.
Close the Event Actions form.

Return to the Event Handlers form and select the Suspend check box.
Save the handler.
Discard the cached metadata.
Test the event handler:
a Open the Customers form.
b Create a new customer record and click Save.
Notice that the newly saved record does not appear in the list of customers at this point when you
refresh the Customers form.
c

Using the credit manager’s logon, open the credit manager’s Inbox.
The new message should appear in the Inbox.

d
e
f
g

On the Variables tab, locate the Row.CreditLimit variable and specify an amount.
Save the record.
On the Response tab, click Post.
On the Customers form, refresh the form and verify that the newly created record appears in the
list.

Infor Mongoose Core Extensibility Guide | 146

Application Event System
You can also devise other test to verify that the system behaves as expected when the credit manager
posts the response without specifying a value in the Row.CreditLimit variable field.
When creating this event handler, keep these points in mind:
•

•

•

•
•

Because the variables are listed on the Variables tab and the question and response buttons are on the
Response tab of the message, you should design your message body to include brief but detailed
instructions for responding to the request. Do not assume the recipient knows or remembers.
Because you did not specify variable access rules to address property variables other than the CreditLimit
variable, all the variable property values associated with the Customers form are displayed and writable.
That means that the credit manager, if desired, can change any variable data before saving and posting
the data to the database by clicking Post. To make other variables non-writable, you must set the variable
access for each individually.
The fact that the prompt message is sent to a single recipient means that only one vote is required for a
quorum. Once the credit manager posts the response, the vote is final and the database is updated. If
there are multiple recipients associated with the CreditMgr global constant, then you also need to set
voting rules to determine how the responses are handled. In this case, it is not necessary, because the
system assumes a Plurality voting rule, and with only one recipient, that means that the first to respond
is the one whose data is committed.
If the credit manager never votes, the record is never committed to the database, but remains adjourned
indefinitely.
The QUESTION parameter has a limit of 80 characters.

Sample Scenario 8: Voting for Various Choices
In this scenario, you need several managers at the same level to approve an engineering change, by means
of a response to a message. So, you must send a notification that prompts the managers for a response. If at
least two of the managers send responses approving the change, you then approve the requested change in
the application.
For this scenario:
•

Assume that global constants are created for EngineeringMgr, ProjectMgr, and ProgramMgr.
The creation of global constants is described in previous scenarios.

•

Use the IdoOnItemInsert framework event.

To accomplish this scenario:
1

Create and save an event handler with these settings:
Event Name
Select IdoOnItemUpdate.
Applies to Objects
Specify SLECNs.
Description
Specify ECN Approval.

Infor Mongoose Core Extensibility Guide | 147

Application Event System
2

Create one action, which sends a prompt to the managers requesting a response:
a On the Event Actions form, specify this information:
Action Sequence
Specify 10.
Action Type
Select Prompt.
b

Use the Event Action Prompt form and associated event action parameter forms to create these
parameters:
TO
Specify GC(EngineeringMgr) + ';' +GC(ProjectMgr) + ';' +GC(ProgramMgr).
SUBJECT
Specify SUBSTITUTE("Need approval for engineering change {0}, {1}", P("EcnNum"),
P("ReasonCodeDescription")).
CATEGORY
Specify "Engineering".
BODY
Specify "Please review the proposed engineering change on the Variables tab. Then use the
Approve or Reject buttons on the Response tab to register your response."

SAVEMESSAGE
Specify FALSE.
QUESTION
Specify To approve or reject, click the buttons below.
CHOICES
Specify 1,sApprove, 0,sReject.
VOTINGRULE
Specify Minimum Count Preferred Choice.
PREFERREDCHOICE
Specify 1.
MINIMUM
Specify 2.
FILTERFORM
Specify EngineeringChangeNotices.
FILTER
Specify SUBSTITUTE("ECNNum={0}", FP("ECNNum")).
c

Save the action.

Infor Mongoose Core Extensibility Guide | 148

Application Event System
If you have done everything correctly, your syntax for this action step should look like this:
TO('' + GC(EngineeringMgr) + ';' + GC(ProjectMgr) + ';' + GC(ProgramMgr))
CATEGORY("Engineering")
SUBJECT(SUBSTITUTE("Need approval for engineering change {0}, {1}", P("EcnNum"), P("Rea
sonCodeDescription")))
BODY("Please review the proposed engineering change on the Variables tab. Then use the
Approve or Reject buttons on the Response tab to register your response.")
SAVEMESSAGE(FALSE)
QUESTION("To approve or reject, click the buttons below.")
CHOICES("1,sApprove,0,sReject") VOTINGRULE(MinimumCountPreferredChoice)
MINIMUM(2)
PREFCHOICE("1")
FILTERFORM("EngineeringChangeNotices")
FILTER(SUBSTITUTE("ECNNum={0}", FP("ECNNum")))

3

Create the second action, which tells the system how to respond if approval is not granted:
a On the Event Actions form, specify this information:
Action Sequence
Specify 20.
Action Type
Select Fail.

4
5
6
7

b

Starting with the Event Action Fail form, use the associated forms to create these parameters:
•
CONDITION(VOTINGRESULT(10) = "0")
•
RESULT("The ECN change request was rejected by the managers.")

c
d

Save the action.
Close the Event Actions form.

Return to the Event Handlers form and select the Suspend check box.
Save the handler.
Discard the cached metadata.
Test the event handler:
a Open the Engineering Change Notices form.
b Update an existing ECN and click Save.
After you save the ECN, when the Engineering Change Notices form is refreshed, the record should
be disabled for updating. This form remains read-only until the ECN has been approved.
c
d
e
f
g
h
i
j
k

Optionally, select Actions > View Event Status.
On the Event Status form, navigate to the last row and verify that the status for this event is Running.
Open the Inbox form for the individual designated as the Engineering Manager.
Verify that the message is received and that the Response tab displays the question and choice
buttons.
Click the Yes button.
Open the Inbox form for the individual designated as the Project Manager.
Verify that the message is received and that the Response tab displays the question and choice
buttons.
Click the Yes button.
Refresh the collection on the Engineering Change Notices form.

Infor Mongoose Core Extensibility Guide | 149

Application Event System
l

Verify that the ECN now displays normally (read/write) and shows your changes.
As soon as two managers vote for the preferred choice, voting is closed and the change is approved.
The third manager's vote is not needed.

You can also do a second test by clicking the No button to reject the request by all three managers. In this
case, when you refresh the Engineering Change Notices form, the ECN record displays normally but your
changes are gone.
When creating this event handler, keep these points in mind:
•

•

When creating a message that requires a response from the recipient, usually a Prompt action type, you
must mark the handler so that it suspends when executed. This means that it is also automatically marked
as a synchronous handler.
Because these event handlers must be suspended, pending the managers' responses, the Framework
Event Service must be enabled for the configuration in which you are logged on.

Sample Scenario 9: Translating Captions in a Purchase Request
This scenario uses strings in captions so the text can be read by users in different countries.
To set up metadata, these strings must exist in the Forms database Strings tables:
Strings.Name

Strings.String

SpainString.String

sItem

Item

Prod

sWhse

Whse

Alm

sPoitemApprovalQuestion

Do you approve of purchasing %1
%2 of [%3:%4] for delivery to
[%5:%6]

Usted aprueba de comprar %1 %2
del %3 "%4" para la entrega al %5
"%6"

sPoitemApproval

Purchase Approval

Aprobación de Comprar el Artículo

sOrderApproval

Order Approval

Aprobación del Documento

To set up the event message category, on the Event Message Categories form, specify this information:
Category
Specify FORMAT(sOrder Approval).
Description
Specify Approval of an Order.
To set up the event action parameters, create an event action that includes these parameters:
...
QUESTION (
CLIENTSUBSTITUTE(
"sPoitemApprovalQuestion",
P(QtyOrdered),
P(UMDesc),

Infor Mongoose Core Extensibility Guide | 150

Application Event System
"STRINGS(sItem)",
P(Item), "STRINGS(sWhse)",
P(Whse)

)
)
SUBJECT(
CLIENTSUBSTITUTE(
P(PoitemApproval)
)
)


