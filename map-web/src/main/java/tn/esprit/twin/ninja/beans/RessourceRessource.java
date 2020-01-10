package tn.esprit.twin.ninja.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import tn.esprit.twin.ninja.interfaces.RessourceServiceLocal;
import tn.esprit.twin.ninja.persistence.*;

@Path("/ressource")
@RequestScoped
public class RessourceRessource {

	private final String UPLOADED_FILE_PATH = "d:\\";
	@EJB(beanName = "RessourceService")
	RessourceServiceLocal ressourceService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRessource(Ressource r) {

		if (ressourceService.addRessource(r))
			return Response.status(Status.CREATED).entity(r).build();
		return Response.status(Status.BAD_REQUEST).entity("Empty fields, check").build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateRessource(Ressource res,@PathParam("id")int id) {
		if (ressourceService.updateRessource(res,id))
			return Response.status(Status.ACCEPTED).build();
		return Response.status(Status.BAD_REQUEST).entity("Empty fields, check").build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/archiver/{ressourceId}")
	public Response deleteRessource(@PathParam(value="ressourceId")int ressourceId) {
		if (ressourceService.deleteRessource(ressourceId))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRessources(@QueryParam(value = "id") Integer id,
			@QueryParam(value = "FirstName") String FirstName) {
		if ((id == null) && (FirstName == null)) {
			if (ressourceService.getAllRessources() == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			if (ressourceService.getAllRessources().size() == 0)
				return Response.status(Response.Status.NO_CONTENT).entity("Pas de contenu").build();

			else
				return Response.ok(ressourceService.getAllRessources()).build();

		} else if ((id != null) && (FirstName == null)) {

			if (ressourceService.getRessourceById(id) == null)
				return Response.status(Response.Status.NOT_FOUND).entity("Ressource does not exist").build();

			else
				return Response.ok(ressourceService.getRessourceById(id)).build();

		} else if ((id == null) && (FirstName != null)) {

			if (ressourceService.getRessourceByName(FirstName) == null)
				return Response.status(Response.Status.NOT_FOUND).entity("Verify your search critera").build();

			if (ressourceService.getRessourceByName(FirstName).size() == 0)
				return Response.status(Response.Status.NO_CONTENT).entity("Pas de contenu").build();

			else
				return Response.ok(ressourceService.getRessourceByName(FirstName)).build();

		} else
			return Response.status(Response.Status.BAD_REQUEST).entity("Wrong request").build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/artp/{projectId}/{ressourceId}")
	public Response affectRessourceToProject(@PathParam(value="projectId") int projectId,
			@PathParam(value="ressourceId") int ressourceId) {

		if (ressourceService.affectRessourceToProject(projectId, ressourceId))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

		
	}

	@POST
	@Consumes("multipart/form-data")
	@Path("/upload")
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				writeFile(bytes, fileName);
				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();

	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("AvailableProjects/{ressourceId}")
	public Response getAvailableProjects(@PathParam(value="ressourceId")int ressourceId){
		
		
		return Response.ok(ressourceService.getAvailableProjects(ressourceId)).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ProjectsByRessource/{ressourceId}")
	public Response getProjectsByRessource(@PathParam(value="ressourceId")int ressourceId){
		
		
		return Response.ok(ressourceService.getProjectsByRessource(ressourceId)).build();
		
	}
	
	
	
	/* Mohamed */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/sendMessageToClient")
	public void sendMessageToResource(Message msg, @QueryParam("cu") int currentUser, @QueryParam("client") int clientId) throws MessagingException {
		ressourceService.sendMessageToClient(msg, currentUser, clientId);
		}
	
	/* Mohamed */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOpenedConversations")
	public Response getOpenedConversations(@QueryParam("id") int id) {
		return Response.ok(ressourceService.getOpenedConversations(id)).build();
	}

	/* Mohamed */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/replyToMessage")
	public void replyToMessage(@QueryParam("convId") int conversation, @QueryParam("current") int current, Message message) throws MessagingException {
		ressourceService.respondToAMessage(conversation, current, message);
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getConversationByType")
	public Response getConversationByType(@QueryParam("current") int current,@QueryParam("msgType") MessageType messageType) {
		return Response.ok(ressourceService.getConversationByType(current, messageType)).build();
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getConversationBySubject")
	public Response getConversationBySubject(@QueryParam("subject") String subject, @QueryParam("current") int current) {
		return Response.ok(ressourceService.getConversationBySubject(subject, current)).build();
	}
}
