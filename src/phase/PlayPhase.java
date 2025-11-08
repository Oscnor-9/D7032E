package phase;

import game.Game;
import java.util.List;
import java.util.ArrayList;
import player.Player;
import card.RedAppleCard;

import java.util.Collections;

import card.Card;

public class PlayPhase implements Phase {
    @Override
    public void execute(Game game) {
        // ✅ Announce start of play phase
        game.broadcast(ui -> ui.showMessage("▶ Players are choosing red cards..."));
        
        List<Player> order = new ArrayList<>(game.getPlayersExcludingJudge());
        Collections.shuffle(order);
        

        for (Player p : order) {
            Card chosen = p.playCard();   // works for both bots and humans

            if (chosen instanceof RedAppleCard redCard) {
                game.submitRedCard(p, redCard);

                // ✅ Tell everyone a card was submitted
                game.broadcast(ui -> ui.showMessage("  " + p.getName() + " submitted a card."));
            } else {
                // ✅ Tell everyone this player failed to play
                game.broadcast(ui -> ui.showMessage("  " + p.getName() + " could not play a red card."));
            }
        }
    }
}
