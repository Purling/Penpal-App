package com.example.comp2100_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {

    EditText messageBox;
    TextView conversation;

    ArrayList<UserMessage> messages = new ArrayList<>();

    DatabaseReference conversationRoot;
    DatabaseReference conversationMessagesRoot;

    DatabaseReference conversationsAvailable;
    ConversationsAvailable available;

    UserPartial user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Intent intent = getIntent();
        user = (UserPartial) intent.getSerializableExtra("user");
        String conversationName = intent.getStringExtra("conversationName");
        boolean owner = intent.getBooleanExtra("owner", true);
        messageBox = findViewById(R.id.messageBox);
        messageBox.setInputType(InputType.TYPE_NULL); // Hides the keyboard
        conversation = findViewById(R.id.conversation);

        conversationRoot = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("conversation").child(conversationName);
        conversationMessagesRoot = conversationRoot.child("messages");

        conversationsAvailable = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("availableConversations");
        conversationsAvailable.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 available = snapshot.getValue(ConversationsAvailable.class);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
        });

        conversationRoot.child(owner ? "user1" : "user2").setValue(user.username);

        UserMessage message = new UserMessage("", user.username + " joined.");
        conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);

        conversationMessagesRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserMessage message = snapshot.getValue(UserMessage.class);
                boolean added = false;
                for (int i = 0; i < messages.size(); i++) {
                    if (Long.valueOf(message.time) < Long.valueOf(messages.get(i).time)) {
                        messages.add(i, message);
                        added = true;
                        break;
                    }
                }
                if (!added) messages.add(message);
                updateConversationDisplay();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserMessage message = new UserMessage(user.username, messageBox.getText().toString());
                conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);
                messageBox.setText("");
            }
        });

        findViewById(R.id.exitConversationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                conversationRoot.removeValue();
                intent.setClass(ConversationActivity.this, MainActivity.class);
                intent.putExtra("user", user);

                available.usernames.remove(conversationName);
                conversationsAvailable.setValue(available);

                startActivity(intent);
            }
        });
    }

    void updateConversationDisplay() {
        //TODO: make it look nicer
        StringBuilder sb = new StringBuilder();
        for (UserMessage message : messages) {
            sb.append(message).append("\n");
        }
        System.out.println(sb.toString());
        conversation.setText(sb.toString());
    }
}