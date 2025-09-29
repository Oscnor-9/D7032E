

package phase;

import game.Game;
import player.Player;
import card.Card;
import java.util.ArrayList;

public class JudgePhase implements Phase {
    @Override
    public void execute(Game game) {
        Player judge = game.getCurrentJudge();
        Card winningCard = judge.selectWinner(
            new ArrayList<>(game.getSubmittedCards())
        );

        Player winner = game.getOwnerOf(winningCard);
        game.awardPoint(winner);

        System.out.println("👑 Judge " + judge.getName() +
                           " chose: " + winningCard.getText() +
                           " → Point for " + winner.getName());
        
        System.out.println("📊 Current scores:");
        for (Player p : game.getPlayers()) {
            System.out.println("   " + p.getName() + " → " + game.getScore(p));
        }
        System.out.println("-----------------------------");
    }
}
