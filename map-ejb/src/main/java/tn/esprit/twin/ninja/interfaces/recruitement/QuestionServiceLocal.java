package tn.esprit.twin.ninja.interfaces.recruitement;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.twin.ninja.persistence.recruitment.Question;
@Local
public interface QuestionServiceLocal {
	public int addQuestion(Question question);
	public Question getQuestion(int idQuestion);
	public List<Question> getAllQuestion();
	public boolean assignQuestionTest(int idQuestion,int idTest);


}
