public class Friendship implements TwoUserInterface {
    User user1;
    User user2;

    boolean active;

    Conversation associatedConversation;

    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.active = true;
        this.associatedConversation = new Conversation();

        user1.addFriend(this);
        user2.addFriend(this);
    }
}