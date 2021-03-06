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

import logic.bean.FilmBean;
import logic.bean.PlaylistBean;
import logic.controllers.CreatePlaylistController;
import logic.controllers.ViewListOfFilmsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FilmNotFoundException;

/**
 * Servlet implementation class AddFilmServlet per aggiungere i film in
 * playlist.
 */

@WebServlet("/AddFilmServlet")
public class AddFilmServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(AddFilmServlet.class.getName());
	private static final long serialVersionUID = 1L;
	public static final String ADDFILM = "add_film.jsp";
	public static final String ERROR = "error";

	public AddFilmServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher(ADDFILM);
		CreatePlaylistController cpc = new CreatePlaylistController();
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		PlaylistBean pb = (PlaylistBean) session.getAttribute("pb");
		FilmBean fb = new FilmBean();
		String film = (String) request.getParameter("film");
		try {
			fb = vfc.getFilm(film);
			addFilm(cpc, pb, fb, request);
			rd = request.getRequestDispatcher(ADDFILM);
		} catch (FilmNotFoundException e) {
			request.setAttribute(ERROR, e.getMessage());
			rd = request.getRequestDispatcher(ADDFILM);
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		rd.forward(request, response);
	}

	private void addFilm(CreatePlaylistController cpc, PlaylistBean pb, FilmBean fb, HttpServletRequest request) {
		try {
			cpc.addFilm(Integer.parseInt(pb.getId()), fb);
			request.setAttribute(ERROR, "Film added in Playlist!");
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.WARNING, e.toString());
		} catch (FieldEmptyException e) {
			request.setAttribute(ERROR, e.getMessage());
		}
	}
}
