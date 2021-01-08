package logic.view;

import java.io.IOException;
import java.sql.SQLException;
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
	public static final String USERPROF = "userProf";
	public static final String FILMLIST = "filmList";
	public static final String FILTER = "film_filter.jsp";

	public HomeBeginnerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("home_beginner.jsp");
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		String searchString = request.getParameter(SEARCHSTRING);
		String userProf = request.getParameter(USERPROF);
		if (searchString == null) {
			searchString = (String) request.getAttribute(SEARCHSTRING);
		}
		request.setAttribute(SEARCHSTRING, searchString);
		try {
			if (userProf.equals("Name")) {
				FilmBean listFilms = vfc.getFilm(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute("listFilms", listFilms);
				rd = request.getRequestDispatcher("film_result.jsp");
			} else if (userProf.equals("Actor")) {
				List<FilmBean> filmList = vfc.getFilmByActor(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute(FILMLIST, filmList);
				rd = request.getRequestDispatcher(FILTER);
			} else if (userProf.equals("Director")) {
				List<FilmBean> filmList = vfc.getFilmByDirector(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute(FILMLIST, filmList);
				rd = request.getRequestDispatcher(FILTER);
			} else if (userProf.equals("Year")) {
				List<FilmBean> filmList = vfc.getFilmByYear(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute(FILMLIST, filmList);
				rd = request.getRequestDispatcher(FILTER);
			} else if (userProf.equals("Genre")) {
				List<FilmBean> filmList = vfc.getFilmByGenre(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute(FILMLIST, filmList);
				rd = request.getRequestDispatcher(FILTER);
			} else if (userProf.equals("Nation")) {
				List<FilmBean> filmList = vfc.getFilmByNation(searchString);
				session.setAttribute(SEARCHSTRING, searchString);
				session.setAttribute(USERPROF, userProf);
				session.setAttribute(FILMLIST, filmList);
				rd = request.getRequestDispatcher(FILTER);
			}
		} catch (FilmNotFoundException e) {
			request.setAttribute("search", e.getMessage());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}
}
