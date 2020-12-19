package de.iubh.meetingmoderatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.activities.Act_CreateMeeting;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        Button btnToCreateMeeting = findViewById(R.id.btn_toCreateMeeting);
        btnToCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Act_CreateMeeting.class);
                startActivity(i);
            }
        });



    }
}