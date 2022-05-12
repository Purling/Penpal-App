package com.example.comp2100_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private Button friend;
    private Button search;
    private EditText input;
    private ListView listView;
    private ImageView profilePicture;
    private Button match;

    DatabaseUserManager manager;

    UserPartial user;

    ConversationsAvailable available;
    HashMap<String, String> conversationsAvailable = new HashMap<>();

    DatabaseReference availableReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = DatabaseUserManager.getInstance(getBaseContext());

        //Intent intent = getIntent();
        //user = (UserPartial) intent.getSerializableExtra("USER");

        input = findViewById(R.id.input_text);
        friend = findViewById(R.id.friend);
        friend.setOnClickListener(friend_listener);
        search = findViewById(R.id.search);
        search.setOnClickListener(search_listener);
        listView = findViewById(R.id.my_lv);
        match = findViewById(R.id.match);
        match.setOnClickListener(match_listener);
        profilePicture = findViewById(R.id.profile_picture);

        manager = DatabaseUserManager.getInstance(getBaseContext());

        Intent intent = getIntent();
        user = (UserPartial) intent.getSerializableExtra("user");
        //System.out.println(user);
        // The avatar, stored in user.avatar, is either null or a String representing the address of the image
        if(user == null || user.avatar == null){
            //notify user if avatar isn't set
            Toast.makeText(getApplicationContext(),"User has no avatar",Toast.LENGTH_SHORT).show();
        }
        else {
            // need to create a new thread to load data from web
            // https://stackoverflow.com/a/14443056
            Thread profilepicthread = new Thread(() -> {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(user.avatar).openConnection().getInputStream());
                    // ui can't be updated in thread
                    // https://stackoverflow.com/a/5162096
                    runOnUiThread(() -> profilePicture.setImageBitmap(bitmap));
                } catch (IOException e) {
                    //if image can't be found
                    Toast.makeText(getApplicationContext(),"Unable to get avatar from url",Toast.LENGTH_SHORT).show();
                }
            });
            profilepicthread.start();
        }

        availableReference = manager.getDatabase().getReference("availableConversations");

        availableReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                conversationsAvailable.put(snapshot.getKey(), snapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                conversationsAvailable.remove(previousChildName);
                conversationsAvailable.put(snapshot.getKey(), snapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                conversationsAvailable.remove(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public View.OnClickListener search_listener = (view) -> {
        Intent intent = new Intent();
        intent.putExtra("search",input.getText().toString());
        intent.setClass(MainActivity.this, SearchActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    };
    public View.OnClickListener friend_listener = (view) -> {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FriendActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    };

    public View.OnClickListener match_listener = (view)->{
        for (String otherUser : conversationsAvailable.keySet()) {
            if (conversationsAvailable.get(otherUser).equals("#QUEUED")) {
                final String conversationName = "new convo";
                availableReference.child(otherUser).setValue(conversationName);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, QueueActivity.class);
                intent.putExtra("conversationName", otherUser);
                intent.putExtra("user", user);
                //intent.putExtra("owner", true);
                startActivity(intent);
                return;
            }
        }

        availableReference.child(user.username).setValue("#QUEUED");

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, QueueActivity.class);
        intent.putExtra("conversationName", user.username);
        intent.putExtra("user", user);
        startActivity(intent);
    };
}