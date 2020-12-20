package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.Participant;

public class Act_Welcome extends AppCompatActivity {
    static String GET_URL="http://10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    private RecyclerView recyTLN;
    private RecyclerView.Adapter tlnAdapter;
    private RecyclerView.LayoutManager tlnLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        Bundle extras = getIntent().getExtras();
        String EinwahlJson = extras.getString("JSON");

        Meeting m = JSONHelper.JSONToMeeting(EinwahlJson);

        recyTLN = findViewById(R.id.recyTeilnehmerliste);
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);

    }
}

