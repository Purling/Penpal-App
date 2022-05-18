package com.example.comp2100_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user = (User) getIntent().getSerializableExtra("user");

        ArrayList<String> friends = new ArrayList<>();
        ArrayList<String> targetConversations = new ArrayList<>();
        for (String key : user.getFriends().keySet()) {
            friends.add(DatabaseUserManager.getUser(key).getDisplayName());
            targetConversations.add(user.getFriends().get(key));
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                friends);

        ListView listView = findViewById(R.id.friend_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String conversationTarget = targetConversations.get(i);
                Intent intent = new Intent();
                intent.setClass(FriendActivity.this, ConversationActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("conversationName", conversationTarget);
                intent.putExtra("permanent", true);
                intent.putExtra("owner", true);
                startActivity(intent);
            }
        });
    }
}