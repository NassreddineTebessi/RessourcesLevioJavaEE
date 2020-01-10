package tn.esprit.twin.ninja.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.twin.ninja.interfaces.recruitement.ApplicationServiceLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.FolderServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.StateFolder;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;

@Path("folder")
@RequestScoped
public class FolderResource {
	@EJB
	FolderServiceLocal folderService;
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getstateminister")
	public Response getStateMinister(@QueryParam("idapplication") int idApplication) {
		 System.out.println(folderService.getStateMinister(idApplication));
			return Response.status(Status.OK).entity(folderService.getStateMinister(idApplication)).build();
		
	}
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getstatefolder")
	public Response getStateFolder(@QueryParam("idapplication") int idApplication) {
		 System.out.println(folderService.getStateFolder(idApplication));
			return Response.status(Status.OK).entity(folderService.getStateFolder(idApplication)).build();
		
	}
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setstateminister/{idapplication}/{state}")
	public Response setStateMinister(@PathParam("idapplication") int idApplication,@PathParam("state") StateMinister state) {
		if (folderService.setStateMinister(idApplication, state))
			return Response.status(Status.OK).entity(state).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
	@PUT
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setstatefolder/{idapplication}/{state}")
	public Response setStateFolder(@PathParam("idapplication") int idApplication,@PathParam("state") StateFolder state) {
		if (folderService.setStateFolder(idApplication, state))
			return Response.status(Status.OK).entity(state).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallfolder")
	public Response getAllfolder() {
		List<Folder> f =folderService.getAllFolder();
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!folderService.getAllFolder().isEmpty())
			return Response.ok(f,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getfolder")
	public Response getfolder(@QueryParam("idApplication") int idapplication) {
		
		//l.stream().forEach(p->System.out.println("eeeeeee"));

		if (!(folderService.getFolder(idapplication)==null))
			return Response.ok(folderService.getFolder(idapplication),MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sendmail/{ida}")
	public Response sendmail(@PathParam("ida") int ida) {
		if (folderService.sendEmail(ida))
			return Response.status(Status.OK).entity(ida).build();
		return Response.status(Status.BAD_REQUEST).build();
	
	}
	

}
