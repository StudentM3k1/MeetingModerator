package Datenbank.Manager;

import java.lang.reflect.Field;

public class NewValue extends SqlParameterBase {
	public NewValue(Field field, Object value) {
		super(field, value);
	}	
}
