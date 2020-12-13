package Datenbank;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	public static Connection GetConnection() {
		try {
			ConnectionSettings settings = getSettings();
			return DriverManager.getConnection(settings.getUrl(), settings.getUser(), settings.getPassword());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private static ConnectionSettings getSettings() {
		return new ConnectionSettings("jdbc:mysql://localhost:3306/test", "root", "");
	}
}