package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import player.*;
import ui.ConsoleInput;
import ui.ConsoleUI;
import ui.NetworkInput;
import ui.NetworkUI;
import game.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static void start(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, int numOfBots) throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Host", new ConsoleUI(), new ConsoleInput()));

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("✅ Server running on port 12345");
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out   = new PrintWriter(clientSocket.getOutputStream(), true);

            HumanPlayer remote = new HumanPlayer(
                "Remote1",
                new NetworkUI(out),
                new NetworkInput(in)
            );

            players.add(remote);
            for (int i = 1; i <= numOfBots; i++) {
                players.add(new BotPlayer("Bot" + i));
            }

            Game game = new Game(greenDeck, redDeck, players);
            game.start();
        }
    }
}
