package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.type.TrueFalseType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table

public class ApplicationTest implements Serializable {
	
	@EmbeddedId
	private ApplicationTestPK appPK;	
	@ManyToOne
	@MapsId("idApp")
	@JsonIgnore
	private Application application;
	@ManyToOne
	@MapsId("idTest")
	private Test test;
	@Column(nullable=true)
	private int note;
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public ApplicationTest() {
		super();
	}
	public ApplicationTest(Application application, Test test) {
		super();
		this.appPK=new ApplicationTestPK(test.getId(), application.getId());
		this.application = application;
		this.test = test;
		//application.getListTest().add(this);
		//test.getListApllication().add(this);
	}
	public ApplicationTest(Application application, Test test, int note) {
		super();
		this.application = application;
		this.test = test;
		this.note = note;
	}
	
	

}
