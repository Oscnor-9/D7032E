
package test;

import org.junit.Test;
import static org.junit.Assert.*;
import phase.ReplenishPhase;
import card.*;
import game.Game;
import player.*;
import static test.TestLoader.*;

import java.util.*;

/**
 * Tests setup and initalization rules, including loading decks,
 * shuffling, initial dealing, and random judge selection
 */
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
        Deck<RedAppleCard> original = loadRedDeck();
        boolean foundDifference = false;

        // Try multiple times to reduce false negatives
        for (int run = 0; run < 3; run++) {
            Deck<RedAppleCard> shuffled = loadRedDeck();
            shuffled.shuffle(); // explicitly shuffle

            // Compare order with original
            boolean allSame = true;
            for (int i = 0; i < original.size(); i++) {
                if (!original.get(i).getText().equals(shuffled.get(i).getText())) {
                    allSame = false;
                    break;
                }
            }
            if (!allSame) {
                foundDifference = true;
                break;
            }
        }
        assertTrue("Shuffling should change the card order at least once", foundDifference);
    }

    @Test
    public void rule4_dealSevenRedApples() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        int initialRedSize = reds.size();

        List<Player> players = Arrays.asList(
                new BotPlayer("Bot 1"),
                new BotPlayer("Bot 2")
        );

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);

        for (Player p : game.getPlayers()) {
            assertEquals("Each player should start with 7 cards", 7, p.getHand().size());
        }

        // Extra: verify deck reduced by 14 cards
        assertEquals("Red deck should shrink by number of dealt cards",
                     initialRedSize - (players.size() * 7), reds.size());
    }

    @Test
    public void rule5_randomiseJudge() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();

        List<Player> players = Arrays.asList(
                new BotPlayer("Bot 1"),
                new BotPlayer("Bot 2"),
                new BotPlayer("Bot 3")
        );

        Set<String> judges = new HashSet<>();

        // Run multiple games to check variation
        for (int i = 0; i < 10; i++) {
            Game game = new Game(loadGreenDeck(), loadRedDeck(), players);
            assertNotNull("There should be a starting judge", game.getCurrentJudge());
            judges.add(game.getCurrentJudge().getName());
        }

        assertTrue("Judge selection should vary between games", judges.size() > 1);
    }
}
