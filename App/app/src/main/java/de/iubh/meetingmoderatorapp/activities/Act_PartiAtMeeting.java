package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_PartiAtMeeting extends AppCompatActivity {
    Meeting m = new Meeting();
    AgendaPoint curAp = null;
    String surname;
    String lastname;
    String  meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    LocalDateTime lastServerChange;
    boolean runMeeting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.snackbar);

        // Meeting Daten auslesen und setzen
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
        }
        m = mh.getMeetingUser(meetingID, sbView);
        TextView meetingTitle = findViewById(R.id.txtMeetingTitle);
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        TextView partiGruss = findViewById(R.id.txtPartiGruss);
        partiGruss.setText("Hallo " + surname + " " + lastname +", willkommen im Meeting.");

        // Aufbau RecyclerView
        AgendaPointAdapter apAdapter;
        RecyclerView recyAP;
        RecyclerView.LayoutManager apLayoutManger;
        recyAP = findViewById(R.id.recyAPPartiAtMeeting);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());
        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);

        runMeeting = true;

        //1. SYNC
        // verbleibende Gesamtzeit anzeigen und runter zählen
        passedTime = mh.syncUserServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                passedTime++;
            }
            @Override
            public void onFinish() {            }
        }.start();

        lastLocalChange = LocalDateTime.now(ZoneId.systemDefault()   );
        curAp = mh.getAgendapointMod(meetingID, sbView);

        while(runMeeting) {
            runMeeting(lastLocalChange, curAp, mh, sbView);
            try {
                wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
        startActivity(i);

        // nächster Agendapunkt/ Teilnehmer
        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> {
            mh.nextUserAgendapoint(m, meetingID, sbView);
        });
    }

    public void runMeeting(LocalDateTime lastLocalChange, AgendaPoint curAP, MeetingHelper mh, View sbView) {
        if (curAP.getId() == 0) {
            runMeeting = false;
        } else {
            //2. STATE
            TextView aktuAP = findViewById(R.id.txtAgendapointTitle);
            aktuAP.setText(curAP.getTitle());
            TextView aktuSprecher = findViewById(R.id.txtAktuSprecher);
            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());

            if (curAp.getActualSpeaker().getUser().getFirstname().equals(surname) && curAP.getActualSpeaker().getUser().getLastname().equals(lastname)) {
                TextView partiSprechNote = findViewById(R.id.txtPartiSprechzeit);
                partiSprechNote.setVisibility(View.VISIBLE);
                long runTime = curAP.getAvailableTime();
                long spokenSec = 0;
                partiSprechNote.setText("Sprich dich aus für die nächsten " + (runTime - spokenSec) + " Sekunden");
                spokenSec++;
            }

            //3. CHANGE
            lastServerChange = mh.getLastChangeUser(meetingID, sbView);
            if (lastServerChange != lastLocalChange) {
                curAp = mh.getAgendapointUser(meetingID, sbView);
                lastLocalChange = lastServerChange;
            }

        }
    }
}
