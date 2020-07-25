package com.example.assistedlivingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreenActivity extends AppCompatActivity {

    //Declare button variable
    Button btnLogout;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Assign button to layout
        btnLogout = findViewById(R.id.btn_logout);

        //Setup OnClickListener for Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intToMain = new Intent(HomeScreenActivity.this, LoginRegister.class);
                startActivity(intToMain);
            }//end of OnClick method

        });////end of OnClickListener

    }//End of OnCreate method

}//End of class
