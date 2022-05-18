package com.example.comp2100_assignment.conversations;

import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

/**
 * @author Zane Gates
 * Permanent conversation formed when members of a transitory conversation want to continue talking
 */
public class PermanentConversation extends Conversation {
    public PermanentConversation(User user1, Language language1, User user2, Language language2) {
        super(user1, language1, user2, language2);
    }
}
