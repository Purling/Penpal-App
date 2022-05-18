package com.example.comp2100_assignment;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DatabaseUserManager {
    static DatabaseUserManager instance;
    static HashMap<String, User> users = new HashMap<>();
    private final FirebaseDatabase database;

    private DatabaseUserManager(Context baseContext) {
        FirebaseApp.initializeApp(baseContext);
        database = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        UserDao.singleton().getAll(data -> {
            DatabaseUserManager.users.put(data.getUsername(), data);
        });
    }

    static DatabaseUserManager getInstance(Context baseContext) {
        if (instance == null) instance = new DatabaseUserManager(baseContext);
        return instance;
    }

    public static boolean userExists(String username) {
        return get(username) != null;
    }

    public static User get(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void printUserData() {
        for (String user : users.keySet()) {
            System.out.println(user + ": " + users.get(user));
        }
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
}
