package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class UserProfile extends AppCompatActivity {

    //Declare variables
    TextView name, email, phone, dob;

    Button changePassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Assign variables
        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        phone = findViewById(R.id.tv_phone);
        dob = findViewById(R.id.tv_age);

        changePassword = findViewById(R.id.btn_changepassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone"));
                email.setText(documentSnapshot.getString("Email"));
                dob.setText(documentSnapshot.getString("Dob"));
            }//end of onEvent method

        });//end of EventListener

        //Setup onClickListener to change the password when button is clicked
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create new edit text view and use alert dialogue class to prompt user to reset password
                final EditText changePassword = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Change Password");
                passwordResetDialog.setMessage("Enter New Password (At Least 6 Characters Long)");
                passwordResetDialog.setView(changePassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //create variable for new password and use firebase to update it
                        String newPassword = changePassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UserProfile.this, "Password has been changed successfully", Toast.LENGTH_SHORT).show();
                            }//end of onSuccess method

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserProfile.this, "Password Change Failed", Toast.LENGTH_SHORT).show();
                            }//end of onFailure method

                        });//end of onSuccess Listener method

                    }//end of onClick method

                });//end of onClickListener

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the dialogue

                    }//end of onClick method

                });//end of onClickListener

                passwordResetDialog.create().show();

            }//end of onClick method

        });//end of onClickListener method

    }//end of OnCreate method

}//end of UserProfile class
