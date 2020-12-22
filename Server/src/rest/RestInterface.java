package rest;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import helper.*;

@Path("/Meeting")
public class RestInterface {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createMeeting(String json) {
		try {
			String json_return = RestHelper.createMeeting(json);
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/User/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMeetingParticipant(@PathParam("id") String id) throws Exception {
		try {
			String json_return = RestHelper.getMeeting(IdHelper.getTechnicalIdByParticipantId(id), false);
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/Moderator/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMeetingModerator(@PathParam("id") String id) {
		try {
			String json_return = RestHelper.getMeeting(IdHelper.getTechnicalIdByModeratorId(id), true);
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/Moderator/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response setMeetingModerator(@PathParam("id") String id, String json) {

		try {
			RestHelper.setMeeting(IdHelper.getTechnicalIdByModeratorId(id), json);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/User/{id}/Change")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getChangeUser(@PathParam("id") String id) throws Exception {

		try {
			String json_return = RestHelper.getRunningAgendaLastChange(IdHelper.getTechnicalIdByParticipantId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/Moderator/{id}/Change")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getChangeModerator(@PathParam("id") String id) {

		try {
			String json_return = RestHelper.getRunningAgendaLastChange(IdHelper.getTechnicalIdByModeratorId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}

	}

	@GET
	@Path("/User/{id}/State")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getStateUser(@PathParam("id") String id) throws Exception {

		try {
			String json_return = RestHelper.getRunningAgenda(IdHelper.getTechnicalIdByParticipantId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/Moderator/{id}/State")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getStateModerator(@PathParam("id") String id) {

		try {
			String json_return = RestHelper.getRunningAgenda(IdHelper.getTechnicalIdByModeratorId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/User/{id}/Sync")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSyncUser(@PathParam("id") String id) throws Exception {

		try {
			String json_return = RestHelper.getSyncTime(IdHelper.getTechnicalIdByParticipantId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/Moderator/{id}/Sync")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSyncModerator(@PathParam("id") String id) {

		try {
			String json_return = RestHelper.getSyncTime(IdHelper.getTechnicalIdByModeratorId(id));
			return Response.ok(json_return, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/Moderator/{id}/Start")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response setStartMeetingModerator(@PathParam("id") String id) {

		try {
			RestHelper.startMeeting(IdHelper.getTechnicalIdByModeratorId(id));
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/User/{id}/Next")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response setNextPointUser(@PathParam("id") String id) throws Exception {

		try {
			RestHelper.nextAgendaPoint(IdHelper.getTechnicalIdByParticipantId(id));
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/Moderator/{id}/Next")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response setNextPointModerator(@PathParam("id") String id) {

		try {

			RestHelper.nextAgendaPoint(IdHelper.getTechnicalIdByModeratorId(id));
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
}
