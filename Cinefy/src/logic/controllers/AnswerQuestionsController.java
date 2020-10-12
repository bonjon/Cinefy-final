package logic.controllers;

import java.sql.SQLException;
import java.util.List;


import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.dao.AdvancedUserDAO;
import logic.dao.DomandaDAO;
import logic.dao.RispostaDAO;
import logic.entities.AdvancedUser;
import logic.entities.Domanda;
import logic.entities.Risposta;
import logic.exceptions.AdvancedNotFoundException;
import logic.utils.Controller;
import logic.utils.FilmAdvice;
import logic.utils.TechnicalAnswer;

public class AnswerQuestionsController extends Controller{
	
	public String answer;

	public List<DomandaBean> getQuestions(String username, String role) throws SQLException {
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(username, role);
		if (ld == null)
			return null;
		return this.convertQuestionList(ld);
	}
	
	public List<RispostaBean> getAnswers(String username, String role) throws SQLException {
		RispostaDAO rd = new RispostaDAO();
		List<Risposta> ld = rd.getAnswers(username, role);
		if (ld == null)
			return null;
		return this.convertAnswerList(ld);
	}
	
	
	//metodo che ci restituisce le domande che un beginner ha in coda verso uno specificato advanced;
	public int questionsInQueue(String AdvancedName, String beginnerName) throws SQLException {
		
		int i;
		int counter=0;
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(AdvancedName, "advanced");
		for (i=0;i< ld.size();i++) {
			String begTemp = ld.get(i).getBeginnerName();
			if(begTemp==beginnerName) {counter++;};
		}
			
		return counter;
		}
	
		
	public void createAnswer(RispostaBean rb) throws NumberFormatException, SQLException {
		
		RispostaDAO rd;
		TechnicalAnswer ta;
		FilmAdvice fa;
		
		if(rb.getChoice()=="technical") {
			ta = new TechnicalAnswer();
			answer = ta.answerCreation(rb);
		}
		else if (rb.getChoice()=="film") {
			fa = new FilmAdvice();
			answer = fa.answerCreation(rb);
			
			
		}
		
		System.out.println(answer);
		System.out.println(Integer.parseInt(rb.getId()));
		rd = new RispostaDAO();
		rd.addAnswer(answer, rb.getBeginnerName(), rb.getAdvancedName(), Integer.parseInt(rb.getId()));
		
		
	}

      
      public AdvancedUserBean getAdvanced(String username) throws AdvancedNotFoundException, SQLException {
  		AdvancedUserDAO aud = new AdvancedUserDAO();
  		AdvancedUser au = aud.selectAdvancedByUsername(username);
  		return this.convert(au);
  	}

	
	
	public void acceptAnswer(RispostaBean rb) throws NumberFormatException, SQLException {
		System.out.println("in acceptAnswer");
		System.out.println(rb.getBeginnerName());
		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), RispostaDAO.ACCEPT);
	}

	public void rejectAnswer(RispostaBean rb) throws NumberFormatException, SQLException {
		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), 	RispostaDAO.REJECT);

	}
      
      
 }