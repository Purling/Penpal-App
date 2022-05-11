package com.example.comp2100_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {

    EditText messageBox;
    TextView conversation;

    ArrayList<String> messages = new ArrayList<>();

    DatabaseReference conversationRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Intent intent = getIntent();
        String conversationName = intent.getStringExtra("conversationName");
        messageBox = findViewById(R.id.messageBox);
        messageBox.setInputType(InputType.TYPE_NULL); // Hides the keyboard
        conversation = findViewById(R.id.conversation);

        conversationRoot = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("conversation").child(conversationName).child("messages");

        sendMessage(false, ">" + SessionInformationStorer.user.username + " joined the conversation.");

        conversationRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                messages.add((String) snapshot.getValue());
                String output = "";
                for (String s : messages) {
                    output += s + "\n";
                }
                conversation.setText(output);
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
                sendMessage(true, messageBox.getText().toString());
                messageBox.setText("");

            }
        });

        System.out.println(SessionInformationStorer.user);
    }

    private void sendMessage(boolean includeName, String contents) {
        conversationRoot.child(String.valueOf(System.currentTimeMillis())).setValue((includeName ? "<" + SessionInformationStorer.user.username + "> " : "") + contents);
    }
}