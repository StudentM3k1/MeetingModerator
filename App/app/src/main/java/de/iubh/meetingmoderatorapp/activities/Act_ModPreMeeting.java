package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;


import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.enumerations.MeetingStatus;
import okhttp3.Call;
import okhttp3.Response;

public class Act_ModPreMeeting extends AppCompatActivity implements CallbackHandler {
    private String meetingID;
    View sbView;
    Meeting m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_pre_meeting);
        AndroidThreeTen.init(this);

        sbView = findViewById(R.id.modPreSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }

        MeetingHelper.getMeetingMod(meetingID, this);
    }

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "StartMeeting":
                Snackbar.make(
                        sbView,
                        "Meeting kann nicht gestartet werden",
                        Snackbar.LENGTH_LONG)
                        .show();
            case "GetMeetingMod":
                Snackbar.make(
                        sbView,
                        "Meeting kann nicht geladen werden",
                        Snackbar.LENGTH_LONG)
                        .show();
            default:
                break;
        }
    }

    public void onResponseCallback(Call call, Response response) {
        if (response.code() == 200) {
            switch (call.request().tag().toString()) {
                case "StartMeeting":
                    Intent i = (new Intent(Act_ModPreMeeting.this, Act_ModAtMeeting.class));
                    i.putExtra("meetingID", meetingID);
                    startActivity(i);
                    break;
                case "GetMeetingMod":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                m = JSONHelper.JSONToMeeting(response.body().string());
                            } catch (Exception e) {
                                onFailureCallback(call, new IOException());
                            }
                            TextView meetingTitle = findViewById(R.id.txtPreMeetingTitle);
                            meetingTitle.setText(m.getSettings().getMeetingTitle());
                            TextView gesamtZeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
                            gesamtZeit.setText(Long.toString(m.getSettings().getDuration()));
                            TextView txtModAktuSprecher = findViewById(R.id.txtModPreOrt);
                            txtModAktuSprecher.setText(m.getOrt());

                            // Aufbau RecyView AgendaPoint
                            RecyclerView recyAP = findViewById(R.id.recyApPreMeetingMod);
                            AgendaPointAdapter apAdapter;
                            RecyclerView.LayoutManager apLayoutManger;
                            recyAP.setHasFixedSize(true);

                            apLayoutManger = new LinearLayoutManager(Act_ModPreMeeting.this);
                            apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());
                            recyAP.setLayoutManager(apLayoutManger);
                            recyAP.setAdapter(apAdapter);

                            // Aufbau RecyView Participant
                            RecyclerView recyTLN = findViewById(R.id.recyAPModAtMeeting);
                            TeilnehmerAdapter tlnAdapter;
                            RecyclerView.LayoutManager tlnLayoutManger;
                            recyTLN.setHasFixedSize(true);
                            tlnLayoutManger = new LinearLayoutManager(Act_ModPreMeeting.this);
                            tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
                            recyTLN.setLayoutManager(tlnLayoutManger);
                            recyTLN.setAdapter(tlnAdapter);
                        }
                    });

                    findViewById(R.id.btnStartMeetingMod).setOnClickListener(v -> {
                        if (m.getMeetingStatus() == MeetingStatus.Done) {
                            Snackbar.make(
                                    sbView,
                                    "Meeting ist bereits beendet",
                                    Snackbar.LENGTH_LONG)
                                    .show();
                        } else if (m.getMeetingStatus() == MeetingStatus.Running) {
                            Intent intent = (new Intent(Act_ModPreMeeting.this, Act_ModAtMeeting.class));
                            intent.putExtra("meetingID", meetingID);
                            startActivity(intent);
                        } else {
                            MeetingHelper.startMeeting(meetingID, this);
                        }
                    });
                    break;
            }
        } else {
            onFailureCallback(call, new IOException());
        }
    }
}
