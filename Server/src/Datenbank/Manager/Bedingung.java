package Datenbank.Manager;

import java.lang.reflect.Field;

import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;

public class Bedingung extends SqlParameterBase {
	public Bedingung(Field field, Object value) {
		super(field, value);
	}
}
