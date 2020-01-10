package tn.esprit.twin.ninja.interfaces;

import javax.ejb.Local;
import javax.mail.MessagingException;

import tn.esprit.twin.ninja.persistence.*;

import java.util.List;
import java.util.Set;


@Local
public interface UserServiceLocal {


	public boolean hasRole(int userId, UserRoles role);
	public void treatClientRequest(int requestId);
	public List<Request> getAllRequests();
	public List<Request> getTreatedRequests();
	public List<Request> getUnTreatedRequests();
	public void sendMessageToClient(Message message) throws MessagingException;

	public int addUser(User u);
	public boolean updateUser(User u);
	boolean modifyUser(User oldUser, User newUser);
	public User findOne(int id);
	public User Authenticate(String email, String pwd);
	public boolean login(String email, String password);
	public User getUserInfo(String email, String password);
	public List<Ressource> getRessourceBySkills(Set<Skill> skills);
	public void deleteTreatedRequests();
	public void deleteRequest(int requestId);



}
