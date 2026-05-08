---
name: syteline-design-mode
description: "Design Mode and Web Designer - entering/exiting, toolbar, toolbox, Object Viewer, IDO metadata cache, Web Designer usage"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, csi]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, csi]
---

About context (shortcut) menus for components.......................................................................276
About DataView action menu items on context menus..............................................................277
About shortcut menus for forms.................................................................................................277
Creating a context (shortcut) menu............................................................................................278
Copying a context (shortcut) menu............................................................................................278
Editing a context menu...............................................................................................................278
Deleting a context (shortcut) menu............................................................................................279
Adding a context menu item.......................................................................................................279
Editing shortcut menu items......................................................................................................279
Deleting menu items..................................................................................................................280
Variables........................................................................................................................................280
About variables..........................................................................................................................280
Creating, copying, editing, or deleting variables........................................................................281
Validators.......................................................................................................................................281
About validators.........................................................................................................................281
About automatic foreign key validations.....................................................................................282
Tip: Reduce round trips to the database server.........................................................................282
Tip: Choose efficient validators..................................................................................................283
Creating, copying, editing, or deleting validators.......................................................................283
Validator Types...........................................................................................................................283

Infor Mongoose Application and Form Development | 8

Contents

Testing and Troubleshooting.........................................................................................................293
About diagnostics..........................................................................................................................293
Running diagnostics......................................................................................................................293
Viewing middle-tier messages.......................................................................................................294
About the Log Monitor form...........................................................................................................295
Viewing data logs remotely...........................................................................................................295
Types of Diagnostic Information....................................................................................................296
Diagnostic information about collections....................................................................................296
Diagnostic information about call details....................................................................................297
Diagnostic information about components.................................................................................297
Diagnostic information about event handlers.............................................................................298
Diagnostic information about forms...........................................................................................298
Diagnostic information about the interpreter..............................................................................299
Diagnostic information about validators.....................................................................................299
Diagnostic information about variables......................................................................................300
Deploying Applications..................................................................................................................301
About application deployment.......................................................................................................301
Creating a deployment package with the Deployment form..........................................................302
Creating scripts using Access As and Object Prefix..................................................................302
Creating scripts using forms and items......................................................................................303
Deploying an application with the Deployment form......................................................................305
Reference........................................................................................................................................306
About WinStudio startup parameters............................................................................................306
Conditional action syntax..............................................................................................................307
Cosmetics I tab settings................................................................................................................313
Cosmetics II tab settings...............................................................................................................319
Gauge samples.............................................................................................................................325
Predefined palette samples...........................................................................................................329
Scripting API for the diagram component......................................................................................332
Keywords.......................................................................................................................................333
About WinStudio keywords........................................................................................................333
Component keywords................................................................................................................333
Event handler keywords.............................................................................................................336
Validator keywords.....................................................................................................................338

Infor Mongoose Application and Form Development | 9

Contents
Form keywords...........................................................................................................................339
About substitution keywords......................................................................................................340
Background task keywords........................................................................................................341
Naming Conventions.....................................................................................................................342
Naming conventions for objects in the forms database.............................................................342
Button Labels, naming conventions...........................................................................................343
Component labels, naming conventions....................................................................................343
Component Names, Naming Conventions.................................................................................344
Event handlers, naming conventions.........................................................................................345
Form captions, naming conventions..........................................................................................345
Form names, naming conventions.............................................................................................346
Message strings.........................................................................................................................346
Property default labels, naming conventions.............................................................................347
Context menu items, naming conventions.................................................................................348
Context Menu Names, Naming Conventions.............................................................................348
String names, naming conventions............................................................................................348
Validator names, naming conventions.......................................................................................350
Variable names, naming conventions........................................................................................350
Events...........................................................................................................................................351
Standard event classification by function...................................................................................351
Standard event classification by actions that generate events...................................................356
Standard event classification by default implementation type....................................................356
Events for non-query forms........................................................................................................356
Alphabetical List of Standard Events.........................................................................................359
WinStudio API...............................................................................................................................406
Interfaces...................................................................................................................................406
Methods.....................................................................................................................................416
Properties..................................................................................................................................696
Application Event System..............................................................................................................785
Reference Tables.......................................................................................................................785
Expression Grammar.................................................................................................................797
Synchronization of Metadata.....................................................................................................823
Mongoose Glossary.......................................................................................................................849
Alias...............................................................................................................................................849
All Forms folder.............................................................................................................................849

Infor Mongoose Application and Form Development | 10

Contents
Application database.....................................................................................................................849
Archive database...........................................................................................................................849
Property (forms and components).................................................................................................850
AutoRun folder...............................................................................................................................850
Background task............................................................................................................................850
Basic editing permissions..............................................................................................................850
BLOB data.....................................................................................................................................851
Bound property..............................................................................................................................851
Caption..........................................................................................................................................851
Check box component...................................................................................................................851
Child form......................................................................................................................................851
Collection.......................................................................................................................................852
Collection-level commit..................................................................................................................852
Combo box component - defined..................................................................................................852
Component....................................................................................................................................852
Component class...........................................................................................................................852
Configuration.................................................................................................................................853
Configuration Manager..................................................................................................................853
Container.......................................................................................................................................853
Current collection..........................................................................................................................853
Current IDO item...........................................................................................................................854
Data type.......................................................................................................................................854
Date combo component - defined.................................................................................................854
Derived property............................................................................................................................854
Derived value.................................................................................................................................854
Development forms database........................................................................................................854
Development templates database.................................................................................................855
Diagnostics....................................................................................................................................855
Drop-down list component - defined..............................................................................................855
Editing permissions.......................................................................................................................855
Editing scope.................................................................................................................................856
Edit component.............................................................................................................................856
Design mode.................................................................................................................................856
Enhanced combo box component.................................................................................................856
enum value....................................................................................................................................856

Infor Mongoose Application and Form Development | 11

Contents
Event handler................................................................................................................................857
Explorer.........................................................................................................................................857
Filter..............................................................................................................................................857
Filter-in-Place................................................................................................................................857
Focus.............................................................................................................................................857
FormControl..................................................................................................................................858
FormSync......................................................................................................................................858
Forms database............................................................................................................................858
Form..............................................................................................................................................858
Form name....................................................................................................................................859
Form page component - defined...................................................................................................859
Form title.......................................................................................................................................859
Full user editing permissions.........................................................................................................859
Global objects................................................................................................................................859
Workspace....................................................................................................................................860
Graph component - defined...........................................................................................................860
Grid column component - defined.................................................................................................860
Grid component - defined..............................................................................................................860
Grid form.......................................................................................................................................860
Group box component...................................................................................................................860
Group version................................................................................................................................861
GUID.............................................................................................................................................861
IDO (intelligent data object)...........................................................................................................861
IDO project - defined.....................................................................................................................861
Impersonation................................................................................................................................861
Inheritance of property attributes..................................................................................................862
Inline list........................................................................................................................................862
Key................................................................................................................................................862
Linked form....................................................................................................................................862
List box component - defined........................................................................................................862
List of values..................................................................................................................................863
List source.....................................................................................................................................863
localhost........................................................................................................................................863
Infor Framework TaskMan.............................................................................................................863
Master Explorer folder...................................................................................................................863

Infor Mongoose Application and Form Development | 12

Contents
Menu.............................................................................................................................................863
IDO method...................................................................................................................................864
Modal form....................................................................................................................................864
Multiline edit component - defined.................................................................................................864
Multiview form................................................................................................................................864
My Folders.....................................................................................................................................864
Notebook component - defined.....................................................................................................865
Notebook tab component - defined...............................................................................................865
Note...............................................................................................................................................865
No editing permission....................................................................................................................865
Objects database..........................................................................................................................865
Actions menu.................................................................................................................................865
Actions menu item component......................................................................................................866
Optional field.................................................................................................................................866
Parent form....................................................................................................................................866
Permanent filter.............................................................................................................................866
PreLoad folder...............................................................................................................................866
Primary collection..........................................................................................................................867
Production forms database...........................................................................................................867
Production templates database.....................................................................................................867
IDO Property.................................................................................................................................867
Property class extension...............................................................................................................868
Public folder...................................................................................................................................868
Push button component................................................................................................................868
Query form....................................................................................................................................868
Radio button component...............................................................................................................868
Required field................................................................................................................................869
Reusable note...............................................................................................................................869
Reversion......................................................................................................................................869
Right-click menu............................................................................................................................869
Run mode......................................................................................................................................869
sa login..........................................................................................................................................869
Scope............................................................................................................................................870
Scripts...........................................................................................................................................870
Secondary collection.....................................................................................................................870

Infor Mongoose Application and Form Development | 13

Contents
Shortcut menu...............................................................................................................................870
Site developer editing permissions................................................................................................871
Site-default version........................................................................................................................871
Static component...........................................................................................................................871
String.............................................................................................................................................871
Subcollection.................................................................................................................................872
Subcollection property...................................................................................................................872
Substitution keyword.....................................................................................................................872
Super user.....................................................................................................................................872
System note..................................................................................................................................872
Templates database......................................................................................................................872
Tools login.....................................................................................................................................873
Unbound property.........................................................................................................................873
User-defined property...................................................................................................................873
User-defined type..........................................................................................................................873
User folders...................................................................................................................................873
User-group-site hierarchy..............................................................................................................874
User permissions...........................................................................................................................874
User-specified filter........................................................................................................................874
User version..................................................................................................................................874
Validator........................................................................................................................................874
Validator output.............................................................................................................................875
Variable.........................................................................................................................................875
Vendor version...............................................................................................................................875
Vendor developer editing permissions...........................................................................................875
Virtual directory.............................................................................................................................876
Wildcard character........................................................................................................................876
WinStudio......................................................................................................................................876
Workspace....................................................................................................................................876

Infor Mongoose Application and Form Development | 14

Contacting Infor

Contacting Infor

If you have questions about Infor products, go to Infor Concierge at https://concierge.infor.com/ and
create a support incident.
The latest documentation is available from docs.infor.com or from the Infor Support Portal. To access
documentation on the Infor Support Portal, select Search > Browse Documentation. We recommend
that you check this portal periodically for updated documentation.
If you have comments about Infor documentation, contact documentation@infor.com.

Infor Mongoose Application and Form Development | 15

About this guide

About this guide

This guide provides detailed information on how to design forms, objects, and applications in Infor
Mongoose for both the Windows client and the web client.
Use this guide for development tasks performed in Design Mode or in the Web Designer.
Intended audience
This guide is designed for use by more advanced and experienced developers who design and build
new forms and applications using Infor Mongoose.
Related documents
You can find these documents in the product documentation section of the Infor Support Portal:
•
•
•
•
•
•

Infor Mongoose Release Notes
Infor Mongoose Configuration Guide for Infor Operating Service
Infor Mongoose IDO Development Guide
Infor Mongoose Integrating IDOs with External Applications
Infor Mongoose Replication Reference Guide
Infor Mongoose Creating and Customizing Reports

You can also access the online help topic for a form or a component of a form through the right-click
menu. Right-click on a form or a component of a form, then select Help.

Infor Mongoose Application and Form Development | 16

About Mongoose-based applications

About Mongoose-based applications

This topic is a high-level overview of Mongoose-based applications and the multi-tier application
development process.
Database tier
The database tier consists of a set of databases that control or process the various types of data
required to construct and use a Mongoose-based application. This tier consists of three basic databases:
Note: In current practice and cloud implementations, these three databases are now typically combined
into a single "all-in-one" database. But the basic architectural concept remains the same.
• An Objects database contains the definitions of the IDOs (Intelligent Data Objects) created for and
used by the application.
• A Forms database contains the specifications for forms and form components, including validation
procedures, scripts, variables, and other objects. The client tier then retrieves and interprets the
specifications at runtime to create a form.
• An Application database contains the primary data maintained by users of an application. In an
inventory application, for example, the database might contain product numbers and quantities.
By default, an Application database contains stored procedures supporting Mongoose functionality,
user-defined data types, and tables for storage of administrative information, such as user names
and passwords.
Middle tier
A middle-tier IDO (Intelligent Data Object) consists of a set of properties, one or more of which constitute
the IDO's identifier or key.
For example, a customer IDO might consist of the following properties:
•
•
•
•

Customer ID
Customer name
Customer address
Credit limit

Since each customer has a unique Customer ID, this property could serve as the IDO's identifier.
A collection is the result set returned by an IDO. For instance, a customer collection might consist of
zero or more rows containing customer information. Collections can be retrieved, navigated, and
updated.

Infor Mongoose Application and Form Development | 17

About Mongoose-based applications
In the simplest case, an IDO corresponds to a relational database table, an IDO item corresponds to
a row in the table, and an IDO property represents a column in the table. In more complex cases, an
IDO can map to parts of multiple tables, and some properties might not have corresponding columns.
A collection can be used in any of these ways:
•
•
•

As primary collections
As secondary collections
As subcollections

For more information about using collections with forms, see About forms with collections on page 149
and About forms with subcollections on page 150.
Client tier
Forms are the basic units of the user interface. A form displays in a window that implements a graphical
user interface for a part of the application. A form contains within it one or more form components.
Components correspond to controls or fields on the screen, such as text boxes, buttons, check boxes,
drop-down lists, grids, charts, graphics, and so forth.
In a standard data maintenance application, a form is bound to one or more collections (though forms
can be created that are not bound to any IDO collections). Each component might be bound to a
property of an IDO from one of the form collections. Or a component might be bound to a collection
itself. Forms can also include global objects, such as variables, validators, strings, scripts, and other
objects whose operation affects more than one form.
For more information about using collections with forms, see the Related Topics links below.
Mongoose-based applications store these UI objects in the forms database. When an application is
deployed, you can use the functionality of both web-based clients and Windows-based clients to
customize forms, components, and global objects (including building new forms). These customized
objects are tagged as different versions and are stored in the forms database.
Development process
This is the basic process to build a Mongoose-based application:
•

Plan, plan, plan!
Decide how you want your application to look and work. Plan what databases you are going to
need and how they must be set up. Good planning up front will save you immense amounts of time
later.

•
•

•

One site and its required databases is set up at installation. You can add other sites to the current
site's database through the Site Management form.
Use the Configuration Manager to set up a development environment, including creating databases
and a development tools login. A tools login automatically has Vendor Developer editing permissions
in Windows client. Thus, objects you develop in Windows client are saved as Vendor Default
versions in the forms database.
Optionally, if you want to run and test forms locally, you can use the IDO Runtime Development
Server (IDORuntimeHost.exe). If you decide to use this tool, make sure you have the framework
services configured properly. For more information, and the appropriate procedures, see the help
for the IDO Runtime Development Server utility.

Infor Mongoose Application and Form Development | 18

About Mongoose-based applications
•

•

•

For each IDO you need for your application, use the New Data Maintenance Wizard to construct
the basic tables, IDOs, and forms. In each case, you will also need to modify and further develop
the basic elements after they have been created.
Use the Windows client Design Mode (or Web client Designer) to finalize your application interface.
For example, you can build the Master Explorer for your application, and you can test and diagnose
problems by running forms.
Once you have your application developed and working properly, deploy your application and
distribute it to your users and/or customers.

This is a greatly simplified version of the process. To gain a deeper understanding, we recommend
that you take advantage of the training courses; explore the videos available on our Mongoose portal;
and make liberal use of the online help that is available for virtually every aspect of Mongoose-based
application development.

Infor Mongoose Application and Form Development | 19

Fundamentals

Fundamentals

Using Design Mode
Entering and exiting Design Mode
Use Design Mode to create or edit forms and global objects.
If you are customizing an application and have Basic, Full User, or Site Developer editing permissions
on page 44, be aware that the version of a form you display is determined by your user ID and by the
hierarchy of user-group-site versions that exist on your system. Your ability to customize a form is
determined by your editing scope.
You can go into Design Mode either by clicking the Design Mode button on the toolbar or by selecting
Design Mode from the Edit menu. Use these same options to exit Design Mode.
If you attempt to enter Design Mode for a form without checking out the form, you are prompted that
the form or object is not locked. Click Check Out to check out the form without the need to exit Design
Mode. Form Control is launched with the necessary fields pre-filled with data so that you only have
to check out the form.
You can view forms in Design Mode by either of these means:
•
•

Open the form and then going into Design Mode.
