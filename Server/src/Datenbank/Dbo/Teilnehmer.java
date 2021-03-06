package Datenbank.Dbo;

import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Tabelle;
import Datenbank.Annotations.Spalte.SpaltenTyp;

@Tabelle(Name = "Teilnehmer")
public class Teilnehmer {
	@Key(Autoincrement = true)
	@Spalte(AllowNull = false, Name = "Teilnehmer_id", Typ = SpaltenTyp.Long)
	public long TeilnehmerId;
	
	@Spalte(AllowNull = false, Name = "Name", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Name;
	
	@Spalte(AllowNull = false, Name = "Vorname", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Vorname;
	
	@Spalte(AllowNull = false, Name = "Mail", Typ = SpaltenTyp.Varchar, MaxLength = 128)
	public String Mail;
}
