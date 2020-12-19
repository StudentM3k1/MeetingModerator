package de.iubh.meetingmoderatorapp.model;

import de.iubh.meetingmoderatorapp.model.enumerations.AgendaPointStatus;

public class AgendaPoint {

	private long id = 0;
	private String title;
	private Participant responsible = new Participant();
	private String note;
	private long availableTime = 0;
	private AgendaPointStatus status = AgendaPointStatus.Planned;
	private long sort = 0;

	// Nur fr interne Benutzung
	public AgendaPoint() {
		
	}


	public AgendaPoint(long id,String title ,String note,long availableTime ,AgendaPointStatus status, long sort) {
	
		this.id =id;
		this.title=title;
		this.note=note;
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
	public Participant getResponsible() {
		return responsible;
	}
	public void setResponsible(Participant responsible) {
		this.responsible = responsible;
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
}
 