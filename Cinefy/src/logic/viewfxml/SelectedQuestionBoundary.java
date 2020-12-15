package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
//import logic.bean.GeneralUserBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.exceptions.FieldEmptyException;
import logic.utils.FileManager;
import javafx.scene.input.KeyEvent;
//import logic.utils.SessionUser;
import javafx.scene.input.MouseEvent;


public class SelectedQuestionBoundary implements Initializable  {
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
	private Button btnSubmit;
	@FXML
	private Label submitError;
	@FXML
	private AnchorPane technicalPane;
	@FXML
	private TextArea taAnswer;
	@FXML
	private CheckBox cbColleague;
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
	
	
	private DomandaBean selectedQuestion;
	private AdvancedGraphicChange agc;
	private AnswerQuestionsController aqc;
	private RispostaBean rb;
	private AdvancedUserBean aub;
	private String id;
	
	private String advancedName;
	private String beginnerName;
	private String selQuestion;
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
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}
	
	@FXML
	public void onSubmitBtn(ActionEvent event) throws NumberFormatException, SQLException, IOException {
		rb = new RispostaBean();
		rb.setAdvancedName(advancedName);
		rb.setBeginnerName(beginnerName);
		rb.setId(Integer.parseInt(id));
		
		try {
		
		if(change==false) {
			//caso in cui la grafica è general answer
			String generalAnswer = taAnswer.getText();
			boolean colleague = cbColleague.isSelected();
			boolean resources = cbResources.isSelected(); 
			
			rb.setChoice(general);
			
			rb.setContenuto(generalAnswer);
			
			if(colleague==true) {
				String reasonChoice = splitMenuReason.getText();
				String colleagueName = tfColleague.getText();
				
				rb.setColleagueFlag(colleague);
				rb.setColleagueName(colleagueName);
				if(buttonFocused==false) {reasonChoice=null;};
				rb.setReasonChoice(reasonChoice);
			}
			
			if(resources==true) {
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
		catch (FieldEmptyException e) {
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
		if(change==true) {
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
	

	public void init(DomandaBean db) throws AdvancedNotFoundException, SQLException, FileNotFoundException  {
		
		
		Integer queueCount;
		
		selectedQuestion = db;
		BeginnerUserBean bub;
		String begPicPath;
		
		
		this.agc = AdvancedGraphicChange.getInstance();
		advancedName = db.getAdvancedName();
		aqc = new AnswerQuestionsController();
		aub = new AdvancedUserBean();
	
		beginnerName = db.getBeginnerName();
		
		laUsername.setText(beginnerName);
		id = db.getId();
		
		AdvancedUserBean auBean = new AdvancedUserBean();
		auBean = aqc.queueCountFromABeg(advancedName, beginnerName);
		queueCount=auBean.getQueueCount();
		laNumber.setText(queueCount.toString());
		
		
		this.selQuestion = selectedQuestion.getContenuto();
		laSelquestion.setText(selQuestion);
		laType.setText("General answer");
		
		
		aub = aqc.getAdvanced(advancedName);
		String bio = aub.getBio();
		laBio.setText(bio);
		profession = aub.getProfession();
		laName.setText(profession);
		bub = new BeginnerUserBean();
		bub = aqc.getBegPic(beginnerName, "beginner");
		if (bub.getProfilePic()==null) {
			begPicPath = FileManager.PROFILE + File.separator + "default.png";
		}
		else {
			begPicPath = FileManager.PROFILE + File.separator + bub.getProfilePic();
		}
		
		
		
		InputStream input = new FileInputStream(begPicPath);
		Image image = new Image(input);
		ivBegPic.setImage(image);
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
	/*	this.agc = AdvancedGraphicChange.getInstance();
		advancedName = this.selectedQuestion.getAdvancedName();
		aqc = new AnswerQuestionsController();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		beginnerName = selectedQuestion.getBeginnerName();
		System.out.println(beginnerName);
		laUsername.setText(beginnerName);
		id = selectedQuestion.getId();*/
		
		
		
		
		
	}	
}
