package de.iubh.meetingmoderatorapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.threeten.bp.LocalDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.controller.CallbackHandler;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.MeetingHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.*;
import okhttp3.Call;
import okhttp3.Response;


import static android.view.View.VISIBLE;
import static de.iubh.meetingmoderatorapp.R.id.btn_toAddParticipan;

public class Act_CreateMeeting extends AppCompatActivity implements CallbackHandler, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Meeting m = new Meeting();
    private String modId;
    private String partId;
    private boolean isCreated = false;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    String meetyear, meetmonth, meetday, meethour, meetminute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);

        EditText meetingTitle = findViewById(R.id.txtCreateMeetingTitle);
        EditText startDate = findViewById(R.id.timeCreateMeetingStartDate);
        EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
        TextView duration = findViewById(R.id.minCreateMeetingDuration);
        EditText ort = findViewById(R.id.txtCreateMeetingOrt);
        Button sendIDviaMail = findViewById(R.id.btnIDMial);

        View sbView = findViewById(R.id.createSnack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                String meeting = extras.getString("JSON");
                m = JSONHelper.JSONToMeeting(meeting);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String calcDur;
            ArrayList<AgendaPoint> apl = new ArrayList<>();
            apl = m.getAgenda().getAgendaPoints();
            long aplTime = 0;
            for (AgendaPoint ap : apl) {
                aplTime += ap.getAvailableTime();
            }
            calcDur = Long.toString(aplTime / 60);
            meetingTitle.setText(m.getSettings().getMeetingTitle());
            meetyear = m.getSettings().getStartTime().toString().substring(0, 4);
            meetmonth = m.getSettings().getStartTime().toString().substring(5, 7);
            meetday = m.getSettings().getStartTime().toString().substring(8, 10);
            startDate.setText(meetday + "." + meetmonth + "." + meetyear);

            meethour = m.getSettings().getStartTime().toString().substring(11, 13);
            meetminute = m.getSettings().getStartTime().toString().substring(14, 16);
            startTime.setText(meethour + ":" + meetminute);
            duration.setText(calcDur);

            ort.setText(m.getOrt());
        } else {
            Calendar now = Calendar.getInstance();
            meetyear = Integer.toString(now.get(Calendar.YEAR));
            meetmonth = Integer.toString(now.get(Calendar.MONTH));
            meetday = Integer.toString(now.get(Calendar.DAY_OF_MONTH));


        }

        RecyclerView recyTLN = findViewById(R.id.recyTln);
        TeilnehmerAdapter tlnAdapter = new TeilnehmerAdapter(m.getParticipants());
        RecyclerView.LayoutManager tlnLayoutManger = new LinearLayoutManager(this);
        recyTLN.setHasFixedSize(true);
        recyTLN.setLayoutManager(tlnLayoutManger);
        recyTLN.setAdapter(tlnAdapter);


        // Datepicker
        startDate.setFocusable(false);
        startDate.setClickable(true);
        startDate.setOnClickListener(v -> {
            datePickerDialog = DatePickerDialog.newInstance(Act_CreateMeeting.this,
                    Integer.parseInt(meetyear),
                    Integer.parseInt(meetmonth)-1,
                    Integer.parseInt(meetday)
            );
            datePickerDialog.setThemeDark(true);
            datePickerDialog.showYearPickerFirst(false);
            datePickerDialog.dismissOnPause(true);
            datePickerDialog.setMinDate(new GregorianCalendar(2021, 00, 01));
            datePickerDialog.setTitle("Meeting Termin definieren");
            datePickerDialog.setOnCancelListener(dialogInterface -> Snackbar.make(sbView, "Meetingdatum nicht aktualisiert", Snackbar.LENGTH_LONG).show());

            datePickerDialog.show(getSupportFragmentManager(), "DateTimePickerDialog");
        });

        //Timepicker
        startTime.setFocusable(false);
        startTime.setClickable(true);
        startTime.setOnClickListener(v -> {
            timePickerDialog = TimePickerDialog.newInstance(Act_CreateMeeting.this,
                    true );

            timePickerDialog.setThemeDark(true);
            timePickerDialog.dismissOnPause(true);
            timePickerDialog.setTitle("Wann startet das Meeting?");

            timePickerDialog.show(getSupportFragmentManager(), "TimePickerDialog");
        });



        ImageButton btnToAgenda = findViewById(R.id.btnToAgenda);
        btnToAgenda.setOnClickListener(v -> {
            Intent i = new Intent(Act_CreateMeeting.this, Act_Agenda.class);
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                m.getSettings().setStartTime(LocalDateTime.parse(meetyear + "-" + meetmonth + "-" + meetday + "T" + meethour + ":" + meetminute));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()) * 60);
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

        Button btnToAddParticipant = findViewById(btn_toAddParticipan);
        btnToAddParticipant.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_addParticipant.class));
            try {
                m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                m.getSettings().setStartTime(LocalDateTime.parse(meetyear + "-" + meetmonth + "-" + meetday + "T" + meethour + ":" + meetminute));
                m.getSettings().setDuration(Long.parseLong(duration.getText().toString()) * 60);
                m.setOrt(ort.getText().toString());
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });


        Button btnCreateMeeting = findViewById(R.id.btn_createMeeting);
        btnCreateMeeting.setOnClickListener(v -> {
                    m.getSettings().setMeetingTitle(meetingTitle.getText().toString());
                    m.getSettings().setStartTime(LocalDateTime.parse(meetyear + "-" + meetmonth + "-" + meetday + "T" + meethour + ":" + meetminute));
                    m.getSettings().setDuration(Long.parseLong(duration.getText().toString()) * 60);
                    m.setOrt(ort.getText().toString());
                    if (m.getAgenda().getAgendaPoints().size() == 0) {
                        Snackbar.make(sbView, "Es muss mindestens ein Agendapunkt angegeben werden!", Snackbar.LENGTH_LONG).show();
                    } else if (m.getParticipants().size() == 0) {
                        Snackbar.make(sbView, "Es muss mindestens ein Teilnehmer am Meeting teilnehmen!", Snackbar.LENGTH_LONG).show();
                    } else if (m.getSettings().getDuration() <= 60) {
                        Snackbar.make(sbView, "Ein Meeting muss länger als 1 Minute dauern!", Snackbar.LENGTH_LONG).show();
                    } else {
                        MeetingHelper.createMeeting(m, this);
                        isCreated = true;
                    }
                }
        );

        sendIDviaMail.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"hiernochStrings@arrayausmeeting.einbringen"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Wir haben ein Meeting zusammen");
            i.putExtra(Intent.EXTRA_TEXT, "Bitte melde Dich am " + meetday + "." + meetmonth + "." + meetyear + " um " + meethour + ":" + meetminute + " mit Deiner MeetingID   ---   " + partId + "   ---   an um teilzunehemen");
            try {
                startActivity(Intent.createChooser(i, "Sende Mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar.make(sbView, "Es sind keine Mail-Clients installiert, weshalb die Mail nicht versendet werden kann.", Snackbar.LENGTH_LONG).show();
            }
        });

        ImageButton btnToHome = findViewById(R.id.btnBackToHome);
        btnToHome.setOnClickListener(v -> {
            if (isCreated) {
                Intent i = (new Intent(Act_CreateMeeting.this, Act_IDEingabe.class));
                i.putExtra("modId", modId);
                startActivity(i);
            } else {
                Snackbar sb = Snackbar.make(sbView, "Ohne Meeting speichern fortfahren?", Snackbar.LENGTH_LONG);
                sb.setAction("Alles verlieren!", v1 -> {
                    Intent i = (new Intent(Act_CreateMeeting.this, Act_IDEingabe.class));
                    startActivity(i);
                });
                sb.show();
            }


        });

        ImageButton btnBackToRegistMod = findViewById(R.id.btnBackToRegistMod);
        btnBackToRegistMod.setOnClickListener(v -> {
            Intent i = (new Intent(Act_CreateMeeting.this, Act_ModRegistration.class));
            try {
                i.putExtra("JSON", JSONHelper.MeetingToJSON(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

    }


    // Callbacks

    public void onFailureCallback(Call call, IOException e) {
        switch (call.request().tag().toString()) {
            case "CreateMeeting":
                onFailureCreateMeeting(call, e);
                break;
            default:
                break;
        }
    }

    public void onResponseCallback(Call call, Response response) {
        if (response.code() == 200) {
            switch (call.request().tag().toString()) {
                case "CreateMeeting":
                    onResponseCreateMeeting(call, response);
                    break;
                default:
                    break;
            }
        } else {
            onFailureCreateMeeting("Server konnte Anfrage nicht verarbeiten.");
        }
    }

    private void onFailureCreateMeeting(Call call, IOException e) {
        onFailureCreateMeeting(e.getMessage());
    }

    public void onFailureCreateMeeting(String error) {

        runOnUiThread(() -> {
            TextView idRes = findViewById(R.id.IDResponse);
            idRes.setText("Hoppla, da hat sich die App verschluckt! \nFehlernachricht: " + error);
        });
    }

    private void onResponseCreateMeeting(Call call, Response response) {
        TextView idRes = findViewById(R.id.IDResponse);
        Button sendIDviaMail = findViewById(R.id.btnIDMial);

        Meeting m = null;
        try {
            m = JSONHelper.JSONToMeeting(response.body().string());
        } catch (Exception e) {
            onFailureCreateMeeting("Response nicht lesbar.");
        }
        if (m != null) {
            modId = m.getSettings().getModeratorId();
            partId = m.getSettings().getParticipantId();

            if (!modId.equals("") && !partId.equals("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        idRes.setText(
                                "Deine ModeratorID ist: "
                                        + modId
                                        + "\nDie MeetingID der User ist: "
                                        + partId
                                        + "\nDie ID's bitte notieren.");
                        sendIDviaMail.setVisibility(VISIBLE);


                    }
                });
            } else {
                onFailureCreateMeeting("Response nicht lesbar.");
            }
        } else {
            onFailureCreateMeeting("Response nicht lesbar.");
        }
    }


    // Date and Time Methods

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
/*if ((m.getSettings().getStartTime().toLocalDate().isEqual(year+"-"+monthOfYear+"-"+dayOfMonthm.getSettings().getStartTime().toLocalTime().isBefore(LocalDateTime.now().toLocalTime()))
                            || m.getSettings().getStartTime().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
                        Snackbar.make(sbView, "Ein Meeting darf nicht in der Vergangenheit beginnen (MEZ)", Snackbar.LENGTH_LONG).show();
                    }*/

        EditText startDate = findViewById(R.id.timeCreateMeetingStartDate);

        monthOfYear += 1;
        if (dayOfMonth < 10) {
            meetday = "0" + dayOfMonth;
        } else {
            meetday = Integer.toString(dayOfMonth);
        }
        if (monthOfYear < 10) {

            meetmonth = "0" + monthOfYear;
        } else {
            meetmonth = Integer.toString(monthOfYear);
        }

        String date = meetday + "." + meetmonth + "." + year;
        startDate.setText(date);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        /*if ((m.getSettings().getStartTime().toLocalTime().isBefore(LocalDateTime.now().toLocalTime()))
                            || m.getSettings().getStartTime().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            Snackbar.make(sbView, "Ein Meeting darf nicht in der Vergangenheit beginnen (MEZ)", Snackbar.LENGTH_LONG).show();
        }*/

        if(hourOfDay < 10){
            meethour = "0"+Integer.toString(hourOfDay);
        }else {
            meethour = Integer.toString(hourOfDay);
        }

        if(minute < 10){
            meetminute =  "0"+Integer.toString(minute);
        }else {
            meetminute =  Integer.toString(minute);
        }



        EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
        String time =  meethour+":"+meetminute;
        startTime.setText(time);

    }
}
