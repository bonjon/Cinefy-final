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

import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.PlaylistDetailsController;

/**
 * Servlet implementation class PlaylistDetailsServlet della view PlaylistDetails.
 */

@WebServlet("/PlaylistDetailsServlet")
public class PlaylistDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    public PlaylistDetailsServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("playlist.jsp");
		PlaylistDetailsController pdc = new PlaylistDetailsController();
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		PlaylistBean P = (PlaylistBean) session.getAttribute("P");
		if (request.getParameter("BTNOK") != null) {
			rd = this.votePlaylist(request, response, pdc, gub, P);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher votePlaylist(HttpServletRequest request, HttpServletResponse response,
			PlaylistDetailsController pdc, GeneralUserBean gub, PlaylistBean p) {
		String rating = request.getParameter("rating");
		if(rating != null) {
			try {
				pdc.votePlaylist(Integer.parseInt(rating), p.getId(), gub.getUsername());
				request.setAttribute("error", "Playlist voted!");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				request.setAttribute("error", "You've already voted this playlist");
			}
		}
		return request.getRequestDispatcher("playlist.jsp");
	}
}
