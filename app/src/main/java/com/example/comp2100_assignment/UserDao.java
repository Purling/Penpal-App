package com.example.comp2100_assignment;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements DaoPattern<User, String> {

    private DatabaseReference mDatabase;
    private static final String CHILD_NAME = "users";
    User user;
    List<User> users;

    @Override
    public Optional<User> get(String username) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(CHILD_NAME).child(username).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                user = task.getResult().getValue(User.class);
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        List<User> users = new ArrayList<>();

        mDatabase.child(CHILD_NAME).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    User addUser = dataSnapshot.getValue(User.class);
                    users.add(addUser);
                }
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });
        return users;
    }

    @Override
    public void save(User user, boolean filled) {
        // Create database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (filled) { // If the user is entirely new or just needs to be entirely updated
            mDatabase.child("users").child(user.getUsername()).setValue(user);
        } else { // If the user only needs to be partially updated
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("username").setValue(user.getUsername());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("password").setValue(user.getPassword());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("blocked_users").setValue(user.getBlockedUsers());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("display_name").setValue(user.getDisplayName());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("profile_picture").setValue(user.getProfilePicture());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("familiarity").setValue(user.getFamiliarity());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("topics").setValue(user.getTopics());
            if (user.getUsername() != null) mDatabase.child("users").child(String.valueOf(user.getUsername()))
                    .child("friend_requests").setValue(user.getFriends());
        }
    }

    @Override
    public void delete(User user) {

    }
}
