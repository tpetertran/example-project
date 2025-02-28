# OMDB Example Project
This example project implements an Android app that searches OMDB for movies.

## Features
* On successful search, displays movies in a LazyColumn
* Handles no movies found, too many results, and error states
* MutableStateFlow debounces so that API isn't called when user isn't finished typing
* In cases of many results, paginates when user scrolls to the bottom
* "View" button creates a Toast message of the movie title
* Basic Intrumented Testing

## App Structure
### UI
UI is split into activity, component, and section packages:

**Components:** Contains my custom components such as buttons, movie items, and the texts I use.

**Section:** Defines the composable screen that holds other composables

**Activity:** Contains MainActivity; the UI logic is separated from activities into sections for code readability

### Data
The data package includes the API interface, and the Retrofit response of which I will obtain the movies that I searched.

### DI
The **di** package contains the Retrofit client that I will inject into my ViewModel. The implementation of dependency injection is to showcase app scalability and testability.

### Instrumented Tests
The androidTest folder contains a TestModule that replaces AppModule for testing. TestModule injects the mock API. The instrumented test runs a simple test that checks for no results, too many results, and for movie results.

## Android Studio Version
Ladybug Feature Drop | 2024.2.2 Patch 1
