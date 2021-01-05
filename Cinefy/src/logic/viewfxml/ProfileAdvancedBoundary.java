package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.bean.AdvancedUserBean;
import logic.bean.GeneralUserBean;
import logic.controllers.ProfileController;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe ProfileAdvancedBoundary di ProfileAdvanced 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class ProfileAdvancedBoundary implements Initializable {

	
	@FXML
	private Label answer;
	@FXML
	private Label home;
	@FXML
	private Label profile;
	@FXML
	private ImageView profilePic;
	@FXML
	private Label playlists;
	@FXML
	private Label username;
	@FXML
	private Label laBioTitle;
	@FXML
	private Label bio;
	@FXML
	private Button btnLogout;
	@FXML
	private Label voto;
	@FXML
	private Label tokens;
	@FXML
	private Label role;
	@FXML
	private AnchorPane anchorPaneBio;

	private AdvancedGraphicChange agc;
	
	private static final Logger logger = Logger.getLogger(ProfileAdvancedBoundary.class.getName());

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAnswerClicked(MouseEvent event) throws IOException {
		this.agc.toAnswer(this.answer.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onLogout(ActionEvent event) throws IOException {
		SessionUser.getInstance().closeSession();
		GraphicChangeTemplate.toLogin(this.btnLogout.getScene());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
		AdvancedUserBean aub;
		ProfileController pc;
		this.agc = AdvancedGraphicChange.getInstance();
		pc = new ProfileController();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		
		aub = pc.getUser2(gub.getUsername(), gub.getRole());
		
		this.username.setText(aub.getUsername());
		if (aub.getProfilePic() == null) {
			String path = FileManager.PROFILE + File.separator + "default.png";
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		} else {
			String path = FileManager.PROFILE + File.separator + aub.getProfilePic();
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		}
		this.voto.setText(aub.getVoto());
		this.role.setText(aub.getProfession());
		this.tokens.setText(aub.getTokens());
		if (aub.getBio().isEmpty()) {
			this.bio.setText("");
			laBioTitle.setText("");
			anchorPaneBio.setVisible(false);
		} else {
			this.bio.setText(aub.getBio());
		}
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARNING, "class not found exception detected");
			e.printStackTrace();
		}
	}
}
