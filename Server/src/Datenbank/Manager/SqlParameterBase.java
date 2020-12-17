package Datenbank.Manager;

import java.lang.reflect.Field;

import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;

public class SqlParameterBase {
	protected Object _value;
	protected Spalte _spalte;
	
	public SqlParameterBase(Field field, Object value) {
		this._value = value;
		this._spalte = field.getAnnotation(Spalte.class);
	}
	
	public boolean NeedParameter() {
		return _spalte.Typ() == SpaltenTyp.Varchar 
				|| _spalte.Typ() == SpaltenTyp.Time 
				|| _spalte.Typ() == SpaltenTyp.Datetime;
	}
	
	public SqlParameter GetParameter(int index) {
		return new SqlParameter(_value, _spalte.Typ(), index);
	}
	
	public String getSql() {
		return _spalte.Name() + " = " + (NeedParameter() ? "?" : (_value != null ? _value.toString() : ""));
	}
}
