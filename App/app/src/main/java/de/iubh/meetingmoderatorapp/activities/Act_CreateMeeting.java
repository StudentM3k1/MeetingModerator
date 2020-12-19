package de.iubh.meetingmoderatorapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.Agenda;
import de.iubh.meetingmoderatorapp.model.Participant;
import de.iubh.meetingmoderatorapp.model.enumerations.MeetingStatus;

public class Act_CreateMeeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);

        /* all of necessay things,  a meeting need



        long id
        Agenda agenda,
        MeetingSettings settings,
        ArrayList<Participant> participants,
                MeetingStatus meetingStatus,
        long passedTime,
        String ort
   */
    }
}