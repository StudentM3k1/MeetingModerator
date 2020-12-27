package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;


import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;



public class Act_Welcome extends AppCompatActivity {
    TextView surname;
    TextView lastname;
    Meeting m;
    String meetingID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);


        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.welcSnack);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingID = extras.getString("meetingID");
            try {
                m = JSONHelper.JSONToMeeting(extras.getString("JSON"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Aufbau RecyclerView Participants
        RecyclerView recyTLN = findViewById(R.id.recyTeilnehmerliste);
        TeilnehmerAdapter tlnAdapter;
        RecyclerView.LayoutManager tlnLayoutManger;
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);

        // clickable RecyclerViewContent
        tlnAdapter.setOnItemClickListener(pos -> {
            Intent i = (new Intent(Act_Welcome.this, Act_PartiAtMeeting.class));
            String json = mh.getMeetingString(meetingID, sbView);
            surname = findViewById(R.id.txtWelcSur);
            lastname = findViewById(R.id.txtWelcLast);
            i.putExtra("meetingID", meetingID);
            i.putExtra("JSON", json);
            i.putExtra("surname", surname.getText().toString());
            i.putExtra("lastname", lastname.getText().toString());
            //Todo UserID mitnehmen
            startActivity(i);
        });

        /* --- Wird eventuell noch umgesetzt, falls Zeit dafür ist
        Button voyeurBtn = findViewById(R.id.btnMeetingbeendet);
        voyeurBtn.setOnClickListener(v -> {
            Intent i = (new Intent(Act_Welcome.this, Act_IDEingabe.class));
            startActivity(i);
        });
         */
    }
}

