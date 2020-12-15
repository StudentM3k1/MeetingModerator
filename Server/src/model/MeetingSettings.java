package model;

import java.util.Date;

public class MeetingSettings {
	private long id;
	private String meetingTitle;
	private Date startTime;
	private long duration;
	private String moderatorId;
	private String participantId;

// Nur für interne Benutzung
	public MeetingSettings() {

	}

	public MeetingSettings(long id, String meetingTitle, Date startTime, long duration, String moderatorId,
			String participantId) {
		this.id = id;
		this.meetingTitle = meetingTitle;
		this.startTime = startTime;
		this.duration = duration;
		this.moderatorId = moderatorId;
		this.participantId = participantId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

}
