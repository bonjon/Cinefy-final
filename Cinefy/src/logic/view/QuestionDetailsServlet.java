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
import logic.bean.RispostaBean;
import logic.controllers.AskForQuestionsController;

/**
 * Servlet implementation class QuestionDetailsServlet che è la view di
 * QuestionDetails
 */

@WebServlet("/QuestionDetailsServlet")
public class QuestionDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public QuestionDetailsServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("question_details.jsp");
		AskForQuestionsController afc = new AskForQuestionsController();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		DomandaBean db = (DomandaBean) session.getAttribute("QU");
		RispostaBean r = new RispostaBean();
		try {
			r = afc.getAnswer(gub.getUsername(), db.getId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("R", r);
		if (request.getParameter("BTNOK") != null) {
			rd = this.voteAdvanced(request, session, afc, gub, r);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher voteAdvanced(HttpServletRequest request, HttpSession session,
			AskForQuestionsController afc, GeneralUserBean gub, RispostaBean r) {
		String rating = request.getParameter("rating");
		if (rating != null) {
			AdvancedUserBean aub = new AdvancedUserBean();
			aub.setUsername(r.getAdvancedName());
			aub.setVoto(Integer.parseInt(rating));
			int a = (int) Double.parseDouble(aub.getVoto());
			try {
				afc.voteAdvanced(aub.getUsername(),a, gub, r);
				request.setAttribute("error", "Advanced voted!");
			} catch (SQLException e) {
				request.setAttribute("error", "You Already vote this advanced");
			}
		}
		return request.getRequestDispatcher("question_details.jsp");
	}
}
