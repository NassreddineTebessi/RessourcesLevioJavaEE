package tn.esprit.twin.ninja.beans;

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

import tn.esprit.twin.ninja.interfaces.SkillServiceLocal;
import tn.esprit.twin.ninja.persistence.Skill;

@Path("skill")
@RequestScoped
public class SkillRessource {

	@EJB(beanName = "SkillService")
	SkillServiceLocal skillService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{ressourceId}")
	public Response addSkill(@PathParam(value="ressourceId")int ressourceId,Skill s) {
		skillService.addSkill(ressourceId,s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	
	/*@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/affectSkills")
	public Response addSkillsToRessource(@QueryParam("ressourceId") int ressourceId, @QueryParam("skillId") int skillId) {

		if (skillService.affectSkills(ressourceId, skillId))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}*/

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateSkills")
	public Response updateRessourceSkill(Skill skill) {

		if (skillService.updateSkills(skill))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{skillId}")
	public Response deleteSkill(@PathParam("skillId") int skillId) {
		if (skillService.deleteSkills(skillId))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/evaluate")
	public Response evaluateSkills(Skill skill) {

		if (skillService.evaluateSkills(skill))
			return Response.status(Status.OK).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{ressourceId}")
	public Response getSkills(@PathParam("ressourceId")int ressourceId){
		
		skillService.getSkillsByRessource(ressourceId);
		return Response.ok(skillService.getSkillsByRessource(ressourceId)).build();
		
	}

}
