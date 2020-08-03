package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

public class DailyTasks extends AppCompatActivity implements View.OnClickListener {

    //Declare Variables
    private CardView monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tasks);

        //Assign variables
        monday = (CardView) findViewById(R.id.monday);
        tuesday = (CardView) findViewById(R.id.tuesday);
        wednesday = (CardView) findViewById(R.id.wednesday);
        thursday = (CardView) findViewById(R.id.thursday);
        friday = (CardView) findViewById(R.id.friday);
        saturday = (CardView) findViewById(R.id.saturday);
        sunday = (CardView) findViewById(R.id.sunday);

        //Add click listener to each card view
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);


    }//end of onCreate method

    //Create method to move to Create New Task screen when button is clicked
    public void createNewTask (View view){
        startActivity(new Intent(getApplicationContext(), CreateNewDailyTask.class));
        finish();
    }//End of logout method

    @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId()) {
            case R.id.monday : i = new Intent (this, MondayTasks.class);
            startActivity(i);
            break;
            case R.id.tuesday : i = new Intent (this, TuesdayTasks.class);
            startActivity(i);
            break;
            case R.id.wednesday : i = new Intent (this, WednesdayTasks.class);
            startActivity(i);
            break;
            case R.id.thursday : i = new Intent (this, ThursdayTasks.class);
            startActivity(i);
            break;
            case R.id.friday : i = new Intent (this, FridayTasks.class);
            startActivity(i);
            break;
            case R.id.saturday : i = new Intent (this, SaturdayTasks.class);
            startActivity(i);
            break;
            case R.id.sunday : i = new Intent (this, SundayTasks.class);
            startActivity(i);
            break;
            default: break;

        }//end of switch statement

    }//end of onClick method

}//end of DailyTasks class
