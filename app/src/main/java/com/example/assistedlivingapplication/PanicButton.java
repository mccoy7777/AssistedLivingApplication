package com.example.assistedlivingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PanicButton extends AppCompatActivity {

    //Declare variables
    Button btnHelp, btnBack;

    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_button);

        //Assign variables
        btnHelp = findViewById(R.id.btn_help);
        btnBack = findViewById(R.id.btn_back);

        //Assign mediaplayer for sound
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.siren_noise);

        //Setup onClickListener for Help Button
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();

            }//end of OnClick method
        });//end of onClickListener method

        //Setup onClickListener for back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }//end of onClickMethod
        });//End of onClickListener

    }//end of onCreate method

}//end of PanicButton class
