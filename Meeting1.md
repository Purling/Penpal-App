# $500 for a kitchen table and bedside sofa
# Week 7 Meeting - 5:00-5:53pm Tuesday 19th April

All present, minutes taken by Zane Gates.

We met to begin planning our project and discuss what we needed to complete prior to this week's checkpoint.

## Self-introductions

We briefly introduced ourselves.

## Application focus

We discussed various ideas for applications that would fulfill both the technical requirements of user accounts and interactions, as well as the overall theme 'Apps 4 Social Good.' Ideas considered included social programs focused around sharing pictures of endangered trees or animals to encourage biodiversity -- these were dismissed as being insufficiently unique.

We read online lists of examples of social causes, and apps relating to these, to brainstorm. One such idea was anonymously connecting people to discuss their mental health with others. Eventually, we agreed to create a slight modification of this idea, where the focus is shifted from mental health to learning languages by randomly connecting people with similar interests.

## High-level structure

Clearly, users will need to create an account. This account must store various information:
- basic information such as a username and password
- a name (real or false) to facilitate naturalistic conversation
- for each language the app supports, we define the learner's experience: any of `UNINTERESTED`, `BEGINNER`, `INTERMEDIATE`, `ADVANCED`, or `FLUENT`.
- a set of interests, probably chosen from a list of presets
This information should be mutable from an Account tab in the application.

Second, we need to be able to connect two users who each want to practice a language the other speaks. To this end, there will be a Converse tab in the application. Here, a user may indicate that they are prepared to enter conversation with another user. A server-side application will try to match up two users A and B if:
- A has marked as `BEGINNER`, `INTERMEDIATE`, or `ADVANCED` a language B has marked as `FLUENT`.
- B has marked as `BEGINNER`, `INTERMEDIATE`, or `ADVANCED` a language A has marked as `FLUENT`.
- A and B share an interest in some particular topic.
When such a pair is found, a conversation will begin between the two users, with the app reporting both languages and the topic that fulfill the above criteria to facilitate conversation. We will begin by implementing text-based conversation for the purposes of demonstration, however, we are interested in implementing voice chat functionality.
The conversation continues until either user presses a button to end the conversation. After this, both users are returned to the queue where the algorithm searches for another suitable partner. A user in the queue can freely exit the queue if they have finished using the app.

Finally, when two users are interested in establishing longer-term contact, they may send each other a friend request. If accepted, they may use a third application tab to hold a persistent conversation over any period of time.

## Group roles

After discussion, it was agreed that specific roles and task allocations were likely to be contradicted and serve only as a detriment to teamwork. However, we did assign overall demesnes in which each of us would likely have the greatest understanding of all application code.

| Member | Role |
| :--- | :--- |
| Zane | Server implementation and application programming interface |
| Ziling | Logical back-end programs, e.g. algorithms to match queued users |
| Xingkun | Graphical user interface design and production |
| Will | Connections between each area, and further back-end support |

## Project timeline

Our approximate roadmap was designed with course-controlled deadlines and checkpoints in mind; however, we also want to keep ourselves accountable and have clearly demonstrable goals after each week.

Given that each of us had substantial workloads this week already, and likely would in week 11, we expected most of the programming work to be performed between weeks 8 and 10. We also identified some sections of code that would rely on others, and ensure these were implemented in the appropriate order; although, generally speaking, different features are almost entirely independent.

| Week | Expectations |
| :--- | :--- |
| Week 7 | Formalised design & architecture skeleton with set-up classes |
| Week 8 | Implemented accounts and receiving messages from the server when a conversation begins |
| Week 9 | Mutual text chat between two users in conversation, or any two friends |
| Week 10| Connecting features together; bugfixing |
| Week 11| Further dataset generation; more bugfixing |

## Further meetings

Further meetings will be held on a weekly basis, every Tuesday, at 5:00pm, as this time suits everyone. We may also schedule additional meetings, possibly without the entire group present, to discuss particular parts of the architecture. Additionally, we will use Discord as our primary means of discussion, and maintain frequent conversation there outside of regimented meetings.
