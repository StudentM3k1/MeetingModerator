package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import okhttp3.Call;
import okhttp3.Response;

public class Act_MeetingBeendet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_meeting_beendet);
        AndroidThreeTen.init(this);


        Button btnBeendet = findViewById(R.id.btnMeetingbeendet);
        btnBeendet.setOnClickListener(v -> {
            Intent i = (new Intent(Act_MeetingBeendet.this, Act_IDEingabe.class));
            startActivity(i);
        });
    }
}