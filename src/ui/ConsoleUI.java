package ui;

import card.Card;
import player.Player;

import java.util.List;
import java.util.function.Function;

public class ConsoleUI implements GameUI {
    @Override
    public void showWinner(Player winner) {
        System.out.println("🏆 Winner: " + winner.getName());
    }

    @Override
    public void showScores(List<Player> players, Function<Player, Integer> scoreFn) {
        System.out.println("Scores:");
        for (Player p : players) {
            System.out.println(" - " + p.getName() + ": " + scoreFn.apply(p));
        }
    }

    @Override
    public void announceJudge(Player judge) {
        System.out.println("👑 Judge this round: " + judge.getName());
    }

    @Override
    public void showSubmissions(List<Card> submissions) {
        System.out.println("Submitted cards:");
        for (int i = 0; i < submissions.size(); i++) {
            System.out.println(" [" + i + "] " + submissions.get(i).getText());
        }
    }

    @Override
    public void showHand(List<Card> hand) {
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(" [" + i + "] " + hand.get(i).getText());
        }
    }

    @Override
    public void promptPlayCard() {
        System.out.print("👉 Choose a card index: ");
    }

    @Override
    public void promptJudgeChoice() {
        System.out.print("👉 Judge: choose the winning card index: ");
    }
}
