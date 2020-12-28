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
	private static ArrayList<MeetingContainer> meetings = new ArrayList<MeetingContainer>();
	private static Timer garbageTimer;
	private static DatenbankService dbService;

	public static MeetingContainer identifyMeetingContainer(long id) throws Exception {
		for (MeetingContainer meetingContainer : meetings) {
			if (meetingContainer.getMeeting().getId() == id) {
				return meetingContainer;
			}
		}
		return null;
	}

	public static Meeting getMeeting(long id) throws Exception {
		if (garbageTimer == null) {
			garbageTimer = new Timer();
			garbageTimer.schedule(new MeetingGarbageCollector(), 900000, 900000);
		}

		for (MeetingContainer meetingContainer : meetings) {
			if (meetingContainer.getMeeting().getId() == id) {
				meetingContainer.increaseCurrentAccess();
				meetingContainer.setTimeStamp();
				meetings.add(meetingContainer);
				return meetingContainer.getMeeting();
			}
		}
		if (dbService == null)
			dbService = DatenbankService.getInstance();

		MeetingContainer newMeetingContainer = new MeetingContainer(dbService.getMeeting(id));
		newMeetingContainer.increaseCurrentAccess();
		newMeetingContainer.setTimeStamp();
		meetings.add(newMeetingContainer);
		return newMeetingContainer.getMeeting();
	}

	public static void releaseMeeting(long id) throws Exception {
		MeetingContainer matchingMeeting = null;
		for (int i = 0; i < meetings.size(); i++) {
			if (meetings.get(i).getMeeting().getId() == id) {
				matchingMeeting = meetings.get(i);
				matchingMeeting.decreaseCurrentAccess();
				if (matchingMeeting.getMeeting().getMeetingStatus() != MeetingStatus.Running
						&& matchingMeeting.getCurrentAccess() == 0) {
					writeToDataBase(matchingMeeting);
					meetings.remove(i);
				}
				break;
			}
		}
	}

	public static void garbageCollect() throws Exception {
		for (MeetingContainer meeting : meetings) {
			if (meeting.getMeeting().getMeetingStatus() != MeetingStatus.Running
					&& meeting.getTimeStamp().isBefore(LocalDateTime.now().minusMinutes(15))) {
				releaseMeeting(meeting.getMeeting().getId());
			}
		}
	}

	public static void writeToDataBase(MeetingContainer meetingContainer) throws Exception {
		if (dbService == null) {
			dbService = DatenbankService.getInstance();
		}

		dbService.saveTeilnehmer(meetingContainer.getAddedParticipants(), 
				meetingContainer.getRemovedParticipants(), meetingContainer.getMeeting().getId());
		dbService.saveAgenda(meetingContainer.getAddedAgendaPoint(),
				meetingContainer.getRemovedAgendaPoints(), meetingContainer.getMeeting().getId());
		dbService.setMeetingStatus(meetingContainer.getMeeting().getId(),
				meetingContainer.getMeeting().getMeetingStatus());
		for (AgendaPoint agendaPoint : meetingContainer.getMeeting().getAgenda().getAgendaPoints()) {
			dbService.setAgendaStatus(agendaPoint.getId(), agendaPoint.getStatus());
		}
		
		meetingContainer.clearLists();
	}
	
	public static void forceReload(MeetingContainer meetingContainer) throws Exception {
		if (dbService == null) {
			dbService = DatenbankService.getInstance();
		}

		writeToDataBase(meetingContainer);
 		meetingContainer.setMeeting(dbService.getMeeting(meetingContainer.getMeeting().getId()));
	}
}
