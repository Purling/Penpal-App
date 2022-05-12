package com.example.comp2100_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    DatabaseUserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        register.setOnClickListener(registerListener);
        login.setOnClickListener(loginListener);

        manager = DatabaseUserManager.getInstance(getBaseContext());

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
        UserPartial successful = manager.attemptLogin(username.getText().toString(), password.getText().toString());
        if (successful != null) {
            System.out.println("Logged in successfully as " + successful.username);
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            intent.putExtra("user", successful);
            startActivity(intent);
        } else {
            System.out.println("Login failed... incorrect credentials?");
        }
    };
    //TODO: write data into the file( But it seems that we cannot wrtie file in android studio)


    public View.OnClickListener registerListener = (view) -> {
        // if username already exists, notify user
        if(manager.userExists(username.getText().toString())){
            Toast.makeText(getApplicationContext(),"Username already exists",Toast.LENGTH_LONG).show();
        }
        // if password doesn't meet policy
        else if(!passwordMeetsPolicy(password.getText().toString())){
            Toast.makeText(getApplicationContext(),"Password doesn't meet requirements",Toast.LENGTH_SHORT).show();
        }
        // create account
        else{
            // check that inputted password is okay

            // todo: create new user
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    };
    // verifies that a password meets defined password policy
    // current policy is:
    // password must be at least 1 character
    public boolean passwordMeetsPolicy(String password){
        if(password == null){
            return false;
        }
        if(password.equals("")){
            return false;
        }
        return true;
    }

}