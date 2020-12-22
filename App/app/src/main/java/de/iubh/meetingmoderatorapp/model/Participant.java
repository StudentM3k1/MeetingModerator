package de.iubh.meetingmoderatorapp.model;

import de.iubh.meetingmoderatorapp.model.enumerations.ParticipantType;

public class Participant {
private	long id = 0;
private User user = new User();
private ParticipantType type = ParticipantType.Participant;
private long usedTime  = 0;
		
		public Participant(long id, User user, ParticipantType type, long usedTime){
		this.id =id;
		this.user =user;
		this.type =type;
		this.usedTime = usedTime;
		}
		
		// Nur f�r interne Benutzung
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

		public long getUsedTime() {
			return usedTime;
		}

		public void setUsedTime(long usedTime) {
			this.usedTime = usedTime;
		}
}

