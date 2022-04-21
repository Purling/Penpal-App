public static class UserQueueObserver {

	public static UserQueueObserver instance;
	public static UserQueueObserver getInstance() {
		if (instance == null) instance = new UserQueueObserver();
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