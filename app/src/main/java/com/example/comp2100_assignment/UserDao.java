package com.example.comp2100_assignment;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements DaoPattern<User, String> {

    @Override
    public String getChildName() {
        return "userList";
    }

    @Override
    public Class<User> getModelClass() {
        return User.class;
    }


    @Override
    public void getAll(OnGetDataListener<List<User>> listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference();

        mDatabase.child(getChildName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>(); // clearing the users
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User addUser = dataSnapshot.getValue(User.class);
                    users.add(addUser);
                }
                listener.onSuccess(users);
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
        }
    }

    @Override
    public void delete(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(getChildName()).child(username).removeValue();
    }
}
