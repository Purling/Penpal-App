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

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {

    EditText messageBox;
    TextView conversation;

    ArrayList<UserMessage> messages = new ArrayList<>();

    DatabaseReference conversationRoot;
    DatabaseReference conversationMessagesRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Intent intent = getIntent();
        String conversationName = intent.getStringExtra("conversationName");
        boolean owner = intent.getBooleanExtra("owner", true);
        messageBox = findViewById(R.id.messageBox);
        messageBox.setInputType(InputType.TYPE_NULL); // Hides the keyboard
        conversation = findViewById(R.id.conversation);

        conversationRoot = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("conversation").child(conversationName);
        conversationMessagesRoot = conversationRoot.child("messages");

        conversationRoot.child(owner ? "user1" : "user2").setValue(SessionInformationStorer.user.username);

        UserMessage message = new UserMessage("", SessionInformationStorer.user.username + " joined.");
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
                UserMessage message = new UserMessage(SessionInformationStorer.user.username, messageBox.getText().toString());
                conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);
                messageBox.setText("");
            }
        });

        System.out.println(SessionInformationStorer.user);
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