package com.example.comp2100_assignment.conversations;

/**
 * @author Zane Gates
 * Message sent by a user into a conversation
 */
public class UserMessage {
    public String author;
    public String content;

    public String time;

    public UserMessage(String author, String content, String time) {
        this.author = author;
        this.content = content.replace('\n', ' ');
        this.time = time;
    }

    public UserMessage(String author, String content) {
        this(author, content, String.valueOf(System.currentTimeMillis()));
    }
    public UserMessage() {
        // Default constructor required for Firebase deserialization
    }

    public String toString() {
        if (!author.equals("")) {
            return author + "> " + content;
        } else {
            return "[" + content + "]";
        }
    }
}
