package com.example.comp2100_assignment;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class User {
    private String username;
    private String password; // this should be hashed
    private String displayName;

    private Bitmap profilePicture;

    public User() {
    }

    private List<User> blockedUsers;


    interface FamiliarityFunction {
        Familiarity run(Familiarity f);
    }

    private HashMap<Language, Familiarity> familiarity;
    private List<ConversationTopic> topics;

    private TransitoryConversation conversation;

    private boolean inQueue;

    public List<FriendshipRequest> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.displayName = username;

        familiarity = new HashMap<>();
        topics = new ArrayList<>();

        friends = new ArrayList<>();

        blockedUsers = new ArrayList<>();
    }


    public String getUsername() {
        return username;
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
        return uninterestedIfNull.run(familiarity.get(language));
    }

    public void setFamiliarity(Language language, Familiarity newFamiliarity) {
        familiarity.put(language, newFamiliarity);
    }

    public boolean getConversationTopic(ConversationTopic topic) {
        return topics.contains(topic);
    }

    // Why not just have this in two methods?
    public void setConversationTopic(ConversationTopic topic, boolean interested) {
        if (topics.contains(topic)) return;
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

    public List<FriendshipRequest> getFriends() {
        return friends;
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

    public void setUsername(String username) {
        this.username = username;
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

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public HashMap<Language, Familiarity> getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(HashMap<Language, Familiarity> familiarity) {
        this.familiarity = familiarity;
    }

    public List<ConversationTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<ConversationTopic> topics) {
        this.topics = topics;
    }

    public TransitoryConversation getConversation() {
        return conversation;
    }

    public void setConversation(TransitoryConversation conversation) {
        this.conversation = conversation;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public void setFriends(ArrayList<FriendshipRequest> friends) {
        this.friends = friends;
    }

    //adds userToBlock to this' list of blocked users
    public void blockUser(User userToBlock){
        if(!blockedUsers.contains(userToBlock)){
            blockedUsers.add(userToBlock);
        }
    }

    //removes userToBlock from this' list of blocked users
    public void unblockUser(User userToUnblock){
        blockedUsers.remove(userToUnblock);
    }

    //returns if userToCheck is blocked by this
    public boolean isBlocked(User userToCheck){
        return blockedUsers.contains(userToCheck);
    }

}