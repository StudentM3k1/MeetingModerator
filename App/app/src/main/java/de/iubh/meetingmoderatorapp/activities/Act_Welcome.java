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

import org.threeten.bp.LocalDateTime;

import de.iubh.meetingmoderatorapp.R;
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
        setContentView(R.layout.act_welcome_scree);
        AndroidThreeTen.init(this);


        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.welcSnack);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingID = extras.getString("meetingID");
        }
        m = mh.getMeetingUser(meetingID, sbView);

        // Aufbau RecyclerView Participants
        if( m!=null) {
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
                surname = findViewById(R.id.txtWelcSur);
                lastname = findViewById(R.id.txtWelcLast);
                i.putExtra("meetingID", meetingID);
                i.putExtra("surname", surname.getText().toString());
                i.putExtra("lastname", lastname.getText().toString());
                startActivity(i);
            });

        }else {
            Snackbar.make(sbView,"RecyView nicht möglich, meeting = null", Snackbar.LENGTH_LONG).show();
        }

        /* --- Wird eventuell noch umgesetzt, falls Zeit dafür ist
        Button voyeurBtn = findViewById(R.id.btnMeetingbeendet);
        voyeurBtn.setOnClickListener(v -> {
            Intent i = (new Intent(Act_Welcome.this, Act_IDEingabe.class));
            startActivity(i);
        });
         */
    }
}

