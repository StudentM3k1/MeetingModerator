package helper;

import java.util.Random;

public class IdHelper {

	
	public static String getNewModeratorId() {
		return "123";
	}
	public static String getNewParticipantId() {
		return "123";
	}
	public static long getNewMeetingId() {
		return generateRandomId();
	}
	public static long getNewUserId() {
		return generateRandomId();
	}
	public static long getNewMeetingSettingsId() {
		return generateRandomId();
	}
	public static long getNewAgendaId() {
		return generateRandomId();
	}
	
	public static long getNewAgendaPointId() {
		return generateRandomId();
	}
	

	
	
	public static long getTechnicalIdByModeratorId(long moderatorId) {
		return moderatorId;	
	}
	
	
	public static long getTechnicalIdByParticipantId(long participantId) {
		return participantId;
	}
	
	
	private static long generateRandomId() {
		
		long id = 0;
		Random r = new Random();
		
		int i = 100000000;
		
		while (i > 1) {
		
			if (i == 100000000) {
				id += (r.nextInt(9) + 1) * i;
			}
			else {
				id += r.nextInt(10)  * i;
		
			}
		i = i / 10;
		}
		return id;
	}
	
	
	
}
