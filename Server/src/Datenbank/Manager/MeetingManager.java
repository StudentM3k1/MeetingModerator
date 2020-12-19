package Datenbank.Manager;

import Datenbank.Dbo.Meeting;

public class MeetingManager extends SqlBase<Meeting> {

	@Override
	protected Meeting CreateNew() {
		return new Meeting();
	}
	
	public Meeting GetById(long meetingId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Meeting.class.getField("MeetingId"), meetingId)));
	}
	
	public Meeting GetByVerbindungsId(String verbindungsId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Meeting.class.getField("VerbindungsId"), verbindungsId)));
	}
	
	public Meeting GetByModeratorVerbindungsId(String moderatorVerbindungsId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Meeting.class.getField("ModeratorVerbindungsId"), moderatorVerbindungsId)));
	}
	
	public long AddNewMeeting(Meeting meeting) throws Exception {
		return this.Add(meeting);
	}
	
	public void setMeetingStatus(long meetingId, int newStatus) throws Exception {
		Meeting meeting = new Meeting();
		meeting.MeetingId = meetingId;
		
		this.Update(meeting, new NewValue(Meeting.class.getField("Status"), newStatus));
	} 
}
