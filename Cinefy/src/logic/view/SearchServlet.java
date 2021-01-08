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
import logic.controllers.AskForQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

/**
 * Servlet implementation class SearchServlet
 */

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String SEARCHSTRING = "searchString";
	private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

	public SearchServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("question.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		String searchString = (String) request.getParameter(SEARCHSTRING);
		if (searchString == null) {
			searchString = (String) request.getAttribute(SEARCHSTRING);
		}
		request.setAttribute(SEARCHSTRING, searchString);
		AdvancedUserBean au = new AdvancedUserBean();
		au.setUsername(searchString);
		try {
			AdvancedUserBean aub = afc.getAdvanced(au);
			session.setAttribute("AdS", aub);
		} catch (AdvancedNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			rd = request.getRequestDispatcher("AskBeginnerServlet");
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		rd.forward(request, response);
	}
}
