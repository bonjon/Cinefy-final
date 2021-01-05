package logic.viewfxml;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;


import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;

import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;


public class SelectedQuestionBoundary  {
	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private CheckBox cbColleague;
	@FXML
	private Label profile;
	@FXML
	private ImageView ivBegPic;
	@FXML
	private Label laBio;
	@FXML
	private Label laNumber;
	@FXML
	private Label laUsername;
	@FXML
	private Label laBioTitle;
	@FXML
	private Button btnSubmit;
	@FXML
	private Label submitError;
	@FXML
	private AnchorPane technicalPane;
	@FXML
	private TextArea taAnswer;
	
	@FXML
	private Label playlists;
	@FXML
	private CheckBox cbResources;
	@FXML
	private TextField tfColleague;
	@FXML
	private SplitMenuButton splitMenuReason;
	@FXML
	private TextField tfWiki;
	@FXML
	private TextField tfYoutube;
	@FXML
	private AnchorPane advicePane;
	
	@FXML
	private TextField tfFilm1;
	@FXML
	private TextField tfName1;
	@FXML
	private TextField tfGenre1;
	@FXML
	private Label laSelquestion;
	@FXML
	private Label laType;
	@FXML
	private TextArea taExplanation;
	@FXML
	private Button btnSwitch;
	@FXML
	private Label laName;
	@FXML
	private AnchorPane anchorPaneBio;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnQuestionsFromABeg;
	
	
	private DomandaBean selectedQuestion;
	private AdvancedGraphicChange agc;
	private AnswerQuestionsController aqc;
	
	private BeginnerUserBean begub;
	private String id;
	
	private String advancedName;
	private String beginnerName;
	
	private String profession;
	private String general = "general";
	private String filmAdvice = "film";
	
	boolean change=false; //booleano: true se la grafica è general answer, false se la grafica è film advice
	boolean buttonFocused=false; //booleano: true se uno dei bottoni dello splitMenuReason è stato pigiato, false altrimenti
	
	Image img;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}
	
	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toAnswer(this.btnBack.getScene());
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
	public void onSubmitBtn(ActionEvent event) throws NumberFormatException, SQLException, IOException, ClassNotFoundException {
		
		RispostaBean rb;
		
		rb = new RispostaBean();
		rb.setAdvancedName(advancedName);
		rb.setBeginnerName(beginnerName);
		rb.setId(Integer.parseInt(id));
		
		try {
		
		if(!change) {
			//caso in cui la grafica è general answer
			String generalAnswer = taAnswer.getText();
			boolean colleague = cbColleague.isSelected();
			boolean resources = cbResources.isSelected(); 
			
			rb.setChoice(general);
			
			rb.setContenuto(generalAnswer);
			
			if(colleague) {
				String reasonChoice = splitMenuReason.getText();
				String colleagueName = tfColleague.getText();
				
				rb.setColleagueFlag(colleague);
				rb.setColleagueName(colleagueName);
				if(!buttonFocused) {reasonChoice=null;}
				rb.setReasonChoice(reasonChoice);
			}
			
			if(resources) {
				String wiki= tfWiki.getText();
				String youtube = tfYoutube.getText();
				
				rb.setResourceFlag(resources);
				rb.setWikiLink(wiki);
				rb.setYoutubeLink(youtube);
			}
			
			
				aqc.createAnswer(rb);
			
		}
		else {
			//caso in cui la grafica è film advices
			String film = tfFilm1.getText();
			String partecipant = tfName1.getText();
			String genre = tfGenre1.getText();
			String advices = taExplanation.getText();
			
			rb.setFilm(film);
			rb.setPartecipant(partecipant);
			rb.setGenre(genre);
			rb.setExplanation(advices);
			rb.setProfession(profession);
			rb.setChoice(filmAdvice);
			
			aqc.createAnswer(rb);
		}
		
		agc.toAnswer(btnSubmit.getScene());
		
		}
		catch (FieldEmptyException | FieldTooLongException e) {
			this.submitError.setText(e.getMessage());
		}
	}
	
	@FXML
	public void keyPressed(KeyEvent event) {
		this.submitError.setText("");
	}
	
	@FXML
	public void onReasonMenu(ActionEvent event ) {
		MenuItem pressedButton = (MenuItem)event.getSource();
		buttonFocused=true;
		String reason = pressedButton.getText();
		splitMenuReason.setText(reason);
		this.submitError.setText("");
		
	}
	
	
	@FXML
	public void onSwitchPressed(ActionEvent event) {
		this.submitError.setText("");
		if(change) {
			laType.setText("General answer");
			technicalPane.setVisible(true);
			advicePane.setVisible(false);
			change=false;
		}
		else {
			laType.setText("Film advice");
			technicalPane.setVisible(false);
			advicePane.setVisible(true);
			change=true;
		}
	}
	
	
	@FXML
	public void onQuestionsFromABegPressed(ActionEvent event) throws IOException, AdvancedNotFoundException, SQLException {
		this.agc.toQuestionsFromABeg(this.btnQuestionsFromABeg.getScene(),selectedQuestion, null,begub);
	}
	

	public void init(DomandaBean db, BeginnerUserBean bub) throws AdvancedNotFoundException, SQLException, FileNotFoundException, ClassNotFoundException  {
		
		AdvancedUserBean aub;
		
		Integer queueCount;
		
		selectedQuestion = db;
		
		String begPicPath;
		String selQuestion;
		
		
		this.agc = AdvancedGraphicChange.getInstance();
		advancedName = db.getAdvancedName();
		aqc = new AnswerQuestionsController();
		
		begub=bub;
		
	
		beginnerName = bub.getUsername();
		
		laUsername.setText(beginnerName);
		id = db.getId();
		

		aub = aqc.getAdvanced(advancedName, beginnerName);
		queueCount=aub.getQueueCount();
		laNumber.setText(queueCount.toString());
		aub.setQueueCount(queueCount);
		
		
		selQuestion = selectedQuestion.getContenuto();
		laSelquestion.setText(selQuestion);
		laType.setText("General answer");
		
		
		aub = aqc.getAdvanced(advancedName);
		String bio = bub.getBio();
		if(bio.isEmpty()) {
			laBio.setVisible(false);
			laBioTitle.setText("");
			anchorPaneBio.setVisible(false);
		}
		else {
		laBio.setText(bio);
		}
		profession = aub.getProfession();
		laName.setText(profession+" to focus on");
		
		if (bub.getProfilePic()==null) {
			begPicPath = FileManager.PROFILE + File.separator + FileManager.generateNewFileName("default.png",beginnerName);
		}
		else {
			begPicPath = FileManager.PROFILE + File.separator + bub.getProfilePic();
		}
		
		InputStream input = new FileInputStream(begPicPath);
		Image image = new Image(input);
		ivBegPic.setImage(image);
		
	}


}
