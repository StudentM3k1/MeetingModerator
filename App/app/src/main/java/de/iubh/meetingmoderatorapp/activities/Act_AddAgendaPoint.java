package de.iubh.meetingmoderatorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.json.JSONException;

import java.io.IOException;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;
import de.iubh.meetingmoderatorapp.model.Meeting;
import de.iubh.meetingmoderatorapp.model.enumerations.AgendaPointStatus;
import okhttp3.Call;
import okhttp3.Response;

public class Act_AddAgendaPoint  extends AppCompatActivity  {

    private Meeting m = new Meeting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_agendapoint);
        AndroidThreeTen.init(this);

        EditText apTitle = findViewById(R.id.txtAgendapointTitle);
        EditText apNote = findViewById(R.id.txtAgendapointNote);
        EditText apTime = findViewById(R.id.txtAPDauer);

        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            try {
                m = JSONHelper.JSONToMeeting(extras.getString("JSON"));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        ImageButton btnBackToAgenda = findViewById(R.id.btnBackToAgenda);
        btnBackToAgenda.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
                startActivity(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ImageButton btnAddAP = findViewById(R.id.btn_addPointToAgenda);
        btnAddAP.setOnClickListener(v -> {
            Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);


            AgendaPoint ap = new AgendaPoint(
                    m.getAgenda()
                    .getAgendaPoints()
                            .size()+1,
                    apTitle.getText().toString(),
                    apNote.getText().toString(),
                    Long.parseLong(apTime.getText().toString())*60,
                    AgendaPointStatus.Planned,
                    0);
            m.getAgenda().getAgendaPoints().add(ap);
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));

            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });


    }
    public void onFailureCallback(Call call, IOException e)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }

    }

    public void onResponseCallback(Call call, Response response)
    {
        switch (call.request().tag().toString())
        {
            case "":
                break;
            default:
                break;
        }
    }
}