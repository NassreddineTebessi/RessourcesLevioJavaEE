package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.twin.ninja.persistence.Request;
import tn.esprit.twin.ninja.persistence.UserRoles;

@Remote
public interface UserServiceRemote {

	public boolean hasRole(int userId, UserRoles role);
	public void treatClientRequest(int userId, int requestId);
	public List<Request> getAllRequests();
	public List<Request> getTreatedRequests();
	public List<Request> getUnTreatedRequests();

}
