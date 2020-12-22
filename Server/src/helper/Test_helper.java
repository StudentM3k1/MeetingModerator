package helper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.*;
import model.enumerations.AgendaPointStatus;
import model.enumerations.MeetingStatus;
import model.enumerations.ParticipantType;

public class Test_helper {

	
	public static Meeting getTestMeeting()
	{
		ArrayList<AgendaPoint> points = new ArrayList<AgendaPoint>();
		points.add(new AgendaPoint(0, "Titel", "Note", 60, AgendaPointStatus.Planned, 0));
		points.add(new AgendaPoint(0, "Punkt2", "", 120, AgendaPointStatus.Planned, 0));
		points.add(new AgendaPoint(0, "Punkt3", null, 180, AgendaPointStatus.Planned, 0));
		model.Agenda agenda = new model.Agenda(points);
		
		MeetingSettings meetingSettings = new MeetingSettings("Meeting Title", LocalDateTime.now(), 360, "123", "456");
		
		ArrayList<Participant> participants = new ArrayList<Participant>();
		participants.add(new Participant(0, new User(0, "Vorname", "Nachname", "Mail"), ParticipantType.Moderator));
		participants.add(new Participant(0, new User(0, "Test1", "Test2", "Test3"), ParticipantType.Participant));
		
		return new Meeting(0, agenda, meetingSettings, participants, MeetingStatus.Planned, "Ort");
		
	}
}
