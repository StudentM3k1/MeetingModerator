package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.Participant;
import de.iubh.meetingmoderatorapp.model.User;
import de.iubh.meetingmoderatorapp.model.enumerations.AgendaPointStatus;
import de.iubh.meetingmoderatorapp.model.enumerations.ParticipantType;

public class Act_addParticipant extends AppCompatActivity {
    private RecyclerView recyTLN;
    private RecyclerView.Adapter tlnAdapter;
    private RecyclerView.LayoutManager tlnLayoutManger;
    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_participant);
        AndroidThreeTen.init(this);


        EditText surname = findViewById(R.id.txtPartiFirstName);
        EditText lastname = findViewById(R.id.txtPartiLastname);
        EditText mail = findViewById(R.id.txtPartiMail);
        Button btnBack = findViewById(R.id.btnBackHome);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String json = extras.getString("JSON");
            m = JSONHelper.JSONToMeeting(json);

        }



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_addParticipant.this, Act_CreateMeeting.class));
            }
        });

        Button btnAddParti = findViewById(R.id.btnAddParti);
        btnAddParti.setOnClickListener(v -> {
            Intent i = new Intent(Act_addParticipant.this, Act_CreateMeeting.class);
            Participant p = new Participant(
                    m.getParticipants()
                    .size()+1,
                    new User(0,surname.getText().toString(),lastname.getText().toString(),mail.getText().toString()),
                    ParticipantType.Participant,0
            );
            m.getParticipants().add(p);

            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });
    }
}