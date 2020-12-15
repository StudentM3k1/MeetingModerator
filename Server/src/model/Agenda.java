package model;

import java.util.ArrayList;

public class Agenda {
	
	
	
	private long id;
	
	private ArrayList<AgendaPoint> agendaPoints;
	
	
	// Nur für interne Benutzung
	public Agenda()
	{
		
	}
	
	
	public Agenda(int id, ArrayList<AgendaPoint> agendaPoints)
	{
		this.id = id;
		this.agendaPoints = agendaPoints;
	}
	
	public ArrayList<AgendaPoint> getAgendaPoints() {
		return agendaPoints;
	}
	public void setAgendaPoints(ArrayList<AgendaPoint> agendaPoints) {
		this.agendaPoints = agendaPoints;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
