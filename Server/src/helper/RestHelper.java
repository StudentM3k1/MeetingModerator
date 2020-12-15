package helper;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONObject;

import model.*;
import model.enumerations.AgendaPointStatus;
import model.enumerations.MeetingStatus;
import model.enumerations.ParticipantType;

public final class RestHelper {

	
	
	public static String createMeeting(String json) {		


		Meeting test = JSONHelper.JSONToMeeting(json);

		MeetingSettings s = new MeetingSettings(9876,"Das Meeting",new Date(987787787), 12, "123456789", "987654321");
		User u = new User(1234,"Uwe","Dietrich","mail@mail.com");
		Participant p = new Participant(1231234, u, ParticipantType.Moderator, 0);
		ArrayList<Participant> participants = new ArrayList<Participant>();
		participants.add(p);
	
		AgendaPoint ap = new AgendaPoint(234234,"Erster Punkt",p,"Labern",600,AgendaPointStatus.Planned);
		ArrayList<AgendaPoint> apl = new ArrayList<AgendaPoint>();
		apl.add(ap);
		Agenda a = new Agenda(12123, apl);
				
		Meeting m = new Meeting(1234, a, s, participants, MeetingStatus.Planned, 0);

				
		
		//String jsons = JSONHelper.MeetingToJSON(m);
		
		
		String jsons = JSONHelper.MeetingToJSON(test);
		return jsons;//String.valueOf(m.getId());
		
	}
	
	public static String getMeeting(long id) {
		return "";
	}
	
	
	public static void setMeeting(long id) {

		String json ="";
		
		Meeting m = JSONHelper.JSONToMeeting(json);
		
		
	}
	
	
	public static String getRunningAgendaLastChange(long id) {
		return "";
		
		
	}
	
	
	public static String getRunningAgenda(long id) {
		return "";
	}
	
	
	
	
	
	public static String getSyncTime (long id) {
		return "";
		
		
		
	}
	
	
	public static void startMeeting (long id) {

		
		
		
	}
	

	public static void nextAgendaMeeting (long id) {

		
		
		
		
		
	}	
}
