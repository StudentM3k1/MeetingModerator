package model;

import java.util.ArrayList;

public class Agenda {
	private long id;
	private ArrayList<AgendaPoint> agendaPoint;
	
	public ArrayList<AgendaPoint> getAgendaPoint() {
		return agendaPoint;
	}
	public void setAgendaPoint(ArrayList<AgendaPoint> agendaPoint) {
		this.agendaPoint = agendaPoint;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
