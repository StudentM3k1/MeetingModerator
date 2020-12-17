package Datenbank;

public class ConnectionSettings {
	private String url;
	private String user;
	private String password;
	private String database;
	
	public ConnectionSettings(String url, String user, String password, String database) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getDatabase() {
		return database;
	}
}
