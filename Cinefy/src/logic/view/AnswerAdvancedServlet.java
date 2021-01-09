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
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		
		try {
			questions = aqc.getQuestions(gub.getUsername(), "advanced");
			if (questions.isEmpty())
				request.setAttribute(ERROR, "No questions list");
			else
				request.setAttribute("questions", questions);
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		session.setAttribute("questions", questions);
		if (request.getParameter("d") != null) {
			int index = Integer.parseInt(request.getParameter("index2"));
			DomandaBean db = (DomandaBean) questions.get(index);
			session.setAttribute("QU", db);
		}
		rd.forward(request, response);
	}
}