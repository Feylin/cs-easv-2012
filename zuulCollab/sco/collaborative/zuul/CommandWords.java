package sco.collaborative.zuul;

/**
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 * 
 * @author Ida, Kim, Mak & Stefan
 */
public class CommandWords {

	// A constant array that holds all valid command words
	private static final String[] validCommands = { "gå", "tag", "se", "ting", "afslut", "hjælp" };

	/**
	 * Constructor - initialise the command words.
	 */
	public CommandWords() {
		// nothing to do at the moment...
	}

	/**
	 * Check whether a given String is a valid command word.
	 * 
	 * @return true if it is, false if it isn't.
	 */
	public boolean isCommand( String aString ){
		for( int i = 0; i < validCommands.length; i++ ){
			if( validCommands[i].equals( aString ) ) return true;
		}
		// if we get here, the string was not found in the commands
		return false;
	}

	/**
	 * Print all valid commands to System.out.
	 */
	public void showAll(){
		for( String command : validCommands ){
			System.out.print( command + "  " );
		}
		System.out.println();
	}
}
