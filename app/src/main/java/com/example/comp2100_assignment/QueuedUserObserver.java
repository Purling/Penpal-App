package com.example.comp2100_assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueuedUserObserver {

    public static QueuedUserObserver instance;
    public static QueuedUserObserver getInstance() {
        if (instance == null) instance = new QueuedUserObserver();
        return instance;
    }

    // Use a faster data structure?
    public List<User> usersInQueue = new ArrayList<>();

    public void userEntered(User user) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (usersInQueue.contains(user)) throw new IllegalArgumentException("User already in queue");
        usersInQueue.add(user);
    }

    public void userExited(User user) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (!usersInQueue.contains(user)) throw new IllegalArgumentException("User already out of queue");
        usersInQueue.remove(user);
    }

    public void formConversations() {
        Random r = new Random();
        int a = r.nextInt(usersInQueue.size());
        int b = r.nextInt(usersInQueue.size()-1);
        if (b == a) b = usersInQueue.size()-1;

        User user1 = usersInQueue.get(a);
        User user2 = usersInQueue.get(b);
        TransitoryConversation conversation = ConversationFormer.getInstance().formConversation(user1, user2);

        if (conversation != null) {
            user1.exitQueue();
            user2.exitQueue();
            user1.setTransitoryConversation(conversation);
            user2.setTransitoryConversation(conversation);
        }
    }

    public List<User> getUsersInQueue() {
        return usersInQueue;
    }
}