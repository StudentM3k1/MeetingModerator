package model;

import java.util.ArrayList;

import model.enumerations.AgendaPointStatus;

public class AgendaPoint {

	private long id;
	private String title = new String();
	private String note = new String();
	private long availableTime;
	private AgendaPointStatus status = AgendaPointStatus.Planned;
	private long sort;

	private Participant actualSpeaker = new Participant();
	private ArrayList<Participant> doneSpeaker = new ArrayList<Participant>();
	private long runningTime;

	// Nur für interne Benutzung
	public AgendaPoint() {

	}

	public AgendaPoint(long id, String title, String note, long availableTime, AgendaPointStatus status, long sort) {

		this.id = id;
		this.title = title;
		this.note = note;
		this.setAvailableTime(availableTime);
		this.status = status;
		this.setSort(sort);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AgendaPointStatus getStatus() {
		return status;
	}

	public void setStatus(AgendaPointStatus status) {
		this.status = status;
	}

	public long getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(long availableTime) {
		this.availableTime = availableTime;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public Participant getActualSpeaker() {
		return actualSpeaker;
	}

	public void setActualSpeaker(Participant actualSpeaker) {
		this.actualSpeaker = actualSpeaker;
	}

	public ArrayList<Participant> getDoneSpeaker() {
		return doneSpeaker;
	}

	public void setDoneSpeaker(ArrayList<Participant> doneSpeaker) {
		this.doneSpeaker = doneSpeaker;
	}

	public boolean equals(AgendaPoint agendaPoint) {
		if (this.id == agendaPoint.getId() && this.title.equals(agendaPoint.getTitle())
				&& this.note.equals(agendaPoint.getNote()) && this.availableTime == agendaPoint.getAvailableTime()
				&& this.status == agendaPoint.getStatus() && this.sort == agendaPoint.getSort()) {
			return true;
		} else {
			return false;
		}
	}

	public long getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(long runningTime) {
		this.runningTime = runningTime;
	}

	public static Participant setNextSpeaker(ArrayList<Participant> all_participants, AgendaPoint agendaPoint) {
		if (agendaPoint.getId() != 0) {
			agendaPoint.getDoneSpeaker().add(agendaPoint.getActualSpeaker());
		}
		for (Participant participant : all_participants) {
			if (!agendaPoint.getDoneSpeaker().contains(participant)) {
				agendaPoint.setActualSpeaker(participant);
				return participant;
			}
		}
		return null;
	}

}
