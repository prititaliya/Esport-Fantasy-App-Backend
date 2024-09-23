package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        SharedPreferences preferences = getSharedPreferences("credentials", 0);
        preferences.edit().remove("username").remove("phonenumber").remove("email").commit();
        Intent intent=new Intent(getApplicationContext(),splashView.class);
        startActivity(intent);
    }
}