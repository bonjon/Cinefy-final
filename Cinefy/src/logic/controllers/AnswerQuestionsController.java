package logic.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;

import logic.bean.RispostaBean;
import logic.dao.AdvancedUserDAO;
import logic.dao.DomandaDAO;
import logic.dao.GeneralUserDAO;
import logic.dao.RispostaDAO;
import logic.entities.AdvancedUser;
import logic.entities.BeginnerUser;
import logic.entities.Domanda;
import logic.entities.Risposta;
import logic.exceptions.AdvancedNotFoundException;
import logic.exceptions.AnswersNotFoundException;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;

import logic.utils.Controller;
import logic.utils.FilmAdviceFactory;
import logic.utils.GeneralAnswerFactory;

public class AnswerQuestionsController extends Controller {

	private String answer;

	public List<DomandaBean> getQuestions(String username, String role) throws SQLException, ClassNotFoundException {
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(username, role);
		if (ld.isEmpty()) {
			return Collections.emptyList();
		}
		return this.convertQuestionList(ld);
	}

	public List<RispostaBean> getAnswers(String username, String role) throws SQLException, ClassNotFoundException {
		RispostaDAO rd = new RispostaDAO();
		List<Risposta> ld = rd.getAnswers(username, role);
		if (ld.isEmpty()) {
			return Collections.emptyList();
		}
		return this.convertAnswerList(ld);
	}

	public List<RispostaBean> getAllAnswers(String advancedName)
			throws SQLException, AnswersNotFoundException, ClassNotFoundException {
		RispostaDAO rd = new RispostaDAO();

		List<Risposta> ld = rd.getAnswers(advancedName, "advanced");
		List<Risposta> ldPending = rd.getAnswers(advancedName, "advanced2");

		List<Risposta> allAnswers = new ArrayList<>();
		int y;
		if (ld.size() != 0) {
			for (y = 0; y < ld.size(); y++) {
				allAnswers.add(ld.get(y));

			}
		}

		if (ldPending.size() != 0) {
			for (y = 0; y < ldPending.size(); y++) {
				allAnswers.add(ldPending.get(y));

			}
		}

		if (this.convertAnswerList(allAnswers).isEmpty()) {
			throw new AnswersNotFoundException("You haven' t answered to any question yet");
		}

		return this.convertAnswerList(allAnswers);
	}

	public DomandaBean getQuestion(int id) throws SQLException, ClassNotFoundException {
		DomandaDAO dd = new DomandaDAO();
		Domanda d = dd.getQuestion(id);
		if (d == null)
			return null;
		return convert(d);
	}

	public boolean checkAnswer(RispostaBean rb) throws NumberFormatException, SQLException, ClassNotFoundException {
		RispostaDAO rd = new RispostaDAO();
		boolean b = true ;
		if (rd.getAnswer(rb.getBeginnerName(), Integer.parseInt(rb.getIdDomanda())) == null) {
			b=false;
		}
		return b;
		
	}

	
	public List<DomandaBean> questionsFromABeg(String advancedName, String beginnerName)
			throws SQLException, ClassNotFoundException {
		
		// metodo che ci restituisce le domande che un beginner ha in coda verso uno specificato ad

		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestionsFromABeg(advancedName, beginnerName);
		if (ld == null)
			return Collections.emptyList();
		return this.convertQuestionList(ld);

	}

	public int queueCountFromABeg(String advancedName, String beginnerName)
			throws SQLException, ClassNotFoundException {

		int counter = 0;
		List<DomandaBean> db;

		DomandaDAO dd = new DomandaDAO();

		List<Domanda> ld = dd.getQuestionsFromABeg(advancedName, beginnerName);
		db = convertQuestionList(ld);

		db = deleteQuestion(db, advancedName);

		counter = db.size();

		return counter;

	}

	public void createAnswer(RispostaBean rb) throws NumberFormatException, SQLException, FieldEmptyException,
			FieldTooLongException, ClassNotFoundException {

		RispostaDAO rd;
		GeneralAnswerFactory gaf;
		FilmAdviceFactory faf;

		if (rb.getChoice() == "general") {
			emptyGeneralControls(rb);
			gaf = new GeneralAnswerFactory();
			answer = gaf.answerCreation(rb);
			
		} else if (rb.getChoice() == "film") {
			emptyAdviceControls(rb);
			faf = new FilmAdviceFactory();
			answer = faf.answerCreation(rb);

		}

		fieldTooLongControls(rb);

		rd = new RispostaDAO();
		rd.addAnswer(answer, rb.getBeginnerName(), rb.getAdvancedName(), Integer.parseInt(rb.getIdDomanda()));

	}
	
	private void emptyGeneralControls(RispostaBean rb) throws FieldEmptyException {
		
		if (rb.getContenuto().isEmpty()) {
			throw new FieldEmptyException("Please, write something in the answer box");
		}
		if (rb.isAColleagueSuggested() && rb.isAResourceSuggested() && (rb.getWikiLink().isEmpty() && rb.getYoutubeLink().isEmpty()
				&& (rb.getReasonChoice() == null  || rb.getColleagueName().isEmpty()))) {
						throw new FieldEmptyException("Please, suggest the colleague and web \nresources you thought of");
		}
		if (rb.isAColleagueSuggested()) {
			if (rb.getColleagueName().isEmpty()) {
				throw new FieldEmptyException("Please, write the name of a colleague");
			}
			if (rb.getReasonChoice() == null) {
				throw new FieldEmptyException("Please, tell why you suggest this colleague");
			}
		}
		if (rb.isAResourceSuggested()&&rb.getWikiLink().isEmpty() && rb.getYoutubeLink().isEmpty()) {
			throw new FieldEmptyException("Please, enter a Wikipedia or YouTube's URL at least");
		}

	}
	
	private void emptyAdviceControls(RispostaBean rb) throws FieldEmptyException {
		
		if (rb.getFilm().isEmpty() || rb.getPartecipant().isEmpty() || rb.getGenre().isEmpty()) {
			throw new FieldEmptyException("Film, " + rb.getProfession() + ", Genre fields cannot be empty");
		}
		if (rb.getExplanation().isEmpty()) {
			throw new FieldEmptyException("Please, explain the reason of your advice");
		}
	}

	public void fieldTooLongControls(RispostaBean rb) throws FieldTooLongException {
		
		
		if (rb.getChoice() == "general") {

			generalControls(rb);

		} else if (rb.getChoice() == "film") {

			adviceControls(rb);
			
		}

	}
	
	private void generalControls(RispostaBean rb) throws FieldTooLongException {
		String characters = " characters)";
		
		// general answer
		// circa 182 caratteri vengono aggiunti nel caso peggiore (reason
		// renown....person in this sector)
		// dal pattern che formatta la risposta per una general answer
		// 2048-182=1866 caratteri disponibili
		Integer genAnswerSize = 600;
		Integer colleagueSize = 16;
		Integer wikiSize = 250;
		Integer youTubeSize = 900;
		
		if (rb.getContenuto().length() > genAnswerSize) {
			throw new FieldTooLongException(
					"Answer box text is too long \n(max " + genAnswerSize.toString() + characters);
		}
		if (rb.isAColleagueSuggested()&&rb.getColleagueName().length() > colleagueSize) {
			throw new FieldTooLongException("Suggested advanced user' s name is too long \n(max "
						+ colleagueSize.toString() + characters);
			
		}
		if (rb.isAResourceSuggested()) {
			if (rb.getWikiLink().length() > wikiSize) {
				throw new FieldTooLongException(
						"The entered Wiki's URL is too long \n(max " + wikiSize.toString() + characters);
			}
			if (rb.getYoutubeLink().length() > youTubeSize) {
				throw new FieldTooLongException(
						"The entered YouTube's URL is too long \n(max " + youTubeSize.toString() + characters);
			}
		}
	}
	
	private void adviceControls(RispostaBean rb) throws FieldTooLongException {
		String characters = " characters)";
		
		// film advice
		// circa 167 caratteri vengono aggiunti nel caso peggiore (professione
		// screenwriter) dal pattern
		// che formatta la risposta per una film advice
		// 2048-167=1881 caratteri disponibili
		Integer filmSize = 150;
		Integer partecipantSize = 70;
		Integer genreSize = 100;
		Integer explanationSize = 1400;
		

		if (rb.getFilm().length() > filmSize) {
			throw new FieldTooLongException(
					"The entered film's title is too long \n(max " + filmSize.toString() + characters);
		}
		if (rb.getPartecipant().length() > partecipantSize) {
			throw new FieldTooLongException("The entered " + rb.getProfession().toLowerCase()
					+ "'s name is too long \n(max " + partecipantSize.toString() + characters);
		}
		if (rb.getGenre().length() > genreSize) {
			throw new FieldTooLongException(
					"The entered genre is too long \n(max " + genreSize.toString() + characters);
		}
		if (rb.getExplanation().length() > explanationSize) {
			throw new FieldTooLongException("The explanation of film advice is too long \n(max "
					+ explanationSize.toString() + characters);
		}

	}

	public List<DomandaBean> deleteQuestion(List<DomandaBean> lb, String advancedName)
			throws SQLException, ClassNotFoundException {
		List<RispostaBean> rb = getAnswers(advancedName, "advanced");
		List<RispostaBean> pendingRb = getAnswers(advancedName, "advanced2");
		List<Integer> idList = new ArrayList<>();
		List<DomandaBean> db = lb;

		int i = 0;
		if (!rb.isEmpty()) {
			while (i < rb.size()) {
				RispostaBean temp = rb.get(i);
				Integer id = Integer.parseInt(temp.getIdDomanda());
				idList.add(id);
				i++;
			}
		}

		i = 0;
		if (!pendingRb.isEmpty()) {
			while (i < pendingRb.size()) {
				RispostaBean temp = pendingRb.get(i);
				Integer id = Integer.parseInt(temp.getIdDomanda());
				idList.add(id);
				i++;
			}
		}

		int y=0;
		if (db.isEmpty()) {
			return Collections.emptyList();
		}

		while(y<db.size()) {

			int tempID = Integer.parseInt(db.get(y).getId());

			if (idList.contains(tempID)) {
				db.remove(db.get(y));
				
			}
			else {
			y++;
			}
		}
		

		return db;
	}

	public BeginnerUserBean getBeginnerUser(String username, String role) throws ClassNotFoundException, SQLException {
		GeneralUserDAO gud = new GeneralUserDAO();
		BeginnerUserBean bub = new BeginnerUserBean();
		BeginnerUser gu = gud.findByName(username, role);
		bub.setUsername(gu.getUsername());
		bub.setPassword(gu.getPassword());
		bub.setBio(gu.getBio());
		bub.setProfilePic(gu.getProfilePic());
		
		return bub;
	}

	public AdvancedUserBean getAdvanced(String username)
			throws AdvancedNotFoundException, SQLException, ClassNotFoundException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		AdvancedUser au = aud.selectAdvancedByUsername(username);
		return this.convert(au);
	}

	public AdvancedUserBean getAdvanced(String username, String beginnerName)
			throws AdvancedNotFoundException, SQLException, ClassNotFoundException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		AdvancedUser au = aud.selectAdvancedByUsername(username);

		int questionsFromBeg = queueCountFromABeg(username, beginnerName);

		AdvancedUserBean aub = convert(au);
		aub.setQueueCount(questionsFromBeg);
		return aub;
	}

	public RispostaBean getVoto(String beginnerName, String idRisposta) throws SQLException, ClassNotFoundException {
		RispostaDAO rd = new RispostaDAO();
		int voto = rd.checkVote(beginnerName, idRisposta);
		RispostaBean rb = new RispostaBean();
		rb.setVoto(voto);
		return rb;
	}
	
	public void acceptAnswer(RispostaBean rb) throws NumberFormatException, SQLException, ClassNotFoundException {

		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), RispostaDAO.ACCEPT);
	}

	public void rejectAnswer(RispostaBean rb) throws NumberFormatException, SQLException, ClassNotFoundException {
		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), RispostaDAO.REJECT);

	}

}