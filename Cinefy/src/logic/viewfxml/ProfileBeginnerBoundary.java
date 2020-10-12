package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import logic.bean.BeginnerUserBean;
import logic.bean.GeneralUserBean;
import logic.controllers.ProfileController;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe ProfileBeginnerBoundaryche della ProfileBeginner 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class ProfileBeginnerBoundary implements Initializable {

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ImageView profilePic;
	@FXML
	private Label username;
	@FXML
	private Label bio;
	@FXML
	private Button btnLogout;

	private BeginnerGraphicChange bgc;
	private BeginnerUserBean bub;
	private ProfileController pc;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAskClicked(MouseEvent event) throws IOException {
		this.bgc.toAsk(this.ask.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onLogout(ActionEvent event) throws IOException {
		SessionUser.getInstance().closeSession();
		GraphicChangeTemplate.toLogin(this.btnLogout.getScene());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.bgc = BeginnerGraphicChange.getInstance();
		this.pc = new ProfileController();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		this.bub = pc.getUser(gub.getUsername(), gub.getRole());
		this.username.setText(bub.getUsername());
		if (bub.getProfilePic() == null) {
			String path = FileManager.PROFILE + File.separator + "default.png";
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		} else {
			String path = FileManager.PROFILE + File.separator + bub.getProfilePic();
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		}
		if (bub.getBio() == null) {
			this.bio.setText("");
		} else {
			this.bio.setText(bub.getBio());
		}
	}
}
