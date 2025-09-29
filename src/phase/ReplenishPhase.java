// phase/ReplenishPhase.java
package phase;

import game.Game;
import player.Player;

public class ReplenishPhase implements Phase {
    @Override
    public void execute(Game game) {
        for (Player p : game.getPlayers()) {
            while (p.getHand().size() < 7) {
                var next = game.getRedDeck().draw(); // returns RedAppleCard
                if (next == null) break;             // deck empty safeguard
                p.receiveCard(next);                 // upcasts to Card automatically
            }
        }
        System.out.println("🔄 Hands replenished!");
    }
}
