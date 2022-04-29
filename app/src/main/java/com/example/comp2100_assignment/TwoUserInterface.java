package com.example.comp2100_assignment;

public interface TwoUserInterface {
    User user1 = null;
    User user2 = null;

    public default User getUser1() {
        return user1;
    }

    public default User getUser2() {
        return user2;
    }

    public default User getOtherUser(User user) {
        if (user == user1) return user2;
        else if (user == user2) return user1;
        else throw new IllegalArgumentException(); // Make a custom exception to handle this?
    }
}