package helper;

import java.util.ArrayList;

import model.Meeting;
import model.MeetingContainer;

public class MeetingContainerHelper {
	private static ArrayList<MeetingContainer> meetings;
		
	public static void getMeeting(long id) { // Meeting


		for (MeetingContainer meetingContainer : meetings ) {
			if (meetingContainer.getMeeting().getId() == id) {			
				meetingContainer.increaseCurrentAccess();
				//return meetingContainer;
			}
		}
		
		// Aus DB holen
		
		
	

		
	//	matchingMeeting.CurrentAccess ++;
//		return matchingMeeting.Meeting;		
	}

	
	
	public static void releaseMeeting(long id) {
		
		MeetingContainer matchingMeeting = null;

		for (MeetingContainer m : meetings ) {
			if (m.getMeeting().getId() == id) {
				matchingMeeting = m;
				
				matchingMeeting.decreaseCurrentAccess();
				// matchingMeeting.Meeting in DB schreiben
				
				
				break;
			}
		}	
	}	
}
