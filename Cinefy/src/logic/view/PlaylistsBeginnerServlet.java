package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.PlaylistBean;
import logic.controllers.ViewPlaylistsController;
import logic.exceptions.PlaylistNotFoundException;

/**
 * Servlet implementation class PlaylistBeginnerServlet della view
 * PlaylistBeginner.
 */

@WebServlet("/PlaylistsBeginnerServlet")
public class PlaylistsBeginnerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String TOPLP = "topLP";
	private static final Logger LOGGER = Logger.getLogger(PlaylistsBeginnerServlet.class.getName());

	public PlaylistsBeginnerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist_beginner.jsp");
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		List<PlaylistBean> topLP = new ArrayList<>();
		try {
			topLP = vpc.getLeaderBoard();
			request.setAttribute(TOPLP, topLP);
		} catch (PlaylistNotFoundException e) {
			request.setAttribute(TOPLP, Collections.emptyList());
			request.setAttribute("errorx", e.getMessage());
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
		session.setAttribute(TOPLP, topLP);
		if (request.getParameter("a") != null)
			rd = this.goToPlaylist(session, request);
		rd.forward(request, response);
	}

	private RequestDispatcher goToPlaylist(HttpSession session, HttpServletRequest request) {
		int index = Integer.parseInt(request.getParameter("index"));
		@SuppressWarnings("unchecked")
		List<PlaylistBean> topLP = (List<PlaylistBean>) session.getAttribute(TOPLP);
		PlaylistBean pb = topLP.get(index);
		session.setAttribute("P", pb);
		return request.getRequestDispatcher("playlist.jsp");
	}
}
