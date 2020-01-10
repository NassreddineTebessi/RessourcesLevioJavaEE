package tn.esprit.twin.ninja.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity

public class Organigramme {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String Description;
	private String Logo;
	private int ResLevel;
	private int SupLevel;
	@ManyToOne
	private Client client;
	@ManyToOne
	private Ressource resource;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	
	public Ressource getResource() {
		return resource;
	}
	public void setResource(Ressource resource) {
		this.resource = resource;
	}
	public int getResLevel() {
		return ResLevel;
	}
	public void setResLevel(int resLevel) {
		ResLevel = resLevel;
	}
	public int getSupLevel() {
		return SupLevel;
	}
	public void setSupLevel(int supLevel) {
		SupLevel = supLevel;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@Override
	public String toString() {
		return "Organigramme [id=" + id + ", Description=" + Description + ", Logo=" + Logo + ", ResLevel=" + ResLevel
				+ ", SupLevel=" + SupLevel + ", client=" + client + "]";
	}
	public Organigramme() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
