package network;

import player.Player;
import card.Card;
import ui.NetworkLogger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkPlayer implements Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private final BufferedReader in;
    private final PrintWriter out;

    public NetworkPlayer(String name, Socket socket) throws IOException {
        this.name = name;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Card> getHand() {
        return new ArrayList<>(hand); // defensive copy
    }

    @Override
    public Card playCard() {
        try {
            out.println("YOUR_TURN:" + hand);
            String response = in.readLine();
            int choice = Integer.parseInt(response);
            return hand.remove(choice);
        } catch (Exception e) {
            NetworkLogger.error("Failed to play card for " + name, e);
            return null;
        }
    }

    @Override
    public void receiveCard(Card card) {
        hand.add(card);
    }

    @Override
    public Card selectWinner(List<Card> submissions) {
        try {
            out.println("JUDGE_TURN:" + submissions);
            String response = in.readLine();
            int choice = Integer.parseInt(response);
            return submissions.get(choice);
        } catch (Exception e) {
            NetworkLogger.error("Failed to select winner for " + name, e);
            return null;
        }
    }
}
