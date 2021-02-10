package logic.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.controllers.RegistrationController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;

/*
 * Servlet implementation class RegistrationServlet che sarà la view 
 * di Registration.
 */

@WebServlet("/RegistrationServlet")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String REGIS = "registration.jsp";
	private static final String REGB = "registration";
	private static final Logger logger = Logger.getLogger(RegistrationServlet.class.getName());
	public static final String FIELD = "field";

	public RegistrationServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(REGIS);
		RegistrationController controller = new RegistrationController();
		if (request.getParameter(REGB) != null) {
			rd = this.register(request, controller);
		}
		else if (request.getParameter("back") != null) {
			rd = request.getRequestDispatcher("index.jsp");
		}
		rd.forward(request, response);
	}

	private RequestDispatcher register(HttpServletRequest request, RegistrationController controller) {
		Boolean regResult = true;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userType = request.getParameter("userType");
		String bio = request.getParameter("bio");
		String newFileName = null;
		String fileName = "";
		Part filePart = null;
		try {
			filePart = request.getPart("avatar");
		} catch (IOException | ServletException e) {
			logger.log(Level.WARNING, e.toString());
		}
		if (filePart != null) {
			fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		}
		if (!fileName.equals(""))
			newFileName = FileManager.generateNewFileName(fileName, username);
		regResult = this.create(userType, username, password, bio, newFileName, controller, request);
		if (Boolean.TRUE.equals(regResult)) {
			request.setAttribute("reg", "Registered");
			if (!fileName.equals("") && filePart != null) {
				String path = FileManager.PROFILE;
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
			return request.getRequestDispatcher("index.jsp");
		} else {
			request.setAttribute("reg", "Not Registered");
			return request.getRequestDispatcher(REGIS);
		}
	}

	private Boolean create(String userType, String username, String password, String bio, String newFileName,
			RegistrationController controller, HttpServletRequest request) {
		if (userType.equals("Beginner")) {
			BeginnerUserBean bub = new BeginnerUserBean();
			bub.setUsername(username);
			bub.setPassword(password);
			bub.setBio(bio);
			bub.setProfilePic(newFileName);
			try {
				return controller.createBeginnerUser(bub);
			} catch (FieldEmptyException | FieldTooLongException e) {
				request.setAttribute(FIELD, e.getMessage());
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, e.toString());
			}
		} else if (userType.equals("Advanced")) {
			String profession = request.getParameter("userProf");
			AdvancedUserBean aub = new AdvancedUserBean();
			aub.setUsername(username);
			aub.setBio(bio);
			aub.setPassword(password);
			aub.setProfession(profession);
			aub.setProfilePic(newFileName);
			try {
				return controller.createAdvancedUser(aub);
			} catch (FieldEmptyException | FieldTooLongException e) {
				request.setAttribute(FIELD, e.getMessage());
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, e.toString());
			}
		}
		return false;
	}
}
