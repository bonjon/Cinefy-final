package logic.viewfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import logic.utils.ExceptionInfo;
import logic.utils.FileManager;


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
	private ImageView ivProfilepic;
	
	@FXML
	private ImageView cinema;
	
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
	

	private File imageFile ;

	private String nomeFile;
	
	private String profession = null;

	private String role = null;
	
	private boolean imageChanged = false; //booleano: true se l' immagine è diversa da quella di default, false altrimenti
	
	private String defaultPic = "default.png";
	
	private String defaultPath = FileManager.PROFILE+File.separator+defaultPic;
	
	private static final Logger logger = Logger.getLogger(RegistrationBoundary.class.getName());

	private String newFileName = null;
	
	private String usernameAlreadyUsed = "This username has been already used";
	
	private Boolean contrRes = Boolean.TRUE;
	
	
	@FXML
	public void onBackToLoginPressed(ActionEvent event) throws IOException {

		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));

			Scene sc = ((Node) event.getSource()).getScene();
			sc.setRoot(loader.load());

		
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
				this.btnDefaultPic.setVisible(false);
				this.laDefaultPic.setVisible(true);
			
			}
			else {
				
				this.laDefaultPic.setVisible(false);
				this.btnDefaultPic.setVisible(true);
		
				InputStream input = new FileInputStream(this.imageFile);
				Image image = new Image(input);
				ivProfilepic.setImage(image);
				imageChanged=true; 
			}
		
		
		
		}

	@FXML
	public void onSignUpPressed(ActionEvent event)  throws IOException, SQLException, ClassNotFoundException{


		RegistrationController controller = new RegistrationController();
		
		String fileName = "";
		String username;
		
		
		
		username=this.tfUser.getText();
		
		try {
		
		if(imageChanged) {
		
			fileName=this.imageFile.getName();
			nomeFile=fileName;
			newFileName = FileManager.generateNewFileName(fileName, username);
		
		}

			
			
				if (role.equals("beginner") || role==null) {
					BeginnerUserBean bub = new BeginnerUserBean();
					
					bub.setUsername(username);
					bub.setPassword(this.tfPass.getText());
					bub.setBio(this.tfTellus.getText());
					bub.setProfilePic(newFileName);

					contrRes = controller.createBeginnerUser(bub);
					
				}
			
				else if (role.equals("advanced")) {
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
					
					//copia dell' immagine nella cartella "img"
					imageCopy(nomeFile,username);
					
					//cambio scena
					GraphicChangeTemplate.toLogin(btnSignup.getScene());
				}

			}
		
		catch (FieldEmptyException | FieldTooLongException e) {
			
			//ad ogni indice della lista delle labels corrisponde un indice della lista dei booleani che descrivono
			//il tipo d errore

			ExceptionInfo ei = controller.getExceptionInfo();
			int y=0;
			boolean tooLong = false;
		
			List<Label> labels = new ArrayList<>();
			labels.add(userError);
			labels.add(passwordError);
			labels.add(userTypeError);
			labels.add(professionError);
			labels.add(userTooLongError);
			labels.add(passwordTooLongError);
			labels.add(bioTooLongError);
			
			List<Boolean> info = new ArrayList<>();
			info.add(ei.getEmptyUsername());
			info.add(ei.getEmptyPassword());
			info.add(ei.getEmptyRole());
			info.add(ei.getEmptyProfession());
			info.add(ei.getTooLongUser());
			info.add(ei.getTooLongPassword());
			info.add(ei.getTooLongBio());
			
			
			while(y<info.size()) {
				if(info.get(y).equals(true)) {
					//se il booleano di info torna true setto il messaggio d' errore sulla label corrispondente
					labels.get(y).setText(e.getMessage());
					tooLong = true;
				}
				if(tooLong) { 
					labels.get(y).setVisible(true);
					tooLong = false;
				}
				
				y++;
			}
		}
			
		
	}
	
	private void imageCopy(String fileName, String username) throws IOException {
		//copia dell' immagine nella cartella "img"
		
		String path = FileManager.PROFILE;
		String fName = fileName;

		if(!imageChanged) {
			newFileName=FileManager.generateNewFileName(defaultPic, username);
			fName=defaultPic;
			File file = new File(path, fName);
			File newFile = new File(path, newFileName);
			InputStream input = new FileInputStream(file);
			Files.copy(input, newFile.toPath());
			
			
		}else {
			
		
		fName = this.imageFile.getName();
		
		File file = new File(path, fName);
		File newFile = new File(path, newFileName);
		InputStream input = new FileInputStream(this.imageFile);
		
		
		
		Files.copy(input, file.toPath());
		if(!file.renameTo(newFile)) {
			logger.log(Level.WARNING, "Unable to rename: {0}", fName);
		}
		}
	}

	@FXML
	public void onBeginnerPressed(ActionEvent event) {

		splitMenu.setText("Beginner");
		role = "beginner";
		this.splitMenuProf.setText("Your profession:");
		profession=null;
	    this.userTypeError.setText("");
	    this.professionError.setText("");
		splitMenuProf.visibleProperty().set(false);

	}

	@FXML
	public void onAdvancedPressed(ActionEvent event) {

		splitMenu.setText("Advanced");
		role = "advanced";
		this.userTypeError.setText("");
		splitMenuProf.visibleProperty().set(true);

	}
	
	@FXML
	public void onProfButtonPressed(ActionEvent event) {
		
			MenuItem pressedButton = (MenuItem)event.getSource();
			profession = pressedButton.getText();
			splitMenuProf.setText(profession);
			this.professionError.setText("");
						
	}
	
	@FXML
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
	}
	
	@FXML
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
