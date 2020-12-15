package Datenbank.Manager;

import java.util.ArrayList;
import java.util.List;

import Datenbank.Dbo.Agenda;

public class AgendaManager extends SqlBase<Agenda> {
		
	public Agenda GetById(int id) throws Exception {
		List<Agenda> result = GetBy(new Bedingung(Agenda.class.getField("AgendaId"), id));
		return result.size() > 0 ? result.get(0) : null;
	}

	public List<Agenda> GetAgenda(int meetingId, int statusId) throws Exception {
		List<Bedingung> bedingungen = new ArrayList<Bedingung>();
		bedingungen.add(new Bedingung(Agenda.class.getField("MeetingId"), meetingId));
		bedingungen.add(new Bedingung(Agenda.class.getField("Status"), statusId));
		return GetBy(bedingungen);
	}
	
	public List<Agenda> GetAgenda(int meetingId) throws Exception {
		return GetBy(new Bedingung(Agenda.class.getField("MeetingId"), meetingId));
	}
	
	@Override
	protected Agenda CreateNew() {
		return new Agenda();
	}	
	
	public void AddAgenda(Agenda agenda) throws Exception {
		this.Add(agenda);
	}
}
