package game;

import card.Card;
import card.RedAppleCard;
import player.Player;
import java.util.*;

/**
 * Manages red card submissions for a single round
 * <p>
 * Keeps track of which player submitted which card and preserves
 * insertion order for consistent judge display
 */
public class SubmissionsManager {

	private final Map<Player, RedAppleCard> submissions = new LinkedHashMap<>();
	
	/**
	 * Registers a red card submission for the given player
	 * @param player the submitting player
	 * @param card the red card they submitted
	 */
	public void submitRedCard(Player player, RedAppleCard card) {
		submissions.put(player, card);
	}
	
	/**
	 * Returns all red cards that have been submitted in this round,
	 * in the order they were received
	 */
	public Collection<RedAppleCard> getSubmittedCards(){
		return submissions.values();
	}
	
	/**
	 * Returns the player who submitted the given card.
	 * @param card the card to look up
	 * @return the player who submitted the card or {@code null} if not found
	 */
	public Player getOwnerOf(Card card) {
		return submissions.entrySet().stream()
				.filter(e -> e.getValue().equals(card))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * Clear all submissions.
	 */
	public void clear() {
		submissions.clear();
	}
}
