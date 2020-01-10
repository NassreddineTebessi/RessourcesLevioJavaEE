package tn.esprit.twin.ninja.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.print.attribute.standard.Media;
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


import tn.esprit.twin.ninja.interfaces.recruitement.ApplicationServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.State;

@Path("application")
@RequestScoped
public class ApplicationResource {
	@EJB
	ApplicationServiceLocal ApplicationService;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idJob}/{idRess}")
	public Response addApplication(Application a,@PathParam("idJob") int idJob,@PathParam("idRess") int idRess){
		ApplicationService.addApplication(a,idJob,idRess);
			return Response.status(Status.CREATED).entity(a).build();

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getapplication")
	public Response getApplication(@QueryParam("idapplication") int idapplication) {
		
		return Response.status(Status.ACCEPTED)
				.entity(ApplicationService.getApplication(idapplication)).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getapplicationid")
	public Response getApplicationId(@QueryParam("idressource") int idressource) {
		
		return Response.status(Status.ACCEPTED)
				.entity(ApplicationService.getApplicationId(idressource)).build();
	}
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteapplication")
	public Response deleteApplication(@QueryParam("idapplication") int idapplication) {
		
		if (ApplicationService.deleteApplication(idapplication))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setState(Application a) {
		if (ApplicationService.setStateApplication(a))
			return Response.status(Status.OK).entity(a).build();
		return Response.status(Status.BAD_REQUEST).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallapplication")
	public Response getAllApplication() {
		List<Application> l =ApplicationService.getAllApplication();
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!ApplicationService.getAllApplication().isEmpty())
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getapplicationbystate")
	public Response getApplicationByState(@QueryParam("state") State state ) {
		List<Application> l =ApplicationService.getApplicationByState(state);
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!ApplicationService.getApplicationByState(state).isEmpty())
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("assignp/{idr}/{idp}")
	public Response assignP(@PathParam("idr") int idr,@PathParam("idp") int idp) {
		if (ApplicationService.assignRessource(idr, idp))
			return Response.status(Status.OK).entity(idr).build();
		return Response.status(Status.BAD_REQUEST).build();
	}
	
}
