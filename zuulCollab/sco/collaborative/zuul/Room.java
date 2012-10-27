package sco.collaborative.zuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 * 
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. For each existing exit, the room
 * stores a reference to the neighboring room.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Room {

	private String description;
	private String roomDescription;
	private Item requiredItem;
	private HashMap<String, Room> exits;        // stores exits of this room.
	private ArrayList<Item> roomItems;
	private String deniedDescription;

	/**
	 * Create a room described "description". Initially, it has
	 * no exits. "description" is something like "a kitchen" or
	 * "an open court yard".
	 * 
	 * @param description The room's description.
	 */
	public Room( String description ) {
		this.description = description;
		exits = new HashMap<String, Room>();
		roomItems = new ArrayList<>();
		requiredItem = null;
		deniedDescription = null;
	}

	/**
	 * Define an exit from this room.
	 * 
	 * @param direction The direction of the exit.
	 * @param neighbor The room to which the exit leads.
	 */
	public void setExit( String direction, Room neighbor ){
		exits.put( direction, neighbor );
	}

	/**
	 * @return The short description of the room
	 *         (the one that was defined in the constructor).
	 */
	public String getShortDescription(){
		return description;
	}

	public String getLongDescription(){
		return String.format( "Du er %s.\n%s\n%s", description, getItemString(), getExitString() );
	}

	public String getRoomDescription(){
		return roomDescription;
	}

	public void setRoomDescription( String description ){
		roomDescription = description;
	}

	public String getItemString(){
		if( roomItems.size() > 0 ){
			String str = "Du kan se ";
			for( Item item : roomItems )
				str += item.getId().toUpperCase() + ", ";
			str = str.replaceAll( ",\\s$", "" ).replaceAll( ",(?!.*,)", " og" ) + ".";
			return str;
		}
		return "Du kan ikke se noget af interesse.";
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: [north] [west]".
	 * 
	 * @return Details of the room's exits.
	 */
	public String getExitString(){
		String returnString = "Udgange:";
		Set<String> keys = exits.keySet();
		for( String exit : keys ){
			returnString += "[" + exit + "]";
		}
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 * 
	 * @param direction The exit's direction.
	 * @return The room in the given direction.
	 */
	public Room getExit( String direction ){
		return exits.get( direction );
	}

	public void add( Item...items ){
		for( Item item : items )
			roomItems.add( item );
	}

	public Item remove( Item item ){
		roomItems.remove( item );
		return item;
	}

	public boolean hasItem( Item item ){
		return roomItems.contains( item );
	}

	public ArrayList<Item> getItems(){
		return roomItems;
	}

	public Item getRequirement(){
		return requiredItem;
	}
	
	public void setRequirement( Item item ){
		requiredItem = item;
	}

	public String getDeniedDesc(){
		return deniedDescription;
	}
	
	public void setDeniedDescription(String description){
		deniedDescription = description;
	}
	
}
