package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.twin.ninja.persistence.Leave;

@Remote
public interface LeaveServiceRemote {

	public List<Leave> getAllLeave();

	public void addLeave(int ressourceId, Leave l);

	public boolean updateLeave(Leave l);

	public boolean deleteLeave(int leaveId);

	public List<Leave> getLeavesByRessource(int ressourceId);
	

}
