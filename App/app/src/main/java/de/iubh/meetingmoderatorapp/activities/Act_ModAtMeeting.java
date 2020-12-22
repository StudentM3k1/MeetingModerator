package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Act_ModAtMeeting  extends AppCompatActivity {
    Meeting m = new Meeting();
    String surname;
    String lastname;
    String  meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);

        // zeitliche abfrage 2 x pro seconde ob changes existieren


        // bei changes environment anpassen

        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAPMod);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtModVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView modGruss = findViewById(R.id.txtModGruss);
        TextView modSprechNote = findViewById(R.id.txtModSprechzeit);


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
        //modGruss.setText("Hallo " + surname + " " + lastname + "Willkommen im Meeting.");
        modGruss.setText("Hallo Moderator, willkommen im Meeting.");






        HTTPClient client = new HTTPClient();



        Button btnEndSpeak = findViewById(R.id.btnPartiSprechenBeenden);
        btnEndSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.postNextModerator(meetingID);
            }
        });
    }
}
