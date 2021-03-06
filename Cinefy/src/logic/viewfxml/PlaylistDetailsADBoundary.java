package logic.viewfxml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import logic.bean.FilmBean;
import logic.bean.PlaylistBean;
import logic.controllers.PlaylistDetailsController;
import logic.exceptions.FilmNotFoundException;
import logic.utils.FileManager;

/*
 * Classe PlaylistDetailsADBoundary di PlaylistDetailsAD che
 * si occuperà di mediare l'interazione tra il sistema 
 * e l'ambiente.
 */

public class PlaylistDetailsADBoundary {

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
	private ListView<FilmBean> filmPlaylist;
	@FXML
	private ImageView playlistPic;
	@FXML
	private Label playlistName;
	@FXML
	private Label advancedName;
	@FXML
	private Label voto;
	@FXML
	private Label playlistDate;
	@FXML
	private Button btnBack;
	@FXML
	private Label errorFilms;

	private AdvancedGraphicChange agc;

	@FXML
	public void onAnswerClicked(MouseEvent event) throws IOException {
		this.agc.toAnswer(this.answer.getScene());
	}
	
	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.agc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.agc.toPlaylists(this.btnBack.getScene());
	}
	
	public void init(PlaylistBean pb) throws ClassNotFoundException {
		String elidedVote;
		
		PlaylistDetailsController pdc = new PlaylistDetailsController();
		this.agc = AdvancedGraphicChange.getInstance();
		PlaylistBean selectedPlaylist = pb;
		this.playlistName.setText(pb.getName());
		this.advancedName.setText(pb.getAdvancedName());
		
		if(pb.getVoto().length()>4) {
			elidedVote=pb.getVoto().substring(0,4);
			voto.setText(elidedVote);
		}
		else {
			voto.setText(pb.getVoto());
		}
		this.playlistDate.setText(pb.getDate());
		this.filmPlaylist.setMouseTransparent(true);
		this.filmPlaylist.setFocusTraversable(false);
		String path = FileManager.PLAYLISTS + File.separator + selectedPlaylist.getPlaylistPic();
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.playlistPic.setImage(img);
		list = FXCollections.observableArrayList();
		list.removeAll(list);
		try {
			List<FilmBean> lfb = pdc.getFilmPlaylist(selectedPlaylist.getId());
			list.addAll(lfb);
			this.filmPlaylist.getItems().addAll(list);
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
		} catch (NumberFormatException | SQLException | FilmNotFoundException e) {
			errorFilms.setText("This playlist does not contain any films");
			
		}
	}
}
