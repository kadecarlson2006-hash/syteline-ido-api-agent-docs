---
name: "syteline-basics-navigation"
description: "Mongoose basics - navigation, Explorer (folders, pinning, shortcuts), context menus, user preferences, getting help, searching help topics"
version: "1.0"
tags:
  - syteline
  - basics
  - navigation
  - explorer
  - mongoose
---

# syteline-basics-navigation

Source: Infor Mongoose Basics User Guide, Release 2026.x

this website periodically for updated documentation.

Infor Mongoose Basics User Guide | 7

Basics

Chapter 1: Basics

In order to understand and use the application interface more effectively, see these topics:
•
•
•
•
•
•
•
•

Navigation in the System on page 9
Using the Explorer on page 9
Context (Right-Click) Menus on page 15
About Forms, Fields, Records, and Collections on page 20
User Interface Reference on page 118
Keyboard Shortcuts on page 118
Menus on page 118
Toolbars on page 134

Infor Mongoose Basics User Guide | 8

Navigation in the System

Chapter 2: Navigation in the System

The application user interface is designed to be intuitive and easy to use. At the same time, you have flexibility
to customize the system and set your own preferences.
You can use these areas of the application for navigation:
•

Use the Explorer to locate and open forms. You can also customize the way you organize and use forms
to reflect your preferences.
See Using the Explorer on page 9.

•

Use your keyboard to navigate and work in the system.
See Keyboard Shortcuts on page 118.

•

Context menus, often called "right-click menus," are shortcuts to perform actions on the current field or
value.
See Context (Right-Click) Menus on page 15.

•

Get help when you need it, quickly and easily. Context-sensitive help is available on most forms.
See Using the Help on page 17

Using the Explorer
The Explorer is often the starting point for working in the system. The Explorer tree pane presents a hierarchical
view of the forms in the system, similar to Windows Explorer. Within the Explorer, forms are grouped in folders.
These are some ways you can use the Explorer:
•
•
•
•
•
•
•

Navigate through the folders to find and open a form.
Set up your favorite forms as a group, so you can get to them easily.
Change the way you view the Explorer, to accommodate the way you work.
Set certain forms to open automatically when you log into the system.
Dock or undock the Explorer to the workspace area.
Resize the Explorer display area.
Pin or unpin the Explorer, according to your working preferences.

Infor Mongoose Basics User Guide | 9

Navigation in the System

Opening the Explorer
To open the Explorer, select View > Explorer.
Depending on what theme you are using, the Explorer might open automatically when you log in to the system.
You can set the Explorer to always open at a certain level.

Structure of the Explorer
When open, the Explorer displays a hierarchy of folders and forms. Initially, the Explorer displays only the
top-level folders:
•
•

My Folders on page 12
Master Explorer Folders on page 10

Note: Your system administrator might have reorganized, removed, or renamed any of the folders described
in this topic.
The Explorer has two panes:
•
•

Use the top tree pane to expand a folder to display and select subfolders and forms.
Click the buttons in the lower button pane to display different trees in the current folder level.

You can customize the Explorer buttons and tree view to show only the areas of the application that you need.
The order in which forms are listed in the Explorer tree structure depends on the Select by Name Instead of
Caption field on the Select Form dialog box. If the option is selected, the forms are listed in alphabetical
order in the Explorer according to the form name; otherwise, they display in order by the form caption.

Master Explorer Folders
Expand the Master Explorer folder, and you see a set of subfolders similar to this:
•
•

All Forms
Modules

These subfolders are divided into groups that provide different ways of locating any form in the system.
If the Button pane is displayed, you can also use the buttons to move to different areas within a level.
All Forms
Expand the All Forms subfolder, and Explorer displays under it all forms available to you, arranged in
alphabetical order. If you know the name of the form you want, this is often the fastest way to access it.
See Finding and Opening Forms in the Explorer on page 30.

Infor Mongoose Basics User Guide | 10

Navigation in the System
Modules Folder
The Modules folder displays forms grouped mostly by application modules. To find a form based on the area
of the application where it exists, select the appropriate module subfolder. The Modules folder also includes
the Codes subfolder, which contains forms where you set up codes that are used throughout the system, and
the System subfolder, which contains forms used for general application administration.
Subfolders
The folders contain multiple levels of subfolders.
The list of forms in the subfolders can be further subdivided by function:
Subfolder
Type

Description

Activities

Forms used for day-to-day activities. These forms typically perform common actions on
records, such as copying them, generating line sequences, printing worksheets, and so
on.

Files

Forms where you can set up codes and parameters used within the module.

Queries

Forms to help you find specific records by using filtering techniques.
For more information, see About Query Forms on page 23.

Reports

Forms to set up the parameters for printing specific information from the records.
For more information, see Reports Overview on page 94.

Utilities

Forms used for activities generally performed by administrators or managers. Utilities
usually require Site Developer (system administrator) permissions to use.

Note: A system administrator can remove or hide folders and subfolders, so you might not see all of the
folders mentioned here in your Explorer window.

Public Folders
The Public Folders subfolder in the Explorer contains any forms your system administrator has set up to
appear there. These forms are typically available to anyone in an organization.
Only users designated as a Site Developer on the Users form - who are usually system administrators - can
make changes in this folder.

User Folders
The User Folders folder in the Explorer allows those who have Site Developer editing permissions, as set on
the Users form, to view and edit the My Folder contents of any user in the system. This folder is available
only to those who have these editing permissions.

Infor Mongoose Basics User Guide | 11

Navigation in the System

My Folders
You will probably find, as you work, that there are some forms that you use more often than others. The My
Folders folder in the Explorer is designed to provide a place where you can create your own set of commonly
used forms. If you want, you can also rename subfolders and forms in My Folders to reflect your preferences.
Finally, you can also select sets of forms to open automatically whenever you log on to the system.
For more information, including the procedures to do these things, see Customizing My Folders in the Explorer
on page 12.
In addition, your system administrator can use My Folders to create shortcuts to forms and set them to
automatically open for you when you log on.

Customizing 'My Folders'
In the Explorer, you can customize the contents of My Folders in several ways:
•
•
•

Add your own subfolders.
Add or copy forms into your My Folders or any subfolders that you have created.
Rename folders or forms to reflect your use of those folders and forms.
Caution: Do not rename the AutoRun or PreLoad subfolders.

•

Delete folders and forms.
Caution: Do not delete the AutoRun or PreLoad subfolders.

•

Use the AutoRun and PreLoad subfolders to set up forms that open automatically when you log on.

Adding Folders
You can add your own subfolders to My Folders in the Explorer.
Note: Your editing permissions determine whether you can add subfolders to other folders, but the procedure
is basically the same.
1
2
3

Select View > Explorer.
Click My Folders.
In My Folders, select the folder or subfolder below which the new subfolder is to be added.
For example, to add a subfolder at the top level underneath My Folders, select My Folders. To add a
subfolder to AutoRun or another subfolder, select that subfolder.

4

Right-click the selected folder and select New Folder.
The system adds the new subfolder and temporarily names it New Folder.

5
6
7

Right-click the new subfolder and select Properties.
In the Caption field of the Explorer Object Properties dialog box, specify the new name for the folder.
Click OK.

Infor Mongoose Basics User Guide | 12

Navigation in the System

Adding forms to folders
In the Explorer, you can add forms to the My Folders area or any of its subfolders. Your editing permissions
determine whether you can add forms to other folders.
To add a form to a folder, follow these steps:
1

If it is not already open, open the Explorer pane.
(Select View > Explorer.)

2
3
4

Right-click the My Folders entry, or another folder to which you want to add a form.
From the context (right-click) menu, select New Shortcut.
Use the Select Form dialog box to locate and select the form you want to add.

The selected form is now available from the My Folders (or other folder) list.

Copying Folders or Forms
You can copy folders and forms from the Master Explorer folder to My Folders.
Note: Your editing permissions determine whether you can copy forms to other folders.
1
2
3
4

In the Explorer, navigate to the folder or subfolder that contains the form or folder you want to copy.
In the tree pane, right-click on the form or folder and select Copy.
Navigate to the folder in which you want to create a copy.
In the tree pane, right-click on the folder and select Paste.

Notes:
•
•
•

You are not really creating a copy of the form, only a shortcut to that form.
You can copy only one form at a time.
You can rename your copy of the form in the Explorer.

Renaming Folders or Forms
To facilitate the way you work, you can rename folders and forms in My Folders.
Note:
•
•
•

Your editing permissions determine whether you can rename items in other folders.
When you change the name of a form, do not change the contents of the Form Name field.
Renaming a form changes its title (caption) only in the current folder in the Explorer. The actual form
name, the title of the same form as displayed in other folders, and the caption displayed in the form when
it is open are all unchanged.

1
2
3

In the Explorer, select the folder or form you want to rename.
Right-click the folder name and select Properties.
In the Caption field, specify the new name.

Infor Mongoose Basics User Guide | 13

Navigation in the System
4

Click OK.

Deleting Folders or Forms
You can delete folders and forms from My Folders. Your editing permissions determine whether you can
delete from other folders.
Caution: Do not delete the AutoRun orPreLoad subfolders. If you do, you will lose the functionality they
provide until you recreate them. For more information, see Setting Up Forms to Load Automatically on page
14.
To delete a folder or form:
1
2

Select the folder or form you want to delete.
Right-click the item and select Delete.

Setting Up Forms to Load Automatically
Typically, there are a few forms that you use constantly or very often. Use these folders to load or open these
forms automatically when you log in to the system:
•
•

AutoRun: When you log in, the system automatically loads and opens any forms in this older. The system
opens them at their default size and cascades them in the workspace.
PreLoad: When you log in, the system automatically retrieves any forms in this folder from the database
into memory, so the forms display more quickly when you open them later.

If the AutoRun or PreLoad folder is missing from My Folders, you can recreate it by adding a new folder and
naming it AutoRun or PreLoad.
To set up forms to open automatically, copy the forms you want to load and/or open automatically into either
the AutoRun or the PreLoad folder.

About pinning Explorer folders
You can pin Explorer folders in the menu bar. By pinning commonly used folders or items, you no longer need
to load them through the System or Master Explorer menus. When you pin a folder, a new menu item, Root,
appears. The Root menu contains the menu items that usually appear when no other folder is pinned (for
example, My Folders and Master Explorer).
You can set a default pinned Explorer folder by setting the Default Pinned Explorer Folder on the Options
tab of the Theme Editor. This default pinned Explorer folder applies to both WinStudio and web client.
The default pinned Explorer folder (if it exists) of a theme that is selected by a user is used if that user does
not specify a default pinned folder. For first time users, the default pinned Explorer folder to be used depends
on the selected configuration during login. You can set a default theme for a configuration in Configuration
Manager.

Infor Mongoose Basics User Guide | 14

Navigation in the System

Pinning Explorer folders
1
2
3

Select Master Explorer > Modules.
Navigate to the module that you want to pin.
Select Pin.

Context (Right-Click) Menus
Context menus are commonly known as right-click menus, because they are typically called forth by
right-clicking on something. Right-click menus are used in these areas:
•

In the Explorer, right-click to show options for working with folders and forms.
Note: These options are not available by any other means.

•

In a form, right-click a field or other component such as a button to display commands that help you
complete or use that field.
You can also use the Edit menu for most of these same commands and options.

•

In a form, if you right-click outside a field, the context menu provides commands that help you use that
form.
You can also use the Actions menu for most of these same commands and options.

•

Field lists have their own sets of context menu options.
See About Lists in Fields on page 71.

Setting User Preferences
User preferences in the web client
In the Web client, you can set a variety of display and operational preferences.
To configure the user preferences in the web client, select View > User Preferences. To see the effects of
your settings on forms that are open, close the forms and reopen them.
This table shows the descriptions of the available user preference settings in the web client:
Settings

Description

Behavior

These are the settings that control how the system behaves.

Infor Mongoose Basics User Guide | 15

Navigation in the System

Settings

Description

Layout

These are the settings that control how the user interface appears in Runtime
Mode.

Event System

These are settings that control how often the system checks for new messages in your Inbox, whether and how you are notified of new messages,
and how the system displays unread messages in the Inbox. There are also
settings that determine how you interact with certain Application Event
System forms.

Diagnostics

These are settings that control how the system works when you are trying
to diagnose problems and bugs while designing and modifying forms. You
need these only if you are actively involved in creating and modifying forms
in the system.
Caution: Configure diagnostics only if Infor Support has instructed you to
do so, or if you are debugging a form under development. Use these settings
to specify what information is recorded in the session log.

This table shows the descriptions of the available user preference settings in the Web Designer:
Settings

Description

Designer

These are settings that control aspects of how forms look and behave when
you are in the Web Designer. You need these only if you are actively involved
in creating or modifying forms.

Form Metrics

These are settings that control how forms and form components are displayed by default.

Infor Mongoose Basics User Guide | 16

Getting Help

Chapter 3: Getting Help

About the Help
Downloading the Latest Help
The latest help for this product is always available on www.docs.infor.com. If you are using this product
on-premises and you need to have the help on-premises, see your product's installation guide for instructions.
Copyrights and Trademarks
All topics, screens, and code are protected by U.S. copyright laws and international treaties.
Copyright Infor. All Rights Reserved. The word and design marks set forth herein are trademarks and/or
registered trademarks of Infor and/or related affiliates and subsidiaries. All rights reserved. All other trademarks
listed herein are the property of their respective owners. www.infor.com
The fonts in C39Tools are copyright 2002 Jerry Whiting. Portions copyright 2002 Azalea Software, Inc. Seattle
WA. All rights reserved. The fonts in C128Tools are copyright 2002 Azalea Software, Inc. Seattle WA. All rights
reserved.
Restricted Rights Legend
Use, duplication, or disclosure by the Government is subject to restrictions as set forth in subparagraph
(c)(1)(ii) of the Rights in Technical Data and Computer Software clause at DFARS 252.227-7013, and Rights in
Data-General at FAR 52.227.14, as applicable. Name of Contractor: Infor, 13560 Morris Road, Suite 4100,
Alpharetta, GA 30004 USA.

Using the Help
Context-sensitive help are extensively available in the system. By using context-sensitive help, you can get
help when and where you need it, so you can get back to the task at hand.
There are several ways for you to get help with a form, a field or other component on a form, or procedures
to accomplish various tasks.

Infor Mongoose Basics User Guide | 17

Getting Help
When you open more than one context-sensitive help topic during a session, the new help topic can open in
the same tab or window, or in a new tab or window. The new help topic opens according to the default option
you set for your browser.

Getting help on a form
1
2

Open a form.
Select Help > Current Form.

Getting help on a component of a form
Field-level help is available on some forms. In most cases where field-level help is not available, there is a
Related Topics link to a help topic that describes the fields.
Where available, you can get help on a field, button, menu, or other components of a form.
1
2

Right-click a field, button, menu, or other component of a form.
Select Help.
Alternatively, position the cursor on the component and select Help > Current Field, or press F1.

Getting general help
1
2

Click Help.
Select Contents and Search.

Searching for a topic in the Help
1

Click the Search icon.
If you are on the Help home page and you click the Search icon, then the search applies to the entire
collection.
If you are on any other page and you click the Search icon, then you can select between Search All or
Search Current Document options.

2

Specify a term or phrase and then press Enter.
Optionally, to reduce the number of matches, specify a search phrase with any of these search operators:
•

Use the Boolean AND between words
For example,specify item AND cost and then press Enter.
The search results will return contents that contain both item and cost.

Infor Mongoose Basics User Guide | 18

Getting Help
•

Enclose the words or phrase between quotation marks (" ")
For example, specify "item cost" and press Enter.
The search results will return contents that include exact matches of the phrase.

Getting form information
To get background and technical information about the current form:
1
2

Open a form.
Select Help > About This Form.

Printing single help topics
You can print help topics to use as quick reference.
1
2

Open a topic and click the Print icon.
Select Print this page.
Alternatively, right-click the help topic page and select Print.

3

Select a printer and then click Print.

