package fr.treeptik.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.treeptik.dao.QuestionDAO;
import fr.treeptik.model.Question;

@Stateless
public class QuestionEJB {

	@EJB
	private QuestionDAO questionDAO;

	public void create(Question question) {
		questionDAO.create(question);
	}

	public List<Question> findAll() {
		return questionDAO.findAll();
	}

	public void delete(Question question) {
		questionDAO.delete(question);
	}

	public void update(Question question) {
		questionDAO.update(question);
	}

	public Question findById(int id) {
		return questionDAO.findById(id);
	}
	
	public Boolean checkAvecCommentaire(Question question) {
		return questionDAO.checkAvecCommentaire(question);
	}
	
	public Boolean checkQuestion4Choix(Question question) {
		return questionDAO.checkQuestion4Choix(question);
	}
	
	public Boolean checkQuestionFermee(Question question) {
		return questionDAO.checkQuestionFermee(question);
	}
}
