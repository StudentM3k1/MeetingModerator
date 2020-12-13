package Datenbank.Mapper;

import java.sql.Time;

import Datenbank.Dbo.Agenda;
import model.AgendaPoint;
import model.enumerations.AgendaPointStatus;

public class MapperAgenda {
	public static Agenda MapToAgenda(AgendaPoint agendaPoint) {
		Agenda agenda = new Agenda();
		
		agenda.AgendaId = (int)agendaPoint.getId(); //TODO
		agenda.MeetingId = 0; //TODO
		agenda.Name = agendaPoint.getTitle();
		agenda.Diskussionszeit = new Time(agendaPoint.getAvailableTime());
		agenda.Sort = 0; //TODO
		agenda.Status = GetAgendaPointStatusId(agendaPoint.getStatus());
		agenda.Notiz = agendaPoint.getNote();
		
		return agenda;
	}
	
	public static AgendaPoint MapToAgendaPoint(Agenda agenda) {
		return new AgendaPoint(agenda.AgendaId, agenda.Name, null, //TODO 
				agenda.Notiz, agenda.Diskussionszeit.getTime(), GetAgendaPointStatus(agenda.Status));
		//TODO Sort fehlt
	}
	
	public static int GetAgendaPointStatusId(AgendaPointStatus status) {
		if(status == AgendaPointStatus.Planned) {
			return 1;
		}
		
		if(status == AgendaPointStatus.Running) {
			return 2;
		}
		
		if(status == AgendaPointStatus.Done) {
			return 3;
		}
		
		return 4;
	}
	
	public static AgendaPointStatus GetAgendaPointStatus(int id) {
		if(id == 1) {
			return AgendaPointStatus.Planned;
		}
		
		if(id == 2) {
			return AgendaPointStatus.Running;
		}
		
		if(id == 3) {
			return AgendaPointStatus.Done;
		}
		
		return AgendaPointStatus.Cancelled;
	}
}
