package logic.viewfxml;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.PlaylistBean;
import logic.utils.Roles;

/*
 * Classe che gestisce i cambi di scena nel caso del
 * beginner user. Anche qui è stato utilizzato il pattern 
 * del singleton perchè appunto si vuole soltanto un'istanza.
 */

public class BeginnerGraphicChange extends GraphicChangeTemplate {

	private static BeginnerGraphicChange INSTANCE = null;

	private BeginnerGraphicChange() {
		whoAmI = Roles.BEGINNERUSER;
	}

	public static BeginnerGraphicChange getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BeginnerGraphicChange();
		}
		return INSTANCE;
	}

	@Override
	public void toHomepage(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeBeginner.fxml"));
		scene.setRoot(loader.load());
	}

	@Override
	public void toProfile(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileBeginner.fxml"));
		ProfileBeginnerBoundary pbb = new ProfileBeginnerBoundary();
		loader.setController(pbb);
		scene.setRoot(loader.load());
	}

	public void toAsk(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AskBeginner.fxml"));
		scene.setRoot(loader.load());
	}

	public void toQuestion(Scene scene, AdvancedUserBean aub) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Question.fxml"));
		QuestionsBoundary qb = new QuestionsBoundary();
		loader.setController(qb);
		scene.setRoot(loader.load());
		qb.init(aub);
	}

	public void toPlaylists(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistBeginner.fxml"));
		scene.setRoot(loader.load());
	}

	public void toViewPlaylist(Scene scene, PlaylistBean selectedItem) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistDetails.fxml"));
		PlaylistDetailsBoundary pdb = new PlaylistDetailsBoundary();
		loader.setController(pdb);
		scene.setRoot(loader.load());
		pdb.init(selectedItem);
	}

	public void toQuestionDetails(Scene scene, DomandaBean selectedItem, String color) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionDetails.fxml"));
		QuestionDetailsBoundary qdp = new QuestionDetailsBoundary();
		loader.setController(qdp);
		scene.setRoot(loader.load());
		qdp.init(selectedItem, color);
	}
}
