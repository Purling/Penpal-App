import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class User {
    private String username;
    private String password; // this should be hashed

    private String displayName;

    interface FamiliarityFunction {
        Familiarity run(Familiarity f);
    }

    private HashMap<Language, Familiarity> familiarity;
    private Set<ConversationTopic> topics;

    boolean inQueue;

    public ArrayList<Friendship> friends;

    public ArrayList<FriendshipRequest> sentFriendRequests;
    public ArrayList<FriendshipRequest> receivedFriendRequests;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        familiarity = new HashMap<>();
        topics = new HashSet<>();

        friends = new ArrayList<>();
        sentFriendRequests = new ArrayList<>();
        receivedFriendRequests = new ArrayList<>();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Familiarity getFamiliarity(Language language) {
        FamiliarityFunction uninterestedIfNull = (f -> f == null ? Familiarity.UNINTERESTED : f);
        return uninterestedIfNull.run(familiarity.get(language));
    }

    public void setFamiliarity(Language language, Familiarity newFamiliarity) {
        familiarity.put(language, newFamiliarity);
    }

    public boolean getConversationTopic(ConversationTopic topic) {
        return topics.contains(topic);
    }

    public void setConversationTopic(ConversationTopic topic, boolean interested) {
        if (interested) topics.add(topic);
        else topics.remove(topic);
    }

    public void enterQueue() {
        inQueue = true;
        QueuedUserObserver.getInstance().userEntered(this);
    }

    public void exitQueue() {
        inQueue = false;
        QueuedUserObserver.getInstance().userExited(this);
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public List<Friendship> getFriends() {
        return friends;
    }

    public void addFriend(Friendship friendship) {
        friends.add(friendship);
    }

    public void receiveFriendRequest(FriendshipRequest request) {
        receivedFriendRequests.add(request);
    }

    public void sendFriendRequest(FriendshipRequest request) {
        sentFriendRequests.add(request);
    }

    public void acceptFriendRequest(FriendshipRequest request) {

    }

    public void ignoreFriendRequest(FriendshipRequest request) {

    }
}