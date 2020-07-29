package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //Declare Variables
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvLogin, tvForgotPassword;
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
        tvForgotPassword = findViewById(R.id.tv_forgotpassword);

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

        //Create onClickListener for Forgot Password link
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create new edit text view and use alert dialogue class to prompt user to reset password
                final EditText resetEmail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to receive reset link");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send the reset link
                        String email = resetEmail.getText().toString();
                        fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }//end of onSuccess method

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error, Reset Link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }//end of onFailure method
                        });//end of onFailure Listener

                    }//end of onClick method

                });//end of onClickListener

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the dialogue

                    }//end of onClick method

                });//end of onClickListener

                passwordResetDialog.create().show();

            }//end of onClick method

        });//end of onClickListener


    }//End of OnCreate method

}//End of Login class
