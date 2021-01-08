package logic.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.GeneralUserBean;
import logic.controllers.LoginController;
import logic.exceptions.FieldEmptyException;

/**
 * Servlet implementation class LoginServlet che sarà la view del caso d'uso
 * Login.
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String INDEX = "index.jsp";
	private static final String LOGIN = "login";
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
	
	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher(INDEX);
		LoginController controller = new LoginController();
		if (request.getParameter(LOGIN) != null) {
			rd = this.login(request, session, controller);
		} else if (request.getParameter("registration") != null) {
			rd = request.getRequestDispatcher("registration.jsp");
		}
		rd.forward(request, response);
	}

	private RequestDispatcher login(HttpServletRequest request, HttpSession session, LoginController controller) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		GeneralUserBean gub = new GeneralUserBean();
		gub.setUsername(username);
		gub.setPassword(password);
		try {
			gub = controller.login(gub);
			if (gub == null) {
				request.setAttribute(LOGIN, "Wrong username or password");
				return request.getRequestDispatcher(INDEX);
			} 
			if (gub.getRole().equals("admin")) {
				session.setAttribute("user", gub);
				return request.getRequestDispatcher("HomeAdminServlet");
			} else {
				session.setAttribute("user", gub);
				return request.getRequestDispatcher("home_beginner.jsp");
			}
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		} catch (FieldEmptyException e) {
			request.setAttribute(LOGIN, e.getMessage());
		}
		return request.getRequestDispatcher(INDEX);
	}

}
