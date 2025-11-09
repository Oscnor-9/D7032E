package phase;

import game.Game;
import card.GreenAppleCard;

/**
 * Phase responsible for drawing the next green apple card 
 */
public class DrawPhase implements Phase {
	public void execute(Game game) {
		GreenAppleCard card = game.getGreenDeck().draw();
		game.setCurrentGreenCard(card);
		
		game.broadcast(ui -> ui.showGreenCard(card.getText()));
	}

}
