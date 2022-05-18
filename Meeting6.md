# $500 for a kitchen table and bedside sofa
# Week 11 Meeting - 5:00 ~ 6:00pm Wednesday 11th May

All present, minutes taken by Ziling Ouyang.

We confirmed deadlines and decided on what would happen in the last few days of the assignment. The vast majority of the meeting was spent confirming what we had already accomplished amongst the assignment tasks and what had left to accomplish.

## Progress Report

Xingkun was working on a search functionality for the app which would work by using a tokeniser and parser. Will had done some miscellaneous work on refactoring and also on fixing some of the code smells. Zane implemented multiple features which had not been fully implemented. Ziling did refactoring on methods to do with database access after fully testing the DAO.

## Goals During Last Days

### Search

Xingkun wants to fully implement the search functionality using a tokenizer and parser. This search should also allow fuzzy searching and also invalid searches. It would match users based on topic or language.

### DAO

After discussing the DAO, it was decided that there would be a hybrid approach between using a `addListenerForSingleValueEvent` and `addValueEventListener()`. Both of these implementations have there own advantages and disadvantages. The single value event listener may be resource intensive under large use. However, based on application, it may be sometimes more appropriate than the value event listener.

### UI Tests

Will decided to try and implement UI tests using the Espresso tests.

### Report Viewer

We decided on using serialised objects to store the reports

### App Functionality

Zane would connect the remaining switches in the settings page to the database (e.g., language preferences, display name, etc)

### Data structure

It was decided that we would store users locally in a binary tree to be more efficient in search.

## Assignment Specifics - Part 1

### Complete Requirements

1. Users must be able to login (not necessarily sign up). \[3 marks\]
2. Users must be able to load data/information (from file(s) or Firebase) and visualise it (e.g. on a list or a timeline activity) \[10 marks\]
3. Your app shall implement at least three design patterns covered in the course \[DAO, Singleton, Observer\]
4. Your app shall retrieve data from a local file \[Firebase\]

### Incomplete but in Progress

1. Users must be able to search for information on your app. Depending on your app theme, you may want to allow users to search for other users, data (e.g. text), tags (e.g. #buildingABetterWorldWithCOMP2100). The search functionality must make use of a tokenizer and parser with a formal grammar of your own creation. \[12 marks\]
2. There must be a data file with at least 2,500 valid data instances. The data file must be used to feed your app simulating a data stream. For example, every x seconds, a new item is read from a file. An item can be an action (e.g. like, follow, support, post, new activities, etc) \[5 marks\]
3. At least one fully implemented data structure taught in this course (e.g., Binary Search Tree, Red-Black tree, AVL tree, B-Tree) for organising, processing, retrieving and storing data. We will also evaluate your choice and use of data structures on your project (not only trees, but also other data structures such as arrays, lists, maps, etc). \[Must implement: Planning on BST\]

## Assignment Specifics - Part 2 Additional Features

### Completed Features

1. Provide users with the ability to ‘block’ things. Things (e.g., events, users, messages containing abusive language, etc) shall not be visible to the user who blocked that activity. \[Medium\]
2. Provide users with the ability to message each other or an institution directly (e.g., a user can message an event/movement that is managed by another user) \[Hard\]
3. Provide users with the ability to restrict who can message them by some association (e.g. a setting for: can only message me if we are friends, if we support the same social cause/movement/event). \[Hard\]
4. Use Firebase to implement user Authentication/Authorisation \[Easy\]
5. Use Firebase to persist all data used in your app (this item replaces the requirement to retrieve data from a local file) \[Medium\]
6. Using Firebase or another remote database to store user information and having the app updated as the remote database is updated without restarting the application. E.g. User A makes a transfer, user B on a separate instance of the application sees user A’s transfer appear on their app instance without restarting their application. \[Hard\]
7. User profile activity containing a media file (image, animation (e.g. gif), video). \[Easy\]

### Incomplete

We only need to complete one more feature to have a full 8 implemented features of difficulties which meet the assignment specification.

1. Search functionality can handle partially valid and invalid search queries. \[Medium\]
2. UI must have portrait and landscape layout variants as well as support for different screen sizes. Simply using Android studio's automated support for orientation and screen sizes and or creating support without effort to make them look reasonable will net you zero marks \[Easy\]
3. UI tests using espresso or similar. Please note that your tests must be of reasonable quality. \[Hard\]
4. Report viewer. Provide users with the ability to see a report of interactions with your app (e.g., total views, total likes, total raised for a campaign, etc, in a graphical manner \[Medium\]
5. Users may send requests which are then accepted or denied by another user or organisation (e.g., a request to follow an event, a person or organisation). \[Easy\]
6. Provide users with the ability to ‘block’ users. Preventing them from directly messaging them \[Medium\]

## Next meeting

We will call an impromptu meeting anytime in the next few days if necessary.
