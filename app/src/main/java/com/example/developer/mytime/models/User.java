package com.example.developer.mytime.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by developer on 9/12/2017.
 */

@IgnoreExtraProperties
public class User {
    private int id;
    private String email;
    private String password;

    public User() {
        /*
        * Default constructor required for calls to
        * DataSnapshot.getValue(User.class)
        * */
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
