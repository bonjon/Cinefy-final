package logic.viewfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.controllers.AskForQuestionsController;
import logic.utils.SessionUser;

/*
 * Classe boundary di HomeAdmin che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente.
 */

public class HomeAdminBoundary implements Initializable {

	ObservableList<DomandaBean> list;
	ObservableList<RispostaBean> list2;
	public static final String BACK = "-fx-control-inner-background: ";
	public static final String COLOR = " #1c1c1c";

	@FXML
	private Label home;
	@FXML
	private Label profile;
	@FXML
	private Label errorLabel;
	@FXML
	private Label errorLabel2;
	@FXML
	private ListView<DomandaBean> listQuestions;
	@FXML
	private ListView<RispostaBean> listAnswers;

	private AdminGraphicChange agc;

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onSelectedQuestion(MouseEvent event) throws IOException {
		if (!this.list.isEmpty()) {
			DomandaBean clicked = this.listQuestions.getSelectionModel().getSelectedItem();
			if (clicked != null) {
				this.agc.toManageQuestion(this.listQuestions.getScene(), clicked);
			}
		}
	}

	@FXML
	public void onSelectedAnswer(MouseEvent event) throws IOException {
		if (!this.list2.isEmpty()) {
			RispostaBean clicked = this.listAnswers.getSelectionModel().getSelectedItem();
			if (clicked != null) {
				this.agc.toManageAnswer(this.listAnswers.getScene(), clicked);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		list2 = FXCollections.observableArrayList();
		AskForQuestionsController afc = new AskForQuestionsController();
		this.agc = AdminGraphicChange.getInstance();
		GeneralUserBean gu = SessionUser.getInstance().getSession();
		// Initialize the list of questions in pending
		list.removeAll(list);
		try {
			List<DomandaBean> dbl = afc.getQuestions(gu, gu.getRole());
			if (dbl != null)
				list.addAll(dbl);
			this.listQuestions.getItems().addAll(list);
			this.listQuestions.setCellFactory(param -> new ListCell<DomandaBean>() {
				@Override
				protected void updateItem(DomandaBean item, boolean empty) {
					super.updateItem(item, empty);
					setStyle(BACK + COLOR + ";");
					if (empty || item == null) {
						setText(null);
					} else {
						setText(item.getContenuto());
					}
				}
			});

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Da implementare lista risposte, creare AnswerQuestionsController.
		AnswerQuestionsController aqc = new AnswerQuestionsController();
		this.agc = AdminGraphicChange.getInstance();

		// Initialize the list of questions in pending
		list2.removeAll(list2);
		try {
			List<RispostaBean> rb = aqc.getAnswers(gu.getUsername(), gu.getRole());
			if (rb != null)
				list2.addAll(rb);
			this.listAnswers.getItems().addAll(list2);
			this.listAnswers.setCellFactory(param -> new ListCell<RispostaBean>() {
				protected void updateItem(RispostaBean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setStyle(BACK + COLOR + ";");
					} else {
						setText(item.getContenuto());
						setStyle(BACK + COLOR + ";");
					}
				}
			});

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
