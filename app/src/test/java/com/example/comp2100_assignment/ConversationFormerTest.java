package com.example.comp2100_assignment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ConversationFormerTest {

    ConversationFormer former;
    User alice, bob, cass, deb, eddy, franz;

    ValidConversationData[] data;

    @Before
    public void createPeople() {
        alice = new User("alice", "");
        alice.setFamiliarity(Language.ENGLISH, Familiarity.FLUENT);
        alice.setFamiliarity(Language.ITALIAN, Familiarity.INTERMEDIATE);
        alice.setFamiliarity(Language.GERMAN, Familiarity.BEGINNER);
        alice.setConversationTopic(ConversationTopic.TRAVEL, Interestedness.INTERESTED);
        alice.setConversationTopic(ConversationTopic.MUSIC, Interestedness.INTERESTED);

        bob = new User("bob", "");
        bob.setFamiliarity(Language.ENGLISH, Familiarity.FLUENT);
        bob.setFamiliarity(Language.KOREAN, Familiarity.BEGINNER);
        bob.setConversationTopic(ConversationTopic.FOOD, Interestedness.INTERESTED);
        bob.setConversationTopic(ConversationTopic.MUSIC, Interestedness.INTERESTED);
        bob.setConversationTopic(ConversationTopic.SPORTS, Interestedness.INTERESTED);

        cass = new User("cass", "");
        cass.setFamiliarity(Language.FRENCH, Familiarity.FLUENT);
        cass.setFamiliarity(Language.GERMAN, Familiarity.ADVANCED);
        cass.setFamiliarity(Language.ENGLISH, Familiarity.INTERMEDIATE);
        cass.setConversationTopic(ConversationTopic.MUSIC, Interestedness.INTERESTED);
        cass.setConversationTopic(ConversationTopic.SPORTS, Interestedness.INTERESTED);

        deb = new User("deb", "");
        deb.setFamiliarity(Language.KOREAN, Familiarity.FLUENT);
        deb.setFamiliarity(Language.MANDARIN, Familiarity.FLUENT);
        deb.setFamiliarity(Language.ENGLISH, Familiarity.INTERMEDIATE);
        deb.setConversationTopic(ConversationTopic.FOOD, Interestedness.INTERESTED);

        eddy = new User("eddy", "");
        eddy.setFamiliarity(Language.ITALIAN, Familiarity.FLUENT);
        eddy.setFamiliarity(Language.MANDARIN, Familiarity.BEGINNER);
        eddy.setFamiliarity(Language.ENGLISH, Familiarity.ADVANCED);
        eddy.setConversationTopic(ConversationTopic.SPORTS, Interestedness.INTERESTED);

        franz = new User("franz", "");
        franz.setFamiliarity(Language.GERMAN, Familiarity.FLUENT);
        franz.setFamiliarity(Language.KOREAN, Familiarity.ADVANCED);
        franz.setFamiliarity(Language.ENGLISH, Familiarity.INTERMEDIATE);
        franz.setFamiliarity(Language.MANDARIN, Familiarity.BEGINNER);
        franz.setFamiliarity(Language.ITALIAN, Familiarity.INTERMEDIATE);
        franz.setConversationTopic(ConversationTopic.TRAVEL, Interestedness.INTERESTED);
        franz.setConversationTopic(ConversationTopic.SPORTS, Interestedness.INTERESTED);
        franz.setConversationTopic(ConversationTopic.FOOD, Interestedness.INTERESTED);
        franz.setConversationTopic(ConversationTopic.MUSIC, Interestedness.INTERESTED);

        data = new ValidConversationData[] {
                new ValidConversationData(alice, bob, null, null, ConversationTopic.MUSIC),
                new ValidConversationData(alice, cass, Language.GERMAN, Language.ENGLISH, ConversationTopic.MUSIC),
                new ValidConversationData(alice, deb, null, Language.ENGLISH, null),
                new ValidConversationData(alice, eddy, Language.ITALIAN, null, null),
                new ValidConversationData(alice, franz, Language.GERMAN, Language.ENGLISH, ConversationTopic.TRAVEL),
                new ValidConversationData(bob, cass, null, Language.ENGLISH, ConversationTopic.SPORTS),
                new ValidConversationData(bob, deb, Language.KOREAN, Language.ENGLISH, ConversationTopic.FOOD),
                new ValidConversationData(bob, eddy, null, Language.ENGLISH, ConversationTopic.SPORTS),
                new ValidConversationData(bob, franz, Language.KOREAN, Language.ENGLISH, ConversationTopic.FOOD),
                new ValidConversationData(cass, deb, null, null, null),
                new ValidConversationData(cass, eddy, Language.ENGLISH, null, ConversationTopic.SPORTS),
                new ValidConversationData(cass, franz, null, null, ConversationTopic.MUSIC),
                new ValidConversationData(deb, eddy, Language.ENGLISH, Language.MANDARIN, null),
                new ValidConversationData(deb, franz, null, Language.MANDARIN, ConversationTopic.FOOD),
                new ValidConversationData(eddy, franz, null, Language.ENGLISH, ConversationTopic.SPORTS)
        };

        former = ConversationFormer.getInstance();
    }

    static class ValidConversationData {
        User user1;
        User user2;
        Language language1;
        Language language2;
        ConversationTopic topic;

        ValidConversationData(User user1, User user2, Language language1, Language language2, ConversationTopic topic) {
            this.user1 = user1;
            this.user2 = user2;
            this.language1 = language1;
            this.language2 = language2;
            this.topic = topic;
        }
    }

    @Test
    public void testNoValidTopics() {
        for (ValidConversationData pair : data) {
            if (pair.topic == null) {
                assertNull(
                        (pair.user1
                                + " and "
                                + pair.user2 + " share no valid topics"),
                        former.formConversation(pair.user1, pair.user2));
            }
        }
    }

    @Test
    public void testNoValidLanguages() {
        for (ValidConversationData pair : data) {
            if (pair.language1 == null || pair.language2 == null) {
                assertNull(
                        (pair.language1 == null ? pair.user1 : pair.user2)
                                + " cannot learn any language from "
                                + (pair.language1 == null ? pair.user2 : pair.user1),
                        former.formConversation(pair.user1, pair.user2));
            }
        }
    }

    @Test
    public void testValid() {
        for (ValidConversationData pair : data) {
            if (pair.language1 != null && pair.language2 != null && pair.topic != null) {
                assertNotNull(
                        (pair.user1
                                + " has a valid conversation with " +
                                pair.user2),
                        former.formConversation(pair.user1, pair.user2));
            }
        }
    }
}
