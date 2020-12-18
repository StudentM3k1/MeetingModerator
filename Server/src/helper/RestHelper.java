package helper;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONObject;

import Datenbank.Manager.DatenbankService;
import model.*;
import model.enumerations.AgendaPointStatus;
import model.enumerations.MeetingStatus;
import model.enumerations.ParticipantType;

public final class RestHelper {

	private static DatenbankService dbService;
	
	public static String createMeeting(String json) {
		
		
		Meeting m = new Meeting();
		
		return JSONHelper.MeetingToJSON(m);

	}
	
	public static String getMeeting(long id) {
		
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
		
		return JSONHelper.MeetingToJSON(newMeeting);
	}
	
	
	public static void setMeeting(long id, String json) {


		
		//Meeting m = JSONHelper.JSONToMeeting(json);
		
		
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
