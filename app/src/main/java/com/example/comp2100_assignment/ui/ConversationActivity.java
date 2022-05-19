package com.example.comp2100_assignment.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.comp2100_assignment.conversations.ConversationFormer;
import com.example.comp2100_assignment.conversations.ConversationTopic;
import com.example.comp2100_assignment.conversations.TransitoryConversation;
import com.example.comp2100_assignment.conversations.UserMessage;
import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.users.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Zane Gates
 * visualises and allows users to interact with transitory and permanent conversations
 */
public class ConversationActivity extends AppCompatActivity {

    EditText messageBox;
    TextView conversation;

    ArrayList<UserMessage> messages = new ArrayList<>();

    DatabaseReference conversationRoot;
    DatabaseReference conversationMessagesRoot;

    DatabaseReference availableReference;

    User user;

    User user1;
    User user2;
    boolean metadataGenerated;
    boolean owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String conversationName = intent.getStringExtra("conversationName");
        boolean permanent = intent.getBooleanExtra("permanent", false);
        String queueName = permanent ? "" : intent.getStringExtra("queueName");
        owner = intent.getBooleanExtra("owner", false);
        messageBox = findViewById(R.id.messageBox);
        messageBox.setInputType(InputType.TYPE_NULL); // Hides the keyboard
        conversation = findViewById(R.id.conversation);

        FirebaseDatabase database = DatabaseUserManager.getInstance(getBaseContext()).getDatabase();

        conversationRoot = database.getReference("conversation").child(conversationName);
        conversationMessagesRoot = conversationRoot.child("messages");

        System.out.println(conversationName);
        availableReference = database.getReference("availableConversations").child(queueName);

        conversationRoot.child(owner ? "user1" : "user2").setValue(user.getUsername());

        UserMessage message = new UserMessage("", user.getUsername() + " joined.");
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
                UserMessage message = new UserMessage(user.getUsername(), messageBox.getText().toString());
                conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);
                messageBox.setText("");
            }
        });

        findViewById(R.id.exitConversationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!permanent)
                    availableReference.setValue("#CLOSED");
                returnToMainActivity(permanent);
            }
        });

        availableReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (permanent) return;
                if ((snapshot.getValue(String.class) == null) || snapshot.getValue(String.class).equals("#CLOSED"))
                    returnToMainActivity(permanent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        associateLabel(R.id.user1Label, R.id.user1Avatar, conversationRoot.child("user1"), 1);
        associateLabel(R.id.user2Label, R.id.user2Avatar, conversationRoot.child("user2"), 2);

        if (permanent) return;

        conversationRoot.child("topic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ConversationTopic topic = snapshot.getValue(ConversationTopic.class);
                ((TextView)findViewById(R.id.conversationTopic)).setText(topic == null ? "" : topic.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationRoot.child("language1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Language language = snapshot.getValue(Language.class);
                ((TextView)findViewById(R.id.language1)).setText(language == null ? "" : language.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationRoot.child("language2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Language language = snapshot.getValue(Language.class);
                ((TextView)findViewById(R.id.language2)).setText(language == null ? "" : language.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void associateLabel(int label, int avatarID, DatabaseReference reference, int userNumber) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = DatabaseUserManager.getUser(snapshot.getValue(String.class));
                if (u == null) return;
                if (userNumber == 1) user1 = u;
                if (userNumber == 2) user2 = u;
                ((TextView)findViewById(label)).setText(u.getDisplayName());
                new Thread(() -> {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(u.getAvatar()).openConnection().getInputStream());
                        runOnUiThread(() -> ((ImageView)findViewById(avatarID)).setImageBitmap(bitmap));
                    } catch (Exception ignored) {}
                }).start();
                if (owner && !metadataGenerated && user1 != null && user2 != null) {
                    metadataGenerated = true;
                    TransitoryConversation generatedConversation = ConversationFormer.getInstance().formConversation(user1, user2);
                    conversationRoot.child("topic").setValue(generatedConversation.getTopic());
                    conversationRoot.child("language1").setValue(generatedConversation.getLanguage1());
                    conversationRoot.child("language2").setValue(generatedConversation.getLanguage2());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void updateConversationDisplay() {
        //TODO: make it look nicer
        ScrollView scrollView = findViewById(R.id.conversationScrollView);
        LinearLayout messageLayout = findViewById(R.id.conversationLinearLayout);
        //only need to do this for last message
        //so get latest message
        UserMessage message = messages.get(messages.size()-1);
        //generate the view
        LinearLayout linearLayout = new LinearLayout(this);
        TextView messageAuthor = new TextView(this);
        messageAuthor.setText(message.author);
        messageAuthor.setTextSize(20);
        TextView conversationText = new TextView(this);
        conversationText.setText(message.content);
        messageAuthor.setTextSize(12);
        linearLayout.addView(messageAuthor);
        linearLayout.addView(conversationText);
        messageLayout.addView(linearLayout);
    }

    void returnToMainActivity(boolean permanent) {
        Intent intent = new Intent();
        intent.setClass(ConversationActivity.this, permanent ? FriendActivity.class : MainActivity.class);
        intent.putExtra("user", user);

        startActivity(intent);
    }
}