---
name: syteline-validation
description: "Component validation, events, context menus, and drag-and-drop operations between components"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

StdCurCompClipboardPasteAppend event
Occurs when the user selects Paste Rows Append from the Edit menu.
The default implementation pastes records (rows) from the system clipboard, inserting them just below
the currently selected record.
In a form without custom event handlers, the default implementation fires the standard event
StdCurCompClipboardPasteAppend.
Your application can call the default implementation by generating the event.
Remarks
Before your application can call the default implementation, it must set the focus to the desired
component.

StdCurCompClipboardPasteAppendComplete event
Occurs when the StdCurCompClipboardPasteAppend event completes.
This event has no default implementation.

StdCurCompClipboardPasteOverwrite event
Occurs when the user selects Paste Rows Overwrite from the Edit menu.
The default implementation pastes records (rows) from the system clipboard into the collection,
overwriting existing records starting with the currently selected record.
In a form without custom event handlers, the default implementation fires the standard event
StdCurCompClipboardPasteOverwrite.
Your application can call the default implementation by generating the event.

Infor Mongoose Application and Form Development | 362

Reference
Remarks
Before your application can call the default implementation, it must set the focus to the desired
component.

StdCurCompClipboardPasteOverwriteComplete event
Occurs when the StdCurCompClipboardPasteOverwrite event completes.
This event has no default implementation.

StdCurCompCriticalDisplaySettings event
Occurs when the user right-clicks a critical number indicator and then selects the Display Settings
option.
The default implementation opens the Critical Number Display Settings form.

StdCurCompCriticalRefresh event
Occurs when the user right-clicks a critical number indicator and then selects the Refresh option.
The default implementation of this event cause the system to recalculate the critical number and to
display the new results.

StdCurCompCriticalSetup event
Occurs when the user right clicks a critical number indicator and then selects the Setup option.
The default implementation opens the Critical Numbers Setup form.

StdCurCompCriticalSnapshots event
Occurs when a user right-clicks on a critical number indicator and then selects the Snapshots option.
The default implementation opens the Critical Number Snapshots form.

StdCurCompDetails event
Occurs when the user clicks Details for Current Field on the Edit menu or double clicks a gauge
component bound to a Critical Number.

Infor Mongoose Application and Form Development | 363

Reference
The default implementation:
•
•
•

Opens the form specified in the component property sheet to use when the user queries for details
on values for the current component
Builds filter criteria to use when querying for details on the current component
Performs a Refresh operation after the query

Remarks
The default implementation operates on the current component, and your application must set the
focus to the desired component before it calls the default implementation.

StdCurCompEditFlexLayout event
This event occurs before the Edit Contained Component Visibility and Order (FlexLayout) dialog
box opens.
The default implementation opens the Edit Contained Component Visibility and Order (FlexLayout)
dialog box for the current form FlexLayout component.
This event is defined in Mongoose runtime. Your application can call the default implementation by
using this event through a form event handler or form script.
Remarks
The contained components of FlexLayout are grouped in regions. You can update its sequence and
visibility attributes.
Similar to grid components, you can create a right-click menu object and assign it to a FlexLayout
component.

StdCurCompEditGridColumns event
Occurs before the Edit Grid Column Visibility and Order dialog box opens.
The default implementation opens the Edit Grid Column Visibility and Order dialog box for the current
form grid component.
Your application can call the default implementation by generating the event.
Remarks
The StdGrid shortcut menu includes the Edit Grid Columns command item that generates this event.
You can assign the event to a command item in another shortcut menu for a form grid component, as
needed.

Infor Mongoose Application and Form Development | 364

Reference

StdCurCompFind event
Occurs when the user clicks Find Value for Current Field on the Edit menu.
The default implementation performs a find for the current component based on the Find-from
specification set in the Form Component Properties dialog box. Windows client launches the specified
form (usually a query form), disables the current form until the user exits that form, and, if the user
selects OK, places the value of the specified property in the current component.
In a form without custom event handlers, the default implementation fires the following sequence of
standard events:
•
•
•
•
•
•
•
•
•

StdCurCompFind
StdFormPredisplay
StdObjectNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNewCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormNewCompleted

Your application can call the default implementation by generating the event.
Remarks
In the sequence of events above, all events after StdCurCompFind occur in the maintenance child
form.
Before your application can call the default implementation, it must set the focus to the desired
component.
Generation of most events is disabled in Filter-in-Place mode. The StdCurCompFind event is an
exception and can be generated in Filter-in-Place mode to call its handler. When this event is generated,
the restriction on generating events in Filter-in-Place mode is lifted and an event handler can generate
any other event and allow its handlers to execute.

StdCurCompGraphProperties event
Occurs before the Graph Control dialog box opens.
The default implementation opens the Graph Control dialog box for the current graph component.
Your application can call the default implementation by generating the event.
Remarks
You can assign the event to a command item in a shortcut menu for a graph component.

Infor Mongoose Application and Form Development | 365

Reference

StdCurCompSearch event
Occurs when the user right-clicks a component and then selects the DataSearch option.
The default implementation opens a DataSearch results form, filtered for exact matches of the value
of the component in which the user right-clicked.
Remarks
Once the search results are displayed in the DataSearch form, the user can then drill down or filter
the data further.

StdCurCompShowMenu event
Occurs when a user clicks or taps a form component for which this event has been assigned as the
Primary Event.
This event causes the component's right-click (context) menu to display when the user left-clicks or
taps a component such as, for example, a Button or HyperLinkButton.
Remarks
Especially for mobile and touchscreen devices, right-clicking is often not an ideal option for the user
to display a context menu. This event provides an alternative way to display the context (right-click)
menu associated with a component when the user left-clicks or taps it.
To use this event, you must assign it as the Primary Event for a component and then associate the
desired context menu with it.

StdCurCompSort event
Occurs when the user double-clicks the grid column header.
Remarks
The users can attach event handlers to this event and use form scripting to customize and override
the client-side sorting of any collection. The default sorting is bypassed if any of the event handlers
have non-zero return code.

StdFormApplySavedFilter event
This event occurs when the user selects Filter from the Actions menu, and then clicks Apply Saved
Filter. After this event is generated, the Select Filter (for object UserNames) dialog box is displayed,
to allow you to choose a saved filter and apply it to your form.

Infor Mongoose Application and Form Development | 366

Reference

StdFormCalledFormReturned event
Occurs after the user closes a modal child form with OK rather than Cancel and before the form closes.
In the default implementation, if the child form was invoked for a Find or Add operation, Windows client
updates the current component on the parent form with the selected value on the child form. If the child
form was called to retrieve a filter specification, as in the Filter by Query operation, then Windows client
sets the filter specification of the parent form's current collection with the child form's filter specification
and refreshes the parent form's current collection.
In a typical query form, for example, Windows client fires the following events when the user clicks
Close and Save Changes (OK) on the Form menu:
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

StdFormExitOk
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdFormPutBoundValues
StdFormCalledFormReturned
StdFormClose
StdFormRefresh
StdFormGetBoundValues
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The event fires while the child form is still active and the values associated with it are available to other
forms.

StdFormClose event
Occurs before a form closes. For example, the event occurs after the user clicks Close and Cancel
Changes or Close and Save Changes (OK) on the Form menu.
The event is not associated with a default handler and is for notification only.
Remarks
To close a form programmatically, you can generate the StdFormExitOk on page 373 or
StdFormExitCancel on page 373 events.

Infor Mongoose Application and Form Development | 367

Reference

StdFormColFind event
Occurs when the user clicks Find Value in Collection on the Edit menu.
The default implementation opens the Find dialog box, with the current property of the current collection
selected.
In a form without custom event handlers, if the user clicks OK in the Find dialog box and a specified
value is found, the entire sequence of standard events is:
•
•
•
•
•
•
•
•
•

StdFormColFind
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormColFindCompleted

Your application can call the default implementation by generating the event.

StdFormColFindCompleted event
Occurs after the default implementation for the StdFormColFind event and a find operation complete
successfully.
The event is not associated with a default handler and is for notification only.

StdFormColReplace event
Occurs when the user clicks Replace Value in Collection on the Edit menu.
The default implementation opens the Replace dialog box, with the current property in the current
collection selected.
In a form without custom event handlers, if the user clicks OK in the Replace dialog box and a specified
value is found and replaced, the entire sequence of standard events is:
•
•
•
•
•
•
•
•
•

StdFormColReplace
StdFormGetBoundValues
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues

Infor Mongoose Application and Form Development | 368

Reference
•

StdFormColReplaceCompleted

Your application can call the default implementation by generating the event.

StdFormColReplaceCompleted event
Occurs after the default implementation for the StdFormColReplace event and a find and replace
operation complete successfully.
The event is not associated with a default handler and is for notification only.

StdFormColSort event
Occurs when the user clicks Sort Collection on the Edit menu.
The default implementation opens the Sort dialog box, with the current property of the current collection
selected.
In a form without custom event handlers, if the user clicks OK in the Sort dialog box, this is the entire
sequence of standard events:
•
•
•
•
•
•
•
•
•

StdFormColSort
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormColSortCompleted

Your application can call the default implementation by generating the event.

StdFormColSortCompleted event
Occurs after the default implementation for the StdFormColSort event completes successfully and a
sort is performed.
The event is not associated with a default handler and is for notification only.

StdFormComponentHelp event
Occurs when the user performs one of these actions:

Infor Mongoose Application and Form Development | 369

Reference
•
•
•

Clicks a form component after selecting What's This? on the Help menu or after clicking the What's
This? button
Selects Current Field on the Help menu
Presses the F1 key

The default implementation launches the online help and displays the context-sensitive help topic
associated with the current component.
Your application can call the default implementation by generating the event.

StdFormCopy event
Occurs when the user clicks Copy on the Actions menu.
The default implementation creates a new object, initializes it with the values from the current object,
and makes the new object the current object.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
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

StdFormCopy
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNewCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormCopyCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. The current object can be in a primary collection,
secondary collection, or subcollection.

StdFormCopyCompleted event
Occurs when the default implementation for the StdFormCopy event completes successfully.
The event is not associated with a default handler and is for notification only.

Infor Mongoose Application and Form Development | 370

Reference

StdFormCurCacheChanged event
Occurs when the focus on a form changes from one collection to another or to a subcollection because
the user clicked on a component associated with a collection or subcollection that is not current.
The event is not associated with a default handler and is for notification only.

StdFormCurCacheChanging event
Occurs when the focus on a form is about to change from one collection or subcollection to another
collection or subcollection because the user clicked on a component associated with a collection or
subcollection that is not current.
Your application cannot call the default implementation by generating the event.

StdFormCurrentNotes event
Occurs when the user clicks Notes For Current on the Actions menu.
The default implementation opens the Notes dialog box.
Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. In the Notes dialog box, users can attach notes
or files to the current object in a collection or subcollection.

StdFormCurrentNotesCompleted event
Occurs when the user selects OK or Cancel in the Notes dialog box.
The default implementation updates the Notes toolbar button, the status bar, and the Actions menu
to indicate any change in note status or file-attachment status.
Your application can call the default implementation by generating the event.
Remarks
The Notes dialog box, opened with the menu command Objects > Notes for Current, applies to notes
and files associated with the current record. If the user attaches a note or file to a record, the default
implementation displays the message NOTES in the status bar, toggles the Notes button on the toolbar
to the down state, and adds a check mark next to the command Notes for Current on the Actions
menu. If the user detaches all notes and files from a record, the NOTES message is removed from the
status bar, the toolbar button displays in the up state, and the check mark is removed.
The default implementation is a form-level operation.

Infor Mongoose Application and Form Development | 371

Reference

StdFormDelete event
Occurs when the user clicks Delete on the Actions menu.
The default implementation handles new objects (that is, objects that have not been saved to a SQL
Server table) and saved objects differently. WinStudio removes a new object from the collection
immediately. WinStudio marks a saved object as deleted. The marked object will be deleted from the
database table when a Save operation is performed.
The default implementation in forms without custom event handlers generate these sequences of
standard events:
For a saved object:
•
•
•
•
•
•
•

StdFormDelete
StdFormGetBoundValues
StdObjectDelete
StdObjectDeleteExisting
StdObjectDeleteExistingCompleted
StdObjectDeleteCompleted
StdFormDeleteCompleted

For a new object:
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

StdFormDelete
StdFormGetBoundValues
StdObjectDelete
StdObjectDeleteNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectDeleteNewCompleted
StdObjectDeleteCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormDeleteCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. If the Delete operation is performed on an object
that has been marked as deleted, Windows client removes the mark and the object is not be deleted
when a Save operation is performed.
A StdObjectDelete event and a StdObjectDeleteExisting or a StdObjectDeleteNew event is fired before
each deletion.

Infor Mongoose Application and Form Development | 372

Reference

StdFormDeleteCompleted event
Occurs when the default implementation for the StdFormDelete event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormExitCancel event
Occurs when the user clicks Close and Cancel Changes on the Form menu.
The default implementation in discards any unsaved application data and prompts the user to save
any changes to the form definition before closing the form. In Design Mode, WinStudio discards any
unsaved changes to the form definition and prompts the user to save any unsaved application data
before closing the form.
Your application can call the default implementation by generating the event.

StdFormExitOK event
Occurs when the user clicks Close and Save Changes on the Form menu.
The default implementation in non-query forms performs validations on the data and, if no validation
error occurs, saves any changed data. If the form definition contains unsaved changes, Windows client
then prompts the user to save any changes to the definition before closing the form. In Design Mode,
WinStudio saves any changes to the form definition and then prompts the user to save any unsaved
application data before closing the form.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
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

StdFormExitOk
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdFormPutBoundValues
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdFormSave
StdFormPutBoundValues
StdObjectSave
StdObjectSaveCompleted
StdFormSaveCompleted
StdFormClose

Infor Mongoose Application and Form Development | 373

Reference
In query forms, the standard implementation retrieves the property values and operators of the current
filter from the components on the form, sets the filter criteria of the form from the filter object, and then
proceeds with normal OK processing.
The default implementation in a typical query form without custom event handlers fires the following
sequence of standard events:
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

StdFormExitOk
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdFormPutBoundValues
StdFormCalledFormReturned
StdFormClose
StdFormRefresh
StdFormGetBoundValues
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted

Your application can call the default implementations by generating the event.

StdFormFilter event
Occurs when the user selects Filter from the Actions menu and then clicks By Query.
The default implementation opens the filter form specified in the form definition.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
•
•
•
•
•
•

StdFormFilter
StdQueryFormInitialize
StdFormPredisplay
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormFilterCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.

Infor Mongoose Application and Form Development | 374

Reference

StdFormFilterCompleted event
Occurs when the default implementation for the StdFormFilter event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormFilterInPlaceBegin event
Occurs when the user selects Filter from the Actions menu and then clicks Begin In Place.
The default implementation activates Filter-in-Place mode for the current form.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
•
•
•

StdFormFilterInPlaceBegin
StdFormGetBoundValues
StdFormFilterInPlaceBeginCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. Generation of most events is disabled in
Filter-in-Place mode.

StdFormFilterInPlaceBeginCompleted event
Occurs when the default implementation for the StdFormFilterInPlaceBegin on page 375 event completes
successfully.
The event is not associated with a default handler and is for notification only.

StdFormFilterInPlaceCancel event
Occurs when the user selects Filter from the Actions menu and then clicks Cancel In Place.
The default implementation deactivates Filter-in-Place mode for the current form.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
•
•
•
•
•

StdFormFilterInPlaceCancel
StdFormGetBoundValues
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormFilterInPlaceCancelCompleted

Infor Mongoose Application and Form Development | 375

Reference
Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. Generation of most events is disabled in
Filter-in-Place mode. The StdFormFilterInPlaceCancel event is an exception and can be generated in
Filter-in-Place mode to call its handler. When this event is generated, the restriction on generating
events in Filter-in-Place mode is lifted and an event handler can generate any other event and allow
its handlers to execute.

StdFormFilterInPlaceCancelCompleted event
Occurs when the default implementation for the StdFormFilterInPlaceCancel event completes
successfully.
The event is not associated with a default handler and is for notification only.

StdFormFilterInPlaceExecute event
Occurs when the user selects Filter from the Actions menu and then clicks Execute In Place.
The default implementation executes Filter-in-Place processing.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
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

StdFormGetBoundValues
StdFormFilterInPlaceExecute
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted
StdFormFilterInPlaceExecuteCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. Generation of most events is disabled in
Filter-in-Place mode. The StdFormFilterInPlaceExecute event is an exception and can be generated
in Filter-in-Place mode to call its handler. When this event is generated, the restriction on generating
events in Filter-in-Place mode is lifted and an event handler can generate any other event and allow
its handlers to execute.

Infor Mongoose Application and Form Development | 376

Reference

StdFormFilterInPlaceExecuteCompleted event
Occurs when the default implementation for the StdFormFilterInPlaceExecute event completes
successfully.
The event is not associated with a default handler and is for notification only.

StdFormFilterInPlaceToggle event
Generated programatically.
The default implementation activates Filter-in-Place mode if Filter-in-Place is inactive and executes
data retrieval if Filter-in-Place is active.
In a basic form without a subcollection or custom event handlers, the default implementation fires one
of two sequences of events, the sequence depending on the current state of Filter-in-Place mode.
Sequence when Filter-in-Place mode is inactive
•
•
•

StdFormFilterInPlaceBegin
StdFormGetBoundValues
StdFormFilterInPlaceBeginCompleted

Sequence when Filter-in-Place mode is active
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

StdFormGetBoundValues
StdFormFilterInPlaceExecute
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted
StdFormFilterInPlaceExecuteCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. Generation of Windows client events is disabled
in Filter-in-Place mode, with the exception of the StdFormFilterInPlaceToggle event, the
StdFormFilterInPlaceCancel event, the StdFormFilterInPlaceExecute event, and the StdCurCompFind
event. These events can be generated programatically when Filter-in-Place is active.

Infor Mongoose Application and Form Development | 377

Reference

StdFormFirst event
Occurs when the user clicks First on the Actions menu.
The default implementation makes the first object in the current collection or subcollection the current
object.
The default implementation in a form without custom event handlers fires this sequence of standard
events:
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

StdFormFirst
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectFirst
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectFirstCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormFirstCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.
A StdObjectFirst event is fired before the first object becomes the current object.

StdFormFirstCompleted event
Occurs when the default implementation for the StdFormFirst event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormGetBoundValues event
Occurs during the execution of most standard operations, such as navigation of a collection or
subcollection and the Save, Refresh, Validate, and New operations. The event also occurs after the
user clicks Find Value in Collection, Replace Value in Collection, or Sort Collection on the Edit
menu.
The default implementation retrieves the current values of all bound components.
Your application cannot call the default implementation by generating the event.

Infor Mongoose Application and Form Development | 378

Reference
Remarks
You can use the event to implement your own binding, in which your application moves data between
a temporary data store of some type and a particular component. When the event is generated, your
handler should check whether the data in the component is modified and then transfer any modified
data to your temporary storage for the current object in the collection.
Because the event occurs in many Windows client operations of various kinds, you should exercise
caution in overriding the default implementation with custom event handlers.

StdFormGraph event
Occurs when the user clicks Graph on the Actions menu.
The default implementation opens the Graph Collection form.
In a basic form without custom event handlers, the default implementation fires the following sequence
of standard events:
•
•
•
•
•
•

StdFormGraph
StdFormGetBoundValues
StdFormGraphCompleted
StdFormPredisplay
StdFormLoadBoundValues
StdFormLoadDerivedValues

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. In the Graph Collection form, users can create
graphs representing specified properties in the current collection or subcollection.

StdFormGraphCompleted event
Occurs when the default implementation for the StdFormGraph event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormHelp event
Occurs when the user performs one of the following actions:
•
•

Clicks a form outside a component after selecting What's This? on the Help menu or after clicking
the What's This? button
Selects Current Form on the Help menu

Infor Mongoose Application and Form Development | 379

Reference
The default implementation launches the online help and displays the help topic associated with the
current form.
Your application can call the default implementation by generating the event.

StdFormHideSplitterPaneOne event
The default implementation hides the grid view.
To display the grid view, execute the StdFormShowSplitterPaneOne event. To hide the grid view again,
execute the StdFormHideSplitterPaneOne event.
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormHideSplitterPaneTwo event
The default implementation hides the detail view.
To display the detail view, execute the StdFormShowSplitterPaneTwo event. To hide the detail view
again, execute the StdFormHideSplitterPaneTwo event.
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormInitCompleted event
Occurs when all form initialization has completed.
The event is not associated with a default handler and is for notification only.
Remarks
The event is the last event to fire during initialization of a form.

Infor Mongoose Application and Form Development | 380

Reference

StdFormLast event
Occurs when the user clicks Last on the Actions menu.
The default implementation makes the last object in the current collection or subcollection the current
object.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
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

StdFormLast
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectLast
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectLastCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormLastCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.
A StdObjectLast event is fired before the last object becomes the current object.

StdFormLastCompleted event
Occurs when the default implementation for the StdFormLast event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormLinkUpdate event
Occurs before a parent form updates a linked child form. The event occurs in the child form during
various standard operations performed on the parent form.
The default implementation updates any variables specified in the SETVARVALUESONLINKUPDATE
on page 1061 specification, and then refreshes the primary collection.
This sequence of events was generated by performing the Next operation in a parent form.
•
•
•

StdFormNext
StdFormGetBoundValues
StdFormPerformValidations
Infor Mongoose Application and Form Development | 381

Reference
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

StdFormValidationsCompleted
StdObjectNext
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNextCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormLinkUpdate
StdFormRefresh
StdFormGetBoundValues
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted
StdFormLinkUpdateCompleted
StdFormNextCompleted

Your application can call the default implementation by generating the event.

StdFormLinkUpdateCompleted event
Occurs when the implementation for the StdFormLinkUpdate event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormLoadBoundValues event
Occurs after an object has become the current object in the current collection and before form
components have loaded their values from their data sources. Thus the event occurs during the
execution of most standard operations, such as navigation of a collection or subcollection and the
Save, Refresh, and New operations. The event also occurs after the user clicks one of the following
on the Edit menu: Find Value in Collection, Replace Value in Collection, Sort Collection, Add
Value for Current Field, or Find Value for Current Field.
In the default implementation, all bound components retrieve data from their sources based on their
data binding specifications.
Your application can call the default implementation by generating the event but cannot cancel the
default implementation by returning a failure value.

Infor Mongoose Application and Form Development | 382

Reference
Remarks
You can use the event to implement your own binding, in which your application moves data between
a temporary data store of some type and a particular component. When the event is generated, your
handler should retrieve the value from the data store and place it in the component.
Because the event occurs in many WinStudio operations of various kinds, you should exercise caution
in overriding the default implementation with custom event handlers.

StdFormLoadDerivedValues event
Occurs after an object has become the current object in the current collection and before form
components have loaded their values from their data sources. Thus the event occurs during the
execution of most standard operations, such as navigation of a collection or subcollection and the
Save, Refresh, and New operations. The event also occurs after the user clicks one of these options
on the Edit menu: Find Value in Collection, Replace Value in Collection, Sort Collection, Add
Value for Current Field, or Find Value for Current Field.
In the default implementation, all components with derived values retrieve data from their sources
based on their data-source specifications.
Your application can call the default implementation by generating the event.
Remarks
Because the event occurs in many WinStudio operations of various kinds, you should exercise caution
in overriding the default implementation with custom event handlers.

StdFormNew event
Occurs when the user clicks New on the Actions menu.
The default implementation creates a new instance of an object in the current collection or subcollection
and initializes it with default values.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
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

StdFormNew
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNewCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues

Infor Mongoose Application and Form Development | 383

Reference
•

StdFormNewCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.
A StdObjectNew event is fired before an object in a collection or a subcollection is created.

StdFormNewCompleted event
Occurs when the default implementation for the StdFormNew event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormNext event
Occurs when the user clicks Next on the Actions menu.
The default implementation makes the next object in the current collection or subcollection the current
object.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
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

StdFormNext
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectNext
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNextCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormNextCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.

Infor Mongoose Application and Form Development | 384

Reference

StdFormNextCompleted event
Occurs when the default implementation for the StdFormNext event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormNotes event
Occurs when the user clicks Notes For All on the Actions menu.
The default implementation opens the Class Notes dialog box.
Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. In the Class Notes dialog box, users can attach
notes or files to the current collection or subcollection.

StdFormNotesCompleted event
Occurs when the user selects OK or Cancel in the Class Notes dialog box.
The default implementation adds or removes a check mark next to the command Notes for All on the
Actions menu.
Your application can call the default implementation by generating the event.
Remarks
The Class Notes dialog box, opened with the menu command Actions > Notes for All, applies to
notes and files attached to the current collection or subcollection. If the user attaches a note or file, the
default implementation adds a check mark next to the command Notes for Current on the Actions
menu. If the user detaches all notes and files, the check mark is removed.
The default implementation is a form-level operation.

StdFormPagePop event
Conditions for firing the event are set by the developer. A typical condition is the user's interaction with
a form component, such as a command button, to which the developer has assigned StdFormPagePop
as an event handler.
The default implementation causes the previously displayed form page to become the current form
page, popping it off the form-page stack.
For example, in a form with three form pages, if you start on page 1 and click a button to go to page
3, popping from page 3 makes page 1 the current form page.
Infor Mongoose Application and Form Development | 385

Reference
Your application can call the default implementation by generating the event.

StdFormPerformValidations event
Occurs when the user clicks Validate on the Actions menu. Windows client also fires the event during
several other operations, as noted below.
The default implementation executes all validations specified for the components on the form and
displays an error message if the value of a component fails a validation.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
•
•
•

StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. Windows client generates the event during several
standard operations: Next, Previous, First, Last, New, Copy, Save, and Save Current.

StdFormPredisplay event
Occurs when a form opens, after Windows client has loaded the components from their data sources
but before the collections on the form are initialized, the initial command is executed, and component
values are displayed on the screen.
The event is not associated with a default handler and is for notification only.
Remarks
The event is the first event to fire during initialization of a non-query form.
Note: In the web client, this event might actually be fired after the form is displayed. The timing is
otherwise correct in relation to the overall sequence of events. However, the name implies this will be
executed before the form is made visible, which is not the case in the web client.
In Design Mode, the event is fired and any handlers associated with it are executed when you regenerate
a form (on the Edit menu, click Regenerate Form).

StdFormPrevious event
Occurs when the user clicks Previous on the Actions menu.

Infor Mongoose Application and Form Development | 386

Reference
The default implementation makes the previous object in the current collection or subcollection the
current object.
The default implementation in a form without custom event handlers fires the following sequence of
standard events:
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

StdFormPrevious
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectPrevious
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectPreviousCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormPreviousCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.
A StdObjectPrevious event is fired before the previous object becomes the current object.

StdFormPreviousCompleted event
Occurs when the default implementation for the StdFormPrevious on page 386 event completes
successfully.
The event is not associated with a default handler and is for notification only.

StdFormPutBoundValues event
The event is obsolete and is included for backward compatibility only.

StdFormRefresh event
Occurs when the user clicks Refresh on the Actions menu.
The default implementation retrieves the collection from the middle-tier IDO, subject to any current
filter criteria. WinStudio then displays the retrieved values in the form.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:

Infor Mongoose Application and Form Development | 387

Reference
•
•
•
•
•
•
•
•
•

StdFormRefresh
StdFormGetBoundValues
StdObjectRefresh
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCompleted

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation. The operation refreshes the current collection
and any subcollections associated with it on the form. When a subcollection is current, the operation
refreshes only that subcollection.
A StdObjectRefresh event is fired before each collection is refreshed.
In query forms, the default implementation retrieves the attributes of the current filter from the
components on the form (values, operators, and so forth), sets the filter criteria of the form from the
filter object, and then performs the normal refresh operation.

StdFormRefreshCompleted event
Occurs when the default implementation for the StdFormRefresh event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormRefreshCurrent event
Occurs when the user clicks Refresh Current on the Actions menu.
The default implementation retrieves the current object in the collection from the middle-tier IDO.
WinStudio then displays the retrieved values in the form.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
•
•
•
•
•
•

StdFormRefreshCurrent
StdObjectRefreshCurrent
StdObjectRefreshCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdFormRefreshCurrentCompleted

Your application can call the default implementation by generating the event.

Infor Mongoose Application and Form Development | 388

Reference
Remarks
The default implementation is a form-level operation. The operation refreshes the current object in the
current collection and any objects associated with it in subcollections on the form. When a subcollection
is current, the operation refreshes only the current object in that subcollection.
A StdObjectRefreshCurrent event is fired before each collection is refreshed.

StdFormRefreshCurrentCompleted event
Occurs when the default implementation for the StdFormRefreshCurrent event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormReportObjectRefreshCompleted event
This event occurs after the report generation process retrieves data from the middle-tier IDO, but before
report output is generated using the new data.
Remarks
This event is for notification only, cannot be canceled and does not have a default implementation or
action.

StdFormReportObjectRefresh event
This event occurs before the report generation process retrieves data from the middle-tier IDO
Remarks
This event is for notification only, cannot be canceled and does not have a default implementation or
action.

StdFormSave event
Occurs when the user clicks Save on the Actions menu.
The default implementation sends all changed object data in collections and subcollections on a form
to the middle-tier IDO, which commits the changes to the SQL Server database.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
•
•

StdFormGetBoundValues
StdFormPerformValidations

Infor Mongoose Application and Form Development | 389

Reference
•
•
•
•
•
•
•
•

StdFormValidationsCompleted
StdFormSave
StdFormPutBoundValues
StdObjectSave
StdObjectSaveCompleted
StdFormSaveCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.
The StdFormSave event is fired once per Save operation. It is not fired for each row in a collection or
subcollection, and it is not fired for each collection and subcollection on a form. An event handler
intended to operate on each row should not be assigned to the StdFormSave event. An event handler
that performs row-level validations, for example, should normally be executed on the
StdFormPerformValidations event.
The StdObjectSave event is fired once for each collection and subcollection in a Save operation.

StdFormSaveCompleted event
Occurs when the default implementation for the StdFormSave event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormSaveCurrent event
Occurs when the user clicks Save Current on the Actions menu.
The default implementation sends changed data in the current object to the middle-tier IDO, which
commits the data to the SQL Server database.
In a basic form without a subcollection or custom event handlers, the default implementation fires the
following sequence of standard events:
•
•
•
•
•
•
•
•

StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdFormSaveCurrent
StdFormPutBoundValues
StdObjectSaveCurrent
StdFormLoadBoundValues
StdObjectSaveCurrentCompleted

Infor Mongoose Application and Form Development | 390

Reference
•
•
•

StdFormSaveCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues

Your application can call the default implementation by generating the event.
Remarks
The default implementation is a form-level operation.

StdFormSaveCurrentCompleted event
Occurs when the default implementation for the StdFormSaveCurrent event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormShowHideSplitterPaneOne event
Occurs when the user performs one of these actions:
•
•
•

Clicks Hide/Show 1st Splitter Pane on the View menu
Press Ctrl + 1 for Windows client
Press Ctrl + Shift + 1 for web client

The default implementation toggles the grid view on or off.
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormShowHideSplitterPaneTwo event
Occurs when the user performs one of these actions:
•
•
•

Clicks Hide/Show 2nd Splitter Pane on the View menu
Press Ctrl + 2 for Windows client
Press Ctrl + Shift + 2 for web client

The default implementation toggles the detail view on or off.

Infor Mongoose Application and Form Development | 391

Reference
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormShowSplitterPaneOne event
The default implementation shows the grid view.
To hide the grid view, execute the StdFormHideSplitterPaneOne event. To display the grid view again,
execute the StdFormShowSplitterPaneOne event.
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormShowSplitterPaneTwo event
The default implementation shows the detail view.
To hide the detail view, execute the StdFormHideSplitterPaneTwo event. To display the detail view
again, execute the StdFormShowSplitterPaneTwo event.
Remarks
The default implementation is a form-level operation. This operation works only with multiview forms,
query forms, or other forms in which there are both grid views and detail views.
The users can attach event handlers to this event. The default implementation is bypassed if any of
the event handlers have non-zero return code.

StdFormSpreadsheetCopy event
Occurs when the user clicks the command To Excel on the Actions menu.
The default implementation exports the contents of the current collection to Microsoft Excel by first
launching Excel (or other spreadsheet program) and then populating the spreadsheet with the contents
of the collection.
Your application can call the default implementation by generating the event.

Infor Mongoose Application and Form Development | 392

Reference

StdFormSpreadsheetCopyCompleted event
Occurs when the default implementation for the StdFormSpreadsheetCopy event completes successfully.
The event is not associated with a default handler and is for notification only.

StdFormTimerTick event
Occurs whenever the Timer interval is reached.
Forms that implement a timer must use an event handler of this type to trigger whatever action needs
to be performed.
You can use any existing response type with this event.
When the timer is started, this event is raised even if the form is not currently active; that is, if you
configure the response to refresh some collection on a form, the form is refreshed at the timer interval
even if the form is not currently activated.

StdFormValidationsCompleted event
Occurs when the default implementation for the StdFormPerformValidations event completes
successfully.
The event is not associated with a default handler and is for notification only.

StdGraphElementClicked event
Occurs when the user clicks an element in a graph component.
For a graph component that is bound to a collection, the default implementation makes the object in
the collection that corresponds to the graph element the current object.
The default implementation fires the following sequence of standard events:
•
•
•
•
•
•
•
•
•

StdGraphElementClicked
StdFormGetBoundValues
StdFormPerformValidations
StdFormValidationsCompleted
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdFormLoadBoundValues
StdFormLoadDerivedValues
StdGraphElementClickedCompleted

Your application cannot call the default implementation by generating the event.

Infor Mongoose Application and Form Development | 393

Reference

StdGraphElementClickedCompleted event
Occurs when the default implementation for the StdGraphElementClicked event completes successfully.
The event is not associated with a default handler and is for notification only.

StdLastIMEValueUpdated event
Occurs when the value of a component is updated and the LASTIMEVALUE keyword is specified in
the component's data binding field.
The event is not associated with a default handler and is for notification only.
Remarks
This StdLastIMEValueUpdated event provides an opportunity to programmatically retrieve the value
from the component.

StdObjectCopy event
Occurs before Windows client creates a new object in a collection or subcollection, initializes it with
values from the current object, and makes the new object the current object during execution of:
•
•
•

The default implementation of the StdFormCopy event
An event handler of the type Collection Copy
The Copy on page 428 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNewCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectCopyCompleted event
Occurs when the default implementation for the StdObjectCopy event completes successfully.
The event is not associated with a default handler and is for notification only.

Infor Mongoose Application and Form Development | 394

Reference

StdObjectDelete event
Occurs before Windows client deletes the current object in a collection or subcollection during execution
of any of these situations:
•
•
•

The default implementation of the StdFormDelete event
An event handler of the type Collection Delete
The Delete on page 437 method in a script

The default implementation handles new objects (that is, objects that have not been saved to a SQL
Server table) and saved objects differently. WinStudio removes a new object from the collection
immediately but marks a saved object as deleted. The marked object will be deleted from the database
table when a Save operation is performed.
The default implementation generates these sequences of standard events:
For saved objects:
•
•
•
•

StdObjectDelete
StdObjectDeleteExisting
StdObjectDeleteExistingCompleted
StdObjectDeleteCompleted

For new objects:
•
•
•
•
•
•

StdObjectDelete
StdObjectDeleteNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectDeleteNewCompleted
StdObjectDeleteCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.
If the default implementation is performed on an object that has been marked as deleted, Windows
client removes the mark and the object will not be deleted when a Save operation is performed.
A StdObjectDeleteExisting or a StdObjectDeleteNew event is fired before each deletion.

StdObjectDeleteCompleted event
Occurs when the default implementation for the StdObjectDelete event completes successfully.
The event is not associated with a default handler and is for notification only.

Infor Mongoose Application and Form Development | 395

Reference

StdObjectDeleteExisting event
Occurs before Windows client marks as deleted an object that has been saved. The event is fired
during execution of any of these:
•
•
•

The default implementation of the StdFormDelete event
An event handler of the type Collection Delete
The Delete on page 437 method in a script

The marked object will be deleted from the database table when a save operation is performed.
The default implementation fires the following sequence of standard events:
•
•

StdObjectDeleteExisting
StdObjectDeleteExistingCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.
If the delete operation is performed on an object that has been marked as deleted, Windows client
removes the mark and the object will not be deleted when a save operation is performed.
Before the deletion of a new, unsaved object, the StdObjectDeleteNew on page 396 event occurs.

StdObjectDeleteExistingCompleted event
Occurs when the default implementation for the StdObjectDeleteExisting event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectDeleteNew event
Occurs before WinStudio deletes an object that has not been saved. The event is fired during execution
of
•
•
•

The default implementation of the StdFormDelete on page 372 event
An event handler of the type Collection Delete
The Delete on page 437 method in a script

The default implementation removes the new object immediately from the collection or subcollection.
The default implementation fires the following sequence of standard events:
•
•
•

StdObjectDeleteNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted

Infor Mongoose Application and Form Development | 396

Reference
•

StdObjectDeleteNewCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.
The StdObjectDeleteExisting event occurs before WinStudio marks as deleted an object that has been
saved.

StdObjectDeleteNewCompleted event
Occurs when the default implementation for the StdObjectDeleteNew event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectFirst event
This event occurs before Windows client navigates to the first object in a collection or subcollection
during execution of any of these:
•
•
•

The default implementation of the StdFormFirst event
An event handler of the type Collection Navigate for which the parameter First is specified
The First on page 437 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectFirst
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectFirstCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectFirstCompleted event
Occurs when the default implementation for the StdObjectFirst event completes successfully.
The event is not associated with a default handler and is for notification only.

Infor Mongoose Application and Form Development | 397

Reference

StdObjectLast event
Occurs before Windows client navigates to the last object in a collection or subcollection during execution
of any of these:
•
•
•

The default implementation of the StdFormLast event
An event handler of the type Collection Navigate for which the parameter Last is specified
The Last on page 594 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectLast
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectLastCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectLastCompleted event
Occurs when the default implementation for the StdObjectLast event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectModified event
The event allows you to take some action when a record in the current collection is modified.
This event is generated only when the current record is not already modified and:
•
•

You change the value of a component bound to a property of the current collection; or
An event handler marks the record or a property modified.

Remarks
This can occur on an auto-inserted record that is modified, but typically, it is generated on a record
queried by means of a LoadCollection action and then modified.

StdObjectNew event
This even occurs before Windows client creates a new object in the current collection or subcollection
and initializes it with default values.

Infor Mongoose Application and Form Development | 398

Reference
•
•
•

The default implementation of the StdFormNew event
An event handler of the type Collection New
The New on page 599 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectNew
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNewCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectNewCompleted event
Occurs when the default implementation for the StdObjectNew event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectNext event
Occurs before Windows client navigates to the next object in a collection or subcollection during
execution of any of these:
•
•
•

The default implementation of the StdFormNext event
An event handler of the type Collection Navigate for which the parameter Next is specified
The Next on page 600 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectNext
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectNextCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

Infor Mongoose Application and Form Development | 399

Reference

StdObjectNextCompleted event
Occurs when the default implementation for the StdObjectNext event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectPrevious event
This event occurs before Windows client navigates to the previous object in a collection or subcollection
during execution of any of these:
•
•
•

The default implementation of the StdFormPrevious event
An event handler of the type Collection Navigate for which the parameter Previous is specified
The Previous on page 604 method in a script

The default implementation fires the following sequence of standard events:
•
•
•
•

StdObjectPrevious
StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectPreviousCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectPreviousCompleted event
Occurs when the default implementation for the StdObjectPrevious event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectRefresh event
This event occurs before Windows client retrieves a collection from the middle-tier IDO, subject to any
current filter criteria, during execution of one of these:
•
•
•

The default implementation of the StdFormRefresh event
An event handler of the type Collection Refresh
The Refresh on page 605 method in a script

The default implementation fires the following sequence of standard events:
•

StdObjectRefresh

Infor Mongoose Application and Form Development | 400

Reference
•
•
•

StdObjectSelectCurrent
StdObjectSelectCurrentCompleted
StdObjectRefreshCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectRefreshCompleted event
Occurs when the default implementation for the StdObjectRefresh event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectRefreshCurrent event
This event occurs before Windows client retrieves the current object in the collection from the middle-tier
IDO during execution of one of the these:
•
•
•

The default implementation of the StdFormRefreshCurrent event
An event handler of the type Collection Refresh Current
The RefreshCurrentObject on page 606 method in a script

The default implementation fires the following sequence of standard events:
•
•

StdObjectRefreshCurrent
StdObjectRefreshCurrentCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectRefreshCurrentCompleted event
Occurs when the default implementation for the StdObjectRefreshCurrent event completes successfully.
The event is not associated with a default handler and is for notification only.

Infor Mongoose Application and Form Development | 401

Reference

StdObjectSave event
This event occurs before Windows client sends all changed object data in a collection or subcollection
to the middle-tier IDO, which commits the changes to the SQL Server database. The event occurs
during execution of one of these:
•
•
•

The default implementation of the StdFormSave event
An event handler of the type Collection Save
The Save on page 613 method in a script

The default implementation fires the following sequence of standard events:
•
•

StdObjectSave
StdObjectSaveCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.
The StdObjectSave event is fired once for each collection and subcollection in a Save operation. The
event allows the developer to determine what action to perform at the start of a Save for a top-level
collection and for each subcollection.
The event is not fired for each row in a collection or subcollection. An event handler intended to operate
on each row in response to a row-level event should not be assigned to the StdObjectSave event.
Instead, to process rows, an event handler on StdObjectSave must loop through all modified rows in
the current top-level collection and all subcollections.
An event handler that performs row-level validations should normally be assigned to the
StdFormPerformValidations event.

StdObjectSaveCompleted event
Occurs when the default implementation for the StdObjectSave event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectSaveCurrent event
This event occurs before Windows client sends changed data in the current object to the middle-tier
IDO, which commits the data to the SQL Server database. The event occurs during execution of the
default implementation of the StdFormSaveCurrent event.
The default implementation generates this sequence of standard events:
•
•

StdObjectSaveCurrent
StdFormLoadBoundValues

Infor Mongoose Application and Form Development | 402

Reference
•

StdObjectSaveCurrentCompleted

Your application cannot call the default implementation by generating the event.
Remarks
The default implementation is a collection-level operation, which can occur in a collection whether or
not the collection is current.

StdObjectSaveCurrentCompleted event
Occurs when the default implementation for the StdObjectSaveCurrent event completes successfully.
The event is not associated with a default handler and is for notification only.

StdObjectSelectCurrent event
Occurs during navigation of a collection or subcollection and during execution of the Refresh, Execute
Filter-in-Place, New, Copy, and Delete (a new object) operations. The event also occurs after the user
clicks Find Value in Collection, Replace Value in Collection, Sort Collection, Add Value for Current
Field, or Find Value for Current Field on the Edit menu.
The default implementation makes a selected object in a collection or subcollection the current object.
Your application cannot call the default implementation by generating the event.
Remarks
Because the event occurs in many WinStudio operations of various kinds, you should exercise caution
in overriding the default implementation with custom event handlers.

StdObjectSelectCurrentCompleted event
Occurs when the default implementation for the StdObjectSelectCurrent event completes successfully.
The event is not associated with a default handler and is for notification only.

StdQueryFormAddAdditional event
This event occurs when the user clicks the Add button on the Additional Criteria tab of a query form.
The default implementation retrieves the values from the form components that specify a property
name, operator, property value, and whether the new clause should take OR or AND. The implementation
constructs a new criteria clause, adds the clause to the currently open filter object, and adds a line to
the list box component that displays additional criteria.

Infor Mongoose Application and Form Development | 403

Reference
Your application can call the default implementation by generating the event.

StdQueryFormClear event
This event occurs when the user clicks the Clear button on a query form.
The default implementation clears the values from the components that specify operators and property
values for primary criteria, clears the same attributes from the currently open filter object, removes all
clauses from the additional criteria component, and removes all additional criteria clauses from the
currently open filter object.
Your application can call the default implementation by generating the event.

StdQueryFormFilterOpen event
This event occurs on a query form, when the user selects Filter from the Actions menu, and then
clicks Open. After this event is generated, the Select Filter dialog box displays, to allow you to choose
a filter to open.
Optionally, you can add a component, like a button, attach this event and an event handler to open the
Select Filter dialog box.

StdQueryFormFilterSave event
This event occurs on a query form, when the user selects Filter from the Actions menu, and then
clicks Save. After this event is generated, the Enter a Name for the Filter dialog box displays, to allow
you to save your defined filter and reuse it later.
Optionally, you can add a component, like a button, attach this event and an event handler to open the
Select Filter dialog box.

StdQueryFormInitialize event
This event occurs when a query form opens.
The default implementation initializes the state and values for the components on the query form from
the filter attributes.
Your application cannot call the default implementation by generating the event.
Remarks
The event is the first event to fire during initialization of a query form.

Infor Mongoose Application and Form Development | 404

Reference

StdQueryFormRemoveAdditional event
Occurs when the user clicks the Remove button on the Additional Criteria tab of a query form.
The default implementation removes the entry currently selected in the list box that displays additional
criteria clauses and removes the corresponding clause from the currently open filter.
Your application can call the default implementation by generating the event.

StdQueryFormSelectAdditionalProperty event
Occurs when the user selects a property name from the drop-down list on the Additional Criteria tab
of a query form.
The default implementation resets the list source on the property-value component in order to provide
the correct list for the selected property. You can specify the list source in the Edit Filter dialog box.
Your application can call the default implementation by generating the event.

StdWebContainerMessageReceived event
When this event is triggered, it populates the form variables StdWebPageMessageContext and
StdWebPageMessageData with the message values that were set by an event handler with response
type Send Web Container Message.
Example
You want a form on the child frame to update an edit box with a value from the main frame.
Generate the Send Web Container message event from the main frame with a message type of
sendMessageToContainer. The "JSON data" contains setComponent=editBoxA,
toValue=V(valueToSend). (The data that is defined here is up to the form developer.)
On the child form, use the event handler StdWebContainerMessageReceived. Parse
ThisForm.Variables(StdWebPageMessageData) to retrieve the values of setComponent and toValue.
Then use a form script to set the value to the component.

StdWebLocationInfoReceived event
This event occurs when the browser returns the device's location information. This event is used in
conjunction with Get Location from Browser event.
This event is for the web client only.
These form variables are set:

Infor Mongoose Application and Form Development | 405

Reference

Form variable

Description

StdLocationPosition

The device's location in latitude and longitude, in decimal notation (for
example, "42.571523299999996, -82.8217975"). This variable has no
value if an error is encountered.

StdLocationElapsedTime

The time in seconds since the location has been updated. This variable
has no value if an error is encountered.

StdLocationError

The error code returned. This variable can have any of these values:
•
•
•
•

PERMISSION_DENIED: The retrieval of the location information
failed because the page has no permission to do so.
POSITION_UNAVAILABLE: The retrieval of the location information
failed because the internal source of position returned an error.
TIMEOUT: The time allowed to acquire the location information was
reached before the location information is obtained.
NOREPLY: The user has not responded to the request after the
Timeout Value lapsed.

WinStudio API
Interfaces
IWSApplication interface (WinStudio scripts)
This topic lists the methods and properties of the IWSApplication interface on page 269. The
IWSApplication interface is the root interface to which all IWSForm, IWSFormComponent, and
IWSIDOCollection interfaces are attached.
Methods
•
•
•
•
•
•
•
•
•

ColorDescriptorFromColor on page 426
ColorFromColorDescriptor on page 427
DateTimeToString on page 435
DiagnosticsLog on page 441
ExpandNumSortedChar on page 447
FindForm on page 453
FocusToForm on page 455
FontDescriptorFromFont on page 456
FontFromFontDescriptor on page 457

Infor Mongoose Application and Form Development | 406

Reference
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

GetBaseFontDescriptor on page 481
GetBaseFontFaceName on page 482
GetStringValue on page 520
GetUserPreferenceValue on page 522
IsCurrentUserAuthorizedToRunForm on page 569
RemoveNotifyIcon on page 608
RunForm on page 612
ShowMessage on page 651
Signout on page 658
ShowOpenFileDialog on page 653
ShowSaveFileDialog on page 656
ToOSDec on page 660
Variables on page 693

Properties
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

ActiveForm on page 696
CollectionCap on page 705
ConfigurationName on page 708
DropDownCap on page 721
Forms on page 728
FormServer on page 729
Platform on page 761
Value on page 778
ValueInternal on page 781
Variables on page 693
Visible on page 782
WildCardCharacter on page 784

IWSForm interface (WinStudio scripts)
This topic lists the methods and properties of the IWSForm interface on page 269. Each instance of a
form is a separate object. To access the IWSForm interface, use the following methods and properties:
•
•
•

ThisForm property (references the currently active form)
Application.ActiveForm property
Application.FindForm() method

Methods
•
•
•
•
•

ApplySavedFilter on page 416
CallGlobalScript on page 420
GenerateEvent on page 479
GetCurrentComponentName on page 484
GetFormPage on page 491

Infor Mongoose Application and Form Development | 407

Reference
•
•
•
•
•
•
•

GetSecondaryIDOCollection on page 519
GotoFormPage on page 527
LastModalChildIsOrInheritsFrom on page 594
PopFormPage on page 602
PostEvent on page 603
SetFocus on page 625
Variables on page 693

Properties
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

BackgroundColor on page 698
Caption on page 701
CharHeight on page 702
CharWidth on page 703
Components on page 706
CurrentIDOCollection on page 710
ForegroundColor on page 725
FormDataModified on page 726
FormModified on page 728
Height on page 732
IsLinkedChild on page 736
IsModalFindChild on page 736
LastModalChildEndedOk on page 741
LastModalChildFindResult on page 742
LastModalChildName on page 743
Left on page 744
LinkedChildForms on page 745
ModalChildForm on page 749
Name on page 750
NonStdBoundDataModified on page 753
ParentForm on page 759
ParentFormName on page 760
PrimaryIDOCollection on page 761
Top on page 773
Width on page 783

IWSFormComponent interface (WinStudio scripts)
This topic lists the methods and properties of the IWSFormComponent interface on page 270. A
component is any field, grid, radio button, or other type of field or control used on a form in WinStudio.
Methods
•

DataViewDisableComponentUpdates on page 429

Infor Mongoose Application and Form Development | 408

Reference
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

DataViewDrilldownLoadDetailForm on page 430
DataViewDrilldownRunSubDrilldown on page 431
DataViewEnableComponentUpdates on page 431
DataViewGetScopeFilter on page 432
DataViewPromptForInputParameters on page 433
DataViewRefresh on page 433
DataViewSetInputParameter on page 434
DefaultData on page 437
DeleteGridRows on page 439
DeleteListBoxString on page 440
DisplayMessage on page 443
ForceRepaint on page 458
GetGridCurrentCol on page 491
GetGridCurrentRow on page 492
GetGridRowCount on page 493
GetGridValue on page 494
GetGridValueByColumnName on page 495
GetListBoxCount on page 497
GetListBoxCurSel on page 499
GetListBoxText on page 500
GetListValue on page 502
GetModifiedSinceLoadOrValidation on page 504
GetNullableValue on page 506
GetValidatorsHaveRunSinceLoadFromSource on page 524
GetValue on page 524
GetValueOfBoolean on page 526
GetValueOfByte on page 526
GetValueOfChar on page 526
GetValueOfDateTime on page 526
GetValueOfDecimal on page 526
GetValueOfDouble on page 526
GetValueOfGuid on page 526
GetValueOfInt16 on page 526
GetValueOfInt32 on page 526
GetValueOfInt64 on page 526
GetValueOfInteger on page 526
GetValueOfLong on page 526
GetValueOfSByte on page 526
GetValueOfShort on page 526
GetValueOfSingle on page 526
GetValueOfString on page 526
GetValueOfUInt16 on page 526
GetValueOfUInt32 on page 526
GetValueOfUInt64 on page 526

Infor Mongoose Application and Form Development | 409

Reference
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

GraphDraw on page 528
GraphEnableRuntimeButtons on page 529
GraphEnableRuntimeProperties on page 529
GraphGetCollectionIndexFrom on page 530
GraphGetCollectionIndexTo on page 531
GraphGetGraphType on page 531
GraphGetSupportedGraphTypes on page 533
GraphLockGraphObjects on page 536
GraphNext on page 536
GraphPrevious on page 537
GraphSetCollectionToGraph on page 538
GraphSetDataSeriesColors on page 539
GraphSetGraphType on page 539
GraphSetMainTitleFontDescriptor on page 542
GraphSetMainTitleText on page 543
GraphSetOtherTitleFontDescriptor on page 543
GraphSetPropertiesToGraph on page 545
GraphSetXAxisLabelProperty on page 545
GraphSetXAxisTitleText on page 546
GraphSetXLimit on page 547
GraphSetYAxisTitleText on page 547
InsertGridRows on page 555
InsertListBoxString on page 556
InvalidateList on page 558
IsListBoxEntrySelected on page 571
ListSourceScriptAddEntries on page 595
ListSourceScriptAddListItem on page 596
LockGridUpdate on page 597
RepaintWindow on page 609
ResetListBoxContent on page 610
Resize on page 611
SaveGraph on page 617
SetGridCurrentCell on page 625
SetGridRowColColor on page 626
SetGridRowColColorByColumnName on page 627
SetGridRowColFont on page 628
SetGridRowColFontByColumnName on page 630
SetGridRowColImage on page 631
SetGridRowColImageByColumnName on page 632
SetGridTopRow on page 633
SetGridValue on page 634
SetGridValueByColumnName on page 635
SetListBoxCurSel on page 636
SetListBoxEntrySelected on page 637

Infor Mongoose Application and Form Development | 410

Reference
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

SetModifiedSinceLoadOrValidation on page 638
SetValidatorsHaveRunSinceLoadFromSource on page 648
SetValue on page 649
TreeCollapsedNodeToXml on page 660
TreeCollapseNodeAt on page 661
TreeCollapseRootNode on page 662
TreeCollapseSelectedNodes on page 662
TreeCollapsingNodeToXml on page 663
TreeExpandedNodeToXml on page 663
TreeExpandingNodeToXml on page 664
TreeExpandNodeAt on page 664
TreeExpandRootNode on page 665
TreeExpandSelectedNodes on page 665
TreeGetChildNodeCountAt on page 666
TreeGetChildNodeCountAtRoot on page 667
TreeGetCurItemLevelProperty on page 667
TreeGetCurItemProperty on page 668
TreeGetCurLevel on page 669
TreeGetItemPropertyAt on page 670
TreeGetRecursiveCount on page 671
TreeGetRecursiveMax on page 671
TreeHasCompletedCollapsing on page 672
TreeHasCompletedExpanding on page 673
TreeHasExpandedAt on page 674
TreeIsCollapsing on page 674
TreeIsCurrentlyExpandedAt on page 675
TreeIsExpanding on page 676
TreeNodeToXmlAt on page 677
TreeRefresh on page 677
TreeRootNodeHasExpanded on page 678
TreeRootNodeIsCurrentlyExpanded on page 678
TreeSelectedNodesToXml on page 679
TreeSetCacheRecordCap on page 680
TreeSetExpandNodeCountCap on page 681
TreeSetNodeFontAt on page 681
TreeSetNodeFontAtTreeLevel on page 682
TreeSetNodeForeColorAt on page 683
TreeSetNodeForeColorAtTreeLevel on page 684
TreeSetNodeImageAt on page 685
TreeSetNodeImageAtTreeLevel on page 686
TreeSetNodeSelectedImageAt on page 686
TreeSetNodeSelectedImageAtTreeLevel on page 687
TreeSetRecursiveMax on page 688
UnlockGridUpdate on page 689

Infor Mongoose Application and Form Development | 411

Reference
•

ValidateData on page 691

Properties
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

BackgroundColor on page 698
BitmapFileName on page 699
BlobFormat on page 700
Caption on page 701
CurrentlyEnabled on page 712
CurrentlyVisible on page 713
DataLength on page 714
DataType on page 715
DecimalSymbol on page 716
Default on page 717
DigitsAfterDecimal on page 719
DisplayedValue on page 720
Enabled on page 722
ForegroundColor on page 725
Height on page 732
IDOCollection on page 733
InputMask on page 734
InputMaskPrompt on page 735
IsNull on page 737
Justify on page 739
Label on page 741
Left on page 744
List on page 746
ListIndex on page 747
Name on page 750
NotebookCurTab on page 754
NumListCols on page 757
NumListRows on page 758
Required on page 765
TabOrder on page 767
TBD on page 769
Text on page 770
TextInternal on page 771
Top on page 773
TruncateDecimal on page 775
Type on page 776
Value on page 778
ValueInternal on page 781
Visible on page 782
Width on page 783

Infor Mongoose Application and Form Development | 412

Reference

IWSFormWizard interface (WinStudio scripts)
This topic lists the methods and properties of the IWSFormWizard interface. Scripts defined in
IWSFormWizard interface are designed to be used within a wizard form type only.
Methods
•

GetComponentDesignTemplate on page 483

IWSIDOCollection interface (WinStudio scripts)
This topic lists the methods and properties of the IWSIDOCollection interface on page 272. Access
these methods and properties using the following IWSForm methods and properties:
•
•
•

PrimaryIDOCollection property
CurrentIDOCollection property
GetSecondaryIDOCollection method

Methods
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

Clear on page 424
ClearEntries on page 425
Copy on page 428
Delete on page 437
DisableComponentUpdates on page 442
EnableComponentUpdates on page 445
ExportBlob on page 448
ExportCollection on page 448
ExportCurrentObjectPropertyAsBlob on page 449
ExportObjectPropertyAsBlob on page 451
First on page 454
GetCurrentObjectIndex on page 485
GetCurrentObjectProperty on page 486
GetCurrentObjectPropertyBinaryLength on page 487
GetCurrentObjectPropertyInternal on page 487
GetDisplayedObjectName on page 489
GetLabelForProperty on page 496
GetNumEntries on page 507
GetNumProperties on page 508
GetObjectForceUpdate on page 509
GetObjectProperty on page 510
GetObjectPropertyBinaryLength on page 511
GetObjectPropertyInternal on page 512
GetPropertyDefault on page 515
GetPropertyLength on page 516

Infor Mongoose Application and Form Development | 413

Reference
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

GetPropertyName on page 517
GetSecondaryCollectionNumber on page 518
GetSubCollection on page 521
ImportBlob on page 551
ImportCurrentObjectPropertyAsBlob on page 552
ImportObjectPropertyAsBlob on page 554
IsBOF on page 559
IsCollectionModified on page 560
IsCurrentObjectAutoInsertedAndUnmodified on page 561
IsCurrentObjectDeleted on page 562
IsCurrentObjectInvalid on page 563
IsCurrentObjectModified on page 564
IsCurrentObjectNew on page 565
IsCurrentObjectNewAndUnmodified on page 566
IsCurrentObjectPropertyModified on page 566
IsCurrentObjectReadOnly on page 567
IsCurrentObjectTheAutoInsertRow on page 568
IsEOF on page 570
IsNewInProgress on page 572
IsObjectAutoInsertedAndUnmodified on page 573
IsObjectDeleted on page 574
IsObjectInvalid on page 575
IsObjectModified on page 576
IsObjectNew on page 577
IsObjectNewAndUnmodified on page 577
IsObjectPropertyModified on page 578
IsObjectReadOnly on page 579
IsObjectTheAutoInsertRow on page 580
IsPropertyBoundByComponent on page 583
IsPropertyIdentity on page 584
IsPropertyKey on page 585
IsPropertyLinkBy on page 586
IsPropertyNullable on page 587
IsPropertyNumeric on page 588
IsPropertyNumSortedChar on page 588
IsPropertyProtected on page 589
IsPropertyQuotable on page 590
IsPropertyReadOnly on page 591
IsPropertyRequired on page 592
IsPropertyUppercase on page 593
Last on page 594
MoveCurrentIndexAndRefresh on page 598
New on page 599
Next on page 600

Infor Mongoose Application and Form Development | 414

Reference
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

NotifyDependentsToRefresh on page 601
Previous on page 604
Refresh on page 605
RefreshCurrentObject on page 606
RefreshKeepCurIndex on page 607
RemoveEntries on page 608
ResetCollectionModified on page 610
Save on page 613
SaveCurrent on page 614
SaveCurrentDeferStateUpdate on page 614
SaveDeferStateUpdate on page 615
SetCurrentObject on page 618
SetCurrentObjectModified on page 620
SetCurrentObjectProperty on page 620
SetCurrentObjectPropertyInternal on page 621
