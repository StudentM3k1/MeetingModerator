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


import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.Callback;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;
import okhttp3.Call;
import okhttp3.Response;

public class Act_ModAtMeeting  extends AppCompatActivity implements Callback {
    Meeting m = null;
    AgendaPoint curAp = null;
    String meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    LocalDateTime lastServerChange;
    boolean runMeeting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.snackbar);

        // Meeting Daten auslesen und setzen
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }
        m = mh.getMeetingMod(meetingID, sbView);
        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        TextView modGruss = findViewById(R.id.txtModGruss);
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");


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
        passedTime = mh.syncModServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                passedTime++;
            }
            @Override
            public void onFinish() {}
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
        Intent i = new Intent(Act_ModAtMeeting.this, Act_MeetingBeendet.class);
        startActivity(i);

        // nächster Agendapunkt/ Teilnehmer
        Button btnEndSpeak = findViewById(R.id.btnModNext);
        btnEndSpeak.setOnClickListener(v -> {
            mh.nextModAgendapoint(m, meetingID, sbView);
        });
        }

    public void runMeeting(LocalDateTime lastLocalChange, AgendaPoint curAP, MeetingHelper mh, View sbView)  {
        if (curAP.getId() == 0) {
            runMeeting = false;
        } else {
            //2. STATE
            TextView aktuAP = findViewById(R.id.txtAktuAPMod);
            aktuAP.setText(curAP.getTitle());
            TextView aktuSprecher = findViewById(R.id.txtModPreOrt);
            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());

            //3. CHANGE
            lastServerChange = mh.getLastChangeMod(meetingID, sbView);
            if (lastServerChange != lastLocalChange) {
                curAp = mh.getAgendapointMod(meetingID, sbView);
                lastLocalChange = lastServerChange;
            }

        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }
}