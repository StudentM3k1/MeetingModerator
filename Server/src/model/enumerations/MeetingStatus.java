package model.enumerations;

import org.json.JSONException;

public enum MeetingStatus {
	Planned, // 0
	Running, // 1
	Done;

	public static int getInt(MeetingStatus meetingStatus) {
		switch (meetingStatus) {
		case Planned:
			return 0;
		case Running:
			return 1;
		case Done:
			return 2;
		default:
			throw new JSONException("Status ungültig");
		}
	}

	public static MeetingStatus getMeetingStatus(int i) {
		switch (i) {
		case 0:
			return MeetingStatus.Planned;
		case 1:
			return MeetingStatus.Running;
		case 2:
			return MeetingStatus.Done;
		default:
			return MeetingStatus.Planned;
		}
	}
	
}
