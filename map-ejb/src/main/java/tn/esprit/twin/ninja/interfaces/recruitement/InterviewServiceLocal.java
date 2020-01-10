package tn.esprit.twin.ninja.interfaces.recruitement;

import java.util.Date;

import tn.esprit.twin.ninja.persistence.recruitment.Interview;
import tn.esprit.twin.ninja.persistence.recruitment.StateInterview;

public interface InterviewServiceLocal {
	public boolean addInterview(Interview interview, int idapplication);

	public boolean setStateInterview(int idInterview, StateInterview state);

	public Interview getInterview(int IdInterview);

	public boolean updateDateInterview(Interview interview);

}
