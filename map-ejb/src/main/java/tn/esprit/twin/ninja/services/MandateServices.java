package tn.esprit.twin.ninja.services;

import tn.esprit.twin.ninja.interfaces.MandateServicesLocal;
import tn.esprit.twin.ninja.interfaces.MandateServicesRemote;
import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Mandate;
import tn.esprit.twin.ninja.persistence.Organigramme;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Ressource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
@Stateless
public class MandateServices implements MandateServicesRemote, MandateServicesLocal {
    @PersistenceContext(unitName="LevioMap-ejb")
    EntityManager em;

    @Override
    public List<Mandate> getAll() {
        TypedQuery<Mandate> query = em.createQuery("SELECT m FROM Mandate m where Archived=false and m.EndDate>=CURRENT_DATE", Mandate.class);
        List<Mandate> results = query.getResultList();
        return results;
    }
    
    @Override
    public List<Project> getAllProject() {
        TypedQuery<Project> query = em.createQuery("SELECT m FROM Project m ", Project.class);
        List<Project> results = query.getResultList();
        return results;
    }
    
    @Override
    public List<Ressource> getAllResource() {
        TypedQuery<Ressource> query = em.createQuery("SELECT res FROM Ressource res ", Ressource.class);
        List<Ressource> results = query.getResultList();
        return results;
    }

    @Override
    public List<Mandate> SearchMandateByDate(Date date) {

        TypedQuery<Mandate> query = em.createQuery("SELECT m FROM Mandate m where m.StartDate<=:date and m.EndDate>=:date and Archived=false", Mandate.class);
        query.setParameter("date", date,TemporalType.DATE);
        List<Mandate> results = query.getResultList();
        return results;
    }
    
   
    @Override
    public List<Mandate> getMandateByResource(int resourceId) {

        TypedQuery<Mandate> query = em.createQuery("SELECT m FROM Mandate m where m.ressource.id=:resId and Archived=false", Mandate.class);
        query.setParameter("resId", resourceId);
        List<Mandate> results = query.getResultList();
        return results;
    }
    
    @Override
    public  Mandate  getFMandateByResource(int resourceId) {

        return em.createQuery("SELECT m FROM Mandate m where m.ressource.id=:resId and Archived=false  and m.EndDate>=CURRENT_DATE", Mandate.class)
        		.setParameter("resId", resourceId).getSingleResult();
    }

    @Override
    public  List<Organigramme>  getOrganim(int clientid) {

        return em.createQuery("SELECT m FROM Organigramme m where m.client.id=:resId ", Organigramme.class)
        		.setParameter("resId", clientid).getResultList();
    }
    
    @Override
    public  Client  getClientByMandate(int projid) {

        TypedQuery<Client> query = em.createQuery("SELECT m FROM Client m where m.projects.id=:projid  ", Client.class);
        query.setParameter("projid", projid);
        Client results = query.getSingleResult();
        return results;
    }
    
    @Override
    public Mandate getMandateById(int Id) {
    	Mandate MandateEntity = em.find(Mandate.class, Id);
    	return MandateEntity;
    }
    @Override
    public Ressource getResourceById(int resourceId) {
    	Ressource resourceEntity = em.find(Ressource.class, resourceId);
    	return resourceEntity;
    }
    @Override
    public Project getProjetById(int projtid) {
    	Project projetEntity = em.find(Project.class, projtid);
    	return projetEntity;
    }
    @Override
    public void AssignResource(int projtid,int resid,String sdate,String edate,float cost) throws ParseException {
	Project projetEntity = em.find(Project.class, projtid);
    	Ressource resourceEntity = em.find(Ressource.class, resid);
         Mandate mand=new Mandate();
     	String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    	mand.setStartDate(simpleDateFormat.parse(sdate));
    	mand.setEndDate(simpleDateFormat.parse(edate));
    	mand.setMontant(cost);
    	mand.setProject(projetEntity);
    	mand.setRessource(resourceEntity); 
    	em.persist(mand);
    	SendMail("notifmaplevio@gmail.com","NinjaC0ders","notifmaplevio@gmail.com","slimen.mami@esprit.tn","Assign Notification","You have new assignation ");
    
    }
    
    @Override
    public void AssignationResource(int projtid,int resid,String sdate,String edate){
      	try {
      		System.out.println(projtid+" "+resid+" "+sdate+" "+edate);
    	Project projetEntity = em.find(Project.class, projtid);
    	Ressource resourceEntity = em.find(Ressource.class, resid);
         Mandate mand=new Mandate();
     	String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		mand.setStartDate(simpleDateFormat.parse(sdate));
    	mand.setEndDate(simpleDateFormat.parse(edate));
    	mand.setMontant(0);
    	mand.setProject(projetEntity);
    	mand.setRessource(resourceEntity); 
    	em.persist(mand);
    	SendMail("notifmaplevio@gmail.com","NinjaC0ders","notifmaplevio@gmail.com","slimen.mami@esprit.tn","Assign Notification","You have new assignation ");
    	} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     @Override
    public void EditMandate(Mandate m)
    {
        Mandate mand = em.find(Mandate.class, m.getId());
        if(m.getStartDate()!=null)
        mand.setStartDate(m.getStartDate());
        if(m.getEndDate()!=null)
        mand.setEndDate(m.getEndDate());
        if(m.getMontant()>0)
        mand.setMontant(m.getMontant());

        em.merge(mand);
    
        
    
    }
     
     @Override
    public void EditMandates(int id,Date startdate,Date enddate,int project,int resource)
    {
        Mandate mand = em.find(Mandate.class, id);
      
        Mandate Resource = em.find(Mandate.class, resource);
        if(startdate!=null)
        mand.setStartDate(startdate);
        if(enddate!=null)
        mand.setEndDate(enddate);
        if(project >=0)
       {Project Projects = em.find(Project.class, project);
       mand.setProject(Projects);
       }
        if(project >=0)
        {Ressource Resources = em.find(Ressource.class, resource);
        mand.setRessource(Resources);
        }
        em.merge(mand);
    
        
    
    }


    @Override
    public void CalculateFees(int mandateID,float taux,float NbrH) {
        Mandate mandateEntity = em.find(Mandate.class, mandateID);
        float montant=taux*NbrH; 
        mandateEntity.setMontant(montant);
    }

    @Override
    public List<Mandate> DisplayHistory() {
        TypedQuery<Mandate> query = em.createQuery("SELECT m FROM Mandate m where m.EndDate<=CURRENT_DATE and Archived=false", Mandate.class);
        List<Mandate> results = query.getResultList();
        return results;
    }
    
    @Override 
    public Project GetProjectByClient(int id) {
        TypedQuery<Project> query = em.createQuery("SELECT m FROM Project m where m.client.id=:id", Project.class);
        query.setParameter("id", id);
        Project results = query.getSingleResult();
       
        return results;
    }
    @Override
    public List<Ressource> GetListResource(int idproj) {
    	   TypedQuery<Ressource> query = em.createQuery("SELECT m FROM Ressource m where m.project.id=:idproj", Ressource.class);
    	   query.setParameter("idproj", idproj);
           List<Ressource> results = query.getResultList();

           return results;
    }

    @Override
    public void ArchiveMandate(int mandateID) {
        Mandate mandateEntity = em.find(Mandate.class, mandateID);
        mandateEntity.setArchived(true);
    }

    @Override
    public void TrackResource() {

    }
    @Override
    public List<Mandate> ArchivedMandate(){
        TypedQuery<Mandate> query = em.createQuery("SELECT m FROM Mandate m where Archived=true", Mandate.class);
        List<Mandate> results = query.getResultList();
        return results;
    
    }
    @Override
    public String SendMail(String username,String password,String from,String to,String subject,String msg)
    {
     // user=   notifmaplevio@gmail.com
    // pass=NinjaC0ders 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            return "Done";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
        
    }
    @Override
    public List<Client> getAllClient() {
        TypedQuery<Client> query = em.createQuery("SELECT m FROM Client m", Client.class);
        List<Client> results = query.getResultList();
        return results;
    
}
    @Override
    public void setBoss(int idres,int parent) {
    	Ressource resEntity = em.find(Ressource.class, idres);
    	resEntity.setSeniority(parent);
    
}
    
    }
