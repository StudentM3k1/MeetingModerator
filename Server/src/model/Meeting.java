package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

import helper.MeetingContainerHelper;
import helper.MeetingRunner;
import model.enumerations.*;

public class Meeting {

	private long id = 0;
	private Agenda agenda = new Agenda();
	private MeetingSettings settings = new MeetingSettings();
	private ArrayList<Participant> participants = new ArrayList<Participant>();
	private MeetingStatus meetingStatus = MeetingStatus.Planned;
	private long passedTime = 0;
	private String ort;

	private LocalDateTime lastChange = LocalDateTime.now();
	private Timer runningMeetingManager;
	private AgendaPoint runningAgendaPoint;

	// Nur f�r interne Benutzung
	public Meeting() {

	}

	public Meeting(long id, Agenda agenda, MeetingSettings settings, ArrayList<Participant> participants,
			MeetingStatus meetingStatus, String ort) {
		this.id = id;
		this.agenda = agenda;
		this.settings = settings;
		this.participants = participants;
		this.meetingStatus = meetingStatus;
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

	public void checkChanges(Meeting newMeeting) throws Exception {
		checkAgenda(newMeeting.getAgenda());
		checkParticipants(newMeeting.getParticipants());
	}

	public void checkAgenda(Agenda newAgenda) throws Exception {
		for (int i = 0; i < agenda.getAgendaPoints().size(); i++) {
			if (!newAgenda.getAgendaPoints().contains(agenda.getAgendaPoints().get(i)))
				removeMeetingAgendaPoint(agenda.getAgendaPoints().get(i));
		}

		for (int i = 0; i < newAgenda.getAgendaPoints().size(); i++) {
			if (!agenda.getAgendaPoints().contains(newAgenda.getAgendaPoints().get(i)))
				addMeetingAgendaPoint(newAgenda.getAgendaPoints().get(i));
		}
	}

	public void checkParticipants(ArrayList<Participant> newParticipants) throws Exception {

		for (int i = 0; i < participants.size(); i++) {
			if (!newParticipants.contains(participants.get(i)))
				removeParticipant(participants.get(i));
		}

		for (int i = 0; i < newParticipants.size(); i++) {
			if (!participants.contains(newParticipants.get(i)))
				addParticipant(newParticipants.get(i));
		}
	}

	private void addMeetingAgendaPoint(AgendaPoint agendaPoint) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addAddedAgendaPoints(agendaPoint);
		agenda.getAgendaPoints().add(agendaPoint);
	}

	private void removeMeetingAgendaPoint(AgendaPoint agendaPoint) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addRemovedAgendaPoints(agendaPoint);
		agenda.getAgendaPoints().remove(agendaPoint);
	}

	private void addParticipant(Participant participant) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addAddedParticipants(participant);
		participants.add(participant);
	}

	private void removeParticipant(Participant participant) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addRemovedParticipants(participant);
		participants.remove(participant);
	}

	public void startMeeting() throws Exception {
		setMeetingStatus(MeetingStatus.Running);
		setLastChange(LocalDateTime.now());	
		runningMeetingManager = new Timer();
		runningMeetingManager.schedule(new MeetingRunner(this), 1000,1000);
	}
	
	public void  MeetingTick()
	{
		passedTime += 1;
		runningAgendaPoint.setRunningTime(runningAgendaPoint.getRunningTime() + 1 );
		try
		{
			if (runningAgendaPoint.getRunningTime() >= runningAgendaPoint.getAvailableTime()) nextPoint();
		}
		catch (Exception e)
		{
			// Wird im n�chsten Durchlauf erneut versucht.
		}
	}

	public AgendaPoint nextPoint() throws Exception {
		runningAgendaPoint = new AgendaPoint();
		for (AgendaPoint agendaPoint : agenda.getAgendaPoints()) {
			if (agendaPoint.getStatus() == AgendaPointStatus.Running) {
				runningAgendaPoint = agendaPoint;
				break;
			}
		}

		if (runningAgendaPoint.getId() == 0 || runningAgendaPoint.getDoneSpeaker().size() + 1 == participants.size()) {
			setLastChange(LocalDateTime.now());
			AgendaPoint nextPoint = new AgendaPoint();
			runningAgendaPoint.setStatus(AgendaPointStatus.Done);

			// N�chsten Punkt suchen, der gr��er ist
			AgendaPoint next_Point = new AgendaPoint();
			;
			for (AgendaPoint agendaPoint_ : agenda.getAgendaPoints()) {
				if (agendaPoint_.getSort() <= nextPoint.getSort())
					next_Point = agendaPoint_;
			}
			if (nextPoint.getId() != 0) {
				nextPoint.setStatus(AgendaPointStatus.Running);
				AgendaPoint.setNextSpeaker(participants, nextPoint);			
				MeetingContainerHelper.writeToDataBase(MeetingContainerHelper.identifyMeetingContainer(id));
				return nextPoint;
			}
			closeMeeting();
			return null;
		}
		else
		{
			runningAgendaPoint.setRunningTime(0);
			AgendaPoint.setNextSpeaker(participants, runningAgendaPoint);	
			return runningAgendaPoint;
		}	
	}

	public void closeMeeting() throws Exception {
		runningMeetingManager.cancel();
		setMeetingStatus(MeetingStatus.Done);
		setLastChange(LocalDateTime.now());
		MeetingContainerHelper.writeToDataBase(MeetingContainerHelper.identifyMeetingContainer(id));
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

	public LocalDateTime getLastChange() {
		return lastChange;
	}

	public void setLastChange(LocalDateTime lastChange) {
		this.lastChange = lastChange;
	}
	
	public AgendaPoint getRunningAgendaPoint()
	{
		return runningAgendaPoint;
		
	}

}
