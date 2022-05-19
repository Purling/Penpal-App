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
    Spinner[] spinners;

    int[] switchIDs = {R.id.switchMusic, R.id.switchTravel, R.id.switchFood, R.id.switchSports};
    ConversationTopic[] topics = new ConversationTopic[]{ConversationTopic.MUSIC, ConversationTopic.TRAVEL, ConversationTopic.FOOD, ConversationTopic.SPORTS};
    int[] languageSpinnerIDs = {R.id.languageSpinner1, R.id.languageSpinner2, R.id.languageSpinner3, R.id.languageSpinner4, R.id.languageSpinner5, R.id.languageSpinner6, R.id.languageSpinner7};
    Language[] languages = new Language[]{Language.ENGLISH, Language.ITALIAN, Language.GERMAN, Language.FRENCH, Language.JAPANESE, Language.KOREAN, Language.MANDARIN};
    Familiarity[] languageFamiliarities = new Familiarity[] {Familiarity.UNINTERESTED, Familiarity.BEGINNER, Familiarity.INTERMEDIATE, Familiarity.ADVANCED, Familiarity.FLUENT};

    EditText displayNameEditor;
    EditText avatarEditor;
    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        tabs = new UITabs(findViewById(R.id.tabs), this);

        // Get the user passed in from the MainActivity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Gets the language dropdown from the UI, and sets them to the value stored in the User
        spinners = new Spinner[languageSpinnerIDs.length];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageSkillLevels, android.R.layout.simple_spinner_item);
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

        // Get the switches from the UI, and sets them to the values stored in the User
        switches = new Switch[switchIDs.length];
        for (int i = 0; i < switches.length; i++) {
            switches[i] = findViewById(switchIDs[i]);
            System.out.println(user + ": " + user.getConversationTopics());
            System.out.println(user.getConversationTopic(topics[i]) == Interestedness.INTERESTED);
            switches[i].setChecked(user.getConversationTopic(topics[i]) == Interestedness.INTERESTED);
        }

        displayNameEditor = findViewById(R.id.editDisplayName);
        displayNameEditor.setText(user.getDisplayName());

        avatarEditor = findViewById(R.id.editAvatar);
        avatarEditor.setText(user.getAvatar());

        reportButton = findViewById(R.id.reportButton);
        reportButton.setOnClickListener(generateReport);
    }

    View.OnClickListener generateReport = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            exitTabCallback();
            Intent intent = new Intent();
            intent.setClass(AccountSettingsActivity.this, AccountReportActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    };

    /**
     * When the user exits this activity (either by pressing another tab)
     * or going to the report viewer, we must save any changed settings
     */
    @Override
    public void exitTabCallback() {
        for (int i = 0; i < switches.length; i++) {
            user.setConversationTopic(topics[i], switches[i].isChecked() ? Interestedness.INTERESTED : Interestedness.UNINTERESTED);
        }
        for (int i = 0; i < spinners.length; i++) {
            user.setFamiliarity(languages[i], languageFamiliarities[spinners[i].getSelectedItemPosition()]);
        }
        user.setDisplayName(displayNameEditor.getText().toString().replaceAll("\n", ""));
        user.setAvatar(avatarEditor.getText().toString().replaceAll("\n", ""));
        UserDao.singleton().save(user, false);
    }
}