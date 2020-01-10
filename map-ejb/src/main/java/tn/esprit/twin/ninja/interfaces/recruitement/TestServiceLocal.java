package tn.esprit.twin.ninja.interfaces.recruitement;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import tn.esprit.twin.ninja.persistence.recruitment.Application;
import tn.esprit.twin.ninja.persistence.recruitment.Test;



@Local
public interface TestServiceLocal {
public int passeTest(int a,int t,int note);
public float getResultTest(int idTest,int idApp);
public int addTest(Test t);
public Test getTest(int idTest,int idApp);
public List<Test> getAllTest();
public boolean assignTest(int idTest,int idApp);


}
