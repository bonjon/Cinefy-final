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

import logic.bean.DomandaBean;
import logic.controllers.AskForQuestionsController;

/**
 * Servlet implementation class ManageServlet
 */

@WebServlet("/ManageServlet")
public class ManageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
 
    public ManageServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("manage.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		if (request.getParameter("accept") != null)
			rd = this.accept(request,session, afc);
		else if(request.getParameter("reject") != null)
			rd = this.reject(request,session,afc);
		rd.forward(request, response);
	}

	private RequestDispatcher reject(HttpServletRequest request, HttpSession session, AskForQuestionsController afc) {
		DomandaBean db = (DomandaBean) session.getAttribute("question");
		try {
			afc.rejectQuestion(db);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return request.getRequestDispatcher("HomeAdminServlet");
	}

	private RequestDispatcher accept(HttpServletRequest request, HttpSession session, AskForQuestionsController afc) {
		DomandaBean db = (DomandaBean) session.getAttribute("question");
		try {
			afc.acceptQuestion(db);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return request.getRequestDispatcher("HomeAdminServlet");
	}

}
