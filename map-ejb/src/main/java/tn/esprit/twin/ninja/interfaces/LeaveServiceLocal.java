package tn.esprit.twin.ninja.interfaces;

import java.util.List;

import tn.esprit.twin.ninja.persistence.Leave;

public interface LeaveServiceLocal {

	public void addLeave(int ressourceId,Leave l) ;
	
	public List<Leave> getAllLeave();

	public boolean updateLeave(Leave l,int id);

	public boolean deleteLeave(int leaveId);

	public List<Leave> getLeavesByRessource(int ressourceId);

}
