package phase;

import game.Game;
import player.Player;
import card.Card;
import java.util.ArrayList;

public class JudgePhase implements Phase {
    @Override
    public void execute(Game game) {
        Player judge = game.getCurrentJudge();

        // Tell everyone the judge is selecting
        game.broadcast(ui -> ui.showMessage("Judge " + judge.getName() + " is selecting..."));

        Card winningCard = judge.selectWinner(new ArrayList<>(game.getSubmittedCards()));

        // ✅ Confirm judge has made a choice
        game.broadcast(ui -> ui.showMessage("Judge returned a choice: " + winningCard.getText()));

        Player winner = game.getOwnerOf(winningCard);
        game.awardPoint(winner);

        // Announce winner
        game.broadcast(ui -> ui.showMessage(
            "Judge " + judge.getName() +
            " chose: " + winningCard.getText() +
            " → Point for " + winner.getName()
        ));

        // Show scores (requires showScores in GameUI)
        game.broadcast(ui -> ui.showScores(game.getPlayers(), game::getScore));

        // Separator
        game.broadcast(ui -> ui.showMessage("-----------------------------"));
    }
}
