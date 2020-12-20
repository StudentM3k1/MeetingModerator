package de.iubh.meetingmoderatorapp.activities;
import android.widget.EditText;
import android.widget.TextView;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


import java.util.ArrayList;
import de.iubh.meetingmoderatorapp.model.*;
import static de.iubh.meetingmoderatorapp.R.id.start;

public class Act_CreateMeeting {    static String POST_URL ="http://192.168.178.110:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_meeting);
        AndroidThreeTen.init(this);


        Intent intent = getIntent();

        Participant p = new Participant(0,
                new User(0, intent.getStringExtra("surname"),
                intent.getStringExtra("lastname"),
                intent.getStringExtra("mail")),
                    ParticipantType.Participant,
                0);

        Button btnToAgenda = findViewById(R.id.btnToAgenda);
        btnToAgenda.setOnClickListener(v -> startActivity(new Intent(Act_CreateMeeting.this, Act_Agenda.class)));

        Button btnToAddParticipant = findViewById(btn_toAddParticipan);
        btnToAddParticipant.setOnClickListener(v -> {
            Intent i = new Intent(Act_CreateMeeting.this, Act_addParticipant.class);
            startActivity(i);
        });



        Button btnCreateMeeting = findViewById(R.id.btn_createMeeting);
        btnCreateMeeting.setOnClickListener(v -> {


            EditText meetingTitle = findViewById(R.id.txtCreateMeetingTitle);
            String mT = meetingTitle.getText().toString();
            EditText startTime = findViewById(R.id.timeCreateMeetingStartTime);
            LocalDateTime sT = LocalDateTime.parse(startTime.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            EditText duration = findViewById(R.id.minCreateMeetingDuration);
            Long dura = Long.parseLong(duration.getText().toString());
            EditText ot = findViewById(R.id.txtCreateMeetingOrt);
            String ort = ot.getText().toString();

            MeetingSettings ms = new MeetingSettings(0, mT, sT, dura, "0", "0");

            ArrayList<AgendaPoint> agendaPoints = new ArrayList<>();
            Agenda agenda = new Agenda(0, agendaPoints);

            ArrayList<Participant> participants = new ArrayList<>();


            Meeting m = new Meeting(0, agenda, ms, participants, MeetingStatus.getMeetingStatus(0), 0, ort);
            String body;
            try {
                body = JSONHelper.MeetingToJSON(m);
                HTTPClient client = new HTTPClient();
                String s = client.postMeeting(POST_URL, body);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

}
