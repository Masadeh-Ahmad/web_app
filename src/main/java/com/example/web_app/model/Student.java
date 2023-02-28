package com.example.web_app.model;

import java.io.Serializable;

public class Student implements UserInterface {
    private int id;
    private String username;
    private String password;

    public Student(User user) {
        if(user.getRole().equals("STUDENT"))
            throw new IllegalArgumentException();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
