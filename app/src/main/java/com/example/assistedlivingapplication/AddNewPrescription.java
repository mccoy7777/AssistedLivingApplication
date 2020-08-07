package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class AddNewPrescription extends AppCompatActivity {

    //Declare variables
    EditText prescriptionName;
    NumberPicker dosage;
    Button addPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_prescription);

        //Assign variables
        prescriptionName = findViewById(R.id.et_PrescriptionName);
        dosage = findViewById(R.id.np_dosage);

        dosage.setMinValue(1);
        dosage.setMaxValue(10);

        addPrescription = findViewById(R.id.btn_AddPrescription);

        //Set onClickListener for add prescription button
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrescription();
            }//end of onClick method

        });//end of onClickListener method



    }//end of onCreate method

    //Create method to save prescription
    private void savePrescription() {
        String name = prescriptionName.getText().toString();
        int prescriptionDosage = dosage.getValue();

        if (name.trim().isEmpty()){
            Toast.makeText(this, "Cannot leave field blank", Toast.LENGTH_SHORT).show();
            return;
        }//end of if statement

        //Add it to the firebase database
        CollectionReference prescriptionRef = FirebaseFirestore.getInstance()
                .collection("Prescriptions");

        Prescription prescription = new Prescription(name, prescriptionDosage);

        prescriptionRef.add(prescription);

        Toast.makeText(this, "Prescription Added", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), PrescriptionService.class));

    }//end of saveTask method

}//end of AddNewPrescription service
