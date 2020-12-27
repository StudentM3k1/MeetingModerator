package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

import javax.accessibility.AccessibleStateSet;

import helper.MeetingContainerHelper;
import helper.MeetingRunner;
import model.enumerations.*;

public class Meeting {

	private long id;
	private Agenda agenda = new Agenda();
	private MeetingSettings settings = new MeetingSettings();
	private ArrayList<Participant> participants = new ArrayList<Participant>();
	private MeetingStatus meetingStatus = MeetingStatus.Planned;
	private long passedTime;
	private String ort;

	private LocalDateTime lastChange = LocalDateTime.now();
	private Timer runningMeetingManager;
	private AgendaPoint runningAgendaPoint;

	// Nur für interne Benutzung
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
		if (meetingStatus == MeetingStatus.Planned) {
			checkAgenda(newMeeting.getAgenda());
			checkParticipants(newMeeting.getParticipants());
		} else {
			throw new Exception ("Keine Änderungen mehr möglich");
		}
	}

	public void checkAgenda(Agenda newAgenda) throws Exception {

		boolean present = false;

		// Prüfen, ob Agendapunkt entfernt
		for (int old_i = 0; old_i < agenda.getAgendaPoints().size(); old_i++) {
			present = false;
			for (int new_i = 0; new_i < newAgenda.getAgendaPoints().size(); new_i++) {
				if (agenda.getAgendaPoints().get(old_i).equals(newAgenda.getAgendaPoints().get(new_i))) {
					present = true;
					break;
				}
			}

			if (present == false) {
				removeMeetingAgendaPoint(agenda.getAgendaPoints().get(old_i));
			}
		}

		// Prüfen, ob Agendapunkt hinzugefügt
		for (int new_i = 0; new_i < newAgenda.getAgendaPoints().size(); new_i++) {
			present = false;
			for (int old_i = 0; old_i < agenda.getAgendaPoints().size(); old_i++) {

				if (newAgenda.getAgendaPoints().get(new_i).equals(agenda.getAgendaPoints().get(old_i))) {
					present = true;
					break;
				}
			}

			if (present == false) {
				addMeetingAgendaPoint(agenda.getAgendaPoints().get(new_i));
			}
		}
	}

	public void checkParticipants(ArrayList<Participant> newParticipants) throws Exception {

		boolean present = false;

		// Prüfen, ob Teilnehmer hinzugefügt
		for (int new_i = 0; new_i < newParticipants.size(); new_i++) {
			present = false;
			for (int old_i = 0; old_i < participants.size(); old_i++) {

				if (newParticipants.get(new_i).equals(participants.get(old_i))) {
					present = true;
					break;
				}
			}
			if (present == false) {
				addParticipant(newParticipants.get(new_i));
			}
		}
		
		// Prüfen, ob Teilnehmer entfernt
		for (int old_i = 0; old_i < participants.size(); old_i++) {
			present = false;
			for (int new_i = 0; new_i < newParticipants.size(); new_i++) {
				if (participants.get(old_i).equals(newParticipants.get(new_i))) {
					present = true;
					break;
				}
			}
			if (present == false) {
				removeParticipant(participants.get(old_i));
			}
		}
	}

	private void addMeetingAgendaPoint(AgendaPoint agendaPoint) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		agendaPoint.setId(0);
		myContainer.addAddedAgendaPoints(agendaPoint);
	}

	private void removeMeetingAgendaPoint(AgendaPoint agendaPoint) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addRemovedAgendaPoints(agendaPoint);
	}

	private void addParticipant(Participant participant) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		participant.setId(0);
		participant.getUser().setId(0);
		myContainer.addAddedParticipants(participant);
	}

	private void removeParticipant(Participant participant) throws Exception {
		MeetingContainer myContainer = MeetingContainerHelper.identifyMeetingContainer(id);
		myContainer.addRemovedParticipants(participant);
	}

	public void startMeeting() throws Exception {
		if (meetingStatus == MeetingStatus.Planned) {
			setLastChange(LocalDateTime.now());
			nextPoint();
			runningMeetingManager = new Timer();
			runningMeetingManager.schedule(new MeetingRunner(this), 1000, 1000);
			setMeetingStatus(MeetingStatus.Running);
		} else
			throw new Exception("Starten in Meeting Zustand nicht möglich");
	}

	public void MeetingTick() {
		passedTime += 1;
		runningAgendaPoint.setRunningTime(runningAgendaPoint.getRunningTime() + 1);
		try {
			if (runningAgendaPoint.getRunningTime() >= runningAgendaPoint.getAvailableTime())
				nextPoint();
		} catch (Exception e) {
			// Wird im nächsten Durchlauf erneut versucht.
		}
	}

	public void nextPoint() throws Exception {
		runningAgendaPoint = new AgendaPoint();
		setLastChange(LocalDateTime.now());

		// Nach laufendem AgendaPunkt suchen
		for (AgendaPoint agendaPoint : agenda.getAgendaPoints()) {
			if (agendaPoint.getStatus() == AgendaPointStatus.Running) {
				runningAgendaPoint = agendaPoint;
				break;
			}
		}

		// Wenn kein laufender Agendapunkt gefunden, oder alle Sprecher gesprochen haben
		// -> Nächsten Punkt
		if (runningAgendaPoint.getId() == 0 || runningAgendaPoint.getDoneSpeaker().size() == participants.size()) {
			setLastChange(LocalDateTime.now());

			runningAgendaPoint.setStatus(AgendaPointStatus.Done);

			// Nächsten Punkt suchen, der größer ist
			AgendaPoint nextPoint = new AgendaPoint();
			nextPoint.setSort(Long.MAX_VALUE);

			for (AgendaPoint agendaPoint_ : agenda.getAgendaPoints()) {
				if (agendaPoint_.getSort() <= nextPoint.getSort()
						&& agendaPoint_.getStatus() == AgendaPointStatus.Planned) {
					nextPoint = agendaPoint_;
				}
			}
			if (nextPoint.getId() != 0) {
				nextPoint.setStatus(AgendaPointStatus.Running);
				AgendaPoint.setNextSpeaker(participants, nextPoint);
				nextPoint.setAvailableTime(nextPoint.getAvailableTime() / participants.size());
				runningAgendaPoint = nextPoint;
				MeetingContainerHelper.writeToDataBase(MeetingContainerHelper.identifyMeetingContainer(id));
			} else
				closeMeeting();
		} else {
			runningAgendaPoint.setRunningTime(0);
			AgendaPoint.setNextSpeaker(participants, runningAgendaPoint);
		}
	}

	public void closeMeeting() throws Exception {
		runningMeetingManager.cancel();
		runningAgendaPoint.setId(0);
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

	public AgendaPoint getRunningAgendaPoint() {
		return runningAgendaPoint;

	}

}
