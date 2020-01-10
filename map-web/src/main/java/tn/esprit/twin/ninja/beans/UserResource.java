package tn.esprit.twin.ninja.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import tn.esprit.twin.ninja.interfaces.UserServiceLocal;
import tn.esprit.twin.ninja.persistence.User;
import tn.esprit.twin.ninja.persistence.Message;
import tn.esprit.twin.ninja.persistence.Skill;

import java.util.Set;

@Path("User")
@RequestScoped
public class UserResource {

	@EJB(beanName = "UserService")
	UserServiceLocal userLocal;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User u) {
		userLocal.addUser(u);
		return Response.ok(u).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMember(User u) {
		if (userLocal.updateUser(u))
			return Response.status(Status.ACCEPTED).entity("member updated: => ").build();
		else
			return Response.status(Status.BAD_REQUEST).entity("member not updated: => ").build();
	}

	@GET
	@Path("/authen")
	@Produces(MediaType.APPLICATION_JSON)

	public Response treatClientRequest(@QueryParam("email") String email, @QueryParam("password") String password) {
		User u = userLocal.Authenticate(email, password);
		return Response.status(Status.ACCEPTED).entity(u).build();
	}

	/* Mohamed */

	@GET
	@Path("/treatClientRequest")
	@Produces(MediaType.TEXT_PLAIN)
	public String treatClientRequests(@QueryParam("requestId") int requestId) {
		userLocal.treatClientRequest(requestId);
		return "Treating request ...";
	}

	/* Mohamed */
	@POST
	@Path("/sendMsgToClient")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessageToClient(Message message) {
		try {
			userLocal.sendMessageToClient(message);
		} catch (MessagingException e) {
			System.out.println("error sending message");
		}
	}
	/* Mohamed */
	@GET
	@POST
	@Path("/getAllRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRequests() {
		return Response.ok(userLocal.getAllRequests()).build();
	}

	@POST
	@Path("/getTreatedRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTreatedRequests() {
		return Response.ok(userLocal.getTreatedRequests()).build();
	}

	@POST
	@Path("/getUnTreatedRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnTreatedRequests() {
		return Response.ok(userLocal.getUnTreatedRequests()).build();
	}

	@POST
	@Path("/getResourcesBySkills")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getResourcesBySkills(Set<Skill> skills) {
		return Response.ok(userLocal.getRessourceBySkills(skills)).build();
	}

	
	/* Mohamed */

	@GET
	@Path("/deleteTreatedRequests")
	public void deleteTreatedRequests() {
		userLocal.deleteTreatedRequests();
	}

	
	/* Mohamed */
	@GET
	@Path("/deleteRequest")
	public void deleteReuest(@QueryParam("requestId") int id) {
		System.out.println(id);
		userLocal.deleteRequest(id);
	}

	/* Mohamed */
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("email") String email, @QueryParam("password") String pass) {
		if(userLocal.login(email, pass) == true) {
			return Response.ok(userLocal.getUserInfo(email, pass)).build();
			}
		return Response.ok(new User()).build();
	}
}
