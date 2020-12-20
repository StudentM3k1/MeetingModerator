package helper;

import java.util.TimerTask;

import model.*;

public class MeetingRunner extends TimerTask {

	private Meeting meeting;

	public MeetingRunner(Meeting meeting) {
		this.meeting = meeting;
	}

	@Override
	public void run() {
		meeting.MeetingTick();
	}
}
