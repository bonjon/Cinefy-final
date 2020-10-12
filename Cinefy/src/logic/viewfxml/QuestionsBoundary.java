package logic.viewfxml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.AdvancedUserBean;
import logic.bean.GeneralUserBean;
import logic.controllers.AskForQuestionsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe QuestionsBoundary che si occuperà di gestire la
 * parte grafica della domanda che andrà a fare l'utente
 * all'adavanced.
 */

public class QuestionsBoundary {

	private AdvancedUserBean selectedAdvanced;
	private BeginnerGraphicChange bgc;
	private AskForQuestionsController afc;

	@FXML
	private Label home, ask, playlists, profile, username, voto, role, bio, labelError;
	@FXML
	private ImageView profilePic;
	@FXML
	private TextArea questionArea;
	@FXML
	private Button btnSubmit;

	public void init(AdvancedUserBean aub) {
		selectedAdvanced = aub;
		this.bgc = BeginnerGraphicChange.getInstance();
		this.afc = new AskForQuestionsController();
		username.setText(selectedAdvanced.getUsername());
		voto.setText(selectedAdvanced.getVoto());
		role.setText(selectedAdvanced.getProfession());
		String path = FileManager.PROFILE + File.separator + aub.getProfilePic();
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.profilePic.setImage(img);
		if (selectedAdvanced.getBio() == null) {
			bio.setText("");
		} else {
			bio.setText(selectedAdvanced.getBio());
		}
	}

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onSubmitBtn(ActionEvent event) throws IOException {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			this.afc.makeQuestion(this.questionArea.getText(), gub.getUsername(), this.selectedAdvanced.getUsername());
			this.bgc.toAsk(this.btnSubmit.getScene());
		} catch (FieldTooLongException e) {
			this.labelError.setText(e.getMessage());
		} catch (FieldEmptyException e) {
			this.labelError.setText(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
