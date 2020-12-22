package Datenbank.Manager;

import Datenbank.Dbo.MeetingTeilnehmer;
import Datenbank.Dbo.Teilnehmer;

public class TeilnehmerDaten {
	private MeetingTeilnehmer _meetingTeilnehmer;
	private Teilnehmer _teilnehmer;
	
	public TeilnehmerDaten(MeetingTeilnehmer meetingTeilnehmer, Teilnehmer teilnehmer) {
		this._meetingTeilnehmer = meetingTeilnehmer;
		this._teilnehmer = teilnehmer;
	}
	
	public MeetingTeilnehmer getMeetingTeilnehmer() {
		return this._meetingTeilnehmer;
	}
	
	public Teilnehmer getTeilnehmer() {
		return this._teilnehmer;
	}
}
