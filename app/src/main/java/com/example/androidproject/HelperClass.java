package com.example.androidproject;

public class HelperClass {
    String username,mobilenumber,email,password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
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

    public HelperClass() {
    }

    public HelperClass(String username, String mobilenumber, String email, String password) {
        this.username = username;
        this.mobilenumber = mobilenumber;
        this.email = email;
        this.password = password;
    }
}
