package tn.esprit.twin.ninja.interfaces;

import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Mandate;
import tn.esprit.twin.ninja.persistence.Organigramme;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Ressource;

import javax.ejb.Remote;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
@Remote
public interface MandateServicesRemote {
    public List<Mandate> getAll();
    public List<Mandate> SearchMandateByDate(Date date);
    public List<Mandate> getMandateByResource(int resourceId);

    public void AssignResource(int projtid,int resid,String sdate,String edate,float cost) throws ParseException;
    public void CalculateFees(int mandateID,float taux,float NbrH);

    public List<Mandate> DisplayHistory();
    public List<Mandate> ArchivedMandate();
    public void ArchiveMandate(int mandateID);
    public void TrackResource();
    public String SendMail(String username,String password,String from,String to,String subject,String msg);
    public void EditMandate(Mandate m);
    public Project GetProjectByClient(int id);
    public List<Ressource> GetListResource(int idproj);
    public List<Client> getAllClient();
    public void setBoss(int idres,int parent);
    public void EditMandates(int id,Date startdate,Date enddate,int project,int resource);
    public void AssignationResource(int projtid,int resid,String sdate,String edate) ;
    public Project getProjetById(int projtid) ;
    public Ressource getResourceById(int resourceId) ;
    public List<Project> getAllProject();
    public List<Ressource> getAllResource();
    public Mandate getMandateById(int Id);
    public  Mandate  getFMandateByResource(int resourceId);
    public  Client  getClientByMandate(int projid) ;
    public   List<Organigramme>   getOrganim(int clientid);
}
