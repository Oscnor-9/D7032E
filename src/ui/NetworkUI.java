package ui;

import card.Card;
import player.Player;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;

public class NetworkUI implements GameUI {
    private final PrintWriter out;

    public NetworkUI(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void showWinner(Player winner) {
        out.println("WINNER:" + winner.getName());
    }

    @Override
    public void showScores(List<Player> players, Function<Player, Integer> scoreFn) {
        out.println("SCORES");
        for (Player p : players) {
            out.println(p.getName() + ":" + scoreFn.apply(p));
        }
        out.println("END_SCORES");
    }

    @Override
    public void announceJudge(Player judge) {
        out.println("JUDGE:" + judge.getName());
    }

    @Override
    public void showSubmissions(List<Card> submissions) {
        out.println("SUBMISSIONS");
        for (int i = 0; i < submissions.size(); i++) {
            out.println(i + ":" + submissions.get(i).getText());
        }
        out.println("END_SUBMISSIONS");
    }

    @Override
    public void showHand(List<Card> hand) {
        out.println("HAND");
        for (int i = 0; i < hand.size(); i++) {
            out.println(i + ":" + hand.get(i).getText());
        }
        out.println("END_HAND");
    }

    @Override
    public void promptPlayCard() {
        out.println("YOUR_TURN");
    }

    @Override
    public void promptJudgeChoice() {
        out.println("JUDGE_TURN");
    }
}
