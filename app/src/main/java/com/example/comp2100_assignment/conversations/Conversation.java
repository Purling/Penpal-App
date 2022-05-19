package com.example.comp2100_assignment.conversations;

import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

import java.util.List;

/**
 * @author Zane Gates
 */
public abstract class Conversation implements TwoUserInterface {
    User user1, user2;
    Language language1, language2;
    String timestamp;

    List<UserMessage> messages;

    // Used for persistent friend-to-friend conversations
    public Conversation(User user1, Language language1, User user2, Language language2) {
        this.user1 = user1;
        this.user2 = user2;
        this.language1 = language1;
        this.language2 = language2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1(Language language1) {
        this.language1 = language1;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2(Language language2) {
        this.language2 = language2;
    }

    public List<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UserMessage> messages) {
        this.messages = messages;
    }

    public void sendMessage(UserMessage message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        return user1 + " (" + language1 + "), " + user2 + " (" + language2 + ")";
    }
}