
package tn.esprit.twin.ninja.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.interfaces.ProjectServiceLocal;
import tn.esprit.twin.ninja.interfaces.ProjectServiceRemote;
import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Ressource;

@Stateless
public class ProjectService implements ProjectServiceLocal, ProjectServiceRemote {

	@PersistenceContext(unitName="LevioMap-ejb")
	EntityManager em;
	
	@Override
	public void addProject(Project p) {
		em.persist(p);
	}

	@Override
	public void deleteProject(int idProject) {
		//em.remove(em.find(Project.class, idProject));
			Project p = em.find(Project.class, idProject);
			p.setArchived(true);
	}

	@Override
	public void updateProject(Project p) {
		Project project = em.find(Project.class, p.getId());
		project.setAdress(p.getAdress());
		project.setPhoto(p.getPhoto());
		project.setArchived(p.isArchived());
		project.setName(p.getName());
		project.setStart_date(p.getStart_date());
		project.setEnd_date(p.getEnd_date());
		project.setNum_ressource_all(p.getNum_ressource_all());
		project.setNum_ressource_levio(p.getNum_ressource_levio());
		project.setType(p.getType());
		project.setClient(p.getClient());
	}
	
	@Override
	public void affectProjecttoClient(int projectId, int clientId) {
		Project p = em.find(Project.class, projectId);
		Client c = em.find(Client.class, clientId);
		p.setClient(c);
	}

	@Override
	public List<Project> getAllProject() {
		return em.createQuery("SELECT p FROM Project p where archived=false", Project.class).getResultList();
	}

	@Override
	public List<Project> getProjectByClient(int idClient) {
		Client client = em.find(Client.class, idClient);
		return em.createQuery(
				"SELECT p FROM Project p WHERE p.client =:client and archived=false",Project.class)
				.setParameter("client", client).getResultList();
	}

	@Override
	public List<Project> getProjectByAdress(String adress) {
		return em.createQuery(
				"SELECT p FROM Project p WHERE p.adress =:adress and archived=false",Project.class)
				.setParameter("adress", adress).getResultList();
	}

	@Override
	public void addPhotoProject(int id, String fileName) {
		Project p = em.find(Project.class, id);
		p.setPhoto(fileName);
		
	}	
	@Override
		public void addProjectDotnet(Project p, int idClient) {
			em.persist(p);
			Client c = em.find(Client.class, idClient);
			p.setClient(c);
			
		}		
		
		@Override
	public Project getProjectById(int idProject) {
			return em.createQuery("SELECT p FROM Project p WHERE p.id=:idProject", Project.class)
					.setParameter("idProject", idProject).getSingleResult();
		}
	
	
}