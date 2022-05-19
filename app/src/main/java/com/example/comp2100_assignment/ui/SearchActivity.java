package com.example.comp2100_assignment.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comp2100_assignment.Parser;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.Tokenizer;
import com.example.comp2100_assignment.conversations.ConversationTopic;
import com.example.comp2100_assignment.database.UserDao;
import com.example.comp2100_assignment.users.Familiarity;
import com.example.comp2100_assignment.users.Interestedness;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.users.User;

import java.util.ArrayList;
import java.util.List;

/***
 * Class which controls logic for searching within the app
 *
 * @author Xingkun Chen, Ziling Ouyang
 */
public class SearchActivity extends TabbedActivity {
    private final List<String> listViewList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private Button button;
    private ListView listView;
    private EditText editText;
    public View.OnClickListener find_Listener = (view) -> {

        listViewList.clear();
        String input = editText.getText().toString();
        Tokenizer tokenizer = new Tokenizer(input);
        if (tokenizer.current() == null)
            Toast.makeText(getApplicationContext(), "Invalid Search Query", Toast.LENGTH_SHORT);
        try {
            String parser = new Parser(tokenizer).parseExp().show();
            String[] content = parser.split(",");
            ArrayList<String> contain = new ArrayList<>();
            ArrayList<String> notContain = new ArrayList<>();

            for (String s : content) {
                if (s.contains("not")) {
                    String word = s.substring(4);
                    word = word.trim();
                    notContain.add(word);
                } else
                    contain.add(s.trim());
            }

            UserDao.singleton().getAll(data -> {
                for (String contains : contain) {
                    if (!listViewList.contains(data.getUsername())) {
                        try {
                            if (data.getFamiliarity(Language.valueOf(contains)) != Familiarity.UNINTERESTED) {
                                listViewList.add(data.getUsername());
                            }
                        } catch (Exception ignored) {
                        }

                        try {
                            if (data.getConversationTopic(ConversationTopic.valueOf(contains)) == Interestedness.INTERESTED) {
                                listViewList.add(data.getUsername());
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }

                for (String notContains : notContain) {
                    if (listViewList.contains(data.getUsername())) {
                        try {
                            if (data.getFamiliarity(Language.valueOf(notContains)) != Familiarity.UNINTERESTED) {
                                listViewList.remove(data.getUsername());
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            if (data.getConversationTopic(ConversationTopic.valueOf(notContains)) == Interestedness.INTERESTED) {
                                listViewList.remove(data.getUsername());
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            });
        } catch (Exception ignored) {
            arrayAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        user = (User) getIntent().getSerializableExtra("user");

        tabs = new UITabs(findViewById(R.id.tabs), this);

        button = findViewById(R.id.search);
        editText = findViewById(R.id.Input);
        listView = findViewById(R.id.result);
        button.setOnClickListener(find_Listener);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listViewList);
        listView.setAdapter(arrayAdapter);
    }


}