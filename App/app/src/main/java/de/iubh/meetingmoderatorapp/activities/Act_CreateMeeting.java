package de.iubh.meetingmoderatorapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.*;
import okhttp3.Call;
import okhttp3.Response;

import static android.view.View.VISIBLE;
import static de.iubh.meetingmoderatorapp.R.id.btn_toAddParticipan;
import static de.iubh.meetingmoderatorapp.R.id.start;
import static de.iubh.meetingmoderatorapp.R.id.visible;

public class Act_CreateMeeting extends AppCompatActivity implements CallbackHandler {
    private Meeting m = new Meeting();
    private String modId;
    private String partId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);

        View sbView = findViewById(R.id.createSnack);

        EditText meetingTitle = findViewById(R.id.txtCreateMeetingTitle);
        EditText startDate = findViewById(R.id.timeCreateMeetingStartDate);
        EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
        TextView duration = findViewById(R.id.minCreateMeetingDuration);
        EditText ort = findViewById(R.id.txtCreateMeetingOrt);
        Button sendIDviaMail = findViewById(R.id.btnIDMial);

        duration.setText("240");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                m = JSONHelper.JSONToMeetinginApp(extras.getString("JSON"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String calcDur;
            ArrayList<AgendaPoint> apl = new ArrayList<>();
            apl = m.getAgenda().getAgendaPoints();
            long aplTime = 0;
            for (AgendaPoint ap : apl) {
                aplTime += ap.getAvailableTime();
            }
            calcDur = Long.toString(aplTime);
            meetingTitle.setText(m.getSettings().getMeetingTitle());
            startDate.setText(m.getSettings().getStartTime().toString().substring(0, 10));
            startTime.setText(m.getSettings().getStartTime().toString().substring(11));
            duration.setText(calcDur);
            ort.setText(m.getOrt());
        }

        RecyclerView recyTLN = findViewById(R.id.recyTln);
        TeilnehmerAdapter tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
        RecyclerView.LayoutManager tlnLayoutManger = new LinearLayoutManager(this);
        recyTLN.setHasFixedSize(true);

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);


        Button btnToAgenda = findViewById(R.id.btnToAgenda);
        btnToAgenda.setOnClickListener(v -> {
            Intent i = new Intent(Act_CreateMeeting.this, Act_Agenda.class);
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                m.getSettings().setStartTime(LocalDateTime.parse(startDate.getText().toString() + "T" + startTime.getText().toString()));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSONinApp(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button btnToAddParticipant = findViewById(btn_toAddParticipan);
        btnToAddParticipant.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_addParticipant.class));
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                m.getSettings().setStartTime(LocalDateTime.parse(startDate.getText().toString() + "T" + startTime.getText().toString()));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSONinApp(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });


        Button btnCreateMeeting = findViewById(R.id.btn_createMeeting);
        btnCreateMeeting.setOnClickListener(v -> {
                    m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                    m.getSettings().setStartTime(LocalDateTime.parse(startDate.getText().toString() + "T" + startTime.getText().toString()));
                    m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                    m.setOrt(ort.getText().toString());
                    MeetingHelper.createMeeting(m,this);
                }
           );

        sendIDviaMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"hiernochStrings@arrayausmeeting.einbringen"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Wir haben ein Meeting zusammen");
                i.putExtra(Intent.EXTRA_TEXT, "Bitte melde Dich am " + startDate.getText().toString() + "um " + startTime.getText().toString() + "mit Deiner MeetingID   ---   " + partId + "   ---   an um teilzunehemen");
                try {
                    startActivity(Intent.createChooser(i, "Sende Mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(sbView, "Es sind keine Mail-Clients installiert, weshalb die Mail nicht versendet werden kann.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        Button btnToHome = findViewById(R.id.btnBackToHome);
        btnToHome.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_IDEingabe.class));
            i.putExtra("modId", modId);
            startActivity(i);
        });

    }

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "CreateMeeting":
                onFailureCreateMeeting(call, e);
                break;
            default:
                break;
        }
    }

    public void onResponseCallback(Call call, Response response) {
        if (response.code() == 200) {
        switch (call.request().tag().toString()) {
            case "CreateMeeting":
                onResponseCreateMeeting(call, response);
                break;
            default:
                break;
        }
        } else {
            onFailureCreateMeeting("Server konnte Anfrage nicht verarbeiten.");
        }
    }

    private void onFailureCreateMeeting(Call call, IOException e) {
        onFailureCreateMeeting(e.getMessage());
    }

    public void onFailureCreateMeeting(String error) {
        TextView idRes = findViewById(R.id.IDResponse);
        idRes.setText("Hoppla, da hat sich die App verschluckt! \nFehlernachricht: " + error);
    }



    private void onResponseCreateMeeting(Call call, Response response) {
            TextView idRes = findViewById(R.id.IDResponse);
            Button sendIDviaMail = findViewById(R.id.btnIDMial);

            Meeting m = null;
            try {
                m = JSONHelper.JSONToMeeting(response.body().string());
            } catch (Exception e) {
                onFailureCreateMeeting("Response nicht lesbar.");
            }
            if (m != null) {
                modId = m.getSettings().getModeratorId();
                partId = m.getSettings().getParticipantId();

                if (!modId.equals("") && !partId.equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            idRes.setText(
                                    "Deine ModeratorID ist: "
                                            + modId
                                            + "\nDie MeetingID der User ist: "
                                            + partId
                                            + "\nDie ID's bitte notieren.");
                            sendIDviaMail.setVisibility(VISIBLE);
                        }
                    });
                } else {
                    onFailureCreateMeeting("Response nicht lesbar.");
                }
            } else {
                onFailureCreateMeeting("Response nicht lesbar.");
            }
    }
}
