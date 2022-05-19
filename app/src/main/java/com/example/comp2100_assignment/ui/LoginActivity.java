package com.example.comp2100_assignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2100_assignment.R;
import com.example.comp2100_assignment.database.DatabaseUserManager;
import com.example.comp2100_assignment.database.UserDao;
import com.example.comp2100_assignment.users.User;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    DatabaseUserManager manager;
    private Button login;
    private Button register;
    private EditText username;
    private EditText password;

    /***
     * Listener to check if the login details are correct. If they are, go to the main activity.
     *
     */
    public View.OnClickListener loginListener = (view) -> {

        // Create new user dao object
        UserDao userDao = UserDao.singleton();

        // When the Firebase database returns with the data, then do the operation.
        // Do not assume that the Firebase database has already returned with the data.
        userDao.get(username.getText().toString(), data -> {

            // If both the username and password match the database user details
            if (data != null && data.getUsername().equals(username.getText().toString())
                    && data.getPassword().equals(password.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Logged in successfully as " + data.getDisplayName(), Toast.LENGTH_SHORT).show();
                if (data.getConversationTopics() == null) data.setConversationTopics(new HashMap<>());
                passToMain(data);
            } else {
                Toast.makeText(getApplicationContext(), "Login Failed: Incorrect Credentials", Toast.LENGTH_LONG).show();
            }
        });
    };

    /***
     * Listener which activates upon clicking the register button.
     * It creates a new User if there doesn't exist one with the same username already, in the database.
     *
     */
    public View.OnClickListener registerListener = (view) -> {

        // Create new UserDao object
        UserDao userDao = UserDao.singleton();

        // Check if the username already exists
        userDao.get(username.getText().toString(), data -> {

            // If User already exists, notify the app user
            if (data != null) {
                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
            // If the password doesn't meet the policy, notify the app user
            } else if (!usernameMeetsPolicy(username.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Username doesn't meet requirements", Toast.LENGTH_SHORT).show();
            } if (!passwordMeetsPolicy(password.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Password doesn't meet requirements", Toast.LENGTH_SHORT).show();
            } else { // Create the account and store in database
                User newUser = new User(username.getText().toString(), password.getText().toString());
                userDao.save(newUser, true);
                Toast.makeText(getApplicationContext(), "Registered successfully as " + newUser.getDisplayName()
                        , Toast.LENGTH_SHORT).show();
                passToMain(newUser);
            }
        });
    };

    /***
     * Method passes the user received from the database into the MainActivity class
     *
     * @param user The user to pass
     */
    public void passToMain(User user) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

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
    }

    /**
     * Checks if the username is up to standard, requiring
     * its length to be at least 3 letters and contains only
     * alphabetical, numerical, dash, and underscore characters
     * @param username the username
     * @return whether the password is up to standard
     * @author Zane Gates
     */
    public boolean usernameMeetsPolicy(String username) {
        if (username == null) return false;
        if (username.length() <= 3) return false;
        // permitted characters: alphanumeric, -, _
        for (char x : username.toCharArray()) {
            if (x == '-' || x == '_') continue;
            if (x < '0' || ('9' < x && x < 'A') || ('Z' < x && x < 'a') || 'z' < x) return false;
        }
        return true;
    }

    /***
     * Checks if the password is up to standard
     * The standard is: the password must be at least 1 letter
     *
     * @param password The string password to be checked
     * @return True if the password is up to standard, false otherwise
     *
     *  * @author Ziling Ouyang and William Loughton
     *
     *
     */
    // TODO Could create a helper class or put this method somewhere else.
    public boolean passwordMeetsPolicy(String password) {

        // Check if the String contains at least one letter
        return !(password == null) && password.matches(".*[a-zA-Z]+.*");
    }

}
