package helper;

import java.util.ArrayList;
import java.util.Date;

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

		
		
		JSONObject agenda = new JSONObject();	
		agenda.put("id", meeting.getAgenda().getId());
		JSONArray agenda_points = new JSONArray();
		for (AgendaPoint agendaPoint : meeting.getAgenda().getAgendaPoints()) {
			JSONObject agendaPoint_json = new JSONObject();
			agendaPoint_json.put("id", agendaPoint.getId());
			agendaPoint_json.put("title", agendaPoint.getTitle());
			agendaPoint_json.put("note", agendaPoint.getNote());
			agendaPoint_json.put("availableTime", agendaPoint.getAvailableTime());
			agendaPoint_json.put("status", AgendaPointStatus.getInt(agendaPoint.getStatus()));
			agenda_points.put(agendaPoint_json);
		}
		agenda.put("agendaPoints", agenda_points);
		jsonobj.put("agenda", agenda);
		
		JSONArray participants = new JSONArray();
		for (Participant participant : meeting.getParticipants()) {	
			JSONObject participant_json = new JSONObject();
			JSONObject user = new JSONObject();
			user.put("id",participant.getUser().getId());
			user.put("firstname",participant.getUser().getFirstname());
			user.put("lastname",participant.getUser().getLastname());
			user.put("mail",participant.getUser().getMail());
			participant_json.put("user", user);
			participant_json.put("id", participant.getId());
			participant_json.put("type", ParticipantType.getInt(participant.getType()));
			participant_json.put("usedTime", participant.getUsedTime());	
			participants.put(participant_json);	
		}
		jsonobj.put("participants", participants);

		return jsonobj.toString();
	}

	public static Meeting JSONToMeeting(String json) {

		Meeting meeting = new Meeting();
		MeetingSettings settings = new MeetingSettings();
		
		
		Agenda agenda = new Agenda();
		ArrayList<Participant> p = new ArrayList<Participant>();


		try {
			
			JSONObject json_obj = new JSONObject(json);

			meeting.setPassedTime(json_obj.getLong("passedTime"));
			meeting.setId(json_obj.getLong("id"));	
			meeting.setMeetingStatus(MeetingStatus.getMeetingStatus(json_obj.getInt("status")));
			
					
			JSONObject json_settings = json_obj.getJSONObject("meetingSettings");
			settings.setId(json_settings.getLong("id"));
			settings.setDuration(json_settings.getLong("duration"));
			settings.setParticipantId(json_settings.getString("participantId"));
			settings.setModeratorId(json_settings.getString("moderatorId"));	
			settings.setMeetingTitle(json_settings.getString("meetingTitle"));
			
			
			JSONObject json_agenda = json_obj.getJSONObject("agenda");
			agenda.setId(json_agenda.getLong("id"));
			JSONArray json_agendaPoints = json_agenda.getJSONArray("agendaPoints");
			for (int i = 0; i < json_agendaPoints.length(); i++) {
				AgendaPoint agendaPoint = new AgendaPoint();
				JSONObject json_agendaPoint = json_agendaPoints.getJSONObject(i);			
				agendaPoint.setId(json_agendaPoint.getLong("id"));
				agendaPoint.setTitle(json_agendaPoint.getString("title"));
				agendaPoint.setNote(json_agendaPoint.getString("note"));
				agendaPoint.setAvailableTime(json_agendaPoint.getLong("avaibleTime"));
				agendaPoint.setStatus(AgendaPointStatus.getAgendaPointStatus(json_agendaPoint.getInt("status")));
				agenda.getAgendaPoints().add(agendaPoint);
			}
			
			JSONArray json_participants = json_agenda.getJSONArray("participants");
		
			
			for (int i = 0; i < json_participants.length(); i++) {
				User user = new User();
				Participant participant = new Participant();
								
				JSONObject json_participant = json_participants.getJSONObject(i);
				participant.setId(json_participant.getLong("id"));
				participant.setType(ParticipantType.getParticipantType(json_participant.getInt("type")));
				participant.setUsedTime(json_participant.getLong("usedTime"));
			
				JSONObject json_user = json_participant.getJSONObject("user");
				user.setFirstname(json_user.getString("firstname"));
				user.setId(json_user.getLong("id"));
				user.setLastname(json_user.getString("lastname"));
				user.setMail(json_user.getString("mail"));
				
				participant.setUser(user);
				
				meeting.getParticipants().add(participant);
			}
			
		} catch (JSONException jsonex) {

			return meeting;
		}
		
		
		
		
		return meeting;
	}
}
