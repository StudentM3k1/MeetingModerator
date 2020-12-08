package rest;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import helper.MeetingHelper;
import helper.RestHelper;


@Path("/Meeting")
public class RestInterface {

		@POST
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String createMeeting()
		{
			return RestHelper.createMeeting();
		}
			
		@GET
		@Path("/User/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getMeetingParticipant(@PathParam("id") Long id) throws Exception
		{
			return getMeetingTechnical(MeetingHelper.getTechnicalIdByParticipantId(id));
		}
			
		
		@GET
		@Path("/Moderator/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.TEXT_PLAIN})
		public String getMeetingModerator(@PathParam("id") Long id)
		{	
			return getMeetingTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
		}
		
		
		@GET
		@Path("/Technical/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.TEXT_PLAIN})
		public String getMeetingTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getMeeting(id);
		}
		
		
			
		@POST
		@Path("/Moderator/{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		public void setMeetingModerator(@PathParam("id") Long id)
		{
			setMeetingTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
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
			return getChangeTechnical(MeetingHelper.getTechnicalIdByParticipantId(id));
		}
			
		
		@GET
		@Path("/Moderator/{id}/Change")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getChangeModerator(@PathParam("id") Long id)
		{	
			return getChangeTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
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
			return getStateTechnical(MeetingHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/State")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getStateModerator(@PathParam("id") Long id)
		{	
			return getStateTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
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
			return getSyncTechnical(MeetingHelper.getTechnicalIdByParticipantId(id));
		}
			
		@GET
		@Path("/Moderator/{id}/Sync")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncModerator(@PathParam("id") Long id)
		{	
			return getSyncTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
		}
		
		@GET
		@Path("/Technical/{id}/Sync")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String getSyncTechnical(@PathParam("id") Long id)
		{
			return RestHelper.getSyncTime(id)
		}
		
		@POST
		@Path("/Moderator/{id}/Start")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setStartMeetingModerator(@PathParam("id") Long id)
		{	
			 getStartMeetingTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
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
			 setNextPointTechnical(MeetingHelper.getTechnicalIdByParticipantId(id));
		}
			
		@POST
		@Path("/Moderator/{id}/Next")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public void setNextPointModerator(@PathParam("id") Long id)
		{	
			setNextPointTechnical(MeetingHelper.getTechnicalIdByModeratorId(id));
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


