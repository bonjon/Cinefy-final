package logic.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
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

	public AdvancedUserBean getAdvanced(AdvancedUserBean aub) throws AdvancedNotFoundException, SQLException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		AdvancedUser au = aud.selectAdvancedByUsername(aub.getUsername());
		return this.convert(au);
	}

	public List<AdvancedUserBean> leaderBoard() throws SQLException, AdvancedNotFoundException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		List<AdvancedUser> lau = aud.leaderBoardAd();
		if (lau == null)
			return null;
		return this.convertAdvancedList(lau);
	}

	public List<AdvancedUserBean> getAdvancedByRole(AdvancedUserBean aub) throws AdvancedNotFoundException, SQLException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		List<AdvancedUser> lau = aud.selectAdvancedByRole(aub.getRole());
		return this.convertAdvancedList(lau);
	}

	public List<DomandaBean> getQuestions(GeneralUserBean gub, String role) throws SQLException {
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(gub.getUsername(), role);
		if (ld == null)
			return Collections.emptyList();
		return this.convertQuestionList(ld);
	}

	public void makeQuestion(DomandaBean db)
			throws FieldTooLongException, SQLException, FieldEmptyException {
		DomandaDAO dd = new DomandaDAO();
		if (db.getContenuto().length() >= 200) {
			throw new FieldTooLongException("Question body can't be longer than 200 characters");
		}
		if (db.getContenuto().isEmpty()){
			throw new FieldEmptyException("You didn't write anything!");
		}
		dd.addQuestion(db.getContenuto(), db.getBeginnerName(), db.getAdvancedName());
	}

	public void acceptQuestion(DomandaBean db) throws NumberFormatException, SQLException {
		DomandaDAO dd = new DomandaDAO();
		dd.manageQuestions(Integer.parseInt(db.getId()), DomandaDAO.ACCEPT);
	}

	public void rejectQuestion(DomandaBean db) throws NumberFormatException, SQLException {
		DomandaDAO dd = new DomandaDAO();
		dd.manageQuestions(Integer.parseInt(db.getId()), DomandaDAO.REJECT);
	}

	public boolean checkAnswer(GeneralUserBean gub, DomandaBean db) throws NumberFormatException, SQLException {
		RispostaDAO rd = new RispostaDAO();
		if (rd.getAnswer(gub.getUsername(), Integer.parseInt(db.getId())) == null)
			return false;
		return true;
	}

	public RispostaBean getAnswer(GeneralUserBean gub, DomandaBean db) throws NumberFormatException, SQLException {
		RispostaDAO rd = new RispostaDAO();
		Risposta r = rd.getAnswer(gub.getUsername(), Integer.parseInt(db.getId()));
		if (r == null)
			return null;
		return this.convert(r);
	}

	public void voteAdvanced(AdvancedUserBean aub, GeneralUserBean gub, RispostaBean rb) throws SQLException {
		BeginnerUserDAO bud = new BeginnerUserDAO();
		int a = (int) Double.parseDouble(aub.getVoto());
		String b = aub.getUsername();
		bud.voteAdvanced(b, gub.getUsername(), a, rb.getId());
	}
}
