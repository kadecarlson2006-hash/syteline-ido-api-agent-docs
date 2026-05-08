---
name: syteline-events
description: "Events and event handlers - form event model, standard events, custom events, handler response types, standard operations"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

use Boolean AND filtering with any end-user filtering or security rules for
row-level filtering.

Binding collections to a form
The act of adding an IDO collection to a form automatically binds the collection to the form. Most forms
have IDO collections bound to them at the time the form is created.
If a form has no bound IDO collection, the first IDO collection that you add automatically becomes the
primary collection.
You can also add other IDO collections to a form that is already bound to a primary collection. These
secondary collections can be used as subcollections.
Once added, you can remove any secondary collection from a form. You cannot remove a primary
collection.
When working with forms and collections, you can:

Infor Mongoose Application and Form Development | 155

Working with Forms
•
•
•

Add an IDO collection to a form
Edit IDO collection specifications
Remove a secondary IDO collection from a form

Binding of components
Binding of collection data most commonly takes place at the form level. However, individual components
can also be bound to properties in a collection.
There are two types of component binding in Mongoose-based forms:
•
•

Scalar binding: This is the binding of a single property value of the current row of the collection.
Collection binding: This is the binding of all property values of a collection to a grid component or
other component type that allows the display of any or all property values in an IDO collection. This
type of binding typically allows the user to navigate the collection.

The binding of components is accomplished in the Web Designer/Design Mode, on the Component
property sheet, under Data Source > Binding. When looking at the Binding specification for a given
component in the Component property sheet, the "object" or "objects" part of the binding refers to a
particular collection, as in these examples:
•
•

•
•
•

object.property refers to a scalar binding to the designated property (property) of the primary
collection (object).
objectn.property refers to a scalar binding to the designated property (property) of a designated
secondary collection (objectn, where 'n' refers to the number of the designated secondary
collection).
objects refers to the binding of the entire primary collection.
objectsn refers to the binding of the entire secondary collection (where 'n' refers to the number
of the designated secondary collection).
object.subcol.property refers to a scalar binding of a designated property (property) in a
subcollection (subcol).

Saving one row per transaction
With primary and secondary collections, you can instruct Windows client to save each modified row
(IDO item) in a separate transaction. When the user executes the save operation on the collection,
Windows client loops through modified rows, executing a Save Current operation for each row. If the
save succeeds, WinStudio moves to the next row. If it fails, WinStudio stops and presents an error
message. The previous rows remain committed.
In the default behavior, all modified rows are sent to the middle tier to be processed within a single
transaction, which is rolled back if there is a failure.
Note: If your form design requires that all modified rows be committed as a unit, with processing on
the unit before and/or after a save operation, saving rows in separate transactions might not be
appropriate: If an error occurs on a row, some rows might be committed and others not committed.

Infor Mongoose Application and Form Development | 156

Working with Forms

Enabling pagination on a primary collection
To enable pagination on a primary collection:
1

Perform one of these steps:
• In the Windows client, open the form that you want to modify, then go into Design Mode.
• In the web client, launch the Web Designer, then open the form that you want to modify.

2

Add these page control components:
• Page number field, for example: Page 1 out of 28
• Current page field
• Previous button
• Go to page button
• Next button

3

In the Form > Behavior section, specify this information:
Initial Command
Specify Refresh.
Script Language
Select Visual C#.

4
5
6

7
8

Right-click on the form, then select Form Script.
Add the form script for the page controls. See the sample form script.
Add events for each button:
Button

Event

Previous

GotoPrevPage

Go to page

GotoPage

Next

GotoNextPage

On the Form > Collections tab, select the Primary Collection.
In the Options > Advanced Options section, specify this information:
Record Cap Override/Page Size
Specify the record cap or the page size of the selected collection.
Enable Paging
Select True.

9 Click Save.
10 Reset IDO cache, if necessary.
11 Test the form.

Infor Mongoose Application and Form Development | 157

Working with Forms

Example of form script code for the page control components:
using System;
using Mongoose.IDO.Protocol;
using Mongoose.Scripting;
using Mongoose.Core.Common;
using System.Diagnostics;
namespace Mongoose.FormScripts
{
public class TestSinglePage : FormScript
{
/// <summary>
/// Triggered when Go to Page button (Primary Collection) is clicked
/// </summary>
public void GotoCurrentPage()
{
int pageNum = ThisForm.Variables( "CurrentPageVar" ).GetVal
ue<int>();
ThisForm.PrimaryIDOCollection.GotoPage( pageNum );
DisplayCurrentPage( pageNum );
}
/// <summary>
/// Triggered when Go to Next button (Primary Collection) is clicked
/// </summary>
public void GotoNextPage()
{
int pageNum = ThisForm.Variables( "CurrentPageVar" ).GetVal
ue<int>();
int maxNumPage = ThisForm.PrimaryIDOCollection.GetNumPages();
int nextPage = pageNum >= maxNumPage ? maxNumPage : pageNum + 1;
// Do not proceed if requesting the same page number
if ( pageNum == nextPage )
return;
ThisForm.PrimaryIDOCollection.GotoPage( nextPage );
DisplayCurrentPage( nextPage );
}
/// <summary>
/// Triggered when Previous button (Primary Collection) is clicked
/// </summary>
public void GotoPreviousPage()
{
int pageNum = ThisForm.Variables( "CurrentPageVar" ).GetVal
ue<int>();
int prevPage = pageNum <= 1 ? 1 : pageNum - 1;
// Do not proceed if requesting the same page number
if ( pageNum == prevPage )

Infor Mongoose Application and Form Development | 158

Working with Forms

return;
ThisForm.PrimaryIDOCollection.GotoPage( prevPage );
DisplayCurrentPage( prevPage );
}
/// <summary>
/// Fills up Paging Info, and the Current page of Primary Collection
/// </summary>
/// <param name="pageNum"></param>
public void DisplayCurrentPage( int pageNum )
{
ThisForm.Components["CurrentPageEdit"].Value = string.Format(
"Page {0} out of {1}", pageNum.ToString(), ThisForm.PrimaryIDOCollec
tion.GetNumPages().ToString() );
ThisForm.Variables( "CurrentPageVar" ).SetValue( pa
geNum.ToString() );
}
#region SubCollection
/// <summary>
/// Triggered when Go to Page button (SubCollection) is clicked
/// </summary>
public void GotoCurrentPageSub()
{
int pageNum = ThisForm.Variables( "CurrentPageSubVar" ).GetVal
ue<int>();
ThisForm.PrimaryIDOCollection.GetSubCollection( "Properties", 0
).GotoPage( pageNum );
DisplayCurrentPageSub( pageNum );
}
/// <summary>
/// Triggered when Next button (SubCollection) is clicked
/// </summary>
public void GotoNextPageSub()
{
int pageNum = ThisForm.Variables( "CurrentPageSubVar" ).GetVal
ue<int>();
int maxNumPage = ThisForm.PrimaryIDOCollection.GetSubCollection(
"Properties", 0 ).GetNumPages();
int nextPage = pageNum >= maxNumPage ? maxNumPage : pageNum + 1;
ThisForm.PrimaryIDOCollection.GetSubCollection( "Properties", 0
).GotoPage( nextPage );
DisplayCurrentPageSub( nextPage );
}
/// <summary>
/// Triggered when Prev button (SubCollection) is clicked
/// </summary>
public void GotoPreviousPageSub()
{

Infor Mongoose Application and Form Development | 159

Working with Forms

int pageNum = ThisForm.Variables( "CurrentPageSubVar" ).GetVal
ue<int>();
int prevPage = pageNum <= 1 ? 1 : pageNum - 1;
ThisForm.PrimaryIDOCollection.GetSubCollection( "Properties", 0
).GotoPage( prevPage );
DisplayCurrentPageSub( prevPage );
}
/// <summary>
/// Fills up Paging Info, and the Current page of Subcollection
/// </summary>
public void DisplayCurrentPageSub( int pageNum )
{
ThisForm.Components["CurrentPageEditSub"].Value = string.Format(
"Page {0} out of {1}",
pa
geNum.ToString(),
ThisForm.Pri
maryIDOCollection.GetSubCollection( "Properties", 0 ).Get
NumPages().ToString() );
ThisForm.Components["PageNumberEditSub"].Value = pa
geNum.ToString();
}
#endregion
}
}

Enabling pagination on a subcollection
To enable pagination on a subcollection:
1

Perform one of these steps:
• In the Windows client, open the form that you want to modify, then go into Design Mode.
• In the web client, launch the Web Designer, then open the form that you want to modify.

2

Add these page control components:
• Page number field, for example: Page 1 out of 28
• Current page field
• Previous button
• Go to page button
• Next button

3
4
5

Select the grid component where the subcollection is the data source.
In the Component > Properties > Data Source > Binding property, click the ellipses (…) button.
In the Edit Component Data Binding > Type field, select Subcollection Grid.

6

Click Edit.

Infor Mongoose Application and Form Development | 160

Working with Forms
7

In the Edit Collection Specification for Subcollection > Advanced Options section, perform
these steps:
a Specify the Record Cap Override value.
b Click the Enable Paging? check box.
c Click Set LinkBy.
d In the Edit LinkBy Specification > Parent Property and Child Property fields, select
CollectionName.
e Click Add.
f
Click OK.

8
9

Click OK twice.
Add events for each button:
Button

Event

Previous

GotoPrevPageSub

Go to page

GotoPageSub

Next

GotoNextPageSub

10 Click Save.
11 Reset IDO cache, if necessary.
12 Test the form.

Tips
About refreshing collections
The filter specification for a collection can reference a property, component, or variable. Windows client
creates a dependency between the collection and the property, component, or variable referenced in
the filter specification. (A dependency is created as long as the filter does not specify a property on the
collection itself.) When the value of the property, component, or variable is changed, the dependency
triggers a refresh of the collection automatically.
For example, suppose you want the primary collection on a form to be refreshed whenever the user
changes the value of a combo box that is bound to a variable. If you reference that variable in the filter
of the primary collection, then Windows client creates a dependency so that the primary collection will
be refreshed whenever the variable's value changes. (To reference a variable in the filter of the primary
collection, use the FILTERPERM keyword.)
In cases where one collection should be refreshed because of changes in another collection, you can
set up a collection-subcollection relationship. The built-in functionality of subcollections causes them
to be refreshed automatically as often as necessary. Whenever possible, use subcollections to handle
refreshing multiple collections. Doing so minimizes form complexity and keeps behaviors consistent
across your application.

Infor Mongoose Application and Form Development | 161

Working with Forms
In rare cases, you might need to create a form that works with multiple collections that do not have the
straightforward parent-child relationships characteristic of subcollections. In these situations, you can
set up a primary collection and one or more secondary collections. To refresh the primary collection
based on a changed property in a secondary collection, you can generate a data-changed event on
the component bound to that property and then create event handlers of the type Collection Refresh
to trigger the refresh of the primary collection.

Filters
About query form filters
A filter is a set of criteria for retrieving a collection from a middle-tier IDO. You create a filter for an IDO
by creating a query form associated with the IDO. Afterwards, you can use the query form to create
additional filters for the same IDO. Filters must contain primary criteria (required properties with operators
and values in an AND relation). They can also contain additional criteria (optional properties with
operators and values in an AND or an OR relation).

Creating query form filters
To create a filter for an IDO collection:
1
2

Open the query form associated with the IDO you want to filter.
Enter your criteria.
Filters must contain at least one Primary Criteria. Addition Criteria are optional.

3
4

From the Objects menu, select Filter > Save.
At the prompt, specify a name for the filter.

Editing a query form filter
Note: All fields in the Identification section of this dialog box are read-only. The data in this section
is set at the time the filter is created and cannot later be changed.
1 With any query form open and in Design Mode, from the Edit menu, select Filter > Edit.
2 In the Select Filter dialog box, select the filter you want to edit, and then click OK.
3 Optionally, add properties to the filter. For each property:
a From the All Available Properties field, select a property.
b Click Add To Additional.

Infor Mongoose Application and Form Development | 162

Working with Forms
This adds the property to the list of Additional Criteria Properties.
Note: You cannot add to, modify, or remove from the list of Primary Criteria Properties. To
change these, you must re-create the query form using the New Form Wizard.
4

Optionally, remove properties from the list of Additional Criteria Properties. For each property:
a Select the property to remove in the list of properties.
b Click Remove.

5

Optionally, adjust the label, the list source, or other attributes of the property. For each property
you want to modify:
a Select the property you want to modify from the list of properties.
b Click Attributes.
c Use the Edit Property Attributes for Filter dialog box to modify the property's attributes.
d Click OK.

6

Click OK.

Tip: Create filters that prompt users for value input
If users enter a question mark (?) on a query form, WinStudio can prompt them to select or enter a
value for the property.
To enable this functionality in filters, specify a question mark for the property value when you create
the filter on the query form. Then save and name the filter as normal.

Events and Event Handlers
About the form event model
Note: This topic presents information about the Mongoose form event model, as opposed to the
Application Event System. The primary difference between the two is that form events are operational
only within a single form, whereas application events can be used application-wide.
There are two kinds of form events that can be generated in Mongoose-based applications:
•
•

Standard events for forms are generated automatically as the user interacts with the client. These
are typically generated by standard toolbar actions and/or by Actions menu items.
Custom form events can be created by application developers and generated, based on specified
conditions.

By themselves, events do not perform work. They simply notify the application that something has
happened. The actual response action is performed by event handlers. Event handlers define units of

Infor Mongoose Application and Form Development | 163

Working with Forms
work (that is, responses) that are associated with a specific event type. The work is executed whenever
an event of that type is generated.
Mongoose provides built-in responses for most standard events. It also provides tools for creating
custom events and event handlers. You can create custom event handlers for all standard and custom
events.
There are two basic reasons that you might want to create your own event handlers:
•

To handle the very simple and very common case of performing some work based on an action
by the user.
For instance, you might want to launch a form or call a method when a button is clicked. In this
case, you would create a custom event associated with the button and create an event handler
that performs the required work in response to the event. There are a variety of response types
that can be used, including the execution of methods and scripts.

•

To override the standard application behavior.
In these cases, you might want to add some processing and/or cancel the default processing that
happens in response to a particular standard event. These adjustments can be easily accomplished
by creating a form event handler for the standard form event.

About form event handlers
Form event handlers define units of work to be performed in response to an event. These event handlers
are defined for specific forms. They are not shared across forms.
Note: This topic presents information about the Mongoose form event model, as opposed to the
Application Event System. The primary difference between the two is that form events are operational
only within a single form, whereas application events can be used application-wide.
Event handler sequence
Event handlers are identified by the name of the event that they are handling and by a sequence
number. You can define multiple event handlers for any event. These event handlers execute in
sequence number order. By default, if any event handler reports a failure, the remaining event handlers
in the sequence are skipped. You can override this default by setting the Ignore Failure parameter. If
you set this attribute, the event handler is always treated as successful.
Built-in responses
When you define additional event handlers for standard events that have built-in responses, the built-in
responses always execute last. This means that you can effectively cancel the built-in responses by
reporting failure from a custom event handler for the same event.
Event handler messages
For any event handler, you can define messages to be displayed when the event handler fails or
succeeds. Use the Error Message and Success Message parameters to define each message. The

Infor Mongoose Application and Form Development | 164

Working with Forms
message definition can contain a translatable string name and can also reference any of the standard
substitution keywords. For example, you can use the %B substitution keyword to reference a message
type parameter that is defined for an event handler of the Method Call response type.
By default, messages generated from event handlers are presented in pop-up dialog boxes. Alternatively,
you can specify that they be presented in the application status bar. To do so, use the Message on
Status Line parameter.
Restricting event handler execution to specific collections
By default, event handlers execute regardless of the currently active collection or the specific collection
operation that generated the event. If you are using multiple collections on a form, you can write event
handlers that are specific to each collection. To do so, use the event handler's Only When Current
Collection Is parameter.
When you specify a specific collection for an event handler, form-based events are handled by this
event handler only when the specified collection is active. Collection-based events are handled by this
event handler only when the event is generated by an operation on the specified collection.

About form standard events
Note: This topic presents information about the Mongoose form event model, as opposed to the
Application Event System. The primary difference between the two is that form events are operational
only within a single form, whereas application events can be used application-wide.
This application generates and handles many standard events as the user interacts with forms and
components. The triggering of one standard event often involves a chain of standard events that all
execute in sequence. This is why it is often most advantageous to use standard events whenever
possible, rather than creating custom events (for which you must create your own chain/sequence of
event handlers).

About form custom events
Note: This topic presents information about the Mongoose form event model, as opposed to the
Application Event System. The primary difference between the two is that form events are operational
only within a single form, whereas application events can be used application-wide.
You can create custom form events in three ways:
•

•

You can specify an event name for one or more of a component's events properties (on the
Component property sheet toolbar, click the Events button). Most components support a primary
event, a data change event, a gain focus event, and a lose focus event. For instance, you might
want to generate a custom event when the user clicks a push button. To implement this, you would
specify the custom event name as the push button's primary event.
You can create an event handler that has a response type of Generate Event and that specifies
a new event name.

Infor Mongoose Application and Form Development | 165

Working with Forms
•

You can create a script that generates an event.

Working with form event handlers
In a Mongoose-based application, form event handlers are objects specific to a form that supplement,
alter, or cancel the default event-handling behavior of the application. You can create and name form
event handlers that execute when an event matching the name of the event handler is generated. The
event can be generated by Mongoose as the user interacts with a form, or it can be generated
programmatically.
To create, copy, edit, or delete event handlers for a form:
1

With the form open in the designer,, from the Edit menu, select Event Handlers.
The Event Handlers dialog box lists any event handlers created for the current form.

2

Perform one of these sets of actions:
• To add an event handler, click New. Specify the event for which you are creating an event
handler, the sequence of the event handler, and the response type of the event handler.
• To copy an event handler, select the one you want and click Copy. Then specify a unique
Name for the new event handler and make any other specifications you need.
• To edit an event handler, select the one you want and click Edit. Then adjust whatever
specifications you need to.
• To delete an event handler, select the one you want and click Delete. When prompted, confirm
the deletion.

Event Handler response types
WinStudio has these response types for form events:
Note: The number in parentheses after a response type is that type's enum value.
• Add entry to the audit log (38)
• Binary value action (37)
• Collection copy (25)
• Collection delete (27)
• Collection init with auto insert row(32)
• Collection make current (34)
• Collection navigate (23)
• Collection new (24)
• Collection notes (30)
• Collection notes for current (31)
• Collection refresh (28)
• Collection refresh current (29)
• Collection save (26)
• Conditional action (36)

Infor Mongoose Application and Form Development | 166

Working with Forms
•
•

DLL Call (1)
Execute EXE or other program (2)

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

Note: This response type does not work in the web client.
Form script method (35)
Generate application event (43)
Generate event (21)
Get Location From Browser (50)
Goto Explorer folder (41)
Goto form - replace this form with the new form (4)
Goto form page (5)
Goto URL (39)
Inline script (33)
Invoke user control (45)
Invoke Web service (42)
Load collection (49)
Method Call (0)
Print preview (40)
Prompt (48)
Run background task (19)
Run form (7)
Run form as modal child (3)
Run form as linked child (6)
Run form to retrieve filter (16)
Run script (8)
Send Web container message (47)
Set values (22)
Timer (20)
Validate collection (47)

Collection processing response types
Event handlers with one of the collection processing response types perform actions on a specific
collection. When you define one of these response types, you must specify the collection.
Response type

Description

Collection Copy

Creates a new item in the specified collection and initializes it
to the values currently in the components that are bound to it.
This action is equivalent to the user issuing a Copy operation
when the specified collection is current.

Collection Delete

Marks the current item of the specified collection for deletion.
This action is equivalent to the user issuing a Delete operation
when the specified collection is current.

Infor Mongoose Application and Form Development | 167

Working with Forms

Response type

Description

Collection Init with Auto
Insert Row

Initializes a collection and inserts a new item in the autoinsert
row.

Collection Make Current

Makes the specified collection the current collection.

Collection Navigate

Navigates the specified collection. You must specify which
navigation action to take (next, previous, last or first). This
action is equivalent to the user issuing a Next, Previous, Last
or First operation when the specified collection is current.

Collection New

Initializes a new item in the specified collection and makes it
the current item. This action is equivalent to the user issuing
a New operation when the specified collection is current.

Collection Notes

Issues the Class Notes (Notes for All) operation for the specified collection. This action is equivalent to the user issuing a
Class Notes (Notes for All) operation when the specified collection is current.

Collection Notes for Current

Issues a Notes (Notes for Current) operation for the current
item in the specified collection. This action is equivalent to the
user issuing a Notes (Notes for Current) operation when the
specified collection is current.

Collection Refresh

Refreshes the specified collection. This action is equivalent to
the user issuing a Refresh operation when the specified collection is current.

Collection Refresh Current

Refreshes the current item of the specified collection. This
action is equivalent to the user issuing a Refresh Current operation when the specified collection is current.

Collection Save

Saves the specified collection. This action is equivalent to the
user issuing a Save operation when the specified collection is
current.

Collection Validate

Validates the specified collection. If a validation error occurs,
the collection and component focus is brought to the component with the validation error,

Execute Exe response type
Event handlers with an Execute Exe response type are used to execute any command string that can
be run from the Windows Run prompt. This response type is useful for launching other desktop programs.
Note: This response type does not work in the web client.
To open a document associated with an application, enter the document path in the parameter of the
event handler. You need not specify the application path.

Infor Mongoose Application and Form Development | 168

Working with Forms

Form Navigation response types
Event handlers with one of the form navigation response types run or execute a form based on an
event. For each of these response types, you specify the name of the target form and the filter to apply
to the target form.
Response type

Description

Run Form

Launches the specified form. The current form
continues to run and is available to the user.

Run Form as Modal Child

Launches the specified form. The user is not able
to interact with the original form until the specified
target form is closed. At that time, control returns
to the original form.
The Run Form as Modal Child response type allows you to use the OK/Cancel Optional parameter. Normally, WinStudio displays the OK or
Cancel prompt when users select the Close box
to exit a modal child form. The OK/Cancel Optional parameter eliminates this prompt. When users
click the Close box, the form closes and indicates
that the user exited the child form without executing a Cancel action or an OK action. The WinStudio API exposes this information during the generation of the standard event StdFormCalledFormReturned.

Goto Form

Closes the current form and launches the specified form.

Infor Mongoose Application and Form Development | 169

Working with Forms

Response type

Description

Run Form as Linked Child

Same as the Run Form response type except
that Windows client manages the parent-child
relationship defined between the two forms.
Typically, the data presented in the child form is
related to data presented in the current IDO item
presented on the parent form. When the parent
form is navigated, the child form is automatically
refreshed.
The Run Form as Linked Child response type
takes several special parameters. First, you must
specify the Linkby parameter. The parameter
consists of pairs of properties, one from the parent form and one from the child form, that are
used to filter the child form. The Linkby specification determines the following behaviors:
•

•

•

The child form is populated by those IDO
items whose properties match the values of
the parent form properties. The filter is reset
and the child form is refreshed every time the
parent form is navigated.
WinStudio automatically propagates the
parent properties to the corresponding child
properties for any new items saved from the
child form.
Child form components that are bound to
linked properties are made read-only.

The Run Form as Linked Child response type
allows you to set variables in the child form every
time the parent form is navigated. Specify the
variables you want to set on the child form as the
target variables. For each target variable, specify
the value you want to assign to it. This value
specification may include the standard substitution keywords and may reference properties,
components, and variables from the parent form.
The Run Form as Linked Child response type
takes a parameter to refresh the current item on
the parent form any time the child form's primary
collection is saved. To cause this behavior, select
the Reload After Child Save parameter. Use the
parameter in cases in which saving the child form
might cause data in the parent form's current item
to become obsolete.
Run Form to Retrieve Filter

Runs a form as a query form for the current form.
When the query form exits, its filter criteria are
returned to the current form.

Infor Mongoose Application and Form Development | 170

Working with Forms
Setting variables
You can set variables on the target form. To do so, use the event handler's Set Values parameter and
specify the variables you want to set on the target form as the target variables. For each target variable,
specify the value you want to assign to it. This value specification may include the standard substitution
keywords and may reference properties, components, and variables on the original form.

Generate Application Event Response Type (WinStudio)
An event handler with the Generate Application Event response type generates an event in the
application event system. Only custom events, defined on the Events form, can be generated.
Note that the application event system is distinct from WinStudio's internal event system. The response
type Generate Event on page 171 generates an event in the WinStudio event system; you define handlers
for the event in WinStudio. The response type Generate Application Event generates an event in a
multi-tier, application-wide system; you define handlers separately in that system.

Generate Event response type
Event handlers with a Generate Event response type are used to generate either a standard event or
a custom event. The failure or success status of the generated event becomes the status of the
generating event handler.

Get Location From Browser response type
The Get Location From Browser response type requests the location information from the browser
and then triggers the StdWebLocationInfoReceived event. Once the location is received from the
browser, or an error occurs, the StdWebLocationInfoReceived event is triggered. This is done
asynchronously.
This response type is used with the web client only.
This response type has two optional parameters:
•

•

Timeout Value: Number of seconds allowed for the browser to return a location. After the
number of seconds passes with no response, the StdWebLocationInfoReceived event is triggered
and the form variable StdLocationError is set to TIMEOUT. If there is a browser prompt to the user
to allow the location and there is no answer to that prompt, StdLocationError is set to NOREPLY.
The default value is inifinity, meaning no timeout occurs.
Maximum Age: If a location is requested and a previous location was requested within the time
set in this parameter, no new request is sent to the browser. StdWebLocationInfoReceived is
triggered with the existing values. The default is zero, meaning every request is sent to the browser.

Infor Mongoose Application and Form Development | 171

Working with Forms

Goto Form Page response type
Event handlers with a Goto Form Page response type are used to select a form page. You must specify
the name of the form page to activate.

Inline Script response type
Event handlers with an Inline Script response type allow you to place the text of a short script into the
Parms field on the Event Handler Properties property sheet. Use the Inline Script response type if
the entered script (including parameters) is less than 1,500 characters.
If your script is longer than 1,500 characters, use the Run Script response type that accesses WinStudio
objects and data through the scripting API.
To indicate failure, the script returns a nonzero value. To indicate success, the script must return 0.

Invoke User Control response type
An event handler with the Invoke User Control response type calls into a .NET WinForms user control
and/or Web control that is hosted as a user control component on page 209 on a form. The response
type calls the Invoke method of the control, which is part of the IWinStudioUserControl interface and
must be implemented in the user control code.

Load Collection response type
The Load Collection response type provides the ability to query an IIDO collection and output the return
results to properties, variables, or components on the form. This functions much like the InCollection
validator, except that you can control when the query happens by generating an event for this type of
handler.
When using the Load Collection response type, keep these ideas in mind:
•
•

•
•
•

You must, at least, specify the IDO collection on which you want to perform the query.
The query is automatically capped at 2, because only the first row is accessible or usable. For
cases in which you need a specific number of rows to be returned to the client, you must define a
collection on the form and bind it to a grid or other components.
You can specify that the query is to clear the output targets if no rows are returned.
You can specify that the output targets do not require validation before navigating or saving the
results.
You can specify a filter to be applied on the records being queried.

Method Call response type
Event handlers with a Method Call response type allow the use of IDO methods to handle the event.

Infor Mongoose Application and Form Development | 172

Working with Forms
To define a Method Call response type, you must specify the name of the IDO that contains the method,
the method name, and any parameters required by the method. You can specify literal, property,
variable, and component values as parameters.
Event handlers with a Method Call response type can output values to properties, variables, or
components. To do this, specify the appropriate property, variable, or component name for the method's
output parameters. You must also specify the Output attribute for each output parameter.
Methods indicate failure by returning a return code greater than 4. Return codes 1 through 4 are
reserved for warnings. If a method returns a code of 1 through 4, Windows client displays the error
message with the word "Warning" prefixed. The method should return a code of 0 to indicate success.

Print Preview response type
Event handlers with a Print Preview response type are used to generate a preview of a report rather
than a printout of a report. The response type submits a job to the Task Manager batch processing
facility. Print Preview takes two parameters TASKNAME(name) and TASKPARMS(parameters), where
name is the name of a task defined in the Background Tasks Definition form and parameters are,
typically, the current values of variable-bound components on the form. For more information, see the
online help for Task Manager.

Prompt Event response type
This response type allows developers to prompt users without writing code.
The prompt event handler is asynchronous, so if it appears in a chain of events, that chain is not
suspended while waiting for the user's response. The prompt is displayed after the event chain that
runs the prompt event handler is completed.
You can associate a WinStudio event name with each response option. When a user selects a response,
the event associated with that button is generated in the calling form.
If multiple prompt event handlers are run in the course of a single event chain, only the last prompt is
displayed.

Run Background Task response type
Use event handlers with a Run Background Task response type to submit a job to the Infor TaskMan
batch processing facility. To define a background task response, you must specify the task name and
a comma-delimited list of parameters.

Infor Mongoose Application and Form Development | 173

Working with Forms

Run Script response type
Event handlers with a Run Script response type allow the use of scripts to handle the event. To define
a Run Script event handler, you must specify the name of the script to run and a comma-delimited list
of parameters to pass to the script. You can specify literal, property, variable, and component values
as parameters.
Inside the script, you access parameters by using the GetParameter() function. GetParameter(0)
returns the first parameter value; GetParameter(1) returns the second parameter value; and so on.
Scripts can also access WinStudio objects and data through the scripting API.
To indicate failure, the script returns a nonzero value. To indicate success, the script must return 0.

Send Web Container Message response type
Event handlers with the Send Web Container Message response type are used to allow communication
between the main Windows client application web frame and any child Web frames. Specify the message
type and the JSON message that describe the information to be passed from the main frame to the
child.

Set Values response type
Event handlers with the Set Values response type are used to set values in variables, properties, and/or
components.
You can specify any number of variable-value, property-value, or component-value pairs. Specify the
name of the variable, property, or component as the target and specify an expression as the value.
The value expression may include the standard substitution keywords and may reference the values
of properties, components, and variables.

Timer response type
Use the Timer response type to start and stop a timer attached to a form. To start the timer as soon
as a form launches, use this response type in a StdFormPreDisplay event.
You can use timers to check and refresh collections automatically, or to perform other tasks that must
be attended to automatically on a periodic basis.
This response type has two possible parameters:
•
•

An ACTION (either Start or Stop)
An INTERVAL, representing the amount of time that is to pass between "ticks" of the timer, in
milliseconds.
Note: For forms used in the web client, the minimum valid value is 5000 (milliseconds). Values
less than this are automatically treated as 5000 in the web client.
See Edit Timer Actions dialog box on page 922.

Infor Mongoose Application and Form Development | 174

Working with Forms
You can set the INTERVAL only if the ACTION is Start. To change the interval, you must stop and
restart the timer.
Timers must be used in conjunction with a StdFormTimerTick on page 393 event. Each time the timer
"ticks", it triggers this event.
Note: When the form closes, the timer stops automatically for that form. In the web client, the timer
also stops whenever the form that uses it loses the focus.

Standard Operations
Understanding standard operations on forms
Standard operations are available to users as commands on the Actions menu. Options for standard
operations appear also on the form's shortcut menu and (in some cases) can be run from the toolbar.
Standard operations execute on either a collection or a subcollection. (The Save and Save Current
operations are exceptions. They update changed data in both a collection and in any associated
subcollections). The user sets the focus on a collection or a subcollection before executing a standard
operation.
You can specify which standard operations are available to the user for each collection or subcollection
on a form. Specifying a standard operation displays its associated commands on the Actions menu,
on the form's shortcut menu, and (if applicable) on the toolbar.
This table lists the standard operations that can be enabled or disabled on a form-by-form basis.
Standard Operation

Description

Navigate

The user may navigate to the first, next, previous, and last items in the queried collection. Activates these Actions menu commands: First, Last, Next, Previous.

Refresh

The user may requery data into the form. Activates these Actions menu commands:
Refresh, Refresh Current.

Filter

The user may specify filter criteria for the data queried into the form. This operation
includes both the Filter-in-Place functionality and access to a query form if it is defined.
Activates these Actions > Filter menu commands: By Query, Begin in Place, Execute
in Place, Cancel in Place, Clear in Place, Apply Saved Filter.

New/Copy

The user may create a new item in the collection. Activates these Actions menu
commands: New, Copy.

Save

The user may save changes made to items in the collection. Activates these Actions
menu commands: Save, Save Current, Validate.

Delete

The user may delete items in the collection. Activates this Actions menu command:
Delete.

Infor Mongoose Application and Form Development | 175

Working with Forms

Standard Operation

Description

Notes

The form supports notes functionality. Activates these Actions menu commands:
Notes for All, Notes for Current.

Graph

The user may generate an ad hoc graph of the data in the queried collection. Activates
this Actions menu command: Graph.

Spreadsheet Disabled

Controls whether the user can export the contents of the current collection to Microsoft
Excel. Applies only to collections, not to subcollections. Activates this Actions menu
command: To Excel.

Using parent and child frames in the WinStudio web
client
You can create a web page consisting of iframes:
•
•

A parent Windows client web application frame
A WSChild.aspx child frame

The WSChild.aspx frame can display a Windows client form, or it can be used as an Infor Ming.le
context application. We use the terms parent and child here to describe the relationship between the
Mongoose-based applications. However, both iframes, in the HTML sense, are siblings (children of a
common parent iframe).
Using a URL to Initialize the Child
WSChild.aspx waits to initialize itself until the parent form, which is loaded via standard URL, is logged
into or is loaded with an existing session.
WSChild.aspx can be loaded standalone, or with parameters to load a form by default. For example,
this URL loads an Items form into the child frame with a filter, after the user logs into the web client in
the parent frame:
http://server/WSWebClient/WSChildForm.aspx?Form=Items(FILTER(Item='FA10000')SETVARVALUES(InitialCommand=Refresh))
Communicating with the Child
In the parent frame, you must use Windows client event handlers with a response type of Send Web
Container Message to send JSON messages that update the child frame. For example, the parent can
tell the child to close the existing form and open a new one, filtered on certain data. Or the parent can
refresh with a new filter a currently opened form in the child frame.
The child frame uses the StdWebContainerMessageReceived event to deal with the messages from
the parent frame. You must use the ThisForm.WebPageMessageData API to extract the content of
the message received using this event.

Infor Mongoose Application and Form Development | 176

Working with Components

Working with Components

Types of Components
Component types
Mongoose supports a wide variety of components for the presentation and manipulation of data in
forms. These components are available for selection both in the Design Mode Toolbox and in the
Component property sheet, Type property.
Note: To deselect all component types in the Toolbox, select the Pointer option.
This table names and describes each of the currently supported component types.
Component Type

Description

Barcode

A component that creates a barcode.
See About Barcode components on page 181.

Browser

A component that points to a URL or displays HTML code.
See About Browser components on page 181.

Button
(PushButton)

A component that creates a button image that the user can click to
do something. Typically, it is used to initiate an action such as running
a procedure or launching another form.
See About Button components on page 181.

Calendar

A component that creates a calendar, similar to the Outlook calendar.
See About Calendar components on page 182.

Chart

A component that is used to define a chart.
See About Chart and Gauge components on page 183.

CheckBox

A component that has two states: selected (checked) and cleared
(unchecked). Used for making choices where there are only two
options, such as Yes and No.
See About CheckBox components on page 185.

Infor Mongoose Application and Form Development | 177

Working with Components

Component Type

Description

ComboBox

The "combo box," or "combination box," is a combination of edit box
and drop-down list box components. The combo box allows the user
to either type text directly into the component or to select from a
drop-down list of values. Like the drop-down list box, the combo box
displays the list only when the drop-down arrow button is clicked.
Otherwise, it displays the currently selected item in the edit-box
portion of the control.
See About List, DropList, and ComboBox components on page 201.

DataView

A component that displays queried data in a layout similar to an Excel
spreadsheet.
See About DataView components on page 185.

DateCombo

A special kind of combo box for entering dates. When the drop-down
arrow button is clicked, an interactive calendar is displayed, allowing
the user to select a date. The user can also enter the date in the
field, using the format mm/dd/yyyy.
See About DateCombo components on page 185.

Diagram

A component that allows event handler diagrams to be displayed.
See About Diagram components on page 186.

DropList

A component that allows data to be displayed and selected from a
drop-down list of values. Unlike the list box, the drop-down list displays the list only when the drop-down arrow button is clicked. Otherwise, it displays the currently selected item.
See About List, DropList, and ComboBox components on page 201.

Edit

A component used for the display and input of text by means of
standard keyboards.
See About Edit and MultilineEdit components on page 187.

FlexLayout

A container type of component that allows flexible positioning of the
components within it.
See About FlexLayout components on page 188.

FormPage

A container component used as an individual page of a form. Often
used to implement wizard-style user interfaces.
See About FormPage components on page 193.

Gauge

A component that is used to define a FusionCharts gauge.
See About Chart and Gauge components on page 183.

Grid

A container component that allows data to be displayed and maintained in a tabular format.
See About Grid and GridColumn components on page 195.

Infor Mongoose Application and Form Development | 178

Working with Components

Component Type

Description

GridColumn

A component that creates a column in a grid component.
Note: This component is not available from the Toolbox; it can only
be created using the Edit Contained Components dialog box. It
can be edited in the Edit Contained Components dialog box or,
after selecting it in the Type property field, in the component property
sheet.
See About GridColumn components on page 195.

GroupBox

A labeled box that can be used to frame a group of related components. This is a container component.
See About GroupBox components on page 198.

HyperLinkButton

A specialized button component that allows you to create a "button"
that looks and behaves like standard hyperlinked text.
See About HyperLinkButton components on page 198.

Infocon

A container component used to display tile forms in a variety of
configurations.
See About Infocon components on page 199.

List

A component that allows data to be displayed and selected from a
list of values.
See About List, DropList, and ComboBox components on page 201.

MenuItem

A specialized component that places an entry on the Actions menu.

(ObjMenuItem)

See About MenuItem components on page 202.

MultiLineEdit

Used for the display and input of large blocks of text. The component
wraps the text at word boundaries. Vertical scrolling is supported.
See About Edit and MultilineEdit components on page 187.

Notebook

A container component that allows other components to be grouped
together on tabs. Only the components on the currently selected tab
are visible to the user. Notebooks are useful for grouping components
and managing screen space.
See About Notebook and NotebookTab components on page 203.

NotebookTab

A container component that creates an individual tab of a notebook
component. This component can contain any number of other components and component types.
Note: This component is not available from the Toolbox; it can only
be created using the Edit Contained Components dialog box. It
can be edited in the Edit Contained Components dialog box or,
after selecting it in the Type property field, in the Component property sheet.
See About Notebook and NotebookTab components on page 203.

Infor Mongoose Application and Form Development | 179

Working with Components

Component Type

Description

RadioButton

Radio (option) buttons are implemented in groups with each button
representing a member of a set of mutually exclusive options. The
user can select only one option in the group.
See About Option (RadioButton) Button components on page 205.

SoHo user components

A collection of predefined options that are used to design and build
forms.
See About SoHo user components on page 216.

Static

A component used for the display of read-only text, such as component labels. Can also be used for the display of graphics images.
See About Static components on page 206.

SubForm

A container component that is used to display or load forms inside
a parent form. A form can have any number of such containers.
See About SubForm components on page 206.

ToolbarButton

A specialized component that creates a button intended to appear
on the toolbar.
See About ToolbarButton components on page 207.

Tree

Allows data to be displayed and selected in a tree structure built on
parent-child relationships between properties in collections.
See About Tree components on page 207.

User Control

A component that acts as a container for a .NET user-created control.
See About User Control components on page 209.

Vertical Grid

A container component that formats a collection data in a two-column
grid that displays property names in the left column and the property
values in the right column.
See About VerticalGrid and VerticalGridElement components on
page 212.

Vertical Grid Element

A specialized component that creates a single element for a vertical
grid component. Each element represents a name-value pair for a
single property of a collection record.
Note: This component is not available from the Toolbox; it can only
be created using the Edit Contained Components dialog box. It
can be edited in the Edit Contained Components dialog box or,
after selecting it in the Type property field, in the component property
sheet.
See About VerticalGrid and VerticalGridElement components on
page 212.

Infor Mongoose Application and Form Development | 180

Working with Components

Framework components
About Barcode components
Barcode components allow you to add barcodes to your forms and reports.
A barcode component can display different types of barcodes by changing the Symbology property
of the component. A variety of one-dimensional and two-dimensional barcode symbologies are available.
You can modify the properties of a barcode component in the Miscellaneous > Specific Attributes
section of the Component property sheet.
Note: An error message is displayed instead of the barcode if the input values are outside the
acceptable range.

About Browser components
Browser components are used for the display of URLs or inline HTML code. Browser components
support word-wrap and vertical scrolling.
A browser component's data binding specification can reference any substitution keywords, like IDO
properties or variables, wrapped in either the URL() or HTML() keyword.
Normally, Browser components do not specify captions. If a label is needed, create a static component
beside the Edit or MultiLineEdit component.

About Button components
Button (PushButton) components initiate an action such as running a procedure or launching a form.
To define the action initiated by a button, specify an event handler for the button's Primary event in
the Events property sheet.
Note: To display "\n" in the caption instead of it being interpreted as a new line, set the No Interpret
New Lines property to TRUE.
To display a bitmap or icon on a button, set the Bitmap File Name property in the Components
property sheet.
Windows client provides several specialized forms of buttons, some of which are available from the
Design Mode Toolbox, and some of which are only available from the Type property on the Component
properties sheet:
•
•

CancelPushButton (only from the Type list): A specialized button component that is preset to
cancel the current action and close the form.
DefPushButton (only from the Type list): A specialized button that becomes the default button
for action when the user presses the Enter key. If another button or component has received the

Infor Mongoose Application and Form Development | 181

Working with Components

•
•

focus after a form is opened, then that component is acted upon when Enter is pressed; this key
becomes the default action only if the focus has not been manually moved elsewhere.
Hyperlink Button: A specialized button component that allows you to create a "button" that looks
and behaves like standard hyperlinked text.
Toolbar Button: A specialized component that creates a button intended to appear on the main
toolbar. This is not, strictly speaking, a "push button" like the others.

Calendar components
About Calendar components
Calendar components allow users to select dates from a calendar that can be set to show a day, week,
or month view. Arrows on the component allow the user to move backward or forward in time.
The Calendar component is bound to an IDO collection that is available to the form. This allows IDO
objects from the collection to be rendered on the calendar as activities or events, depending on how
you define them in the Calendar Settings dialog box.
You can set the variables and properties of the component to create an alert when a user selects an
activity for a date on the calendar, for example, when a user selects a vacation day.
In the component definition, you can set up categories and colors for different types of activities. Then
if the user selects or adds an activity in the calendar that matches a defined category name, the activity
is displayed in the calendar with that color.

Adding a Calendar component to a form
1
2
3

With the form open, go into Design Mode.
From the Toolbox, select Calendar.
Perform one of these actions:
• Single-click in the form to draw the calendar at its default size.
• Click in the form where you want one corner of the calendar to be, hold down the mouse button
and drag the cursor to draw the calendar to the size you want.

4

From the Component property sheet, under Data Source, click in the Binding option and click the
ellipses at the right to open the Edit Component Data Binding dialog box.
In the Type field, select Calendar and click Edit .
In the Calendar Settings dialog box, specify the calendar settings.
Click OK.
Save your changes to the form and exit Design Mode.

5
6
7
8

Infor Mongoose Application and Form Development | 182

Working with Components

Chart/Gauge components
About charts and gauges
Forms can display data using either of two different kinds of graphical devices, charts and gauges.
Types of charts include column or bar charts, line charts, pie charts, doughnut charts, scatter charts,
and spark charts, to name a few. These charts can display data from a single source (or series) or
from multiple series. They can also include combination displays, showing both lines and columns, for
example.
Gauges are single or multiple value indicators that are used in dashboards, real-time monitors, and
reports. They are used to display Key Performance Indicators (KPIs), progress indicators, and quantity
indicators.
To display data using these devices, Design Mode offers two form components: a Chart component
and a Gauge component. To use either of these components, you add the component to the form and
then bind the data to it, using a special dialog box. The appearance and content displayed by chart
and gauge components are controlled by the settings made in their respective dialog boxes.
You can also modify charts and gauges using form scripting.

Adding a chart to a form
1
2
3
4
5
6
7

With the form open, go into Design Mode.
From the Toolbox, select Chart , and draw the component where you want it on the form.
From the Component property sheet, select Data Source > Binding and then click the ellipses
(...) button on the right.
In the Edit Component Data Binding dialog box, Type drop-down list, select Chart.
Click Edit.
In the Specify Chart Settings dialog box, on the Data tab, specify at least the Chart Style and
the Chart Type.
On the same tab, in the Chart Data group box, specify:
• The IDO Collection to use as the source for the data
• The property or properties, using the Available Properties list and clicking Add.
For a Single Series chart, specify only a single property. For a Multi-Series or Combination
chart, specify multiple properties, according to what the chart is to display.

8

To save your initialized chart, click OK till you get back to the form.
Note: The chart does not display any data until you perform this step and the next step.

9 Save your changes, close, and then reopen the form.
10 Make other settings as desired on the various tabs of the Specify Chart Settings dialog box.
11 To preview the chart, click Apply and move the Specify Chart Settings dialog box out of way
until you can see your chart.
See Previewing chart types on page 1246.

Infor Mongoose Application and Form Development | 183

Working with Components
12 To apply your changes and save the chart, click OK .

Adding a gauge to a form
1
2
3
4
5
6
7

With the form open, go into Design Mode.
From the toolbox, select Gauge , and draw the component where you want it on the form.
From the Component property sheet, select Data Source > Binding and then click the ellipses
(...) button on the right.
In the Edit Component Data Binding dialog box, Type drop-down list, select Gauge.
Click Edit.
In the Specify Gauge Settings dialog box, on the General tab, specify at least the Gauge Type.
Specify the gauge settings on the General tab, as desired.
See Specifying general settings for gauges on page 1176.
You must specify at least this information:
•

Gauge Type - Select the type of gauge you want to display.
To preview the gauge types, see Gauge samples on page 325.

•

8
9

Gauge Data
Note: In some earlier versions of Mongoose, the Gauge Data group is labeled as Chart Data.
• To base the gauge display on data in an IDO collection, select the Collection option.
• To base the gauge display on data from a Critical Number, select the Critical Number
option.

Optionally, use the other tabs on the Specify Gauge Settings dialog box to set up additional
options for the gauge.
To apply your changes and save the gauge, click OK .

About modifying charts and gauges with script
To create a chart or a gauge on a form, you must use the appropriate form component and then use
the Specify Chart Settings dialog box or the Specify Gauge Settings dialog box to define it. You
can also further modify your chart and gauge components using these dialog boxes.
Note: You cannot create a chart or gauge using form scripting. You must create the chart component
using the Specify Chart Settings dialog box, and the gauge component using the the Specify Gauge
Settings dialog box. Once the component has been initially created and defined, then you can use
form scripting to modify and refine the components.
However, there might be times when you want to include functionality or features on your chart/gauge
that you cannot specify using these dialog boxes. These times might include these types of situations:
•

You might want to structure your chart/gauge so that the chart/gauge display changes whenever
you click a certain button on the form.
This type of functionality is currently not available through the settings dialog boxes.

•

You might want to use chart/gauge properties that are supported in Windows client, but not
represented on either of the settings dialog boxes.

Infor Mongoose Application and Form Development | 184

Working with Components
In these cases, you can add the features or functionality you want by creating form scripts.

About CheckBox components
The CheckBox component is used to display and manipulate Yes/No or True/False types of user
responses.
By default, the value of a selected check box is 1 and the value of a cleared check box is 0. To translate
these values to other values, set the Boolean True and Boolean False properties on the Component
properties sheet.
Note: To display "\n" in the caption instead of it being interpreted as a new line, set the No Interpret
New Lines property to TRUE.

About component templates
You can save a selected component and its child-components as a template. Component templates
are saved and listed within the Toolbox.
Use component templates to quickly create a component and its child-components in Design Mode.

About DataView components
DataView components are used to display results from DataView (or DataView-like) queries within a
form, instead of requiring a separate form to display the results. The results to be displayed can be
based on any of the four types of DataViews: Predefined DataViews, Form DataViews, Critical Number
Drilldowns, or DataSearches.
Like their separate-form counterparts, DataView components can be set up to use filters, input
parameters, and other pre-query mechanisms to perform the query. Also like their separate-form
counterparts, the resulting displays can be sorted, rearranged, and use layouts, calculations, and
summaries.
To set up a DataView component, use the Specify DataView Settings dialog box.

About DateCombo components
The DateCombo box component works like the ComboBox, except that it presents a drop-down calendar
for date selection instead of a drop-down list. Rather than use the drop-down calendar, you can enter
the date in the field using the format mm/dd/yyyy.

Infor Mongoose Application and Form Development | 185

Working with Components

About Diagram components
The Diagram toolset component uses the Nevron Diagram application to create drag-and-drop diagram
tasks and then interact with them via event handlers and scripting APIs. For a complete set of Nevron
Diagram for .NET documentation, see the Nevron web site.
Component properties
When you add a Diagram component to a form, set the following diagram-specific options using the
Data Source > Binding property of the component.
Type
Choose between Manual and Automatic.
•
•

Manual lets you drag and drop shapes onto the diagramming area, and manually edit each shape's
settings.
Automatic uses MGDiagramDesigner.dll to render the diagram according to the specified IDO
collection property, with these options:
• Flowchart Shape List Builder Type specifies the assembly name and type name to implement
a predefined interface, returning "LinkedListFlowchartNodeShape." The interface is available
through MGDiagramDesigner.dll, and the type defined in this field must implement the interface.
The Diagram control displays a list of node shapes for the flowchart on the diagramming area.
• Layer/Vertex Spacing determines the spacing (in pixels) between the layers, and the vertices.

Background style and style sheets
The visual appearance of all diagram elements is controlled with styles. Define style sheets at the
diagramming document level, then assign them to shapes on the diagram to control their appearance.
Your choices include Bridge, Start/End Arrowhead, Shadow, Stroke, and Text.
Design Mode options
The Diagram component creates two button-based toolbars:
•

Design Mode toolbar
Button

Description

Grid

Enable/disable diagramming area grid.

Rulers

Enable/disable horizontal/vertical rulers.

Library Browser

Enable/disable shape library browser.

Ports

Show/hide shape ports.

Arrowheads

Show/hide connector arrowheads.

Shadows

Show/hide shape shadows.

Pan and Zoom

Enable/disable Pan and Zoom control.

Infor Mongoose Application and Form Development | 186

Working with Components

•

Button

Description

View Layout

Diagram viewing layout option:
• Normal
• Fit
• Stretch
• Stretch-to-Width
• Stretch-to-Height

Undo / Redo

Undo/redo previous action(s).

Runtime toolbar
Grid

Enable/disable diagramming area grid.

Rulers

Enable/disable horizontal/vertical rulers.

Library Browser

Enable/disable shape library browser.

Ports

Show/hide shape ports.

Arrowheads

Show/hide connector arrowheads.

Shadows

Show/hide shape shadows.

Pan and Zoom

Enable/disable Pan and Zoom control.

View Layout

Diagram viewing layout option:
• Normal
• Fit
• Stretch
• Stretch-to-Width
• Stretch-to-Height

Undo / Redo

Undo/redo previous action(s).

Right-click menus
The diagram, as a whole, as well as the individual shapes within the diagram, can have right-click
menus assigned to them.
See Adding or changing a component's context (shortcut) menu on page 253.

About Edit and MultilineEdit components
Edit components and MultiLineEdit (multi-line edit) components are used for the display and input of
text (using standard keyboard entry). Typically, the MultiLineEdit component is used to display and
edit large blocks of text. It supports word wrap and vertical scrolling.

Infor Mongoose Application and Form Development | 187

Working with Components
An Edit or MultiLineEdit component can be bound to individual IDO properties or variables. The value
of the property or variable is displayed and edited in the component's edit box.
Normally, Edit and MultiLineEdit components do not specify captions. If a label is needed, create a
static component beside the Edit or MultiLineEdit component.
To use an Edit component as a password field, set the Password property on the Component properties
sheet to convert any entered data into asterisks.
This is an example of an edit component with the caption User Initials in a static component:

This is an example of a MultiLineEdit component:

FlexLayout components
About FlexLayout components
A FlexLayout component is a container component which is used to flexibly house other components.
Each FlexLayout component can be divided into different horizontal or vertical regions, and each region
can contain multiple child components. The overall positioning of those child components is specified
by the FlexLayout region attributes, and their order is specified by the Sequence property.
You can nest FlexLayout components inside regions of other FlexLayout components. You can nest
these components as many levels deep as you need to accomplish your form design.
A FlexLayout component can be customized by using the properties that are described in this topic.
FlexLayout container types
When using multiple regions, the FlexLayout component can group the regions horizontally or vertically.
Horizontal Box

Infor Mongoose Application and Form Development | 188

Working with Components
Vertical Box

Region length
The Length and Units attributes control the individual length of each region of a FlexLayout component.
Length is expressed as a number of characters, equal portions of the total available width (Flex units),
or as a combination of both.
Char units only

Flex units only

Combination of Char and Flex units

About the Orientation, Pack, and Align attributes
The Orientation attribute specifies whether child components are arranged vertically or horizontally
within each region. This attribute is also used to determine if a component should Fill the
The Pack and Align attributes work together to position the child components within the region.
Vertical Orientation
When the FlexLayout region is vertically oriented, use Align to position the child components at the
left, center, right, or to stretch them to fill the entire width of the region. Use Pack to position the child
components at the start (top), center (middle), or end (bottom) of the region.

Infor Mongoose Application and Form Development | 189

Working with Components

Horizontal Orientation
When the FlexLayout region is horizontally oriented, use Align to position the child components at the
top, middle, bottom, or to stretch them to fill the entire height of the region. Use Pack to position the
child components at the start (left), center (middle), or end (right) of the region.

Note: Bottom alignment (when vertically oriented) and Right alignment (when horizontally oriented)
are not supported by the web client.
Theme Border
The Theme Border attribute is used to enable or disable the region's border of a FlexLayout component
on WinStudio and web client.
Note: Enabling this option is applicable only for FlexRegionOnly and FlexLayoutAndRegion
border visibility.
Use the Theme Editor to specify these border attributes:
•
•
•

Border color
Top, left, right, and bottom border visibility
FlexLayout and FlexRegion border visibility

Infor Mongoose Application and Form Development | 190

Working with Components
•
•

If you select None, no border is displayed for both the FlexLayout and FlexRegion.
If you select FlexLayoutOnly, the border is displayed around the FlexLayout only. Any
border for the regions are not displayed even if the Theme Border from the Flex Region
properties is enabled.
• If you select FlexRegionOnly, the border is displayed around the FlexRegions only. Any
border around the FlexLayout are not displayed. Use the Theme Border attribute from the
Flex Region properties to enable or disable the border visibility of a specific region.
• If you select FlexLayoutAndRegion, the border is displayed for both the FlexLayout and
FlexRegion. The border of the region is only displayed if the Theme Border attribute of that
region is enabled.
Note: You can also use the FlexLayout theme classes that are available under the Infor theme.
These theme classes have predefined border settings in the Theme Editor:
• FlexLayoutAndRegionBorder
• FlexLayoutBorder
• FlexRegionBottomBorder
• FlexRegionTopBorder
• FlexRegionLeftBorder
• FlexRegionRightBorder
• FlexRegionBorder
VisibleWhen
The VisibleWhen attribute specifies the conditions under which the component is visible or hidden.
Animation
The Animation attribute is used to add a sliding and/or bouncing effect to a region when it is opened
or closed. Leave the field blank to disable animation, or select from these options:
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

FromRight
FromLeft
FromTop
FromBottom
FromCenter
FromRightWithBounce
FromLeftWithBounce
FromTopWithBounce
FromBottomWithBounce
FromCenterWithBounce

Background image
The Background Image attribute is used to add an image to the background of a region.

Infor Mongoose Application and Form Development | 191

Working with Components
Minimum/Maximum length
When a region size is defined as a number of flex units, use the Min/Max attribute to specify the
maximum and minimum possible size of that flex region.
Region margins
The Default Margin property specifies the margin, measured in WinStudio characters, that defaults
for any child components added to the current FlexLayout region.
The Margin property is used to override, if necessary, the Default Margin property for components
contained in the FlexLayout region.
About the Orientation, Pack, and Align Attributes
The Orientation Adorner is used to select horizontal or vertical box when creating a Flex region.
The Add flex regions is used to add a region on the selected flex layout. The Flex region orientation
is based on the Orientation Adorner selected.
The Remove flex regions is used to remove a region on the selected FlexLayout component.
The Add Flex is used to add a FlexLayout component on a selected region that has no contained
component.

Adding FlexLayout components to a form
To add a FlexLayout component to a form:
1
2
3
4
5
6

7

With the form open in Design Mode (or the Web Designer), select FlexLayout in the Toolbox.
To create the initial size and position of the component, left-click and drag a box on the form.
To open the Edit FlexLayout Attributes dialog box, click the Edit FlexLayout button in the upper
left corner of the component
To cause any regions created to display as columns, specify HorizontalBox In the Type field. To
cause any regions created to display as rows, specify VerticalBox in the Type field.
By default, the FlexLayout component contains one region. To create additional regions, click Add.
For each region, set the properties and attributes that control how the FlexLayout component is to
display.
See the help on FlexLayout properties and attributes on page 1294.
Add the required components to each region of the FlexLayout component by creating the
components inside the region where you want them to display.
Note: If you are using the Orientation option Fill in a Report-type form: To make it easier to select
the FlexLayout component or the contained component, you can right-click the area, select Set
Current Selection from the context menu, and then select the desired component from the
submenu.
Whether or not you are using the Fill option, the selected component--whether it is the FlexLayout
component or a contained component--is highlighted when it is selected.

Infor Mongoose Application and Form Development | 192

Working with Components

Adding a FlexLayout component in a region
To add a FlexLayout component in a region:
1
2
3

With the form open in the Web Designer, select a region you want to add a FlexLayout component.
Using the right-click context menu, select Add Flex.
Select Add Row to add a FlexLayout row, or select Add Column to add a FlexLayout column.
Note: The Add Flex option is available only when the selected region has no contained component.

FormPage components
About FormPage components
Form pages are similar to notebook tabs in that they are containers that allow you to reuse screen
space. Components contained in a form page are visible and active only when the form page is current.
Typically, a series of form pages is used to construct wizard-style interfaces.
You can set up an interface to navigate form pages by creating an event handler with a response type
of Goto Form Page. For instance, you can use this response type to handle a push button's primary
event. The navigation history of form pages are kept on a stack. To return to the previous form page,
generate the standard event StdFormPagePop on page 385.
The Sequence property of a FormPage component must be zero-based for each FormPage component.
When building a multi-page form, you can:
•
•
•
•
•

Add form pages
Select form pages for editing
Edit a selected form page
Delete a selected form page
Add components to a specific form page

Adding a FormPage component
You can add a form page to a form as you would add any component on page 222 to a form.
Alternately, you can follow this procedure:
1
2
3
4
5
6

Open a form and go into Design Mode.
In the Form property sheet, select Form Pages.
Click the associated ellipsis (...) button.
In the Edit Form Pages dialog box, click the New button.
In the Form Component Properties dialog box, specify properties for the form page. In the
Containment section, be sure to specify the Sequence number of the form page.
Click OK.

Infor Mongoose Application and Form Development | 193

Working with Components
7

In the Edit Form Pages dialog box, click OK.

Selecting a form page component
1
2

Open a form that contains a form page component and enter Design Mode.
Perform one of these actions:
• In the Component property sheet, select the name of a form page in the drop-down list of
component names at the head of the sheet.
• In the Object Viewer, click the name of a form page.
• On the Edit menu or the right-click menu, select Component, and then click Set Current
Form Page.

3

Click OK.

Adding components to a FormPage component
1
2
3

With the form open and in Design Mode, make sure the form page you want to add a component
to is selected.
Add components to the FormPage component as you would add components to any other form.
On the Component properties sheet, verify that the Container property value for each new
component is the name of the form page.

Editing a form page component
1
2
3
4
5
6

Open the form that contains the form page component you want to edit and go into Design Mode.
In the Form property sheet, select Form Pages.
Click the associated ellipsis (...) button.
In the Edit Form Pages dialog box, select the name of the form page.
Click the Edit button.
In the Form Component Properties dialog box:
• Use the Properties sheet to set the form page properties.
• Optionally, use the Events sheet to set any events to use.
• Click OK.

7

In the Edit Form Pages dialog box, click OK .

Deleting a form page component
1
2
3

Open the form that contains the form page component you want to delete, and go into Design
Mode.
In the Form property sheet, select Form Pages.
Click the associated ellipsis (...) button.

Infor Mongoose Application and Form Development | 194

Working with Components
4
5
6
7

In the Edit Form Pages dialog box, select the name of the form page to delete.
Click the Delete button.
When prompted to confirm, click OK.
In the Edit Form Pages dialog box, click OK.

Grid and GridColumn Components
About Grid and GridColumn components
Grid components allow you to display and maintain data in a tabular format. The data in a grid is taken
from an IDO collection, which must be bound to the grid.
The grid component itself is a containerfor one or more grid columns. Each column displays data
associated with a single property from the IDO collection.
A grid can be bound to a primary collection, a secondary collection, or a subcollection. A collection
bound to a grid is displayed and maintained in the grid.

Adding a Grid component
1
2
3
4

Open the form you want to add a grid to, and go into Design Mode on page 20.
In the Toolbox, select Grid.
Place and size the grid component where you want it on the form.
In the Binding property field of the Component properties sheet, specify the collection to bind to
the grid:
• Click the ellipsis (...) button associated with the Binding field.
• Use the Edit Component Data Binding dialog box to specify the collection to which the grid
is to be bound.

5

Use the Component properties sheet to specify other properties as desired.
Note: When the Grid component is first created, Windows client automatically creates two
generically named grid columns.

6

Add and configure grid columns as needed to display all the data you want to display in the grid.

About GridColumn components
A grid column is an individual column in a grid component. Grid columns are contained within a grid
component. They can be bound to IDO properties from the collection to which the grid is bound.
Grid columns can act like edit components, combo box components, or check box components. The
default behavior for a grid column is the same as that of an edit component. If you specify a List Source
for the column in the Component properties sheet, it behaves like a combo box. If you set the Check
Box property, it behaves like a check box.

Infor Mongoose Application and Form Development | 195

Working with Components
If the grid contains too many grid columns to display at once, WinStudio automatically supplies horizontal
scroll bars. If you want a particular grid column to be locked in place and displayed in spite of scrolling,
you must set the Locked From Scrolling property in the Component properties sheet.

Adding a GridColumn component
1
2
3
4
5
6
7
8
9

Open the form that contains the grid component to which you want to add a column, and go into
Design Mode on page 20.
Select the Grid component.
In the upper left corner of the grid component, click the Edit grid column(s) button.
In the Edit Contained Components dialog box, click Add (near the bottom of the dialog box).
In the Name field of the Properties tab, assign a name that describes the purpose and type of the
new grid column.
Optionally, specify other properties of the grid column.
Optionally, use the up and down arrow buttons to rearrange the columns.
Click OK.
Back in the grid component, set the column width and other visual aspects of the column as desired.

Editing a grid column component
1
2
3
4
5
6
7

Open the form that contains the grid with the column you want to edit, and go into Design Mode
on page 20.
Select the grid component.
In the upper left corner of the grid component, click the Edit grid column(s) button.
Use the Edit Contained Components dialog box to make the changes you want.
Optionally, use the up and down arrow buttons to rearrange the columns.
Click OK.
Back in the grid component, set the column width and other visual aspects of the column as desired.

Setting up the grid column component visibility and order
To set up the grid column component visibility and order:
1
2
3

Open the form that contains the grid with the column you want to show, hide, or reorder.
In the upper left corner of the grid component, right-click and select Edit Grid Columns....
In the Edit Grid Column Visibility and Order dialog box, perform any of these actions:
• To show a grid column, select the Visible check box.
• To hide a grid column, clear the Visible check box.
• To reorder grid columns, click the Up or Down buttons.
• To display all grid columns, click the Show All button.
• To hide all grid columns, click the Hide All button.

Infor Mongoose Application and Form Development | 196

Working with Components
4

Click OK.

Note: In the Windows client, you can perform this procedure together with the GridSetColumnLayout
method to further edit the layout, as needed. In the web client, the Edit Grid Columns... option is
removed when the GridSetColumnLayout method is used.

Enabling pagination on a Grid component
Note: This feature is available in the web client only.
1 Launch the Web Designer.
2 Open the form with the Grid component that you want to modify.
3 From the Component property sheet, select Miscellaneous > Specific Attributes.
4 In the Client Grid Pagination field, select one of these options:
• None: The default value.
• Full Featured: This option displays these page controls at the bottom of the grid:
• First Page button
• Previous Page button
• Page Selector field, for example, 1 of 19
• Next Page button
• Last Page button
• Get More Rows button
• Record Number field, for example, Displaying 1 – 19 of 200
• Navigation Buttons: This option displays only the navigation buttons at the bottom of the grid.
This option includes the First Page, the Previous Page, the Next Page, and the Last Page
buttons.
•
•
•
•
•
•
•
•
•
5
6

Nav Plus Get More Rows: This option displays only the navigation buttons and the Get More
Rows button at the bottom of the grid.
Nav Plus Page Selector: This option displays only the navigation buttons and the Page
Selector field at the bottom of the grid.
Nav Plus Page Set Plus Get More: This option displays only the navigation buttons, the Page
Selector field, and the Get More Rows button at the bottom of the grid.
Nav Plus Record Number: This option displays only the navigation buttons, the Page Selector
field, and the Record Number field at the bottom of the grid.
Full Featured On Top: This option displays all the page controls on top of the grid.
Navigation Buttons On Top: This option displays only the navigation buttons only on top of
the grid.
Nav Plus Get More Rows On Top: This option displays only the navigation buttons and the
Get More Rows button on top of the grid.
Nav Plus Page Selector On Top: This option displays only the navigation buttons and the
Page Selector field on top of the grid.
Nav Plus Page Set Plus Get More On Top: This option displays only the navigation buttons,
the Page Selector field, and the Get More Rows button on top of the grid.

Save your changes to the form.
Close the Web Designer.

Infor Mongoose Application and Form Development | 197

Working with Components
7

In the main web client window, open the form and verify that your changes were made.
Note: Unload the global form objects to see the changes.

GroupBox components
About GroupBox components
A group box is a labeled container-type component that frames a group of components. Depending
on the theme that is being used, users might or might not see the borders around a group box, and
there might be other visual differences as well.
You can enable or disable all components in a group boxes by setting the Read-Only/Disable property
of the group box.
You can change the appearance of the group box header with the Group Header property.
You can use group boxes to format multiple collections.

Binding a group box to a collection
Forms with more than one collection (such as a primary collection and a subcollection) often use a
group box component to outline all the components associated with each collection.
To bind a group box component to a collection:
1
2
3
4
5
6
7

With a form open and in Design Mode, create or select a group box component.
On the Component property sheet, select Data Source >Binding, and then click the ellipsis (...)
button.
In the Type box, select CollectionID Group Box.
Click Edit.
In the Collection box, specify the collection to be bound to the group box.
Optionally, select the Disable Raised 3D Display check box.
Click OK.

About HyperLinkButton components
HyperLinkButton components format the Caption text as hypertext links (that is, blue font face and
underlined) and typically are used to create a button that looks like hyperlinked text.
To define the action and target destination for a hyperlink button, specify an event handler for the
button's Primary event in the Events property sheet.

Infor Mongoose Application and Form Development | 198

Working with Components

About Infocon Components
The infocon component is a specialized container-type component used to display tile forms in a flexible
"pullout" format. This component can be set to:
•
•
•
•
•
•
•

Expand and collapse, to show many tiles or a single tile
When collapsed, display either a single value from the selected record or the entire tile
Expand the display in any of four directions, so as to view multiple tiles
Limit the number of tiles that display when expanded
Provide the option to scroll and see more tiles, if there are more tiles than the viewable space can
display at once
While in the expanded mode, allow users to select a single tile by clicking it, which then collapses
the display again
Always display in the expanded mode

Infocon structure and functionality
This diagram illustrates the basic structure and functionality of the infocon component:

1

Selected content area: This area is where the content for the selected record displays.
What actually displays here depends on the Display Selected Item As property for the infocon
component. If that property is set to Text, then the value that displays here is taken from the first
display field in the selected tile. If the Display Selected Item As property is set to Tile, then this
area displays the entire selected tile.
Note that the tile is not resized to fit the available space. If it is too large, it is simply truncated. So,
you must take care when defining the width and height for the infocon component, to make sure
that the tile can display if the Tile option is selected. This means that the infocon must be sized
larger than the tile form it is being used to display.

2

Infocon header: This is the area that displays at all times. This area includes both the Selected
content area and the Drop-down arrow.
When the infocon is displaying in the collapsed mode, the header is all that displays. When the
infocon is in the expanded mode, the Expanded display area also displays.

3

Note: If the Always Expand property is set to True, the entire header is hidden.
Drop-down arrow: This button toggles the infocon back and forth from the collapsed mode to the
expanded mode.
This button functions much like the drop-down arrows used in ComboBox and List components.

4

Expanded display area: This area displays only when the infocon is in the expanded mode.
Note: If the Always Expand property is set to True, then this area always displays.

Infor Mongoose Application and Form Development | 199

Working with Components
This area can extend beyond the borders of the Infocon header area in any direction, depending
on how the Tile Direction property is set for the infocon. In all cases, this display area "floats"
above all other form content. The size of this area is specified by settings in the Tile Direction,
List Columns, and List Rows properties for the infocon.
When a tile is selected, this area closes, and the selected tile is displayed in the header.
Designing infocon behavior
When designing forms to work with infocon components, it is helpful to keep in mind that the Tile
Direction, List Columns, and List Rows properties interact to define the infocon's appearance and
behavior.
When tile direction is right or left
These are the general rules for infocon behavior when the Tile Direction property is set to Right or
Left. Note that these rules might not apply, depending on the size and placement of the infocon
component within the form.
•
•

The height of the expanded display area (see previous section) is determined by the List Rows
property. The number specified there determines the number of whole tiles that display top-to-bottom.
If all tiles returned do not fit within the available expanded display area, a horizontal scrollbar
provides access to the rest of the tiles.

When tile direction is down or up
These are the general rules for infocon behavior when the Tile Direction property is set to Down or
Up. Note that these rules might not apply, depending on the size and placement of the infocon component
within the form.
•

•
•

The width of the expanded display area (see previous section) is determined by the List Columns
property. The number specified there determines the number of whole tiles that are displayed
side-by-side. If necessary, a horizontal scrollbar is used.
If all tiles returned do not fit within the available Expanded display area, a vertical scrollbar provides
access to the rest of the tiles.
If horizontal expansion is required, the Expanded display area always expands to the right.

Miscellaneous other comments on infocon behavior
These are some other notes and comments about infocon behaviors:
•

•

Tile images can be loaded using "Just In Time" processing. For the smart client, this depends on
the tile definition. For the web client, the images are always loaded after the tiles are initially retrieved
and rendered. The images then appear in the tiles as they are loaded.
Previous sections have noted that the general rules might not apply, depending on the size and
placement of the infocon component within the form.
For example, in cases where the Tile Direction property has been set to Down, if the infocon
component is located near the bottom of the form, it actually displays the Expanded display area
as though the Tile Direction is set to Up.

Infor Mongoose Application and Form Development | 200

Working with Components
Typically, the underlying logic first tries to display the popup panel with the requested direction and
size (the List Columns or List Rows properties). If that fails, it tries the opposite direction, still
with the requested size. If that fails, scrollbars are introduced and the size is reduced.
In some cases, the expanded display area can even overlap the header (see the section on infocon
structure and functionality).
This is seen most often in the web client, because the limitations of the browser window are more
stringent.
•

In the web client only, the tile forms themselves can have buttons and hyperlink button components.
These components trigger their events if the named event handler is defined on the host form. The
infocon processing logic guarantees that the tile that contains the clicked button is selected before
the event is triggered.

About drag and drop events with infocons
You can use Drag From and Drop On form events as you would in a list box component.

About List, DropList, and ComboBox components
List (list boxes), DropList (drop-down lists), and ComboBox (combination box) components all allow
data to be selected from a list of values.
The List always displays a scrollable list:

The DropList (also called a drop-down list) always displays its current value:

It displays its list only when the user clicks the down arrow:

A ComboBox (combination box) is like a DropList except that the user can enter data directly into its
edit portion and also select data from the drop-down list.

Infor Mongoose Application and Form Development | 201

Working with Components
The current value of a List, DropList, or ComboBox is a single value. Typically, this is the value that is
associated with the component's data binding. The current value is determined by selecting an item
from the component's list. In the case of the ComboBox, the current value may also be determined by
typing data directly into the component's edit box.
Each List, DropList, and ComboBox has a list of items from which the user can select the component's
value. The component's list can contain multiple columns, but only one column is used to provide the
value of the component. The other columns are available to display additional information that is useful
in helping the user choose an item from the list. An example of this is a list that shows both codes and
descriptions. The value of the component is the code. The description is provided as additional
information to help the user determine which code to select.
You must set the List Source property in the Component properties sheet to specify what items show
in a component's list.
Once you specify a list source, you can use the Value Is List Index property to set the component's
value to the zero-based index of the selected item. If the first item is selected, the component's value
is set to 0; if the second item is selected, the component's value is set to 1; and so on.
You can set the Sort property to sort the list. For List components, you can set the Multiple Selection
property to allow users to select more than one item from the list.

Adding MenuItem components to the Actions menu
You can add commands to a form's Actions menu by creating MenuItem components.
Note: In previous versions, these items were on the Objects menu. The Objects menu no longer
exists.
To add a menu item to the Actions menu:
1
2
3
4

Open a form and go into Design Mode.
In the Toolbox, select the MenuItem component.
Click the form.
In the Form property sheet, set at least the following properties:
• Caption - The caption specifies text displayed on the Actions menu for the command.
• Binding - To enable the menu item only when a particular collection is selected, specify the
collection bound to the component.
• Primary Event - Select the primary event to generate when the menu command is selected.

5

Regenerate the form.
See Regenerating a form on page 143

Infor Mongoose Application and Form Development | 202

Working with Components

Notebook and NotebookTab components
About Notebook and NotebookTab components
Notebook and NotebookTab components allow you to reuse screen space. A notebook is a component
that contains one or more tabs. Each tab can contain other components such as edit boxes, grids,
buttons, and so on. The components contained in a tab are visible only when the tab is selected.
If a notebook contains too many tabs to fit horizontally, WinStudio automatically supplies horizontal
scroll bars. If you prefer to stack notebook tabs instead of using scroll bars, you can set the No Scroll
property.
For most forms, you can:
•
•
•
•

Add a notebook
Add tabs to a notebook
Configure existing notebook tabs
Delete tabs from a notebook

Adding a Notebook component
1
2
3
4

Open the form to which you want to add a notebook, and go into Design Mode.
In the Toolbox, select the N otebook component and place the component on the form where you
want it to display.
Use the Component properties sheet to specify properties of the notebook as desired.
Add and configure one or more notebook tabs as needed.

Note: When a Notebook component is created, it automatically contains two generic NotebookTab
components. You should rename and configure these before creating additional notebook tabs.

Adding a NotebookTab component
1
2
3

Open the form that contains the Notebook component that you want to add notebook tabs to.
Go into Design Mode.
Select the Notebook component.
Note: If this notebook component is new, then two notebook tabs are automatically available. You
must rename and configure these notebook tabs before creating additional tabs.

4
5
6

Click the Edit notebook tab(s) button.
In the Edit Contained Components dialog box, click Add (near the bottom of the dialog box).
Configure the new tab with the components and data binding as needed.
At the least, we recommend that you rename any new tabs to reflect their intended purpose. For
example, if you are creating or adding a tab to contain the fields related to a customer's identification
information, you can specify customerIDtab in the Properties > Name field.

Infor Mongoose Application and Form Development | 203

Working with Components
Note: You can hide notebook tabs in the web client display. You can use the Properties > Hidden
field in the Edit Contained Components dialog box or use a form script.

Configuring a notebook tab
To configure an existing notebook tab, you can take one of these actions:
•
•

Double click the icon in the upper left corner of the notebook component and use the Edit Contained
Components dialog box.
From the Component property sheet for the form, select the Components tab and then in the
drop-down list, select the notebook tab. Then use the notebook tab's property sheet to configure
the tab.

Deleting a notebook tab
1
2
3
4

With the form that contains the notebook component open and in Design Mode, select the notebook
component
In the upper left corner of the notebook component, click the Edit notebook tab(s) button.
In the Edit Contained Components dialog box, click the Delete ( X ) button (near the bottom of
the dialog box).
When prompted, confirm the deletion.

Hiding a notebook tab
You can hide a notebook tab by setting the Hidden property to True and toggle it visible or hidden by
creating and using a form script.
Note: The notebook tab is hidden on the web client display only. These methods are not applicable to
the Windows client (smart) client display.
To toggle the notebook tab visibility, you can create a form script similar to this:
{
public class MJS_HiddenNotebookTab : FormScript
{
public void toggleTabVis() {
var nbTab = ThisForm.Components[“notebookTab”];
string formvar = ThisForm.Components(“tabVisibility”).Value;
if (formvar == “true”) {
nbTab.Visible = true;
} else {
nbTab.Visible = false;
}
}
}
}
Note: This script is written in C#.
Infor Mongoose Application and Form Development | 204

Working with Components
If you copy this script, you must create a tabVisibility variable and a button component with a
toggleTabVis()parameter to match the form script.
When you launch the form in the web client, the notebook tab should be hidden. To show this tab, you
must do whatever is required to trigger the form script.

Hiding notebook tab headers
You can hide the notebook tab headers by setting the Hide Tabs property to True.
Note: The tab headers are hidden on the web client display only. This method is not applicable to the
WinStudio (smart) client display.
The notebook component becomes similar to a form page that contains a button, which allows you to
view the next tab.
When the tab headers are hidden, you can dynamically change the tabs. You can perform tab switching
by using a form script. You can create a form script similar to this:
{
public class MyForm : FormScript
{
public void SetCurTab( string name )
{
ThisForm.Components["notebook1"].NotebookCurTab = name;
}
}
}
Note: This script is written in C#.

About Option (RadioButton) Button components
Groups of Option buttons (in the user interface called RadioButtons) are used to select one option from
a set of mutually exclusive options. Each button represents one option. When an option is selected,
any previously selected button is automatically cleared.
Each member of a group of radio buttons should have the same data binding. You can specify the
value represented by a selected button by setting the Radio Button Selected Value property for the
component. The value you specify is the value that is assigned to the data binding when the user
selects the option button.
Note: To display "\n" in the caption instead of it being interpreted as a new line, set the No Interpret
New Lines property to TRUE.
When tabbing through a form, pressing TAB moves to the first radio button in a group. Within the group,
you can press the UP ARROW key and the DOWN ARROW key to move between radio buttons.
Pressing TAB within the group moves to the next component following the group of radio buttons.

Infor Mongoose Application and Form Development | 205

Working with Components
To support this standard Windows behavior, the Group property of the first member (in tab sequence)
of a group of option buttons should be set to True.

About Static components
Static components are used to present read-only text and graphics on the form.
•

•
•
•
•

To specify text to be displayed in the component, use the Caption property (and optionally the No
Colon property).
Note: To display "\n" instead of it being interpreted as a new line, set the No Interpret New Lines
property to TRUE.
To display the component as a black, rectangular outline (with no text or graphics displayed), use
the Frame property.
To display the component as a solid black rectangle (with no text or graphics displayed), use the
Solid property.
To display an image in the component, use the Bitmap File Name property.
You can also display the current, read-only value of an IDO property in a static component. To do
so, bind the static component to the IDO property by using the Property data binding type.

About SubForm components
SubForm components are container components that allow you to display other forms inside a parent
form.
A parent form can have any number of SubForm containers to load multiple forms. A single container
can be used to load or display different forms one at a time, using the Run Form or Run Form as Linked
Child event type.
By using these specific attributes, you can control how a SubForm component is displayed:
Property

Description

Default SubForm Spec

This property specifies any form to be loaded by default.

Title Bar

This property hides or displays the form header.

Disable Scrollbars

This property hides or displays the scroll bars if the form is larger
than the SubForm component.
When you disable scroll bars on a touch device, the parent form
can be scrolled by dragging on the SubForm.

Modify the SubForm component attributes in the Miscellaneous > Specific Attributes section of the
Component property sheet.

Infor Mongoose Application and Form Development | 206

Working with Components

About ToolbarButton components
Use the ToolbarButton component to create a custom button that appears on the main toolbar. Typically,
this component is most useful on a mobile form.
When created, this toolbar button appears to the right of any other existing buttons displayed on the
toolbar.
To create a toolbar button, select this component type from the Toolbox and specify the desired
properties. You can change the opacity of toolbar buttons for enabled, disabled, and hover states.
The action initiated by a custom toolbar button is determined by the Primary Event setting on the
Events property sheet. The button displays and is active only for the form with which it is associated.
When creating the icon to use for the button, you have two options:
•
•

You can use a simple image. This image must be no more than 16 pixels by 16 pixels and must
be formatted as .GIF, .JPG, or .PNG.
You can use a sprite image, so that when a user moves the pointer over the icon, the appearance
changes. For the procedure to use a sprite image, see Creating Sprite Images for Toolbar Buttons
on page 148.

Tree components
About Tree components
In a Tree component, IDO collections populate levels in a graphic tree. Collections from different IDOs
can be used at different levels, linked in parent-child relationships, or a single IDO can be used
recursively to populate the levels. Dynamic and fixed filtering can be applied at each level.

Nodes can display one or more properties. Nodes can also contain hidden properties that serve in
linkages and that are accessible through the API.
Several methods on the component object on page 408 apply to Tree components. The Text property
of the component object returns the value of the first property in the current node. The WinStudio
keyword COMPONENTS (or C) also returns the value of the first property in the current node.
A tree's primary event is generated when the user double-clicks a node. The data change event is
generated when the user selects a node.

Infor Mongoose Application and Form Development | 207

Working with Components

Adding a Tree component (WinStudio Design Mode)
1
2
3
4
5
6
7
8

Open a form and go into Design Mode.
In the Toolbox, select the Tree component, and then draw the component on the form.
Optionally, specify a text label for the root level of the tree using the Caption property in the
Component property sheet.
Optionally, to specify an icon other than the default folder icon for the root level, set the Bitmap
File Name property in the Component property sheet.
In the Component property sheet, expand the Data Source section, select Binding, and then
click the ellipsis (...) button.
In the Type field of the Edit Component Data Binding dialog box, select Tree.
Click Edit.
In the Tree Control Data Binding dialog box, at a minimum, specify values for these fields:
• Top-Level IDO
• Properties
• Props To Display (Properties to display)
• Order By - Use one of the values from Props To Display.
Other fields are optional.

9

Optionally, create sublevels, using the New button in the Specified Levels group box and the
associated Tree Binding for Sublevel dialog box.
10 Save the form definition.

Addressing specific nodes in Tree script APIs
There is a difference in the way the Windows smart client and the web client operate on nodes addressed
from Tree scripting APIs.
See About the FormComponent object on page 270.
In a Windows smart client (WinStudio)
In WinStudio, the tree component implements an invisible "dummy" child node that is a place holder
for a "real" child node. After a parent node is first expanded, this "dummy" child node is deleted and
replaced by the "real" child node(s). When created and loaded, these "real" child node(s) each reference
a "dummy" child node and so on as each node is successively expanded. If no "real" child node exists,
then this dummy node is simply deleted when the parent is expanded.
When a "dummy" child node is addressed from a script API, WinStudio addresses the specified operation
on the parent of that dummy node. In the following example, node index series "0" addresses level 1
node 1. (For more information about addressing specific nodes, see Notes on the Tree Node ID (GUID)
and Index Series on page 1306. )

ThisForm.Components("tree_comp_name").ExpandNodeAt("0")

Infor Mongoose Application and Form Development | 208

Working with Components
For example, if the above script method is executed against a tree that has not yet been expanded,
the parent node (root node in this case) is expanded. If node 1 of level 1 has already been loaded
(expanded from root), then the above script method expands that node. Since the "dummy" child node
can only exist on parent nodes that exist and have been loaded, a script method addressing any node
in WinStudio at a level and index beyond where a "dummy" child would exist will simply return without
performing any action.
In a web client
In the Web Client, the tree component does not implement a "dummy" child node. Therefore, addressing
any node that has not yet been loaded via expansion of the parent simply returns without performing
any action.
In both the Windows smart client and the web client
Methods executed against nodes that have been "loaded" operate the same in both WinStudio and
the Web Client. When writing form script code that is expected to work the same against tree components
in both WinStudio and a Web Client, structure the code so that unexpanded child nodes ("dummy"
child nodes in WinStudio) are never addressed.

User control components
About User Control components
A User Control component is a container for a Web User Control that might or might not include a .NET
assembly.
User Controls are used to add custom user interface elements to forms. Mongoose supports several
implementation options for developing user controls:
•

To create a User Control that functions in both the Windows (smart) client and the web client,
create a Web User Control. This type of User Control is essentially an HTML page that is designed
to function as part of the user interface. In this case, the User Control component functions as a
container for the Web User Control, which is an HTML page with its associated resources.
Note: This is the only option we fully support at this time. The other options mentioned here are
still supported for legacy user controls, but we do not encourage them for ongoing development.
This option is created by selecting the Use Web Control option in the Windows section of the
Edit User Control Reference dialog box and supplying a Control URL for the control
implementation in the Web section.

•

•

(Deprecated) If the target platform is the Windows client only, you can create a Windows user
control based on the .NET System.Windows.Forms.UserControl class. This component does not
render or function if the form is opened in the web client.
(Deprecated) If the target platform is the web client only, you can create a web-based user control
that works only in the web client. This is done by:
• Clearing all fields and options in the Windows section of the Edit Web User Control Reference
dialog box.

Infor Mongoose Application and Form Development | 209

Working with Components
•

Specifying a Control URL in the Web section of the Edit Web User Control Reference dialog
box; OR specifying a .NET assembly and an absolute or relative URL in that same section.

This component does not render or function if the form is opened in the Windows client.
•

(Deprecated) Finally, you can create both a Windows user control assembly and a web user control,
which causes the framework to render one or the other, depending on the client. In this case, you
must provide both a Windows Assembly Name and a Control URL. This use case is rare.

Creating a User Control component
A User Control component is a container for a Web User Control that might or might not include a .NET
assembly. User Controls are used to add custom user interface elements to forms.
This topic presents the recommended basic process to create a user control. The specifics will depend
on the user control you are creating. There are other ways to create user controls that are not covered
by this topic.
Note: This generalized process is for a user control that is functional in both the Windows (smart) client
and the web client.
1

Create an HTML web page to host and display the user control.
You can use whatever HTML-related assets you want in the file. These assets can include JavaScript
files, image files, CSS files, DLL assemblies, and so on.
For ease in importing them into Mongoose later, it is a good idea to organize all the assets you
want to import into a single folder/directory, if you can.
Note: It is possible to reference scripts, style sheets, images, or just about any asset stored on
another server. For example, to include the jQuery JavaScript library without importing it into
Mongoose, you could use a URL reference such as this in your user control web page:
https://code.jquery.com/jquery-3.1.1.min.js.

2

In Mongoose, import your HTML page and associated files/resources, using the Edit Web User
Control Assets dialog box.
See Importing Web User Control Assets on page 211.

3

Open or create the form where you want to create your user control component, and go into Design
Mode.
In the Toolbox, select User Control and draw the component on the form where you want it to
appear.
In the Web section of the Edit User Control References dialog box, from the Control URL
drop-down list, select the HTML file that hosts your user control.
Verify that the Use Web Control and Web User Control Asset options are both selected.
Click OK.
Do whatever else that might be required to integrate the user control with the data from the form.
This might include data binding, form scripting, or whatever else is necessary to push and pull data
between the form and the user control.

4
5
6
7
8

For calls from the form to the user control, you can use the PostUserControlMessage scripting API
and/or a form event handler with the response type Invoke User Control.

Infor Mongoose Application and Form Development | 210

Working with Components
For calls from the user control back to the form, you can use JavaScript API methods such as
WSForm.getCompValue, WSForm.setCompValue, WSForm.getVarValue, WSForm.setVarValue,
WSForm.invoke, WSForm.generate, WSForm.subscribe, WSForm.UserControl.registerListener,
MG.onReady, and MG.generateServerEvent.
9 Save the form, close it, and reopen it.
10 Test the user control for correct operation.

Managing Web User Control Assets
Web User Control Assets are files and other resources used to create a Web User Control. Web User
Controls are specialized user controls that you can create to perform a wide variety of user interface
functions, both in the Windows (smart) client and in the web client.
Web User Control Assets are managed using the Edit Web User Control Assets dialog box, which
is accessible from the Edit menu in Design Mode. In this dialog box, you can perform these tasks:
•

Import assets used to implement Web User Controls.
See Importing Web User Control Assets on page 211.

•

Create HTML pages and other resources that can be used to host and display Web User Controls.
The New option launches a built-in HTML editor, the Edit Browser File dialog box, with the basic
HTML framework already created. Any text resource (HTML, JavaScript, CSS, XML, etc.) can be
created or edited using this built-in editor. In addition, if the content type is an image type, this
dialog box can be used to import or export those images.

•

Edit HTML pages and other resources that can be used to host and display Web User Controls.
The Edit option launches the Edit Browser File dialog box, already populated with the data for
the selected asset.

•
•

Delete a selected asset.
Copy a selected asset, which you can then modify for your own purposes.

Importing Web User Control Assets
Web User Control Assets are files and other resources used to create a Web User Control. Web User
Controls are specialized user controls that you can create to perform a wide variety of user interface
functions, both in the Windows (smart) client and in the web client.
