package ui;

import card.Card;
import game.Game;
import player.Player;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleUI implements InteractiveUI {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showWinner(Player winner) {
        System.out.println("🎉 Winner: " + winner.getName());
    }

    @Override
    public void showScores(List<Player> players, Function<Player, Integer> scoreFn) {
        System.out.println("📊 Current scores:");
        for (Player p : players) {
            System.out.println("   " + p.getName() + " → " + scoreFn.apply(p));
        }
        System.out.println("-----------------------------");
    }

    @Override
    public void announceJudge(Player judge) {
        System.out.println("👩‍⚖️ Judge this round: " + judge.getName());
    }

    @Override
    public void showSubmissions(List<Card> submissions) {
        System.out.println("Submitted cards:");
        for (int i = 0; i < submissions.size(); i++) {
            System.out.printf("%d: %s%n", i, submissions.get(i).getText());
        }
    }

    @Override
    public void promptPlayCard() {
        System.out.println("▶ Choose a card from your hand:");
    }

    @Override
    public void promptJudgeChoice() {
        System.out.println("▶ Judge, choose the winning card:");
    }

    @Override
    public void showHand(List<Card> hand) {
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.printf("%d: %s%n", i, hand.get(i).getText());
        }
    }

    @Override
    public void showJudgeDecision(Player judge, Card winningCard, Player winner, List<Player> players, Game game) {
        System.out.println("Judge " + judge.getName() +
                           " chose: " + winningCard.getText() +
                           " → Point for " + winner.getName());
        showScores(players, game::getScore);
    }
}
