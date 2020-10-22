package logic.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.entities.AdvancedUser;
import logic.entities.Domanda;
import logic.entities.Risposta;
import logic.exceptions.AdvancedNotFoundException;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.dao.AdvancedUserDAO;
import logic.dao.BeginnerUserDAO;
import logic.dao.DomandaDAO;
import logic.dao.RispostaDAO;
import logic.utils.Controller;

/*
 * Classe AskForQuestionsController che avrà il compito di
 * coordinare il comportamento del caso d'uso
 * Ask For Questions.
 */

public class AskForQuestionsController extends Controller {

	public AdvancedUserBean getAdvanced(String username) throws AdvancedNotFoundException, SQLException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		AdvancedUser au = aud.selectAdvancedByUsername(username);
		return this.convert(au);
	}

	public List<AdvancedUserBean> leaderBoard() throws SQLException, AdvancedNotFoundException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		List<AdvancedUser> lau = aud.leaderBoardAd();
		if (lau == null)
			return null;
		return this.convertAdvancedList(lau);
	}

	public List<AdvancedUserBean> getAdvancedByRole(String role) throws AdvancedNotFoundException, SQLException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		List<AdvancedUser> lau = aud.selectAdvancedByRole(role);
		return this.convertAdvancedList(lau);
	}

	public List<DomandaBean> getQuestions(String username, String role) throws SQLException {
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(username, role);
		if (ld == null)
			return Collections.emptyList();
		return this.convertQuestionList(ld);
	}

	public void makeQuestion(String text, String username, String username2)
			throws FieldTooLongException, SQLException, FieldEmptyException {
		DomandaDAO dd = new DomandaDAO();
		if (text.length() >= 200) {
			throw new FieldTooLongException("Question body can't be longer than 200 characters");
		}
		if (text.isEmpty()){
			throw new FieldEmptyException("You didn't write anything!");
		}
		dd.addQuestion(text, username, username2);
	}

	public void acceptQuestion(DomandaBean db) throws NumberFormatException, SQLException {
		DomandaDAO dd = new DomandaDAO();
		dd.manageQuestions(Integer.parseInt(db.getId()), DomandaDAO.ACCEPT);
	}

	public void rejectQuestion(DomandaBean db) throws NumberFormatException, SQLException {
		DomandaDAO dd = new DomandaDAO();
		dd.manageQuestions(Integer.parseInt(db.getId()), DomandaDAO.REJECT);
	}

	public boolean checkAnswer(String username, String id) throws NumberFormatException, SQLException {
		RispostaDAO rd = new RispostaDAO();
		if (rd.getAnswer(username, Integer.parseInt(id)) == null)
			return false;
		return true;
	}

	public RispostaBean getAnswer(String username, String id) throws NumberFormatException, SQLException {
		RispostaDAO rd = new RispostaDAO();
		Risposta r = rd.getAnswer(username, Integer.parseInt(id));
		return this.convert(r);
	}

	public void voteAdvanced(String advancedName, String username, int getValue) throws SQLException {
		BeginnerUserDAO bud = new BeginnerUserDAO();
		bud.voteAdvanced(advancedName, username, getValue);
	}
}
