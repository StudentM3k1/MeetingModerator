package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.jakewharton.threetenabp.AndroidThreeTen;
import de.iubh.meetingmoderatorapp.R;

public class Act_MeetingBeendet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_eingabe);
        AndroidThreeTen.init(this);


        Button btnBeendet = findViewById(R.id.btnMeetingbeendet);
        btnBeendet.setOnClickListener(v -> {
            Intent i = (new Intent(Act_MeetingBeendet.this, Act_IDEingabe.class));
            startActivity(i);
        });
    }
}