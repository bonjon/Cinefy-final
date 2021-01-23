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
	public static final String QUESTION = "question.jsp";
	private static final Logger LOGGER = Logger.getLogger(MakeQuestionServlet.class.getName());

	public MakeQuestionServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher(QUESTION);
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
		} catch (FieldTooLongException | FieldEmptyException e) {
			request.setAttribute("error3", e.getMessage());
			return request.getRequestDispatcher(QUESTION);
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		return request.getRequestDispatcher("AskBeginnerServlet");
	}
}
