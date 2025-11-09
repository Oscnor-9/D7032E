package player;

import card.Card;
import ui.InteractiveUI;
import ui.NetworkInput;
import ui.NetworkUI;
import ui.InputParser;
import ui.ServerLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player connected over the network
 * <p>
 * Uses a {@link NetworkUI} to send messages to the remote client and a
 * {@link NetworkInput} to receive their choices
 */
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

    /**
     * Returns the network UI for the remote player
     */
    public NetworkUI getUI() {
    	return ui;
    }

    @Override
    public Card playCard() {				
    	//Handles the playPhase for the remote connected player
        ui.showHand(hand);
        ui.promptPlayCard();

        String line = safeReadLine("playCard");
        if (line == null) {
            // fallback if disconnected
            return hand.isEmpty() ? null : hand.remove(0);
        }

        int idx = InputParser.parseIndex(line, hand.size());
        return hand.remove(idx);
    }
    @Override
    public Card selectWinner(List<Card> submissions) {	
    	//Handles the JudgePhase for the remote connected Player
        ui.showSubmissions(submissions);
        ui.promptJudgeChoice();
        ui.endSubmissions();

        String line = safeReadLine("selectWinner");
        if (line == null) {
            // fallback if disconnected
            return submissions.isEmpty() ? null : submissions.get(0);
        }
        int idx = InputParser.parseIndex(line, submissions.size());
        return submissions.get(idx);
    }
    
    /**
     * Read a line from the remote player and handles disconnects
     * @param context a short label describing the current action
     * @return the read line, or {@code null} if the player disconnected
     */
    private String safeReadLine(String context) {	
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


}
