package com.example.comp2100_assignment;

import java.io.Serializable;

public class UserPartial implements Serializable {
    public String username;
    public String password;
    public String avatar;

    public UserPartial(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public String toString() {
        return username + " / " + password + " / " + avatar;
    }
}
