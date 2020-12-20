package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;

public class Act_IDEingabe extends AppCompatActivity {
    static String GET_URL="http://10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        Button btnToCreateMeeting = findViewById(R.id.btn_createNewMeeting);
        btnToCreateMeeting.setOnClickListener(v -> {
            Intent i = new Intent(Act_IDEingabe.this, Act_CreateMeeting.class);
            startActivity(i);
        });


        Button btnJoinMeeting = findViewById(R.id.btnMeetingEinwahl);
        btnJoinMeeting.setOnClickListener(v -> {

            EditText meetingId = findViewById(R.id.txtMeetingID);
            String id = meetingId.getText().toString();
            if(id.matches("[0-9]{1,6}")) {
                HTTPClient client = new HTTPClient();
                String meeting = client.getMeeting(GET_URL + id);
                if(meeting == null) {
                    Snackbar.make(findViewById(R.id.IDView), "ID ist falsch.", Snackbar.LENGTH_LONG).show();
                }
                if(meeting.startsWith("Err")) {
                    Snackbar.make(findViewById(R.id.IDView), meeting, Snackbar.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(Act_IDEingabe.this, Act_Welcome.class);
                    i.putExtra("JSON", meeting);
                    startActivity(i);
                }
            } else {
                Snackbar.make(findViewById(R.id.IDView), "Bitte Meeting-ID eingeben.", Snackbar.LENGTH_LONG).show();

            }
        });
    }
}

