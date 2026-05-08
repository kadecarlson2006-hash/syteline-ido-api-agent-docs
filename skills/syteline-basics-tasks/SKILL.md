---
name: "syteline-basics-tasks"
description: "User tasks, calendar, workflows (Workflow Wizard, notifications, approvals, alerts), utilities/activities, background tasks, printing, reports (preview, generate, email, themes), notes, document attachments"
version: "1.0"
tags:
  - syteline
  - basics
  - workflows
  - reports
  - notes
  - mongoose
---

# syteline-basics-tasks

Source: Infor Mongoose Basics User Guide, Release 2026.x

event system.
To cancel the email without sending it, click Close.

Infor Mongoose Basics User Guide | 74

About Forms, Fields, Records, and Collections

Managing Form-specific Email Templates
You can set up a template for sending emails from a specific form. For example, you are viewing a row in a
form that contains specific data that you need to include in an email. You click the email tool bar button to
open an email and select a template. The template contains information you need to send often from this
form, set up as variables that pick up property values from the current row in the form. The data is filled in,
and any attached documents that are part of the template are included by default (although you can choose
to exclude them or add different documents).
1

To access the Email Templates form and set up a template to use with a particular form, use either of
these methods:
•
From the current form, click the envelope icon on the tool bar, or select Actions > Email for Current,
to display the Send email for the current object form. Then click Manage Templates to open the
Email Templates form, with the current form specified in the Form field.
•
From the Explorer, select the Email Templates form. Add a record. In the Form field, and specify
the form to which this template applies.

2
3
4

Specify a template name or use the default value.
Specify a description for the template.
To make the template available to users in the Send email for the current object form for the specified
form, select Active.
If the template will include HTML tags, select Body is in HTML format. When you finish setting up the
template, you can click Preview to see how the formatted email will look.
In the Send To, CC, BCC, and Subject of the message, specify the properties and variables to be substituted
with values from the parent form. For example, in the To field you could specify
P(Customers.ExternalEmailAddr), or just P(ExternalEmailAddr) if the email template is linked to the
Customers form.
In the Message field, specify the text message and include any relevant objects. You can add any P and
V value, for example:

5
6

7

Hi P(UserDesc), We are sending you V(TotalPartNum) P(PartDesc)...

8
9
10
11
12
13

Save your changes.
If you want to attach documents to the template, click Attached Documents and select a document.
Import the document if it does not already exist.
Save your changes and return to the Send email for the current object form. The document is listed in
the Attached Email Documents grid for the template.
Optionally, to preview how the email will look, click Preview Mode.
If you previewed the email, click Template Mode to return to the template view.
Save your changes.

When a user selects the template in the Send email for the current object form, substitution of all variables
is performed before the values from the parent form are displayed in the fields.

Infor Mongoose Basics User Guide | 75

Adding User Tasks

Chapter 5: Adding User Tasks

1
2
3

To access the Create User Task form, selectView > Task.
In the Task Name field, specify a name for the task.
In the Remind Date field, specify a reminder date and, optionally, time.
To specify both a date and time, enter the date/time manually, in the format DD/MM/YY HH:MM:SS AM|PM.

4
5
6
7
8

If you are a system administrator creating a task for another user, specify the user's name in the User
Name field.
If you want the task to be deleted after the reminder date has passed, select Delete After Reminder.
Optionally, use the Description field to specify any additional information about the task.
Click OK .
Optionally, set notification options for the task:
a From the View menu, select User Preferences.
b In the User Preferences dialog box, select the Event System tab.
c In the Check for New Messages Every field, specify how often you want the system to check for new
messages.
d To have the system play an alert sound when messages are received, select the Play a Sound option.
You can also click Play to hear the currently designated sound file, and you can select an alternate
audio file by clicking Browse.
e
f

To have the system display an envelope icon in the notification taskbar, select the Display an Alert
with Message Count option.
To specify how long the alert image is to display, use the Alert Duration slider bar.

9

To have the system automatically delete tasks after sending the reminder, select Delete After Reminder
on the Create User Tasks form or the Task List form.
10 To see and delete notification messages in the Inbox form, select View > Inbox.
You can also manually delete tasks using the Task List form.

Infor Mongoose Basics User Guide | 76

Using the User Calendar

Chapter 6: Using the User Calendar

Adding an appointment to the User Calendar
To add an appointment in the Calendar View of the User Calendar, double-click the appropriate date and
time to display the Update User Appointment form. Specify this appointment information and then click
OK:
•
•
•
•

•
•

Subject
Location: When you add locations, they are stored in the calendar, so you can reuse them later in a
drop-down menu.
Starting Time and Ending Time: Specify the date and time, if different than the default value.
Remind Date: When the reminder date and time is reached, a message is sent to the Inbox form. A
message notification icon can be displayed in the status bar and a sound can play when the reminder
message arrives, if you have chosen those options in the user preferences.
Delete After Reminder: Select this field to delete the appointment after the time is past.
Description: Optionally, specify additional information about the appointment.

To add an appointment in the Tabular View, add a record, specifying the same information as above, and
then save the record. The Starting Time, Ending Time and Remind Date default to the current date and
time.

Changing your calendar view
You can perform these actions in the User Calendar form:
•
•
•
•
•

To move forward or backward timewise, use the arrows at the top of the form.
To view a month, week, or day at a time, select a Mode.
To skip to any specific date in the past or future, specify the date in the Go to Date field and click the
calendar icon.
While in the calendar view, to list all of your appointments in a grid, where you can sort or delete them,
click Tabular View. The list is initially sorted by time.
While in the tabular view, to display your appointments in a standard calendar format, click Calendar
View.

Infor Mongoose Basics User Guide | 77

Using the User Calendar

Removing an appointment from the User Calendar
To remove an appointment from the User Calendar, use any of these methods:
•
•
•

If you selected Delete After Reminder, the appointment is automatically deleted after the due date and
time.
To manually delete an appointment in the Calendar View, double-click the appointment entry to open
the Update User Appointment form. Click Delete Appointment and then click OK.
To manually delete an appointment in the Tabular View, select the row in the grid and click Delete. Then
save your changes.

Viewing appointment details
To view information about an appointment, use any of these methods on the User Calendar form:
•
•
•

To view a brief summary of the time and subject, hover the cursor over the appointment entry in the
Calendar view.
To open the Update User Appointment form, double-click the appointment entry.
To view the appointment entries and details in the form of a list, click Tabular View.

Infor Mongoose Basics User Guide | 78

Creating Workflows

Chapter 7: Creating Workflows

Using the Workflow Wizard
Users such as purchasing managers or executives can use the Workflow Wizard to create event handlers and
actions for some basic notification scenarios. For example, a purchasing manager can build a workflow to
"Notify me when a PO line is created for more than $1000."
As you make choices in the wizard, behind the scenes event handler and event actions metadata is created.
This metadata can later be viewed or edited in the standard Application Event System (AES) forms.
After you finish creating your workflow, you can view it using the My Workflows form. To further customize
a workflow, use the editors on the Event Handlers form.
Event Handlers cannot be deleted, but you can edit the Event Handler and mark it as Obsolete.

Workflow example 1: A simple notification
To build a simple notification workflow:
1
2

Open the Workflow Wizard.
In the Type of Workflow section, select one of these options:
•
Notify Me When: Select this option to receive a notification in your application Inbox form when
the change you specify in the following pages is made to the system.
•
Notify You When: Select this option if the person who makes the specified change should receive a
notification in his or her application Inbox form.
•
Build from Scratch: Select this option and click Finish to exit the workbench and use the standard
Application Event System (AES) forms to build the workflow.

3
4

Click Next.
Specify this information:
Description
Specify a description of the workflow. You can change the description on later pages.
Apply To
Specify the IDO or the form, or both, that you want the system to track. You must identify the IDO.
You can further restrict the triggering condition by specifying the form where the changes are made.
•

If you know the form but not the IDO, select a form name and then click in the IDO field. The IDO is
automatically filled in.

Infor Mongoose Basics User Guide | 79

Creating Workflows
•
•

If you specify an IDO name, and you want the workflow to apply to all forms that use that IDO, you
can either leave the Form field blank or select All Forms.
If you want the workflow to apply to all IDOs associated with the specified form, select All IDOs.

What to track
Select the type of operation on the IDO that triggers the notification: Additions, Changes, or Deletions.
5
6

Click Next.
In the Filters section, select one of these options:
•
All Rows: Select this option to trigger the notification on any change to the IDO rows.
•
Where: Select this option to specify the property and value filter criteria.
Select this option if you only want a notification triggered when the change for a row matches the
filter criteria.
To specify multiple filter clauses, select the OR Instead Of AND With Previous Clause check box,
and click Add This Clause.
For each clause, you can specify one of these options:
•
•
•

7

8

9

Property, operator, and value
Property, operator, and another property, with an optional date-time adjustment
Property, operator, and the date or time when the workflow runs, with an optional adjustment.

Optionally, select Customize Resulting Workflow to open the event handler you created in the standard
AES forms. This option is enabled only if you selected one of the notify options in the Type of Workflow
section.
Click Finish to complete the workflow.
If you selected Customize Resulting Workflow, the Event Handlers form is launched for this workflow's
event handler.
Test the workflow.

Workflow example 2: A conditional notification
Scenario: When a new purchase order line is added where the cost is more than $1000, a notification must
be sent to your Inbox.
To create a workflow for this scenario:
1
2
3
4

Open the Workflow Wizard.
In the Type of Workflow section, select Notify Me When.
Click Next.
Specify this information:
Description
Specify Notify me when new PO line cost is > $1000.
Apply To
Select the PurchaseOrderLines form and click in the IDO field to display the SLPoItems IDO and Purchase
Order Items caption.

Infor Mongoose Basics User Guide | 80

Creating Workflows
What to track
Select Additions.
5
6

Click Next.
In the Filters section, perform these actions:
a Select Where. The Purchase Order Items caption is displayed.
b Select the DerExtItemCostConv property from the drop-down list.
Note: The field label associated with the property is displayed in the drop-down list where possible,
to help you find the property you want. In this case, DerExtItemCostConv has the field label Extended
Cost, which is what you want to compare to your maximum amount.
c
d
e

7
8

Specify > (the greater than operator).
Specify 1000 (the value to compare to). Do not specify separators or currency symbols.
Click Add This Clause to add your filter to the workflow.

To complete the workflow, click Finish.
To test the workflow, ask another user to add a new PO line for more than $1000. You should receive a
notification in your Inbox.

Workflow example 3: A time-conditional notification
Scenario: When a PO line change occurs and the Promise Date is later than the Due Date, a notification must
be sent to your Inbox.
To create a workflow for this scenario:
1
2

Open the Workflow Wizard.
In the Type of Workflow section, select one of these options:
•
Notify Me When: Select this option to receive a notification in your application Inbox form when
the change you specify in the following pages is made to the system.
•
Notify You When: Select this option if the person who makes the specified change should receive a
notification in his or her application Inbox form.
•
Build from Scratch: Select this option and click Finish to exit the workbench and use the standard
Application Event System (AES) forms to build the workflow.

3
4

Click Next.
Specify this information:
Description
Specify a description of the workflow. You can change the description on later pages.
Apply To
Specify the IDO or the form, or both, that you want the system to track. You must identify the IDO.
You can further restrict the triggering condition by specifying the form where the changes are made.
•

If you know the form but not the IDO, select a form name and then click in the IDO field. The IDO is
automatically filled in.

Infor Mongoose Basics User Guide | 81

Creating Workflows
•
•

If you specify an IDO name, and you want the workflow to apply to all forms that use that IDO, you
can either leave the Form field blank or select All Forms.
If you want the workflow to apply to all IDOs associated with the specified form, select All IDOs.

What to track
Select Changes.
5

In the Filters section, perform these actions:
a Select Where.
The Purchase Order Items caption displays.
b
c
d
e

6

7
8

Select the PromiseDate property from the drop-down list.
Specify the Is after operator.
Specify PurchaseOrderItems.DueDate or other property to compare to.
Click Add This Clause to add your filter to the workflow.

Optionally, select Customize Resulting Workflow to open the event handler you created in the standard
AES forms. This option is enabled only if you selected one of the notify options in the Type of Workflow
section.
Click Finish to complete the workflow.
Test the workflow.

Using the New External Workflow Wizard
Users can use the New External Workflow Wizard to generate alert workflows, approval request workflows,
and pulse alerts.
Similar to the Workflow Wizard, which is used to create event handlers and actions for basic notification
scenarios, the New External Workflow Wizard creates event handlers for external systems, particularly Infor
OS workflows.
The external system for this wizard integrates with Infor ION workflow. The approval process and receipt of
alerts are in Infor ION. The list of external workflows is taken through an ION API call.
Unlike the Workflow Wizard, the New External Workflow Wizard will require the user to specifically choose
a specific IDO. An IDO must be explicitly selected, especially for approval request types, to identify which IDO
and IDO properties are being changed.
The BOD Mapping section in this wizard is used to associate IDO properties or expressions for outbound, to
be used as a parameter in BODs.
Alert workflow
An alert workflow is used to track addition, changes, and deletion. An eligible alert workflow is not an approval
request.
This workflow must contain input properties only, specifically Outbound, relative to Mongoose.

Infor Mongoose Basics User Guide | 82

Creating Workflows
The event handler for this workflow, IdoOnItem, tracks which IDO properties are changed and sends the
Process.Workflow BOD. This event handler does not suspend rows.
Approval request workflow
An approval request workflow can only track row updates due to Application Event System limitations.
A workflow is eligible to be an approval request, if its BOD contains:
•
•
•

A RowPointer that is both an Inbound and Outbound parameter
An ApproveReject output parameter, specifically Inbound, relative to Mongoose
At least one Output New Value Parameter, which means this parameter is meant to hold the updated
value temporarily in ION

This workflow has two event handlers, which are used to send and receive BODs:
•
•

IdoOnItemUpdate is used to validate which fields are part of a request for approval. The Process.Workflow
BOD will be sent to external systems, in this case, Infor ION. This also suspends IDO rows.
BodOnReceive is used to await BODs from Infor ION. If an expected workflow (the type of workflow must
be Acknowledge.Workflow and the name of the workflow must match) is received, there will be an
evaluation of approval. It is expected that the requested new values are part of this Acknowledgment
BOD, which were previously sent Process.Workflow from Mongoose. The affected IDO Row is Unsuspended
after the entire process.

Pulse alert
This alert is also called ION Pulse. This alert is not a workflow and does not suspend rows.
This alert requires a valid user, with a valid Infor Federated Services GUID.
The event handlers created by this type can be an IdoOnItem. This handler sends a Sync.PulseAlert BOD.

Workflow example 4: An alert workflow
To generate an alert workflow:
1
2
3
4

Open the New External Workflow Wizard.
In the Type of Workflow section, select Generate Workflow Alert.
Click Next.
Specify this information:
Description
Specify a description of the workflow. You can change the description on later pages.
Apply To
Specify the IDO or the form, or both, that you want the system to track. You must identify the IDO.
You can further restrict the triggering condition by specifying the form where the changes are made.
•

If you know the form but not the IDO, select a form name and then click in the IDO field. The IDO is
automatically filled in.

Infor Mongoose Basics User Guide | 83

Creating Workflows
•

If you specify an IDO name, and you want the workflow to apply to all forms that use that IDO, you
can either leave the Form field blank or select All Forms.

What to track
Select the type of operation on the IDO that triggers the notification: Additions, Changes, or Deletions.
5
6

Click Next.
In the Filters section, select one of these options:
•
All Rows: Select this option to trigger the notification on any change to the IDO rows.
•
Where: Select this option to specify the property and value filter criteria.
Select this option if you only want a notification triggered when the change for a row matches the
filter criteria.
To specify multiple filter clauses, select the OR Instead Of AND With Previous Clause check box,
and click Add This Clause.
For each clause, you can specify one of these options:
•
•
•

7
8

Property, operator, and value
Property, operator, and another property, with an optional date-time adjustment
Property, operator, and the date or time when the workflow runs, with an optional adjustment.

Click Next.
In the BOD Mapping section, specify this information:
Workflow
Select an external workflow.
Recipient
Disabled. This field displays the recipient user.
Workflow ID
Disabled. This field displays the workflow ID.
BOD Field
Disabled. This field displays the BOD parameter name of the workflow from Infor OS workflow.
Outbound
Specify the source of a value used to populate the Process_Workflow BOD parameters. This value can
be an IDO Property Name or an Expression (Application Event System).
Inbound
Disabled. This field displays the IDO Property Name that is updated for a particular IDO Row. The updated
value comes from the Acknowledge.Workflow BOD Property.
Use New Value
Select this check box to indicate whether the BOD Field contains a new or updated value for an IDO
Property.
This field is enabled when either of these conditions are true:
•
•

All Rows is selected for the condition.
The property selected in Inbound is also a property used in the condition.

Infor Mongoose Basics User Guide | 84

Creating Workflows
9

Optionally, select the Customize Resulting Workflow check box to launch the Event Handlers form for
this workflow's event handler.
10 Click Finish to complete the workflow.
11 Test the workflow.

Workflow example 5: an approval request workflow
To generate an approval request workflow:
1
2
3
4

Open the New External Workflow Wizard.
In the Type of Workflow section, select Generate Workflow Approval Request.
Click Next.
Specify this information:
Description
Specify a description of the workflow. You can change the description on later pages.
Apply To
Specify the IDO or the form, or both, that you want the system to track. You must identify the IDO.
You can further restrict the triggering condition by specifying the form where the changes are made.
•
•

If you know the form but not the IDO, select a form name and then click in the IDO field. The IDO is
automatically filled in.
If you specify an IDO name, and you want the workflow to apply to all forms that use that IDO, you
can either leave the Form field blank or select All Forms.

What to track
Select the type of operation on the IDO that triggers the notification: Additions, Changes, or Deletions.
5
6

Click Next.
In the Filters section, select one of these options:
•
All Rows: Select this option to trigger the notification on any change to the IDO rows.
•
Where: Select this option to specify the property and value filter criteria.
Select this option if you only want a notification triggered when the change for a row matches the
filter criteria.
To specify multiple filter clauses, select the OR Instead Of AND With Previous Clause check box,
and click Add This Clause.
For each clause, you can specify one of these options:
•
•
•

7
8

Property, operator, and value
Property, operator, and another property, with an optional date-time adjustment
Property, operator, and the date or time when the workflow runs, with an optional adjustment.

Click Next.
In the BOD Mapping section, specify this information:
Workflow
Select an external workflow.
Infor Mongoose Basics User Guide | 85

Creating Workflows
Recipient
Disabled. This field displays the recipient user.
Workflow ID
Disabled. This field displays the workflow ID.
BOD Field
Disabled. This field displays the BOD parameter name of the workflow from Infor OS workflow.
Outbound
Specify the source of a value used to populate the Process_Workflow BOD parameters. This value can
be an IDO Property Name or an Expression (Application Event System).
Inbound
Disabled. This field displays the IDO Property Name that is updated for a particular IDO Row. The updated
value comes from the Acknowledge.Workflow BOD Property.
This field is enabled for an Output BOD Property.
Use New Value
Select this check box to indicate whether the BOD Field contains a new or updated value for an IDO
Property.
This field is enabled when either of these conditions are true:
•
•

All Rows are selected in the condition.

The property selected in Inbound is also a property used in the condition.

9

Optionally, select the Customize Resulting Workflow check box to launch the Event Handlers form for
this workflow's event handler.
10 Click Finish to complete the workflow.
11 Test the workflow.

Workflow example 6: A pulse alert
To generate a pulse alert:
1
2
3
4

Open the New External Workflow Wizard.
In the Type of Workflow section, select Generate Pulse Alert.
Click Next.
Specify this information:
Description
Specify a description of the workflow. You can change the description on later pages.
Apply To
Specify the IDO or the form, or both, that you want the system to track. You must identify the IDO.
You can further restrict the triggering condition by specifying the form where the changes are made.

Infor Mongoose Basics User Guide | 86

Creating Workflows
•
•

If you know the form but not the IDO, select a form name and then click in the IDO field. The IDO is
automatically filled in.
If you specify an IDO name, and you want the workflow to apply to all forms that use that IDO, you
can either leave the Form field blank or select All Forms.

What to track
Select the type of operation on the IDO that triggers the notification: Additions, Changes, or Deletions.
5
6

Click Next.
In the Filters section, select one of these options:
•
All Rows: Select this option to trigger the notification on any change to the IDO rows.
•
Where: Select this option to specify the property and value filter criteria.
Select this option if you only want a notification triggered when the change for a row matches the
filter criteria.
To specify multiple filter clauses, select the OR Instead Of AND With Previous Clause check box,
and click Add This Clause.
For each clause, you can specify one of these options:
•
•
•

7
8

Property, operator, and value
Property, operator, and another property, with an optional date-time adjustment
Property, operator, and the date or time when the workflow runs, with an optional adjustment.

Click Next.
In the BOD Mapping section, specify this information:
Workflow
Disabled. This field displays the external workflow.
Recipient
Select the recipient user.
Workflow ID
Disabled. This field displays the workflow ID.
BOD Field
Disabled. This field displays the BOD parameter name of the workflow from Infor OS workflow.
Outbound
Specify the source of a value used to populate the Process_Workflow BOD parameters. This value can
be an IDO Property Name or an Expression (Application Event System).
Inbound
Disabled. This field displays the IDO Property Name that is updated for a particular IDO Row. The updated
value comes from the Acknowledge.Workflow BOD Property.
Use New Value
Select this check box to indicate whether the BOD Field contains a new or updated value for an IDO
Property.
This field is enabled when either of these conditions are true:
•

All Rows are selected in the condition.

Infor Mongoose Basics User Guide | 87

Creating Workflows
•

The property selected in Inbound is also a property used in the condition.

9

Optionally, select the Customize Resulting Workflow check box to launch the Event Handlers form for
this workflow's event handler.
10 Click Finish to complete the workflow.
11 Test the workflow.

Infor Mongoose Basics User Guide | 88

Utilities and Activities

Chapter 8: Utilities and Activities

Using Utilities and Activities
Utility and activity forms process multiple records in one operation. They typically perform tasks such as
purging records, updating values, posting transactions, or changing the status of records. The Delete Audit
Logs form, for example, deletes all audit logs created between specified dates for specified message types.
To use a utility or activity, you specify criteria to select information to be processed and you select options
to define the processing task. The form then processes information that meets the criteria. In certain forms,
you can preview results of the process before you save them in the database.

Previewing and Processing Results in Utilities and
Activities
Utility and activity forms that display a grid provide an option to preview the results of processing data without
actually changing records in the application database. You can use this feature to assess your selection criteria
and task options. You can adjust them as needed before you save the changes. You can also use the results
as you would use information in a spreadsheet, searching columns for values, sorting rows, and so forth.
Results display in the grid at the bottom of the form.
To preview the results of running a utility or activity, specify your processing criteria on the form, select
Preview and click Process. The results are displayed but not saved to the database.
To actually run the utility or activity, specify your processing criteria on the form, select Commit and click
Process. The modified records are stored in the application database.

Specifying Selection Criteria in Utilities and Activities
In many utilities and activities, you select items to process by defining a range of values or several ranges of
values. Range fields are pairs of fields with which you specify a starting value and an ending value.
Range fields can apply to identification numbers such as purchase order numbers or line numbers, dates,
names, and most other types of information.

Infor Mongoose Basics User Guide | 89

Utilities and Activities
Use this table to specify the appropriate range of values:
To select

Specify this information

One value

Specify the same value in both starting and ending
fields.
Note: If you select Set Default Ending Value to
Starting Value on the General Parameters form,
then in any form where you can specify a range of
values, the value you specify in the starting field is
automatically set as the default value in the ending
field. This only occurs if the ending field is blank.

All available values

Leave both the starting and ending fields blank.

Values between and including two values

Specify the lower value in the starting field and the
higher value in the ending field.

The lowest possible value through a specified value

Leave the starting field blank and specify the cutoff
value in the ending field.

A specified value through the highest possible value

Specify the starting field value and leave the ending
field blank.

Using Results from Utilities and Activities
Utility and activity forms that contain a grid allow you to use the results of a process as you might use data
in a spreadsheet. The result set displayed in the grid is similar to other collections of records in the application.
These topics provide more information about results:
•
•
•
•
•

Finding values in results on page 60
Sorting rows in results on page 49
Copying results to a spreadsheet on page 51
Exporting results on page 50
Printing results on page 64

Infor Mongoose Basics User Guide | 90

About background tasks

Chapter 9: About background tasks

Background tasks are operations or activities that can be set up to run in the background. Many reports,
utilities, and some activities can be scheduled to run daily, weekly, or monthly. After the process is scheduled,
it runs automatically in the background at a set time, using your selection criteria and option settings.
Most reports that are run as background tasks are placed on a list of active background tasks, to be run as
scheduled or demanded. You can also set up other tasks to run in the background. The Infor Framework
TaskMan service regularly polls the list of active background tasks and executes any task that is posted as
READY to be executed.
Forms used with background tasks
These are the basic forms and utilities associated with background tasks:
•

•
•
•

•

Background Task Definitions: Use this form to create records that define and identify background tasks
for TaskMan. TaskMan then monitors the application database in order to execute these defined
background tasks, either on demand or as scheduled.
Active Background Tasks: Use this form to schedule background tasks and to view the statuses of
background tasks that are waiting to execute or are in progress.
Background Task History: Use this form to display information about background tasks that have been
run, and whether they completed successfully. If not, this form also displays associated error messages.
Active Background Task Purge Utility: Use this form to purge (usually) long-running active background
tasks that are in READY or RUNNING status. This utility can be used for tasks that have become "hung" or
that have been created accidentally and are no longer needed.
Background Queue: Use this form to specify when and/or how often, the task from the 'parent' form is
to run.

Guidelines for creating background tasks
Background tasks must meet these requirements:
•
•
•
•

The task name should match the form name, to make it easier to identify.
Stored procedures must be written in SQL and reside in your SQL Application Database. Utility and activity
forms generally use the Executable Type: SP (stored procedure).
Executable programs must reside in a directory available to the directory where TaskMan resides.
These types of reports are supported as background tasks:
•
DATAVIEW: Reports based on predefined DataViews and their layouts.
•
FORM: Reports created using the Report form type.
•
SSRS: (Deprecated) Reports created using Microsoft SQL Server Reporting Services (SSRS).

Infor Mongoose Basics User Guide | 91

About background tasks

Note: To enable the Report Type options, you must select RPT (Report) as the Executable Type.
•

IDO methods, as background tasks, specify a method name (in the format IDO.Method--for example,
MyProgID.SLSites.MyTestMethod)--and use the Executable Type IDOMTH. When processing tasks of this
type, TaskMan requires a configuration with the same name as the site name. On the machine where
TaskMan is running (typically the application server), you must create a configuration where the
configuration name matches the database site name, if one does not already exist.

Background task statuses
The status of an active or scheduled background task displays on the Active Background Tasks form. The
status might be any of these, depending on how the task is defined and scheduled:
•
•
•
•

READY: The task has been submitted and is ready to execute.
RUNNING: The task is currently executing.
WAITING: The task is scheduled and on hold until some requirement (usually the scheduled day/time)
is met.
PROCESS: The task is a scheduled task that has spawned a 'child' task that is either READY or RUNNING.

The status of a task that has already run or is currently running displays on the Background Task History
form. The status indicates one of these cases:
•
•
•

Task Succeeded
Task Failed - Error -1: The task failed to finish and generated an error. The explanation of the error is
provided in the Error Message field.
Task Cancelled - Error -2: The task was cancelled at the request of a user. When this is the case, the
explanation of the cancellation is provided in the Error Message field.

Scheduling background tasks
Background tasks are set up using the Background Task Definitions form. They can also be scheduled to
run on a once-only or recurring basis using the Active Background Tasks form or the Background Queue
form.
Note: When scheduling background tasks, keep these facts in mind:
•
•

Forms can only be scheduled to run as background tasks if they have the Background option available
on the Actions menu.
To schedule (and run) background tasks, you must have the Infor Framework Event Service configured
and running.

Once scheduled, background tasks can only be deleted if they have a Status of WAITING on the Active
Background Tasks form. Tasks that are in a status of READY or RUNNING can be canceled without deleting them.

Infor Mongoose Basics User Guide | 92

About Printing Records and Collections

Chapter 10: About Printing Records and Collections

You can print records or collections or records from any form that handles records or collections. Such printing
is handled on your computer, not on the system server.
If you print a single record, fields are listed alphabetically opposite their values.
If you print an entire collection of records, field labels become column headings, and each record becomes
a row in the printed table.

Infor Mongoose Basics User Guide | 93

Working with Reports

Chapter 11: Working with Reports

Reports Overview
Reports are available in a variety of forms and formats. Generally, they fall into one of these categories:
•
•
•
•

Custom reports generated using a Report type form
Custom reports generated using DataViews
System standard form reports
Reports generated using Microsoft SSRS

Custom reports from Report type forms
The application has a form type called "Report." The Report type of form allows you to create application
forms that are designed to generate templates for custom reports. This is an improvement over other types
of custom reports in the application, because you can both run and publish the report without having to use
third-party reporting engines. This kind of report is ideal for reports that you want to generate on a regular
basis, either on demand (by running the form) or automatically (as a background task).
Custom reports from DataViews
For certain kinds of reports, the DataViews functionality can be a valuable and easy tool to generate them.
These are most commonly used for reports that you might want to generate "on the fly", without wanting to
invest a lot of time and effort in formatting the results.
These reports also do not require the use of a third-party report generation engine, but they do require that
you be licensed to use DataViews.
System standard form reports
The system includes a number of standard report forms created for the purpose of generating certain reports.
These reports can be identified by the use of the word "Report" in the form name/caption (for example, User
Authorization Report).
Reports using the Microsoft SSRS reporting engine
While you can still create and use these kinds of reports (and we still support them for backward compatibility),
SQL Server Reporting Services (SSRS) reports are now deprecated in the application. Instead, we recommend
that you use either the new Report type of form or a DataView-based report.

Infor Mongoose Basics User Guide | 94

Working with Reports
Publishing reports
Depending on the type of report, reports can be published to PDF or Excel files, which can then be delivered
to printers, or email addresses. Most reports also allow you to set various options and preview the output
before actually publishing them.
Customizing/Modifying reports
If you have the required editing permissions, you can customize reports of any type to your needs. The
requirements for customizations and modifications depend on what type of report you want to customize
or modify.

Previewing Reports
Most reports allow you to preview the output before generating it.
To preview a report:
1
2

Open a report criteria form and define the report parameters.
Click Preview.

Note: After you click the Preview button, a preview window displays. Closing the preview window closes
the message window and prevents the preview from opening. However, the preview background task continues
to run and preview files are generated. If you believe there is a problem with a preview, and you would like
to cancel the background task, contact your system administrator.
Report previews behave the same as generated reports, with these exceptions:
•
•
•

•

Previews are not sent to a printer.
Previews cannot be configured from the Report Options form. Previews use global settings that are
defined on the Intranets form.
For previews, the format depends on the type of report:
•
Reports created from Report type forms display in a separate preview display window.
•
DataView reports and standard form reports display in a DataView Results form.
•
Reports created using SSRS display in a separate preview display window.
Email notification is not needed or supported.

Generating Reports
To generate a report means to output it to a printer, or to an email recipient. When you click the Print button
on a report form, you generate the report.
In most cases, we recommend previewing the report before generating it. This gives you a chance to see what
the output will look like and make any necessary changes before actually outputting the report.

Infor Mongoose Basics User Guide | 95

Working with Reports
When you generate a report, the report is sent to the background task queue, where it is then routed to the
report generator. From there, it is routed either to a printer, or to an email recipient, depending on the options
for that report as set on the Report Options form.

Printing Reports
When you print a report, the report data is first sent to the report generator and then to the printer. The system
uses printing options as set on the Report Options form or the Intranets form. You can select a printer other
than your default printer. You can also preview the report before you actually print it.
Note: Before reports can be printed, verify that the printer is configured on the application server where
Infor Framework TaskMan Service is installed. For a multi-tenant cloud environment, verify that the printer
is registered with Infor Document Management. See the Configuring printers help topic.
To print reports:
1

Open the Report Options form.
a In the Output Format field, verify that Printer or Cloud Print is selected.
If no option record exists for your report, the report output defaults to the format as set on the
Intranets form. The default output format on the Intranets form is Acrobat Format (PDF).
Note: If the Printer or Cloud Print output format is not selected on either the Report Options form
or the Intranets form, the output does not go to a printer.
b

Optionally, in the Email Notification field, select Yes to receive an email notification when the report
is processed and generated.
You will also be notified if the Send Email Notification check box is selected on the Intranets form.
Note: You will receive an email through the email address as specified in your record on the Users
form.

c
2
3

Save the changes.

Open the report form and define the report parameters.
Optionally, to send output to a printer other than your default printer, perform these steps:
a Click Select Printer to open the Report Options form.
Note: In this step, the Report Options form opens with limited functionality. This functionality is
restricted to allow you only to select a printer. To make other changes to the report options for this
report, you must perform Step 1.
b
c

In the Printer Name field, specify the name of the printer to use.
Save and close the Report Options form.
Note: You can select different printers for successive print jobs, but you must be careful: TaskMan
uses the printer you have designated at the time it runs the print job.
Suppose, for example, that you queue up one print job to use Printer A and then a second print job
to use Printer B. If TaskMan already had a queue and did not pick up the first job before you switched
printers for the second job, then TaskMan uses Printer B for both print jobs.

Infor Mongoose Basics User Guide | 96

Working with Reports

To be safe, it is best to let one print job finish, if possible, before initiating the second print job.
4
5

Optionally, preview the report output.
Click Print.

Emailing Reports
When you send the report as an email, the system sends it to the email address that is specified on the Report
Options form, or on the Intranets form if no address is specified on the Report Options form).
Note: Before reports can be printed, TaskMan must be set up as described in the System Administration Guide.
To email a report:
1

Optionally, verify that the Report Options form has been set up for the report you are running:
•
Make sure the Output Format field for this report is not set to Printer.
Note: If no option record exists for your report on the Report Options form, the report output
defaults to the format as set on the Intranets form. The default output format on the Intranets form
is Acrobat Format (PDF).
•

Verify that the Email Notification and Attach Report fields are both set to Yes.
If you do not want to attach a copy of the report to your email notification, you can leave the Attach
Report field set to No. If you later want to view the report, you can find the output file at: TaskMan
path\Report\Output Files\YourUserID

2
3
4

Open the report form and define the report parameters.
Optionally, preview the report output.
Click Print.

Setting Report Options
There are two basic ways to set report options:
•
•

To set the report options for individual reports, use the Report Options form.
To save, retrieve, and manage commonly-used report settings, you can use the Store Options, Get
Options, and Options Defaults forms, if available.

Setting Report Ranges
When you select ranges of values in report parameters forms, use these rules:
•

To begin the range at the first record in the system, leave the Starting field blank.

Infor Mongoose Basics User Guide | 97

Working with Reports
•
•
•
•

To end the range at the last record in the system, leave the Ending field blank.
To include all records in the system, leave both Starting and Ending fields blank.
To include a selected range of records, specify both starting and ending record numbers, or select the
desired start range value and end range value from the fields.
To set the Starting field value as the default Ending field value when the Ending field is blank, select
Set Default Ending Value to Starting Value on the General Parameters form or the System Parameters
form.

Where do report outputs go?
When a report is previewed or generated, the output can go to any of a number of different locations, depending
on various application settings. The Infor Task Manager (or TaskMan, and not to be confused with Windows
Task Manager) is set to send report outputs according to these rules:
•

If the process default Report output obfuscation is set to 1, then all report output goes to a single system
folder. The filename for each consists of the report name appended with a session ID number.
If the Report output obfuscation process default is not set or is set to 0 (which is the default), then
TaskMan looks in the next location.

•

•

•

The next location TaskMan looks at is the Report Options form.
•
If a report profile has been created there for the report, then TaskMan checks to see if a destination
for report outputs has been set in the Output Directory field. If a destination has been set in the
Output Directory field, then TaskMan uses that output path.
•
If no Output Directory has been specified, then TaskMan uses the output setting specified on the
Intranets form.
If no report profile for a report exists on the Report Options form, then TaskMan checks next to see if
the Sites or Sites/Entities form has a Report Output Folder designated for report outputs. If so, TaskMan
uses that folder as the destination for report outputs. If not, then TaskMan uses the output setting specified
on the Intranets form.
Finally, if no other output destination has been defined elsewhere, then TaskMan uses the output path
as set in the TaskMan Path field of the Intranets form.

In all cases where the Report output obfuscation process default is 0 (zero), TaskMan uses the designated
report output path and looks for a subfolder labeled with the user ID of the user who initiated the report
generation. If that subfolder does not exist already, then TaskMan creates it before placing the output in that
subfolder.

Setting the report theme
To set the report theme to use when generating reports, select the Report Theme value on any of these forms:
•
Report Options form
Note: On this form, the Report Theme value is not required. You can select a theme or leave this field
as blank.
•

Intranets form

Infor Mongoose Basics User Guide | 98

Working with Reports

Note: On this form, the Report Theme value is required. The default theme is InforReport.
The theme used in reports is based on the values set in this hierarchy:
1
2
3
4
5

Report Options > Report Theme field
Intranets > Reporting > Report Theme field
In the web client: View > User Preferences > Layout > Theme field
Configuration Manager > New/Edit Application - Main > Default Theme > Theme field
Mongoose default theme: Infor

Report theme takes the highest priority if the theme is valid.

Assigning Date and Number Formats on Reports
To override the default or regional settings for report output, make these settings:
1
2

Set the date format and numeric format for a specific language on the Language IDs form.
Select that language in the User Preferences form.

Changing Fonts in Reports
Note: This topic applies only to Form reports.
To change the font for a report output:
1
2
3

Open the Language IDs form.
In the row representing the language in which you will print reports, select a font in the Font Descriptor
field.
Click Actions > Save.

Notes:
Font names in the Font Descriptor list represent fonts installed on your local client workstation. To ensure
that reports print in the selected font, the font must be installed also on the TaskMan server, the utility server,
and the print server.
Barcode type fonts do not need to be installed on the client workstation if they are not going to be previewed
there.
The font setting does not apply to barcodes or currency amounts in checks. To change fonts for these objects,
edit the objects in the report source file.

Infor Mongoose Basics User Guide | 99

Working with Reports

Sending Email Notification
Use the Report Options form to send a notification email to yourself when a report that you initiated is
complete. To do this, specify Yes in the Email Notification field.
To attach a copy of the report to the email message, specify Yes in the Attach Report field.

Infor Mongoose Basics User Guide | 100

Working with Notes

Chapter 12: Working with Notes

About notes
You can annotate a record or a collection of records by attaching one or more notes. This topic describes the
types of notes. The Related Topics links point to other topics that explain how to create, view, and attach or
detach notes.
Object and Class Notes
You can create these types of notes:
•

Notes for a single record (Object Notes):
These notes are attached to the currently selected record. Object notes can be internal or external. You
can create a note specifically for that record, or you can attach a reusable note.

•

Notes for all records in a collection (Class Notes):
These notes are attached to every record in a collection. For example, if you attach a class note to one
customer record, it is automatically attached to all customer records. Class notes can be internal or
external. You can only attach reusable notes as class notes. Also, class notes do not print on reports.

You can attach multiple notes, of either type, to a record. After you attach a note of any type to a record, all
other users of the system can read the note.
When you attach a note, the status bar displays the word "Notes", and under the Actions menu, a check mark
is displayed next to the Notes for Current option, for single-record notes, or next to the Notes for All option,
for notes attached to the whole collection.
Reusable Notes
Reusable notes are defined on the System/User Notes form. You can create these notes once and attach
them multiple times, either as object notes or class notes. There are two kinds of reusable notes:
•

User notes: After a note is created, it can be reused and attached to records only by the person who
created the note. For example, if Sally creates a user note in the System/User Notes form, she can later
view that note and attach it to other records. However, when Pete opens the form, he cannot see Sally's
notes or attach them to his records.
User notes do not print on reports.

•

System notes: After a system note is created, it is visible and is available for use by any user on the system.
For example, if Joe creates a system note, both he and Jane can later view that note in the System/User
Notes form. They can also both attach it to as many records as they want.

Infor Mongoose Basics User Guide | 101

Working with Notes
System notes can be printed on reports.
Internal/External Notes
All notes can also be classified as either external or internal notes. If you select the Internal option for a note,
the system tags it as an internal note. Otherwise, the system treats the note as an external note.
This classification is used when printing reports. You can print internal notes or external notes on forms where
the Print External Notes and Print Internal Notes options are available.

About creating and attaching notes
On many forms, you can create notes to be attached to specific records or collections of records. Notes can
be used in these ways:
•
•
•
•

Attached specifically to a single record
Created as reusable notes that can be attached to multiple records
Read or modified from the records they are attached to
Created as class notes, system notes, or single user notes

You can also attach to notes links to web-accessible files and web pages, or full paths of external files.
Attachments can include any type of file that your work environment supports. For example, you can attach
URLs or files that represent text documents, spreadsheets, graphics, and even multimedia files

Creating a single-use note for a specific record
A single-use note is created specifically for a particular record and is directly attached to that record.
1
2

Open a form and select the record to which you want to add a note.
Navigate to Actions > Notes for Current.
Optionally, select a record and click the System Notes icon on the toolbar.

3

On the Object Notes form, specify this information:
Subject
Specify an appropriate subject identifier.
Note: The subject line does not print on reports. It is used only to distinguish a note from other notes.
Internal
To tag the selected note as an "internal" note, select this check box. To tag the selected note as an
"external" note, clear this check box.
This classification is used when printing reports. You can print internal notes or external notes on forms
where Print External Notes and Print Internal Notes options are available.

Infor Mongoose Basics User Guide | 102

Working with Notes
Reusable
This field is read-only. If this check box is selected, then it indicates that the note is a "reusable" note
that was attached using the Attach/Detach Reusable button. In this case, you cannot edit the note in
this form.
System
This field is read-only. If this check box is selected, then it indicates that the note is a reusable system
note that was attached using the Attach/Detach Reusable button. In this case, you cannot edit the note
in this form.
Note Content
Specify the contents of the note.
Note: Use this field only if you are not attaching a link or a file to the note. You can print the contents
of this field as part of report printouts.
4

Optionally, to attach a file, or a web-accessible link to a file or a web page, click Attach Link and specify
a valid URL.
Note: If you attach a link or a file to a note, then the URL, or the full path of the file and its name, is stored
on the Note Content field.

5

Click the Save icon.

Creating or selecting reusable notes
To create reusable notes or add existing notes to other records, use the System/User Notes form. You can
launch the System/User Notes stand-alone and create reusable notes from there. You can also launch the
System/User Notes form from an Object Notes form that is linked to a record, or from a Class Notes form
that is linked to a collection.
If you launch the System/User Notes form from a linked Object Notes or Class Notes form, then you can
directly attach new or existing notes from the System/User Notes form, to the linked record or collection of
the Object Notes or Class Notes form.
1
2

Open a form and select a record where you want to add a note.
Select one of these options:
•
To open the Object Notes form and attach a note to a single record, select Actions > Notes for
Current.
Alternatively, click the System Notes icon on the toolbar.
•

3
4

To open the Class Notes form and attach a note to all records in a collection, select Actions > Notes
for All.

To display the System/User Notes form, on the Object Notes form, click Attach/Detach Reusable.
Decide which kind of note you want to create or select, and then use the fields under that heading:
•
A system note can be viewed or selected by all system users, and is printable in some cases.
•
A user note can be viewed or selected only by the user who created it, and is not printable.

Infor Mongoose Basics User Guide | 103

Working with Notes
5
6

On the System Notes Reusable By All section, or on the User Notes Reusable By Current User section
of the System/User Notes form, select any row and click the Add icon on the toolbar.
On the new row, specify this information:
Subject
Specify an appropriate subject identifier.
Note: The subject line does not print on reports. It is used only to distinguish a note from other notes.
Note Content
Specify the contents of the note.
Note: Use this field only if you are not attaching a link or a file to the note. You can print the contents
of this field as part of report printouts.

7

Optionally, click Attach File and select the file.
Note: If you attach a link or a file to a note, then the URL, or the full path of the file and its name, is stored
on the Note Content field.

8
9

To save the new note, click the Save icon on the toolbar.
Optionally, to attach the new note or any existing note to the record or to all records in the collection,
select the Attach check box next to the notes and click OK to return to the linked Object Notes form.
10 On the Object Note form, click the Save icon on the toolbar.

Reading and modifying notes
To read and modify reusable notes, use the System/User Notes form. You can launch the System/User Notes
stand-alone, and read or modify reusable notes from there. You can also launch the System/User Notes form
from an Object Notes form that is linked to a record, or from a Class Notes form that is linked to a collection.
To launch the System/User Notes form stand-alone, without reference to any record or collection, select
View > System Notes.
You can read both single-use and reusable notes that are attached to a record or a collection. In many cases,
you can also modify the notes.
1

Open a form and select a record that has notes attached. You can tell which records have notes attached
by checking these areas:
•
In the tool bar, the Notes button is highlighted when a record has an object note, that is, a note for
the current record, attached.
•
The status bar for a record with a note attached displays the word NOTES.
•
The Actions menu for records with attached notes displays a check mark next to the Notes for
Current or Notes for All option.
Note: If no notes are displayed, but the Notes for Current or Notes for All option has a check mark,
then the attached note is a private or non-system user note that was added by another user.

2

Open the appropriate notes form:

Infor Mongoose Basics User Guide | 104

Working with Notes
•
•
3

To open the Object Notes form for single-record notes, select Actions > Notes for Current.
To open the Class Notes form for notes that are linked to all records in a collection, select Actions
> Notes for All.

In the notes grid, select the note that you want to read or modify.
Note: If the fields are inactive, then it indicates that the attached note is a reusable note. To modify a
reusable note, click the Attach/Detach Reusable button and update the note on the System/User Notes
form.

4
5

Optionally, if a note includes an attached file or link, you can click the Open Link button.
Optionally, modify the subject and note text.
To modify an existing note with an attached link or file, you can click the Attach Link button.

6

To save the changes, click the Save icon on the toolbar.

Deleting notes
To delete a reusable note, launch the System/User Notes form and delete the note from there. In the
System/User Notes form, you cannot delete notes that are currently linked to records. You must detach the
note in the Object Notes or Class Notes form before you delete the note in the System/User Notes form.
You can use the Attach/Detach Reusable button to launch the System/User Notes form and unlink reusable
notes from there.
When you delete notes on the Object Notes form, single-use notes are permanently deleted. Reusable notes
on the Object Notes form and on the Class Notes form are only detached, and are still available in the
System/User Notes form.
Attached files are not imported into the system. If you delete a note with an attached file, the file stays in its
location.
1
2

Open a form and select a record in which you want to delete a note.
Select one of these options:
•
To open the Object Notes form and delete a note, select Actions > Notes for Current.
Alternatively, click the System Notes icon on the toolbar.
•

3
4

To open the Class Notes form and detach a note that is linked to all records in a collection, select
Actions > Notes for All.

Select the note and then click the Delete button on the toolbar.
To permanently delete the note, click the Save button on the toolbar.
Note: Reusable notes are only detached and are still available in the System/User Notes form.

Infor Mongoose Basics User Guide | 105

Working with Notes

Detaching notes
You can detach only reusable notes. Reusable notes are defined in the System/User Notes form. You can
attach and detach reusable notes as object notes and class notes. You cannot detach single-use object notes,
but you can delete them.
1
2

Open a form and select a record in which you want to detach a note.
Select one of these options:
•
To open the Object Notes form and detach a note from a record, select Actions > Notes for Current.
Alternatively, click the System Notes icon on the toolbar.
•

3
4
5

To open the Class Notes form and detach a note that is linked to all records in a collection, select
Actions > Notes for All.

To open the System/User Notes form, on the Object Notes form or on the Class Notes form, click
Attach/Detach Reusable.
On the System/User Notes form, clear the Attach check box next to the notes and click OK to return to
the Object Notes form or Class Notes form.
Click Save.

About printing associated notes
Many reports offer options to print associated notes. These notes are created by you or other people on your
system. The following general principles govern the printing of notes:
•
•
•
•

You can print notes attached to individual records (also known as object notes), including reusable system
notes but not user notes.
In certain cases, you can choose to print notes tagged as internal, external, or both.
Notes tagged as Class Notes (also known as Notes for All) do not print with reports.
Attachments to notes do not print with reports.

Accessing the System/User Notes form
You can launch the System/User Notes form stand-alone. You can also open the System/User Notes form
from an Object Notes form that is linked to a record, or from a Class Notes form that is linked to a collection.
To launch the System/User Notes form stand-alone, without reference to any record or collection, select
View > System Notes.
If you launch the System/User Notes form from a linked Object Notes or Class Notes form, then you can
directly attach new or existing notes from the System/User Notes form, to the linked record or collection of
the Object Notes or Class Notes form.
To launch the System/User Notes form from an Object Notes form that is linked to a record or collection:

Infor Mongoose Basics User Guide | 106

Working with Notes
1
2

Open a form and select a record where you want to add a note.
Select any of these options:
•
To open the Object Notes form for a specific record, select Actions > Notes for Current.
Alternatively, click the Notes button on the toolbar.
•

3

To open the Class Notes form for all records in a collection, select Actions > Notes for All.

To open the System/User Notes form, on the Object Notes form or on the Class Notes form, click
Attach/Detach Reusable.

About attaching external files and links to notes
You can attach to notes links to web-accessible files and web pages, or full paths of external files. Attachments
can include any type of file that your work environment supports. For example, you can attach URLs or files
that represent text documents, spreadsheets, graphics, and even multimedia files. You can use the Attach
Link button. You can attach a URL or a file to a new note or an existing note. You can attach only one link or
file per note.
You can attach links only in the web client, and you can manually add links or file directories on the Note
Content field. If you manually add links or file directories on the Note Content field, then the Open Link and
Open Attachment buttons are disabled.
To open an attached link or file, you can use the Open Link button in the web client. The link or the file must
already exist in a location that is accessible to any users who might access it from the note. You can open
attached links, but you cannot open attached files in the web client. To open an attached file when using the
web client, copy the full path of the file from the Note Content field and paste it on the address bar of the
file explorer. Alternatively, click the Open Attachment button and on the web browser, click Copy Link, then
paste the link on the address bar of the file explorer.
Caution: If you attach a link or a file to a note, then the URL, or the full path of the file and its name, is stored
on the Note Content field. To preserve existing note texts, you can copy the text to another note, or attach
the link or file to a new note.
Notes that contain attachments do not print in reports and other printing utilities.
Attach Link button (web client only)
Use the Attach Link button to add a link to web-accessible files or pages. You can attach any valid URL. For
example you can attach http, https, ftp, or even mailto links. The attached link is stored in the Note Content
field. If the URL is changed and you select the Open Link button, then the browser returns an error.

Infor Mongoose Basics User Guide | 107

Working with Document (File) Attachments

Chapter 13: Working with Document (File) Attachments

Working with document (file) attachments
You can augment many forms by attaching documents (files) that are related to the specific form, properties
on the form, or records in a collection. You can also import documents into the database or link documents
from file servers or FTP/SFTP servers. The documents can then be attached by other users to forms, properties,
or records. Query and Note forms cannot have attachments.
Document types
Before documents can be imported into the system and attached to forms, properties, or records, an authorized
system administrator must use the Document Types form to specify information about the types of documents
that can be attached, and where each type of document will be stored. Documents can be stored in the
application database or in a linked server or FTP/SFTP location.
When a document is imported or attached, a document type is assigned to the document. Documents can
be filtered based on document type.
When a document type is created, a default file extension is assigned to it. This file extension is overwritten
if the document you import actually has a different file extension.

Maintaining Document Types
Before a document can be imported into the system and attached to a record or form, the system administrator
must create document types in the Document Types form. You can include this information for each document
type:
•

•

Associate a default file extension with the document type, which determines the application to use when
presenting the document for viewing. For example, users might want to attach purchase order documents
created as Microsoft Word (*.docx) files to customer orders. Create a document type called CustomerPOs,
and associate the file extension "docx" with that type. You can then use that document type when
attaching PO files to customer orders.
Specify the storage method used for this document type. Documents can be stored in the application
database or linked from a file server or FTP/SFTP server. For linked documents, specify the area where
that type of document is stored.
See the help about managing file servers for linked documents.

Infor Mongoose Basics User Guide | 108

Working with Document (File) Attachments
•

Specify which forms, and/or properties of forms, can have this document type attached.
Note: This specification can be different for different users, if multiple versions of the form exist with
different scope type and/or scope names, or if a different forms database is used. This is because some
versions of the form might contain the property and some might not.

To set up a document type, using the Document Types form:
1

Specify this information:
Document Type
Specify a name for the document type. This name could describe the type of application used, for
example, Acrobat, Word, or Graphic, the file extension, for example, PDF or JPG, the storage method,
for example, FTP or URL, the name of the file server, or some combination of these.
Description
Fully describe the document type.
Storage Method
Specify where files of this type will be stored: in the database or on a linked server.
File Extension
Optionally, specify the file extension that is associated by default with this document type. For example,
if the Document Type is Acrobat or PDF , the default file extension could be pdf. If the Document Type
is Graphic , the default file extension could be png or jpg. This default file extension is overwritten if the
document you import has a different file extension.
Media Type
Optionally, specify a default media type for this document type. The default media type is populated in
the Attached Documents form when this document type is selected.

2

If you specified Link as the Storage Method, specify this additional information:
File Server
Specify the name of the file server or FTP/SFTP server.
Note: The file server named here must be defined on the File Servers form.
When you specify a server, the Server Root Path field automatically displays the server name and path
as defined on the File Servers form.
If no server is specified, the local server is assumed and Windows authentication is used.
Logical Folder Name
Optionally, from the drop-down list, select the logical folder where the files are to be written.
Note: This property is used to add "access security" to the document type.
File Specification
If a logical folder is specified, this field is read-only.
If no logical folder is specified or is set to Default, specify the root path where files are to be stored on

