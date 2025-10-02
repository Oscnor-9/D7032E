package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import player.HumanPlayer;
import player.Player;
import player.BotPlayer;
import ui.ConsoleInput;
import ui.ConsoleUI;
import game.Game;

import java.util.ArrayList;
import java.util.List;

//Main for a player that wants to play a local game against bots

public class LocalMain {
    public static void start(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, int numOfBots) {
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Host", new ConsoleUI(), new ConsoleInput()));
        for (int i = 1; i <= numOfBots; i++) {
            players.add(new BotPlayer("Bot" + i));
        }

        Game game = new Game(greenDeck, redDeck, players);
        game.start();
    }
}
