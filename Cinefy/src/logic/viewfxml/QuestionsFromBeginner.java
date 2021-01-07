package logic.viewfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.utils.FileManager;

public class QuestionsFromBeginner {
	
	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ImageView ivBegPic;
	@FXML
	private Label laUsername;
	@FXML
	private Label laNumber;
	@FXML
	private Label laBio;
	@FXML
	private Label laBioTitle;
	@FXML
	private AnchorPane anchorPaneBio;
	@FXML
	private Button btnBack;
	@FXML
	private ListView<DomandaBean> questions;
	@FXML
	private Label labelErrorQuestions;
	
	
	private AdvancedGraphicChange agc;
	private AnswerQuestionsController aqc;
	
	List<DomandaBean> questionsFromABeg;
	List<DomandaBean> questionsFromABeg_del;
	private String beginnerName;
	private String advancedName;
	ObservableList<DomandaBean> listReceived;
	private BeginnerUserBean begub;
	private AdvancedUserBean aub;
	private DomandaBean selQuestion;
	private RispostaBean selAnswer;
	
	Image img;
	
	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}
	
	
	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}
	
	@FXML
	public void onBack(ActionEvent event) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		if(selAnswer == null ) {
			this.agc.toSelQuestionDetail(this.btnBack.getScene(),selQuestion ,begub);
		}
		else if (selQuestion == null) {
			
			this.agc.toAnswerDetail(this.btnBack.getScene(), selAnswer);
		}
	}
	
	@FXML
	public void onSelectedQuestion(MouseEvent event) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		
		
		if (!listReceived.isEmpty()) {
		
			DomandaBean clickedItem = this.questions.getSelectionModel().getSelectedItem();
			if(clickedItem!=null) {
				this.agc.toSelQuestionDetail(this.questions.getScene(),clickedItem,begub);
			}
	
		}
	}

	public void init(DomandaBean db,RispostaBean rb, BeginnerUserBean bub) throws AdvancedNotFoundException, SQLException, FileNotFoundException, ClassNotFoundException  {
		
		
		Integer queueCount;
		String begPicPath;
		
		
		this.agc = AdvancedGraphicChange.getInstance();
		
		aqc = new AnswerQuestionsController();
		
		begub=bub;
		selQuestion = db;
		selAnswer = rb;
		
		if(selAnswer == null ) {
			advancedName = selQuestion.getAdvancedName();
		}
		else if (selQuestion == null) {
			
			advancedName = selAnswer.getAdvancedName();
		}
		
		
		beginnerName = bub.getUsername();
		
		laUsername.setText(beginnerName);
		
		aub = new AdvancedUserBean();
		aub = aqc.getAdvanced(advancedName, beginnerName);
		queueCount=aub.getQueueCount();
		laNumber.setText(queueCount.toString());
		
		String bio = bub.getBio();
		if(bio.isEmpty()) {
			laBio.setVisible(false);
			laBioTitle.setText("");
			anchorPaneBio.setVisible(false);
		}
		else {
		laBio.setText(bio);
		}
		
		
		if (bub.getProfilePic()==null) {
			begPicPath = FileManager.PROFILE + File.separator + FileManager.generateNewFileName("default.png",beginnerName);
		}
		else {
			begPicPath = FileManager.PROFILE + File.separator + bub.getProfilePic();
		}
		
		
		
		InputStream input = new FileInputStream(begPicPath);
		Image image = new Image(input);
		ivBegPic.setImage(image);
		
		questionsFromABeg = aqc.questionsFromABeg(advancedName, beginnerName);
		
		listReceived = FXCollections.observableArrayList();
		
		this.agc = AdvancedGraphicChange.getInstance();
		
		try { listReceived.removeAll(listReceived);
	
			questionsFromABeg_del = aqc.deleteQuestion(questionsFromABeg,advancedName);
			
			if (questionsFromABeg_del == null || questionsFromABeg_del.isEmpty()) {	
			
				labelErrorQuestions.setText("No received questions without answer");
			}
		
			else {
			
				listReceived.addAll(questionsFromABeg);
		
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
	}
	catch(SQLException e) {
		e.printStackTrace();
	}

	}	
}
