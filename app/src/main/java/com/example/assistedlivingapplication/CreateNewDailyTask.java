package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewDailyTask extends AppCompatActivity {

    //Declare variables
    EditText taskName, description;
    Button add;
    Spinner spnDaysOfWeek;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference allTasksRef = fStore.collection("Tasks");

    private final String KEY_TASK_NAME = "task name";
    private final String KEY_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_daily_task);

        //Assign variables
        spnDaysOfWeek = findViewById(R.id.spn_DaysOfTheWeek);

        taskName = findViewById(R.id.et_TaskName);
        description = findViewById(R.id.et_Description);

        add = findViewById(R.id.btn_AddTask);

        //Create new array list
        List <String> daysOfTheWeek = new ArrayList<>();
        daysOfTheWeek.add("- Choose the day of the week -");
        daysOfTheWeek.add("Monday");
        daysOfTheWeek.add("Tuesday");
        daysOfTheWeek.add("Wednesday");
        daysOfTheWeek.add("Thursday");
        daysOfTheWeek.add("Friday");
        daysOfTheWeek.add("Saturday");
        daysOfTheWeek.add("Sunday");

        //Setup array adapter
        ArrayAdapter<String> daysOfTheWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfTheWeek);
        daysOfTheWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDaysOfWeek.setAdapter(daysOfTheWeekAdapter);

        //Set up click listener for array adapter
        spnDaysOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String yourChoice = spnDaysOfWeek.getSelectedItem().toString();

            }//end of onItemSelected method

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }//end of onNothingSelected

        });//end of setOnItemSelectedListener method

        //Set onClickListener for Save Button to save the new task to the database
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set variables and get the text from that the user inputs
                 String addTaskName = taskName.getText().toString().trim();
                 String addDescription = description.getText().toString().trim();

                 //Create Tasks object
                 Tasks tasks = new Tasks(addTaskName, addDescription);

                 //Add tasks to firebase collections
                allTasksRef.add(tasks).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateNewDailyTask.this, "New Task Added", Toast.LENGTH_SHORT).show();
                    }//end of onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewDailyTask.this, "Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                    }//end of onFailure method

                });//end of onSuccess Listener

                //Use intent to switch back to DailyTasks screen
                Intent i = new Intent(getApplicationContext(), DailyTasks.class );
                startActivity(i);

            }//end of onClick method

        });//end of OnClickListener

    }//end of onCreate Method


}//end of CreateNewDailyTask class
