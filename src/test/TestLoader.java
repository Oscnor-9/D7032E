package test;

import card.*;
import io.FileCardLoader;

public class TestLoader {

    public static Deck<GreenAppleCard> loadGreenDeck() throws Exception {
        return new Deck<>(FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new));
    }

    public static Deck<RedAppleCard> loadRedDeck() throws Exception {
        return new Deck<>(FileCardLoader.loadCards("redApples.txt", RedAppleCard::new));
    }
}
