package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table

public class Question implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Subject;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String validChoise;
	@ManyToMany(mappedBy="listQuestion")
	@JsonIgnore
	private List<Test> listTest;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}
	public String getValidChoise() {
		return validChoise;
	}
	public void setValidChoise(String validChoise) {
		this.validChoise = validChoise;
	}
	public List<Test> getListTest() {
		return listTest;
	}
	public void setListTest(List<Test> listTest) {
		this.listTest = listTest;
	}
	public Question(String subject, String choice1, String choice2, String choice3, String choice4,
			String validChoise) {
		super();
		Subject = subject;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.validChoise = validChoise;
	}
	public Question() {
		super();
	}
	
	

}
