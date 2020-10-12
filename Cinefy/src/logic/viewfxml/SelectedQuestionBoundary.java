package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

import javafx.scene.input.MouseEvent;


public class SelectedQuestionBoundary  {
	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	/*@FXML
	private ImageView profilePic;*/
	@FXML
	private Label laUsername;
	@FXML
	private Label laNumber;
	@FXML
	private Label laBio;
	@FXML
	private Button btnSubmit;
	@FXML
	private Label labelError;
	@FXML
	private AnchorPane technicalPane;
	@FXML
	private TextArea taAnswer;
	@FXML
	private AnchorPane suggestionPane;
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
	private String technical = "technical";
	private String filmAdvice = "film";
	
	boolean change=false; //booleano: true se la grafica è technical answer, false se la grafica è film advice

	
	
	/*public SelectedQuestionBoundary(DomandaBean db) {
		this.selectedQuestion = db;
	}*/

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
		
		
		
		if(change==false) {
			//caso in cui la grafica è technical answer
			String technicalAnswer = taAnswer.getText();
			String advices = taExplanation.getText();
			
			rb.setContenuto(technicalAnswer);
			rb.setExplanation(advices);
			rb.setChoice(technical);
			
			
			aqc.createAnswer(rb );
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
	
	
	@FXML
	public void onSwitchPressed(ActionEvent event) {
		if(change==true) {
			laType.setText("Technical answer");
			taExplanation.setPromptText("Suggestions for practicing");
			technicalPane.setVisible(true);
			suggestionPane.setVisible(false);
			change=false;
		}
		else {
			laType.setText("Film advice");
			taExplanation.setPromptText("Explanation");
			technicalPane.setVisible(false);
			suggestionPane.setVisible(true);
			change=true;
		}
	}
	

	public void init(DomandaBean db) throws AdvancedNotFoundException, SQLException  {
		
			selectedQuestion = db;
		
		this.agc = AdvancedGraphicChange.getInstance();
		advancedName = db.getAdvancedName();
		aqc = new AnswerQuestionsController();
		//GeneralUserBean gub = SessionUser.getInstance().getSession();
		beginnerName = db.getBeginnerName();
		System.out.println(beginnerName);
		laUsername.setText(beginnerName);
		id = db.getId();
		
		this.selQuestion = selectedQuestion.getContenuto();
		laSelquestion.setText(selQuestion);
		laType.setText("Technical answer");
		taExplanation.setPromptText("Suggestions for practicing");
		aub = new AdvancedUserBean();
		aub = aqc.getAdvanced(advancedName);
		String bio = aub.getBio();
		laBio.setText(bio);
		profession = aub.getProfession();
		laName.setText(profession);
	
		
		
		
		
		
	}


/*	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		this.agc = AdvancedGraphicChange.getInstance();
		advancedName = this.selectedQuestion.getAdvancedName();
		aqc = new AnswerQuestionsController();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		beginnerName = selectedQuestion.getBeginnerName();
		System.out.println(beginnerName);
		laUsername.setText(beginnerName);
		id = selectedQuestion.getId();
		
		this.selQuestion = selectedQuestion.getContenuto();
		laSelquestion.setText(selQuestion);
		laType.setText("Technical answer");
		taExplanation.setPromptText("Suggestions for practicing");
		aub = new AdvancedUserBean();
		try {
			aub = aqc.getAdvanced(advancedName);
		} catch (AdvancedNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bio = aub.getBio();
		laBio.setText(bio);
		profession = aub.getProfession();
		laName.setText(profession);
	
		
		
	}	*/
}
