package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class PrescriptionService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_service);
    }//end of onCreate method

    //Create method that allows user to logout of application
    public void addNewPrescription (View view){
        startActivity(new Intent(getApplicationContext(), AddNewPrescription.class));
        finish();
    }//End of addNewPrescription method

    //Create method that allows user to logout of application
    public void createMonthlyReminder (View view){
        startActivity(new Intent(getApplicationContext(), CreateMonthlyReminder.class));
        finish();
    }//End of createMonthlyReminder method

}//end of PrescriptionService class
