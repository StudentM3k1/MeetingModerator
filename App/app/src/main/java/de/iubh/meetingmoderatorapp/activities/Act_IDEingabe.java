package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_IDEingabe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        EditText meetingID = findViewById(R.id.txtMeetingID);
        View sbView = findViewById(R.id.idSnack);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String modId = extras.getString("modId");
            meetingID.setText(modId);
        }


        Button btnToCreateMeeting = findViewById(R.id.btn_createNewMeeting);
        btnToCreateMeeting.setOnClickListener(v -> {
            Intent i = new Intent(Act_IDEingabe.this, Act_CreateMeeting.class);
            startActivity(i);
        });

        Button btnJoinMeeting = findViewById(R.id.btnMeetingEinwahl);
        btnJoinMeeting.setOnClickListener(v -> {
            String id = meetingID.getText().toString();
            Meeting m = mh.updateMeetingMod(id, sbView);
            if(m == null) {
                Snackbar.make(sbView, "Meeting konnte nicht geholt werden.", Snackbar.LENGTH_LONG).show();
            } else {
                Intent i;
                if (id.equals("")) {
                    Snackbar.make(
                            sbView,
                            "Bitte Meeting-ID eingeben.",
                            Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    if(m.getSettings().getModeratorId().equals("0") ){
                        i = (new Intent(Act_IDEingabe.this, Act_Welcome.class));
                    } else {
                        i = (new Intent(Act_IDEingabe.this, Act_ModPreMeeting.class));
                    }
                    i.putExtra("JSON", mh.getMeetingString(id, sbView));
                    i.putExtra("meetingID", id);
                    startActivity(i);

                }
            }

        });
    }
}




