package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_IDEingabe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);
        EditText meetingId = findViewById(R.id.txtMeetingID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingId.setText(extras.getString("modId"));
        }

        Button btnToCreateMeeting = findViewById(R.id.btn_createNewMeeting);
        btnToCreateMeeting.setOnClickListener(v -> {
            Intent i = new Intent(Act_IDEingabe.this, Act_CreateMeeting.class);
            startActivity(i);
        });

        Button btnJoinMeeting = findViewById(R.id.btnMeetingEinwahl);
        btnJoinMeeting.setOnClickListener(v -> {
            String id = meetingId.getText().toString();

            if (id == null) {
                Snackbar.make(
                        findViewById(R.id.IDView),
                        "Bitte Meeting-ID eingeben.",
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                HTTPClient client = new HTTPClient();
                while (client.getResponseReceived() == false) {
                }
                try {
                    if (client.getResponseCode() == 200) {
                        String meeting = client.getMeeting(id);
                        Intent i = (new Intent(Act_IDEingabe.this, Act_ModPreMeeting.class));
                        try {
                            i.putExtra("JSON", meeting);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(i);
                    } else {
                        Snackbar.make(
                                findViewById(R.id.IDView),
                                "Die MeetingID konnte nicht verarbeitet werden",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}




