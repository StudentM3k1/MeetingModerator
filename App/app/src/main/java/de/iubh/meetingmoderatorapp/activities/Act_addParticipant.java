package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import de.iubh.meetingmoderatorapp.R;

public class Act_addParticipant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_participant);
        AndroidThreeTen.init(this);

        Button btnBack = findViewById(R.id.btnBackHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_addParticipant.this, Act_CreateMeeting.class));
            }
        });

        Button btnAddParti = findViewById(R.id.btnAddParti);
        btnAddParti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Act_addParticipant.this, Act_CreateMeeting.class);
                String surname = findViewById(R.id.txtPartiFirstName).toString();
                String lastname = findViewById(R.id.txtPartiLastname).toString();
                String mail = findViewById(R.id.txtPartiMail).toString();
                Switch isMod = (Switch) findViewById(R.id.switchMod);
                Boolean isso = isMod.isChecked();

                i.putExtra("surname", surname);
                i.putExtra("lastname", lastname);
                i.putExtra("mail", mail);
                i.putExtra("isMod", isso);
                startActivity(i);

            }
        });

    }
}