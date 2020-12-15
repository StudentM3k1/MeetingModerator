package Datenbank.Mapper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Datenbank.Manager.TeilnehmerDaten;
import model.Participant;
import model.enumerations.MeetingStatus;

public class MapperMeeting {
	public static Datenbank.Dbo.Meeting MapToMeeting(model.Meeting meeting) {
		Datenbank.Dbo.Meeting result = new Datenbank.Dbo.Meeting();
		
		result.MeetingId = (int)meeting.getId(); //TODO
		result.Bezeichnung = meeting.getSettings().getMeetingTitle();
		result.Gesamtdauer = new Time(meeting.getSettings().getDuration()); //TODO
		result.Ort = meeting.getOrt();
		result.Datum = meeting.getSettings().getStartTime();
		result.VerbindungsId = meeting.getSettings().getParticipantId();
		result.ModeratorVerbindungsId = meeting.getSettings().getModeratorId();
		
		return result;
	}
	
	public static model.Meeting MapToMeeting(Datenbank.Dbo.Meeting meeting, List<Datenbank.Dbo.Agenda> agendaList, 
			List<Datenbank.Manager.TeilnehmerDaten> teilnehmerList) {
		
		model.Agenda agenda = new model.Agenda(); //TODO id?
		ArrayList<model.AgendaPoint> agendaPoints = new ArrayList<model.AgendaPoint>();
		for(Datenbank.Dbo.Agenda item : agendaList) {
			agendaPoints.add(MapperAgenda.MapToAgendaPoint(item));
		}
		agenda.setAgendaPoints(agendaPoints);		
		
		model.MeetingSettings settings = new model.MeetingSettings(meeting.MeetingId, meeting.Bezeichnung, meeting.Datum, 
				meeting.Gesamtdauer.getTime(), meeting.ModeratorVerbindungsId, meeting.VerbindungsId);
				
		ArrayList<Participant> participants = new ArrayList<Participant>();
		for(TeilnehmerDaten teilnehmer : teilnehmerList) {
			participants.add(MapperMeetingTeilnehmer.MapToParticipant(teilnehmer.getMeetingTeilnehmer(), teilnehmer.getTeilnehmer()));
		}
		
		model.Meeting result = new model.Meeting(meeting.MeetingId, agenda, settings, participants, MeetingStatus.Planned, 0, meeting.Ort);		
		//TODO woher Status
		
		return result;
	}
}
