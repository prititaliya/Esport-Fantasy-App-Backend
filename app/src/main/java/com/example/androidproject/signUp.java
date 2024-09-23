package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class signUp extends AppCompatActivity {
    TextInputEditText username,useremail,phonenumber,password,confirmPassword;
    Button signupButton;
    AwesomeValidation validation;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG=".signUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=findViewById(R.id.username);
        useremail=findViewById(R.id.useremail);
        phonenumber=findViewById(R.id.userMobile);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        signupButton=findViewById(R.id.signupButton);


        confirmPassword.addTextChangedListener(confirmPasswordChnageListener);
        validation=new AwesomeValidation(ValidationStyle.BASIC);
        validation.addValidation(this,R.id.username,RegexTemplate.NOT_EMPTY,R.string.emptyUsername);
        validation.addValidation(this,R.id.useremail, Patterns.EMAIL_ADDRESS,R.string.pattern);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=phonenumber.getText().toString();
                String email=useremail.getText().toString();
                String name=username.getText().toString();
                String pass=password.getText().toString().trim();
                String cPass=confirmPassword.getText().toString();
                String encryption=MD5Fuction(pass);
                    if(validation.validate() && checkCredential(number,pass,cPass)){
                        HelperClass helperClass=new HelperClass(name,number,email,encryption);
                        DocumentReference documentReference=db.collection("users").document(number);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.getResult().getString("mobilenumber")==null){
                                    DocumentReference documentReference1=db.collection("users").document(number);
                                    documentReference1.set(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "onSuccess: success");
                                            login(view);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "onFailure: failed");
                                        }
                                    });
                                }else {
                                    Log.d(TAG, "onComplete: else"+task.getResult());
                                    Toast.makeText(getApplicationContext(), "Phone number is already registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Log.d("helperuser", "onClick: "+helperClass.getUsername()+" "+helperClass.getEmail()+" "+helperClass.getMobilenumber()+" "+helperClass.getPassword());
                    }else {
                        if(number.length()!=10){
                            Toast.makeText(getApplicationContext(), "enter valid phone number"+number, Toast.LENGTH_SHORT).show();
                        }else if(pass!=cPass){
                            Toast.makeText(getApplicationContext(), "passwords doesnt match "+pass+" "+cPass, Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });

    }

    private String MD5Fuction(String  password) {
        MD5 md5=new MD5();
        String hashpass=md5.md5(password.trim());

        return  hashpass;

    }

    public void login(View view){
        Intent intent=new Intent(this,loginPage.class);
        startActivity(intent);
    }
        public boolean checkCredential(String number,String pass,String cPass){
                if (number.length()==10 && pass.compareTo(cPass)==0){
                    Log.d("credentials", "checkCredential: "+number+" "+pass+" "+cPass);
                    return true;
                }else {
                    Log.d("credentials", "checkCredential: "+number+" "+pass+" "+cPass+" "+pass.compareTo(cPass));
                    return false;
                }
        }

    TextWatcher confirmPasswordChnageListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            signupButton.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String enteredUserName=username.getText().toString();
            String enteredUserEmail=useremail.getText().toString();
            String enteredUserMobile=phonenumber.getText().toString();
            String enteredUserPassword=password.getText().toString();
            String enteredUserConfirmPassword=confirmPassword.getText().toString();

            if(enteredUserName.length()!=0 && enteredUserEmail.length()!=0 && enteredUserMobile.length()!=0 && enteredUserPassword.length()!=0 && enteredUserConfirmPassword.length()!=0){
//                Toast.makeText(getApplicationContext(), "all data is filledup", Toast.LENGTH_SHORT).show();

            signupButton.setEnabled(true);
            }
        }
    };


}