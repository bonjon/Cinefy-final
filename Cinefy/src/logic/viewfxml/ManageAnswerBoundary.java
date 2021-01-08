package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;

public class ManageAnswerBoundary {
	
	private static final Logger LOGGER = Logger.getLogger(ManageAnswerBoundary.class.getName());
	
	@FXML
	private Label home;
	@FXML
	private Label profile;
	@FXML
	private Label labelAnswer;
	@FXML
	private Button btnAccept;
	@FXML
	private Button btnReject;
	@FXML
	private Button btnBack;

	private AdminGraphicChange agc;
	private RispostaBean selectedAnswer;
	private AnswerQuestionsController aqc;

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());

	}

	@FXML
	public void onAccept(ActionEvent event) throws IOException, ClassNotFoundException {
		try {
			this.aqc.acceptAnswer(this.selectedAnswer);
			this.agc.toHomepage(this.btnAccept.getScene());
		} catch (NumberFormatException | SQLException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
	}

	@FXML
	public void onReject(ActionEvent event) throws IOException, ClassNotFoundException {
		try {
			this.aqc.rejectAnswer(this.selectedAnswer);
			this.agc.toHomepage(this.btnReject.getScene());
		} catch (NumberFormatException | SQLException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toHomepage(this.btnBack.getScene());
	}

	public void init(RispostaBean rb) {
		this.selectedAnswer = rb;
		this.agc = AdminGraphicChange.getInstance();
		this.aqc = new AnswerQuestionsController();
		this.labelAnswer.setText(this.selectedAnswer.getContenuto());
	}
}
