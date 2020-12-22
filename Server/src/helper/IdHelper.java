package helper;

import java.util.Random;

import Datenbank.Manager.DatenbankService;
import model.Meeting;

public class IdHelper {

	public static long getTechnicalIdByModeratorId(String moderatorId) {

		DatenbankService dbService = DatenbankService.getInstance();
		Meeting meeting = new Meeting();
		try {
			meeting = dbService.getMeeting(moderatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (moderatorId.equals(meeting.getSettings().getModeratorId()))
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

		if (participantId.equals(meeting.getSettings().getParticipantId()))
			return meeting.getId();
		else
			return 0;
	}
}
