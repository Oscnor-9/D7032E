package game;

import card.Card;
import card.RedAppleCard;
import player.Player;
import java.util.*;

public class SubmissionsManager {

	private final Map<Player, RedAppleCard> submissions = new LinkedHashMap<>();
	
	public void submitRedCard(Player player, RedAppleCard card) {
		submissions.put(player, card);
	}
	
	public Collection<RedAppleCard> getSubmittedCards(){
		return submissions.values();
	}
	
	public Player getOwnerOf(Card card) {
		return submissions.entrySet().stream()
				.filter(e -> e.getValue().equals(card))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}
	
	public void clear() {
		submissions.clear();
	}
}
