package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_addParticipant extends AppCompatActivity {
    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_participant);
        AndroidThreeTen.init(this);


        EditText surname = findViewById(R.id.txtPartiFirstName);
        EditText lastname = findViewById(R.id.txtPartiLastname);
        EditText mail = findViewById(R.id.txtPartiMail);
        Button btnBack = findViewById(R.id.btnBackHome);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_addParticipant.this, Act_CreateMeeting.class));
            }
        });

        Button btnAddParti = findViewById(R.id.btnAddParti);
        btnAddParti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Act_addParticipant.this, Act_CreateMeeting.class);

                try {
                    i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(i);

            }
        });

    }
}