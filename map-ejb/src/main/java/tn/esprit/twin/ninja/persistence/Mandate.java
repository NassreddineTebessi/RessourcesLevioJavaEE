package tn.esprit.twin.ninja.persistence;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.Date;
@JsonRootName("Mandate")
@Entity
@Table(name="Mandate")
public class Mandate implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date StartDate;
    @Temporal(TemporalType.DATE)
    private Date EndDate;
    @Temporal(TemporalType.DATE)
    private Date ActualEndDate ;
    @Column(nullable = true)
    private float Montant ;
    @Column(nullable = true)
    private Boolean Archived=false;
    
    @ManyToOne
    private Ressource ressource;
    @ManyToOne
    private Project project;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public Date getActualEndDate() {
		return ActualEndDate;
	}
	public void setActualEndDate(Date actualEndDate) {
		ActualEndDate = actualEndDate;
	}
	public float getMontant() {
		return Montant;
	}
	public void setMontant(float montant) {
		Montant = montant;
	}
	public Boolean getArchived() {
		return Archived;
	}
	public void setArchived(Boolean archived) {
		Archived = archived;
	}
	public Ressource getRessource() {
		return ressource;
	}
	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Mandate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Mandate(int id, Date startDate, Date endDate, Date actualEndDate, float montant, Boolean archived,
			Ressource ressource, Project project) {
		super();
		this.id = id;
		StartDate = startDate;
		EndDate = endDate;
		ActualEndDate = actualEndDate;
		Montant = montant;
		Archived = archived;
		this.ressource = ressource;
		this.project = project;
	}
    

    


}
