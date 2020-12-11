package model;

import model.enumerations.ParticipantType;

public class Participant {
private	long id;
private User user;
private ParticipantType type;
private long usedTime;
		
		public Participant(long id,User user,ParticipantType type,long usedTime){
		this.id =id;
		this.user =user;
		this.type =type;
		this.usedTime = usedTime;
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

