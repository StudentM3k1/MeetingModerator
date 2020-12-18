package rest;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import helper.*;


@Path("/Meeting")
public class RestInterface {

		@POST
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String createMeeting(String json)
		{ 
			return RestHelper.createMeeting(json);
		}
		
		@GET
		@Path("/User/{id}")
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingParticipant(@PathParam("id") String id) throws Exception
		{
			return RestHelper.getMeeting(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}")
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingModerator(@PathParam("id") String id)
		{	
			return RestHelper.getMeeting(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@POST
		@Path("/Moderator/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setMeetingModerator(@PathParam("id") String id, String json)
		{
			RestHelper.setMeeting(IdHelper.getTechnicalIdByModeratorId(id), json);
		}
			
		
		@GET
		@Path("/User/{id}/Change")
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeUser(@PathParam("id") String id) throws Exception
		{	
		
			return RestHelper.getRunningAgendaLastChange(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/Change")
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeModerator(@PathParam("id") String id)
		{	
			return RestHelper.getRunningAgendaLastChange(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		
		@GET
		@Path("/User/{id}/State")
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateUser(@PathParam("id") String id) throws Exception
		{	
			return RestHelper.getRunningAgenda(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/State")
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateModerator(@PathParam("id") String id)
		{	
			return RestHelper.getRunningAgenda(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
			
		@GET
		@Path("/User/{id}/Sync")
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncUser(@PathParam("id") String id) throws Exception
		{	
			return RestHelper.getSyncTime(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/Sync")
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncModerator(@PathParam("id") String id)
		{	
			return RestHelper.getSyncTime(IdHelper.getTechnicalIdByModeratorId(id));
		}
					
		
		@POST
		@Path("/Moderator/{id}/Start")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setStartMeetingModerator(@PathParam("id") String id)
		{	
			 RestHelper.startMeeting(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@POST
		@Path("/User/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setNextPointUser(@PathParam("id") String id) throws Exception
		{	
			RestHelper.nextAgendaPoint(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@POST
		@Path("/Moderator/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setNextPointModerator(@PathParam("id") String id)
		{	
			RestHelper.nextAgendaPoint(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
	
	
}


