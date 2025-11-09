package ui;


/**
 * SImple console output for local game setup messages
 */
public class LocalConsoleUI {
	
	/**
	 * Displays a startup message indicating number of bots in the local game
	 * @param numBots number of bots in the game
	 */
    public void showGameStarting(int numBots) {
        System.out.println("Starting local game with " + numBots + " bot(s)...");
    }
}
