package model;

public class User {
	
	private long id =0;
	private String firstname = new String();
	private String lastname = new String();
	private String mail = new String();
	
	public User(long id,String firstname,String lastname, String mail) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		
	}
	
	// Nur für interne Benutzung
	public User() {
	
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	
}
