package player;

import java.util.List;
import card.Card;

public interface Player {
	String getName();
	List<Card> getHand();
	
	Card playCard();
	void receiveCard(Card card);
	
	Card selectWinner(List<Card> submissions);
}
