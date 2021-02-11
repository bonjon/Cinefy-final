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

import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.PlaylistDetailsController;
import logic.exceptions.PlaylistNotFoundException;

/**
 * Servlet implementation class PlaylistDetailsServlet della view
 * PlaylistDetails.
 */

@WebServlet("/PlaylistDetailsServlet")
public class PlaylistDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(PlaylistDetailsServlet.class.getName());
		
	public PlaylistDetailsServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist.jsp");
		PlaylistDetailsController pdc = new PlaylistDetailsController();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		PlaylistBean pb = (PlaylistBean) session.getAttribute("P");
		if (request.getParameter("BTNOK") != null) {
			try {
				rd = this.votePlaylist(request, pdc, gub, pb);
			} catch (PlaylistNotFoundException e) {
				LOGGER.log(Level.WARNING, e.toString());
			}
		}
		rd.forward(request, response);
	}

	private RequestDispatcher votePlaylist(HttpServletRequest request, PlaylistDetailsController pdc,
			GeneralUserBean gub, PlaylistBean p) throws PlaylistNotFoundException {
		String rating = request.getParameter("rating");
		if (rating != null) {
			try {
				pdc.votePlaylist(Integer.parseInt(rating), p.getId(), gub.getUsername());
				request.setAttribute("error", "Playlist voted!");
			} catch (NumberFormatException | ClassNotFoundException e) {
				LOGGER.log(Level.WARNING, e.toString());
			} catch (SQLException e) {
				request.setAttribute("error", "You've already voted this playlist");
			}
		}
		return request.getRequestDispatcher("playlist.jsp");
	}
}
