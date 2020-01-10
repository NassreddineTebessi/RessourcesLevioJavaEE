package tn.esprit.twin.ninja.services.recruitement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tn.esprit.twin.ninja.interfaces.recruitement.TestServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.ApplicationTest;
import tn.esprit.twin.ninja.persistence.recruitment.ApplicationTestPK;
import tn.esprit.twin.ninja.persistence.recruitment.Test;

@Stateless
public class TestService implements TestServiceLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;

	@Override
	public int passeTest(int a,int t,int note) {
	ApplicationTest at=em.find(ApplicationTest.class, new ApplicationTestPK(a, t));	
	at.setNote(note);
		return 0;
	}

	@Override
	public float getResultTest(int idTest,int idApp) {
		ApplicationTestPK a= new ApplicationTestPK(1,1);
		try {
			ApplicationTest t = em.find(ApplicationTest.class,a);
			return t.getNote();
		} catch (Exception e) {
			return -1;
		}
	
		
		
	}

	@Override
	public int addTest(Test t) {
		em.persist(t);
		return t.getId();
	}

	@Override
	public Test getTest(int idTest,int idApp) {
		ApplicationTestPK pk=new ApplicationTestPK(idTest, idApp);
		ApplicationTest ap=em.find(ApplicationTest.class, pk);
		return ap.getTest();
	}

	@Override
	public List<Test> getAllTest() {
		Query query = em.createQuery("SELECT a from Test a");	
		return query.getResultList();
		
	}

	@Override
	public boolean assignTest(int idTest, int idApp) {
		System.out.println(idTest+idApp);
			Application app =em.find(Application.class, idTest);
			Test test = em.find(Test.class, idTest);
			ApplicationTest at=new ApplicationTest(app, test);
			em.persist(at);
			
			return true;
		
		
	}

}
