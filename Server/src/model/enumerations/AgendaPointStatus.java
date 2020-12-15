package model.enumerations;

import org.json.JSONException;

public enum AgendaPointStatus {
	Planned, Running, Done, Cancelled;

	public static int getInt(AgendaPointStatus agendaPointStatus) {
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
				throw new JSONException("Status ungültig");
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
