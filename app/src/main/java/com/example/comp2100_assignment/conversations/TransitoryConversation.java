package com.example.comp2100_assignment.conversations;

import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

/**
 * @author Zane Gates
 * Transitory conversations are formed in the queue, representing one
 * only used for a small amount of time. This becomes a permanent
 * conversation if users wish to continue talking
 */
public class TransitoryConversation extends Conversation {
    private ConversationTopic topic;

    public TransitoryConversation(User user1, Language language1, User user2, Language language2, ConversationTopic topic) {
        super(user1, language1, user2, language2);
        this.topic = topic;
    }

    public ConversationTopic getTopic() {
        return topic;
    }
}
