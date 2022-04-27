# $500 for a kitchen table and bedside sofa
# Week 7 Meeting - 5:00~5:40pm Tuesday 26th April

All present, minutes taken by Ziling Ouyang.

We started to consider more concrete goals and actual coding.

## Code Skeleton
Zane introduced the skeleton of code which he created for the first checkpoint. We discussed the possibility of replacing the list data structure in the queueing system to be replaced by some sort of tree structure.

## Concrete Goals/Tasks

- There needs to be a refactoring of code so that the project works on Android Studio
- DAO pattern
-	GUI login and logout
-	Android session persistence
- Minimumlly viable GUI by the end of the week
-	Match up people based on language, fluency and queue time
-	Skeleton for a transitory conversation into a permanent conversation
    -	Save the data in the conversations



## Features

We decided on the following features that we would most probably implement:

- Users may send requests which are then accepted or denied by another user or organisation (easy)
- UI must have portrait and landscape layout variants as well as support for different screen sizes. Simply using Android studio's automated support for orientation and screen sizes and or creating support without effort to make them look reasonable will net you zero marks. (easy)
-	A user can only see a profile/event that is Public (consider that there are at least two types of profiles: public and private). (easy)
-	Provide users with the ability to ‘block’ things. Things (e.g., events, users, messages containing abusive language, etc) shall not be visible to the user who blocked that activity. (medium)
-	Provide users with the ability to ‘block’ users. Preventing them from directly messaging them. (medium)
-	Deletion method of either a Red-Black Tree, AVL tree or B-Tree data structure. The deletion of nodes must serve a purpose within your application. (hard)

Due to the fact that the features will change in week 10, we decided not to decide every feature that would be implemented as they could change.

## Project timeline

We took into account the feedback given to us in the first checkpoint and updated the timeline to include writing the report.

| Week | Expectations |
| :--- | :--- |
| Week 7 | Formalised design & architecture skeleton with set-up classes |
| Week 8 | Implemented accounts and receiving messages from the server when a conversation begins |
| Week 9 | Mutual text chat between two users in conversation, or any two friends |
| Week 10| Connecting features together; bugfixing |
| Week 11| Further dataset generation; more bugfixing; write the report; make the presentation |

## Conflict Resolution

Based on the feedback received in the first checkpoint, we decided to create a conflict resolution policy. It is as follows:

- If the conflict is strictly one of opinion, the group will hold a democratic vote to determine the best course of action for the project. If this vote ends in a tie, the tie-breaker will come from the group member who has been assigned that particular component of the project. (e.g., if the conflict is about the UI, Xingkun will be the tiebreaker vote)

- If the conflict is more of personality, we will try to resolve things through conversation, or if really necessary get a third-party person to mediate (e.g., tutor or maybe another unrelated person from the group) 

## Database

We talked about the use of Firebase and determined that we did not have enough information to concretely say that it would be the best DBMS to use. However, because the DAO pattern requires knowledge of the DBMS language, we may first create a "placeholder" DAO.

## Further Meeting

We will have a meeting at 5:00pm next week.
