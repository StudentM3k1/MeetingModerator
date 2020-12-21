package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;


import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.*;

import static de.iubh.meetingmoderatorapp.R.id.btn_toAddParticipan;

public class Act_CreateMeeting extends AppCompatActivity {
    private RecyclerView recyTLN;
    private RecyclerView.Adapter tlnAdapter;
    private RecyclerView.LayoutManager tlnLayoutManger;
    private Meeting m = new Meeting();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);

        EditText meetingTitle = findViewById(R.id.txtCreateMeetingTitle);
        EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
        EditText duration = findViewById(R.id.minCreateMeetingDuration);
        EditText ort = findViewById(R.id.txtCreateMeetingOrt);



        Bundle extras = getIntent().getExtras();
        if(extras != null) {
          m = JSONHelper.JSONToMeeting(extras.getString("JSON"));
          meetingTitle.setText(m.getSettings().getMeetingTitle());
          startTime.setText(m.getSettings().getStartTime().toString());
          //TODO NumberFormatting
          duration.setText("240");
          ort.setText(m.getOrt());

        }
        recyTLN = findViewById(R.id.recyTln);
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);


        Button btnToAgenda = findViewById(R.id.btnToAgenda);
        btnToAgenda.setOnClickListener(v -> {
            Intent i = new Intent(Act_CreateMeeting.this, Act_Agenda.class);
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                //TODO Zeit aus Layout ubernehmenr
                //m.getSettings().setStartTime(LocalDateTime.parse("20.12.20 12:20 11:55".toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button btnToAddParticipant = findViewById(btn_toAddParticipan);
        btnToAddParticipant.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_addParticipant.class));
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                //TODO Zeit aus Layout ubernehmenr
                //m.getSettings().setStartTime(LocalDateTime.parse("20.12.20 12:20 11:55", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
               // m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });


        Button btnCreateMeeting = findViewById(R.id.btn_createMeeting);
        btnCreateMeeting.setOnClickListener(v -> {
            m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
            //TODO Zeit aus Layout ubernehmenr
            //m.getSettings().setStartTime(LocalDateTime.parse("20.12.20 12:20 11:55", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
            m.setOrt(ort.getText().toString());
            try {
                HTTPClient client = new HTTPClient();
                Meeting meetingResponse = JSONHelper
                        .JSONToMeeting(client.postMeeting(JSONHelper.MeetingToJSON(m)));
                TextView idRes = findViewById(R.id.IDResponse);
                idRes.setText(meetingResponse.getSettings().getModeratorId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        Button btnToHome = findViewById(R.id.btnBackToHome);


    }
}
