package com.peter.daniel.photosapp;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.UserDatabase;

/**
 * The Class buttonUtility.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class buttonUtility.
 */
public class buttonUtility
{
	/*
	 * creates method for all guis to log out and to safely quit
	 */
	
	/**
	 * edit to login screen when done.
	 *
	 * @param stage the stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void logOut(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(buttonUtility.class.getResource("/login/login.fxml"));
		Parent root = loader.load();
		UserDatabase.saveUsernames();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	/**
	 * saves list of usernames.
	 *
	 * @param stage the stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void quit(Stage stage) throws IOException
	{
		UserDatabase.saveUsernames();
		stage.close();
	}
	
	
}
