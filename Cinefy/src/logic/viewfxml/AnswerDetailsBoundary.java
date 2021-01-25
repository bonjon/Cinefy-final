package logic.viewfxml;

import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.exceptions.AdvancedNotFoundException;

import logic.utils.FileManager;

public class AnswerDetailsBoundary {
	
	@FXML
	private Label home;
	@FXML
	private Label answer; 
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ImageView imageView;
	@FXML
	private Label labelCheck;
	@FXML
	private Label laUsername;
	@FXML
	private Label laQueueCount;
	@FXML
	private Button btnQuestionsFromABeg;
	@FXML
	private Button btnBack;
	@FXML
	private Label labelQuestion;
	@FXML
	private Label labelAnswer;
	@FXML 
	private Label laVoteNumber; 
	@FXML 
	private Label laTokensNumber; 
	
	private AdvancedGraphicChange agc;
	private RispostaBean selAnswer;
	private BeginnerUserBean bub;
	
	
	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toAnswer(this.btnBack.getScene());
	}
	
	@FXML
	public void onQuestionsFromABegPressed(ActionEvent event) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		this.agc.toQuestionsFromABeg(this.btnQuestionsFromABeg.getScene(),null, selAnswer,bub);
	}
	

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}
	
	public void init(RispostaBean selectedItem) throws SQLException, AdvancedNotFoundException, ClassNotFoundException {
		
		AnswerQuestionsController aqc;
		DomandaBean question;
		AdvancedUserBean aub;

		String advancedName;
		int queueCount;
		int idDomanda;
	    String idRisposta;
		String selQuestion;
		int vote;
		int tokens;
		boolean check; //booleano: true se la domanda fa parte della tabella di risposte, false se è parte di risposte in coda
		
		this.agc = AdvancedGraphicChange.getInstance();
		
		selAnswer=selectedItem;
		aqc = new AnswerQuestionsController();
		bub=new BeginnerUserBean();
		advancedName = selAnswer.getAdvancedName();
		
		
		String beginnerName=selAnswer.getBeginnerName();
		bub = aqc.getBeginnerUser(beginnerName, "beginner");	
		idRisposta = selAnswer.getId();
		
		aub = aqc.getAdvanced(advancedName,beginnerName);
		
		check = aqc.checkAnswer(selAnswer);
		if(check) {
			String path = FileManager.MARK;
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.imageView.setImage(img);
			this.labelCheck.setText("Our admins accepted your answer");
		}
		else {
			String path = FileManager.YELLOW;
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.imageView.setImage(img);
			this.labelCheck.setText("The answer was sent to our admins who will accept or reject it");
			this.labelAnswer.setText("");
			this.labelAnswer.setText("No answer from " + advancedName);
		}
		
		laUsername.setText(beginnerName);
		
		queueCount=aub.getQueueCount();
		laQueueCount.setText(String.valueOf(queueCount));
		
		idDomanda = Integer.parseInt(selAnswer.getIdDomanda());
		question = aqc.getQuestion(idDomanda);
		selQuestion = question.getContenuto();
		labelQuestion.setText(selQuestion);
		
		labelAnswer.setText(selAnswer.getContenuto());
		
		RispostaBean rb = aqc.getVoto(beginnerName, idRisposta);
		vote = rb.getVoto();
		if(vote==0) {
			if(check) {
				laVoteNumber.setText("Beginner user hasn't voted your answer yet");
			}
			else {
				laVoteNumber.setText("Beginner user has not received your answer yet");
			}
			tokens=0;
			laTokensNumber.setText(String.valueOf(tokens));
		}
		else {
			double voto ;
			voto = vote;
			laVoteNumber.setText(String.valueOf(voto));
			tokens=vote;
			laTokensNumber.setText(String.valueOf(tokens));
			
		}
		
		
		
	}
	
}
