package test;

import org.junit.Test;
import static org.junit.Assert.*;

import card.*;
import player.*;
import game.Game;
import phase.*;
import static test.TestLoader.*;

import java.util.*;

public class GameplayRulesTest {
    @Test
    public void rule6_drawGreenApple() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"));

        Game game = new Game(greens, reds, players);
        new DrawPhase().execute(game);
        assertNotNull("A green card should be drawn", game.getCurrentGreenCard());
    }

    @Test
    public void rule7_and_9_allPlayersSubmit() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"), new BotPlayer("Bot 3"));

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);
        new PlayPhase().execute(game);

        assertEquals("All non-judge players should submit 1 card",
                     players.size() - 1, game.getSubmittedCards().size());
    }

    @Test
    public void rule8_randomiseSubmissionOrder() throws Exception {
        Game game = new Game(
            loadGreenDeck(),
            loadRedDeck(),
            Arrays.asList(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"), new BotPlayer("Bot 3"))
        );

        // Initial dealing so players have red cards
        new phase.ReplenishPhase().execute(game);

        // Draw a green card to start the round
        new phase.DrawPhase().execute(game);

        // Capture submissions from multiple runs
        List<List<Card>> allRuns = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // repeat to reduce false positives
            game.clearSubmissions();  // you need a method in Game to reset between runs
            new phase.PlayPhase().execute(game);
            allRuns.add(new ArrayList<>(game.getSubmittedCards()));
        }

        // 1. Check every run has the same set size (one per non-judge player)
        int expected = game.getPlayers().size() - 1; 
        for (List<Card> run : allRuns) {
            assertEquals("Each run must collect one card per player (excluding judge)",
                         expected, run.size());
        }

        // 2. Check that across runs, order changes at least once
        boolean foundDifferentOrder = false;
        for (int i = 1; i < allRuns.size(); i++) {
            if (!allRuns.get(0).equals(allRuns.get(i))) {
                foundDifferentOrder = true;
                break;
            }
        }
        assertTrue("Submissions should not always be in the same order (must be randomized)",
                   foundDifferentOrder);
    }


    @Test
    public void rule10_judgeSelectsWinner() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"));

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);
        new DrawPhase().execute(game);
        new PlayPhase().execute(game);
        new JudgePhase().execute(game);

        // After judge picks, a point must be awarded
        assertTrue("At least one player must have >=1 point", game.hasWinner() || game.totalPoints() > 0);
    }

    @Test
    public void rule11_clearSubmissionsAfterRound() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"));

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);
        new DrawPhase().execute(game);
        new PlayPhase().execute(game);
        game.clearSubmissions();

        assertTrue("Submissions should be empty after round", game.getSubmittedCards().isEmpty());
    }

    @Test
    public void rule12_replenishHands() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"));

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);

        // Simulate one card played
        Player p = players.get(0);
        p.playCard();
        assertEquals(6, p.getHand().size());

        new ReplenishPhase().execute(game);
        assertEquals("Hand should be refilled to 7", 7, p.getHand().size());
    }

    @Test
    public void rule13_judgeRotates() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = List.of(new BotPlayer("Bot 1"), new BotPlayer("Bot 2"), new BotPlayer("Bot 3"));

        Game game = new Game(greens, reds, players);
        Player firstJudge = game.getCurrentJudge();
        game.nextJudge();
        Player secondJudge = game.getCurrentJudge();

        assertNotEquals("Judge should rotate", firstJudge, secondJudge);
    }
}

