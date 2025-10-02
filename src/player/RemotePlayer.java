package player;

import card.Card;
import ui.NetworkInput;
import ui.NetworkUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemotePlayer implements Player {
    private final String name;
    private final NetworkUI ui;
    private final NetworkInput input;
    private final List<Card> hand = new ArrayList<>();

    public RemotePlayer(String name, NetworkUI ui, NetworkInput input) {
        this.name = name;
        this.ui = ui;
        this.input = input;
    }

    @Override
    public Card playCard() {
        try {
            ui.showHand(hand);
            ui.promptPlayCard();
            String line = input.readLine();   // may throw IOException
            int idx = parseIndex(line, hand.size());
            return hand.remove(idx);
        } catch (IOException e) {
            // fallback if network fails
            return hand.remove(0);
        }
    }

    @Override
    public Card selectWinner(List<Card> submissions) {
        try {
            ui.showSubmissions(submissions);
            ui.promptJudgeChoice();
            String line = input.readLine();   // may throw IOException
            int idx = parseIndex(line, submissions.size());
            return submissions.get(idx);
        } catch (IOException e) {
            return submissions.get(0); // fallback
        }
    }

    @Override
    public void receiveCard(Card c) {
        hand.add(c);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    // -- helpers --
    private int parseIndex(String s, int size) {
        try {
            int x = Integer.parseInt(s.trim());
            if (x < 0) return 0;
            if (x >= size) return size - 1;
            return x;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
