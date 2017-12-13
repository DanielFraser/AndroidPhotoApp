package com.peter.daniel.photosapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 * The Class User.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * each user has 0 or more albums
 * each user can will search its own list of albums given a name.
 * @author Daniel Fraser
 */
public class User implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4353768237255205291L;

	/** The albums. */
	private static ArrayList<Album> albums = new ArrayList<>();

	/** The user photos. */
	private static ArrayList<Photo> userPhotos = new ArrayList<>();

	public static ArrayList<Photo> temp;

	/** The id. */
	private static int id = 0;

	private static Context con;

	public static Stack<Intent> parents = new Stack<android.content.Intent>();

	public static void setCon(Context con2)
	{
		con = con2;
	}

	/**
	 * Save Albums and Photos.
	 */
	public static void saveAll()
	{
		savePhotos();
		saveAlbums();
		saveID();
	}

	/**
	 * Load Albums and Photos.
	 */
	public static void loadAll()
	{
		loadPhotos();
		loadAlbums();
		loadID();
	}

	/**
	 * Save Albums.
	 */
	public static void saveID()
	{
		try
		{
			//Saving of object in a file
			int temp = id;
			FileOutputStream file = con.openFileOutput("id.ser", Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(temp);

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
	public static void loadID()
	{
		try
		{
			// Reading the object from a file
			FileInputStream file = con.openFileInput("id.ser");
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			id = (Integer) in.readObject();

			in.close();
			file.close();

			//System.out.println("usernames has been deserialized");
		}
		catch(FileNotFoundException e)
		{
			Toast.makeText(con, "No files?", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Save Albums.
	 */
	public static void savePhotos()
	{
		try
		{
			//Saving of object in a file
			ArrayList<Photo> temp = userPhotos;
			FileOutputStream file = con.openFileOutput("photos.ser", Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(temp);

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
	public static void loadPhotos()
	{
		try
		{
			// Reading the object from a file
			FileInputStream file = con.openFileInput("photos.ser");
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			userPhotos = (ArrayList<Photo>) in.readObject();

			in.close();
			file.close();

			//System.out.println("usernames has been deserialized");
		}
		catch(FileNotFoundException e)
		{
			Toast.makeText(con, "No files?", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Save Albums.
	 */
	public static void saveAlbums()
	{
		try
		{
			//Saving of object in a file
			FileOutputStream file = con.openFileOutput("albums.ser",Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(albums);

			out.close();
			file.close();
		}
		catch(Exception e)
		{
			Log.d("error:", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Load usernames.
	 */
	@SuppressWarnings("unchecked")
	public static void loadAlbums()
	{
		try
		{
			// Reading the object from a file
			FileInputStream file = con.openFileInput("albums.ser");
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			albums = (ArrayList<Album>) in.readObject();

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
	 * Delete album.
	 *
	 * @param name the name
	 */
	public static void deleteAlbum(String name)
	{
		Iterator itr = albums.iterator();
		Album a;
		while(itr.hasNext())
		{
			a = (Album) itr.next();
			if(a.getName().equalsIgnoreCase(name))
			{
				itr.remove();
			}

		}
	}

	/**
	 * Adds the album.
	 *
	 * @param name the name
	 */
	public static boolean addAlbum(String name)
	{
		boolean canAdd = true;
		for(Album a : albums)
		{
			if(a.getName().equalsIgnoreCase(name))
				canAdd = false;
		}
		if(canAdd)
			albums.add(new Album(name));
		else
			return false;
		return true;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param location the location
	 * @return the photo
	 */
	public static int getPhoto(String location)
	{
		for(Photo p : userPhotos)
		{
			Log.d("User", "getPhoto: " + p.getLocation() + " " + location + " " + p.getLocation().equalsIgnoreCase(location));
			if(p.getLocation().equalsIgnoreCase(location))
				return p.getId();
		}
		Log.d("User", "getPhoto: " + -1);
		return -1;
	}
	
	/**
	 * Adds the photo.
	 *
	 * @param s the s
	 * @return the int
	 */
	public static int addPhoto(String s)
	{
		if(getPhoto(s) == -1)
		{
			Photo p = new Photo(s, id);
			id++;
			Log.d("debug3", String.valueOf(id));
			userPhotos.add(p);
			return p.getId();
		}
		Log.d("debugw", String.valueOf(getPhoto(s)));
		return getPhoto(s);
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param photoInt the photo int
	 * @return the photo
	 */
	public static Photo getPhoto(Integer photoInt)
	{
		for(int i = 0; i < userPhotos.size(); i++)
		{
			if(userPhotos.get(i).getId() == photoInt)
				return userPhotos.get(i);
		}
		return null;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param name the album name
	 * @return the photo
	 */
	public static ArrayList<Photo> getPhotos(String name)
	{
		Album album = getAlbum(name);
		ArrayList<Photo> photos = new ArrayList<>();
		for(int i : album.getPhotos())
			photos.add(getPhoto(i));
		temp = photos;
		return photos;
	}
	
	/**
	 * Same name.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean sameName(String name)
	{
		for(Album a : albums)
		{
			if(a.getName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	/**
	 * Sets the album name.
	 *
	 * @param name the name
	 * @param album the album
	 * @return true, if successful
	 */
	public static boolean setAlbumName(String name, String album)
	{
		if(name.equalsIgnoreCase(album))
			return true;
		for(int i = 0; i < albums.size(); i++)
		{
			if(!sameName(name) && albums.get(i).getName().equalsIgnoreCase(album))
			{
				albums.get(i).setName(name);
				return true;
			}
			Log.e("album:",albums.get(i).getName());
		}
		return false;
	}
	
	/**
	 * Gets the album.
	 *
	 * @param name the name
	 * @return the album
	 */
	public static Album getAlbum(String name)
	{
		for(Album a : albums)
		{
			if(a.getName().equalsIgnoreCase(name))
			{
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Gets the album names.
	 *
	 * @return the album names
	 */
	public static ArrayList<String> getAlbumNames()
	{
		ArrayList<String> s = new ArrayList<>();
		for(Album a : albums)
			s.add(a.getName());
		Collections.sort(s, String.CASE_INSENSITIVE_ORDER);
		return s;
	}

	
	/**
	 * Search.
	 *
	 * @param location the location
	 * @param person the person
	 * @return the array list
	 */
	public static ArrayList<Photo> search(String location, String person)
	{
		ArrayList<Photo> match = new ArrayList<>();
		boolean hasAll = true;
		for(Photo p : userPhotos)
		{
			hasAll = true;
			if(!location.equals(""))
				if(!p.getLocationTag().contains(location))
					hasAll = false;
			if(!person.equals(""))
				if(!p.getPersonTag().contains(person))
					hasAll = false;
			if(hasAll)
				match.add(p);
		}
		return match;
	}

}
