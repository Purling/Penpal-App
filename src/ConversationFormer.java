import java.util.ArrayList;
import java.util.Random;

public class ConversationFormer {

    private static ConversationFormer instance;

    public static ConversationFormer getInstance() {
        if (instance == null) instance = new ConversationFormer();
        return instance;
    }

    public TransitoryConversation formConversation(User user1, User user2) {

        ArrayList<ConversationTopic> validTopics = new ArrayList<>();
        for (ConversationTopic topic : ConversationTopic.values()) {
            if (user1.getConversationTopic(topic) && user2.getConversationTopic(topic)) validTopics.add(topic);
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

        ConversationTopic chosenTopic = validTopics.get(r.nextInt(validTopics.size()));
        Language chosenLanguage1 = validLanguages1.get(r.nextInt(validLanguages1.size()));
        Language chosenLanguage2 = validLanguages2.get(r.nextInt(validLanguages2.size()));

        return new TransitoryConversation(user1, chosenLanguage1, user2, chosenLanguage2, chosenTopic);
    }
}