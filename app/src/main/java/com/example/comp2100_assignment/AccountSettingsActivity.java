package com.example.comp2100_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;

public class AccountSettingsActivity extends AppCompatActivity {

    User user;

    Switch[] switches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        int[] switchIDs = {R.id.switchMusic, R.id.switchTravel, R.id.switchFood, R.id.switchSports};
        ConversationTopic[] topics = {ConversationTopic.MUSIC, ConversationTopic.TRAVEL, ConversationTopic.FOOD, ConversationTopic.SPORTS};

        switches = new Switch[switchIDs.length];
        for (int i = 0; i < switches.length; i++) {
            switches[i] = findViewById(switchIDs[i]);
            switches[i].setChecked(user.getConversationTopic(topics[i]));
        }

        ((Button)findViewById(R.id.saveSettingsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder topicString = new StringBuilder();
                boolean firstTopicString = true;

                for (int i = 0; i < switches.length; i++) {
                    user.setConversationTopic(topics[i], switches[i].isChecked());
                    if (switches[i].isChecked()) {
                        if (!firstTopicString) topicString.append(",");
                        topicString.append(topics[i].toString());
                        firstTopicString = false;
                    }
                }
                DatabaseReference userReference = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("userList").child(user.getUsername());

                userReference.child("topics").setValue(topicString.toString());

                Intent intent = new Intent();
                intent.setClass(AccountSettingsActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}