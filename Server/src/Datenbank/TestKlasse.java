package Datenbank;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Datenbank.Dbo.Agenda;
import Datenbank.Manager.AgendaManager;
import Datenbank.Manager.DatenbankService;
import model.AgendaPoint;
import model.Meeting;
import model.MeetingSettings;
import model.Participant;
import model.User;
import model.enumerations.AgendaPointStatus;
import model.enumerations.MeetingStatus;
import model.enumerations.ParticipantType;

public class TestKlasse {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			//TestCreateDatabase();
			TestAddMeeting();
			//TestGetMeetingId();
			//TestGetMeeting();			
			//TestGetAgendaPoints();
			//TestSetAgendaStatus();
			//TestSaveTeilnehmer();
			//TestSaveAgenda();
					
			System.out.println("Done");	
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void TestCreateDatabase() throws Exception {
		DatenbankService.getInstance().CreateDatabase();
	}
	
	private static void TestGetMeetingId() throws Exception {
		Meeting meeting = DatenbankService.getInstance().getMeeting(4);	
	}
	
	private static void TestGetMeeting() throws Exception {
		Meeting meetingTeilnehmer = DatenbankService.getInstance().getMeeting("456");
		
		Meeting meetingModerator = DatenbankService.getInstance().getMeeting("123");
	}
	
	private static void TestAddMeeting() throws Exception {
		ArrayList<AgendaPoint> points = new ArrayList<AgendaPoint>();
		points.add(new AgendaPoint(0, "Titel", "Note", 60, AgendaPointStatus.Planned, 0));
		points.add(new AgendaPoint(0, "Punkt2", "", 120, AgendaPointStatus.Planned, 0));
		points.add(new AgendaPoint(0, "Punkt3", null, 180, AgendaPointStatus.Planned, 0));
		model.Agenda agenda = new model.Agenda(0, points);
		
		MeetingSettings meetingSettings = new MeetingSettings(0, "Meeting Title", new Date(2020, 12, 17), 360, "123", "456");
		
		ArrayList<Participant> participants = new ArrayList<Participant>();
		participants.add(new Participant(0, new User(0, "Vorname", "Nachname", "Mail"), ParticipantType.Moderator, 30));
		participants.add(new Participant(0, new User(0, "Test1", "Test2", "Test3"), ParticipantType.Participant, 150));
		
		Meeting meeting = new Meeting(0, agenda, meetingSettings, participants, MeetingStatus.Planned, 0, "Ort");
		
		DatenbankService.getInstance().addMeeting(meeting);
	}
	
	private static void TestSaveTeilnehmer() throws Exception {
		
		List<Participant> addParticipants = new ArrayList<Participant>();
		//addParticipants.add(new Participant(0, new User(0, "Neuer", "Teil", "nehmer"), ParticipantType.Participant, 6000));
		List<Participant> updateParticipants = new ArrayList<Participant>();
		//updateParticipants.add(new Participant(4, new User(4, "Vorname", "Nachname", "Neue Mail"), ParticipantType.Moderator, 6000));
		List<Participant> deleteParticipants = new ArrayList<Participant>();
		deleteParticipants.add(new Participant(4, new User(4, "Neuer", "Teil", "nehmer"), ParticipantType.Participant, 6000));
		
		DatenbankService.getInstance().saveTeilnehmer(addParticipants, updateParticipants, deleteParticipants, 4);
	}
	
	private static void TestSaveAgenda() throws Exception {
		List<AgendaPoint> addAgendaPoints = new ArrayList<AgendaPoint>();
		addAgendaPoints.add(new AgendaPoint(0, "Neuer Punkt", "ganz wichtig", 360, AgendaPointStatus.Planned, 0));
		List<AgendaPoint> updateAgendaPoints = new ArrayList<AgendaPoint>();
		//updateAgendaPoints.add(new AgendaPoint(10, "Titel", "Note", 360, AgendaPointStatus.Running, 0));
		List<AgendaPoint> deleteAgendaPoints = new ArrayList<AgendaPoint>();
		//deleteAgendaPoints.add(new AgendaPoint(10, "Titel", "Note", 360, AgendaPointStatus.Planned, 0));
		
		DatenbankService.getInstance().saveAgenda(addAgendaPoints, updateAgendaPoints, deleteAgendaPoints, 4);
	}
	
	private static void TestSetAgendaStatus() throws Exception {
		DatenbankService.getInstance().setAgendaStatus(8, AgendaPointStatus.Running);
	}
	
	private static void TestGetAgendaPoints() throws Exception {
		List<AgendaPoint> result = DatenbankService.getInstance().getAgendaPoints(4, AgendaPointStatus.Planned);
	}
}
