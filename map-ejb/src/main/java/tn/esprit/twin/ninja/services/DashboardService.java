package tn.esprit.twin.ninja.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tn.esprit.twin.ninja.interfaces.DashboardServicesLocal;
import tn.esprit.twin.ninja.persistence.Client;
import tn.esprit.twin.ninja.persistence.Leave;
import tn.esprit.twin.ninja.persistence.Mandate;
import tn.esprit.twin.ninja.persistence.Project;
import tn.esprit.twin.ninja.persistence.Ressource;
import tn.esprit.twin.ninja.persistence.Skill;
@Stateless
public class DashboardService implements DashboardServicesLocal {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Long getNumberFreelancers() {//ok
		String sql = "SELECT COUNT(r.id) FROM Ressource r WHERE r.contract_type='freelancer'";
		Query q = em.createQuery(sql);
		Long count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public Long getNumberEmployees() {//ok
		String sql = "SELECT COUNT(r.id) FROM Ressource r WHERE r.contract_type='employee'";
		Query q = em.createQuery(sql);
		Long count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public Long getNumberEmployeesInMandates() {//ok
		Long leavescounter=new Long(0);
		Date d=new Date();
		String sql = "SELECT COUNT(r.id) FROM Ressource r WHERE r.state='notAvailable' or r.state='soonAvailable'";
		Query q = em.createQuery(sql);
		Long count =(Long) q.getSingleResult();
		String sql2 ="Select r.leaves from Ressource r WHERE r.state='notAvailable'";
		Query q2 = em.createQuery(sql2);
		List<Leave> leaves  =(List<Leave>) q2.getResultList();
		for (Leave l : leaves){
			if (d.after(l.getStart()) && d.before(l.getEnd())){
				leavescounter++;
			}
		}
		return count-leavescounter;
	}

	@Override
	public Long getNumberEmployeesInterMandate() {
		String sql = "SELECT COUNT(r.id) FROM Ressource r WHERE r.state='available' and r.role<>'ROLE_ADMIN_AGENT'";
		Query q = em.createQuery(sql);
		Long count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public Long getNumberEmployeesAdministration() {
		String sql = "SELECT COUNT(r.id) FROM Ressource r WHERE r.role='ROLE_ADMIN_AGENT'";
		Query q = em.createQuery(sql);
		Long count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public Long reclamationsCount() {
		Long count = null;
		String sql = "Select count(m.id) from Message m where m.type='reclamation'";
		Query q = em.createQuery(sql);
		count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public Long satisfactionsCount() {
		Long count = null;
			String sql = "Select count(m.id) from Message m where m.type='satisfaction'";
			Query q = em.createQuery(sql);
			count =(Long) q.getSingleResult();
		return count;
	}

	@Override
	public float satisfactionRate() {
		Long reclamation = reclamationsCount();
		Long satisfaction = satisfactionsCount();
		return (satisfaction.floatValue()/(satisfaction.floatValue()+reclamation.floatValue()))*100;
	}

	@Override
	public int numberOfResourcesToClient(int clientId) {//ok
		Client c=em.find(Client.class, clientId);
		int numRes=0;
		String sql = "Select p from Project p where p.client.id="+c.getId();
		Query q = em.createQuery(sql);
		List<Project> projects  =(List<Project>) q.getResultList();
		for (Project p : projects){
			String sql2="Select Count(distinct m.ressource) from Mandate m where m.project.id="+p.getId();
			Query q2 = em.createQuery(sql2);
			Long count = (Long)q2.getSingleResult();
			numRes+=count;
		}
		return numRes;
	}

	@Override
	public void reportResource(int ressourceId) throws IOException {//ok
		Ressource r=em.find(Ressource.class, ressourceId);
		String html="<html><head><style type=\"text/css\">\r\n" + 
				"        * { margin: 0; padding: 0; }\r\n" + 
				"        body { font: 16px Helvetica, Sans-Serif; line-height: 24px; background: url(images/noise.jpg); }\r\n" + 
				"        .clear { clear: both; }\r\n" + 
				"        #page-wrap { width: 800px; margin: 40px auto 60px; }\r\n" + 
				"        #pic { float: right; margin: -30px 0 0 0; }\r\n" + 
				"        h1 { margin: 0 0 16px 0; padding: 0 0 16px 0; font-size: 42px; font-weight: bold; letter-spacing: -2px; }\r\n" + 
				"        h2 { font-size: 20px; margin: 0 0 6px 0; position: relative; }\r\n" + 
				"        h2 span { position: absolute; bottom: 0; right: 0; font-style: italic; font-family: Georgia, Serif; font-size: 16px; color: #999; font-weight: normal; }\r\n" + 
				"        p { margin: 0 0 16px 0; }\r\n" + 
				"        a { color: #999; text-decoration: none; border-bottom: 1px dotted #999; }\r\n" + 
				"        a:hover { border-bottom-style: solid; color: black; }\r\n" + 
				"        ul { margin: 0 0 32px 17px; }\r\n" + 
				"        #objective { width: 500px; float: left; }\r\n" + 
				"        #objective p { font-family: Georgia, Serif; font-style: italic; color: #666; }\r\n" + 
				"        dt { font-style: italic; font-weight: bold; font-size: 18px; text-align: right; padding: 0 26px 0 0; width: 150px; float: left; height: 100px; border-right: 1px solid #999;  }\r\n" + 
				"        dd { width: 600px; float: right; }\r\n" + 
				"        dd.clear { float: none; margin: 0; height: 15px; }\r\n" + 
				"     </style></head><body><div id=\"page-wrap\">\r\n" + 
				"    \r\n" + 
				"        <img src=\"images/Levio.png\" width=\"300\" height=\"120\" alt=\"Levio\" id=\"pic\" />\r\n" + 
				"    \r\n" + 
				"        <div id=\"contact-info\" class=\"vcard\">\r\n" + 
				"        \r\n" + 
				"            <!-- Microformats! -->\r\n" + 
				"        \r\n" + 
				"            <h1 class=\"fn\">Resource report</h1>\r\n" + 
				"            <dl>\r\n" + 
				"            <dd class=\"clear\"></dd>\r\n" + 
				"            <dt>Basic Information</dt>";
		html+="<dd><p><strong>First name :</strong> "+r.getFirst_name()+"</p>";
		html+="<p><strong>Last name :</strong> "+r.getLast_name()+"</p>";
		html+="<p><strong>Contract Type :</strong> "+r.getContract_type()+"</p></dd>"
				+ "<div class=\"clear\"></div>"
				+ "<hr>";
		html+="<dt>Leaves List :</dt>"
				+ "<dd><ul>";
		int i=0;
		for(Leave l : r.getLeaves()){
			i++;
			html+="<li>";
			html+="<strong>Start Date :</strong> "+l.getStart();
			html+="		<strong>End Date :</strong> "+l.getEnd()+"</li>";
		}
		html+="</ul></dd>"
				+ "<dd class=\"clear\"></dd>"
				+ "<hr>"
				+ "<dt>Mandates affected to resource : </dt><dd>";
		for(Mandate m : r.getMandate()){
			html+="<h2>Mandate : "+m.getId()+"</h2>";
			html+="<p>Start Date : "+m.getStartDate()+"<br>";
			html+="End Date : "+m.getEndDate()+"<br>";
			html+="Charges : "+m.getMontant()+"<br>";
			html+="Project "+m.getProject().getName()+" for Client \""+m.getProject().getClient().getName()+"\"<br>";
			html+="Project <strong>"+m.getProject().getName()+"</strong> for Client <strong>"+m.getProject().getClient().getName()+"</strong><br></p>";
			html+="-------------------------------------------------------------------<br>";
		}
		html+="</dd>"
				+ "<dd class=\"clear\"></dd>"
				+ "<hr>"
				+ "<dt>Skills :</dt>"
				+ "<dd>";
		for(Skill s : r.getSkills()){
			html+="<ul>";
			html+="<li><strong>Skill :</strong>"+s.getName()+"</li>";
			html+="<li><strong>Rating :</strong> "+s.getRating()+"</li>";
			html+="</ul>";
		}
		html+="</dd></dl><div class=\"clear\"></div></div><footer>"+new Date().toString()+"</footer></body></html>";
		File file = new File("C:/Users/Firassov/Desktop/reports/report_"+r.getFirst_name()+"_"+r.getLast_name()+".html");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(html);
		bw.close();
		/*HtmlToPdf.create()
	    .object(HtmlToPdfObject.forHtml(html))
	    .convert("C:/Users/Firassov/Desktop/pdf/file.pdf");*/
	}

	@Override
	public List<Object> mostUsedSkills() {//ok
		String sql = "SELECT s.name,COUNT(s.id) as value_occurrence FROM Skill s GROUP BY s.name ORDER BY value_occurrence DESC";
		Query q = em.createQuery(sql);
		q.setMaxResults(5);
		List<Object> mostSkills=(List<Object>) q.getResultList();
		return mostSkills;
	}
	@Override
	public List<Object> mostProfitProject() {//ok
		String sql = "SELECT m.project.name,SUM(m.Montant) as sum_profit FROM Mandate m GROUP BY m.project ORDER BY sum_profit DESC";
		Query q = em.createQuery(sql);
		q.setMaxResults(5);
		List<Object> mostSkills=(List<Object>) q.getResultList();
		return mostSkills;
	}
	@Override
	public List<Object> mostProfitClient() {//ok
		String sql = "SELECT m.project.client.name,COUNT(distinct m.ressource) as num_res FROM Mandate m GROUP BY m.project.client ORDER BY num_res DESC";
		Query q = em.createQuery(sql);
		q.setMaxResults(5);
		List<Object> mostSkills=(List<Object>) q.getResultList();
		return mostSkills;
	}

	@Override
	public float mandateEfficiency(int mandateId) {
		Mandate m=em.find(Mandate.class, mandateId);
		float optimumDuration=(m.getEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
		float actualDuration=(m.getActualEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
		return (optimumDuration/actualDuration)*100;
		
	}

	@Override
	public float resourceEfficiency(int resourceId) {
		float sum=0;
		int i=0;
		Ressource r = em.find(Ressource.class,resourceId);
		for (Mandate m : r.getMandate()) {
			float optimumDuration=(m.getEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
			float actualDuration=(m.getActualEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
			sum+=(optimumDuration/actualDuration)*100;
			i++;
		}
		return sum/i;
	}

	@Override
	public float projectEfficiency(int projectID) {
		float sum=0;
		int i=0;
		Project p = em.find(Project.class,projectID);
		for (Mandate m : p.getMandates()) {
			float optimumDuration=(m.getEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
			float actualDuration=(m.getActualEndDate().getTime()-m.getStartDate().getTime())/ (24 * 60 * 60 * 1000);
			sum+=(optimumDuration/actualDuration)*100;
			i++;
		}
		return sum/i;
	}
	
}
