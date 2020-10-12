package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.controllers.AskForQuestionsController;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe QuestionDetailsBoundary della QuestionDetails 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class QuestionDetailsBoundary {

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private Label labelName;
	@FXML
	private ImageView imageView;
	@FXML
	private Label labelCheck;
	@FXML
	private Slider sliderVote;
	@FXML
	private Button btnOk;
	@FXML
	private Label labelQuestion;
	@FXML
	private Label labelAnswer;

	private BeginnerGraphicChange bgc;
	private DomandaBean selectedQuestion;
	private AskForQuestionsController afc;

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
	public void onOk(ActionEvent event) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			this.afc.voteAdvanced(this.selectedQuestion.getAdvancedName(),gub.getUsername(),(int)this.sliderVote.getValue());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void init(DomandaBean db, String color) {
		this.selectedQuestion = db;
		this.bgc = BeginnerGraphicChange.getInstance();
		this.afc = new AskForQuestionsController();
		this.labelQuestion.setText(selectedQuestion.getContenuto());
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			if (color == "y") {
				String path = FileManager.YELLOW;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("The question has been sent to our admins who will accept or reject");
				this.labelAnswer.setText("");
				this.labelName.setText("No answer from " + this.selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(true);
				this.btnOk.setDisable(true);
			}
			if (color == "g") {
				String path = FileManager.GREEN;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("The question has been accepted by the admin and it's arrived to "
						+ this.selectedQuestion.getAdvancedName());
				this.labelAnswer.setText("");
				this.labelName.setText("No answer from " + this.selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(true);
				this.btnOk.setDisable(true);
			}
			if (color == "m") {
				String path = FileManager.MARK;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("You have received an answer from " + this.selectedQuestion.getAdvancedName());
				this.labelAnswer
						.setText(afc.getAnswer(gub.getUsername(), this.selectedQuestion.getId()).getContenuto());
				this.labelName.setText("Answer from " + this.selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(false);
				this.btnOk.setDisable(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
