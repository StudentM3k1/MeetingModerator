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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.snackbar);

        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAPMod);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtModVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView aktuSprecher = findViewById(R.id.txtModAktuSprecher);
        TextView modGruss = findViewById(R.id.txtModGruss);
        TextView modSprechNote = findViewById(R.id.txtModSprechzeit);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }

        m = mh.updateMeetingMod(meetingID, sbView);
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");

        // Aufbau RecyclerView
        AgendaPointAdapter apAdapter;
        RecyclerView recyAP;
        RecyclerView.LayoutManager apLayoutManger;
        recyAP = findViewById(R.id.recyPartiPreMeetingMod);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());



        // verbleibende Gesamtzeit anzeigen und runter zählen
        passedTime = mh.syncModServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                passedTime++;
            }

            @Override
            public void onFinish() {
                verbleibendeGesamtzeit.setText("Meeting Ende");
                Intent i = new Intent(Act_ModAtMeeting.this, Act_MeetingBeendet.class);
                startActivity(i);
            }
        }.start();


        //Change
        LocalDateTime lastLocalChange = null;
        LocalDateTime lastServerChange;
        curAp = mh.getAgendapointMod(meetingID, sbView);
        while (!m.getAgenda().getAgendaPoints().isEmpty()) {
            recyAP.setLayoutManager(apLayoutManger);
            recyAP.setAdapter(apAdapter);
            aktuAP.setText(curAp.getTitle());
            // Zeit des AP runterzählen
            new CountDownTimer(curAp.getAvailableTime(), 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    verbleibendeAPZeit.setText(String.valueOf(curAp.getAvailableTime() - passedTime));
                    passedTime++;
                }

                @Override
                public void onFinish() {
                    verbleibendeAPZeit.setText("Agendapunkt Ende");
                    // neuen Agendapunkt anfordern
                    mh.nextModAgendapoint(m, meetingID, sbView);
                }
            }.start();

            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());

            lastServerChange = mh.getLastChangeMod(meetingID, sbView);
            // Changes unterschiedlich, aber noch Agendapunktvorhanden
            if(lastServerChange != lastLocalChange) {
                //neuen Agendapunkt setzen
                curAp = mh.getAgendapointMod(meetingID, sbView);
                //Meeting updaten
                m = mh.updateMeetingMod(meetingID, sbView);
                // Change updaten
                lastLocalChange = lastServerChange;

            }
        }
        //Meeting beenden
        endMeeting();

        // nächster Teilnehmer
        Button btnStartSpeak = findViewById(R.id.btnModSprechenStarten);
        btnStartSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO nicht ganz richtig....

                mh.startMeetingMod(m, meetingID,sbView);

            }
        });

        // nächster Agendapunkt
        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> {
            mh.nextModAgendapoint(m, meetingID, sbView);
        });
        }

    public void endMeeting(){
        Intent i = new Intent(Act_ModAtMeeting.this, Act_MeetingBeendet.class);
        startActivity(i);
    }

}