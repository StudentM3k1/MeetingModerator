package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;


import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;



public class Act_Welcome extends AppCompatActivity {
    TextView surname;
    TextView lastname;
    Meeting m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);

        RecyclerView recyTLN;
        TeilnehmerAdapter tlnAdapter;
        RecyclerView.LayoutManager tlnLayoutManger;

        Bundle extras = getIntent().getExtras();
        String json = extras.getString("JSON");
try {


     m = JSONHelper.JSONToMeeting(json);
}catch (Exception e) {
    e.printStackTrace();
}

        recyTLN = findViewById(R.id.recyTeilnehmerliste);
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);


        tlnAdapter.setOnItemClickListener(pos -> {
            Intent i = (new Intent(Act_Welcome.this, Act_PartiAtMeeting.class));
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
                surname = findViewById(R.id.txtWelcSur);
                lastname = findViewById(R.id.txtWelcLast);
                i.putExtra("surname", surname.getText().toString());
                i.putExtra("lastname", lastname.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button voyeurBtn = findViewById(R.id.btnMeetingbeendet);
        voyeurBtn.setOnClickListener(v -> {
            Intent i = (new Intent(Act_Welcome.this, Act_IDEingabe.class));
            startActivity(i);
        });
    }
}

