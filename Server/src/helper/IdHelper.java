package helper;

import java.util.Random;

import Datenbank.Manager.DatenbankService;
import model.Meeting;

public class IdHelper {

	public static String getNewModeratorId() {
		return "sdfsdf";
	}

	public static String getNewParticipantId() {
		return "sdfsdf";
	}

	public static long getTechnicalIdByModeratorId(String moderatorId) {

		DatenbankService dbService = DatenbankService.getInstance();
		Meeting meeting = new Meeting();
		try {
			meeting = dbService.getMeeting(moderatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (moderatorId == meeting.getSettings().getModeratorId())
			return meeting.getId();
		else
			return 0;
	}

	public static long getTechnicalIdByParticipantId(String participantId) {
		DatenbankService dbService = DatenbankService.getInstance();
		Meeting meeting = new Meeting();
		try {
			meeting = dbService.getMeeting(participantId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (participantId == meeting.getSettings().getParticipantId())
			return meeting.getId();
		else
			return 0;
	}

	private static long generateRandomId() {

		long id = 0;
		Random r = new Random();

		int i = 100000000;

		while (i > 1) {

			if (i == 100000000) {
				id += (r.nextInt(9) + 1) * i;
			} else {
				id += r.nextInt(10) * i;

			}
			i = i / 10;
		}
		return id;
	}

}
