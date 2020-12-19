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
       
    public SearchPlaylistServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist_beginner.jsp");
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		PlaylistBean pb = (PlaylistBean) session.getAttribute("P");
		String searchString = (String) request.getParameter("searchString");
		if(searchString == null) {
			searchString = (String) request.getAttribute("searchString");
		}
		request.setAttribute("searchString", searchString);
		try {
			pb = vpc.getPlaylist(searchString);
			session.setAttribute("P", pb);
			rd = request.getRequestDispatcher("playlist.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PlaylistNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			rd = request.getRequestDispatcher("PlaylistsBeginnerServlet");
		}
		rd.forward(request, response);
	}
}
