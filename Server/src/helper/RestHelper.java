package helper;

import java.time.LocalDateTime;
import Datenbank.Manager.DatenbankService;
import model.*;

public final class RestHelper {

	public static String createMeeting(String json) throws Exception {
		Meeting meeting = JSONHelper.JSONToMeeting(json);

		if (meeting.getSettings().getStartTime().isAfter(LocalDateTime.now())
				&& meeting.getSettings().getMeetingTitle() != null && !meeting.getSettings().getMeetingTitle().isEmpty()
				&& meeting.getSettings().getDuration() > 60 && meeting.getAgenda().getAgendaPoints().size() > 0
				&& meeting.getParticipants().size() > 0) {
			meeting.getSettings().setModeratorId(VerbindungsIdGenerator.createModeratorId());
			meeting.getSettings().setParticipantId(VerbindungsIdGenerator.createUserId());

			DatenbankService dbService = DatenbankService.getInstance();
			long id = 0;
			try {
				id = dbService.addMeeting(meeting);
			} catch (Exception e) {
				throw new Exception("Datenbankzugriff fehlerhaft!");
			}

			Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
			MeetingContainerHelper.releaseMeeting(id);
			return JSONHelper.MeetingToJSON(newMeeting);
		} else {
			throw new Exception("Meeting-Daten fehlerhaft!");
		}
	}

	public static String getMeeting(long id, boolean isModerator) throws Exception {
		Meeting newMeeting;
		try {
			newMeeting = MeetingContainerHelper.getMeeting(id);
			if (isModerator == false)
				newMeeting.getSettings().setModeratorId("0");
		} finally {
			MeetingContainerHelper.releaseMeeting(id);
		}
		if (newMeeting != null) {
			return JSONHelper.MeetingToJSON(newMeeting);
		} else {
			throw new Exception("Meeting kann nicht gefunden werden.");
		}
	}

	public static void setMeeting(long id, String json) throws Exception {
		Meeting newMeeting = JSONHelper.JSONToMeeting(json);
		Meeting meeting = MeetingContainerHelper.getMeeting(id);
		meeting.checkChanges(newMeeting);
		MeetingContainerHelper.forceReload(MeetingContainerHelper.identifyMeetingContainer(id));
		MeetingContainerHelper.releaseMeeting(id);
	}

	public static String getRunningAgendaLastChange(long id) throws Exception {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(id);
		return JSONHelper.LastChangeToJSON(newMeeting.getLastChange());
	}

	public static String getRunningAgenda(long id) throws Exception {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(id);
		return JSONHelper.StateToJSON(newMeeting.getRunningAgendaPoint());
	}

	public static String getSyncTime(long id) throws Exception {

		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(id);
		return JSONHelper.SyncToJSON(newMeeting.getPassedTime());
	}

	public static void startMeeting(long id) throws Exception {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		newMeeting.startMeeting();
	}

	public static void nextAgendaPoint(long id) throws Exception {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		newMeeting.nextPoint();
		MeetingContainerHelper.releaseMeeting(id);
	}
}
