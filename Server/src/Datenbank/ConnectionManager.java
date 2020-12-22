package Datenbank;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class ConnectionManager {

	private static ConnectionSettings _settings;

	public static Connection GetConnection() {
		return GetConnection(true);
	}
	
	private static Connection GetConnection(boolean withDatabase) {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
			ConnectionSettings settings = getSettings();
			return DriverManager.getConnection(settings.getUrl() + (withDatabase ? settings.getDatabase() : ""), 
					settings.getUser(), settings.getPassword());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	} 

	public static void CreateDatabase() throws Exception {
		ConnectionSettings settings = getSettings();

		String sql = "CREATE DATABASE IF NOT EXISTS " + settings.getDatabase();
		GetConnection(false).createStatement().execute(sql);
	}

	private static ConnectionSettings getSettings() throws Exception {

		if (_settings == null) {
			_settings = LoadSettings(true);
		}

		return _settings;
	}
	
	private static ConnectionSettings LoadSettings(boolean fromPath) throws Exception {

		if(fromPath) {
			File file = new File("ConnectionSettings.xml");

			if(file.exists()) {
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = documentBuilder.parse(file);

				String url = document.getElementsByTagName("url").item(0).getTextContent();
				String user = document.getElementsByTagName("user").item(0).getTextContent();
				String password = document.getElementsByTagName("password").item(0).getTextContent();
				String database = document.getElementsByTagName("database").item(0).getTextContent();

				return new ConnectionSettings(url, user, password, database);
			}
		}
		
		return new ConnectionSettings("jdbc:mysql://localhost:3306/", "root", "", "MeetingModerator");
	}
}