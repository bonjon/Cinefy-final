package logic.viewfxml;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import logic.utils.Roles;

/*
 * Classe astratta GraphicChangeTemplate in cui ci saranno
 * i cambi di scena "predefiniti" per ogni utente, come puo 
 * essere il login, in cui avremo come metodi toHomepage
 * o toProfile che sono appunto i metodi che hanno tutti e
 *  3 i graphic change diminuendo la duplicazione del codice.
 *  Inoltre ci sarà il metodo to login che verrà ereditato dalle
 *  classi figlie.
 */

public abstract class GraphicChangeTemplate {

	protected Roles whoAmI;

	public static void toLogin(Scene scene) throws IOException{
		FXMLLoader loader = new FXMLLoader(GraphicChangeTemplate.class.getResource("Login.fxml"));
		scene.setRoot(loader.load());
	}


	public abstract void toHomepage(Scene scene) throws IOException;
	
	public abstract void toProfile(Scene scene) throws IOException;
}
