package tn.esprit.twin.ninja.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import tn.esprit.twin.ninja.persistence.recruitment.Application;

@JsonIgnoreProperties({ "mandate" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Entity
public class Ressource extends User implements Serializable {

	private int seniority;
	private String sector;
	@Enumerated(EnumType.STRING)
	private RessourceState state;
	private String profile;
	private String contract_type;
	@OneToMany(mappedBy = "ressource")
	@LazyCollection(LazyCollectionOption.FALSE)
	//@JsonIgnore
	private List<Leave> leaves;
	@OneToMany(mappedBy = "ressource")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Skill> skills;
	@JsonIgnore
	@OneToMany(mappedBy = "ressource")
	private List<Mandate> mandate;
	@ManyToOne
	private Project project;
 
	@ManyToOne
	@JsonIgnore
	private Ressource assigned;
	@JsonIgnore
	@OneToMany(mappedBy="assigned")
	@LazyCollection(LazyCollectionOption.FALSE)
	 
	private List<Ressource> listAssigned;
	@JsonIgnore
	@OneToMany(mappedBy="resource")
	private List<Organigramme> organigramme;
	@JsonIgnore
	@OneToMany(mappedBy="ressource")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Application> listApplication;

	public Project getProject() {
		return project;
	}

	
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	public List<Organigramme> getOrganigramme() {
		return organigramme;
	}

	public void setOrganigramme(List<Organigramme> organigramme) {
		this.organigramme = organigramme;
	}

	public Ressource(int seniority, String sector, RessourceState state, String profile, String contract_type,
			List<Leave> leaves, List<Skill> skills) {
		super();
		this.seniority = seniority;
		this.sector = sector;
		this.state = state;
		this.profile = profile;
		this.contract_type = contract_type;
		this.leaves = leaves;
		this.skills = skills;
	}

	public Ressource() {
		super();
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public RessourceState getState() {
		return state;
	}

	public void setState(RessourceState state) {
		this.state = state;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getContract_type() {
		return contract_type;
	}

	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Mandate> getMandate() {
		return mandate;
	}

	public void setMandate(List<Mandate> mandate) {
		this.mandate = mandate;
	}

	public Ressource getAssigned() {
		return assigned;
	}

	public void setAssigned(Ressource assigned) {
		this.assigned = assigned;
	}

	public List<Ressource> getListAssigned() {
		return listAssigned;
	}

	public void setListAssigned(List<Ressource> listAssigned) {
		this.listAssigned = listAssigned;
	}

	public List<Application> getListApplication() {
		return listApplication;
	}

	public void setListApplication(List<Application> listApplication) {
		this.listApplication = listApplication;
	}

	
	
	
}