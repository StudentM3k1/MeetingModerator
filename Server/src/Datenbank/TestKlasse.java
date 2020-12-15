package Datenbank;

import java.sql.Time;

import Datenbank.Dbo.Agenda;
import Datenbank.Manager.AgendaManager;

public class TestKlasse {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			AgendaManager manager = new AgendaManager();
			
			Agenda t = manager.GetById(3);
			
			Agenda agenda = new Agenda();
			
			//agenda.AgendaId = 2;
			agenda.Name = "Test Agenda";
			agenda.Diskussionszeit = new Time(1, 20, 0);
			agenda.Sort = 0;
			agenda.Status = 0;
			agenda.Notiz = "TTTTT";
			
			manager.AddAgenda(agenda);
			
			System.out.println("Done");	
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
