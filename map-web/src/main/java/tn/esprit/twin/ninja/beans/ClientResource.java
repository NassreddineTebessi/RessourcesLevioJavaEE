package tn.esprit.twin.ninja.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
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
import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Message;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Request;

import java.io.FileNotFoundException;

@Path("client")
@RequestScoped
public class ClientResource {

	@EJB(beanName = "ClientService")
	ClientServiceLocal clientLocal;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addClient(Client c){
			
		clientLocal.addClient(c);
		return "client added"+ c.getLatitude() + c.getLongitude();
		}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("update/{id}")
	public String updateProject(@PathParam(value="id")int id,Client c){
			
		clientLocal.updateClient(id,c);
		return "client updated";
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("delete/{id}")
	public String DeleteClient(@PathParam ("id") int id) {
		
		clientLocal.deleteClient(id);
		return "client deleted";	
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("clients")
	public Response getAllClients()
	{
		if (!clientLocal.getAllClients().isEmpty())
			return Response.ok(clientLocal.getAllClients()).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idClient}")
	public Response getClientById(@PathParam("idClient") int idClient) {
		if (clientLocal.getClientById(idClient) == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(clientLocal.getClientById(idClient)).build();
	}
	/* Mohamed */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOpenedConversations/{id}")
	public Response getOpenedConversations(@PathParam("id") int id) {
		return Response.ok(clientLocal.getOpenedConversations(id)).build();
	}

	/* Mohamed */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/sendMessageToResource")
	public void sendMessageToResource(Message msg, @QueryParam("cu") int currentUser, @QueryParam("resource") int resourceId) throws MessagingException {
		clientLocal.sendMessageToRessource(msg, currentUser, resourceId);
	}

	/* Mohamed */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/AddRequest")
	public void addRequest(@QueryParam("clientId") int clientId, Request request) throws MessagingException {
		clientLocal.addRequest(clientId, request);
	}

	/* Mohamed */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/DeleteRequest")
	public String deleteRequest(@QueryParam("id") int requestId) throws MessagingException {
		clientLocal.deleteRequestById(requestId);
		return "Request deleted ! ";
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOpenedConversations")
	public Response gedtOpenedConversations(@QueryParam("clientId") int clientId) {
		return Response.ok(clientLocal.getOpenedConversations(clientId)).build();
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getConversationBySubject")
	public Response getConversationBySubject(@QueryParam("subject") String subject, @QueryParam("current") int current) {
		return Response.ok(clientLocal.getConversationBySubject(subject, current)).build();
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getConversationMessages")
	public Response getConversationMessages(@QueryParam("id") int conversation, @QueryParam("current") int current) {
		return Response.ok(clientLocal.getConversationMessages(conversation, current)).build();
	}

	/* Mohamed */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/extractMessagesToCsv")
	public String extractMessagesToCsv(@QueryParam("id") int conversation, @QueryParam("current") int current) throws FileNotFoundException {
		clientLocal.extractConversationMessages(conversation, current);
		return "extraction done!";
	}

}
