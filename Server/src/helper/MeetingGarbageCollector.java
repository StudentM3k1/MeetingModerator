package helper;

import java.util.TimerTask;

public class MeetingGarbageCollector extends TimerTask {
	
	  @Override public void run() 
	  {
	    try {
			MeetingContainerHelper.garbageCollect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	}
