package test;

import card.*;
import io.FileCardLoader;

/**
 * Helper class for loading test decks from the real card text files.
 * <p>
 * Used by multiple test suites to ensure consistent deck setup.
 */
public class TestLoader {

    public static Deck<GreenAppleCard> loadGreenDeck() throws Exception {
        return new Deck<>(FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new));
    }

    public static Deck<RedAppleCard> loadRedDeck() throws Exception {
        return new Deck<>(FileCardLoader.loadCards("redApples.txt", RedAppleCard::new));
    }
}
