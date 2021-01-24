package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;

/**
 * Servlet implementation class FilmAdviceServlet
 */

@WebServlet("/FilmAdviceServlet")
public class FilmAdviceServlet  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static final String ANSWER = "answer.jsp";
	private static final Logger LOGGER = Logger.getLogger(FilmAdviceServlet.class.getName());
	private String profession;
	
	public FilmAdviceServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			HttpSession session = request.getSession();
			session.setAttribute("type", "film");
			AnswerQuestionsController aqc = new AnswerQuestionsController();
			DomandaBean db = (DomandaBean) session.getAttribute("QU");
			String adv = db.getAdvancedName();
			AdvancedUserBean aub = new AdvancedUserBean();
			try {
				aub = aqc.getAdvanced(adv);
			} catch (ClassNotFoundException | AdvancedNotFoundException | SQLException e) {
				LOGGER.log(Level.WARNING, e.toString());
			}
			profession = aub.getProfession();
			request.setAttribute("prof", profession);
			RequestDispatcher rd = request.getRequestDispatcher(ANSWER);
			if (request.getParameter("make") != null) {
				rd = this.makeAnswer(request, session, aqc);
			}
			else if (request.getParameter("switch2") != null){
				rd = request.getRequestDispatcher("GeneralAnswerServlet");
			}
			rd.forward(request, response);
		}
	

private RequestDispatcher makeAnswer(HttpServletRequest request, HttpSession session,AnswerQuestionsController aqc) {
	String emptyString = "";
	
	String film = "film";
	String suggestedFilm = (String) request.getParameter("suggestedFilm");
	if(suggestedFilm==null) {suggestedFilm=emptyString;}
	String partecipant = (String) request.getParameter("partecipant");
	if(partecipant==null) {partecipant=emptyString;}
	String genre = (String) request.getParameter("genre");
	if(genre==null) {genre=emptyString;}
	String explanation = (String) request.getParameter("explanation");
	if(explanation==null) {explanation=emptyString;}

	RispostaBean rb = new RispostaBean();
	DomandaBean db = (DomandaBean) session.getAttribute("QU");
	
	int domId = Integer.parseInt(db.getId());
	
	BeginnerUserBean bub = (BeginnerUserBean) session.getAttribute("begS");
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
	
	rb.setIdDomanda(domId);
	rb.setAdvancedName(gub.getUsername());
	rb.setBeginnerName(bub.getUsername());
	rb.setChoice(film);
	rb.setProfession(profession);
	rb.setFilm(suggestedFilm);
	rb.setPartecipant(partecipant);
	rb.setGenre(genre);
	rb.setExplanation(explanation);

	try {
		aqc.createAnswer(rb);
	} catch (FieldTooLongException | FieldEmptyException e) {
		request.setAttribute("error", e.getMessage());
		return request.getRequestDispatcher(ANSWER);
	} catch (SQLException | ClassNotFoundException e) {
		LOGGER.log(Level.WARNING, e.toString());
	}
	return request.getRequestDispatcher("AnswerAdvancedServlet");
}
	

}