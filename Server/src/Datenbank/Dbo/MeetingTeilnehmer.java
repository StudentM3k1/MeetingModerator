package Datenbank.Dbo;

import java.sql.Time;

import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;
import Datenbank.Annotations.Tabelle;

@Tabelle(Name = "MeetingTeilnehmer")
public class MeetingTeilnehmer {
	@Key(Autoincrement = false)
	@Spalte(AllowNull = false, Name = "Meeting_id", Typ = SpaltenTyp.Int)
	public int MeetingId;
	
	@Key(Autoincrement = false)
	@Spalte(AllowNull = false, Name = "Teilnehmer_id", Typ = SpaltenTyp.Int)
	public int TeilnehmerId;
	
	@Spalte(AllowNull = false, Name = "Sprechzeit", Typ = SpaltenTyp.Time)
	public Time Sprechzeit;
	
	@Spalte(AllowNull = false, Name = "Typ", Typ = SpaltenTyp.Int)
	public int Typ;
}
