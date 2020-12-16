package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import logic.controllers.AskForQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

/**
 * Servlet implementation class AskBeginnerServlet che è la view di AskBeginner.
 */

@WebServlet("/AskBeginnerServlet")
public class AskBeginnerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			request.setAttribute("topAd", topAd);
		} catch (SQLException e) {
			request.setAttribute("error", e.getMessage());
		} catch (AdvancedNotFoundException e) {
			e.printStackTrace();
		}
		session.setAttribute("topAd", topAd);
		try {
			questions = afc.getQuestions(gub, gub.getRole());
			if (questions == null)
				request.setAttribute("error", "No questions list");
			else
				request.setAttribute("questions", questions);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (request.getParameter("a") != null) {
			rd = this.goToQuestion(session, request, afc);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher goToQuestion(HttpSession session, HttpServletRequest request,
			AskForQuestionsController afc) {
		int index = Integer.parseInt(request.getParameter("index"));
		@SuppressWarnings("unchecked")
		List<AdvancedUserBean> topAd = (List<AdvancedUserBean>) session.getAttribute("topAd");
		AdvancedUserBean aub = (AdvancedUserBean) topAd.get(index);
		session.setAttribute("AdS", aub);
		return request.getRequestDispatcher("question.jsp");
	}
}
