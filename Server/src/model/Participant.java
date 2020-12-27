package model;

import model.enumerations.ParticipantType;

public class Participant {
	private long id;
	private User user = new User();
	private ParticipantType type = ParticipantType.Participant;

	public Participant(long id, User user, ParticipantType type) {
		this.id = id;
		this.user = user;
		this.type = type;

	}

	// Nur für interne Benutzung
	public Participant() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ParticipantType getType() {
		return type;
	}

	public void setType(ParticipantType type) {
		this.type = type;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Participant)) {
			return false;
		}
		Participant participant = (Participant) object;
		if (this.id == participant.getId() && this.type == participant.getType()
				&& this.user.getFirstname().equals(participant.user.getFirstname())
				&& this.user.getLastname().equals(participant.user.getLastname())
				&& this.user.getMail().equals(participant.user.getMail())
				&& this.user.getMail().equals(participant.user.getMail())) {
			return true;
		} else {
			return false;
		}
	}
}
