package model;


public class MeetingContainer {
	

	private int currentAccess = 0;
	private Meeting meeting;
	
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
}
