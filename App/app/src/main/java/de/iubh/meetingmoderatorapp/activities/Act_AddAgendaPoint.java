package de.iubh.meetingmoderatorapp.activities;

public class Act_AddAgendaPoint {    static String GET_URL="http://192.168.178.110:8080/MeetingModeratorServer/Meeting/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_agendapoint);
        AndroidThreeTen.init(this);

        Button btnBackToAgenda = findViewById(R.id.btnToAgenda);
        btnBackToAgenda.setOnClickListener(v -> startActivity(new Intent(Act_AddAgendaPoint.this, Act_Agenda.class)));

        Button btnAddAP = findViewById(R.id.btnAddAgendapoint);
        btnAddAP.setOnClickListener(v -> {
            Intent i = new Intent(Act_AddAgendaPoint.this, Act_Agenda.class);
            String apTitle = findViewById(R.id.txtAgendapointTitle).toString();
            String apNote = findViewById(R.id.txtAgendapointNote).toString();
            long availableTime = Long.parseLong(findViewById(R.id.txtAvailaleTim).toString());

            i.putExtra("apTitle", apTitle);
            i.putExtra("apNote", apNote);
            i.putExtra("availableTime", availableTime);
            startActivity(i);
        });


    }

}
