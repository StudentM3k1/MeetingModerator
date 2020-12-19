package Datenbank.Manager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDateTime;

import com.mysql.cj.PerConnectionLRUFactory;

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
			case Long:
				prepareStatement.setLong(_id, (long)_value);
				break;
			case Time:
				prepareStatement.setTime(_id, (Time)_value);
				break;
			case Datetime:
				prepareStatement.setString(_id, ((LocalDateTime)_value).toString());
				break;
			case Varchar:
				prepareStatement.setString(_id, (_value == null ? "" : _value.toString()));
				break;
		}
	}
}
