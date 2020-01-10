package tn.esprit.twin.ninja.services.recruitement;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.twin.ninja.interfaces.recruitement.LetterServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Folder;
import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.StateLetter;
import tn.esprit.twin.ninja.persistence.recruitment.Typel;

@Stateless
public class LetterService implements LetterServiceLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;

	@Override
	public Letter getLetterEmp(int idfolder, Typel role) {
		
		TypedQuery<Letter> query = em.createQuery("SELECT l from Letter l where l.type=:role and l.folder=:folder ", Letter.class);
		query.setParameter("role", role);
		query.setParameter("folder", em.find(Folder.class, idfolder));
		
		return query.getResultList().get(0);
		

	}

	@Override
	public boolean setLetterEmpUser(int idFolder, Letter letter) {
		try {
			Folder f = em.find(Folder.class, idFolder);
			letter.setType(Typel.user);
			letter.setFolder(f);
			f.getListeLetter().add(letter);
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean setLetterEmpAdmin(int idFolder, Letter letter) {
		try {
			Folder f = em.find(Folder.class, idFolder);
			letter.setType(Typel.admin);
			letter.setFolder(f);
			f.getListeLetter().add(letter);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean setStateLetter(int idFolder) {
		try {
			Folder f = em.find(Folder.class, idFolder);
			f.setStateLetter(StateLetter.signed);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
