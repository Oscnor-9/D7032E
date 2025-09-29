package phase;

import game.Game;
import card.GreenAppleCard;



public class DrawPhase implements Phase {
	public void execute(Game game) {
		GreenAppleCard card = game.getGreenDeck().draw();
		System.out.println("\n Green card: " + card.getText());
		game.setCurrentGreenCard(card);
	}

}
