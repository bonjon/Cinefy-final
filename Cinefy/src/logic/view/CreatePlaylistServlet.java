package logic.view;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import logic.bean.FilmBean;
import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.CreatePlaylistController;
import logic.controllers.ViewListOfFilmsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.exceptions.FilmNotFoundException;
import logic.utils.FileManager;

/**
 * Servlet implementation class CreatePlaylistServlet
 */

@WebServlet("/CreatePlaylistServlet")
public class CreatePlaylistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CreatePlaylistServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("create_playlist.jsp");
		CreatePlaylistController cpc = new CreatePlaylistController();
		FilmBean F = new FilmBean();
		session.setAttribute("check", "false");
		session.setAttribute("search", "false");
		if (request.getParameter("ok") != null) {
			rd = this.create(request, session, cpc);
		}
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		String film = request.getParameter("film");
		try {
			F = vfc.getFilm(film);
			session.setAttribute("search", "true");
		} catch (FilmNotFoundException e) {
			request.setAttribute("error", e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("F", F);
		if (request.getParameter("add") != null) {
			rd = this.addFilm(request, session, cpc);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher addFilm(HttpServletRequest request, HttpSession session, CreatePlaylistController cpc) {
		FilmBean F = (FilmBean) session.getAttribute("F");
		PlaylistBean P = (PlaylistBean) session.getAttribute("pb");
		try {
			cpc.addFilm(Integer.parseInt(P.getId()),F);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			request.setAttribute("error", "Film already added");
		} catch (FieldEmptyException e) {
			request.setAttribute("error", e.getMessage());
		}
		return request.getRequestDispatcher("create_playlist.jsp");
	}

	private RequestDispatcher create(HttpServletRequest request, HttpSession session, CreatePlaylistController cpc) {
		String name = (String) request.getParameter("name");
		String newFileName = null;
		String fileName = "";
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		PlaylistBean pb = new PlaylistBean();
		Part filePart = null;
		try {
			filePart = request.getPart("avatar");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		if (filePart != null) {
			fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		}
		newFileName = FileManager.generateNewFileName(fileName, name);
		try {
			pb = cpc.createPlaylist(name, gub.getUsername(), newFileName);
			session.setAttribute("check", "true");
		} catch (FieldEmptyException e) {
			request.setAttribute("errorx", e.getMessage());
		} catch (FieldTooLongException e) {
			request.setAttribute("errorx", e.getMessage());
		} catch (SQLException e) {
			request.setAttribute("errorx", "Name already taken");
		}
		session.setAttribute("pb", pb);
		return request.getRequestDispatcher("create_playlist.jsp");
	}
}
