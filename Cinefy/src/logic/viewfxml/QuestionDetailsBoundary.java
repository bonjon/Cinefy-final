package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AskForQuestionsController;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe QuestionDetailsBoundary della QuestionDetails 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class QuestionDetailsBoundary {

	private static final Logger LOGGER = Logger.getLogger(QuestionDetailsBoundary.class.getName());

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
	private Label labelError;
	@FXML
	private Slider sliderVote;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnBack;
	@FXML
	private Label labelQuestion;
	@FXML
	private Label labelAnswer;

	private BeginnerGraphicChange bgc;
	private RispostaBean answer;
	private AskForQuestionsController afc;

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onOk(ActionEvent event) throws NumberFormatException, ClassNotFoundException {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		int a = (int) this.sliderVote.getValue();
		this.labelError.setText("You voted " + a);
		try {
			this.afc.voteAdvanced(gub.getUsername(), this.answer, a);
		} catch (SQLException e) {
			this.labelError.setText(e.getMessage());
		}
	}

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.bgc.toAsk(this.btnBack.getScene());
	}

	public void init(DomandaBean db, String color) throws NumberFormatException, ClassNotFoundException {
		DomandaBean selectedQuestion = db;
		this.bgc = BeginnerGraphicChange.getInstance();
		this.afc = new AskForQuestionsController();
		this.labelQuestion.setText(selectedQuestion.getContenuto());
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			if (color.equals("y")) {
				String path = FileManager.YELLOW;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("The question was sent to our admins who will accept or reject it");
				this.labelAnswer.setText("");
				this.labelName.setText("No answer from " + selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(true);
				this.btnOk.setDisable(true);
			}
			if (color.equals("g")) {
				String path = FileManager.GREEN;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("The question was accepted by the admin and it's arrived to "
						+ selectedQuestion.getAdvancedName());
				this.labelAnswer.setText("");
				this.labelName.setText("No answer from " + selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(true);
				this.btnOk.setDisable(true);
			}
			if (color.equals("m")) {
				String path = FileManager.MARK;
				File file = new File(path);
				Image img = new Image(file.toURI().toString());
				this.imageView.setImage(img);
				this.labelCheck.setText("You received an answer from " + selectedQuestion.getAdvancedName());
				this.answer = afc.getAnswer(gub.getUsername(), selectedQuestion.getId());
				this.labelAnswer.setText(this.answer.getContenuto());
				this.labelName.setText("Answer from " + selectedQuestion.getAdvancedName());
				this.sliderVote.setDisable(false);
				this.btnOk.setDisable(false);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
	}
}
