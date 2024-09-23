package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginPage extends AppCompatActivity {
    TextInputEditText phoneNumber,password;
    public static final String TAG=".login";
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        phoneNumber=findViewById(R.id.loginPhoneNumber);
        password=findViewById(R.id.loginPassword);
        login=findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=phoneNumber.getText().toString();
                String userPassword=password.getText().toString().trim();
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                DocumentReference documentReference=db.collection("users").document(number);
                Log.d("pritItaliya", "onClick: "+userPassword);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String registeredName=documentSnapshot.getString("username");
                        String registeredMobileNumber=documentSnapshot.getString("mobilenumber");
                        String registeredEmail=documentSnapshot.getString("email");
                        String registeredPassword=documentSnapshot.getString("password").trim();
                        MD5 md5=new MD5();
                        String confirmPass= md5.md5(userPassword);
                        
                        Log.d(TAG, "onSuccess: "+documentSnapshot.getString("password"));
                        Log.d(TAG, "onSuccess: "+confirmPass);
                        if (confirmPass.equals(registeredPassword)){
                           SharedPreferences sharedPreferences=getSharedPreferences("credentials",MODE_PRIVATE);
                           SharedPreferences.Editor editor=sharedPreferences.edit();
                           editor.putString("username", String.valueOf(registeredName));
                           editor.putString("email", String.valueOf(registeredEmail));
                           editor.putString("phonenumber", String.valueOf(registeredMobileNumber));
                           editor.apply();
                           Log.d(TAG, "if: same");
                           Intent intent =new Intent(getApplicationContext(), homePage.class);
                           startActivity(intent);
                        }else {
                            Log.d(TAG, "else: "+(registeredPassword.equals(registeredPassword)));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: failed");
                    }
                });
            }
        });


    }
}