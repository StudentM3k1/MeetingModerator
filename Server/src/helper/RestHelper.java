package helper;

import java.time.LocalDateTime;

import Datenbank.Manager.DatenbankService;
import model.*;

public final class RestHelper {

	public static String createMeeting(String json) {

		Meeting meeting = JSONHelper.JSONToMeeting(json);

		if (meeting.getSettings().getStartTime().isAfter(LocalDateTime.now())
				&& meeting.getSettings().getMeetingTitle() != "" && meeting.getSettings().getDuration() > 60
				&& meeting.getAgenda().getAgendaPoints().size() > 0 && meeting.getParticipants().size() > 0) {
			meeting.getSettings().setParticipantId(IdHelper.getNewParticipantId());
			meeting.getSettings().setParticipantId(IdHelper.getNewModeratorId());
			DatenbankService dbService = DatenbankService.getInstance();
			long id = 0;
			try {

				dbService.CreateDatabase();

				id = dbService.addMeeting(meeting);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
			MeetingContainerHelper.releaseMeeting(newMeeting.getId());
			return JSONHelper.MeetingToJSON(newMeeting);
		} else
			return "";

	}

	public static String getMeeting(long id) {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
		return JSONHelper.MeetingToJSON(newMeeting);
	}

	public static void setMeeting(long id, String json) {
		Meeting newMeeting = JSONHelper.JSONToMeeting(json);
		Meeting meeting = MeetingContainerHelper.getMeeting(id);
		meeting.checkChanges(newMeeting);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
	}

	public static String getRunningAgendaLastChange(long id) {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
		return JSONHelper.LastChangeToJSON(newMeeting.getLastChange());
	}

	public static String getRunningAgenda(long id) {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
		return JSONHelper.StateToJSON(newMeeting.getRunningPoint());
	}

	public static String getSyncTime(long id) {

		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
		return JSONHelper.SyncToJSON(newMeeting.getPassedTime());
	}

	public static void startMeeting(long id) {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		newMeeting.startMeeting();
	}

	public static void nextAgendaPoint(long id) {
		Meeting newMeeting = MeetingContainerHelper.getMeeting(id);
		newMeeting.nextPoint();
		MeetingContainerHelper.releaseMeeting(newMeeting.getId());
	}
}
