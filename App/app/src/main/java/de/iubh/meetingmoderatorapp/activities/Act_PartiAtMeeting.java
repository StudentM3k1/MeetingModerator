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
    String firstname;
    String lastname;
    String meetingID;
    long passedTime;
    LocalDateTime lastLocalChange;
    LocalDateTime lastServerChange;
    View sbView;
    boolean runningMeeting = false;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);
        sbView = findViewById(R.id.txtPartiMeetSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
        }

        btnNext = findViewById(R.id.btnPartiSprechenBeenden);
        btnNext.setOnClickListener(v -> {
            MeetingHelper.nextAgendapointUser(meetingID, this);
        });

        MeetingHelper.getMeetingUser(meetingID, this);

    }

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "GetMeetingUser":
                Snackbar.make(
                        sbView,
                        "Meeting Update konnte nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetChangeUser":
                Snackbar.make(
                        sbView,
                        "ServerChangeRequest fehlgeschlagen",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "GetStateUser":
                Snackbar.make(
                        sbView,
                        "Neuer Agenapunkt konte nicht geladen werden",
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
                        "Nächster Agendapunkt nicht möglich",
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
                case "GetStateUser":
                    stateMeeting(call, response);
                    break;
                case "GetSyncUser":
                    syncMeeting(call, response);
                    break;
                case "NextUser":
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
            MeetingHelper.syncUser(meetingID, Act_PartiAtMeeting.this);
        }
    };

    Handler agendaTimerHandler = new Handler();
    Runnable agendaTimerRunnable = new Runnable() {
        @Override
        public void run() {
            MeetingHelper.getAgendapointUser(meetingID, Act_PartiAtMeeting.this);
        }
    };

    private void syncMeeting(Call call, Response response) {
        try {
            passedTime = JSONHelper.JSONToSync(response.body().string());
            TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
            verbleibendeGesamtzeit.setText(String.valueOf(passedTime));
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
        if (runningMeeting == true) {
            timerHandler.postDelayed(timerRunnable, 500);
        }
    }

    private void stateMeeting(Call call, Response response) {
        try {
            String body = response.body().string();
            curAp = JSONHelper.JSONToState(body);
            if (curAp.getId() == 0) {
                runningMeeting = false;
                Intent i = new Intent(Act_PartiAtMeeting.this, Act_MeetingBeendet.class);
                startActivity(i);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView aktuAP = findViewById(R.id.aktuAPParti);
                        aktuAP.setText(curAp.getTitle());
                        TextView tv_sprechzeit = findViewById(R.id.txtPartiSprechzeit);
                        String string_sprecher = "Aktueller Sprecher: " + curAp.getActualSpeaker().getUser().getFirstname() + " " + curAp.getActualSpeaker().getUser().getLastname() +
                                "\nVerbleibende Sprechzeit: " + (curAp.getAvailableTime() - curAp.getRunningTime());
                        tv_sprechzeit.setText(string_sprecher);
                    }
                });

                if (firstname.equals(curAp.getActualSpeaker().getUser().getFirstname()) && lastname.equals(curAp.getActualSpeaker().getUser().getLastname())) {
                    btnNext.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setVisibility(View.INVISIBLE);
                }
            }
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        } finally {
            if (runningMeeting == true) {
                agendaTimerHandler.postDelayed(agendaTimerRunnable, 500);
            }
        }
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
                    partiGruss.setText("Hallo " + firstname + " " + lastname + ", willkommen im Meeting.");

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

            runningMeeting = true;
            lastLocalChange = LocalDateTime.now(ZoneId.systemDefault());
            timerHandler.post(timerRunnable);
            agendaTimerHandler.post(agendaTimerRunnable);

            MeetingHelper.syncMod(meetingID, this);
            MeetingHelper.getAgendapointMod(meetingID, this);

        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
    }
}
