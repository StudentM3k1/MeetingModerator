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

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;


import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.*;

import static de.iubh.meetingmoderatorapp.R.id.btn_toAddParticipan;
import static de.iubh.meetingmoderatorapp.R.id.start;

public class Act_CreateMeeting extends AppCompatActivity {
    private Meeting m = new Meeting();
    private String modId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        View sbView = findViewById(R.id.createSnack);

        EditText meetingTitle = findViewById(R.id.txtCreateMeetingTitle);
        EditText startDate = findViewById(R.id.timeCreateMeetingStartDate);
        EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
        EditText duration = findViewById(R.id.minCreateMeetingDuration);
        EditText ort = findViewById(R.id.txtCreateMeetingOrt);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            try{
          m = JSONHelper.JSONToMeeting(extras.getString("JSON"));}
          catch (Exception e) {
                e.printStackTrace();
            }
          meetingTitle.setText(m.getSettings().getMeetingTitle());
          startDate.setText(m.getSettings().getStartTime().toString().substring(0,10));
          startTime.setText(m.getSettings().getStartTime().toString().substring(11));
          duration.setText("240");
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
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
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
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
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
            TextView idRes = findViewById(R.id.IDResponse);
            Meeting meetingResponse = mh.postMeetingMod(m, sbView);

            modId = meetingResponse.getSettings().getModeratorId();

            //TODO Mailversand an Partis mit deren ID.
            idRes.setText(
                    "Deine ModeratorID ist: "
                            + modId
                            +"\nDie MeetingID der User ist: "
                            + meetingResponse.getSettings().getParticipantId()
                            +"\nDie ID's bitte notieren.");
        });

        Button btnToHome = findViewById(R.id.btnBackToHome);
        btnToHome.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_IDEingabe.class));
                i.putExtra("modId", modId);
            startActivity(i);
        });

    }
}
