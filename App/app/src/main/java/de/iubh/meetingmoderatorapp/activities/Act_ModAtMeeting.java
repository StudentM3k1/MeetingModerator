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

public class Act_ModAtMeeting  extends AppCompatActivity {
    Meeting m = null;
    AgendaPoint curAp = null;
    String meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    LocalDateTime lastServerChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.snackbar);

        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
        TextView modGruss = findViewById(R.id.txtModGruss);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }

        m = mh.getMeetingMod(meetingID, sbView);

        meetingTitle.setText(m.getSettings().getMeetingTitle());
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");

        // Aufbau RecyclerView
        AgendaPointAdapter apAdapter;
        RecyclerView recyAP;
        RecyclerView.LayoutManager apLayoutManger;
        recyAP = findViewById(R.id.recyAPModAtMeeting);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());
        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);



        //1. SYNC
        // verbleibende Gesamtzeit anzeigen und runter zählen
        passedTime = mh.syncModServer(meetingID, sbView);
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
        if (curAp != null) {
            runMeeting(lastLocalChange, curAp, mh, sbView);
        }

        // nächster Agendapunkt/ Teilnehmer
        Button btnEndSpeak = findViewById(R.id.btnModNext);
        btnEndSpeak.setOnClickListener(v -> {
            mh.nextModAgendapoint(m, meetingID, sbView);
        });
        }

    public void runMeeting(LocalDateTime lastLocalChange, AgendaPoint curAP, MeetingHelper mh, View sbView)  {
        if (curAP.getId() == 0) {
            Intent i = new Intent(Act_ModAtMeeting.this, Act_MeetingBeendet.class);
            startActivity(i);
        } else {
            //1. SYNC

            //2. STATE
            curAP = mh.getAgendapointMod(meetingID, sbView);
                // update Agendapoint
                TextView aktuAP = findViewById(R.id.txtAktuAPMod);
                aktuAP.setText(curAP.getTitle());
                // update aktueller Sprecher
                TextView aktuSprecher = findViewById(R.id.txtModPreOrt);
                aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());
            //3. CHANGE

            new CountDownTimer(curAp.getAvailableTime(), 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    lastServerChange = mh.getLastChangeMod(meetingID, sbView);
                }
                @Override
                public void onFinish() {
                }
            }.start();
            if (lastServerChange != lastLocalChange) {
                curAp = mh.getAgendapointMod(meetingID, sbView);
                lastLocalChange = lastServerChange;
                runMeeting(lastLocalChange, curAP, mh, sbView);
            }

        }
    }
}