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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.utils.SessionUser;

/*
 * Classe AnswerAdvancedBoundary della AnswerAdvanced che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente.
 */

public class AnswerAdvancedBoundary implements Initializable {

	ObservableList<DomandaBean> listReceived;
	ObservableList<RispostaBean> list2;
	List<DomandaBean> lb;

	@FXML
	private Label home, answer, playlists, profile;

	
	@FXML
	private ListView<DomandaBean> questions;

	private AnswerQuestionsController aqc; 
	private AdvancedGraphicChange agc;
	

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.profile.getScene());
	}
	
	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}


	@FXML
	public void onSelectedQuestion(MouseEvent event) throws IOException, AdvancedNotFoundException, SQLException {
		
		
		if (!listReceived.isEmpty()) {
		
			DomandaBean clickedItem = this.questions.getSelectionModel().getSelectedItem();
			BeginnerUserBean bub = new BeginnerUserBean();
			String beginnerName = clickedItem.getBeginnerName();
			bub = aqc.getUser(beginnerName, "beginner");
			
			this.agc.toSelQuestionDetail(this.questions.getScene(),clickedItem,bub);
		}
	}

	@FXML
	public void onSelectedAnswer(MouseEvent event) {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		list2 = FXCollections.observableArrayList();
		listReceived = FXCollections.observableArrayList();
		this.aqc = new AnswerQuestionsController();
		this.agc = AdvancedGraphicChange.getInstance();
		
		try { listReceived.removeAll(listReceived);
	
		lb = aqc.getQuestions(gub.getUsername(), "advanced");
		
		if (lb != null) {
			listReceived.addAll(lb);}
		
		
		
		questions.getItems().addAll(listReceived);
		
		questions.setCellFactory(param -> new ListCell<DomandaBean>() {
			@Override
			protected void updateItem(DomandaBean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				}
				else {
				HBox hBox = new HBox(2);
			
				Label label = new Label(item.getContenuto());
				label.setFont(Font.font("Arial", 15));
			
				hBox.getChildren().addAll(label);
				hBox.setAlignment(Pos.CENTER_LEFT);
				setGraphic(hBox);
				setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				}
			}
		});
		
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	}
}
