package model;

import java.util.ArrayList;

public class MeetingContainerList {
	private static ArrayList<MeetingContainer> meetings;
		
	public static Meeting getMeeting(long id) {

		MeetingContainer matchingMeeting = new MeetingContainer();

		for (MeetingContainer m : meetings ) {
			if (m.Meeting.getId() == id) {
				matchingMeeting.Meeting = m.Meeting;
				break;
			}
		}
		
		if (matchingMeeting.Meeting == null) {
			
			// aus Datenbank holen
			
			
		}
		
		matchingMeeting.CurrentAccess ++;
		return matchingMeeting.Meeting;		
	}

	
	
	public static void releaseMeeting(long id) {
		
		MeetingContainer matchingMeeting = null;

		for (MeetingContainer m : meetings ) {
			if (m.Meeting.getId() == id) {
				matchingMeeting = m;
				
				matchingMeeting.CurrentAccess --;
				// matchingMeeting.Meeting in DB schreiben
				
				
				break;
			}
		}	
	}	
}
