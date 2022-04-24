import java.util.List;

public abstract class Conversation implements TwoUserInterface {
    User user1, user2;
    Language language1, language2;

    List<TextMessage> messages;

    // Used for persistent friend-to-friend conversations
    public Conversation(User user1, Language language1, User user2, Language language2) {
        this.user1 = user1;
        this.user2 = user2;
        this.language1 = language1;
        this.language2 = language2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public Language getLanguage1() {
        return language1;
    }

    public Language getLanguage2() {
        return language2;
    }

    public List<TextMessage> getMessages() {
        return messages;
    }

    public void sendMessage(TextMessage message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        return user1 + " (" + language1 + "), " + user2 + " (" + language2 + ")";
    }
}