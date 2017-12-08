package com.peter.daniel.photosapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	private static User user = new User(); //list of current loaded users
	
	/**
	 * Save usernames.
	 */
	public static void saveUsernames()
	{
		try
        {   
			//Saving of object in a file
            FileOutputStream file = new FileOutputStream("user.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for serialization of object
            out.writeObject(user);
             
            out.close();
            file.close();
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
            FileInputStream file = new FileInputStream("user.ser");
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            user = ((User)in.readObject());
             
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
}
