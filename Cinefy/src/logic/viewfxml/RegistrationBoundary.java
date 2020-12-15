package logic.viewfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
//import java.nio.file.Files;
//import java.util.logging.Level;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.controllers.RegistrationController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.FileManager;
import logic.utils.Roles;

public class RegistrationBoundary implements Initializable {

	@FXML
	private Button btnBacktologin;

	@FXML
	private Button btnSignup;

	@FXML
	private TextField tfUser;

	@FXML
	private TextArea tfTellus;

	@FXML
	private PasswordField tfPass;

	@FXML
	private SplitMenuButton splitMenu;

	@FXML
	private SplitMenuButton splitMenuProf;

	@FXML
	private ImageView ivProfilepic, cinema;
	
	@FXML
	private Label userError;
	
	@FXML
	private Label passwordError;
	
	@FXML
	private Label userTypeError;
	
	@FXML
	private Label professionError;
	
	@FXML
	private Label laDefaultPic;
	
	@FXML
	private Button btnDefaultPic;
	
	@FXML
	private Label bioError;
	
	@FXML
	private Label userTooLongError;
	
	@FXML
	private Label passwordTooLongError;
	
	@FXML
	private Label bioTooLongError;
	

	public File imageFile ;

	public String fileName;
	
	public String profession = null;

	public Roles role = null;
	
	public String noSelRole = "noSelRole"; 
	
	private boolean imageChanged = false; //booleano: true se l' immagine è diversa da quella di default, false altrimenti
	
	private String defaultPath = FileManager.PROFILE+File.separator+"default.png";
	
	private static final Logger logger = Logger.getLogger(RegistrationBoundary.class.getName());

	public String newFileName;
	
	private String usernameAlreadyUsed = "This username has been already used";
	
	private Boolean contrRes = Boolean.TRUE;
	
	
	@FXML
	public void onBackToLoginPressed(ActionEvent event) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));

			Scene sc = ((Node) event.getSource()).getScene();
			sc.setRoot(loader.load());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onImagePressed(MouseEvent event) throws FileNotFoundException  {
		

			final FileChooser fc = new FileChooser();
			fc.setTitle("Select image");
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
			this.imageFile = fc.showOpenDialog(new Stage());
			
			if(this.imageFile==null) {
				InputStream input = new FileInputStream(defaultPath);
				Image image = new Image(input);
				ivProfilepic.setImage(image);
				newFileName=null;
				imageChanged=false; 
			
			}
			else {
				
				this.laDefaultPic.setVisible(false);
				this.btnDefaultPic.setVisible(true);
		
				InputStream input = new FileInputStream(this.imageFile);
				Image image = new Image(input);
				ivProfilepic.setImage(image);
				imageChanged=true; 
			}
		
		return ;
		
		}

	
	public void onSignUpPressed(ActionEvent event)  throws IOException, SQLException{


		RegistrationController controller = new RegistrationController();
		
		String fileName;
		String username;
		
		
		username=this.tfUser.getText();
		
		if(imageChanged==false) { newFileName = null;}
		
		else {
		
			fileName=this.imageFile.getName();
			
			newFileName = FileManager.generateNewFileName(fileName, username);
		
		}

			try {
			
				if (role == Roles.BEGINNERUSER || role==null) {
					BeginnerUserBean bub = new BeginnerUserBean();
					if(role==null) { bub.setRole(noSelRole); };
					bub.setUsername(username);
					bub.setPassword(this.tfPass.getText());
					bub.setBio(this.tfTellus.getText());
					bub.setProfilePic(newFileName);

					contrRes = controller.createBeginnerUser(bub);
					
				}
			
				else if (role == Roles.ADVANCEDUSER) {
					AdvancedUserBean aub = new AdvancedUserBean();
					aub.setUsername(username);
					aub.setPassword(this.tfPass.getText());
					aub.setBio(this.tfTellus.getText());
					aub.setProfilePic(newFileName);
					aub.setProfession(profession);
				
					contrRes = controller.createAdvancedUser(aub);
					
				}

				if(contrRes.equals(Boolean.FALSE)) {
					
					userError.setText(usernameAlreadyUsed);
				}
				else {
					
					//copia dell' immagine nella cartella "res"
					
					String path = FileManager.PROFILE;
					

					if(imageChanged==false) {
						newFileName=FileManager.generateNewFileName("default.png", username);
						fileName="default.png";
						File file = new File(path, fileName);
						File newFile = new File(path, newFileName);
						InputStream input = new FileInputStream(file);
						Files.copy(input, newFile.toPath());
						
						
					}else {
						
					
					fileName = this.imageFile.getName();
					
					File file = new File(path, fileName);
					File newFile = new File(path, newFileName);
					InputStream input = new FileInputStream(this.imageFile);
					
					
					
					Files.copy(input, file.toPath());
					if(!file.renameTo(newFile)) {
						logger.log(Level.WARNING, "Unable to rename: {0}", fileName);
					}
					}
					//cambio scena
					GraphicChangeTemplate.toLogin(btnSignup.getScene());
				}

			}
		
		catch (FieldEmptyException | FieldTooLongException e) {
			
			if (FieldEmptyException.EMPTYUSERNAME==true) {
				this.userError.setText(e.getMessage());
				FieldEmptyException.EMPTYUSERNAME=false;
			}
			if (FieldEmptyException.EMPTYPASSWORD==true) {
				this.passwordError.setText(e.getMessage());
				FieldEmptyException.EMPTYPASSWORD=false;
			}
			if (FieldEmptyException.EMPTYROLE==true) {
				this.userTypeError.setText(e.getMessage());
				FieldEmptyException.EMPTYROLE=false;
			}
			if (FieldEmptyException.EMPTYPROFESSION==true) {
				this.professionError.setText(e.getMessage());
				FieldEmptyException.EMPTYPROFESSION=false;
			}
			if (FieldTooLongException.USERTOOLONG==true) {
				this.userError.setText(e.getMessage());
				this.userTooLongError.setVisible(true);
				FieldTooLongException.USERTOOLONG=false;
			}
			if(FieldTooLongException.PASSTOOLONG==true) {
				this.passwordError.setText(e.getMessage());
				this.passwordTooLongError.setVisible(true);
				FieldTooLongException.PASSTOOLONG=false;
			}
			if(FieldTooLongException.BIOTOOLONG==true) {
				this.bioError.setText(e.getMessage());
				this.bioTooLongError.setVisible(true);
				FieldTooLongException.BIOTOOLONG=false;
			}
		}
			
		
	}

	public void onBeginnerPressed(ActionEvent event) {

		splitMenu.setText("Beginner");
		role = Roles.BEGINNERUSER;
		this.splitMenuProf.setText("Your profession:");
		profession=null;
	    this.userTypeError.setText("");
	    this.professionError.setText("");
		splitMenuProf.visibleProperty().set(false);
		return;

	}

	public void onAdvancedPressed(ActionEvent event) {

		splitMenu.setText("Advanced");
		role = Roles.ADVANCEDUSER;
		this.userTypeError.setText("");
		splitMenuProf.visibleProperty().set(true);
		return;

	}
	
	public void onProfButtonPressed(ActionEvent event) {
		
			MenuItem pressedButton = (MenuItem)event.getSource();
			profession = pressedButton.getText();
			splitMenuProf.setText(profession);
			this.professionError.setText("");
			
			
			return;
			
	}
	
	public void keyPressed(KeyEvent event) {
			if(event.getSource().equals(tfUser)) {
				userError.setText("");
				userTooLongError.setVisible(false);
				
			}
			if(event.getSource().equals(tfPass)) {
			    passwordError.setText("");
			    passwordTooLongError.setVisible(false);
			}
			if(event.getSource().equals(tfTellus)) {
			    bioError.setText("");
			    bioTooLongError.setVisible(false);
			}
			return;
	}
			
	public void onDefaultPicButton(ActionEvent event) throws FileNotFoundException {
		this.btnDefaultPic.setVisible(false);
		this.laDefaultPic.setVisible(true);
		InputStream input = new FileInputStream(defaultPath);
		Image image = new Image(input);
		ivProfilepic.setImage(image);
		newFileName=null;
		imageChanged=false;
		
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = FileManager.PROJECT + "cinema.png";
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.cinema.setImage(img);
		File file2 = new File(defaultPath);
		Image img2 = new Image(file2.toURI().toString());
		this.ivProfilepic.setImage(img2);		
	}

}
