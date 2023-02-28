package com.example.web_app.model;

public class User implements UserInterface {
    private int id;
    private String username;
    private String password;
    private String role;

    public User(int id, String username, String password,String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
