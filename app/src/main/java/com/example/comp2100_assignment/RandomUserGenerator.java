package com.example.comp2100_assignment;

import java.util.Random;

public class RandomUserGenerator {

    static String[] names = {"alice", "bob", "cassandra", "deborah", "eddy", "franz", "georgia", "ingrid", "hubert", "jemima",
            "klaus", "marty", "nora", "oliver", "pete", "quandale", "rupert", "sally", "zane"};

    static Familiarity[] familiarities = {Familiarity.UNINTERESTED, Familiarity.UNINTERESTED,
            Familiarity.UNINTERESTED, Familiarity.UNINTERESTED, Familiarity.UNINTERESTED,
            Familiarity.BEGINNER, Familiarity.BEGINNER, Familiarity.INTERMEDIATE,
            Familiarity.ADVANCED, Familiarity.FLUENT, Familiarity.FLUENT};

    public static User generateUser() {
        Random r = new Random();
        User user = new User(names[r.nextInt(names.length)] + String.valueOf(r.nextInt(1000)), "");
        for (Language l : Language.values()) {
            Familiarity f = familiarities[r.nextInt(familiarities.length)];
            user.setFamiliarity(l, f);
        }
        for (ConversationTopic t : ConversationTopic.values()) {
            user.setConversationTopic(t, r.nextBoolean() ? Interestedness.INTERESTED : Interestedness.UNINTERESTED);
        }

        return user;
    }
}
