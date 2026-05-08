---
name: "syteline-app-builder-design"
description: "App Builder design - Flexboxes, adding/configuring components, composite components, data services, interactions (triggers and actions), boundaries for responsive design"
version: "1.0"
tags:
  - syteline
  - app-builder
  - flexbox
  - components
  - interactions
---

# syteline-app-builder-design

Source: Infor Mongoose App Builder/App Hub User Guide, Release 2026.x

URL.

Editing a URL variable
If, after you have created a URL variable, you decide you want to change it—for example, to change its
name—you can do so.
To edit a URL variable, use this procedure:
1
2
3

With your App Builder project open, from the More (•••) menu, select Manage URL Variables.
In the Manage URL Variables dialog box, edit the name or description for the desired URL variable.
Click Save.

App Builder saves your changes. If you changed the name, the new name is automatically changed anywhere
it is used in your app.
Note: Changing the name of the URL variable in App Builder requires that you also change the references to
it in URLs from any other apps or web serivces that might be passing values to your app as inbound variables.

Deleting a URL variable
If a URL variable is no longer needed or used in your App Builder project, you can delete it.
To delete a URL variable, use this procedure:
1
2

With your App Builder project open, from the More (•••) menu, select Manage URL Variables.
In the Manage URL Variables dialog box, locate the variable you want to delete and click the trashcan
icon next to it.
Note:
•
•

App Builder does not prompt for confirmation.
The variable is immediately removed from the list, but it is not actually deleted until you click Save.
Should you decide you don't want to delete it after all, before you click Save, simply cancel without
saving.

Infor Mongoose App Builder/App Hub User Guide | 50

Additional project-level activities
3

Click Save.
App Builder deletes the URL variable and removes any references to it from your project.

Infor Mongoose App Builder/App Hub User Guide | 51

Defining Flexboxes

Chapter 5: Defining Flexboxes

All page templates in App Builder consist of one or more predefined Flexbox components. You can use these
Flexboxes "as is" and you can also redefine them to suit your needs. You can also modify the existing Flexbox
components by subdividing them into smaller Flexboxes, adding more Flexboxes, or deleting Flexboxes.
When defining new or existing Flexbox components, you can modify these settings:
•
•
•
•
•
•
•

Orientation
Width or height
Handling of overflow
Background color
Alignment of content
Padding
Adding rows or columns

Setting the Flexbox orientation
Flexbox components can be set to either a vertical or a horizontal orientation. When set to a vertical orientation,
the components inside that Flexbox are arranged from top to bottom. When set to a horizontal orientation,
the components are arranged from left to right.
To set the orientation of the Flexbox component, use this procedure:
1
2
3
4

Select the Flexbox in the workspace.
In the component Properties panel, select the Style tab.
In the list of Style settings, locate the Orientation option and click Show More.
From the drop-down list, select the orientation option you want:
•
Vertical (the default setting) - Causes components inside the Flexbox to be arranged from top to
bottom.
•
Horizontal - Causes components inside the Flexbox to be arranged from left to right.

Infor Mongoose App Builder/App Hub User Guide | 52

Defining Flexboxes

Setting the Flexbox width/height
Depending on whether the Flexbox is defined as a "column" or a "row", you can set the width OR the height
of a Flexbox component, but not both. The Flexbox components created as part of a page template definition
might be defined either as columns or rows. You can tell by noting whether the Style setting is for the Width
(column) or Height (row).
Note: The only way you can have both rows and columns in the same app is to have one type nested within
the other. For instance, one page template has the basic Flexboxes arranged like this:

The primary orientation for this layout is horizontal, consisting of three rows. The second and third Flexboxes
then each have two nested Flexboxes, set to a vertical orientation (columns). Keep this principle in mind when
designing your apps.
Regardless of whether you are setting the width or the height, the basic principles apply to both. In each case,
you can set the width/height to a relative value (that is, a value that is relative to other Flexboxes in the
column/row); or you can set the width/height to an absolute pixel-based value.
If you are using relative settings, keep in mind that the widths or heights of the affected Flexbox components
are set as ratios.
For example, suppose you have three Flexboxes in your workspace set up as three (vertical) columns. Now
suppose that you want those vertical Flexboxes to be set up such that the first Flexbox is twice the width of
the second Flexbox. You also want the third Flexbox to be twice as wide as the second Flexbox. This means
that you effectively want the widths of the three Flexboxes to be set in a ratio of 2:1:2. When making the Width
settings for these Flexboxes, then, you would use a Value of 2 for the first Flexbox, a Value of 1 for the second
Flexbox, and a Value of 2 for the third Flexbox.

Making the width/height relative
If the Flexbox component is defined as part of a "row" of Flexboxes, you can set the width of each column in
the row relative to the other columns. If the Flexbox component is defined as part of a "column" of Flexboxes,
you can set the relative height of each row.
To make the width/height of a Flexbox component relative to other Flexboxes, use this procedure:
1

Select the Flexbox for the width/height setting you want to change.
If you are working with nested Flexboxes, make sure you have the correct (nested) Flexbox selected (and
not the parent Flexbox).

2

In the component Properties panel, select the Style tab.

Infor Mongoose App Builder/App Hub User Guide | 53

Defining Flexboxes
3
4
5

From the list of styles, locate the Width or Height setting and click Show More.
Select the Relative option.
In the Value field, enter the ratio value to use for the selected Flexbox.
App Builder automatically changes the display as soon as you enter this value.
Note:
•
•
•

Make relative settings for each Flexbox in the affected row or column. If you do not, then App Builder
defaults a value of "1" for any Flexbox in which it is not otherwise defined.
If one Flexbox in a row/column is set to a Relative value, and another is set to a Pixel value, the Pixel
value takes precedence.
You can use only integers in the Value field.

Making the width/height absolute (pixel-based)
If the Flexbox component is defined as part of a "row" of Flexboxes, you can set a pixel-based width for each
column. If the Flexbox component is defined as part of a "column" of Flexboxes, you can set a pixel-based
height for each row.
To make the width/height of a Flexbox component absolute (pixel-based), use this procedure:
1

Select the Flexbox for the width/height setting you want to change.
If you are working with nested Flexboxes, make sure you have the correct (nested) Flexbox selected (and
not the parent Flexbox).

2
3
4
5

In the component Properties panel, select the Style tab.
From the list of styles, locate the Width or Height setting and click Show More.
Select the Pixel option.
In the Value field, enter the number of pixels to use for the width or height of the selected Flexbox.
App Builder automatically changes the display as soon as you enter this value.
Note:
•
•

•

Choose these values carefully, keeping in mind that monitors or screens with different resolutions
can radically affect the resulting appearance of the Flexbox.
Keep in mind also that, if you use pixel settings for each Flexbox in the row or column, you can create
a situation in which the total number of pixels exceeds the available number of pixels for some
resolutions. In this case the Flexboxes will run off the available space and the display will be cut off.
If one Flexbox in a row/column is set to a Relative value, and another is set to a Pixel value, the Pixel
value takes precedence.

Infor Mongoose App Builder/App Hub User Guide | 54

Defining Flexboxes

Determining how Flexboxes are to handle overflow
Depending on how you have defined a Flexbox, it is possible that the content within that Flexbox can "overflow"
the available space. This is especially true when the width or height of the Flexbox is defined by pixels. When
this happens you have three options for how to handle the overflow content: by hiding it, by wrapping it, or
by allowing it to scroll.
To determine how Flexboxes are to handle overflow of content, use this procedure:
1
2
3
4

Select the Flexbox in the workspace.
In the component Properties panel, select the Style tab.
In the list of Style settings, locate the Overflow option and click Show More.
From the drop-down list, select the overflow option you want:
•
Hidden (the default setting) - "Hides" the overflow content so that it does not display at all.
•
Wrap - Allows the overflow content to "wrap" so that it fits within the available space and no content
is hidden.
•
Scroll - "Hides" the overflow content, but adds a scrollbar, so you can "scroll" to view the overflow
content.

Setting the Flexbox background color
Optionally, you can set the background color for each Flexbox component to a different color. The colors
available are according to a predetermined set of colors. (In other words, you have only a limited set of colors
from which to choose.)
To set the background color for a Flexbox component, use this procedure:
1
2
3
4

Select the Flexbox in the workspace.
In the component Properties panel, select the Style tab.
In the list of Style settings, locate the Background Color option and click Show More.
From the drop-down list, select the background color to use:
•
None (the default setting) - Displays the background as transparent (no color)
•
Azure 1–3 - Colors the background progressively darker shades of a sky-blue-type color
•
Graphite 1–5 - Colors the background progressively darker shades of gray
•
White - Makes the background pure white

Specifying the alignment of content in a Flexbox
Within a given Flexbox component, the alignment of content can be set both horizontally and vertically.
To specify the alignment of content in a Flexbox, use this procedure:

Infor Mongoose App Builder/App Hub User Guide | 55

Defining Flexboxes
1
2
3
4

Select the Flexbox in the workspace.
In the component Properties panel, select the Style tab.
In the list of Style settings, locate the Alignment option and click Show More.
From the drop-down list, select the alignment options you want:
•
Horizontal - The drop-down list provides these options for the horizontal alignment of content:
•
Left (default)
•
Center
•
Right
•
Vertical - The drop-down list provides these options for the vertical alignment of content:
•
Top
•
Middle
•
Bottom

Specifying padding in a Flexbox
In a Flexbox component, the padding determines how much space is to be kept clear around the outside of
the Flexbox. You can set the padding to be the same for all four sides of the Flexbox, or you can set the padding
for each side individually. All padding settings are specified in pixels.
To set the padding for a Flexbox component, use this procedure:
1
2
3
4

Select the Flexbox in the workspace.
In the component Properties panel, select the Style tab.
In the list of Style settings, locate the Padding option and click Show More.
Select one of the primary options:
•
All Sides (default) - This option sets the padding for all four sides to the same value.
If you select this option, App Builder displays only one Padding field in which to specify the number
of pixels for the padding.
•

Individual - This option allows you to set the padding for each side of the Flexbox to a different value.
If you select this option, App Builder displays four Padding fields in which to specify the number of
pixels for padding--one each for the Top, the Right side, the Bottom, and the Left side.

Note: Although Flexbox components do not have a setting option for borders, you can use this and other
settings to create apparent borders. See Creating an apparent border for a Flexbox on page 120.

Infor Mongoose App Builder/App Hub User Guide | 56

Defining Flexboxes

Subdividing the area inside a Flexbox
There might be times when you need the area inside a Flexbox to be subdivided for the purpose of using the
space more effectively. App Builder makes it easy to subdivide the space inside a Flexbox by adding columns
or rows.
When you want to divide the Flexbox into two or more vertical spaces side-by-side, add columns. When you
want to divide the Flexbox into two or more horizontal spaces stacked on top of one another, add rows.
In reality, you are not adding either true rows or columns. You are actually adding two or more "sub-Flexboxes"
and nesting them inside the original Flexbox. Each "subdivided" space, then, is a Flexbox component in its
own right, and it can be defined and designed more or less independently of other Flexboxes. Of course, that
is not entirely true, as the settings for one Flexbox often affect the layout and appearance of neighboring
Flexboxes.
Adding columns or rows
To subdivide a Flexbox, use this procedure:
1
2
3

In the workspace, select the Flexbox you want to subdivide.
In the component Properties panel, select the Content tab.
To subdivide the Flexbox vertically, click Add Column. To subdivide the Flexbox horizontally, click Add
Row.
To subdivide into multiple "columns" or "rows" (or if multiple columns/rows already exist), click the plus
( + ) icon for each additional column or row to add.
Any content that exists in the original Flexbox stays in that Flexbox, and the new Flexbox is empty.

Infor Mongoose App Builder/App Hub User Guide | 57

Adding and managing components

Chapter 6: Adding and managing components

The creation of an App Builder app consists mostly of adding and managing components. This involves placing
the components in the workspace as desired, as well as configuring them to behave according to your design.
This section provides information about the addition and management of each of the types of components.

Adding a component to a project
The first step in building an App Builder app (after creating the project itself) is to add the needed components
to the workspace.
To add a component to your App Builder project, use this procedure:
1
2

Select the component to add from the Components panel.
Move the cursor over the Flexbox component in which to place the component.
As you move the cursor over the workspace, "ghost" images of the component display in the Flexboxes,
to show you where the component will be added in that space if you add it there.

3

Click inside the desired Flexbox.

App Builder adds the component to the designated location.

Configuring a basic component
After adding a basic component to the workspace, it must be configured before it can actually do anything.
Configuration of components is different for each component. For specific configuration information for each
component type, see the Related Topics.
To configure a basic component, use this procedure:
1

Click to select the component you want to configure.
App Builder displays the Properties panel for the component.

2

Optionally (but recommended), assign the component a Component Name.

Infor Mongoose App Builder/App Hub User Guide | 58

Adding and managing components
This name, ideally, helps identify which component it is and gives some idea what it is designed to do.
For example, if a button is used to clear another field's contents, you might name the button "Clear Name
Field Button".
This name is only used internally to help you identify the component while designing and is not seen by
the end user.
3

If you need to use the component in conjunction with a data service, select the data service from the
Data Service drop-down list.
Otherwise, you can leave the Data Service option at {In Context Value}.

4

On the Properties panel, select the Content tab and make the desired settings for the content to associate
with the component.
For the Content settings that are valid for each component, see the applicable "Component settings"
topic in the Basic component settings on page 81 appendix.

5

On the Interactions tab, create and/or add any interactions required to make the component function
as desired.
For the Interactions that are valid for each component, see the applicable "Component settings" topic
in the Basic component settings on page 81 appendix.

6

On the Style tab, make whatever settings are desired to have the component look and appear the way
you want.
In particular, be aware that you can use the Margin setting to "fine tune" the placement of the component
in the Flexbox.
For the Style settings that are valid for each component, see the applicable "Component settings" topic
in the Basic component settings on page 81 appendix.

Infor Mongoose App Builder/App Hub User Guide | 59

About composite components

Chapter 7: About composite components

Composite components are assembled and created from existing basic components. Essentially, you create
them by selecting a group of basic components and defining them as your own custom composite components.
Typically, you use composite components when you have a set of basic components that you want to be able
to reuse as a group.
To be able to create a composite component, all the basic components must be placed into the same Flexbox.
This can also be accomplished by nesting multiple Flexboxes inside a parent Flexbox, but note that the
resulting composite component will include all basic components inside both Flexbox components.
Composite components can be created from scratch, saved and exported for use elsewhere, and imported
from a file or from the App Builder Repository.

Creating a composite component
App Builder does not include any predefined composite components. To be able to use a composite component,
you must create or import it for yourself.
To create a composite component, use this procedure:
1

Place and configure the basic components to be included as part of the composite component into a
single Flexbox component.
Note: All basic components in the composite component must be in the same Flexbox. You cannot
include individual components from different Flexboxes in a composite component. You can, however,
include multiple Flexboxes and their contents by nesting those Flexbox components inside another
Flexbox.

2
3
4
5
6
7
8
9

Select the Flexbox that contains the components that are to constitute the composite component.
In the App Builder toolbar, click the Components icon.
On the Components panel, select the Composite tab.
Click Create New.
In the Create Composite Component dialog box, provide a Name for the composite component.
Verify that the correct component is selected.
Click Next.
On the Include page, clear or select the elements and attributes of the basic components to include as
part of the composite component.

Infor Mongoose App Builder/App Hub User Guide | 60

About composite components
10 Click Next.
11 On the Summary page, verify that the specifications for the composite component are correct, and then
click Finish.
App Builder creates the composite component as specified and adds it to the list of composite of composite
components in your project.
Note: At this point, the composite component is available only within your current project. To make it available
for use in other App Builder projects, you must first export it and then import it into the Repository.

Exporting a composite component to a file
After you have created a composite component, you might decide you want to use it in other projects as well.
To do this, you must export the composite component to a file and then import it into the other project.
To export a composite component to a file, use this procedure:
1
2
3
4
5
6
7

With the App Builder project that has the composite component open, on the Components panel, select
the Composite tab.
Click Manage Composite Components.
In the Project Composite Components dialog box, select the (check box for) the composite component
you want to export.
Click Export to File.
In the Export Composite Components dialog box, provide the Name of the exported file.
Click Export.
Click Close.

App Builder creates the composite component file with the ".iac" extension and saves it to your Downloads
folder.

Importing a composite component from a file
There are multiple ways to acquire a composite component for use in your App Builder project. You can, for
instance, import it from a file created in another App Builder project.
To import a composite component from a file into your project, use this procedure:
1
2
3
4
5

With your App Builder project open, on the Components panel, select the Composite tab.
Click Manage Composite Components.
In the Project Composite Components dialog box, click Import from File.
In the Import New dialog box, click the folder icon and navigate to and "open" the composite component
(*.iac) file to import.
Click Import.

Infor Mongoose App Builder/App Hub User Guide | 61

About composite components
6

Click Close.

App Builder imports the composite component and adds it to the list of available composite components in
your project.

Importing a composite component from the Repository
There are multiple ways to acquire a composite component for use in your App Builder project. You can, for
instance, import it from the App Builder Repository.
To import a composite component from the Repository into your project, use this procedure:
1
2
3
4
5
6
7

With your App Builder project open, on the App Builder toolbar click the Components icon.
On the Components panel, select the Composite tab.
Click Manage Composite Components.
In the Project Composite Components dialog box, click Add from Repository.
In the Repository dialog box, select the composite to import.
Click Add To Project.
In the Project Composite Components dialog box, click Close.

App Builder adds the composite component to the set of composite components available in the project.

Using a composite component in a project
Using a composite component in an App Builder project is much like using any basic component. The possible
exception is that, because many composite components are pre-configured, there is often little or no additional
configuration required.
To use a composite component in your project, use this procedure:
1
2

With your App Builder project open, on the Components panel, select the Composite tab.
Select the composite component you want and place it in the Flexbox component in which you want to
use it.
Note: The Flexbox must be empty.

3

Optionally, make any configuration changes that might be needed.

Infor Mongoose App Builder/App Hub User Guide | 62

Managing data services

Chapter 8: Managing data services

App Builder uses data services to retrieve data from other sources, connect with other Infor products using
ION APIs, and use Mongoose resources. These data services can be created or added either in the project
itself or in the App Builder Repository.
The activities you can perform with data services differ slightly in the App Builder Repository and in App
Builder projects. As well, the data services in the Repository and in your project are largely independent of
each other, though you can move data services from one to the other.
For the activities you can perform with data services in the App Builder Repository, see Working with data
services in the Repository on page 37.
Within your App Builder project, you can perform these activities with data services:
•
•
•
•
•
•
•
•

Create data services using the Wizard, using either ION APIs or Mongoose IDOs as the basis for the data
service.
Import data services from the App Builder Repository.
Import data services from files.
Create data services by manual configuration.
Edit data services in the project.
Delete data services from the project.
Export data services to files.
Search Datagrids for specific records.

About data services
App Builder uses data services to retrieve data from other sources, connect with other Infor products using
API Gateway or ION APIs, and use Mongoose resources.
For example, a data service can be used to load data from an external source into your Infor App Builder
project, or to save data from your project to an API Gateway or ION API destination.
Data services can connect App Builder to Infor APIs, using API Gateway or ION API technology, as persistent
references. This enables the use of App Builder that involves multiple other applications.
Data services can be created directly within your App Builder project. They can also be exported to files, which
can then be imported into the App Builder Repository for use in other projects. This enables you to share
them and move them between different projects, environments, and installations.

Infor Mongoose App Builder/App Hub User Guide | 63

Managing data services
For a data service to work with other applications in App Builder, you must have the appropriate system or
ERP in your IOS installation. For example, if you have Infor M3, Infor Document Management (IDM), Infor OS
Portal, or Infor Distribution SX.e installed and configured in IOS, then you can use their respective APIs in App
Builder.
In addition, because App Builder is fully integrated with and incorporated into Infor Mongoose, you can use
data services as a means of accessing and using Mongoose Intelligent Data Objects (IDOs) in your App Builder
projects.

Creating or adding a data service in a project
In App Builder, there are two possible starting points from which you can create or add a data service for a
project: You can add it directly into your project, or you can add it first to the App Builder Repository and then
import it from there into your project.
If you create the data service in your project, it is saved only as part of your project. If you later decide to put
it into the App Builder Repository, you must first export it from your project as a file and then import that file
into the Repository.
If you create the data service in the App Builder Repository, it is initially saved only to the Repository. If you
then want to use it in your project, you can add it to your project directly from the Repository.

Creating a data service within a project
To create a data service within a project using the Wizard, follow this procedure:
Note: The procedure to create a data service in the App Builder Repository using the Wizard is similar, but
not exactly the same. See Creating a data service in the Repository on page 39.
1
2
3
4

On the Properties panel, click Manage Data Services.
In the Project Data Services dialog box, click + Add Data Service.
From the drop-down menu, select Create from Wizard.
In the Create from Wizard dialog box, select the source for the data service:
•
ION API - This option allows you to select from among the many API Gateway or ION APIs available
to you, based on your licensed product suites.
•
Local IDO - This option allows you to select a Mongoose IDO.
•
Static List - This option allows you to create a static list of data values.

5

Click Next.
Your next steps at this point depend on which option you selected in the previous step. If you selected:
•
ION API - See Configuring an ION API data service on page 65.
•
Local IDO - See Configuring a Local IDO data service on page 66.
•
Static List - See Configuring a Static List data service on page 67.

Infor Mongoose App Builder/App Hub User Guide | 64

Managing data services

Configuring an ION API data service
Whether creating a data service in a project or in the App Builder Repository, you can use an API Gateway or
ION API as the source. This sub-procedure describes how to configure that data service.
To configure a data service using an API Gateway or ION API as the source, use this sub-procedure:
Note: This sub-procedure assumes you have already performed the actions in either the topic Creating a
data service within a project on page 64 or the topic Creating a data service in the Repository on page 39).
1
2
3

4

In the Create Data Service - Find API dialog box, use the Suite drop-down menu to select the Infor
product suite that contains the API you want.
Optionally, to filter your search for the desired API, specify a search term in the Search field, and then
click Search.
Optionally, to filter the results according to specific operations, select the operations to search for, using
the list of If you leave this field blank, App Builder searches for all available APIs in the suite (or all suites,
if no suite has been specified). Filters.
From the list of results, select the API you want.
Note: If you are using the Infor Mongoose REST API set, you must select only APIs that use JSON.
To view the details for a particular API, what properties it contains, and so on, click Details for that API.
This launches the Operation details dialog box, which displays detailed information about the API.

5

Once you locate and select the API you want, click Next.
App Builder opens the Create Data Service dialog box to the Input Parameters page. This page displays
information about any input parameters required by or accepted by the API. You can also use this page
to modify input parameters for the API.
View JSON
Http Method
From the drop-down list, select the type of HTTP action to take with the API request.
API Relative URL
This field displays the URL for the API, relative to
Request Header
This field displays the request header information for the API request.
Input Parameters
This section displays options to set input parameters for the API request. You can add or remove
parameters, set parameters as requred, define the parameter types, and optionally provide aliases for
the parameters.

6

Optionally, verify that the data service is performing as desired:
a Click Test Data Service.
b In the Test Data Service dialog box, manually input values for any required input parameters.
c Optionally, manually input values for any optional input parameters you want to test.
d Scroll to the bottom of the dialog box and click Run Data Service.
e Verify that the data service response is as expected by examining the contents of the Response Body
field.

Infor Mongoose App Builder/App Hub User Guide | 65

Managing data services
f

Assuming the data service responds as expected, optionally click Copy to clipboard.
This copies the entire contents of the Response Body field to the system clipboard. You will need
this data to test the output parameters.

g
7

Click Close.

When you are finished with the Input Parameters page, click Next.
App Builder opens the Output Parameters page. Use this page to specify this information:
Output Type
Select the response output type.
•
JSON requires the response data to be in JSON format.
In the next step, you can use the data copied in the previous step to provide this.
•

File requires the data to be imported from a file, as specified in the Response alias field.

Root Element
Object Id
Output Parameters
Use this section to generate and test output parameters for the selected API.
GENERATE PARAMETERS
8

Optionally, test the data service to verify that it accomplishes whatever it is supposed to. To do this, click
Test Data Service, provide whatever input parameters are needed, scroll to the bottom, and click Run
Data Service.
The results of the test display in the Response Body section of the Test Data Service dialog box. If the
expected data is returned, then the test was successful.

9 Click Next.
10 On the Name & Description page of Create Data Service, provide a Name and optional Description for
the data service.
11 Click Next.
12 On the Summary page of the wizard, verify that all your specifications are correct. When you are satisfied
that they are, click Finish.
App Builder adds the data service to the list of available data services.
13 Click Close.
The new data service is now available for use in the App Builder workspace.

Configuring a Local IDO data service
Whether creating a data service in a project or in the App Builder Repository, you can use a local (Mongoose)
IDO as the source. This sub-procedure describes how to configure that data service.
To configure a data service using a local IDO as the source, use this sub-procedure:

Infor Mongoose App Builder/App Hub User Guide | 66

Managing data services

Note: This sub-procedure assumes you have already performed the actions in the topic "Creating a data
service using the Wizard" (either in a project on page 64 or in the App Builder Repository on page 39).
1
2

In the Create Data Service - Find IDO dialog box, use the Project drop-down menu to select the IDO
project (collection) that contains the IDO you want.
Optionally, to filter your search for the desired IDO, specify a search term in the Search field, and then
click Search.
If you leave this field blank, App Builder searches for all available IDOs in the collection.

3

From the list of results, select the IDO you want.
To view the details for a particular IDO, what properties it contains, and so on, click Details for that IDO.

4
5

Once you locate and select the IDO you want, click Next.
On the Operation page of the wizard, select the operation that you want the data service to perform on
the IDO collection.
Click Next.
Use the Parameters page of the wizard to provide any input parameters, output parameters, or other
settings required by the IDO.
Some of these settings are optional, depending on the operation you selected.

6
7

8

Optionally, to test the data service and verify that it behaves as expected, click Test Data Service.
Supply any input parameters required or optionally accepted by the IDO.

9 Click Next.
10 On the Name & Description page of the wizard, provide a Name and, optionally, a Description for the
data service.
11 Click Next.
12 On the Summary page of the wizard, verify that all your specifications are correct. When you are satisfied
that they are, click Finish.
App Builder adds the data service to the list of available data services.
13 Click Close.
The new data service is now available for use in the App Builder workspace.

Configuring a Static List data service
Whether creating a data service in a project or in the App Builder Repository, you can create a list of static
data values to use in your app. This sub-procedure describes how to configure that data service.
To configure a data service by creating and configuring a static list of values as the source, use this
sub-procedure:
Note: This sub-procedure assumes you have already performed the actions in the topic "Creating a data
service using the Wizard" (either in a project on page 64 or in the App Builder Repository on page 39).
1

In the Create Data Service - Static List dialog box, create the required columns:
a Click Add Column.
b In the Add New Column dialog box, provide a Column Name for the column.

Infor Mongoose App Builder/App Hub User Guide | 67

Managing data services
This column name cannot contain spaces. It is used only for internal purposes and does not display
where the end user can see it.
c Click Add.
Repeat this step for each additional column you want to add.
The order in which you create the columns is not particularly important at this point. You can always
rearrange the columns when using the data service.
2

Create the required rows and their values:
a Click Add Row.
b Add a value for each column cell, as appropriate.
Repeat this step for each addition row of values you want to add.

3
4

Click Next.
On the Name & Description page of the wizard, provide a Name for your Static List data service. Optionally,
you can also provide a Description. This description is only used for internal purposes.
Click Next.
On the Summary page of the wizard, verify that the Static List contains the data you require.
Click FInish.

5
6
7

You can now use, export, and import the data service as you would other data services.

Adding a data service from the Repository
If you have or know of a data service that exists in the App Builder Repository, you can add it to your project
directly from the Repository. This eliminates the need to export the data service from the Repository as a file
and then import that file into your project.
To add a data service to your project from the Repository, use this procedure:
1
2
3

On the Properties panel, click Manage Data Services.
In the Project Data Services dialog box, click + Add Data Service.
From the drop-down menu, select Add from Repository.
App Builder displays the list of data services in the Repository.

4
5

From the list of data services, select the one you want to add to your project.
Click Add To Project.

App Builder adds the data service to the list of data services available in your project.

Adding a data service by file import
To import a data service into your project, follow this procedure:
Note: The procedure to import a data service directly into the App Builder Repository is similar, but not
totally the same. See Adding a data service into the Repository by file import on page 39.

Infor Mongoose App Builder/App Hub User Guide | 68

Managing data services
1
2
3
4
5
6

On the Properties panel, click Manage Data Services.
In the Project Data Services dialog box, click + Add Data Service.
From the drop-down menu, select Import from file.
In the Import New dialog box, click the folder icon.
Use the Open dialog box to navigate to, select, and "open" the data service (*.iads) file.
In the Import New dialog box, click Import.

App Builder adds the the data service to the list of available data services.

Editing a data service
There might be times when, for one reason or another, you need to modify a data service. You can edit data
services either in your project or in the App Builder Repository. If the data service exists in both places, your
modifications affect only the environment in which you make them (that is, if you make them in your project,
the Repository version remains unchanged, and vice versa).
Note: This means, among other things, that you can use a data service from the Repository as the basis for
a completely new one in your project.
To edit a data service, use this procedure:
1

Perform one of these actions:
•
If you are in a project, go to the Properties panel and click Manage Data Services.
•
If you are in the Repository, make sure the Data Services tab is selected.

2
3
4

From the list of data services available, select the one you want to edit.
On the toolbar, click Edit.
Use the four pages of the Edit Data Service dialog box to make whatever changes you need to make to
the data service.
The Edit Data Service dialog box is very similar to the dialog box used to create a data service in the first
place. When you open it, it is populated with the current settings. The options available for editing depend
on the nature of the data service itself.

5

When you are finished with your changes, select the Summary page, check your settings, and click Finish.

Deleting a data service
When a data service is no longer being used or is otherwise obsolete, you can delete it. You can delete data
services either from your project or from the App Builder Repository. If the data service exists in both places,
it is deleted only from the environment in which you take the action (that is, if you delete it from your project,
the Repository version remains, and vice versa).
To delete a data service, use this procedure:

Infor Mongoose App Builder/App Hub User Guide | 69

Managing data services
1

Perform one of these actions:
•
If you are in a project, go to the Properties panel and click Manage Data Services.
•
If you are in the Repository, make sure the Data Services tab is selected.

2
3
4

From the list of data services available, select the one you want to delete.
On the toolbar, click Delete.
When prompted for confirmation, click Yes.

Exporting a data service
You can export a data service in the form of a specially formatted file. You can then copy this file to another
system or import it into another App Builder project.
Note: You can also export a data service from the App Builder Repository to a file. The procedure is similar
but not exactly the same. See Exporting a data service to a file (from the Repository) on page 40.
To export a data service from your App Builder project to a file, use this procedure:
1
2
3
4

On the Properties panel, click Manage Data Services.
In the Project Data Services dialog box, select the data service you want to export.
On the toolbar, click Export.
In the Export Data Services dialog box, provide the Name of the exported file.
If you do not provide a name, App Builder uses the current date as the filename.

5

Click Export.

App Builder saves the data service file with an *.iads extension to your Downloads folder.

Infor Mongoose App Builder/App Hub User Guide | 70

Working with interactions

Chapter 9: Working with interactions

If components and data services are the basic building blocks of App Builder projects, then interactions are
the connectors and motors between them. Interactions are the specifications that cause the parts to do
whatever it is you want them to do in your app.

Using interactions
Interactions are the instructive elements that tell the app what to do in response to "trigger" events. An
interaction always has a source component and trigger, and a target component and action. Before the
interaction can be established, both the source component and the target component must be in place in
your project.
The source component can be any component in the application, or it can be the application itself. For
instance, an interaction might be set to do something when a component, such as a Button, is clicked. Or it
might be set to do something, such as load a data collection when the application first starts. The events that
set the interaction into motion are known as "triggers".
The target component is the recipient of an action triggered by the source component. Very often, the target
action is to run a data service associated with the target component, but a wide variety of actions can be
proscribed.

Creating an interaction
Interactions constitute the "logic" and the workflow between components that, when taken together, enable
your App Builder project to do whatever you have designed it to do. Some interactions are defined automatically
when you create your project and add components, but most of them must be created.
1 In your App Builder project, select the component that is to trigger the action.
2 On the Properties panel, select the Interactions tab.
If any interactions already exist for the selected component, either as source or target, they display in a
list.
3

Click Create Interaction.
If there are existing interactions, click the plus (+) icon, which does the same thing.
The Interactions tab expands to display the various settings that must be made.
Infor Mongoose App Builder/App Hub User Guide | 71

Working with interactions
4

On the Interactions tab, make these settings:
•
In the Trigger Event section, set these values:
Component
This is the component that is to be used to trigger the action. It should be the component you
selected in the second step.
Event
This defines what must actually happen to trigger the event. In most cases, it is limited by the
component being used to trigger it. Button components, for example, can only trigger events by
being clicked or by running an associated data service.
For details about the types of interactions possible for each component, see the component settings
topic for that component.
•

In the Target Actionsection, specify which Component is to be the recipient of the action and what
the Action is to be.
Component
This is the component in which the action is to take place when the event is triggered.
Action
This specifies what action is to take place.

Note: Depending on the data service being used, input parameters might also be required or optional.
If that is the case, App Builder prompts for them in the Data Key Mapping section at the bottom of the
tab.
Once the interaction has been created, you can test it either in Preview mode or by saving and launching the
app.

Editing an interaction
It can happen often that, after an interaction has been initially created, you might need to modify it. This can
be because it doesn't work as you thought, or you want to augment its behavior in some way.
1 In your project, select either the source component or the target component.
Note: You can modify the interaction from either component. Just make sure you know which type the
component is. It can be easy to get them confused.
2
3

In the Properties panel, select the Interactions tab.
From the list of interactions for the selected component, click to select the interaction you want to edit.
App Builder expands the specification for the selected interaction.

4

Modify the settings for the interaction as desired.
App Builder automatically saves your changes as you make them.

Once the interaction has been edited, you can test it either in Preview mode or by saving and launching the
app.
Infor Mongoose App Builder/App Hub User Guide | 72

Working with interactions

Removing an interaction
There might be times when you need to remove an interaction, either because it does not work, or because
you want to replace it with a different one. In such cases, you can remove the interaction.
1 In your project, select either the source component or the target component associated with the
interaction.
Note: You can modify the interaction from either component. Just make sure you know which type the
component is. It can be easy to get them confused.
2
3

In the Properties panel, select the Interactions tab.
From the list of interactions for the selected component, click to select the interaction you want to
remove.
App Builder expands the specification for the selected interaction.

4
5

Click the trash can icon.
When App Builder prompts you to confirm the deletion, click Yes.

App Builder removes the interaction from both the source component and the target component.

Infor Mongoose App Builder/App Hub User Guide | 73

Working with boundaries

Chapter 10: Working with boundaries

When you create an app that is designed to make use of responsive design features, the primary mechanism
used to define the size of the display is the use of boundaries. Boundaries are defined by the pixel-width of
the display.
A key to the use of boundaries is the slider, which is used to manage many aspects of boundaries.

Using the slider
The key tool for adding and using boundaries in responsive design apps is the App Builder slider.
The slider is found on the extreme right edge of the canvas area. The slider is an oblong charcoal gray object.
This object has three segments that are used to manage boundaries.

You can use this slider to simulate various widths for the screen, by dragging it left or right. This changes the
size (width) of the canvas. When you reach a boundary marker, the display changes to whatever is defined
for that boundary marker.
You can also use the slider to add standard boundaries or a custom boundary to your project.

Infor Mongoose App Builder/App Hub User Guide | 74

Working with boundaries

Using standard boundaries
App Builder includes a set of predefined boundaries, called standard boundaries. These boundaries include
a variety of screen widths commonly used in computer monitors, mobile phone devices, andInfor OS Portal
widgets.
Access these predefined boundaries from the slider:
1

With your project open, click the lowest segment of the slider.

