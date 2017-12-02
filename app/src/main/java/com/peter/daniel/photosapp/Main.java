package com.peter.daniel.photosapp;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import login.LoginController;
import stock.StockUser;
import users.UserDatabase;

/**
 * The Class Main.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class Main.
 */
public class Main extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		UserDatabase.loadUserNames(); //load user names
		StockUser.createStockUser();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/login/login.fxml"));
		AnchorPane root = loader.load();
		
		LoginController controller = loader.getController();
		controller.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		    	//List<Consultation> showing = provider.getItems();
		    	//controller.saveList();
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	/*public static void main(String[] args) {
		launch(args);
	}*/
}
