package Datenbank.Mapper;

import java.sql.Time;

public class Converter {
	public static Time LongToTime(long value) {
		return new Time(value * 1000);
	}
	
	public static long TimeToLong(Time value) {
		return value.getTime() / 1000;
	}
}
