import java.util.List;

public class Conversation implements TwoUserInterface {
    User user1, user2;
    ConversationTopic topic;
    Language language1, language2;

    List<TextMessage> messages;

    ConversationType type;

    // Used for persistent friend-to-friend conversations
    public Conversation(User user1, User user2) {

    }

    // Used for short-term paired conversations
    public Conversation(User user1, Language language1, User user2, Language language2, ConversationTopic topic) {

    }

    public ConversationTopic getTopic() {
        return topic;
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

    public ConversationType getType() {
        return type;
    }
}