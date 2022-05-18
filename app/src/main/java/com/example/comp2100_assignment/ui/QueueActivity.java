package com.example.comp2100_assignment.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Here, the user waits for a suitable person to form a conversation with
 * and moves to the conversation activity as soon as such a conversation is formed
 *
 * Users who are joining a preexisting conversation briefly visit here while
 * space for the conversation is allocated in the database
 * @author Zane Gates
 */
public class QueueActivity extends AppCompatActivity {

    DatabaseReference awaitingConversation;

    User user;
    boolean willBeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String conversationName = intent.getStringExtra("conversationName");
        willBeOwner = intent.getBooleanExtra("willBeOwner", false);

        awaitingConversation = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("availableConversations").child(conversationName);

        ((Button)findViewById(R.id.exitQueueButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                awaitingConversation.removeValue();

                Intent intent = new Intent();
                intent.setClass(QueueActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        awaitingConversation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newConversationName = snapshot.getValue(String.class);
                if (newConversationName != null && !newConversationName.equals("#QUEUED")) {
                    Intent intent = new Intent();
                    intent.setClass(QueueActivity.this, ConversationActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("conversationName", newConversationName);
                    intent.putExtra("queueName", conversationName);
                    intent.putExtra("owner", willBeOwner);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}