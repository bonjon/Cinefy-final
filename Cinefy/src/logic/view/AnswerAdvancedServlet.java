package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import logic.exceptions.AnswersNotFoundException;


/**
 * Servlet implementation class AnswerAdvancedServlet che è la view di AnswerAdvanced.
 */

@WebServlet("/AnswerAdvancedServlet")
public class AnswerAdvancedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AnswerAdvancedServlet.class.getName());
	public static final String ERROR = "error";
	public static final String ERROR2 = "error2";

	public AnswerAdvancedServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("answer_advanced.jsp");
		AnswerQuestionsController aqc = new AnswerQuestionsController();
		List<DomandaBean> questions = new ArrayList<>();
		List<DomandaBean> questionsDel = new ArrayList<>();
		List<RispostaBean> allAnswers= new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		System.out.println(gub.getUsername());
		BeginnerUserBean bub;
		
		try {
			questions = aqc.getQuestions(gub.getUsername(), "advanced");
			questionsDel = aqc.deleteQuestion(questions, gub.getUsername());
			if (questionsDel.isEmpty()) {
				request.setAttribute(ERROR, "No received questions without answer");
			}
			else {
				request.setAttribute("questions", questionsDel);
			}
		
			allAnswers = aqc.getAllAnswers(gub.getUsername());
			if(!allAnswers.isEmpty()) {
				request.setAttribute("answers", allAnswers);
			}
		
	} 
		catch(AnswersNotFoundException e ) {
			request.setAttribute(ERROR2, e.getMessage());
		}
		
		catch (SQLException | ClassNotFoundException e) {
		LOGGER.log(Level.WARNING,e.getMessage());
	}
		
	try {
		if (request.getParameter("d") != null) {
			int index = Integer.parseInt(request.getParameter("index2"));
			DomandaBean db = (DomandaBean) questionsDel.get(index);
			String beginner = db.getBeginnerName();
			
			bub = aqc.getBeginnerUser(beginner, "beginner");
			session.setAttribute("QU", db);
			session.setAttribute("begS", bub);
			rd = request.getRequestDispatcher("GeneralAnswerServlet");
		}else if (request.getParameter("a") !=null){
			int index = Integer.parseInt(request.getParameter("ansIndex"));
			RispostaBean rb = (RispostaBean) allAnswers.get(index);
			session.setAttribute("RI", rb);
			rd = request.getRequestDispatcher("AnswerDetailsServlet");
			
		}
		
	} catch (ClassNotFoundException | SQLException e) {
		LOGGER.log(Level.WARNING,e.getMessage());
	}
		rd.forward(request, response);
	}
}