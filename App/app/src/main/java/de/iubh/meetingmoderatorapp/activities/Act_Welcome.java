package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;


import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_Welcome extends AppCompatActivity {
    private RecyclerView recyTLN;
    private RecyclerView.Adapter tlnAdapter;
    private RecyclerView.LayoutManager tlnLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        Bundle extras = getIntent().getExtras();
        String EinwahlJson = extras.getString("JSON");

        Meeting m = JSONHelper.JSONToMeeting(EinwahlJson);

        recyTLN = findViewById(R.id.recyTeilnehmerliste);
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);

        /*
        tlnAdapter.setOnItemClickListener(new TeilnehmerAdapter.OnItemClickListener() {

                                              @Override
                                              public void onItemClick(int pos) {
                                                  Intent i = (new Intent(Act_Welcome.this, Act_PartiAtMeeting.class));

                                                  startActivity(i);
                                              }
                                          });
*/
        Button voyeurBtn = findViewById(R.id.btnVoyeurButton);
        voyeurBtn.setOnClickListener(v -> {
            Intent i = (new Intent(Act_Welcome.this, Act_IDEingabe.class));
            startActivity(i);
        });
    }
}

