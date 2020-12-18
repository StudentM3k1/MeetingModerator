package model.enumerations;

public enum AgendaPointStatus {
	Planned, Running, Done, Cancelled;

	public static int getInt(AgendaPointStatus agendaPointStatus) {
		if (agendaPointStatus == null) 	return 0;
		 switch (agendaPointStatus) {		 
			case Planned:
				return 0;
			case Running:
				return 1;
			case Done:
				return 2;
			case Cancelled:
				return 3;
			default:
				return 0;
		 }
	}

	public static AgendaPointStatus getAgendaPointStatus(int i) {	
			 switch (i) {
				case 0:
					return AgendaPointStatus.Planned;
				case 1:
					return AgendaPointStatus.Running;
				case 2:
					return AgendaPointStatus.Done;
				case 3:
					return AgendaPointStatus.Cancelled;
				default:
					return AgendaPointStatus.Planned;
		 }
	}
}
