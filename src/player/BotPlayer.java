package player;

import java.util.ArrayList;
import ui.GameUI;
import java.util.List;
import java.util.Random;
import card.Card;

/**
 * A simple AI-controlled player that makes random choices.
 * <p>
 * The bot randomly plays cards from its hand and, when acting as a judge,
 * randomly selects a winning card from the submissions.
 */
public class BotPlayer implements Player{
	private String name;
	private List<Card> hand = new ArrayList<>();
	private Random random = new Random();
	
	public BotPlayer(String namn){
		this.name = namn;
	}
	public String getName() {
		return name;
	}
	public List<Card> getHand(){
		return hand;
	}
	public Card playCard() {
		if(hand.isEmpty()) {
			return null;
		}
		return hand.remove(random.nextInt(hand.size()));
	}
	public void receiveCard(Card card) {
		hand.add(card);
	}
	public Card selectWinner(List<Card> submissions) {
		if(submissions.isEmpty()) {
			return null;
		}
		return submissions.get(random.nextInt(submissions.size()));
	}
	@Override
	public GameUI getUI() {
	    return null; // bots don’t need a UI
	}
}
