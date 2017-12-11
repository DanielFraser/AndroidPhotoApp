package com.peter.daniel.photosapp;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The Class Photo.
 *
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class Photo.
 */
public class Photo implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2788753881453693638L;

	/** The caption. */
	private String location = "", caption;

	/** The tags. */
	private String personTag = "";
	private String locationTag = "";

	/** The id. */
	private int id;
	
	/**
	 * Instantiates a new photo.
	 *
	 * @param initLocation the init location
	 * @param userID the user ID
	 */
	public Photo(String initLocation, int userID)
	{
		id = userID;
		location = initLocation;
	}

	/**
	 * Adds the caption.
	 *
	 * @param initCaption the init caption
	 */
	public void addCaption(String initCaption)
	{
		caption = initCaption;
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		File f = new File(location);
		return f.getName();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Photo))
		{
			return false;
		}
		else
		{
			Photo p = (Photo) obj;
			return p.getLocation().equals(this.location);
		}
	}

	public String getLocationTag() {
		return locationTag;
	}

	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	public String getPersonTag() {
		return personTag;
	}

	public void setPersonTag(String personTag) {
		this.personTag = personTag;
	}
}
