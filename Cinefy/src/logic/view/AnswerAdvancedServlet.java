package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.controllers.AskForQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

/**
 * Servlet implementation class AnswerAdvancedServlet che è la view di AnswerAdvanced.
 */

@WebServlet("/AnswerAdvancedServlet")
public class AnswerAdvancedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AnswerAdvancedServlet.class.getName());
	public static final String ERROR = "error";

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
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		
		try {
			questions = aqc.getQuestions(gub.getUsername(), "advanced");
			questionsDel = aqc.deleteQuestion(questions, gub.getUsername());
			if (questionsDel.isEmpty()) {
				request.setAttribute(ERROR, "No received questions without answer");
			}
			else {
				request.setAttribute("questions", questionsDel);
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("sono in catch");
		}
		if (request.getParameter("d") != null) {
			int index = Integer.parseInt(request.getParameter("index2"));
			DomandaBean db = (DomandaBean) questionsDel.get(index);
			session.setAttribute("QU", db);
		}else {
			LOGGER.log(Level.WARNING,"else funziona");
		}
		rd.forward(request, response);
	}
}