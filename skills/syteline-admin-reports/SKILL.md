---
name: "syteline-admin-reports"
description: "Reports administration and feature management - report setup, Crystal Reports, SSRS, report options, feature flags, enabling/disabling features"
version: "1.0"
tags:
  - syteline
  - admin
  - reports
  - features
  - mongoose
---

# syteline-admin-reports

Source: Infor Mongoose Administration Guide, Release 2026.x

Chapter 14: Reports
Defining a custom paper size
The application can use many different paper sizes when printing or generating report outputs. If the standard
predefined sizes are not sufficient for your needs, you can define your own custom paper sizes.
To define a custom paper size, use this procedure:
1 
Open the Report Paper Sizes form and initiate a new record.
2 
Provide this information:
Paper Size
Provide a descriptive name for your paper size.
For example, if you were creating a paper size for 2 inch by 4 inch mailing labels, you might name your
paper size as 2X4Labels.
Note:  All system standard sizes are named using ALLCAPS (all capital letters). To make your custom
paper sizes easier to identify in lists, we recommend the use of mixed case names.
System Record
Verify that this check box is not selected.
Note:  This option indicates that the size is defined as a standard size by Vendor Developers (only) and
that the size cannot be modified. Unless you have Vendor Developer editing permissions, this option
should be disabled.
Report Orientation
Select whether you want this paper size to use a Portrait orientation or a Landscape orientation.
3 
Use one set of the Width and Height fields to set the dimensions for your paper size.
In the case of our example, you would use the Width (in) and Height (in) fields for the dimensions. Notice
that, when you enter the 2 for the width, the width for the pixels and millimeters are automatically
calculated and displayed as well.
Note:  You must specify a value for the Height that is greater than the Width. If you specify a value for
the Height that is less than the Width, the system displays an error message.
For example, if you tried to specify paper dimensions with the Height = 37 mm. and the Width = 26 mm.,
the system would display this error message:
Height (px) must be greater than or equal to 140. 
Infor Mongoose Administration Guide | 155
 
Reports

This is because the application translates and calculates all paper size dimensions in terms of pixels. The
Height in this case is equal to 98 pixels, which is less than the Width, which has a converted value of 140
pixels. The fact that the Height is less than the Width results in the error message.
For reports that use a Landscape orientation, the values for the Height and Width are flipped at the time
the report is printed.
4 
If you are a Vendor Developer, and you want to prevent others from modifying or deleting this size, select
this option.
In this case, the Width and Height fields are all now read-only.
Note:  You must set the Width and Height fields BEFORE you select this option. If you do not, you cannot
define the width or height for the size.
5 
Save your work.
Your new paper size is now available on any field where users can select the paper size for a report.
Adding a filename prefix parameter to a report
When you create a report that runs as a background task, you can add a parameter to the event handler for
the background task that designates a prefix for the filename of the output. This can make it easier to search
for and locate the report file later. This procedure is particularly helpful when working in a Multi-Tenant Cloud
(MTC) environment.
By default, TaskMan assigns the name (not caption) of the report form as the filename prefix. The procedure
in this topic allows you to use your own custom prefix in place of the report name. This procedure is best used
to define a prefix that does not change whenever the report is run.
Note:  Alternatively, you can use an Application Event System (AES) event handler to assign a filename prefix
to the report file on a dynamic basis. For that procedure, see Assigning a report filename prefix using the AES
on page 157.
1 
Open the report's form in the Web Designer.
2 
From the Edit menu, select Event Handlers.
3 
In the Event Handlers dialog box, locate and select the GenerateReport event handler, which has a
Response Type of Run Background.
4 
Click Edit.
5 
In the Response section of the Event Handler Properties dialog box, select Parameters.
6 
Click the ellipses (•••) button.
7 
In the Event Handler Parameters dialog box, click Type Specific Parameters.
8 
In the Edit Background Task Name and Parms dialog box, in the Profile Parms field, enter the string
to use as the filename prefix for the report outputs.
Make sure to enclose the prefix string in double quotation marks (" ").
Infor Mongoose Administration Guide | 156
 
Reports

Note:  This parameter must be the ninth parameter listed in this field. This means that, if the Profile
Parms field does not already contain eight parameters, you must add enough commas to make your
prefix string the ninth parameter.
9 
Click OK, OK, OK, and Done.
10 Exit the Web Designer.
The system applies the new filename prefix to the report output files.
You can verify that this procedure was successful by running the report and then checking the filename of
the resultant report output file.
Assigning a report filename prefix using the AES
When you create a report that runs as a background task, you can assign a prefix to the filename for the output
by means of the Application Event System (AES). This prefix can make it easier to search for and locate the
report file later. This procedure is particularly helpful when working in a Multi-Tenant Cloud (MTC) environment.
By default, TaskMan assigns the name (not caption) of the report form as the filename prefix. The procedure
in this topic allows you to use your own custom prefix in place of the report name. This procedure is best used
to specify dynamically changing prefixes that might depend, for instance, on the identity of the intended
recipient, a product name, or some other value that changes, depending on the context.
Use the procedure in this topic to assign a filename prefix to a report output file, by means of an AES event
handler.
Note:  Alternatively, you can use a parameter in a form event handler to assign a new, more permanent,
filename prefix to the report file. For that procedure, see Adding a filename prefix parameter to a report on
page 156.
1 
Open the Event Handlers form.
2 
From the Applies To Objects drop-down list, select the report for which the event handler is to be used.
3 
Use the Event Actions form to create any event actions in which you want the filename prefix to be used.
For example, you might want to use a Set Values event action to set the new value to be used as the
filename prefix.
4 
When you are finished defining all the actions you want for the handler, in the Event Handlers form,
select the Activate option.
5 
Save.
The system creates and activates the AES event handler. The next time the report runs, the application uses
the specified filename prefix for the report output file.
You can verify that this procedure was successful by running the report and then checking the filename of
the resultant report output file.
Infor Mongoose Administration Guide | 157
 
Reports

Troubleshooting reports
Several common problems can occur when users try to preview or print a report. These are some of the more
common ones with possible solutions:
•
Reports do not print.
See Reports do not print on page 159.
•
Scheduled reports do not run as scheduled.
See Scheduled reports do not run as scheduled on page 159.
•
Report outputs do not pick up header/footer variable values.
See Report outputs do not pick up header/footer variable values on page 159.
•
The report generates a "File not found" error message.
See File not found on page 160.
•
Users outside the network are not receiving forwarded reports.
See Users outside network not receiving forwarded reports on page 162.
•
Labels are not being replaced with Strings table values.
See Labels not being replaced with Strings table values on page 163.
•
No report output is found.
See No report output on page 164.
•
Reports fail intermittently with errors
See Intermittent failures with errors on page 165.
•
Reports fail with an error code.
See Reports fail with error code on page 165.
•
Notes do not print with a report.
See Notes do not print on page 165.
•
Report gets an Error 13 - Type Mismatch.
See Error 13 - Type Mismatch on page 166.
•
Report gets an Error 128 - Error Running Report.
See Error 128 - Error Running Report on page 166.
•
Report gets an Error 534: Error Detected by Database DLL.
This is similar to and has the same possible solutions as a "File not found" error. See File not found on
page 160.
•
Running a report produces the error: "This field name is not known."
See This field name is not known on page 166.
•
No users are receiving email messages about background tasks (including reports).
See No users are receiving email messages on page 167.
Infor Mongoose Administration Guide | 158
 
Reports

Reports do not print
Symptoms
Runtime users cannot print reports.
Possible solutions
•
Make sure the Infor Mongoose TaskMan service is started on the application (utility) server.
•
Verify that TaskMan is set up as a Windows service that logs on as with a user account created to access
printers.
•
Verify that the user account configured as the owner of the Infor Mongoose TaskMan service has print
privileges under Windows for the default printer, plus all printers listed on the Report Options form.
•
Verify that a default printer has been configured for the server on which TaskMan resides.
•
If the report being printed requires a printer other than the default printer on TaskMan's server, verify
that the correct printer is defined for the report on the Report Options form.
•
Verify that the user account that was used to run the Infor Mongoose TaskMan service is the same user
account that was used to map the printers on the TaskMan machine.
•
Verify that the printer was mapped using the printer name, not the share name.
•
Use SQL Profiler to verify that only one instance of TaskMan is monitoring the application database. See
Using SQL profiler to trace TaskMan instances on page 125.
Scheduled reports do not run as scheduled
Symptoms
Tasks that have been scheduled to run as background tasks using the Background Queue form are submitted
but do not get processed when scheduled. The report task status remains WAITING.
Possible solution
Verify that the Infor Framework Event Service is running. If necessary, restart the service and set it to start
automatically.
Report outputs do not pick up header/footer variable values
Symptoms
The report output for predefined DataView reports or reports based on a supplied SSRS template do not
display the values of one or more global variables in the header or footer. Instead, they display the name of
the variable (for example, BG_COMPANYNAME) or the space is blank.
Infor Mongoose Administration Guide | 159
 
Reports

Possible solutions
The system looks for the value to replace this variable from a field on the System Parameters form. If this
field is null or missing, there is no value to pick up.
Note:  In some Mongoose-based applications, the System Parameters form is known as the General
Parameters form.
•
If your application has a System Parameters form, check and verify that there is a Company or Company
Name field and that it has a value specified. If the field is there, but there is no value, specify a value for
the field. If the field is missing, perform these procedures:
1 
Use the Sql Columns form to add a column to the "parms" table.
This column must be named company, set to the nvarchar data type, be 40 characters in length, and
be nullable.
2 
Starting with the IDOs form, create a new IDO to extend and replace the Parms IDO.
3 
Create a new property for the IDO you just created. This property must be bound to the "par.company"
column you created earlier.
4 
Modify the System Parameters form by adding an Edit field component bound to the "company"
property.
Make sure you also add a field label (Static component).
5 
After saving the System Parameters form and exiting the designer, enter a value into the new field
and save it.
6 
Make sure your system is set to unload IDO metadata with global objects. Then clear the metadata
cache (Ctrl+U) and restart the report form.
•
If your application has a General Parameters form, check and verify that there is a value specified in the
Company field on the Address tab.
File not found
Symptoms
The report generates a "File not found" error message.
Possible solutions
This error message usually indicates an error in the programming of the report. Check for messages generated
when the task ran in the Background Task History form, or check the reports error log (TaskMan_Path\Report\
Errors\userID). Either of these might give more information about the problem.
It is also possible that the computer responsible for running reports has not been configured properly.
There are a number of things that can cause this error:
•
Check the Background Task History form to make sure there are no uninterpreted V(…) or P(…) keywords
in the parameters.
Infor Mongoose Administration Guide | 160
 
Reports

Finally, sub-reports are linked to the main report by parameters on the subreport. When Notes sub-reports
are linked in, the ShowInternal, ShowExternal, Rowpointer, and pConnectionString subreport parameters
must be linked back to the appropriate main report fields (typically, two main report parameters).
In addition to these four parameters, any other parameters used in the sub-report must also be linked
in the properties. Finally, the parameters linked in the properties should allow null values or have Null
as their default values.
If this is not done, the system expects these values to be passed from the originating form. If the wrong
types of values are passed, the system generates an error. If no values are passed, the report usually
"hangs" when run through TaskMan, while the report engine tries to prompt for these values on the
TaskMan machine.
•
The parameters passed by the report form are in the wrong order or are of the wrong data type, which
can produce a T-SQL error.
For example, if a string or date parameter is plugged into an integer parameter, SQL generates an
exception. Following the remedial steps in the next option catches these errors as well.
•
A Transact-SQL error occurred in the stored procedure.
To get a more meaningful error message for a T-SQL error, run the report stored procedure through the
SQL Server Management Studio. The easiest way to do this is to print the report and copy the report
parameters from the Background Task History form. Paste these in as the parameters for the report
stored procedure in SQL Server Management Studio, making these changes:
•
Enclose character and date parameters in single quotes.
•
Replace empty parameters with NULL.
If there is a T-SQL error, this will give a line number and error description. The most common SQL errors
in report stored procedures are data truncation errors. Many report stored procedures use temporary
tables created using SQL data types. These tables are often populated from variables declared with
user-defined data types. The lengths of many of these UDFs have been increased (for example, address
fields for an internationalized site ID). This can cause a SQL exception by executing INSERT or UPDATE
statements against the temporary tables using these variables, or applying the CONVERT function to
them.
•
The report's stored procedure was changed, but the report definition file was not resynchronized with
the stored procedure.
•
The stored procedure makes a call to RaiseErrorSp or raiserrorsp. RaiseErrorSp and raiserrorsp are used
to generate a SQL exception with a user-specified error message. Neither of these two stored procedures
should be used in report stored procedures.
•
The computer responsible for running reports must have:
•
Access to the TaskMan folder (where TaskMan.exe and RunReport.exe are installed) on the server
where TaskMan is running; OR
•
The TaskMan URL, if previewing over the Internet.
The easiest way to check this is to select Run from the Windows Start menu and try to run a report.
•
In rare cases, this error occurs if the RunReport.exe has become corrupted.
If this is the case, running the report through TaskMan produces an error code of -1,073,741,819 and the
report fails.
Infor Mongoose Administration Guide | 161
 
Reports

Users outside network not receiving forwarded reports
Symptoms
Users and customers outside your network are not receiving automatically forwarded reports.
Possible solutions
•
If only certain users are not receiving the reports, but others are, verify that report options are set correctly
for those users, by performing these actions:
1 
On the Background Task Definitions form, click Report Options.
2 
For the report and user you are checking, verify that the Email Notification option is set to Yes.
If Attach Report is set to Yes, you must also set Email Notification to Yes.
•
If no users outside your network are getting the reports, verify that the Exchange Server through which
the system routes email is set to relay email, by performing these actions:
1 
Open the Intranets form, Reports/TaskMan tab, and make note of the contents in these fields:
•
SMTP Server
•
SMTP Server Port
•
SMTP From Email
2 
Click Start > Run.
3 
In the Open field, specify cmd, and then click OK.
The system displays a Windows Command Prompt window.
4 
At the command prompt, enter this command: telnet
5 
At the Microsoft Telnet command prompt, enter this command: set localecho
6 
At the prompt, enter one of these commands: o SMTPserver SMTPport or open SMTPserver SMTPport,
where:
•
SMTPserver is the name of the SMTP server you collected from the SMTP Server field
•
SMTPport is the port number you collected from the SMTP Server Port field in Step 1.
7 
At the SMTP server prompt, enter the command: hello
On some systems, this command might vary; for instance, you might have to enter the command as
HELO or some other variation. The SMTP server responds with a message that ends with Hello, followed
by the IP address.
8 
Enter the command: mail from:<SMTPfrom>
where SMTPfrom is the email address you collected from the SMTP From Email field in Step 1.
The SMTP server responds with a message ending in ...Sender OK.
9 
Enter the command: rcpt to:<recipient_email>
where recipient_email is the email address of the user you are trying to relay the reports to.
•
If the SMTP Exchange server is not set up to relay email, the server displays a message similar
to this:
550 5.7.1 Unable to relay for recipient_email
If you see this type of message, configure your SMTP Exchange server to relay the email. For the
procedure, see your Exchange server documentation.
Infor Mongoose Administration Guide | 162
 
Reports

•
If the SMTP Exchange server is set up to relay email, the server displays a message similar to
this:
250 2.1.5 recipient_email
If you see this type of message, continue with the next step.
10 Enter the command: data
11 Type a test message to send to the recipient.
12 When you are finished with your message, press the Enter key, followed by a period, followed by
the Enter key again.
This terminates the message and queues it for sending.
13 To exit the SMTP server session, type quit, followed by any other key press.
14 To exit the Microsoft Telnet session, type quit
15 To exit the command prompt window, type: exit
16 Confirm with the recipient that the test message was received.
Note:  If you receive the error "Could not open connection to the host, on port port_#", check the firewall
and virus software on the mail server to see if they are blocking the email.
Labels not being replaced with Strings table values
Symptoms
Labels on report outputs are not correct or are not being translated correctly.
Possible solutions
•
Verify that the report is configured to use the correct Strings table. It might be that the system has been
incorrectly configured and the report cannot find the correct Strings table.
To select the correct String table record on the Sites form, use the Site Name field on the System
Parameters form. Make sure that, for this site, there is a value in the Forms Database Name field of the
Sites form (in some versions, labeled as the Strings Table Specification field). If the forms database is
on a different server than the application database, the value for this field should also indicate the linked
server name, using this format: server_name.Forms_database
Note:  In some applications, the Sites form is known as the Sites/Entities form, and the System
Parameters form is known as the General Parameters form.
TaskMan then determines the proper Strings table name by searching the specified forms database for
the Strings table associated with the current session.
•
Find the Strings table value using a query.
You can check this using the SQL Server Management Studio by running this query on the application
database:
SELECT i.intranet_name, p.site, i.tm_path , s.strings_table
FROM parms p
Infor Mongoose Administration Guide | 163
 
Reports

     INNER JOIN site s ON s.site = p.site
     INNER JOIN intranet i ON i.intranet_name = s.intranet_name
If this SELECT action does not return any rows, then some piece of initialization data required by TaskMan
is missing.
•
Validate the Strings table name using a query.
To test the Strings table name using the SQL Server Management Studio, run this statement on the
application database to return the number of strings in the Strings table:
DECLARE
     @StringSQL AS NVARCHAR(255),
     @FormsDB AS NVARCHAR(255),
     @OwnerPos INT
SELECT @FormsDB = s.strings_table
FROM parms p INNER JOIN site s ON s.site = p.site
SELECT @OwnerPos = CHARINDEX( N'.dbo.', @FormsDb)
IF @OwnerPos <= 0 SELECT @OwnerPos = CHARINDEX(N'..', @FormsDb)
IF @OwnerPos > 0 SELECT @FormsDb = LEFT(@FormsDb, @OwnerPos - 1)
SELECT @StringSQL = N'SELECT COUNT(*)FROM ' + @FormsDB + N'.dbo.' +
l.StringTableName
FROM LanguageIDs l
WHERE l.LanguageID = 1033
EXEC (@StringSQL)
If the Strings table specification is invalid, this SQL code returns something similar to this:
Server: Msg 208, Level 16, State 1, Line 1
Invalid object name 'String_Table_Specification'.
•
Verify that you are using the correct version of TaskMan.
One indicator that you might not be using the correct version of TaskMan is an error message that says
"Invalid String Table Name" followed by a number.
No report output
Symptoms
Reports might be marked as Started and Completed in the Background Task History form, but there is no
output in the TaskMan\Reports\OutputFiles folder.
Possible solutions
There are probably two or more instances of TaskMan monitoring the same database. To verify this, shut
down the instance of TaskMan you think should be monitoring the database and resubmit the report. If the
Background Task History record still gets updated, then there is at least one more instance of TaskMan
monitoring the database. To find out if this is the case, launch the SQL Profiler against the database to see
what hosts were polling.
Infor Mongoose Administration Guide | 164
 
Reports

See Using SQL profiler to trace TaskMan instances on page 125.
Intermittent failures with errors
Symptoms
The same report is run a number of times, sometimes running successfully, and other times failing with errors.
This happens for all reports. Whether it succeeds or fails appears to be random.
Possible solutions
This could occur because of the same issues described in No report output on page 164.
Alternatively, there might be two instances of TaskMan: one has the privileges needed to run reports, but the
other does not.
Reports fail with error code
Symptoms
Reports fail to process and return an error code of 1,073,741,819.
Possible solutions
If you see this return code, an executable might have become corrupt. Try double-clicking on the RunReport.exe
file. If it does not open dialog boxes, the executable files have been corrupted, possibly by a virus. Delete
them and copy them again from the installation source.
Notes do not print
Symptoms
You print a report, but the notes do not print along with it.
Possible solution
Verify that notes are set up correctly to print with the report.
Infor Mongoose Administration Guide | 165
 
Reports

Error 13 - Type Mismatch
Symptoms
There is a data type mismatch between either what is passed from the form to the report file, or from the
report file to the stored procedure.
Possible solutions
This can occur if the form passes the parameters in the wrong order, or a parameter is not defaulted correctly.
For example, a form might be passing in a blank for the Show Header check box.
If you get this error, try selecting the check box and running it again.
If this does not work, try selecting every check box and putting a value in every field.
Finally, try running the stored procedure through the SQL Server Management Studio with the same parameters
as those in BGTaskHistory, replacing blank parameters with NULL, and putting single quotes around strings,
dates, and guids.
Error 128 - Error Running Report
Symptoms
Reports fail with the message:
Error running report. This field name is not known.
Possible solutions
Verify that the report field was correctly mapped to the appropriate stored procedure field. The solution is
similar to the solution for type mismatches.
For more information, see Error 13 - Type Mismatch on page 166.
This field name is not known
Symptoms
Running a report produces the error:
This field name is not known.
Infor Mongoose Administration Guide | 166
 
Reports

Possible solutions
Having a report field that was not successfully mapped to a stored procedure field, can sometimes cause this.
In these cases, the unmapped stored procedure field will be part of the error message; for example:
This field name is not known : {Rpt_Ap01FRISp;1.TcAmtAmtPaid} 
Make sure the report field is in the stored procedure’s result set, and set the data source location as described
in the Error 534 topic.
No users are receiving email messages
Symptoms
No users are receiving email messages about background tasks (including reports).
Possible solutions
•
Verify that the Infor Mongoose TaskMan service is set up as a Windows service that logs on as a user
account. If TaskMan is configured as a Local System Account, email notification cannot be sent.
•
Verify that the user account configured for the Infor Mongoose TaskMan service under Windows has email
privileges in your mail system.
•
On the Intranets form, verify that the Send Email Notification field is selected. TaskMan gets the value
that is set here for the first database TaskMan is configured to use, and then uses that value for all
databases to which it connects from the Application Server.
If you do not know which database is the "first" database, you can select the Send Email Notification
field for another database. Then, no email will be sent and no error messages appear in the event logs.
You must either determine what the "first" database is and set the option there, or set the option in all
databases. Conversely, to stop email notifications, either clear the option in the "first" database or clear
the option in all databases.
•
Verify that the recipient's user ID specifies an email address in the Users form.
•
The system requires a default email profile, or a profile called "TaskMan," on the server where TaskMan
resides. To verify this, if Microsoft Outlook is installed, open its properties (right-click on the Outlook icon
and select Properties). Click Show Profiles. If there is no TaskMan profile, copy one that already exists,
copy it, and rename it TaskMan.
Where to find information about error messages?
You can find error messages or additional information about an error message in these places:
•
Background Task History form: Error messages for reports and report previews are displayed in the
Error Message field on this form.
Infor Mongoose Administration Guide | 167
 
Reports

•
Microsoft Event Viewer: TaskMan runs as a service under Windows and generates event messages that
you can view in the Microsoft Event Viewer.
If you are having problems with a background task, you can run TaskMan in debug mode, which generates
additional messages. See Running TaskMan in debug mode on page 115.
•
Error log for SSRS reports: If the SQL Server Reporting Service (SSRS) generated an error message, you
can view the error log for additional information.
The error log is located in the \Reports\Errors\userID subfolder, in the base installation directory on the
server where TaskMan resides.
Infor Mongoose Administration Guide | 168
 
Reports

Chapter 15: Feature management
The practice of "feature management" allows authorized administrators to keep certain installed features
inactive until they have tested them and are ready to implement them in the production environment.
Additionally, it allows them to test authorized pre-release features before activating them in the production
environment.
When a feature displays in the Feature Manager form, on the Available Features tab, it is available for testing
and verification in a pre-production environment. When a feature is displayed on the Pre-release Features
tab, it must first be authorized using a "key" before it can be enabled for testing and verification.
A feature that is displayed on either of those tabs can then be activated when it is ready to be available in a
production environment. To do this you must select the Activate option for the feature. At that point, the
feature is displayed on the Activated Features tab and becomes available to users in the production
environment.
Note:  Once activated, a feature cannot be deactivated.
Managing features in Mongoose
Some new feature functionality is disabled by default, which provides the administrator and authorized users
an opportunity to test, verify, and fully understand how to use the features before activating them. Before a
feature is activated, it displays on either the Available Features tab or the Pre-release Features tab of the
Feature Manager form. When a feature is activated, it displays on the Activated Features tab.
Caution:  Once activated, features cannot be deactivated.
To activate features, first open the Feature Manager form.
Activating an available feature
1 
Select the Available Features tab.
2 
Select the feature you want to enable.
3 
Select the Activate option.
4 
Save.
The newly-activated feature displays on the Activated Features tab.
Infor Mongoose Administration Guide | 169
 
Feature management

Activating a pre-release feature
1 
Select the Pre-release Features tab.
2 
Select the pre-release feature you want to enable.
Note:  Pre-release features are not supported in the production environment. Activation of these features
requires a key, which is provided to beta testing customers only. If you want to be part of the customer
beta testing program, we recommend that you contact your Infor account representative for assistance.
3 
Apply the key to the selected feature, using the Pre-release Key field.
4 
Select the Activate option for a feature to which a key has been applied.
5 
Save.
The newly-activated feature displays on the Activated Features tab.
Infor Mongoose Administration Guide | 170
 
Feature management


