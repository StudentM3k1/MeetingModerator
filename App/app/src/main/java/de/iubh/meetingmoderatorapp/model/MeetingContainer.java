package model;

import java.time.LocalDateTime;

public class MeetingContainer {
	

	private int currentAccess = 0;
	private Meeting meeting;
	private LocalDateTime timeStamp;
	
	public MeetingContainer (Meeting meeting) {
		this.setMeeting(meeting);
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public int getCurrentAccess() {
		return currentAccess;
	}
	
	public void increaseCurrentAccess() {
		this.currentAccess++;
	}

	public void decreaseCurrentAccess() {
		this.currentAccess--;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp() {
		this.timeStamp = LocalDateTime.now();
	}
	
}
