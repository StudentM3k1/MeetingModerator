package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_PartiAtMeeting extends AppCompatActivity {
    Meeting m = new Meeting();
    String surname;
    String lastname;
    String  meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        TextView meetingTitle = findViewById(R.id.txtMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAP);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView partiGruss = findViewById(R.id.txtPartiGruss);
        TextView partiSprechNote = findViewById(R.id.txtPartiSprechzeit);


        RecyclerView recyAP;
        RecyclerView.Adapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
            String json = extras.getString("JSON");
            m = JSONHelper.JSONToMeeting(json);
        }

            recyAP = findViewById(R.id.recyPartiPoints);
            recyAP.setHasFixedSize(true);
            apLayoutManger = new LinearLayoutManager(this);
            apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

            recyAP.setLayoutManager(apLayoutManger);
            recyAP.setAdapter(apAdapter);

            meetingID = Long.toString(m.getId());
            meetingTitle.setText(m.getSettings().getMeetingTitle());
            partiGruss.setText("Hallo " + surname + " " + lastname + "Willkommen im Meeting.");








        HTTPClient client = new HTTPClient();

        // TODO Make button only visible when aktuSpeaker == User

        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.postNextUser(meetingID);
            }
        });
    }


}
