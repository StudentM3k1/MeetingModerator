package Datenbank.Mapper;

import Datenbank.Dbo.Teilnehmer;
import model.User;

public class MapperTeilnehmer {
	public static Teilnehmer MapToTeilnehmer(User user) {
		Teilnehmer teilnehmer = new Teilnehmer();
		
		teilnehmer.TeilnehmerId = (int)user.getId(); //TODO
		teilnehmer.Name = user.getLastname();
		teilnehmer.Vorname = user.getFirstname();
		teilnehmer.Mail = user.getMail();
		
		return teilnehmer;
	}
	
	public static User MapToUser(Teilnehmer teilnehmer) {
		return new User(teilnehmer.TeilnehmerId, teilnehmer.Vorname, teilnehmer.Name, teilnehmer.Mail);
	}
}
