package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;


import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_ModPreMeeting extends AppCompatActivity {
    private String meetingID;
    private Meeting m = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_pre_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.modPreSnack);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingID = extras.getString("meetingID");
        }

        m = mh.getMeetingMod(meetingID, sbView);

        TextView meetingTitle = findViewById(R.id.txtPreMeetingTitle);
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        TextView gesamtZeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
        //gesamtZeit.setText(Long.toString(m.getSettings().getDuration()));
        TextView txtModAktuSprecher = findViewById(R.id.txtModPreOrt);
        txtModAktuSprecher.setText(m.getOrt());


        // Aufbau RecyView AgendaPoint
        RecyclerView recyAP = findViewById(R.id.recyApPreMeetingMod);
        AgendaPointAdapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());
        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);


        // Aufbau RecyView Participant
        RecyclerView recyTLN = findViewById(R.id.recyAPPartiAtMeeting);
        TeilnehmerAdapter tlnAdapter;
        RecyclerView.LayoutManager tlnLayoutManger;
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);

        // Button geändertes Meeting speichern
        Button btnStartMeeting = findViewById(R.id.btnStartMeetingMod);
        btnStartMeeting.setOnClickListener(v -> {
            // check meeting start response code findet in meetingHelperMethode statt
            mh.startMeetingMod(m, meetingID, sbView);

            Intent i = (new Intent(Act_ModPreMeeting.this, Act_ModAtMeeting.class));
            i.putExtra("meetingID", meetingID);
            startActivity(i);
        });
        }
    }

