package helper;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONObject;

import model.*;
import model.enumerations.MeetingStatus;

public final class RestHelper {

	
	
	public static String createMeeting(String json) {		

		MeetingSettings s = new MeetingSettings(9876,"Das Meeting",new Date(987787787), 12, "123456789", "987654321");
		Meeting m = new Meeting(1234, new Agenda(), s, new ArrayList<Participant>(), MeetingStatus.Planned, 0);

		String jsons = JSONHelper.MeetingToJSON(m);

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
