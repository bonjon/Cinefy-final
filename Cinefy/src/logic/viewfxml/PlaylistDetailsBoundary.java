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
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.FilmBean;
import logic.bean.GeneralUserBean;
import logic.bean.PlaylistBean;
import logic.controllers.PlaylistDetailsController;
import logic.exceptions.FilmNotFoundException;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe PlaylistDetailsBoundary di PlaylistDetails 
 * che si occuperà di mediare l'interazione tra 
 * il sistema e l'ambiente.
 */

public class PlaylistDetailsBoundary {

	ObservableList<FilmBean> list;
	public static final String BACK = "-fx-control-inner-background: ";

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private Label labelError;
	@FXML
	private ListView<FilmBean> filmPlaylist;
	@FXML
	private Label playlistName;
	@FXML
	private Label advancedName;
	@FXML
	private Label voto;
	@FXML
	private Label playlistDate;
	@FXML
	private Slider sliderVote;
	@FXML
	private ImageView playlistPic;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnBack;
	@FXML
	private Label errorFilms;
	

	private BeginnerGraphicChange bgc;
	private PlaylistBean selectedPlaylist;
	private PlaylistDetailsController pdc;
	int vote;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onAskClicked(MouseEvent event) throws IOException {
		this.bgc.toAsk(this.ask.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onOk(ActionEvent event) throws ClassNotFoundException {
		vote = (int) this.sliderVote.getValue();
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		try {
			pdc.votePlaylist(vote, this.selectedPlaylist.getId(), gub.getUsername());
		} catch (NumberFormatException | SQLException e) {
			this.labelError.setText(e.getMessage());
		}
	}

	@FXML
	public void onBack(ActionEvent event) throws IOException {
		this.bgc.toPlaylists(this.btnBack.getScene());
	}

	public void init(PlaylistBean pb) throws ClassNotFoundException {
		this.playlistName.setText(pb.getName());
		this.advancedName.setText(pb.getAdvancedName());
		this.voto.setText(pb.getVoto());
		this.playlistDate.setText(pb.getDate());
		this.filmPlaylist.setMouseTransparent(true);
		this.filmPlaylist.setFocusTraversable(false);
		String path = FileManager.PLAYLISTS + File.separator + this.selectedPlaylist.getPlaylistPic();
		this.bgc = BeginnerGraphicChange.getInstance();
		this.pdc = new PlaylistDetailsController();
		this.selectedPlaylist = pb;
		File file = new File(path);
		Image img = new Image(file.toURI().toString());
		this.playlistPic.setImage(img);
		list = FXCollections.observableArrayList();
		list.removeAll(list);
		try {
			List<FilmBean> lfb = pdc.getFilmPlaylist(this.selectedPlaylist.getId());
			list.addAll(lfb);
			this.filmPlaylist.getItems().addAll(list);
			this.filmPlaylist.setCellFactory(param -> new ListCell<FilmBean>() {
				@Override
				protected void updateItem(FilmBean item, boolean empty) {
					super.updateItem(item, empty);
					setStyle(BACK + " #1c1c1c" + ";");
					if (empty || item == null || item.getTitolo() == null) {
						setText(null);
					} else {
						setText(item.getTitolo());
					}
				}
			});
		} catch (NumberFormatException | SQLException | FilmNotFoundException e) {
			errorFilms.setText("This playlist does not contain any films");
		}
	}
}
