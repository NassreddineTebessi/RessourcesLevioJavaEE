package tn.esprit.twin.ninja.beans;

import java.util.List;

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

import tn.esprit.twin.ninja.interfaces.ClientServiceLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.JobOfferLocal;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Interview;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
import tn.esprit.twin.ninja.persistence.recruitment.State;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;

@Path("joboffer")
@RequestScoped
public class JobOfferResource {
	@EJB(beanName = "JobOfferService")
	JobOfferLocal jobofferLocal;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addJobOffer(JobOffer jobOffer) {
		jobofferLocal.addJobOffer(jobOffer);
		return Response.status(Status.CREATED).entity(jobOffer).build();

	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateJobOffer(JobOffer jobOffer) {
		if (jobofferLocal.updateOffer(jobOffer))
			return Response.status(Status.OK).entity(jobOffer).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idjoboffer}")
	public Response removeJobOffer(@PathParam("idjoboffer") int idoffer) {
		if (jobofferLocal.removeJobOffer(idoffer))
			return Response.status(Status.OK).entity(idoffer).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getjoboffer/{id}")
	public Response getJobOffer(@PathParam("id") int idJoboffer ) {
		JobOffer l =jobofferLocal.getJobOffer(idJoboffer);
		

		if (!(jobofferLocal.getJobOffer(idJoboffer)==null))
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getalljoboffer")
	public Response getallJobOffer() {
		List<JobOffer> l =jobofferLocal.getAllJobOffer();
		

		if (!(jobofferLocal.getAllJobOffer().isEmpty()))
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getjobofferbyskills")
	public Response getJobOfferBySkills(@QueryParam("id") int id) {
		List<JobOffer> l =jobofferLocal.getJobOfferBySkills(id);
		l.stream().forEach(p->System.out.println(p.getId()));

		if (!(jobofferLocal.getJobOfferBySkills(id).isEmpty()))
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
