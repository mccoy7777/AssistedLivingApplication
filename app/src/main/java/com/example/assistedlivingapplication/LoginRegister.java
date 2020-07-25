package com.example.assistedlivingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRegister extends AppCompatActivity {

    //Setup edittext, buttons, textview and Firebase variables
     EditText et_email, et_password;
     Button btn_Register;
     TextView tv_signIn;
     FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);

        //assign variables to their positions on the layout
        mFirebaseAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_Register = findViewById(R.id.btn_register);
        tv_signIn = findViewById(R.id.tv_sign_in);

        //---------------Set OnClickListener for Register button--------------------------------
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (email.isEmpty()){
                    et_email.setError("Please enter your email");
                    et_email.requestFocus();
                }//end of if statement
                else if (password.isEmpty()){
                    et_password.setError("Please enter your password");
                    et_password.requestFocus();
                }//end of else if statement
                else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginRegister.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }//end of else if statement
                else if (! (email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginRegister.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(LoginRegister.this, "Registration failed, Please try again", Toast.LENGTH_SHORT).show();
                            }//end of if statement
                            else {
                                startActivity(new Intent(LoginRegister.this, HomeScreenActivity.class ));
                            }//end of else statement

                        }//end of on complete method

                    });//end of OnCompleteListener

                }//end of else if statement
                else {
                    Toast.makeText(LoginRegister.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }//end of else statement

            }//end of onClick method

        });//end of OnClickListener

        //-----------------Set OnClickListener for sign in textview-------------------------------------------
        tv_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginRegister.this, MainActivity.class);
                startActivity(i);
            }//end of OnClick method
        });//End of OnClickListener

    }//end of OnCreate Method

}//end of class
