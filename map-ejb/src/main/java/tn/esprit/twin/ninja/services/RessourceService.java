package tn.esprit.twin.ninja.services;

import tn.esprit.twin.ninja.communication.MailSender;
import tn.esprit.twin.ninja.interfaces.RessourceServiceLocal;
import tn.esprit.twin.ninja.persistence.*;

import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

@Stateless
public class RessourceService implements RessourceServiceLocal {

	@PersistenceContext(unitName = "LevioMap-ejb")
	EntityManager em;

	@Override
	public void sendMessageToClient(Message message, int currentResource, int clientId) throws MessagingException {
		Ressource ressource = em.find(Ressource.class, currentResource);
		Client client = em.find(Client.class, clientId);
		Conversation conversation = new Conversation();
		message.setFromUser(ressource);
		message.setToUser(client);
		conversation.setState("open");
		em.persist(conversation);
		em.flush();
		message.setConversation(conversation);
		em.persist(message);
		em.flush();
		MailSender mailSender = new MailSender();
		mailSender.sendMessage("smtp.gmail.com", "mohamed@pixelwilderness.com", "V4Vendetta", "587", "true", "true",
				client.getEmail(), message.getSubject() + ": " + message.getType(), message.getMessage());

}

	@Override
	public List<Conversation> getOpenedConversations(int resourceId) {
		return em.createQuery(
				"SELECT c FROM Conversation c where c.state = 'open' and (c.fromUser.id = :user OR c.toUser.id = :user)",
				Conversation.class).setParameter("user", resourceId).getResultList();
	}

	@Override
	public void respondToAMessage(int conversationId,int currencResource, Message message) throws MessagingException {
		//rigel reciepient
		Conversation conversation = em.find(Conversation.class, conversationId);
		Ressource cr = em.find(Ressource.class, currencResource);
		message.setConversation(conversation);
		em.persist(message);
		MailSender mailSender = new MailSender();
		mailSender.sendMessage(
				"smtp.gmail.com",
				"mohamed@pixelwilderness.com",
				"V4Vendetta",
				"587",
				"true",
				"true",
				"mohamed.abdelhafidh@esprit.tn",
				message.getSubject()+ ": " + message.getType(),
				message.getMessage()
		);

	}

	@Override
	public List<Conversation> getConversationByType(int currentResource, MessageType messageType) {
		Ressource cr = em.find(Ressource.class, currentResource);
		return em
				.createQuery(
						"SELECT  m.conversation from Message m where m.conversation.state = 'open' and m.type = :msgType and (m.conversation.toUser.email = :user or m.conversation.fromUser.email = :user) order by m.createDate",
						Conversation.class)
				.setParameter("msgType", messageType).setParameter("user", cr.getEmail()).getResultList();
	}

	@Override
	public List<Conversation> getConversationBySubject(String subject, int currentResource) {
		Ressource cr = em.find(Ressource.class, currentResource);
		return em
				.createQuery(
						"select m.conversation from Message m where m.subject LIKE :sub and (m.conversation.fromUser.id = :current or m.conversation.toUser.id = :current)",
						Conversation.class)
				.setParameter("sub", "%" + subject + "%").setParameter("current", cr.getId()).getResultList();
	}

	@Override
	public boolean addRessource(Ressource r) {

		if (r.getFirst_name() == null || r.getLast_name() == null || r.getSector() == null
				|| r.getContract_type() == null || r.getEmail() == null) {
			return false;
		} else
			em.persist(r);
		r.setRole(UserRoles.ROLE_RESOURCE);
		r.setState(RessourceState.available);
		Leave l = new Leave();
		em.persist(l);
		l.setRessource(r);
		l.setDescription("No leaves");
		l.setSubject("No leaves");
		l.setStart(new Date(1970, 11, 11));
		l.setEnd(new Date(1970, 11, 11));
		l.setThemeColor("Black");
		return true;

	}

	@Override
	public void addPhotoRessource(int ressourceId, String photo) {

		Ressource r = em.find(Ressource.class, ressourceId);
		r.setPhoto(photo);

	}

	@Override
	public boolean updateRessource(Ressource res, int id) {

		Ressource r = em.find(Ressource.class, id);

		r.setFirst_name(res.getFirst_name());
		r.setLast_name(res.getLast_name());
		r.setContract_type(res.getContract_type());
		r.setEmail(res.getEmail());
		r.setProfile(res.getProfile());
		r.setSector(res.getSector());

		return true;

	}

	@Override
	public boolean deleteRessource(int ressourceId) {
		try {
			Ressource r = em.find(Ressource.class, ressourceId);
			r.setArchived(true);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Ressource> getAllRessources() {

		return em.createQuery("SELECT r FROM Ressource r WHERE r.archived=0", Ressource.class).getResultList();

	}

	@Override
	public Ressource getRessourceById(int ressourceId) {

		return em.createQuery("SELECT r FROM Ressource r WHERE r.id=:ressourceId", Ressource.class)
				.setParameter("ressourceId", ressourceId).getSingleResult();
	}

	@Override
	public List<Ressource> getRessourceByName(String FirstName) {

		return em.createQuery(
				"SELECT r FROM Ressource r WHERE r.archived=0 AND r.first_name LIKE :FirstName OR r.last_name LIKE:FirstName",
				Ressource.class).setParameter("FirstName", "%" + FirstName + "%").getResultList();
	}

	@Override
	public boolean affectRessourceToProject(int projectId,int ressourceId) {
		try {
			Project p = em.find(Project.class, projectId);
			Ressource r = em.find(Ressource.class, ressourceId);
			r.getProject().setNum_ressource_all(r.getProject().getNum_ressource_all()-1);
			r.getProject().setNum_ressource_levio(r.getProject().getNum_ressource_levio()-1);
			r.setProject(p);
			p.setNum_ressource_all(p.getNum_ressource_all()+1);
			p.setNum_ressource_levio(p.getNum_ressource_levio()+1);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Project> getAvailableProjects(int ressourceId) {

		return em.createQuery("SELECT p FROM Project p WHERE p.id in (SELECT r.project.id FROM Ressource r WHERE r.id != :ressourceId "
				+ "and r.project.id != (select r.project.id from Ressource r where r.id = :ressourceId))" , Project.class)
				.setParameter("ressourceId", ressourceId).getResultList();
	}

	@Override
	public List<Project> getProjectsByRessource(int ressourceId) {

		return em.createQuery("SELECT p FROM Project p WHERE p.id in (SELECT r.project.id FROM Ressource r WHERE r.id = :ressourceId)" , Project.class)
				.setParameter("ressourceId", ressourceId).getResultList();
	}

}
