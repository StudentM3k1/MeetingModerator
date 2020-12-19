package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.Participant;

public class Act_Welcome extends AppCompatActivity {
    static String GET_URL="http://192.168.178.110:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        Bundle extras = getIntent().getExtras();
        String EinwahlJson = extras.getString("JSON");

        Meeting m = JSONHelper.JSONToMeeting(EinwahlJson);

        RecyclerView recyTLN = (RecyclerView) findViewById(R.id.recyTeilnehmerliste);

        for(Participant p : m.getParticipants()){



        }

    }
}
