package tn.esprit.twin.ninja.interfaces;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Ressource;

@Local
public interface DashboardServicesLocal {
public Long getNumberFreelancers();
public Long getNumberEmployees();
public Long getNumberEmployeesInMandates();
public Long getNumberEmployeesInterMandate();
public Long getNumberEmployeesAdministration();
public Long reclamationsCount();
public Long satisfactionsCount();
public float satisfactionRate();
public int numberOfResourcesToClient(int clientId);
public void reportResource(int ressourceId) throws IOException;
public List<Object>mostUsedSkills();
public List<Object> mostProfitProject();
public List<Object> mostProfitClient();
public float mandateEfficiency(int mandateId);
public float resourceEfficiency(int resourceId);
public float projectEfficiency(int projectID);
}