package Datenbank.Manager;

import Datenbank.Dbo.Teilnehmer;

public class TeilnehmerManager extends SqlBase<Teilnehmer> {

	@Override
	protected Teilnehmer CreateNew() {
		return new Teilnehmer();
	}

	public long AddOrUpdateTeilnehmer(Teilnehmer teilnehmer) throws Exception {
		if(teilnehmer.TeilnehmerId > 0) {
			this.Update(teilnehmer);
			return teilnehmer.TeilnehmerId;
		}
		
		return this.Add(teilnehmer);
	}
	
	public long AddTeilnehmer(Teilnehmer teilnehmer) throws Exception {
		return this.Add(teilnehmer);
	}
	
	public Teilnehmer GetById(long teilnehmerId) throws Exception {
		return getFirst(this.GetBy(new Bedingung(Teilnehmer.class.getField("TeilnehmerId"), teilnehmerId)));
	}
}
