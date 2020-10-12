package logic.viewfxml;

import java.io.IOException;
import java.sql.SQLException;

import logic.bean.GeneralUserBean;
import logic.controllers.LoginController;
import logic.exceptions.FieldEmptyException;
import logic.utils.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginBoundary {

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
				if (FieldEmptyException.EMPTYUSERNAME==true) {
					System.out.println(e.getMessage());
					this.userError.setText(e.getMessage());
					FieldEmptyException.EMPTYUSERNAME=false;
				}
				if (FieldEmptyException.EMPTYPASSWORD==true) {
					this.passwordError.setText(e.getMessage());
					FieldEmptyException.EMPTYPASSWORD=false;
				}
			}
		}
		 

	

	@FXML
	public void onRegistrationPressed(ActionEvent event) throws IOException {
		try {
			System.out.println("ciao");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Regis.fxml"));

			Scene sc = ((Node) event.getSource()).getScene();
			sc.setRoot(loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		return;
}

}
