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
    //y020evGyb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        TextView meetingTitle = findViewById(R.id.txtMeetingTitle);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtVerbleibendeAPZeit);
        TextView aktuSprecher = findViewById(R.id.txtAktuSprecher);
        TextView partiGruss = findViewById(R.id.txtPartiGruss);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.snackbar);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
        }

        m = mh.getMeetingUser(meetingID, sbView);

        meetingTitle.setText(m.getSettings().getMeetingTitle());
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



        //1. SYNC
        // verbleibende Gesamtzeit anzeigen und runter zählen
        passedTime = mh.syncUserServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                passedTime++;
            }
            @Override
            public void onFinish() {            }
        }.start();

        lastLocalChange = LocalDateTime.now(ZoneId.systemDefault()   );

        curAp = mh.getAgendapointMod(meetingID, sbView);
        runMeeting(lastLocalChange, curAp, mh, sbView);



        // nächster Agendapunkt/ Teilnehmer
        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> {
            mh.nextUserAgendapoint(m, meetingID, sbView);
        });
    }

    public void runMeeting(LocalDateTime lastLocalChange, AgendaPoint curAP, MeetingHelper mh, View sbView) {
        if (curAP.getId() == 0) {
            Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
            startActivity(i);
        } else {
            //1. SYNC

            //2. STATE
            curAP = mh.getAgendapointUser(meetingID, sbView);
            // update Agendapoint
            TextView aktuAP = findViewById(R.id.txtAgendapointTitle);
            aktuAP.setText(curAP.getTitle());
            // update aktueller Sprecher
            TextView aktuSprecher = findViewById(R.id.txtAktuSprecher);
            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());

            if (curAp.getActualSpeaker().getUser().getFirstname().equals(surname) && curAP.getActualSpeaker().getUser().getLastname().equals(lastname)) {
                TextView partiSprechNote = findViewById(R.id.txtPartiSprechzeit);
                partiSprechNote.setVisibility(View.VISIBLE);
                long runTime = curAP.getAvailableTime();
                new CountDownTimer(curAP.getRunningTime(), 1000) {
                    long spokenSec = 0;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        partiSprechNote.setText("Sprich dich aus für die nächsten " + (runTime - spokenSec) + " Sekunden");
                        spokenSec++;
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();
            }

            //3. CHANGE
            new CountDownTimer(curAp.getAvailableTime(), 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    lastServerChange = mh.getLastChangeUser(meetingID, sbView);
                }
                @Override
                public void onFinish() {
                }
            }.start();
            if (lastServerChange != lastLocalChange) {
                curAp = mh.getAgendapointUser(meetingID, sbView);
                lastLocalChange = lastServerChange;
                runMeeting(lastLocalChange, curAP, mh, sbView);
            }

        }
    }
}
