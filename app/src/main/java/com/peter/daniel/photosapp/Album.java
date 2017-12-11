package com.peter.daniel.photosapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class Album.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class Album.
 */
public class Album implements Serializable 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8132144142964125790L;

	/** The photos. */
	private ArrayList<Integer> photos = new ArrayList<>();
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new album.
	 *
	 * @param initName the init name
	 */
	public Album(String initName) {
		setName(initName);
	}
	
	
	/**
	 * Instantiates a new album.
	 *
	 * @param initName the init name
	 * @param initPhotos the init photos
	 */
	public Album(String initName,  ArrayList<Integer> initPhotos) {
		setName(initName);
		photos = initPhotos;
	}
	
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public ArrayList<Integer> getPhotos() {
		return photos;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) 
	{
		
		this.name = name;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size of photo album
	 */
	public int getSize()
	{
		return photos.size();
	}
	
	/**
	 * Adds the photo.
	 *
	 * @param i the i
	 */
	public void addPhoto(Integer i)
	{
		photos.add(i);
	}
}
