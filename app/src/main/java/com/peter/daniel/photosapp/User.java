package com.peter.daniel.photosapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

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
	private ArrayList<Album> albums = new ArrayList<>();

	/** The user photos. */
	private ArrayList<Photo> userPhotos = new ArrayList<>();
	
	/** The user name. */
	private String userName;

	/** The id. */
	private int id = 0;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name the name
	 */
	public User(String name) 
	{
		userName = name;
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
	 * Search albums.
	 *
	 * @param name the name
	 * @return the album
	 */
	public Album searchAlbums(String name) {
		Predicate<Album> predicate = c-> c.getName().equals(name);
		Album obj = albums.stream().filter(predicate).findFirst().get();
		return obj;
	}

	/**
	 * Delete album.
	 *
	 * @param name the name
	 */
	public void deleteAlbum(String name)
	{
		Predicate<Album> predicate = c-> c.getName().equals(name);
		Album alb = albums.stream().filter(predicate).findFirst().get();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setContentText("Are you sure you want to delete " + name);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			this.albums.remove(alb);
		} 
		
	}

	/**
	 * Adds the album.
	 *
	 * @param name the name
	 */
	public void addAlbum(String name)
	{
		albums.add(new Album(name, this));
	}
	
	/**
	 * Adds the album.
	 *
	 * @param a the a
	 */
	public void addAlbum(Album a)
	{
		albums.add(a);
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Adds the photo.
	 *
	 * @param p the p
	 */
	public void addPhoto(Photo p)
	{
		userPhotos.add(p);
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param location the location
	 * @return the photo
	 */
	public int getPhoto(String location)
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
	 * @param ld the ld
	 * @return the int
	 */
	public int addPhoto(String s, LocalDateTime ld)
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
	 * Edits the photo.
	 *
	 * @param i the i
	 * @param p the p
	 */
	public void editPhoto(Integer i, Photo p)
	{
		Predicate<Photo> predicate = c-> c.getId() == i;
		Photo photo = userPhotos.stream().filter(predicate).findFirst().get();
		photo.addCaption(p.getCaption());
		photo.setTags(p.getTags());
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param photoInt the photo int
	 * @return the photo
	 */
	public Photo getPhoto(Integer photoInt)
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
	public ArrayList<Photo> getPhoto(Album album)
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
	public ArrayList<Photo> getPhoto(ArrayList<Integer> album)
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
	public boolean sameName(String name)
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
	public boolean setAlbumName(String name, String album)
	{
		for(int i = 0; i < albums.size(); i++)
		{
			if(!sameName(name) && albums.get(i).getName().equals(album))
			{
				albums.get(i).setName(name);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the album.
	 *
	 * @param name the name
	 * @return the album
	 */
	public Album getAlbum(String name)
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
	public int getNumPhotos() {
		return userPhotos.size();
	}
	
	/**
	 * Edits the album.
	 *
	 * @param a the a
	 */
	public void editAlbum(Album a)
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
	public ArrayList<String> getAlbumNames()
	{
		ArrayList<String> s = new ArrayList<>();
		for(Album a : albums)
			s.add(a.getName());
		return s;
	}
	
	/**
	 * Search.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the array list
	 */
	public ArrayList<Photo> search(LocalDate start, LocalDate end)
	{
		ArrayList<Photo> match = new ArrayList<>();
		for(Photo p : userPhotos)
		{
			if(!p.getDate().toLocalDate().isBefore(start) && !p.getDate().toLocalDate().isAfter(end))
			{
				match.add(p);
			}
		}
		return match;
	}
	
	/**
	 * Search.
	 *
	 * @param tag the tag
	 * @return the array list
	 */
	public ArrayList<Photo> search(ArrayList<Pair<String, String>> tag)
	{
		ArrayList<Photo> match = new ArrayList<>();
		boolean hasAll = true;
		for(Photo p : userPhotos)
		{
			hasAll = true;
			for(Pair<String, String> t : tag)
			{
				if(t.getKey().equals("*") && !t.getValue().equals("*"))
					hasAll = hasAll && p.hasTag(t.getKey(), t.getValue());
				else if(!t.getKey().equals("*") && t.getValue().equals("*"))
					hasAll = hasAll && p.hasTag(t.getKey());
				else
					hasAll = hasAll && p.hasTag(t.getKey(), t.getValue());
			}
			if(hasAll)
				match.add(p);
		}
		return match;
	}
	
	/**
	 * Search albums.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the array list
	 */
	public ArrayList<Album> searchAlbums(LocalDate start, LocalDate end)
	{
		ArrayList<Album> match = new ArrayList<>();
		boolean fits = true;
		for(Album a : albums)
		{
			fits = true;
			
			if(start != null)
			{
				fits = fits && !a.getDateCreated().isBefore(start);
			}
			if(end != null)
			{
				fits = fits && !a.getDateCreated().isAfter(end);
			}
			if(fits)
				match.add(a);
		}
		return match;
	}
	
	/**
	 * Search albums.
	 *
	 * @param name the name
	 * @return the array list
	 */
	public ArrayList<Album> searchAlbumsA(String name)
	{
		ArrayList<Album> match = new ArrayList<>();
		for(Album a : albums)
		{
			if(a.getName().contains(name))
				match.add(a);
		}
		return match;
	}

	/**
	 * Search photos.
	 *
	 * @param currentAlbum the current album
	 * @param start the start
	 * @param end the end
	 * @return the array list
	 */
	public ArrayList<Photo> searchPhotos(Album currentAlbum, LocalDate start, LocalDate end) 
	{
		ArrayList<Photo> match = new ArrayList<>();
		boolean fits = true;
		LocalDate p;
		for(Integer i : currentAlbum.getPhotos())
		{
			fits = true;
			p = getPhoto(i).getDate().toLocalDate();
			if(start != null)
			{
				fits = fits && !p.isBefore(start);
			}
			if(end != null)
			{
				fits = fits && !p.isAfter(end);
			}
			if(fits)
				match.add(getPhoto(i));
		}
		return match;
	}
	
	/**
	 * Search photos.
	 *
	 * @param currentAlbum the current album
	 * @param name the name
	 * @return the array list
	 */
	public ArrayList<Photo> searchPhotos(Album currentAlbum, String name) 
	{
		ArrayList<Photo> match = new ArrayList<>();
		Photo p;
		for(Integer i : currentAlbum.getPhotos())
		{
			p = getPhoto(i);
			
			if(p.getCaption().contains(name))
				match.add(getPhoto(i));
		}
		return match;
	}
}
