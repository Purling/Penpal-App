package com.example.comp2100_assignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationsAvailable implements Serializable {
    public List<String> serializer;
    public List<String> usernames;
    public ConversationsAvailable() {
        this.serializer = new ArrayList<String>();
        serializer.add("000");
        this.usernames = new ArrayList<String>();
    }

    public ConversationsAvailable add(String toAdd) {
        usernames.add(toAdd);
        return this;
    }

    public ConversationsAvailable removeFirst() {
        usernames.remove(0);
        return this;
    }
}
