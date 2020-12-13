package Datenbank.Manager;

import java.lang.reflect.Field;

import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;

public class Bedingung {
	private Object _value;
	private Spalte _spalte;

	public Bedingung(Field field, Object value) {
		this._value = value;
		this._spalte = field.getAnnotation(Spalte.class);
	}

	public String GetWhere() {
		return _spalte.Name() + " = " + (NeedParameter() ? "?" : _value.toString()); 
	}
	
	public boolean NeedParameter() {
		return _spalte.Typ() == SpaltenTyp.Varchar || _spalte.Typ() == SpaltenTyp.Time;
	}
	
	public SqlParameter GetParameter(int index) {
		return new SqlParameter(_value, _spalte.Typ(), index);
	}
}
