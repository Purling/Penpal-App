package com.example.comp2100_assignment.database;

import android.content.Context;

import com.example.comp2100_assignment.users.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUserManager {
    static DatabaseUserManager instance;
    private static UserBinarySearchTree users;
    private final FirebaseDatabase database;

    private DatabaseUserManager(Context baseContext) {
        FirebaseApp.initializeApp(baseContext);
        database = FirebaseDatabase.getInstance(
                "https://comp2100-team-assignment-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        UserDao.singleton().getAll(data -> {
            if (users == null) {
                users = new UserBinarySearchTree().setRoot(new UserBinarySearchTree.UserBinaryTreeNode(data));
            } else {
                users.addSubTree(users.getHead(), new UserBinarySearchTree.UserBinaryTreeNode(data));
            }
        });
    }

    public static DatabaseUserManager getInstance(Context baseContext) {
        if (instance == null) instance = new DatabaseUserManager(baseContext);
        return instance;
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean userExists(String username) {
        return getUser(username) != null;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
}
