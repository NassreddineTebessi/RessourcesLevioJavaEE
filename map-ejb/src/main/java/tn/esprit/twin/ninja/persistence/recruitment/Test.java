package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table

public class Test implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String typeTest;
	private String version;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Question> listQuestion;
	@JsonIgnore
	@OneToMany(mappedBy="test", cascade= CascadeType.ALL, orphanRemoval=true)
	private List<ApplicationTest> listApllication;
	
	
	public List<ApplicationTest> getListApllication() {
		return listApllication;
	}
	public void setListApllication(List<ApplicationTest> listApllication) {
		this.listApllication = listApllication;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeTest() {
		return typeTest;
	}
	public void setTypeTest(String typeTest) {
		this.typeTest = typeTest;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Test(String typeTest, String version) {
		super();
		this.typeTest = typeTest;
		this.version = version;
	}
	public Test() {
		super();
	}
	public List<Question> getListQuestion() {
		return listQuestion;
	}
	public void setListQuestion(List<Question> listQuestion) {
		this.listQuestion = listQuestion;
	}
	

}
