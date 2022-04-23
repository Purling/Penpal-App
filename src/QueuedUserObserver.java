import java.util.List;

public class QueuedUserObserver {

    public static QueuedUserObserver instance;
    public static QueuedUserObserver getInstance() {
        if (instance == null) instance = new QueuedUserObserver();
        return instance;
    }

    // Use a faster data structure -- like some binary tree -- instead of a list?
    public List<User> users;

    public List<User> usersInQueue;

    public void userEntered(User user) {

    }

    public void userExited(User user) {

    }

    public List<User> getUsersInQueue() {
        return usersInQueue;
    }
}