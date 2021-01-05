package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.Participant;
import de.iubh.meetingmoderatorapp.model.User;
import de.iubh.meetingmoderatorapp.model.enumerations.ParticipantType;
import okhttp3.Call;
import okhttp3.Response;

public class Act_ModRegistration extends AppCompatActivity{
    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_registration);
        AndroidThreeTen.init(this);

        EditText surname = findViewById(R.id.txtModFirstName);
        EditText lastname = findViewById(R.id.txtModLastname);
        EditText mail = findViewById(R.id.txtModMail);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String json = extras.getString("JSON");
            try {
                m = JSONHelper.JSONToMeeting(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (m.getSettings().getMeetingTitle().equals("")) {
            m.getSettings().setMeetingTitle("myMeeting");
        }
        if (m.getSettings().getStartTime().equals("")) {
            m.getSettings().setStartTime(LocalDateTime.parse("2021-01-31T13:00"));
        }
        if (m.getSettings().getDuration() == 0) {
            m.getSettings().setDuration(60);
        }
        if (m.getOrt() == null) {
            m.setOrt("MeetingSpace");
        }

        Button btnBack = findViewById(R.id.btnModRegistBackHome);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(de.iubh.meetingmoderatorapp.activities.Act_ModRegistration.this, Act_CreateMeeting.class);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button btnAddMod = findViewById(R.id.btnAddMod);
        btnAddMod.setOnClickListener(v -> {
            Intent i = new Intent(Act_ModRegistration.this, Act_CreateMeeting.class);
            Participant p = new Participant(
                    m.getParticipants().size() + 1,
                    new User(0, surname.getText().toString(), lastname.getText().toString(), mail.getText().toString()),
                    ParticipantType.Moderator
            );
            m.getParticipants().add(p);

            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });
    }



}

