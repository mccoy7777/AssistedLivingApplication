package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    //Declare variables
    EditText etName, etEmail, etPassword, etPhone, etDob;
    Button btnRegister;
    TextView tvLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


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
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }//if statement to show user that they have successfully created their profile
                        else {
                            Toast.makeText(Register.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }//else statement to show user if there is an error when creating their profile

                    }//end of OnComplete method

                });//End of addOnCompleteListener method

            }//End of OnClick method

        });//End of OnClickListener


    }//End of OnCreate Method

}//End of Register class
