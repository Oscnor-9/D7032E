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
	    Deck<GreenAppleCard> greens = new Deck<>(new ArrayList<>(Arrays.asList(
	        new GreenAppleCard("Tall")
	    )));
	    Deck<RedAppleCard> reds = new Deck<>(new ArrayList<>(Arrays.asList(
	        new RedAppleCard("Funny")
	    )));

	    Player bot = new BotPlayer("Bot 1");
	    List<Player> players = Collections.singletonList(bot);

	    Game game = new Game(greens, reds, players);
	    game.setCurrentGreenCard(new GreenAppleCard("Tall")); // ensure a green card exists
	    game.awardPoint(bot);

	    // ✅ Stronger check: this player has exactly 1 point
	    assertEquals("Bot should have exactly 1 point after being awarded", 1, game.getScore(bot));
	    // ✅ Total points should match
	    assertEquals("Total points should equal 1", 1, game.totalPoints());
	}


	@Test
	public void rule15_winConditionThresholds() throws Exception {
	    Deck<GreenAppleCard> greens = loadGreenDeck();
	    Deck<RedAppleCard> reds = loadRedDeck();

	    // playersCount → requiredWinningPoints
	    Map<Integer, Integer> thresholds = new LinkedHashMap<>();
	    thresholds.put(4, 8);
	    thresholds.put(5, 7);
	    thresholds.put(6, 6);
	    thresholds.put(7, 5);
	    thresholds.put(8, 4);
	    thresholds.put(9, 4); // 8+ rule, same threshold

	    for (Map.Entry<Integer, Integer> entry : thresholds.entrySet()) {
	        int playerCount = entry.getKey();
	        int requiredPoints = entry.getValue();

	        // build players
	        List<Player> players = new ArrayList<>();
	        for (int i = 1; i <= playerCount; i++) {
	            players.add(new BotPlayer("Bot " + i));
	        }

	        Game game = new Game(greens, reds, players);
	        game.setCurrentGreenCard(new GreenAppleCard("Temp"));

	        Player firstBot = players.get(0);

	        // give (requiredPoints - 1) → no winner
	        for (int i = 0; i < requiredPoints - 1; i++) {
	            game.awardPoint(firstBot);
	        }
	        assertFalse("With " + playerCount + " players and "
	            + (requiredPoints - 1) + " points, there should be no winner",
	            game.hasWinner());

	        // give 1 more → winner
	        game.awardPoint(firstBot);
	        assertTrue("With " + playerCount + " players, reaching " + requiredPoints
	            + " points should declare a winner", game.hasWinner());
	        assertEquals("Winning bot should have exactly " + requiredPoints + " points",
	            requiredPoints, game.getScore(firstBot));
	    }
	}

}
