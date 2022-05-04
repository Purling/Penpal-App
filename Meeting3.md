# $500 for a kitchen table and bedside sofa
# Week 9 Meeting - 5:00--5:25pm Tuesday 3rd May

All present, minutes taken by Zane Gates.

We refined our goals and discussed the architecture of various features.

## Firebase integration

Zane opened by presenting the results of his research into database solutions; in particular, the appropriateness of Google Firebase. Google Firebase limits daily read operations to the database (in the free version) to 20 000, but we decided this was sufficient for our purposes, and that all of the other limitations were also moot.

We agreed Zane, leading database and server-side integration, could run Firebase from a personal Google account rather than creating one from the whole group, and will report on his progress next meeting.

## Refactoring from Java project to Android Studio project

Will successfully refactored the project to Android studio, porting across all pre-existing classes and verifying they continued to pass the unit tests. As others verified there were no conflicts (for example with `.gitignore`), we agreed to merge the branch on which this had been done to the master.

## GUI Progress

The basic GUI, such as the log-in activity, has been completed. The functionality for the GUI still requires some work, as do some of the other activities.

## GitLab issues and testing

Ziling took responsibility for connecting the project to some of GitLab's features. We are now using GitLab issues for tracking features and bugs alike. The pipeline has also been set up to use CI/CD for automated testing whenever code is pushed, although this does not seem to be functioning properly.

## Looking forwards

We are satisfied with our chosen features to implement, and confident that they fulfill the project requirements (at least until they change during Week 10). Accordingly, we will work on currently unimplemented but planned features and integration before discussing any further extensions to the specification.

We were overall disappointed by the lack of concrete progress in the previous week, and resolved to each complete some work prior to our next meeting, which we pushed forward to **Friday 5pm this week**.