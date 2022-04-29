package com.example.comp2100_assignment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;

public class QueuedUserObserverTest {

    User[] users;

    @Before
    public void createPeople() {
        users = new User[30];
        for (int i = 0; i < users.length; i++) {
            users[i] = RandomUserGenerator.generateUser();
        }
        for (User user : users) {
            user.enterQueue();
        }
    }

    @Test
    public void testConversation() {
        for (int i = 0; i < 1000; i++) {
            QueuedUserObserver.getInstance().formConversations();
        }

        int usersInConversation = 0;
        for (User user : users) {
            //System.out.println(user + ": " + user.getTransitoryConversation());
            if (user.getTransitoryConversation() != null) {
                usersInConversation++;
                assertNotSame(user + " is either not party to their own conversation, or is the only party", user.getTransitoryConversation().getUser1() == user, user.getTransitoryConversation().getUser2() == user);
            }
            assertNotSame("Users should remain in the queue iff they are not in conversation", user.getTransitoryConversation() != null, user.isInQueue());
        }

        // TODO: what actual tests can be implemented here?
        // Sure, we can validate any resulting conversations
        // But the inherent randomness means we can't check much else
        assertTrue("No conversations were formed", usersInConversation > 0);
    }
}
