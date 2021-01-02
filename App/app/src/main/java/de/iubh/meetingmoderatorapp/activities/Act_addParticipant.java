package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;


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

public class Act_addParticipant extends AppCompatActivity implements CallbackHandler {
    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_participant);
        AndroidThreeTen.init(this);


        EditText surname = findViewById(R.id.txtPartiFirstName);
        EditText lastname = findViewById(R.id.txtPartiLastname);
        EditText mail = findViewById(R.id.txtPartiMail);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String json = extras.getString("JSON");
            try {
                m = JSONHelper.JSONToMeetinginApp(json);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        Button btnBack = findViewById(R.id.btnBackHome);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Act_addParticipant.this, Act_CreateMeeting.class);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSONinApp(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button btnAddParti = findViewById(R.id.btnAddParti);
        btnAddParti.setOnClickListener(v -> {
            Intent i = new Intent(Act_addParticipant.this, Act_CreateMeeting.class);
            Participant p = new Participant(
                    m.getParticipants().size()+1,
                    new User(0,surname.getText().toString(),lastname.getText().toString(),mail.getText().toString()),
                    ParticipantType.Participant
            );
            m.getParticipants().add(p);

            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSONinApp(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });
    }    public void onFailureCallback(Call call, IOException e)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }

    }

    public void onResponseCallback(Call call, Response response)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }
    }
}