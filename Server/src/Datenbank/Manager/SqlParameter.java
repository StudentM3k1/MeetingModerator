package Datenbank.Manager;

import java.sql.PreparedStatement;
import java.sql.Time;

import Datenbank.Annotations.Spalte.SpaltenTyp;

class SqlParameter {
	private SpaltenTyp _typ;
	private Object _value;
	private int _id;
	
	public SqlParameter(Object value, SpaltenTyp typ, int id) {
		this._value = value;
		this._typ = typ;
		this._id = id;
	}
	
	public void AddToStatemant(PreparedStatement prepareStatement) throws Exception {
		switch(_typ) {
			case Int:
				prepareStatement.setInt(_id, (int)_value);
				break;
			case Time:
				prepareStatement.setTime(_id, (Time)_value);
				break;
			case Varchar:
				prepareStatement.setString(_id, _value.toString());
				break;
		}
	}
}
