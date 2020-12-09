package model;

import java.util.ArrayList;

import helper.IdHelper;
import model.enumerations.*;

public class Meeting {

	
	
	private long id;
	private Agenda agenda;
	private MeetingSettings settings;
	private ArrayList<Participant> participants;
	private MeetingStatus meetingStatus;
	
	
	public Meeting(Agenda agenda, MeetingSettings settings, ArrayList<Participant> participants)
	{
		this.id = IdHelper.getNewMeetingId();
		this.agenda = agenda;
		this.settings = settings;
		this.participants = participants;
		this.meetingStatus = MeetingStatus.Planned;
	}
	
	
	public MeetingStatus getMeetingStatus() {
		return meetingStatus;
	}

	
	public ArrayList<Participant> getParticipants() {
		return participants;
	}
	
	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}
	
	public MeetingSettings getSettings() {
		return settings;
	}
	
	public void setSettings(MeetingSettings settings) {
		this.settings = settings;
	}
	
	public Agenda getAgenda() {
		return agenda;
	}
	
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	public long getId() {
		return id;
	}
		
	private void changeMeetingAgenda() {
		
	}
	private void changeMeetingSettings() {
	
	}
	private void startMeeting() {
		
	}
	private void nextPoint() {
		
		
	}
	private void closeMeeting() {
		
	}
	
	
	private void joinMeeting() {
		
	}
	
	
	private void addParticipant() {
		
	}
	
	
	private void removeParticipant() {
		
	}

}
