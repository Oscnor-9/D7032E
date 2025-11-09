package ui;

import card.Card;
import player.Player;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import network.Protocol;

/**
 * Handles communication from the server to a connected remote client.
 * <p>
 * Sends serialized protocol messages over a {@link PrintWriter} stream
 */
public class NetworkUI implements GameUI {
    private final PrintWriter out;

    /**
     * Creates a new network UI using a given output stream
     * @param out the socket output stream to write protocol messages to
     */
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

    /** Sends an end marker indicating all submission are sent */
    public void endSubmissions() {
        out.println(Protocol.END_SUBMISSIONS);
        out.flush();
    }


    /** Prompts the remote player to play a card */
    public void promptPlayCard() {
        out.println(Protocol.YOUR_TURN);
    }

    /** Prompts the remote player to select a winning card */
    public void promptJudgeChoice() {
        out.println(Protocol.JUDGE_TURN);
    }

    /**
     * Sends the player's hand to the client
     * @param hand list of cards currently held by the player
     */
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
