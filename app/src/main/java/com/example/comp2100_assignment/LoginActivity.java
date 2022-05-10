package com.example.comp2100_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText username;
    private EditText password;
    BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        register.setOnClickListener(registerListener);

    try {
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("logindetails.csv"), StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(",");
            for (String token : tokens) {
                if (token.contains(username.getText().toString())) {
                    login.setOnClickListener(loginListener);
                } else
                    login.setOnClickListener(registerListener);
            }
        }
        bufferedReader.close();
        }catch (IOException e){
        }
    }
    //TODO: read data from the file to check username and password.


    public View.OnClickListener loginListener = (view) -> {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    };
    //TODO: write data into the file


    // this should be jump to register one
    public View.OnClickListener registerListener = (view) -> {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    };

}