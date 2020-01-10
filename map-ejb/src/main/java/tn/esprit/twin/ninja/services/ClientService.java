package tn.esprit.twin.ninja.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.communication.MailSender;
import tn.esprit.twin.ninja.interfaces.ClientServiceLocal;
import tn.esprit.twin.ninja.interfaces.ClientServiceRemote;
import tn.esprit.twin.ninja.persistence.*;

@Stateless
public class ClientService implements ClientServiceLocal{

	@PersistenceContext(unitName="LevioMap-ejb")
	private EntityManager em;

	@Override
	public void addRequest(int clientId, Request request) throws MessagingException {
		Client client = em.find(Client.class, clientId);
		client.getRequests().add(request);
		em.persist(request);
		em.flush();
		for(Skill s : request.getSkills()) {
			s.setRequest(request);
			em.persist(s);
			em.flush();
		}
		MailSender mailSender = new MailSender();
		String messageBody = "Dear Supervisor, <br>"
				+ "You have a new resource request from the client : " + client.getName() + ". <br>"
				+ "<b>Request details:</b> <br>"
				+ "Request context: " + request.getContext() + "<br>"
				+ "Resource type: " + request.getResourceType() + "<br>"
				+ "Delivery date: " + request.getDeliveryDate() + "<br>"
				+ "Resource profile: " + this.skillsToString(request.getSkills());
		try {
			mailSender.sendMessage(
					"smtp.gmail.com",
					"mohamed@pixelwilderness.com",
					"V4Vendetta",
					"587",
					"true",
					"true",
					"mohamed@pixelwilderness.com",
					"New Resource Request",
					messageBody
			);
		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void deleteRequestById(int requestId) {
		Request request = em.find(Request.class, requestId);
		em.createNativeQuery("delete from client_request where requests_id = :id").setParameter("id", requestId).executeUpdate();
		for(Skill s : request.getSkills()) {
			s.setRequest(null);
		}
		request.getSkills().clear();
		em.remove(request);
		em.flush();
		System.out.println(requestId);
	}
	@Override
	public void sendMessageToRessource(Message message,int currentClient, int ressourceId) throws MessagingException {
		Ressource resource = em.find(Ressource.class, ressourceId);
		Client client = em.find(Client.class, currentClient);
		Conversation conversation = new Conversation();
		conversation.setState("open");
		em.persist(conversation);
		em.flush();
		message.setFromUser(client);
		message.setToUser(resource);
		message.setConversation(conversation);
		em.persist(message);
		em.flush();
		MailSender mailSender = new MailSender();
		mailSender.sendMessage(
				"smtp.gmail.com",
				"mohamed@pixelwilderness.com",
				"V4Vendetta",
				"587",
				"true",
				"true",
				resource.getEmail(),
				message.getSubject() + ": " + message.getType(),
				message.getMessage()+ resource.getEmail() + ressourceId
		);
	}

	@Override
	public List<Conversation> getOpenedConversations(int clientId) {
		return em.createQuery("SELECT c FROM Conversation c where c.state = 'open' and (c.fromUser.id = :user OR c.toUser.id = :user)", Conversation.class).setParameter("user", clientId).getResultList();
	}

	@Override
	public void respondToAMessage(int conversationId,int currencClient, Message message) throws MessagingException {
		//Rigel reciepient
		Conversation conversation = em.find(Conversation.class, conversationId);
		Client cc = em.find(Client.class, currencClient);
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
	public List<Conversation> getConversationByType(int currentClient, MessageType messageType) {
		Client cc = em.find(Client.class, currentClient);
		return  em.createQuery("SELECT  m.conversation from Message m where m.conversation.state = 'open' and m.type = :msgType and (m.conversation.toUser.email = :user or m.conversation.fromUser.email = :user) order by m.createDate", Conversation.class).setParameter("msgType" , messageType).setParameter("user", cc.getEmail()).getResultList();
	}

	@Override
	public List<Conversation> getConversationBySubject(String subject, int currentClient) {
		Client cc = em.find(Client.class, currentClient);
		return  em.createQuery("select m.conversation from Message m where m.subject LIKE :sub and (m.conversation.fromUser.id = :current or m.conversation.toUser.id = :current)", Conversation.class).setParameter("sub", "%"+subject+"%").setParameter("current", cc.getId()).getResultList();
	}
	@Override
	public List<Message> getConversationMessages(int conversationId, int currentId) {
		Client cc = em.find(Client.class, currentId);
		return em.createQuery("select m from Message m where m.conversation.id = :id order by m.createDate", Message.class).setParameter("id", conversationId).getResultList();
	}
	@Override
	public void extractConversationMessages(int conversationId, int currentId) throws FileNotFoundException {
		List<Message> listConversation = this.getConversationMessages(conversationId, currentId);
		PrintWriter pw = new PrintWriter(new File("C:/Users/COMPUTER/workspace/backup/Messages.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Conversation id");
		sb.append(',');
		sb.append("createDate");
		sb.append(',');
		sb.append("state");
		sb.append(',');
		sb.append("type");
		sb.append(',');
		sb.append("subject");
		sb.append(',');
		sb.append("message");
		sb.append('\n');
		for(Message m : listConversation) {
			sb.append(m.getConversation().getId());
			sb.append(',');
			sb.append(m.getCreateDate());
			sb.append(',');
			sb.append(m.getConversation().getState());
			sb.append(',');
			sb.append(m.getType());
			sb.append(',');
			sb.append(m.getSubject());
			sb.append(',');
			sb.append(m.getMessage());
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");
	}

	@Override
	public void addClient(Client c) {
		em.persist(c);
		
	}

	@Override
	public void deleteClient(int id) {
		//em.remove(em.find(Client.class, idClient));
		Client client = em.find(Client.class, id);
		client.setArchived(true);
	}

	@Override
	public void updateClient(int id,Client c) {
		Client client = em.find(Client.class, id);
		client.setName(c.getName());
		client.setArchived(c.isArchived());
		client.setCategory(c.getCategory());
		client.setType(c.getType());
	}

	@Override
	public List<Client> getAllClients() {
		return em.createQuery("SELECT c FROM Client c where archived=false", Client.class).getResultList();
	}

	@Override
	public Client getClientById(int idClient) {
		return em.createQuery("SELECT c FROM Client c WHERE c.id=:idClient", Client.class)
				.setParameter("idClient", idClient).getSingleResult();
	}
	private String skillsToString(Set<Skill> skills) {
		String returnedSkills = " ";
		for(Skill s : skills) {
			returnedSkills+=s.getName() + ", ";
		}
		return  returnedSkills;
	}


}