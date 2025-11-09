package player;

import java.util.List;
import ui.GameUI;
import card.Card;

/**
 * Common interface for all player types in the game
 */
public interface Player {
	
	/**
	 * Returns the display name of this player
	 */
	String getName();
	
	/**
	 * Returns the current hand of cards for this player
	 */
	List<Card> getHand();
	
	/**
	 * Lets the player choose a card to play
	 * @return the chosen card, or {@code null} if the player cannot play
	 */
	Card playCard();
	
	/**
	 * Gives a new card to this player
	 * @param card the card to add to the hand
	 */
	void receiveCard(Card card);
	
	/**
	 * Lets the player select a winning card from a list of submissions
	 * @param submissions the cards that have been submitted
	 * @return the winning card, or {@code null} if no choice is made
	 */
	Card selectWinner(List<Card> submissions);
	
	/**
	 * Returns the UI associated with this player.
	 * <p> 
	 * Bot players may return {@code null} because the do not need a UI
	 * @return the UI instance, {@code null} if none.
	 */
	GameUI getUI();
}
