public interface TwoUserInterface {
    User user1;
    User user2;

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public User getOtherUser(User user) {
        if (user == user1) return user2;
        else if (user == user2) return user1;
        else throw new InvalidArgumentException(); // Make a custom exception to handle this?
    }
}