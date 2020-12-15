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
		try {
			ConnectionSettings settings = getSettings();
			return DriverManager.getConnection(settings.getUrl(), settings.getUser(), settings.getPassword());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private static ConnectionSettings getSettings() throws Exception {
		
		if(_settings == null) {
			_settings = LoadSettings();
		}
		
		return _settings;
	}
	
	private static ConnectionSettings LoadSettings() throws Exception {
		
		File file = new File("target\\classes\\Datenbank\\ConnectionSettings.xml");
		
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		
		String url = document.getElementsByTagName("url").item(0).getTextContent();
		String user = document.getElementsByTagName("user").item(0).getTextContent();
		String password = document.getElementsByTagName("password").item(0).getTextContent();
		String database = document.getElementsByTagName("database").item(0).getTextContent(); 
		
		return new ConnectionSettings(url + database, user, password);
	}
}