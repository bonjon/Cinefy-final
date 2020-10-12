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

import logic.bean.FilmBean;
import logic.controllers.ViewListOfFilmsController;
import logic.exceptions.FilmNotFoundException;

/**
 * Servlet implementation class HomeBeginnerServlet della view HomeBeginner.
 */

@WebServlet("/HomeBeginnerServlet")
public class HomeBeginnerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SEARCHSTRING = "searchString";

	public HomeBeginnerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		FilmBean listFilms = new FilmBean();
		List<FilmBean> filmList = new ArrayList<>();
		String searchString = request.getParameter(SEARCHSTRING);
		String userProf = request.getParameter("userProf");
		if (searchString == null) {
			searchString = (String) request.getAttribute(SEARCHSTRING);
		}
		request.setAttribute(SEARCHSTRING, searchString);
		try {
			if (userProf.equals("Name")) {
				listFilms = vfc.getFilm(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("listFilms", listFilms);
				rd = request.getRequestDispatcher("film_result.jsp");
			} else if(userProf.equals("Actor")) {
				filmList = vfc.getFilmByActor(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("filmList", filmList);
				rd = request.getRequestDispatcher("film_filter.jsp");
			} else if(userProf.equals("Director")) {
				filmList = vfc.getFilmByDirector(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("filmList", filmList);
				rd = request.getRequestDispatcher("film_filter.jsp");
			} else if(userProf.equals("Year")) {
				filmList = vfc.getFilmByYear(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("filmList", filmList);
				rd = request.getRequestDispatcher("film_filter.jsp");
			} else if(userProf.equals("Genre")) {
				filmList = vfc.getFilmByGenre(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("filmList", filmList);
				rd = request.getRequestDispatcher("film_filter.jsp");
			} else if(userProf.equals("Nation")) {
				filmList = vfc.getFilmByNation(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute("userProf", userProf);
				session.setAttribute("filmList", filmList);
				rd = request.getRequestDispatcher("film_filter.jsp");
			}
		} catch (FilmNotFoundException e) {
			request.setAttribute("search", e.getMessage());
			rd = request.getRequestDispatcher("home_beginner.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}
}
