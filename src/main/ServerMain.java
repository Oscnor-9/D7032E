package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import io.FileCardLoader;
import player.HumanPlayer;
import player.BotPlayer;
import player.Player;
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
import java.util.Scanner;

public class ServerMain {

    public static void start(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, int numOfBots) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Ask how many remote players
        System.out.print("How many remote players should join? ");
        int numRemote = scanner.nextInt();

        List<Player> players = new ArrayList<>();

        // Local host player
        players.add(new HumanPlayer("Host", new ConsoleUI(), new ConsoleInput()));

        // Bots
        for (int i = 1; i <= numOfBots; i++) {
            players.add(new BotPlayer("Bot" + i));
        }

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("✅ Server running on port 12345, waiting for " + numRemote + " players...");

            // Wait until all remote players have connected
            for (int i = 1; i <= numRemote; i++) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("🔌 Remote player connected (" + i + "/" + numRemote + ")");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                HumanPlayer remote = new HumanPlayer(
                        "Remote" + i,
                        new NetworkUI(out),
                        new NetworkInput(in)
                );

                players.add(remote);
            }
        }

        // ✅ Start game once all players are connected
        Game game = new Game(greenDeck, redDeck, players);
        game.start();
    }
}
