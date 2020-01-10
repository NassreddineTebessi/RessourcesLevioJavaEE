package tn.esprit.twin.ninja.beans;

import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import tn.esprit.twin.ninja.interfaces.LeaveServiceLocal;
import tn.esprit.twin.ninja.interfaces.RessourceServiceLocal;
import tn.esprit.twin.ninja.persistence.Leave;

@Path("leave")
@RequestScoped
public class LeaveRessource {

	@EJB(beanName = "LeaveService")
	LeaveServiceLocal leaveService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addLeave/{ressourceId}")
	public Response addLeave(@PathParam(value="ressourceId")int ressourceId,Leave l) {

		leaveService.addLeave(ressourceId,l);
		return Response.status(Status.OK).build();

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateLeave(Leave l,@PathParam(value="id")int id) {

		if (leaveService.updateLeave(l,id))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("delete/{leaveId}")
	public Response deleteLeave(@PathParam(value="leaveId") int leaveId) {
		if (leaveService.deleteLeave(leaveId))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLeaves() {

		if (leaveService.getAllLeave() == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		if (leaveService.getAllLeave().size() == 0)
			return Response.status(Response.Status.NO_CONTENT).entity("Pas de contenu").build();
		else

			leaveService.getAllLeave();
		return Response.ok(leaveService.getAllLeave()).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{ressourceId}")
	public Response getLeavesByRessource(@PathParam("ressourceId") int ressourceId) {

		if (leaveService.getLeavesByRessource(ressourceId) == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		if (leaveService.getLeavesByRessource(ressourceId).size() == 0)
			return Response.status(Response.Status.NO_CONTENT).entity("Pas de contenu").build();
		else
			leaveService.getAllLeave();
		return Response.ok(leaveService.getLeavesByRessource(ressourceId)).build();

	}

}
