package tn.esprit.twin.ninja.services.recruitement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.twin.ninja.interfaces.recruitement.JobOfferLocal;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.Skill;
import tn.esprit.twin.ninja.persistence.SkillName;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
@Stateless
public class JobOfferService implements JobOfferLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;
	@Override
	public int addJobOffer(JobOffer jobOffer) {
		em.persist(jobOffer);
		return jobOffer.getId();
	}

	@Override
	public boolean updateOffer(JobOffer jobOffer) {
		try {
			em.merge(jobOffer);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}

	@Override
	public boolean removeJobOffer(int idJobOffer) {
		try {
			JobOffer jo=em.find(JobOffer.class, idJobOffer);
			em.remove(jo);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public JobOffer getJobOffer(int idJobOffer) {
		
		return em.find(JobOffer.class, idJobOffer);
	}

	@Override
	public List<JobOffer> getAllJobOffer() {
		Query query = em.createQuery("SELECT a from JobOffer a");	
		return query.getResultList();
	}

	@Override
	public List<JobOffer> getJobOfferBySkills(int idres) {
		Ressource ressource=em.find(Ressource.class, idres);
		List<SkillName> l1=new ArrayList<SkillName>();
		
		System.out.println(ressource);
	    List<JobOffer> FinalList = new ArrayList<JobOffer>();
	    for(int i=0;i<getAllJobOffer().size();i++){
	    	for(Skill s:ressource.getSkills()){
				l1.add(s.getName());
			}
	    	List<SkillName> l2=new ArrayList<SkillName>();
	    	for(Skill s:getAllJobOffer().get(i).getListSkills()){
	    		l2.add(s.getName());	
	    	}
	    	l1.retainAll(l2);
	    	if((l1.size()==ressource.getSkills().size())){
	        	FinalList.add(getAllJobOffer().get(i));
	        }
	    
	    }
		return FinalList;
	}

}
