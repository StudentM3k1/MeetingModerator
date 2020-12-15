package Datenbank.Manager;

import java.util.ArrayList;
import java.util.List;

import Datenbank.Mapper.MapperAgenda;
import Datenbank.Mapper.MapperMeeting;
import Datenbank.Mapper.MapperMeetingTeilnehmer;
import Datenbank.Mapper.MapperTeilnehmer;
import model.Meeting;
import model.enumerations.AgendaPointStatus;

public class DatenbankService {
	
	private static DatenbankService _instance;
	
	public static DatenbankService getInstance() {
		if(_instance == null) {
			_instance = new DatenbankService();
		}
		
		return _instance;
	}
	
	private AgendaManager _agendaManager;
	private TeilnehmerManager _teilnehmerManager;
	private MeetingManager _meetingManager;
	private MeetingTeilnehmerManager _meetingTeilnehmerManager;
	
	private DatenbankService() {
		this._agendaManager = new AgendaManager();
		this._teilnehmerManager = new TeilnehmerManager();
		this._meetingManager = new MeetingManager();
		this._meetingTeilnehmerManager = new MeetingTeilnehmerManager();
	}
	
	public Meeting getMeeting(String verbindungsId) throws Exception {
		Datenbank.Dbo.Meeting meeting = this._meetingManager.GetByVerbindungsId(verbindungsId);
		
		if(meeting == null) {
			meeting = this._meetingManager.GetByModeratorVerbindungsId(verbindungsId);
		}
		
		if(meeting == null) {
			return null;
		}
		
		return MapperMeeting.MapToMeeting(meeting, this.getMeetingAgenda(meeting), this.getMeetingTeilnehmer(meeting));
	}
	
	public Meeting getMeeting(int id) throws Exception {
		Datenbank.Dbo.Meeting meeting = this._meetingManager.GetById(id);
		
		if(meeting == null) {
			return null;
		}
		
		return MapperMeeting.MapToMeeting(meeting, this.getMeetingAgenda(meeting), this.getMeetingTeilnehmer(meeting));
	}
	
	private List<Datenbank.Dbo.Agenda> getMeetingAgenda(Datenbank.Dbo.Meeting meeting) throws Exception {
		return this._agendaManager.GetAgenda(meeting.MeetingId);
	}
	
	private List<Datenbank.Manager.TeilnehmerDaten> getMeetingTeilnehmer(Datenbank.Dbo.Meeting meeting) throws Exception {
		List<Datenbank.Manager.TeilnehmerDaten> result = new ArrayList<Datenbank.Manager.TeilnehmerDaten>();
		List<Datenbank.Dbo.MeetingTeilnehmer> meetingTeilnehmerList = new ArrayList<Datenbank.Dbo.MeetingTeilnehmer>();
		
		for(Datenbank.Dbo.MeetingTeilnehmer meetingTeilnehmer : meetingTeilnehmerList) {
			result.add(new TeilnehmerDaten(meetingTeilnehmer, this._teilnehmerManager.GetById(meetingTeilnehmer.TeilnehmerId)));
		}
		
		return result;
	}
	
	public void addMeeting(Meeting meeting) throws Exception {
		int meetingId = this._meetingManager.AddNewMeeting(MapperMeeting.MapToMeeting(meeting));
		
		for(model.AgendaPoint agendaPoint : meeting.getAgenda().getAgendaPoint()) {
			Datenbank.Dbo.Agenda agenda = MapperAgenda.MapToAgenda(agendaPoint, meetingId);
			this._agendaManager.AddAgenda(agenda);
		}
		
		for(model.Participant participant : meeting.getParticipants()) {
			int teilnehmerId = this._teilnehmerManager.AddOrUpdateTeilnehmer(MapperTeilnehmer.MapToTeilnehmer(participant.getUser()));
			Datenbank.Dbo.MeetingTeilnehmer meetingTeilnehmer = MapperMeetingTeilnehmer.MapToMeetingTeilnehmer(participant);
			meetingTeilnehmer.TeilnehmerId = teilnehmerId;
			meetingTeilnehmer.MeetingId = meetingId;
			this._meetingTeilnehmerManager.AddMeetingTeilnehmer(meetingTeilnehmer);
		}
	}
	
	public void saveTeilnehmer(List<model.Participant> addParticipants, 
			List<model.Participant> updateParticipants, 
			List<model.Participant> deleteParticipants) throws Exception {
		if(addParticipants != null) {
			for(model.Participant item : addParticipants) {
				int teilnehmerId = this._teilnehmerManager.AddOrUpdateTeilnehmer(MapperTeilnehmer.MapToTeilnehmer(item.getUser()));
				Datenbank.Dbo.MeetingTeilnehmer meetingTeilnehmer = MapperMeetingTeilnehmer.MapToMeetingTeilnehmer(item);
				meetingTeilnehmer.TeilnehmerId = teilnehmerId;
				this._meetingTeilnehmerManager.AddMeetingTeilnehmer(meetingTeilnehmer);
			}
		}
		
		if(updateParticipants != null) {
			for(model.Participant item : updateParticipants) {
				int teilnehmerId = this._teilnehmerManager.AddOrUpdateTeilnehmer(MapperTeilnehmer.MapToTeilnehmer(item.getUser()));
				Datenbank.Dbo.MeetingTeilnehmer meetingTeilnehmer = MapperMeetingTeilnehmer.MapToMeetingTeilnehmer(item);
				meetingTeilnehmer.TeilnehmerId = teilnehmerId;
				this._meetingTeilnehmerManager.UpdateMeetingTeilnehmer(meetingTeilnehmer);
			}
		}
		
		if(deleteParticipants != null) {
			for(model.Participant item : deleteParticipants) {
				this._meetingTeilnehmerManager.Delete(MapperMeetingTeilnehmer.MapToMeetingTeilnehmer(item));
			}
		}
	}
	
	public void saveAgenda(List<model.AgendaPoint> addAgendaPoints, 
			List<model.AgendaPoint> updateAgendaPoints, 
			List<model.AgendaPoint> deleteAgendaPoints, int meetingId) throws Exception {
		if(addAgendaPoints != null) {
			for(model.AgendaPoint item : addAgendaPoints) {
				this._agendaManager.Add(MapperAgenda.MapToAgenda(item, meetingId));
			}
		}
		
		if(updateAgendaPoints != null) {
			for(model.AgendaPoint item : updateAgendaPoints) {
				this._agendaManager.Update(MapperAgenda.MapToAgenda(item, meetingId));
			}
		}
		
		if(deleteAgendaPoints != null) {
			for(model.AgendaPoint item : deleteAgendaPoints) {
				this._agendaManager.Delete(MapperAgenda.MapToAgenda(item, meetingId));
			}
		}
	}
	
	public void setAgendaStatus(int agendaPointId, model.enumerations.AgendaPointStatus newStatus) throws Exception {
		//TODO
	}
	
	public List<model.AgendaPoint> getAgendaPoints(int meetingId, AgendaPointStatus agendaPointStatus) throws Exception {
		List<model.AgendaPoint> result = new ArrayList<model.AgendaPoint>();
		for(Datenbank.Dbo.Agenda agenda : this._agendaManager.GetAgenda(meetingId, MapperAgenda.GetAgendaPointStatusId(agendaPointStatus))) {
			result.add(MapperAgenda.MapToAgendaPoint(agenda));
		}
		return result;
	}
}
