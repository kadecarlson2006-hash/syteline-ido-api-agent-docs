---
name: syteline-global-objects
description: "Global objects - component classes and property class extensions"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

SetCurrentObjectPropertyInternal on page 621
SetCurrentObjectPropertyModified on page 622
SetCurrentObjectPropertyPlusModifyRefresh on page 623
SetCurrentObjectPropertyPlusModifyRefreshInternal on page 624
SetObjectDeleted on page 639
SetObjectForceUpdate on page 640
SetObjectInvalid on page 641
SetObjectModified on page 642
SetObjectNew on page 643
SetObjectProperty on page 644
SetObjectPropertyInternal on page 644
SetObjectPropertyModified on page 645
SetObjectPropertyPlusModifyRefresh on page 646
SetObjectPropertyPlusModifyRefreshInternal on page 647
Sort on page 659
UpdateCurrentStatePostSave on page 689
UpdateStatesPostSave on page 690

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

AutoInsertEnabled on page 697
ClassNotesExist on page 704
CopyEnabled on page 709
CurrentItem on page 711
CurrentRow on page 713
DeleteEnabled on page 718
Enabled on page 722
Filter on page 723
FilterEnabled on page 724
GraphEnabled on page 730
Item on page 738

Infor Mongoose Application and Form Development | 415

Reference
•
•
•
•
•
•
•

Items on page 739
NavigateEnabled on page 750
NewEnabled on page 752
NotesEnabled on page 755
RecordCapOverride on page 762
RefreshEnabled on page 763
SaveEnabled on page 766

Methods
ApplySavedFilter method (WinStudio scripts)
Applies To
IWSForm interface
Definition
Applies a saved query filter to a specified form and returns a Boolean value indicating whether the filter
was successfully applied.
Syntax
object.ApplySavedFilter( string )
Part

Description

object

Required. Reference to a valid form object.

string

Required. The name of a saved filter.

Remarks
Query filters must have been previously saved in the context of a query form for this method to work.
A return value of:
•
•

TRUE indicates that the saved filter was successfully applied.
FALSE indicates that the saved filter was not successfully applied.

Example
Sub Main()
Dim Successful As Boolean

Infor Mongoose Application and Form Development | 416

Reference

Successful = ThisForm.ApplySavedFilter("myFilter")
Application.ShowMessage(Successful.ToString)
End Sub

ApplyThemeClass method (WinStudio scripts)
Applies to
IWSForm interface
Description
This method is used to apply a specific theme class to a component.
Syntax
ApplyThemeClass(string themeClassName)
where themeClassName is a single string parameter for the name of the theme class to be applied.
Example
ApplyThemeClass(string ThemeClass)

BuildJSONFileSpec method (WinStudio scripts)
Applies to
Description
This string can be used to generate file specification along with other properties. This string can be
used as file specification to be passed with file server scripts API. The same is available in
FileserverExtension method.
Syntax
BuildJSONFileSpec (string filePath, string fileName, string extensionsToFil
ter, string additionalPropsAsJSONString, bool forceDepth, bool useServerPath,
int accessDepth,int recursiveDirDepth)

Infor Mongoose Application and Form Development | 417

Reference
Example
public string BuildJSONFileSpec(
string filePath,
string fileName = "",
string extensionsToFilter = "",
bool forceCreateDir = false,
string additionalPropsAsJSONString = "",
bool useServerPath = true,
int accessDepth = -1,
int recursiveDirDepth = -1
Remarks
Optionally, you can use the additionalPropsAsJSONString to set the Mongoose Document Type
attributes in IDM Document Type.

BuildJSONPrintSpec method (WinStudio scripts)
Applies to
Document (file) operations
Description
This string is used to create print specification that is sent for printing.
Syntax
BuildJSONPrintSpec( string fileToBePrinted, string printerId, int printCount
= 1);
where:
•
•
•

fileToBePrinted is the name of file to be printed.
printerId is the unique identifier of the printer.
printCount is the number of print jobs.

CalendarSelectDate method (WinStudio scripts)
Applies To
IWSFormComponent interface

Infor Mongoose Application and Form Development | 418

Reference
Definition
Select the given date within the calendar.
Get Syntax
CalendarSelectDate (int year, int month, int day)
Part

Description

year

Must be an integer in the range 0001-9999.

month

Must be an integer in the range 01-12.

day

Must be an integer in the range 01-31.

Remarks
This method only has a Get Syntax.
Note: In Calendar components, this value is set using the Calendar Settings dialog box.
Example for Component Objects
Sub Main()
ThisForm.Components("calendar").CalendarSelectDate(2014,3,18)
End Sub

CalendarSetMode method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Sets the calendar viewing mode.
Syntax
CalendarSetMode (int mode)
Part

Description

mode

Required. The calendar viewing mode.

Remarks
The mode determines whether the calendar component is displayed by day, week, or month.

Infor Mongoose Application and Form Development | 419

Reference
Valid parameter values are:
•
•
•

0 - Day
1 - Week
2 - Month

This method only has a Set Syntax.
Note: In Calendar components, this value is set using the Calendar Settings dialog box.
Examples
See Example for component objects on page 1280.
Sub Main()
ThisForm.Components("calendar").CalendarSetMode(0)
End Sub

CallGlobalScript method (WinStudio scripts)
Applies To
IWSForm interface
Definition
Invokes a global script, passing up to twenty parameters to the script, and returns a string containing
any output from that script.
Syntax
object.CallGlobalScript( string0, string1, ... string20 )
Part

Description

object

Required. Reference to a valid form object.

string0

Required. The name of the global script.

string1, . . . string20

Required. The values, in string form, of up to twenty parameters to be
passed to the global script.
If the script does not take twenty parameters, the extra strings must be
designated as empty strings.

Remarks
The return value is a string containing any return outputs from the script.

Infor Mongoose Application and Form Development | 420

Reference

Example
ThisForm.CallGlobalScript("MyGlobalScript", "inputValue1", "inputVal
ue2", _
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"")

ChartAddTrendLine method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
This method adds a trend line to a chart component, using line elements to define the trend line
attributes.
Syntax
Dim trendLineName As TrendLine
trendLineName.attributeX = valueX
chart.ChartAddTrendLine( trendLineName )
Part

Description

trendLineName

Required. Specifies the name by which the trend line is to be identified.

attributeX

Optional. Specifies the name of a trend line attribute.

valueX

One required for each attribute specified. Specifies the value to be assigned to
attirbuteX.

Remarks
You should declare and name the trend line and its attributes before you attempt to use this method
to add the trend line to the chart. It is also a good practice to employ the ChartClearTrendLines method
before you declare and use this one.
You can use this method to define multiple trend lines.
For a complete list and description of the trend line attributes available for scripting, see Specifying
trend line settings for charts on page 1202.

Infor Mongoose Application and Form Development | 421

Reference
Example
This example shows how the trend line should first be defined/declared and the attributes specified
before the trend line is actually added to the chart.
This example is given in Visual Basic.
Dim trendLine As TrendLine
trendLine.StartValue = 1.5
trendLine.Color = "00FF00"
trendLine.Dashed = True
trendLine.DisplayValue = "High"
chart.ChartAddTrendLine( trendLine )

ChartClearTrendLines method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
This method clears all trend lines from a chart component.
Syntax
chart.ChartClearTrendLines( )
Remarks
This method can be helpful when you want to add trend lines to a chart. Use this method to clear
existing trend lines before adding the new ones.
Example
In this example, the ChartClearTrendLines method is being used to make sure that existing trend lines
are removed from the chart before a new one is drawn.
This example is given in Visual Basic.
chart.ChartClearTrendLines()
Dim trendLine As TrendLine
trendLine.StartValue = 1.5
trendLine.Color = "00FF00"
trendLine.Dashed = True
trendLine.DisplayValue = "High"
chart.ChartAddTrendLine( trendLine )

Infor Mongoose Application and Form Development | 422

Reference

ChartGetProperty method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Returns the value of a chart component property as specified in the ChartProperty enumeration.
Syntax
T ChartGetProperty<T>( ChartProperty.propertyName )
Part

Description

T

Required. Indicates the datatype of the property value being returned (string,
int, float, etc.).

<T>

Required. Indicates a generic datatype parameter.

propertyName

Required. Specifies the name of the chart property for which you want to retrieve
the value.

Remarks
The ChartProperty enumeration contains a value for each available chart property.
Examples
In C#:
IWSFormComponent chart = ThisForm.Components("chart1");
string xAxisName = chart.ChartGetProperty<string>(ChartProperty.XAxisName);
chart.ChartSetProperty(ChartProperty.XAxisName, "Sales");
in VB:
Dim chart As IWSFormComponent = ThisForm.Components("chart1")
Dim xAxisName As String = chart.ChartGetProperty(Of String)(ChartProper
ty.XAxisName)
chart.ChartSetProperty(ChartProperty.XAxisName, "Sales")

ChartSetProperty method (WinStudio scripts)
Applies To
IWSFormComponent interface

Infor Mongoose Application and Form Development | 423

Reference
Definition
This method sets the value of a chart property specified by the ChartProperty enumeration to a
designated value.
Syntax
ChartSetProperty<T>( ChartProperty.propertyName, T value )
Part

Description

<T>

Required. Indicates a generic datatype parameter.

propertyName

Required. Specifies the name of the chart property for which you want to set
the value.

T

Required. Specifies the datatype of the property being set (string, int, float, etc.).

value

Required. Specifies the value or a an expression that resolves to the value to
which the property is to be set.

Remarks
The ChartProperty enumeration contains a value for each available chart property.
Examples
In C#:
IWSFormComponent chart = ThisForm.Components("chart1");
string xAxisName = chart.ChartGetProperty<string>(ChartProperty.XAxisName);
chart.ChartSetProperty(ChartProperty.XAxisName, "Sales");
In VB:
Dim chart As IWSFormComponent = ThisForm.Components("chart1")
Dim xAxisName As String = chart.ChartGetProperty(Of String)(ChartProper
ty.XAxisName)
chart.ChartSetProperty(ChartProperty.XAxisName, "Sales")

Clear method (WinStudio scripts)
Applies To
IWSIDOCollection interface

Infor Mongoose Application and Form Development | 424

Reference
Definition
Removes all objects from the specified IDO collection and resets the number of entries to 0.
Syntax
object.Clear( )
Part

Description

object

Required. A reference to an IDO collection object.

Remarks
The Clear method is normally not used to empty an IDO collection. This method removes a collection's
property and binding information in addition to the row items. The ClearEntries method empties the
row items without removing other collection information.
Example
Sub Main()
'Clears the secondary collection.
ThisForm.CurrentIDOCollection = ThisForm.GetSecondaryIDOCollection(2)
ThisForm.CurrentIDOCollection.Clear()
End Sub

Related Topics
ClearEntries method on page 425
RemoveEntries method on page 608

ClearEntries method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Empties all rows in a specified IDO collection including any autoinsert row, retaining property and
binding information, and resetting the number of entries in the collection to 0.
Syntax
object.ClearEntries( )

Infor Mongoose Application and Form Development | 425

Reference

Part

Description

object

Required. A reference to an IDO collection object.

Remarks
This method should normally be used to clear a collection because it retains the collection object's
property and binding information. The Clear method removes this information.
Example
Sub Main()
ThisForm.PrimaryIDOCollection.ClearEntries()
Application.ShowMessage("Cache cleared.")
End Sub

Related Topics
Clear method on page 424
RemoveEntries method on page 608

ColorDescriptorFromColor method (WinStudio scripts)
Applies To
IWSApplication interface
Definition
Takes a Color data type as input and returns the value as a color descriptor (RGB) string.
Syntax
object.ColorDescriptorFromColor( Color )
Part

Description

object

Required. Name of a valid application object.

Remarks
The return value is a string containing the RGB code value of the object's color. This RGB string consists
of a series of three comma-separated integers, each in the range 0-255. For example, the RGB code
for a deep purple color might be rendered as (158,44,186).

Infor Mongoose Application and Form Development | 426

Reference
This method must be used in conjunction with another method, such as SetGridRowColColor,
SetGridRowColColorByColumnName, or any other method that requires a color descriptor as input.
When using this method, you must include the following comment above the Imports section in any
global or form script:
'//<Ref>System.Drawing.dll</Ref>
Example
Dim backColor As Color
Dim BackColorDesc As String
BackColorDesc = Application.ColorDescriptorFromColor( Color.Aquamarine )

ColorFromColorDescriptor method (WinStudio scripts)
Applies To
IWSApplication interface
Definition
Takes a color descriptor string as input and returns the color as a Color data type.
Syntax
object.ColorFromColorDescriptor( string )
Part

Description

object

Required. Name of a valid application object.

string

Required. A string containing the RGB code value of the Color data type
to be returned (see "Remarks").

Remarks
This method can be used whenever you want to work with a Color data type, when given a color
descriptor string. Any WinStudio properties or methods that return a foreground or background color,
return them as a color descriptor string.
The return value is a Color data type matching the RGB code.
The RGB string consists of a series of three comma-separated integers, each in the range 0-255. For
example, the RGB code for a deep purple color might be rendered as (158,44,186).
This method must be used in conjunction with another method that requires a Color data type as input.

Infor Mongoose Application and Form Development | 427

Reference
When using this method, you must include the following comment above the Imports section in any
global or form script:
'//<Ref>System.Drawing.dll</Ref>
Example
Dim BackColor As Color
Dim BackColorDesc As String
BackColorDesc = Application.GetUserPreferenceValue("BaseFormBackColorDe
scriptor")
BackColor = Application.ColorFromColorDescriptor( BackColorDesc )

Copy method (WinStudio scripts)
Applies To
IWSIDOCollection interface
Definition
Creates a copy of a designated record in the specified IDO collection and returns a Boolean value
indicating whether the operation was successful.
Syntax
object.Copy( )
Part

Description

object

Required. A reference to an IDO collection object.

Remarks
You can copy data from a selected object or from the current object.
Returns a Boolean value:
•
•

TRUE - Indicates that the Copy operation was successful.
FALSE - Indicates that the Copy operation failed.

Example
Sub Main()
Dim bCopy As Boolean
Dim intIndex As Integer

Infor Mongoose Application and Form Development | 428

Reference

bCopy = ThisForm.PrimaryIDOCollection.Copy()
If bCopy Then
intIndex = ThisForm.PrimaryIDOCollection.GetCurrentObjectIndex()
ThisForm.PrimaryIDOCollection.SetObjectProperty("LanguageDesc",
intIndex, "New value")
ThisForm.PrimaryIDOCollection.NotifyDependentsToRefresh("Lan
guageDesc")
End If
End Sub

CreateDirectory method (WinStudio scripts)
Applies to
Document (file) operations
Description
Creates a directory on a file server.
Syntax
CreateDirectory( string fileSpec, string servername, string logicalFolder
Name, out string errorMessage )
where:
•
•
•
•

fileSpec is the name of a file to be placed into the newly created directory.
servername is the file server on which the directory is to be created.
logicalFolderName is the name of the logical folder on the file server where the new directory is to
be created.
errorMessage is the error message to be returned if the directory cannot be created.

DataViewDisableComponentUpdates method (WinStudio scripts)
Applies to
IWSFormComponent interface, DataView components
Description
Disables the refreshing or redrawing of a DataView.

Infor Mongoose Application and Form Development | 429

Reference
Syntax
object.DataViewDisableComponentUpdates()
Part

Description

object

Required. A reference to a valid DataView component object.

Remarks
This method is used for times where several changes happen at once or in close succession; for
example, when setting several input parameter values through scripting. It prevents the system from
refreshing after each change.
Example
ThisForm.Components("dataView1").DataViewDisableComponentUpdates()

DataViewDrilldownLoadDetailForm method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Runs the specified form to show details related to the current record in the critical number drilldown.
Syntax
object.DataViewDrilldownLoadDetailForm(string1, string2)
Part

Description

object

Required. A reference to a valid Drilldown bound DataView object.

string1

Required. The name of the form to call.

string2

Optional. Custom parameters to use when calling the form. Property substitutions will
be done against the current row of the drilldown.

Examples
Sub Main()
ThisForm.Components("DrilldownGrid").DataViewDrilldownLoadDetail

Infor Mongoose Application and Form Development | 430

Reference

Form("Items", "")
End Sub
Sub Main()
ThisForm.Components("DrilldownGrid").DataViewDrilldownLoadDetail
Form("Items", "FILTER(Item=FP(Item) SETVARVALUES(InitialCommand=Refresh)")
End Sub

DataViewDrilldownRunSubDrilldown method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Runs the specified Sub-Drilldown for the current Drilldown.
Syntax
object.DataViewDrilldownRunSubDrilldown(int)
Part

Description

object

Required. A reference to a valid Drilldown bound DataView object.

int

Required. The number of the Sub-Drilldown to be run.

Example
Sub Main()
ThisForm.Components("DrilldownGrid").DataViewDrilldownRunSubDrill
down(5)
End Sub

DataViewEnableComponentUpdates (WinStudio scripts)
Applies to
IWSFormComponent interface, DataView components

Infor Mongoose Application and Form Development | 431

Reference
Description
Enables the refreshing or redrawing of a DataView that was previously disabled using the
DataViewDisableComponentUpdates method.
Syntax
object.DataViewEnableComponentUpdates()
Part

Description

object

Required. A reference to a valid DataView component object.

Remarks
This method is used for times where the system has been prevented from refreshing or redrawing the
DataView because of multiple changes happening at once or in close succession; for example, when
setting several input parameter values through scripting.
This method, then, re-enables the refreshing or redrawing of the DataView once those changes are
complete.

ThisForm.Components("dataView1").DataViewEnableComponentUpdates()

DataViewGetScopeFilter method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Based on the user's scope, returns a string that can be used as a filter against an object that has
ScopeType and ScopeName properties like DataView Layouts.
Syntax
object.DataViewGetScopeFilter
Part

Description

object

Required. A reference to a valid DataView component object.

Infor Mongoose Application and Form Development | 432

Reference

Example
Sub Main()
Dim sFilter as String
sFilter = ThisForm.Components("DrilldownGrid").DataViewGetScopeFilter()
End Sub

DataViewPromptForInputParameters (WinStudio scripts)
Applies to
IWSFormComponent interface, DataView components
Description
If the DataView component is bound to a predefined DataView that has input parameters defined,
calling this method will cause the DataView to prompt for those input parameters using the DataView
Inputs form. Otherwise, this method does nothing.
Syntax
object.DataViewPromptForInputParameters()
Part

Description

object

Required. A reference to a valid DataView component object.

Example
ThisForm.Components("dataView1").DataViewPromptForInputs()

DataViewRefresh method (WinStudio scripts)
Applies To
IWSFormComponent interface
Definition
Causes the DataView to reload and redisplay the data.

Infor Mongoose Application and Form Development | 433

Reference
Syntax
object.DataViewRefresh
Part

Description

object

Required. A reference to a valid DataView component object.

Example
Sub Main()
ThisForm.Components("DrilldownGrid").DataViewRefresh()
End Sub

DataViewSetInputParameter method (WinStudio scripts)
Applies to
IWSFormComponent interface, DataView components
Description
Used to set the values of input parameters for a DataView that has input parameters defined.
Syntax
object.DataViewSetInputParameter(sequence, string)
Part

Description

object

Required. A reference to a valid DataView component object.

sequence

Required. An integer that indicates the Sequence number of the input parameter
that is being set.

string

Required. A string that indicates the value to which the input parameter is to be set.

Example
Sub Main()
ThisForm.Components("DataView1").DataViewSetInputParameter(0, This
Form.Variables("StartingItem").Value)
End Sub

Infor Mongoose Application and Form Development | 434

Reference

DateTimeToString method (WinStudio scripts)
Applies To
IWSApplication interface
Definition
Returns a date, time, or both, as a string formatted for the language in use by the WinStudio client or
formatted as specified in a parameter passed to the method.
Syntax
Application.DateTimeToString( string1 [, string2, Boolean ] )
Part

Description

string1

Required. A value that represents a date, time, or both.
The format must follow that of the format settings as explained in the "Format Settings" section below.

string2

Optional. A value that specifies the return format for the date, the time, or both. If
a return format is not specified, the return value is formatted according to the WinStudio client language. See the "Format Settings" section below.
If you use this option, you should also include the Boolean option. It makes little
sense to use one without the other.

Boolean

Optional. Boolean value that instructs the system whether or not to apply the format
defined in string2.
• TRUE: Applies the Format specification to the return value regardless of the
WinStudio client language.
• FALSE: (Default) Does not apply the Format specification to the return value.

Format Settings
A format is defined by combining the following settings:
g M/D/Y h:m:s p
Settings are case-sensitive.
Part

Description

g

Emperor era, used in Japanese, Korean, and other East Asian languages

M

Month

D

Day

Y

Year

Infor Mongoose Application and Form Development | 435

Reference

/

Date separator

h

Hour

m

Minute

s

Second

p

AM or PM

:

Time separator

Remarks
If the string1 argument or the string2 argument is invalid, the return value is an empty string.
Example
This code sample shows return values with various settings and locales.
Dim strProp, strDate As String
' In these examples, the value of the property CheckDate is
' "2/3/2004 2:23:32 PM"
strProp = ThisForm.PrimaryIDOCollection.GetCurrentObjectProperty("Check
Date")
strDate = Application.DateTimeToString(strProp)
' Value of strDate by locale:
'
United States (English): 2/3/2004 2:23:32 PM
'
Germany (German): 3.2.2004 14:23:32
'
Netherlands (Dutch): 3-2-2004 14:23:32
strDate = Application.DateTimeToString(strProp, "M/D")
' Value of strDate by locale:
'
United States (English): 2/3
'
Germany (German): 3.2
'
Netherlands (Dutch): 3-2
strDate = Application.DateTimeToString(strProp, "Y/D/M s:h:m", True)
' Value of strDate by locale:
'
United States (English): 2004/3/2 32:2:23
'
Germany (German): 2004/3/2 32:2:23
'
Netherlands (Dutch): 2004/3/2 32:2:23
