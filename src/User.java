public class User {
    private String username;
    private String password; // this should be hashed

    private String displayName;

    private Dictionary<Language, Familiarity> familiarity;
    private ArraySet<ConversationTopic> topics;

    boolean inQueue;
    static UserQueueObserver observer;

    public List<Friendship> friends;

    public List<FriendshipRequest> sentFriendRequests;
    public List<FriendshipRequest> receivedFriendRequests;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFamiliarity(Language language) {
        return familiarity.get(language);
    }

    public boolean setFamiliarity(Language language, Familiarity newFamiliarity) {

    }

    public ArraySet<ConversationTopic> getConversationTopics() {
        return topics;
    }

    public boolean setConversationTopic(ConversationTopic topic, boolean interested) {

    }

    public void enterQueue() {
        inQueue = true;
        if (observer != null) observer.notifyEntered(user);
    }

    public void exitQueue() {
        inQueue = false;
        if (observer != null) observer.notifyExited(user);
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public static void setObserver(UserQueueObserver observer) {

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