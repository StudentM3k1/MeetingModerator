package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
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

public class Act_PartiAtMeeting extends AppCompatActivity implements CallbackHandler {
    Meeting m = new Meeting();
    AgendaPoint curAp = null;
    String surname;
    String lastname;
    String meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    LocalDateTime lastServerChange;
    boolean runMeeting = false;
    View sbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);
        sbView = findViewById(R.id.txtPartiMeetSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
        }
        MeetingHelper.getMeetingUser(meetingID, this);

        // nächster Agendapunkt/ Teilnehmer
        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(v -> {
            MeetingHelper.nextAgendapointUser(meetingID, this);
        });
    }

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "GetMeetingUser":
                Snackbar.make(
                        sbView,
                        "Meeting Update konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetChangeUser":
                Snackbar.make(
                        sbView,
                        "ServerChangeReq fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetStateUser":
                Snackbar.make(
                        sbView,
                        "neuer Agenapunkt konte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetSyncUser":
                Snackbar.make(
                        sbView,
                        "ServerSync fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "NextUser":
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
                case "GetMeetingUser":
                    joinMeeting(call, response);
                    break;
                case "GetChangeUser":
                    changeMeeting(call, response);
                    break;
                case "GetStateUser":
                    stateMeeting(call, response);
                    break;
                case "GetSyncUser":
                    syncMeeting(call, response);
                    break;
                case "NextUser":
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
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
            verbleibendeGesamtzeit.setText(String.valueOf(passedTime));
            passedTime++;
            timerHandler.postDelayed(this, 1000);
        }
    };


    Handler agendaTimerHandler = new Handler();
    Runnable agendaTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (curAp != null) {
                TextView tv_sprechzeit = findViewById(R.id.txtVerbleibendeAPZeit);
                String string_sprecher = "Aktueller Sprecher: " + curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname() +
                        "\nVerbleibende Sprechzeit: " + (curAp.getAvailableTime() - curAp.getRunningTime());
                tv_sprechzeit.setText(string_sprecher);
                curAp.setRunningTime(curAp.getRunningTime() + 1);
            }
            agendaTimerHandler.postDelayed(this, 1000);
        }
    };

    private void syncMeeting(Call call, Response response) {
        try {
            passedTime = JSONHelper.JSONToSync(response.body().string());
        } catch (Exception e) {
            passedTime = 0;
            onFailureCallback(call, new IOException());
        }
        timerHandler.post(timerRunnable);
        agendaTimerHandler.post(agendaTimerRunnable);
    }

    private void stateMeeting(Call call, Response response) {
        try {
            curAp = JSONHelper.JSONToState(response.body().string());
            if (curAp.getId() == 0) {
                runMeeting = false;
            } else {
                TextView aktuAP = findViewById(R.id.aktuAPParti);
                aktuAP.setText(curAp.getTitle());
                TextView aktuSprecher = findViewById(R.id.txtAktuSprecher);
                aktuSprecher.setText(curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname());
            }
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
            MeetingHelper.getAgendapointUser(meetingID, this);
            lastLocalChange = lastServerChange;
        }
    }

    private void nextMeeting(Call call, Response response) {
        MeetingHelper.getLastChangeUser(meetingID, this);
    }

    private void joinMeeting(Call call, Response response) {
        try {
            m = JSONHelper.JSONToMeeting(response.body().string());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView meetingTitle = findViewById(R.id.txtMeetingTitle);
                    meetingTitle.setText(m.getSettings().getMeetingTitle());
                    TextView partiGruss = findViewById(R.id.txtPartiGruss);
                    partiGruss.setText("Hallo " + surname + " " + lastname + ", willkommen im Meeting.");

                    // Aufbau RecyclerView
                    AgendaPointAdapter apAdapter;
                    RecyclerView recyAP;
                    RecyclerView.LayoutManager apLayoutManger;
                    recyAP = findViewById(R.id.recyAPUserAtMeeting);
                    recyAP.setHasFixedSize(true);
                    apLayoutManger = new LinearLayoutManager(Act_PartiAtMeeting.this);
                    apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());
                    recyAP.setLayoutManager(apLayoutManger);
                    recyAP.setAdapter(apAdapter);
                }
            });
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

            Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
            startActivity(i);

        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
    }
}
