package tn.esprit.twin.ninja.services.recruitement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tn.esprit.twin.ninja.interfaces.recruitement.FolderServiceLocal;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.StateFolder;
import tn.esprit.twin.ninja.persistence.recruitment.StateLetter;
import tn.esprit.twin.ninja.persistence.recruitment.StateMinister;
import tn.esprit.twin.ninja.utils.SendMinisterMail;

@Stateless
public class FolderService implements FolderServiceLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;

	

	

	@Override
	public StateMinister getStateMinister(int idApplication) {
		Application a=em.find(Application.class, idApplication);
		try {a.getFolder().getStateMinister();
			return a.getFolder().getStateMinister();
		} catch (Exception e) {
			return StateMinister.inPregress;
		}
	}

	@Override
	public boolean setStateMinister(int idApplication,StateMinister stateM) {
		Application a=em.find(Application.class, idApplication);
		try {a.getFolder().setStateMinister(stateM);
			return true;
		} catch (Exception e) {
			return false;
		}
	}



	



	



	@Override
	public Folder getFolder(int idApplication) {
		return em.find(Application.class, idApplication).getFolder();
	}



	@Override
	public List<Folder> getAllFolder() {
		Query query = em.createQuery("SELECT f from Folder f ");	
		
		//query.getResultList().stream().forEach(p->System.out.println("eeeeeee"));
		return query.getResultList();
	}

	@Override
	public StateFolder getStateFolder(int idApplication) {
		Application a=em.find(Application.class, idApplication);
		try {a.getFolder().getStateFolder();
			return a.getFolder().getStateFolder();
		} catch (Exception e) {
			return StateFolder.notComplited;
		}
	}

	@Override
	public boolean setStateFolder(int idApplication,StateFolder stateF) {
		Application a=em.find(Application.class, idApplication);
		try {a.getFolder().setStateFolder(stateF);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean sendEmail(int ida) {
		
		return SendMinisterMail.sendMail(em.find(Application.class, ida));
	}

	
	

}
