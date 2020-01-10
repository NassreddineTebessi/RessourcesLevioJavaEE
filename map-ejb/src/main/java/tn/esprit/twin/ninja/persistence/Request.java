package tn.esprit.twin.ninja.persistence;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;

@Entity
public class Request implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String resourceType;
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	private String context;
	private Boolean status;

	@OneToMany(mappedBy = "request",fetch = FetchType.EAGER)
	@Cascade(CascadeType.DELETE)
	private Set<Skill> skills = new HashSet<>();
	@ManyToMany (mappedBy="requests", fetch = FetchType.EAGER)
	private List<Client> clients;

	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public Set<Skill> getSkills() {
		return skills;
	}
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
