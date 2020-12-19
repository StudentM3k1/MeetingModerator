package de.iubh.meetingmoderatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        HTTPClient client = new HTTPClient();
        Meeting meeting = new Meeting();
        meeting = client.postMeeting("http://localhost/MeetintgModerator/Meeting/","Hier: kommt JSON");




    }
}