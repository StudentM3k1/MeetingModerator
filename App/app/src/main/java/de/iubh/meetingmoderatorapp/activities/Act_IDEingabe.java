package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.MainActivity;
import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_IDEingabe extends AppCompatActivity {
    static String GET_URL="http://192.168.178.110:8080/MeetingModeratorServer/Meeting/";
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
        btnJoinMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = findViewById(R.id.txtMeetingID).toString();
                HTTPClient client = new HTTPClient();
                String meeting = client.getMeeting(GET_URL + id);
                Intent i = new Intent(Act_IDEingabe.this, Act_Welcome.class);
                i.putExtra("JSON", meeting);
                startActivity(i);
            }
        });
    }
}
