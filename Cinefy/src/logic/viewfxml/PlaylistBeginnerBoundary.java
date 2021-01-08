package logic.viewfxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.bean.PlaylistBean;
import logic.controllers.ViewPlaylistsController;
import logic.exceptions.PlaylistNotFoundException;
import logic.utils.FileManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*
 * Classe PlaylistBeginnerBoundary di PlaylistBeginner 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class PlaylistBeginnerBoundary implements Initializable {

	ObservableList<PlaylistBean> listPlay;
	ObservableList<PlaylistBean> listTop;
	private static final Logger LOGGER = Logger.getLogger(PlaylistBeginnerBoundary.class.getName());

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ListView<PlaylistBean> topPlaylist;
	@FXML
	private TextField plText;
	@FXML
	private ListView<PlaylistBean> playlistList;
	@FXML
	private Label labelError1;
	@FXML
	private Label labelError3;
	@FXML
	private RadioButton radioBtn;

	private ViewPlaylistsController vpc;
	private BeginnerGraphicChange bgc;
	int btn = 0;
	int start = 0;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAskClicked(MouseEvent event) throws IOException {
		this.bgc.toAsk(this.ask.getScene());
	}

	@FXML
	public void onSelectedTopPl(MouseEvent event) throws IOException, ClassNotFoundException {
		if (!this.listTop.isEmpty()) {
			PlaylistBean topPlayItem = this.topPlaylist.getSelectionModel().getSelectedItem();
			if (topPlayItem != null) {
				this.bgc.toViewPlaylist(this.topPlaylist.getScene(), topPlayItem);
			}
		}
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onEnterPressed(KeyEvent event) throws SQLException, ClassNotFoundException {
		this.playlistList.getItems().clear();
		this.labelError1.setText("");
		if (start == 0) {
			this.radioBtn.setSelected(false);
			btn = 0;
		}
		if (event.getCode() == KeyCode.ENTER) {
			start = 0;
			if (btn == 0) {
				String p = this.plText.getText().toString();
				listPlay.removeAll(listPlay);
				try {
					PlaylistBean pb = vpc.getPlaylist(p);
					listPlay.add(pb);
					this.playlistList.getItems().addAll(listPlay);
					setCellPlaylist(1);
				} catch (PlaylistNotFoundException e) {
					labelError1.setText(e.getMessage());
				}
			} else {
				String user = this.plText.getText().toString();
				listPlay.removeAll(listPlay);
				try {
					List<PlaylistBean> pb = vpc.getPlaylistByAd(user);
					listPlay.addAll(pb);
					this.playlistList.getItems().addAll(listPlay);
					setCellPlaylist(1);
				} catch (PlaylistNotFoundException e) {
					labelError1.setText(e.getMessage());
				}
			}
		}
	}

	private void setCellPlaylist(int i) {
		ListView<PlaylistBean> playlistL;
		if (i == 1) {
			playlistL = this.playlistList;
		} else {
			playlistL = this.topPlaylist;
		}
		playlistL.setCellFactory(param -> new ListCell<PlaylistBean>() {
			@Override
			protected void updateItem(PlaylistBean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				} else {
					String path;
					ImageView iv;
					VBox vBox;
					Label voto;
					Label name;
					path = FileManager.PLAYLISTS + File.separator + item.getPlaylistPic();
					iv = new ImageView(new Image(new File(path).toURI().toString()));
					vBox = new VBox(3);
					name = new Label(item.getName());
					voto = new Label(item.getVoto() + "/10.0");
					name.setFont(Font.font("Arial", 13));
					voto.setFont(Font.font("Arial", 13));
					name.setStyle("-fx-text-fill: " + "#f5c518" + ";");
					voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
					iv.setFitHeight(150);
					iv.setFitWidth(150);
					iv.setPreserveRatio(false);
					vBox.setAlignment(Pos.CENTER);
					vBox.getChildren().addAll(iv, name, voto);
					setGraphic(vBox);
					setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				}
			}
		});
	}

	@FXML
	public void onSelectedSearchPl(MouseEvent event) throws IOException, ClassNotFoundException {
		if (!this.listPlay.isEmpty()) {
			PlaylistBean playItem = this.playlistList.getSelectionModel().getSelectedItem();
			if (playItem != null) {
				this.bgc.toViewPlaylist(this.playlistList.getScene(), playItem);
			}
		}
	}

	@FXML
	public void onRadioPressed(ActionEvent event) {
		btn = 1;
		start = 1;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listPlay = FXCollections.observableArrayList();
		listTop = FXCollections.observableArrayList();
		this.vpc = new ViewPlaylistsController();
		this.bgc = BeginnerGraphicChange.getInstance();
		listTop.removeAll(listTop);
		try {
			List<PlaylistBean> lpb = vpc.getLeaderBoard();
			listTop.addAll(lpb);
			this.topPlaylist.getItems().addAll(lpb);
			setCellPlaylist(2);
		} catch (PlaylistNotFoundException e) {
			this.labelError3.setText(e.getMessage());
		} catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
	}
}
