package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Message;
import tn.esprit.twin.ninja.persistence.Request;

@Remote
public interface ClientServiceRemote {

	public void addRequest(int clientId, Request request);
	public void sendMessageToRessource(Message message, int ressourceId);
	public void addClient(Client c);
	public void deleteClient(Client c);
	public void updateClient(Client c);
	public List<Client> getAllClients();
	public Client getClientById(int idClient);
}
