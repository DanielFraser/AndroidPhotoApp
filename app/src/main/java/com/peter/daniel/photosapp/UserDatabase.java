package com.peter.daniel.photosapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import stock.StockUser;

/**
 * The Class UserDatabase.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class UserDatabase.
 */
public class UserDatabase 
{
	
	/** The users. */
	private static ArrayList<User> users = new ArrayList<>(); //list of current loaded users
	
	/**
	 * Save usernames.
	 */
	public static void saveUsernames()
	{
		try
        {   
			//remove stock user
			
			Predicate<User> predicate = c-> c.getUserName().equals("stock");
			boolean hasStock = users.stream().filter(predicate).anyMatch(predicate);
			if(hasStock)
			{
				User user = null;
				for(User u : users)
				{
					if(u.getUserName().equals("stock"))
						user = u;
				}
				users.remove(user);
			}
				
			
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("usernames.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for serialization of object
            out.writeObject(users);
             
            out.close();
            file.close();
            
            StockUser.createStockUser();
            //System.out.println("usernames has been serialized");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	/**
	 * Load usernames.
	 */
	@SuppressWarnings("unchecked")
	public static void loadUserNames()
	{
		try
        {   
            // Reading the object from a file
            FileInputStream file = new FileInputStream("usernames.ser");
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            users = (ArrayList<User>)in.readObject();
             
            in.close();
            file.close();
            
            //System.out.println("usernames has been deserialized");
        }
        catch(FileNotFoundException e)
		{
        	//do nothing
		}
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public static void addUser(User user)
	{
		users.add(user);
	}
	
	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public static ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * Adds the users.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean addUser(String name) 
	{
		boolean exists = false;
		for(User u : users)
		{
			if(u.getUserName().equalsIgnoreCase(name))
				exists = true;
		}
		if(!exists)
			users.add(new User(name));
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Username already exists");
			alert.showAndWait();
		}
		return !exists;
	}
	
	/**
	 * Find user.
	 *
	 * @param name the name
	 * @return the user
	 */
	public static User findUser(String name)
	{
		Predicate<User> predicate = c-> c.getUserName().equals(name);
		for(User u: users)
		{
			if(u.getUserName().equalsIgnoreCase(name))
				return u;
		}
		return null;
	}
	
	/**
	 * Find user B.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean findUserB(String name)
	{
		for(User u: users)
		{
			if(u.getUserName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the usernames.
	 *
	 * @return the usernames
	 */
	public static ArrayList<String> getUsernames()
	{
		ArrayList<String> usernames = new ArrayList<>();
		for(User u : users)
			usernames.add(u.getUserName());
		return usernames;
	}
	
	/**
	 * Delete username.
	 *
	 * @param name the name
	 */
	public static void deleteUsername(String name)
	{
		Predicate<User> predicate = c-> c.getUserName().equals(name);
		User user = users.stream().filter(predicate).findFirst().get();
		users.remove(user);
	}
}
