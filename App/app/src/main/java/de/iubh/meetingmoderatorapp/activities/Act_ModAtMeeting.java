package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;
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

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_ModAtMeeting  extends AppCompatActivity {
    Meeting m = new Meeting();
    HTTPClient client = new HTTPClient();
    String surname;
    String lastname;
    String meetingID;
    Handler changeReqHandler = new Handler();
    Runnable runnable;
    int delay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);


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


        recyAP = findViewById(R.id.recyPartiPreMeeting);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);

        meetingID = Long.toString(m.getId());
        meetingTitle.setText(m.getSettings().getMeetingTitle());
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");

        HTTPClient client = new HTTPClient();


        // Synchro
        long passedTime;
        try {
            client.getModSync(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        findViewById(R.id.snackbar),
                        "ServerSync fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                passedTime = JSONHelper.JSONToSync(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        //Change
        LocalDateTime lastLocalChange;
        try {
            client.getModChange(meetingID);
            while(!client.getResponseReceived()) {}
            if(client.getResponseCode() != 200) {
                Snackbar.make(
                        findViewById(R.id.snackbar),
                        "ServerChangeReq fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                lastLocalChange = JSONHelper.JSONToLastChange(client.getResponseBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
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
/*
        LocalDateTime lastChange = null;
        String serverChange;
        String localChange = "";
        serverChange = client.getModChange(Long.toString(m.getId()));
        try {
                localChange = JSONHelper.LastChangeToJSON(lastChange);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */


}
