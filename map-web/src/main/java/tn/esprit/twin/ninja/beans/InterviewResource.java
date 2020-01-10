package tn.esprit.twin.ninja.beans;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.twin.ninja.interfaces.recruitement.FolderServiceLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.InterviewServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Interview;
import tn.esprit.twin.ninja.persistence.recruitment.StateInterview;

@Path("interview")
@RequestScoped
public class InterviewResource {
	@EJB
	InterviewServiceLocal interviewService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idapplication}")
	public Response addInterview(Interview interview,@PathParam("idapplication") int idapplication) {
		interviewService.addInterview(interview,idapplication);
		return Response.status(Status.CREATED).entity(interview).build();

	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setstate/{state}")
	public Response setStateInterview(Interview interview,@PathParam("state") StateInterview state) {
		if (interviewService.setStateInterview(interview.getId(), state))
			return Response.status(Status.OK).entity(interview).build();
		return Response.status(Status.BAD_REQUEST).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatedate")
	public Response updateDateInterview(Interview interview) {
		if (interviewService.updateDateInterview(interview))
			return Response.status(Status.OK).entity(interview).build();
		return Response.status(Status.BAD_REQUEST).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getinterview")
	public Response getInterview(@QueryParam("idinterview") int idinterview) {
		
		return Response.status(Status.OK).entity(interviewService.getInterview(idinterview)).build();
	}

}
