package tn.esprit.twin.ninja.services.recruitement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tn.esprit.twin.ninja.interfaces.recruitement.ApplicationServiceLocal;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
import tn.esprit.twin.ninja.persistence.recruitment.State;

@Stateless
public class ApplicationService implements ApplicationServiceLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;
	@Override
	public int addApplication(Application a,int idRess,int idJob) {
		a.setJobOffer(em.find(JobOffer.class, idJob));
		a.setRessource(em.find(Ressource.class, idRess));
		Folder f= new Folder();
		a.setFolder(f);
		em.persist(a);
		return a.getId();
	}

	@Override
	public Application getApplication(int idApplication) {
		
		return em.find(Application.class, idApplication);
	}

	@Override
	public boolean deleteApplication(int idApplication) {
		Application a = em.find(Application.class, idApplication);
		try {
			em.remove(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean setStateApplication(Application application) {
		try {em.merge(application);
			return true;
		} catch (Exception e) {
			return false;
		}

		
	}

	@Override
	public List<Application> getAllApplication() {
		Query query = em.createQuery("SELECT a from Application a");	
		//query.getResultList().stream().forEach(p->System.out.println("eeeeeee"));
		return query.getResultList();
		
	}

	@Override
	public List<Application> getApplicationByState(State state) {
		Query query = em.createQuery("SELECT a from Application a where a.state=:state");	
		query.setParameter("state", state);
		//query.getResultList().stream().forEach(p->System.out.println("eeeeeee"));
		return query.getResultList();
	}

	@Override
	public boolean assignRessource(int idr, int idp) {
		try {
			Ressource r=em.find(Ressource.class, idr);
			Ressource p=em.find(Ressource.class, idr);
			r.setAssigned(p);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public List<Application> getApplicationId(int idressource) {
		
		return em.find(Ressource.class, idressource).getListApplication();
	}
	

}
