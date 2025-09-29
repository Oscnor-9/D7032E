
package test;

import org.junit.Test;
import static org.junit.Assert.*;
import phase.ReplenishPhase;
import card.*;
import game.Game;
import player.*;
import static test.TestLoader.*;

import java.util.*;

public class SetupRulesTest {
    @Test
    public void rule1_readGreenApplesFromFile() throws Exception {
    	Deck<GreenAppleCard> greens = loadGreenDeck();
        assertFalse("Green deck should not be empty", greens.isEmpty());
    }

    @Test
    public void rule2_readRedApplesFromFile() throws Exception {
        Deck<RedAppleCard> reds = loadRedDeck();
        assertFalse("Red deck should not be empty", reds.isEmpty());
    }

    @Test
    public void rule3_shuffleDecks() throws Exception {
        Deck<RedAppleCard> reds1 = loadRedDeck();
        Deck<RedAppleCard> reds2 = loadRedDeck();
        // After shuffle, order should not always be identical
        boolean allSame = true;
        for (int i = 0; i < reds1.size(); i++) {
            if (!reds1.get(i).getText().equals(reds2.get(i).getText())) {
                allSame = false; break;
            }
        }
        assertFalse("Decks should not always be in the same order after shuffle", allSame);
    }

    @Test
    public void rule4_dealSevenRedApples() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();

        List<Player> players = new ArrayList<>();
        players.add(new BotPlayer("Bot 1"));
        players.add(new BotPlayer("Bot 2"));

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);

        for (Player p : game.getPlayers()) {
            assertEquals("Each player should start with 7 cards", 7, p.getHand().size());
        }
    }

    @Test
    public void rule5_randomiseJudge() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();

        List<Player> players = new ArrayList<>();
        players.add(new BotPlayer("Bot 1"));
        players.add(new BotPlayer("Bot 2"));
        players.add(new BotPlayer("Bot 3"));

        Game game = new Game(greens, reds, players);
        assertNotNull("There should be a starting judge", game.getCurrentJudge());
    }
}
