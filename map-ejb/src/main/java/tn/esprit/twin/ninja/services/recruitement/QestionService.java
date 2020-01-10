package tn.esprit.twin.ninja.services.recruitement;

import java.util.List;

import javax.ejb.FinderException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tn.esprit.twin.ninja.interfaces.recruitement.QuestionServiceLocal;
import tn.esprit.twin.ninja.persistence.recruitment.Question;
import tn.esprit.twin.ninja.persistence.recruitment.Test;

@Stateless
public class QestionService implements QuestionServiceLocal {
	@PersistenceContext(unitName = "LevioMap-ejb")
	private EntityManager em;

	@Override
	public int addQuestion(Question question) {
		em.persist(question);
		return question.getId();
	}

	@Override
	public Question getQuestion(int idQuestion) {
		
		return em.find(Question.class, idQuestion);
	}

	@Override
	public List<Question> getAllQuestion() {
		Query query = em.createQuery("SELECT a from Question a");	
		return query.getResultList();

	}

	@Override
	public boolean assignQuestionTest(int idQuestion, int idTest) {
		try {
			Question q=em.find(Question.class, idQuestion);
			Test t=em.find(Test.class, idTest);
			t.getListQuestion().add(q);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
