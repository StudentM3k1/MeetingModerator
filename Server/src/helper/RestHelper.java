package helper;

import java.util.ArrayList;
import model.*;

public final class RestHelper {

	
	
	public static String createMeeting() {		
		Meeting m = new Meeting(new Agenda(), new MeetingSettings(), new ArrayList<Participant>());
		String json = JSONHelper.MeetingToJSON(m);

		return json;//String.valueOf(m.getId());
		
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
