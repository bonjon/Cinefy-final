package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;

import logic.bean.GeneralUserBean;
import logic.controllers.ProfileController;

import logic.utils.FileManager;
import logic.utils.ListConverter;
import logic.utils.SessionUser;

/*
 * Classe ProfileBeginnerBoundaryche della ProfileBeginner 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class ProfileBeginnerBoundary implements Initializable {

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ImageView profilePic;
	@FXML
	private Label username;
	@FXML
	private Label laBioTitle;
	@FXML
	private Label bio;
	@FXML
	private Button btnLogout;
	@FXML
	private Label laQuestionsNum;
	@FXML
	private Label laAdvNumTitle;
	@FXML
	private Label laAdvNum;
	@FXML
	private Label laAdvListTitle;
	@FXML
	private Label laAdvList;
	@FXML
	private AnchorPane anchorPaneBio;
	@FXML
	private AnchorPane anchorPaneAdv;

	private BeginnerGraphicChange bgc;
	private BeginnerUserBean bub;
	private ProfileController pc;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAskClicked(MouseEvent event) throws IOException {
		this.bgc.toAsk(this.ask.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onLogout(ActionEvent event) throws IOException {
		SessionUser.getInstance().closeSession();
		GraphicChangeTemplate.toLogin(this.btnLogout.getScene());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Integer questionsNum=0;
		int advCounter=0;
		
		this.bgc = BeginnerGraphicChange.getInstance();
		this.pc = new ProfileController();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		this.bub = pc.getUser(gub.getUsername(), gub.getRole());
		this.username.setText(bub.getUsername());
		if (bub.getProfilePic() == null) {
			String path = FileManager.PROFILE + File.separator + FileManager.generateNewFileName("default.png",gub.getUsername());
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		} else {
			String path = FileManager.PROFILE + File.separator + bub.getProfilePic();
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.profilePic.setImage(img);
		}
		String begBio=bub.getBio();
		if (begBio.isEmpty()) {
			this.bio.setVisible(false);
			laBioTitle.setText("");
			anchorPaneBio.setVisible(false);
		}
		else {
			this.bio.setText(begBio);
		}
		
		try {
				questionsNum=pc.getQuestions(gub.getUsername(), "beginner").size();
		} catch (SQLException e) {
				
		}
		laQuestionsNum.setText(questionsNum.toString());
		if(questionsNum==0) {			//se il num di domande accettate è zero, le label della colonna centrale scompaiono
			laAdvNumTitle.setVisible(false);
			laAdvNum.setVisible(false);
			laAdvListTitle.setVisible(false);
			laAdvList.setVisible(false);
			anchorPaneAdv.setVisible(false);
		}
		else {
			List<AdvancedUserBean> advList = new ArrayList<AdvancedUserBean>();
			advList = pc.differentAdv(gub.getUsername());
			advCounter=advList.size();				//numero di adv differenti contattati
			laAdvNum.setText(String.valueOf(advCounter));
			if(advList.isEmpty()) {					//lista di adv differenti contattati
				laAdvListTitle.setVisible(false);
				laAdvList.setText("");
			
			}
			else {
				String elenco = ListConverter.Converter(advList);
				
				laAdvList.setText(elenco);
			}
		
		}
		
	}
}
