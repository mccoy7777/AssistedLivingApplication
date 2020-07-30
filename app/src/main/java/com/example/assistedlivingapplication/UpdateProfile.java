package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class UpdateProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    //Declare variables
    EditText profileName, profileEmail, profilePhone, profileAge;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //Assign variables
        profileName = findViewById(R.id.et_name);
        profileEmail = findViewById(R.id.et_email);
        profilePhone = findViewById(R.id.et_phone);
        profileAge = findViewById(R.id.et_age);

        save = findViewById(R.id.btn_save);

        //Create Intent that uses the Intent from the previous activity (User Profile) & retrieve data values
        Intent data = getIntent();
        String name = data.getStringExtra("Name");
        String email = data.getStringExtra("Email");
        String phone = data.getStringExtra("Phone");
        String age = data.getStringExtra("Age");

        //Pass data to EditText section in the activity using setText method
        profileName.setText(name);
        profileEmail.setText(email);
        profilePhone.setText(phone);
        profileAge.setText(age);

        //Use Log TAG to check on LogCat to make sure data is passed to this activity
        Log.d(TAG, "onCreate: " + name + email + phone + age);

    }//end of onCreate method

}//end of UpdateProfile class
