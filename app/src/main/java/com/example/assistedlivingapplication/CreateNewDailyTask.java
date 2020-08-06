package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewDailyTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    //Declare variables
    EditText taskName, taskDescription, taskDay;
    TextView taskTime;
    Button addTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_daily_task);

        //Assign variables
        taskName = findViewById(R.id.et_TaskName);
        taskDescription = findViewById(R.id.et_Description);
        taskTime = findViewById(R.id.et_time);
        taskDay = findViewById(R.id.et_TaskDay);

        addTask = findViewById(R.id.btn_AddTask);


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }//end of onClick method

        });//end of onClickListener

        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }//end of onClick method

        });//end of onClick Listener


    }//end of onCreate Method

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
        taskTime.setText(i + ":" + i1);
    }//end of onTimeSet method

    //Create method to save task
    private void saveTask() {
        String name = taskName.getText().toString();
        String description = taskDescription.getText().toString();
        String time = taskTime.getText().toString();
        String tagInput = taskDay.getText().toString();
        String[] tagArray = tagInput.split("\\s*, \\s*");
        List<String> tags = Arrays.asList(tagArray);

        if (name.trim().isEmpty() || description.trim().isEmpty() || time.trim().isEmpty()){
            Toast.makeText(this, "Cannot leave field blank", Toast.LENGTH_SHORT).show();
            return;
        }//end of if statement

        //Add it to the firebase database
        CollectionReference tasksRef = FirebaseFirestore.getInstance()
                .collection("Tasks");

        Tasks tasks = new Tasks(name, description, time, tags);

        tasksRef.add(tasks);

        Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
        finish();

    }//end of saveTask method


}//end of CreateNewDailyTask class
