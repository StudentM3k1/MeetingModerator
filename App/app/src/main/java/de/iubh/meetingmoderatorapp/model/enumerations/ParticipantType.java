package de.iubh.meetingmoderatorapp.model.enumerations;

public enum ParticipantType {	Participant,
    Moderator;

    public static int getInt(ParticipantType participantType) {
        if ( participantType == null) return 0;
        switch (participantType) {
            case Participant:
                return 0;
            case Moderator:
                return 1;
            default:
                return 0;
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
