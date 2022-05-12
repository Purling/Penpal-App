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

    User user;

    DatabaseReference availableReference;

    DatabaseDictionaryWatcher queueWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = DatabaseUserManager.getInstance(getBaseContext());

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
        user = (User) intent.getSerializableExtra("user");

        new Thread(() -> {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(user.avatar).openConnection().getInputStream());
                runOnUiThread(() -> profilePicture.setImageBitmap(bitmap));
            } catch (Exception ignored) {}
        }).start();

        availableReference = manager.getDatabase().getReference("availableConversations");
        queueWatcher = new DatabaseDictionaryWatcher(availableReference);
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
        for (String otherUser : queueWatcher.map.keySet()) {
            if (queueWatcher.map.get(otherUser).equals("#QUEUED")) {
                User other = DatabaseUserManager.get(otherUser);
                Conversation formed = ConversationFormer.getInstance().formConversation(user, other);
                if (formed != null) {
                    availableReference.child(otherUser).setValue(System.currentTimeMillis() + "_transitory");
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, QueueActivity.class);
                    intent.putExtra("conversationName", otherUser);
                    intent.putExtra("user", user);
                    intent.putExtra("willBeOwner", true);
                    startActivity(intent);
                    return;
                }
            }
        }

        availableReference.child(user.getUsername()).setValue("#QUEUED");

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, QueueActivity.class);
        intent.putExtra("conversationName", user.getUsername());
        intent.putExtra("user", user);
        intent.putExtra("willBeOwner", false);
        startActivity(intent);
    };
}