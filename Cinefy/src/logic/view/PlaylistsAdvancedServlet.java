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

import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.ViewPlaylistsController;
import logic.exceptions.PlaylistNotFoundException;

/**
 * Servlet implementation class PlaylistsAdvancedServlet della view
 * PlaylistAdvanced.
 */

@WebServlet("/PlaylistsAdvancedServlet")
public class PlaylistsAdvancedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String TOPLP = "topLP";
	private static final Logger LOGGER = Logger.getLogger(PlaylistsAdvancedServlet.class.getName());

	public PlaylistsAdvancedServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist_advanced.jsp");
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		List<PlaylistBean> topLP = new ArrayList<>();
		List<PlaylistBean> lp = new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
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
		try {
			lp = vpc.getPlaylistByAd(gub.getUsername());
			request.setAttribute("LP", lp);
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("LP", Collections.emptyList());
			request.setAttribute("errorx", e.getMessage());
		}
		session.setAttribute("LP", lp);
		if (request.getParameter("a") != null)
			rd = this.goToPlaylist(session, request, topLP);
		if (request.getParameter("b") != null)
			rd = this.goToPlaylist(session, request, lp);
		rd.forward(request, response);
	}

	private RequestDispatcher goToPlaylist(HttpSession session, HttpServletRequest request, List<PlaylistBean> topLP) {
		int index = Integer.parseInt(request.getParameter("index"));
		PlaylistBean pb = topLP.get(index);
		session.setAttribute("P", pb);
		return request.getRequestDispatcher("playlist.jsp");
	}
}
