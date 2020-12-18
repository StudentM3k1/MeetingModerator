package model.enumerations;

import org.json.JSONException;

public enum MeetingStatus {
	Planned, // 0
	Running, // 1
	Done;

	public static int getInt(MeetingStatus meetingStatus) {
		if ( meetingStatus == null) return 0;
		switch (meetingStatus) {
		case Planned:
			return 0;
		case Running:
			return 1;
		case Done:
			return 2;
		default:
			return 0;
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
