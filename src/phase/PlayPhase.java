package phase;

import game.Game;
import player.Player;
import card.RedAppleCard;
import card.Card;

public class PlayPhase implements Phase {
    @Override
    public void execute(Game game) {
        System.out.println("▶ Players are choosing red cards...");

        for (Player p : game.getPlayersExcludingJudge()) {
            Card chosen = p.playCard();   // works for both bots and humans

            if (chosen instanceof RedAppleCard) {
                game.submitRedCard(p, (RedAppleCard) chosen);
                System.out.println("  " + p.getName() + " submitted a card.");
            } else {
                System.out.println("  " + p.getName() + " could not play a red card.");
            }
        }
    }
}
