package com.example.eventmanagement;

public abstract class User {
    private String username;
    private String password;
    private String dateOfBirth;

    public User(String username, String password, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract boolean login();
    public abstract boolean signup();
    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}

