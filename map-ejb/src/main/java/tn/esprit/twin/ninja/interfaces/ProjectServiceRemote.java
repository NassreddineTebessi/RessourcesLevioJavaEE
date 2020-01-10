package tn.esprit.twin.ninja.interfaces;
import java.util.List;
import javax.ejb.Remote;
import tn.esprit.twin.ninja.persistence.Project;

@Remote
public interface ProjectServiceRemote {
	
	public void addProject(Project p);
	public void deleteProject(int idProject);
	public void updateProject(Project p);
	public void affectProjecttoClient(int projectId, int clientId);
	public List<Project> getAllProject();
	public List<Project> getProjectByClient(int idClient);
	public List<Project> getProjectByAdress(String adress);
	public void addPhotoProject(int id, String fileName);
	public void addProjectDotnet(Project p, int idClient) ;
	public Project getProjectById(int idProject);
	
}
