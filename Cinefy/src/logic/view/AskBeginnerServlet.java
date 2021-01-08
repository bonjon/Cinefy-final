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
import logic.controllers.AskForQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

/**
 * Servlet implementation class AskBeginnerServlet che è la view di AskBeginner.
 */

@WebServlet("/AskBeginnerServlet")
public class AskBeginnerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AskBeginnerServlet.class.getName());
	public static final String TOPAD = "topAd";
	public static final String ERROR = "error";

	public AskBeginnerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("ask_beginner.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		List<AdvancedUserBean> topAd = new ArrayList<>();
		List<DomandaBean> questions = new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		try {
			topAd = afc.leaderBoard();
			if (topAd.isEmpty()) {
				request.setAttribute(TOPAD, Collections.emptyList());
				request.setAttribute("errorx", "No advanced leaderboard");
			} else {
				request.setAttribute(TOPAD, topAd);
			}
		} catch (AdvancedNotFoundException | SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		session.setAttribute(TOPAD, topAd);
		try {
			questions = afc.getQuestions(gub, gub.getRole());
			if (questions.isEmpty())
				request.setAttribute(ERROR, "No questions list");
			else
				request.setAttribute("questions", questions);
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		session.setAttribute("questions", questions);
		if (request.getParameter("a") != null) {
			rd = this.goToQuestion(session, request);
		} else if (request.getParameter("d") != null) {
			int index = Integer.parseInt(request.getParameter("index2"));
			DomandaBean db = (DomandaBean) questions.get(index);
			session.setAttribute("QU", db);
			int i = getAnswer(gub, db, request);
			if (i == 0) {
				rd = request.getRequestDispatcher("QuestionDetailsServlet");
			} else {
				rd = request.getRequestDispatcher("ask_beginner.jsp");
			}
		}
		rd.forward(request, response);
	}

	private int getAnswer(GeneralUserBean gub, DomandaBean db, HttpServletRequest request) {
		int i = 0;
		try {
			AskForQuestionsController afc = new AskForQuestionsController();
			RispostaBean r = afc.getAnswer(gub.getUsername(), db.getId());
			if (r == null) {
				request.setAttribute(ERROR, "No answer from this advanced");
				i = 1;
			}
		} catch (NumberFormatException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		} catch (SQLException e) {
			request.setAttribute(ERROR, e.getMessage());
		}
		return i;
	}

	private RequestDispatcher goToQuestion(HttpSession session, HttpServletRequest request) {
		int index = Integer.parseInt(request.getParameter("index"));
		@SuppressWarnings("unchecked")
		List<AdvancedUserBean> topAd = (List<AdvancedUserBean>) session.getAttribute(TOPAD);
		AdvancedUserBean aub = (AdvancedUserBean) topAd.get(index);
		session.setAttribute("AdS", aub);
		return request.getRequestDispatcher("question.jsp");
	}
}
