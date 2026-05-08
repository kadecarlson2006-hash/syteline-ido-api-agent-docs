---
name: syteline-variables
description: "Form variables and validators - variable types, validator types, automatic FK validations"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

IsObjectInvalid method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object in a specified IDO collection is in an error state.
Syntax
object.IsObjectInvalid( integer )
Part

Description

object

Required. The name of a valid IDO collection object.

integer

Required. The index number of an object in the entries for the current
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the object has an error.
FALSE indicates that the object does not have an error.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectInvalid method.
Example
Sub Main()
Dim bValue As Boolean
Dim i As Integer
Dim curCol As IWSIDOCollection
curCol = ThisForm.PrimaryIDOCollection()
For i = 0 To ThisForm.PrimaryIDOCollection.GetNumEntries() - 1
bValue = curCol.IsObjectInvalid(i)
If bValue And curCol.IsObjectModified(i) Then
curCol.SetObjectModified(i, False)
End If
Next i
End Sub

Infor Mongoose Application and Form Development | 575

Reference

IsObjectModified method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object in a specified IDO collection has been modified.
Syntax
object.IsObjectModified( integer )
Part

Description

object

Required. The name of a valid IDO collection object.

integer

Required. The index number of an object in the entries for the current
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the object has been modified (by entering or changing data).
FALSE indicates that the object has not been modified.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectModified method.
Example
Sub Main()
Dim i As Integer
Dim iEnableOrDisable As Integer
Dim curCol As IWSIDOCollection
curCol = ThisForm.Components(GetParameter(0)).IDOCollection
iEnableOrDisable = 0
For i = 0 To curCol.GetNumEntries - 1
If curCol.IsObjectModified(i) Then
iEnableOrDisable = 1
Exit For
End If
Next i
End Sub

Infor Mongoose Application and Form Development | 576

Reference

IsObjectNew method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether the object in a specified IDO collection is marked as new.
Syntax
object.IsObjectNew( integer )
Part

Description

object

Required. The name of a valid IDO collection object.

integer

Required. The index number of an object in the entries for the current
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the object is marked as new.
FALSE indicates that the object is not marked as new.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectNew method.
Example
Sub Main()
Dim i As Integer
i = ThisForm.PrimaryIDOCollection.GetCurrentObjectIndex()
If ThisForm.PrimaryIDOCollection.IsObjectNew(i) Then
Application.ShowMessage("This is a new record.")
End If
End Sub

IsObjectNewAndUnmodified method (WinStudio scripts)
Applies To
IWSIDOCollection interface

Infor Mongoose Application and Form Development | 577

Reference
Definition
Returns a Boolean value indicating whether the object in a specified IDO collection is marked as new
and has not been modified.
Syntax
object.IsObjectNewAndUnmodified( integer )
Part

Description

object

Required. The name of a valid IDO collection object.

integer

Required. The index number of an object in the entries for the current
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the object is marked as new and has not been modified.
FALSE indicates either that the object is not marked as new, that it has been modified, or both.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectNewAndUnmodified method.
Example
Sub Main()
Dim i As Integer
For i = 0 to ThisForm.PrimaryIDOCollection.GetNumEntries() - 1
If ThisForm.PrimaryIDOCollection.IsObjectNewAndUnmodified(i) Then
ThisForm.Components("PaymentTypeCombo").Text = ""
End If
Next i
End Sub

IsObjectPropertyModified method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection has been
modified.

Infor Mongoose Application and Form Development | 578

Reference
Syntax
object.IsObjectPropertyModified( string, integer )
Part

Description

object

Required. The name of a valid IDO collection object.

string

Required. The name of the object property.

integer

Required. The index number of an object in the entries for the specified
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the object property has been modified.
FALSE indicates that the object property has not been modified.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectPropertyModified method.
Example
Sub Main()
Dim i As Integer
Dim returnValue As String
returnValue = "0"
For i = 0 to ThisForm.PrimaryIDOCollection.GetNumEntries() - 1
If (ThisForm.PrimaryIDOCollection.IsObjectPropertyModified(GetPa
rameter(0), i) = True And _
ThisForm.PrimaryIDOCollection.GetCurrentObjectProperty(GetPa
rameter(0)) = "3") Then
returnValue = "1"
End If
Next i
End Sub

IsObjectReadOnly method (WinStudio scripts)
Applies To
IWSIDOCollection interface

Infor Mongoose Application and Form Development | 579

Reference
Definition
Returns a Boolean value indicating whether a specified object in a collection is read-only.
Syntax
object.IsObjectReadOnly(long)
Part

Description

object

Required. The name of a valid IDO collection object.

long

Required. A long integer indicating the row index number of the object in
the collection.

Remarks
A return value of:
•
•

TRUE indicates that the object in the collection is read-only.
FALSE indicates that the object is not read-only.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectReadOnly method.
Example
Sub Main()
Application.ShowMessage(ThisForm.PrimaryIDOCollection.IsObjectReadOn
ly(5))
End Sub

IsObjectTheAutoInsertRow method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether a row in a specified IDO collection is the automatically
inserted row (the autoinsert row).
Syntax
object.IsObjectTheAutoInsertRow( integer )

Infor Mongoose Application and Form Development | 580

Reference

Part

Description

object

Required. The name of a valid IDO collection object.

integer

Required. The index number of an object in the entries for the current
IDO collection.

Remarks
A return value of:
•
•

TRUE indicates that the specified row is the autoinsert row.
FALSE indicates that the specified row is not the autoinsert row.

If the specified IDO collection might not have focus, use this method. If the specified IDO collection
has focus, use the IsCurrentObjectTheAutoInsertRow method.
Example
Sub SelectAll(ByVal InputSelected As Integer)
Dim i As Integer
Dim iSelectedQty As Decimal
Dim oSubCol As IWSIDOCollection
oSubCol = ThisForm.Components("SerialGrid").IDOCollection
iSelectedQty = 0
For i = 0 To oSubCol.GetNumEntries - 1
If (Not oSubCol.IsObjectTheAutoInsertRow(i)) Then
oSubCol.SetObjectPropertyPlusModifyRefresh("UbSelect", i,
CStr(InputSelected))
If InputSelected = 0 Then
oSubCol.SetObjectModified(i, False)
End If
iSelectedQty = iSelectedQty + InputSelected
End If
Next i
ThisForm.PrimaryIDOCollection.SetCurrentObjectPropertyPlusModifyRe
fresh("UbSelectedQty", CStr(iSelectedQty))
If InputSelected = 0 Then
oSubCol.SetCurrentObjectModified(False)
End If
oSubCol = Nothing
End Sub

IsPaneOneHidden method (WinStudio scripts)
Applies to
IWSForm interface

Infor Mongoose Application and Form Development | 581

Reference
Description
Returns a Boolean value which indicates whether splitter pane one is currently hidden
Syntax
object.IsPaneOneHidden( )
Part

Description

object

Required. A reference to a valid form object.

Remarks
A return value of:
•
•

TRUE indicates that the pane is hidden.
FALSE indicates that the pane is visible.

This form script API can be used along with the standard events to have more control over the splitter
panes.
Example
public void HandlePaneState()
{
if ( ThisForm.IsPaneOneHidden )
{
// implementation here...
}
}

IsPaneTwoHidden method (WinStudio scripts)
Applies to
IWSForm interface
Description
Returns a Boolean value which indicates whether splitter pane two is currently hidden
Syntax
object.IsPaneTwoHidden( )

Infor Mongoose Application and Form Development | 582

Reference

Part

Description

object

Required. A reference to a valid form object.

Remarks
A return value of:
•
•

TRUE indicates that the pane is hidden.
FALSE indicates that the pane is visible.

This form script API can be used along with the standard events to have more control over the splitter
panes.
Example
public void HandlePaneState()
{
if ( ThisForm.IsPaneTwoHidden )
{
// implementation here...
}
}

IsPropertyBoundByComponent method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether a specified property of a collection is bound by a form
component.
Syntax
object.IsPropertyBoundByComponent( string, Boolean )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of a property of a collection.

Infor Mongoose Application and Form Development | 583

Reference

Boolean

Required. Determines whether the method should ignore hidden components.
• TRUE - Ignore hidden components.
• FALSE - Do not ignore hidden components.

Remarks
A return value of:
•
•

TRUE indicates that the property is bound by a component.
FALSE indicates that the property is not bound by a component.

Example
Sub Main()
Dim i As Integer
Dim iNumEntries As Integer
Dim strProperty As String
Dim strPropList As String
i = 0
iNumEntries = ThisForm.PrimaryIDOCollection.GetNumProperties
strPropList = ""
Do While i > iNumEntries
strProperty = ThisForm.PrimaryIDOCollection.GetPropertyName(i)
If ThisForm.PrimaryIDOCollection.IsPropertyBoundByComponent(strProp
erty, False) Then
strProperty = strProperty & " is bound by a component"
Else
strProperty = strProperty & " is not bound by a component"
End If
strPropList = strPropList & strProperty & vbLf
i = i + 1
Loop
Application.ShowMessage(strPropList)
End Sub

IsPropertyIdentity method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection is the
Identity attribute that uniquely identifies the row in the application database table.

Infor Mongoose Application and Form Development | 584

Reference
Syntax
object.IsPropertyIdentity( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property is the Identity attribute in the application database table.
FALSE indicates that the object property is not the Identity attribute.

Example
Dim bIsIdentity As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyIdentity("AccountNo") Then
bIsIdentity = TRUE
End If

IsPropertyKey method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection is the Key
attribute set in the IDO.
Syntax
object.IsPropertyKey( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Infor Mongoose Application and Form Development | 585

Reference
Remarks
A return value of:
•
•

TRUE indicates that the object property is the Key attribute.
FALSE indicates that the object property is not the Key attribute.

Example
Dim bIsKey As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyKey("AccountNo") Then
bIsKey = TRUE
End If

IsPropertyLinkBy method (WinStudio scripts)
Applies To
IWSIDOCollection interface, child forms
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection object is
one of the properties specified in the LINKBY keyword in WinStudio.
Syntax
object.IsPropertyLinkBy( string )
Part

Description

object

Required. A reference to a valid IDO collection object that is a child
form.

string

Required. The name of the object property.

Remarks
In WinStudio, you use the LINKBY keyword to connect parent and child forms. Use this method in a
script that is attached to a linked child form.
A return value of:
•
•

TRUE indicates that the object property (of the child form) is used in the LINKBY keyword.
FALSE indicates that the object property (of the child form) is not used in the LINKBY keyword.

Infor Mongoose Application and Form Development | 586

Reference

Example
Dim bIsLinkBy As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyLinkBy("AccountNo") Then
bIsLinkBy = TRUE
End If

IsPropertyNullable method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection has the
Nullable attribute set in the IDO.
Syntax
object.IsPropertyNullable( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property has the Nullable attribute set in the IDO.
FALSE indicates that the object property does not have the Nullable attribute in the IDO.

Example
Dim bIsNullable As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyNullable("AccountNo") Then
bIsNullable = TRUE
End If

Infor Mongoose Application and Form Development | 587

Reference

IsPropertyNumeric method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property has the Numeric attribute set in the
IDO.
Syntax
object.IsPropertyNumeric( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property has the Numeric attribute set in the IDO.
FALSE indicates that the object property does not have the Numeric attribute in the IDO.

Example
Sub Main()
Dim bIsNumeric As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyNumeric("AccountNo") Then
bIsNumeric = True
End If
End Sub

IsPropertyNumSortedChar method (WinStudio scripts)
Applies To
IWSIDOCollection interface

Infor Mongoose Application and Form Development | 588

Reference
Definition
Returns a Boolean value indicating whether the data type of a property in a collection is
NUMSORTCHAR. Read-only.
Syntax
object.IsPropertyNumSortedChar( string )
Part

Description

object

Required. A reference to an IDO collection object.

string

Required. The name of an IDO collection property.

Remarks
A NUMSORTCHAR value is a numeric or alphanumeric string that is padded for the purpose of proper
numeric sorting. For details on how the property is padded, see the ExpandNumSortedChar on page
447 method.
Example
Sub Main()
Dim Length As Integer
Dim CustNum As String
Dim CustSeq As String
CustNum = ThisForm.Variables("CustNumV").Value
CustSeq = ThisForm.Variables("CustSeqV").Value
If ThisForm.PrimaryIDOCollection.IsPropertyNumSortedChar("CustNum")
Then
Length = ThisForm.PrimaryIDOCollection.GetPropertyLength("CustNum")
If (Length > 0 And CustNum <> "") Then
CustNum = Application.ExpandNumSortedChar(CustNum, Length)
End If
End If
End Sub

IsPropertyProtected method (WinStudio scripts)
Applies To
IWSIDOCollection interface

Infor Mongoose Application and Form Development | 589

Reference
Definition
Returns a Boolean value indicating whether an object property is read-only or is used in the LINKBY
keyword in WinStudio.
Syntax
object.IsPropertyProtected( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property is either read-only or is used in the LINKBY keyword in
WinStudio.
FALSE indicates that the object property is neither read-only nor used in the LINKBY keyword in
WinStudio.

Example
Sub Main()
Dim bIsProtected As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyProtected("AccountNo") Then
bIsProtected = True
End If
End Sub

IsPropertyQuotable method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property in a specified IDO collection requires
values to be placed inside quotation marks when they are used in a filter.
Syntax
object.IsPropertyQuotable( string )

Infor Mongoose Application and Form Development | 590

Reference

Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that values for the object property must be placed inside quotation marks when
they are used in a filter.
FALSE indicates that values for the property do not require quotation marks when they are used
in a filter.

Example
Sub Main()
Dim bIsQuotable As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyQuotable("AccountNo") Then
bIsQuotable = True
End If
End Sub

IsPropertyReadOnly method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property for a specified IDO collection has the
Read-Only attribute set in the IDO.
Syntax
object.IsPropertyReadOnly( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Infor Mongoose Application and Form Development | 591

Reference
Remarks
A return value of:
•
•

TRUE indicates that the object property has the Read-Only attribute set in the IDO.
FALSE indicates that the object property does not have the Read-Only attribute in the IDO.

Example
Sub Main()
Dim bIsReadOnly As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyReadOnly("AccountNo") Then
bIsReadOnly = True
End If
End Sub

IsPropertyRequired method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property has the Required attribute set in the
IDO.
Syntax
object.IsPropertyRequired( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property has the Required attribute set in the IDO.
FALSE indicates that the object property does not have the Required attribute in the IDO.

Infor Mongoose Application and Form Development | 592

Reference

Example
Sub Main()
Dim bIsRequired As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyRequired("AccountNo") Then
bIsRequired = True
End If
End Sub

IsPropertyUppercase method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Returns a Boolean value indicating whether an object property has the Uppercase attribute set in the
IDO.
Syntax
object.IsPropertyUppercase( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Remarks
A return value of:
•
•

TRUE indicates that the object property has the Uppercase attribute set in the IDO.
FALSE indicates that the object property does not have the Uppercase attribute in the IDO.

Example
Sub Main()
Dim bIsUppercase As Boolean
If ThisForm.PrimaryIDOCollection.IsPropertyUppercase("AccountNo") Then
bIsUppercase = True
End If
End Sub

Infor Mongoose Application and Form Development | 593

Reference

Last method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Goes to the last object in a specified IDO collection (the bottom entry) and returns a Boolean value
indicating whether the navigation was successful.
Syntax
object.Last( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the collection was navigated to the last object.
FALSE indicates that the collection was not navigated to the last object.

This method navigates objects in the collection but does not set the focus of the form to the last object.
Example
Sub Main()
Dim bIsLast As Boolean
Dim strOldEntityCode As String
bIsLast = ThisForm.PrimaryIDOCollection.Last()
If bIsLast Then
strOldEntityCode = ThisForm.PrimaryIDOCollection.GetCurrentObject
Property("EntityCode")
End If
End Sub

LastModalChildIsOrInheritsFrom method (WinStudio scripts)
Applies to
IWSForms interface

Infor Mongoose Application and Form Development | 594

Reference
Description
This method takes a single string parameter for the name of the form from which the current
LastModalChildName's form descends.
Syntax
ThisForm.LastModalChildIsOrInheritsFrom("Dialog")
where Dialog is a single string parameter for the name of the form from which the current
LastModalChildName's form descends.
Remarks
The function must take a form name string parameter, and return true if the form's LastModalChildForm
either is, or descends from (per the BaseForm property) the form named in the string parameter.
This method pulls up the WSForm object for the current form's last modal child.
This method gathers the child form's ancestors using the form's existing HierarchyMap property.
Example
ThisForm.LastModalChildIsOrInheritsFrom("WBCriticalNumberIDOSetup")

ListSourceScriptAddEntries method (WinStudio scripts)
Applies To
IWSFormComponent interface, list source objects
Definition
Inserts entries into a list.
Syntax
object.ListSourceScriptAddEntries( listEntries )
Part

Description

object

Required. A reference to a list source component object.

listEntries

Required. A list of type System.Collections.Generic.List(Of String).

Remarks
List source components include these component types:

Infor Mongoose Application and Form Development | 595

Reference
•
•
•
•

Combo boxes (ComboBox)
Drop-down lists (DropList)
List boxes (List)
Grid columns (GridColumn)

This API must be used from a global script that is referenced in the component's list source, where the
list source is of type Script.
Example
In the following example:
•
•

The first parameter is a form name.
The second parameter is the name of a component from which we are loading a list.

Putting this code in the list source for a component of type drop-down list populates the list with a form's
components.
Sub Main()
Dim bListInitialized As Integer
Dim ComponentNames As New List(Of String)
Dim FormName As String
On Error GoTo ErrorHandler
ReturnValue = "0"
bListInitialized = 0
FormName = GetParameter(0).ToString()
ComponentNames = Application.FormServer.GetFormComponentNames("Form
Name")
ThisForm.Components(GetParameter(1)).ListSourceScriptAddEntries(Compo
nentNames)
Exit Sub
ErrorHandler:
Application.ShowMessage("GetComponentList: " & Err.Description)
ReturnValue = "1"
Exit Sub
End Sub

ListSourceScriptAddListItem method (WinStudio scripts)
Applies To
IWSFormComponent interface, list source objects
Definition
Inserts entries into the list for a list source object.
Syntax
object.ListSourceScriptAddListItem( string )

Infor Mongoose Application and Form Development | 596

Reference

Part

Description

object

Required. The name of a valid component object.

string

Required. The name of the new tab-delimited list item.

Remarks
Typically, use this method in a script that functions as the list source for a component with a drop-down
list (DropList component). However, you can also use it with List, ComboBox, or GridColumn
components.
This API must be used from a global script that is referenced in the component's list source, where the
list source is of type Script.
Example
Sub Main()
ThisForm.Components( "ListComboBox" ).ListSourceScriptAddListItem(
"Item 1" )
ThisForm.Components( "ListComboBox" ).ListSourceScriptAddListItem(
"Item 2" )
ThisForm.Components( "ListComboBox" ).ListSourceScriptAddListItem(
"Item 3" )
ThisForm.Components( "ListComboBox" ).ListSourceScriptAddListItem(
"Item 4" )
ThisForm.Components( "ListComboBox" ).ListSourceScriptAddListItem(
"Item 5" )
End Sub

LockGridUpdate method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Stops the painting of a grid component.
Syntax
object.LockGridUpdate( )
Part

Description

object

Required. A reference to a valid grid component object.

Infor Mongoose Application and Form Development | 597

Reference
Remarks
The process of painting messages can slow performance when a large number of data updates is
underway. This method turns off painting. The UnlockGridUpdate method turns it back on.
Example
Sub Main()
ThisForm.Components("FormCollectionGrid").LockGridUpdate()
End Sub

MoveCurrentIndexAndRefresh method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Navigates to an object in a collection specified by row index number, makes the object current, repaints
components bound to properties in the collection, and sets the Modified State of the components.
Syntax
object.MoveCurrentIndexAndRefresh( integer, Boolean )
Part

Description

object

Required. A reference to a valid IDO collection object.

integer

Required. A row index number.
The index number of the first row in a collection is 0.

Boolean

Required. Sets the modified state of components.
• TRUE: If the destination object is marked invalid because it previously failed
a validation, marks all components bound to properties as modified (needing
validation). If the destination object is not marked invalid, marks all components
bound to properties as not modified (not needing validation). The TRUE parameter provides normal WinStudio behavior.
• FALSE: Does not change the modified state of components bound to properties.
With the FALSE parameter, the valid/invalid state of the destination object is
disregarded.

Remarks
A return value of:

Infor Mongoose Application and Form Development | 598

Reference
•
•

TRUE indicates the method executed successfully.
FALSE indicates that the method did not execute successfully.

This method does not cause the current object to be validated before it navigates to another object.
Examples
Sub Main()
Dim iRow As Integer
iRow = 0
ThisForm.CurrentIDOCollection.MoveCurrentIndexAndRefresh(iRow, True)
End Sub
Sub Main()
Dim bOK As Boolean
Dim iNewRow As Integer
Dim bSetProp As Boolean
iNewRow = ThisForm.Variables("NewRow").GetValue(Of Integer)() - 1
bSetProp = ThisForm.Variables("SetProp").GetValue(Of Boolean)()
bOK = ThisForm.PrimaryIDOCollection.MoveCurrentIndexAndRefresh(iNewRow,
bSetProp)
If Not bOK Then
'Code to handle error.
End If
End Sub

New method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Inserts a new object into a specified IDO collection and returns a Boolean value indicating whether this
operation was successful.
Syntax
object.New( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Infor Mongoose Application and Form Development | 599

Reference
Remarks
A return value of:
•
•

TRUE indicates that the new object was inserted successfully.
FALSE indicates that the new object was not inserted successfully.

Example
Sub Main()
If (ThisForm.PrimaryIDOCollection.GetNumEntries() > 1 And _
(Not ThisForm.PrimaryIDOCollection.IsCurrentObjectTheAutoIn
sertRow())) Then
ThisForm.PrimaryIDOCollection.New()
End If
End Sub

Next method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Goes to the next object in a specified IDO collection and returns a Boolean value indicating whether
this navigation was successful.
Syntax
object.Next( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•
•
•

TRUE indicates that the collection was navigated to the next object.
If there was no next object and if the New operation is enabled for the form, indicates that this
method created a new object and navigated to it.
FALSE indicates that the collection was not navigated to the next object.
If there was no next object and if the New operation is disabled for the form, indicates that this
method did not create a new object.

Infor Mongoose Application and Form Development | 600

Reference

Example
Sub Main()
Dim bIsNext As String
bIsNext = ThisForm.PrimaryIDOCollection.Next().ToString
Application.ShowMessage(bIsNext)
End Sub

NotifyDependentsToRefresh method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Refreshes an object property in a specified IDO collection.
Syntax
object.NotifyDependentsToRefresh( string )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.
Note: If the string is empty (""), all properties are refreshed.

Example
Sub Main()
Dim vEcnitemType As String
Dim bCostView As Boolean
If ThisForm.PrimaryIDOCollection.IsCurrentObjectNew Then
vEcnitemType = Left(ThisForm.PrimaryIDOCollection.GetCurrentObject
Property("EcnitemType"), 1)
Select Case vEcnitemType
Case "C"
bCostView = ThisForm.Variables("vCanStdViewCosts;").Value = "1"
Case "J"
bCostView = ThisForm.Variables("vCanJobViewCosts").Value = "1"
Case "E"
bCostView = ThisForm.Variables("vCanEstViewCosts").Value = "1"
End Select
If bCostView Then

Infor Mongoose Application and Form Development | 601

Reference

ThisForm.PrimaryIDOCollection.CurrentItem("DerMatlCostConv"). _
SetValue(ThisForm.PrimaryIDOCollection.CurrentItem.Proper
ties("MatlCostConv"))
ThisForm.PrimaryIDOCollection.NotifyDependentsToRefresh("DerMatl
CostConv")
ThisForm.PrimaryIDOCollection.CurrentItem("DerLbrCostConv"). _
SetValue(ThisForm.PrimaryIDOCollection.CurrentItem.Properties("Lbr
CostConv"))
ThisForm.PrimaryIDOCollection.NotifyDependentsToRefresh("DerLbr
CostConv")
ThisForm.PrimaryIDOCollection.CurrentItem("DerFovhdCostConv").
_
SetValue(ThisForm.PrimaryIDOCollection.CurrentItem.Proper
ties("FovhdCostConv"))
End If
End If
End Sub

PopFormPage method (WinStudio scripts)
Applies To
IWSForm interface
Definition
Removes the form page that is at the top of the form page stack and displays the next form page in
the stack.
Syntax
object.PopFormPage( )
Part

Description

object

Required. Reference to a valid form object.

Example
Sub Main()
Application.FindForm("formName").PopFormPage()
End Sub

Infor Mongoose Application and Form Development | 602

Reference

PostEvent method (WinStudio scripts)
Applies To
IWSForm interface
Definition
Generates a standard event or a customized event as the last event for the specified form.
Syntax
object.PostEvent( string )
Part

Description

object

Required. The name of a valid form object.

string

Required. The name of the event.

Example
The following script generates a form event to run an appropriate report based on a selection.
Sub Main()
Dim Selection As String
On Error GoTo ErrorHandler
Application.DiagnosticsLog("entering script")
ReturnValue = "1"
Selection = GetParameter(0)
Application.DiagnosticsLog("Selection= " & Selection)
Select Case Selection
Case "1"
ThisForm.PostEvent("ReportByBank")
Case "2"
ThisForm.PostEvent("ReportByCustomer")
Case "3"
ThisForm.PostEvent("ReportByAccountSet")
Case Else
GoTo ErrorExit
End Select
GoTo NormalExit
NormalExit:
Application.DiagnosticsLog("exiting script normally")
ReturnValue = "0"
Exit Sub
ErrorExit:
Application.DiagnosticsLog("exiting script via ErrorExit")
ReturnValue = "1"
Exit Sub
ErrorHandler:
Application.ShowMessage(Err.Description)

Infor Mongoose Application and Form Development | 603

Reference

ReturnValue = "1"
Exit Sub
End Sub

Print method (WinStudio scripts)
Applies to
Document (file) operations
Description
This string is used to print an existing IDM document or any file content using IDM Enterprise Printer.
Syntax
To print existing IDM document:
bool Print( string serverName, string logicalFolderName, string printSpec,
out string errorMessage );
To print any file content using IDM Enterprise Printer:
bool Print( string serverName, string logicalFolderName, byte[] fileContent,
string printSpec, out string errorMessage );
where:
•
•
•
•
•

serverName is the name of the IDM file server.
logicalFolderName is the name of the logical folder on the IDM file server.
fileContent are the contents of the file to print.
printSpec is the name of the file to print.
errorMessage is the error message to be returned if the system fail to print the file.

Previous method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Goes to the previous object in a specified IDO collection and returns a Boolean value indicating whether
this navigation was successful.

Infor Mongoose Application and Form Development | 604

Reference
Syntax
object.Previous( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the collection was navigated to the previous object.
FALSE indicates that the collection was not navigated to the previous object.

This method navigates objects in the collection but does not set the focus of the form to the previous
object.
Example
Sub Main()
Dim bIsPrevious As String
bIsPrevious = ThisForm.PrimaryIDOCollection.Previous().ToString
Application.ShowMessage(bIsPrevious)
End Sub

Refresh method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Refreshes a specified IDO collection, makes the first object in the collection the current object, and
returns a Boolean value indicating whether the operation was successful.
Syntax
object.Refresh( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Infor Mongoose Application and Form Development | 605

Reference
Remarks
A return value of:
•
•

TRUE indicates that the collection was refreshed.
FALSE indicates that the collection was not refreshed.

If the collection contains new or altered objects that you want to preserve, save the objects before the
collection is refreshed. If changes to objects in the collection are pending and have not been saved,
they are lost when the collection is refreshed.
Example
Sub Main()
Dim bValue As Boolean
bValue = ThisForm.PrimaryIDOCollection.Refresh()
End Sub

RefreshCurrentObject method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Refreshes the current object in a specified IDO collection and returns a Boolean value indicating whether
the operation was successful.
Syntax
object.RefreshCurrentObject( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the current object was refreshed.
FALSE indicates that the current object was not refreshed.

If the current object is new or altered and you want to preserve the changes, save the object before it
is refreshed. If changes to the current object are pending and have not been saved, they are lost when
the object is refreshed.

Infor Mongoose Application and Form Development | 606

Reference

Example
Sub Main()
Dim bValue As Boolean
bValue = ThisForm.PrimaryIDOCollection.RefreshCurrentObject()
End Sub

RefreshKeepCurIndex method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Refreshes a specified IDO collection, sets as the current object the object that was current before the
refresh, and returns a Boolean value indicating whether the operation was successful.
Syntax
object.RefreshKeepCurIndex( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the collection was refreshed.
FALSE indicates that the collection was not refreshed.

If the collection contains new or altered objects that you want to preserve, save the objects before the
collection is refreshed. If changes to objects in the collection are pending and have not been saved,
they are lost when the collection is refreshed.
Example
Sub Main()
Dim bValue As Boolean
bValue = ThisForm.PrimaryIDOCollection.RefreshKeepCurIndex()
End Sub

Infor Mongoose Application and Form Development | 607

Reference

RemoveEntries method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Removes specified objects (entries) from a collection and resets the row index numbers.
Syntax
object.RemoveEntries( int1, int2 )
Part

Description

object

Required. A reference to a valid IDO collection object.

int1

Required. An integer indicating the row index number of the first object in the
collection to remove.
Row index numbers are zero-based.

int2

Required. An integer indicating the number of objects to remove.
To remove all objects from index number int1 through the end of the collection,
set the value to -1.

Remarks
If int1 is set to an invalid row index, such as -1, this method does nothing.
Example
'Remove 5 rows from the collection, starting with the second row.
ThisForm.PrimaryIDOCollection.RemoveEntries(1, 5)
'Remove all but the first 3 rows.
ThisForm.PrimaryIDOCollection.RemoveEntries(3, -1)
' Remove all records.
ThisForm.PrimaryIDOCollection.RemoveEntries(0, -1)

RemoveNotifyIcon method (WinStudio scripts)
Applies To
IWSApplication interface

Infor Mongoose Application and Form Development | 608

Reference
Definition
Removes the Notify icon related to a WinStudio object that supports notification icons.
Syntax
Application.RemoveNotifyIcon( string )
Part

Description

string

Required. Name of the form that supports notification icons and from which you
want to remove the Notify icon.
Note: Currently, the only form that supports notification icons is the Inbox form.

Example
Application.RemoveNotifyIcon("Inbox")

RepaintWindow method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Repaints the specified component.
Syntax
object.RepaintWindow( )
Part

Description

object

Required. A reference to a valid component object.

Example
Sub Main()
ThisForm.Components("grid1").RepaintWindow()
End Sub

Infor Mongoose Application and Form Development | 609

Reference

ResetCollectionModified method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Resets a modified IDO collection to an unmodified status.
Syntax
object.ResetCollectionModified( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Example
Sub Main()
If ThisForm.Components("AccountNoGridCol").Text = "AA-10000" And _
ThisForm.PrimaryIDOCollection.IsCurrentObjectModified() Then
ThisForm.PrimaryIDOCollection.ResetCollectionModified()
End If
End Sub

ResetListBoxContent method (WinStudio scripts)
Applies To
IWSFormComponent interface, List component type objects
Definition
Resets a list box, emptying its contents.
Syntax
object.ResetListBoxContent( )
Part

Description

object

Required. A reference to a list box (List type component).

Infor Mongoose Application and Form Development | 610

Reference

Example
Sub Main()
Dim oListBox As IWSFormComponent
Dim iCurrentRow As Integer
oListBox = ThisForm.Components("list1")
'Check to verify that a list item is selected.
'If not, display a message and exit.
iCurrentRow = oListBox.GetListBoxCurSel
If iCurrentRow = -1 Then
Application.ShowMessage("No list item selected!")
Exit Sub
End If
'Display list box properties.
Application.ShowMessage("Number of items: " & oListBox.GetListBox
Count().ToString() _
& vbLf & "Current selection: " & (iCurrentRow + 1).ToString() & vbLf
& _
"Text: " & oListBox.GetListBoxText(iCurrentRow))
oListBox.ResetListBoxContent()
End Sub

Resize method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Sets the coordinates and dimensions of a component.
Syntax
object.Resize( left, top, height, width )
Part

Description

object

Required. A reference to a component object.

left

Required. A Double that sets the distance from the left edge of the component
to the left edge of the WinStudio workspace.

top

Required. A Double that sets the distance from the top of the component to
the top of the WinStudio workspace.

height

Required. A Double that sets the height of the component.

width

Required. A Double that sets the width of the component.

Infor Mongoose Application and Form Development | 611

Reference
Remarks
The parameters are measured in character units.
Example
Sub Main()
ThisForm.Components("grid1").Resize(23, 26, 10, 60)
ThisForm.Components("grid1").RepaintWindow()
End Sub

Related Topics
About character units on page 146
CharHeight property (WinStudio scripts) on page 702
CharWidth property (WinStudio scripts) on page 703
Height property (WinStudio scripts) on page 732
Left property (WinStudio scripts) on page 744
Top property (WinStudio scripts) on page 773
Width property (WinStudio scripts) on page 783

RunForm method (WinStudio scripts)
Applies To
IWSApplication interface
Definition
Initiates a call to run a specified form and returns a Boolean value indicating whether the call was
successful.
Run Syntax
Application.RunForm( string )
Part

Description

string

Required. The name of the form to run.

Infor Mongoose Application and Form Development | 612

Reference
Remarks
The return value indicates only whether the call to the form was successful, not whether the system
subsequently opened the form successfully. If, for example, a user is not authorized to open a form,
this method still considers the call successful, even though the form is not actually opened.
Example
Sub Main()
Application.RunForm("myForm")
End Sub

Save method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Saves a specified IDO collection and returns a Boolean value indicating whether the Save operation
was successful.
Syntax
object.Save( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the collection was saved successfully.
FALSE indicates that the collection not saved successfully.

Example
Sub Main()
Dim bValue As Boolean
bValue = ThisForm.PrimaryIDOCollection.Save()
End Sub

Infor Mongoose Application and Form Development | 613

Reference

SaveCurrent method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Saves the current object in a collection, and returns a Boolean value indicating whether the object was
saved successfully.
Syntax
object.SaveCurrent( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the current object was saved successfully.
FALSE indicates that the object was not saved successfully.

Example
Sub Main()
ThisForm.PrimaryIDOCollection.SaveCurrent()
End Sub

SaveCurrentDeferStateUpdate method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Saves the current object in a collection but does not perform subsequent operations that WinStudio
normally performs after a save (see "Remarks" below). Returns a Boolean value indicating whether
the object was saved successfully.

Infor Mongoose Application and Form Development | 614

Reference
Syntax
object.SaveCurrentDeferStateUpdate( )
Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that the current object was saved successfully.
FALSE indicates that the object was not saved successfully.

This method is designed to be used in conjunction with the UpdateCurrentStatePostSave method. The
operation of the two methods on the current object in a collection is similar to the operation of the
SaveDeferStateUpdate method and the UpdateStatesPostSave method on all objects in a collection.
Example
Sub Main()
Dim bSuccess As Boolean
Dim ReturnValue As String
bSuccess = ThisForm.CurrentIDOCollection.SaveCurrentDeferStateUpdate()
If (bSuccess) Then
End If
If (bSuccess) Then
ThisForm.CurrentIDOCollection.UpdateCurrentStatePostSave()
Else
End If
ReturnValue = "-1"
End Sub

SaveDeferStateUpdate method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Saves objects in a collection but does not perform subsequent operations that Windows client normally
performs after a save. Returns a Boolean value indicating whether the objects were saved successfully.
Syntax
object.SaveDeferStateUpdate( )
Infor Mongoose Application and Form Development | 615

Reference

Part

Description

object

Required. A reference to a valid IDO collection object.

Remarks
A return value of:
•
•

TRUE indicates that objects in the collection were saved successfully.
FALSE indicates that the objects were not saved successfully.

After a Save operation, Windows client normally performs the following tasks:
•
•
•
•

It deletes any objects in the collection that were marked for deletion.
It refreshes all modified objects and new objects and updates components bound to the refreshed
data.
It resets the status of objects in the collection and of each individual property value as well as the
status of components bound to the properties:
• The New state is set to "not new."
• The Modified state is set to "not modified."
• The Save button is disabled.
• All property values are marked as valid.

This method saves objects in a collection but does not perform the three subsequent tasks. In some
cases, you might need to perform other actions after a Save has occurred but before the three tasks
are performed. If your actions fail, you can then rollback the effects of the Save operation. This method
allows you to do this.
This method is designed to be used in conjunction with the UpdateStatesPostSave method, which
performs the three operations that Windows client normally performs following a Save action.
Example
Sub Main()
' This sample shows a scheme for an event handler
' that replaces the default implementation of the StdFormSave
' event.
Dim bSuccess As Boolean
Dim ReturnValue As String
bSuccess = ThisForm.CurrentIDOCollection.SaveDeferStateUpdate()
If (bSuccess) Then
' Some custom processing that returns bSuccess
' for successful completion -- that is, a method call.
End If
' Either complete or rollback the transaction based on bSuccess.
If (bSuccess) Then
ThisForm.CurrentIDOCollection.UpdateStatesPostSave()
Else
' Process to rollback the transaction.
End If
' Cancel the default processing.

Infor Mongoose Application and Form Development | 616

Reference

ReturnValue = "-1"
End Sub

SaveGraph method (WinStudio scripts)
Applies To
IWSFormComponent interface, graph objects
Definition
Creates a file that contains user settings of properties for a graph component and saves the file to a
local disk. Returns a Boolean value indicating whether the method was successfully applied.
Syntax
object.SaveGraph( )
Part

Description

object

Required. A reference to a valid graph component object.

Remarks
A return value of:
•
•

TRUE indicates that the SaveGraph method was successfully applied to a graph component.
FALSE indicates that the SaveGraph method was applied to a component that is not a graph
component or that the method was unable to access the graph component.

Graph components retrieve properties from the .gsp file when a form opens.
The file name consists of the Windows client user ID from the current session logon and the file extension
.gsp (for example, MyUserID.gsp). By default, Windows client saves the file in the client directory from
which WinStudio.exe was opened (for example, C:\Program Files\Infor\application).
Example
Sub Main()
' Save the settings for a graph component named SalesGraph
ThisForm.Components("SalesGraph").SaveGraph()
End Sub

Infor Mongoose Application and Form Development | 617

Reference

SetCurrentObject method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Makes an object in an IDO collection, specified by index number, the current object and returns a
Boolean value indicating whether the operation was successful.
Syntax
object.SetCurrentObject( long )
Part

Description

object

Required. A reference to a valid IDO collection object.

long

Required. A long integer indicating the index number of an object in the
collection.

Remarks
A return value of:
•
•

TRUE indicates that the current object's index number is set.
FALSE indicates that the current object's index number was not set.

This method makes a specified object current without the associated processing that would normally
occur, such as updating components bound to data in the collection.

Sub Main()
Dim i As Integer
If i < ThisForm.PrimaryIDOCollection.GetNumEntries() - 1 And _
ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Entity
Code", i) And _
ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Account
No", i) And _
Not ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Pay
mentType", i) Then
ThisForm.PrimaryIDOCollection.SetCurrentObject(i)
ThisForm.SetFocus("PaymentTypeGridCol")
End If
End Sub

Infor Mongoose Application and Form Development | 618

Reference

SetCurrentObjectHard method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Makes an object in an IDO collection, specified by index number, the current object and returns a
Boolean value indicating whether the operation was successful.
Syntax
object.SetCurrentObject( long )
Part

Description

object

Required. A reference to a valid IDO collection object.

long

Required. A long integer indicating the index number of an object in the
collection.

Remarks
A return value of:
•
•

TRUE indicates that the current object's index number is set.
FALSE indicates that the current object's index number was not set.

This method makes a specified object current with all the associated processing that would normally
occur, such as updating components bound to data in the collection.

Sub Main()
Dim i As Integer
If i < ThisForm.PrimaryIDOCollection.GetNumEntries() - 1 And _
ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Entity
Code", i) And _
ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Account
No", i) And _
Not ThisForm.PrimaryIDOCollection.IsObjectPropertyModified("Pay
mentType", i) Then
ThisForm.PrimaryIDOCollection.SetCurrentObjectHard(i)
ThisForm.SetFocus("PaymentTypeGridCol")
End If
End Sub

Infor Mongoose Application and Form Development | 619

Reference

SetCurrentObjectModified method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the current object in the specified IDO collection to "modified" based on the specified criteria.
Syntax
object.SetCurrentObjectModified( Boolean )
Part

Description

object

Required. A reference to a valid IDO collection object.

Boolean

Required. Determines whether the object is marked as "modified":
• TRUE: The object is marked as "modified".
• FALSE: The object is not marked as "modified".

Example
Sub Main()
If ThisForm.PrimaryIDOCollection.GetCurrentObjectProperty("SelectFlag")
= "1" Then
ThisForm.PrimaryIDOCollection.SetCurrentObjectModified(False)
End If
End Sub

SetCurrentObjectProperty method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the value of a specified property of the current object in an IDO collection.
Syntax
object.SetCurrentObjectProperty( string1, string2 )

Infor Mongoose Application and Form Development | 620

Reference

Part

Description

object

Required. A reference to a valid IDO collection object.

string1

Required. The name of the current object property.

string2

Required. The value of the current object property.

Remarks
This method does not mark the property as "modified" or notify dependents (such as components
bound to the property) to refresh.
Note: The difference between this and the SetCurrentObjectPropertyInternal method is that this one
sets the value to an interpretable value that might, for instance, be localized, whereas the
SetCurrentObjectPropertyInternal method sets it to a system internal value.
This is particularly applicable to date/time values. Localized date values can be displayed, for example,
as dd/mm/yyyy, mm/dd/yyyy, or mm/dd/yy. When such a value is used, the system tries to interpret
the appropriate display value for the property. A system internal value, however, uses the format
yyyy/mm/dd, and it never changes. So, to use the internal value, your input value must conform to this
format.

SetCurrentObjectPropertyInternal method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the value of a specified property of the current object in an IDO collection to an internal system
value.
Syntax
object.SetCurrentObjectPropertyInternal( string1, string2 )
Part

Description

object

Required. A reference to a valid IDO collection object.

string1

Required. The name of the current object property.

string2

Required. The value of the current object property.

Remarks
This method does not mark the property as "modified" or notify dependents (such as components
bound to the property) to refresh.

Infor Mongoose Application and Form Development | 621

Reference
Note: The difference between this and the SetCurrentObjectProperty method is that this one sets the
value to a system internal value, whereas the SetCurrentObjectProperty method sets it to an interpretable
value that might, for instance, be localized.
This is particularly applicable to date/time values. Localized date values can be displayed, for example,
as dd/mm/yyyy, mm/dd/yyyy, or mm/dd/yy. When such a value is used, the system tries to interpret
the appropriate display value for the property. A system internal value, however, uses the format
yyyy/mm/dd, and it never changes. So, to use the internal value, your input value must conform to this
format.

SetCurrentObjectPropertyModified method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the current object property in the specified IDO collection to "modified".
Syntax
object.SetCurrentObjectPropertyModified( string, Boolean )
Part

Description

object

Required. A reference to a valid IDO collection object.

string

Required. The name of the object property.

Boolean

Required. Determines whether the object is marked as "modified":
• TRUE - The object is marked as "modified".
• FALSE - The object is not marked as "modified".

Example
Sub Main()
Dim ColCache As IWSIDOCollection
Dim RevisedQtyDue As Double
Dim QtyPerAssembly As Double
Dim RemainingQtyDue As Double
ColCache = ThisForm.PrimaryIDOCollection
RemainingQtyDue = CDbl(ColCache.GetCurrentObjectProperty("RemainingQty
Due"))
QtyPerAssembly = CDbl(ColCache.GetCurrentObjectProperty("QtyPerAssem
bly"))
If QtyPerAssembly > 0.0 And RemainingQtyDue > 0.0 Then
RevisedQtyDue = QtyPerAssembly * RemainingQtyDue
ColCache.SetCurrentObjectProperty("RevisedQtyDue", RevisedQty

Infor Mongoose Application and Form Development | 622

Reference

Due.ToString)
ColCache.SetCurrentObjectPropertyModified("RevisedQtyDue", True)
ColCache.NotifyDependentsToRefresh("RevisedQtyDue")
ThisForm.Components("RevisedQtyDueSubGridCol").Text = RevisedQty
Due.ToString
End If
End Sub

SetCurrentObjectPropertyPlusModifyRefresh method (WinStudio
scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the value for a property of the current object, marks the property modified, marks the object
modified, and notifies any dependents, such as components bound to the property, to refresh.
Syntax
object.SetCurrentObjectPropertyPlusModifyRefresh( string1, string2 )
Part

Description

object

Required. A reference to a valid IDO collection object.

string1

Required. The name of a property of the current object.

string2

Required. The value of the property named by string1.

Remarks
For information about other methods and properties that you can use to set the value of a property or
perform one or more of the other operations performed by this method, see the Related Topics.
This method is equivalent to executing the following four low-level methods:
• SetCurrentObjectProperty
• SetCurrentObjectPropertyModified
• SetCurrentObjectModified
• NotifyDependentsToRefresh
Note: The difference between this and the SetCurrentObjectPropertyPlusModifyRefreshInternal method
is that this one sets the value to an interpretable value that might, for instance, be localized, whereas
the SetCurrentObjectPropertyPlusModifyRefreshInternal method sets it to a system internal value.

Infor Mongoose Application and Form Development | 623

Reference
This is particularly applicable to date/time values. Localized date values can be displayed, for example,
as dd/mm/yyyy, mm/dd/yyyy, or mm/dd/yy. When such a value is used, the system tries to interpret
the appropriate display value for the property. A system internal value, however, uses the format
yyyy/mm/dd, and it never changes. So, to use the internal value, your input value must conform to this
format.

SetCurrentObjectPropertyPlusModifyRefreshInternal method
(WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Sets the internal value for a property of the current object, marks the property modified, marks the
object modified, and notifies any dependents, such as components bound to the property, to refresh.
Syntax
object.SetCurrentObjectPropertyPlusModifyRefreshInternal( string1, string2 )
Part

Description

object

Required. A reference to a valid IDO collection object.

string1

Required. The name of a property of the current object.

string2

Required. The internal value of the property named by string1.

Remarks
For information about other methods and properties that you can use to set the value of a property or
perform one or more of the other operations performed by this method, see the Related Topics.
This method is equivalent to executing the following four low-level methods:
• SetCurrentObjectPropertyInternal
• SetCurrentObjectPropertyModified
• SetCurrentObjectModified
• NotifyDependentsToRefresh
Note: The difference between this and the SetCurrentObjectPropertyPlusModifyRefresh method is
that this one sets the value to a system internal value, whereas the
SetCurrentObjectPropertyPlusModifyRefresh method sets it to an interpretable value that might, for
instance, be localized.
This is particularly applicable to date/time values. Localized date values can be displayed, for example,
as dd/mm/yyyy, mm/dd/yyyy, or mm/dd/yy. When such a value is used, the system tries to interpret
the appropriate display value for the property. A system internal value, however, uses the format

Infor Mongoose Application and Form Development | 624

Reference
yyyy/mm/dd, and it never changes. So, to use the internal value, your input value must conform to this
format.

SetFocus method (WinStudio scripts)
Applies To
IWSForm interface
Definition
Changes the focus on the form to a specified component.
Syntax
object.SetFocus( string )
Part

Description

object

Required. The name of a valid form object.

string

Required. The name of the component to receive the focus.

Remarks
Generally, you should avoid using this method to work around tab-order problems related to dynamic
enabling/disabling of components. If a form is later modified, this method can cause unexpected behavior
that is difficult to diagnose.
Example
Sub Main()
Application.FindForm("formName").SetFocus("componentName")
End Sub

SetGridCurrentCell method (WinStudio scripts)
Applies To
IWSFormComponent interface, unbound grid components

Infor Mongoose Application and Form Development | 625

Reference
Definition
Changes the focus of the form to the specified row and column in an unbound grid component.
Syntax
object.SetGridCurrentCell( integer1, integer2 )
Part

Description

object

Required. A reference to a valid grid component.

integer1

Required. Indicates the row number.
Row 0 is the row of column titles at the top of the grid component. Row 1 is
the first data row.

integer2

Required. Indicates the column number.
Column 0 is the column of row numbers on the grid component. Column 1
is the first data column.

Example
Sub Main()
Dim iRow As Integer
Dim iCol As Integer
iRow = 1
iCol = 2
ThisForm.Components("MrpPlanviewTempsGrid").SetGridCurrentCell(iRow,
iCol)
End Sub

SetGridRowColColor method (WinStudio scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Assigns background and foreground colors to specified grid cells. This method uses integers to specify
the cell locations.
Syntax
object.SetGridRowColColor( integer1, integer2, string1, string2 )

Infor Mongoose Application and Form Development | 626

Reference

Part

Description

object

Required. A reference to a grid object.

integer1

Required. Indicates the row number.
Row numbering is 1-based.

integer2

Required. Indicates the column number.
Column numbering is 1-based.
If you use 0 (zero) for this value, the colors are applied to the entire row.

string1

Required. A string containing the RGB code value of the color to be used
for the background.

string2

Required. A string containing the RGB code value of the color to be used
for the foreground.

Remarks
The background color is used for the background color of the designated cells. The term "foreground
color" typically refers to the font/text color.
The RGB strings consist of a series of three comma-separated integers, each in the range 0-255. For
example, the RGB code for a deep purple color might be rendered as (158,44,186).
The use of an empty (null) string ("") tells the system to use the default setting for that value.
Example
'The following code sets the background color to red
'and resets the foreground color to the current WinStudio default.
ThisForm.Components("FormCollectionGrid").SetGridRowColColor(5, 1,
"255,0,0", "")

SetGridRowColColorByColumnName Method (WinStudio Scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Assigns background and foreground colors to specified grid cells. This method uses an integer to
identify the row number and the column name to identify the column location.
For more information about background and foreground colors, see the "Remarks" section.

Infor Mongoose Application and Form Development | 627

Reference
Syntax
object.SetGridRowColColor( integer, string1, string2, string3 )
Part

Description

object

Required. A reference to a grid object.

integer

Required. Indicates the row number.
Row numbering is 1-based.

string1

Required. The name of the column component, as it is known in the WinStudio form metadata.

string2

Required. A string containing the RGB code value of the color to be used
for the background.

string3

Required. A string containing the RGB code value of the color to be used
for the foreground.

Remarks
The background color is used for the background color of the designated cells. The term "foreground
color" typically refers to the font/text color.
The RGB strings consist of a series of three comma-separated integers, each in the range 0-255. For
example, the RGB code for a deep purple color might be rendered as (158,44,186).
The use of an empty (null) string ("") tells the system to use the default setting for that value.
Example

Sub Main()
'The following code sets the background color to red
'and resets the foreground color to the current WinStudio default.
ThisForm.Components("grid1").SetGridRowColColorByColumnName(5, "grid
Column1", "255,0,0", "")
End Sub

SetGridRowColFont Method (WinStudio Scripts)
Applies To
IWSFormComponent interface, grid objects

Infor Mongoose Application and Form Development | 628

Reference
Definition
Assigns a font face and size to be used in a specified cell, and automatically adjusts the height of the
row, if necessary, to accommodate the assigned font.
Syntax
object.SetGridRowColFont( integer1, integer2, string, Boolean )
Part

Description

object

Required. A reference to a grid component.

integer1

Required. Indicates the row number.
Row numbering is 1-based.

integer2

Required. Indicates the column number.
Column numbering is 1-based.
If you use 0 (zero) for this value, the colors are applied to the entire row.

string

Required. Font descriptor string consisting of fourteen comma-separated values:
Size,0,0,0,Weight,Italic,Underline,Strikethrough,0,0,0,0,0,FontFamily
For more information about these values, see "Settings" below.

Boolean

Required. Determines whether the system should adjust the height of the row,
if necessary, to accommodate the font face and size being assigned.
• TRUE: The system is to adjust the row height if necessary.
• FALSE: The system should not adjust the row height.
Unless you have specific reasons for doing otherwise, you should use a value
of TRUE.

Settings
Setting

Description

Size

A decimal value representing the point size.

Weight

Range from 0 (non-bold) to 700 (bold).

Italic

0 = Normal text.
1 = Italicized text.

Underline

0 = Text not underlined.
1 = Text underlined.

Strikethrough

0 = Normal text.
1 = Text struck through.

FontFamily

Name of the font to be used.

Infor Mongoose Application and Form Development | 629

Reference

Example

Sub Main()
'Sets the font for the specified cell to 12-point bold Times New Roman.
Dim fontDescriptor As String = "12,0,0,0,700,0,0,0,0,0,0,0,0,Times New
Roman"
ThisForm.Components("grid").SetGridRowColFont(3, 1, fontDescriptor,
True)
End Sub

SetGridRowColFontByColumnName Method (WinStudio Scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Assigns a font face and size to be used in a specified cell, and automatically adjusts the height of the
row, if necessary, to accommodate the assigned font.
Syntax
object.SetGridRowColFont( integer, string1, string2, Boolean )
Part

Description

object

Required. A reference to a grid component.

integer

Required. Indicates the row number.
Row numbering is 1-based.

string1

Required. The name of the column component, as it is known in the WinStudio
form metadata.

string2

Required. Font descriptor string consisting of fourteen comma-separated values:
Size,0,0,0,Weight,Italic,Underline,Strikethrough,0,0,0,0,0,FontFamily
For more information about these values, see "Settings" below.

Boolean

Required. Determines whether the system should adjust the height of the row,
if necessary, to accommodate the font face and size being assigned.
• TRUE: The system is to adjust the row height if necessary.
• FALSE: The system should not adjust the row height.
Unless you have specific reasons for doing otherwise, you should use a value
of TRUE.

Infor Mongoose Application and Form Development | 630

Reference
Settings
Setting

Description

Size

A decimal value representing the point size.

Weight

Range from 0 (non-bold) to 700 (bold).

Italic

0 = Normal text.
1 = Italicized text.

Underline

0 = Text not underlined.
1 = Text underlined.

Strikethrough

0 = Normal text.
1 = Text struck through.

FontFamily

Name of the font to be used.

Example

Sub Main()
'Sets the font for specified cells to 12-point bold Times New Roman.
Dim fontDescriptor As String = "12,0,0,0,700,0,0,0,0,0,0,0,0,Times New
Roman"
ThisForm.Components("grid").SetGridRowColFontByColumnName(4, "custName",
fontDescriptor, True)
End Sub

SetGridRowColImage Method (WinStudio Scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Assigns a specified image file to display in a grid cell. This method uses integers to specify the cell
location.
Syntax
object.SetGridRowColImage( integer1, integer2, string )
Part

Description

Infor Mongoose Application and Form Development | 631

Reference

object

Required. A reference to a grid object.

integer1

Required. Indicates the row number.
Row numbering is 1-based.
If you use 0 (zero) for this value, the image displays in the header row.

integer2

Required. Indicates the column number.
Column numbering is 1-based.

string

Required. The name of an image file found in the WinStudio executable directory.
Note: Supported image file types include *.bmp, *.emf, *.exif, *.gif, *.ico,
*.jpg, and *.png.

Remarks
The image file must reside in the same directory as winstudio.exe.
The height of the cell in which the image appears adjusts automatically to fit the height dimension of
the image file. The width of the column can be preset or manually adjusted to accommodate the width
of the image.
To clear an image from a particular cell, use this method and pass an empty string (" ") for the image
file string.
Example

Sub Main()
'The following code assigns the graphic file
'to the header row cell in the first column.
ThisForm.Components("FormCollectionGrid").SetGridRowColImage(0, 1,
"MyImage.png")
End Sub

SetGridRowColImageByColumnName Method (WinStudio Scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Assigns a specified image file to display in a grid cell. This method uses an integer to identify the row
number and the column name to identify the column location.

Infor Mongoose Application and Form Development | 632

Reference
Syntax
object.SetGridRowColImageByColumnName( integer, string1, string2 )
Part

Description

object

Required. A reference to a grid object.

integer

Required. Indicates the row number.
Row numbering is 1-based.
If you use 0 (zero) for this value, the image displays in the header row.

string1

Required. The name of the column component, as it is known in the WinStudio
form metadata.

string2

Required. The name of an image file found in the WinStudio executable directory.
Note: Supported image file types include *.bmp, *.emf, *.exif, *.gif, *.ico, *.jpg,
*.png, and *.svg.

Remarks
The image file must reside in the same directory as winstudio.exe.
The height of the cell in which the image appears adjusts automatically to fit the height dimension of
the image file. The width of the column can be preset or manually adjusted to accommodate the width
of the image.
To clear an image from a particular cell, use this method and pass an empty string (" ") for the image
file string.
Example

Sub Main()
'The following code assigns the graphic file
'to the third row cell in the column named "CompanyLogo".
ThisForm.Components("FormCollectionGrid"). _
SetGridRowColImageByColumnName(3, "CompanyLogo", "MyImage.png")
End Sub

SetGridTopRow method (WinStudio scripts)
Applies To
IWSFormComponent interface, grid objects

Infor Mongoose Application and Form Development | 633

Reference
Definition
Sets a row in the grid to be the top row.
Syntax
object.SetGridTopRow( integer )
Part

Description

object

Required. A reference to a grid object.

integer

Required. The number that identifies which row is to be the top row.

Example
Sub Main()
Dim iRow As Integer
iRow = 2
ThisForm.Components( "FormCollectionGrid" ).SetGridTopRow(iRow)
End Sub

SetGridValue method (WinStudio scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Sets the value of a cell in a grid component.
Syntax
object.SetGridValue( integer1, integer2, string )
Part

Description

object

Required. A reference to a grid object.

integer1

Required. The row number.

integer2

Required. The column number.

string

Required. The value being set.

Infor Mongoose Application and Form Development | 634

Reference

Example
Sub Main()
Dim iRow As Integer
Dim iCol As Integer
iRow = 5
iCol = 1
ThisForm.Components("MrpPlanviewTempsGrid").SetGridValue(iRow, iCol,
"1")
iCol = 2
ThisForm.Components("MrpPlanviewTempsGrid").SetGridValue(iRow, iCol,
"02/18/79")
End Sub

SetGridValueByColumnName method (WinStudio scripts)
Applies To
IWSFormComponent interface, grid objects
Definition
Sets the value of a row/column in a grid component.
Syntax
object.SetGridValueByColumnName( integer, string1, string2 )
Part

Description

object

Required. A reference to a grid object.

integer

Required. The row number.

string1

Required. The column name.

string2

Required. The value being set.

Example
Sub Main()
Dim iRow As Integer
iRow = 1
ThisForm.Components("FormCollectionGrid"). _
SetGridValueByColumnName(iRow, "OutputFormatDescGridCol", "New Val

Infor Mongoose Application and Form Development | 635
