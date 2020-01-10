package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.twin.ninja.persistence.Skill;

@Remote
public interface SkillServiceRemote {

	public void addSkill(Skill s);

	public boolean updateSkills(Skill skill);

	public boolean affectSkills(int ressourceId, int skillId);

	public boolean deleteSkills(int skillId);

	public boolean evaluateSkills(Skill skill);

	public List<Skill> getBestSkills();

}
