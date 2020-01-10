package tn.esprit.twin.ninja.persistence.recruitment;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ApplicationTestPK implements Serializable{
	
	
	private int idTest;
	private int idApp;
	public int getIdTest() {
		return idTest;
	}
	public void setIdTest(int idTest) {
		this.idTest = idTest;
	}
	public int getIdApp() {
		return idApp;
	}
	public void setIdApp(int idApp) {
		this.idApp = idApp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idApp;
		result = prime * result + idTest;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationTestPK other = (ApplicationTestPK) obj;
		if (idApp != other.idApp)
			return false;
		if (idTest != other.idTest)
			return false;
		return true;
	}
	public ApplicationTestPK(int idTest, int idApp) {
		super();
		this.idTest = idTest;
		this.idApp = idApp;
	}
	public ApplicationTestPK() {
		super();
	}
	
	

}
