package logic.viewfxml;

import java.io.File;
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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

	@FXML
	private Label home, ask, playlists, profile, labelError1, labelError2, labelError3;
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

	private ObservableAdTopList observable;
	private TopRatedPanel tp;
	private AskForQuestionsController afc;
	private BeginnerGraphicChange bgc;
	int start;
	String color;

	@FXML
	public void onHomeClicked(MouseEvent event) throws IOException {
		this.bgc.toHomepage(this.home.getScene());
	}

	@FXML
	public void onPlaylistsClicked(MouseEvent event) throws IOException {
		this.bgc.toPlaylists(this.playlists.getScene());
	}

	@FXML
	public void onProfileClicked(MouseEvent event) throws IOException {
		this.bgc.toProfile(this.profile.getScene());
	}

	@FXML
	public void onSelectedTopAd(MouseEvent event) throws IOException {
		if (this.observable != null)
			this.bgc.toQuestion(this.topAdvanced.getScene(), topAdvanced.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void onEnterPressed(KeyEvent event) throws SQLException {
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
							setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
						} else {
							String path = FileManager.PROFILE + File.separator + item.getProfilePic();
							File file = new File(path);
							Image img = new Image(file.toURI().toString());
							VBox vBox = new VBox(3);
							ImageView iv = new ImageView(img);
							Label username = new Label(item.getUsername());
							Label voto = new Label(item.getVoto() + "/5.0");
							username.setFont(Font.font("Arial", 13));
							voto.setFont(Font.font("Arial", 13));
							username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
							voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
							iv.setFitHeight(150);
							iv.setFitWidth(150);
							iv.setPreserveRatio(false);
							vBox.setAlignment(Pos.CENTER);
							vBox.getChildren().addAll(iv, username, voto);
							setGraphic(vBox);
							setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
						}
					}
				});
			} catch (AdvancedNotFoundException e) {
				this.labelError1.setText(e.getMessage());
			}
		}
	}

	@FXML
	public void onDirectorPressed(ActionEvent event) throws SQLException {
		this.splitMenu.setText("Director");
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
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onProductorPressed(ActionEvent event) throws SQLException {
		this.splitMenu.setText("Productor");
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
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onActorPressed(ActionEvent event) throws SQLException {
		this.splitMenu.setText("Actor");
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
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onScreenwriterPressed(ActionEvent event) throws SQLException {
		this.splitMenu.setText("Screenwriter");
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
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onFilmeditorPressed(ActionEvent event) throws SQLException {
		this.splitMenu.setText("Film editor");
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
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError1.setText(e.getMessage());
		}
	}

	@FXML
	public void onSelectedSearchAd(MouseEvent event) throws IOException {
		if (!listSearch.isEmpty())
			this.bgc.toQuestion(this.advanced.getScene(), this.advanced.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void onSelectedQuestion(MouseEvent event) throws IOException {
		if (!listQuestions.isEmpty()) {
			GeneralUserBean gub = SessionUser.getInstance().getSession();
			try {
				if (lb.contains(this.questions.getSelectionModel().getSelectedItem())) {
					color = "y";
					this.bgc.toQuestionDetails(this.questions.getScene(),
							this.questions.getSelectionModel().getSelectedItem(), color);
				}
				if (ldb.contains(this.questions.getSelectionModel().getSelectedItem())) {
					if (!this.afc.checkAnswer(gub, this.questions.getSelectionModel().getSelectedItem())) {
						color = "g";
						this.bgc.toQuestionDetails(this.questions.getScene(),
								this.questions.getSelectionModel().getSelectedItem(), color);
					} else {
						color = "m";
						this.bgc.toQuestionDetails(this.questions.getScene(),
								this.questions.getSelectionModel().getSelectedItem(), color);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GeneralUserBean gub = SessionUser.getInstance().getSession();
		listSearch = FXCollections.observableArrayList();
		listQuestions = FXCollections.observableArrayList();
		this.afc = new AskForQuestionsController();
		this.bgc = BeginnerGraphicChange.getInstance();
		try {
			List<AdvancedUserBean> aub = afc.leaderBoard();
			if (aub != null) {
				this.observable = new ObservableAdTopList(aub);
				this.tp = new TopRatedPanel(observable, this);
				this.observable.attach(tp);
				try {
					this.observable.notifyObservers();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				this.labelError3.setText("No leaderboard");
			}
			this.topAdvanced.setCellFactory(param -> new ListCell<AdvancedUserBean>() {
				@Override
				protected void updateItem(AdvancedUserBean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						String path = FileManager.PROFILE + File.separator + item.getProfilePic();
						File file = new File(path);
						Image img = new Image(file.toURI().toString());
						VBox vBox = new VBox(3);
						ImageView iv = new ImageView(img);
						Label username = new Label(item.getUsername());
						Label voto = new Label(item.getVoto() + "/5.0");
						username.setFont(Font.font("Arial", 13));
						voto.setFont(Font.font("Arial", 13));
						username.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						voto.setStyle("-fx-text-fill: " + "#f5c518" + ";");
						iv.setFitHeight(150);
						iv.setFitWidth(150);
						iv.setPreserveRatio(false);
						vBox.setAlignment(Pos.CENTER);
						vBox.getChildren().addAll(iv, username, voto);
						setGraphic(vBox);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					}
				}
			});
			listQuestions.removeAll(listQuestions);
			lb = afc.getQuestions(gub, "beginner2"); // Domande in coda
			if (lb != null)
				listQuestions.addAll(lb);
			ldb = afc.getQuestions(gub, "beginner");
			if (ldb != null)
				listQuestions.addAll(ldb);
			this.questions.getItems().addAll(listQuestions);
			this.questions.setCellFactory(param -> new ListCell<DomandaBean>() {
				@Override
				protected void updateItem(DomandaBean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
					} else {
						if (lb != null && lb.contains(item)) {
							String path = FileManager.YELLOW;
							File file = new File(path);
							Image img = new Image(file.toURI().toString());
							HBox hBox = new HBox(2);
							ImageView iv = new ImageView(img);
							Label label = new Label(item.getContenuto());
							label.setFont(Font.font("Arial", 15));
							iv.setFitHeight(50);
							iv.setFitWidth(50);
							iv.setPreserveRatio(false);
							hBox.getChildren().addAll(iv, label);
							hBox.setAlignment(Pos.CENTER_LEFT);
							setGraphic(hBox);
							setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
						}
						if (ldb != null && ldb.contains(item)) {
							try {
								if (!afc.checkAnswer(gub, item)) {
									String path = FileManager.GREEN;
									File file = new File(path);
									Image img = new Image(file.toURI().toString());
									HBox hBox = new HBox(2);
									ImageView iv = new ImageView(img);
									Label label = new Label(item.getContenuto());
									label.setFont(Font.font("Arial", 15));
									iv.setFitHeight(50);
									iv.setFitWidth(50);
									iv.setPreserveRatio(false);
									hBox.getChildren().addAll(iv, label);
									hBox.setAlignment(Pos.CENTER_LEFT);
									setGraphic(hBox);
									setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
								} else {
									String path = FileManager.MARK;
									File file = new File(path);
									Image img = new Image(file.toURI().toString());
									HBox hBox = new HBox(2);
									ImageView iv = new ImageView(img);
									Label label = new Label(item.getContenuto());
									label.setFont(Font.font("Arial", 15));
									iv.setFitHeight(50);
									iv.setFitWidth(50);
									iv.setPreserveRatio(false);
									hBox.getChildren().addAll(iv, label);
									hBox.setAlignment(Pos.CENTER_LEFT);
									setGraphic(hBox);
									setStyle("-fx-control-inner-background: " + " #1c1c1c" + ";");
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		} catch (AdvancedNotFoundException e) {
			this.labelError3.setText(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
