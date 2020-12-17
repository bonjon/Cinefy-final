package logic.controllers;

import java.sql.SQLException;
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
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.Controller;
import logic.utils.FilmAdviceFactory;
import logic.utils.GeneralAnswerFactory;

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
	public List<Domanda> questionsFromABeg (String advancedName, String beginnerName) throws SQLException {
		
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestionsFromABeg(advancedName, beginnerName);
		
		return ld;
		}
	
	public AdvancedUserBean queueCountFromABeg (String advancedName, String beginnerName) throws SQLException {
		
		int counter=0;
		
		DomandaDAO dd = new DomandaDAO();
		AdvancedUserBean aub = new AdvancedUserBean();
		List<Domanda> ld = dd.getQuestionsFromABeg(advancedName, beginnerName);
		counter=ld.size();
		
		
		aub.setQueueCount(counter);
		return aub;
		
		}
	
		
	public void createAnswer(RispostaBean rb) throws NumberFormatException, SQLException, FieldEmptyException, FieldTooLongException {
		
		RispostaDAO rd;
		GeneralAnswerFactory gaf;
		FilmAdviceFactory faf;
		
		
		
		
		if(rb.getChoice()=="general") {
			if(rb.getContenuto().isEmpty()) {
				throw new FieldEmptyException("Please, write something in the answer box");
			}
			if(rb.isAColleagueSuggested()==true&&rb.isAResourceSuggested()==true) {
				if(rb.getColleagueName().isEmpty()&&(rb.getWikiLink().isEmpty()&&rb.getYoutubeLink().isEmpty())) {
					throw new FieldEmptyException("Please, suggest the colleague and web \nresources you thinked of");
				}
				else if(rb.getReasonChoice()==null&&(rb.getWikiLink().isEmpty()&&rb.getYoutubeLink().isEmpty())) {
					throw new FieldEmptyException("Please, suggest the colleague and web \nresources you thinked of");
				}
			}
			if(rb.isAColleagueSuggested()==true) {
				if(rb.getColleagueName().isEmpty()) {
					throw new FieldEmptyException("Please, write the name of a colleague");
				}
				if(rb.getReasonChoice()==null) {
					throw new FieldEmptyException("Please, tell why you suggest this colleague");
				}
			}
			if(rb.isAResourceSuggested()==true) {
				if(rb.getWikiLink().isEmpty()&&rb.getYoutubeLink().isEmpty()) {
					throw new FieldEmptyException("Please, enter a Wikipedia or YouTube's URL at least");
				}
			}
			
			
			
			gaf = new GeneralAnswerFactory();
			answer = gaf.answerCreation(rb);
		}
		else if (rb.getChoice()=="film") {
			
			if(rb.getFilm().isEmpty()||rb.getPartecipant().isEmpty()||rb.getGenre().isEmpty()) {
				throw new FieldEmptyException("Film, "+rb.getProfession()+", Genre fields cannot be empty");
			}
			if(rb.getExplanation().isEmpty()) {
				throw new FieldEmptyException("Please, explain the reason of your advice");
			}
		
			faf = new FilmAdviceFactory();
			answer = faf.answerCreation(rb);
			
			
		}
		
		fieldTooLongControls(rb);
		
		rd = new RispostaDAO();
		rd.addAnswer(answer, rb.getBeginnerName(), rb.getAdvancedName(), Integer.parseInt(rb.getId()));
		
		
	}
	
	public void fieldTooLongControls(RispostaBean rb) throws FieldTooLongException {
		//general answer
		//circa 182 caratteri vengono aggiunti nel caso peggiore (reason: renown....person in this sector)
		//dal pattern che formatta la risposta per una  general answer
		//2048-182=1866 caratteri disponibili
		Integer genAnswerSize = 600;
		Integer colleagueSize = 16;
		Integer wikiSize = 250;
		Integer youTubeSize = 900;
		
		//film advice
		//circa 120 caratteri vengono aggiunti nel caso peggiore (professione screenwriter) dal pattern 
		//che formatta la risposta per una  film advice
		//2048-120=1928 caratteri disponibili
		Integer filmSize = 150;
		Integer partecipantSize = 70;
		Integer genreSize = 100;
		Integer explanationSize = 1400;
		
		
		if(rb.getChoice()=="general") {
			
			if (rb.getContenuto().length()>genAnswerSize) {
				throw new FieldTooLongException("Answer box text is too long \n(max "+genAnswerSize.toString()+" characters)");
			}
			if(rb.isAColleagueSuggested()==true) {	
				if (rb.getColleagueName().length()>colleagueSize) {
					throw new FieldTooLongException("Suggested advanced user' s name is too long \n(max " +colleagueSize.toString()+" characters)");
				}
			}
			if(rb.isAResourceSuggested()==true) {
				if(rb.getWikiLink().length()>wikiSize) {
					throw new FieldTooLongException("The entered Wiki's URL is too long \n(max "+wikiSize.toString()+" characters)");
				}
				if(rb.getYoutubeLink().length()>youTubeSize) {
					throw new FieldTooLongException("The entered YouTube's URL is too long \n(max "+youTubeSize.toString()+" characters)");
				}
			}
			
		}
		else if (rb.getChoice()=="film") {
			
			if(rb.getFilm().length()>filmSize) {
				throw new FieldTooLongException("The entered film's title is too long \n(max "+filmSize.toString()+" characters)");
			}
			if(rb.getPartecipant().length()>partecipantSize) {
				throw new FieldTooLongException("The entered "+rb.getProfession().toLowerCase()+"'s name is too long \n(max "+partecipantSize.toString()+" characters)");
			}
			if(rb.getGenre().length()>genreSize) {
				throw new FieldTooLongException("The entered film's title is too long \n(max "+filmSize.toString()+" characters)");
			}
			if(rb.getExplanation().length()>explanationSize) {
				throw new FieldTooLongException("The explanation of the film advice is too long \n(max "+explanationSize.toString()+" characters)");
			}
		}
		
	}
	
	public void deleteQuestion() {
		
	}
	
	public BeginnerUserBean getUser(String username, String role) {
		GeneralUserDAO gud = new GeneralUserDAO();
		BeginnerUserBean bub = new BeginnerUserBean();
		try {
			BeginnerUser gu = gud.findByName(username, role);
			bub.setUsername(gu.getUsername());
			bub.setPassword(gu.getPassword());
			bub.setBio(gu.getBio());
			bub.setProfilePic(gu.getProfilePic());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bub;
	}

      
      public AdvancedUserBean getAdvanced(String username) throws AdvancedNotFoundException, SQLException {
  		AdvancedUserDAO aud = new AdvancedUserDAO();
  		AdvancedUser au = aud.selectAdvancedByUsername(username);
  		return this.convert(au);
  	}

	
	
	public void acceptAnswer(RispostaBean rb) throws NumberFormatException, SQLException {
		
		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), RispostaDAO.ACCEPT);
	}

	public void rejectAnswer(RispostaBean rb) throws NumberFormatException, SQLException {
		RispostaDAO dd = new RispostaDAO();
		dd.manageAnswers(Integer.parseInt(rb.getId()), 	RispostaDAO.REJECT);

	}
      
	
 }