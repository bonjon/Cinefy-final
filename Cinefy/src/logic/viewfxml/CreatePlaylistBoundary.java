package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.bean.FilmBean;
import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.CreatePlaylistController;
import logic.controllers.ViewListOfFilmsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.exceptions.FilmNotFoundException;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe CreatePlaylistBoundary di CreatePlaylist che 
 * si occuperà di mediare l'interazione tra sistema
 * e ambiente.
 */

public class CreatePlaylistBoundary implements Initializable {

	ObservableList<FilmBean> list;

	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private Label labelError;
	@FXML
	private ImageView imageView;
	@FXML
	private ListView<FilmBean> filmPlaylist;
	@FXML
	private TextField movie;
	@FXML
	private TextField playlistName;
	@FXML
	private Label tvStatus;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnBack;
	@FXML
	private Label nameError;
	@FXML
	private Label laPicChange;
	@FXML
	private Label laPicChosen;
	@FXML
	private Label laCreate;

	private AdvancedGraphicChange agc;
	private CreatePlaylistController cpc;
	private PlaylistBean pb;
	private FilmBean selected;
	private File imageFile;
	private String pathPic;

	private static final Logger logger = Logger.getLogger(RegistrationBoundary.class.getName());

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAnswerClicked(MouseEvent event) throws IOException {
		this.agc.toAnswer(this.answer.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onSelectedFilm(MouseEvent event) {
		this.selected = this.filmPlaylist.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void onOk(ActionEvent event) throws IOException {
		try {
			GeneralUserBean gub = SessionUser.getInstance().getSession();
			String fileName;
			String newFileName;
			String playlist = this.playlistName.getText();
			if (this.imageFile == null) {
				fileName = "";
				newFileName = "default2.jpg";
				this.pathPic = newFileName;
			} else {
				fileName = this.imageFile.getName();
				newFileName = playlist + fileName;
				this.pathPic = newFileName;
				String path = FileManager.PLAYLISTS;
				fileName = imageFile.getName();
				File file = new File(path, fileName);
				File newFile = new File(path, newFileName);
				InputStream input = new FileInputStream(this.imageFile);
				Files.copy(input, file.toPath());
				if (!file.renameTo(newFile)) {
					logger.log(Level.WARNING, "Unable to rename: {0}", fileName);
				}
			}
			this.pb = this.cpc.createPlaylist(this.playlistName.getText(), gub.getUsername(), pathPic);
			this.btnAdd.setVisible(true);
			this.btnOk.setVisible(false);
			this.playlistName.setDisable(true);
			this.imageView.setDisable(true);
			this.laPicChange.setVisible(false);
			this.laPicChosen.setVisible(true);
			this.laCreate.setVisible(true);
			this.tvStatus.setText("Now you can insert films");
			this.movie.setVisible(true);
			this.filmPlaylist.setVisible(true);
		} catch (FieldEmptyException e) {
			this.nameError.setText(e.getMessage());
		} catch (FieldTooLongException e) {
			this.nameError.setText(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toPlaylists(this.btnBack.getScene());
	}

	@FXML
	public void onAdd(ActionEvent event) throws NumberFormatException, SQLException {
		try {
			this.cpc.addFilm(Integer.parseInt(this.pb.getId()), this.selected);
		} catch (FieldEmptyException e) {
			this.labelError.setText(e.getMessage());
		} catch (SQLException e) {
			this.labelError.setText("Film already added");
		}
	}

	@FXML
	public void onEnterPressed(KeyEvent event) {
		this.filmPlaylist.getItems().clear();
		this.labelError.setText("");
		ViewListOfFilmsController vfc = new ViewListOfFilmsController();
		if (event.getCode() == KeyCode.ENTER) {
			String a = this.movie.getText().toString();
			list.removeAll(list);
			try {
				FilmBean fb = vfc.getFilm(a);
				list.add(fb);
				this.filmPlaylist.getItems().addAll(list);
				/*
				 * Utilizziamo una setCellFactory per stampare i nomi dei film, infatti essa
				 * serve a specificare come popolare le celle con una singola colonna
				 */
				this.filmPlaylist.setCellFactory(param -> new ListCell<FilmBean>() {
					@Override
					protected void updateItem(FilmBean item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null || item.getTitolo() == null) {
							setText(null);
							setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
						} else {
							setText(item.getTitolo());
							setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
						}
					}
				});

			} catch (FilmNotFoundException e) {
				this.labelError.setText(e.getMessage());
			} catch (SQLException e) {
				this.labelError.setText("SqlError");
			}
		}
	}

	@FXML
	public void onImageClick(MouseEvent event) throws FileNotFoundException {
		final FileChooser fc = new FileChooser();
		fc.setTitle("Select image");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		this.imageFile = fc.showOpenDialog(new Stage());
		if(this.imageFile==null) {
			String path = FileManager.PLAYLISTS + File.separator + "default2.jpg";
			File file = new File(path);
			Image img = new Image(file.toURI().toString());
			this.imageView.setImage(img);
		}
		else {
			InputStream input = new FileInputStream(this.imageFile);
			Image image = new Image(input);
			this.imageView.setImage(image);
		}
			return;
	}
	
	public void keyPressed(KeyEvent event) {
		if(event.getSource().equals(playlistName)) {
			nameError.setText("");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = FileManager.PLAYLISTS + File.separator + "default2.jpg";
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.imageView.setImage(img);
		this.agc = AdvancedGraphicChange.getInstance();
		this.cpc = new CreatePlaylistController();
		list = FXCollections.observableArrayList();
		;
		this.tvStatus.setText("");
		this.movie.setVisible(false);
		this.btnAdd.setVisible(false);
		this.filmPlaylist.setVisible(false);
	}
}
