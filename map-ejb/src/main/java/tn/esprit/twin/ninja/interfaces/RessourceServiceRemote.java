package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import javax.mail.MessagingException;

import tn.esprit.twin.ninja.persistence.Conversation;
import tn.esprit.twin.ninja.persistence.Leave;
import tn.esprit.twin.ninja.persistence.Message;
import tn.esprit.twin.ninja.persistence.MessageType;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.Skill;

public interface RessourceServiceRemote {

	public boolean addRessource(Ressource r);

	public boolean updateRessource(Ressource res, int id);

	public boolean deleteRessource(int ressourceId);

	public List<Ressource> getAllRessources();

	public Ressource getRessourceById(int ressourceId);

	public boolean affectRessourceToProject(int projectId, int ressourceId);

	public List<Ressource> getRessourceByName(String FirstName);

	public void addPhotoRessource(int ressourceId, String photo);

	public void sendMessageToClient(Message message,int currentResource, int clientId) throws MessagingException;

	public List<Conversation> getOpenedConversations(int ResourceId);

	public void respondToAMessage(int conversationId,int currencResource, Message message) throws MessagingException;

	public List<Conversation> getConversationByType(int currentResource, MessageType messageType);

	public List<Conversation> getConversationBySubject(String subject, int currentResource);


}
