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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.filters.Secured;
import tn.esprit.twin.ninja.interfaces.recruitement.JobOfferLocal;
import tn.esprit.twin.ninja.interfaces.recruitement.QuestionServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
import tn.esprit.twin.ninja.persistence.recruitment.Question;

@Path("question")
@RequestScoped
public class QuestionResource {
	@EJB
	QuestionServiceLocal questionService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuestion(Question question) {
		questionService.addQuestion(question);
		return Response.status(Status.CREATED).entity(question).build();

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getquestion/{id}")
	public Response getQestion(@PathParam("id") int idQuestion ) {
		Question l =questionService.getQuestion(idQuestion);
		

		if (!(questionService.getQuestion(idQuestion)==null))
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallquestion")
	public Response getAllQestion() {
		List<Question> l =questionService.getAllQuestion();
		

		if (!(questionService.getAllQuestion().isEmpty()))
			return Response.ok(l,MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	@Secured
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idquestion}/{idtest}")
	public Response assignTestQuestion(@PathParam("idquestion") int idQuestion,@PathParam("idtest") int idTest) {
		if (questionService.assignQuestionTest(idQuestion, idTest))
			return Response.status(Status.OK).entity(idQuestion).build();
		return Response.status(Status.BAD_REQUEST).build();
		
	}
}
