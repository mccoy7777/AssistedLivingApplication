package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PrescriptionService extends AppCompatActivity {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference prescriptionRef = fStore.collection("Prescriptions");
    PrescriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_service);

        setUpRecyclerView();
    }//end of onCreate method

    //Create method to setup Recycler adapter
    private void setUpRecyclerView(){

        Query query = prescriptionRef.orderBy("prescriptionName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Prescription> options = new FirestoreRecyclerOptions.Builder<Prescription>()
                .setQuery(query, Prescription.class)
                .build();

        adapter = new PrescriptionAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_prescription);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

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
