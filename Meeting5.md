# $500 for a kitchen table and bedside sofa
# Week 10 Meeting - 5:00--5:35pm Wednesday 11th May

All present, minutes taken by Zane Gates.

We discussed timelines and schedules to ensure we reached deadlines and were prepared for this week's checkpoint.

It is worth noting this meeting was originally scheduled on Tuesday but, due to a delay in a team member's previous meeting, we postponed to Wednesday.

## Miscellaneous progress

Will did a small amount of work with regards to the skeleton. Most programming is now reliant on Zane's database progress due to dependencies. Accordingly, he also prepared some notes regarding the surprise feature.

Similarly, Ziling's DAO pattern is also waiting on genuine data to test it on.

Xingkun added a button to add friends into the chat activity. The login reader has support for usernames, but not for password checking.

## Surprise feature

We discussed the meaning of the surprise feature -- in particular, what might qualify as a code smell. We read through and verified the aforementioned notes about pre-existing smells. Even though only 3 have been identified, we suspect there are a wealth of them throughout the code. These smells had been found by using an online tool; however, some of the output of this tool (such as not using `System.out.println` in production code) is obviously inapplicable advice.

## Database progress

Zane has opted to use the realtime Firebase database because of greater documentation and support, as well as the syntax appearing simpler. He has also migrated the database from a personal Google account (as was previously agreed) to a newly created Google account for the team for the purposes of testing.

He has promised to connect many of the existing features to the database in the coming days prior to the checkpoint on Friday morning.

## Checkpoint

Zane, having his workshop latest in the week, will also present the checkpoint. We will make sure most of the features are working correctly, in particular, being able to demonstrate conversations and other features that require two user devices, although we will not focus much on robustness, as we can test and debug next week.

## Server-sided code

Much of the algorithmic code that has been written -- for example, forming conversations between two users -- would be best run on a permanently active server, rather than relying on and trusting each end user to execute it. Nevertheless, we do not have access to any server, so we decided on changing the conversational protocol to allow each user to form their own. This is primarily because Firebase does not inherently support any level of encapsulation or data privacy.

## Report generation

One of the features we would like to implement, generating a report of every interaction a user has performed, also needs a plan. We considered various ways of implementing this, through a list of strings stored by the user, a serializable list of interactions stored in the user which are converted to strings at runtime, or simply by scraping the entire database for any record containing the user. We were not able to reach a clear consensus of a suitable method and postponed this feature until next week.

## Next meeting

We will meet next week or, if necessitated by the checkpoint feedback, earlier.