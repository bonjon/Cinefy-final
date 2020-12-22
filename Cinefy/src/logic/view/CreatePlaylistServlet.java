package logic.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.CreatePlaylistController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;

/**
 * Servlet implementation class CreatePlaylistServlet
 */

@WebServlet("/CreatePlaylistServlet")
@MultipartConfig
public class CreatePlaylistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreatePlaylistServlet.class.getName());
	
	public CreatePlaylistServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("create_playlist.jsp");
		CreatePlaylistController cpc = new CreatePlaylistController();
		if (request.getParameter("ok") != null) {
			rd = this.create(request, session, cpc);
		}
		rd.forward(request, response);
	}

	private RequestDispatcher create(HttpServletRequest request, HttpSession session, CreatePlaylistController cpc) {
		String name = (String) request.getParameter("name");
		GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
		PlaylistBean pb = new PlaylistBean();
		String newFileName = null;
		String fileName = "";
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
			if (!fileName.equals("") && filePart != null) {
				String path = FileManager.PLAYLISTS;
				File file = new File(path, fileName);
				File newFile = new File(path, newFileName);
				try (InputStream input = filePart.getInputStream()) {
					Files.copy(input, file.toPath());
				} catch (Exception e) {
					logger.log(Level.WARNING, e.toString());
				}
				if (!file.renameTo(newFile)) {
					logger.log(Level.WARNING, "Unable to rename: {0}", fileName);
				}
			}
		} catch (FieldEmptyException e) {
			request.setAttribute("errorx", e.getMessage());
			return request.getRequestDispatcher("create_playlist.jsp");
		} catch (FieldTooLongException e) {
			request.setAttribute("errorx", e.getMessage());
			return request.getRequestDispatcher("create_playlist.jsp");
		} catch (SQLException e) {
			request.setAttribute("errorx", "Name already taken");
			return request.getRequestDispatcher("create_playlist.jsp");
		}
		session.setAttribute("pb", pb);
		return request.getRequestDispatcher("AddFilmServlet");
	}
}
