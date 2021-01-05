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
 * Classe Boundary della HomeBeginner che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente. 
 */

public class HomeBeginnerBoundary implements Initializable {

	ObservableList<FilmBean> list;

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label errorLabel;
	@FXML
	private Label profile;
	@FXML
	private TextField movie;
	@FXML
	private ListView<FilmBean> listView;
	@FXML
	private WebView web;
	@FXML
	private SplitMenuButton splitMenu;

	private ViewListOfFilmsController vfc;
	private BeginnerGraphicChange bgc;
	int itemSelected; // 0 movie name, 1 Director, 2 Nation, 3 Actor, 4 Release Year, 5 Genre

	@FXML
	public void onAskClicked(MouseEvent event) throws IOException {
		this.bgc.toAsk(this.ask.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}
	
	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onDirectorPressed(ActionEvent event) {
		itemSelected = 1;
		this.splitMenu.setText("Director");
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
	public void onYearPressed(ActionEvent event) {
		itemSelected = 4;
		this.splitMenu.setText("Year");
	}

	@FXML
	public void onGenrePressed(ActionEvent event) {
		itemSelected = 5;
		this.splitMenu.setText("Genre");
	}

	@FXML
	public void onResetPressed(ActionEvent event) {
		itemSelected = 0;
		this.splitMenu.setText("Search by:");
	}

	@FXML
	public void onSelectedFilm(MouseEvent event) {
		WebEngine engine = web.getEngine();
		FilmBean fb = this.listView.getSelectionModel().getSelectedItem();
		String url = fb.getUrl();
		engine.load(url);
		this.web.setVisible(true);
	}

	@FXML
	public void onEnterPressed(KeyEvent event) throws SQLException, ClassNotFoundException {
		this.listView.getItems().clear();
		this.errorLabel.setText("");
		if (event.getCode() == KeyCode.ENTER) {
			// Caso in cui non si applicano filtri e si cerchi per nome del film
			if (itemSelected == 0) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByName(a);
			} else if (itemSelected == 1) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByDirector(a);
			} else if (itemSelected == 2) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByNation(a);
			} else if (itemSelected == 3) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByActor(a);
			} else if (itemSelected == 4) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByYear(a);
			} else if (itemSelected == 5) {
				String a = this.movie.getText().toString();
				list.removeAll(list);
				getFilmByGenre(a);
			}
		}
	}

	private void getFilmByYear(String a) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByYear(a);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getFilmByGenre(String a) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByGenre(a);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getFilmByActor(String a) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByActor(a);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getFilmByNation(String a) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByNation(a);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	private void getFilmByDirector(String a) throws ClassNotFoundException, SQLException {
		try {
			List<FilmBean> mlb = vfc.getFilmByDirector(a);
			list.addAll(mlb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	public void setCellMovies() {
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
					setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				} else {
					setText(item.getTitolo());
					setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
				}
			}
		});
	}

	public void getFilmByName(String a) throws ClassNotFoundException, SQLException {
		try {
			FilmBean fb = vfc.getFilm(a);
			list.add(fb);
			this.listView.getItems().addAll(list);
			setCellMovies();
		} catch (FilmNotFoundException e) {
			this.errorLabel.setText(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		this.vfc = new ViewListOfFilmsController();
		this.bgc = BeginnerGraphicChange.getInstance();
		itemSelected = 0;
	}
}
