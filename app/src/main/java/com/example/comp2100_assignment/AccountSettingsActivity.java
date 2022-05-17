package com.example.comp2100_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AccountSettingsActivity extends AppCompatActivity {

    User user;

    Switch[] switches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // Get the user passed in from the MainActivity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Get the switches from the UI
        int[] switchIDs = {R.id.switchMusic, R.id.switchTravel, R.id.switchFood, R.id.switchSports};
        ConversationTopic[] topics = {ConversationTopic.MUSIC, ConversationTopic.TRAVEL, ConversationTopic.FOOD, ConversationTopic.SPORTS};

        // Gets the language dropdown from the UI
        int[] languageSpinnerIDs = {R.id.languageSpinner1, R.id.languageSpinner2, R.id.languageSpinner3, R.id.languageSpinner4, R.id.languageSpinner5};
        Language[] languages = {Language.ENGLISH, Language.ITALIAN, Language.GERMAN, Language.FRENCH, Language.JAPANESE};
        Spinner[] spinners = new Spinner[languageSpinnerIDs.length];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageSkillLevels, android.R.layout.simple_spinner_item);
        Familiarity[] languageFamiliarities = new Familiarity[] {Familiarity.UNINTERESTED, Familiarity.BEGINNER, Familiarity.FLUENT};

        for (int i = 0; i < languageSpinnerIDs.length; i++) {
            spinners[i] = findViewById(languageSpinnerIDs[i]);
            spinners[i].setAdapter(adapter);
            switch(user.getFamiliarity(languages[i])) {
                case BEGINNER: spinners[i].setSelection(1); break;
                case INTERMEDIATE: spinners[i].setSelection(1); break;
                case ADVANCED: spinners[i].setSelection(2); break;
                case FLUENT: spinners[i].setSelection(2); break;
                default: spinners[i].setSelection(0); break;
            }
        }

        switches = new Switch[switchIDs.length];
        for (int i = 0; i < switches.length; i++) {
            switches[i] = findViewById(switchIDs[i]);
            switches[i].setChecked(user.getTopicsSet(topics[i]));
        }

        EditText displayNameEditor = findViewById(R.id.editDisplayName);
        displayNameEditor.setText(user.getDisplayName());

        ((Button)findViewById(R.id.saveSettingsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < switches.length; i++) {
                    user.setTopicsSet(topics[i], switches[i].isChecked());
                }

                for (int i = 0; i < spinners.length; i++) {
                    user.setFamiliarity(languages[i], languageFamiliarities[spinners[i].getSelectedItemPosition()]);
                    System.out.println(languages[i] + "; " + user.getFamiliarity(languages[i]));
                }

                user.setDisplayName(displayNameEditor.getText().toString());

                UserDao dao = new UserDao();
                dao.save(user, false);

                Intent intent = new Intent();
                intent.setClass(AccountSettingsActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}