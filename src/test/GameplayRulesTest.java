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
        List<Player> players = Arrays.asList(
                new BotPlayer("Bot 1"),
                new BotPlayer("Bot 2"),
                new BotPlayer("Bot 3")
        );

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);
        new PlayPhase().execute(game);

        int expected = players.size() - 1;
        assertEquals("All non-judge players should submit 1 card",
                     expected, game.getSubmittedCards().size());

        Player judge = game.getCurrentJudge();

        // Judge should NOT have played a card (still 7)
        assertEquals("Judge should not submit a card", 7, judge.getHand().size());

        // All non-judge players now have 6 cards
        for (Player p : players) {
            if (!p.equals(judge)) {
                assertEquals("Non-judge players should have 6 cards left", 6, p.getHand().size());
            }
        }

        // (Optional stronger check) None of the submitted cards belong to the judge
        for (Card c : game.getSubmittedCards()) {
            Player owner = game.getOwnerOf(c);
            assertNotEquals("Judge must not be among submitters", judge, owner);
        }
    }

    @Test
    public void rule8_randomiseSubmissionOrder() throws Exception {
        List<Player> players = Arrays.asList(
            new BotPlayer("Bot 1"),
            new BotPlayer("Bot 2"),
            new BotPlayer("Bot 3")
        );

        Game game = new Game(loadGreenDeck(), loadRedDeck(), players);
        new ReplenishPhase().execute(game);
        new DrawPhase().execute(game);

        List<List<String>> ownerOrders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            game.clearSubmissions();
            new PlayPhase().execute(game);

            // derive owner order from the ordered submissions
            List<String> owners = new ArrayList<>();
            for (Card c : game.getSubmittedCards()) {
                owners.add(game.getOwnerOf(c).getName());
            }
            ownerOrders.add(owners);

            // keep hands topped up between runs
            new ReplenishPhase().execute(game);
        }

        int expected = players.size() - 1;
        for (List<String> run : ownerOrders) {
            assertEquals(expected, run.size());
        }

        boolean varied = ownerOrders.stream().distinct().count() > 1;
        assertTrue("Submission order should vary across runs", varied);
    }



    @Test
    public void rule10_judgeSelectsWinner() throws Exception {
        Deck<GreenAppleCard> greens = loadGreenDeck();
        Deck<RedAppleCard> reds = loadRedDeck();
        List<Player> players = Arrays.asList(
                new BotPlayer("Bot 1"),
                new BotPlayer("Bot 2")
        );

        Game game = new Game(greens, reds, players);
        new ReplenishPhase().execute(game);
        new DrawPhase().execute(game);
        new PlayPhase().execute(game);

        // Before judging: no points
        for (Player p : players) {
            assertEquals(0, game.getScore(p));
        }

        new JudgePhase().execute(game);

        // After judging: exactly one player should have 1 point
        long winners = players.stream().filter(p -> game.getScore(p) == 1).count();
        assertEquals("Exactly one player should receive a point", 1, winners);
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

