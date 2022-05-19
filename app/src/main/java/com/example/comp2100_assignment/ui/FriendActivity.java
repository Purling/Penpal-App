package com.example.comp2100_assignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.users.User;

import java.util.ArrayList;

/**
 * Allows users to visit conversations with any of their friends.
 * These conversations are persistent and stored within the database.
 *
 * @author Zane Gates
 */
public class FriendActivity extends TabbedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        user = (User) getIntent().getSerializableExtra("user");
        tabs = new UITabs(findViewById(R.id.tabs), this);

        // Gets all of the user's friends
        ArrayList<String> friends = new ArrayList<>();
        ArrayList<String> targetConversations = new ArrayList<>();
        for (String key : user.getFriends().keySet()) {
            friends.add(DatabaseUserManager.getUser(key).getDisplayName());
            targetConversations.add(user.getFriends().get(key));
        }

        // Displays all the friends in a list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                friends);
        ListView listView = findViewById(R.id.friend_list);
        listView.setAdapter(adapter);

        // Logic for what happens when a friend is clicked (the conversation should open)
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String conversationTarget = targetConversations.get(i);
            Intent intent = new Intent();
            intent.setClass(FriendActivity.this, ConversationActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("conversationName", conversationTarget);
            intent.putExtra("permanent", true);
            intent.putExtra("owner", true);
            startActivity(intent);
        });
    }
}