// phase/ReplenishPhase.java
package phase;

import game.Game;
import player.HumanPlayer;
import player.Player;

/**
 * Phase that replenish each player's hand up to 7 red cards,
 * drawing from the red deck until either the hand is full or the deck is empty.
 */
public class ReplenishPhase implements Phase {
    @Override
    public void execute(Game game) {
        for (Player p : game.getPlayers()) {
            while (p.getHand().size() < 7) {
                var next = game.getRedDeck().draw(); 
                if (next == null) break;             // deck empty safeguard
                p.receiveCard(next);                 
            }
        }      
    }
}
