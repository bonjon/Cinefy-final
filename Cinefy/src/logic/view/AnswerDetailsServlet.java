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

import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;

/**
 * Servlet implementation class AnswerDetailsServlet che è la view di
 * AnswerDetails
 */

@WebServlet("/AnswerDetailsServlet")
public class AnswerDetailsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AnswerDetailsServlet.class.getName());

	public AnswerDetailsServlet() {
		super();
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		AnswerQuestionsController aqc = new AnswerQuestionsController();
		RispostaBean rb = (RispostaBean) session.getAttribute("RI");
		int idDomanda = Integer.parseInt(rb.getIdDomanda());
		DomandaBean qu = new DomandaBean();
		boolean check = false;
		int vote = 0;
		RispostaBean perVoto = new RispostaBean();
		try {
			qu = aqc.getQuestion(idDomanda);
			check = aqc.checkAnswer(rb);
			perVoto = aqc.getVoto(qu.getBeginnerName(), rb.getId());
			vote = perVoto.getVoto();
			
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.WARNING,e.getMessage());
		}
		session.setAttribute("quDetails", qu);
		session.setAttribute("check", check);
		session.setAttribute("vote", vote);
		RequestDispatcher rd = request.getRequestDispatcher("answer_details.jsp");
		rd.forward(request, response);
	}
}
