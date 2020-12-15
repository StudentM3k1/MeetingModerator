package model.enumerations;

import org.json.JSONException;

public enum ParticipantType {
	Participant,
	Moderator;
	
	public static int getInt(ParticipantType participantType) {
		 
		 switch (participantType) {
			case Participant:
				return 0;
			case Moderator:
				return 1;
			default:
				throw new JSONException("Status ungültig");
		 }
	}

	public static ParticipantType getParticipantType(int i) {			 
			 switch (i) {
				case 0:
					return ParticipantType.Participant;
				case 1:
					return ParticipantType.Moderator;
				default:
					return ParticipantType.Participant;
		 }
	}
}


