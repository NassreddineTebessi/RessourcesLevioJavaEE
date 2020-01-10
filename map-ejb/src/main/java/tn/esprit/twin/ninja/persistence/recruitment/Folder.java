package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;
import java.util.List;

import javax.jms.JMSSessionMode;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table
public class Folder implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@OneToMany(mappedBy="folder",cascade=CascadeType.PERSIST)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Letter> listeLetter;
	@Enumerated(EnumType.STRING)
	private StateLetter stateLetter=StateLetter.notSigned;
	@Enumerated(EnumType.STRING)
	private StateFolder stateFolder=StateFolder.notComplited;
	@Enumerated(EnumType.STRING)
	private StateMinister stateMinister=StateMinister.notSend;
	@OneToOne(mappedBy="folder",cascade=CascadeType.PERSIST)
	@JsonBackReference
	private Application application;
	
	
	
	
	
	
	
	public StateMinister getStateMinister() {
		return stateMinister;
	}
	public void setStateMinister(StateMinister stateMinister) {
		this.stateMinister = stateMinister;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StateFolder getStateFolder() {
		return stateFolder;
	}
	public void setStateFolder(StateFolder stateFolder) {
		this.stateFolder = stateFolder;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
		
	public Folder() {
		super();
	}
	public List<Letter> getListeLetter() {
		return listeLetter;
	}
	public void setListeLetter(List<Letter> listeLetter) {
		this.listeLetter = listeLetter;
	}
	public StateLetter getStateLetter() {
		return stateLetter;
	}
	public void setStateLetter(StateLetter stateLetter) {
		this.stateLetter = stateLetter;
	}
	
	
	

}
