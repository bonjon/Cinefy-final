package logic.viewfxml;


import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;
import logic.bean.PlaylistBean;
import logic.bean.RispostaBean;
import logic.exceptions.AdvancedNotFoundException;
import logic.utils.Roles;

/*
 * Classe che gestisce i cambi di scena nel caso del
 * advanced user. Anche qui è stato utilizzato il pattern 
 * del singleton perchè appunto si vuole soltanto un'istanza.
 */

public class AdvancedGraphicChange extends GraphicChangeTemplate {

	private static AdvancedGraphicChange instance = null;

	private AdvancedGraphicChange() {
		whoAmI = Roles.ADVANCEDUSER;
	}

	public static AdvancedGraphicChange getInstance() {
		if (instance == null) {
			instance = new AdvancedGraphicChange();
		}
		return instance;
	}

	@Override
	public void toHomepage(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeAdvanced.fxml"));
		scene.setRoot(loader.load());
	}

	@Override
	public void toProfile(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileAdvanced.fxml"));
		ProfileAdvancedBoundary pab = new ProfileAdvancedBoundary();
		loader.setController(pab);
		scene.setRoot(loader.load());
	}

	public void toAnswer(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AnswerAdvanced.fxml"));
		scene.setRoot(loader.load());
	}
	
	public void toSelQuestionDetail(Scene scene, DomandaBean selectedItem, BeginnerUserBean bub) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedQuestion.fxml"));
		SelectedQuestionBoundary sqb = new SelectedQuestionBoundary();
		loader.setController(sqb);
		scene.setRoot(loader.load());
		sqb.init(selectedItem,bub);
	}
	
	public void toAnswerDetail(Scene scene, RispostaBean selectedItem) throws IOException, SQLException, AdvancedNotFoundException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AnswerDetails.fxml"));
		AnswerDetailsBoundary adb = new AnswerDetailsBoundary();
		
		loader.setController(adb);
		scene.setRoot(loader.load());
		adb.init(selectedItem);
	}
	
	public void toQuestionsFromABeg(Scene scene, DomandaBean db, RispostaBean rb, BeginnerUserBean bub) throws IOException, AdvancedNotFoundException, SQLException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionsFromABeginner.fxml"));
		QuestionsFromBeginner qfb = new QuestionsFromBeginner();
		loader.setController(qfb);
		scene.setRoot(loader.load());
		qfb.init(db,rb,bub);
	}

	public void toPlaylists(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistAdvanced.fxml"));
		scene.setRoot(loader.load());
	}

	public void toPlaylistDetails(Scene scene, PlaylistBean selectedItem) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistDetailsAD.fxml"));
		PlaylistDetailsADBoundary pdb = new PlaylistDetailsADBoundary();
		loader.setController(pdb);
		scene.setRoot(loader.load());
		pdb.init(selectedItem);
	}

	public void toCreatePlaylist(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatePlaylist.fxml"));
		scene.setRoot(loader.load());
	}
}
