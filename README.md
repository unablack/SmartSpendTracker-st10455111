# SmartSpend Expense Tracker

## Overview,

SmartSpend Expense Tracker is an Android mobile application developed using Kotlin and Android Studio. The purpose of the application is to help users monitor and manage their daily spending by recording expenses, setting budget goals, viewing spending trends, and analysing financial habits.

The application provides a simple and user-friendly interface that allows users to capture expense information, attach receipt images, search previous expenses, filter spending records, and visualise expenditure through graphical reports.

---

## Project Objectives

The main objectives of SmartSpend are:

* Help users track daily expenses.
* Encourage responsible budgeting.
* Provide visual insights into spending habits.
* Allow quick searching and filtering of expenses.
* Motivate users through gamification features and achievement badges.

---

## Features Implemented

### Expense Management

* Add new expenses.
* Capture expense description.
* Record expense amount.
* Select expense category.
* Select expense date.
* Select expense time.
* Attach receipt or supporting image.
* Save expenses locally using Room Database.

### Expense Categories

Users can categorise expenses into:

* Food
* Transport
* Entertainment
* Bills
* Shopping
* Education
* Other

### Search Functionality

* Search expenses using keywords.
* Quickly find previously recorded expenses.

### Summary and Filtering

* View all recorded expenses.
* Filter expenses by category.
* Filter expenses by date range.
* Calculate category totals.

### Budget Goals

* Set minimum budget goals.
* Set maximum budget goals.
* Compare actual spending against goals.

### Graphical Reporting

* View spending statistics using MPAndroidChart.
* Display category-based spending.
* Filter graph data by date range.
* Visual representation of spending trends.

### Gamification

* Budget achievement badge.
* Spending goal badge.
* Logging streak badge.

### Data Persistence

* Room Database integration.
* Local data storage.
* Persistent expense and goal records.

---

## Technologies Used

* Kotlin
* Android Studio
* Room Database
* MVVM Architecture
* Navigation Component
* RecyclerView
* View Binding
* MPAndroidChart
* Material Design Components

---

## System Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern.

### Model

* Expense Entity
* Goal Entity
* ExpenseDao
* GoalDao
* Room Database

### View

* Add Expense Screen
* Summary Screen
* Search Screen
* Budget Graph Screen

### ViewModel

* ExpenseViewModel

---

## Screens Included

1. Home Screen
2. Add Expense Screen
3. Expense Summary Screen
4. Search Screen
5. Budget Goal Screen
6. Budget Graph Screen

---

## Installation

### Option 1: Install APK

Download the latest APK:

[https://github.com/unablack/SmartSpendTracker-st10455111/releases/tag/v1.0/app-debug.apk]

Install the APK on an Android device and allow installation from unknown sources if required.

### Option 2: Run From Source

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle files.
4. Build the project.
5. Run on an Android device or emulator.

---

## GitHub Repository

Project Source Code:

[https://github.com/unablack/SmartSpendTracker-st10455111]

---

## Demonstration Video

YouTube Demonstration:

[https://youtu.be/sv6993eAGdM?si=gPHo1VBLftacc0zp]

The video demonstrates:

* Expense creation
* Expense searching
* Filtering expenses
* Budget goal management
* Graph reporting
* Gamification features

---

## Future Improvements

Possible future enhancements include:

* Cloud backup and synchronisation.
* User authentication.
* Export reports to PDF.
* Monthly spending analytics.
* Multiple user profiles.
* Dark mode support.
* Notifications and reminders.

---

## Developer Information

Developer: YOUR NAME

Student Number: YOUR STUDENT NUMBER

Module: OPSC6311

Project: SmartSpend Expense Tracker

Year: 2025
s

## 💾 Tech Stack
- Kotlin
- Room Database
- Android Jetpack (ViewModel, LiveData, Navigation)
- MPAndroidChart for graphing
