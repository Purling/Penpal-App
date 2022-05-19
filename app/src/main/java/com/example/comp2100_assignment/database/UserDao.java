package com.example.comp2100_assignment.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comp2100_assignment.Singleton;
import com.example.comp2100_assignment.users.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/***
 * Class which implements the DAO pattern for Users stored in the database
 *
 * @author Ziling Ouyang
 */
public class UserDao implements DaoPattern<User, String>, Singleton {

    private static UserDao userDao = null;

    private UserDao() {
    }

    public static UserDao singleton() {

        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    @Override
    public void get(String id, OnGetDataListener<User> listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(getModelClass());
                if (user == null) {
                    listener.onSuccess(null);
                }
                else {
                    if (user.getFamiliarity() == null) user.setFamiliarity(new HashMap<>());
                    if (user.getConversationTopics() == null) user.setConversationTopics(new HashMap<>());
                    if (user.getFriends() == null) user.setFriends(new HashMap<>());
                    listener.onSuccess(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public String getChildName() {
        return "userList";
    }

    @Override
    public Class<User> getModelClass() {
        return User.class;
    }


    @Override
    public void getAll(OnGetDataListener<User> listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                listener.onSuccess(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                listener.onSuccess(user);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void save(User user, boolean filledOrNew) {
        // Create database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        if (filledOrNew) { // If the user is entirely new or just needs to be entirely updated
            mDatabase.child(getChildName()).child(user.getUsername()).setValue(user);
        } else { // If the user only needs to be partially updated
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("username").setValue(user.getUsername());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("password").setValue(user.getPassword());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("blockedUsers").setValue(user.getBlockedUsers());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("displayName").setValue(user.getDisplayName());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("profilePicture").setValue(user.getProfilePicture());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("familiarity").setValue(user.getFamiliarity());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("conversationTopics").setValue(user.getConversationTopics());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("friendRequests").setValue(user.getFriends());
            if (user.getUsername() != null)
                mDatabase.child(getChildName()).child(String.valueOf(user.getUsername()))
                        .child("interactions").setValue(user.getInteractions());
        }
    }

    @Override
    public void delete(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(getChildName()).child(username).removeValue();
    }
}
