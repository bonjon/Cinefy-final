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

import logic.bean.PlaylistBean;
import logic.controllers.ViewPlaylistsController;
import logic.exceptions.PlaylistNotFoundException;

/**
 * Servlet implementation class SearchPlaylistServlet
 */

@WebServlet("/SearchPlaylistServlet")
public class SearchPlaylistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String SEARCHSTRING = "searchString";
	
	public SearchPlaylistServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist.jsp");
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		String searchString = (String) request.getParameter(SEARCHSTRING);
		if (searchString == null) {
			searchString = (String) request.getAttribute(SEARCHSTRING);
		}
		request.setAttribute(SEARCHSTRING, searchString);
		try {
			PlaylistBean pb = vpc.getPlaylist(searchString);
			session.setAttribute("P", pb);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			rd = request.getRequestDispatcher("PlaylistsBeginnerServlet");
		}
		rd.forward(request, response);
	}
}
