---
name: "syteline-aes-design"
description: "AES design and setup - application events, triggers, handlers, actions, action parameters, event variables, global constants, design forms"
version: "1.0"
tags:
  - syteline
  - aes
  - events
  - actions
  - mongoose
---

# syteline-aes-design

Source: Infor Mongoose Core Extensibility Guide, Release 2026.x


Infor Mongoose Core Extensibility Guide | 63

Application Event System

Note: If you do not perform this setup, any event handler you create that requires the event service does not
work.
For more information, including the procedure, see the online help for the Service Configuration Manager
utility.
To use the IDO Runtime Development Server (IDORuntimeHost.exe) for development work, you must also
temporarily remove the dependency that the Event Service has on the IDO Runtime service.

The processing order in the Event Service
The Infor Framework Event Service processes any queued events in "first in, first out" (FIFO) order.
Because the Event Service can receive a request to run something while it is executing a prior request, all
requests are queued for execution in the order requested. If the new request is the only one in the queue and
the Event Service is not busy, the request is executed at the next polling.
When the event queue is empty, and immediately after processing each queued application event or event
handler, the Event Service checks the Event Handler State and Event Trigger tables for any items where a
Retest At time setting has arrived or passed.
The event service then checks the Event Handler State table for any items for which the timeout has expired.
This is when the Times Out At time setting has arrived or passed. The event service processes these in order
(oldest first), by using the Retest At time or Times Out At time to determine the order.
After this, the event service checks the event queue again for any incoming items.

About application event handler revisions
The system creates a set of event handler revisions the first time that any of these incidents occurs:
•
•
•

The event is generated or any of its handlers executes.
The event is generated after any of its constituent handlers or their actions has been modified.
Any of the event’s handlers executes after any of its constituent actions has been modified.

Modifications can include additions, changes, or deletions to the handlers or actions associated with the
event or handler.
When the revision is created, the system copies all of the event's handlers and actions to a read-only table.
The event or handler then uses the data in this read-only table when executing the handlers and actions, until
a new revision is created.
Revisions are used to ensure that the metadata that is used by an application event and its handlers and
actions is not altered while the event is executed. In some cases, it can take the system a period of time to
finish executing an event's handlers, either because of the processing time involved, or because the system
is waiting for some input or response before it can continue. It is possible that someone could make changes
to the event's handlers and actions while the event is processing or waiting, and that these changes could
affect the execution of the event that is in progress.

Infor Mongoose Core Extensibility Guide | 64

Application Event System
Revisions were developed to address this kind of potential problem. When an event fires, the application
event and its handlers use the revisions that are in effect at the time they start executing until they are finished.
Suppose you are using an application event to send notices to a manager when a customer order is more
than $10,000. The manager must approve the order before it can be processed.
During a transfer of responsibilities from one manager to another, a new manager is assigned the responsibility
of approving such orders. The system administrator makes the change of notice to the appropriate event
action.
However, the original manager has a few orders pending approval. These orders continue to use the metadata
for the revision in effect at the time they were created and await that manager's approval. In the meantime,
the new manager receives notices for any subsequent orders, because the first new order generates a new
revision using the information for the new manager.

Designing and using application events and handlers
Infor has built in to the system a number of application events and/or handlers that are available for immediate
use. In addition, if you have an add-on product distributed by one of Infor’s business partners, they can also
add their own application events and handlers for your use.
You can also design and create your own custom events and handlers to automate tasks for your particular
needs.
Note: If you are creating and using your own custom events and handlers, we recommend that you refresh
the metadata cache periodically. This process should be done after doing development work, before testing,
and after synchronizing the metadata in your application.

About application events
An application event is defined as a uniquely named situation that can be triggered by these things:
•
•
•
•
•

Actions performed by users working in the system
Conditions that occur while the system is running
Certain values that are exceeded in a database record
Another event’s handler
Other similar occurrences

Application events can be one of these general types:
•

Framework events: These are events that Infor has defined and built in to the system. They are tagged
with an Access As identifier of Core and a Framework indicator on the Events form.
These events generally fall into one of these categories:
•

Business process-related events that are generated when certain IDOs (Intelligent Data Objects) are
invoked

Infor Mongoose Core Extensibility Guide | 65

Application Event System
These IDOs include IdoOnItemInsert, IdoOnLoadCollection, IdoOnInvoke, and others. You can identify
these events easily by their names, which begin with the letters Ido.
•

Session events that are generated when certain session activities take place
These include SessionOnLogin, SessionOnLogout, and SessionOnVarChanged.

•

Business Object Document (BOD) events that are generated when certain BOD-related operations
occur
You can identify these events easily by their names, which begin with the letters Bod.

•

Task events that are generated when certain operations are initiated by the TaskMan service
You can identify these events easily by their names, which begin with the letters Task, excluding
TaskListCheck.

These events are always synchronous and transactional. Some can optionally be suspended to await
user responses.
•

•

Application-specific events: These are application events that typically have been created by Infor, its
business partners, and authorized vendors. They are tagged with an Access As identifier that indicates
what application or development organization they belong to, which can include Core.
Customer-defined events: These are events that a developer in an end-customer organization has created.
They are normally tagged with a blank Access As identifier, which indicates that they were created by
and belong to the customer.

Creating application events
Application events can be created or named on these forms:
•
•
•

Events
Event Triggers
Event Handlers

To create your own custom events to use as part of the application event system, use this procedure:
1
2
3

Open the Events form and execute Filter-In-Place.
Initiate a new record.
Provide this information:
Event Name
Specify the name that is to identify the application event.
Description
Optionally, specify a functional description that identifies the application event's intended use.

4

Save your changes.

Note: You can specify the application event in the Event Name field of the Event Triggers form and Event
Handlers form. However, application events that are named on those forms are not displayed on the Events
form. If you want the application event to display on the Events form, you must name the application event
using that form.

Infor Mongoose Core Extensibility Guide | 66

Application Event System

Modifying application events
After an application event is created and saved, you can modify only the application event's description. The
application event name and other attributes are locked.
Note: You can modify an application event's description only if the Access As field value for the application
event is the same as the current value on the Access As form.
To modify an event description:
1
2
3
4

Open the Events form.
Select the application event that you want to modify.
In the Description field, modify the description text as desired.
Save your change.

Deleting application events
You can delete an application event only if the event's Access As field contains the current Access As value,
as displayed on the Access As form.
To delete an application eventm use this procedure:
1
2
3
4

Open the Events form.
Select the application event that you want to delete.
Press Ctrl+D.
Save your changes.

About application event triggers
An application event trigger is a condition that causes an application event to "fire", independent of anything
that may be happening in the user interface. The event trigger carries a set of event trigger parameters for
use when the application event fires.
An event trigger can be set to fire the application event only once, or can be set to retest for its condition after
waiting a certain amount of time since either of these situations was true:
•
•

The trigger last successfully fired the application event.
The trigger last tested unsuccessfully for its condition.

In both cases, you can set the interval for the event trigger to wait, both for the successful firing of the trigger
and for the unsuccessful test for the trigger conditions by using separate settings. Testing and retesting is
accomplished by means of polling; this is not a true interruptive trigger.
An event trigger carries with it the user name and configuration in effect at the time it was defined. This data
is passed on to the event state when the trigger fires the application event.
Each event trigger must contain a condition that consist one of these expressions:

Infor Mongoose Core Extensibility Guide | 67

Application Event System
•
•

A Boolean expression
Two non-Boolean expressions that are separated by a comparison operator

Examples
•

This example causes the application event to fire when seven days have elapsed since the current result
of the database function dbo.LastEntryDate():
DATEDIFF(day, DBFUNCTION("LastEntryDate"), CURDATETIME()) > 7

•

This example causes the application event to fire when the balance on a certain customer's order is
greater than $10,000:
DBFUNCTION("OrderBalance", GC(BigCustNum)) > 10000

•

This example causes the application event to fire on the first day of each month:
DATEPART(day, CURDATETIME()) = 1

Note: The condition should generally involve a time operation, a database calculation, or both. This is because
time and the database are the only known factors that can undergo change from external stimuli (that is, by
the forward movement of time or by the actions of other application users, respectively).

Creating application event triggers
An application event trigger is a condition that causes an application event to "fire". Triggers can be based
on conditions that are created in these cases:
•
•

By user actions, such as saving and closing a form, changing a record, and so on
Apart from user actions, such as the passage of time or the result of a database calculation
Note: The Event Triggers form is used to set conditions of the second type.

1
2
3

Open the Event Triggers form and execute Filter-In-Place.
Initiate a new record.
Provide this information:
Event Name
Select the application event for which you want to define a trigger.
Note: You cannot define a trigger for a framework event.
Access As
Verify that this read-only field is displaying your Access As identifier.
Requested By
Verify that this read-only field is displaying your login user ID.
Configuration Name
Specify the system configuration in which the selected event trigger is being created, and whose system
configuration parameters control its behavior.
To indicate that the configuration name matches the name in the Site field on the System Parameters
form, leave this field as blank.

Infor Mongoose Core Extensibility Guide | 68

Application Event System
In Process
Leave this check box cleared.
When selected, this check box indicates that evaluation of the selected event trigger is in process. A
trigger that is in the process of being created cannot be "In Process".
Active
To indicate that you want the selected event trigger to be active, select this check box.
When you select this check box, the event service monitors the condition. Whenever the condition is
met, event handlers associated with that event execute.
If you clear this check box, even when the conditions are met, the event does not get triggered and no
event handlers for it execute.
You can use this option to test a new event trigger or temporarily disable an event trigger.
Transactional
To indicate that the event should be fired in a single transaction, select this check box.
Clear "In Process"
For a new trigger, this button is inactive. It is used to reset an event trigger that is in process.
4

On the Trigger tab, provide this information:
Condition
Specify the condition that causes the event to fire.
This condition typically involves either a time operation or a database calculation, or both. Each condition
consists of one of these values:
•
•

A Boolean keyword
Two non-Boolean expressions separated by a comparison operator

Edit
To open the Event Action Parameter Condition form, which is used to build a condition statement
using expressions, click this button.
Condition Retest Interval (seconds)
Specify the number of seconds the system is to wait before retesting for the condition, in the case where
the test for the condition returns a negative value.
For example, if you want the system to wait five minutes before testing for the condition again, specify
300 in this field.
Note: The Minimum Interval setting on the System Configuration Parameters form overrides this
setting. For example, if you set this field to 300 and the Minimum Interval field is set to 600, the system
does not retest for the condition until 600 seconds have passed.
If set to 0 (zero), the system does not retest for the condition after the initial test.
Trigger Reset Interval (seconds)
Specify in this field the number of seconds the system is to wait after an event fires successfully before
the condition can be tested for again.
For example, if you want the system to wait five minutes after the event fires before it can fire again,
specify 300 in this field.

Infor Mongoose Core Extensibility Guide | 69

Application Event System

Note: Similar to the Condition Retest Interval field, the Minimum Interval setting on the System
Configuration Parameters form overrides this setting.
If set to 0 (zero), the system does not reset the condition after it fires successfully.
Last Fired
This read-only field displays the date and time the event was last fired as a result of this trigger's condition
evaluating to true.
Retest At
This read-only field displays the date and time at which the system is set to retest for this condition.
The data in this field is influenced by a number of different factors, which includes the event handler or
trigger condition settings, retry interval settings, and when the event last fired.
5
6

On the Parameters tab, specify the names and values for any event parameters for which you must pass
values to the event handlers when the event fires.
Click Save.

Retesting application event triggers
When the Retest At date setting on the Event Trigger form becomes older than the current system clock
time, the event trigger is available to be processed by the Infor Framework Event Service. This happens when
the Event Service is free from processing any waiting queued events and handlers and any already-waiting
triggers that need to be tested or retested.
To process the event trigger, the system analyzes and evaluates the condition:
•

•

If the condition evaluates to True, then the application event fires. At that point, the Retest At date setting
is set to the current time plus the amount of time set for the Trigger Reset Interval (seconds). If the
Trigger Reset Interval (seconds) is set to 0 (zero), then the Active check box is cleared, which indicates
that the trigger is not to be retested.
If the condition evaluates to False, then the Retest At date setting is set to the current time plus the
amount of time set for the Condition Retest Interval (seconds). If the Condition Retest Interval
(seconds) is set to 0 (zero), then the Active check box is cleared, which indicates that the trigger is not
to be retested.

About application event handlers
An application event handler defines the actions to be taken upon the firing of a particular application event.
Each event handler is comprised of one or more event actions and, optionally, an initial state.
Each application event can have multiple event handlers that execute when the application event fires. In
such cases, the handler sequence number and other factors determine the order in which event handlers are
actually processed.

Infor Mongoose Core Extensibility Guide | 70

Application Event System

Creating application event handlers
In its most basic form, an application event handler consists of these parts:
•
•
•

An association with a particular application event
A handler sequence number
One or more actions to perform when the application event is triggered

Each event handler is uniquely defined in the system by the combination of an event name and a handler
sequence number. Both of these are set on the Event Handlers form. Event handlers must have one or more
associated event actions.
1
2
3

Open the Event Handlers form and execute Filter-In-Place.
Initiate a new record.
Ata minimum, provide information for these fields and options:
Event Name
Select an existing application event or specify a new event name for the handler that you want to define.
Handler Description
Specify a description for the handler. This description is helpful when you have multiple handlers with
the same event name. This description allows you to find the appropriate handler in a list.
Sequence
No action is required. The application assigns this number automatically when you save the new event
handler.
Access As
Verify that this read-only field is displaying your Access As identifier.
Active
To designate this event handler for immediate and active use, select this option.
When this option is selected, it means that the handler executes whenever the event fires.
When this option is cleared, the event handler is considered to be inactive, and the handler does not
execute when the event fires.
Can Override
To allow event handlers created under different Access As identifiers to override this one (using a Keep
With selection), select this option.
Clearing this option prevents other event handlers from overriding this one.

4
5

Save the new event handler.
Optionally, to create and assign event actions to be performed when this event handler executes, click
Event Actions.
This launches the Event Actions form. Use that form to create and assign the required event actions.
When you save your work on that form and close it, the application returns you to the Event Handlers
form. The newly assigned event actions display in sequence in the Event Actions grid.

6

On the Event Handlers form, set the other options as desired.
For information about form options, see the context-sensitive online help for each option.

Infor Mongoose Core Extensibility Guide | 71

Application Event System
7

Optionally, to see a graphical (diagrammatic) representation of the event handler and its actions, click
Diagram.
This button opens the Event Handler Diagram form. This form allows you to edit and add event actions
to the event handler flow.

8

Optionally, if you have multiple event handlers for the application event, and you want to change the
order in which they execute, click Resequence.
This launches the Event Handler Sequence form, which you can use to alter the sequence.

9

Save the event handler.

Using the Event Handler Diagram form
Note: Event diagramming uses the Nevron Diagram for .NET tool. For more information, including complete
documentation, go to www.nevron.com.
You can use the Event Handler Diagram form for these tasks:
•
•
•
•
•
•
•
•

View a graphical (diagrammatic) representation of an event handler and its actions. on page 589
Access the Event Actions form to modify event actions. on page 537
Add event actions to the flow. on page 537
Delete event actions from the flow. on page 548
Edit the final diagram before you copy, print, or save it. on page 551
Copy the diagram to the Windows clipboard. on page 546
Print the diagram. on page 555
Save the diagram as a graphics file. on page 557

About application event actions
An application event action is defined as a unit of work to be performed during the execution of an application
event handler.
A single event handler can have multiple event actions, but each action is assigned to a single event handler.
Depending on its action type, an event action can do these kinds of things:
•
•
•
•
•
•

Evaluate and compare expressions by using the results to select which event action of its event handler
to perform next
Affect the application event's visual state
Complete the event handler
Set event variables
Call methods or web services
Perform other predefined tasks

Infor Mongoose Core Extensibility Guide | 72

Application Event System

Creating application event actions
To be useful, each application event handler must have at least one application event action associated with
it. Often, it requires a series of event actions to complete the work the handler is designed to perform.
An event action is defined as a unit of work to be done when the event handler executes.
1
2
3

4
5

Open the Event Handlers form.
Create a new event handler; or, in the Grid View, select the event handler for which you want to create
the action.
Click Event Actions.
This launches the Event Actions form, populated with any information that might exist for the event
handler already.
To create an action for an event handler that already has event actions defined, in the grid, create a new
row.
Provide this information:
Action Sequence
Either accept the default assigned sequence number or change it as desired.
Action Type
Select the type of action to be performed.
For the list of action types and what they do, see the context-sensitive help for this field.
Action Description
Optionally, specify a description for the action.
This description is helpful when you have multiple actions with the same action type. This description
allows you to find the appropriate action in a list.

6

Click Edit Parameters. Based on the action type you selected, the system opens the associated event
action parameter form, which you can use to construct the parameters for the action type.
Note: If you are familiar with the parameters, functions, and syntax for the action parameters, you can
click Show Details to display tabs where you can manually enter the parameter information in the text
field. However, unless you are very confident in your ability to write this data from scratch or you are
pasting in data from a reliable source, and to help ensure that you use only valid parameters, functions,
and syntax, we recommend that you use the event action parameter forms, which have been designed
specifically for this purpose.
For more information about the event action parameter forms, see the context-sensitive help for each
form.

7

When you are finished setting up parameters in the appropriate event action parameter form, click OK.
When you close an event action parameter form, the parameters you set up there are automatically
returned to the Event Actions form, with the correct syntax. Even so, we recommend that you verify the
syntax is error-free by clicking Check Syntax before you proceed.

8
9

If the action involves a variable to be used in event messages and you want to restrict how the variable's
value is treated, set those restrictions on the Variable Access tab.
Save your work.

Infor Mongoose Core Extensibility Guide | 73

Application Event System

Showing application event action contexts
When creating event handlers that send system messages to recipients, you might want to make the record
for which the application event is generated available to the message recipients, in context on its form.
For example, suppose you are setting up an event handler that generates a request for approval to a purchasing
manager every time a purchase order is created. To make it easier for the purchasing manager to view the
actual purchase order being requested, you can set up the message so that the manager can simply click the
Show Context button in the Inbox form. When you click this button, the new purchase order record
automatically displays in the Purchase Orders form.
Note: This procedure can be performed only for Notify or Prompt actions, on the Event Action Notify or
Event Action Prompt forms.
1
2
3

In the Event Actions form, create an application event action for a Notify or Prompt action type.
WIth that action selected, click Edit Parameters.
In the Event Action Notify or Event Action Prompt form, designate the name of a form in the Filter
Form field.
This argument designates the form that you want to open when the recipient clicks the Show Context
button.
This parameter enables the Show Context button when the recipient views the message.

4
5

Click Filter Form.
Use the Event Action Expression Editor to designate an expression that resolves to a string that involves
the property on which you want that form to filter when it opens.
Example: This expression might be something like this:
SUBSTITUTE("propertyName = {0}", FP("propertyName"))

This particular example allows the system to pass the specific record that triggered the event handler.
6
7
8

Set up the rest of the Notify or Prompt action as desired.
Click OK.
Back in the Event Actions form, save your work.
We recommend that you check the syntax and view the expression details before saving.

9

Close the Event Actions form.

The application returns you to the Event Handlers form, where you can see the results of any changes in the
Event Actions grid.

Setting event action variables from a BOD template
You can use the Event Action Set Variables From BOD form to set any number of variables, session variables,
and/or parameter values for a BOD (Business Object Document) template.
Note: This procedure applies only to application event handlers being created for use with the BodOnReceive
framework event.
1
2

In the Event Actions form, create or select an event action and set the Action Type to Set Values.
Click Edit Parameters.
Infor Mongoose Core Extensibility Guide | 74

Application Event System
3
4

In the Event Action Set Values form, select the Event Specific tab.
In the Values From BOD section, click Edit.
The application launches the Event Action Set Variables From BOD form.

5

Optionally, in the Select XML Template From section, load (or reload) the XML template for which you
want to set event action variables.
In the Tree View section, select the node from which the value is to be set.

6

The Tree View displays the template currently being used. If no template is specified, this view displays
whatever is specified for the BODXML value.
The Tree View includes several options to make it easier to work with the template for the inbound BOD:
•
•

Expand All: Displays the entire tree hierarchy so that you can see all nodes.
Collapse All: Displays only the top level of the tree, hiding all nodes.
This allows you to select which nodes you actually want to view more easily.

•
•

Enter Name below to Add/Rename: Specify the name of an element or attribute to either add or
rename.
Add Element: Adds an element node to the tree.
Before you can add the element, you must first select the node under which the node is to be added,
and then specify the name of the element to add in the Specify name below to add/rename field.

•

Add Attribute: Adds an attribute node to the tree.
Before you can add the attribute, you must first select the node under which the node is to be added,
and then specify the name of the attribute to add in the Specify name below to add/rename field.

•
•

Remove: Removes a selected node from the Tree View.
Rename: Renames a node selected in the Tree View.
Before you can rename the node, you must first select it in the Tree View, specify the new name in
the Specify name below to add/rename field, and then click this button.

7

In the Set Value section, use the As field to specify how the value is to be used:
•
As an event Variable
•
As an event Parameter
•
As a Session Variable

8

In the Name field, specify a name to be assigned to the value.
You can either select an existing value from the drop-down list or enter the name of a variable or parameter
yet to be defined.

9 Click Save.
10 Repeat these steps to define all the variables and parameters that you require.
The XPath field indicates the XPath of a selected node within the XML hierarchy.

Setting up the Extraction XML Collection action
Use the Extract XML Collection action to extract a row or a collection of rows from an XML string or from a
section of an XML string. Each row in the collection must have the same predefined set of properties.

Infor Mongoose Core Extensibility Guide | 75

Application Event System
A collection extracted in this way is equivalent in operation to one resulting from a Load Collection event
action. This means that:
•
•
•

It can be manipulated by a subsequent Update Collection action.
On any subsequent relevant action, it can be converted back to an XML string using the XML() function.
On any subsequent relevant action, its rows can be iterated using the ROWS() function, and each row's
properties can be referenced using the P() or FP() functions.

This event action provides a convenient way to work with XML data without having to parse through the tags.
To set up the action and its parameters, use this procedure:
1
2

Create an event action with the action type Extract XML Collection.
Click Edit Parameters.
This launches the Event Action Extract XML Collection form, which has these buttons, each with an
accompanying field:

Button/Field

Notes/Comments

XML Template

The button opens the Event Action Select XML Template form, which allows you
to select and load the XML template to use with the XML data being extracted. You
can also type the XML template in this field manually, or paste it from another source.
An XML template is an XML fragment that has the same structure as a single row of
data in the XML from which the data is to be extracted. This XML fragment can thus
serve as a template for that row of data.
The field displays the XML template as a single string.

XML

The button opens an instance of the Event Action Expression Editor.
The XML referred to here is the XML string from which data is to be extracted.
When this type of event action is part of an event handler:
•
•

Path

With a BodOnReceive event, a commonly used function is BODXML(), which extracts data from the inbound Business Object Document (BOD).
You can also perform one of these actions:
•
Use the FILECONTENTS() function to read in the contents of a fixed XML
file.
•
Use the XML() function to construct an XML string from another collection.
•
Reference a variable or parameter.
•
Manually type a fixed XML string into the field.

The button opens an instance of the Event Action Extract XML Collection Set Path
form that allows you to set the extraction path for the XML.
This path is to one or more nodes in the XML string under which the data resides for
the rows to be extracted. This is expressed in XML Path Language (XPath) syntax.

Infor Mongoose Core Extensibility Guide | 76

Application Event System

Button/Field

Notes/Comments

Property Map

The button opens an instance of the Event Action Extract XML Collection Set
Property Map form that allows you to create one or more sets of property
name/value pairs that map values to be extracted from the XML string to properties
in each row of the resulting collection.
The property map consists of a list of comma-separated pairs. Each pair, in turn,
consists of a property name and a path separated by an equals sign.
•
•

The property name governs how the extracted value can be referenced later in
each row of the extracted collection.
The path describes the location of the data to be extracted for the named
property, relative to each location in the XML string identified by the Path field.
Note: This path is expressed in XML Path Language (XPath) syntax.

Result Set Assignment

The button opens the Event Action Set Result Set ID form, which allows you to
provide a name for the set of records that result from the processing of this event
action.

Server Name

This read-only field shows the server name from the Event Action Select XML
Template From File Server form.

Logical Folder
Name

This read-only field shows the logical folder name from the Event Action Select
XML Template From File Server form.

File Specification

This read-only field is automatically populated based on the file server and Logical
Folder Name details.

3

Set these parameters and click OK to close the form.

About application event action parameters
Depending on the action type, you specify optional parameters for each event action type. You can specify
parameters in any order for a particular action. To list multiple parameters, specify them one after another,
specifying either a space or nothing between them. Event action parameters are defined on the Parameters
tab of the Event Actions form.
Note: You can use the event action parameter forms to define the parameters. When you do, the parameters
are returned to the Event Actions form properly formatted and free of syntax errors.
The basic syntax for each event action parameter is FUNCTION(value).
Note: Although it is not a requirement that function names be specified by using all uppercase letters, we
recommend the practice, as it leads to greater ease of recognition and readability.
The value enclosed in parentheses can consist of:
•
•

A constant number
A literal string enclosed in quotation marks

Infor Mongoose Core Extensibility Guide | 77

Application Event System
•
•

A Boolean value: TRUE or FALSE
An event function call
Function call can be nested.

•

An expression consisting of a number of these elements that are combined by using operators

You can also use the parentheses after the function to wrap expressions that signify operations to be performed
on the results of the expression.
For example, the function V takes as a parameter the name of a variable. This function can be placed in the
parameters for other functions, for example, METHOD(V(FuncNameVar)).
Function types
Functions can be any of these basic types:
•

Parameter functions: These are functions whose parentheses wrap a parameter to the event action.
For example, these are all typical parameter functions:
•
SETVARVALUES
•
METHOD
•
INTERVAL
•
EVENTNAME
These functions must always appear at the root level and can never be nested inside any other type of
function.
These functions are identified in this documentation generically as PARAMS(…).

•

Value functions: These are functions that call event values such as these:
•
SUBSTITUTE
•
DATE
•
ABS
•
CEILING
These types of functions can never appear at the root level but must be nested within another function
construct (either a parameter or another function).
These functions are identified in this documentation generically as FUNCTION(…).

•

Word functions: These are verbatim words used inside event function calls, such as these:
•
AS
•
STRING
•
NUMBER
•
DAY
•
DATE
Note: Some of these functions can also be used as event function calls. However, these functions always
appear within an event function call.
These functions are identified in this documentation generically as …WORD…

Infor Mongoose Core Extensibility Guide | 78

Application Event System

Nesting function calls
When defining application event action parameters, keep these rules in mind:
•
•
•

PARAMS(…)-type functions can never be nested.
FUNCTION(…)-type functions must be nested and can be nested either within PARAMS(…) functions or
within other FUNCTION(…) functions.
…WORD…-type functions must be nested within FUNCTION(…)-type functions.

This is an example of an event action parameter that uses nested functions:
PARAMS(…FUNCTION1(…FUNCTION2(…WORD1…)…WORD2…)…)

Passing parameters from application event actions
Parameter lists to methods, scripts, web services, and generated events are always enclosed in a PARAMS(…)
function and delimited by commas. Each parameter is specified in one of these ways:
Syntax

Direction

Meaning

V(var)

Input

Pass in the value of the variable var.

expression

Input

Pass in the value obtained by evaluating the expression.

RV(var)

Input and output

Pass in the value of the variable var, and place the
output value into the same variable.

Setting variable and parameter values
You can set values for event variables and parameters for application event actions, by using these syntax
forms:
Event action type

Storage type

Syntax

Call Database Method

Variable

PARMS(RV(var))

Call IDO Method

Parameter

PARMS(RE(param))

Generate Event

Variable

SET(RV(var)=name)

Load IDO Row

Parameter

SET(RE(param)=name)

Set Values

Variable

SETVARVALUES(var=expr)
or adjusted through SETVARENTRIES(...)
or SETVARNAMEDENTRIES(...)

Parameter

SETPARMVALUES(param=expr)
or adjusted through SETPARMENTRIES(...)
or SETPARMNAMEDENTRIES(...)

Infor Mongoose Core Extensibility Guide | 79

Application Event System

About setting application event action parameters
There are two basic ways you can set parameters for an application event action:
•

Use the event action parameter forms associated with each action type.
To access these forms, select an Action Type and then click Edit Parameters on the Event Actions form.

•

Specify the parameters directly in the text edit field on the Event Actions form.

You can begin with the event action parameter forms and then manually edit the output in the Event Actions
form. You can also begin by directly entering starting parameters in the text edit field on the Event Actions
form and then adjusting the input using the event action parameter forms.
Note: If you are familiar with the parameters, functions, and syntax for the action parameters, you can
manually enter the parameter information in the text field. However, unless you are very confident in your
ability to write this data from scratch or you are pasting in data from a reliable source, and to help ensure
that you use only valid parameters, functions, and syntax, we recommend that you use the event action
parameter forms, which have been designed specifically for this purpose.
Tips and guidelines for using the event action parameter forms effectively
While the event action parameter forms make it easier to set application event action parameters than creating
them manually, you must still be somewhat familiar with the parameters, functions, and syntax available for
each action type. Probably, the best way to do this is to open the form associated with each action type and
access the help for that form and its fields.
Each event action parameter form includes only those parameters and functions that will work for the selected
action type. So, for example, if you are creating an action to notify recipients of something, only the parameters
you might need to create that notification are available from the Event Action Notify form.
Most options on an event action parameter form include both a field and a button. The field might be any of
these types:
•
•
•

A text edit field into which you can directly enter the value for that option.
A drop-down list from which you can select the value you want.
A combo box that allows you to either select a value from a drop-down list or enter the value manually.

The associated button typically opens either of these forms:
•
•

The Event Action Expression Editor, which is a generic form used to create desired values using
expressions.
Another auxiliary event action parameter form specifically designed to help with the creation of an
appropriate value for that option.
For example, the Condition button on many event action parameter forms opens the Event Action
Parameter Condition form, which is designed specifically to make it easier to create and format an
appropriate condition statement for the application event system to use.

When you click OK in the event action parameter form, the values you specified are returned to the parent
form, formatted using the correct syntax.
To verify that the syntax is error-free, click the Check Syntax button before you proceed.

Infor Mongoose Core Extensibility Guide | 80

Application Event System

Example:
As part of an application event that notifies a manager when a customer's credit limit has been changed, you
want to prompt a Credit Manager for approval if the new credit limit is $500,000 or less. If the new credit limit
is more than $500,000, then the Credit Supervisor must approve the change. You could use a Branch action
type to determine who gets the prompt message.
To handle this situation, you would perform these steps:
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
12

13
14

On the Event Handlers form, create an event handler that runs every time a customer's credit is changed.
Click Event Actions.
On the Event Actions form, specify the action sequence number and select the Branch action type.
Click Edit Parameters.
On the Event Action Branch form, click Condition.
On the Event Action Parameter Condition form, click Expression 1.
On the Event Action Expression Editor form, select the PROPERTY function and specify CreditLimit as
the first argument.
Click OK.
Back on the Event Action Parameter Condition form, from the Operator drop-down list, select the >
(greater than) symbol.
In the Expression 2 field, specify 500000.
Click OK.
Back on the Event Action Branch form, in the Destination field, perform one of these actions:
•
If the target action sequence step exists, select the number of the action sequence step you want
the handler to go to.
•
If the target action sequence step does not exist, specify the number of the step that you plan to
create for the target later.
Click OK.
Back on the Event Actions form, click Check Syntax to verify that the parameter syntax is all correct.
If there is an error, an error message displays. You can use this error message to determine what and
where the error appears and the preceding context. In many cases, such as this one, you must have the
property name in quotation marks, or the system returns an error. In fact, missing quotation marks are
the most common cause of syntax errors.
If this happens, try to correct the error manually and then click Check Syntax again. Keep doing this until
you have eliminated any errors.
Caution: If you do not correct any syntax errors before you click Edit Parameters again, you will lose
all parameter text and need to start over.

15 Click Save.

Using expressions in event action parameters
Many, but not all, application event action parameters allow you to use expressions, rather than literal values,
to specify the values of parameters. You typically do this when you want to allow for variable or dynamic
values to be used for these values.

Infor Mongoose Core Extensibility Guide | 81

Application Event System
For example, you want to specify a group of recipients to receive various notifications and prompt messages,
and that group membership changes often. You can create a global constant value for the group and then
use that global constant whenever you want a message sent to that group. Then, when the group membership
changes, you can change the global constant in one place and all event handlers that use that global constant
automatically pick up the change.
Syntax for expressions
Use quotation marks to indicate a literal string value. For numbers, dates, or Boolean values (TRUE/FALSE),
quotation marks are not required or allowed.
Consider these examples:
•

CONDITION( "CreditLimit" < "500000" )

Both sides of the comparison are recognized as literal string values and are treated accordingly when
executing the event action. The values are compared alphabetically as strings. Therefore, this condition
results in a false result, because C sorts higher than 5 in the Unicode collation.
•

CONDITION( CreditLimit < 500000 )

A syntax error is returned, because CreditLimit is not recognized as a valid function.
•

CONDITION( P( "CreditLimit" ) > "500000" )

Both sides of the comparison are valid, but again, the numeric value 500000 is treated as a literal string
and compared with the value of the Credit Limit field that is returned. Property values are typeless, so
the operation of the comparison depends on the expression on the other side. In this case, "500000" is a
literal string value (as indicated by the quotation marks), so the property value is compared to it
alphabetically as a string and may or may not return the expected result.
•

CONDITION( E(MG_CurrentSite) IN ("MI";"ZZ"))

The MG_CurrentSite parameter is set for all running events. It is used to make it easier for event actions
to exit processing based on the value of the current site. A finish action can be used to make a handler
only operate for certain sites.
•

CONDITION( P( "CreditLimit" ) > 500000 )

The current value of the CreditLimit property is used, but this time it is compared mathematically as a
numeric value to the numeric constant 500000. If the property value cannot be converted numerically
(for example, if it contains non-digit characters), a runtime error occurs.

About event action parameter functions
Predefined functions can be used, within application event action parameters, to build expressions that
reference values needed by that action. These values can represent variable values, parameter values, or
sub-actions. For example, the GC function can be invoked to reference a particular event global constant
value. Or the SUBSTITUTE function can be used to build an expression that substitutes certain values within
a defined string at run time.
All these functions are available from the Select a function field on the Event Action Expression Editor form.
You can also use them by typing the names manually into an expression in the Parameters field of the Event
Actions form.

Infor Mongoose Core Extensibility Guide | 82

Application Event System
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
•
•

ACTIONSEQ on page 481
ACTIONTYPENAME on page 481
ANYHANDLERSFAILED on page 481
APPNAME on page 482
ATTACHMENTEMBEDDED on page 482
ATTACHMENTLIST on page 483
ATTACHMENTNAME on page 483
ATTACHMENTS on page 483
ATTACHMENTSEQ on page 484
BEGINDATE on page 484
BODNOUN on page 484
BODVERB on page 485
BODXML on page 485
CAST on page 485
CEILING on page 486
CLIENTSUBSTITUTE on page 486
COMPANYNAME on page 487
CONFIGNAME on page 487
CURDATETIME on page 488
CUSTOMDELETE on page 488
CUSTOMINSERT on page 489
CUSTOMUPDATE on page 489
DATE on page 489
DATEADD on page 490
DATEDIFF on page 491
DATEPART on page 492
DBFUNCTION on page 492
DOCDESC on page 493
DOCEXT on page 493
DOCINTERNAL on page 494
DOCMEDIATYPE on page 494
DOCMODIFIED on page 495
DOCNAME on page 495
DOCREADONLY on page 496
DOCSEQ on page 496
DOCTYPE on page 497
E on page 497
ENTRY on page 498
ENTRYNAMES on page 498
EVENTNAME on page 499
EVENTPARMID on page 499
EVENTREVISION on page 499
EVENTSTATE on page 500
EVENTSTATEID on page 500

Infor Mongoose Core Extensibility Guide | 83

Application Event System
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
•
•

EVENTTITLE on page 500
FE on page 501
FGC on page 501
FILECONTENTS on page 502
FILTER on page 354
FILTERMETHODPARM on page 503
FILTERPROPERTY on page 503
FILTERSTRING on page 504
FILTERTASKPARM on page 505
FLOOR on page 505
FP on page 503
FSV on page 505
FV on page 506
GC on page 507
HANDLERACCESSAS on page 507
HANDLERIGNORESFAILURE on page 507
HANDLERSEQ on page 508
HANDLERSUSPENDS on page 508
HANDLERSYNCHRONOUS on page 508
HANDLERTRANSACTIONAL on page 509
HASBEGUN on page 509
HASFINISHED on page 509
IDO on page 510
IF on page 510
INITIATOR on page 510
INSIDEDATABASE on page 511
INSTR on page 511
LEN on page 512
LOADFLAGS on page 512
LOWER on page 512
MESSAGE on page 513
METHOD on page 514
METHODPARM on page 514
METHODPARMS on page 514
NAMEDENTRY on page 515
NEWGUID on page 515
NONRESPONDERLIST on page 515
NUMENTRIES on page 516
ORIGINATOR on page 516
P on page 518
POSTQUERYACTIONS on page 517
POWER on page 517
PROPERTY on page 518
PROPERTYAVAILABLE on page 519

Infor Mongoose Core Extensibility Guide | 84

Application Event System
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
•
•
•
•
•

PROPERTYMODIFIED on page 519
PROPERTYNAMES on page 520
RECIPIENTLIST on page 521
RECIPIENTS on page 521
RECORDCAP on page 521
REPLACE on page 522
RESPONDERLIST on page 522
RESPONDERS on page 523
ROUND on page 523
ROWS (for IDO Load Collection action) on page 524
ROWS (for IdoPostLoad-Collection event) on page 524
SUBSTITUTE on page 525
SUBSTRING on page 525
SUBXML on page 526
SV on page 526
TASKNAME on page 527
TASKNUMBER on page 527
TASKPARM on page 527
TASKPARMLIST on page 528
TASKPARMS on page 528
TASKSTATUS on page 528
TRUNC on page 529
UPPER on page 530
USERDESC on page 530
USERNAME on page 531
V on page 531
VARIABLENAME on page 532
VARIABLEVALUE on page 532
VOTINGDISPARITY on page 532
VOTINGRESULT on page 533
VOTINGTIE on page 533
WORKINGDIR on page 534
XML on page 534

In addition, you can use these "pre-parser" functions when you need an expression that contains elements
for other functions or expressions:
•
•

TGC on page 529
TV on page 530

Using filter functions
Filter functions are basically used to retrieve a value from somewhere and return it as a string enclosed in
single quotation marks. This applies to these filter functions:

Infor Mongoose Core Extensibility Guide | 85

Application Event System
•
•
•
•
•
•
•
•

FE on page 501
FGC on page 501
FILTER on page 502
FILTERMETHODPARM on page 503
FILTERPROPERTY on page 503
FP on page 503
FSV on page 505
FV on page 506

The sole purpose of the filter functions is to construct phrases for SQL WHERE-clauses, which are used in the
FILTER() keyword. So it follows that the only use of the standalone FILTER() function is when constructing a
phrase for a SQL WHERE-clause using an expression that is not a PROPERTY, V (variable), E (event parameter),
SV (session variable), GC (global constant), or METHODPARM (method parameter), because all of those have
corresponding filter functions that you could use without bothering about FILTER() itself.
For example, a parameter named Prefix is passed to your application event. This application event is designed
to do something to all the items whose item ID code begins with the prefix. You can load those items using a
Load IDO Collection action with the following parameters:
IDO("SLItems")
FILTER( SUBSTITUTE("Item LIKE {0}", FILTER( E(Prefix) + "*" ) ) )
PROPERTIES("Item, Description")

Now suppose your application event fires and is passed a prefix of AL.
The expression inside the first FILTER() keyword works like this (from the inside out):
•
•
•

E(Prefix) + "*" evaluates to: AL*

The FILTER() around that places single quotes around it:'AL*'
The SUBSTITUTE() function turns that into: "Item LIKE 'AL*' "

This resulting string is an ideal SQL WHERE-clause, because the IDO Runtime Service turns the asterisk into
a percent-sign that SQL Server understands.
Note: On the other hand, an FE(Prefix) expression would evaluate to " 'AL' ", which is not conducive to
getting the asterisk inside the single-quotes where we need it. So, in effect, we are postponing the wrapping
of the single-quotes until just the right time.
The same effect could be implemented using another level of SUBSTITUTE(), or string concatenations using
quoted quotes, but FILTER() is cleaner.

About event variables and initial states
If an application event handler has variables associated with it, those variables can each be assigned an initial
state. These values are assigned when the event handler starts to execute. Each initial state consists of these
attributes:
•

A name that identifies it in the system

Infor Mongoose Core Extensibility Guide | 86

Application Event System
•

Any number of event variables with the initial values they are to have when the event handler starts to
execute

For example, suppose you want to use these initial values with your event handlers variables:
Name of variable

Initial value

Increment

250

ItemTypes

AB

TimesRemaining

GC(MaxTimes)

Comments

In this case, the value is determined by the value of the MaxTimes global constant.

Initial states are defined using the Event Variable Groups form. Once created, you can use a defined initial
state with any other event handler by selecting it in the Initial State field on the Event Handlers form.

About application event global constants
An event global constant is a named static value that event expressions can reference and use during the
processing of associated event handlers.

Defining and using event global constants
Application event global constants are defined using theEvent Global Constants form.
The application references a global constant by using a function mechanism that allows dynamic evaluation
at each reference.
Event global constants are typically used when defining a set of choices to offer the recipients of a prompt
message. For example:
On the Event Global Constants form, specify this information:
Name
Specify PromptChoicesYesNo.
Access As
This field displays the current Access As identifier, which identifies who created the metadata object.
Value
Specify 1,sYes,0,sNo.
You can then reference this constant for any prompt event action by using the expression:
CHOICES(GC(PromptChoicesYesNo))

Infor Mongoose Core Extensibility Guide | 87

Application Event System

Example: Using an event global constant
Suppose you have one person in your organization who is authorized to review and set customer credit limits.
You can use an event global constant to refer to that person and then use it in a variety of ways. You can set
up these events:
•
•
•

Email this person when an order exceeds a customer's credit limit.
Notify this person when a customer has been placed on credit hold for some reason.
Prompt this person for a response to a request for an increase to a customer's credit limit.

Later, if this person is replaced by another person who has the same respnosibilities, you can simply change
the reference on the Event Global Constants form, so that all these actions involve the new person instead.

About the Application Event System design forms
Your Mongoose-based application includes a set of specialized forms created to enable you to create and use
your own custom application events. With the exception of the Access As form and the System Configuration
Parameters form in this table, these forms are located in the Explorer under Master Explorer > System >
Event System. The Access As and System Configuration Parameters forms are located at Master Explorer
> System.
Default access to these forms is controlled by the System Administration authorization group.
This table lists and briefly describes the use of the Application Event System (AES) design forms:
Form name

Description

Access As

Although not directly used in the creation and customization of application events
and handlers, this form displays the current Access As setting.
The Access As value is an indicator of which AES elements you are authorized to modify and delete.

Events

Use this form to name application events. Once named here, application events are
available on other forms as well, particularly the Event Triggers form and the Event
Handlers form.

Event Triggers

This form is used to define event triggers, which set conditions that cause a named
application event to fire.

Event Handlers

Use this form to display and define event handlers, which determine the work to be
done when an application event fires.

Event Handler
Diagram

Use this form to present a graphical representation of an application event handler
flow. You can also use this form to access the Event Actions form for selected event
actions to view or modify them. Finally, you can add event actions to an event handler
flow by using this form.

Event Handler
Sequence

Use this form to change the order (sequence) of any handlers that have the current
Access As identifier as indicated on the Access As form.

Infor Mongoose Core Extensibility Guide | 88

Application Event System

Form name

Description

Event Actions

Use this form to define and display the actions to be performed by a particular event
handler during its execution. These actions are the individual tasks accomplished by
the event handlers.

Event action parameter forms

Use these forms to define the event action parameters for each action type.

Event Variable
Groups

Use this form to define and display initial states, which are sets of event variables with
the initial values they are to pass to the event handler when it starts to run.

Event Global
Constants

Use this form to display and define event global constants, which are static values that
can be accessed and used by expressions during the running of an event handler.

System Configuration Parameters

Use this form to define certain parameters that affect the system configuration.

Workflow Event
Handler Activation

Use this form to activate predefined event handlers that represent common workflows.
You must specify some information, such as users or email addresses that are notified
when an application event occurs. You can also copy and modify these event handlers.

Although not directly used in the creation and customization of application events
and handlers, you can use this form to control some aspects of how application events
and handlers behave on the system. These aspects are concerned mostly with retest
and reset intervals that globally govern when and how often various conditions can
be retested or events can retry.

Note: This form is not available with all Mongoose-based applications, including
Mongoose as a stand-alone application.
See the online help in your application for this form.
This diagram shows the functional relationship between the design forms and elements for the application
event system:

Infor Mongoose Core Extensibility Guide | 89

Application Event System

Setting up custom application events and handlers
To create and use your own custom application events, there are several important steps and considerations
to keep in mind. You must take these measures:
•
•
•

Design and define your custom event.
Carefully plan and set up the order in which event handlers and actions are to execute.
Periodically refresh the metadata cache to ensure that you are working with the most current version of
the event metadata.

Designing a custom application event
When you create a custom application event, you must also define what fires the application event. In the
current application framework, there are several ways to generate a custom event. You can use these methods:
•
•
•

Create an event trigger by using the Event Trigger form. This step is the easiest and most common way
to define what generates a custom application event.
Use Generate Event action type in another event handler.
Use a form event handler with a response type of Generate Application Event.
Note: Do not confuse the form event handlers with the Application Event System handlers. Form event
handlers are limited to operating within specific forms. Application event handlers can be used by any
forms or other global objects.

•

Use the Mongoose API to write a custom script that generates an application event.

Infor Mongoose Core Extensibility Guide | 90

Application Event System
•
•
•

Invoke an IDO Extension Class method that calls the FireApplicationEvent() method.
Use the Mongoose REST service, IDORequestService/MGRestService.svc.
Use a SQL stored procedure or trigger to call the PostEventSp stored procedure.

These steps represent a typical process for creating custom application events:
1

Optionally, on the Events form, specify the name of the application event before you define how it should
be triggered or handled.
Note: If you do not name the application event on the Events form, you can specify the application event
name on the Event Triggers form or the Event Handlers form. However, application events that are
named on those forms are not displayed on the Events form.

2

On the Event Triggers form, define one or more triggers that fire the application event.
Note: This step is required only if you want to fire the application event by using an event trigger.

3

On the Event Handlers form, define one or more event handlers that execute when the application event
fires.
Note: Each application event can have multiple handlers that execute when the application event fires.
The order in which multiple handlers execute is controlled by a number of factors.

4
5
6
7

On the Event Actions form, define one or more event actions for each event handler.
If required, on the Event Variable Groups form, name and define an initial state for the event handler
to use.
If required, on the Event Global Constants form, name and define any global constants for the event
handler to use.
Test the application event and its triggers and handlers on a test system before implementing them on
your live system.

Ordering application event handlers
When an application event handler is first defined and saved, the application automatically assigns a handler
sequence number. When the event handler is first saved, the application checks to see if there are other
handlers associated with the named application event. Depending on the results of that check, the application
then assigns the number 1 to the handler (if there are no other handlers associated with the event) or the
next available integer (if there are other handlers associated with the event).
In general, then, if an application event uses multiple event handlers, by default, the application uses the
handler sequence numbers to determine the order in which the handlers execute.
However, It is possible to indirectly alter this default order. This can be done by taking one of these actions:
•
•

Use the Keep With and Chronology fields on the Event Handlers form.
Move your own adjacent event handlers up or down in the sequence by using the Event Handler Sequence
form.

To alter the default order in which event handlers execute using the Keep With and Chronology fields on the
Event Handlers form, use this procedure:

Infor Mongoose Core Extensibility Guide | 91

Application Event System
1

Optionally, from the Keep With drop-down list, select the event handler that you want to use as a reference
point and anchor for the current handler.
Note: This step is not required if you are using the First or Last option in the Chronology field.

2

From the Chronology drop-down list, select the option that you want the current handler to use with
respect to the handler you selected in the first step.
This table shows the list of options:
Option

Description

First

This option executes the current handler before any other handlers.

Before

This option executes the current handler just before the referenced handler.

Instead

This option executes the current handler in place of the referenced handler.
In this case, the referenced (original) handler does not execute at all.

Exclusively Instead

This option executes the current handler instead of the referenced handler
and any other handler that may be referenced to execute instead of that same
handler.

After

This option executes the current handler immediately after the referenced
handler.

Last

This option executes the current handler after all other handlers have finished.

Resequencing application event handlers
To change the sequence in which your event handlers execute for a specified application event, use the Event
Handler Sequence form.
Note: When resequencing application event handlers, keep these ideas in mind:
•
•

•

This form is intended to be accessed as a linked form, only from the Event Handlers form through the
Resequence button. It cannot be called directly.
You can change the order only of event handlers that have the same Access As identifier as you, and then
only if they are grouped together (that is, adjacent to one another in the sequence). You cannot use this
form to change the sequence of event handlers with other Access As values.
To change the order of your event handlers with respect to those of others (that is, with different Access
As identifiers), use the Keep With and Chronology fields on the Event Handlers form.

To change the sequence of an event handler, follow these steps:
1
2

In the Grid View, select an event handler that has your Access As value.
To move the selected handler up or down in the sequence, keeping in mind the restrictions mentioned
previously, use the Up and Down buttons.
If you attempt to violate the restrictions, the application generates an error, and you cannot complete
the move.

3

Save your changes.

Infor Mongoose Core Extensibility Guide | 92

Application Event System

Ordering application event actions
A single application event handler can include multiple event actions. When you define an event action, you
must assign an action sequence number in the Action Sequence field of the Event Actions form. You can
assign any number you want in that field and the system automatically sorts the actions in the correct sequence
on the Event Handlers form. When the event handler executes, the system uses this Action Sequence number
to determine the order in which the actions execute.
The only exception to this rule is that, if you select a particular action (other than 1) in the Initial Action field
of the Event Handlers form, then processing of the event actions begins with the designated action and
proceeds from there. So, for example, if you have four actions associated with a handler, and you later decide
that you want action number 3 to be the starting point, you would select 3 in the Initial Action field. In this
case, only actions 3 and 4 execute. The system skips over actions 1 and 2. You can later execute these actions
by using a Branch action type or a Goto action type, with one of these actions (1 or 2) as the destination.

Determining the names of IDO collections and properties
To create custom application event handlers and actions, you are often required to know the internal names
of the collections, known internally as IDO collections, and the properties you want to refer to. For example,
to set up a handler, you often need the name of the IDO collection associated with a particular form. To include
dynamic content in the subject or body of a message, you often must know the internal name of a property
within that IDO.
At times, these names are provided in drop-down selection lists, but at other times, you might need to locate
the names manually.

Determining the name of an IDO object
When working in the Application Event System (AES), there are times that you need the name (not caption)
of an IDO object. Use this procedure:
1 Open and check out the form that uses the IDO collection that the object belongs to.
For example, if you were setting up an event handler to work with document attachments, you would
check out and open the Documents form.
2
3
4
5

Launch the designer (Web Designer or Design Mode) for that form.
Verify that the Form Properties sheet is selected.
If you are working in the Web Designer, expand the Details panel.
Select the Collections tab.
The names of all IDO collections associated with that form are displayed in the Collections list at the top
of the tab. Usually, there is only collection, which makes it easy to figure out. If more than one collection
is listed, you must take further steps to determine which is the one you need.
The internal name of the IDO is what displays after the colon (:). For example, the name of the IDO
collection that the Documents form uses is "DocumentObjects".

Infor Mongoose Core Extensibility Guide | 93

Application Event System

Determining the name of a property bound to a form component
When working in the Application Event System (AES), there are times that you need the name (not caption)
of an IDO property that is bound to a form component. Use this procedure:
1 Open the form that has the field or other component that uses the property you are working with.
For example, to designate the property that contains name of a document to be attached to a form, you
would open the Documents form.
2
3

Launch the designer (Web Designer or Design Mode) for that form.
Select the desired component.
In our example, you would select the edit field that displays the name of the document to be attached,
labeled as Document Name in the UI.

4
5

Verify that the Component Properties sheet is selected.
On the Component Properties sheet, expand the Data Source group of properties and locate the Binding
property field.
The name of the property bound to that component displays in that field, after the period.
In our example, the property for the name of the document displays in the Binding field as object.Docu
mentName. Thus, the Document Name field is bound to the property named DocumentName. That is the
name you will need in the AES event action.

Refreshing the metadata cache
Because certain event metadata is cached for faster performance, the IDO metadata cache must be refreshed
periodically—usually after changes to event metadata (that is, after making changes to events, handlers,
actions, triggers, and global constants). This must be done, at a minimum, after doing development work,
before testing, and after synchronizing the metadata on your system.
Note:
•

•

If you have multiple Application Servers in your system, you must refresh cached metadata for each
Application Server on which metadata might have been cached. The best way to do this is by unloading
global objects from your system.
Any event metadata that is not referenced within two minutes is automatically flushed from the cache
and refreshed. That is why you might notice that things work the way you expect, even without manually
refreshing the cached metadata. We recommend, as a precaution, however, that you manually refresh
the cached metadata to be sure.

These are the ways you can refresh the cached metadata:
•
•

Click the Discard Cache button on the Utilities tab of the Configuration Manager.
Unload global objects.
This requires that you first select the Unload IDO Metadata with Forms option in User Preferences.

•

Restart the IDO Runtime Service on the Applications Server or the local instance of the IDO Runtime host
service.

Infor Mongoose Core Extensibility Guide | 94

Application Event System

Tracking Application Event System status
Some application events can take a considerable amount of time to process, especially if they involve event
messages that require responses from the recipients. The system provides a number of tools and forms that
allow you to track the status of application events as they execute and after they have finished executing.
Some of these forms also allow you to temporarily adjust the behavior of handler execution.
These forms are located in the Explorer under Master Explorer > System > Event System.
Initially, these forms can be accessed only by members of the System Administration authorization group.
These forms can all be used to track various aspects of event system status:
•
•
•
•
•
•

Event Status form
Event Handler Status form
Event Queue form
Event Revisions form
Event Handler Revisions form
Suspended Updates form

About Application Event System messages
Messages to users can be generated in the Application Event System (AES) in either of these ways:
•

By the application itself, as part of the actions of an application event handler
Note: Only Notify and Prompt action types can generate messages.

•

By other users, using the Send Message form

Each message is visible only by the recipients and optionally, the sender of that message.
Event message-related forms
Application event message-related forms are used to view, sort, file, respond to, and send messages generated
within the AES. These message forms reside in the Master Explorer > System > Messages folder.
The AES uses these message-related forms:
•
•
•

Inbox
Saved Messages
Send Message

Responding to application-generated messages
If a message is the result of a application-generated prompt, each recipient can respond to the prompt, usually
by means of a set of voting buttons.

Infor Mongoose Core Extensibility Guide | 95

Application Event System
If the message is also sent to the recipient’s external email inbox and the recipient responded to the email
message, then the message in the refreshed Inbox is marked as expired and the buttons are inactive, so the
recipient cannot respond twice.
If the message is application-generated and involves variables, for each variable, depending on the Variable
Access setting for the event variable (on the Event Actions form) or initial state (on the Event Variable Groups
form) or payload status (on the Event Action Notify or Event Action Prompt form), recipients can respond
in any of these ways:
•
•
•
•

Provide an optional response
Provide a mandatory response
Only read the variable value
Not see the variable value at all

Setting access to Inbox variables
The effective visibility and writability of each variable displayed on the Inbox form is determined by these
factors:
•
•

What type of action generated the message (Notify or Prompt)
The (optional) variable access level as specified in any combination on these forms:
•
The Event Action form for the action itself
•
The Event Variable Groups form for the event handler’s initial state
•
The Event Action Notify or Event Action Prompt form for the action with regard to the payload
status of the property that corresponds to the variable, when the handler is associated with an IDO
event

For Notify type messages, the default for variable access is Read-Only. You can use the variable access options
to override this to Hidden for each variable.
For Prompt type messages, the default for variable access is Writable. You can use the variable access options
to override this to Hidden, Read-Only, or Mandatory for each variable.
To change the value of a variable that may appear with a message in the Inbox form, you can perform any of
these actions:
•
•
•

Specify the data, using the Variables tab on the Inbox form as part of a response to a Prompt action.
Use a Set Values event action with this syntax: SETVARVALUES(VariableName=expression)
Use any of various event actions that can set a variable on output by using this syntax: RV(VariableName)

Setting translatable captions for Inbox variables
On the Inbox form, the Variables > Caption column displays the contents of the Notify or Prompt message's
variable captions in the current user’s language. This assumes that these conditions are true:
•
•

The Caption component attributes are set to interpret the bound contents.
The Caption contains a translatable string name.
Infor Mongoose Core Extensibility Guide | 96

Application Event System
For payload variables resulting from an IDO event, this string name can come from an IDO property’s Label
String ID attribute. For non-payload variables that are created by an event action, define this string name by
using the Variable Access tab on the Event Actions form.

Moving messages between folders
You can use the Saved Messages form to move messages from one folder to another. If the folder does not
already exist, you can create the folder at the same time.
1
2
3
4

5

Open the Saved Messages form.
From the Folder Name field, select the folder in which the message you want to move is currently placed.
All messages in that folder are displayed in the grid.
Select the message that you want to move.
In the Folder Name field, perform one of these actions:
•
To move the message to an existing folder, specify the name of the folder.
•
To create a new folder and move the message to that folder, specify the name of the new folder.
Save your changes.

Sending email to an external email Inbox for prompts
If a user is set up in the Users form to allow the system to send external prompts, then a Prompt action sends
Application Event System messages both to the application's Inbox form and to the recipient’s Inbox in an
external email client such as Microsoft Outlook. The message that is sent to the external email system is an
HTML-formatted email that consists of these parts:
•
•
•
•
•
•
•
•

Original subject in Subject line
Category
List of internal recipients
List of internal Cc recipients
Original Message (either in plain text or HTML format)
Original Question
Choices, as individual hyperlinks to a .NET active server page (ASP) that records the vote
Payload, which is the contents of the Variables grid from the Inbox

Text in the Subject, Category, Message, Question, Choices, and payload elements can be translated and
formatted based on the default language specified for the recipient user in the Users form.
When the recipient votes by clicking the link in the email, their Windows-default web browser opens or
launches a new tab, as applicable, and sends the information from the link they clicked to the ASP URL that
is included (and hidden) in the email. This URL is built by the system based on the Web Server list.
The ASP then registers the vote programmatically, as if the recipient had logged into the system, displayed
the same message in the Inbox, and selected the corresponding Choice button on the Response tab.
The web page then displays a success or failure message.

Infor Mongoose Core Extensibility Guide | 97

Application Event System
The message displays both in email and in the Inbox form, but only one response is allowed, according to
these protocols:
•
•

If users respond from email first, when they display the Inbox (properly refreshed), the message is expired
and the buttons are inactive.
If users respond from the Inbox first, then clicking a link in the external email brings up the web page
with a message that the message has expired and they already voted, using the previously selected
choice.

About prompt choices and responses
If a message is the result of a Prompt action in the Application Event System (AES), the sender of that message
can request a specific response from each recipient, usually in the form of a button-vote mechanism. In such
cases, the system must be set first to wait for responses, then to know how to handle responses as they are
received, and finally, be instructed what to do when responses are not received within a specified timeout
period.
Incoming prompts request a response from recipients (using the Question field on the Inbox form or the
external email) and display a set of choices. The choices are displayed in the form of voting buttons in the
Response tab area of the Inbox form, or in the form of links in an external email. For example, a prompt may
include buttons or links labeled like any of these:
•
•
•

Approve or Disapprove (default option)
Yes or No
OK, Send More Info, or Cancel

To customize the choices for a prompt, you must include a Prompt action with a Choices parameter as part
of the event action definition. This Choices parameters consists of the CHOICES function followed by a string
expression that evaluates to a comma-separated list that contains an even number of elements (value/label
pairs). For example, if you want the voting buttons to be labeled 'Yes' and 'No', with corresponding values
returned to the action to be '1' and '0', you might include this parameter:
CHOICES("1,sYes,0,sNo")

In this example, the strings sYes and sNo are translatable strings. These are defined for the system as "Yes"
and "No", respectively.
If you want your button labels to be localized, for the recipient, you must take one of these additional steps:
•
•

Use names of existing translatable strings (as found in the Strings table).
Add your own strings, using the Strings form, and provide the necessary translations.
To open the Strings form, you must be in a designer (the Web Designer or Design Mode) and then, from
the Edit menu, select Strings.

If localization is not an issue, you can also use a literal value that displays on the button verbatim. To specify
the string as a literal value here, simply specify it as a list value. If the system does not find the string in the
Strings table, the system automatically treats it as a literal value.

Infor Mongoose Core Extensibility Guide | 98

Application Event System

Using custom entry forms
For most Prompt messages—that is, messages that require a response from the recipients—you can gather
responses from recipients using the Question and Choices fields. In some cases, you might need to gather
more specific or detailed responses. You might want to collect the responses by means of a custom form.
1

Create the custom entry form.
We recommend that you create the form from scratch.
Include buttons or other devices to allow the recipient to indicate the desired choice.
Use form event handlers to define how these buttons behave. To return the recipient's choice, each of
your form's buttons should generate an event, for example, Accept, with two handlers. The first handler,
of Set Values type should set the variable StdVote to the positional number of the voting result; for
example 1, or 2, or 3. The second handler should generate the event StdFormExitOk. You should also
include a button to allow the recpient to exit without voting. This button should generate an event, for
example, Cancel, with a single handler that generates the form event StdFormExitCancel.

2

Include any display fields that you might need to display relevant information.
To display an event variable in a component, bind it to a form variable named for that event variable.
For example, to display the Row.CoNum event variable, create an component of type Edit and set its
Data Source Binding attribute to variables.Row.CoNum. At runtime, the component displays, automatically
enabled and/or decorated according to that event variable's Variable Access setting (Hidden, Read-Only,
Writable, or Mandatory).
To display other information contained in the Prompt message, you can similarly bind components to
these form variables:
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

StdFrom
StdTo
StdSubject
StdMessage
StdSent
StdCC
StdQuestion
StdResponseDate
StdExpiresAfter
StdExpired
StdRead
StdVote (set to the SelectedChoice property for the current message, or -1 if the message is expired)

You can also use this custom form to collect data values, perform calculations, or do anything else you
want it to do. You can perform initialization actions that depend on the above form variables by adding
handlers on the StdInboxPayloadInitCompleted event.
3
4

Create an application event action for a Prompt action type.
Click Edit Parameters on the Event Actions form.
This launches the Event Action Prompt form.

5
6
7

In the Entry Form field, specify the name of the custom entry form you created.
Set up the rest of the Prompt action as desired.
Save your work and return to the Event Actions form.

Infor Mongoose Core Extensibility Guide | 99

Application Event System

Note: The Send External Prompts check box might be selected for some users on the Users form. In this
case, you must include a note in the body of the prompt email, instructing users to log in to this application
to enter the requested data or perform the actions needed on the custom form.

About voting rules in AES prompts
When a prompt in an Application Event System (AES) action is sent to a single recipient, the result of the
prompt is the return value from the choice of that recipient. However, when a prompt is sent to multiple
recipients, you must select a vote-counting method to determine the result of the prompt and include a Voting
Rule (VOTINGRULE) parameter in your event action definition.
This table lists and describes the available voting rules.
Rule

Description

Majority

To win, a choice must receive more than 50% of the total votes. As soon as more
than 50% of the recipients respond with a particular choice, that choice wins,
and the vote is closed.
For example, suppose a prompt went to nine recipients. Of the first six to respond, five vote in favor. At that point, the vote is over with this option, because
five votes is more than 50%. The event handler moves on, and it does not matter
what the remaining three recipients do.
If you use this voting rule, you should use a Voting Tie (VOTINGTIE) parameter
to tell the system how to handle a tied vote.

Plurality

The choice with the highest number of votes wins, even if the choice does not
receive more than 50% of the vote.
For example, suppose three choices are offered, resulting like this:
•
•
•

The first choice receives 24% of the vote.
The second choice receives 43% of the vote.
The third choice receives the remaining 33% of the vote.

In this case, the second choice wins, even though the votes received are less
than 50% of the total vote.
If you use this voting rule, you should use a Voting Tie (VOTINGTIE) parameter
to tell the system how to handle a tied winning vote.

Infor Mongoose Core Extensibility Guide | 100

Application Event System

Rule

Description

Conditional Plurality

The choice with the highest number of votes wins, but only if a specified minimum percentage of the vote count is reached.
If you use this rule, you must also include a Minimum Percentage (MINIMUM)
parameter.
For example, if three choices are offered to 19 recipients, and you specify a
minimum percentage of 40% to win, then these are possible results:
•
•

In an 8-7-4 split, the choice with eight votes wins because this choice meets
the minimum percentage.
In a 7-6-6 split, there are no winners, because no choice meets the minimum
percentage. In this case, the application must deal with the vote as an indeterminate result.

Note: With a simple Plurality vote, the choice that reaches seven votes in a 76-6 split wins.
If you use this voting rule, you should use a Voting Tie (VOTINGTIE) parameter
or Voting Disparity (VOTINGDISPARITY) parameter to tell the system how to
handle the vote should it result in a tie or indeterminate result.
Minimum Count

The first choice to reach a specified minimum number of votes wins.
If you use this rule, you must also include a Minimum Count (MINIMUM) parameter.
For example, if three choices are offered to 13 recipients, and you specify a
minimum of five votes to win, the first choice to receive five votes automatically
wins.
Note: As soon as the minimum count is reached, event handler execution moves
immediately to the next action. In this case, the system expires any responses
not yet received, and no further voting can take place.

Minimum Percentage

The first choice to receive a specified percentage of the vote wins. The percentage is based on the number of recipients of the prompt, not the number of respondents.
If you use this rule, you must also include a Minimum Percentage (MINIMUM)
parameter.
Note: As soon as the minimum percentage is reached for a choice, event handler
execution moves immediately to the next action. In this case, the system expires
any responses not yet received, and no further voting can take place.

Earliest Response

The first response to the prompt wins, regardless of the choice.
Note: As soon as the first response is received, event handler execution moves
immediately to the next action. In this case, the system expires any responses
not yet received, and no further voting can take place.

Infor Mongoose Core Extensibility Guide | 101

Application Event System

Rule

Description

Preferred Choice

If any one respondent votes for the preferred choice, that choice wins. In a case
where none of the respondents select the preferred choice, then this rule behaves as the Plurality rule for the remaining choices.
If you use this rule, you should also include a Preferred Choice (PREFCHOICE)
parameter to specify which choice is the preferred choice.
For example, if you have three choices, and you specify the first choice as the
preferred choice, then these might be the results:
•
•

If anyone votes for the first choice, that choice wins.
If the end vote is a 0-6-5 split, the second choice wins.

Note: As soon as the preferred choice receives a vote, event handler execution
moves immediately to the next action. In this case, the system expires any responses not yet received, and no further voting can take place.
Minimum Count Preferred Choice

If a specified number of votes for a specified choice is cast, that choice wins. If
you use this rule, you must also include a Minimum (MINIMUM) parameter to
specify the minimum count, and a Preferred Choice (PREFCHOICE) parameter
to specify which is the preferred choice.

