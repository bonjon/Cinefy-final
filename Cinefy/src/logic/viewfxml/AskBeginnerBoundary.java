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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.controllers.AskForQuestionsController;
import logic.exceptions.AdvancedNotFoundException;
import logic.observer.ObservableAdTopList;
import logic.observer.TopRatedPanel;
import logic.utils.ChangeOnceListener;
import logic.utils.FileManager;
import logic.utils.SessionUser;

/*
 * Classe Boundary della AskBeginner che si occuperà
 * di mediare l'interazione tra il sistema e l'ambiente.
 */

public class AskBeginnerBoundary implements Initializable {

	ObservableList<AdvancedUserBean> listSearch;
	ObservableList<DomandaBean> listQuestions;
	List<DomandaBean> ldb;
	List<DomandaBean> lb; // Domande in coda, cioè che devono essere accettate
	public static final String FXBACK = "-fx-control-inner-background: ";
	public static final String COLOR = " #1c1c1c";
	public static final String DEFAULT = "default.png";
	public static final String FONT = "Arial";
	public static final String TEXTCOLOR = "#f5c518";
	public static final String TEXTFILL = "-fx-text-fill: ";
	private static final Logger LOGGER = Logger.getLogger(AskBeginnerBoundary.class.getName());

	@FXML
	private Label home;
	@FXML
	private Label ask;
	@FXML
	private Label playlists;
	@FXML
	private Label profile;
	@FXML
	private Label labelError1;
	@FXML
	private Label labelError2;
	@FXML
	private Label labelError3;
	@FXML
	private TextField adText;
	@FXML
	public ListView<AdvancedUserBean> topAdvanced;
	@FXML
	private ListView<AdvancedUserBean> advanced;
	@FXML
	private ListView<DomandaBean> questions;
	@FXML
	private SplitMenuButton splitMenu;
	@FXML
	private ScrollPane scrollPane;

	private AskForQuestionsController afc;
	private BeginnerGraphicChange bgc;
	int start;
	String col;

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onSelectedTopAd(MouseEvent event) throws IOException {
		if (this.topAdvanced.getSelectionModel().getSelectedItem() != null)
			this.bgc.toQuestion(this.topAdvanced.getScene(), topAdvanced.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void onEnterPressed(KeyEvent event) throws SQLException, ClassNotFoundException {
		this.advanced.getItems().clear();
		this.labelError1.setText("");
		if (start == 1) {
			this.splitMenu.setText("Search by:");
		}
		if (event.getCode() == KeyCode.ENTER) {
			start = 0;
			String a = this.adText.getText().toString();
			listSearch.removeAll(listSearch);
			AdvancedUserBean ab = new AdvancedUserBean();
			ab.setUsername(a);
			try {
				AdvancedUserBean aub = afc.getAdvanced(ab);
				listSearch.add(aub);
				this.advanced.getItems().addAll(listSearch);
				this.advanced.setCellFactory(param -> new ListCell<AdvancedUserBean>() {
					@Override
					protected void updateItem(AdvancedUserBean item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setStyle(FXBACK + COLOR + ";");
						} else {
							VBox vBox;
							ImageView iv;
							String path;
							Label username;
							Label voto;
							path = FileManager.PROFILE + File.separator + DEFAULT;
							if (item.getProfilePic() != null) {
								path = FileManager.PROFILE + File.separator + item.getProfilePic();
							}
							vBox = new VBox(3);
							username = new Label(item.getUsername());
							voto = new Label(item.getVoto() + "/5.0");
							username.setFont(Font.font(FONT, 13));
							voto.setFont(Font.font(FONT, 13));
							username.setStyle(TEXTFILL + TEXTCOLOR + ";");
							voto.setStyle(TEXTFILL + TEXTCOLOR + ";");
							iv = new ImageView(new Image(new File(path).toURI().toString()));
							iv.setFitWidth(150);
							iv.setPreserveRatio(false);
							iv.setFitHeight(150);			
							vBox.getChildren().addAll(iv, username, voto);
							vBox.setAlignment(Pos.CENTER);
							setGraphic(vBox);
							setStyle(FXBACK + COLOR + ";");
						}
					}
				});
			} catch (AdvancedNotFoundException e) {
				this.labelError1.setText(e.getMessage());
			}
		}
	}

	@FXML
	public void onFilterPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
		MenuItem item = (MenuItem) event.getSource();
		this.splitMenu.setText(item.getText());
		this.advanced.getItems().clear();
		start = 1;
		String a = this.splitMenu.getText().toString();
		listSearch.removeAll(listSearch);
		AdvancedUserBean ab = new AdvancedUserBean();
		ab.setRole(a);
		try {
			List<AdvancedUserBean> aub = afc.getAdvancedByRole(ab);
			listSearch.addAll(aub);
			this.advanced.getItems().addAll(listSearch);
			this.advanced.setCellFactory(param -> new ListCell<AdvancedUserBean>() {
				@Override
				protected void updateItem(AdvancedUserBean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setStyle(FXBACK + COLOR + ";");
					} else {
						String path;
						Label username;
						Label voto;
						ImageView iv;
						VBox vBox;
						if (item.getProfilePic() == null) {
							path = FileManager.PROFILE + File.separator + DEFAULT;
						} else {
							path = FileManager.PROFILE + File.separator + item.getProfilePic();
						}
						iv = new ImageView(new Image(new File(path).toURI().toString()));
						username = new Label(item.getUsername());
						voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font(FONT, 13));
						voto.setFont(Font.font(FONT, 13));
						username.setStyle(TEXTFILL + TEXTCOLOR + ";");
						voto.setStyle(TEXTFILL + TEXTCOLOR + ";");
						vBox = new VBox(3);
						iv.setPreserveRatio(false);
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle(FXBACK + COLOR + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onSelectedSearchAd(MouseEvent event) throws IOException {
		if (this.advanced.getSelectionModel().getSelectedItem() != null)
			this.bgc.toQuestion(this.advanced.getScene(), this.advanced.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void onSelectedQuestion(MouseEvent event) throws IOException, NumberFormatException, ClassNotFoundException {
		if (!listQuestions.isEmpty()) {
			GeneralUserBean gub = SessionUser.getInstance().getSession();
			try {
				if (lb.contains(this.questions.getSelectionModel().getSelectedItem())) {
					col = "y";
					this.bgc.toQuestionDetails(this.questions.getScene(),
							this.questions.getSelectionModel().getSelectedItem(), col);
				}
				if (ldb.contains(this.questions.getSelectionModel().getSelectedItem())) {
					if (!this.afc.checkAnswer(gub.getUsername(),
							this.questions.getSelectionModel().getSelectedItem().getId())) {
						col = "g";
						this.bgc.toQuestionDetails(this.questions.getScene(),
								this.questions.getSelectionModel().getSelectedItem(), col);
					} else {
						col = "m";
						this.bgc.toQuestionDetails(this.questions.getScene(),
								this.questions.getSelectionModel().getSelectedItem(), col);
					}
				}
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, e.toString());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();

		scrollPane.hvalueProperty()
				.addListener(new ChangeOnceListener<>((observable, oldValue, newValue) -> scrollPane.setHvalue(0.0)));

		listSearch = FXCollections.observableArrayList();
		listQuestions = FXCollections.observableArrayList();
		this.afc = new AskForQuestionsController();
		this.bgc = BeginnerGraphicChange.getInstance();
		try {
			List<AdvancedUserBean> aub = afc.leaderBoard();
			if (!aub.isEmpty()) {
				ObservableAdTopList observable = new ObservableAdTopList(aub);
				TopRatedPanel tp = new TopRatedPanel(observable, this);
				observable.attach(tp);
				observable.notifyObservers();
			} else {
				this.labelError3.setText("No leaderboard");
			}
			this.topAdvanced.setCellFactory(param -> new ListCell<AdvancedUserBean>() {
				@Override
				protected void updateItem(AdvancedUserBean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setStyle(FXBACK + COLOR + ";");
					} else {
						ImageView iv;
						Label username;
						Label voto;
						VBox vBox;
						String path;
						username = new Label(item.getUsername());
						voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font(FONT, 13));
						voto.setFont(Font.font(FONT, 13));
						username.setStyle(TEXTFILL + TEXTCOLOR + ";");
						voto.setStyle(TEXTFILL + TEXTCOLOR + ";");
						vBox = new VBox(3);
						path = FileManager.PROFILE + File.separator + item.getProfilePic();
						iv = new ImageView(new Image(new File(path).toURI().toString()));
						iv.setFitHeight(150);
						iv.setPreserveRatio(false);
						iv.setFitWidth(150);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle(FXBACK + COLOR + ";");
					}
				}
			});
			listQuestions.removeAll(listQuestions);
			lb = afc.getQuestions(gub, "beginner2"); // Domande in coda
			if (!lb.isEmpty())
				listQuestions.addAll(lb);
			ldb = afc.getQuestions(gub, "beginner");
			if (!ldb.isEmpty())
				listQuestions.addAll(ldb);
			if (lb.isEmpty() & ldb.isEmpty())
				this.labelError2.setText("No questions");
			this.questions.getItems().addAll(listQuestions);
			setCells(ldb, lb);
		} catch (AdvancedNotFoundException e) {
			this.labelError3.setText(e.getMessage());
		} catch (SQLException | ClassNotFoundException | IOException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
	}

	private void setCells(List<DomandaBean> ldb, List<DomandaBean> lb) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		this.questions.setCellFactory(param -> new ListCell<DomandaBean>() {
			@Override
			protected void updateItem(DomandaBean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setStyle(FXBACK + COLOR + ";");
				} else {
					String path;
					ImageView iv;
					Label label;
					HBox hBox;
					if (lb.contains(item)) {
						path = FileManager.YELLOW;
						iv = new ImageView(new Image(new File(path).toURI().toString()));
						iv.setPreserveRatio(false);
						iv.setFitHeight(50);
						iv.setFitWidth(50);
						label = new Label(item.getContenuto());
						label.setFont(Font.font(FONT, 15));
						hBox = new HBox(2);
						hBox.getChildren().addAll(iv, label);
						hBox.setAlignment(Pos.CENTER_LEFT);
						setGraphic(hBox);
						setStyle(FXBACK + COLOR + ";");
					} else if (ldb.contains(item)) {
						controlAnswer(item, gub);
					}
				}
			}

			private void controlAnswer(DomandaBean item, GeneralUserBean gub) {
				String path;
				ImageView iv;
				Label label;
				HBox hBox;
				path = FileManager.MARK;
				boolean j = false;
				try {
					j = afc.checkAnswer(gub.getUsername(), item.getId());
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					LOGGER.log(Level.WARNING, e.toString());
				}
				if (!j) {
					path = FileManager.GREEN;
				}
				hBox = new HBox(2);
				iv = new ImageView(new Image(new File(path).toURI().toString()));
				label = new Label(item.getContenuto());
				label.setFont(Font.font(FONT, 15));
				iv.setFitHeight(50);
				iv.setFitWidth(50);
				iv.setPreserveRatio(false);
				hBox.getChildren().addAll(iv, label);
				hBox.setAlignment(Pos.CENTER_LEFT);
				setGraphic(hBox);
				setStyle(FXBACK + COLOR + ";");
			}
		});
	}
}
