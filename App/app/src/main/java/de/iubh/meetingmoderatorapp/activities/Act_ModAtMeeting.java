package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
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

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;


import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;
import okhttp3.Call;
import okhttp3.Response;

public class Act_ModAtMeeting  extends AppCompatActivity implements CallbackHandler {
    Meeting m = null;
    AgendaPoint curAp = null;
    String meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    boolean runMeeting = false;
    View sbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);
        sbView = findViewById(R.id.snackbar);

        // Meeting Daten auslesen und setzen
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }
        MeetingHelper.getMeetingMod(meetingID, this);
    }

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "GetMeetingMod":
                Snackbar.make(
                        sbView,
                        "Meeting Update konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetChangeMod":
                Snackbar.make(
                        sbView,
                        "ServerChangeReq fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetStateMod":
                Snackbar.make(
                        sbView,
                        "neuer Agenapunkt konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetSyncMod":
                Snackbar.make(
                        sbView,
                        "ServerSync fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "NextMod":
                Snackbar.make(
                        sbView,
                        "nächster Agendapunkt nicht möglich",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }


    public void onResponseCallback(Call call, Response response) {
        if (response.code() == 200) {
            switch (call.request().tag().toString()) {
                case "GetMeetingMod":
                    startMeeting(call, response);
                    break;
                case "GetChangeMod":
                    changeMeeting(call, response);
                    break;
                case "GetStateMod":
                    stateMeeting(call, response);
                    break;
                case "GetSyncMod":
                    syncMeeting(call, response);
                    break;
                case "NextMod":
                    nextMeeting(call, response);
                    break;
                default:
                    break;
            }
        } else {
            onFailureCallback(call, new IOException());
        }
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run() {
            TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
            verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
            passedTime++;
            timerHandler.postDelayed(this, 1000);
        }
    };



    private void syncMeeting(Call call, Response response) {
        try {
            passedTime = JSONHelper.JSONToSync(response.body().string());
        }
        catch (Exception e)
        {
            passedTime = 0;
            onFailureCallback( call, new IOException());
        }
        timerHandler.post(timerRunnable);

        /*new CountDownTimer(m.getSettings().getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
                        verbleibendeGesamtzeit.setText(String.valueOf(m.getSettings().getDuration() - passedTime));
                        passedTime++;
                }

            @Override
            public void onFinish() {
            }
        }.start();*/
    }

    private void stateMeeting(Call call, Response response) {
        try {
            curAp = JSONHelper.JSONToState(response.body().string());
            if (curAp.getId() == 0) {
                runMeeting = false;
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                TextView aktuAP = findViewById(R.id.txtAktuAPMod);
                aktuAP.setText(curAp.getTitle());
                TextView aktuSprecher = findViewById(R.id.txtModPreOrt);
                aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());
                    }
                }); }
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
    }

    private void changeMeeting(Call call, Response response) {
        LocalDateTime lastServerChange = null;
        try {
            lastServerChange = JSONHelper.JSONToLastChange(response.body().string());
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }

        if (lastServerChange != lastLocalChange) {
            MeetingHelper.getAgendapointMod(meetingID, this);
            lastLocalChange = lastServerChange;
        }
    }

    private void nextMeeting(Call call, Response response) {
        MeetingHelper.syncMod(meetingID, this);
    }

    private void startMeeting(Call call, Response response) {
        try {
            m = JSONHelper.JSONToMeeting(response.body().string());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
            TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
            meetingTitle.setText(m.getSettings().getMeetingTitle());
            TextView modGruss = findViewById(R.id.txtModGruss);
            modGruss.setText("Hallo Moderator, willkommen im Meeting.");
                }
            });


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

            lastLocalChange = LocalDateTime.now(ZoneId.systemDefault());

            //1. SYNC
            // verbleibende Gesamtzeit anzeigen und runter zählen
            MeetingHelper.syncMod(meetingID, this);
            MeetingHelper.getAgendapointMod(meetingID, this);

            while (runMeeting) {
                MeetingHelper.getLastChangeMod(meetingID, this);
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
                MeetingHelper.nextAgendapointMod(meetingID, this);
            });
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
    }
}