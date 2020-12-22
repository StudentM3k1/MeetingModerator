package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_ModPreMeeting extends AppCompatActivity {
    String meetingID;
    Meeting m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parti_at_meeting);
        AndroidThreeTen.init(this);

        Bundle extras = getIntent().getExtras();
        String json = extras.getString("JSON");

try {
            m = JSONHelper.JSONToMeeting(json);
}
    catch (Exception e) {
        e.printStackTrace();
    }
        HTTPClient client = new HTTPClient();
        Button btnStartMeeting = findViewById(R.id.btnStartMeeting);
        btnStartMeeting.setOnClickListener(v -> {

            try {
                String j = JSONHelper.MeetingToJSON(m);
                meetingID = Long.toString(m.getId());
                if(j != null){
                    client.postStartModerator(j, meetingID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }

            Intent i = (new Intent(Act_ModPreMeeting.this, Act_ModAtMeeting.class));
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
