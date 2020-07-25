package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Setup edittext, buttons, textview and Firebase variables
    EditText et_emailId, et_password;
    Button btn_signIn;
    TextView tv_notRegistered;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //Update application


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables to their positions on the layout
        mFirebaseAuth = FirebaseAuth.getInstance();
        et_emailId = findViewById(R.id.et_email2);
        et_password = findViewById(R.id.et_password2);
        btn_signIn = findViewById(R.id.btn_signin);
        tv_notRegistered = findViewById(R.id.tv_not_registered);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseUser = mFirebaseAuth.getCurrentUser();

                if (mFireBaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent (MainActivity.this, HomeScreenActivity.class);
                    startActivity(i);
                }//end of if statement
                else {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }//end of else statement

            }//end of FireBaseAuth method

        };//end of AuthStateListener

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_emailId.getText().toString();
                String password = et_password.getText().toString();

                if (email.isEmpty()){
                    et_emailId.setError("Please enter your email");
                    et_emailId.requestFocus();
                }//end of if statement
                else if (password.isEmpty()){
                    et_password.setError("Please enter your password");
                    et_password.requestFocus();
                }//end of else if statement
                else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }//end of else if statement
                else if (! (email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            }//end of if statement
                            else {
                                Intent intToHome = new Intent(MainActivity.this, HomeScreenActivity.class);
                                startActivity(intToHome);
                            }//end of else statement

                        }//end of onComplete method

                    });//end of OnCompleteListener

                }//end of else if statement

                else {
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }//end of else statement

            }//end of OnClick method

        });//end of set OnClickListener method

        tv_notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intRegister = new Intent(MainActivity.this, LoginRegister.class);
                startActivity(intRegister);
            }//end of OnClick method

        });//end of OnClickListener

    }
}
