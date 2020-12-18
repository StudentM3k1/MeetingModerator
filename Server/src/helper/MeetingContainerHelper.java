package helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Datenbank.Manager.DatenbankService;
import model.AgendaPoint;
import model.Meeting;
import model.MeetingContainer;
import model.Participant;
import model.enumerations.MeetingStatus;

public class MeetingContainerHelper {
	private static ArrayList<MeetingContainer> meetings;
	private static Timer garbageTimer;
	private static DatenbankService dbService;

	public static MeetingContainer identifyMeetingContainer(long id) {
		for (MeetingContainer meetingContainer : meetings) {
			if (meetingContainer.getMeeting().getId() == id) {
				return meetingContainer;
			}
		}
		return null;
	}

	public static Meeting getMeeting(long id) {
		if (garbageTimer == null) {
			garbageTimer = new Timer();
			garbageTimer.schedule(new MeetingGarbageCollector(), 900000, 900000);
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
			e.printStackTrace();
		}

		MeetingContainer newMeetingContainer = new MeetingContainer(newMeeting);

		newMeetingContainer.increaseCurrentAccess();
		newMeetingContainer.setTimeStamp();
		return newMeetingContainer.getMeeting();
	}

	public static void releaseMeeting(long id) {
		MeetingContainer matchingMeeting = null;
		for (int i = 0; i < meetings.size(); i++) {
			if (meetings.get(i).getMeeting().getId() == id) {
				matchingMeeting = meetings.get(i);
				matchingMeeting.decreaseCurrentAccess();
				if (matchingMeeting.getMeeting().getMeetingStatus() != MeetingStatus.Running) {
					writeToDataBase(matchingMeeting);
					meetings.remove(i);
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

	private static void writeToDataBase(MeetingContainer meetingContainer) {
		if (dbService == null)
			dbService = DatenbankService.getInstance();

		try {
			dbService.saveTeilnehmer(meetingContainer.getAddedParticipants(), new ArrayList<Participant>(),
					meetingContainer.getRemovedParticipants(), meetingContainer.getMeeting().getId());
			dbService.saveAgenda(meetingContainer.getAddedAgendaPoint(), new ArrayList<AgendaPoint>(),
					meetingContainer.getRemovedAgendaPoints(), meetingContainer.getMeeting().getId());
			dbService.setMeetingStatus(meetingContainer.getMeeting().getId(),
					meetingContainer.getMeeting().getMeetingStatus());
			for (AgendaPoint agendaPoint : meetingContainer.getMeeting().getAgenda().getAgendaPoints()) {
				dbService.setAgendaStatus(agendaPoint.getId(), agendaPoint.getStatus());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
