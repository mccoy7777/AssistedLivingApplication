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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //Declare Variables
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign variables
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btn_login);
        tvLogin = findViewById(R.id.tv_login);

        //Create new onClickListener for Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

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

                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "User Successfully Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }//if statement to let user know they have successfully logged in
                        else {
                            Toast.makeText(Login.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }//else statement to show any login errors

                    }//end of onComplete method

                });//end of addOnCompleteListener method

            }//end of onClick method

        });//end of onClickListener

        //Create onClickListener for login textview
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }//end of OnClick method

        });//End of onClickListener method


    }//End of OnCreate method

}//End of Login class
