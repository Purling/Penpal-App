package com.example.comp2100_assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.net.URL;


public class MainActivity extends TabbedActivity {
    DatabaseUserManager manager;

    DatabaseReference availableReference;
    DatabaseDictionaryWatcher queueWatcher;

    public View.OnClickListener match_listener = (view) -> {
        for (String otherUser : queueWatcher.map.keySet()) {
            if (queueWatcher.map.get(otherUser).equals("#QUEUED")) {
                User other = DatabaseUserManager.getUser(otherUser);
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
    private Button friend;
    private Button search;
    private EditText input;
    public View.OnClickListener search_listener = (view) -> {
        Intent intent = new Intent();
        intent.putExtra("search", input.getText().toString());
        intent.setClass(MainActivity.this, SearchActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    };
    private ListView listView;
    private ImageView profilePicture;
    private Button match;
    private Button settingsButton;
    private Button logOutButton;

    public View.OnClickListener log_out_listener = (view) -> {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = new UITabs(findViewById(R.id.tabs), this);

        manager = DatabaseUserManager.getInstance(getBaseContext());

        input = findViewById(R.id.input_text);
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
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(user.getAvatar()).openConnection().getInputStream());
                runOnUiThread(() -> profilePicture.setImageBitmap(bitmap));
            } catch (Exception ignored) {
            }
        }).start();

        availableReference = manager.getDatabase().getReference("availableConversations");
        queueWatcher = new DatabaseDictionaryWatcher(availableReference);

        logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(log_out_listener);
    }

    public <T extends AppCompatActivity> void passUser(Class<T> appCompatActivity) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, appCompatActivity);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}