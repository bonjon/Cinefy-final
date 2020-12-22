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
 * Servlet implementation class AddFilmServlet per aggiungere i film in playlist.
 */

@WebServlet("/AddFilmServlet")
public class AddFilmServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public AddFilmServlet() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("add_film.jsp");
		CreatePlaylistController cpc = new CreatePlaylistController();
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		PlaylistBean pb = (PlaylistBean) session.getAttribute("pb");
		FilmBean F = new FilmBean();
		String film = (String) request.getParameter("film");
		try {
			F = vfc.getFilm(film);
			try {
				cpc.addFilm(Integer.parseInt(pb.getId()), F);
				request.setAttribute("error", "Film added in Playlist!");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (FieldEmptyException e) {
				request.setAttribute("error", e.getMessage());
				rd = request.getRequestDispatcher("add_film.jsp");
			}
			rd = request.getRequestDispatcher("add_film.jsp");
		} catch (FilmNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			rd = request.getRequestDispatcher("add_film.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rd.forward(request, response);
	}
}
