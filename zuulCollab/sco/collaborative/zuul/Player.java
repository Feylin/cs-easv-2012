package sco.collaborative.zuul;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds information about the player, including the current room the
 * player is in, visited rooms, the name of the player and which items the
 * player is carrying.
 * 
 * @author Ida, Kim, Mak & Stefan
 * 
 */
public class Player {

	private Room currentRoom;
	private String name;
	private List<Item> playerItems;
	private List<Room> visitedRooms;

	/**
	 * Construct a player.
	 * 
	 * @param name name of the player.
	 */
	public Player( String name ) {
		this.name = name;
		playerItems = new ArrayList<>();
		visitedRooms = new ArrayList<>();
	}

	/**
	 * @return the name of the player.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return a string detailing the items the player is carrying.
	 */
	public String getItemString(){
		if( playerItems.size() > 0 ){
			String str = "Du har ";
			for( Item item : playerItems )
				str += item.getId() + ", ";
			str = str.replaceAll( ",\\s$", "" ).replaceAll( ",(?!.*,)", " og" ) + ".";
			return str;
		}
		return "Du ejer intet. Du er fattig.";
	}

	/**
	 * @return the room the player is in.
	 */
	public Room getRoom(){
		return currentRoom;
	}

	/**
	 * Move the player to another room.
	 * 
	 * @param command command including the direction to move in.
	 */
	public void goTo( Command command ){
		// Print an error message if the user didn't specify a direction
		if( !command.hasSecondWord() ){
			System.out.println( "Gå hvor?" );
			return;
		}

		// Store the direction in a string
		String direction = command.getSecondWord();

		// Try to move to the next room
		try{
			Room nextRoom = currentRoom.getExit( direction );
			Item required = nextRoom.getRequirement();
			//			if( nextRoom == null ){
			//				System.out.println( "Der er ikke noget der." );
			//			}else{
			if( required == null || playerItems.contains( required ) ){
				setRoom( nextRoom );
				visitedRooms.add( currentRoom );
			}else{
				System.out.println( nextRoom.getDeniedDesc() );
				//			}

			}
		}
		// If there's nothing in the direction specified, print an errorstring
		catch( NullPointerException e ){
			System.out.println( "Der er ikke noget der!" );
			return;
		}
	}

	/**
	 * @return a list of visited rooms.
	 */
	public List<Room> getVisitedRooms(){
		return visitedRooms;
	}

	/**
	 * Test if the player is carrying a specified item.
	 * 
	 * @param item item to check for
	 * @return true if the player is carrying the item
	 */
	public boolean hasItem( Item item ){
		return playerItems.contains( item );
	}

	/**
	 * Make the player pick an item up.
	 * 
	 * @param command command containing the item to pick up.
	 */
	public void pickUp( Command command ){
		// If there's nothing to pick up, print an errorstring
		if( !command.hasSecondWord() ){
			System.out.println( "Tag hvad?" );
			return;
		}
		// Make an Item variable to store the matched item, if any
		Item item = null;
		// If the room contains the item, store it in the item variable
		for( Item i : getRoom().getItems() ){
			if( i.getId().equals( command.getSecondWord() ) ){
				item = i;
			}
		}

		// Store the required item in a variable
		Item required = item.getRequirement();
		// If the item doesn't require anything, or if the player is carrying the required item...
		if( required == null || playerItems.contains( required ) ){
			currentRoom.remove( item );
			playerItems.add( item );
			// ..else print the error message.
		}else System.out.println( item.getDeniedDesc() );
	}

	/**
	 * Put the player into a specific room
	 * 
	 * @param room room to put the player in
	 */
	public void setRoom( Room room ){
		currentRoom = room;
	}
}
