package Datenbank.Manager;

import Datenbank.Dbo.Agenda;
import Datenbank.Dbo.Teilnehmer;

public class TeilnehmerManager extends SqlBase<Teilnehmer> {

	@Override
	protected Teilnehmer CreateNew() {
		return new Teilnehmer();
	}

	public int AddOrUpdateTeilnehmer(Teilnehmer teilnehmer) throws Exception {
		if(teilnehmer.TeilnehmerId > 0) {
			this.Update(teilnehmer);
			return teilnehmer.TeilnehmerId;
		}
		
		return this.Add(teilnehmer);
	}
	
	public Teilnehmer GetById(int teilnehmerId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Agenda.class.getField("TeilnehmerId"), teilnehmerId)));
	}
}
