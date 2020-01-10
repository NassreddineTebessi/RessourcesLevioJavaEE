package tn.esprit.twin.ninja.services;

import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

import javax.persistence.TypedQuery;

import tn.esprit.twin.ninja.communication.MailSender;
import tn.esprit.twin.ninja.interfaces.UserServiceLocal;
import tn.esprit.twin.ninja.interfaces.UserServiceRemote;
import tn.esprit.twin.ninja.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Stateless
public class UserService implements UserServiceLocal {
    @PersistenceContext(unitName="LevioMap-ejb")
    EntityManager em;
	
	public boolean hasRole(int userId, UserRoles role) {
		User user = em.find(User.class, userId);
		return user.getRole() == role;
	}


	@Override
	public void treatClientRequest(int requestId) {
			Request request = em.find(Request.class, requestId);
			request.setStatus(true);
	}

	@Override
	public List<Request> getAllRequests() {
		return  em.createQuery("select r from Request r", Request.class).getResultList();
	}

	@Override
	public List<Request> getTreatedRequests() {
		return  em.createQuery("select r from Request r where r.status = true", Request.class).getResultList();
	}

	@Override
	public void sendMessageToClient(Message message) throws MessagingException {
		MailSender mailSender = new MailSender();
		mailSender.sendMessage("smtp.gmail.com", "mohamed@pixelwilderness.com", "V4Vendetta", "587", "true", "true",
		message.getToUserEmail(), message.getSubject() + ": " + message.getType(), message.getMessage());
	}
	@Override
	public List<Request> getUnTreatedRequests() {
		return  em.createQuery("select r from Request r where r.status = false or r.status is null", Request.class).getResultList();
	}
	@Override
	public int addUser(User u) {
		String token = this.getSaltString();
		u.setToken(token);
		String pwd = this.encrypt(u.getPassword());
		u.setPassword(pwd);
		em.persist(u);
		return u.getId();
	}


	@Override
	public boolean updateUser(User u) {
		u.setPassword(this.encrypt(u.getPassword()));
			em.merge(u);
			return true;
	
		
		
	}


	@Override
	public boolean modifyUser(User oldUser, User newUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public User findOne(int id) {
		User u = em.find(User.class, id);
		return u;

	}
	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
}
	public String encrypt(String password) {
		String crypte = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			crypte = crypte + (char) c;
		}
		// System.out.println("token: " + crypte);
		return crypte;
	}


	@Override
	public User Authenticate(String email, String pwd) {
		try{
			User user = null;
			String crypted_pwd = encrypt(pwd);
			TypedQuery<User> tq = em.createQuery(
					"select e from User e where e.email=:email and e.password=:password",User.class);
		

			tq.setParameter("email", email);
			System.out.println("email: " + email);
			tq.setParameter("password", crypted_pwd);
			System.out.println("password:   " + crypted_pwd);
			user = tq.getResultList().get(0);
			System.out.println(user+"aaaaaaaaaa");
			User u = em.find(User.class, user.getId());
			
			if (user != null)
				return user;
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Ressource> getRessourceBySkills(Set<Skill> skills) {

		TypedQuery<Ressource> query = em.createQuery("SELECT r FROM Ressource r where r.state = 'available'",Ressource.class);
		List<Ressource> availableResources = query.getResultList();
		List<Ressource> resources = new ArrayList<>();
		for (Ressource r : availableResources) {
			for(Skill rs : skills) {
				for(Skill s : r.getSkills()) {
					if(s.getName() == rs.getName()) {
						resources.add(r);
					}
				}
			}
		}
		return resources;

	}
	
	@Override
	public void deleteTreatedRequests() {
		List<Request> listRequests = new ArrayList<>();
		listRequests = em.createQuery("select r from Request r where r.status = :stat",Request.class).setParameter("stat", true).getResultList();
		for(Request r : listRequests) {
			em.createNativeQuery("delete from client_request where requests_id = :id").setParameter("id", r.getId()).executeUpdate();
			for(Skill s : r.getSkills()) {
				s.setRequest(null);
			}
			r.getSkills().clear();
			em.remove(r);
		}
		
	}
	@Override
	public void deleteRequest(int requestId) {
		System.out.println(requestId);
		//Request request = em.find(Request.class, requestId);
	//	em.remove(request);
	}


	@Override
	public boolean login(String email, String password) {
		try {
			User u =  em.createQuery("select e from User e where e.email = :email", User.class).setParameter("email", email).getSingleResult();
			 if(u == null) {
				 return false;
			 } else {
				 if (u.getPassword().equals(password)) {
					 return true;
				 } else {
					 return false;
				 }
			 }
		} catch (NoResultException e) {
			System.out.println("no result found in login");
			return false;
		}
	
		
	}
	@Override
	public User getUserInfo(String email, String password) {
		return em.createQuery("select e from User e where e.email = :email", User.class).setParameter("email", email).getSingleResult();
	}
	
}
