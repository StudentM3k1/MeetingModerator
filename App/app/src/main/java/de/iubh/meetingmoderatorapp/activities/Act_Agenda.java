package de.iubh.meetingmoderatorapp.activities;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.iubh.meetingmoderatorapp.controller.AgendaPointAdapter;
import de.iubh.meetingmoderatorapp.controller.JSONHelper;
import de.iubh.meetingmoderatorapp.controller.TeilnehmerAdapter;
import de.iubh.meetingmoderatorapp.model.Meeting;

public class Act_Agenda {    private RecyclerView recyAP;
    private RecyclerView.Adapter apAdapter;
    private RecyclerView.LayoutManager apLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_see_agenda);
        AndroidThreeTen.init(this);


        // Aufbau RecyView
        Bundle extras = getIntent().getExtras();
        String EinwahlJson = extras.getString("JSON");

        Meeting m = JSONHelper.JSONToMeeting(EinwahlJson);

        recyAP = findViewById(R.id.recyAP);
        recyAP.setHasFixedSize(true);
        apLayoutManger = new LinearLayoutManager(this);
        apAdapter = new AgendaPointAdapter(m.getAgenda().getAgendaPoints());

        recyAP.setLayoutManager(apLayoutManger);
        recyAP.setAdapter(apAdapter);

        // Button zu Activitz AddAgendapoint
        Button btnAddAgendapoint = findViewById(R.id.btnAddAgendapoint);
        btnAddAgendapoint.setOnClickListener(v -> {
            Intent i = new Intent(Act_Agenda.this, Act_AddAgendaPoint.class);

            startActivity(i);


        });

    }

}
