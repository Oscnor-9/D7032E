package ui;

import card.Card;
import player.Player;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import network.Protocol;

public class NetworkUI implements GameUI {
    private final PrintWriter out;

    public NetworkUI(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void showWinner(Player winner) {
        out.println(Protocol.WINNER_PREFIX + winner.getName());
        out.flush();
    }
    
    @Override
    public void showScores(List<Player> players, Function<Player, Integer> scoreProvider) {
        StringBuilder sb = new StringBuilder(Protocol.SCORES_PREFIX);
        for (Player p : players) {
            sb.append(p.getName())
              .append("=")
              .append(scoreProvider.apply(p))
              .append(";");
        }
        out.println(sb.toString());
        out.flush();
    }

    @Override
    public void announceJudge(Player judge) {
        out.println(Protocol.JUDGE_PREFIX + judge.getName());
    }

    @Override
    public void showSubmissions(List<Card> submissions) {
        out.println(Protocol.SUBMISSIONS);
        for (int i = 0; i < submissions.size(); i++) {
            out.println(i + ":" + submissions.get(i).getText());
        }
        // do not close here
    }

    public void endSubmissions() {
        out.println(Protocol.END_SUBMISSIONS);
        out.flush();
    }


    // ⚡ Extra protocol messages — not part of GameUI
    public void promptPlayCard() {
        out.println(Protocol.YOUR_TURN);
    }

    public void promptJudgeChoice() {
        out.println(Protocol.JUDGE_TURN);
    }

    public void showHand(List<Card> hand) {
        out.println(Protocol.HAND);
        for (int i = 0; i < hand.size(); i++) {
            out.println(i + ":" + hand.get(i).getText());
        }
        out.println(Protocol.END_HAND);
        out.flush();
    }
    @Override
    public void showMessage(String msg) {
    	out.println(Protocol.MESSAGE_PREFIX + msg);
    	out.flush();
    }
    @Override
    public void showDisconnect(String playerName) {
        System.out.println("Player disconnected: " + playerName);
    }
    @Override
    public void showGreenCard(String text) {
        out.println(Protocol.GREEN_PREFIX + text);
        out.flush();
    }
}
