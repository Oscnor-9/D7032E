package player;

import java.util.List;
import ui.GameUI;
import card.Card;

public interface Player {
	String getName();
	List<Card> getHand();
	
	Card playCard();
	void receiveCard(Card card);
	
	Card selectWinner(List<Card> submissions);
	
	GameUI getUI();
}
