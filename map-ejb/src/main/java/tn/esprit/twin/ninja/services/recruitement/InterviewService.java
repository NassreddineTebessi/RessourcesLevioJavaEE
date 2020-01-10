package tn.esprit.twin.ninja.services.recruitement;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.interfaces.recruitement.InterviewServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Interview;
import tn.esprit.twin.ninja.persistence.recruitment.StateInterview;

@Stateless
public class InterviewService implements InterviewServiceLocal{
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;

	@Override
	public boolean addInterview(Interview interview,int idapplication) {
		try {
			interview.setApplication(em.find(Application.class, idapplication));
			em.persist(interview);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean setStateInterview(int idInterview, StateInterview state) {
		try {
			Interview interview=em.find(Interview.class, idInterview);
			interview.setStateInterview(state);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean updateDateInterview(Interview interview) {
		try {
			em.merge(interview);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public Interview getInterview(int IdInterview) {
		Interview interview=em.find(Interview.class, IdInterview);
		return interview;
	}
	

}
