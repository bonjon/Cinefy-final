package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;


public class ManageAnswerBoundary {
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

	private AdminGraphicChange agc;
	private RispostaBean selectedAnswer;
	private AnswerQuestionsController aqc;

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());

	}
	

     public void onAccept(ActionEvent event) throws IOException {
		try {
			this.aqc.acceptAnswer(this.selectedAnswer);
			this.agc.toHomepage(this.btnAccept.getScene());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	public void onReject(ActionEvent event) {
		
	}
	
	public void init(RispostaBean rb) {
		this.selectedAnswer = rb;
		this.agc = AdminGraphicChange.getInstance();
		this.aqc = new AnswerQuestionsController();
		this.labelAnswer.setText(this.selectedAnswer.getContenuto());
	}
}
