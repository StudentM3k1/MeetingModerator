package Datenbank.Manager;

import java.lang.reflect.Field;

class InsertParameter extends SqlParameterBase {

	public InsertParameter(Field field, Object value) {
		super(field, value);
	}

	@Override
	public String getSql() {
		return NeedParameter() ? "?" : (_value != null ? _value.toString() : "");
	}
}
