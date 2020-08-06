package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class MondayTasks extends AppCompatActivity {

    //Create Variables
    TextView mondayTasks;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference taskRef = fStore.collection("Tasks").document("My Tasks");

    CollectionReference allTasksRef = fStore.collection("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday_tasks);

        //Assign variables
        mondayTasks = findViewById(R.id.tv_MondayTask);


        //Get tasks from database
        allTasksRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Tasks tasks = documentSnapshot.toObject(Tasks.class);

                    String name = tasks.getName();
                    String description = tasks.getDescription();

                    data += "Name : " + name + "\n Description : " + description + "\n\n";
                }//end of for loop

                mondayTasks.setText(data);
            }//end of onSuccess method

        });//end of onSuccess Listener


    }//end of onCreate Method

}//end of MondayTasks class
