package tn.esprit.twin.ninja.interfaces.recruitement;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.recruitment.JobOffer;
@Local
public interface JobOfferLocal {
	public int addJobOffer(JobOffer jobOffer);
	public boolean updateOffer(JobOffer jobOffer);
	public boolean removeJobOffer(int idJobOffer);
	public JobOffer getJobOffer(int idJobOffer);
	public List<JobOffer> getAllJobOffer();
	public List<JobOffer> getJobOfferBySkills(int ressource);

}
