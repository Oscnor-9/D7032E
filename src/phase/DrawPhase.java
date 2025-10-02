package phase;

import game.Game;
import card.GreenAppleCard;



public class DrawPhase implements Phase {
	public void execute(Game game) {
		GreenAppleCard card = game.getGreenDeck().draw();
		game.setCurrentGreenCard(card);
		
		game.broadcast(ui -> ui.showGreenCard(card.getText()));
	}

}
