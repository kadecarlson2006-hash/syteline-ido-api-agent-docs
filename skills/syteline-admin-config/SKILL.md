---
name: "syteline-admin-config"
description: "Administration configuration - time zones, online help setup, multi-language interface, application schema metadata, metadata cache, next keys, Master Explorer folder customization"
version: "1.0"
tags:
  - syteline
  - admin
  - config
  - localization
  - schema
  - mongoose
---

# syteline-admin-config

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 7: Time zones
About time zones
Time zone information can be defined at different levels in the application.
Server time zone
In the Configuration Wizard, the administrator specifies the time zone for the database server. This time zone
displays on the System Parameters form.
Site time zone
A site's time zone is independent of the server time zone. It displays on the Sites form and can be changed.
Each site can have a different time zone. If the DateTime in Client Time Zone process default is not set, forms
and reports show the time zone of the site, and audit columns such as CreateDate and RecordDate also use
the site’s time zone.
User's time zone
When you set up a user on the Users form, you can select the local time zone to display the current time based
on the user's time zone.
This field is ignored unless the DateTime in Client Time Zone process default is enabled on the Process
Defaults form. If the process default is enabled and this field is blank, the value defaults to the time zone of
either the local user’s machine or the web server. If you enable the process default, we recommend that this
value be specified for each user and not be left blank.
Specifying information about time zones
You can set up new time zones or modify the information about existing time zones on the Time Zone Data
form.
Infor Mongoose Administration Guide | 127
 
Time zones

Configuring time zone data
To add new or update existing time zone entries, use the Time Zone Data form. Initialized system data
contains standard time zone names, but any time zone name can be specified as needed.  If legislation changes
the time zone in a particular area, that information can be updated on this form.
Daylight Saving Time has a start day and an end day.  This is specified as the first, second, third, fourth, or
last weekday of the month.  For example, Central Standard Time has a Daylight Saving Time of one hour that
starts on the second Sunday of March and ends on the first Sunday of November.
1 
When you add or update a time zone entry, specify the Offset Minutes for the time zone.
This is the offset to be used for translation, in minutes. The offset is the difference, in minutes, between
Coordinated Universal Time (UTC) and local time. All translations between UTC and local time are based
on this formula:
UTC = local time + offset minutes
2 
For Daylight Saving Time, provide this information:
Offset Minutes
Specify the offset to be used for translation, in minutes. This is usually 60, which is added to the local
time.
Start Month
Specify the month when the Daylight Saving Time starts.
Start Week
Specify the week when the Daylight Saving Time starts. You can select from the list whether the start
week is the first, second, third, or last week of the Start Month.
Start Day
Specify the day when the Daylight Saving Time starts.
End Month
Specify the month when the Daylight Saving Time ends.
End Week
Specify the week when the Daylight Saving Time ends. You can select from the list whether the end week
is the first, second, third, or last week of the End Month.
End Day
Specify the day when the Daylight Saving Time ends.
Infor Mongoose Administration Guide | 128
 
Time zones

Chapter 8: Online help
About the online help file system
All user documentation for Infor Mongoose is available in the form of online help from docs.infor.com. For
multi-tenant cloud (MTC) environments, access to this site is included as part of your subscription.
For on-premises installations, customers who do not want (for whatever reason) to access the help from the
web have the option to install and use the help on their local systems. On-premises customers also have
access to the help systems for the various Mongoose utilities (which are unavailable to MTC customers).
Help at docs.infor.com
Virtually all documentation related to Mongoose is now incorporated as part of the Mongoose online help
library. This includes information that was previously published in the form of stand-alone PDF publications.
The only exception is the Infor Mongoose Installation Guide, which is published as a separate help document.
To learn what publications are included in the Infor Mongoose documentation library, see the primary headings
in the help Table of Contents panel. Each of those headings represents a separate "module" that can be
published to PDF, should you want to print hard copies.
For additional information about using the help, see the User > Basics > Getting Help section of topics.
Help options for on-premises installations
If you are an administrator operating in an on-premises environment, and if, for whatever reason, you do not
want your users to access the help located on the web at docs.infor.com, you can install and use the help
locally.
To do this, you must first contact the Infor Download Center and request the files required for your release
version. This typically includes these deliverables:
•
A .zip file containing the Mongoose Core help files.
This file contains all the files required to access the base, or core, online help this product. These files
include the entire set of Mongoose help files as published on docs.infor.com for your release version of
Mongoose.
This also includes a set of instructions to properly install and implement the help from your local
environment.
•
A PDF file containing the Infor Mongoose Installation Guide.
Embedded within your installation is another set of files that contain the online help systems for the various
Mongoose utilities (Configuration Manager, Form Control, FormSync, and so on). As a rule, you should not
Infor Mongoose Administration Guide | 129
 
Online help

have to bother with these, as they are installed in the correct locations automatically during product installation
and configuration and accessible from each utility. Should you need to reinstall them for any reason, contact
your Infor Customer Support representative.
About the help URL
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
By default, the Infor Mongoose help is available on the Infor Documentation Central website (docs.infor.com).
This help is always the most current and includes information about the latest updates to the application
and the latest translated help. But it might sometimes not be applicable or helpful to customers operating
in an on-premises environment.
Note:  Infor OS Portal® In-context User Assistance (ICUA) context-sensitive help app for this application only
works with the help on this website.
The core help URL can be broken down into segments, each of which must be set up separately, similar to
this:
https://helpServerName/product/version/language-culture/folder/filename.html
Note:  The version segment is not used on the URLs of locally installed translated help and utility help.
The different segments of the Core help URL are generated by different settings within your application. To
define the base URL, folder, and help start page, use the Configuration Manager utility. To specify the help
language-culture designation of a particular language, use the Language IDs form. These settings can be
changed only by authorized system administrators.
This table shows the different segments of the help URL:
Infor Mongoose Administration Guide | 130
 
Online help

Description
Format
Help URL segment
The base URL contains this information:
•
The first part can be http or https, depending on
which protocol your website is set to use. We recom-
mend the use of https.
•
The helpServerName is the name of the server from
which the help files are to be accessed. By default,
the helpServerName is set to docs.infor.com.
•
The product segment is the product code or the ab-
breviated form of the product name, for example,
mg for Mongoose.
•
The version for which the help is produced, for exam-
ple, 2022.xx.
Note:  If you are seeking to use the help on docs.in-
for.com, to access the latest version of the help,
specify latest as the version.
•
The final forward slash (/) is required.
An example of a base URL is: https://docs.infor.com/
mg/latest/
https://helpServer-
Name/product/version/
Base URL
This setting is based on the system language being used
for your application, for example, en-us, which refers to
U.S. English, or es-mx, which refers to Mexican Spanish.
By default, the values are set to en-us.
Note:  Currently, the Mongoose help is available only in
U.S. English.
language-culture
Language and cul-
ture
This is the name of the subdirectory on the server in
which the help files reside; for example, mongooseolh.
folder
Folder
This is the file name of the help topic or the home start
page; for example, default.html, which refers to the help
start page, or a topic page which has a file name similar
to this: lsm1454147636124.html.
filename.html
File name
Setting the base URL and start page
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
This procedure is for the configuration of the base help URL and start page for the Mongoose online help. It
involves providing a base help URL and designating the help start page.
Infor Mongoose Administration Guide | 131
 
Online help

These values are pre-populated by default when you install Mongoose. So, in most cases, you should be able
simply to verify that these values are correct for your system. This procedure is provided especially for those
who want to update, install, and/or use the help files from an alternate location.
1 
Launch the Configuration Manager, and select the Applications tab.
2 
Select an existing application and then click Edit.
Note:  You can also perform this procedure within the context of creating a new application. For ease of
explanation in this procedure, we assume that you already have a configuration created, but the principles
are the same.
3 
In the Edit Application dialog box, select the Options tab.
4 
In the Help Information section, Help Server URL field, specify a base URL in this format: https://help
ServerName/product/version/
where:
•
The first part can be either http or https, depending on whether your website has been created as
an HTTP or HTTPS site; but it must match the designation for your web server.
Note:  If using HTTP, then you should enable the port for HTTP (by default, 80). If using HTTPS, then
you should enable the port for HTTPS (by default, 443).
•
helpServerName is the name of the server from which the help files are to be accessed. By default,
the helpServerName is set to docs.infor.com.
•
The product segment is the product code or the abbreviated form of the product name, for example,
mg for Mongoose.
•
The version is the version number for which the help is produced, for example, 2022.xx.
Note:  If you are accessing help from docs.infor.com, to access the latest version of the Help, specify
latest as the version.
If you are configuring help for on-premises use, the version segment of the URL must match with the
name of the version folder on the help installation path. For example, if the help installation path is
C:\inetpub\wwwroot\MG\2022.xx\en-us\mongooseolh\, then specify 2022.xx as the version in the base
URL.
•
The final forward slash (/) is required.
An example of a Help Server URL is: https://docs.infor.com/mg/latest/
5 
Verify that the Help Start Page field value is set to default.html.
6 
Verify that the Subfolder field value is set to mongooseolh.
7 
Click Save.
Configuring the help middle segment
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
Infor Mongoose Administration Guide | 132
 
Online help

The middle segment of a help URL designates the language and culture of the set of help files to be accessed.
By default, this value is set to en-US, but if you want to view the help in another language you can change it.
Then, if help for the designated culture exists, the help displays in that language. If not, the system reverts to
U.S. English (en-US).
Note:  Currently, the Mongoose help is available only in U.S. English. This topic and procedure apply primarily
to other Mongoose-based applications.
To modify this middle segment for an application:
1 
Launch Mongoose and open the Language IDs form.
The middle segment of the help URL is controlled by the entry in the Help Sub Dir column. This column
displays the name of the subdirectory on the web server that contains the online help for the selected
language.
Generally, the Help Sub Dir values are the same as for the Language ID, which is the "language-culture"
designation, when translated help files exist for a given language. But, by default, any language for which
translations are not provided, this field is set to U.S. English (en-US).
You can change the Help Sub Dir value so that the help points to a different language. For example, if
you wanted the interface to display in French (France), but the help to display in Canadian French, you
can change the Help Sub Dir for fr-FR to fr-CA.
2 
Select the language entry for which you want to redirect the help middle segment.
3 
In the Help Sub Dir column, enter the language-culture value for the language you want to use for the
help.
4 
Click Save.
Configuring help for on-premises use
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
In some cases, you might not want to access the online help from the Infor Documentation Central website.
Instead, you might choose to store and use your help on-premises. This might be true for cases like these:
•
Your company's firewall does not allow access to outside web sites.
•
Your company is not automatically upgrading the application on a regular basis, and you prefer not to
see information about new features that you don't yet have installed.
For those (and other similar) cases, we provide the option for you to download and access your help
on-premises. Be aware that, if you select this option, this help is never automatically updated. You must
manually download and install later versions as required.
If you choose to use a specific, on-premises version of the online help rather than the most current help on
Infor Documentation Central website (docs.infor.com), use this procedure:
1 
Download the help files to a local drive:
Infor Mongoose Administration Guide | 133
 
Online help

a 
From the Download Center, locate and download the help zip file for your version of Mongoose.
By convention, this file is named according to this format:
mg_version_MongooseCoreHelp_yyyymmdd.zip
where:
•
version is the release number for Mongoose.
•
yyyymmdd is the date on which the zip file was published.
b 
Copy this .zip file to the application server desktop.
c 
On the Application Server, go to C:\inetpub\wwwroot\, and look for the MG\10.x\en-us\mongooseolh
subfolder.
Note:  If this path and subfolder do not exist, create them. If the mongooseolh subfolder exists, delete
any files that might be in it.
d 
Unzip the help zip file directly into this subfolder.
Do not install it into any subfolder under that folder.
2 
Configure the base help URL and start page/file.
See Setting the base URL and start page on page 131.
3 
On a client, open your web browser, clear the cache, and allow popups.
4 
In Mongoose, select Help > Contents and Search.
The help contents home page displays in the browser window.
Updating the help files
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
If you are accessing help from docs.infor.com, there is no need to update help files. The help files at this
website are always the most up-to-date available.
If, however, you are accessing the help files from a local installation, such as in an on-premises setup, then
you might need to update the help files on your system, to ensure that you have the latest available help for
your installation.
To update the help files for your on-premises installation, use this procedure:
1 
From the Infor Product Download Center, locate and download the help zip file for your version of
Mongoose.
For example, if you were working in an on-premises installation of Mongoose 2021.12, you would look
for updates to that version of the MongooseCore.zip file.
By convention, this file is named according to this format:
mg_version_MongooseCoreHelp_yyyymmdd.zip
Infor Mongoose Administration Guide | 134
 
Online help

where:
•
version is the release number for Mongoose.
•
yyyymmdd is the date on which the zip file was published.
2 
Copy this help .zip file to the application server desktop.
3 
On the Application Server, go to C:\inetpub\wwwroot\, and look for the MG\version\en-us\mongooseolh
subfolder.
The version is the version number for which the help is produced, for example, 2022.xx. The name of the
version folder must match with the version specified in the Help Server URL field in the Configuration
Manager.
Note:  If this path and subfolder do not exist, create them. If the mongooseolh subfolder exists, delete any
files that might be in it.
4 
Unzip the help .zip file directly into this subfolder.
Do not install it into any subfolder under that folder.
Configuring the help URL for cloud access
Note:  This topic applies only to help being installed and used locally in an on-premises environment. It does
not apply to Multi-Tenant Cloud (MTC) customers. The settings described in this topic are usually configured
at installation and should not be changed. You should change these settings only if you are redirecting your
help access from the Infor Documentation Central website to a local or on-premises setup, or vice versa.
In cases where the online help for Mongoose has been configured for local access, you might come to a point
where you prefer to access the help from docs.infor.com.
Note:  In new installations, docs.infor.com is the default setting for online help. This topic applies only to
situations where you are not accessing the help from the this website but you want to.
1 
Launch the Configuration Manager.
2 
From the Applications tab, select the application for which you want to set the base help URL.
3 
Click Edit.
4 
In the Edit Application dialog box, select the Options tab.
5 
In the Help Server URL field, specify the base help URL, using this format:
https://docs.infor.com/mg/version/
where:
•
The first part is the base URL: https://docs.infor.com.
•
mg is the product code.
•
version is the version of Mongoose for which the help is generated, for example, 2022.xx.
Note:  To always access the latest version of the help, specify latest as the version.
•
The trailing forward slash (/) is required.
6 
In the Help Start Page field, verify that the value is: default.html
7 
In the Subfolder field, verify that it is set to: mongooseolh
Infor Mongoose Administration Guide | 135
 
Online help

8 
If you are using a help language other than U.S. English, launch the Language IDs form and verify that
the Override Middle Help URL setting is correct for your language.
Note:  Currently, the Mongoose help is available only in U.S. English.
9 
Click OK, and close the Configuration Manager.
Infor Mongoose Administration Guide | 136
 
Online help

Chapter 9: Multi-language interface
This application provides for a "multi-language interface"; that is, an interface in which some things can
display in one language, while other things display in a different language. In this application, that typically
means that the user interface, dialog boxes, windows, and so on, can display in one language. At the same
time, system messages can be set to display in another language. And finally, the online help can be set to
display in yet a third language.
Granted, most of the time, administrators and users would not want to do this, but it can be helpful to know
that you can, should the need arise.
It is also worth mentioning that most language settings can be made—and overridden—at various levels,
both system-wide and user-level.
This all starts in the language resource definitions, which are created and maintained using the Language
IDs form. That is where language settings for a particular language (culture code combination) are defined
and maintained.
About Language IDs
The Language IDs form provides information about the Language ID, the language Code, the String Table
Name, and other information pertaining to languages defined for the application.
Where the string tables and application message tables are available for a specified language, the Language
IDs form is used to select the appropriate tables for that language. For example, to specify a new language
for your application, select a Language ID from that column. The system automatically populates other
columns with appropriate values for that language. You can then further refine how the language designation
handles the language.
Note:  The values in the Language ID column are actually the culture codes.
Use this form to view and/or specify these attributes and resources for languages:
•
At the least, the Language ID (culture code), the language Code, and the name of the string table for that
language ID and code (String Table Name).
•
Optionally, a description for the language (Description).
This typically takes this format: NameOfLanguage (NameOfCountry). For example: Spanish (Venezuela)
The country name makes it possible to distinguish between a base language and a country or regional
dialect.
Infor Mongoose Administration Guide | 137
 
Multi-language interface

Note:  If you want the language descriptions to display in your local language (rather than the system
default language), you can use the DerDescription (derived) property of the Language IDs IDO. See
Providing for localization in language fields.
•
Optionally, the subdirectory that contains translated help files (Help Sub Dir).
•
Optionally, the language-culture (culture code) to be used to override the default help location designation
for a particular application (Override Middle Help URL).
•
Optionally, the message table used to display messages (Message Language).
Specify this only if you want system messages to display in a language other than the base Language ID.
•
Optionally, the font to be used to print reports for a specified language (Font Descriptor).
•
Optionally, the date format and numeric format used to print reports for a specified language (Date
Format and Numeric Format). The Date Format and Numeric Format columns apply only to SQL Server
Reporting Services (SSRS) reports.
You can customize Language IDs to create a mixed-language user interface. For example, you could revise
the default Language ID fr-FR (French [France]) by setting the help subdirectory to en-US (English [United
States]). With the Language ID set as fr-FR, the user interface would display in French, but the help would
display in English.
Note:  Before you upgrade to a new version, make sure that you do not have duplicate language codes or
other bad data. Otherwise, the upgrade will fail.
About language-culture codes and folders
This application uses standard industry language-culture designations to assign the language that displays
in the user interface, in system messages, and in the online help. This topic explains how these language-culture
designations are used in this application.
Culture codes and folders
A language name is paired with a region or country name to form a culture code. For example:
Culture Code
Language - Country
en-US
English - United States
en-UK
English - United Kingdom
es-ES
Spanish - Spain
es-AR
Spanish - Argentina
ja-JP
Japanese - Japan
Notice that a given language can have multiple countries associated with it, each culture code representing
a different dialect of that language.
Infor Mongoose Administration Guide | 138
 
Multi-language interface

In this application, culture codes are used as names of subfolders under the working directory's folder. Each
subfolder contains language-specific resources (which include the application's menus, dialogs, and strings,
translated into the designated language) and might also contain help files translated into that language.
When the application starts, the system determines the current site settings and user-default settings, along
with the settings in the Language IDs form, to display the user interface, the help, and system messages in
the designated language, based on these settings. In each case, the language used is based on the
language-culture designation.
If the language-specific resources are not available for your user-default settings, the user interface, messages,
and/or help display in the application's base language (U.S. English).
About language translations
Language translations for your application fall into two general categories: UI-related translations, based on
string tables for the application; and message-related translations, based on application message tables.
Translated strings
The application's forms database includes a set of "strings" tables, which contain translations of the text
strings used in form titles, field labels, buttons, and so on. The default strings table, for U.S. English, is named
Strings. Other strings tables have names like JapaneseStrings, FrenchStrings, and so on.
The strings table used for each language ID is determined by the settings for the application in the Language
IDs form. In the Sites form, the Forms Database Name should point to the Forms database that contains
the strings tables you want a specific site to use. This becomes the site default language for all users in that
site.
Translated messages
Application message strings are maintained in the ApplicationMessages table. The table includes default text
strings in U.S. English and any translations of message strings.
The language used for messages in an application is determined by the Message Language setting in the
Language IDs form.
Overriding language settings
For both sites and individual users, you can set and override the default language settings. This topic discusses
how language overrides work.
Levels of language selection and overrides
Language settings are made at a number of different levels and locations:
Infor Mongoose Administration Guide | 139
 
Multi-language interface

•
System-level settings:
At the lowest level, if no other language settings have been made, U.S. English or en-US is the default
value used for all language settings.
This value is set by default on the Sites form.
On this form, administrators can change the default site or system language to another language.
•
User-level settings:
At the next level up, assuming the user is using the web client in a browser, browser settings override the
default application language setting.
•
If the application is accessed through Infor OS Portal, then the user language preference, as set in
Infor OS Portal is used. This setting overrides the browser's language setting.
•
If the user has a default language preference set on the Users form, then that setting overrides all
lower settings in Infor OS Portal, browser, or the default application.
This setting is typically made by an administrator for the user. The setting is made on the Users form,
in the Login Information > Default Language field.
•
In the User Preferences dialog box, individual users can change the default language preference
for themselves. This setting overrides all other language settings.
To reset the language to the user-default language, select <User Default Language Setting>.
This language override information is saved and remains in effect the next time you log in to the
application, and until you reset the override.
Limitations to language overrides
Whether you are an end user or system administrator, there are some limitations to setting language overrides:
•
When you override the language, if you select a language ID for which language-specific resources are
not available, the missing resources display in the application's default language, which is U.S. English.
For example, for a defined language, the Strings table for the language might exist, but the Messages
table might not. In this case, the user interface would display using the new language, but any system
messages would display in U.S. English.
•
Drop-down calendars always display in the language of the user-default culture.
•
Graphs which are drawn by a third-party application might display text in the language of the user-default
culture.
•
Date and number formats in the application's user interface can only be changed through the Windows
regional settings on the computer where the application's client resides. If you are using a remote desktop
connection to log on to the system, this affects all users connecting to the application through that client.
Applying languages to your application
Your Mongoose-based application inherently supports a number of languages, but not all languages are
automatically loaded. Before you can use any supported language (other than the default language, U.S.
English), you must load it into the system, using the Language Management form. This can typically be done
only by authorized administrators.
Infor Mongoose Administration Guide | 140
 
Multi-language interface

1 
Open the Language Management form.
All supported languages display. Languages that have been loaded into the system display a check mark
in the Add column check box.
2 
Select the Add check box for the language that you want to load.
3 
Click Process.
The application processes and loads all language packages for which the Add option has been selected.
At the same time, if the Add check box for any currently available language has been cleared, that language
package is removed.
When processing is complete, the application displays a message that sumarizes all the language packages
that were loaded or removed.
4 
Click OK.
The application closes the message box and automatically saves your work.
Removing languages from your application
Your Mongoose-based application inherently supports a number of languages, but not all languages are
automatically loaded. Once a language package has been loaded, it remains active and available for use until
it is removed. This can typically be done only by authorized administrators, using the Language Management
form.
1 
Open the Language Management form.
All supported languages display. Languages that have been loaded into the system display a check mark
in the Add column check box.
2 
Clear the Add check box of the language you want to remove from your application.
3 
Click Process.
The application processes and removes all language packages for which the Add option has been cleared.
At the same time, if the Add check box has been selected for any language not currently loaded, that
language package is loaded.
When processing is complete, the application displays a message that sumarizes all the language packages
that were loaded or removed.
4 
Click OK.
The application closes the message box and automatically saves your work.
Infor Mongoose Administration Guide | 141
 
Multi-language interface

Chapter 10: Application schema metadata
Schema metadata about tables includes this information:
•
The application module to which a table belongs
•
Data about table triggers, such as whether a primary key is generated, whether inserts or updates are
allowed, and so on
•
Data about AlphaKeys, which are system-generated values used as keys
•
Data about AlphaKeys (or Next Keys), which are system-generated values used as keys
•
Data about user extended table (UET) inheritance
•
Data about views generated for multi-site tables
•
Data about application-specific table extensions, such as ERDBGW population for APS
The application refers to this metadata when generating table triggers.
Note:  When you create a new table, first try generating and testing table triggers without adding your table
to the metadata. If it works the way you want, do not add it to the metadata. Otherwise, refer to this section
and the related topics to determine how to add metadata to adjust the generation.
The Insert and Update Trigger Generation sectio of the help explains in much greater detail the specifics of
this metadata and its use in the system.
Defining application schema metadata
After you create a custom table or a custom column on a standard table, you must define any needed metadata:
1 
If necessary, use the Application Schema Modules Metadata form to define any custom modules.
2 
Use the Application Schema Tables Metadata form to perform these actions:
•
Assign your custom tables to the appropriate module.
•
Specify information about insert and update triggers on the table.
Note:  To exclude certain columns that typically trigger large amounts of replication generation, you
can register those columns in the AppTable table, using the Skip Replication Noisy Columns field
on this form.
•
When UETs are added to the currently selected table, specify any other tables to which the UETs
should be applied.
•
For backward compatibility with existing multi-site tables, assign a view name. The view name should
match the former table name. For example, a table formerly named item which is now item_mst
Infor Mongoose Administration Guide | 142
 
Application schema metadata

would have a view named item. Specify a column name that contains the site value. The combination
of view name and site value is used to create a view over the table.
3 
If a table has an AlphaKey (you want to generate Next Keys for a column in the specified table), use the
Application Schema Columns Metadata form to define this.
If you later remove a custom table or column from the application database schema, you must also remove
the corresponding row from the application schema metadata, if that row exists.
Generating application schema metadata triggers
To generate triggers using the metadata you defined, use the Trigger Management form, specifying the
tables where you changed metadata.
INSERT and UPDATE triggers on the tables are generated by the Trigger Management form. However, not
all triggers on all tables are generated by this form. For example, if Iup and Del triggers are required for a
table, they are loaded when the application is installed.
INSERT and UPDATE triggers require generation after you perform these actions:
•
Add columns to standard tables.
•
Add custom tables, if you want standard trigger functionality.
•
Impact the schema for User Extended Tables with the UET Impact Schema form. (This generates triggers
automatically and creates views for multi-site tables.)
•
Upgrade databases where the schema has changed. (This generates triggers automatically.)
•
Run the Database Migration utility. (This generates triggers automatically.)
Building and running application schema metadata scripts
You can dump the metadata into SQL scripts which can then be loaded into other Application Databases; for
example, at other sites where the same table/column changes are being made.
1 
In one site, define the metadata as described above.
2 
Use the Generate Application Schema Metadata Scripts form to create these scripts, as needed:
•
Module metadata, as defined on the Application Schema Modules Metadata form
•
Tables metadata, as defined on the Application Schem Tables Metadata form
•
Column (Next Keys) metadata, as defined on the Application Schema Columns Metadata form.
3 
Load the SQL scripts into other sites (Application Databases), which must also contain any applicable
new or modified schema elements.
4 
At these other sites, use the Trigger Management form to generate triggers for specified tables.
Infor Mongoose Administration Guide | 143
 
Application schema metadata

Viewing core triggers into which application code will be
inserted
1 
Open the Application Schema Triggers Metadata form.
2 
Only when creating a new one, specify a trigger name in the Trigger Name field.
Otherwise, this field is read-only.
3 
In the Stored Procedure with Application Code field, specify the name of the stored procedure that
contains application code to be inserted into the Core trigger.
4 
Save.
Infor Mongoose Administration Guide | 144
 
Application schema metadata

Chapter 11: Metadata cache
Maintaining or discarding the local metadata cache
When users log out of a session, the application caches metadata about IDOs and forms to two XML files on
the local computer. The file can persist between sessions, so that when users start a new session, the
application uses the cached data, and users can open forms quickly.
Thesse are the metadata cache files:
•
config]IDOMetadataCache[windowsuser.xml
•
config]FormsMetadataCache[windowsuser.xml
where:
•
config is the name of the configuration used for the session.
•
windowsuser is the user name (ID) of the user logged in to that session.
These files are stored in drive\documents and settings\windowsuser\Local Settings\Application Data\Infor\
WinStudio on the user's computer.
Discarding/Refreshing the cache
The cache files contain the last login timestamp. If the IDO or forms metadata has changed since the last
login, because of software patches or new customizations, the cache must be discarded and refreshed. Caches
are discarded when any of these events occur:
•
The application checks the "last changed" timestamp for the IDO and forms data against the last login
timestamp stored in the XML cache files. If the "last changed" timestamp is more recent than the last
login timestamp, the application discards the appropriate cache.
•
If a new user using a different language logs into the application using the same local computer, the
forms strings cache on the local computer is cleared automatically.
•
A user selects the Form > Definition > Unload form and global objects menu option to manually discard
both caches on the local computer.
•
An administrator clicks the Discard IDO Cache button in the Configuration Manager utility for the specified
configurations. This changes the "last changed" timestamp for IDO metadata, so the next time users
connected to that configuration log in, their IDO metadata cache is cleared.
•
An administrator clicks the Publish Form Change button in the Configuration Manager utility for the
specified configurations. This changes the "last changed" timestamp for forms metadata, so the next
time users connected to that configuration log in, their forms metadata cache is cleared.
Infor Mongoose Administration Guide | 145
 
Metadata cache

How "Last Changed" timestamps are updated
The objects database updates its "Last Changed" information by means of a trigger, but the forms database
does not.
To reset the "last changed" timestamp when form changes have been applied using SQL rather than through
the form server, use the Publish Form Change button in the Configuration Manager utility. Click this button
when either of these events occur:
•
You create new Application Event System handlers that suspend inserts. You might need to manually
mark form metadata as modified, because the application caches form information about whether new
operations are suspended.
•
You install an on-demand patch.
Note:  When you install a service pack, you do not need to click this button.
To reset the "Last Changed" timestamp when changes have been made to IDO metadata, use th Discard IDO
Metadata button in the Configuration Manager utility. This should be done when new IDOs have been added
or when new properties or methods have been added to existing IDOs.
Disabling metadata caching
Because the metadata cache could potentially consume a large amount of disk space on the local computer,
administrators can disable it by setting the Persist WinStudio Metadata Cache process default to 0 (zero).
Infor Mongoose Administration Guide | 146
 
Metadata cache

Chapter 12: Next Keys
About synchronization of Next Keys
"Next Keys" functionality allows long-running transactions to get a next value for a column entry, with or
without a prefix, and without blocking other processes that are doing the same thing. These kinds of stored
procedure calls can be set up using the Application Schema Columns Metadata form, in the Generator(s)
field.
The application database includes a NextKeys table that lists the last value used for alphanumeric keys whose
system-generated value can have a prefix. For example, customer orders could include a prefix such as "CO-",
so the order numbers would be CO-00001115, CO-00001116, and so on. The value in NextKeys is incremented
to determine the next system-generated value for the key.
Occasionally, the data in the NextKeys table gets out-of-sync with the contents of the other database tables
that contain the key values. When this happens, the application displays "duplicate key" error messages.
To prevent this from happening, administrators should periodically run the Synchronize Next Keys form.
This form synchronizes the NextKeys table with the contents of the database tables that contain the key
values.
The information on that form can help you determine if some values are not being used as planned. For
example, if someone accidentally entered CO-40000 instead of CO-400 on a new customer order, you would
run out of available numbers with that prefix much sooner than planned. If you catch it before more orders
are entered, you could delete the problem order and add it back with the correct number.
Synchronizing Next Keys
To prevent Next Keys data from getting out-of-sync, we recommend that administrators periodically use the
Synchronize Next Keys form to synchronize Next Keys data.
1 
Open the Synchronize Next Keys form.
2 
Optionally, copy the data displayed in the grid to a spreadsheet.
This makes it easier to compare the contents of the grid after processing, so that you can assess what, if
anything, changed.
3 
Click Process.
4 
Optionally, compare the before and after contents of the grid and assess whether any corrective actions
might be needed.
Infor Mongoose Administration Guide | 147
 
Next Keys

About Next Key definitions
Next Keys functionality allows long-running transactions to get a next value for a column entry, with or without
a prefix, and without blocking other processes that are doing the same thing. These kinds of stored procedure
calls are set up using the Application Schema Columns Metadata form, in the Generator(s) field.
How Next Key definitions work
The root stored procedure for Next Key calls looks like this:
EXEC @Severity = dbo.Next2SubKeySp 
   @TableName = @TableName
 , @ColumnName = @ColumnName
 , @Prefix = @Prefix
 , @KeyLength = @KeyLength
 , @SubKey = @SubKey
 , @Increment = @Increment
 , @KeyVal = @KeyVal OUTPUT
 , @Infobar = @Infobar OUTPUT 
For example: Application code might have a Customer Order Number column that might or might not use
a prefix. So, a customer order number with a prefix of TG might be TG00000109, and a customer order number
without any prefix might be 0000000547.
If two processes, then, call the Next2SubKeySp code for the CustomerOrder table and CoNum column at the
same time, requesting the next key for prefix TG, they would get the next two values from the NextKeys table
(TG00000110, and TG00000111). There would be no blocking and both values would be stored in the NextKeys
table.
Synchronizing Next Key values requires the system to go back to the base tables to figure out what the current
high values are for each prefix. This is where the Next Key definitions come into play. These definitions contain
the metadata that indicate what tables and columns have Next Key associations.
About primary and secondary specifications
In some cases, you might have an archival table that includes the same Next Key associations as another
table. For example, you might have a table, named CustomerOrderHistory, that holds historical data that has
been archived from the CustomerOrder table. The CustomerOrder table, then, would be considered the
"primary" table. The CustomerOrderHistory table would be considered a "secondary" table that also needs
to be checked when synchronizing Next Keys, since either table could contain the current maximum key
values.
About Sub Keys
Sub Key columns allow a Next Key to be grouped based on the value of another column.
For example, an InteractionID might be stored in a table called DailyInteraction, based on the
DayOfInteraction sub key column. The desire in this case is to have InteractionID start at 1 and increment
with each additional entry, but only for a given DayOfInteraction value. To view the current maximum value
for a given day, then, you would change the Sub Key value in the Next Key request.
Infor Mongoose Administration Guide | 148
 
Next Keys

Table 1: DailyInteraction table
InteractionID
DayOfInteraction
1
07/04/1776
2
07/04/1776
3
07/04/1776
1
07/05/1776
2
07/05/1776
Next Key synchronization in this case would look at the DailyInteraction table and figure out the current
maximum InteractionID for each DayOfInteraction value. In this example, there would be two sets of values:
07/04/1776, 3 and 07/05/1776, 2. These values would be inserted into the NextKeys table so that Next Key
processing could proceed correctly for that table and column.
About the Master Table and Master Column
The Master Table and Master Column options are used only for a specialized integer-only case of sub key
processing. Next Keys are generally character-based, but in certain cases, they are integer-based with a Sub
Key column defined in a separate table.
For example, suppose that a Journal table has a BatchID column and a Sequence column, and the Sequence
column is integer-based. In this case, a separate table containing the list of BatchID values, which are sub
key values, must be defined. Non-Master sub key data is always character-based and Master sub key data is
always integer-based.
Defining Next Key metadata
Next Key specifications for new tables and columns can be defined using metadata that the
PopulateAllNextKeysMetaSp stored procedure can use when synchronizing or otherwise processing Next Key
data.
To create a Next Key metadata definition, use this procedure:
1 
Open the Next Key Definitions form.
2 
Initiate a new record.
3 
In the Primary section, provide this information:
Table
(Required) Select the table that contains the column for which you want to define a Next Key.
Column
(Required) Select the column for which you want to define the Next Key.
Sub Key
Select the column you want to use for the sub key values.
Infor Mongoose Administration Guide | 149
 
Next Keys

Master Table
If the Next Key you are defining includes the use of a master table, select the table.
Master Column
If the Next Key you are defining includes the use of a master table, select the column to be used as the
master column.
4 
If the Next Key involves the use of a secondary table, in the Secondary section, provide this information.
Table
Select the table that contains the column for which you want to define a secondary Next Key.
Column
Select the column for which you want to define the secondary Next Key.
Sub Key
Select the column you want to use for the secondary sub key values.
5 
Save your work.
Purging Next Keys data
Depending on how you have your Next Keys definitions set up and the amount of traffic on your system, Next
Keys data can accumulate and eventually impair performance. Next Keys data is not automatically purged
from the system, by design. For this reason, we recommend that you periodically purge the Next Keys data
in your system.
1 
Open the Synchronize Next Keys form.
2 
Optionally, copy the data displayed in the grid to a spreadsheet.
This makes it easier to compare the contents of the grid after purging, so that you can assess what, if
anything, changed.
3 
Click Purge.
4 
Optionally, compare the before and after contents of the grid and assess whether any corrective actions
might be needed.
Infor Mongoose Administration Guide | 150
 
Next Keys

Chapter 13: The Master Explorer folder
About “pruning” the Master Explorer folder display
The Prune Explorer Folder Callout process default is used to specify a user-defined method to be called
during expansion of each Explorer folder node. The specification must be in this form: IDOName.MethodName
Note:  The IDOName and MethodName are determined by the application developers.
The purpose of this callout method is to “prune out” undesired form names from the Explorer node’s XML
according to criteria that is defined by the user.
This method must be defined as an IDO extension class method and can be on an existing or new application
IDO.
The callout method accepts these parameters:
Description
Parameters
This is the username (ID) of the user logged in to the application.
The framework supplies this value when the callout method is
invoked.
UserName (input, string)
This is the explorer XML for a given node that is loaded in re-
sponse to the framework LoadExplorer request. The framework
supplies this XML stream when the callout method is invoked.
OriginalExplorerXML (input, string)
This is a “pruned” version of the OriginaExplorerXML that is
passed in.
PrunedExplorerXML (output, string)
The OriginalExplorerXML string represents a serialized version of the LoadExplorerResponseData class. To
massage or prune any form name specifications out of that XML, the method must deserialize that XML,
manipulate the data in the resultant LoadExplorerResponseData class, and then serialize the XML again before
you assign the XML to the third output parameter.
To deserialize, use LoadExplorerResponseData lxrd = LoadExplorerResponseData.FromXml( OriginalExplorerXML
). To serialize, use PrunedExplorerXML = lxrd.ToXml().
Implementation of the desired algorithm for pruning the Explorer folder contents must be done by the
application developer. A method template is provided and the user implementation code is included between
these two comments:
// Begin user implementation
// End user implementation
Infor Mongoose Administration Guide | 151
 
The Master Explorer folder

Pruning the Master Explorer folder display
It is up to application developers to implement the desired algorithm for pruning the Master Explorer folder
contents.
1 
Write the appropriate pruning method.
To learn how to make use of the input parameters and how to format the required output value, see
Prune Explorer Folder callout method template on page 152.
2 
Specify the method in the Prune Explorer Property Callout property.
The framework calls this method and passes the appropriate input values into the method when you click
an Explorer folder to expand the folder contents.
Prune Explorer folder callout method template
Implementation of the desired algorithm for pruning the Explorer folder contents must be done by the
application developer. We provide this method template to leverage the LoadExplorerResponseData class
to deserialize and serialize the OriginalExplorerXML and PrunedExplorerXML input or output parameter.
[IDOMethod]
public int method_name( 
   string userName, 
   string originalExplorerXML, 
   ref string prunedExplorerXML )
{
   int result = (int)StdMethodResult.Success;
   LoadExplorerResponseData expRespData = null;
   if ( string.IsNullOrEmpty( userName ) )
      prunedExplorerXML = originalExplorerXML;
   else
   {
      expRespData = LoadExplorerResponseData.FromXml( originalExplorerXML );
      // Begin user implementation
      // End user implementation
      prunedExplorerXML = expRespData.ToXml();
   }   
   return result;
}
Note:  The method_name above must be replaced by whatever method name the developer chooses to use.
The content of the method must include the template code provided above.
One possible criteria to determine form names to be pruned is whether the user has permissions to execute
a given form. The following sample C# code can prune form names that do not have executable permissions
for a given user. This sample implementation also illustrates how to build a list of executable form names
and then prune the form names from the LoadExplorerResponseData class instance.
// Begin user implementation
ListString executableForms = new ListString();
using ( AppDB db = IDORuntime.Context.CreateAppDB() )
{
  try
Infor Mongoose Administration Guide | 152
 
The Master Explorer folder

  {
     // Get list of form names (ObjectName1) and associated executable flag 
     // for each 
     using ( SqlCommand cmd = db.Connection.CreateCommand() )
     {
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "select ObjectName1, " +
        "CASE WHEN SUM(ISNULL(aau.ExecutePrivilege, 0)) >= 1 THEN 1 ELSE 
           0 END " +
        "FROM AccountAuthorizations aau " + 
        "INNER JOIN UserNames una ON una.Username = N'" + userName + "' " + 
        "WHERE aau.ObjectType = 0 AND " +
        "aau.ObjectName2 = aau.ObjectName1 AND " +
        "aau.UserFlag = 0 AND aau.Id IN ( " + 
        "SELECT ugm.GroupId " +                  
        "FROM UserGroupMap ugm " +                 
        "WHERE ugm.UserId = una.UserId ) " + 
        "group by ObjectName1";
        using ( IDataReader reader = db.ExecuteReader( cmd ) )
        {
           while ( reader.Read() )
           {
              if ( (int) reader.GetValue(1) == 1 )
              { 
                 string formName = reader.GetString(0);
                 if ( formName != string.Empty )
                    executableForms.Add( formName );
              }
           }
        }
     }
  }
  catch
  {
  }
}
// Prune form names from node that aren't in list of executable forms
var node = (FormServer.Protocol.ExplorerNodeDef) null;
int idx = 0;
while ( idx < expRespData.Nodes.Count )
{
   node = expRespData.Nodes[idx];
   if ( node.ObjectType == WinStudio.Enums.ExplorerObjectType.NormalForm || 
        node.ObjectType == WinStudio.Enums.ExplorerObjectType.QueryForm )
   {
      if ( !executableForms.Contains( node.ObjectTextData ) )
         expRespData.Nodes.RemoveAt( idx );
      else
         idx++;
   }
   else
      idx++;   
}
// End user implementation
The ido.method: AccountAuthorizations.PruneExplorerFolder is included in the Core framework as a sample
callout.
This method leverages the method template and sample implementation code included above. AccountAu
thorizations.PruneExplorerFolder must not be used for an actual implementation as it is not designed for
efficiency when running against a large repository of forms and does not include robust error handling.
However, AccountAuthorizations.PruneExplorerFolder can be specified as the value for the Prune Explorer
Callout process default as a sample test implementation.
Infor Mongoose Administration Guide | 153
 
The Master Explorer folder

Troubleshooting problems with the Prune Explorer callout
method
If the Prune Explorer Callout method is defined in the process defaults, the framework generates and sends
a log message when ever it is invoked. This log message can be viewed in the LogMonitor application, which
can be run on a Mongoose Application Server, or in the log provided by the IDORuntimeHost.exe application.
Success or error messages are in the log.
Infor Mongoose Administration Guide | 154
 
The Master Explorer folder


