package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;
import okhttp3.Call;
import okhttp3.Response;

public class Act_Welcome extends AppCompatActivity implements CallbackHandler {
    TextView surname;
    TextView lastname;
    Meeting m;
    String meetingID;
    View sbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome_scree);
        AndroidThreeTen.init(this);

        MeetingHelper mh = new MeetingHelper();
        sbView = findViewById(R.id.welcSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meetingID = extras.getString("meetingID");
        }
        MeetingHelper.getMeetingUser(meetingID, this);

    }

    public void onFailureCallback(Call call, IOException e) {
        Snackbar.make(sbView, "RecyView nicht möglich, meeting = null", Snackbar.LENGTH_LONG).show();
    }

    public void onResponseCallback(Call call, Response response) {
        if (response.code() == 200) {
            // Aufbau RecyclerView Participants
            try {
                m = JSONHelper.JSONToMeeting(response.body().string());
            }
            catch (Exception e) {
                onFailureCallback(call, new IOException());
            }

            if (m != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyTLN = findViewById(R.id.recyTeilnehmerliste);
                        TeilnehmerAdapter tlnAdapter;
                        RecyclerView.LayoutManager tlnLayoutManger;
                        recyTLN.setHasFixedSize(true);
                        tlnLayoutManger = new LinearLayoutManager(Act_Welcome.this);
                        tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
                        recyTLN.setLayoutManager(tlnLayoutManger);
                        recyTLN.setAdapter(tlnAdapter);

                        // clickable RecyclerViewContent
                        tlnAdapter.setOnItemClickListener(pos -> {
                            Intent i = (new Intent(Act_Welcome.this, Act_PartiAtMeeting.class));
                            //  String json = mh.getMeetingString(meetingID, sbView);
                            surname = findViewById(R.id.txtWelcSur);
                            lastname = findViewById(R.id.txtWelcLast);
                            i.putExtra("meetingID", meetingID);
                            // i.putExtra("JSON", json);
                            i.putExtra("surname", surname.getText().toString());
                            i.putExtra("lastname", lastname.getText().toString());
                            startActivity(i);
                        });
                    }
                });
            }
        } else onFailureCallback(call, new IOException());
    }
}


