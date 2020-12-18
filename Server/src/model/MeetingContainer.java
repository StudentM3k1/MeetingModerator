package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MeetingContainer {
	

	private int currentAccess = 0;
	private Meeting old_meeting;
	private Meeting meeting;
	private LocalDateTime timeStamp;
	private LocalDateTime lastChange;
	
	private ArrayList<AgendaPoint> addedAgendaPoints = new ArrayList<AgendaPoint>();
	private ArrayList<AgendaPoint> removedAgendaPoints = new ArrayList<AgendaPoint>();
	
	private ArrayList<Participant> addedParticipants = new ArrayList<Participant>();
	private ArrayList<Participant> removedParticipants = new ArrayList<Participant>();
		
	
	public MeetingContainer (Meeting meeting) {
		this.setMeeting(meeting);
		this.setOld_meeting(meeting);
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

	public Meeting getOld_meeting() {
		return old_meeting;
	}

	public void setOld_meeting(Meeting old_meeting) {
		this.old_meeting = old_meeting;
	}

	public LocalDateTime getLastChange() {
		return lastChange;
	}

	public void setLastChange(LocalDateTime lastChange) {
		this.lastChange = LocalDateTime.now();
	}

	public ArrayList<AgendaPoint> getAddedAgendaPoint() {
		return addedAgendaPoints;
	}

	public void addAddedAgendaPoints(AgendaPoint addedAgendaPoint) {
		addedAgendaPoints.add(addedAgendaPoint);
	}

	public ArrayList<AgendaPoint> getRemovedAgendaPoints() {
		return removedAgendaPoints;
	}

	public void addRemovedAgendaPoints(AgendaPoint removedAgendaPoint) {
		removedAgendaPoints.add(removedAgendaPoint);
	}

		
	public ArrayList<Participant> getAddedParticipants() {
		return addedParticipants;
	}

	public void addAddedParticipants(Participant addedParticipants) {
		this.addedParticipants.add(addedParticipants);
	}

	public ArrayList<Participant> getRemovedParticipants() {
		return removedParticipants;
	}

	public void addRemovedMeetingSettings(Participant removedParticipants) {
		this.removedParticipants.add(removedParticipants);
	}
	
}
