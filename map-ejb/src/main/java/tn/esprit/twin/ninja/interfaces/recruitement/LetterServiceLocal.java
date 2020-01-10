package tn.esprit.twin.ninja.interfaces.recruitement;

import javax.ejb.Local;

import tn.esprit.twin.ninja.persistence.recruitment.Letter;
import tn.esprit.twin.ninja.persistence.recruitment.Typel;

@Local
public interface LetterServiceLocal {
	public Letter getLetterEmp(int idApplication,Typel role);
	public boolean setLetterEmpUser(int idFolder,Letter letter);
	public boolean setLetterEmpAdmin(int idFolder,Letter letter);	
	public boolean setStateLetter(int idFolder);
	

}
