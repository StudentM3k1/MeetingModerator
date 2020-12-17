package Datenbank.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Spalte {
	public String Name();
	public SpaltenTyp Typ();
	public boolean AllowNull() default false; 
	public int MaxLength() default -1;
	
	public enum SpaltenTyp {
		Varchar,
		Int,
		Time,
		Datetime,
		Long
	}
}
