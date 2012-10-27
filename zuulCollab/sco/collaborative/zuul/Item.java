package sco.collaborative.zuul;

/**
 * This class represents an item in the "World of Zuul" game.
 * An item has an id-String with a simple (and preferably unique) description
 * like "a gun" or "a pack of gum"
 * 
 * @author Ida, Kim, Mak & Stefan
 * 
 */
public class Item {

	private String id;
	private String description;
	private String deniedDescription;
	private Item requiredItem;

	/**
	 * Constuct an item.
	 * 
	 * @param id should be something like "a key" or "the dog".
	 */
	public Item( String id ) {
		setId( id );
		description = null;
		deniedDescription = null;
		requiredItem = null;
	}

	/**
	 * Set the id of the item.
	 * 
	 * @param id new id to set
	 */
	public void setId( String id ){
		this.id = id;
	}

	/**
	 * @return the id of the item
	 */
	public String getId(){
		return id;
	}

	/**
	 * @return the description of the item
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Set a description for the item. Can be used with a future
	 * 'inspect'-command to get more info about the item
	 * 
	 * @param description a string describing the item in greater detail
	 */
	public void setDescription( String description ){
		this.description = description;
	}

	/**
	 * @return the requirement for the item
	 */
	public Item getRequirement(){
		return requiredItem;
	}

	/**
	 * Set an item to be required before the player can pick this item up.
	 * 
	 * @param item required item
	 */
	public void setRequirement( Item item ){
		requiredItem = item;
	}

	/**
	 * @return a String with an error message in case the player doesn't have
	 *         the required item to pic kthis item up.
	 */
	public String getDeniedDesc(){
		return deniedDescription;
	}

	/**
	 * Sets an error message in the case the player doesn't have the required
	 * item to pic kthis item up.
	 * 
	 * @param description error message to display
	 */
	public void setDeniedDescription( String description ){
		deniedDescription = description;
	}
}
