package player;

import card.Card;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HumanPlayer implements Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private final BufferedReader in;
    private final PrintWriter out;

    // Generic constructor: any Reader/Writer
    public HumanPlayer(String name, Reader reader, Writer writer) {
        this.name = name;
        this.in = new BufferedReader(reader);
        this.out = new PrintWriter(writer, true);
    }

    // Local console human
    public static HumanPlayer local(String name) {
        return new HumanPlayer(
                name,
                new InputStreamReader(System.in),
                new OutputStreamWriter(System.out)
        );
    }

    // Remote human (socket-based)
    public static HumanPlayer remote(String name, Socket socket) throws IOException {
        return new HumanPlayer(
                name,
                new InputStreamReader(socket.getInputStream()),
                new OutputStreamWriter(socket.getOutputStream())
        );
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
            out.println("Your hand:");
            for (int i = 0; i < hand.size(); i++) {
                out.println(i + ": " + hand.get(i).getText());
            }
            out.println("Choose a card index:");
            int index = Integer.parseInt(in.readLine());
            return hand.remove(index);
        } catch (Exception e) {
            out.println("⚠ Invalid choice, using first card.");
            return hand.remove(0);
        }
    }

    @Override
    public void receiveCard(Card card) {
        hand.add(card);
    }

    @Override
    public Card selectWinner(List<Card> submissions) {
        try {
            out.println("Choose the winning card:");
            for (int i = 0; i < submissions.size(); i++) {
                out.println(i + ": " + submissions.get(i).getText());
            }
            int index = Integer.parseInt(in.readLine());
            return submissions.get(index);
        } catch (Exception e) {
            out.println("⚠ Invalid choice, picking first card.");
            return submissions.get(0);
        }
    }
}

