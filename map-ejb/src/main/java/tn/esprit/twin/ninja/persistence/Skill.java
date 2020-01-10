package tn.esprit.twin.ninja.persistence;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.Cascade;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
import org.hibernate.annotations.CascadeType;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Skill implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private SkillName name;
	@Column(nullable = true)
	private int rating;
	@JsonIgnore
	@ManyToOne
	private Ressource ressource;
	@ManyToOne
	@JsonIgnore
	JobOffer jobOffer;
	private String photo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
	@JsonIgnore
	@Cascade(CascadeType.DELETE)
	private Request request;

	public Skill() {
		super();
	}

	public Ressource getRessource() {
		return ressource;
	}

	public SkillName getName() {
		return name;
	}

	public void setName(SkillName name) {
		this.name = name;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
