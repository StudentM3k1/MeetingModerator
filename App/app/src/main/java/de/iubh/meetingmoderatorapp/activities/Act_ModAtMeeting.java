package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import java.net.HttpCookie;
import java.time.LocalDateTime;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_ModAtMeeting  extends AppCompatActivity {
    Meeting m = new Meeting();
    HTTPClient client = new HTTPClient();
    String surname;
    String lastname;
    String meetingID;
    Handler changeReqHandler = new Handler();
    Runnable runnable;
    int delay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);


        TextView meetingTitle = findViewById(R.id.txtModMeetingTitle);
        TextView aktuAP = findViewById(R.id.aktuAPMod);
        TextView verbleibendeGesamtzeit = findViewById(R.id.txtModVerbleibendeGesamtzeit);
        TextView verbleibendeAPZeit = findViewById(R.id.txtModVerbleibendeAPZeit);
        TextView verbleibendeSprechZeit = findViewById(R.id.txtVerbleibendeSprechZeit);
        TextView aktuSprecher = findViewById(R.id.txtModAktuSprecher);
        TextView modGruss = findViewById(R.id.txtModGruss);
        TextView modSprechNote = findViewById(R.id.txtModSprechzeit);


        RecyclerView recyAP;
        AgendaPointAdapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            surname = extras.getString("surname");
            lastname = extras.getString("lastname");
            String json = extras.getString("JSON");
            try {

            m = JSONHelper.JSONToMeeting(json);
        }catch (Exception e) {
                e.printStackTrace();
            }}

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
        btnEndSpeak.setOnClickListener(v -> {
            String j = null;
            try {
                j = JSONHelper.MeetingToJSON(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }

            client.postNextModerator(j, meetingID);
        });
    }

    @Override
    protected void onResume() {
        changeReqHandler.postDelayed(runnable = new Runnable() {
            public void run() {
                changeReqHandler.postDelayed(runnable, delay);

                client.getUserChange(meetingID);
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        changeReqHandler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

}
