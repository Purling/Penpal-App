package com.example.comp2100_assignment;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    public List<FriendshipRequest> friends;
    private String username;
    private String password; // this should be hashed
    private String displayName;
    private String avatar;
    private Bitmap profilePicture;
    private List<User> blockedUsers;
    private HashMap<String, Familiarity> familiarity;
    private HashMap<String, Interestedness> conversationTopics;
    private TransitoryConversation conversation;
    private boolean inQueue;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.displayName = username;

        familiarity = new HashMap<>();
        conversationTopics = new HashMap<>();

        friends = new ArrayList<>();

        blockedUsers = new ArrayList<>();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public HashMap<String, Interestedness> getConversationTopics() {
        return conversationTopics;
    }

    public void setConversationTopics(HashMap<String, Interestedness> conversationTopics) {
        this.conversationTopics = conversationTopics;
    }

    public boolean tryLogin(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Familiarity getFamiliarity(Language language) {
        FamiliarityFunction uninterestedIfNull = (f -> f == null ? Familiarity.UNINTERESTED : f);
        return uninterestedIfNull.run(familiarity.get(language.name()));
    }

    public void setFamiliarity(Language language, Familiarity newFamiliarity) {
        familiarity.put(language.name(), newFamiliarity);
    }

    /***
     * Adds a topic to both the set and the list of topics.
     *
     * @param topic The topic to be added to the list and set
     */
    public void setConversationTopic(ConversationTopic topic, Interestedness interested) {
        conversationTopics.put(topic.name(), interested);
    }

    public Interestedness getConversationTopic(ConversationTopic topic) {
        InterestednessFunction uninterestedIfNull = (f -> f == null ? Interestedness.UNINTERESTED : f);
        return uninterestedIfNull.run(conversationTopics.get(topic.name()));
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

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public List<FriendshipRequest> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendshipRequest> friends) {
        this.friends = friends;
    }

    public void receiveFriendRequest(FriendshipRequest request) {
        friends.add(request);
    }

    public void acceptFriendRequest(FriendshipRequest request) {
        request.accept(this);
    }

    public void ignoreFriendRequest(FriendshipRequest request) {
        request.ignore(this);
    }

    public void unfriendFriend(FriendshipRequest request) {
        request.unfriend(this);
    }

    public void withdrawFriendRequest(FriendshipRequest request) {
        request.withdraw(this);
    }

    public void resendFriendRequest(FriendshipRequest request) {
        request.resend(this);
    }

    public TransitoryConversation getTransitoryConversation() {
        return conversation;
    }

    public void setTransitoryConversation(TransitoryConversation newConversation) {
        conversation = newConversation;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public HashMap<String, Familiarity> getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(HashMap<String, Familiarity> familiarity) {
        this.familiarity = familiarity;
    }

    public TransitoryConversation getConversation() {
        return conversation;
    }

    public void setConversation(TransitoryConversation conversation) {
        this.conversation = conversation;
    }

    //adds userToBlock to this' list of blocked users
    public void blockUser(User userToBlock) {
        if (!blockedUsers.contains(userToBlock)) {
            blockedUsers.add(userToBlock);
        }
    }

    //removes userToBlock from this' list of blocked users
    public void unblockUser(User userToUnblock) {
        blockedUsers.remove(userToUnblock);
    }

    //returns if userToCheck is blocked by this
    public boolean isBlocked(User userToCheck) {
        return blockedUsers.contains(userToCheck);
    }

    interface FamiliarityFunction {
        Familiarity run(Familiarity f);
    }

    interface InterestednessFunction {
        Interestedness run(Interestedness f);
    }
}