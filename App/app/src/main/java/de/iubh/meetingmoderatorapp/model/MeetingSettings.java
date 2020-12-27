package de.iubh.meetingmoderatorapp.model;

import org.threeten.bp.LocalDateTime;

public class MeetingSettings {
	private String meetingTitle = new String();
	private LocalDateTime startTime = LocalDateTime.now();
	private long duration = 0;
	private String moderatorId = new String();
	private String participantId = new String();

	// Nur für interne Benutzung
	public MeetingSettings() {	}

	public MeetingSettings( String meetingTitle, LocalDateTime startTime, long duration, String moderatorId,
							String participantId) {
		this.meetingTitle = meetingTitle;
		this.startTime = startTime;
		this.duration = duration;
		this.moderatorId = moderatorId;
		this.participantId = participantId;
	}


	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
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
