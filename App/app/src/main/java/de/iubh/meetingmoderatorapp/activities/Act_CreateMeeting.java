package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Agenda;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.Participant;
import de.iubh.meetingmoderatorapp.model.User;
import de.iubh.meetingmoderatorapp.model.enumerations.MeetingStatus;
import de.iubh.meetingmoderatorapp.model.enumerations.ParticipantType;

import static de.iubh.meetingmoderatorapp.R.id.btn_toAddParticipan;

public class Act_CreateMeeting extends AppCompatActivity {
    static String POST_URL ="http://192.168.178.110:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);


        Intent intent = getIntent();

        Participant p = new Participant(0,
                new User(0, intent.getStringExtra("surname"),
                intent.getStringExtra("lastname"),
                intent.getStringExtra("mail")),
                    ParticipantType.Participant,
                0);

        Button btnToAgenda = findViewById(R.id.btnToAgenda);
        btnToAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_CreateMeeting.this, Act_Agenda.class));
            }
        });

        Button btnToAddParticipant = findViewById(btn_toAddParticipan);
        btnToAddParticipant.setOnClickListener(v -> {
            Intent i = new Intent(Act_CreateMeeting.this, Act_addParticipant.class);
            startActivity(i);
        });



        Button btnCreateMeeting = findViewById(R.id.btn_createMeeting);
        btnCreateMeeting.setOnClickListener(v -> {

            Meeting m = new Meeting();
            String body;
            try {
                body = JSONHelper.MeetingToJSON(m);
                HTTPClient client = new HTTPClient();
                String s = client.postMeeting(POST_URL, body);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}