package Datenbank.Manager;

import java.util.List;

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
	
	public List<MeetingTeilnehmer> GetByMeetingId(long meetingId) throws Exception {
		return this.GetBy(new Bedingung(MeetingTeilnehmer.class.getField("MeetingId"), meetingId));
	}
}
