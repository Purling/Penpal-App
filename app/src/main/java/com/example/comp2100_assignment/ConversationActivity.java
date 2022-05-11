package com.example.comp2100_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    UserPartial user;

    EditText messageBox;
    TextView conversation;

    ArrayList<String> messages = new ArrayList<>();

    DatabaseReference conversationRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Intent intent = getIntent();
        UserPartial user = (UserPartial) intent.getSerializableExtra("USER");
        messageBox = findViewById(R.id.messageBox);
        conversation = findViewById(R.id.conversation);

        conversationRoot = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("conversation");

        conversationRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                messages.add((String) snapshot.getValue());
                conversation.setText(messages.toString());
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
                String message = messageBox.getText().toString();
                messageBox.setText("");
                messageBox.clearFocus();
                messageBox.setFocusable(false);
                conversationRoot.child(String.valueOf(System.currentTimeMillis())).setValue("<" + user.username + "> " + message);
            }
        });
    }


}