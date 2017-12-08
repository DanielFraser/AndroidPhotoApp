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
	
	/** The date created. */
	private Date dateCreated;

	/** The tags. */
	private HashMap<String, String> tags = new HashMap<>();
	
	/** The id. */
	private int id;
	
	/**
	 * Instantiates a new photo.
	 *
	 * @param initLocation the init location
	 * @param date the date
	 * @param userID the user ID
	 */
	public Photo(String initLocation, Date date, int userID)
	{
		id = userID;
		location = initLocation;
		dateCreated = date;
	}
	
	/**
	 * Checks for tag.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean hasTag(String key)
	{
		return tags.containsKey(key);
	}
	
	/**
	 * Checks for tag.
	 *
	 * @param key the key
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean hasTag(String key, String value)
	{
		if(key.equals("*"))
			return tags.containsValue(value);
		if(hasTag(key))
			return tags.get(key).equalsIgnoreCase(value);
		return false;
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addTag(String key, String value) 
	{
		if(!tags.containsKey(key))
			tags.put(key, value);
		else
		{

		}
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
	 * Removes the tag.
	 *
	 * @param key the key
	 */
	public void removeTag(String key)
	{
		tags.remove(key);
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
	 * Prints the tags.
	 *
	 * @return the string
	 */
	public String printTags()
	{
		String s = "";
		for (Entry<String,String> pair : tags.entrySet())
		{
			  s += pair.getKey()+": "+pair.getValue() + "\n";
		}
		return s;
	}

	/**
	 * Gets the date.
	 *
	 * @return the created
	 */
	public Date getDate()
	{
		return dateCreated;
	}
	
	/**
	 * Gets the date S.
	 *
	 * @return the date S
	 */
	public String getDateS()
	{
        return dateCreated.toString();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the tags.
	 *
	 * @param hm the hm
	 */
	public void setTags(HashMap<String, String> hm)
	{
		tags = hm;
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public HashMap<String, String> getTags()
	{
		return tags;
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
}
