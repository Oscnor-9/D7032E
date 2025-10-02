package player;

import card.Card;
import ui.InteractiveUI;
import ui.NetworkInput;
import ui.NetworkUI;
import ui.ServerLogger;
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

    public NetworkUI getUI() {
    	return ui;
    }

    @Override
    public Card playCard() {				//Handles the playPhase for the remote connected player
        ui.showHand(hand);
        ui.promptPlayCard();

        String line = safeReadLine("playCard");
        if (line == null) {
            // fallback if disconnected
            return hand.isEmpty() ? null : hand.remove(0);
        }

        int idx = parseIndex(line, hand.size());
        return hand.remove(idx);
    }
    @Override
    public Card selectWinner(List<Card> submissions) {	//Handles the JudgePhase for the remote connected Player
        ui.showSubmissions(submissions);
        ui.promptJudgeChoice();
        ui.endSubmissions();

        String line = safeReadLine("selectWinner");
        if (line == null) {
            // fallback if disconnected
            return submissions.isEmpty() ? null : submissions.get(0);
        }
        int idx = parseIndex(line, submissions.size());
        return submissions.get(idx);
    }
    
    private String safeReadLine(String context) {	//Helper function that read the input from remote player and catches if they disconnect
        try {
            String line = input.readLine();
            if (line == null) {
                ServerLogger.warn("Remote player disconnected (" + context + "): " + name);
                ui.showDisconnect(name);
                return null;
            }
            return line;
        } catch (IOException e) {
            ServerLogger.warn("Remote player disconnected (" + context + ", IOException): " + name);
            ui.showDisconnect(name);
            return null;
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
