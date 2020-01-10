package tn.esprit.twin.ninja.beans;

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
import tn.esprit.twin.ninja.interfaces.recruitement.LetterServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;
import tn.esprit.twin.ninja.persistence.recruitment.Typel;

@Path("letter")
@RequestScoped
public class LetterResource {
	@EJB
	LetterServiceLocal letterService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/setletteruser/{idfolder}")
	public Response setletteruser(@PathParam("idfolder") int idFolder,Letter letter) {
		
		return Response.status(Status.OK).entity(letterService.setLetterEmpUser(idFolder, letter)).build();

	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/setletteradmin/{idfolder}")
	public Response setletteradmin(@PathParam("idfolder") int idFolder,Letter letter) {
		
		return Response.status(Status.OK).entity(letterService.setLetterEmpAdmin(idFolder, letter)).build();

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getletter")
	public Response getLetter(@QueryParam("idfolder") int idFolder,@QueryParam("role") Typel role) {
		
		return Response.status(Status.OK).entity(letterService.getLetterEmp(idFolder, role)).build();

	}
	
	@PUT
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setstateletter/{idfolder}")
	public Response setStateLetter(@PathParam("idfolder") int idfolder) {
		if (letterService.setStateLetter(idfolder))
			return Response.status(Status.OK).entity(idfolder).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
	

}
