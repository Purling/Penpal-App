public class FriendshipRequest {
	User sender;
	User recipient;

	boolean actioned = false;

	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public boolean isActioned() {
		return actioned;
	}

	public void accept() {
		// Construct the Friendship

		actioned = true;
	}

	// Declines the request without notifying the sender
	public void ignore() {

		actioned = true;
	}
}