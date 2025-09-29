package main;

import game.Game;
import card.GreenAppleCard;
import card.RedAppleCard;
import card.Deck;
import io.FileCardLoader;
import player.Player;
import player.BotPlayer;
import player.HumanPlayer;
import network.GameServer;

import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static void main(String[] args) {
        try {
            // 1. Load decks
            Deck<GreenAppleCard> greenDeck =
                new Deck<>(FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new));
            Deck<RedAppleCard> redDeck =
                new Deck<>(FileCardLoader.loadCards("redApples.txt", RedAppleCard::new));

            // 2. Start server
            GameServer server = new GameServer(12345);
            System.out.println("🚀 Server started on port 12345");

            // 3. Create players: 2 bots + 1 remote human
            List<Player> players = new ArrayList<>();
            players.add(new BotPlayer("Bot Alice"));
            players.add(new BotPlayer("Bot Bob"));
            players.add(server.waitForRemotePlayer("RemoteHuman"));

            // 4. Start game
            Game game = new Game(greenDeck, redDeck, players);
            game.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
