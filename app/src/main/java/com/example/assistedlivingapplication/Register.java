package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    //Declare variables
    EditText etName, etEmail, etPassword, etPhone, etDob;
    Button btnRegister;
    TextView tvLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assign variables
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etDob = findViewById(R.id.et_dob);

        btnRegister = findViewById(R.id.btn_login);
        tvLogin = findViewById(R.id.tv_login);

        FirebaseApp.initializeApp(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //Create if statement that uses FireBase to check if user has logged in before, if so - redirect to Main Activity
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }//end of if statement

        //Set OnClick Listener for the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set variables for required fields and get the text from what user inputs
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();
                final String dob = etDob.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email is required");
                    return ;
                }//if statement to validate that email is not empty

                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password is required");
                    return;
                }//if statement to validate that password is not empty

                if(password.length() < 6){
                    etPassword.setError("Password must be at least 6 characters");
                }//if statement to validate that password is at least 6 characters

                //Once the user clicks on register, progress bar becomes visible
                progressBar.setVisibility(view.VISIBLE);

                //Register the user into FireBase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Successfully Created", Toast.LENGTH_SHORT).show();

                            //Get userId from FireBase, store userId in collection called users
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);
                            //Create HashMap for all user details
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Email", email);
                            user.put("Phone", phone);
                            user.put("Dob", dob);

                            //Insert into FireCloud database, onSuccessListener will let user know that they have been added successfully
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userId);
                                }//end of onSuccess method

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }//end of onFailure method

                            });//end of OnSuccessListener

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }//if statement to show user that they have successfully created their profile
                        else {
                            Toast.makeText(Register.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }//else statement to show user if there is an error when creating their profile

                    }//end of OnComplete method

                });//End of addOnCompleteListener method

            }//End of OnClick method

        });//End of OnClickListener

        //Create onClickListener for login textview
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }//end of OnClick method

        });//End of onClickListener method


    }//End of OnCreate Method

}//End of Register class
