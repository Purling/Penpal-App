package com.example.comp2100_assignment.conversations;

import com.example.comp2100_assignment.Singleton;
import com.example.comp2100_assignment.users.Familiarity;
import com.example.comp2100_assignment.users.Interestedness;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zane Gates
 */
public class ConversationFormer implements Singleton {

    // Implement the singleton pattern
    private static ConversationFormer instance;
    public static ConversationFormer getInstance() {
        if (instance == null) instance = new ConversationFormer();
        return instance;
    }

    /**
     * Attempts to generate a conversation between users, which requires that they share
     * both a mutual interested topic and have a language each can learn from the other
     * @param user1 the first user
     * @param user2 the second user
     * @return a random valid conversation if possible, null otherwise
     */
    public TransitoryConversation formConversation(User user1, User user2) {
        if (user1 == null || user2 == null || user1 == user2) return null;

        ArrayList<ConversationTopic> validTopics = new ArrayList<>();
        for (ConversationTopic topic : ConversationTopic.values()) {
            if (user1.getConversationTopic(topic) == Interestedness.INTERESTED && user2.getConversationTopic(topic) == Interestedness.INTERESTED) validTopics.add(topic);
        }

        if (validTopics.size() == 0) return null;

        ArrayList<Language> validLanguages1 = new ArrayList<>();
        ArrayList<Language> validLanguages2 = new ArrayList<>();
        for (Language language : Language.values()) {
            Familiarity f1 = user1.getFamiliarity(language);
            Familiarity f2 = user2.getFamiliarity(language);

            if (f1 == Familiarity.UNINTERESTED || f2 == Familiarity.UNINTERESTED) continue;

            boolean learning1 = f1 == Familiarity.BEGINNER || f1 == Familiarity.INTERMEDIATE;
            boolean learning2 = f2 == Familiarity.BEGINNER || f2 == Familiarity.INTERMEDIATE;

            if (learning1 && !learning2) validLanguages1.add(language);
            if (learning2 && !learning1) validLanguages2.add(language);
        }

        if (validLanguages1.size() == 0 || validLanguages2.size() == 0) return null;

        Random r = new Random();

        Language chosenLanguage1 = validLanguages1.get(r.nextInt(validLanguages1.size()));
        Language chosenLanguage2 = validLanguages2.get(r.nextInt(validLanguages2.size()));
        ConversationTopic chosenTopic = validTopics.get(r.nextInt(validTopics.size()));

        return new TransitoryConversation(user1, chosenLanguage1, user2, chosenLanguage2, chosenTopic);
    }
}