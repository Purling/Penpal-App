package com.example.comp2100_assignment.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.conversations.ConversationFormer;
import com.example.comp2100_assignment.conversations.ConversationTopic;
import com.example.comp2100_assignment.conversations.TransitoryConversation;
import com.example.comp2100_assignment.conversations.UserMessage;
import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.database.UserDao;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Visualises and allows users to interact with transitory and permanent conversations.
 * Transitory conversations are made using the queueing system.
 * Permanent conversations are between users who are friends.
 *
 * @author Zane Gates, William Loughton
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

    User otherUser;
    boolean metadataGenerated;
    boolean owner;

    Button addFriendButton;

    boolean permanent;

    /***
     *
     * @author Zane Gates, Ziling Ouyang (removing joined messages)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // Get all the information passed through from intent
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String conversationName = intent.getStringExtra("conversationName");
        permanent = intent.getBooleanExtra("permanent", false);
        String queueName = permanent ? "" : intent.getStringExtra("queueName");
        owner = intent.getBooleanExtra("owner", false);

        // Get UI elements
        messageBox = findViewById(R.id.messageBox);
        conversation = findViewById(R.id.conversation);

        FirebaseDatabase database = DatabaseUserManager.getInstance(getBaseContext()).getDatabase();

        conversationRoot = database.getReference("conversation").child(conversationName);
        conversationMessagesRoot = conversationRoot.child("messages");

        availableReference = database.getReference("availableConversations").child(queueName);

        conversationRoot.child(owner ? "user1" : "user2").setValue(user.getUsername());

        // Only make the message joined if the conversation is not permanent
        if (!permanent) {
            UserMessage message = new UserMessage("", user.getUsername() + " joined.");
            conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);

        }

        // We use a listener to keep track of every message sent in this conversation
        // The arrayList is updated whenever this changes, and the conversation is updated
        conversationMessagesRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserMessage message = snapshot.getValue(UserMessage.class);
                boolean added = false;
                if (message != null && permanent && message.author.equals("")) {
                    return;
                }
                for (int i = 0; i < messages.size(); i++) {
                    if (Long.parseLong(message.time) < Long.parseLong(messages.get(i).time)) {
                        messages.add(i, message);
                        added = true;
                        break;
                    }
                }
                if (!added) messages.add(message);
                updateConversationDisplay();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Send a message and clear the box when the button is pressed
        findViewById(R.id.sendButton).setOnClickListener(view -> {
            UserMessage message = new UserMessage(user.getUsername(), messageBox.getText().toString());
            conversationMessagesRoot.child(String.valueOf(message.hashCode())).setValue(message);
            messageBox.setText("");
        });

        // Logic for what happens when the exit button is pressed
        findViewById(R.id.exitConversationButton).setOnClickListener(view -> {
            if (!permanent)
                availableReference.setValue("#CLOSED");
            returnToMainActivity(permanent);
        });

        // Whenever either user presses the back button of a transitory conversation, both users should exit
        availableReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (permanent) return;
                if ((snapshot.getValue(String.class) == null) || snapshot.getValue(String.class).equals("#CLOSED")) {
                    System.out.println(snapshot.getValue(String.class));
                    System.out.println("Closed by #CLOSED");
                    returnToMainActivity(permanent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Make the username labels and avatars correspond with the conversation's users
        associateLabel(R.id.user1Label, R.id.user1Avatar, conversationRoot.child("user1"), 1);
        associateLabel(R.id.user2Label, R.id.user2Avatar, conversationRoot.child("user2"), 2);

        addFriendButton = findViewById(R.id.addFriendButton);
        // The friend button becomes an unfriend button if this is a permanent conversation
        if (permanent) {
            addFriendButton.setBackgroundColor(getResources().getColor(R.color.gray));
            addFriendButton.setText("Unfriend");
        }
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permanent) {
                    // (As an unfriend button) remove the friend and exit the conversation
                    user.removeFriendConversation(conversationName);
                    UserDao.singleton().save(user, false);
                    returnToMainActivity(permanent);
                } else {
                    // (As a friend button) save the conversation, but remain
                    user.addFriendConversation(otherUser.getUsername(), conversationName);
                    UserDao.singleton().save(user, false);
                    addFriendButton.setEnabled(false);
                }
            }
        });

        if (permanent) return;

        // Use the topic and language labels to spur conversation
        // Each of these loads the corresponding value from the database and stores it here
        // We can't use an immediate listener because these values might not be set
        // by the time either user reaches this activity, depending on connection speed

        conversationRoot.child("topic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ConversationTopic topic = snapshot.getValue(ConversationTopic.class);
                ((TextView) findViewById(R.id.conversationTopic)).setText(topic == null ? "" : topic.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationRoot.child("language1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Language language = snapshot.getValue(Language.class);
                ((TextView) findViewById(R.id.language1)).setText(language == null ? "" : language.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationRoot.child("language2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Language language = snapshot.getValue(Language.class);
                ((TextView) findViewById(R.id.language2)).setText(language == null ? "" : language.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Makes GUI elements show the data of a particular user
     * @param label the ID for the GUI name
     * @param avatarID the ID for the GUI avatar
     * @param reference the database reference to where the user is stored (as a child of conversation)
     * @param userNumber the index of the user (1 or 2)
     */
    void associateLabel(int label, int avatarID, DatabaseReference reference, int userNumber) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = DatabaseUserManager.getUser(snapshot.getValue(String.class));
                if (u == null) return;
                if (userNumber == 1) user1 = u;
                if (userNumber == 2) user2 = u;
                if (u != user) otherUser = u;
                ((TextView) findViewById(label)).setText(u.getDisplayName());
                new Thread(() -> {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(u.getAvatar()).openConnection().getInputStream());
                        runOnUiThread(() -> ((ImageView) findViewById(avatarID)).setImageBitmap(bitmap));
                    } catch (Exception ignored) {
                    }
                }).start();
                if (!permanent && owner && !metadataGenerated && user1 != null && user2 != null) {
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

    /**
     * @author Zane Gates and William Loughton
     * displays all messages in the conversation
     */
    void updateConversationDisplay() {
        ScrollView scrollView = findViewById(R.id.conversationScrollView);
        LinearLayout messageLayout = findViewById(R.id.conversationLinearLayout);
        //this code updates everything on every message
        //to prevent desync errors
        //clear everything
        messageLayout.removeAllViewsInLayout();
        //display each message
        for (UserMessage message : messages) {
            //generate the view
            LinearLayout linearLayout = new LinearLayout(this);
            TextView messageAuthor = new TextView(this);
            if (message.author != null) {
                User sender = DatabaseUserManager.getUser(message.author);
                if (sender != null) {
                    messageAuthor.setText(DatabaseUserManager.getUser(message.author).getDisplayName());
                    messageAuthor.setTextSize(20);
                    messageAuthor.setPadding(0, 0, 20, 0);
                }
            }
            TextView conversationText = new TextView(this);
            conversationText.setText(message.content);
            conversationText.setTextSize(16);
            linearLayout.addView(messageAuthor);
            linearLayout.addView(conversationText);
            messageLayout.addView(linearLayout);
        }
        messageLayout.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);
    }

    /**
     * Return to the appropriate activity given whether this conversation was permanent or not
     * @param permanent whether or not the conversation was permanent
     */
    void returnToMainActivity(boolean permanent) {
        Intent intent = new Intent();
        intent.setClass(ConversationActivity.this, permanent ? FriendActivity.class : MainActivity.class);
        intent.putExtra("user", user);

        startActivity(intent);
    }
}