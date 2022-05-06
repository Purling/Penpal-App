# $500 for a kitchen table and bedside sofa
# Week 9 Meeting - 5:00--5:17pm Friday 6th May

All present, minutes taken by Ziling Ouyang.

We discussed a bit about what we did this week and also what we intend to do in the coming days.

## Firebase integration

Zane discussed more about the Fireabse integration. In particular, he started to set up some of the code necessary to actually interact with the database. There are a few things which require the database to first be set up in order to progress. These include the login and also the DAO pattern. We discussed creating a group account for the Firebase database. This is because, it might be inconvenient for some people to not be able to directly access the database. Zane said he would create the group Google account. We also discussed whether we should use the realtime database or the Firestore database. This will be investigated by Zane.

## GUI

Xingkun successfully created a GUI with limited functionality. Much of the functionality is still to be added (e.g., messaging, login, etc). This is also related to the Firebase integration. Will created a basic framework for adding a picture to the profile of a user.

## GitLab CI Pipeline

The CI pipeline now works properly and displays if the project has built without errors. Additionally, it also generates a report of each JUnit test. This may prove to be helpful later down the line.

## Further Tasks

Apart from the tasks requiring database integration, there are other tasks such as improving the queue matching functionality. Xingkun will continue to work on the GUI and implement functionality such as joining the queue. Zane will continue to work on Firebase integration. William will work on adding a way to block users. Ziling will work on improving the queue matching functionality and DAO when the Firebase database is fully set up.