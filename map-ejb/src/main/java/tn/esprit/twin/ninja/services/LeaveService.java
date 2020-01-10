package tn.esprit.twin.ninja.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.interfaces.LeaveServiceLocal;
import tn.esprit.twin.ninja.persistence.Leave;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.RessourceState;

@Stateless
public class LeaveService implements LeaveServiceLocal {

	@PersistenceContext(unitName = "LevioMap-ejb")
	EntityManager em;

	@Override
	public void addLeave(int ressourceId,Leave l) {
		
		Ressource r = em.find(Ressource.class, ressourceId);
		em.persist(l);
		l.setRessource(r);
		l.setAllDay(true);
		r.setState(RessourceState.notAvailable);

	}

	@Override
	public boolean updateLeave(Leave l,int id) {

		Leave leave = em.find(Leave.class, id);
		leave.setStart(l.getStart());
		leave.setEnd(l.getEnd());
		return true;
	}

	@Override
	public boolean deleteLeave(int leaveId) {
		try {
			Leave l = em.find(Leave.class, leaveId);
			em.remove(l);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Leave> getLeavesByRessource(int ressourceId) {
		return em.createQuery("SELECT l FROM Leave l WHERE l.ressource.id=:ressourceId", Leave.class)
				.setParameter("ressourceId", ressourceId).getResultList();
	}

	@Override
	public List<Leave> getAllLeave() {
		return em.createQuery("SELECT l from Leave l", Leave.class).getResultList();

	}

}
