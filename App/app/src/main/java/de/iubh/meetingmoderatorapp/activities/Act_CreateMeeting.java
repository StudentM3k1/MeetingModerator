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
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
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
        RecyclerView recyTLN;
        TeilnehmerAdapter tlnAdapter;
        RecyclerView.LayoutManager tlnLayoutManger;
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
                m.getSettings().setStartTime(LocalDateTime.parse(startDate.getText().toString() + "T" + startTime.getText().toString()));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()));
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
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
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
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
            Meeting meetingResponse = new Meeting();
            HTTPClient client = new HTTPClient();
            try {
                client.postMeeting(JSONHelper.MeetingToJSON(m));
                while (client.getResponseReceived() == false)
                {
                }
                    if (client.getResponseCode() != 200){
                        idRes.setText("Server Response: " + Integer.toString(client.getResponseCode()));
                    }else{
                        meetingResponse = JSONHelper.JSONToMeeting(client.getResponseBody());
                    }

            } catch (Exception e) {
                // Message : Servererror?
                e.printStackTrace();
            }

            modId = meetingResponse.getSettings().getModeratorId();
            idRes.setText("Deine ModeratorID ist: " + meetingResponse.getSettings().getModeratorId());
        });

        Button btnToHome = findViewById(R.id.btnBackToHome);
        btnToHome.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_IDEingabe.class));
                i.putExtra("modId", modId);
            startActivity(i);
        });

    }
}
