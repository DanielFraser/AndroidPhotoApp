package com.peter.daniel.photosapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import nonadmin.AlbumController;
import users.Album;
import users.Photo;
import users.User;
import utility.buttonUtility;
import utility.customLabel;

/**
 * The Class SearchController.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class SearchController.
 */
public class SearchController 
{
	
	/** The logout. */
	@FXML
	private Button logout;

	/** The quit. */
	@FXML
	private Button quit;
	
	/** The album display. */
	@FXML
	ImageView photoDisplay;
	
	/** The add photo. */
	@FXML
	private Button addPhoto;

	/** The quit. */
	@FXML
	private Button home;
	
	/** The username. */
	@FXML
	private Label username;
	
	/** The album display pane. */
	@FXML
	private ScrollPane searchDisplayPane;
	
	/** The album name. */
	@FXML
	private TextArea caption;
	
	/** The num photos. */
	@FXML
	private Label date;
	
	/** The date created. */
	@FXML
	private Label tags2;
	
	/** The cur user. */
	private User curUser;

	/** The tile pane. */
	private TilePane tilePane;
	
	/** The dp T. */
	@FXML
	private DatePicker dpT;
	
	/** The dp F. */
	@FXML
	private DatePicker dpF;
	
	/** The tags. */
	@FXML
	private TextField tags;
	
	/** The photos. */
	private ArrayList<Integer> newAlbum = new ArrayList<>();

	/** The edit capt. */
	@FXML
	private Button editCapt;
	
	/** The id. */
	private int id;

	/** The prev P. */
	private int prevP;

	/** The next P. */
	private int nextP;

	/** The edit cap. */
	private boolean editCap = true;

	/** The old text. */
	private String oldText;
	
	/** The lbl caption. */
	@FXML
	private Label lblCaption;
	/**
	 * Start.
	 *
	 * @param stage the stage
	 * @param currentUser the current user
	 */
	public void start(Stage stage, User currentUser) {
		curUser = currentUser;
		createTilePane();
		//fillScrollPane();
		username.setText(currentUser.getUserName());
		caption.setOnKeyPressed(new EventHandler<KeyEvent>() {  
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					caption.setEditable(false);
					editCap = true;
					lblCaption.setText(oldText);
					caption.setVisible(false);
					lblCaption.setVisible(true);
					editCapt.setText("Edit Caption");
					fillScrollPane(curUser.getPhoto(newAlbum));
					setInfo(curUser.getPhoto(id));
					caption.setText("");
				}
			}
		});
	}
	
	/**
	 * Creates the tile pane.
	 *
	 * @return the v box
	 */
	private VBox createTilePane()
	{
		VBox vbox = new VBox(20);

		tilePane = new TilePane();
		tilePane.setHgap(10); //horizontal gap
		tilePane.setVgap(10); //vertical gap
		tilePane.setPadding(new Insets(10)); //padding
		vbox.getChildren().addAll(tilePane);
		VBox.setMargin(vbox, new Insets(10));

		return vbox;
	}

	/**
	 * Fill scroll pane.
	 *
	 * @param photos the photos
	 */
	private void fillScrollPane(ArrayList<Photo> photos)
	{
		VBox vb = createTilePane();
		int imageSize = 128;
		tilePane.getChildren().clear();
		newAlbum.clear();
		for (Photo p : photos)
		{
			customLabel bt2 = new customLabel(p.getCaption(), p.getId());                        
			Image img2 = new Image(p.getLocation(), imageSize, 0, true, false);
			ImageView view2 = new ImageView(img2);
			bt2.setGraphic(view2);
			bt2.setContentDisplay(ContentDisplay.TOP);
			bt2.addEventHandler(MouseEvent.MOUSE_CLICKED, new clickPhoto());
			bt2.setWrapText(true);
			tilePane.getChildren().add(bt2);
			newAlbum.add(p.getId());
		}

		searchDisplayPane.setFitToWidth(true); //prevent horizontal scrolling
		searchDisplayPane.setContent(vb); //add images to scrollpane
	}
	
	/**
	 * in debugging mode.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void quitAct(ActionEvent e) throws IOException
	{
		Stage stage = (Stage) quit.getScene().getWindow();
		buttonUtility.quit(stage);
	}

	/**
	 * in debugging mode.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void logOutAct(ActionEvent e) throws IOException
	{
		Stage stage = (Stage) quit.getScene().getWindow();
		buttonUtility.logOut(stage);
	}

	/**
	 * Sets the info.
	 *
	 * @param p the new info
	 */
	private void setInfo(Photo p)
	{
		id = p.getId();
		photoDisplay.setImage(new Image(p.getLocation(),128, 0, true, false));
		tags2.setText(p.printTags());
		lblCaption.setText(p.getCaption());
		date.setText(p.getDateS());
		int length = newAlbum.size();
		int i = newAlbum.indexOf(p.getId());
		prevP = -1;
		nextP = -1;
		if(i > 0)
			prevP = i-1;
		if(i < length - 1)
			nextP = i+1;
	}
	
	/**
	 * The Class clickAlbum.
	 */
	private class clickPhoto implements EventHandler<MouseEvent>{

		/* (non-Javadoc)
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(MouseEvent event) {
			customLabel lbl = (customLabel) event.getSource();
			setInfo(curUser.getPhoto(lbl.getIdI()));
		}

	}
	
	/**
	 * Search.
	 *
	 * @param e the e
	 */
	@FXML
	private void search(ActionEvent e)
	{
		if(dpT.getValue() != null && dpF.getValue()!= null)
		{
			fillScrollPane(curUser.search(dpF.getValue(), dpT.getValue()));
		}
		else if(!tags.getText().isEmpty())
		{
			ArrayList<Pair<String, String>> photos = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(tags.getText(), ",");
			StringTokenizer st2;
			while(st.hasMoreTokens())
			{
				st2 = new StringTokenizer(st.nextToken(), ":");
				String s1 = st2.nextToken().trim(), s2 = st2.nextToken().trim();
				if(!(s1.equals("*") && s2.equals("*")))
					photos.add(new Pair<String, String>(s1, s2));
			}
			fillScrollPane(curUser.search(photos));
		}
		else
		{
			fillScrollPane(new ArrayList<>());
			System.out.println("Error!");
		}
	}
	
	/**
	 * Home.
	 *
	 * @param e the e
	 */
	@FXML
	private void home(ActionEvent e)
	{
		System.out.println("home");
		Stage stage = (Stage) quit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/nonadmin/album.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		AlbumController controller = loader.getController();
		controller.start(stage, curUser.getUserName());

		stage.setScene(new Scene(root));
		stage.show();
	}
	
	/**
	 * To album.
	 *
	 * @param e the e
	 */
	@FXML
	private void toAlbum(ActionEvent e)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("New album name");
		dialog.setContentText("Enter name for album:");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent())
		{
			boolean b = curUser.sameName(result.get());
			if(!b)
			{
				curUser.addAlbum(new Album(result.get(), newAlbum));
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setContentText("Album name is already in use, please select a different name!");
				
				alert.showAndWait();
			}
		}
		
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param e the e
	 */
	@FXML
	private void addTag(ActionEvent e)
	{
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("add Tag");

		ButtonType addTBtn = new ButtonType("Add tag", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addTBtn, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField type = new TextField();
		type.setPromptText("Enter type");
		TextField value = new TextField();
		value.setPromptText("Enter value");

		grid.add(new Label("Enter type:"), 0, 0);
		grid.add(type, 1, 0);
		grid.add(new Label("Enter value:"), 0, 1);
		grid.add(value, 1, 1);

		Node loginButton = dialog.getDialogPane().lookupButton(addTBtn);
		loginButton.setDisable(true);

		type.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addTBtn) {
				return new Pair<>(type.getText(), value.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(valueAndType -> {
			curUser.getPhoto(id).addTag(valueAndType.getKey(), valueAndType.getValue());
			fillScrollPane(curUser.getPhoto(newAlbum));
			setInfo(curUser.getPhoto(id));
		});
	}
	
	/**
	 * Rm tag.
	 *
	 * @param e the e
	 */
	@FXML
	private void rmTag(ActionEvent e)
	{
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Remove Tag(s)");

		ButtonType rmBtn = new ButtonType("Remove tag", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(rmBtn, new ButtonType("Done", ButtonData.CANCEL_CLOSE));

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		Map<String, String> map = curUser.getPhoto(id).getTags();

		TableColumn<Entry<String, String>, String> value = new TableColumn<Entry<String, String>, String>("Value");
		value.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Entry<String, String>, String> p) {
				return new SimpleStringProperty(p.getValue().getValue());
			}
		});
		TableColumn<Entry<String, String>, String> type = new TableColumn<Entry<String, String>, String>("Type");
		type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Entry<String, String>, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Entry<String, String>, String> p) {
				return new SimpleStringProperty(p.getValue().getKey());
			}
		});

		ObservableList<Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
		final TableView<Entry<String,String>> tags = new TableView<>(items);
		tags.setEditable(false);
		tags.getColumns().setAll(value, type);

		grid.add(tags, 0, 0);

		Node rmT = dialog.getDialogPane().lookupButton(rmBtn);
		rmT.disableProperty().bind(Bindings.isEmpty(tags.getSelectionModel().getSelectedItems()));
		rmT.addEventFilter(ActionEvent.ACTION, (event) -> {
			Photo p = curUser.getPhoto(id);
			Entry<String, String> temp = tags.getSelectionModel().getSelectedItem();
			p.removeTag(temp.getKey(), temp.getValue());
			items.remove(temp);
			event.consume(); 
		}); 
		
		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == rmBtn) {
				Photo p = curUser.getPhoto(id);
				Entry<String, String> temp = tags.getSelectionModel().getSelectedItem();
				p.removeTag(temp.getKey(), temp.getValue());
			}
			return null;
		});

		dialog.showAndWait();
	}
	
	/**
	 * Edits the caption.
	 *
	 * @param e the e
	 */
	@FXML
	private void editCaption(ActionEvent e)
	{
		if(editCap)
		{
			caption.setEditable(true);
			editCap = !editCap;
			editCapt.setText("Save caption");
			oldText = lblCaption.getText();
			caption.setVisible(true);
			lblCaption.setVisible(false);
			caption.setText(lblCaption.getText());
		}
		else
		{
			caption.setVisible(false);
			lblCaption.setVisible(true);
			curUser.getPhoto(id).addCaption(caption.getText());
			editCap = !editCap;
			lblCaption.setText(caption.getText());
			editCapt.setText("Edit Caption");
			fillScrollPane(curUser.getPhoto(newAlbum));
			setInfo(curUser.getPhoto(id));
		}
	}
	
	/**
	 * Next photo.
	 *
	 * @param e the e
	 */
	@FXML
	private void nextPhoto(ActionEvent e)
	{
		if(nextP != -1)
			setInfo(curUser.getPhoto(newAlbum.get(nextP)));
	}

	/**
	 * Prev photo.
	 *
	 * @param e the e
	 */
	@FXML
	private void prevPhoto(ActionEvent e)
	{
		if(prevP != -1)
			setInfo(curUser.getPhoto(newAlbum.get(prevP)));
	}
	
	/**
	 * Delete.
	 *
	 * @param e the e
	 */
	@FXML
	private void delete(ActionEvent e)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setContentText("Are you sure you want to delete this photo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			newAlbum.remove(new Integer(id));
			fillScrollPane(curUser.getPhoto(newAlbum));
		}
	}
	
	/**
	 * Copy.
	 *
	 * @param e the e
	 */
	@FXML
	private void copy(ActionEvent e)
	{
		ArrayList<String> choices = curUser.getAlbumNames();
		//choices.remove(newAlbum.getName());

		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Copy Photo");
		dialog.setContentText("Choose album:");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent())
		{
			Album a = curUser.getAlbum(result.get());
			a.addPhoto(id);
		}
	}
}
