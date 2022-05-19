package com.example.comp2100_assignment.database;

import android.content.Context;

import com.example.comp2100_assignment.users.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 *  Uses a binary search tree to maintain a list of users in a database
 *  and fetches them upon request for other classes to access
 * @author Zane Gates, Ziling Ouyang
 */
public class DatabaseUserManager {

    private static UserBinarySearchTree users;
    private final FirebaseDatabase database;

    /**
     * implements singleton pattern
     */
    private static DatabaseUserManager instance;
    public static DatabaseUserManager getInstance(Context baseContext) {
        if (instance == null) instance = new DatabaseUserManager(baseContext);
        return instance;
    }

    /**
     * initializes the database and begins to fetch all the users
     * @param baseContext application base context, used only when database is yet to be initialized
     */
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

    /**
     * Fetches user by username
     * @param username
     * @return corresponding User if it exists, else null
     */
    public static User getUser(String username) {
        return users.get(username);
    }

    /**
     * checks if a user exists
     * @param username
     * @return whether the user exists
     */
    public static boolean userExists(String username) {
        return getUser(username) != null;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
}
