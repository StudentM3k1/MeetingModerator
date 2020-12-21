package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_Agenda extends AppCompatActivity {
    private RecyclerView recyAP;
    private RecyclerView.Adapter apAdapter;
    private RecyclerView.LayoutManager apLayoutManger;
    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_see_agenda);
        AndroidThreeTen.init(this);

        // Aufbau RecyView
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String json = extras.getString("JSON");

            m = JSONHelper.JSONToMeeting(json);

            recyAP = findViewById(R.id.recyAP);
            recyAP.setHasFixedSize(true);
            apLayoutManger = new LinearLayoutManager(this);
            apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

            recyAP.setLayoutManager(apLayoutManger);
            recyAP.setAdapter(apAdapter);
        }



        // Button zu Activity AddAgendapoint
        Button btnAddAgendapoint = findViewById(R.id.btnAddAgendapoint);
        btnAddAgendapoint.setOnClickListener(v ->        {
            Intent i = new Intent(Act_Agenda.this, Act_AddAgendaPoint.class);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });


        // Button zu Activity CreateMeeting
        Button btnToMeetingCreation = findViewById(R.id.btnToMeetingCreation);
        btnToMeetingCreation.setOnClickListener(v ->        {
            Intent i = new Intent(Act_Agenda.this, Act_CreateMeeting.class);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

    }
}