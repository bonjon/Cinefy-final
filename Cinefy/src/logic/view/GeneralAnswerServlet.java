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
			RequestDispatcher rd = request.getRequestDispatcher(ANSWER);
			AnswerQuestionsController aqc = new AnswerQuestionsController();
			if (request.getParameter("make") != null)
				rd = this.makeAnswer(request, session, aqc);
			rd.forward(request, response);
		}
	

private RequestDispatcher makeAnswer(HttpServletRequest request, HttpSession session,AnswerQuestionsController aqc) {
	String answer = (String) request.getParameter("answer");
	String colleagueMark = (String) request.getParameter("colleagueMark");
	String colleagueName = (String) request.getParameter("colleagueName");
	String wikiLink = (String) request.getParameter("wikiLink");
	String youtubeLink = (String) request.getParameter("youtubeLink");
	RispostaBean rb = new RispostaBean();
	DomandaBean db = (DomandaBean) session.getAttribute("QU");
	
	int domId = Integer.parseInt(db.getId());
	
	BeginnerUserBean bub = (BeginnerUserBean) session.getAttribute("begS");
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
	
	rb.setIdDomanda(domId);
	rb.setAdvancedName(gub.getUsername());
	rb.setBeginnerName(bub.getUsername());
	rb.setChoice("general");
	rb.setContenuto(answer);
	if(colleagueMark.equals("true")) {
		rb.setColleagueFlag(true);
	}
	else {
		rb.setColleagueFlag(false);
	}
	rb.setWikiLink(wikiLink);
	rb.setYoutubeLink(youtubeLink);
	rb.setResourceFlag(false);
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