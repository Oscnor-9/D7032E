package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import io.FileCardLoader;
import ui.MainConsoleUI;


/**
 * Entry point for the game application.
 * <p> 
 * Lets the user choose between local play, hosting a server, or joining
 * an existing server as a remote client
 */


public class Main {
	
	/**
	 * Start the application and ask the user which mode to run 
	 * @param args unused
	 * @throws Exception if loading cards or starting a mode fails 
	 */
    public static void main(String[] args) throws Exception {
        Deck<GreenAppleCard> greenDeck = new Deck<>(
            FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new)
        );
        Deck<RedAppleCard> redDeck = new Deck<>(
            FileCardLoader.loadCards("redApples.txt", RedAppleCard::new)
        );

        MainConsoleUI ui = new MainConsoleUI();

        int choice = ui.askMode();
        int numOfBots = 0;
        
        // For local and server modes require at least 2 bots
        if (choice == 1 || choice == 2) {
            do {
                numOfBots = ui.askNumOfBots();
                if (numOfBots < 2) {
                    ui.showInvalidBotCount();
                }
            } while (numOfBots < 2);
        }

        switch (choice) {
            case 1 -> LocalMain.start(greenDeck, redDeck, numOfBots);
            case 2 -> ServerMain.start(greenDeck, redDeck, numOfBots);
            case 3 -> ClientMain.main(new String[]{}); //Join as remote player
            default -> ui.showInvalidChoice();
        }
    }
}
