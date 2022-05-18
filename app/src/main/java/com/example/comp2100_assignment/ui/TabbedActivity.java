package com.example.comp2100_assignment.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2100_assignment.users.User;

/**
 * Superclass for any activity using the tab menu at the bottom
 * Currently this is FriendActivity, MainActivity, and AccountSettingsActivity
 *
 * @author Zane Gates
 */
public abstract class TabbedActivity extends AppCompatActivity {

    User user;
    UITabs tabs;

    /**
     * Function called when a different tab is selected,
     * so the menu needs to save any data and release
     * possible memory leaks
     */
    public void exitTabCallback() {

    }
}
