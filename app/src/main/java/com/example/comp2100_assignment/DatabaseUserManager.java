package com.example.comp2100_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Context;

import java.util.HashMap;

public class DatabaseUserManager {
    static DatabaseUserManager instance;
    static DatabaseUserManager getInstance(Context baseContext) {
        if (instance == null) instance = new DatabaseUserManager(baseContext);
        return instance;
    }

    final static HashMap<String, UserPartial> users = new HashMap<>();

    private DatabaseUserManager(Context baseContext) {
        FirebaseApp.initializeApp(baseContext);
        FirebaseDatabase database = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        DatabaseReference usersRoot = database.getReference("users");
        System.out.println("Generated users root.");

        usersRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                users.put(snapshot.getKey(), new UserPartial(
                        tryToGet(snapshot, "username"),
                        tryToGet(snapshot, "password"),
                        tryToGet(snapshot, "avatar")
                ));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public static String tryToGet(DataSnapshot snapshot, String tag) {
        try {
            return snapshot.child(tag).getValue(Long.class).toString();
        } catch (Exception ignored) {}
        try {
            return snapshot.child(tag).getValue(String.class);
        } catch (Exception ignored) {}
        return "";
    }

    public void printUserData() {
        for (String user : users.keySet()) {
            System.out.println(user + ": " + users.get(user));
        }
    }

    public UserPartial attemptLogin(String username, String password) {
        for (UserPartial user : users.values()) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }
}
