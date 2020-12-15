package helper;

import java.util.ArrayList;
import org.json.*;
import model.*;
import model.enumerations.*;

public class JSONHelper {

	public static String MeetingToJSON(Meeting meeting) {

		JSONObject jsonobj = new JSONObject();

		jsonobj.put("id", meeting.getId());
		jsonobj.put("passedTime", meeting.getPassedTime());
		jsonobj.put("status", MeetingStatus.getInt(meeting.getMeetingStatus()));

		JSONObject meetingSettings = new JSONObject();
		meetingSettings.put("id", meeting.getSettings().getId());
		meetingSettings.put("meetingTitle", meeting.getSettings().getMeetingTitle());
		meetingSettings.put("startTime", meeting.getSettings().getStartTime());
		meetingSettings.put("duration", meeting.getSettings().getDuration());
		meetingSettings.put("moderatorId", meeting.getSettings().getModeratorId());
		meetingSettings.put("participantId", meeting.getSettings().getParticipantId());
		jsonobj.put("meetingSettings", meetingSettings);

		
		
		
		
		
		
	//	JSONObject agenda = new JSONObject();
	//	agenda.put("id", meeting.getAgenda().getId());

		// for (AgendaPoint agendaPoint : meeting.getAgenda().getAgendaPoints()) {

		// JSONObject agendaPointJSON = new JSONObject();

		// agendaPointJSON.put("title", agendaPoint.getTitle());
		//
		// agendaPointJSON.put("responsible",);
		// agendaPointJSON.put("note",);
		// // agendaPointJSON.put("avaibleTime",);
		//
		// }

		return jsonobj.toString();
	}

	public static Meeting JSONToMeeting(String json) {

		Meeting m = new Meeting();
		Agenda a = new Agenda();
		ArrayList<Participant> p = new ArrayList<Participant>();
		MeetingSettings s = new MeetingSettings();

		try {
			JSONObject jsonobj = new JSONObject(json);

			m.setPassedTime(jsonobj.getLong("PassedTime"));

			JSONArray agenda_arr = jsonobj.getJSONArray("Agenda");

			for (int i = 0; i < agenda_arr.length(); i++) {
				AgendaPoint ap = new AgendaPoint();
				JSONObject agenda = agenda_arr.getJSONObject(i);
				ap.setTitle(agenda.getString("title"));

				// ap.setResponsible(agenda.getString("responsible"));

				ap.setNote(agenda.getString("note"));
				ap.setAvailableTime(agenda.getLong("avaibleTime"));

			}
		} catch (JSONException jsonex) {

			return m;
		}
		return m;
	}
}
