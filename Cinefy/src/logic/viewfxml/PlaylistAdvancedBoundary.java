package logic.viewfxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.ViewPlaylistsController;
import logic.exceptions.PlaylistNotFoundException;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe PlaylistAdvancedBoundary di PlaylistAdvanced
 * che si occuperà di mediare l'interazione tra il sistema
 * e l'ambiente.
 */

public class PlaylistAdvancedBoundary implements Initializable {

	ObservableList<PlaylistBean> list;
	ObservableList<PlaylistBean> list2;
	public static final String BACK = "-fx-control-inner-background: ";
	public static final String COLOR = " #1c1c1c";
	public static final String FONT = "Arial";
	public static final String TEXTCOLOR = "#f5c518";
	public static final String TEXTFILL = "-fx-text-fill: ";

	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private ListView<PlaylistBean> topPlaylist;
	@FXML
	private ListView<PlaylistBean> playlistList;
	@FXML
	private Button btnCreate;
	@FXML
	private Label labelError1;
	@FXML
	private Label labelError2;

	private AdvancedGraphicChange agc;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAnswerClicked(MouseEvent event) throws IOException {
		this.agc.toAnswer(this.answer.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onSelectedPlaylist(MouseEvent event) throws IOException {

		if (event.getSource() == topPlaylist && !list.isEmpty()) {
			PlaylistBean topPlayItem = this.topPlaylist.getSelectionModel().getSelectedItem();
			if (topPlayItem != null) {
				this.agc.toPlaylistDetails(this.topPlaylist.getScene(), topPlayItem);
			}
		} else if (event.getSource() == playlistList && !list2.isEmpty()) {
			PlaylistBean playItem = this.playlistList.getSelectionModel().getSelectedItem();
			if (playItem != null) {
				this.agc.toPlaylistDetails(this.playlistList.getScene(), playItem);
			}
		}
	}

	@FXML
	public void onCreatePlaylist(ActionEvent event) throws IOException {
		this.agc.toCreatePlaylist(this.btnCreate.getScene());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		list2 = FXCollections.observableArrayList();
		ViewPlaylistsController vpc = new ViewPlaylistsController();
		this.agc = AdvancedGraphicChange.getInstance();
		list.removeAll(list);
		list2.removeAll(list2);
		try {
			List<PlaylistBean> lpb = vpc.getLeaderBoard();
			list.addAll(lpb);
			this.topPlaylist.getItems().addAll(lpb);
			setCellsPlaylist(1);
		} catch (PlaylistNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			List<PlaylistBean> lp = vpc.getPlaylistByAd(gub.getUsername());
			list2.addAll(lp);
			this.playlistList.getItems().addAll(lp);
			setCellsPlaylist(2);
		} catch (PlaylistNotFoundException e) {
			this.labelError2.setText(e.getMessage());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setCellsPlaylist(int i) {
		ListView<PlaylistBean> playlist;
		if (i == 1) {
			playlist = this.topPlaylist;
		} else {
			playlist = this.playlistList;
		}
		playlist.setCellFactory(param -> new ListCell<PlaylistBean>() {
			@Override
			protected void updateItem(PlaylistBean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setStyle(BACK + COLOR + ";");
				} else {
					VBox vBox;
					Label name;
					Label voto;
					ImageView iv;
					String path;
					path = FileManager.PLAYLISTS + File.separator + item.getPlaylistPic();
					iv = new ImageView(new Image(new File(path).toURI().toString()));
					name = new Label(item.getName());
					voto = new Label(item.getVoto() + "/10.0");
					name.setFont(Font.font(FONT, 13));
					voto.setFont(Font.font(FONT, 13));
					vBox = new VBox(3);
					iv.setFitHeight(150);
					iv.setFitWidth(150);
					iv.setPreserveRatio(false);
					name.setStyle(TEXTFILL + TEXTCOLOR + ";");
					voto.setStyle(TEXTFILL + TEXTCOLOR + ";");
					vBox.setAlignment(Pos.CENTER);
					vBox.getChildren().addAll(iv, name, voto);
					setGraphic(vBox);
					setStyle(BACK + COLOR + ";");
				}
			}
		});
	}
}
