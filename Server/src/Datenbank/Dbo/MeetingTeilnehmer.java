package Datenbank.Dbo;

import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;
import Datenbank.Annotations.Tabelle;

@Tabelle(Name = "MeetingTeilnehmer")
public class MeetingTeilnehmer {
	@Key(Autoincrement = false)
	@Spalte(AllowNull = false, Name = "Meeting_id", Typ = SpaltenTyp.Long)
	public long MeetingId;
	
	@Key(Autoincrement = false)
	@Spalte(AllowNull = false, Name = "Teilnehmer_id", Typ = SpaltenTyp.Long)
	public long TeilnehmerId;
	
	@Spalte(AllowNull = false, Name = "Typ", Typ = SpaltenTyp.Int)
	public int Typ;
}
