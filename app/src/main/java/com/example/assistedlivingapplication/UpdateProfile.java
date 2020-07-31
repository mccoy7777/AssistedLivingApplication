package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    //Declare variables
    EditText profileName, profileEmail, profilePhone, profileAge;
    Button save;

    ImageView profileImageView;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //Assign variables
        profileName = findViewById(R.id.et_name);
        profileEmail = findViewById(R.id.et_email);
        profilePhone = findViewById(R.id.et_phone);
        profileAge = findViewById(R.id.et_age);
        profileImageView = findViewById(R.id.iv_saveprofileimage);

        save = findViewById(R.id.btn_save);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        //Create Storage Reference for the profile image
        //This Storage Reference is used to create a directory for the 'users', so multiple users can upload their own profile image
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }//end of onSuccess method

        });//end of onSuccess Listener

        //Set onClick Listener for save button , to update the users profile data
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty() || profileAge.getText().toString().isEmpty()){
                    Toast.makeText(UpdateProfile.this, "Some of the fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }//if statement to make sure user updates all details

                final String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        DocumentReference docRef = fStore.collection("users").document(user.getUid());

                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Name", profileName.getText().toString());
                        edited.put("Email", email);
                        edited.put("Phone", profilePhone.getText().toString());
                        edited.put("Dob", profileAge.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfile.this, "Profile details have been updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                                finish();
                            }//end of onSuccess method

                        });//end of onSuccess Listener

                        Toast.makeText(UpdateProfile.this, "Email has been updated", Toast.LENGTH_SHORT).show();
                    }//end of onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }//end of onFailure method

                });//end of onSuccessListener

            }//end of onClick method

        });//end of onClickListener

        //Set onClick Listener when User clicks profile image
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(openGalleryIntent, 1000);
            }//end of onClick method

        });//end of onClickListener

        //Create Intent that uses the Intent from the previous activity (User Profile) & retrieve data values
        Intent data = getIntent();
        String name = data.getStringExtra("Name");
        String email = data.getStringExtra("Email");
        String phone = data.getStringExtra("Phone");
        String age = data.getStringExtra("Age");

        //Pass data to EditText section in the activity using setText method
        profileName.setText(name);
        profileEmail.setText(email);
        profilePhone.setText(phone);
        profileAge.setText(age);

        //Use Log TAG to check on LogCat to make sure data is passed to this activity
        Log.d(TAG, "onCreate: " + name + email + phone + age);

    }//end of onCreate method

    //Create method that uses Android library to get requestCode, resultCode and use CONTENT_URI as above as data parameter
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                uploadImageToFirebase(imageUri);

            }//nested if statement to check if resultCode is ok

        }//if statement to check if requestCode = the request code set by myself as above

    }//end of onActivityResult method

    //Create private method to upload image from gallery to firebase storage
    private void uploadImageToFirebase (Uri imageUri) {

        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }//end of nested onSuccess method
                });

            }//end of onSuccess method

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }//end of onFailure method

        });//end of onSuccess Listener

    }//end of uploadImageToFirebase method

}//end of UpdateProfile class
