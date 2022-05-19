package com.example.comp2100_assignment.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.users.User;

/**
 * @author Zane Gates
 * Displays a report of the user's interactions within the app for GDPR (and COMP2100!) compliance
 */
public class AccountReportActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_report);

        user = (User) getIntent().getSerializableExtra("user");

        ((TextView)findViewById(R.id.userReport)).setText(user.generateUserReport());

        ((Button)findViewById(R.id.backButton)).setOnClickListener(returnToMainActivityListener);
    }

    View.OnClickListener returnToMainActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(AccountReportActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    };
}