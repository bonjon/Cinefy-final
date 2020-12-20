package logic.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public PlaylistsAdvancedServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist_advanced.jsp");
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		List<PlaylistBean> topLP = new ArrayList<>();
		List<PlaylistBean> LP = new ArrayList<>();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		try {
			topLP = vpc.getLeaderBoard();
			request.setAttribute("topLP", topLP);
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("topLP", Collections.EMPTY_LIST);
			request.setAttribute("errorx", e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("topLP", topLP);
		try {
			LP = vpc.getPlaylistByAd(gub.getUsername());
			request.setAttribute("LP", LP);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("LP", Collections.EMPTY_LIST);
			request.setAttribute("errorx", e.getMessage());
		}
		session.setAttribute("LP", LP);
		if (request.getParameter("a") != null)
			rd = this.goToPlaylist(session, request, vpc, topLP);
		if (request.getParameter("b") != null)
			rd = this.goToPlaylist(session, request, vpc, LP);
		rd.forward(request, response);
	}

	private RequestDispatcher goToPlaylist(HttpSession session, HttpServletRequest request,
			ViewPlaylistsController vpc, List<PlaylistBean> topLP) {
		int index = Integer.parseInt(request.getParameter("index"));
		PlaylistBean pb = topLP.get(index);
		session.setAttribute("P", pb);
		return request.getRequestDispatcher("playlist.jsp");
	}
}
