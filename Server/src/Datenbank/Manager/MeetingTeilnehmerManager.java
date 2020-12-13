package Datenbank.Manager;

import Datenbank.Dbo.MeetingTeilnehmer;

public class MeetingTeilnehmerManager extends SqlBase<MeetingTeilnehmer> {

	@Override
	protected MeetingTeilnehmer CreateNew() {
		return new MeetingTeilnehmer();
	}

	public void AddMeetingTeilnehmer(MeetingTeilnehmer meetingTeilnehmer) throws Exception {
		this.Add(meetingTeilnehmer);
	}
	
	public void UpdateMeetingTeilnehmer(MeetingTeilnehmer meetingTeilnehmer) throws Exception {
		this.Update(meetingTeilnehmer);
	}
}
