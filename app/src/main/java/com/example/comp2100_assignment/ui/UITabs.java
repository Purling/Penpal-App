package com.example.comp2100_assignment.ui;

import android.content.Intent;

import com.google.android.material.tabs.TabLayout;

/**
 * @author Zane Gates
 * Adds logic to the included UI tab menu to make it
 * show the correct tab, and go to the correct layout
 * when a different tab is selected
 */
public class UITabs {
    TabLayout view;
    TabbedActivity activity;

    public UITabs(TabLayout view, TabbedActivity activity) {
        this.view = view;
        this.activity = activity;

        view.selectTab(view.getTabAt(getID(activity.getClass())));

        view.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activity.exitTabCallback();
                Intent intent = new Intent();
                intent.putExtra("user", activity.user);
                System.out.println((String) tab.getText());
                intent.setClass(activity, getTab((String) tab.getText()));
                System.out.println(getTab((String) tab.getText()));
                activity.startActivity(intent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Gets the activity corresponding to a listing in the menu
     * @param name the listing, which may need to be cast from CharSequence
     * @return the activity
     */
    public Class getTab(String name) {
        switch(name) {
            case "SETTINGS": return AccountSettingsActivity.class;
            case "MAIN": return MainActivity.class;
            case "FRIENDS": return FriendActivity.class;
            default: return MainActivity.class;
        }
    }

    /**
     * Get the index of appearance of a particular listing
     * @param c the class listed in the menu
     * @return the index, counting rightward from 0
     */
    public int getID (Class c) {
        if (AccountSettingsActivity.class.equals(c)) {
            return 0;
        } else if (MainActivity.class.equals(c)) {
            return 1;
        } else if (FriendActivity.class.equals(c)) {
            return 2;
        }
        return 1;
    }
}
