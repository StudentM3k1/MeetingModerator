package Datenbank.Mapper;

import java.sql.Time;

import Datenbank.Dbo.MeetingTeilnehmer;
import Datenbank.Dbo.Teilnehmer;
import model.Participant;
import model.enumerations.ParticipantType;

public class MapperMeetingTeilnehmer {
	public static MeetingTeilnehmer MapToMeetingTeilnehmer(Participant participant) {
		MeetingTeilnehmer meetingTeilnehmer = new MeetingTeilnehmer();
		
		meetingTeilnehmer.MeetingId = (int)participant.getId(); //TODO
		meetingTeilnehmer.TeilnehmerId = (int)participant.getUser().getId(); //TODO
		meetingTeilnehmer.Sprechzeit = new Time(participant.getUsedTime()); //TODO in sec
		meetingTeilnehmer.Typ = getParticipantTypeId(participant.getType());
		
		return meetingTeilnehmer;
	}
	
	public static Participant MapToParticipant(MeetingTeilnehmer meetingTeilnehmer, Teilnehmer teilnehmer) {
		return new Participant(meetingTeilnehmer.MeetingId, MapperTeilnehmer.MapToUser(teilnehmer), 
				getParticipantType(meetingTeilnehmer.Typ), meetingTeilnehmer.Sprechzeit.getTime()); //TODO
	}
	
	private static int getParticipantTypeId(ParticipantType type) {
		if(type == ParticipantType.Participant) {
			return 1;
		}
		
		return 2;
	}
	
	private static ParticipantType getParticipantType(int id) {
		if(id == 1) {
			return ParticipantType.Participant;
		}
		
		return ParticipantType.Moderator;
	}
}
