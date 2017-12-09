package com.peter.daniel.photosapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

	/** The id. */
	private static int id = 0;

	private static Context con;

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
	}

	/**
	 * Load Albums and Photos.
	 */
	public static void loadAll()
	{
		loadPhotos();
		loadAlbums();
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
	 * Gets the albums.
	 *
	 * @return the albums
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
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
	 * Adds the photo.
	 *
	 * @param p the p
	 */
	public static void addPhoto(Photo p)
	{
		userPhotos.add(p);
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
			if(p.getLocation().equals(location))
				return p.getId();
		}
		return -1;
	}
	
	/**
	 * Adds the photo.
	 *
	 * @param s the s
	 * @return the int
	 */
	public static int addPhoto(String s, Date ld)
	{
		if(getPhoto(s) == -1)
		{
			Photo p = new Photo(s, ld, id);
			id++;
			userPhotos.add(p);
			return p.getId();
		}
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
		for(Photo p : userPhotos)
		{
			if(p.getId() == photoInt)
				return p;
		}
		return null;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param album the album
	 * @return the photo
	 */
	public static ArrayList<Photo> getPhoto(Album album)
	{
		ArrayList<Photo> photos = new ArrayList<>();
		for(int i : album.getPhotos())
			photos.add(getPhoto(i));
		return photos;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param album the album
	 * @return the photo
	 */
	public static ArrayList<Photo> getPhoto(ArrayList<Integer> album)
	{
		ArrayList<Photo> photos = new ArrayList<>();
		for(int i : album)
			photos.add(getPhoto(i));
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

	public static void setAlbumsTemp(String[] s)
	{
		albums = new ArrayList<>();
		for(String a : s)
		{
			albums.add(new Album(a));
		}
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
	 * Gets the num photos.
	 *
	 * @return the num photos
	 */
	public static int getNumPhotos() {
		return userPhotos.size();
	}
	
	/**
	 * Edits the album.
	 *
	 * @param a the a
	 */
	public static void editAlbum(Album a)
	{
		for(int i = 0; i < albums.size(); i++)
		{
			if(a.getName().equals(albums.get(i).getName()))
			{
				albums.set(i, a);
			}
		}
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
	 * @param tag the tag
	 * @return the array list
	 */
	public static ArrayList<Photo> search(ArrayList<Pair<String, String>> tag)
	{
		ArrayList<Photo> match = new ArrayList<>();
		boolean hasAll = true;
		for(Photo p : userPhotos)
		{
			hasAll = true;
			for(Pair<String, String> t : tag)
			{
				hasAll = hasAll && t.equals(tag);
			}
			if(hasAll)
				match.add(p);
		}
		return match;
	}

}
