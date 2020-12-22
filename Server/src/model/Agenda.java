package model;

import java.util.ArrayList;

public class Agenda {
	
	private ArrayList<AgendaPoint> agendaPoints = new ArrayList<AgendaPoint>();
		
	// Nur f�r interne Benutzung
	public Agenda()
	{
		
	}
		
	public Agenda(ArrayList<AgendaPoint> agendaPoints)
	{
		this.agendaPoints = agendaPoints;
	}
	
	public ArrayList<AgendaPoint> getAgendaPoints() {
		return agendaPoints;
	}
	public void setAgendaPoints(ArrayList<AgendaPoint> agendaPoints) {
		this.agendaPoints = agendaPoints;
	}
}
