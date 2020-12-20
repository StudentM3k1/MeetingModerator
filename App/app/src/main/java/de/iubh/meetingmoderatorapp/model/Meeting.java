package model;

import java.util.ArrayList;
import model.enumerations.*;

public class Meeting {

    private long id = 0;
    private Agenda agenda = new Agenda();
    private MeetingSettings settings = new MeetingSettings();
	private ArrayList<Participant> participants = new ArrayList<Participant>();
    private MeetingStatus meetingStatus = MeetingStatus.Planned;
    private long passedTime = 0;
    private String ort;

	// Nur f�r interne Benutzung
	public Meeting()
	{

    }
	
	
	public Meeting(long id, Agenda agenda, MeetingSettings settings, ArrayList<Participant> participants, MeetingStatus meetingStatus, long passedTime, String ort)
	{
		this.id = id;
		this.agenda = agenda;
		this.settings = settings;
		this.participants = participants;
		this.meetingStatus = meetingStatus;
		this.passedTime = passedTime;
		this.ort = ort;
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

    public void setId(long id) {
        this.id = id;
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

    public long getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(long passedTime) {
        this.passedTime = passedTime;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setMeetingStatus(MeetingStatus meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

}