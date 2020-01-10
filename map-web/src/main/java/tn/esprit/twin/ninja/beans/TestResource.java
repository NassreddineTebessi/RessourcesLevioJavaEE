package tn.esprit.twin.ninja.beans;

import java.util.List;

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

import tn.esprit.twin.ninja.interfaces.RessourceServiceLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.TestServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;
import tn.esprit.twin.ninja.persistence.recruitment.Test;

@Path("test")
@RequestScoped
public class TestResource {
	@EJB
	TestServiceLocal testService;
	@PUT
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("passetest/{idapp}/{idtest}/{note}")
	public Response passeTest(@PathParam("idapp") int idApp,@PathParam("idtest") int idtest,@PathParam("note") int note) {
		if (testService.passeTest(idApp, idtest, note)==0)
			return Response.status(Status.OK).entity(note).build();
		return Response.status(Status.OK).build();
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getalltest")
	public Response getAlltest() {
		List<Test> f =testService.getAllTest();
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!testService.getAllTest().isEmpty())
			return Response.ok(f,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	} 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response addTest(Test t){
		testService.addTest(t);
			return Response.status(Status.CREATED).entity(t).build();

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gettest")
	public Response getTest(@QueryParam("idtest") int idtest,@QueryParam("idapp") int idapp) {
		
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!(testService.getTest(idtest, idapp)==null))
			return Response.ok(testService.getTest(idtest, idapp),MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getresulttest")
	public Response getresultTest(@QueryParam("idtest") int idtest,@QueryParam("idapp") int idapp) {
		
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!(testService.getResultTest(idtest, idapp)==-1))
			return Response.ok(testService.getTest(idtest, idapp),MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@POST
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("assigntest/{idapp}/{idtest}")
	public Response assignTest(@PathParam("idapp") int idApp,@PathParam("idtest") int idtest) {
		System.out.println(idApp);
		System.out.println(idtest);
		if (testService.assignTest(idApp, idtest))
			return Response.status(Status.OK).entity(idApp).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
}
