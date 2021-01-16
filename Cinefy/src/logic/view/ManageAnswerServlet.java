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

import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;

/**
 * Servlet implementation class ManageAnswerServlet
 */

@WebServlet("/ManageAnswerServlet")
public class ManageAnswerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ManageAnswerServlet.class.getName());
       
    public ManageAnswerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("manage2.jsp");
		AnswerQuestionsController aqc = new AnswerQuestionsController();
		if (request.getParameter("accept") != null) {
			rd = this.accept(request, session, aqc);
		}
		else if (request.getParameter("reject") != null) {
			rd = this.reject(request, session, aqc);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher reject(HttpServletRequest request, HttpSession session, AnswerQuestionsController aqc) {
		RispostaBean rb = (RispostaBean) session.getAttribute("answer");
		try {
			aqc.rejectAnswer(rb);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return request.getRequestDispatcher("HomeAdminServlet"); 
	}

	private RequestDispatcher accept(HttpServletRequest request, HttpSession session, AnswerQuestionsController aqc) {
		RispostaBean rb = (RispostaBean) session.getAttribute("answer");
		try {
			aqc.acceptAnswer(rb);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return request.getRequestDispatcher("HomeAdminServlet");
	}

}
