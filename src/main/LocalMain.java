package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import player.HumanPlayer;
import player.Player;
import player.BotPlayer;
import ui.ConsoleInput;
import ui.ConsoleUI;
import ui.LocalConsoleUI;
import game.Game;

import java.util.ArrayList;
import java.util.List;


/**
 * Start a local-only game with one human host a a configurable number of bots.
 */

public class LocalMain {
	/**
	 * Creates the local players and runs a game session using console input/output
	 * @param greenDeck the deck of green apple cards
	 * @param redDeck the deck of red apple cards
	 * @param numOfBots the number of bots to include
	 */
    public static void start(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, int numOfBots) {
        LocalConsoleUI ui = new LocalConsoleUI();
        ui.showGameStarting(numOfBots);

        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Host", new ConsoleUI(), new ConsoleInput()));
        for (int i = 1; i <= numOfBots; i++) {
            players.add(new BotPlayer("Bot" + i));
        }

        Game game = new Game(greenDeck, redDeck, players);
        game.start();
    }
}
