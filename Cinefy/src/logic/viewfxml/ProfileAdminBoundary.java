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
import logic.bean.GeneralUserBean;
import logic.utils.FileManager;
import logic.utils.SessionUser;

public class ProfileAdminBoundary implements Initializable {

	@FXML
	private Label home;
	@FXML
	private Label profile;
	@FXML
	private Label username;
	@FXML
	private Button btnLogout;
	@FXML
	private ImageView profilePic;

	private AdminGraphicChange agc;
	private GeneralUserBean gub;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}
	
	@FXML
	public void onLogout(ActionEvent event) throws IOException {
		SessionUser.getInstance().closeSession();
		GraphicChangeTemplate.toLogin(this.btnLogout.getScene());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.agc = AdminGraphicChange.getInstance();
		this.gub = SessionUser.getInstance().getSession();
		this.username.setText(this.gub.getUsername());
		String path = FileManager.PROFILE + File.separator + "default.png";
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.profilePic.setImage(img);
	}
}
