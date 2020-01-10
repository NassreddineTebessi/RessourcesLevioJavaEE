package tn.esprit.twin.ninja.beans;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import tn.esprit.twin.ninja.interfaces.ProjectServiceLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.ApplicationServiceLocal;
import tn.esprit.twin.ninja.persistence.Project;

@Path("project")
@RequestScoped
public class ProjectResource {

	@EJB
	ProjectServiceLocal projectLocal;
	private final String UPLOADED_FILE_PATH = "e:\\";

	@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		@Path("add/{idClient}")
		public String addProjectDotnet(Project p, @PathParam("idClient") int idClient){
				
			projectLocal.addProjectDotnet(p, idClient);
			return "project added";
		}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addProject(Project p){
			
		projectLocal.addProject(p);
		return "project added";
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("update")
	public String updateProject(Project p){
			
		projectLocal.updateProject(p);
		return "project updated";
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("delete/{projectId}")
	public String DeleteProject(@PathParam ("projectId") int projectId) {
		
		projectLocal.deleteProject(projectId);
		return "project deleted";
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("affect/{projectId}/{clientId}")
	public String affectProjecttoClient(@PathParam ("projectId")int projectId, @PathParam ("clientId")int clientId)
	{
		projectLocal.affectProjecttoClient(projectId, clientId);
		return "ok";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("projects")
	public Response getAllProjects()
	{
		if (!projectLocal.getAllProject().isEmpty())
			return Response.ok(projectLocal.getAllProject()).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("projectbyclient/{idClient}")
	public Response getProjectByClient(@PathParam("idClient") int idClient) {
		if (projectLocal.getProjectByClient(idClient) == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(projectLocal.getProjectByClient(idClient)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("projectbyadress/{adress}")
	public Response getProjectByAdress(@PathParam("adress") String adress) {
		if (projectLocal.getProjectByAdress(adress) == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(projectLocal.getProjectByAdress(adress)).build();
	}
	
	@POST
	@Consumes("multipart/form-data")
	@Path("/upload")
	public Response uploadFile(MultipartFormDataInput input, @QueryParam("id") int id) {

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
				projectLocal.addPhotoProject(id, fileName);
				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();

	}

	@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("{idProject}")
		public Response getProjectById(@PathParam("idProject") int idProject) {
			if (projectLocal.getProjectById(idProject) == null)
				return Response.status(Status.NOT_FOUND).build();
			return Response.ok(projectLocal.getProjectById(idProject)).build();
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

}
