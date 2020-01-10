package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import tn.esprit.twin.ninja.persistence.Skill;

public interface SkillServiceLocal {

	public void addSkill(int ressourceId, Skill s);

	public boolean updateSkills(Skill skill);

	public boolean affectSkills(int ressourceId, int skillId);

	public boolean deleteSkills(int skillId);

	public boolean evaluateSkills(Skill skill);

	public List<Skill> getBestSkills();

	public List<Skill> getSkillsByRessource(int ressourceId);


}
