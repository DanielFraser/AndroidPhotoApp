package com.peter.daniel.photosapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import drawing.drawingController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import photo.PhotoController;
import search.SearchController;
import users.Album;
import users.User;
import users.UserDatabase;
import utility.buttonUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class AlbumController.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class AlbumController.
 */
public class AlbumController
{

	/** The tile pane. */
	private TilePane tilePane;

	/** The album name. */
	/*
	 * fxml objects
	 */
	@FXML
	private Label albumName;

	/** The num photos. */
	@FXML
	private Label numPhotos;

	/** The date created. */
	@FXML
	private Label dateCreated;

	/** The logout. */
	@FXML
	private Button logout;

	/** The quit. */
	@FXML
	private Button quit;

	/** The album display. */
	@FXML
	private ImageView albumDisplay;

	/** The username. */
	@FXML
	private Label username;

	/** The album display pane. */
	@FXML
	private ScrollPane albumDisplayPane;//will contain the thumbnails of photo albums

	/** The delete. */
	@FXML
	private Button delete;

	/** The edit. */
	@FXML
	private Button edit;

	/** The edit. */
	@FXML
	private Button search;
	
	/** The open. */
	@FXML
	private Button open;

	/** The new albumn. */
	@FXML
	private Button newAlbumn;

	/** The current user. */
	private User currentUser;
	
	/** The to. */
	@FXML
	private DatePicker to;
	
	/** The from. */
	@FXML
	private DatePicker from;
	
	/** The name. */
	@FXML
	private TextField name;
	
	/**
	 * sets properties to buttons and a few of the labels
	 * loads list from file.
	 *
	 * @param mainStage the main stage
	 * @param user the user
	 */
	public void start(Stage mainStage, String user) { // Initialization of FXapp
		currentUser = UserDatabase.findUser(user);
		VBox vb = createTilePane(); //will hold images
		fillScrollPane(currentUser.getAlbums()); //add images
		albumDisplayPane.setFitToWidth(true); //prevent horizontal scrolling
		albumDisplayPane.setContent(vb); //add images to scrollpane
		username.setText(user); //debug
		from.valueProperty().addListener(new ChangeListener<LocalDate>() {
	        @Override
	        public void changed(
	                ObservableValue<? extends LocalDate> observableValue,
	                LocalDate oldValue, LocalDate newValue) {
	            fillScrollPane(currentUser.searchAlbums(newValue, to.getValue()));  
	        }
	    });
		to.valueProperty().addListener(new ChangeListener<LocalDate>() {
	        @Override
	        public void changed(
	                ObservableValue<? extends LocalDate> observableValue,
	                LocalDate oldValue, LocalDate newValue) {
	            fillScrollPane(currentUser.searchAlbums(from.getValue(),newValue));  
	        }
	    });
		name.textProperty().addListener((observable, oldValue, newValue) -> {
			fillScrollPane(currentUser.searchAlbumsA(newValue));
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
	 * @param albums the albums
	 */
	private void fillScrollPane(ArrayList<Album> albums)
	{
		int imageSize = 64;
		tilePane.getChildren().clear();
		for (Album a : albums)
		{
			if(a.getSize() > 0)
			{
				Label bt2 = new Label();                        
				Image img2 = new Image(currentUser.getPhoto(a.getPhotos().get(0)).getLocation(),imageSize, 0, true, false);
				ImageView view2 = new ImageView(img2);
				bt2.setGraphic(view2);
				bt2.setText(a.getName());
				bt2.setContentDisplay(ContentDisplay.TOP);
				bt2.addEventHandler(MouseEvent.MOUSE_CLICKED, new clickAlbum());
				tilePane.getChildren().add(bt2);  
			}
			else
			{
				Label bt2 = new Label();                        
				Image img2 = new Image("/nonadmin/folder.png",64, 0, true, false);
				ImageView view2 = new ImageView(img2);
				bt2.setGraphic(view2);
				bt2.setText(a.getName());
				bt2.setContentDisplay(ContentDisplay.TOP);
				bt2.addEventHandler(MouseEvent.MOUSE_CLICKED, new clickAlbum());
				tilePane.getChildren().add(bt2);  
			}

		}
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
		Stage stage = (Stage) albumDisplay.getScene().getWindow();
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
		Stage stage = (Stage) albumDisplay.getScene().getWindow();
		buttonUtility.logOut(stage);
	}

	/**
	 * The Class clickAlbum.
	 */
	private class clickAlbum implements EventHandler<MouseEvent>{

		/* (non-Javadoc)
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(MouseEvent event) {
			Label lbl = (Label) event.getSource();
			albumName.setText(lbl.getText());
			Album a = currentUser.getAlbum(albumName.getText());
			if(a.getSize() > 0)
			{
				albumDisplay.setImage( new Image(currentUser.getPhoto(a).get(0).getLocation(),128, 0, true, false));
			}
			else
			{
				albumDisplay.setImage( new Image("/nonadmin/folder.png",128, 0, true, false));
			}
			
			albumDisplay.setPreserveRatio(true);
			if(event.getClickCount() == 2)
			{
				Stage stage = (Stage) quit.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/photo/photo.fxml"));
				Parent root = null;
				try {
					root = loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				PhotoController controller = loader.getController();
				controller.start(stage, currentUser.searchAlbums(lbl.getText()), currentUser);

				stage.setScene(new Scene(root));
				stage.show();
			}
		}
	}


	/**
	 * Delete album.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void deleteAlbum(ActionEvent e) throws IOException
	{
		currentUser.deleteAlbum(albumName.getText());
		fillScrollPane(currentUser.getAlbums());
	}


	/**
	 * Edits the album.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void editAlbum(ActionEvent e) throws IOException
	{

		TextInputDialog dialog = new TextInputDialog(albumName.getText());
		dialog.setTitle("Edit album name");
		dialog.setContentText("Enter new name for album:");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent())
		{
			boolean b = currentUser.setAlbumName(result.get(), albumName.getText());
			if(b)
			{
				
				albumName.setText(result.get());
				fillScrollPane(currentUser.getAlbums());
			}
		}
	}


	/**
	 * View album.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void viewAlbum(ActionEvent e) throws IOException
	{
		Stage stage = (Stage) quit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo/photo.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		PhotoController controller = loader.getController();
		controller.start(stage, currentUser.searchAlbums(albumName.getText()), currentUser);

		stage.setScene(new Scene(root));
		stage.show();
	}

	/**
	 * View album.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException 
	 */
	@FXML
	private void addAlbum(ActionEvent e) throws IOException, InterruptedException
	{
		TextInputDialog dialog = new TextInputDialog(albumName.getText());
		dialog.setTitle("Edit album name");
		dialog.setContentText("Enter name for album:");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent())
		{
			boolean b = currentUser.sameName(result.get());
			if(!b)
			{
				currentUser.addAlbum(result.get());
				Label bt2 = new Label(result.get());                        
				Image img2 = new Image("/nonadmin/folder.png",64, 0, true, false);
				ImageView view2 = new ImageView(img2);
				bt2.setGraphic(view2);
				bt2.setContentDisplay(ContentDisplay.TOP);
				bt2.addEventHandler(MouseEvent.MOUSE_CLICKED, new clickAlbum());
				tilePane.getChildren().add(bt2);  
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setContentText("Sorry, album name already taken, please select another");
				alert.show();
			}
		}
	}
	
	/**
	 * Search.
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void search(ActionEvent e) throws IOException
	{
		Stage stage = (Stage) quit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/search/search.fxml"));
		Parent root = loader.load();

		SearchController controller = loader.getController();
		controller.start(stage, currentUser);

		stage.setScene(new Scene(root));
		stage.show();
	}
	
	/**
	 * Draw photo.
	 *
	 * @param e the e
	 * @throws IOException 
	 */
	@FXML
	private void drawPhoto(ActionEvent e) throws IOException
	{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/drawing/drawing.fxml"));
		Parent root = loader.load();

		drawingController controller = loader.getController();
		controller.start(stage);

		stage.setScene(new Scene(root));
		stage.show();
	}
}
