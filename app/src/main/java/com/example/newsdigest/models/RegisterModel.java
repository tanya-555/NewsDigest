package com.example.newsdigest.models;

public class RegisterModel {

    public String username;

    public String password;

    public String confirmPassword;

    public RegisterModel(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
