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

    final static HashMap<String, String> users = new HashMap<>();

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
                String username = snapshot.child("username").getValue(String.class);
                String password = "";
                try {
                    password = snapshot.child("password").getValue(Long.class).toString();
                } catch (Exception ignored) {}
                try {
                    password = snapshot.child("password").getValue(String.class);
                } catch (Exception ignored) {}
                System.out.println(username + "/" + password);
                users.put(username, password);
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

    public void printUserData() {
        for (String user : users.keySet()) {
            System.out.println(user + ": " + users.get(user));
        }
    }

    public boolean attemptLogin(String username, String password) {
        for (String user : users.keySet()) {
            if (user.equals(username) && users.get(user).equals(password)) return true;
        }
        return false;
    }
}
