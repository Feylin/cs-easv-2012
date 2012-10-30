package sco.collaborative.zuul;

/**
 * This is the main class of the text-based adventure "World of Zuul". The game
 * is very basic, and serves the purpose of teaching good object oriented
 * habits.
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others; it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Ida, Kim, Mak & Stefan
 * 
 */
public class Game {

	private static final int TIME_TO_COMPLETE = 300; // Time in seconds to complete the game.
	private long startTime;
	private Parser parser;
	private Item noegle, taske, pung, kaffe;
	private Room bar, sal, kantine, reception, korridor, b3, b2;
	private Player player;

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		startTime = System.currentTimeMillis();
		parser = new Parser();
		player = new Player( "Player" );

		createRooms();
		player.setRoom( sal );
		createItems();
		setRequirements();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms(){
		/* create the rooms */
		bar = new Room( "i baren. Du har brugt nøglen til at komme ind." );
		sal = new Room( "i salen." );
		kantine = new Room( "i kantinen." );
		reception = new Room( "i receptionen." );
		korridor = new Room( "i korridoren." );
		b2 = new Room( "i b2. Du nåede det!" );
		b3 = new Room( "i b3." );

		/* Set the exits */
		// Bar
		bar.setExit( "ud", sal );

		// Sal
		sal.setExit( "op", kantine );
		sal.setExit( "til baren", bar );

		// Reception
		reception.setExit( "tilbage", kantine );

		// Kantine
		kantine.setExit( "til receptionen", reception );
		kantine.setExit( "til korridoren", korridor );
		kantine.setExit( "ned", sal );

		// Korridor
		korridor.setExit( "til b2", b2 );
		korridor.setExit( "til b3", b3 );
		korridor.setExit( "til kantinen", kantine );

		// b3
		b3.setExit( "til korridoren", korridor );

		// b2
		b2.setExit( "til korridoren", korridor );
	}

	/**
	 * Create the items.
	 */
	private void createItems(){
		/* Make the items */
		taske = new Item( "din skoletaske" );
		kaffe = new Item( "en kop kaffe" );
		pung = new Item( "din pung" );
		noegle = new Item( "en nøgle" );

		/* Put the items in the rooms */
		sal.add( taske );
		kantine.add( kaffe );
		bar.add( pung );
		reception.add( noegle );
	}

	/**
	 * Set the requirements for the rooms and items.
	 */
	private void setRequirements(){
		/* Bar requires a key */
		bar.setRequirement( noegle );
		bar.setDeniedDescription( "Døren er låst!" );

		/* b2 requires a bag */
		b2.setRequirement( taske );
		b2.setDeniedDescription( "Du har ikke din skoletaske med. Alle vil grine!" );

		/* Coffee requires a wallet */
		kaffe.setRequirement( pung );
		kaffe.setDeniedDescription( "Du har ingen penge. Find din pung!" );

		/* Bag requires coffee */
		taske.setRequirement( kaffe );
		taske.setDeniedDescription( "Du er træt. Du har ikke energi til at bære din taske!" );
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play(){
		/* Print welcome message */
		printWelcome();

		/* Start the main game loop */
		boolean finished = false;
		while( !finished ){
			Command command = parser.getCommand();
			finished = processCommand( command ) || outOfTime() || player.getRoom() == b2;
		}

		/* Check reason for exiting the loop */
		if( outOfTime() ) System.out.println( "Timen er startet! Du har tabt!" );
		else if( player.getRoom() == b2 ) return;
		else System.out.println( "Tak for at du spillede med.  Ha' det godt." );
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome(){
		String storyString = getStoryString();
		String helpString = "Skriv 'hjælp' for at se en liste over kommandoer.";
		String exits = player.getRoom().getExitString();

		System.out.printf( "%s\n%s\n\n%s\n", helpString, storyString, exits );
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
	private boolean processCommand( Command command ){
		boolean wantToQuit = false;

		// Print an error message if the command is unknown.
		if( command.isUnknown() ){
			System.out.println( getUnknownCommandString() );
			return false;
		}

		// React to the usercommand.
		String commandWord = command.getCommandWord();
		switch( commandWord ){
			case "hjælp":
				printHelp();
				break;
			case "gå":
				player.goTo( command );
				printRoomDescription();
				System.out.println();
				break;
			case "tag":
				player.pickUp( command );
				break;
			case "se":
				printDetailedRoomDescription();
				break;
			case "ting":
				System.out.println( player.getItemString() );
				break;
			case "afslut":
				wantToQuit = quit( command );
				break;
		}
		return wantToQuit;
	}

	/**
	 * Print the help string.
	 */
	private void printHelp(){
		System.out.printf( "%s\n\nDine muligheder er:\n", getStoryString() );
		parser.showCommands();
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we
	 * really want to quit the game.
	 * 
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit( Command command ){
		if( command.hasSecondWord() ){
			System.out.println( "Bare skriv 'afslut', hvis du ønsker at afslutte." );
			return false;
		}else{
			return true;
		}
	}

	/**
	 * Check if we're out of time.
	 * 
	 * @return true if more than 'TIME_TO_COMPLETE' seconds has passed since
	 *         execution.
	 */
	private boolean outOfTime(){
		long time = System.currentTimeMillis();
		int deltaTime = (int) ((time - startTime) / 1000);
		return (deltaTime >= TIME_TO_COMPLETE);
	}

	/**
	 * Get the time remaining in a fancy " X minutes and Y seconds"-format.
	 * 
	 * @return time remaining in a fancy " X minutes and Y seconds"-format.
	 */
	public String getTimeRemaining(){
		// Store the current time
		long time = System.currentTimeMillis();
		// Calculate the time passed since executionen in seconds, 
		// and subtract it from the time given to complete the game.
		int remaining = TIME_TO_COMPLETE - (int) ((time - startTime) / 1000);
		// Calculate remaining minutes and seconds
		int minutes = remaining / 60;
		int seconds = remaining % 60;
		// Fix grammar
		String minutesPost = minutes == 1 ? "minut" : "minutter";
		String secondsPost = seconds == 1 ? "sekund" : "sekunder";
		//Increase readablity of the code
		String muchTime = String.format( "%s %s og %s %s", minutes, minutesPost, seconds, secondsPost );
		String lessTime = String.format( "%s %s", seconds, secondsPost );
		// Return the correct string
		return minutes > 0 ? muchTime : lessTime;
	}

	/**
	 * Prints a description of the room along with the time remaining.
	 */
	public void printRoomDescription(){
		// Split the complete string up in parts for increased readability.
		String shortDesc = player.getRoom().getShortDescription();
		String time = getTimeRemaining();
		String exit = player.getRoom().getExitString();
		System.out.printf( "Du er %s Du har %s til timen starter.\n%s", shortDesc, time, exit );
	}

	/**
	 * Prints a detailed description of the room including all the items in the
	 * room, along with the time remaining.
	 */
	public void printDetailedRoomDescription(){
		// Split the complete string up in parts for increased readability.
		String shortDesc = player.getRoom().getShortDescription();
		String time = getTimeRemaining();
		String items = player.getRoom().getItemString();
		String exit = player.getRoom().getExitString();
		System.out.printf( "Du er %s Du har %s til timen starter.\n%s\n%s", shortDesc, time, items, exit );
	}

	/**
	 * @return the backgroundstory of the game.
	 */
	public String getStoryString(){
		return "Du vågner efter fredagsbaren. Du indser at du har sovet hele "
				+ "weekenden, og at der nu er 5 minutter til du skal møde.\n"
				+ "Du mangler din morgenkaffe, og du er derfor meget træt.\n"
				+ "Du har mistet din pung og skoletaske et sted på skolen.";
	}

	/**
	 * Return the string to print when 
	 * @return
	 */
	public String getUnknownCommandString(){
		return "Altså hvad mener du?";
	}
}
