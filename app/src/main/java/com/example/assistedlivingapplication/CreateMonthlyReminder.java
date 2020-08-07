package com.example.assistedlivingapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateMonthlyReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    //Declare variables
    NotificationManagerCompat notificationManager;
    EditText notificationTitle, message;
    TextView setTime;
    Button chooseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_monthly_reminder);

        //Assign variables
        notificationManager = NotificationManagerCompat.from(this);

        notificationTitle = findViewById(R.id.et_NotificationTitle);
        message = findViewById(R.id.et_message);
        setTime = findViewById(R.id.tv_SetTime);

        chooseTime = findViewById(R.id.btn_ChooseTime);

        //Set onClickListener for choose time button
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new com.example.assistedlivingapplication.TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }//end of OnClick method

        });//end of onClickListener method

    }//end of onCreate method

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i1);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }//end of onTimeSet method

    private void updateTimeText(Calendar c){
        String timeText = "Notification time set for : ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        setTime.setText(timeText);

    }//end of updateTimeText method

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlertReciever.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,1, i, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);

    }//end of startAlarm method

}//end of CreateMonthlyReminder class
