package ui;

import card.Card;
import player.Player;
import java.util.List;

/**
 * Base interface for all user interfaces in the game.
 * <p>
 * Defines display methods for key game events such as
 * showing scores, announcing winners, or display messages.
 */
public interface GameUI {
	
	/**Displays the game winner */
    void showWinner(Player winner);
    
    /**
     * Display the current scores for all players
     * @param players a list of the players
     * @param scoreFn a function used to obtain each player's score
     */
    void showScores(List<Player> players, java.util.function.Function<Player,Integer> scoreFn);
    
    /** Announce the current judge */
    void announceJudge(Player judge);
    
    /** Display all submitted cards for the current round */
    void showSubmissions(List<Card> submissions);
    
    /** Display a general purpose message to the players */
    void showMessage(String msg);
    
    /** Notifies the players that another player has disconnected */
    void showDisconnect(String playerName);
    
    /** Display the current green apple card */
    void showGreenCard(String text);
}
