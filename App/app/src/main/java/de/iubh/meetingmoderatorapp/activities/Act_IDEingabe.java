package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.enumerations.MeetingStatus;
import okhttp3.Call;
import okhttp3.Response;

public class Act_IDEingabe extends AppCompatActivity implements CallbackHandler {

    View sbView;
    String id;
    boolean isModerator = true;

    /* todo
    *  evtl. Ping bei start
    *  Meeting Änderungen (Abfragen auf Dauer, Anzahl User/MP)
    *  1,0 kassieren
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        EditText meetingID = findViewById(R.id.txtMeetingID);
        sbView = findViewById(R.id.idSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String modId = extras.getString("modId");
            meetingID.setText(modId);
        }

        Button btnToCreateMeeting = findViewById(R.id.btn_createNewMeeting);
        btnToCreateMeeting.setOnClickListener(v -> {
            Intent i = new Intent(Act_IDEingabe.this, Act_ModRegistration.class);
            startActivity(i);
        });

        Button btnJoinMeeting = findViewById(R.id.btnMeetingEinwahl);
        btnJoinMeeting.setOnClickListener(v -> {
            id = meetingID.getText().toString();

            if (id.equals("")) {
                Snackbar.make(
                        sbView,
                        "Bitte Meeting-ID eingeben.",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                MeetingHelper.getMeetingMod(id, this);
            }
        });
    }

    public void onFailureCallback(Call call, IOException e) {
        if (isModerator) {
            isModerator = false;
            MeetingHelper.getMeetingUser(id, this);
        } else {
            isModerator = true;
            Snackbar.make(
                    sbView,
                    "Meeting kann nicht geladen werden.",
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void onResponseCallback(Call call, Response response) {
        try {
            Meeting m = null;
            m = JSONHelper.JSONToMeeting(response.body().string());
            Intent i;
            if (m != null) {
                if (isModerator) {
                     i = (new Intent(Act_IDEingabe.this, Act_ModPreMeeting.class));
                    i.putExtra("meetingID", id);
                    startActivity(i);
                } else {
                    isModerator = true;
                    if (m.getMeetingStatus() == MeetingStatus.Planned) {
                        Snackbar.make(sbView, "Meeting wurde noch nicht gestartet.", Snackbar.LENGTH_LONG).show();
                    }
                    else if (m.getMeetingStatus() == MeetingStatus.Done) {
                        Snackbar.make(sbView, "Meeting wurde bereits beendet.", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        i = (new Intent(Act_IDEingabe.this, Act_PartiPreMeeting.class));
                        i.putExtra("meetingID", id);
                        startActivity(i);
                    }
                }
            } else {
                Snackbar.make(sbView, "Die ID stimmt nicht.", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            onFailureCallback(call, new IOException());
        }
    }
}




