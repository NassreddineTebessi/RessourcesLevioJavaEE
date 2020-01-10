package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.Skill;
@Entity
@Table
public class JobOffer implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String mission;
	private String required_profile;
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginning;
	private String experience;
	private String function;
	private int nbPoste;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expDate;
	@OneToMany(mappedBy = "jobOffer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Skill> listSkills;
	@OneToMany(mappedBy="jobOffer")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Application> listApplicant;
	
	public int getNbPoste() {
		return nbPoste;
	}
	public void setNbPoste(int nbPoste) {
		this.nbPoste = nbPoste;
	}
	public List<Application> getListApplicant() {
		return listApplicant;
	}
	public void setListApplicant(List<Application> listApplicant) {
		this.listApplicant = listApplicant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getRequired_profile() {
		return required_profile;
	}
	public void setRequired_profile(String required_profile) {
		this.required_profile = required_profile;
	}
	public Date getBeginning() {
		return beginning;
	}
	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public List<Skill> getListSkills() {
		return listSkills;
	}
	public void setListSkills(List<Skill> listSkills) {
		this.listSkills = listSkills;
	}
	public JobOffer() {
		super();
	}
	

}
