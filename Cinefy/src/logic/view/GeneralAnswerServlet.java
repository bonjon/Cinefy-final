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

import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;

/**
 * Servlet implementation class GeneralAnswerServlet
 */

@WebServlet("/GeneralAnswerServlet")
public class GeneralAnswerServlet  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static final String ANSWER = "answer.jsp";
	private static final Logger LOGGER = Logger.getLogger(GeneralAnswerServlet.class.getName());
	
	public GeneralAnswerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			HttpSession session = request.getSession();
			session.setAttribute("type", "general");
			RequestDispatcher rd = request.getRequestDispatcher(ANSWER);
			AnswerQuestionsController aqc = new AnswerQuestionsController();
			if (request.getParameter("make") != null) {
				System.out.println("sono qui general ");
				
				rd = this.makeAnswer(request, session, aqc);
				
			}
			else if(request.getParameter("switch") != null){
				rd = request.getRequestDispatcher("FilmAdviceServlet");
			}
			rd.forward(request, response);
		
		}
	

private RequestDispatcher makeAnswer(HttpServletRequest request, HttpSession session,AnswerQuestionsController aqc) {
	String emptyString = "";
	
	String general = "general";
	String answer = (String) request.getParameter("answer");
	if(answer==null) {answer=emptyString;}
	String colleagueMark = (String) request.getParameter("colleagueMark");
	String resourceMark =  (String) request.getParameter("resourceMark");
	
	RispostaBean rb = new RispostaBean();
	DomandaBean db = (DomandaBean) session.getAttribute("QU");
	
	int domId = Integer.parseInt(db.getId());
	
	BeginnerUserBean bub = (BeginnerUserBean) session.getAttribute("begS");
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
	
	rb.setIdDomanda(domId);
	rb.setAdvancedName(gub.getUsername());
	rb.setBeginnerName(bub.getUsername());
	rb.setChoice(general);
	rb.setContenuto(answer);
	if(colleagueMark!=null) {
		
		String colleagueName = (String) request.getParameter("colleagueName");
		if(colleagueName==null) {colleagueName=emptyString;}
		String reasonChoice  = (String) request.getParameter("reasonChoice");
		rb.setColleagueFlag(true);
		rb.setColleagueName(colleagueName);
		rb.setReasonChoice(reasonChoice);
	}
	else{
		rb.setColleagueFlag(false);
		rb.setColleagueName(emptyString);
		rb.setReasonChoice(emptyString);
	}
	if(resourceMark!=null) {
		String wikiLink = (String) request.getParameter("wikiLink");
		if(wikiLink==null) {wikiLink=emptyString;}
		String youtubeLink = (String) request.getParameter("youtubeLink");
		if(youtubeLink==null) {youtubeLink=emptyString;}
		rb.setResourceFlag(true);
		rb.setWikiLink(wikiLink);
		rb.setYoutubeLink(youtubeLink);
	}
	else {
		rb.setResourceFlag(false);
		rb.setWikiLink(emptyString);
		rb.setYoutubeLink(emptyString);
	}
	
	try {
		aqc.createAnswer(rb);
		System.out.println("contenuto risposta creata: "+ rb.getContenuto());
	} catch (FieldTooLongException | FieldEmptyException e) {
		request.setAttribute("error", e.getMessage());
		return request.getRequestDispatcher(ANSWER);
	} catch (SQLException | ClassNotFoundException e) {
		LOGGER.log(Level.WARNING, e.toString());
	}
	return request.getRequestDispatcher("AnswerAdvancedServlet");
}
	

}