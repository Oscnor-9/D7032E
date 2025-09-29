package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import game.Game;
import io.FileCardLoader;
import network.GameServer;
import player.BotPlayer;
//import player.HumanPlayer;
import player.Player;

import java.util.List;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        int port = 12345;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many remote players should join? ");
        int expectedClients = Integer.parseInt(scanner.nextLine().trim());

        try {
            // Load decks
            Deck<GreenAppleCard> greenDeck =
                new Deck<>(FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new));
            Deck<RedAppleCard> redDeck =
                new Deck<>(FileCardLoader.loadCards("redApples.txt", RedAppleCard::new));

            // Start server
            GameServer server = new GameServer(port);

            // Wait for remote players
            for (int i = 1; i <= expectedClients; i++) {
                server.acceptRemotePlayer("RemotePlayer" + i);
            }

            // Add optional bots
            server.addPlayer(new BotPlayer("Bot 1"));
            server.addPlayer(new BotPlayer("Bot 2"));

            // Build game with ALL connected players
            List<Player> players = server.getPlayers();
            Game game = new Game(greenDeck, redDeck, players);
            game.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
