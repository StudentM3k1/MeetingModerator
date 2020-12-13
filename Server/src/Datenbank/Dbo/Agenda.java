package Datenbank.Dbo;

import java.sql.Time;

import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;
import Datenbank.Annotations.Tabelle;

@Tabelle(Name = "Agenda")
public class Agenda {
	@Key(Autoincrement = true)
	@Spalte(AllowNull = false, Name = "Agenda_id", Typ = SpaltenTyp.Int)
	public int AgendaId;
	
	@Spalte(AllowNull = false, Name = "Meeting_id", Typ = SpaltenTyp.Int)
	public int MeetingId;
	
	@Spalte(AllowNull = false, Name = "Name", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Name;
	
	@Spalte(AllowNull = false, Name = "Diskussionszeit", Typ = SpaltenTyp.Time)
	public Time Diskussionszeit;
	
	@Spalte(AllowNull = false, Name = "Sort", Typ = SpaltenTyp.Int)
	public int Sort;
	
	@Spalte(AllowNull = false, Name = "Status", Typ = SpaltenTyp.Int)
	public int Status;
	
	@Spalte(AllowNull = true, Name = "Notiz", Typ = SpaltenTyp.Varchar)
	public String Notiz;
}
