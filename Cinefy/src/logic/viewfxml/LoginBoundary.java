package logic.viewfxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import logic.bean.GeneralUserBean;
import logic.controllers.LoginController;
import logic.exceptions.FieldEmptyException;
import logic.utils.ExceptionInfo;
import logic.utils.FileManager;
import logic.utils.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class LoginBoundary implements Initializable {

	@FXML
	public TextField tfUser;
	@FXML
	public PasswordField pfPass;
	@FXML
	public Button btnSignin;
	@FXML
	public Button btnReg;
	@FXML
	public Label userError;
	@FXML
	public Label passwordError;
	@FXML
	private Label userPassInvalid;
	@FXML
	private ImageView cinema;
	
	@FXML
	private ImageView facebook;

	
	@FXML
	public void onSignInPressed(ActionEvent event) throws SQLException, IOException, FieldEmptyException, ClassNotFoundException {
		

		GeneralUserBean gub = new GeneralUserBean();
		gub.setUsername(this.tfUser.getText());
		gub.setPassword(this.pfPass.getText());
		LoginController controller = new LoginController();
		
		try {
			GeneralUserBean gu;
			gu = controller.login(gub);
			
			if (gu==null) 
			{userPassInvalid.setVisible(true);}
			else {
		
				String role = gu.getRole();

				// SET SESSION GENERAL USER
				SessionUser su = SessionUser.getInstance();
				su.setSession(gu);

				switch(role) {
					case "beginner" :
				
						BeginnerGraphicChange.getInstance().toHomepage(this.tfUser.getScene());
						break;
				

					case "advanced":
			 
						AdvancedGraphicChange.getInstance().toHomepage(this.tfUser.getScene());
						break; 
			
					case "admin":
			 
						AdminGraphicChange.getInstance().toHomepage(this.tfUser.getScene()); 
						break;
					
					default: 
						break;
				}
			}
		}
			catch (FieldEmptyException e) {
				ExceptionInfo info = controller.getExceptionInfo();
				if (info.getEmptyUsername()) {
					this.userError.setText(e.getMessage());
					
				}
				if (info.getEmptyPassword()) {
					this.passwordError.setText(e.getMessage());
					
				}
			}
		}
		 

	

	@FXML
	public void onRegistrationPressed(ActionEvent event) throws IOException {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Regis.fxml"));

			Scene sc = ((Node) event.getSource()).getScene();
			sc.setRoot(loader.load());
		

	}
	
	public void keyPressed(KeyEvent event) {
		if(event.getSource().equals(tfUser)) {
			userError.setText("");
			userPassInvalid.setVisible(false);
			
		}
		if(event.getSource().equals(pfPass)) {
		    passwordError.setText("");
		    userPassInvalid.setVisible(false);
		}
		
}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = FileManager.PROJECT + "cinema.png";
		String path2 = FileManager.PROJECT + "facebook.png";
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.cinema.setImage(img);
		File file2 = new File(path2);
		Image img2 = new Image(file2.toURI().toString());
		this.facebook.setImage(img2);
	}

}
