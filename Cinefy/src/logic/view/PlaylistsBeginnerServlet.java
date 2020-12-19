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
			request.setAttribute("topLP", topLP);
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("topLP", Collections.EMPTY_LIST);
			request.setAttribute("errorx", e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("topLP", topLP);
		if (request.getParameter("a") != null)
			rd = this.goToPlaylist(session, request, vpc);
		rd.forward(request, response);
	}

	private RequestDispatcher goToPlaylist(HttpSession session, HttpServletRequest request,
			ViewPlaylistsController vpc) {
		int index = Integer.parseInt(request.getParameter("index"));
		@SuppressWarnings("unchecked")
		List<PlaylistBean> topLP = (List<PlaylistBean>) session.getAttribute("topLP");
		PlaylistBean pb = topLP.get(index);
		session.setAttribute("P", pb);
		return request.getRequestDispatcher("playlist.jsp");
	}
}
