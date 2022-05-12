package com.example.comp2100_assignment;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class User implements Serializable {
    private String username;
    public  String password; // this should be hashed

    public String avatar;

    private String displayName;

    private Bitmap profilePicture;



    private ArrayList<User> blockedUsers;


    interface FamiliarityFunction {
        Familiarity run(Familiarity f);
    }

    private HashMap<Language, Familiarity> familiarity;
    private Set<ConversationTopic> topics;

    private TransitoryConversation conversation;

    private boolean inQueue;

    public ArrayList<FriendshipRequest> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.displayName = username;

        familiarity = new HashMap<>();
        topics = new HashSet<>();

        friends = new ArrayList<>();

        blockedUsers = new ArrayList<>();
    }

    public User(String username, String password, String avatar, String displayName, String commaSeparatedTopics) {
        this(username, password);
        this.avatar = avatar;
        this.displayName = displayName;
        String[] stringTopics = commaSeparatedTopics.split("[,]");
        for (String stringTopic : stringTopics) {
            setConversationTopic(conversationTopicFromString(stringTopic), true);
        }
    }

    ConversationTopic conversationTopicFromString(String stringTopic) {
        switch (stringTopic) {
            case "MUSIC": return ConversationTopic.MUSIC;
            case "SPORTS": return ConversationTopic.SPORTS;
            case "FOOD": return ConversationTopic.FOOD;
            case "TRAVEL": return ConversationTopic.TRAVEL;
            default: return ConversationTopic.MUSIC;
        }
    }

    public boolean tryLogin(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
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