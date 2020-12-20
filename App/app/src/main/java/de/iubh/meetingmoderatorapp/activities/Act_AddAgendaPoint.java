package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.enumerations.AgendaPointStatus;

public class Act_AddAgendaPoint  extends AppCompatActivity {

    private Meeting m = new Meeting();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_agendapoint);
        AndroidThreeTen.init(this);

        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            m = JSONHelper.JSONToMeeting(extras.getString("JSON"));
        }

        Button btnBackToAgenda = findViewById(R.id.btnBackToAgenda);
        btnBackToAgenda.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
                startActivity(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        Button btnAddAP = findViewById(R.id.btn_addPointToAgenda);
        btnAddAP.setOnClickListener(v -> {
            Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);
            String apTitle = findViewById(R.id.txtAgendapointTitle).toString();
            String apNote = findViewById(R.id.txtAgendapointNote).toString();
            long availableTime = Long.parseLong(findViewById(R.id.txtAvailaleTim).toString());
            AgendaPoint ap = new AgendaPoint(
                    m.getAgenda()
                    .getAgendaPoints()
                            .size()+1,
                    apTitle,
                    apNote,
                    availableTime,
                    AgendaPointStatus.Planned,
                    0);
            m.getAgenda().getAgendaPoints().add(ap);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });


    }
}