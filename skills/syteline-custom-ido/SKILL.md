---
name: syteline-custom-ido
description: "How to create custom IDOs in Syteline 10 using User Extensibility Tools (UET)"
version: "1.0"
tags: [erp, syteline, infor, mongoose, ido, uet, customization]
category: syteline
metadata:
  hermes:
    tags: [erp, syteline, infor, mongoose, ido, uet, customization]
---

# Creating Custom IDOs in Syteline 10

To create a custom IDO in Syteline 10, use the **User Extensibility Tools (UET)**. The process has 5 steps:

## Step 1: Create a User Class

The user class definition is the highest level to extend an application database table. A user class groups related custom fields and provides the structure that UET uses to manage and display them.

## Step 2: Create User Fields

User fields are generic and can be a part of many classes. If you change any property of a user field, all user classes inherit the change. This makes user fields reusable across multiple classes.

## Step 3: Associate the User Field with a User Class

The UET tools look for this association to place the user fields in the form that belongs to the user class. Without this association, the fields will not appear on any form.

## Step 4: Link an Application Database Table with the User Class

The association between a table and a class provides the information that UET needs to retrieve, arrange, and display the user fields that belong to a user class.

To link the table with the class, define a **rule** that determines if the record accessed has a valid user class associated with it. If valid data is entered in existing fields to make the rule expression true, the new user field displays.

## Step 5: Impact the Schema

Use the **UET Impact Schema** form to apply the changes you made in the previous steps to all affected databases. This step propagates the custom IDO definition across the system.

## Summary

```
1. Create User Class          → defines the extension point
2. Create User Fields         → defines the custom properties
3. Associate Field → Class    → links fields to the class
4. Link Table → Class (rule)  → controls when fields display
5. Impact Schema              → applies changes to all databases
```
