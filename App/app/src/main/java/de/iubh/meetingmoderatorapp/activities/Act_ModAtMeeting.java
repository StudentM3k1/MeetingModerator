package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;
import org.threeten.bp.LocalDateTime;

import java.util.Timer;
import java.util.TimerTask;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
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

        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAPMod);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtModVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView aktuSprecher = findViewById(R.id.txtModAktuSprecher);
        TextView modGruss = findViewById(R.id.txtModGruss);
        TextView modSprechNote = findViewById(R.id.txtModSprechzeit);


        RecyclerView recyAP;
        AgendaPointAdapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String json = extras.getString("JSON");
            try {
                m = JSONHelper.JSONToMeeting(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Aufbau RecyclerView
        recyAP = findViewById(R.id.recyPartiPreMeeting);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);

        meetingID = Long.toString(m.getId());
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");

        View sbView = findViewById(R.id.snackbar);


        // verbleibende Gesamtzeit
        passedTime = mh.syncServer(meetingID, sbView);
        new CountDownTimer(m.getSettings().getDuration(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                passedTime++;

            }

            @Override
            public void onFinish() {

                verbleibendeGesamtzeit.setText("Meeting Ende");
                // Aufrufen der
            }
        }.start();


        //Change
        LocalDateTime lastLocalChange = null;
        LocalDateTime lastServerChange = mh.getLastChange(meetingID, sbView);
        while (lastServerChange == lastLocalChange) {

             // aktueller AP
             curAp = mh.getAgendapoint(meetingID, sbView);
            aktuAP.setText(curAp.getTitle());
            new CountDownTimer(curAp.getAvailableTime(), 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    verbleibendeAPZeit.setText(String.valueOf(curAp.getAvailableTime() - passedTime));
                    passedTime++;
                }

                @Override
                public void onFinish() {
                    verbleibendeAPZeit.setText("Agendapunkt Ende");
                }
            }.start();

            aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());
            //TODO verbleibende Sprechzeit anzeigen und runter zählen


            lastServerChange = mh.getLastChange(meetingID, sbView);

            if(lastServerChange != lastLocalChange) {

                curAp = mh.getAgendapoint(meetingID, sbView);
                m = mh.updateMeeting(meetingID, sbView);
                lastLocalChange = lastServerChange;


            }
        }



        Button btnStartSpeak = findViewById(R.id.btnModSprechenStarten);
        btnStartSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // push APState to server

            }
        });


        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> {
            HTTPClient client = new HTTPClient();
            String j = null;
            try {
                j = JSONHelper.MeetingToJSON(m);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            client.postNextModerator(j, meetingID);
        });
        }



}