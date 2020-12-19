package de.iubh.meetingmoderatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.controller.HTTPClient;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        HTTPClient client = new HTTPClient();
        String s = client.postMeeting("http://192.168.178.110:8080/MeetingModeratorServer/Meeting/","{\n" +
                "    \"meetingSettings\": {\n" +
                "        \"duration\": 12,\n" +
                "        \"participantId\": \"8888888321\",\n" +
                "        \"meetingTitle\": \"Das Meeting\",\n" +
                "        \"startTime\": \"1970-01-12\",\n" +
                "        \"id\": 9876,\n" +
                "        \"moderatorId\": \"123456789\"\n" +
                "    },\n" +
                "    \"passedTime\": 0,\n" +
                "    \"id\": 1234,\n" +
                "    \"agenda\": {\n" +
                "        \"agendaPoints\": [\n" +
                "            {\n" +
                "                \"note\": \"Labern\",\n" +
                "                \"availableTime\": 600,\n" +
                "                \"id\": 234234,\n" +
                "                \"title\": \"Erster Punkt\",\n" +
                "                \"status\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"id\": 12123\n" +
                "    },\n" +
                "    \"status\": 0,\n" +
                "    \"participants\": [\n" +
                "        {\n" +
                "            \"usedTime\": 0,\n" +
                "            \"id\": 1231234,\n" +
                "            \"type\": 1,\n" +
                "            \"user\": {\n" +
                "                \"firstname\": \"Uwe\",\n" +
                "                \"mail\": \"mail@mail.com\",\n" +
                "                \"id\": 1234,\n" +
                "                \"lastname\": \"Dietrich\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        TextView tv = findViewById(R.id.txtView);
        tv.setText(s);



    }
}