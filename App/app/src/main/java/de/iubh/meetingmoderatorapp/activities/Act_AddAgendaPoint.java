package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;

public class Act_AddAgendaPoint extends AppCompatActivity {
    static String GET_URL="http://10.0.2.2:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_agendapoint);
        AndroidThreeTen.init(this);

        Button btnBackToAgenda = findViewById(R.id.btnBackToAgenda);
        btnBackToAgenda.setOnClickListener(v -> startActivity(new Intent(Act_AddAgendaPoint.this, Act_Agenda.class)));

        Button btnAddAP = findViewById(R.id.btn_addPointToAgenda);
        btnAddAP.setOnClickListener(v -> {
            Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);
            String apTitle = findViewById(R.id.txtAgendapointTitle).toString();
            String apNote = findViewById(R.id.txtAgendapointNote).toString();
            long availableTime = Long.parseLong(findViewById(R.id.txtAvailaleTime).toString());

            i.putExtra("apTitle", apTitle);
            i.putExtra("apNote", apNote);
            i.putExtra("availableTime", availableTime);
            startActivity(i);
        });


    }
}