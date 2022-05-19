package com.example.comp2100_assignment.conversations;

import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

/**
 * @author Zane Gates
 * Transitory conversation formed by two suitable users in a queue together
 */
public class TransitoryConversation extends Conversation {
    ConversationTopic topic;

    public TransitoryConversation(User user1, Language language1, User user2, Language language2, ConversationTopic topic) {
        super(user1, language1, user2, language2);
        this.topic = topic;
    }

    public ConversationTopic getTopic() {
        return topic;
    }
}
