package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;

public class Act_ModAtMeeting  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mod_at_meeting);
        AndroidThreeTen.init(this);

        // zeitliche abfrage 2 x pro seconde ob changes existieren


        // bei changes environment anpassen


    }
}
