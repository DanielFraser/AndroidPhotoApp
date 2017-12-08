package com.peter.daniel.photosapp;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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

	/** The id. */
	private int id = 0;

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
	public void deleteAlbum(String name)
	{
		//TODO fill it in
	}

	/**
	 * Adds the album.
	 *
	 * @param name the name
	 */
	public void addAlbum(String name)
	{
		albums.add(new Album(name));
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
	 * @return the int
	 */
	public int addPhoto(String s, Date ld)
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
				hasAll = hasAll && t.equals(tag);
			}
			if(hasAll)
				match.add(p);
		}
		return match;
	}
	
	/**
	 * Search albums.
	 *
	 * @param name the name
	 * @return the array list
	 *//*
	public ArrayList<Album> searchAlbumsA(String name)
	{
		ArrayList<Album> match = new ArrayList<>();
		for(Album a : albums)
		{
			if(a.getName().contains(name))
				match.add(a);
		}
		return match;
	}*/
}
