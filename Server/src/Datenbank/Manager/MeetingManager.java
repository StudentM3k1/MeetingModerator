package Datenbank.Manager;

import Datenbank.Dbo.Agenda;
import Datenbank.Dbo.Meeting;

public class MeetingManager extends SqlBase<Meeting> {

	@Override
	protected Meeting CreateNew() {
		return new Meeting();
	}
	
	public Meeting GetById(int meetingId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Agenda.class.getField("MeetingId"), meetingId)));
	}
	
	public Meeting GetByVerbindungsId(String verbindungsId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Agenda.class.getField("VerbindungsId"), verbindungsId)));
	}
	
	public Meeting GetByModeratorVerbindungsId(String moderatorVerbindungsId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Agenda.class.getField("ModeratorVerbindungsId"), moderatorVerbindungsId)));
	}
	
	public int AddNewMeeting(Meeting meeting) throws Exception {
		return this.Add(meeting);
	}
}
