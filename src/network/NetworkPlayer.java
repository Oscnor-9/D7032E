package network;

import player.Player;
import card.Card;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkPlayer implements Player {
    private String name;
    private List<Card> hand = new ArrayList<>();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public NetworkPlayer(String name, Socket socket) throws IOException {
        this.name = name;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public Card playCard() {
        try {
            out.println("YOUR_TURN:" + hand);
            String response = in.readLine();
            int choice = Integer.parseInt(response);
            return hand.remove(choice);
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
