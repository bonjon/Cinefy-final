package logic.viewfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import logic.bean.FilmBean;
import logic.controllers.ViewListOfFilmsController;
import logic.exceptions.FilmNotFoundException;

/*
 * Classe Boundary della HomeAdvanced che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente. 
 */

public class HomeAdvancedBoundary implements Initializable {

	ObservableList<FilmBean> list;
	public static final String BACK = "-fx-control-inner-background: ";
	public static final String COLOR = " #1c1c1c";

	@FXML
	private Label home;
	@FXML
	private Label answer;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private Label errorLabel;
	@FXML
	private TextField movie;
	@FXML
	private ListView<FilmBean> listView;
	@FXML
	private WebView web;
	@FXML
	private SplitMenuButton splitMenu;

	private ViewListOfFilmsController vfc;
	private AdvancedGraphicChange agc;
	int itemSelected; // 0 movie name, 1 Director, 2 Nation, 3 Actor, 4 Release Year, 5 Genre

	@FXML
	public void onAnswerClicked(MouseEvent event) throws IOException {
		this.agc.toAnswer(this.answer.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.agc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.agc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onNationPressed(ActionEvent event) {
		itemSelected = 2;
		this.splitMenu.setText("Nation");
	}

	@FXML
	public void onActorPressed(ActionEvent event) {
		itemSelected = 3;
		this.splitMenu.setText("Actor");
	}

	@FXML
	public void onDirectorPressed(ActionEvent event) {
		itemSelected = 1;
		this.splitMenu.setText("Director");
	}

	@FXML
	public void onGenrePressed(ActionEvent event) {
		itemSelected = 5;
		this.splitMenu.setText("Genre");
	}

	@FXML
	public void onYearPressed(ActionEvent event) {
		itemSelected = 4;
		this.splitMenu.setText("Year");
	}

	@FXML
	public void onResetPressed(ActionEvent event) {
		itemSelected = 0;
		this.splitMenu.setText("Search by:");
	}

	@FXML
	public void onSelectedFilm(MouseEvent event) {
		WebEngine engine = web.getEngine();
		FilmBean fb = listView.getSelectionModel().getSelectedItem();
		String url = fb.getUrl();
		engine.load(url);
		this.web.setVisible(true);
	}

	@FXML
	public void onEnterPressed(KeyEvent event) throws SQLException, ClassNotFoundException {
		this.errorLabel.setText("");
		this.listView.getItems().clear();
		if (event.getCode() == KeyCode.ENTER) {
			// Caso in cui non si applicano filtri e si cerchi per nome del film
			if (itemSelected == 0) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMovie(search);
			} else if (itemSelected == 1) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMovieByDirector(search);
			} else if (itemSelected == 2) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMovieByNation(search);
			} else if (itemSelected == 3) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMovieByActor(search);
			} else if (itemSelected == 4) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMobieByYear(search);
			} else if (itemSelected == 5) {
				String search = this.movie.getText().toString();
				list.removeAll(list);
				getMovieByGenre(search);
			}
		}
	}

	private void getMovieByGenre(String search) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByGenre(search);
			list.addAll(mlb);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getMobieByYear(String search) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByYear(search);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getMovieByActor(String search) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByActor(search);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getMovieByDirector(String search) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByDirector(search);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getMovieByNation(String search) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByNation(search);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getMovie(String search) throws ClassNotFoundException, SQLException {
		try {
			FilmBean fb = vfc.getFilm(search);
			list.add(fb);
			this.listView.getItems().addAll(list);
			setCellsFilm();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void setCellsFilm() {
		/*
		 * Utilizziamo una setCellFactory per stampare i nomi dei film, infatti essa
		 * serve a specificare come popolare le celle con una singola colonna
		 */
		this.listView.setCellFactory(param -> new ListCell<FilmBean>() {
			@Override
			protected void updateItem(FilmBean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getTitolo() == null) {
					setText(null);
					setStyle(BACK + COLOR + ";");
				} else {
					setText(item.getTitolo());
					setStyle(BACK + COLOR + ";");
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		this.vfc = new ViewListOfFilmsController();
		this.agc = AdvancedGraphicChange.getInstance();
		itemSelected = 0;
	}
}
