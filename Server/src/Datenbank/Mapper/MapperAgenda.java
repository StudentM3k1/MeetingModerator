package Datenbank.Mapper;

import Datenbank.Dbo.Agenda;
import model.AgendaPoint;
import model.enumerations.AgendaPointStatus;

public class MapperAgenda {
	public static Agenda MapToAgenda(AgendaPoint agendaPoint, long meetingId) {
		Agenda agenda = new Agenda();
		
		agenda.AgendaId = agendaPoint.getId();
		agenda.MeetingId = meetingId;
		agenda.Name = agendaPoint.getTitle();
		agenda.Diskussionszeit = agendaPoint.getAvailableTime();
		agenda.Sort = agendaPoint.getSort();
		agenda.Status = AgendaPointStatus.getInt(agendaPoint.getStatus());
		agenda.Notiz = agendaPoint.getNote();
		
		return agenda;
	}
	
	public static AgendaPoint MapToAgendaPoint(Agenda agenda) {
		return new AgendaPoint(agenda.AgendaId, agenda.Name, agenda.Notiz, agenda.Diskussionszeit, 
				AgendaPointStatus.getAgendaPointStatus(agenda.Status), agenda.Sort);
	}
}
