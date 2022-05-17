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
    private HashMap<Language, Familiarity> familiarity;
    private Set<ConversationTopic> topicsSet;
    private List<ConversationTopic> conversationTopics;
    private TransitoryConversation conversation;
    private boolean inQueue;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.displayName = username;

        familiarity = new HashMap<>();
        topicsSet = new HashSet<>();
        conversationTopics = new ArrayList<>();

        friends = new ArrayList<>();

        blockedUsers = new ArrayList<>();
    }

    public User(String username, String password, String avatar, String displayName, String commaSeparatedTopics) {
        this(username, password);
        this.avatar = avatar;
        if (avatar == null) avatar = "";
        this.displayName = displayName;
        if (displayName == null) displayName = username;
        if (!(commaSeparatedTopics == null || commaSeparatedTopics.equals(""))) {
            String[] stringTopics = commaSeparatedTopics.split(",");
            for (String stringTopic : stringTopics) {
                addConversationTopic(ConversationTopic.valueOf(stringTopic));
            }
        }
        familiarity = new HashMap<>();
        topicsSet = new HashSet<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ConversationTopic> getConversationTopics() {
        return conversationTopics;
    }

    public void setConversationTopics(List<ConversationTopic> conversationTopics) {
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
        return uninterestedIfNull.run(familiarity.get(language));
    }

    public void setFamiliarity(Language language, Familiarity newFamiliarity) {
        familiarity.put(language, newFamiliarity);
    }

    public boolean getTopicsSet(ConversationTopic topic) {
        return topicsSet.contains(topic);
    }

    // Why not just have this in two methods?
    public void setTopicsSet(ConversationTopic topic, boolean interested) {
        if (interested) topicsSet.add(topic);
        else topicsSet.remove(topic);
    }

    public void setAllTopicsSet (Set<ConversationTopic> topicsSet) {
        this.topicsSet = topicsSet;
    }

    /***
     * Adds a topic to both the set and the list of topics.
     *
     * @param topic The topic to be added to the list and set
     */
    public void addConversationTopic(ConversationTopic topic) {
        topicsSet.add(topic);
        conversationTopics.add(topic);
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

    public HashMap<Language, Familiarity> getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(HashMap<Language, Familiarity> familiarity) {
        this.familiarity = familiarity;
    }

    @Exclude
    public Set<ConversationTopic> getTopicsSet() {
        return topicsSet;
    }

    public void setTopicsSet(Set<ConversationTopic> topicsSet) {
        this.topicsSet = topicsSet;
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

}