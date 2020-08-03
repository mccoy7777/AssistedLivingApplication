package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CreateNewDailyTask extends AppCompatActivity {

    //Declare variables
     Spinner spnDaysOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_daily_task);

        //Assign variables
        spnDaysOfWeek = findViewById(R.id.spn_DaysOfTheWeek);

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



    }//end of onCreate Method

}//end of CreateNewDailyTask class
