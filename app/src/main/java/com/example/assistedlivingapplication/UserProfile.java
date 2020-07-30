package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class UserProfile extends AppCompatActivity {

    //Declare variables
    TextView name, email, phone, dob;

    ImageView profileImage;

    Button changePassword, updateProfile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    FirebaseUser user;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Assign variables
        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        phone = findViewById(R.id.tv_phone);
        dob = findViewById(R.id.tv_age);

        updateProfile = findViewById(R.id.btn_updateprofile);

        profileImage = findViewById(R.id.iv_profileimage);

        changePassword = findViewById(R.id.btn_changepassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //Create storage reference for firebase cloud storage
        storageReference = FirebaseStorage.getInstance().getReference();

        //Create Storage Reference for the profile image
        //This Storage Reference is used to create a directory for the 'users', so multiple users can upload their own profile image
        StorageReference profileRef = storageReference.child("users/" + userId + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }//end of onSuccess method

        });//end of onSuccess Listener

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

        //Setup onClickListener for update profile button
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open phone gallery using Intent
                //Create Intent to switch to Update Profile activity when button is clicked
                Intent i = new Intent(view.getContext(), UpdateProfile.class);

                //Use putExtra method to pass data from one activity to another through an Intent
                i.putExtra("Name", "Shaun McLaughlin");
                i.putExtra("Email", "mclaughlin-s5@hotmail.com");
                i.putExtra("Phone", "0778767887");
                i.putExtra("Age", "33");

                startActivity(i);
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                //startActivityForResult(openGalleryIntent, 1000);
            }//end of onClick method

        });//end of onClickListener method

    }//end of OnCreate method

    //Create method that uses Android library to get requestCode, resultCode and use CONTENT_URI as above as data parameter
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);

            }//nested if statement to check if resultCode is ok

        }//if statement to check if requestCode = the request code set by myself as above

    }//end of onActivityResult method

    //Create private method to upload image from gallery to firebase storage
    private void uploadImageToFirebase (Uri imageUri) {

        final StorageReference fileRef = storageReference.child("users/" + userId + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Picasso.get().load(uri).into(profileImage);
                   }//end of nested onSuccess method
               });

            }//end of onSuccess method

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
            }//end of onFailure method

        });//end of onSuccess Listener

    }//end of uploadImageToFirebase method

}//end of UserProfile class
