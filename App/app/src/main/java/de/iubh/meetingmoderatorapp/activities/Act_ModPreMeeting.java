package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_ModPreMeeting extends AppCompatActivity {
    private String meetingID;
    private Meeting m = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        RecyclerView recyAP;
        AgendaPointAdapter apAdapter;
        RecyclerView.LayoutManager apLayoutManger;

        RecyclerView recyTLN;
        TeilnehmerAdapter tlnAdapter;
        RecyclerView.LayoutManager tlnLayoutManger;

        Bundle extras = getIntent().getExtras();
        String json = extras.getString("JSON");

        try {
            m = JSONHelper.JSONToMeeting(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HTTPClient client = new HTTPClient();


            // Aufbau RecyView AgendaPoint
            recyAP = findViewById(R.id.recyAP);
            recyAP.setHasFixedSize(true);
            apLayoutManger = new LinearLayoutManager(this);
            apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

            recyAP.setLayoutManager(apLayoutManger);
            recyAP.setAdapter(apAdapter);


        // Aufbau RecyView Participant

        recyTLN = findViewById(R.id.recyTln);
        recyTLN.setHasFixedSize(true);
        tlnLayoutManger = new LinearLayoutManager(this);
        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());

        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);

        Button btnStartMeeting = findViewById(R.id.btnStartMeeting);
        btnStartMeeting.setOnClickListener(v -> {
            Intent i = (new Intent(Act_ModPreMeeting.this, Act_ModAtMeeting.class));

            try {
                String j = JSONHelper.MeetingToJSON(m);
                meetingID = Long.toString(m.getId());
                if(j != null){
                    client.postStartModerator(j, meetingID);
                    while (client.getResponseReceived() == false) {        }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }


            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);

        });
        }

    }

