package logic.viewfxml;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.utils.Roles;

/*
 * Classe che gestisce i cambi di scena nel caso del
 * admin user. Anche qui è stato utilizzato il pattern 
 * del singleton perchè appunto si vuole soltanto un'istanza.
 */

public class AdminGraphicChange extends GraphicChangeTemplate {

	private static AdminGraphicChange instance = null;

	private AdminGraphicChange() {
		whoAmI = Roles.ADMIN;
	}

	public static AdminGraphicChange getInstance() {
		if (instance == null) {
			instance = new AdminGraphicChange();
		}
		return instance;
	}

	@Override
	public void toHomepage(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeAdmin.fxml"));
		scene.setRoot(loader.load());
	}

	@Override
	public void toProfile(Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileAdmin.fxml"));
		ProfileAdminBoundary pab = new ProfileAdminBoundary();
		loader.setController(pab);
		scene.setRoot(loader.load());
	}

	public void toManageQuestion(Scene scene, DomandaBean selectedItem) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageQuestion.fxml"));
		ManageQuestionBoundary mqb = new ManageQuestionBoundary();
		loader.setController(mqb);
		scene.setRoot(loader.load());
		mqb.init(selectedItem);
	}
	public void toManageAnswer(Scene scene, RispostaBean selectedItem) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageAnswer.fxml"));
		ManageAnswerBoundary mab = new ManageAnswerBoundary();
		loader.setController(mab);
		scene.setRoot(loader.load());
		mab.init(selectedItem);
	}
	
}
