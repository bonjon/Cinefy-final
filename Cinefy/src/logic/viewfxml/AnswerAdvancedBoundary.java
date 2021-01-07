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
import logic.exceptions.AnswersNotFoundException;
import logic.utils.SessionUser;

/*
 * Classe AnswerAdvancedBoundary della AnswerAdvanced che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente.
 */

public class AnswerAdvancedBoundary implements Initializable {

	ObservableList<DomandaBean> listReceived;
	ObservableList<RispostaBean> listAnswers;
	
	List<DomandaBean> lb;
	List<DomandaBean> lb_del;
	List<RispostaBean> rbTotal;

	@FXML
	private Label home;
	@FXML
	private Label answer; 
	@FXML
	private Label playlists;
	@FXML
	private Label profile;

	
	@FXML
	private ListView<DomandaBean> questions;
	@FXML
	private ListView<RispostaBean> answers;
	@FXML
	private Label labelErrorAnswers;
	@FXML
	private Label labelErrorQuestions;
	@FXML
	private Label laBeginner;
	@FXML
	private Label laQuestion;
	

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
	public void onSelectedQuestion(MouseEvent event) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		
		
		if (!listReceived.isEmpty()) {
		
			DomandaBean clickedItem = this.questions.getSelectionModel().getSelectedItem();
			if(clickedItem!=null) {
				BeginnerUserBean bub = new BeginnerUserBean();
				String beginnerName = clickedItem.getBeginnerName();
				bub = aqc.getBeginnerUser(beginnerName, "beginner");
				this.agc.toSelQuestionDetail(this.questions.getScene(),clickedItem,bub);
			}
		}
	}

	
	
	@FXML
	public void onSelectedAnswer(MouseEvent event) throws IOException, SQLException, AdvancedNotFoundException {
		
	
		if (!listAnswers.isEmpty()) {
			RispostaBean clickedItem = this.answers.getSelectionModel().getSelectedItem();
			if(clickedItem!=null) {
				this.agc.toAnswerDetail(this.answers.getScene(), clickedItem);
			}
		}
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		
		
		
		listReceived = FXCollections.observableArrayList();
		this.aqc = new AnswerQuestionsController();
		this.agc = AdvancedGraphicChange.getInstance();
		
		try { listReceived.removeAll(listReceived);
	
		lb = aqc.getQuestions(gub.getUsername(), "advanced");
		lb_del = aqc.deleteQuestion(lb, gub.getUsername());
		
		if(lb_del==null||lb_del.isEmpty()) {
			laBeginner.setVisible(false);
			laQuestion.setVisible(false);
			labelErrorQuestions.setText("No received questions without answer");
		}
		else {
			
			
			listReceived.addAll(lb_del);
		
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
			
				HBox headerBox = new HBox(1);
				String begName = item.getBeginnerName();
				Label header = new Label(begName);
				header.setFont(Font.font("Arial", 15));
				
				
				
				headerBox.setMaxWidth(255.0);
				headerBox.setPrefWidth(255.0);
				headerBox.getChildren().addAll(header);
				
				
				Label label = new Label(item.getContenuto());
				label.setFont(Font.font("Arial", 15));
				label.setMaxHeight(15.0);
				label.setMaxWidth(810.0);
				
				
			
				hBox.getChildren().addAll(headerBox,label);
				hBox.setAlignment(Pos.CENTER_LEFT);
				setGraphic(hBox);
				setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				
				}
				
			}
		});
		
		
		}
	}
	
	catch(SQLException | ClassNotFoundException  e ) {
		e.printStackTrace();
	}
	
		listAnswers = FXCollections.observableArrayList();
		
		
	    try { listAnswers.removeAll(listAnswers);
	    
	    rbTotal=aqc.getAllAnswers(gub.getUsername());
		
		
		if (rbTotal != null) {
			listAnswers.addAll(rbTotal);}
		
		
		answers.getItems().addAll(listAnswers);
		
		
		
		answers.setCellFactory(param -> new ListCell<RispostaBean>() {
			@Override
			protected void updateItem(RispostaBean item, boolean empty) {
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
	
	catch(AnswersNotFoundException e) {
		labelErrorAnswers.setText(e.getMessage());
	}
	catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			labelErrorAnswers.setText("No answers found in our database");
	}
	    
	}
}
