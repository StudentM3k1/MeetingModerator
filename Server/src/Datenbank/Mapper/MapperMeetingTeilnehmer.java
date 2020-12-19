package Datenbank.Mapper;

import Datenbank.Dbo.MeetingTeilnehmer;
import Datenbank.Dbo.Teilnehmer;
import model.Participant;
import model.enumerations.ParticipantType;

public class MapperMeetingTeilnehmer {
	public static MeetingTeilnehmer MapToMeetingTeilnehmer(Participant participant, long meetingId) {
		MeetingTeilnehmer meetingTeilnehmer = new MeetingTeilnehmer();
		
		meetingTeilnehmer.MeetingId = meetingId;
		meetingTeilnehmer.TeilnehmerId = participant.getUser().getId();
		meetingTeilnehmer.Sprechzeit = participant.getUsedTime();
		meetingTeilnehmer.Typ = ParticipantType.getInt(participant.getType());
		
		return meetingTeilnehmer;
	}
	
	public static Participant MapToParticipant(MeetingTeilnehmer meetingTeilnehmer, Teilnehmer teilnehmer) {
		return new Participant(meetingTeilnehmer.MeetingId, MapperTeilnehmer.MapToUser(teilnehmer), 
				ParticipantType.getParticipantType(meetingTeilnehmer.Typ), meetingTeilnehmer.Sprechzeit);
	}
}
