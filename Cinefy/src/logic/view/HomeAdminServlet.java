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

import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.controllers.AskForQuestionsController;

/**
 * Servlet implementation class HomeAdminServlet
 */

@WebServlet("/HomeAdminServlet")
public class HomeAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(HomeAdminServlet.class.getName());
	
	public HomeAdminServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("home_admin.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		AnswerQuestionsController aqc = new AnswerQuestionsController();
		List<DomandaBean> questionsList = new ArrayList<>();
		List<RispostaBean> answersList = new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		try {
			questionsList = afc.getQuestions(gub, gub.getRole());
			answersList = aqc.getAnswers(gub.getUsername(), gub.getRole());
			if (questionsList.isEmpty())
				request.setAttribute("error", "No questions");
			if (answersList.isEmpty())
				request.setAttribute("search", "No answers");
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		if(request.getParameter("d") != null)
			rd = this.manage(request, session);
		if(request.getParameter("r") != null)
			rd = this.manage2(request, session);
		session.setAttribute("questionsList", questionsList);
		session.setAttribute("answersList", answersList);
		rd.forward(request, response);
	}

	private RequestDispatcher manage2(HttpServletRequest request, HttpSession session) {
		int index = Integer.parseInt(request.getParameter("index"));
		@SuppressWarnings("unchecked")
		List<RispostaBean> list = (List<RispostaBean>) session.getAttribute("answersList");
		RispostaBean rb = list.get(index);
		session.setAttribute("answer", rb);
		return request.getRequestDispatcher("manage2.jsp");
	}

	private RequestDispatcher manage(HttpServletRequest request, HttpSession session) {
		int index = Integer.parseInt(request.getParameter("index2"));
		@SuppressWarnings("unchecked")
		List<DomandaBean> list = (List<DomandaBean>) session.getAttribute("questionsList");
		DomandaBean db = list.get(index);
		session.setAttribute("question", db);
		return request.getRequestDispatcher("manage.jsp");
	}
}
