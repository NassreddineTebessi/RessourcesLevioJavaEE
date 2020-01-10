package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.twin.ninja.persistence.Ressource;
@Entity
@Table

public class Application implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.TIMESTAMP )
	private Date date_app= new Date();
	@Enumerated(EnumType.STRING)
	private State state=State.notApplay;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private Folder folder;
	@OneToMany(mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ApplicationTest> listTest;
	@OneToMany(mappedBy="application")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Interview> listInterview;
	private String Description;
	@ManyToOne
	@JsonIgnore
	private JobOffer jobOffer;
	@ManyToOne
	@JsonIgnore
	private Ressource ressource;
	
	
	public JobOffer getJobOffer() {
		return jobOffer;
	}
	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public List<ApplicationTest> getListTest() {
		return listTest;
	}
	public void setListTest(List<ApplicationTest> listTest) {
		this.listTest = listTest;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_app() {
		return date_app;
	}
	public void setDate_app(Date date_app) {
		this.date_app = date_app;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	public List<Interview> getListInterview() {
		return listInterview;
	}
	public void setListInterview(List<Interview> listInterview) {
		this.listInterview = listInterview;
	}
	public Application(Date date_app, State state) {
		super();
		this.date_app = date_app;
		this.state = state;
	}
	public Application() {
		super();
	}
	public void addTest(Test test){
		ApplicationTest ap= new ApplicationTest(this, test);
		listTest.add(ap);
		test.getListApllication().add(ap);
	}
	public Ressource getRessource() {
		return ressource;
	}
	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}
	
	
	

}
