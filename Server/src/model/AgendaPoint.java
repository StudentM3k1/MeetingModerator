package model;

import model.enumerations.AgendaPointStatus;

public class AgendaPoint {

	private long id;
	private String title;
	private Participant responsible ;
	private String note;
	private long availableTime;
	private AgendaPointStatus status;

	// Nur für interne Benutzung
	public AgendaPoint() {
		
	}
	
	public AgendaPoint(long id,String title,Participant responsible ,String note,long availableTime ,AgendaPointStatus status  ) {
	
		this.id =id;
		this.title=title;
		this.responsible=responsible;
		this.note=note;
		this.setAvailableTime(availableTime);
		this.status = status;
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



}
 