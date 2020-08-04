package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare variables
    private CardView myProfile, dailyTasks, prescriptionService, panicButton, dailyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variables
        myProfile = (CardView) findViewById(R.id.my_profile);
        dailyTasks = (CardView) findViewById(R.id.daily_tasks);
        prescriptionService = (CardView) findViewById(R.id.prescription_service);
        panicButton = (CardView) findViewById(R.id.panic_button);
        dailyNotification = (CardView) findViewById(R.id.daily_notification);

        //Add Click Listener to each CardView
        myProfile.setOnClickListener(this);
        dailyTasks.setOnClickListener(this);
        prescriptionService.setOnClickListener(this);
        panicButton.setOnClickListener(this);
        dailyNotification.setOnClickListener(this);


    }//End of OnCreate Method

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.my_profile : i = new Intent(this, UserProfile.class);
            startActivity(i);
            break;
            case R.id.daily_tasks : i = new Intent (this, DailyTasks.class);
            startActivity(i);
            break;
            case R.id.prescription_service : i = new Intent(this, PrescriptionService.class);
            startActivity(i);
            break;
            case R.id.panic_button : i = new Intent (this, PanicButton.class);
            startActivity(i);
            break;
            case R.id.daily_notification : i = new Intent (this, DailyNotification.class);
            startActivity(i);
            break;
            default: break;
        }//end of switch statement

    }//end of onClick method


}//End of Main class
