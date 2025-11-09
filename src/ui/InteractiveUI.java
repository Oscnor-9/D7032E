package ui;

import java.util.List;
import card.Card;
import player.Player;
import game.Game;

/**
 * Extended interface for UIs that require two-way player interaction.
 * <p>
 * Includes methods for prompting choices and displaying hand or judge actions
 */
public interface InteractiveUI extends GameUI {
	
	/** Prompt the player to select a card to play */
    void promptPlayCard();
    
    /** Prompt the judge to select a winning card */
    void promptJudgeChoice();
    
    /** Display the players current hand */
    void showHand(List<Card> hand);
    
    /** Display the judge's decision and updated scores */
    void showJudgeDecision(Player judge, Card winningCard, Player winner, List<Player> players, Game game);
}