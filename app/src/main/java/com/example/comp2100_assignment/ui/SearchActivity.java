package com.example.comp2100_assignment.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.comp2100_assignment.Exp;
import com.example.comp2100_assignment.Parser;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.Tokenizer;
import com.example.comp2100_assignment.conversations.Conversation;
import com.example.comp2100_assignment.conversations.ConversationTopic;
import com.example.comp2100_assignment.users.Familiarity;
import com.example.comp2100_assignment.users.Interestedness;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private EditText editText;
    private List<String> arrayList = new ArrayList<String>();
    private List<String> listViewList = new ArrayList<String>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("username");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent= getIntent();
        intent.getStringExtra("search");
        startActivity(intent);

        button = findViewById(R.id.search);
        editText = findViewById(R.id.Input);
        listView = findViewById(R.id.result);
        button.setOnClickListener(find_Listener);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listViewList);
        listView.setAdapter(arrayAdapter);
        String input = editText.getText().toString();
        Tokenizer tokenizer = new Tokenizer(input);
        String parser = new Parser(tokenizer).parseExp().show();
        String[] content = parser.split(",");
        ArrayList<String> contain = new ArrayList<String>();
        ArrayList<String> Notcontain = new ArrayList<String>();

        for (int i = 0; i < content.length; i++) {
            if (content[i].contains("not")){
                String word = content[i].substring(4);
                Notcontain.add(word);
            }else
                contain.add(content[i]);
        }


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    String username = user.getUsername();

                    for (int i = 0; i < contain.toArray().length; i++) {
                        String word = (String) contain.toArray()[i];
                        if (!arrayList.contains(username)) {
                            if (word.equalsIgnoreCase("ENGLISH") & user.getFamiliarity(Language.ENGLISH) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("ITALIAN") & user.getFamiliarity(Language.ITALIAN) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("GERMAN") & user.getFamiliarity(Language.GERMAN) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("FRENCH") & user.getFamiliarity(Language.FRENCH) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("JAPANESE") & user.getFamiliarity(Language.JAPANESE) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("KOREAN") & user.getFamiliarity(Language.KOREAN) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("MANDARIN") & user.getFamiliarity(Language.MANDARIN) != Familiarity.UNINTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("MUSIC") & user.getConversationTopic(ConversationTopic.MUSIC) == Interestedness.INTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("SPORTS") & user.getConversationTopic(ConversationTopic.SPORTS) == Interestedness.INTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("FOOD") & user.getConversationTopic(ConversationTopic.FOOD) == Interestedness.INTERESTED) {
                                arrayList.add(word);
                            }
                            if (word.equalsIgnoreCase("TRAVEL") & user.getConversationTopic(ConversationTopic.TRAVEL) == Interestedness.INTERESTED) {
                                arrayList.add(word);
                            }
                        }
                        if (Notcontain.toArray().length > 0) {
                            for (int j = 0; j < Notcontain.toArray().length; j++) {
                                String notCont = (String) Notcontain.toArray()[j];
                                if (arrayList.contains(username)) {
                                    if (word.equalsIgnoreCase("ENGLISH") & user.getFamiliarity(Language.ENGLISH) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("ITALIAN") & user.getFamiliarity(Language.ITALIAN) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("GERMAN") & user.getFamiliarity(Language.GERMAN) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("FRENCH") & user.getFamiliarity(Language.FRENCH) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("JAPANESE") & user.getFamiliarity(Language.JAPANESE) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("KOREAN") & user.getFamiliarity(Language.KOREAN) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("MANDARIN") & user.getFamiliarity(Language.MANDARIN) == Familiarity.UNINTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("MUSIC") & user.getConversationTopic(ConversationTopic.MUSIC) != Interestedness.INTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("SPORTS") & user.getConversationTopic(ConversationTopic.SPORTS) != Interestedness.INTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("FOOD") & user.getConversationTopic(ConversationTopic.FOOD) != Interestedness.INTERESTED) {
                                        arrayList.add(word);
                                    }
                                    if (word.equalsIgnoreCase("TRAVEL") & user.getConversationTopic(ConversationTopic.TRAVEL) != Interestedness.INTERESTED) {
                                        arrayList.add(word);
                                    }
                                }
                            }
                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(eventListener);
    }
    public View.OnClickListener find_Listener = (view) -> {
        Intent intent = new Intent();
        listViewList.clear();
        listViewList = arrayList;
        startActivity(intent);
    };

}