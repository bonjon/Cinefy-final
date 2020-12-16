package logic.view;

import java.io.IOException;
import java.sql.SQLException;

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
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;

/**
 * Servlet implementation class MakeQuestionServlet
 */

@WebServlet("/MakeQuestionServlet")
public class MakeQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MakeQuestionServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("question.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		if (request.getParameter("make") != null)
			rd = this.makeQuestion(request, session, afc);
		rd.forward(request, response);
	}

	private RequestDispatcher makeQuestion(HttpServletRequest request, HttpSession session,
			AskForQuestionsController afc) {
		String question = (String) request.getParameter("question");
		DomandaBean db = new DomandaBean();
		db.setContenuto(question);
		AdvancedUserBean aub = (AdvancedUserBean) session.getAttribute("AdS");
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		db.setAdvancedName(aub.getUsername());
		db.setBeginnerName(gub.getUsername());
		try {
			afc.makeQuestion(db);
		} catch (FieldTooLongException e) {
			request.setAttribute("error", e.getMessage());
			return request.getRequestDispatcher("AskBeginnerServlet");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FieldEmptyException e) {
			request.setAttribute("error", e.getMessage());
		}
		return request.getRequestDispatcher("AskBeginnerServlet");
	}
}
