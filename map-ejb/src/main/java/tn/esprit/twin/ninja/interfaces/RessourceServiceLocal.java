package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import tn.esprit.twin.ninja.persistence.*;

import javax.mail.MessagingException;

public interface RessourceServiceLocal {

	public boolean addRessource(Ressource r);

	public boolean updateRessource(Ressource res, int id);

	public boolean deleteRessource(int ressourceId);

	public List<Ressource> getAllRessources();

	public Ressource getRessourceById(int ressourceId);

	boolean affectRessourceToProject(int projectId, int ressourceId);

	public List<Ressource> getRessourceByName(String FirstName);

	public void addPhotoRessource(int ressourceId, String photo);

	public void sendMessageToClient(Message message,int currentResource, int clientId) throws MessagingException;
	
	public List<Conversation> getOpenedConversations(int ResourceId);

	public void respondToAMessage(int conversationId,int currencResource, Message message) throws MessagingException;

	public List<Conversation> getConversationByType(int currentResource, MessageType messageType);

	public List<Conversation> getConversationBySubject(String subject, int currentResource);

	public List<Project> getAvailableProjects(int ressourceId);

	public List<Project> getProjectsByRessource(int ressourceId);









}
