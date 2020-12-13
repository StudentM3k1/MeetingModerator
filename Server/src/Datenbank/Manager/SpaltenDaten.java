package Datenbank.Manager;

import java.lang.reflect.Field;

import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;

class SpaltenDaten {
	private Spalte spalte;
	private Key key;
	private Field field;
	
	public SpaltenDaten(Spalte spalte, Key key, Field field) {
		this.spalte = spalte;
		this.key = key;
		this.field = field;
	}
	
	public Spalte getSpalte() {
		return spalte;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Field getField() {
		return field;
	}
}
