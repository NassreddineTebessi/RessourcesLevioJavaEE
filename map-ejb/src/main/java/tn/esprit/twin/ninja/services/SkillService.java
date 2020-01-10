package tn.esprit.twin.ninja.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.interfaces.SkillServiceLocal;
import tn.esprit.twin.ninja.persistence.Leave;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.Skill;

@Stateless
public class SkillService implements SkillServiceLocal {

	@PersistenceContext(unitName = "LevioMap-ejb")
	EntityManager em;

	@Override
	public void addSkill(int ressourceId,Skill s) {
		Ressource r = em.find(Ressource.class, ressourceId);
		em.persist(s);
		s.setRessource(r);

	}

	@Override
	public boolean affectSkills(int ressourceId, int skillId) {
		try {
			Ressource r = em.find(Ressource.class, ressourceId);
			Skill s = em.find(Skill.class, skillId);
			s.setRessource(r);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateSkills(Skill skill) {
		try {
			Skill s = em.find(Skill.class, skill.getId());
			em.merge(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteSkills(int skillId) {
		try {
			Skill s = em.find(Skill.class, skillId);
			em.remove(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean evaluateSkills(Skill skill) {
		try {
			Skill s = em.find(Skill.class, skill.getId());
			s.setRating(skill.getRating());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Skill> getBestSkills() {
		return em.createQuery("SELECT s FROM Skill s ORDER BY s.rating DESC", Skill.class).getResultList();
	}

	@Override
	public List<Skill> getSkillsByRessource(int ressourceId) {
		return em.createQuery("SELECT s FROM Skill s WHERE s.ressource.id=:ressourceId", Skill.class)
				.setParameter("ressourceId", ressourceId).getResultList();

	}



}
