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
import java.util.List;

public class DatabaseUserManager {
    static DatabaseUserManager instance;
    static DatabaseUserManager getInstance(Context baseContext) {
        if (instance == null) instance = new DatabaseUserManager(baseContext);
        return instance;
    }

    static HashMap<String, User> users = new HashMap<>();

    private DatabaseUserManager(Context baseContext) {
        FirebaseApp.initializeApp(baseContext);
        database = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        DatabaseReference usersRoot = database.getReference("userList");
        System.out.println("Generated users root.");

        new UserDao().getAll(new OnGetDataListener<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                for (User user : data) {
                    DatabaseUserManager.users.put(user.getUsername(), user);
                }
            }
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

    public User attemptLogin(String username, String password) {
        for (User user : users.values()) {
            if (user.tryLogin(username, password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean userExists(String username){
        return get(username) != null;
    }

    public static User get(String username) {
        for (User user : users.values()){
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private final FirebaseDatabase database;

    public FirebaseDatabase getDatabase() {
        return database;
    }
}
