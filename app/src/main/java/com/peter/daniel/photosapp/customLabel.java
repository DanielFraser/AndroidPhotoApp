package com.peter.daniel.photosapp;

import javafx.scene.control.Label;

/**
 * @author Daniel Fraser
 * @author Peter Laskai
 * 
 * The Class customLabel.
 */
public class customLabel extends Label
{
	
	/** The id. */
	private int id;
	
	/**
	 * Instantiates a new custom label.
	 *
	 * @param s the s
	 * @param newId the new id
	 */
	public customLabel(String s, int newId) 
	{
		super(s);
		id = newId;
	}
	
	/**
	 * Gets the id I.
	 *
	 * @return the id I
	 */
	public int getIdI() 
	{
		return id;
	}
}
