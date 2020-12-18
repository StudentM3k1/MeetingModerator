package helper;

import java.util.TimerTask;

public class MeetingGarbageCollector extends TimerTask {
	
	  @Override public void run()
	  {
	    MeetingContainerHelper.garbageCollect();
	  }
	}
