package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class splashView extends AppCompatActivity {

    LoadingAlert loadingAlert=new LoadingAlert(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        loadingAlert.startDialog(this)
        getSupportActionBar().hide();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              loadingAlert.startDialog(this);
              Handler handler1=new Handler();
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      SharedPreferences sharedPreferences = getSharedPreferences("credentials", 0);
                      String alreadyExistsUsername = sharedPreferences.getString("username", "");
                      String alreadyExistsPhoneNumber = sharedPreferences.getString("phonenumber", "");
                      String alreadyExistsEmail = sharedPreferences.getString("email", "");
                      if (alreadyExistsUsername.length() == 0) {
                          Intent intent = new Intent(getApplicationContext(), signUp.class);
                          startActivity(intent);
                          finish();
                      }else {
                          Log.d("MainPage", "in else onCreate: "+alreadyExistsUsername);
                          Intent intent=new Intent(getApplicationContext(),homePage.class);
                          startActivity(intent);
                          finish();
                      }
                      loadingAlert.dismissdialog();
                  }
              },1000);
            }
        },1000);

    }

//    /**
//     * {@inheritDoc}
//     * <p>
//     * Dispatch onResume() to fragments.  Note that for better inter-operation
//     * with older versions of the platform, at the point of this call the
//     * fragments attached to the activity are <em>not</em> resumed.
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences sharedPreferences = getSharedPreferences("Login", 0);
//        String alreadyExistsUsername = sharedPreferences.getString("username", "");
//        if (alreadyExistsUsername.length() == 0) {
//            Intent intent = new Intent(getApplicationContext(), signUp.class);
//            startActivity(intent);
//            finish();
//        }else {
//            Log.d("MainPage", "in else onCreate: "+alreadyExistsUsername);
//            Intent intent=new Intent(getApplicationContext(),loginPage.class);
//            startActivity(intent);
//            finish();
//        }
//
//    }
}