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

public class Act_PartiAtMeeting extends AppCompatActivity {
    Meeting m = new Meeting();
    AgendaPoint curAp = null;
    String surname;
    String lastname;
    String  meetingID;
    long passedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        TextView meetingTitle = findViewById(R.id.txtMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAP);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView aktuSprecher = findViewById(R.id.txtAktuSprecher);
        TextView partiGruss = findViewById(R.id.txtPartiGruss);
        TextView partiSprechNote = findViewById(R.id.txtPartiSprechzeit);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.txtPartiMeetSnack);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
            String json = extras.getString("JSON");
            meetingID = extras.getString("meetingID");
        }

        m = mh.updateMeetingUser(meetingID, sbView);

        meetingTitle.setText(m.getSettings().getMeetingTitle());
        partiGruss.setText("Hallo " + surname + " " + lastname + "Willkommen im Meeting.");

        //Aufbau RecyView Agendapoints
        RecyclerView recyAP = findViewById(R.id.recyPartiPreMeeting);
        AgendaPointAdapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());


        // verbleibende Gesamtzeit anzeigen und runter zählen
        passedTime = mh.syncUserServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration()/60000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration()/60000 - passedTime));
                passedTime++;
            }

            @Override
            public void onFinish() {
                verbleibendeGesamtzeit.setText("Meeting Ende");
                Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
                startActivity(i);
            }
        }.start();

        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> mh.nextUserAgendapoint(meetingID, sbView));

        //Change
        LocalDateTime lastLocalChange = null;
        LocalDateTime lastServerChange;
        curAp = mh.getAgendapointUser(meetingID, sbView);
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
                    mh.nextUserAgendapoint(meetingID, sbView);
                }
            }.start();

            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());
            if(curAp.getActualSpeaker().getUser().getFirstname().equals(surname) && curAp.getActualSpeaker().getUser().getLastname().equals(lastname)) {
                btnEndSpeak.setVisibility(View.VISIBLE);
            }
            lastServerChange = mh.getLastChangeUser(meetingID, sbView);
            // Changes unterschiedlich, aber noch Agendapunktvorhanden
            if(lastServerChange != lastLocalChange) {
                //neuen Agendapunkt setzen
                curAp = mh.getAgendapointUser(meetingID, sbView);
                //Meeting updaten
                m = mh.updateMeetingUser(meetingID, sbView);
                // Change updaten
                lastLocalChange = lastServerChange;

            }
        }
        //Meeting beenden
        endMeeting();




    }

    public void endMeeting(){
        Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
        startActivity(i);
    }
}
