package Datenbank.Dbo;

import java.sql.Time;
import java.time.LocalDateTime;
import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;
import Datenbank.Annotations.Tabelle;

@Tabelle(Name = "Meeting")
public class Meeting {
	@Key(Autoincrement = true)
	@Spalte(AllowNull = false, Name = "Meeting_id", Typ = SpaltenTyp.Long)
	public long MeetingId;
	
	@Spalte(AllowNull = false, Name = "Bezeichnung", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Bezeichnung;
	
	@Spalte(AllowNull = false, Name = "Gesamtdauer", Typ = SpaltenTyp.Long)
	public long Gesamtdauer;
	
	@Spalte(AllowNull = false, Name = "Ort", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Ort;
	
	@Spalte(AllowNull = false, Name = "Datum", Typ = SpaltenTyp.Datetime)
	public LocalDateTime Datum;
	
	@Spalte(AllowNull = false, Name = "VerbindungsId", Typ = SpaltenTyp.Varchar, MaxLength = 9)
	public String VerbindungsId;
	
	@Spalte(AllowNull = false, Name = "ModeratorVerbindungsId", Typ = SpaltenTyp.Varchar, MaxLength = 9)
	public String ModeratorVerbindungsId;
	
	@Spalte(AllowNull = false, Name = "Status", Typ = SpaltenTyp.Int)
	public int Status;
}
