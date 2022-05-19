package com.example.comp2100_assignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.comp2100_assignment.users.Familiarity;
import com.example.comp2100_assignment.users.Interestedness;
import com.example.comp2100_assignment.users.Language;
import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.users.User;
import com.example.comp2100_assignment.database.UserDao;
import com.example.comp2100_assignment.conversations.ConversationTopic;

/**
 * @author Zane Gates
 * allows users to modify some details about their account
 * including display name, interested topic, and language familiarity levels
 */
public class AccountSettingsActivity extends TabbedActivity {
    Switch[] switches;

    ConversationTopic[] topics;
    Spinner[] spinners;
    Language[] languages;
    Familiarity[] languageFamiliarities;
    EditText displayNameEditor;

    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        tabs = new UITabs(findViewById(R.id.tabs), this);

        // Get the user passed in from the MainActivity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Get the switches from the UI
        int[] switchIDs = {R.id.switchMusic, R.id.switchTravel, R.id.switchFood, R.id.switchSports};
        topics = new ConversationTopic[]{ConversationTopic.MUSIC, ConversationTopic.TRAVEL, ConversationTopic.FOOD, ConversationTopic.SPORTS};

        // Gets the language dropdown from the UI
        int[] languageSpinnerIDs = {R.id.languageSpinner1, R.id.languageSpinner2, R.id.languageSpinner3, R.id.languageSpinner4, R.id.languageSpinner5};
        languages = new Language[]{Language.ENGLISH, Language.ITALIAN, Language.GERMAN, Language.FRENCH, Language.JAPANESE};
        spinners = new Spinner[languageSpinnerIDs.length];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageSkillLevels, android.R.layout.simple_spinner_item);
        languageFamiliarities = new Familiarity[] {Familiarity.UNINTERESTED, Familiarity.BEGINNER, Familiarity.INTERMEDIATE, Familiarity.ADVANCED, Familiarity.FLUENT};

        for (int i = 0; i < languageSpinnerIDs.length; i++) {
            spinners[i] = findViewById(languageSpinnerIDs[i]);
            spinners[i].setAdapter(adapter);
            switch(user.getFamiliarity(languages[i])) {
                case BEGINNER: spinners[i].setSelection(1); break;
                case INTERMEDIATE: spinners[i].setSelection(2); break;
                case ADVANCED: spinners[i].setSelection(3); break;
                case FLUENT: spinners[i].setSelection(4); break;
                default: spinners[i].setSelection(0); break;
            }
        }

        switches = new Switch[switchIDs.length];
        for (int i = 0; i < switches.length; i++) {
            switches[i] = findViewById(switchIDs[i]);
            System.out.println(user + ": " + user.getConversationTopics());
            System.out.println(user.getConversationTopic(topics[i]) == Interestedness.INTERESTED);
            switches[i].setChecked(user.getConversationTopic(topics[i]) == Interestedness.INTERESTED);
        }

        displayNameEditor = findViewById(R.id.editDisplayName);
        displayNameEditor.setText(user.getDisplayName());

        reportButton = findViewById(R.id.reportButton);
        reportButton.setOnClickListener(generateReport);
    }

    View.OnClickListener generateReport = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println(user.generateUserReport());
        }
    };

    @Override
    public void exitTabCallback() {
        for (int i = 0; i < switches.length; i++) {
            user.setConversationTopic(topics[i], switches[i].isChecked() ? Interestedness.INTERESTED : Interestedness.UNINTERESTED);
        }
        for (int i = 0; i < spinners.length; i++) {
            user.setFamiliarity(languages[i], languageFamiliarities[spinners[i].getSelectedItemPosition()]);
        }
        user.setDisplayName(displayNameEditor.getText().toString());
        UserDao.singleton().save(user, false);
    }
}