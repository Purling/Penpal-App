public class FriendshipRequest {
	private User sender;
	private User recipient;

	private Language languageSender;
	private Language languageRecipient;

	private FriendshipRequestStatus status;

	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	private PermanentConversation conversation = null;

	public FriendshipRequestStatus getStatus(User asker) {
		if (status == FriendshipRequestStatus.DECLINED)
			return asker == sender ? FriendshipRequestStatus.DECLINED : FriendshipRequestStatus.PENDING;
		else
			return status;
	}

	public void accept(User actioner) {
		if (actioner != recipient) return;
		if (status != FriendshipRequestStatus.PENDING) return;
		status = FriendshipRequestStatus.ACCEPTED;

		if (conversation == null) conversation = new PermanentConversation(sender, languageSender, recipient, languageRecipient);
		conversation.sendMessage(new SystemMessage("Friended."));
	}

	// Declines the request without notifying the sender
	public void ignore(User actioner) {
		if (actioner != recipient) return;
		if (status != FriendshipRequestStatus.PENDING) return;

		status = FriendshipRequestStatus.DECLINED;
	}

	public void unfriend(User actioner) {
		if (actioner != sender && actioner != recipient) return;
		if (status != FriendshipRequestStatus.ACCEPTED) return;

		if (conversation != null) conversation.sendMessage(new SystemMessage("Unfriended."));
		status = FriendshipRequestStatus.UNFRIENDED;
	}

	public void withdraw(User actioner) {
		if (actioner != sender) return;
		if (status != FriendshipRequestStatus.PENDING && status != FriendshipRequestStatus.DECLINED) return;

		status = FriendshipRequestStatus.WITHDRAWN;
	}

	public void resend(User actioner) {
		if (actioner == sender) {
			if (status != FriendshipRequestStatus.UNFRIENDED && status != FriendshipRequestStatus.WITHDRAWN) return;
		} else if (actioner == recipient) {
			if (status != FriendshipRequestStatus.DECLINED && status != FriendshipRequestStatus.WITHDRAWN && status != FriendshipRequestStatus.UNFRIENDED) return;
		} else return;

		status = FriendshipRequestStatus.PENDING;

		if (actioner != sender) {
			User temp = recipient;
			recipient = sender;
			sender = temp;

			Language tempL = languageRecipient;
			languageRecipient = languageSender;
			languageSender = tempL;
		}
	}

	public PermanentConversation getConversation() {
		return conversation;
	}
}