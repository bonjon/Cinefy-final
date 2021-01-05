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

	private static final long serialVersionUID = 1L;
	public static final String ADDFILM = "add_film.jsp";
	public static final String ERROR = "";

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
		FilmBean Fb = new FilmBean();
		String film = (String) request.getParameter("film");
		try {
			Fb = vfc.getFilm(film);
			addFilm(cpc, pb, Fb, request, rd);
			rd = request.getRequestDispatcher(ADDFILM);
		} catch (FilmNotFoundException e) {
			request.setAttribute(ERROR, e.getMessage());
			rd = request.getRequestDispatcher(ADDFILM);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		rd.forward(request, response);
	}

	private void addFilm(CreatePlaylistController cpc, PlaylistBean pb, FilmBean fb, HttpServletRequest request,
			RequestDispatcher rd) {
		try {
			cpc.addFilm(Integer.parseInt(pb.getId()), fb);
			request.setAttribute(ERROR, "Film added in Playlist!");
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (FieldEmptyException e) {
			request.setAttribute(ERROR, e.getMessage());
		} 
	}
}
