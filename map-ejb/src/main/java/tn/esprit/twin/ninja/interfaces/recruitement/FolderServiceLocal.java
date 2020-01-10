package tn.esprit.twin.ninja.interfaces.recruitement;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.StateFolder;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;

@Local

public interface FolderServiceLocal {
	
	public StateMinister getStateMinister(int idApplication);
	public boolean setStateMinister(int idApplication,StateMinister stateM);
	public StateFolder getStateFolder(int idApplication);
	public boolean setStateFolder(int idApplication, StateFolder stateF);
	public Folder getFolder(int idApplication);
	public List<Folder> getAllFolder();
	public boolean sendEmail(int idr);

}
