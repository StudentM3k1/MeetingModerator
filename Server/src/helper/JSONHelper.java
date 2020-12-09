package helper;

import java.util.ArrayList;

import model.Agenda;
import model.Meeting;
import model.MeetingSettings;
import model.Participant;

public class JSONHelper {

	
	public static String MeetingToJSON(Meeting meeting) {
		
		return "asd";
	}
	
	public static Meeting JSONToMeeting(String json) {
		
		return new Meeting(new Agenda(), new MeetingSettings(), new ArrayList<Participant>());
	}
	
	
	
}
