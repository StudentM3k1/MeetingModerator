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
		public String createMeeting()
		{
			String response = RestHelper.createMeeting();
			
			return response;
		}
			
		@GET
		@Path("/User/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingParticipant(@PathParam("id") Long id) throws Exception
		{
			return "sdfsdsdfasdfasdfasdfasdfasdf";
			//return getMeetingTechnical(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		
		@GET
		@Path("/Moderator/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingModerator(@PathParam("id") Long id)
		{	
			return getMeetingTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		
		@GET
		@Path("/Technical/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getMeeting(id);
		}
		
	
		@POST
		@Path("/Moderator/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setMeetingModerator(@PathParam("id") Long id)
		{
			setMeetingTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
			
		
		@POST
		@Path("/Technical/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setMeetingTechnical(@PathParam("id") Long id)
		{
			RestHelper.setMeeting(id);
		}
		
		
		@GET
		@Path("/User/{id}/Change")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeUser(@PathParam("id") Long id) throws Exception
		{	
			return getChangeTechnical(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		
		@GET
		@Path("/Moderator/{id}/Change")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeModerator(@PathParam("id") Long id)
		{	
			return getChangeTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		
		@GET
		@Path("/Technical/{id}/Change")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getRunningAgendaLastChange(id);
		}
		
		
		@GET
		@Path("/User/{id}/State")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateUser(@PathParam("id") Long id) throws Exception
		{	
			return getStateTechnical(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/State")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateModerator(@PathParam("id") Long id)
		{	
			return getStateTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@GET
		@Path("/Technical/{id}/State")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getRunningAgenda(id);
		}
		
		@GET
		@Path("/User/{id}/Sync")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncUser(@PathParam("id") Long id) throws Exception
		{	
			return getSyncTechnical(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/Sync")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncModerator(@PathParam("id") Long id)
		{	
			return getSyncTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@GET
		@Path("/Technical/{id}/Sync")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getSyncTime(id);
		}
		
		@POST
		@Path("/Moderator/{id}/Start")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setStartMeetingModerator(@PathParam("id") Long id)
		{	
			 getStartMeetingTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@POST
		@Path("/Technical/{id}/Start")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void getStartMeetingTechnical(@PathParam("id") Long id)
		{
			 RestHelper.startMeeting(id);
		}
		
		@POST
		@Path("/User/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setNextPointUser(@PathParam("id") Long id) throws Exception
		{	
			 setNextPointTechnical(IdHelper.getTechnicalIdByParticipantId(id));
		}
			
		@POST
		@Path("/Moderator/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setNextPointModerator(@PathParam("id") Long id)
		{	
			setNextPointTechnical(IdHelper.getTechnicalIdByModeratorId(id));
		}
		
		@POST
		@Path("/Technical/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setNextPointTechnical(@PathParam("id") Long id)
		{
			RestHelper.nextAgendaMeeting(id);
		}
	
}


