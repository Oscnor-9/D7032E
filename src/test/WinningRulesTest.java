package test;

import org.junit.Test;
import static org.junit.Assert.*;

import card.*;
import player.*;
import game.Game;
import static test.TestLoader.*;

import java.util.*;

public class WinningRulesTest {

    @Test
    public void rule14_awardGreenAppleAsPoint() {
        // Use Arrays.asList instead of List.of (Java 8 compatible)
        Deck<GreenAppleCard> greens = new Deck<>(new ArrayList<>(Arrays.asList(
            new GreenAppleCard("Tall")
        )));
        Deck<RedAppleCard> reds = new Deck<>(new ArrayList<>(Arrays.asList(
            new RedAppleCard("Funny")
        )));

        Player bot = new BotPlayer("Bot 1");
        List<Player> players = new ArrayList<>();
        players.add(bot);

        Game game = new Game(greens, reds, players);
        game.setCurrentGreenCard(new GreenAppleCard("Tall")); // so awardPoint has a card
        game.awardPoint(bot);

        assertTrue("Bot should have at least 1 point", game.totalPoints() >= 1);
    }

    @Test
    public void rule15_winConditionFor4Players() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();

        List<Player> players = Arrays.asList(
            new BotPlayer("Bot 1"),
            new BotPlayer("Bot 2"),
            new BotPlayer("Bot 3"),
            new BotPlayer("Bot 4")
        );

        Game game = new Game(greens, reds, players);
        game.setCurrentGreenCard(new GreenAppleCard("Temp")); // set active green card

        // Simulate awarding enough points
        for (int i = 0; i < 8; i++) {
            game.awardPoint(players.get(0));
        }

        assertTrue("With 4 players, 8 points should win", game.hasWinner());
    }
}
