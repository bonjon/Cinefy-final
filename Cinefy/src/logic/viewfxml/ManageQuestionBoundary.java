package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import logic.bean.DomandaBean;
import logic.controllers.AskForQuestionsController;

/*
 * Classe ManageQuestionBoudary di ManageQuestion
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class ManageQuestionBoundary {

	@FXML
	private Label home;
	@FXML
	private Label profile;
	@FXML
	private Label labelQuestion;
	@FXML
	private Button btnAccept;
	@FXML
	private Button btnReject;
	@FXML
	private Button btnBack;

	private AdminGraphicChange agc;
	private DomandaBean selectedQuestion;
	private AskForQuestionsController afc;

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onAccept(ActionEvent event) throws IOException, ClassNotFoundException {
		try {
			this.afc.acceptQuestion(this.selectedQuestion);
			this.agc.toHomepage(this.btnAccept.getScene());
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onReject(ActionEvent event) throws IOException, ClassNotFoundException {
		try {
			this.afc.rejectQuestion(this.selectedQuestion);
			this.agc.toHomepage(this.btnReject.getScene());
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toHomepage(this.btnBack.getScene());
	}

	public void init(DomandaBean db) {
		this.selectedQuestion = db;
		this.agc = AdminGraphicChange.getInstance();
		this.afc = new AskForQuestionsController();
		this.labelQuestion.setText(this.selectedQuestion.getContenuto());
	}
}
