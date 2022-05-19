package com.example.comp2100_assignment.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.reports.Interaction;
import com.example.comp2100_assignment.reports.InteractionType;
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

    int eyeImageIndex = 0;
    int ticksUntilNextFrame = 1;

    boolean leavingToConversaion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String conversationName = intent.getStringExtra("conversationName");
        willBeOwner = intent.getBooleanExtra("willBeOwner", false);

        awaitingConversation = DatabaseUserManager.getInstance(getBaseContext()).getDatabase().getReference("availableConversations").child(conversationName);

        // When the user exists the queue, remove their name from the queue
        // and then return to the main activity
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

        // When a suitable conversation is found
        // (that is, the database queue pointer becomes the name of a conversation)
        // we exit the queue and move to that conversation
        awaitingConversation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newConversationName = snapshot.getValue(String.class);
                if (newConversationName != null && !newConversationName.equals("#QUEUED")) {
                    user.logInteraction(InteractionType.TRANSITORY_CONVERSATION, newConversationName);
                    Intent intent = new Intent();
                    intent.setClass(QueueActivity.this, ConversationActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("conversationName", newConversationName);
                    intent.putExtra("queueName", conversationName);
                    intent.putExtra("owner", willBeOwner);
                    leavingToConversaion = true;
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Finally, display an animation of a blinking eye on the queue
        // screen to show users the app has not crashed, and is actually
        // working as intended

        int[] eyes = {R.drawable.eye_open,
                R.drawable.eye_part_open,
                R.drawable.eye_part_closed,
                R.drawable.eye_closed,
                R.drawable.eye_part_closed,
                R.drawable.eye_part_open,};

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(eyes[1]);

        // Run a tick every 100 milliseconds. Once enough ticks have passed
        // move to the next frame and set the number of necessary ticks
        // depending on the current image
        CountDownTimer timer = new CountDownTimer(100, 100) {
            @Override
            public void onFinish() {
                ticksUntilNextFrame--;
                if (ticksUntilNextFrame <= 0) {
                    eyeImageIndex = (eyeImageIndex + 1) % eyes.length;
                    imageView.setImageResource(eyes[eyeImageIndex]);
                    switch(eyes[eyeImageIndex]) {
                        case R.drawable.eye_open: ticksUntilNextFrame = 20; break;
                        case R.drawable.eye_closed: ticksUntilNextFrame = 3; break;
                        default: ticksUntilNextFrame = 1;
                    }
                }
                start();
            }

            @Override
            public void onTick(long l) {

            }
        };
        timer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // This code is triggered when the user exits this activity
        // without triggering one of the other exit functions --
        // implying their app has crashed or phone has powered off
        // -- in which case we must manually remove them from the queue
        if (!leavingToConversaion)
            awaitingConversation.removeValue();
    }
}