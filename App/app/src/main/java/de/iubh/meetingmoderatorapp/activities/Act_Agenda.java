package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_Agenda extends AppCompatActivity {
    private RecyclerView recyAP;
    private RecyclerView.Adapter apAdapter;
    private RecyclerView.LayoutManager apLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_see_agenda);
        AndroidThreeTen.init(this);


        // Aufbau RecyView
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String EinwahlJson = extras.getString("JSON");
            Meeting m = JSONHelper.JSONToMeeting(EinwahlJson);


        recyAP = findViewById(R.id.recyAP);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);
        } else {
            Snackbar.make(findViewById(R.id.recyAP),"Es wurden noch keine Agendapunkte hinzugefügt!", Snackbar.LENGTH_LONG).show();
        }

        // Button zu Activitz AddAgendapoint
        Button btnAddAgendapoint = findViewById(R.id.btnAddAgendapoint);
        btnAddAgendapoint.setOnClickListener(v -> {
            Intent i = new Intent(Act_Agenda.this, Act_AddAgendaPoint.class);
            startActivity(i);
        });

    }
}