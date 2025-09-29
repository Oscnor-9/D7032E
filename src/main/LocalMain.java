package main;

import java.util.ArrayList;
import java.util.List;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import game.Game;
import io.FileCardLoader;
import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

public class LocalMain {
    public static void main(String[] args) {
        try {
            Deck<GreenAppleCard> greenDeck =
                new Deck<>(FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new));
            Deck<RedAppleCard> redDeck =
                new Deck<>(FileCardLoader.loadCards("redApples.txt", RedAppleCard::new));

            List<Player> players = new ArrayList<>();
            players.add(HumanPlayer.local("Oscar"));
            players.add(new BotPlayer("Bot Alice"));
            players.add(new BotPlayer("Bot Bob"));

            Game game = new Game(greenDeck, redDeck, players);
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
