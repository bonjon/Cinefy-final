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

import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.controllers.AskForQuestionsController;

/**
 * Servlet implementation class HomeAdminServlet
 */

@WebServlet("/HomeAdminServlet")
public class HomeAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeAdminServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("home_admin.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		List<DomandaBean> questionsList = new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		try {
			questionsList = afc.getQuestions(gub.getUsername(), gub.getRole());
			if (questionsList == null)
				request.setAttribute("error", "No questions");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(request.getParameter("d") != null)
			rd = this.manage(request, session, afc);
		session.setAttribute("questionsList", questionsList);
		rd.forward(request, response);
	}

	private RequestDispatcher manage(HttpServletRequest request, HttpSession session, AskForQuestionsController afc) {
		int index = Integer.parseInt(request.getParameter("index2"));
		@SuppressWarnings("unchecked")
		List<DomandaBean> list = (List<DomandaBean>) session.getAttribute("questionsList");
		DomandaBean db = list.get(index);
		session.setAttribute("question", db);
		return request.getRequestDispatcher("manage.jsp");
	}
}
