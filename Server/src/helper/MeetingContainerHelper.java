package helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Datenbank.Manager.DatenbankService;
import model.Meeting;
import model.MeetingContainer;
import model.enumerations.MeetingStatus;

public class MeetingContainerHelper {
	private static ArrayList<MeetingContainer> meetings;
	private static Timer garbageTimer;
	private static DatenbankService dbService;

	public static Meeting getMeeting(long id) {
		if (garbageTimer == null) {
			garbageTimer = new Timer();
			garbageTimer.schedule(new MeetingGarbageCollector(), 15000, 15000);
		}

		for (MeetingContainer meetingContainer : meetings) {
			if (meetingContainer.getMeeting().getId() == id) {
				meetingContainer.increaseCurrentAccess();
				meetingContainer.setTimeStamp();
				return meetingContainer.getMeeting();
			}
		}

		if (dbService == null)
			dbService = DatenbankService.getInstance();
		Meeting newMeeting = null;

		try {
			newMeeting = dbService.getMeeting(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MeetingContainer newMeetingContainer = new MeetingContainer(newMeeting);

		newMeetingContainer.increaseCurrentAccess();
		newMeetingContainer.setTimeStamp();
		return newMeetingContainer.getMeeting();
	}

	public static void releaseMeeting(long id) {
		MeetingContainer matchingMeeting = null;

		for (MeetingContainer m : meetings) {
			if (m.getMeeting().getId() == id) {
				matchingMeeting = m;
				matchingMeeting.decreaseCurrentAccess();

				if (matchingMeeting.getMeeting().getMeetingStatus() != MeetingStatus.Running) {
					if (dbService == null)
						dbService = DatenbankService.getInstance();

// TODO DB SPEICHERN

				}
				break;
			}
		}
	}

	public static void garbageCollect() {
		for (MeetingContainer meeting : meetings) {
			if (meeting.getMeeting().getMeetingStatus() != MeetingStatus.Running
					&& meeting.getTimeStamp().isBefore(LocalDateTime.now().minusMinutes(15))) {
				releaseMeeting(meeting.getMeeting().getId());
			}
		}
	}
}
